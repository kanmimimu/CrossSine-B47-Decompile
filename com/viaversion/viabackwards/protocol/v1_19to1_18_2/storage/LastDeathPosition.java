package com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import java.util.Objects;

public final class LastDeathPosition implements StorableObject {
   private final GlobalBlockPosition position;

   public LastDeathPosition(GlobalBlockPosition position) {
      this.position = position;
   }

   public GlobalBlockPosition position() {
      return this.position;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof LastDeathPosition)) {
         return false;
      } else {
         LastDeathPosition var2 = (LastDeathPosition)var1;
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
