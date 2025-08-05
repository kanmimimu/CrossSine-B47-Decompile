package com.viaversion.viaversion.api.platform;

public interface ViaServerProxyPlatform extends ViaPlatform {
   ProtocolDetectorService protocolDetectorService();

   default boolean couldBeReloading() {
      return false;
   }
}
