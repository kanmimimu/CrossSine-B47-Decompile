package net.ccbluex.liquidbounce.utils.misc;

public enum Direction {
   FORWARDS,
   BACKWARDS;

   public Direction opposite() {
      return this == FORWARDS ? BACKWARDS : FORWARDS;
   }

   public boolean forwards() {
      return this == FORWARDS;
   }

   public boolean backwards() {
      return this == BACKWARDS;
   }
}
