package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.tasks.v1_18_2to1_19.AckSequenceTask;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.provider.AckSequenceProvider;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.storage.SequenceStorage;

public final class BukkitAckSequenceProvider extends AckSequenceProvider {
   private final ViaVersionPlugin plugin;

   public BukkitAckSequenceProvider(ViaVersionPlugin plugin) {
      this.plugin = plugin;
   }

   public void handleSequence(UserConnection connection, int sequence) {
      SequenceStorage sequenceStorage = (SequenceStorage)connection.get(SequenceStorage.class);
      int previousSequence = sequenceStorage.setSequenceId(sequence);
      if (previousSequence == -1) {
         ProtocolVersion serverProtocolVersion = connection.getProtocolInfo().serverProtocolVersion();
         long delay = serverProtocolVersion.newerThan(ProtocolVersion.v1_8) && serverProtocolVersion.olderThan(ProtocolVersion.v1_14) ? 2L : 1L;
         if (this.plugin.isEnabled()) {
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new AckSequenceTask(connection, sequenceStorage), delay);
         }
      }

   }
}
