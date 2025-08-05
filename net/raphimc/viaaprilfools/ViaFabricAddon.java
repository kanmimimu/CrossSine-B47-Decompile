package net.raphimc.viaaprilfools;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import net.raphimc.viaaprilfools.api.VAFServerVersionProvider;
import net.raphimc.viaaprilfools.fabric.util.LoggerWrapper;
import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;
import org.apache.logging.log4j.LogManager;

public class ViaFabricAddon implements ViaAprilFoolsPlatform, Runnable {
   private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaAprilFools"));
   private File configDir;

   public void run() {
      Path configDirPath = FabricLoader.getInstance().getConfigDir().resolve("ViaAprilFools");
      this.configDir = configDirPath.toFile();
      this.init(new File(this.getDataFolder(), "config.yml"));
      ViaManager manager = Via.getManager();
      manager.addPostEnableListener(() -> {
         VersionProvider delegate = (VersionProvider)manager.getProviders().get(VersionProvider.class);
         manager.getProviders().use(VersionProvider.class, new VAFServerVersionProvider(delegate));
      });
   }

   public File getDataFolder() {
      return this.configDir;
   }

   public Logger getLogger() {
      return this.logger;
   }
}
