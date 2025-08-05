package com.viaversion.viaversion.api.protocol;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.rewriter.ComponentRewriter;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.rewriter.TagRewriter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.ProtocolLogger;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Protocol {
   default void registerClientbound(State state, ClientboundPacketType packetType, @Nullable PacketHandler handler) {
      Preconditions.checkArgument(packetType.state() == state);
      this.registerClientbound(state, packetType.getId(), packetType.getId(), handler, false);
   }

   default void registerServerbound(State state, ServerboundPacketType packetType, @Nullable PacketHandler handler) {
      Preconditions.checkArgument(packetType.state() == state);
      this.registerServerbound(state, packetType.getId(), packetType.getId(), handler, false);
   }

   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, @Nullable PacketHandler handler) {
      this.registerServerbound(state, unmappedPacketId, mappedPacketId, handler, false);
   }

   void registerServerbound(State var1, int var2, int var3, @Nullable PacketHandler var4, boolean var5);

   void cancelServerbound(State var1, int var2);

   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, @Nullable PacketHandler handler) {
      this.registerClientbound(state, unmappedPacketId, mappedPacketId, handler, false);
   }

   void cancelClientbound(State var1, int var2);

   void registerClientbound(State var1, int var2, int var3, @Nullable PacketHandler var4, boolean var5);

   void registerClientbound(ClientboundPacketType var1, @Nullable PacketHandler var2);

   default void registerClientbound(ClientboundPacketType packetType, @Nullable ClientboundPacketType mappedPacketType) {
      this.registerClientbound((ClientboundPacketType)packetType, mappedPacketType, (PacketHandler)null);
   }

   default void registerClientbound(ClientboundPacketType packetType, @Nullable ClientboundPacketType mappedPacketType, @Nullable PacketHandler handler) {
      this.registerClientbound(packetType, mappedPacketType, handler, false);
   }

   void registerClientbound(ClientboundPacketType var1, @Nullable ClientboundPacketType var2, @Nullable PacketHandler var3, boolean var4);

   void cancelClientbound(ClientboundPacketType var1);

   default void registerServerbound(ServerboundPacketType packetType, @Nullable ServerboundPacketType mappedPacketType) {
      this.registerServerbound((ServerboundPacketType)packetType, mappedPacketType, (PacketHandler)null);
   }

   void registerServerbound(ServerboundPacketType var1, @Nullable PacketHandler var2);

   default void registerServerbound(ServerboundPacketType packetType, @Nullable ServerboundPacketType mappedPacketType, @Nullable PacketHandler handler) {
      this.registerServerbound(packetType, mappedPacketType, handler, false);
   }

   void registerServerbound(ServerboundPacketType var1, @Nullable ServerboundPacketType var2, @Nullable PacketHandler var3, boolean var4);

   void cancelServerbound(ServerboundPacketType var1);

   default boolean hasRegisteredClientbound(ClientboundPacketType packetType) {
      return this.hasRegisteredClientbound(packetType.state(), packetType.getId());
   }

   default boolean hasRegisteredServerbound(ServerboundPacketType packetType) {
      return this.hasRegisteredServerbound(packetType.state(), packetType.getId());
   }

   boolean hasRegisteredClientbound(State var1, int var2);

   boolean hasRegisteredServerbound(State var1, int var2);

   void appendClientbound(ClientboundPacketType var1, PacketHandler var2);

   void appendServerbound(ServerboundPacketType var1, PacketHandler var2);

   void transform(Direction var1, State var2, PacketWrapper var3) throws InformativeException, CancelException;

   PacketTypesProvider getPacketTypesProvider();

   /** @deprecated */
   @Deprecated
   @Nullable Object get(Class var1);

   /** @deprecated */
   @Deprecated
   void put(Object var1);

   void initialize();

   default boolean hasMappingDataToLoad() {
      return this.getMappingData() != null;
   }

   void loadMappingData();

   default void register(ViaProviders providers) {
   }

   default void init(UserConnection connection) {
   }

   default @Nullable MappingData getMappingData() {
      return null;
   }

   ProtocolLogger getLogger();

   default @Nullable EntityRewriter getEntityRewriter() {
      return null;
   }

   default @Nullable ItemRewriter getItemRewriter() {
      return null;
   }

   default @Nullable TagRewriter getTagRewriter() {
      return null;
   }

   default @Nullable ComponentRewriter getComponentRewriter() {
      return null;
   }

   default boolean isBaseProtocol() {
      return false;
   }
}
