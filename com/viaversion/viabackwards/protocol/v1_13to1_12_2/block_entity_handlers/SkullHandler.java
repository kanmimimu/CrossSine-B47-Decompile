package com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;

public class SkullHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
   private static final int SKULL_START = 5447;

   public CompoundTag transform(int blockId, CompoundTag tag) {
      int diff = blockId - 5447;
      int pos = diff % 20;
      byte type = (byte)((int)Math.floor((double)((float)diff / 20.0F)));
      tag.putByte("SkullType", type);
      if (pos < 4) {
         return tag;
      } else {
         tag.putByte("Rot", (byte)(pos - 4 & 255));
         return tag;
      }
   }
}
