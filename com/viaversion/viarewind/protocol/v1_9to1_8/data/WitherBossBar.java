package com.viaversion.viarewind.protocol.v1_9to1_8.data;

import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WitherBossBar implements BossBar {
   private static int highestId = 2147473647;
   private final UUID uuid;
   private String title;
   private float health;
   private boolean visible = false;
   private final UserConnection connection;
   private final int entityId;
   private double locX;
   private double locY;
   private double locZ;

   public WitherBossBar(UserConnection connection, UUID uuid, String title, float health) {
      this.entityId = highestId++;
      this.connection = connection;
      this.uuid = uuid;
      this.title = title;
      this.health = health;
   }

   public String getTitle() {
      return this.title;
   }

   public BossBar setTitle(String title) {
      this.title = title;
      if (this.visible) {
         this.updateEntityData();
      }

      return this;
   }

   public float getHealth() {
      return this.health;
   }

   public BossBar setHealth(float health) {
      this.health = health;
      if (this.health <= 0.0F) {
         this.health = 1.0E-4F;
      }

      if (this.visible) {
         this.updateEntityData();
      }

      return this;
   }

   public BossColor getColor() {
      return null;
   }

   public BossBar setColor(BossColor bossColor) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " does not support color");
   }

   public BossStyle getStyle() {
      return null;
   }

   public BossBar setStyle(BossStyle bossStyle) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " does not support styles");
   }

   public BossBar addPlayer(UUID uuid) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " is only for one UserConnection!");
   }

   public BossBar addConnection(UserConnection userConnection) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " is only for one UserConnection!");
   }

   public BossBar removePlayer(UUID uuid) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " is only for one UserConnection!");
   }

   public BossBar removeConnection(UserConnection userConnection) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " is only for one UserConnection!");
   }

   public BossBar addFlag(BossFlag bossFlag) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " does not support flags");
   }

   public BossBar removeFlag(BossFlag bossFlag) {
      String var4 = this.getClass().getName();
      throw new UnsupportedOperationException(var4 + " does not support flags");
   }

   public boolean hasFlag(BossFlag bossFlag) {
      return false;
   }

   public Set getPlayers() {
      return Collections.singleton(this.connection.getProtocolInfo().getUuid());
   }

   public Set getConnections() {
      String var3 = this.getClass().getName();
      throw new UnsupportedOperationException(var3 + " is only for one UserConnection!");
   }

   public BossBar show() {
      if (!this.visible) {
         this.addWither();
         this.visible = true;
      }

      return this;
   }

   public BossBar hide() {
      if (this.visible) {
         this.removeWither();
         this.visible = false;
      }

      return this;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public UUID getId() {
      return this.uuid;
   }

   public void setLocation(double x, double y, double z) {
      this.locX = x;
      this.locY = y;
      this.locZ = z;
      this.updateLocation();
   }

   private void addWither() {
      PacketWrapper addMob = PacketWrapper.create(ClientboundPackets1_7_2_5.ADD_MOB, (UserConnection)this.connection);
      addMob.write(Types.VAR_INT, this.entityId);
      addMob.write(Types.UNSIGNED_BYTE, Short.valueOf((short)64));
      addMob.write(Types.INT, (int)(this.locX * (double)32.0F));
      addMob.write(Types.INT, (int)(this.locY * (double)32.0F));
      addMob.write(Types.INT, (int)(this.locZ * (double)32.0F));
      addMob.write(Types.BYTE, (byte)0);
      addMob.write(Types.BYTE, (byte)0);
      addMob.write(Types.BYTE, (byte)0);
      addMob.write(Types.SHORT, Short.valueOf((short)0));
      addMob.write(Types.SHORT, Short.valueOf((short)0));
      addMob.write(Types.SHORT, Short.valueOf((short)0));
      List<EntityData> entityData = new ArrayList();
      entityData.add(new EntityData(0, EntityDataTypes1_8.BYTE, (byte)32));
      entityData.add(new EntityData(2, EntityDataTypes1_8.STRING, this.title));
      entityData.add(new EntityData(3, EntityDataTypes1_8.BYTE, (byte)1));
      entityData.add(new EntityData(6, EntityDataTypes1_8.FLOAT, this.health * 300.0F));
      addMob.write(Types1_8.ENTITY_DATA_LIST, entityData);
      addMob.scheduleSend(Protocol1_9To1_8.class);
   }

   private void updateLocation() {
      PacketWrapper teleportEntity = PacketWrapper.create(ClientboundPackets1_7_2_5.TELEPORT_ENTITY, (UserConnection)this.connection);
      teleportEntity.write(Types.VAR_INT, this.entityId);
      teleportEntity.write(Types.INT, (int)(this.locX * (double)32.0F));
      teleportEntity.write(Types.INT, (int)(this.locY * (double)32.0F));
      teleportEntity.write(Types.INT, (int)(this.locZ * (double)32.0F));
      teleportEntity.write(Types.BYTE, (byte)0);
      teleportEntity.write(Types.BYTE, (byte)0);
      teleportEntity.write(Types.BOOLEAN, false);
      teleportEntity.scheduleSend(Protocol1_9To1_8.class);
   }

   private void updateEntityData() {
      PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_DATA, (UserConnection)this.connection);
      setEntityData.write(Types.VAR_INT, this.entityId);
      List<EntityData> entityData = new ArrayList();
      entityData.add(new EntityData(2, EntityDataTypes1_8.STRING, this.title));
      entityData.add(new EntityData(6, EntityDataTypes1_8.FLOAT, this.health * 300.0F));
      setEntityData.write(Types1_8.ENTITY_DATA_LIST, entityData);
      setEntityData.scheduleSend(Protocol1_9To1_8.class);
   }

   private void removeWither() {
      PacketWrapper removeEntity = PacketWrapper.create(ClientboundPackets1_7_2_5.REMOVE_ENTITIES, (UserConnection)this.connection);
      removeEntity.write(Types.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.entityId});
      removeEntity.scheduleSend(Protocol1_9To1_8.class);
   }

   public void setPlayerLocation(double posX, double posY, double posZ, float yaw, float pitch) {
      double yawR = Math.toRadians((double)yaw);
      double pitchR = Math.toRadians((double)pitch);
      posX -= Math.cos(pitchR) * Math.sin(yawR) * (double)48.0F;
      posY -= Math.sin(pitchR) * (double)48.0F;
      posZ += Math.cos(pitchR) * Math.cos(yawR) * (double)48.0F;
      this.setLocation(posX, posY, posZ);
   }
}
