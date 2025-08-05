package com.viaversion.viaversion.api.minecraft.data;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

final class FilledStructuredData implements StructuredData {
   private final StructuredDataKey key;
   private Object value;
   private int id;

   FilledStructuredData(StructuredDataKey key, Object value, int id) {
      Preconditions.checkNotNull(key);
      this.key = key;
      this.value = value;
      this.id = id;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public void write(ByteBuf buffer) {
      this.key.type().write(buffer, this.value);
   }

   public void setId(int id) {
      this.id = id;
   }

   public StructuredDataKey key() {
      return this.key;
   }

   public Object value() {
      return this.value;
   }

   public boolean isEmpty() {
      return false;
   }

   public int id() {
      return this.id;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FilledStructuredData<?> that = (FilledStructuredData)o;
         if (this.id != that.id) {
            return false;
         } else {
            return !this.key.equals(that.key) ? false : Objects.equals(this.value, that.value);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.key.hashCode();
      result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
      result = 31 * result + this.id;
      return result;
   }

   public String toString() {
      int var5 = this.id;
      Object var4 = this.value;
      StructuredDataKey var3 = this.key;
      return "FilledStructuredData{key=" + var3 + ", value=" + var4 + ", id=" + var5 + "}";
   }
}
