package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;

public final class TrackedEntityImpl implements TrackedEntity {
   private final EntityType entityType;
   private StoredEntityData data;
   private boolean sentEntityData;

   public TrackedEntityImpl(EntityType entityType) {
      this.entityType = entityType;
   }

   public EntityType entityType() {
      return this.entityType;
   }

   public StoredEntityData data() {
      if (this.data == null) {
         this.data = new StoredEntityDataImpl(this.entityType);
      }

      return this.data;
   }

   public boolean hasData() {
      return this.data != null;
   }

   public boolean hasSentEntityData() {
      return this.sentEntityData;
   }

   public void sentEntityData(boolean sentEntityData) {
      this.sentEntityData = sentEntityData;
   }

   public String toString() {
      boolean var5 = this.sentEntityData;
      StoredEntityData var4 = this.data;
      EntityType var3 = this.entityType;
      return "TrackedEntityImpl{entityType=" + var3 + ", data=" + var4 + ", sentEntityData=" + var5 + "}";
   }
}
