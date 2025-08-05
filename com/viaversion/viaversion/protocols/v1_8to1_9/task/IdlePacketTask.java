package com.viaversion.viaversion.protocols.v1_8to1_9.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.MovementTracker;

public class IdlePacketTask implements Runnable {
   public void run() {
      for(UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
         ProtocolInfo protocolInfo = info.getProtocolInfo();
         if (protocolInfo != null && protocolInfo.getPipeline().contains(Protocol1_8To1_9.class)) {
            MovementTracker movementTracker = (MovementTracker)info.get(MovementTracker.class);
            if (movementTracker != null) {
               long nextIdleUpdate = movementTracker.getNextIdlePacket();
               if (nextIdleUpdate <= System.currentTimeMillis() && info.getChannel().isOpen()) {
                  ((MovementTransmitterProvider)Via.getManager().getProviders().get(MovementTransmitterProvider.class)).sendPlayer(info);
               }
            }
         }
      }

   }
}
