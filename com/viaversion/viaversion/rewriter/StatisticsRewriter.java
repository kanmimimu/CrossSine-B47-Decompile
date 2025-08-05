package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StatisticsRewriter {
   static final int CUSTOM_STATS_CATEGORY = 8;
   final Protocol protocol;

   public StatisticsRewriter(Protocol protocol) {
      this.protocol = protocol;
   }

   public void register(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);
         int newSize = size;

         for(int i = 0; i < size; ++i) {
            int categoryId = (Integer)wrapper.read(Types.VAR_INT);
            int statisticId = (Integer)wrapper.read(Types.VAR_INT);
            int value = (Integer)wrapper.read(Types.VAR_INT);
            if (categoryId == 8 && this.protocol.getMappingData().getStatisticsMappings() != null) {
               statisticId = this.protocol.getMappingData().getStatisticsMappings().getNewId(statisticId);
               if (statisticId == -1) {
                  --newSize;
                  continue;
               }
            } else {
               RegistryType type = this.getRegistryTypeForStatistic(categoryId);
               IdRewriteFunction statisticsRewriter;
               if (type != null && (statisticsRewriter = this.getRewriter(type)) != null) {
                  statisticId = statisticsRewriter.rewrite(statisticId);
               }
            }

            wrapper.write(Types.VAR_INT, categoryId);
            wrapper.write(Types.VAR_INT, statisticId);
            wrapper.write(Types.VAR_INT, value);
         }

         if (newSize != size) {
            wrapper.set(Types.VAR_INT, 0, newSize);
         }

      }));
   }

   protected @Nullable IdRewriteFunction getRewriter(RegistryType type) {
      IdRewriteFunction var10000;
      switch (type) {
         case BLOCK:
            var10000 = this.protocol.getMappingData().getBlockMappings() != null ? (id) -> this.protocol.getMappingData().getNewBlockId(id) : null;
            break;
         case ITEM:
            var10000 = this.protocol.getMappingData().getItemMappings() != null ? (id) -> this.protocol.getMappingData().getNewItemId(id) : null;
            break;
         case ENTITY:
            var10000 = this.protocol.getEntityRewriter() != null ? (id) -> this.protocol.getEntityRewriter().newEntityId(id) : null;
            break;
         default:
            throw new IllegalArgumentException("Unknown registry type in statistics packet: " + type);
      }

      return var10000;
   }

   public @Nullable RegistryType getRegistryTypeForStatistic(int statisticsId) {
      RegistryType var10000;
      switch (statisticsId) {
         case 0:
            var10000 = RegistryType.BLOCK;
            break;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            var10000 = RegistryType.ITEM;
            break;
         case 6:
         case 7:
            var10000 = RegistryType.ENTITY;
            break;
         default:
            var10000 = null;
      }

      return var10000;
   }
}
