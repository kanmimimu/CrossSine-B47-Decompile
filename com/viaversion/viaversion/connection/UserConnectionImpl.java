package com.viaversion.viaversion.connection;

import com.google.common.cache.CacheBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.CodecException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public class UserConnectionImpl implements UserConnection {
   private static final AtomicLong IDS = new AtomicLong();
   private final long id;
   private final Map storedObjects;
   private final Map entityTrackers;
   private final Map clientWorlds;
   private final PacketTracker packetTracker;
   private final Set passthroughTokens;
   private final ProtocolInfo protocolInfo;
   private final Channel channel;
   private final boolean clientSide;
   private boolean active;
   private boolean pendingDisconnect;

   public UserConnectionImpl(@Nullable Channel channel, boolean clientSide) {
      this.id = IDS.incrementAndGet();
      this.storedObjects = new ConcurrentHashMap();
      this.entityTrackers = new HashMap();
      this.clientWorlds = new HashMap();
      this.packetTracker = new PacketTracker(this);
      this.passthroughTokens = Collections.newSetFromMap(CacheBuilder.newBuilder().expireAfterWrite(10L, TimeUnit.SECONDS).build().asMap());
      this.protocolInfo = new ProtocolInfoImpl();
      this.active = true;
      this.channel = channel;
      this.clientSide = clientSide;
   }

   public UserConnectionImpl(@Nullable Channel channel) {
      this(channel, false);
   }

   public @Nullable StorableObject get(Class objectClass) {
      return (StorableObject)this.storedObjects.get(objectClass);
   }

   public boolean has(Class objectClass) {
      return this.storedObjects.containsKey(objectClass);
   }

   public @Nullable StorableObject remove(Class objectClass) {
      StorableObject object = (StorableObject)this.storedObjects.remove(objectClass);
      if (object != null) {
         object.onRemove();
      }

      return object;
   }

   public void put(StorableObject object) {
      StorableObject previousObject = (StorableObject)this.storedObjects.put(object.getClass(), object);
      if (previousObject != null) {
         previousObject.onRemove();
      }

   }

   public Collection getEntityTrackers() {
      return this.entityTrackers.values();
   }

   public @Nullable EntityTracker getEntityTracker(Class protocolClass) {
      return (EntityTracker)this.entityTrackers.get(protocolClass);
   }

   public void addEntityTracker(Class protocolClass, EntityTracker tracker) {
      this.entityTrackers.putIfAbsent(protocolClass, tracker);
   }

   public @Nullable ClientWorld getClientWorld(Class protocolClass) {
      return (ClientWorld)this.clientWorlds.get(protocolClass);
   }

   public void addClientWorld(Class protocolClass, ClientWorld clientWorld) {
      this.clientWorlds.putIfAbsent(protocolClass, clientWorld);
   }

   public void clearStoredObjects(boolean isServerSwitch) {
      if (isServerSwitch) {
         this.storedObjects.values().removeIf((storableObject) -> {
            if (storableObject.clearOnServerSwitch()) {
               storableObject.onRemove();
               return true;
            } else {
               return false;
            }
         });

         for(EntityTracker tracker : this.entityTrackers.values()) {
            tracker.clearEntities();
         }
      } else {
         for(StorableObject object : this.storedObjects.values()) {
            object.onRemove();
         }

         this.storedObjects.clear();
         this.entityTrackers.clear();
         this.clientWorlds.clear();
      }

   }

   public void sendRawPacket(ByteBuf packet) {
      this.sendRawPacket(packet, true);
   }

   public void scheduleSendRawPacket(ByteBuf packet) {
      this.sendRawPacket(packet, false);
   }

   private void sendRawPacket(ByteBuf packet, boolean currentThread) {
      if (currentThread) {
         this.sendRawPacketNow(packet);
      } else {
         try {
            this.channel.eventLoop().submit(() -> this.sendRawPacketNow(packet));
         } catch (Throwable e) {
            packet.release();
            e.printStackTrace();
         }
      }

   }

   private void sendRawPacketNow(ByteBuf buf) {
      ChannelPipeline pipeline = this.getChannel().pipeline();
      ViaInjector injector = Via.getManager().getInjector();
      if (this.clientSide) {
         pipeline.context(injector.getDecoderName()).fireChannelRead(buf);
      } else {
         pipeline.context(injector.getEncoderName()).writeAndFlush(buf);
      }

   }

   public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
      if (this.clientSide) {
         this.getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(packet);
         return this.getChannel().newSucceededFuture();
      } else {
         return this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(packet);
      }
   }

   public PacketTracker getPacketTracker() {
      return this.packetTracker;
   }

   public void disconnect(String reason) {
      if (this.channel.isOpen() && !this.pendingDisconnect) {
         this.pendingDisconnect = true;
         Via.getPlatform().runSync(() -> {
            if (!Via.getPlatform().disconnect(this, ChatColorUtil.translateAlternateColorCodes(reason))) {
               this.channel.close();
            }

         });
      }
   }

   public void sendRawPacketToServer(ByteBuf packet) {
      if (this.clientSide) {
         this.sendRawPacketToServerClientSide(packet, true);
      } else {
         this.sendRawPacketToServerServerSide(packet, true);
      }

   }

   public void scheduleSendRawPacketToServer(ByteBuf packet) {
      if (this.clientSide) {
         this.sendRawPacketToServerClientSide(packet, false);
      } else {
         this.sendRawPacketToServerServerSide(packet, false);
      }

   }

   private void sendRawPacketToServerServerSide(ByteBuf packet, boolean currentThread) {
      ByteBuf buf = packet.alloc().buffer();

      try {
         ChannelHandlerContext context = PipelineUtil.getPreviousContext(Via.getManager().getInjector().getDecoderName(), this.channel.pipeline());
         if (this.shouldTransformPacket()) {
            Types.VAR_INT.writePrimitive(buf, 1000);
            Types.UUID.write(buf, this.generatePassthroughToken());
         }

         buf.writeBytes(packet);
         if (currentThread) {
            this.fireChannelRead(context, buf);
         } else {
            try {
               this.channel.eventLoop().submit(() -> this.fireChannelRead(context, buf));
            } catch (Throwable t) {
               buf.release();
               throw t;
            }
         }
      } finally {
         packet.release();
      }

   }

   private void fireChannelRead(@Nullable ChannelHandlerContext context, ByteBuf buf) {
      if (context != null) {
         context.fireChannelRead(buf);
      } else {
         this.channel.pipeline().fireChannelRead(buf);
      }

   }

   private void sendRawPacketToServerClientSide(ByteBuf packet, boolean currentThread) {
      if (currentThread) {
         this.writeAndFlush(packet);
      } else {
         try {
            this.getChannel().eventLoop().submit(() -> this.writeAndFlush(packet));
         } catch (Throwable e) {
            e.printStackTrace();
            packet.release();
         }
      }

   }

   private void writeAndFlush(ByteBuf buf) {
      this.getChannel().pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(buf);
   }

   public boolean checkServerboundPacket() {
      if (this.pendingDisconnect) {
         return false;
      } else {
         return !this.packetTracker.isPacketLimiterEnabled() || !this.packetTracker.incrementReceived() || !this.packetTracker.exceedsMaxPPS();
      }
   }

   public boolean checkClientboundPacket() {
      this.packetTracker.incrementSent();
      return true;
   }

   public boolean shouldTransformPacket() {
      return this.active;
   }

   public void transformClientbound(ByteBuf buf, Function cancelSupplier) throws InformativeException, CodecException {
      this.transform(buf, Direction.CLIENTBOUND, cancelSupplier);
   }

   public void transformServerbound(ByteBuf buf, Function cancelSupplier) throws InformativeException, CodecException {
      this.transform(buf, Direction.SERVERBOUND, cancelSupplier);
   }

   private void transform(ByteBuf buf, Direction direction, Function cancelSupplier) throws InformativeException, CodecException {
      if (buf.isReadable()) {
         int id = Types.VAR_INT.readPrimitive(buf);
         if (id == 1000) {
            if (!this.passthroughTokens.remove(Types.UUID.read(buf))) {
               throw new IllegalArgumentException("Invalid token");
            }
         } else {
            PacketWrapper wrapper = new PacketWrapperImpl(id, buf, this);
            State state = this.protocolInfo.getState(direction);

            try {
               this.protocolInfo.getPipeline().transform(direction, state, wrapper);
            } catch (CancelException ex) {
               throw (CodecException)cancelSupplier.apply(ex);
            }

            ByteBuf transformed = buf.alloc().buffer();

            try {
               wrapper.writeToBuffer(transformed);
               buf.clear().writeBytes(transformed);
            } finally {
               transformed.release();
            }

         }
      }
   }

   public long getId() {
      return this.id;
   }

   public @Nullable Channel getChannel() {
      return this.channel;
   }

   public ProtocolInfo getProtocolInfo() {
      return this.protocolInfo;
   }

   public Map getStoredObjects() {
      return this.storedObjects;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public boolean isPendingDisconnect() {
      return this.pendingDisconnect;
   }

   public void setPendingDisconnect(boolean pendingDisconnect) {
      this.pendingDisconnect = pendingDisconnect;
   }

   public boolean isClientSide() {
      return this.clientSide;
   }

   public boolean shouldApplyBlockProtocol() {
      return !this.clientSide;
   }

   public UUID generatePassthroughToken() {
      UUID token = UUID.randomUUID();
      this.passthroughTokens.add(token);
      return token;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         UserConnectionImpl that = (UserConnectionImpl)o;
         return this.id == that.id;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Long.hashCode(this.id);
   }
}
