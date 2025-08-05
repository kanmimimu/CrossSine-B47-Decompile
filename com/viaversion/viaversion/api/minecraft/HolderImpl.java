package com.viaversion.viaversion.api.minecraft;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;

final class HolderImpl implements Holder {
   private final Object value;
   private final int id;

   HolderImpl(int id) {
      Preconditions.checkArgument(id >= 0, "id cannot be negative");
      this.value = null;
      this.id = id;
   }

   HolderImpl(Object value) {
      this.value = value;
      this.id = -1;
   }

   public boolean isDirect() {
      return this.id == -1;
   }

   public boolean hasId() {
      return this.id != -1;
   }

   public Object value() {
      Preconditions.checkArgument(this.isDirect(), "Holder is not direct");
      return this.value;
   }

   public int id() {
      return this.id;
   }

   public Holder updateId(Int2IntFunction rewriteFunction) {
      if (this.isDirect()) {
         return this;
      } else {
         int rewrittenId = rewriteFunction.applyAsInt(this.id);
         if (rewrittenId == this.id) {
            return this;
         } else if (rewrittenId == -1) {
            throw new IllegalArgumentException("Received invalid id in updateId");
         } else {
            return Holder.of(rewrittenId);
         }
      }
   }

   public String toString() {
      int var4 = this.id;
      Object var3 = this.value;
      return "HolderImpl{value=" + var3 + ", id=" + var4 + "}";
   }
}
