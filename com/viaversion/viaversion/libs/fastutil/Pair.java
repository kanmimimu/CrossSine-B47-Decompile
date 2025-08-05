package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutablePair;
import java.util.Comparator;

public interface Pair {
   Object left();

   Object right();

   default Pair left(Object l) {
      throw new UnsupportedOperationException();
   }

   default Pair right(Object r) {
      throw new UnsupportedOperationException();
   }

   default Object first() {
      return this.left();
   }

   default Object second() {
      return this.right();
   }

   default Pair first(Object l) {
      return this.left(l);
   }

   default Pair second(Object r) {
      return this.right(r);
   }

   default Pair key(Object l) {
      return this.left(l);
   }

   default Pair value(Object r) {
      return this.right(r);
   }

   default Object key() {
      return this.left();
   }

   default Object value() {
      return this.right();
   }

   static Pair of(Object l, Object r) {
      return new ObjectObjectImmutablePair(l, r);
   }

   static Comparator lexComparator() {
      return (x, y) -> {
         int t = ((Comparable)x.left()).compareTo(y.left());
         return t != 0 ? t : ((Comparable)x.right()).compareTo(y.right());
      };
   }
}
