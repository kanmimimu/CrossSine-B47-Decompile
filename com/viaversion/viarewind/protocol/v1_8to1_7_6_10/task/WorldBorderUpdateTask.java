package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.task;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.WorldBorderEmulator;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.logging.Level;

public class WorldBorderUpdateTask implements Runnable {
   public static final int VIEW_DISTANCE = 16;

   public void run() {
      for(UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
         WorldBorderEmulator worldBorderEmulatorTracker = (WorldBorderEmulator)connection.get(WorldBorderEmulator.class);
         if (worldBorderEmulatorTracker.isInit()) {
            PlayerSessionStorage playerSession = (PlayerSessionStorage)connection.get(PlayerSessionStorage.class);
            double radius = worldBorderEmulatorTracker.getSize() / (double)2.0F;

            for(WorldBorderEmulator.Side side : WorldBorderEmulator.Side.values()) {
               double d;
               double pos;
               double center;
               if (side.modX != 0) {
                  pos = playerSession.getPosZ();
                  center = worldBorderEmulatorTracker.getZ();
                  d = Math.abs(worldBorderEmulatorTracker.getX() + radius * (double)side.modX - playerSession.getPosX());
               } else {
                  center = worldBorderEmulatorTracker.getX();
                  pos = playerSession.getPosX();
                  d = Math.abs(worldBorderEmulatorTracker.getZ() + radius * (double)side.modZ - playerSession.getPosZ());
               }

               if (!(d >= (double)16.0F)) {
                  double r = Math.sqrt((double)256.0F - d * d);
                  double minH = Math.ceil(pos - r);
                  double maxH = Math.floor(pos + r);
                  double minV = Math.ceil(playerSession.getPosY() - r);
                  double maxV = Math.floor(playerSession.getPosY() + r);
                  if (minH < center - radius) {
                     minH = Math.ceil(center - radius);
                  }

                  if (maxH > center + radius) {
                     maxH = Math.floor(center + radius);
                  }

                  if (minV < (double)0.0F) {
                     minV = (double)0.0F;
                  }

                  double centerH = (minH + maxH) / (double)2.0F;
                  double centerV = (minV + maxV) / (double)2.0F;
                  double particleOffset = (double)2.5F;
                  PacketWrapper spawnParticle = PacketWrapper.create(ClientboundPackets1_8.LEVEL_PARTICLES, (UserConnection)connection);
                  spawnParticle.write(Types.STRING, ViaRewind.getConfig().getWorldBorderParticle());
                  spawnParticle.write(Types.FLOAT, (float)(side.modX != 0 ? worldBorderEmulatorTracker.getX() + radius * (double)side.modX : centerH));
                  spawnParticle.write(Types.FLOAT, (float)centerV);
                  spawnParticle.write(Types.FLOAT, (float)(side.modX == 0 ? worldBorderEmulatorTracker.getZ() + radius * (double)side.modZ : centerH));
                  spawnParticle.write(Types.FLOAT, (float)(side.modX != 0 ? (double)0.0F : (maxH - minH) / particleOffset));
                  spawnParticle.write(Types.FLOAT, (float)((maxV - minV) / particleOffset));
                  spawnParticle.write(Types.FLOAT, (float)(side.modX == 0 ? (double)0.0F : (maxH - minH) / particleOffset));
                  spawnParticle.write(Types.FLOAT, 0.0F);
                  spawnParticle.write(Types.INT, (int)Math.floor((maxH - minH) * (maxV - minV) * (double)0.5F));

                  try {
                     spawnParticle.send(Protocol1_8To1_7_6_10.class);
                  } catch (Exception e) {
                     ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send world border particle", e);
                  }
               }
            }
         }
      }

   }
}
