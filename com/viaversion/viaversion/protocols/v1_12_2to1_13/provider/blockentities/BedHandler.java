package com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.BlockEntityProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.storage.BlockStorage;

public class BedHandler implements BlockEntityProvider.BlockEntityHandler {
   public int transform(UserConnection user, CompoundTag tag) {
      BlockStorage storage = (BlockStorage)user.get(BlockStorage.class);
      BlockPosition position = new BlockPosition(tag.getNumberTag("x").asInt(), tag.getNumberTag("y").asShort(), tag.getNumberTag("z").asInt());
      if (!storage.contains(position)) {
         Protocol1_12_2To1_13.LOGGER.warning("Received an bed color update packet, but there is no bed! O_o " + tag);
         return -1;
      } else {
         int blockId = storage.get(position).getOriginal() - 972 + 748;
         NumberTag color = tag.getNumberTag("color");
         if (color != null) {
            blockId += color.asInt() * 16;
         }

         return blockId;
      }
   }
}
