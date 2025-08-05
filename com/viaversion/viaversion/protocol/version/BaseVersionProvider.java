package com.viaversion.viaversion.protocol.version;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;

public class BaseVersionProvider implements VersionProvider {
   public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
      return Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
   }
}
