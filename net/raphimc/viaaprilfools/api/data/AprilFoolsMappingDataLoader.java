package net.raphimc.viaaprilfools.api.data;

import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import java.io.File;
import java.util.logging.Logger;
import net.raphimc.viaaprilfools.ViaAprilFools;

public class AprilFoolsMappingDataLoader extends BackwardsMappingDataLoader {
   public static final AprilFoolsMappingDataLoader INSTANCE = new AprilFoolsMappingDataLoader();

   public AprilFoolsMappingDataLoader() {
      super(AprilFoolsMappingDataLoader.class, "assets/viaaprilfools/data/");
   }

   public File getDataFolder() {
      return ViaAprilFools.getPlatform().getDataFolder();
   }

   public Logger getLogger() {
      return ViaAprilFools.getPlatform().getLogger();
   }
}
