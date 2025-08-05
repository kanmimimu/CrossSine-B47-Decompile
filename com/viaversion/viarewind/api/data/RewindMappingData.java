package com.viaversion.viarewind.api.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viarewind.ViaRewind;
import java.util.logging.Logger;

public class RewindMappingData extends BackwardsMappingData {
   public RewindMappingData(String unmappedVersion, String mappedVersion) {
      super(unmappedVersion, mappedVersion);
   }

   protected Logger getLogger() {
      return ViaRewind.getPlatform().getLogger();
   }

   protected CompoundTag readMappingsFile(String name) {
      return RewindMappingDataLoader.INSTANCE.loadNBTFromDir(name);
   }
}
