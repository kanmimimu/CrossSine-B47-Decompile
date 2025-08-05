package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.tasks.v1_11_1to1_12.BukkitInventoryUpdateTask;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.provider.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.storage.ItemTransactionStorage;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class BukkitInventoryQuickMoveProvider extends InventoryQuickMoveProvider {
   private final Map updateTasks = new ConcurrentHashMap();
   private final boolean supported = this.isSupported();
   private Class windowClickPacketClass;
   private Object clickTypeEnum;
   private Method nmsItemMethod;
   private Method craftPlayerHandle;
   private Field connection;
   private Method packetMethod;

   public BukkitInventoryQuickMoveProvider() {
      this.setupReflection();
   }

   public boolean registerQuickMoveAction(short windowId, short slotId, short actionId, UserConnection userConnection) {
      if (!this.supported) {
         return false;
      } else if (slotId < 0) {
         return false;
      } else {
         if (windowId == 0 && slotId >= 36 && slotId <= 45) {
            ProtocolVersion protocol = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
            if (protocol.equalTo(ProtocolVersion.v1_8)) {
               return false;
            }
         }

         ProtocolInfo info = userConnection.getProtocolInfo();
         UUID uuid = info.getUuid();
         BukkitInventoryUpdateTask updateTask = (BukkitInventoryUpdateTask)this.updateTasks.get(uuid);
         boolean registered = updateTask != null;
         if (!registered) {
            updateTask = new BukkitInventoryUpdateTask(this, uuid);
            this.updateTasks.put(uuid, updateTask);
         }

         updateTask.addItem(windowId, slotId, actionId);
         if (!registered && Via.getPlatform().isPluginEnabled()) {
            Via.getPlatform().runSync(updateTask);
         }

         return true;
      }
   }

   public Object buildWindowClickPacket(Player p, ItemTransactionStorage storage) {
      if (!this.supported) {
         return null;
      } else {
         InventoryView inv = p.getOpenInventory();
         short slotId = storage.slotId();
         Inventory tinv = inv.getTopInventory();
         InventoryType tinvtype = tinv == null ? null : tinv.getType();
         if (tinvtype != null) {
            ProtocolVersion protocol = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
            if (protocol.equalTo(ProtocolVersion.v1_8) && tinvtype == InventoryType.BREWING && slotId >= 5 && slotId <= 40) {
               --slotId;
            }
         }

         ItemStack itemstack = null;
         if (slotId <= inv.countSlots()) {
            itemstack = inv.getItem(slotId);
         } else {
            int var10001 = inv.countSlots();
            InventoryType var14 = inv.getType();
            int var13 = var10001;
            String cause = (new StringBuilder()).append("Too many inventory slots: slotId: ").append(slotId).append(" invSlotCount: ").append(var13).append(" invType: ").append(var14).append(" topInvType: ").append(tinvtype).toString();
            Via.getPlatform().getLogger().severe("Failed to get an item to create a window click packet. Please report this issue to the ViaVersion Github: " + cause);
         }

         Object packet = null;

         try {
            packet = this.windowClickPacketClass.getDeclaredConstructor().newInstance();
            Object nmsItem = itemstack == null ? null : this.nmsItemMethod.invoke((Object)null, itemstack);
            ReflectionUtil.set(packet, "a", Integer.valueOf(storage.windowId()));
            ReflectionUtil.set(packet, "slot", Integer.valueOf(slotId));
            ReflectionUtil.set(packet, "button", 0);
            ReflectionUtil.set(packet, "d", storage.actionId());
            ReflectionUtil.set(packet, "item", nmsItem);
            ProtocolVersion protocol = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
            if (protocol.equalTo(ProtocolVersion.v1_8)) {
               ReflectionUtil.set(packet, "shift", 1);
            } else if (protocol.newerThanOrEqualTo(ProtocolVersion.v1_9)) {
               ReflectionUtil.set(packet, "shift", this.clickTypeEnum);
            }
         } catch (Exception e) {
            Logger var10000 = Via.getPlatform().getLogger();
            Level var23 = Level.SEVERE;
            String var19 = e.getMessage();
            var10000.log(var23, "Failed to create a window click packet. Please report this issue to the ViaVersion Github: " + var19, e);
         }

         return packet;
      }
   }

   public boolean sendPacketToServer(Player p, Object packet) {
      if (packet == null) {
         return true;
      } else {
         try {
            Object entityPlayer = this.craftPlayerHandle.invoke(p);
            Object playerConnection = this.connection.get(entityPlayer);
            this.packetMethod.invoke(playerConnection, packet);
            return true;
         } catch (InvocationTargetException | IllegalAccessException e) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to send packet to server", e);
            return false;
         }
      }
   }

   public void onTaskExecuted(UUID uuid) {
      this.updateTasks.remove(uuid);
   }

   private void setupReflection() {
      if (this.supported) {
         try {
            this.windowClickPacketClass = NMSUtil.nms("PacketPlayInWindowClick");
            ProtocolVersion protocol = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
            if (protocol.newerThanOrEqualTo(ProtocolVersion.v1_9)) {
               Class<?> eclassz = NMSUtil.nms("InventoryClickType");
               Object[] constants = eclassz.getEnumConstants();
               this.clickTypeEnum = constants[1];
            }

            Class<?> craftItemStack = NMSUtil.obc("inventory.CraftItemStack");
            this.nmsItemMethod = craftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);
         } catch (Exception e) {
            throw new RuntimeException("Couldn't find required inventory classes", e);
         }

         try {
            this.craftPlayerHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle");
         } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find CraftPlayer", e);
         }

         try {
            this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
         } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException("Couldn't find Player Connection", e);
         }

         try {
            this.packetMethod = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", this.windowClickPacketClass);
         } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find CraftPlayer", e);
         }
      }
   }

   private boolean isSupported() {
      ProtocolVersion protocol = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
      return protocol.newerThanOrEqualTo(ProtocolVersion.v1_8) && protocol.olderThanOrEqualTo(ProtocolVersion.v1_11_1);
   }
}
