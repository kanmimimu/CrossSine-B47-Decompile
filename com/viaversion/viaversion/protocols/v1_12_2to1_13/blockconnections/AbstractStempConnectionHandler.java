package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractStempConnectionHandler implements ConnectionHandler {
   private static final BlockFace[] BLOCK_FACES;
   private final IntSet blockId = new IntOpenHashSet();
   private final int baseStateId;
   private final Map stemps = new EnumMap(BlockFace.class);

   protected AbstractStempConnectionHandler(String baseStateId) {
      this.baseStateId = ConnectionData.getId(baseStateId);
   }

   ConnectionData.ConnectorInitAction getInitAction(String blockId, String toKey) {
      return (blockData) -> {
         if (blockData.getSavedBlockStateId() == thisx.baseStateId || blockId.equals(blockData.getMinecraftKey())) {
            if (blockData.getSavedBlockStateId() != thisx.baseStateId) {
               this.blockId.add(blockData.getSavedBlockStateId());
            }

            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), this);
         }

         if (blockData.getMinecraftKey().equals(toKey)) {
            String facing = blockData.getValue("facing").toUpperCase(Locale.ROOT);
            thisx.stemps.put(BlockFace.valueOf(facing), blockData.getSavedBlockStateId());
         }

      };
   }

   public int connect(UserConnection user, BlockPosition position, int blockState) {
      if (blockState != this.baseStateId) {
         return blockState;
      } else {
         for(BlockFace blockFace : BLOCK_FACES) {
            if (this.blockId.contains(this.getBlockData(user, position.getRelative(blockFace)))) {
               return (Integer)this.stemps.get(blockFace);
            }
         }

         return this.baseStateId;
      }
   }

   static {
      BLOCK_FACES = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
   }
}
