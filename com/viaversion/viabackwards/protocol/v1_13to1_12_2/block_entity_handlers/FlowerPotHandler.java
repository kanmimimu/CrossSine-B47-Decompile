package com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.util.Pair;

public class FlowerPotHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
   private static final Int2ObjectMap FLOWERS = new Int2ObjectOpenHashMap(22, 0.99F);
   private static final Pair AIR = new Pair("minecraft:air", (byte)0);

   private static void register(int id, String identifier, byte data) {
      FLOWERS.put(id, new Pair(identifier, data));
   }

   public static boolean isFlowah(int id) {
      return id >= 5265 && id <= 5286;
   }

   public Pair getOrDefault(int blockId) {
      Pair<String, Byte> pair = (Pair)FLOWERS.get(blockId);
      return pair != null ? pair : AIR;
   }

   public CompoundTag transform(int blockId, CompoundTag tag) {
      Pair<String, Byte> item = this.getOrDefault(blockId);
      tag.putString("Item", (String)item.key());
      tag.putInt("Data", (Byte)item.value());
      return tag;
   }

   static {
      FLOWERS.put(5265, AIR);
      register(5266, "minecraft:sapling", (byte)0);
      register(5267, "minecraft:sapling", (byte)1);
      register(5268, "minecraft:sapling", (byte)2);
      register(5269, "minecraft:sapling", (byte)3);
      register(5270, "minecraft:sapling", (byte)4);
      register(5271, "minecraft:sapling", (byte)5);
      register(5272, "minecraft:tallgrass", (byte)2);
      register(5273, "minecraft:yellow_flower", (byte)0);
      register(5274, "minecraft:red_flower", (byte)0);
      register(5275, "minecraft:red_flower", (byte)1);
      register(5276, "minecraft:red_flower", (byte)2);
      register(5277, "minecraft:red_flower", (byte)3);
      register(5278, "minecraft:red_flower", (byte)4);
      register(5279, "minecraft:red_flower", (byte)5);
      register(5280, "minecraft:red_flower", (byte)6);
      register(5281, "minecraft:red_flower", (byte)7);
      register(5282, "minecraft:red_flower", (byte)8);
      register(5283, "minecraft:red_mushroom", (byte)0);
      register(5284, "minecraft:brown_mushroom", (byte)0);
      register(5285, "minecraft:deadbush", (byte)0);
      register(5286, "minecraft:cactus", (byte)0);
   }
}
