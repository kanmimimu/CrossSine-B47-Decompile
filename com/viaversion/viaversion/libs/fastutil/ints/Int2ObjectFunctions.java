package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Int2ObjectFunctions {
   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

   private Int2ObjectFunctions() {
   }

   public static Int2ObjectFunction singleton(int key, Object value) {
      return new Singleton(key, value);
   }

   public static Int2ObjectFunction singleton(Integer key, Object value) {
      return new Singleton(key, value);
   }

   public static Int2ObjectFunction synchronize(Int2ObjectFunction f) {
      return new SynchronizedFunction(f);
   }

   public static Int2ObjectFunction synchronize(Int2ObjectFunction f, Object sync) {
      return new SynchronizedFunction(f, sync);
   }

   public static Int2ObjectFunction unmodifiable(Int2ObjectFunction f) {
      return new UnmodifiableFunction(f);
   }

   public static Int2ObjectFunction primitive(Function f) {
      Objects.requireNonNull(f);
      if (f instanceof Int2ObjectFunction) {
         return (Int2ObjectFunction)f;
      } else if (f instanceof IntFunction) {
         IntFunction var10000 = (IntFunction)f;
         Objects.requireNonNull((IntFunction)f);
         return var10000::apply;
      } else {
         return new PrimitiveFunction(f);
      }
   }

   public static class EmptyFunction extends AbstractInt2ObjectFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyFunction() {
      }

      public Object get(int k) {
         return null;
      }

      public Object getOrDefault(int k, Object defaultValue) {
         return defaultValue;
      }

      public boolean containsKey(int k) {
         return false;
      }

      public Object defaultReturnValue() {
         return null;
      }

      public void defaultReturnValue(Object defRetValue) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return 0;
      }

      public void clear() {
      }

      public Object clone() {
         return Int2ObjectFunctions.EMPTY_FUNCTION;
      }

      public int hashCode() {
         return 0;
      }

      public boolean equals(Object o) {
         if (!(o instanceof com.viaversion.viaversion.libs.fastutil.Function)) {
            return false;
         } else {
            return ((com.viaversion.viaversion.libs.fastutil.Function)o).size() == 0;
         }
      }

      public String toString() {
         return "{}";
      }

      private Object readResolve() {
         return Int2ObjectFunctions.EMPTY_FUNCTION;
      }
   }

   public static class Singleton extends AbstractInt2ObjectFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final int key;
      protected final Object value;

      protected Singleton(int key, Object value) {
         this.key = key;
         this.value = value;
      }

      public boolean containsKey(int k) {
         return this.key == k;
      }

      public Object get(int k) {
         return this.key == k ? this.value : this.defRetValue;
      }

      public Object getOrDefault(int k, Object defaultValue) {
         return this.key == k ? this.value : defaultValue;
      }

      public int size() {
         return 1;
      }

      public Object clone() {
         return this;
      }
   }

   public static class PrimitiveFunction implements Int2ObjectFunction {
      protected final Function function;

      protected PrimitiveFunction(Function function) {
         this.function = function;
      }

      public boolean containsKey(int key) {
         return this.function.apply(key) != null;
      }

      /** @deprecated */
      @Deprecated
      public boolean containsKey(Object key) {
         if (key == null) {
            return false;
         } else {
            return this.function.apply((Integer)key) != null;
         }
      }

      public Object get(int key) {
         V v = (V)this.function.apply(key);
         return v == null ? null : v;
      }

      public Object getOrDefault(int key, Object defaultValue) {
         V v = (V)this.function.apply(key);
         return v == null ? defaultValue : v;
      }

      /** @deprecated */
      @Deprecated
      public Object get(Object key) {
         return key == null ? null : this.function.apply((Integer)key);
      }

      /** @deprecated */
      @Deprecated
      public Object getOrDefault(Object key, Object defaultValue) {
         if (key == null) {
            return defaultValue;
         } else {
            V v;
            return (v = (V)this.function.apply((Integer)key)) == null ? defaultValue : v;
         }
      }

      /** @deprecated */
      @Deprecated
      public Object put(Integer key, Object value) {
         throw new UnsupportedOperationException();
      }
   }
}
