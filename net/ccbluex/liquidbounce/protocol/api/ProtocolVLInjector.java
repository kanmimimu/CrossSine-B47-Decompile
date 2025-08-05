package net.ccbluex.liquidbounce.protocol.api;

import net.raphimc.vialoader.impl.viaversion.VLInjector;

public class ProtocolVLInjector extends VLInjector {
   public String getDecoderName() {
      return "via-decoder";
   }

   public String getEncoderName() {
      return "via-encoder";
   }
}
