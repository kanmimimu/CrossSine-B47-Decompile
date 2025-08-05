package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class Object2IntFunctions {
   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

   private Object2IntFunctions() {
   }

   public static Object2IntFunction singleton(Object key, int value) {
      return new Singleton(key, value);
   }

   public static Object2IntFunction singleton(Object key, Integer value) {
      return new Singleton(key, value);
   }

   public static Object2IntFunction synchronize(Object2IntFunction f) {
      return new SynchronizedFunction(f);
   }

   public static Object2IntFunction synchronize(Object2IntFunction f, Object sync) {
      return new SynchronizedFunction(f, sync);
   }

   public static Object2IntFunction unmodifiable(Object2IntFunction f) {
      return new UnmodifiableFunction(f);
   }

   public static Object2IntFunction primitive(Function f) {
      Objects.requireNonNull(f);
      if (f instanceof Object2IntFunction) {
         return (Object2IntFunction)f;
      } else {
         return (Object2IntFunction)(f instanceof ToIntFunction ? (key) -> ((ToIntFunction)f).applyAsInt(key) : new PrimitiveFunction(f));
      }
   }

   public static class EmptyFunction extends AbstractObject2IntFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyFunction() {
      }

      public int getInt(Object k) {
         return 0;
      }

      public int getOrDefault(Object k, int defaultValue) {
         return defaultValue;
      }

      public boolean containsKey(Object k) {
         return false;
      }

      public int defaultReturnValue() {
         return 0;
      }

      public void defaultReturnValue(int defRetValue) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return 0;
      }

      public void clear() {
      }

      public Object clone() {
         return Object2IntFunctions.EMPTY_FUNCTION;
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
         return Object2IntFunctions.EMPTY_FUNCTION;
      }
   }

   public static class Singleton extends AbstractObject2IntFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Object key;
      protected final int value;

      protected Singleton(Object key, int value) {
         this.key = key;
         this.value = value;
      }

      public boolean containsKey(Object k) {
         return Objects.equals(this.key, k);
      }

      public int getInt(Object k) {
         return Objects.equals(this.key, k) ? this.value : this.defRetValue;
      }

      public int getOrDefault(Object k, int defaultValue) {
         return Objects.equals(this.key, k) ? this.value : defaultValue;
      }

      public int size() {
         return 1;
      }

      public Object clone() {
         return this;
      }
   }

   public static class PrimitiveFunction implements Object2IntFunction {
      protected final Function function;

      protected PrimitiveFunction(Function function) {
         this.function = function;
      }

      public boolean containsKey(Object key) {
         return this.function.apply(key) != null;
      }

      public int getInt(Object key) {
         Integer v = (Integer)this.function.apply(key);
         return v == null ? this.defaultReturnValue() : v;
      }

      public int getOrDefault(Object key, int defaultValue) {
         Integer v = (Integer)this.function.apply(key);
         return v == null ? defaultValue : v;
      }

      /** @deprecated */
      @Deprecated
      public Integer get(Object key) {
         return (Integer)this.function.apply(key);
      }

      /** @deprecated */
      @Deprecated
      public Integer getOrDefault(Object key, Integer defaultValue) {
         Integer v;
         return (v = (Integer)this.function.apply(key)) == null ? defaultValue : v;
      }

      /** @deprecated */
      @Deprecated
      public Integer put(Object key, Integer value) {
         throw new UnsupportedOperationException();
      }
   }
}
