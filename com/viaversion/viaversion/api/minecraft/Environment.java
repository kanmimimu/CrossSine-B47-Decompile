package com.viaversion.viaversion.api.minecraft;

public enum Environment {
   NORMAL(0),
   NETHER(-1),
   END(1);

   private final int id;

   private Environment(int id) {
      this.id = id;
   }

   public int id() {
      return this.id;
   }

   public static Environment getEnvironmentById(int id) {
      Environment var10000;
      switch (id) {
         case 0:
            var10000 = NORMAL;
            break;
         case 1:
            var10000 = END;
            break;
         default:
            var10000 = NETHER;
      }

      return var10000;
   }

   // $FF: synthetic method
   private static Environment[] $values() {
      return new Environment[]{NORMAL, NETHER, END};
   }
}
