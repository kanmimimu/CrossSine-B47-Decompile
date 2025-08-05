package net.ccbluex.liquidbounce.protocol;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.ccbluex.liquidbounce.protocol.api.ProtocolVLInjector;
import net.ccbluex.liquidbounce.protocol.api.ProtocolVLLegacyPipeline;
import net.ccbluex.liquidbounce.protocol.api.ProtocolVLLoader;
import net.ccbluex.liquidbounce.protocol.api.VFNetworkManager;
import net.ccbluex.liquidbounce.protocol.api.VFPlatform;
import net.raphimc.vialoader.ViaLoader;
import net.raphimc.vialoader.impl.platform.ViaBackwardsPlatformImpl;
import net.raphimc.vialoader.impl.platform.ViaLegacyPlatformImpl;
import net.raphimc.vialoader.impl.platform.ViaRewindPlatformImpl;
import net.raphimc.vialoader.impl.platform.ViaVersionPlatformImpl;
import net.raphimc.vialoader.netty.CompressionReorderEvent;

public class ProtocolBase {
   private ProtocolVersion targetVersion;
   public static final AttributeKey LOCAL_VIA_USER = AttributeKey.valueOf("local_via_user");
   public static final AttributeKey VF_NETWORK_MANAGER = AttributeKey.valueOf("encryption_setup");
   private static ProtocolBase manager;
   public static List versions = new ArrayList();

   public ProtocolBase() {
      this.targetVersion = ProtocolVersion.v1_8;
   }

   public static void init(VFPlatform platform) {
      if (manager == null) {
         manager = new ProtocolBase();
         ViaLoader.init(new ViaVersionPlatformImpl((File)null), new ProtocolVLLoader(platform), new ProtocolVLInjector(), (ViaCommandHandler)null, ViaBackwardsPlatformImpl::new, ViaRewindPlatformImpl::new, ViaLegacyPlatformImpl::new, null);
         versions.addAll(ProtocolVersion.getProtocols());
         versions.removeIf((i) -> i == ProtocolVersion.unknown || i.olderThan(ProtocolVersion.v1_7_2));
      }
   }

   public void inject(Channel channel, VFNetworkManager networkManager) {
      if (channel instanceof SocketChannel) {
         UserConnection user = new UserConnectionImpl(channel, true);
         new ProtocolPipelineImpl(user);
         channel.attr(LOCAL_VIA_USER).set(user);
         channel.attr(VF_NETWORK_MANAGER).set(networkManager);
         channel.pipeline().addLast(new ChannelHandler[]{new ProtocolVLLegacyPipeline(user, this.targetVersion)});
      }

   }

   public ProtocolVersion getTargetVersion() {
      return this.targetVersion;
   }

   public void setTargetVersionSilent(ProtocolVersion targetVersion) {
      this.targetVersion = targetVersion;
   }

   public void setTargetVersion(ProtocolVersion targetVersion) {
      this.targetVersion = targetVersion;
   }

   public void reorderCompression(Channel channel) {
      channel.pipeline().fireUserEventTriggered(CompressionReorderEvent.INSTANCE);
   }

   public static ProtocolBase getManager() {
      return manager;
   }
}
