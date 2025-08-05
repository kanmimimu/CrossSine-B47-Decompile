package com.viaversion.viaversion.protocols.v1_16_4to1_17.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public final class ItemPacketRewriter1_17 extends ItemRewriter {
   public ItemPacketRewriter1_17(Protocol1_16_4To1_17 protocol) {
      super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
   }

   public void registerPackets() {
      this.registerCooldown(ClientboundPackets1_16_2.COOLDOWN);
      this.registerSetContent(ClientboundPackets1_16_2.CONTAINER_SET_CONTENT);
      this.registerMerchantOffers(ClientboundPackets1_16_2.MERCHANT_OFFERS);
      this.registerSetSlot(ClientboundPackets1_16_2.CONTAINER_SET_SLOT);
      this.registerAdvancements(ClientboundPackets1_16_2.UPDATE_ADVANCEMENTS);
      this.registerSetEquipment(ClientboundPackets1_16_2.SET_EQUIPMENT);
      this.registerLevelParticles(ClientboundPackets1_16_2.LEVEL_PARTICLES, Types.DOUBLE);
      (new RecipeRewriter(this.protocol)).register(ClientboundPackets1_16_2.UPDATE_RECIPES);
      this.registerSetCreativeModeSlot(ServerboundPackets1_17.SET_CREATIVE_MODE_SLOT);
      ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_17.EDIT_BOOK, (wrapper) -> this.handleItemToServer(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2)));
      ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_17.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.handler((wrapper) -> wrapper.write(Types.SHORT, Short.valueOf((short)0)));
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int length = (Integer)wrapper.read(Types.VAR_INT);

               for(int i = 0; i < length; ++i) {
                  wrapper.read(Types.SHORT);
                  wrapper.read(Types.ITEM1_13_2);
               }

               Item item = (Item)wrapper.read(Types.ITEM1_13_2);
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (action != 5 && action != 1) {
                  ItemPacketRewriter1_17.this.handleItemToServer(wrapper.user(), item);
               } else {
                  item = null;
               }

               wrapper.write(Types.ITEM1_13_2, item);
            });
         }
      });
      ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.CONTAINER_ACK, (ClientboundPacketType)null, (wrapper) -> {
         short inventoryId = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         short confirmationId = (Short)wrapper.read(Types.SHORT);
         boolean accepted = (Boolean)wrapper.read(Types.BOOLEAN);
         if (!accepted) {
            int id = 1073741824 | inventoryId << 16 | confirmationId & '\uffff';
            PacketWrapper pingPacket = wrapper.create(ClientboundPackets1_17.PING);
            pingPacket.write(Types.INT, id);
            pingPacket.send(Protocol1_16_4To1_17.class);
         }

         wrapper.cancel();
      });
      ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_17.PONG, (ServerboundPacketType)null, (wrapper) -> {
         int id = (Integer)wrapper.read(Types.INT);
         if ((id & 1073741824) != 0) {
            short inventoryId = (short)(id >> 16 & 255);
            short confirmationId = (short)(id & '\uffff');
            PacketWrapper packet = wrapper.create(ServerboundPackets1_16_2.CONTAINER_ACK);
            packet.write(Types.UNSIGNED_BYTE, inventoryId);
            packet.write(Types.SHORT, confirmationId);
            packet.write(Types.BOOLEAN, true);
            packet.sendToServer(Protocol1_16_4To1_17.class);
         }

         wrapper.cancel();
      });
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag tag = item.tag();
         if (item.identifier() == 733) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            if (tag.getNumberTag("map") == null) {
               tag.put("map", new IntTag(0));
            }
         }

         item.setIdentifier(((Protocol1_16_4To1_17)this.protocol).getMappingData().getNewItemId(item.identifier()));
         return item;
      }
   }
}
