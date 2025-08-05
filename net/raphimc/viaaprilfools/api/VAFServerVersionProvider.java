package net.raphimc.viaaprilfools.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;

public class VAFServerVersionProvider implements VersionProvider {
   private final VersionProvider delegate;

   public VAFServerVersionProvider(VersionProvider delegate) {
      this.delegate = delegate;
   }

   public ProtocolVersion getClientProtocol(UserConnection connection) {
      ProtocolVersion version = connection.getProtocolInfo().protocolVersion();
      return version.getVersionType() == VersionType.SPECIAL ? ProtocolVersion.getProtocol(VersionType.SPECIAL, version.getOriginalVersion()) : this.delegate.getClientProtocol(connection);
   }

   public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
      return this.delegate.getClosestServerProtocol(connection);
   }
}
