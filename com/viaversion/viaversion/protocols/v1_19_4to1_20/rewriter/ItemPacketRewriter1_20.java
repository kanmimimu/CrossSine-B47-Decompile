package com.viaversion.viaversion.protocols.v1_19_4to1_20.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_19_4to1_20.Protocol1_19_4To1_20;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;

public final class ItemPacketRewriter1_20 extends ItemRewriter {
   public ItemPacketRewriter1_20(Protocol1_19_4To1_20 protocol) {
      super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
   }

   public void registerPackets() {
      final BlockRewriter<ClientboundPackets1_19_4> blockRewriter = BlockRewriter.for1_14(this.protocol);
      blockRewriter.registerBlockEvent(ClientboundPackets1_19_4.BLOCK_EVENT);
      blockRewriter.registerBlockUpdate(ClientboundPackets1_19_4.BLOCK_UPDATE);
      blockRewriter.registerLevelEvent(ClientboundPackets1_19_4.LEVEL_EVENT, 1010, 2001);
      blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
      this.registerOpenScreen(ClientboundPackets1_19_4.OPEN_SCREEN);
      this.registerCooldown(ClientboundPackets1_19_4.COOLDOWN);
      this.registerSetContent1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_CONTENT);
      this.registerSetSlot1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_SLOT);
      this.registerSetEquipment(ClientboundPackets1_19_4.SET_EQUIPMENT);
      this.registerContainerClick1_17_1(ServerboundPackets1_19_4.CONTAINER_CLICK);
      this.registerMerchantOffers1_19(ClientboundPackets1_19_4.MERCHANT_OFFERS);
      this.registerSetCreativeModeSlot(ServerboundPackets1_19_4.SET_CREATIVE_MODE_SLOT);
      this.registerContainerSetData(ClientboundPackets1_19_4.CONTAINER_SET_DATA);
      this.registerLevelParticles1_19(ClientboundPackets1_19_4.LEVEL_PARTICLES);
      ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_ADVANCEMENTS, (wrapper) -> {
         wrapper.passthrough(Types.BOOLEAN);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.OPTIONAL_STRING);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               wrapper.passthrough(Types.COMPONENT);
               wrapper.passthrough(Types.COMPONENT);
               this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
               wrapper.passthrough(Types.VAR_INT);
               int flags = (Integer)wrapper.passthrough(Types.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Types.STRING);
               }

               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.FLOAT);
            }

            wrapper.passthrough(Types.STRING_ARRAY);
            int requirements = (Integer)wrapper.passthrough(Types.VAR_INT);

            for(int array = 0; array < requirements; ++array) {
               wrapper.passthrough(Types.STRING_ARRAY);
            }

            wrapper.write(Types.BOOLEAN, false);
         }

      });
      ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, (wrapper) -> {
         wrapper.passthrough(Types.BLOCK_POSITION1_14);
         wrapper.write(Types.BOOLEAN, true);
      });
      ((Protocol1_19_4To1_20)this.protocol).registerServerbound(ServerboundPackets1_19_4.SIGN_UPDATE, (wrapper) -> {
         wrapper.passthrough(Types.BLOCK_POSITION1_14);
         boolean frontText = (Boolean)wrapper.read(Types.BOOLEAN);
         if (!frontText) {
            wrapper.cancel();
         }

      });
      ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.LEVEL_CHUNK_WITH_LIGHT, new PacketHandlers() {
         protected void register() {
            this.handler(blockRewriter.chunkHandler1_19(ChunkType1_18::new, (user, blockEntity) -> ItemPacketRewriter1_20.this.handleBlockEntity(blockEntity)));
            this.read(Types.BOOLEAN);
         }
      });
      ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.LIGHT_UPDATE, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.VAR_INT);
         wrapper.read(Types.BOOLEAN);
      });
      ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.SECTION_BLOCKS_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.LONG);
            this.read(Types.BOOLEAN);
            this.handler((wrapper) -> {
               for(BlockChangeRecord record : (BlockChangeRecord[])wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                  record.setBlockId(((Protocol1_19_4To1_20)ItemPacketRewriter1_20.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
               }

            });
         }
      });
      RecipeRewriter<ClientboundPackets1_19_4> recipeRewriter = new RecipeRewriter1_19_4(this.protocol);
      ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_RECIPES, (wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);
         int newSize = size;

         for(int i = 0; i < size; ++i) {
            String type = (String)wrapper.read(Types.STRING);
            String cutType = Key.stripMinecraftNamespace(type);
            if (cutType.equals("smithing")) {
               --newSize;
               wrapper.read(Types.STRING);
               wrapper.read(Types.ITEM1_13_2_ARRAY);
               wrapper.read(Types.ITEM1_13_2_ARRAY);
               wrapper.read(Types.ITEM1_13_2);
            } else {
               wrapper.write(Types.STRING, type);
               wrapper.passthrough(Types.STRING);
               recipeRewriter.handleRecipeType(wrapper, cutType);
            }
         }

         wrapper.set(Types.VAR_INT, 0, newSize);
      });
   }

   void handleBlockEntity(BlockEntity blockEntity) {
      if (blockEntity.typeId() == 7 || blockEntity.typeId() == 8) {
         CompoundTag tag = blockEntity.tag();
         CompoundTag frontText = new CompoundTag();
         tag.put("front_text", frontText);
         ListTag<StringTag> messages = new ListTag(StringTag.class);

         for(int i = 1; i < 5; ++i) {
            Tag text = tag.remove("Text" + i);
            messages.add(text instanceof StringTag ? (StringTag)text : new StringTag(ComponentUtil.emptyJsonComponentString()));
         }

         frontText.put("messages", messages);
         ListTag<StringTag> filteredMessages = new ListTag(StringTag.class);

         for(int i = 1; i < 5; ++i) {
            Tag text = tag.remove("FilteredText" + i);
            filteredMessages.add(text instanceof StringTag ? (StringTag)text : (StringTag)messages.get(i - 1));
         }

         if (!filteredMessages.equals(messages)) {
            frontText.put("filtered_messages", filteredMessages);
         }

         Tag color = tag.remove("Color");
         if (color != null) {
            frontText.put("color", color);
         }

         Tag glowing = tag.remove("GlowingText");
         if (glowing != null) {
            frontText.put("has_glowing_text", glowing);
         }

      }
   }
}
