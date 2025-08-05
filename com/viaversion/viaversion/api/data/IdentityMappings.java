package com.viaversion.viaversion.api.data;

public final class IdentityMappings implements Mappings {
   private final int size;
   private final int mappedSize;

   public IdentityMappings(int size, int mappedSize) {
      this.size = size;
      this.mappedSize = mappedSize;
   }

   public int getNewId(int id) {
      return id >= 0 && id < this.size ? id : -1;
   }

   public void setNewId(int id, int mappedId) {
      throw new UnsupportedOperationException();
   }

   public Mappings inverse() {
      return new IdentityMappings(this.mappedSize, this.size);
   }

   public int size() {
      return this.size;
   }

   public int mappedSize() {
      return this.mappedSize;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof IdentityMappings)) {
         return false;
      } else {
         IdentityMappings var2 = (IdentityMappings)var1;
         return this.size == var2.size && this.mappedSize == var2.mappedSize;
      }
   }

   public int hashCode() {
      return (0 * 31 + Integer.hashCode(this.size)) * 31 + Integer.hashCode(this.mappedSize);
   }

   public String toString() {
      return String.format("%s[size=%s, mappedSize=%s]", this.getClass().getSimpleName(), Integer.toString(this.size), Integer.toString(this.mappedSize));
   }
}
