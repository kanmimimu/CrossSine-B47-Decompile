package com.viaversion.viaversion.libs.fastutil;

@FunctionalInterface
public interface Function extends java.util.function.Function {
   default Object apply(Object key) {
      return this.get(key);
   }

   default Object put(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   Object get(Object var1);

   default Object getOrDefault(Object key, Object defaultValue) {
      V value = (V)this.get(key);
      return value == null && !this.containsKey(key) ? defaultValue : value;
   }

   default boolean containsKey(Object key) {
      return true;
   }

   default Object remove(Object key) {
      throw new UnsupportedOperationException();
   }

   default int size() {
      return -1;
   }

   default void clear() {
      throw new UnsupportedOperationException();
   }
}
