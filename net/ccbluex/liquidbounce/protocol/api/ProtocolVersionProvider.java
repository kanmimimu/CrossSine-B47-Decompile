package net.ccbluex.liquidbounce.protocol.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocol.version.BaseVersionProvider;
import io.netty.channel.Channel;
import java.util.Objects;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public class ProtocolVersionProvider extends BaseVersionProvider {
   public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
      return connection.isClientSide() && !MinecraftInstance.mc.func_71387_A() ? ((VFNetworkManager)((Channel)Objects.requireNonNull(connection.getChannel())).attr(ProtocolBase.VF_NETWORK_MANAGER).get()).viaForge$getTrackedVersion() : super.getClosestServerProtocol(connection);
   }
}
