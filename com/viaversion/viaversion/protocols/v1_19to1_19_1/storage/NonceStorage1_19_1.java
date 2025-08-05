package com.viaversion.viaversion.protocols.v1_19to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class NonceStorage1_19_1 implements StorableObject {
   private final byte @Nullable [] nonce;

   public NonceStorage1_19_1(byte @Nullable [] nonce) {
      this.nonce = nonce;
   }

   public byte @Nullable [] nonce() {
      return this.nonce;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof NonceStorage1_19_1)) {
         return false;
      } else {
         NonceStorage1_19_1 var2 = (NonceStorage1_19_1)var1;
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
