package com.viaversion.viarewind.protocol.v1_9to1_8.storage;

import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.HashMap;
import java.util.Map;

public class WindowTracker extends StoredObject {
   private final HashMap types = new HashMap();
   private final HashMap brewingItems = new HashMap();
   private final Map enchantmentProperties = new HashMap();

   public WindowTracker(UserConnection user) {
      super(user);
   }

   public String get(short windowId) {
      return (String)this.types.get(windowId);
   }

   public void put(short windowId, String type) {
      this.types.put(windowId, type);
   }

   public void remove(short windowId) {
      this.types.remove(windowId);
      this.brewingItems.remove(windowId);
   }

   public Item[] getBrewingItems(short windowId) {
      return (Item[])this.brewingItems.computeIfAbsent(windowId, (key) -> new Item[]{new DataItem(), new DataItem(), new DataItem(), new DataItem()});
   }

   public short getEnchantmentValue(short key) {
      return !this.enchantmentProperties.containsKey(key) ? 0 : (Short)this.enchantmentProperties.remove(key);
   }

   public void putEnchantmentProperty(short key, short value) {
      this.enchantmentProperties.put(key, value);
   }

   public void clearEnchantmentProperties() {
      this.enchantmentProperties.clear();
   }

   public static void updateBrewingStand(UserConnection user, Item blazePowder, short windowId) {
      if (blazePowder == null || blazePowder.identifier() == 377) {
         int amount = blazePowder == null ? 0 : blazePowder.amount();
         PacketWrapper openWindow = PacketWrapper.create(ClientboundPackets1_8.OPEN_SCREEN, (UserConnection)user);
         openWindow.write(Types.UNSIGNED_BYTE, windowId);
         openWindow.write(Types.STRING, "minecraft:brewing_stand");
         ATextComponent var10000 = (new StringComponent()).append(new TranslationComponent("container.brewing", new Object[0]));
         TextFormatting var10 = TextFormatting.DARK_GRAY;
         var10000 = var10000.append((ATextComponent)(new StringComponent(": " + var10)));
         TextFormatting var13 = TextFormatting.DARK_RED;
         ATextComponent title = var10000.append((ATextComponent)(new StringComponent(amount + " " + var13))).append((ATextComponent)(new TranslationComponent("item.blazePowder.name", new Object[]{TextFormatting.DARK_RED})));
         openWindow.write(Types.COMPONENT, TextComponentSerializer.V1_8.serializeJson(title));
         openWindow.write(Types.UNSIGNED_BYTE, (short)420);
         openWindow.scheduleSend(Protocol1_9To1_8.class);
         Item[] items = ((WindowTracker)user.get(WindowTracker.class)).getBrewingItems(windowId);

         for(int i = 0; i < items.length; ++i) {
            PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_8.CONTAINER_SET_SLOT, (UserConnection)user);
            setSlot.write(Types.UNSIGNED_BYTE, windowId);
            setSlot.write(Types.SHORT, (short)i);
            setSlot.write(Types.ITEM1_8, items[i]);
            setSlot.scheduleSend(Protocol1_9To1_8.class);
         }

      }
   }
}
