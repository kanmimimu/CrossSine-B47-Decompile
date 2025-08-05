package com.viaversion.viabackwards.protocol.v1_20to1_19_4.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.util.Objects;

public final class BackSignEditStorage implements StorableObject {
   private final BlockPosition position;

   public BackSignEditStorage(BlockPosition position) {
      this.position = position;
   }

   public BlockPosition position() {
      return this.position;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BackSignEditStorage)) {
         return false;
      } else {
         BackSignEditStorage var2 = (BackSignEditStorage)var1;
         return Objects.equals(this.position, var2.position);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.position);
   }

   public String toString() {
      return String.format("%s[position=%s]", this.getClass().getSimpleName(), Objects.toString(this.position));
   }
}
