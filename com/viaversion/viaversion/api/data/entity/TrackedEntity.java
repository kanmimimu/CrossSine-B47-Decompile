package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;

public interface TrackedEntity {
   EntityType entityType();

   StoredEntityData data();

   boolean hasData();

   boolean hasSentEntityData();

   void sentEntityData(boolean var1);
}
