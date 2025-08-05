package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ConnectionManagerImpl implements ConnectionManager {
   protected final Map clients = new ConcurrentHashMap();
   protected final Set connections = Collections.newSetFromMap(new ConcurrentHashMap());

   public void onLoginSuccess(UserConnection connection) {
      Objects.requireNonNull(connection, "connection is null!");
      Channel channel = connection.getChannel();
      if (channel == null || channel.isOpen()) {
         boolean newlyAdded = this.connections.add(connection);
         if (this.isFrontEnd(connection)) {
            UUID id = connection.getProtocolInfo().getUuid();
            UserConnection previous = (UserConnection)this.clients.put(id, connection);
            if (previous != null && previous != connection) {
               Via.getPlatform().getLogger().warning("Duplicate UUID on frontend connection! (" + id + ")");
            }
         }

         if (channel != null) {
            if (!channel.isOpen()) {
               this.onDisconnect(connection);
            } else if (newlyAdded) {
               channel.closeFuture().addListener((ChannelFutureListener)(future) -> this.onDisconnect(connection));
            }
         }

      }
   }

   public void onDisconnect(UserConnection connection) {
      Objects.requireNonNull(connection, "connection is null!");
      this.connections.remove(connection);
      if (this.isFrontEnd(connection)) {
         UUID id = connection.getProtocolInfo().getUuid();
         this.clients.remove(id);
      }

      connection.clearStoredObjects();
   }

   public Map getConnectedClients() {
      return Collections.unmodifiableMap(this.clients);
   }

   public @Nullable UserConnection getConnectedClient(UUID clientIdentifier) {
      return (UserConnection)this.clients.get(clientIdentifier);
   }

   public @Nullable UUID getConnectedClientId(UserConnection connection) {
      if (connection.getProtocolInfo() == null) {
         return null;
      } else {
         UUID uuid = connection.getProtocolInfo().getUuid();
         UserConnection client = (UserConnection)this.clients.get(uuid);
         return connection.equals(client) ? uuid : null;
      }
   }

   public Set getConnections() {
      return Collections.unmodifiableSet(this.connections);
   }

   public boolean isClientConnected(UUID playerId) {
      return this.clients.containsKey(playerId);
   }
}
