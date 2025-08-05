package com.viaversion.viaversion.protocols.v1_13_1to1_13_2;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_1to1_13_2.rewriter.EntityPacketRewriter1_13_2;
import com.viaversion.viaversion.protocols.v1_13_1to1_13_2.rewriter.ItemPacketRewriter1_13_2;
import com.viaversion.viaversion.protocols.v1_13_1to1_13_2.rewriter.WorldPacketRewriter1_13_2;

public class Protocol1_13_1To1_13_2 extends AbstractProtocol {
   public Protocol1_13_1To1_13_2() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
   }

   protected void registerPackets() {
      ItemPacketRewriter1_13_2.register(this);
      WorldPacketRewriter1_13_2.register(this);
      EntityPacketRewriter1_13_2.register(this);
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers() {
         public void register() {
            this.map(Types.ITEM1_13_2, Types.ITEM1_13);
         }
      });
      this.registerClientbound(ClientboundPackets1_13.UPDATE_ADVANCEMENTS, (wrapper) -> {
         wrapper.passthrough(Types.BOOLEAN);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.OPTIONAL_STRING);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               wrapper.passthrough(Types.COMPONENT);
               wrapper.passthrough(Types.COMPONENT);
               Item icon = (Item)wrapper.read(Types.ITEM1_13);
               wrapper.write(Types.ITEM1_13_2, icon);
               wrapper.passthrough(Types.VAR_INT);
               int flags = (Integer)wrapper.passthrough(Types.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Types.STRING);
               }

               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.FLOAT);
            }

            wrapper.passthrough(Types.STRING_ARRAY);
            int arrayLength = (Integer)wrapper.passthrough(Types.VAR_INT);

            for(int array = 0; array < arrayLength; ++array) {
               wrapper.passthrough(Types.STRING_ARRAY);
            }
         }

      });
   }
}
