package com.viaversion.viaversion.rewriter.entitydata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityDataHandlerEventImpl implements EntityDataHandlerEvent {
   private final UserConnection connection;
   private final TrackedEntity trackedEntity;
   private final int entityId;
   private final List dataList;
   private final EntityData data;
   private List extraData;
   private boolean cancel;

   public EntityDataHandlerEventImpl(UserConnection connection, @Nullable TrackedEntity trackedEntity, int entityId, EntityData data, List dataList) {
      this.connection = connection;
      this.trackedEntity = trackedEntity;
      this.entityId = entityId;
      this.data = data;
      this.dataList = dataList;
   }

   public UserConnection user() {
      return this.connection;
   }

   public int entityId() {
      return this.entityId;
   }

   public @Nullable TrackedEntity trackedEntity() {
      return this.trackedEntity;
   }

   public EntityData data() {
      return this.data;
   }

   public void cancel() {
      this.cancel = true;
   }

   public boolean cancelled() {
      return this.cancel;
   }

   public @Nullable EntityData dataAtIndex(int index) {
      for(EntityData data : this.dataList) {
         if (index == data.id()) {
            return data;
         }
      }

      return null;
   }

   public List dataList() {
      return Collections.unmodifiableList(this.dataList);
   }

   public @Nullable List extraData() {
      return this.extraData;
   }

   public void createExtraData(EntityData entityData) {
      if (this.extraData == null) {
         this.extraData = new ArrayList();
      }

      this.extraData.add(entityData);
   }
}
