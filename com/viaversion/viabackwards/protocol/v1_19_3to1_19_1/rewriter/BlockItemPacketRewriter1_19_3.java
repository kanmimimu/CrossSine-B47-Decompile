package com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.rewriter;

import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ServerboundPackets1_19_1;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class BlockItemPacketRewriter1_19_3 extends BackwardsItemRewriter {
   public BlockItemPacketRewriter1_19_3(Protocol1_19_3To1_19_1 protocol) {
      super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
   }

   protected void registerPackets() {
      BlockRewriter<ClientboundPackets1_19_3> blockRewriter = BlockRewriter.for1_14(this.protocol);
      blockRewriter.registerBlockEvent(ClientboundPackets1_19_3.BLOCK_EVENT);
      blockRewriter.registerBlockUpdate(ClientboundPackets1_19_3.BLOCK_UPDATE);
      blockRewriter.registerSectionBlocksUpdate(ClientboundPackets1_19_3.SECTION_BLOCKS_UPDATE);
      blockRewriter.registerLevelEvent(ClientboundPackets1_19_3.LEVEL_EVENT, 1010, 2001);
      blockRewriter.registerLevelChunk1_19(ClientboundPackets1_19_3.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_18::new);
      blockRewriter.registerBlockEntityData(ClientboundPackets1_19_3.BLOCK_ENTITY_DATA);
      this.registerCooldown(ClientboundPackets1_19_3.COOLDOWN);
      this.registerSetContent1_17_1(ClientboundPackets1_19_3.CONTAINER_SET_CONTENT);
      this.registerSetSlot1_17_1(ClientboundPackets1_19_3.CONTAINER_SET_SLOT);
      this.registerSetEquipment(ClientboundPackets1_19_3.SET_EQUIPMENT);
      this.registerAdvancements(ClientboundPackets1_19_3.UPDATE_ADVANCEMENTS);
      this.registerContainerClick1_17_1(ServerboundPackets1_19_1.CONTAINER_CLICK);
      this.registerMerchantOffers1_19(ClientboundPackets1_19_3.MERCHANT_OFFERS);
      this.registerSetCreativeModeSlot(ServerboundPackets1_19_1.SET_CREATIVE_MODE_SLOT);
      this.registerContainerSetData(ClientboundPackets1_19_3.CONTAINER_SET_DATA);
      this.registerLevelParticles1_19(ClientboundPackets1_19_3.LEVEL_PARTICLES);
      ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_3.EXPLODE, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE, Types.FLOAT);
            this.map(Types.DOUBLE, Types.FLOAT);
            this.map(Types.DOUBLE, Types.FLOAT);
         }
      });
      RecipeRewriter<ClientboundPackets1_19_3> recipeRewriter = new RecipeRewriter(this.protocol);
      ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_3.UPDATE_RECIPES, (wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            String type = Key.stripMinecraftNamespace((String)wrapper.passthrough(Types.STRING));
            wrapper.passthrough(Types.STRING);
            switch (type) {
               case "crafting_shapeless":
                  wrapper.passthrough(Types.STRING);
                  wrapper.read(Types.VAR_INT);
                  int ingredients = (Integer)wrapper.passthrough(Types.VAR_INT);

                  for(int j = 0; j < ingredients; ++j) {
                     Item[] items = (Item[])wrapper.passthrough(Types.ITEM1_13_2_ARRAY);

                     for(Item item : items) {
                        this.handleItemToClient(wrapper.user(), item);
                     }
                  }

                  this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
                  break;
               case "crafting_shaped":
                  int ingredients = (Integer)wrapper.passthrough(Types.VAR_INT) * (Integer)wrapper.passthrough(Types.VAR_INT);
                  wrapper.passthrough(Types.STRING);
                  wrapper.read(Types.VAR_INT);

                  for(int j = 0; j < ingredients; ++j) {
                     Item[] items = (Item[])wrapper.passthrough(Types.ITEM1_13_2_ARRAY);

                     for(Item item : items) {
                        this.handleItemToClient(wrapper.user(), item);
                     }
                  }

                  this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
                  break;
               case "smelting":
               case "campfire_cooking":
               case "blasting":
               case "smoking":
                  wrapper.passthrough(Types.STRING);
                  wrapper.read(Types.VAR_INT);
                  Item[] items = (Item[])wrapper.passthrough(Types.ITEM1_13_2_ARRAY);

                  for(Item item : items) {
                     this.handleItemToClient(wrapper.user(), item);
                  }

                  this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
                  wrapper.passthrough(Types.FLOAT);
                  wrapper.passthrough(Types.VAR_INT);
                  break;
               case "crafting_special_armordye":
               case "crafting_special_bookcloning":
               case "crafting_special_mapcloning":
               case "crafting_special_mapextending":
               case "crafting_special_firework_rocket":
               case "crafting_special_firework_star":
               case "crafting_special_firework_star_fade":
               case "crafting_special_tippedarrow":
               case "crafting_special_bannerduplicate":
               case "crafting_special_shielddecoration":
               case "crafting_special_shulkerboxcoloring":
               case "crafting_special_suspiciousstew":
               case "crafting_special_repairitem":
                  wrapper.read(Types.VAR_INT);
                  break;
               default:
                  recipeRewriter.handleRecipeType(wrapper, type);
            }
         }

      });
   }
}
