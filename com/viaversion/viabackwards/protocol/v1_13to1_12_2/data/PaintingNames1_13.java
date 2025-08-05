package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

public class PaintingNames1_13 {
   private static final Int2ObjectMap PAINTINGS = new Int2ObjectOpenHashMap(26, 0.99F);

   public static void init() {
      add("Kebab");
      add("Aztec");
      add("Alban");
      add("Aztec2");
      add("Bomb");
      add("Plant");
      add("Wasteland");
      add("Pool");
      add("Courbet");
      add("Sea");
      add("Sunset");
      add("Creebet");
      add("Wanderer");
      add("Graham");
      add("Match");
      add("Bust");
      add("Stage");
      add("Void");
      add("SkullAndRoses");
      add("Wither");
      add("Fighters");
      add("Pointer");
      add("Pigscene");
      add("BurningSkull");
      add("Skeleton");
      add("DonkeyKong");
   }

   private static void add(String motive) {
      PAINTINGS.put(PAINTINGS.size(), motive);
   }

   public static String getStringId(int id) {
      return (String)PAINTINGS.getOrDefault(id, "kebab");
   }
}
