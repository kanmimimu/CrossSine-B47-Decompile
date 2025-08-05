package com.viaversion.viaversion.api.minecraft;

public enum GameMode {
   NOT_SET(""),
   SURVIVAL("Survival Mode"),
   CREATIVE("Creative Mode"),
   ADVENTURE("Adventure Mode"),
   SPECTATOR("Spectator Mode");

   private final String text;

   private GameMode(String text) {
      this.text = text;
   }

   public String text() {
      return this.text;
   }

   public static GameMode getById(int id) {
      GameMode var10000;
      switch (id) {
         case -1:
            var10000 = NOT_SET;
            break;
         case 0:
         default:
            var10000 = SURVIVAL;
            break;
         case 1:
            var10000 = CREATIVE;
            break;
         case 2:
            var10000 = ADVENTURE;
            break;
         case 3:
            var10000 = SPECTATOR;
      }

      return var10000;
   }

   // $FF: synthetic method
   private static GameMode[] $values() {
      return new GameMode[]{NOT_SET, SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR};
   }
}
