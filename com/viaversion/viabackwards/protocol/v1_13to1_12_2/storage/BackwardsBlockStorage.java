package com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsBlockStorage implements StorableObject {
   private static final IntSet WHITELIST = new IntOpenHashSet(779);
   private final Map blocks = new ConcurrentHashMap();

   public void checkAndStore(BlockPosition position, int block) {
      if (!WHITELIST.contains(block)) {
         this.blocks.remove(position);
      } else {
         this.blocks.put(position, block);
      }
   }

   public @Nullable Integer get(BlockPosition position) {
      return (Integer)this.blocks.get(position);
   }

   public int remove(BlockPosition position) {
      return (Integer)this.blocks.remove(position);
   }

   public void clear() {
      this.blocks.clear();
   }

   public Map getBlocks() {
      return this.blocks;
   }

   static {
      for(int i = 5265; i <= 5286; ++i) {
         WHITELIST.add(i);
      }

      for(int i = 0; i < 256; ++i) {
         WHITELIST.add(748 + i);
      }

      for(int i = 6854; i <= 7173; ++i) {
         WHITELIST.add(i);
      }

      WHITELIST.add(1647);

      for(int i = 5447; i <= 5566; ++i) {
         WHITELIST.add(i);
      }

      for(int i = 1028; i <= 1039; ++i) {
         WHITELIST.add(i);
      }

      for(int i = 1047; i <= 1082; ++i) {
         WHITELIST.add(i);
      }

      for(int i = 1099; i <= 1110; ++i) {
         WHITELIST.add(i);
      }

   }
}
