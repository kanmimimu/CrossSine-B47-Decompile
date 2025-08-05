package com.viaversion.viaversion.protocols.v1_13to1_13_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13to1_13_1.rewriter.EntityPacketRewriter1_13_1;
import com.viaversion.viaversion.protocols.v1_13to1_13_1.rewriter.ItemPacketRewriter1_13_1;
import com.viaversion.viaversion.protocols.v1_13to1_13_1.rewriter.WorldPacketRewriter1_13_1;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_13To1_13_1 extends AbstractProtocol {
   public static final MappingData MAPPINGS = new MappingDataBase("1.13", "1.13.2");
   final EntityPacketRewriter1_13_1 entityRewriter = new EntityPacketRewriter1_13_1(this);
   final ItemPacketRewriter1_13_1 itemRewriter = new ItemPacketRewriter1_13_1(this);
   final TagRewriter tagRewriter = new TagRewriter(this);

   public Protocol1_13To1_13_1() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      WorldPacketRewriter1_13_1.register(this);
      this.registerServerbound(ServerboundPackets1_13.COMMAND_SUGGESTION, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.STRING, new ValueTransformer(Types.STRING) {
               public String transform(PacketWrapper wrapper, String inputValue) {
                  return inputValue.startsWith("/") ? inputValue.substring(1) : inputValue;
               }
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers() {
         public void register() {
            this.map(Types.ITEM1_13);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               Item item = (Item)wrapper.get(Types.ITEM1_13, 0);
               Protocol1_13To1_13_1.this.itemRewriter.handleItemToServer(wrapper.user(), item);
            });
            this.handler((wrapper) -> {
               int hand = (Integer)wrapper.read(Types.VAR_INT);
               if (hand == 1) {
                  wrapper.cancel();
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.COMMAND_SUGGESTIONS, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int start = (Integer)wrapper.get(Types.VAR_INT, 1);
               wrapper.set(Types.VAR_INT, 1, start + 1);
               int count = (Integer)wrapper.get(Types.VAR_INT, 3);

               for(int i = 0; i < count; ++i) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.OPTIONAL_COMPONENT);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.BOSS_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UUID);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (action == 0) {
                  wrapper.passthrough(Types.COMPONENT);
                  wrapper.passthrough(Types.FLOAT);
                  wrapper.passthrough(Types.VAR_INT);
                  wrapper.passthrough(Types.VAR_INT);
                  short flags = (short)(Byte)wrapper.read(Types.BYTE);
                  if ((flags & 2) != 0) {
                     flags = (short)(flags | 4);
                  }

                  wrapper.write(Types.UNSIGNED_BYTE, flags);
               }

            });
         }
      });
      this.tagRewriter.register(ClientboundPackets1_13.UPDATE_TAGS, RegistryType.ITEM);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_13.AWARD_STATS);
   }

   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_13.EntityType.PLAYER));
      userConnection.addClientWorld(this.getClass(), new ClientWorld());
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityPacketRewriter1_13_1 getEntityRewriter() {
      return this.entityRewriter;
   }

   public ItemPacketRewriter1_13_1 getItemRewriter() {
      return this.itemRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }
}
