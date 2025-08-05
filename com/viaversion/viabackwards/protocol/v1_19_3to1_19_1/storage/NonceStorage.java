package com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class NonceStorage implements StorableObject {
   private final byte @Nullable [] nonce;

   public NonceStorage(byte @Nullable [] nonce) {
      this.nonce = nonce;
   }

   public byte @Nullable [] nonce() {
      return this.nonce;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof NonceStorage)) {
         return false;
      } else {
         NonceStorage var2 = (NonceStorage)var1;
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
