package com.viaversion.viaversion.protocols.v1_18_2to1_19.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;

public final class NonceStorage1_19 implements StorableObject {
   private final byte[] nonce;

   public NonceStorage1_19(byte[] nonce) {
      this.nonce = nonce;
   }

   public byte[] nonce() {
      return this.nonce;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof NonceStorage1_19)) {
         return false;
      } else {
         NonceStorage1_19 var2 = (NonceStorage1_19)var1;
         return Objects.equals(this.nonce, var2.nonce);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.nonce);
   }

   public String toString() {
      return String.format("%s[nonce=%s]", this.getClass().getSimpleName(), Objects.toString(this.nonce));
   }
}
