package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PacketWrapperImpl implements PacketWrapper {
   final Deque readableObjects = new ArrayDeque();
   final List packetValues = new ArrayList();
   final ByteBuf inputBuffer;
   final UserConnection userConnection;
   boolean send = true;
   PacketType packetType;
   int id;

   public PacketWrapperImpl(int packetId, @Nullable ByteBuf inputBuffer, UserConnection userConnection) {
      this.id = packetId;
      this.inputBuffer = inputBuffer;
      this.userConnection = userConnection;
   }

   public PacketWrapperImpl(@Nullable PacketType packetType, @Nullable ByteBuf inputBuffer, UserConnection userConnection) {
      this.packetType = packetType;
      this.id = packetType != null ? packetType.getId() : -1;
      this.inputBuffer = inputBuffer;
      this.userConnection = userConnection;
   }

   public Object get(Type type, int index) throws InformativeException {
      int currentIndex = 0;

      for(PacketValue packetValue : this.packetValues) {
         if (packetValue.type() == type) {
            if (currentIndex == index) {
               return packetValue.value();
            }

            ++currentIndex;
         }
      }

      String var7 = type.getTypeName();
      throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + var7 + " at " + index), type, index);
   }

   public boolean is(Type type, int index) {
      int currentIndex = 0;

      for(PacketValue packetValue : this.packetValues) {
         if (packetValue.type() == type) {
            if (currentIndex == index) {
               return true;
            }

            ++currentIndex;
         }
      }

      return false;
   }

   public boolean isReadable(Type type, int index) {
      int currentIndex = 0;

      for(PacketValue packetValue : this.readableObjects) {
         if (packetValue.type().getBaseClass() == type.getBaseClass()) {
            if (currentIndex == index) {
               return true;
            }

            ++currentIndex;
         }
      }

      return false;
   }

   public void set(Type type, int index, @Nullable Object value) throws InformativeException {
      int currentIndex = 0;

      for(PacketValue packetValue : this.packetValues) {
         if (packetValue.type() == type) {
            if (currentIndex == index) {
               packetValue.setValue(value);
               return;
            }

            ++currentIndex;
         }
      }

      String var8 = type.getTypeName();
      throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + var8 + " at " + index), type, index);
   }

   public Object read(Type type) {
      return this.readableObjects.isEmpty() ? this.readFromBuffer(type) : this.pollReadableObject(type).value;
   }

   Object readFromBuffer(Type type) {
      Preconditions.checkNotNull(this.inputBuffer, "This packet does not have an input buffer.");

      try {
         return type.read(this.inputBuffer);
      } catch (Exception e) {
         throw this.createInformativeException(e, type, this.packetValues.size() + 1);
      }
   }

   PacketValue pollReadableObject(Type type) {
      PacketValue<?> readValue = (PacketValue)this.readableObjects.poll();
      Type<?> readType = readValue.type();
      if (readType != type && (type.getBaseClass() != readType.getBaseClass() || type.getOutputClass() != readType.getOutputClass())) {
         String var10003 = type.getTypeName();
         String var6 = readValue.type().getTypeName();
         String var5 = var10003;
         throw this.createInformativeException(new IOException("Unable to read type " + var5 + ", found " + var6), type, this.readableObjects.size());
      } else {
         return readValue;
      }
   }

   public void write(Type type, Object value) {
      this.packetValues.add(new PacketValue(type, value));
   }

   @Nullable Object attemptTransform(Type expectedType, @Nullable Object value) {
      if (value != null && !expectedType.getOutputClass().isAssignableFrom(value.getClass())) {
         if (expectedType instanceof TypeConverter) {
            return ((TypeConverter)expectedType).from(value);
         }

         Logger var10000 = Via.getPlatform().getLogger();
         String var10001 = value.getClass().getName();
         Class var6 = expectedType.getOutputClass();
         String var5 = var10001;
         var10000.warning("Possible type mismatch: " + var5 + " -> " + var6);
      }

      return value;
   }

   public Object passthrough(Type type) throws InformativeException {
      if (this.readableObjects.isEmpty()) {
         T value = (T)this.readFromBuffer(type);
         this.packetValues.add(new PacketValue(type, value));
         return value;
      } else {
         PacketValue<T> value = this.pollReadableObject(type);
         this.packetValues.add(value);
         return value.value;
      }
   }

   public Object passthroughAndMap(Type type, Type mappedType) throws InformativeException {
      if (type == mappedType) {
         return this.passthrough(mappedType);
      } else {
         Object value = this.read(type);
         T mappedValue = (T)this.attemptTransform(mappedType, value);
         this.write(mappedType, mappedValue);
         return mappedValue;
      }
   }

   public void passthroughAll() throws InformativeException {
      this.packetValues.addAll(this.readableObjects);
      this.readableObjects.clear();
      if (this.inputBuffer.isReadable()) {
         this.passthrough(Types.REMAINING_BYTES);
      }

   }

   public void writeToBuffer(ByteBuf buffer) throws InformativeException {
      if (this.id != -1) {
         Types.VAR_INT.writePrimitive(buffer, this.id);
      }

      if (!this.readableObjects.isEmpty()) {
         this.packetValues.addAll(this.readableObjects);
         this.readableObjects.clear();
      }

      for(int i = 0; i < this.packetValues.size(); ++i) {
         PacketValue<?> packetValue = (PacketValue)this.packetValues.get(i);

         try {
            packetValue.write(buffer);
         } catch (Exception e) {
            throw this.createInformativeException(e, packetValue.type(), i);
         }
      }

      this.writeRemaining(buffer);
   }

   InformativeException createInformativeException(Exception cause, Type type, int index) {
      return (new InformativeException(cause)).set("Index", index).set("Type", type.getTypeName()).set("Packet ID", this.id).set("Packet Type", this.packetType).set("Data", this.packetValues);
   }

   public void clearInputBuffer() {
      if (this.inputBuffer != null) {
         this.inputBuffer.clear();
      }

      this.readableObjects.clear();
   }

   public void clearPacket() {
      this.clearInputBuffer();
      this.packetValues.clear();
   }

   void writeRemaining(ByteBuf output) {
      if (this.inputBuffer != null) {
         output.writeBytes(this.inputBuffer);
      }

   }

   public void send(Class protocol, boolean skipCurrentPipeline) throws InformativeException {
      this.send0(protocol, skipCurrentPipeline, true);
   }

   public void scheduleSend(Class protocol, boolean skipCurrentPipeline) throws InformativeException {
      this.send0(protocol, skipCurrentPipeline, false);
   }

   void send0(Class protocol, boolean skipCurrentPipeline, boolean currentThread) throws InformativeException {
      if (!this.isCancelled()) {
         UserConnection connection = this.user();
         if (currentThread) {
            this.sendNow(protocol, skipCurrentPipeline);
         } else {
            connection.getChannel().eventLoop().submit(() -> this.sendNow(protocol, skipCurrentPipeline));
         }

      }
   }

   void sendNow(Class protocol, boolean skipCurrentPipeline) throws InformativeException {
      try {
         ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
         this.user().sendRawPacket(output);
      } catch (InformativeException e) {
         throw e;
      } catch (CancelException var5) {
      } catch (Exception e) {
         if (!PipelineUtil.containsCause(e, CancelException.class)) {
            throw new InformativeException(e);
         }
      }

   }

   ByteBuf constructPacket(@Nullable Class protocolClass, boolean skipCurrentPipeline, Direction direction) throws InformativeException, CancelException {
      this.resetReader();
      ProtocolInfo protocolInfo = this.user().getProtocolInfo();
      List<Protocol> protocols = protocolInfo.getPipeline().pipes(protocolClass, skipCurrentPipeline, direction);
      this.apply(direction, protocolInfo.getState(direction), protocols);
      ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

      ByteBuf var7;
      try {
         this.writeToBuffer(output);
         var7 = output.retain();
      } finally {
         output.release();
      }

      return var7;
   }

   public ChannelFuture sendFuture(Class protocolClass) throws InformativeException {
      if (!this.isCancelled()) {
         ByteBuf output;
         try {
            output = this.constructPacket(protocolClass, true, Direction.CLIENTBOUND);
         } catch (CancelException var4) {
            return this.user().getChannel().newFailedFuture(new RuntimeException("Cancelled packet"));
         }

         return this.user().sendRawPacketFuture(output);
      } else {
         return this.cancelledFuture();
      }
   }

   public void sendRaw() throws InformativeException {
      this.sendRaw(true);
   }

   public ChannelFuture sendFutureRaw() throws InformativeException {
      if (this.isCancelled()) {
         return this.cancelledFuture();
      } else {
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         ChannelFuture var2;
         try {
            this.writeToBuffer(output);
            var2 = this.user().sendRawPacketFuture(output.retain());
         } finally {
            output.release();
         }

         return var2;
      }
   }

   public void scheduleSendRaw() throws InformativeException {
      this.sendRaw(false);
   }

   void sendRaw(boolean currentThread) throws InformativeException {
      if (!this.isCancelled()) {
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         try {
            this.writeToBuffer(output);
            if (currentThread) {
               this.user().sendRawPacket(output.retain());
            } else {
               this.user().scheduleSendRawPacket(output.retain());
            }
         } finally {
            output.release();
         }

      }
   }

   ChannelFuture cancelledFuture() {
      return this.user().getChannel().newFailedFuture(new RuntimeException("Tried to send cancelled packet"));
   }

   public PacketWrapperImpl create(int packetId) {
      return new PacketWrapperImpl(packetId, (ByteBuf)null, this.user());
   }

   public PacketWrapperImpl create(int packetId, PacketHandler handler) throws InformativeException {
      PacketWrapperImpl wrapper = this.create(packetId);
      handler.handle(wrapper);
      return wrapper;
   }

   public void apply(Direction direction, State state, List pipeline) throws InformativeException, CancelException {
      int i = 0;

      for(int size = pipeline.size(); i < size; ++i) {
         Protocol<?, ?, ?, ?> protocol = (Protocol)pipeline.get(i);
         protocol.transform(direction, state, this);
         this.resetReader();
         if (this.packetType != null) {
            state = this.packetType.state();
         }
      }

   }

   public boolean isCancelled() {
      return !this.send;
   }

   public void setCancelled(boolean cancel) {
      this.send = !cancel;
   }

   public UserConnection user() {
      return this.userConnection;
   }

   public void resetReader() {
      for(int i = this.packetValues.size() - 1; i >= 0; --i) {
         this.readableObjects.addFirst((PacketValue)this.packetValues.get(i));
      }

      this.packetValues.clear();
   }

   public void sendToServerRaw() throws InformativeException {
      this.sendToServerRaw(true);
   }

   public void scheduleSendToServerRaw() throws InformativeException {
      this.sendToServerRaw(false);
   }

   void sendToServerRaw(boolean currentThread) throws InformativeException {
      if (!this.isCancelled()) {
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         try {
            this.writeToBuffer(output);
            if (currentThread) {
               this.user().sendRawPacketToServer(output.retain());
            } else {
               this.user().scheduleSendRawPacketToServer(output.retain());
            }
         } finally {
            output.release();
         }

      }
   }

   public void sendToServer(Class protocol, boolean skipCurrentPipeline) throws InformativeException {
      this.sendToServer0(protocol, skipCurrentPipeline, true);
   }

   public void scheduleSendToServer(Class protocol, boolean skipCurrentPipeline) throws InformativeException {
      this.sendToServer0(protocol, skipCurrentPipeline, false);
   }

   void sendToServer0(Class protocol, boolean skipCurrentPipeline, boolean currentThread) throws InformativeException {
      if (!this.isCancelled()) {
         UserConnection connection = this.user();
         if (currentThread) {
            try {
               ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
               connection.sendRawPacketToServer(output);
            } catch (InformativeException e) {
               throw e;
            } catch (CancelException var7) {
            } catch (Exception e) {
               if (!PipelineUtil.containsCause(e, CancelException.class)) {
                  throw new InformativeException(e);
               }
            }

         } else {
            connection.getChannel().eventLoop().submit(() -> {
               try {
                  ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
                  connection.sendRawPacketToServer(output);
               } catch (InformativeException e) {
                  throw e;
               } catch (CancelException var6) {
               } catch (Exception e) {
                  if (!PipelineUtil.containsCause(e, CancelException.class)) {
                     throw new InformativeException(e);
                  }
               }

            });
         }
      }
   }

   public @Nullable PacketType getPacketType() {
      return this.packetType;
   }

   public void setPacketType(PacketType packetType) {
      this.packetType = packetType;
      this.id = packetType != null ? packetType.getId() : -1;
   }

   public int getId() {
      return this.id;
   }

   /** @deprecated */
   @Deprecated
   public void setId(int id) {
      this.packetType = null;
      this.id = id;
   }

   public @Nullable ByteBuf getInputBuffer() {
      return this.inputBuffer;
   }

   public String toString() {
      Deque var6 = this.readableObjects;
      List var5 = this.packetValues;
      int var4 = this.id;
      PacketType var3 = this.packetType;
      return "PacketWrapper{type=" + var3 + ", id=" + var4 + ", values=" + var5 + ", readable=" + var6 + "}";
   }

   public static final class PacketValue {
      final Type type;
      Object value;

      PacketValue(Type type, @Nullable Object value) {
         this.type = type;
         this.value = value;
      }

      public Type type() {
         return this.type;
      }

      public @Nullable Object value() {
         return this.value;
      }

      public void write(ByteBuf buffer) throws Exception {
         this.type.write(buffer, this.value);
      }

      public void setValue(@Nullable Object value) {
         this.value = value;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            PacketValue<?> that = (PacketValue)o;
            return !this.type.equals(that.type) ? false : Objects.equals(this.value, that.value);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.type.hashCode();
         result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
         return result;
      }

      public String toString() {
         Object var4 = this.value;
         Type var3 = this.type;
         return "{" + var3 + ": " + var4 + "}";
      }
   }
}
