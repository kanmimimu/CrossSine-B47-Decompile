package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;

public final class ObjectComparators {
   public static final Comparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
   public static final Comparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

   private ObjectComparators() {
   }

   public static Comparator oppositeComparator(Comparator c) {
      return (Comparator)(c instanceof OppositeComparator ? ((OppositeComparator)c).comparator : new OppositeComparator(c));
   }

   public static Comparator asObjectComparator(Comparator c) {
      return c;
   }

   protected static class NaturalImplicitComparator implements Comparator, Serializable {
      private static final long serialVersionUID = 1L;

      public final int compare(Object a, Object b) {
         return ((Comparable)a).compareTo(b);
      }

      public Comparator reversed() {
         return ObjectComparators.OPPOSITE_COMPARATOR;
      }

      private Object readResolve() {
         return ObjectComparators.NATURAL_COMPARATOR;
      }
   }

   protected static class OppositeImplicitComparator implements Comparator, Serializable {
      private static final long serialVersionUID = 1L;

      public final int compare(Object a, Object b) {
         return ((Comparable)b).compareTo(a);
      }

      public Comparator reversed() {
         return ObjectComparators.NATURAL_COMPARATOR;
      }

      private Object readResolve() {
         return ObjectComparators.OPPOSITE_COMPARATOR;
      }
   }

   protected static class OppositeComparator implements Comparator, Serializable {
      private static final long serialVersionUID = 1L;
      final Comparator comparator;

      protected OppositeComparator(Comparator c) {
         this.comparator = c;
      }

      public final int compare(Object a, Object b) {
         return this.comparator.compare(b, a);
      }

      public final Comparator reversed() {
         return this.comparator;
      }
   }
}
