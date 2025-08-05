package com.viaversion.viaversion.protocols.v1_18_2to1_19.storage;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DimensionRegistryStorage implements StorableObject {
   private Map dimensions;

   public @Nullable String dimensionKey(CompoundTag dimensionData) {
      return (String)this.dimensions.get(dimensionData);
   }

   public void setDimensions(Map dimensions) {
      this.dimensions = dimensions;
   }

   public Map dimensions() {
      return this.dimensions;
   }

   public boolean clearOnServerSwitch() {
      return false;
   }
}
