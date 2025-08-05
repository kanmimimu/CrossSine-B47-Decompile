package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

@FunctionalInterface
public interface VersionProvider extends Provider {
   default ProtocolVersion getClientProtocol(UserConnection connection) {
      return null;
   }

   default ProtocolVersion getServerProtocol(UserConnection connection) {
      try {
         return this.getClosestServerProtocol(connection);
      } catch (Exception var3) {
         return null;
      }
   }

   ProtocolVersion getClosestServerProtocol(UserConnection var1) throws Exception;
}
