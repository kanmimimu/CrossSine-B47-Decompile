package net.raphimc.viaaprilfools;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.velocity.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import net.raphimc.viaaprilfools.api.VAFServerVersionProvider;
import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;

@Plugin(
   id = "viaaprilfools",
   name = "ViaAprilFools",
   version = "${version}",
   authors = {"RK_01", "FlorianMichael/EnZaXD"},
   description = "ViaVersion addon to add support for some notable Minecraft snapshots",
   dependencies = {@Dependency(
   id = "viaversion"
), @Dependency(
   id = "viabackwards"
)},
   url = "https://viaversion.com/aprilfools"
)
public class VelocityPlugin implements ViaAprilFoolsPlatform {
   private Logger logger;
   @Inject
   private org.slf4j.Logger loggerSlf4j;
   @Inject
   @DataDirectory
   private Path configPath;

   @Subscribe(
      order = PostOrder.LATE
   )
   public void onProxyStart(ProxyInitializeEvent e) {
      this.logger = new LoggerWrapper(this.loggerSlf4j);
      ViaManager manager = Via.getManager();
      manager.addEnableListener(() -> this.init(new File(this.getDataFolder(), "config.yml")));
      manager.addPostEnableListener(() -> {
         VersionProvider delegate = (VersionProvider)manager.getProviders().get(VersionProvider.class);
         manager.getProviders().use(VersionProvider.class, new VAFServerVersionProvider(delegate));
      });
   }

   public File getDataFolder() {
      return this.configPath.toFile();
   }

   public Logger getLogger() {
      return this.logger;
   }
}
