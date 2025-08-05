package com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5;

import com.viaversion.viarewind.api.type.version.Types1_7_6_10;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ServerboundPackets1_7_2_5;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Protocol1_7_6_10To1_7_2_5 extends AbstractProtocol {
   public static final ValueTransformer REMOVE_DASHES;

   public Protocol1_7_6_10To1_7_2_5() {
      super(ClientboundPackets1_7_2_5.class, ClientboundPackets1_7_2_5.class, ServerboundPackets1_7_2_5.class, ServerboundPackets1_7_2_5.class);
   }

   protected void registerPackets() {
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING, Protocol1_7_6_10To1_7_2_5.REMOVE_DASHES);
            this.map(Types.STRING);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2_5.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.STRING, Protocol1_7_6_10To1_7_2_5.REMOVE_DASHES);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               int size = (Integer)wrapper.read(Types.VAR_INT);

               for(int i = 0; i < size; ++i) {
                  wrapper.read(Types.STRING);
                  wrapper.read(Types.STRING);
                  wrapper.read(Types.STRING);
               }

            });
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types1_7_6_10.ENTITY_DATA_LIST);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2_5.SET_PLAYER_TEAM, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               byte mode = (Byte)wrapper.get(Types.BYTE, 0);
               if (mode == 0 || mode == 2) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.BYTE);
               }

               if (mode == 0 || mode == 3 || mode == 4) {
                  List<String> entryList = new ArrayList();
                  int size = (Short)wrapper.read(Types.SHORT);

                  for(int i = 0; i < size; ++i) {
                     entryList.add((String)wrapper.read(Types.STRING));
                  }

                  entryList = (List)entryList.stream().map((it) -> it.length() > 16 ? it.substring(0, 16) : it).distinct().collect(Collectors.toList());
                  wrapper.write(Types.SHORT, (short)entryList.size());

                  for(String entry : entryList) {
                     wrapper.write(Types.STRING, entry);
                  }
               }

            });
         }
      });
   }

   static {
      REMOVE_DASHES = new ValueTransformer(Types.STRING) {
         public String transform(PacketWrapper wrapper, String s) {
            return s.replace("-", "");
         }
      };
   }
}
