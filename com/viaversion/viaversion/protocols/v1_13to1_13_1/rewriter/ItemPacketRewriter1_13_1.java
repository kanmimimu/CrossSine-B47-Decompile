package com.viaversion.viaversion.protocols.v1_13to1_13_1.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public class ItemPacketRewriter1_13_1 extends ItemRewriter {
   public ItemPacketRewriter1_13_1(Protocol1_13To1_13_1 protocol) {
      super(protocol, Types.ITEM1_13, Types.ITEM1_13_SHORT_ARRAY);
   }

   public void registerPackets() {
      this.registerSetSlot(ClientboundPackets1_13.CONTAINER_SET_SLOT);
      this.registerSetContent(ClientboundPackets1_13.CONTAINER_SET_CONTENT);
      this.registerAdvancements(ClientboundPackets1_13.UPDATE_ADVANCEMENTS);
      this.registerCooldown(ClientboundPackets1_13.COOLDOWN);
      ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               String channel = Key.namespaced((String)wrapper.get(Types.STRING, 0));
               if (channel.equals("minecraft:trader_list")) {
                  wrapper.passthrough(Types.INT);
                  int size = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

                  for(int i = 0; i < size; ++i) {
                     ItemPacketRewriter1_13_1.this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13));
                     ItemPacketRewriter1_13_1.this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13));
                     boolean secondItem = (Boolean)wrapper.passthrough(Types.BOOLEAN);
                     if (secondItem) {
                        ItemPacketRewriter1_13_1.this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13));
                     }

                     wrapper.passthrough(Types.BOOLEAN);
                     wrapper.passthrough(Types.INT);
                     wrapper.passthrough(Types.INT);
                  }
               }

            });
         }
      });
      this.registerSetEquippedItem(ClientboundPackets1_13.SET_EQUIPPED_ITEM);
      RecipeRewriter<ClientboundPackets1_13> recipeRewriter = new RecipeRewriter(this.protocol) {
         protected Type itemType() {
            return Types.ITEM1_13;
         }

         protected Type itemArrayType() {
            return Types.ITEM1_13_ARRAY;
         }
      };
      ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.UPDATE_RECIPES, (wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.STRING);
            String type = Key.stripMinecraftNamespace((String)wrapper.passthrough(Types.STRING));
            recipeRewriter.handleRecipeType(wrapper, type);
         }

      });
      this.registerContainerClick(ServerboundPackets1_13.CONTAINER_CLICK);
      this.registerSetCreativeModeSlot(ServerboundPackets1_13.SET_CREATIVE_MODE_SLOT);
      this.registerLevelParticles(ClientboundPackets1_13.LEVEL_PARTICLES, Types.FLOAT);
   }
}
