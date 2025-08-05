package com.viaversion.viarewind.api.data;

import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viarewind.ViaRewind;
import java.io.File;
import java.util.logging.Logger;

public class RewindMappingDataLoader extends BackwardsMappingDataLoader {
   public static final RewindMappingDataLoader INSTANCE = new RewindMappingDataLoader();

   public RewindMappingDataLoader() {
      super(RewindMappingDataLoader.class, "assets/viarewind/data/");
   }

   public Logger getLogger() {
      return ViaRewind.getPlatform().getLogger();
   }

   public File getDataFolder() {
      return ViaRewind.getPlatform().getDataFolder();
   }
}
