package com.viaversion.viaversion.api.minecraft;

import java.util.Objects;

public final class ProfileKey {
   private final long expiresAt;
   private final byte[] publicKey;
   private final byte[] keySignature;

   public ProfileKey(long expiresAt, byte[] publicKey, byte[] keySignature) {
      this.expiresAt = expiresAt;
      this.publicKey = publicKey;
      this.keySignature = keySignature;
   }

   public long expiresAt() {
      return this.expiresAt;
   }

   public byte[] publicKey() {
      return this.publicKey;
   }

   public byte[] keySignature() {
      return this.keySignature;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProfileKey)) {
         return false;
      } else {
         ProfileKey var2 = (ProfileKey)var1;
         return this.expiresAt == var2.expiresAt && Objects.equals(this.publicKey, var2.publicKey) && Objects.equals(this.keySignature, var2.keySignature);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Long.hashCode(this.expiresAt)) * 31 + Objects.hashCode(this.publicKey)) * 31 + Objects.hashCode(this.keySignature);
   }

   public String toString() {
      return String.format("%s[expiresAt=%s, publicKey=%s, keySignature=%s]", this.getClass().getSimpleName(), Long.toString(this.expiresAt), Objects.toString(this.publicKey), Objects.toString(this.keySignature));
   }
}
