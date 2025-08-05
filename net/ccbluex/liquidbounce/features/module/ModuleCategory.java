package net.ccbluex.liquidbounce.features.module;

import kotlin.Metadata;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "", "(Ljava/lang/String;I)V", "CLIENT", "COMBAT", "PLAYER", "MOVEMENT", "VISUAL", "WORLD", "CONFIG", "INFO", "CrossSine"}
)
public enum ModuleCategory {
   CLIENT,
   COMBAT,
   PLAYER,
   MOVEMENT,
   VISUAL,
   WORLD,
   CONFIG,
   INFO;

   // $FF: synthetic method
   private static final ModuleCategory[] $values() {
      ModuleCategory[] var0 = new ModuleCategory[]{CLIENT, COMBAT, PLAYER, MOVEMENT, VISUAL, WORLD, CONFIG, INFO};
      return var0;
   }
}
