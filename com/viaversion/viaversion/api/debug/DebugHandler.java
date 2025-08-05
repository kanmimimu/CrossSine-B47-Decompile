package com.viaversion.viaversion.api.debug;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import java.util.logging.Level;

public interface DebugHandler {
   boolean enabled();

   void setEnabled(boolean var1);

   @Beta
   void addPacketTypeNameToLog(String var1);

   @Beta
   void addPacketTypeToLog(PacketType var1);

   @Beta
   boolean removePacketTypeNameToLog(String var1);

   @Beta
   void clearPacketTypesToLog();

   @Beta
   boolean logPostPacketTransform();

   @Beta
   void setLogPostPacketTransform(boolean var1);

   boolean shouldLog(PacketWrapper var1, Direction var2);

   @Beta
   default void enableAndLogIds(PacketType... packetTypes) {
      this.setEnabled(true);

      for(PacketType packetType : packetTypes) {
         this.addPacketTypeToLog(packetType);
      }

   }

   default void error(String error, Throwable t) {
      if (!Via.getConfig().isSuppressConversionWarnings() || this.enabled()) {
         Via.getPlatform().getLogger().log(Level.SEVERE, error, t);
      }

   }
}
