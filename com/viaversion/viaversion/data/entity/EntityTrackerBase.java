package com.viaversion.viaversion.data.entity;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.util.Key;
import java.util.Collections;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityTrackerBase implements EntityTracker, ClientEntityIdChangeListener {
   protected final Int2ObjectMap entities = new Int2ObjectOpenHashMap();
   private final UserConnection connection;
   private final EntityType playerType;
   private Integer clientEntityId;
   private int currentWorldSectionHeight = -1;
   private int currentMinY;
   private String currentWorld;
   private int biomesSent = -1;
   private Map dimensions = Collections.emptyMap();

   public EntityTrackerBase(UserConnection connection, @Nullable EntityType playerType) {
      this.connection = connection;
      this.playerType = playerType;
   }

   public UserConnection user() {
      return this.connection;
   }

   public void addEntity(int id, EntityType type) {
      this.entities.put(id, new TrackedEntityImpl(type));
   }

   public boolean hasEntity(int id) {
      return this.entities.containsKey(id);
   }

   public @Nullable TrackedEntity entity(int entityId) {
      return (TrackedEntity)this.entities.get(entityId);
   }

   public @Nullable EntityType entityType(int id) {
      TrackedEntity entity = (TrackedEntity)this.entities.get(id);
      return entity != null ? entity.entityType() : null;
   }

   public @Nullable StoredEntityData entityData(int id) {
      TrackedEntity entity = (TrackedEntity)this.entities.get(id);
      return entity != null ? entity.data() : null;
   }

   public @Nullable StoredEntityData entityDataIfPresent(int id) {
      TrackedEntity entity = (TrackedEntity)this.entities.get(id);
      return entity != null && entity.hasData() ? entity.data() : null;
   }

   public void removeEntity(int id) {
      this.entities.remove(id);
   }

   public void clearEntities() {
      for(int id : this.entities.keySet().toIntArray()) {
         this.removeEntity(id);
      }

      if (this.clientEntityId != null) {
         this.entities.put(this.clientEntityId, new TrackedEntityImpl(this.playerType));
      }

   }

   public boolean hasClientEntityId() {
      return this.clientEntityId != null;
   }

   public int clientEntityId() throws IllegalStateException {
      if (this.clientEntityId == null) {
         throw new IllegalStateException("Client entity id not set");
      } else {
         return this.clientEntityId;
      }
   }

   public void setClientEntityId(int clientEntityId) {
      Preconditions.checkNotNull(this.playerType);
      TrackedEntity oldEntity;
      if (this.clientEntityId != null && (oldEntity = (TrackedEntity)this.entities.remove(this.clientEntityId)) != null) {
         this.entities.put(clientEntityId, oldEntity);
      } else {
         this.entities.put(clientEntityId, new TrackedEntityImpl(this.playerType));
      }

      this.clientEntityId = clientEntityId;
   }

   public int currentWorldSectionHeight() {
      return this.currentWorldSectionHeight;
   }

   public void setCurrentWorldSectionHeight(int currentWorldSectionHeight) {
      this.currentWorldSectionHeight = currentWorldSectionHeight;
   }

   public int currentMinY() {
      return this.currentMinY;
   }

   public void setCurrentMinY(int currentMinY) {
      this.currentMinY = currentMinY;
   }

   public @Nullable String currentWorld() {
      return this.currentWorld;
   }

   public void setCurrentWorld(String currentWorld) {
      this.currentWorld = currentWorld;
   }

   public int biomesSent() {
      return this.biomesSent;
   }

   public void setBiomesSent(int biomesSent) {
      this.biomesSent = biomesSent;
   }

   public EntityType playerType() {
      return this.playerType;
   }

   public @Nullable DimensionData dimensionData(String dimension) {
      return (DimensionData)this.dimensions.get(Key.stripMinecraftNamespace(dimension));
   }

   public @Nullable DimensionData dimensionData(int dimensionId) {
      return (DimensionData)this.dimensions.values().stream().filter((data) -> data.id() == dimensionId).findFirst().orElse((Object)null);
   }

   public void setDimensions(Map dimensions) {
      this.dimensions = dimensions;
   }
}
