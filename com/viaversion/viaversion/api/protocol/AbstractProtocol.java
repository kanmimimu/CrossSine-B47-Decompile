package com.viaversion.viaversion.api.protocol;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMapping;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMappings;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.rewriter.MappingDataListener;
import com.viaversion.viaversion.api.rewriter.Rewriter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.ProtocolLogger;
import com.viaversion.viaversion.util.ProtocolUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractProtocol implements Protocol {
   protected final Class unmappedClientboundPacketType;
   protected final Class mappedClientboundPacketType;
   protected final Class mappedServerboundPacketType;
   protected final Class unmappedServerboundPacketType;
   protected final PacketTypesProvider packetTypesProvider;
   protected final PacketMappings clientboundMappings;
   protected final PacketMappings serverboundMappings;
   private final Map storedObjects;
   private boolean initialized;
   private ProtocolLogger logger;

   /** @deprecated */
   @Deprecated
   protected AbstractProtocol() {
      this((Class)null, (Class)null, (Class)null, (Class)null);
   }

   protected AbstractProtocol(@Nullable Class unmappedClientboundPacketType, @Nullable Class mappedClientboundPacketType, @Nullable Class mappedServerboundPacketType, @Nullable Class unmappedServerboundPacketType) {
      this.storedObjects = new HashMap();
      this.unmappedClientboundPacketType = unmappedClientboundPacketType;
      this.mappedClientboundPacketType = mappedClientboundPacketType;
      this.mappedServerboundPacketType = mappedServerboundPacketType;
      this.unmappedServerboundPacketType = unmappedServerboundPacketType;
      this.packetTypesProvider = this.createPacketTypesProvider();
      this.clientboundMappings = this.createClientboundPacketMappings();
      this.serverboundMappings = this.createServerboundPacketMappings();
   }

   public final void initialize() {
      Preconditions.checkArgument(!this.initialized, "Protocol has already been initialized");
      this.initialized = true;
      if (this.getLogger() == null) {
         this.logger = new ProtocolLogger(this.getClass());
      }

      this.registerPackets();
      this.registerConfigurationChangeHandlers();
      if (this.unmappedClientboundPacketType != null && this.mappedClientboundPacketType != null && this.unmappedClientboundPacketType != this.mappedClientboundPacketType) {
         this.registerPacketIdChanges(this.packetTypesProvider.unmappedClientboundPacketTypes(), this.packetTypesProvider.mappedClientboundPacketTypes(), this::hasRegisteredClientbound, this::registerClientbound);
      }

      if (this.mappedServerboundPacketType != null && this.unmappedServerboundPacketType != null && this.mappedServerboundPacketType != this.unmappedServerboundPacketType) {
         this.registerPacketIdChanges(this.packetTypesProvider.unmappedServerboundPacketTypes(), this.packetTypesProvider.mappedServerboundPacketTypes(), this::hasRegisteredServerbound, this::registerServerbound);
      }

   }

   protected void registerConfigurationChangeHandlers() {
      SU configurationAcknowledgedPacket = (SU)this.configurationAcknowledgedPacket();
      if (configurationAcknowledgedPacket != null) {
         this.appendServerbound(configurationAcknowledgedPacket, this.setClientStateHandler(State.CONFIGURATION));
      }

      CU startConfigurationPacket = (CU)this.startConfigurationPacket();
      if (startConfigurationPacket != null) {
         this.appendClientbound(startConfigurationPacket, this.setServerStateHandler(State.CONFIGURATION));
      }

      SU finishConfigurationPacket = (SU)this.serverboundFinishConfigurationPacket();
      if (finishConfigurationPacket != null) {
         this.appendServerbound(finishConfigurationPacket, this.setClientStateHandler(State.PLAY));
      }

      CU clientboundFinishConfigurationPacket = (CU)this.clientboundFinishConfigurationPacket();
      if (clientboundFinishConfigurationPacket != null) {
         this.appendClientbound(clientboundFinishConfigurationPacket, this.setServerStateHandler(State.PLAY));
      }

   }

   public void appendClientbound(ClientboundPacketType type, PacketHandler handler) {
      PacketMapping mapping = this.clientboundMappings.mappedPacket(type.state(), type.getId());
      if (mapping != null) {
         mapping.appendHandler(handler);
      } else {
         this.registerClientbound(type, handler);
      }

   }

   public void appendServerbound(ServerboundPacketType type, PacketHandler handler) {
      PacketMapping mapping = this.serverboundMappings.mappedPacket(type.state(), type.getId());
      if (mapping != null) {
         mapping.appendHandler(handler);
      } else {
         this.registerServerbound(type, handler);
      }

   }

   private void registerPacketIdChanges(Map unmappedPacketTypes, Map mappedPacketTypes, Predicate registeredPredicate, BiConsumer registerConsumer) {
      for(Map.Entry entry : mappedPacketTypes.entrySet()) {
         PacketTypeMap<M> mappedTypes = (PacketTypeMap)entry.getValue();
         PacketTypeMap<U> unmappedTypes = (PacketTypeMap)unmappedPacketTypes.get(entry.getKey());

         for(PacketType unmappedType : unmappedTypes.types()) {
            M mappedType = (M)mappedTypes.typeByName(unmappedType.getName());
            if (mappedType == null) {
               Preconditions.checkArgument(registeredPredicate.test(unmappedType), "Packet %s in %s has no mapping - it needs to be manually cancelled or remapped", new Object[]{unmappedType, this.getClass()});
            } else if (unmappedType.getId() != mappedType.getId() && !registeredPredicate.test(unmappedType)) {
               registerConsumer.accept(unmappedType, mappedType);
            }
         }
      }

   }

   public final void loadMappingData() {
      this.getMappingData().load();
      this.onMappingDataLoaded();
   }

   protected void registerPackets() {
      this.callRegister(this.getEntityRewriter());
      this.callRegister(this.getItemRewriter());
   }

   protected void onMappingDataLoaded() {
      this.callOnMappingDataLoaded(this.getEntityRewriter());
      this.callOnMappingDataLoaded(this.getItemRewriter());
      this.callOnMappingDataLoaded(this.getTagRewriter());
   }

   private void callRegister(@Nullable Rewriter rewriter) {
      if (rewriter != null) {
         rewriter.register();
      }

   }

   private void callOnMappingDataLoaded(@Nullable MappingDataListener rewriter) {
      if (rewriter != null) {
         rewriter.onMappingDataLoaded();
      }

   }

   protected void addEntityTracker(UserConnection connection, EntityTracker tracker) {
      connection.addEntityTracker(this.getClass(), tracker);
   }

   protected PacketTypesProvider createPacketTypesProvider() {
      return new SimplePacketTypesProvider(ProtocolUtil.packetTypeMap(this.unmappedClientboundPacketType, this.unmappedClientboundPacketType), ProtocolUtil.packetTypeMap(this.mappedClientboundPacketType, this.mappedClientboundPacketType), ProtocolUtil.packetTypeMap(this.mappedServerboundPacketType, this.mappedServerboundPacketType), ProtocolUtil.packetTypeMap(this.unmappedServerboundPacketType, this.unmappedServerboundPacketType));
   }

   protected PacketMappings createClientboundPacketMappings() {
      return PacketMappings.arrayMappings();
   }

   protected PacketMappings createServerboundPacketMappings() {
      return PacketMappings.arrayMappings();
   }

   protected @Nullable ServerboundPacketType configurationAcknowledgedPacket() {
      return this.packetTypesProvider.unmappedServerboundType(State.PLAY, "CONFIGURATION_ACKNOWLEDGED");
   }

   protected @Nullable ClientboundPacketType startConfigurationPacket() {
      return this.packetTypesProvider.unmappedClientboundType(State.PLAY, "START_CONFIGURATION");
   }

   protected @Nullable ServerboundPacketType serverboundFinishConfigurationPacket() {
      return this.packetTypesProvider.unmappedServerboundType(State.CONFIGURATION, "FINISH_CONFIGURATION");
   }

   protected @Nullable ClientboundPacketType clientboundFinishConfigurationPacket() {
      return this.packetTypesProvider.unmappedClientboundType(State.CONFIGURATION, "FINISH_CONFIGURATION");
   }

   public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler, boolean override) {
      Preconditions.checkArgument(unmappedPacketId != -1, "Unmapped packet id cannot be -1");
      PacketMapping packetMapping = PacketMapping.of(mappedPacketId, handler);
      if (!override && this.serverboundMappings.hasMapping(state, unmappedPacketId)) {
         Via.getPlatform().getLogger().log(Level.WARNING, unmappedPacketId + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
      }

      this.serverboundMappings.addMapping(state, unmappedPacketId, packetMapping);
   }

   public void cancelServerbound(State state, int unmappedPacketId) {
      this.registerServerbound(state, unmappedPacketId, unmappedPacketId, PacketWrapper::cancel);
   }

   public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler, boolean override) {
      Preconditions.checkArgument(unmappedPacketId != -1, "Unmapped packet id cannot be -1");
      PacketMapping packetMapping = PacketMapping.of(mappedPacketId, handler);
      if (!override && this.clientboundMappings.hasMapping(state, unmappedPacketId)) {
         Via.getPlatform().getLogger().log(Level.WARNING, unmappedPacketId + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
      }

      this.clientboundMappings.addMapping(state, unmappedPacketId, packetMapping);
   }

   public void cancelClientbound(State state, int unmappedPacketId) {
      this.registerClientbound(state, unmappedPacketId, unmappedPacketId, PacketWrapper::cancel);
   }

   public void registerClientbound(ClientboundPacketType packetType, @Nullable PacketHandler handler) {
      PacketTypeMap<CM> mappedPacketTypes = (PacketTypeMap)this.packetTypesProvider.mappedClientboundPacketTypes().get(packetType.state());
      CM mappedPacketType = (CM)((ClientboundPacketType)mappedPacketType(packetType, mappedPacketTypes, this.unmappedClientboundPacketType, this.mappedClientboundPacketType));
      this.registerClientbound(packetType, mappedPacketType, handler);
   }

   public void registerClientbound(ClientboundPacketType packetType, @Nullable ClientboundPacketType mappedPacketType, @Nullable PacketHandler handler, boolean override) {
      this.register(this.clientboundMappings, packetType, mappedPacketType, this.unmappedClientboundPacketType, this.mappedClientboundPacketType, handler, override);
   }

   public void cancelClientbound(ClientboundPacketType packetType) {
      this.registerClientbound(packetType, (ClientboundPacketType)null, PacketWrapper::cancel);
   }

   public void registerServerbound(ServerboundPacketType packetType, @Nullable PacketHandler handler) {
      PacketTypeMap<SM> mappedPacketTypes = (PacketTypeMap)this.packetTypesProvider.mappedServerboundPacketTypes().get(packetType.state());
      SM mappedPacketType = (SM)((ServerboundPacketType)mappedPacketType(packetType, mappedPacketTypes, this.unmappedServerboundPacketType, this.mappedServerboundPacketType));
      this.registerServerbound(packetType, mappedPacketType, handler);
   }

   public void registerServerbound(ServerboundPacketType packetType, @Nullable ServerboundPacketType mappedPacketType, @Nullable PacketHandler handler, boolean override) {
      this.register(this.serverboundMappings, packetType, mappedPacketType, this.unmappedServerboundPacketType, this.mappedServerboundPacketType, handler, override);
   }

   public void cancelServerbound(ServerboundPacketType packetType) {
      this.registerServerbound(packetType, (ServerboundPacketType)null, PacketWrapper::cancel);
   }

   private void register(PacketMappings packetMappings, PacketType packetType, @Nullable PacketType mappedPacketType, Class unmappedPacketClass, Class mappedPacketClass, @Nullable PacketHandler handler, boolean override) {
      checkPacketType(packetType, unmappedPacketClass == null || unmappedPacketClass.isInstance(packetType));
      if (mappedPacketType != null) {
         checkPacketType(mappedPacketType, mappedPacketClass == null || mappedPacketClass.isInstance(mappedPacketType));
         Preconditions.checkArgument(packetType.state() == mappedPacketType.state(), "Packet type state does not match mapped packet type state");
         Preconditions.checkArgument(packetType.direction() == mappedPacketType.direction(), "Packet type direction does not match mapped packet type state");
      }

      PacketMapping packetMapping = PacketMapping.of(mappedPacketType, handler);
      if (!override && packetMappings.hasMapping(packetType)) {
         this.getLogger().log(Level.WARNING, packetType + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
      }

      packetMappings.addMapping(packetType, packetMapping);
   }

   private static PacketType mappedPacketType(PacketType packetType, PacketTypeMap mappedTypes, Class unmappedPacketTypeClass, Class mappedPacketTypeClass) {
      Preconditions.checkNotNull(packetType);
      checkPacketType(packetType, unmappedPacketTypeClass == null || unmappedPacketTypeClass.isInstance(packetType));
      if (unmappedPacketTypeClass == mappedPacketTypeClass) {
         return packetType;
      } else {
         Preconditions.checkNotNull(mappedTypes, "Mapped packet types not provided for state %s of type class %s", new Object[]{packetType.state(), mappedPacketTypeClass});
         M mappedType = (M)mappedTypes.typeByName(packetType.getName());
         if (mappedType != null) {
            return mappedType;
         } else {
            String var7 = packetType.getClass().getSimpleName();
            throw new IllegalArgumentException("Packet type " + packetType + " in " + var7 + " could not be automatically mapped!");
         }
      }
   }

   public boolean hasRegisteredClientbound(State state, int unmappedPacketId) {
      return this.clientboundMappings.hasMapping(state, unmappedPacketId);
   }

   public boolean hasRegisteredServerbound(State state, int unmappedPacketId) {
      return this.serverboundMappings.hasMapping(state, unmappedPacketId);
   }

   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws InformativeException, CancelException {
      PacketMappings mappings = direction == Direction.CLIENTBOUND ? this.clientboundMappings : this.serverboundMappings;
      int unmappedId = packetWrapper.getId();
      PacketMapping packetMapping = mappings.mappedPacket(state, unmappedId);
      if (packetMapping != null) {
         packetMapping.applyType(packetWrapper);
         PacketHandler handler = packetMapping.handler();
         if (handler != null) {
            try {
               handler.handle(packetWrapper);
            } catch (InformativeException e) {
               e.addSource(handler.getClass());
               this.printRemapError(direction, state, unmappedId, packetWrapper.getId(), e);
               throw e;
            } catch (Exception e) {
               InformativeException ex = new InformativeException(e);
               ex.addSource(handler.getClass());
               this.printRemapError(direction, state, unmappedId, packetWrapper.getId(), ex);
               throw ex;
            }

            if (packetWrapper.isCancelled()) {
               throw CancelException.generate();
            }
         }

      }
   }

   public ProtocolLogger getLogger() {
      return this.logger;
   }

   private void printRemapError(Direction direction, State state, int unmappedPacketId, int mappedPacketId, InformativeException e) {
      if (state != State.PLAY && direction == Direction.SERVERBOUND && !Via.getManager().debugHandler().enabled()) {
         e.setShouldBePrinted(false);
      } else {
         PacketType packetType = (PacketType)(direction == Direction.CLIENTBOUND ? this.packetTypesProvider.unmappedClientboundType(state, unmappedPacketId) : this.packetTypesProvider.unmappedServerboundType(state, unmappedPacketId));
         if (packetType != null) {
            Logger var10000 = Via.getPlatform().getLogger();
            String var10001 = this.getClass().getSimpleName();
            String var10 = ProtocolUtil.toNiceHex(unmappedPacketId);
            String var8 = var10001;
            var10000.warning("ERROR IN " + var8 + " IN REMAP OF " + packetType + " (" + var10 + ")");
         } else {
            Logger var16 = Via.getPlatform().getLogger();
            String var17 = this.getClass().getSimpleName();
            String var10003 = ProtocolUtil.toNiceHex(unmappedPacketId);
            String var15 = ProtocolUtil.toNiceHex(mappedPacketId);
            String var14 = var10003;
            String var12 = var17;
            var16.warning("ERROR IN " + var12 + " IN REMAP OF " + state + " " + var14 + "->" + var15);
         }

      }
   }

   private static void checkPacketType(PacketType packetType, boolean isValid) {
      if (!isValid) {
         String var5 = packetType.getClass().getSimpleName();
         throw new IllegalArgumentException("Packet type " + packetType + " in " + var5 + " is taken from the wrong packet types class");
      }
   }

   private PacketHandler setClientStateHandler(State state) {
      return (wrapper) -> wrapper.user().getProtocolInfo().setClientState(state);
   }

   private PacketHandler setServerStateHandler(State state) {
      return (wrapper) -> wrapper.user().getProtocolInfo().setServerState(state);
   }

   public final PacketTypesProvider getPacketTypesProvider() {
      return this.packetTypesProvider;
   }

   public @Nullable Object get(Class objectClass) {
      return this.storedObjects.get(objectClass);
   }

   public void put(Object object) {
      this.storedObjects.put(object.getClass(), object);
   }

   public String toString() {
      String var3 = this.getClass().getSimpleName();
      return "Protocol:" + var3;
   }
}
