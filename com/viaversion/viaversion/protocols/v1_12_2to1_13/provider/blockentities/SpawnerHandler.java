package com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.EntityNameMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.BlockEntityProvider;

public class SpawnerHandler implements BlockEntityProvider.BlockEntityHandler {
   public int transform(UserConnection user, CompoundTag tag) {
      CompoundTag data = tag.getCompoundTag("SpawnData");
      if (data != null) {
         StringTag id = data.getStringTag("id");
         if (id != null) {
            id.setValue(EntityNameMappings1_13.rewrite(id.getValue()));
         }
      }

      return -1;
   }
}
