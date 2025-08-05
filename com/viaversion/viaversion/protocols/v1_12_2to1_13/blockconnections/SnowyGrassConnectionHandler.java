package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import java.util.HashSet;
import java.util.Set;

public class SnowyGrassConnectionHandler implements ConnectionHandler {
   static final Object2IntMap GRASS_BLOCKS = new Object2IntOpenHashMap();
   static final IntSet SNOWY_GRASS_BLOCKS = new IntOpenHashSet();

   static ConnectionData.ConnectorInitAction init() {
      Set<String> snowyGrassBlocks = new HashSet();
      snowyGrassBlocks.add("minecraft:grass_block");
      snowyGrassBlocks.add("minecraft:podzol");
      snowyGrassBlocks.add("minecraft:mycelium");
      GRASS_BLOCKS.defaultReturnValue(-1);
      SnowyGrassConnectionHandler handler = new SnowyGrassConnectionHandler();
      return (blockData) -> {
         if (snowyGrassBlocks.contains(blockData.getMinecraftKey())) {
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
            blockData.set("snowy", "true");
            GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), true), blockData.getBlockStateId());
            blockData.set("snowy", "false");
            GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), false), blockData.getBlockStateId());
         }

         if (blockData.getMinecraftKey().equals("minecraft:snow") || blockData.getMinecraftKey().equals("minecraft:snow_block")) {
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
            SNOWY_GRASS_BLOCKS.add(blockData.getSavedBlockStateId());
         }

      };
   }

   public int connect(UserConnection user, BlockPosition position, int blockState) {
      int blockUpId = this.getBlockData(user, position.getRelative(BlockFace.TOP));
      int newId = GRASS_BLOCKS.getInt(new GrassBlock(blockState, SNOWY_GRASS_BLOCKS.contains(blockUpId)));
      return newId != -1 ? newId : blockState;
   }

   private static final class GrassBlock {
      final int blockStateId;
      final boolean snowy;

      GrassBlock(int blockStateId, boolean snowy) {
         this.blockStateId = blockStateId;
         this.snowy = snowy;
      }

      public int blockStateId() {
         return this.blockStateId;
      }

      public boolean snowy() {
         return this.snowy;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof GrassBlock)) {
            return false;
         } else {
            GrassBlock var2 = (GrassBlock)var1;
            return this.blockStateId == var2.blockStateId && this.snowy == var2.snowy;
         }
      }

      public int hashCode() {
         return (0 * 31 + Integer.hashCode(this.blockStateId)) * 31 + Boolean.hashCode(this.snowy);
      }

      public String toString() {
         return String.format("%s[blockStateId=%s, snowy=%s]", this.getClass().getSimpleName(), Integer.toString(this.blockStateId), Boolean.toString(this.snowy));
      }
   }
}
