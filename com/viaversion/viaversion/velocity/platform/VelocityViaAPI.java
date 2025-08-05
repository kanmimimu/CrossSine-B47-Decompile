package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.ViaAPIBase;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public class VelocityViaAPI extends ViaAPIBase {
   public ProtocolVersion getPlayerProtocolVersion(Player player) {
      return this.getPlayerProtocolVersion((UUID)player.getUniqueId());
   }

   public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
      this.sendRawPacket((UUID)player.getUniqueId(), packet);
   }
}
