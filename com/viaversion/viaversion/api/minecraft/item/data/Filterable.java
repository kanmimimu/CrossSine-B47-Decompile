package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class Filterable {
   final Object raw;
   final Object filtered;

   protected Filterable(Object raw, @Nullable Object filtered) {
      this.raw = raw;
      this.filtered = filtered;
   }

   public Object raw() {
      return this.raw;
   }

   public boolean isFiltered() {
      return this.filtered != null;
   }

   public @Nullable Object filtered() {
      return this.filtered;
   }

   public Object get() {
      return this.filtered != null ? this.filtered : this.raw;
   }

   public abstract static class FilterableType extends Type {
      final Type elementType;
      final Type optionalElementType;

      protected FilterableType(Type elementType, Type optionalElementType, Class outputClass) {
         super(outputClass);
         this.elementType = elementType;
         this.optionalElementType = optionalElementType;
      }

      public Filterable read(ByteBuf buffer) {
         T raw = (T)this.elementType.read(buffer);
         T filtered = (T)this.optionalElementType.read(buffer);
         return this.create(raw, filtered);
      }

      public void write(ByteBuf buffer, Filterable value) {
         this.elementType.write(buffer, value.raw());
         this.optionalElementType.write(buffer, value.filtered());
      }

      protected abstract Filterable create(Object var1, Object var2);
   }
}
