package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2CharFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2LongFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface Object2IntFunction extends com.viaversion.viaversion.libs.fastutil.Function, ToIntFunction {
   default int applyAsInt(Object operand) {
      return this.getInt(operand);
   }

   default int put(Object key, int value) {
      throw new UnsupportedOperationException();
   }

   int getInt(Object var1);

   default int getOrDefault(Object key, int defaultValue) {
      int v;
      return (v = this.getInt(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   default int removeInt(Object key) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default Integer put(Object key, Integer value) {
      boolean containsKey = this.containsKey(key);
      int v = this.put(key, value);
      return containsKey ? v : null;
   }

   /** @deprecated */
   @Deprecated
   default Integer get(Object key) {
      int v;
      return (v = this.getInt(key)) == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
   }

   /** @deprecated */
   @Deprecated
   default Integer getOrDefault(Object key, Integer defaultValue) {
      int v = this.getInt(key);
      return v == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   /** @deprecated */
   @Deprecated
   default Integer remove(Object key) {
      return this.containsKey(key) ? this.removeInt(key) : null;
   }

   default void defaultReturnValue(int rv) {
      throw new UnsupportedOperationException();
   }

   default int defaultReturnValue() {
      return 0;
   }

   /** @deprecated */
   @Deprecated
   default Function andThen(Function after) {
      return com.viaversion.viaversion.libs.fastutil.Function.super.andThen(after);
   }

   default Object2ByteFunction andThenByte(Int2ByteFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Byte2IntFunction composeByte(Byte2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2ShortFunction andThenShort(Int2ShortFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Short2IntFunction composeShort(Short2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2IntFunction andThenInt(Int2IntFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Int2IntFunction composeInt(Int2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2LongFunction andThenLong(Int2LongFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Long2IntFunction composeLong(Long2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2CharFunction andThenChar(Int2CharFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Char2IntFunction composeChar(Char2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2FloatFunction andThenFloat(Int2FloatFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Float2IntFunction composeFloat(Float2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2DoubleFunction andThenDouble(Int2DoubleFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Double2IntFunction composeDouble(Double2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2ObjectFunction andThenObject(Int2ObjectFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Object2IntFunction composeObject(Object2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }

   default Object2ReferenceFunction andThenReference(Int2ReferenceFunction after) {
      return (k) -> after.get(this.getInt(k));
   }

   default Reference2IntFunction composeReference(Reference2ObjectFunction before) {
      return (k) -> this.getInt(before.get(k));
   }
}
