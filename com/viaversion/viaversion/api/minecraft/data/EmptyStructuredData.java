package com.viaversion.viaversion.api.minecraft.data;

import io.netty.buffer.ByteBuf;

final class EmptyStructuredData implements StructuredData {
   private final StructuredDataKey key;
   private int id;

   EmptyStructuredData(StructuredDataKey key, int id) {
      this.key = key;
      this.id = id;
   }

   public void setValue(Object value) {
      throw new UnsupportedOperationException();
   }

   public void write(ByteBuf buffer) {
   }

   public void setId(int id) {
      this.id = id;
   }

   public StructuredDataKey key() {
      return this.key;
   }

   public Object value() {
      return null;
   }

   public boolean isEmpty() {
      return true;
   }

   public int id() {
      return this.id;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EmptyStructuredData<?> that = (EmptyStructuredData)o;
         return this.id != that.id ? false : this.key.equals(that.key);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.key.hashCode();
      result = 31 * result + this.id;
      return result;
   }

   public String toString() {
      int var4 = this.id;
      StructuredDataKey var3 = this.key;
      return "EmptyStructuredData{key=" + var3 + ", id=" + var4 + "}";
   }
}
