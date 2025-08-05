package com.viaversion.viaversion.protocols.v1_8to1_9.storage;

import com.google.common.cache.CacheBuilder;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.GameMode;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.BossBarProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.EntityIdProvider;
import com.viaversion.viaversion.util.ComponentUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EntityTracker1_9 extends EntityTrackerBase {
   public static final String WITHER_TRANSLATABLE = "{\"translate\":\"entity.WitherBoss.name\"}";
   public static final String DRAGON_TRANSLATABLE = "{\"translate\":\"entity.EnderDragon.name\"}";
   private final Int2ObjectMap uuidMap = new Int2ObjectOpenHashMap();
   private final Int2IntMap vehicleMap = new Int2IntOpenHashMap();
   private final Int2ObjectMap bossBarMap = new Int2ObjectOpenHashMap();
   private final IntSet validBlocking = new IntOpenHashSet();
   private final IntSet knownHolograms = new IntOpenHashSet();
   private final Set blockInteractions;
   private boolean blocking;
   private boolean autoTeam;
   private BlockPosition currentlyDigging;
   private boolean teamExists;
   private GameMode gameMode;
   private String currentTeam;
   private int heldItemSlot;
   private Item itemInSecondHand;

   public EntityTracker1_9(UserConnection user) {
      super(user, EntityTypes1_9.EntityType.PLAYER);
      this.blockInteractions = Collections.newSetFromMap(CacheBuilder.newBuilder().maximumSize(1000L).expireAfterAccess(250L, TimeUnit.MILLISECONDS).build().asMap());
   }

   public UUID getEntityUUID(int id) {
      return (UUID)this.uuidMap.computeIfAbsent(id, (Int2ObjectFunction)((k) -> UUID.randomUUID()));
   }

   public void setSecondHand(Item item) {
      this.setSecondHand(this.clientEntityId(), item);
   }

   public void setSecondHand(int entityID, Item item) {
      PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.SET_EQUIPPED_ITEM, (ByteBuf)null, this.user());
      wrapper.write(Types.VAR_INT, entityID);
      wrapper.write(Types.VAR_INT, 1);
      wrapper.write(Types.ITEM1_8, this.itemInSecondHand = item);
      wrapper.scheduleSend(Protocol1_8To1_9.class);
   }

   public Item getItemInSecondHand() {
      return this.itemInSecondHand;
   }

   public void syncShieldWithSword() {
      boolean swordInHand = this.hasSwordInHand();
      if (!swordInHand || this.itemInSecondHand == null) {
         this.setSecondHand(swordInHand ? new DataItem(442, (byte)1, (short)0, (CompoundTag)null) : null);
      }

   }

   public boolean hasSwordInHand() {
      InventoryTracker inventoryTracker = (InventoryTracker)this.user().get(InventoryTracker.class);
      int inventorySlot = this.heldItemSlot + 36;
      int itemIdentifier = inventoryTracker.getItemId((short)0, (short)inventorySlot);
      return Protocol1_8To1_9.isSword(itemIdentifier);
   }

   public void removeEntity(int entityId) {
      super.removeEntity(entityId);
      this.vehicleMap.remove(entityId);
      this.uuidMap.remove(entityId);
      this.validBlocking.remove(entityId);
      this.knownHolograms.remove(entityId);
      BossBar bar = (BossBar)this.bossBarMap.remove(entityId);
      if (bar != null) {
         bar.hide();
         ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleRemove(this.user(), bar.getId());
      }

   }

   public boolean interactedBlockRecently(int x, int y, int z) {
      for(BlockPosition position : this.blockInteractions) {
         if (Math.abs(position.x() - x) <= 1 && Math.abs(position.y() - y) <= 1 && Math.abs(position.z() - z) <= 1) {
            return true;
         }
      }

      return false;
   }

   public void addBlockInteraction(BlockPosition p) {
      this.blockInteractions.add(p);
   }

   public void handleEntityData(int entityId, List entityDataList) {
      EntityType type = this.entityType(entityId);
      if (type != null) {
         for(EntityData entityData : new ArrayList(entityDataList)) {
            if (type == EntityTypes1_9.EntityType.SKELETON && this.getDataByIndex(entityDataList, 12) == null) {
               entityDataList.add(new EntityData(12, EntityDataTypes1_9.BOOLEAN, true));
            }

            if (type == EntityTypes1_9.EntityType.HORSE && entityData.id() == 16) {
               int value = (Integer)entityData.value();
               if (value < 0 || value > 3) {
                  entityData.setValue(0);
               }
            }

            if (type == EntityTypes1_9.EntityType.PLAYER) {
               if (entityData.id() == 0) {
                  byte data = (Byte)entityData.getValue();
                  if (entityId != this.getProvidedEntityId() && Via.getConfig().isShieldBlocking()) {
                     if ((data & 16) == 16) {
                        if (this.validBlocking.contains(entityId)) {
                           Item shield = new DataItem(442, (byte)1, (short)0, (CompoundTag)null);
                           this.setSecondHand(entityId, shield);
                        } else {
                           this.setSecondHand(entityId, (Item)null);
                        }
                     } else {
                        this.setSecondHand(entityId, (Item)null);
                     }
                  }
               }

               if (entityData.id() == 12 && Via.getConfig().isLeftHandedHandling()) {
                  entityDataList.add(new EntityData(13, EntityDataTypes1_9.BYTE, (byte)(((Byte)entityData.getValue() & 128) != 0 ? 0 : 1)));
               }
            }

            if (type == EntityTypes1_9.EntityType.ARMOR_STAND && Via.getConfig().isHologramPatch() && entityData.id() == 0 && this.getDataByIndex(entityDataList, 10) != null) {
               EntityData data = this.getDataByIndex(entityDataList, 10);
               byte value = (Byte)entityData.getValue();
               EntityData displayName;
               EntityData displayNameVisible;
               if ((value & 32) == 32 && ((Byte)data.getValue() & 1) == 1 && (displayName = this.getDataByIndex(entityDataList, 2)) != null && !((String)displayName.getValue()).isEmpty() && (displayNameVisible = this.getDataByIndex(entityDataList, 3)) != null && (Boolean)displayNameVisible.getValue() && !this.knownHolograms.contains(entityId)) {
                  this.knownHolograms.add(entityId);
                  PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.MOVE_ENTITY_POS, (ByteBuf)null, this.user());
                  wrapper.write(Types.VAR_INT, entityId);
                  wrapper.write(Types.SHORT, Short.valueOf((short)0));
                  wrapper.write(Types.SHORT, (short)((int)((double)128.0F * Via.getConfig().getHologramYOffset() * (double)32.0F)));
                  wrapper.write(Types.SHORT, Short.valueOf((short)0));
                  wrapper.write(Types.BOOLEAN, true);
                  wrapper.scheduleSend(Protocol1_8To1_9.class);
               }
            }

            if (Via.getConfig().isBossbarPatch() && (type == EntityTypes1_9.EntityType.ENDER_DRAGON || type == EntityTypes1_9.EntityType.WITHER)) {
               if (entityData.id() == 2) {
                  BossBar bar = (BossBar)this.bossBarMap.get(entityId);
                  String title = (String)entityData.getValue();
                  if (title.isEmpty()) {
                     title = type == EntityTypes1_9.EntityType.ENDER_DRAGON ? "{\"translate\":\"entity.EnderDragon.name\"}" : "{\"translate\":\"entity.WitherBoss.name\"}";
                  } else {
                     title = ComponentUtil.plainToJson(title).toString();
                  }

                  if (bar == null) {
                     bar = Via.getAPI().legacyAPI().createLegacyBossBar(title, BossColor.PINK, BossStyle.SOLID);
                     this.bossBarMap.put(entityId, bar);
                     bar.addConnection(this.user());
                     bar.show();
                     ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(this.user(), bar.getId());
                  } else {
                     bar.setTitle(title);
                  }
               } else if (entityData.id() == 6 && !Via.getConfig().isBossbarAntiflicker()) {
                  BossBar bar = (BossBar)this.bossBarMap.get(entityId);
                  float maxHealth = type == EntityTypes1_9.EntityType.ENDER_DRAGON ? 200.0F : 300.0F;
                  float health = Math.max(0.0F, Math.min((Float)entityData.getValue() / maxHealth, 1.0F));
                  if (bar == null) {
                     String title = type == EntityTypes1_9.EntityType.ENDER_DRAGON ? "{\"translate\":\"entity.EnderDragon.name\"}" : "{\"translate\":\"entity.WitherBoss.name\"}";
                     bar = Via.getAPI().legacyAPI().createLegacyBossBar(title, health, BossColor.PINK, BossStyle.SOLID);
                     this.bossBarMap.put(entityId, bar);
                     bar.addConnection(this.user());
                     bar.show();
                     ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(this.user(), bar.getId());
                  } else {
                     bar.setHealth(health);
                  }
               }
            }
         }

      }
   }

   public EntityData getDataByIndex(List list, int index) {
      for(EntityData data : list) {
         if (index == data.id()) {
            return data;
         }
      }

      return null;
   }

   public void sendTeamPacket(boolean add, boolean now) {
      PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.SET_PLAYER_TEAM, (ByteBuf)null, this.user());
      wrapper.write(Types.STRING, "viaversion");
      if (add) {
         if (!this.teamExists) {
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.STRING, "viaversion");
            wrapper.write(Types.STRING, "§f");
            wrapper.write(Types.STRING, "");
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.STRING, "");
            wrapper.write(Types.STRING, "never");
            wrapper.write(Types.BYTE, (byte)15);
         } else {
            wrapper.write(Types.BYTE, (byte)3);
         }

         wrapper.write(Types.STRING_ARRAY, new String[]{this.user().getProtocolInfo().getUsername()});
      } else {
         wrapper.write(Types.BYTE, (byte)1);
      }

      this.teamExists = add;
      if (now) {
         wrapper.send(Protocol1_8To1_9.class);
      } else {
         wrapper.scheduleSend(Protocol1_8To1_9.class);
      }

   }

   public int getProvidedEntityId() {
      try {
         return ((EntityIdProvider)Via.getManager().getProviders().get(EntityIdProvider.class)).getEntityId(this.user());
      } catch (Exception var2) {
         return this.clientEntityId();
      }
   }

   public Int2ObjectMap getUuidMap() {
      return this.uuidMap;
   }

   public Int2IntMap getVehicleMap() {
      return this.vehicleMap;
   }

   public Int2ObjectMap getBossBarMap() {
      return this.bossBarMap;
   }

   public IntSet getValidBlocking() {
      return this.validBlocking;
   }

   public IntSet getKnownHolograms() {
      return this.knownHolograms;
   }

   public Set getBlockInteractions() {
      return this.blockInteractions;
   }

   public boolean isBlocking() {
      return this.blocking;
   }

   public void setBlocking(boolean blocking) {
      this.blocking = blocking;
   }

   public boolean isAutoTeam() {
      return this.autoTeam;
   }

   public void setAutoTeam(boolean autoTeam) {
      this.autoTeam = autoTeam;
   }

   public BlockPosition getCurrentlyDigging() {
      return this.currentlyDigging;
   }

   public void setCurrentlyDigging(BlockPosition currentlyDigging) {
      this.currentlyDigging = currentlyDigging;
   }

   public boolean isTeamExists() {
      return this.teamExists;
   }

   public GameMode getGameMode() {
      return this.gameMode;
   }

   public void setGameMode(GameMode gameMode) {
      this.gameMode = gameMode;
   }

   public String getCurrentTeam() {
      return this.currentTeam;
   }

   public void setCurrentTeam(String currentTeam) {
      this.currentTeam = currentTeam;
   }

   public void setHeldItemSlot(int heldItemSlot) {
      this.heldItemSlot = heldItemSlot;
   }
}
