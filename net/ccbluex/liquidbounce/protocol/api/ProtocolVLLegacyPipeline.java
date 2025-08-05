package net.ccbluex.liquidbounce.protocol.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.raphimc.vialoader.netty.VLLegacyPipeline;

public class ProtocolVLLegacyPipeline extends VLLegacyPipeline {
   public ProtocolVLLegacyPipeline(UserConnection user, ProtocolVersion version) {
      super(user, version);
   }

   protected String decompressName() {
      return "decompress";
   }

   protected String compressName() {
      return "compress";
   }

   protected String packetDecoderName() {
      return "decoder";
   }

   protected String packetEncoderName() {
      return "encoder";
   }

   protected String lengthSplitterName() {
      return "splitter";
   }

   protected String lengthPrependerName() {
      return "prepender";
   }
}
