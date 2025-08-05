package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Types;

public class AttributeRewriter {
   private final Protocol protocol;

   public AttributeRewriter(Protocol protocol) {
      this.protocol = protocol;
   }

   public void register1_21(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);
         int newSize = size;

         for(int i = 0; i < size; ++i) {
            int attributeId = (Integer)wrapper.read(Types.VAR_INT);
            int mappedId = this.protocol.getMappingData().getNewAttributeId(attributeId);
            if (mappedId == -1) {
               --newSize;
               wrapper.read(Types.DOUBLE);
               int modifierSize = (Integer)wrapper.read(Types.VAR_INT);

               for(int j = 0; j < modifierSize; ++j) {
                  wrapper.read(Types.STRING);
                  wrapper.read(Types.DOUBLE);
                  wrapper.read(Types.BYTE);
               }
            } else {
               wrapper.write(Types.VAR_INT, mappedId);
               wrapper.passthrough(Types.DOUBLE);
               int modifierSize = (Integer)wrapper.passthrough(Types.VAR_INT);

               for(int j = 0; j < modifierSize; ++j) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.DOUBLE);
                  wrapper.passthrough(Types.BYTE);
               }
            }
         }

         if (size != newSize) {
            wrapper.set(Types.VAR_INT, 1, newSize);
         }

      }));
   }
}
