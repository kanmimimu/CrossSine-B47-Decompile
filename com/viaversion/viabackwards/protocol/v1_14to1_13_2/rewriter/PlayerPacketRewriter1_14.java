package com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter;

import com.viaversion.viabackwards.protocol.v1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage.DifficultyStorage;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;

public class PlayerPacketRewriter1_14 extends RewriterBase {
   public PlayerPacketRewriter1_14(Protocol1_14To1_13_2 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.CHANGE_DIFFICULTY, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.read(Types.BOOLEAN);
            this.handler((wrapper) -> {
               byte difficulty = ((Short)wrapper.get(Types.UNSIGNED_BYTE, 0)).byteValue();
               ((DifficultyStorage)wrapper.user().get(DifficultyStorage.class)).setDifficulty(difficulty);
            });
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_SIGN_EDITOR, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.BLOCK_ENTITY_TAG_QUERY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BLOCK_POSITION1_8, Types.BLOCK_POSITION1_14);
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.PLAYER_ACTION, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BLOCK_POSITION1_8, Types.BLOCK_POSITION1_14);
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int type = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (type == 0) {
                  wrapper.passthrough(Types.STRING);
               } else if (type == 1) {
                  wrapper.passthrough(Types.BOOLEAN);
                  wrapper.passthrough(Types.BOOLEAN);
                  wrapper.passthrough(Types.BOOLEAN);
                  wrapper.passthrough(Types.BOOLEAN);
                  wrapper.write(Types.BOOLEAN, false);
                  wrapper.write(Types.BOOLEAN, false);
                  wrapper.write(Types.BOOLEAN, false);
                  wrapper.write(Types.BOOLEAN, false);
               }

            });
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.SET_COMMAND_BLOCK, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, Types.BLOCK_POSITION1_14);
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.SET_STRUCTURE_BLOCK, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, Types.BLOCK_POSITION1_14);
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.SIGN_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, Types.BLOCK_POSITION1_14);
         }
      });
      ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.USE_ITEM_ON, (wrapper) -> {
         BlockPosition position = (BlockPosition)wrapper.read(Types.BLOCK_POSITION1_8);
         int face = (Integer)wrapper.read(Types.VAR_INT);
         int hand = (Integer)wrapper.read(Types.VAR_INT);
         float x = (Float)wrapper.read(Types.FLOAT);
         float y = (Float)wrapper.read(Types.FLOAT);
         float z = (Float)wrapper.read(Types.FLOAT);
         wrapper.write(Types.VAR_INT, hand);
         wrapper.write(Types.BLOCK_POSITION1_14, position);
         wrapper.write(Types.VAR_INT, face);
         wrapper.write(Types.FLOAT, x);
         wrapper.write(Types.FLOAT, y);
         wrapper.write(Types.FLOAT, z);
         wrapper.write(Types.BOOLEAN, false);
      });
   }
}
