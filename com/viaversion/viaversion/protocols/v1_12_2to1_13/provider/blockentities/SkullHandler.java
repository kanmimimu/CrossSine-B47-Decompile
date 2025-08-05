package com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.BlockEntityProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.storage.BlockStorage;

public class SkullHandler implements BlockEntityProvider.BlockEntityHandler {
   private static final int SKULL_WALL_START = 5447;
   private static final int SKULL_END = 5566;

   public int transform(UserConnection user, CompoundTag tag) {
      BlockStorage storage = (BlockStorage)user.get(BlockStorage.class);
      BlockPosition position = new BlockPosition(tag.getNumberTag("x").asInt(), tag.getNumberTag("y").asShort(), tag.getNumberTag("z").asInt());
      if (!storage.contains(position)) {
         Protocol1_12_2To1_13.LOGGER.warning("Received an head update packet, but there is no head! O_o " + tag);
         return -1;
      } else {
         int id = storage.get(position).getOriginal();
         if (id >= 5447 && id <= 5566) {
            NumberTag skullType = tag.getNumberTag("SkullType");
            if (skullType != null) {
               id += skullType.asInt() * 20;
            }

            NumberTag rot = tag.getNumberTag("Rot");
            if (rot != null) {
               id += rot.asInt();
            }

            return id;
         } else {
            Protocol1_12_2To1_13.LOGGER.warning("Why does this block have the skull block entity? " + tag);
            return -1;
         }
      }
   }
}
