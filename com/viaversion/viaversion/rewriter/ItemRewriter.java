package com.viaversion.viaversion.rewriter;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemRewriter extends RewriterBase implements com.viaversion.viaversion.api.rewriter.ItemRewriter {
   final Type itemType;
   final Type mappedItemType;
   final Type itemArrayType;
   final Type mappedItemArrayType;
   final Type itemCostType;
   final Type mappedItemCostType;
   final Type optionalItemCostType;
   final Type mappedOptionalItemCostType;
   final Type particleType;
   final Type mappedParticleType;

   public ItemRewriter(Protocol protocol, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType, Type itemCostType, Type optionalItemCostType, Type mappedItemCostType, Type mappedOptionalItemCostType, Type particleType, Type mappedParticleType) {
      super(protocol);
      this.itemType = itemType;
      this.itemArrayType = itemArrayType;
      this.mappedItemType = mappedItemType;
      this.mappedItemArrayType = mappedItemArrayType;
      this.itemCostType = itemCostType;
      this.mappedItemCostType = mappedItemCostType;
      this.optionalItemCostType = optionalItemCostType;
      this.mappedOptionalItemCostType = mappedOptionalItemCostType;
      this.particleType = particleType;
      this.mappedParticleType = mappedParticleType;
   }

   public ItemRewriter(Protocol protocol, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType) {
      this(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, (Type)null, (Type)null, (Type)null, (Type)null, (Type)null, (Type)null);
   }

   public ItemRewriter(Protocol protocol, Type itemType, Type itemArrayType) {
      this(protocol, itemType, itemArrayType, itemType, itemArrayType);
   }

   public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.identifier()));
         }

         return item;
      }
   }

   public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.identifier()));
         }

         return item;
      }
   }

   public void registerSetContent(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.UNSIGNED_BYTE);
         Item[] items = (Item[])wrapper.passthroughAndMap(this.itemArrayType, this.mappedItemArrayType);

         for(int i = 0; i < items.length; ++i) {
            items[i] = this.handleItemToClient(wrapper.user(), items[i]);
         }

      }));
   }

   public void registerSetContent1_17_1(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               Item[] items = (Item[])wrapper.passthroughAndMap(ItemRewriter.this.itemArrayType, ItemRewriter.this.mappedItemArrayType);

               for(int i = 0; i < items.length; ++i) {
                  items[i] = ItemRewriter.this.handleItemToClient(wrapper.user(), items[i]);
               }

               ItemRewriter.this.handleClientboundItem(wrapper);
            });
         }
      }));
   }

   public void registerOpenScreen(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> ItemRewriter.this.handleMenuType(wrapper));
         }
      }));
   }

   public void handleMenuType(PacketWrapper wrapper) {
      int windowType = (Integer)wrapper.read(Types.VAR_INT);
      int mappedId = this.protocol.getMappingData().getMenuMappings().getNewId(windowType);
      if (mappedId == -1) {
         wrapper.cancel();
      } else {
         wrapper.write(Types.VAR_INT, mappedId);
      }
   }

   public void registerSetSlot(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.handler((wrapper) -> ItemRewriter.this.handleClientboundItem(wrapper));
         }
      }));
   }

   public void registerSetSlot1_17_1(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> ItemRewriter.this.handleClientboundItem(wrapper));
         }
      }));
   }

   public void registerSetEquippedItem(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> ItemRewriter.this.handleClientboundItem(wrapper));
         }
      }));
   }

   public void registerSetEquipment(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               byte slot;
               do {
                  slot = (Byte)wrapper.passthrough(Types.BYTE);
                  ItemRewriter.this.handleClientboundItem(wrapper);
               } while(slot < 0);

            });
         }
      }));
   }

   public void registerSetCreativeModeSlot(ServerboundPacketType packetType) {
      this.protocol.registerServerbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.handler((wrapper) -> ItemRewriter.this.handleServerboundItem(wrapper));
         }
      }));
   }

   public void registerContainerClick(ServerboundPacketType packetType) {
      this.protocol.registerServerbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> ItemRewriter.this.handleServerboundItem(wrapper));
         }
      }));
   }

   public void registerContainerClick1_17_1(ServerboundPacketType packetType) {
      this.protocol.registerServerbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int length = (Integer)wrapper.passthrough(Types.VAR_INT);

               for(int i = 0; i < length; ++i) {
                  wrapper.passthrough(Types.SHORT);
                  ItemRewriter.this.handleServerboundItem(wrapper);
               }

               ItemRewriter.this.handleServerboundItem(wrapper);
            });
         }
      }));
   }

   public void registerCooldown(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         int itemId = (Integer)wrapper.read(Types.VAR_INT);
         wrapper.write(Types.VAR_INT, this.protocol.getMappingData().getNewItemId(itemId));
      }));
   }

   public void registerCustomPayloadTradeList(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         protected void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               String channel = (String)wrapper.get(Types.STRING, 0);
               if (channel.equals("MC|TrList")) {
                  ItemRewriter.this.handleTradeList(wrapper);
               }

            });
         }
      }));
   }

   public void handleTradeList(PacketWrapper wrapper) {
      wrapper.passthrough(Types.INT);
      int size = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

      for(int i = 0; i < size; ++i) {
         this.handleClientboundItem(wrapper);
         this.handleClientboundItem(wrapper);
         if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
            this.handleClientboundItem(wrapper);
         }

         wrapper.passthrough(Types.BOOLEAN);
         wrapper.passthrough(Types.INT);
         wrapper.passthrough(Types.INT);
      }

   }

   public void registerMerchantOffers(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int size = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

         for(int i = 0; i < size; ++i) {
            this.handleClientboundItem(wrapper);
            this.handleClientboundItem(wrapper);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               this.handleClientboundItem(wrapper);
            }

            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.INT);
         }

      }));
   }

   public void registerMerchantOffers1_19(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            this.handleClientboundItem(wrapper);
            this.handleClientboundItem(wrapper);
            this.handleClientboundItem(wrapper);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.INT);
         }

      }));
   }

   public void registerMerchantOffers1_20_5(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            Item input = (Item)wrapper.read(this.itemCostType);
            wrapper.write(this.mappedItemCostType, this.handleItemToClient(wrapper.user(), input));
            this.handleClientboundItem(wrapper);
            Item secondInput = (Item)wrapper.read(this.optionalItemCostType);
            if (secondInput != null) {
               this.handleItemToClient(wrapper.user(), secondInput);
            }

            wrapper.write(this.mappedOptionalItemCostType, secondInput);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.INT);
         }

      }));
   }

   public void registerAdvancements(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.BOOLEAN);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.OPTIONAL_STRING);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               JsonElement title = (JsonElement)wrapper.passthrough(Types.COMPONENT);
               JsonElement description = (JsonElement)wrapper.passthrough(Types.COMPONENT);
               com.viaversion.viaversion.api.rewriter.ComponentRewriter componentRewriter = this.protocol.getComponentRewriter();
               if (componentRewriter != null) {
                  componentRewriter.processText(wrapper.user(), title);
                  componentRewriter.processText(wrapper.user(), description);
               }

               this.handleClientboundItem(wrapper);
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

      }));
   }

   public void registerAdvancements1_20_3(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.BOOLEAN);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.OPTIONAL_STRING);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               Tag title = (Tag)wrapper.passthrough(Types.TAG);
               Tag description = (Tag)wrapper.passthrough(Types.TAG);
               com.viaversion.viaversion.api.rewriter.ComponentRewriter componentRewriter = this.protocol.getComponentRewriter();
               if (componentRewriter != null) {
                  componentRewriter.processTag(wrapper.user(), title);
                  componentRewriter.processTag(wrapper.user(), description);
               }

               this.handleClientboundItem(wrapper);
               wrapper.passthrough(Types.VAR_INT);
               int flags = (Integer)wrapper.passthrough(Types.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Types.STRING);
               }

               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.FLOAT);
            }

            int requirements = (Integer)wrapper.passthrough(Types.VAR_INT);

            for(int array = 0; array < requirements; ++array) {
               wrapper.passthrough(Types.STRING_ARRAY);
            }

            wrapper.passthrough(Types.BOOLEAN);
         }

      }));
   }

   public void registerContainerSetData(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               Mappings mappings = ItemRewriter.this.protocol.getMappingData().getEnchantmentMappings();
               if (mappings != null) {
                  short property = (Short)wrapper.passthrough(Types.SHORT);
                  if (property >= 4 && property <= 6) {
                     short enchantmentId = (short)mappings.getNewId((Short)wrapper.read(Types.SHORT));
                     wrapper.write(Types.SHORT, enchantmentId);
                  }

               }
            });
         }
      }));
   }

   public void registerLevelParticles(ClientboundPacketType packetType, final Type coordType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(coordType);
            this.map(coordType);
            this.map(coordType);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler(ItemRewriter.this.levelParticlesHandler());
         }
      }));
   }

   public void registerLevelParticles1_19(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BOOLEAN);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler(ItemRewriter.this.levelParticlesHandler(Types.VAR_INT));
         }
      }));
   }

   public void registerLevelParticles1_20_5(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.BOOLEAN);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               Particle particle = (Particle)wrapper.passthroughAndMap(ItemRewriter.this.particleType, ItemRewriter.this.mappedParticleType);
               ItemRewriter.this.rewriteParticle(wrapper.user(), particle);
            });
         }
      }));
   }

   public void registerExplosion(ClientboundPacketType packetType) {
      SoundRewriter<C> soundRewriter = new SoundRewriter(this.protocol);
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.FLOAT);
         int blocks = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < blocks; ++i) {
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.BYTE);
         }

         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.VAR_INT);
         Particle smallExplosionParticle = (Particle)wrapper.passthroughAndMap(this.particleType, this.mappedParticleType);
         Particle largeExplosionParticle = (Particle)wrapper.passthroughAndMap(this.particleType, this.mappedParticleType);
         this.rewriteParticle(wrapper.user(), smallExplosionParticle);
         this.rewriteParticle(wrapper.user(), largeExplosionParticle);
         soundRewriter.soundHolderHandler().handle(wrapper);
      }));
   }

   public PacketHandler levelParticlesHandler() {
      return this.levelParticlesHandler(Types.INT);
   }

   public PacketHandler levelParticlesHandler(Type idType) {
      return (wrapper) -> {
         int id = (Integer)wrapper.get(idType, 0);
         if (id != -1) {
            ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
            if (mappings.isBlockParticle(id)) {
               int data = (Integer)wrapper.read(Types.VAR_INT);
               wrapper.write(Types.VAR_INT, this.protocol.getMappingData().getNewBlockStateId(data));
            } else if (mappings.isItemParticle(id)) {
               this.handleClientboundItem(wrapper);
            }

            int mappedId = this.protocol.getMappingData().getNewParticleId(id);
            if (mappedId != id) {
               wrapper.set(idType, 0, mappedId);
            }

         }
      };
   }

   void handleClientboundItem(PacketWrapper wrapper) {
      Item item = this.handleItemToClient(wrapper.user(), (Item)wrapper.read(this.itemType));
      wrapper.write(this.mappedItemType, item);
   }

   void handleServerboundItem(PacketWrapper wrapper) {
      Item item = this.handleItemToServer(wrapper.user(), (Item)wrapper.read(this.mappedItemType));
      wrapper.write(this.itemType, item);
   }

   protected void rewriteParticle(UserConnection connection, Particle particle) {
      ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
      int id = particle.id();
      if (mappings.isBlockParticle(id)) {
         Particle.ParticleData<Integer> data = particle.getArgument(0);
         data.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)data.getValue()));
      } else if (mappings.isItemParticle(id)) {
         Particle.ParticleData<Item> data = particle.getArgument(0);
         Item item = this.handleItemToClient(connection, (Item)data.getValue());
         if (this.mappedItemType() != null && this.itemType() != this.mappedItemType()) {
            particle.set(0, this.mappedItemType(), item);
         } else {
            data.setValue(item);
         }
      }

      particle.setId(this.protocol.getMappingData().getNewParticleId(id));
   }

   public Type itemType() {
      return this.itemType;
   }

   public Type itemArrayType() {
      return this.itemArrayType;
   }

   public Type mappedItemType() {
      return this.mappedItemType;
   }

   public Type mappedItemArrayType() {
      return this.mappedItemArrayType;
   }
}
