package net.raphimc.vialoader.impl.viaversion;

import com.viaversion.viaversion.ViaAPIBase;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import io.netty.buffer.ByteBuf;

public class VLApiBase extends ViaAPIBase {
   public ProtocolVersion getPlayerProtocolVersion(UserConnection player) {
      return player.getProtocolInfo().protocolVersion();
   }

   public void sendRawPacket(UserConnection player, ByteBuf packet) {
      player.scheduleSendRawPacket(packet);
   }
}
