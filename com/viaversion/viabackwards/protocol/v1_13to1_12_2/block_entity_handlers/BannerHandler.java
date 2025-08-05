package com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;

public class BannerHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
   private static final int WALL_BANNER_START = 7110;
   private static final int WALL_BANNER_STOP = 7173;
   private static final int BANNER_START = 6854;
   private static final int BANNER_STOP = 7109;

   public CompoundTag transform(int blockId, CompoundTag tag) {
      if (blockId >= 6854 && blockId <= 7109) {
         int color = blockId - 6854 >> 4;
         tag.putInt("Base", 15 - color);
      } else if (blockId >= 7110 && blockId <= 7173) {
         int color = blockId - 7110 >> 2;
         tag.putInt("Base", 15 - color);
      } else {
         Protocol1_13To1_12_2.LOGGER.warning("Why does this block have the banner block entity? :(" + tag);
      }

      ListTag<CompoundTag> patternsTag = tag.getListTag("Patterns", CompoundTag.class);
      if (patternsTag != null) {
         for(CompoundTag pattern : patternsTag) {
            NumberTag colorTag = pattern.getNumberTag("Color");
            if (colorTag != null) {
               pattern.putInt("Color", 15 - colorTag.asInt());
            }
         }
      }

      return tag;
   }
}
