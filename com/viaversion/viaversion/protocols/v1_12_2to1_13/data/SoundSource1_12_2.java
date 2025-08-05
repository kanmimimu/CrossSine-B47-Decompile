package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import java.util.Optional;

public enum SoundSource1_12_2 {
   MASTER("master", 0),
   MUSIC("music", 1),
   RECORD("record", 2),
   WEATHER("weather", 3),
   BLOCK("block", 4),
   HOSTILE("hostile", 5),
   NEUTRAL("neutral", 6),
   PLAYER("player", 7),
   AMBIENT("ambient", 8),
   VOICE("voice", 9);

   private final String name;
   private final int id;

   private SoundSource1_12_2(String name, int id) {
      this.name = name;
      this.id = id;
   }

   public static Optional findBySource(String source) {
      for(SoundSource1_12_2 item : values()) {
         if (item.name.equalsIgnoreCase(source)) {
            return Optional.of(item);
         }
      }

      return Optional.empty();
   }

   public String getName() {
      return this.name;
   }

   public int getId() {
      return this.id;
   }

   // $FF: synthetic method
   private static SoundSource1_12_2[] $values() {
      return new SoundSource1_12_2[]{MASTER, MUSIC, RECORD, WEATHER, BLOCK, HOSTILE, NEUTRAL, PLAYER, AMBIENT, VOICE};
   }
}
