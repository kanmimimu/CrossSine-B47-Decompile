package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.Comparator;
import java.util.Objects;

@FunctionalInterface
public interface IntComparator extends Comparator {
   int compare(int var1, int var2);

   default IntComparator reversed() {
      return IntComparators.oppositeComparator(this);
   }

   /** @deprecated */
   @Deprecated
   default int compare(Integer ok1, Integer ok2) {
      return this.compare(ok1, ok2);
   }

   default IntComparator thenComparing(IntComparator second) {
      return (IntComparator)((Serializable)((k1, k2) -> {
         int comp = this.compare(k1, k2);
         return comp == 0 ? second.compare(k1, k2) : comp;
      }));
   }

   default Comparator thenComparing(Comparator second) {
      return (Comparator)(second instanceof IntComparator ? this.thenComparing((IntComparator)second) : super.thenComparing(second));
   }

   static IntComparator comparing(Int2ObjectFunction keyExtractor) {
      Objects.requireNonNull(keyExtractor);
      return (IntComparator)((Serializable)((k1, k2) -> ((Comparable)keyExtractor.get(k1)).compareTo(keyExtractor.get(k2))));
   }

   static IntComparator comparing(Int2ObjectFunction keyExtractor, Comparator keyComparator) {
      Objects.requireNonNull(keyExtractor);
      Objects.requireNonNull(keyComparator);
      return (IntComparator)((Serializable)((k1, k2) -> keyComparator.compare(keyExtractor.get(k1), keyExtractor.get(k2))));
   }

   static IntComparator comparingInt(Int2IntFunction keyExtractor) {
      Objects.requireNonNull(keyExtractor);
      return (IntComparator)((Serializable)((k1, k2) -> Integer.compare(keyExtractor.get(k1), keyExtractor.get(k2))));
   }

   static IntComparator comparingLong(Int2LongFunction keyExtractor) {
      Objects.requireNonNull(keyExtractor);
      return (IntComparator)((Serializable)((k1, k2) -> Long.compare(keyExtractor.get(k1), keyExtractor.get(k2))));
   }

   static IntComparator comparingDouble(Int2DoubleFunction keyExtractor) {
      Objects.requireNonNull(keyExtractor);
      return (IntComparator)((Serializable)((k1, k2) -> Double.compare(keyExtractor.get(k1), keyExtractor.get(k2))));
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "lambda$comparingDouble$4ab3f26c$1":
            if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getFunctionalInterfaceMethodName().equals("compare") && lambda.getFunctionalInterfaceMethodSignature().equals("(II)I") && lambda.getImplClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getImplMethodSignature().equals("(Lit/unimi/dsi/fastutil/ints/Int2DoubleFunction;II)I")) {
               return (IntComparator)(k1, k2) -> Double.compare(keyExtractor.get(k1), keyExtractor.get(k2));
            }
            break;
         case "lambda$comparingInt$c94ca11c$1":
            if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getFunctionalInterfaceMethodName().equals("compare") && lambda.getFunctionalInterfaceMethodSignature().equals("(II)I") && lambda.getImplClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getImplMethodSignature().equals("(Lit/unimi/dsi/fastutil/ints/Int2IntFunction;II)I")) {
               return (IntComparator)(k1, k2) -> Integer.compare(keyExtractor.get(k1), keyExtractor.get(k2));
            }
            break;
         case "lambda$comparing$ee768883$1":
            if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getFunctionalInterfaceMethodName().equals("compare") && lambda.getFunctionalInterfaceMethodSignature().equals("(II)I") && lambda.getImplClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getImplMethodSignature().equals("(Lit/unimi/dsi/fastutil/ints/Int2ObjectFunction;II)I")) {
               return (IntComparator)(k1, k2) -> ((Comparable)keyExtractor.get(k1)).compareTo(keyExtractor.get(k2));
            }
            break;
         case "lambda$thenComparing$931d6fed$1":
            if (lambda.getImplMethodKind() == 7 && lambda.getFunctionalInterfaceClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getFunctionalInterfaceMethodName().equals("compare") && lambda.getFunctionalInterfaceMethodSignature().equals("(II)I") && lambda.getImplClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getImplMethodSignature().equals("(Lit/unimi/dsi/fastutil/ints/IntComparator;II)I")) {
               IntComparator var10000 = (IntComparator)lambda.getCapturedArg(0);
               return (IntComparator)(k1, k2) -> {
                  int comp = this.compare(k1, k2);
                  return comp == 0 ? second.compare(k1, k2) : comp;
               };
            }
            break;
         case "lambda$comparing$942f8bdc$1":
            if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getFunctionalInterfaceMethodName().equals("compare") && lambda.getFunctionalInterfaceMethodSignature().equals("(II)I") && lambda.getImplClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getImplMethodSignature().equals("(Ljava/util/Comparator;Lit/unimi/dsi/fastutil/ints/Int2ObjectFunction;II)I")) {
               return (IntComparator)(k1, k2) -> keyComparator.compare(keyExtractor.get(k1), keyExtractor.get(k2));
            }
            break;
         case "lambda$comparingLong$8c6cda96$1":
            if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getFunctionalInterfaceMethodName().equals("compare") && lambda.getFunctionalInterfaceMethodSignature().equals("(II)I") && lambda.getImplClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && lambda.getImplMethodSignature().equals("(Lit/unimi/dsi/fastutil/ints/Int2LongFunction;II)I")) {
               return (IntComparator)(k1, k2) -> Long.compare(keyExtractor.get(k1), keyExtractor.get(k2));
            }
      }

      throw new IllegalArgumentException("Invalid lambda deserialization");
   }
}
