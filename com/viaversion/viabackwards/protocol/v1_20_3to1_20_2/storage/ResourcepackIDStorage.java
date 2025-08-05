package com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;
import java.util.UUID;

public final class ResourcepackIDStorage implements StorableObject {
   private final UUID uuid;

   public ResourcepackIDStorage(UUID uuid) {
      this.uuid = uuid;
   }

   public boolean clearOnServerSwitch() {
      return false;
   }

   public UUID uuid() {
      return this.uuid;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ResourcepackIDStorage)) {
         return false;
      } else {
         ResourcepackIDStorage var2 = (ResourcepackIDStorage)var1;
         return Objects.equals(this.uuid, var2.uuid);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.uuid);
   }

   public String toString() {
      return String.format("%s[uuid=%s]", this.getClass().getSimpleName(), Objects.toString(this.uuid));
   }
}
