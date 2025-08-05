package com.viaversion.viaversion.api.protocol;

import com.google.common.collect.Range;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolManager {
   ServerProtocolVersion getServerProtocolVersion();

   @Nullable Protocol getProtocol(Class var1);

   @Nullable Protocol getProtocol(ProtocolVersion var1, ProtocolVersion var2);

   Protocol getBaseProtocol();

   List getBaseProtocols(@Nullable ProtocolVersion var1, @Nullable ProtocolVersion var2);

   Collection getProtocols();

   /** @deprecated */
   @Deprecated(
      forRemoval = true
   )
   default boolean isBaseProtocol(Protocol protocol) {
      return protocol.isBaseProtocol();
   }

   void registerProtocol(Protocol var1, ProtocolVersion var2, ProtocolVersion var3);

   void registerProtocol(Protocol var1, List var2, ProtocolVersion var3);

   void registerBaseProtocol(Direction var1, Protocol var2, Range var3);

   @Nullable List getProtocolPath(ProtocolVersion var1, ProtocolVersion var2);

   /** @deprecated */
   @Deprecated
   default @Nullable List getProtocolPath(int clientVersion, int serverVersion) {
      return this.getProtocolPath(ProtocolVersion.getProtocol(clientVersion), ProtocolVersion.getProtocol(serverVersion));
   }

   VersionedPacketTransformer createPacketTransformer(ProtocolVersion var1, @Nullable Class var2, @Nullable Class var3);

   void setMaxPathDeltaIncrease(int var1);

   int getMaxPathDeltaIncrease();

   int getMaxProtocolPathSize();

   void setMaxProtocolPathSize(int var1);

   SortedSet getSupportedVersions();

   boolean isWorkingPipe();

   void completeMappingDataLoading(Class var1);

   boolean checkForMappingCompletion();

   void addMappingLoaderFuture(Class var1, Runnable var2);

   void addMappingLoaderFuture(Class var1, Class var2, Runnable var3);

   @Nullable CompletableFuture getMappingLoaderFuture(Class var1);

   PacketWrapper createPacketWrapper(@Nullable PacketType var1, @Nullable ByteBuf var2, UserConnection var3);

   /** @deprecated */
   @Deprecated
   PacketWrapper createPacketWrapper(int var1, @Nullable ByteBuf var2, UserConnection var3);

   boolean hasLoadedMappings();
}
