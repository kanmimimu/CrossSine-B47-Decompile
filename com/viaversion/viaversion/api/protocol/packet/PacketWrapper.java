package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketWrapper {
   int PASSTHROUGH_ID = 1000;

   static PacketWrapper create(@Nullable PacketType packetType, UserConnection connection) {
      return create(packetType, (ByteBuf)null, connection);
   }

   static PacketWrapper create(@Nullable PacketType packetType, @Nullable ByteBuf inputBuffer, UserConnection connection) {
      return Via.getManager().getProtocolManager().createPacketWrapper(packetType, inputBuffer, connection);
   }

   /** @deprecated */
   @Deprecated
   static PacketWrapper create(int packetId, @Nullable ByteBuf inputBuffer, UserConnection connection) {
      return Via.getManager().getProtocolManager().createPacketWrapper(packetId, inputBuffer, connection);
   }

   Object get(Type var1, int var2) throws InformativeException;

   /** @deprecated */
   @Deprecated
   boolean is(Type var1, int var2);

   boolean isReadable(Type var1, int var2);

   void set(Type var1, int var2, @Nullable Object var3) throws InformativeException;

   Object read(Type var1) throws InformativeException;

   void write(Type var1, @Nullable Object var2);

   Object passthrough(Type var1) throws InformativeException;

   Object passthroughAndMap(Type var1, Type var2) throws InformativeException;

   void passthroughAll() throws InformativeException;

   void writeToBuffer(ByteBuf var1) throws InformativeException;

   void clearInputBuffer();

   void clearPacket();

   default void send(Class protocol) throws InformativeException {
      this.send(protocol, true);
   }

   void send(Class var1, boolean var2) throws InformativeException;

   default void scheduleSend(Class protocol) throws InformativeException {
      this.scheduleSend(protocol, true);
   }

   void scheduleSend(Class var1, boolean var2) throws InformativeException;

   ChannelFuture sendFuture(Class var1) throws InformativeException;

   void sendRaw() throws InformativeException;

   ChannelFuture sendFutureRaw() throws InformativeException;

   void scheduleSendRaw() throws InformativeException;

   default PacketWrapper create(PacketType packetType) {
      return this.create(packetType.getId());
   }

   default PacketWrapper create(PacketType packetType, PacketHandler handler) throws InformativeException {
      return this.create(packetType.getId(), handler);
   }

   PacketWrapper create(int var1);

   PacketWrapper create(int var1, PacketHandler var2) throws InformativeException;

   void apply(Direction var1, State var2, List var3) throws InformativeException, CancelException;

   boolean isCancelled();

   default void cancel() {
      this.setCancelled(true);
   }

   void setCancelled(boolean var1);

   UserConnection user();

   void resetReader();

   void sendToServerRaw() throws InformativeException;

   void scheduleSendToServerRaw() throws InformativeException;

   default void sendToServer(Class protocol) throws InformativeException {
      this.sendToServer(protocol, true);
   }

   void sendToServer(Class var1, boolean var2) throws InformativeException;

   default void scheduleSendToServer(Class protocol) throws InformativeException {
      this.scheduleSendToServer(protocol, true);
   }

   void scheduleSendToServer(Class var1, boolean var2) throws InformativeException;

   @Nullable PacketType getPacketType();

   void setPacketType(@Nullable PacketType var1);

   int getId();

   /** @deprecated */
   @Deprecated
   void setId(int var1);
}
