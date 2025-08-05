package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.io.Serializable;
import java.util.Objects;

public final class Object2ObjectFunctions {
   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

   private Object2ObjectFunctions() {
   }

   public static Object2ObjectFunction singleton(Object key, Object value) {
      return new Singleton(key, value);
   }

   public static Object2ObjectFunction synchronize(Object2ObjectFunction f) {
      return new SynchronizedFunction(f);
   }

   public static Object2ObjectFunction synchronize(Object2ObjectFunction f, Object sync) {
      return new SynchronizedFunction(f, sync);
   }

   public static Object2ObjectFunction unmodifiable(Object2ObjectFunction f) {
      return new UnmodifiableFunction(f);
   }

   public static class EmptyFunction extends AbstractObject2ObjectFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyFunction() {
      }

      public Object get(Object k) {
         return null;
      }

      public Object getOrDefault(Object k, Object defaultValue) {
         return defaultValue;
      }

      public boolean containsKey(Object k) {
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
         return Object2ObjectFunctions.EMPTY_FUNCTION;
      }

      public int hashCode() {
         return 0;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Function)) {
            return false;
         } else {
            return ((Function)o).size() == 0;
         }
      }

      public String toString() {
         return "{}";
      }

      private Object readResolve() {
         return Object2ObjectFunctions.EMPTY_FUNCTION;
      }
   }

   public static class Singleton extends AbstractObject2ObjectFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Object key;
      protected final Object value;

      protected Singleton(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public boolean containsKey(Object k) {
         return Objects.equals(this.key, k);
      }

      public Object get(Object k) {
         return Objects.equals(this.key, k) ? this.value : this.defRetValue;
      }

      public Object getOrDefault(Object k, Object defaultValue) {
         return Objects.equals(this.key, k) ? this.value : defaultValue;
      }

      public int size() {
         return 1;
      }

      public Object clone() {
         return this;
      }
   }
}
