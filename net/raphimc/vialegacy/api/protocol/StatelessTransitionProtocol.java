package net.raphimc.vialegacy.api.protocol;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;

public abstract class StatelessTransitionProtocol extends StatelessProtocol {
   public StatelessTransitionProtocol(Class unmappedClientboundPacketType, Class mappedClientboundPacketType, Class mappedServerboundPacketType, Class unmappedServerboundPacketType) {
      super(unmappedClientboundPacketType, mappedClientboundPacketType, mappedServerboundPacketType, unmappedServerboundPacketType);
   }

   public void registerServerboundTransition(ServerboundPacketType unmappedPacketType, ServerboundPacketType mappedPacketType, PacketHandler handler) {
      this.registerServerbound(unmappedPacketType.state(), unmappedPacketType.getId(), mappedPacketType != null ? mappedPacketType.getId() : -1, (wrapper) -> {
         wrapper.setPacketType(mappedPacketType);
         if (handler != null) {
            handler.handle(wrapper);
         }

      });
   }

   public void registerClientboundTransition(ClientboundPacketType unmappedPacketType, Object... handlers) {
      if (handlers.length % 2 != 0) {
         throw new IllegalArgumentException("handlers.length % 2 != 0");
      } else {
         this.registerClientbound(unmappedPacketType.state(), unmappedPacketType.getId(), -1, (wrapper) -> {
            State currentState = wrapper.user().getProtocolInfo().getServerState();
            int i = 0;

            while(true) {
               if (i >= handlers.length) {
                  throw new IllegalStateException("No handler found for packet " + unmappedPacketType + " in state " + currentState);
               }

               Object patt2681$temp = handlers[i];
               if (patt2681$temp instanceof State) {
                  State state = (State)patt2681$temp;
                  if (state == currentState) {
                     break;
                  }
               } else {
                  ClientboundPacketType mappedPacketType = (ClientboundPacketType)handlers[i];
                  if (mappedPacketType.state() == currentState) {
                     wrapper.setPacketType(mappedPacketType);
                     break;
                  }
               }

               i += 2;
            }

            PacketHandler handler = (PacketHandler)handlers[i + 1];
            if (handler != null) {
               handler.handle(wrapper);
            }

         });
      }
   }
}
