package com.viaversion.viaversion.platform;

import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractProtocolDetectorService implements ProtocolDetectorService {
   protected final Object2IntMap detectedProtocolIds = new Object2IntOpenHashMap();
   protected final ReadWriteLock lock = new ReentrantReadWriteLock();

   protected AbstractProtocolDetectorService() {
      this.detectedProtocolIds.defaultReturnValue(-1);
   }

   public ProtocolVersion serverProtocolVersion(String serverName) {
      this.lock.readLock().lock();

      int detectedProtocol;
      try {
         detectedProtocol = this.detectedProtocolIds.getInt(serverName);
      } finally {
         this.lock.readLock().unlock();
      }

      if (detectedProtocol != -1) {
         return ProtocolVersion.getProtocol(detectedProtocol);
      } else {
         Map<String, Integer> servers = this.configuredServers();
         Integer protocol = (Integer)servers.get(serverName);
         if (protocol != null) {
            return ProtocolVersion.getProtocol(protocol);
         } else {
            Integer defaultProtocol = (Integer)servers.get("default");
            return defaultProtocol != null ? ProtocolVersion.getProtocol(defaultProtocol) : this.lowestSupportedProtocolVersion();
         }
      }
   }

   public void setProtocolVersion(String serverName, int protocolVersion) {
      this.lock.writeLock().lock();

      try {
         this.detectedProtocolIds.put(serverName, protocolVersion);
      } finally {
         this.lock.writeLock().unlock();
      }

   }

   public int uncacheProtocolVersion(String serverName) {
      this.lock.writeLock().lock();

      int var2;
      try {
         var2 = this.detectedProtocolIds.removeInt(serverName);
      } finally {
         this.lock.writeLock().unlock();
      }

      return var2;
   }

   public Object2IntMap detectedProtocolVersions() {
      this.lock.readLock().lock();

      Object2IntOpenHashMap var1;
      try {
         var1 = new Object2IntOpenHashMap(this.detectedProtocolIds);
      } finally {
         this.lock.readLock().unlock();
      }

      return var1;
   }

   protected abstract Map configuredServers();

   protected abstract ProtocolVersion lowestSupportedProtocolVersion();
}
