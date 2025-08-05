package com.viaversion.viabackwards.protocol.v1_11to1_10.rewriter;

import com.viaversion.viabackwards.protocol.v1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.util.ComponentUtil;

public class PlayerPacketRewriter1_11 {
   static final ValueTransformer TO_NEW_FLOAT;

   public static void register(Protocol1_11To1_10 protocol) {
      protocol.registerClientbound(ClientboundPackets1_9_3.SET_TITLES, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (action == 2) {
                  JsonElement message = (JsonElement)wrapper.read(Types.COMPONENT);
                  wrapper.clearPacket();
                  wrapper.setPacketType(ClientboundPackets1_9_3.CHAT);
                  String legacy = ComponentUtil.jsonToLegacy(message);
                  JsonElement var4 = new JsonObject();
                  ((JsonElement)var4).getAsJsonObject().addProperty("text", legacy);
                  wrapper.write(Types.COMPONENT, var4);
                  wrapper.write(Types.BYTE, (byte)2);
               } else if (action > 2) {
                  wrapper.set(Types.VAR_INT, 0, action - 1);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9_3.TAKE_ITEM_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> wrapper.read(Types.VAR_INT));
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9_3.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.UNSIGNED_BYTE, PlayerPacketRewriter1_11.TO_NEW_FLOAT);
            this.map(Types.UNSIGNED_BYTE, PlayerPacketRewriter1_11.TO_NEW_FLOAT);
            this.map(Types.UNSIGNED_BYTE, PlayerPacketRewriter1_11.TO_NEW_FLOAT);
         }
      });
   }

   static {
      TO_NEW_FLOAT = new ValueTransformer(Types.FLOAT) {
         public Float transform(PacketWrapper wrapper, Short inputValue) {
            return (float)inputValue / 16.0F;
         }
      };
   }
}
