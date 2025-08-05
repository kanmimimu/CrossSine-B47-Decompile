package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.legacy.LegacyAPI;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ViaAPIBase implements ViaAPI {
   private final LegacyAPI legacy = new LegacyAPI();

   public ServerProtocolVersion getServerVersion() {
      return Via.getManager().getProtocolManager().getServerProtocolVersion();
   }

   public ProtocolVersion getPlayerProtocolVersion(UUID uuid) {
      UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
      return connection != null ? connection.getProtocolInfo().protocolVersion() : ProtocolVersion.unknown;
   }

   public String getVersion() {
      return Via.getPlatform().getPluginVersion();
   }

   public boolean isInjected(UUID uuid) {
      return Via.getManager().getConnectionManager().isClientConnected(uuid);
   }

   public @Nullable UserConnection getConnection(UUID uuid) {
      return Via.getManager().getConnectionManager().getConnectedClient(uuid);
   }

   public void sendRawPacket(UUID uuid, ByteBuf packet) throws IllegalArgumentException {
      if (!this.isInjected(uuid)) {
         throw new IllegalArgumentException("This player is not controlled by ViaVersion!");
      } else {
         UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(uuid);
         user.scheduleSendRawPacket(packet);
      }
   }

   public SortedSet getSupportedProtocolVersions() {
      SortedSet<ProtocolVersion> outputSet = new TreeSet(Via.getManager().getProtocolManager().getSupportedVersions());
      BlockedProtocolVersions blockedVersions = Via.getPlatform().getConf().blockedProtocolVersions();
      Objects.requireNonNull(blockedVersions);
      outputSet.removeIf(blockedVersions::contains);
      return outputSet;
   }

   public SortedSet getFullSupportedProtocolVersions() {
      return Via.getManager().getProtocolManager().getSupportedVersions();
   }

   public LegacyViaAPI legacyAPI() {
      return this.legacy;
   }
}
