package com.viaversion.viaversion.api.minecraft.data;

import com.viaversion.viaversion.util.IdHolder;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface StructuredData extends IdHolder {
   static StructuredData of(StructuredDataKey key, Object value, int id) {
      return new FilledStructuredData(key, value, id);
   }

   static StructuredData empty(StructuredDataKey key, int id) {
      return new EmptyStructuredData(key, id);
   }

   @Nullable Object value();

   void setValue(Object var1);

   void setId(int var1);

   StructuredDataKey key();

   default boolean isPresent() {
      return !this.isEmpty();
   }

   boolean isEmpty();

   void write(ByteBuf var1);
}
