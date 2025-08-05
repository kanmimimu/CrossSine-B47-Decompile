package net.ccbluex.liquidbounce.utils.render;

public class Pair {
   private final Object first;
   private final Object second;

   public Pair(Object first, Object second) {
      this.first = first;
      this.second = second;
   }

   public Object getFirst() {
      return this.first;
   }

   public Object getSecond() {
      return this.second;
   }
}
