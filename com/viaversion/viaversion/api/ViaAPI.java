package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ViaAPI {
   default int majorVersion() {
      return 5;
   }

   default int apiVersion() {
      return 26;
   }

   ServerProtocolVersion getServerVersion();

   default int getPlayerVersion(Object player) {
      return this.getPlayerProtocolVersion(player).getVersion();
   }

   ProtocolVersion getPlayerProtocolVersion(Object var1);

   default int getPlayerVersion(UUID uuid) {
      return this.getPlayerProtocolVersion(uuid).getVersion();
   }

   ProtocolVersion getPlayerProtocolVersion(UUID var1);

   boolean isInjected(UUID var1);

   @Nullable UserConnection getConnection(UUID var1);

   String getVersion();

   void sendRawPacket(Object var1, ByteBuf var2);

   void sendRawPacket(UUID var1, ByteBuf var2);

   /** @deprecated */
   @Deprecated
   default SortedSet getSupportedVersions() {
      return (SortedSet)this.getSupportedProtocolVersions().stream().map(ProtocolVersion::getVersion).collect(Collectors.toCollection(TreeSet::new));
   }

   /** @deprecated */
   @Deprecated
   default SortedSet getFullSupportedVersions() {
      return (SortedSet)this.getFullSupportedProtocolVersions().stream().map(ProtocolVersion::getVersion).collect(Collectors.toCollection(TreeSet::new));
   }

   SortedSet getSupportedProtocolVersions();

   SortedSet getFullSupportedProtocolVersions();

   LegacyViaAPI legacyAPI();
}
