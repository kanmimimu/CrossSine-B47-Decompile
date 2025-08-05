package com.viaversion.viaversion.protocol;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPathKey;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionType;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocol.packet.VersionedPacketTransformerImpl;
import com.viaversion.viaversion.protocols.base.InitialBaseProtocol;
import com.viaversion.viaversion.protocols.base.v1_7.ClientboundBaseProtocol1_7;
import com.viaversion.viaversion.protocols.base.v1_7.ServerboundBaseProtocol1_7;
import com.viaversion.viaversion.protocols.v1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viaversion.protocols.v1_11to1_11_1.Protocol1_11To1_11_1;
import com.viaversion.viaversion.protocols.v1_12_1to1_12_2.Protocol1_12_1To1_12_2;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.Protocol1_12To1_12_1;
import com.viaversion.viaversion.protocols.v1_13_1to1_13_2.Protocol1_13_1To1_13_2;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.protocols.v1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.protocols.v1_14_1to1_14_2.Protocol1_14_1To1_14_2;
import com.viaversion.viaversion.protocols.v1_14_2to1_14_3.Protocol1_14_2To1_14_3;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.Protocol1_14_3To1_14_4;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.protocols.v1_14to1_14_1.Protocol1_14To1_14_1;
import com.viaversion.viaversion.protocols.v1_15_1to1_15_2.Protocol1_15_1To1_15_2;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.protocols.v1_15to1_15_1.Protocol1_15To1_15_1;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.protocols.v1_16_2to1_16_3.Protocol1_16_2To1_16_3;
import com.viaversion.viaversion.protocols.v1_16_3to1_16_4.Protocol1_16_3To1_16_4;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.protocols.v1_16to1_16_1.Protocol1_16To1_16_1;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.Protocol1_17To1_17_1;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.protocols.v1_18to1_18_2.Protocol1_18To1_18_2;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.Protocol1_19_1To1_19_3;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.protocols.v1_19_4to1_20.Protocol1_19_4To1_20;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.Protocol1_19To1_19_1;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.Protocol1_20_2To1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.Protocol1_9_1To1_9_3;
import com.viaversion.viaversion.protocols.v1_9_3to1_10.Protocol1_9_3To1_10;
import com.viaversion.viaversion.protocols.v1_9to1_9_1.Protocol1_9To1_9_1;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ProtocolManagerImpl implements ProtocolManager {
   private static final Protocol BASE_PROTOCOL = new InitialBaseProtocol();
   private final Object2ObjectMap registryMap = new Object2ObjectOpenHashMap(32);
   private final Map protocols = new HashMap(64);
   private final Map pathCache = new ConcurrentHashMap();
   private final Set supportedVersions = new HashSet();
   private final List serverboundBaseProtocols = Lists.newCopyOnWriteArrayList();
   private final List clientboundBaseProtocols = Lists.newCopyOnWriteArrayList();
   private final ReadWriteLock mappingLoaderLock = new ReentrantReadWriteLock();
   private Map mappingLoaderFutures = new HashMap();
   private ThreadPoolExecutor mappingLoaderExecutor;
   private boolean mappingsLoaded;
   private ServerProtocolVersion serverProtocolVersion;
   private int maxPathDeltaIncrease;
   private int maxProtocolPathSize;

   public ProtocolManagerImpl() {
      this.serverProtocolVersion = new ServerProtocolVersionSingleton(ProtocolVersion.unknown);
      this.maxProtocolPathSize = 50;
      ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("Via-Mappingloader-%d").build();
      this.mappingLoaderExecutor = new ThreadPoolExecutor(12, Integer.MAX_VALUE, 30L, TimeUnit.SECONDS, new SynchronousQueue(), threadFactory);
      this.mappingLoaderExecutor.allowCoreThreadTimeOut(true);
   }

   public void registerProtocols() {
      BASE_PROTOCOL.initialize();
      BASE_PROTOCOL.register(Via.getManager().getProviders());
      this.registerBaseProtocol(Direction.CLIENTBOUND, new ClientboundBaseProtocol1_7(), Range.atLeast(ProtocolVersion.v1_7_2));
      this.registerBaseProtocol(Direction.SERVERBOUND, new ServerboundBaseProtocol1_7(), Range.atLeast(ProtocolVersion.v1_7_2));
      this.registerProtocol(new Protocol1_8To1_9(), (ProtocolVersion)ProtocolVersion.v1_9, ProtocolVersion.v1_8);
      this.registerProtocol(new Protocol1_9To1_9_1(), (List)Arrays.asList(ProtocolVersion.v1_9_1, ProtocolVersion.v1_9_2), ProtocolVersion.v1_9);
      this.registerProtocol(new Protocol1_9_1To1_9_3(), (ProtocolVersion)ProtocolVersion.v1_9_3, ProtocolVersion.v1_9_2);
      this.registerProtocol(new Protocol1_9_3To1_10(), (ProtocolVersion)ProtocolVersion.v1_10, ProtocolVersion.v1_9_3);
      this.registerProtocol(new Protocol1_10To1_11(), (ProtocolVersion)ProtocolVersion.v1_11, ProtocolVersion.v1_10);
      this.registerProtocol(new Protocol1_11To1_11_1(), (ProtocolVersion)ProtocolVersion.v1_11_1, ProtocolVersion.v1_11);
      this.registerProtocol(new Protocol1_11_1To1_12(), (ProtocolVersion)ProtocolVersion.v1_12, ProtocolVersion.v1_11_1);
      this.registerProtocol(new Protocol1_12To1_12_1(), (ProtocolVersion)ProtocolVersion.v1_12_1, ProtocolVersion.v1_12);
      this.registerProtocol(new Protocol1_12_1To1_12_2(), (ProtocolVersion)ProtocolVersion.v1_12_2, ProtocolVersion.v1_12_1);
      this.registerProtocol(new Protocol1_12_2To1_13(), (ProtocolVersion)ProtocolVersion.v1_13, ProtocolVersion.v1_12_2);
      this.registerProtocol(new Protocol1_13To1_13_1(), (ProtocolVersion)ProtocolVersion.v1_13_1, ProtocolVersion.v1_13);
      this.registerProtocol(new Protocol1_13_1To1_13_2(), (ProtocolVersion)ProtocolVersion.v1_13_2, ProtocolVersion.v1_13_1);
      this.registerProtocol(new Protocol1_13_2To1_14(), (ProtocolVersion)ProtocolVersion.v1_14, ProtocolVersion.v1_13_2);
      this.registerProtocol(new Protocol1_14To1_14_1(), (ProtocolVersion)ProtocolVersion.v1_14_1, ProtocolVersion.v1_14);
      this.registerProtocol(new Protocol1_14_1To1_14_2(), (ProtocolVersion)ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_1);
      this.registerProtocol(new Protocol1_14_2To1_14_3(), (ProtocolVersion)ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_2);
      this.registerProtocol(new Protocol1_14_3To1_14_4(), (ProtocolVersion)ProtocolVersion.v1_14_4, ProtocolVersion.v1_14_3);
      this.registerProtocol(new Protocol1_14_4To1_15(), (ProtocolVersion)ProtocolVersion.v1_15, ProtocolVersion.v1_14_4);
      this.registerProtocol(new Protocol1_15To1_15_1(), (ProtocolVersion)ProtocolVersion.v1_15_1, ProtocolVersion.v1_15);
      this.registerProtocol(new Protocol1_15_1To1_15_2(), (ProtocolVersion)ProtocolVersion.v1_15_2, ProtocolVersion.v1_15_1);
      this.registerProtocol(new Protocol1_15_2To1_16(), (ProtocolVersion)ProtocolVersion.v1_16, ProtocolVersion.v1_15_2);
      this.registerProtocol(new Protocol1_16To1_16_1(), (ProtocolVersion)ProtocolVersion.v1_16_1, ProtocolVersion.v1_16);
      this.registerProtocol(new Protocol1_16_1To1_16_2(), (ProtocolVersion)ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_1);
      this.registerProtocol(new Protocol1_16_2To1_16_3(), (ProtocolVersion)ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_2);
      this.registerProtocol(new Protocol1_16_3To1_16_4(), (ProtocolVersion)ProtocolVersion.v1_16_4, ProtocolVersion.v1_16_3);
      this.registerProtocol(new Protocol1_16_4To1_17(), (ProtocolVersion)ProtocolVersion.v1_17, ProtocolVersion.v1_16_4);
      this.registerProtocol(new Protocol1_17To1_17_1(), (ProtocolVersion)ProtocolVersion.v1_17_1, ProtocolVersion.v1_17);
      this.registerProtocol(new Protocol1_17_1To1_18(), (ProtocolVersion)ProtocolVersion.v1_18, ProtocolVersion.v1_17_1);
      this.registerProtocol(new Protocol1_18To1_18_2(), (ProtocolVersion)ProtocolVersion.v1_18_2, ProtocolVersion.v1_18);
      this.registerProtocol(new Protocol1_18_2To1_19(), (ProtocolVersion)ProtocolVersion.v1_19, ProtocolVersion.v1_18_2);
      this.registerProtocol(new Protocol1_19To1_19_1(), (ProtocolVersion)ProtocolVersion.v1_19_1, ProtocolVersion.v1_19);
      this.registerProtocol(new Protocol1_19_1To1_19_3(), (ProtocolVersion)ProtocolVersion.v1_19_3, ProtocolVersion.v1_19_1);
      this.registerProtocol(new Protocol1_19_3To1_19_4(), (ProtocolVersion)ProtocolVersion.v1_19_4, ProtocolVersion.v1_19_3);
      this.registerProtocol(new Protocol1_19_4To1_20(), (ProtocolVersion)ProtocolVersion.v1_20, ProtocolVersion.v1_19_4);
      this.registerProtocol(new Protocol1_20To1_20_2(), (ProtocolVersion)ProtocolVersion.v1_20_2, ProtocolVersion.v1_20);
      this.registerProtocol(new Protocol1_20_2To1_20_3(), (ProtocolVersion)ProtocolVersion.v1_20_3, ProtocolVersion.v1_20_2);
      this.registerProtocol(new Protocol1_20_3To1_20_5(), (ProtocolVersion)ProtocolVersion.v1_20_5, ProtocolVersion.v1_20_3);
      this.registerProtocol(new Protocol1_20_5To1_21(), (ProtocolVersion)ProtocolVersion.v1_21, ProtocolVersion.v1_20_5);
   }

   public void registerProtocol(Protocol protocol, ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
      this.registerProtocol(protocol, Collections.singletonList(clientVersion), serverVersion);
   }

   public void registerProtocol(Protocol protocol, List supportedClientVersion, ProtocolVersion serverVersion) {
      protocol.initialize();
      if (!this.pathCache.isEmpty()) {
         this.pathCache.clear();
      }

      this.protocols.put(protocol.getClass(), protocol);

      for(ProtocolVersion clientVersion : supportedClientVersion) {
         Preconditions.checkArgument(!clientVersion.equals(serverVersion));
         Object2ObjectMap<ProtocolVersion, Protocol> protocolMap = (Object2ObjectMap)this.registryMap.computeIfAbsent(clientVersion, (s) -> new Object2ObjectOpenHashMap(2));
         protocolMap.put(serverVersion, protocol);
      }

      protocol.register(Via.getManager().getProviders());
      if (Via.getManager().isInitialized()) {
         this.refreshVersions();
      }

      if (protocol.hasMappingDataToLoad()) {
         if (this.mappingLoaderExecutor != null) {
            Class var10001 = protocol.getClass();
            Objects.requireNonNull(protocol);
            this.addMappingLoaderFuture(var10001, protocol::loadMappingData);
         } else {
            protocol.loadMappingData();
         }
      }

   }

   public void registerBaseProtocol(Direction direction, Protocol baseProtocol, Range supportedProtocols) {
      Preconditions.checkArgument(baseProtocol.isBaseProtocol(), "Protocol is not a base protocol");
      ProtocolVersion lower = supportedProtocols.hasLowerBound() ? (ProtocolVersion)supportedProtocols.lowerEndpoint() : null;
      ProtocolVersion upper = supportedProtocols.hasUpperBound() ? (ProtocolVersion)supportedProtocols.upperEndpoint() : null;
      Preconditions.checkArgument(lower == null || lower.getVersionType() != VersionType.SPECIAL, "Base protocol versions cannot contain a special version");
      Preconditions.checkArgument(upper == null || upper.getVersionType() != VersionType.SPECIAL, "Base protocol versions cannot contain a special version");
      baseProtocol.initialize();
      if (direction == Direction.SERVERBOUND) {
         this.serverboundBaseProtocols.add(new Pair(supportedProtocols, baseProtocol));
      } else {
         this.clientboundBaseProtocols.add(new Pair(supportedProtocols, baseProtocol));
      }

      baseProtocol.register(Via.getManager().getProviders());
      if (Via.getManager().isInitialized()) {
         this.refreshVersions();
      }

   }

   public void refreshVersions() {
      this.supportedVersions.clear();
      this.supportedVersions.add(this.serverProtocolVersion.lowestSupportedProtocolVersion());

      for(ProtocolVersion version : ProtocolVersion.getProtocols()) {
         List<ProtocolPathEntry> protocolPath = this.getProtocolPath(version, this.serverProtocolVersion.lowestSupportedProtocolVersion());
         if (protocolPath != null) {
            this.supportedVersions.add(version);

            for(ProtocolPathEntry pathEntry : protocolPath) {
               this.supportedVersions.add(pathEntry.outputProtocolVersion());
            }
         }
      }

   }

   public @Nullable List getProtocolPath(ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
      if (clientVersion == serverVersion) {
         return null;
      } else {
         ProtocolPathKey protocolKey = new ProtocolPathKeyImpl(clientVersion, serverVersion);
         List<ProtocolPathEntry> protocolList = (List)this.pathCache.get(protocolKey);
         if (protocolList != null) {
            return protocolList.isEmpty() ? null : protocolList;
         } else {
            Object2ObjectSortedMap<ProtocolVersion, Protocol> outputPath = this.getProtocolPath(new Object2ObjectLinkedOpenHashMap(), clientVersion, serverVersion);
            if (outputPath == null) {
               this.pathCache.put(protocolKey, Collections.emptyList());
               return null;
            } else {
               List<ProtocolPathEntry> path = new ArrayList(outputPath.size());

               for(Map.Entry entry : outputPath.entrySet()) {
                  path.add(new ProtocolPathEntryImpl((ProtocolVersion)entry.getKey(), (Protocol)entry.getValue()));
               }

               this.pathCache.put(protocolKey, path);
               return path;
            }
         }
      }
   }

   public VersionedPacketTransformer createPacketTransformer(ProtocolVersion inputVersion, @Nullable Class clientboundPacketsClass, @Nullable Class serverboundPacketsClass) {
      Preconditions.checkArgument(clientboundPacketsClass != ClientboundPacketType.class && serverboundPacketsClass != ServerboundPacketType.class);
      return new VersionedPacketTransformerImpl(inputVersion, clientboundPacketsClass, serverboundPacketsClass);
   }

   private @Nullable Object2ObjectSortedMap getProtocolPath(Object2ObjectSortedMap current, ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
      if (current.size() > this.maxProtocolPathSize) {
         return null;
      } else {
         Object2ObjectMap<ProtocolVersion, Protocol> toServerProtocolMap = (Object2ObjectMap)this.registryMap.get(clientVersion);
         if (toServerProtocolMap == null) {
            return null;
         } else {
            Protocol protocol = (Protocol)toServerProtocolMap.get(serverVersion);
            if (protocol != null) {
               current.put(serverVersion, protocol);
               return current;
            } else {
               Object2ObjectSortedMap<ProtocolVersion, Protocol> shortest = null;

               for(Map.Entry entry : toServerProtocolMap.entrySet()) {
                  ProtocolVersion translatedToVersion = (ProtocolVersion)entry.getKey();
                  if (!current.containsKey(translatedToVersion)) {
                     if (this.maxPathDeltaIncrease != -1 && translatedToVersion.getVersionType() == clientVersion.getVersionType()) {
                        int delta = Math.abs(serverVersion.getVersion() - translatedToVersion.getVersion()) - Math.abs(serverVersion.getVersion() - clientVersion.getVersion());
                        if (delta > this.maxPathDeltaIncrease) {
                           continue;
                        }
                     }

                     Object2ObjectSortedMap<ProtocolVersion, Protocol> newCurrent = new Object2ObjectLinkedOpenHashMap(current);
                     newCurrent.put(translatedToVersion, (Protocol)entry.getValue());
                     newCurrent = this.getProtocolPath(newCurrent, translatedToVersion, serverVersion);
                     if (newCurrent != null && (shortest == null || newCurrent.size() < shortest.size())) {
                        shortest = newCurrent;
                     }
                  }
               }

               return shortest;
            }
         }
      }
   }

   public @Nullable Protocol getProtocol(Class protocolClass) {
      return (Protocol)this.protocols.get(protocolClass);
   }

   public @Nullable Protocol getProtocol(ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
      Object2ObjectMap<ProtocolVersion, Protocol> map = (Object2ObjectMap)this.registryMap.get(clientVersion);
      return map != null ? (Protocol)map.get(serverVersion) : null;
   }

   public List getBaseProtocols(@Nullable ProtocolVersion clientVersion, @Nullable ProtocolVersion serverVersion) {
      List<Protocol> list = new ArrayList();
      if (clientVersion != null) {
         for(Pair rangeProtocol : this.serverboundBaseProtocols) {
            if (((Range)rangeProtocol.key()).contains(clientVersion)) {
               list.add((Protocol)rangeProtocol.value());
            }
         }
      }

      if (serverVersion != null) {
         for(Pair rangeProtocol : this.clientboundBaseProtocols) {
            if (((Range)rangeProtocol.key()).contains(serverVersion)) {
               list.add((Protocol)rangeProtocol.value());
            }
         }
      }

      return list;
   }

   public Collection getProtocols() {
      return Collections.unmodifiableCollection(this.protocols.values());
   }

   public ServerProtocolVersion getServerProtocolVersion() {
      return this.serverProtocolVersion;
   }

   public void setServerProtocol(ServerProtocolVersion serverProtocolVersion) {
      this.serverProtocolVersion = serverProtocolVersion;
   }

   public boolean isWorkingPipe() {
      for(Object2ObjectMap map : this.registryMap.values()) {
         for(ProtocolVersion protocolVersion : this.serverProtocolVersion.supportedProtocolVersions()) {
            if (map.containsKey(protocolVersion)) {
               return true;
            }
         }
      }

      return false;
   }

   public SortedSet getSupportedVersions() {
      return Collections.unmodifiableSortedSet(new TreeSet(this.supportedVersions));
   }

   public void setMaxPathDeltaIncrease(int maxPathDeltaIncrease) {
      this.maxPathDeltaIncrease = Math.max(-1, maxPathDeltaIncrease);
   }

   public int getMaxPathDeltaIncrease() {
      return this.maxPathDeltaIncrease;
   }

   public int getMaxProtocolPathSize() {
      return this.maxProtocolPathSize;
   }

   public void setMaxProtocolPathSize(int maxProtocolPathSize) {
      this.maxProtocolPathSize = maxProtocolPathSize;
   }

   public Protocol getBaseProtocol() {
      return BASE_PROTOCOL;
   }

   public void completeMappingDataLoading(Class protocolClass) {
      if (!this.mappingsLoaded) {
         CompletableFuture<Void> future = this.getMappingLoaderFuture(protocolClass);
         if (future != null) {
            future.join();
         }

      }
   }

   public boolean checkForMappingCompletion() {
      this.mappingLoaderLock.readLock().lock();

      boolean var1;
      try {
         if (!this.mappingsLoaded) {
            for(CompletableFuture future : this.mappingLoaderFutures.values()) {
               if (!future.isDone()) {
                  boolean var3 = false;
                  return var3;
               }
            }

            this.shutdownLoaderExecutor();
            var1 = true;
            return var1;
         }

         var1 = false;
      } finally {
         this.mappingLoaderLock.readLock().unlock();
      }

      return var1;
   }

   public void addMappingLoaderFuture(Class protocolClass, Runnable runnable) {
      CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, this.mappingLoaderExecutor).exceptionally(this.mappingLoaderThrowable(protocolClass));
      this.mappingLoaderLock.writeLock().lock();

      try {
         this.mappingLoaderFutures.put(protocolClass, future);
      } finally {
         this.mappingLoaderLock.writeLock().unlock();
      }

   }

   public void addMappingLoaderFuture(Class protocolClass, Class dependsOn, Runnable runnable) {
      CompletableFuture<Void> future = this.getMappingLoaderFuture(dependsOn).whenCompleteAsync((v, throwable) -> runnable.run(), this.mappingLoaderExecutor).exceptionally(this.mappingLoaderThrowable(protocolClass));
      this.mappingLoaderLock.writeLock().lock();

      try {
         this.mappingLoaderFutures.put(protocolClass, future);
      } finally {
         this.mappingLoaderLock.writeLock().unlock();
      }

   }

   public @Nullable CompletableFuture getMappingLoaderFuture(Class protocolClass) {
      this.mappingLoaderLock.readLock().lock();

      CompletableFuture var2;
      try {
         var2 = this.mappingsLoaded ? null : (CompletableFuture)this.mappingLoaderFutures.get(protocolClass);
      } finally {
         this.mappingLoaderLock.readLock().unlock();
      }

      return var2;
   }

   public PacketWrapper createPacketWrapper(@Nullable PacketType packetType, @Nullable ByteBuf buf, UserConnection connection) {
      return new PacketWrapperImpl(packetType, buf, connection);
   }

   /** @deprecated */
   @Deprecated
   public PacketWrapper createPacketWrapper(int packetId, @Nullable ByteBuf buf, UserConnection connection) {
      return new PacketWrapperImpl(packetId, buf, connection);
   }

   public boolean hasLoadedMappings() {
      return this.mappingsLoaded;
   }

   public void shutdownLoaderExecutor() {
      Preconditions.checkArgument(!this.mappingsLoaded);
      Via.getPlatform().getLogger().info("Finished mapping loading, shutting down loader executor.");
      this.mappingsLoaded = true;
      this.mappingLoaderExecutor.shutdown();
      this.mappingLoaderExecutor = null;
      this.mappingLoaderFutures.clear();
      this.mappingLoaderFutures = null;
      MappingDataLoader.INSTANCE.clearCache();
   }

   private Function mappingLoaderThrowable(Class protocolClass) {
      return (throwable) -> {
         Logger var10000 = Via.getPlatform().getLogger();
         Level var10001 = Level.SEVERE;
         String var4 = protocolClass.getSimpleName();
         var10000.log(var10001, "Error during mapping loading of " + var4, throwable);
         return null;
      };
   }
}
