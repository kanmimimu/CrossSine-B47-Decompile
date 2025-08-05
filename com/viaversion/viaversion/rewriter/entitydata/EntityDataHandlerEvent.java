package com.viaversion.viaversion.rewriter.entitydata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityDataHandlerEvent {
   UserConnection user();

   int entityId();

   @Nullable TrackedEntity trackedEntity();

   default @Nullable EntityType entityType() {
      return this.trackedEntity() != null ? this.trackedEntity().entityType() : null;
   }

   default int index() {
      return this.data().id();
   }

   default void setIndex(int index) {
      this.data().setId(index);
   }

   EntityData data();

   void cancel();

   boolean cancelled();

   @Nullable EntityData dataAtIndex(int var1);

   List dataList();

   @Nullable List extraData();

   default boolean hasExtraData() {
      return this.extraData() != null;
   }

   void createExtraData(EntityData var1);
}
