package com.viaversion.viarewind.api;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.Protocol1_7_6_10To1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.File;
import java.util.logging.Logger;

public interface ViaRewindPlatform {
   String VERSION = "4.0.3-SNAPSHOT";
   String IMPL_VERSION = "git-ViaRewind-4.0.3-SNAPSHOT:74ebb09";

   default void init(File configFile) {
      com.viaversion.viarewind.ViaRewindConfig config = new com.viaversion.viarewind.ViaRewindConfig(configFile, this.getLogger());
      config.reload();
      Via.getManager().getConfigurationProvider().register(config);
      ViaRewind.init(this, config);
      Via.getManager().getSubPlatforms().add("git-ViaRewind-4.0.3-SNAPSHOT:74ebb09");
      this.getLogger().info("Registering protocols...");
      ProtocolManager protocolManager = Via.getManager().getProtocolManager();
      protocolManager.registerProtocol(new Protocol1_7_6_10To1_7_2_5(), (ProtocolVersion)ProtocolVersion.v1_7_2, ProtocolVersion.v1_7_6);
      protocolManager.registerProtocol(new Protocol1_8To1_7_6_10(), (ProtocolVersion)ProtocolVersion.v1_7_6, ProtocolVersion.v1_8);
      protocolManager.registerProtocol(new Protocol1_9To1_8(), (ProtocolVersion)ProtocolVersion.v1_8, ProtocolVersion.v1_9);
   }

   Logger getLogger();

   File getDataFolder();
}
