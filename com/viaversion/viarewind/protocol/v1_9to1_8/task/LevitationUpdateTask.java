package com.viaversion.viarewind.protocol.v1_9to1_8.task;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.LevitationStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.logging.Level;

public class LevitationUpdateTask implements Runnable {
   public void run() {
      if (ViaRewind.getConfig().emulateLevitationEffect()) {
         for(UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            LevitationStorage levitation = (LevitationStorage)connection.get(LevitationStorage.class);
            if (levitation.isActive()) {
               PacketWrapper velocityPacket = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_MOTION, (UserConnection)connection);
               velocityPacket.write(Types.VAR_INT, connection.getEntityTracker(Protocol1_9To1_8.class).clientEntityId());
               velocityPacket.write(Types.SHORT, Short.valueOf((short)0));
               velocityPacket.write(Types.SHORT, (short)((levitation.getAmplifier() + 1) * 360));
               velocityPacket.write(Types.SHORT, Short.valueOf((short)0));

               try {
                  velocityPacket.scheduleSend(Protocol1_9To1_8.class);
               } catch (Exception e) {
                  ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send levitation packet", e);
               }
            }
         }

      }
   }
}
