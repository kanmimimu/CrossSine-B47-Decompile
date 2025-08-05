package com.viaversion.viarewind;

import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viarewind.fabric.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

public class ViaFabricAddon implements ViaRewindPlatform, Runnable {
   private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaRewind"));
   private File configDir;

   public void run() {
      Path configDirPath = FabricLoader.getInstance().getConfigDir().resolve("ViaRewind");
      this.configDir = configDirPath.toFile();
      this.init(new File(this.getDataFolder(), "config.yml"));
   }

   public File getDataFolder() {
      return this.configDir;
   }

   public Logger getLogger() {
      return this.logger;
   }
}
