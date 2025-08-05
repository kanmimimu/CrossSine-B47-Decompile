package com.viaversion.viaversion.protocols.v1_14_4to1_15;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.packet.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter.EntityPacketRewriter1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter.ItemPacketRewriter1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter.WorldPacketRewriter1_15;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_14_4To1_15 extends AbstractProtocol {
   public static final MappingData MAPPINGS = new MappingDataBase("1.14", "1.15");
   private final EntityPacketRewriter1_15 entityRewriter = new EntityPacketRewriter1_15(this);
   private final ItemPacketRewriter1_15 itemRewriter = new ItemPacketRewriter1_15(this);
   private final TagRewriter tagRewriter = new TagRewriter(this);

   public Protocol1_14_4To1_15() {
      super(ClientboundPackets1_14_4.class, ClientboundPackets1_15.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      WorldPacketRewriter1_15.register(this);
      SoundRewriter<ClientboundPackets1_14_4> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound(ClientboundPackets1_14_4.SOUND_ENTITY);
      soundRewriter.registerSound(ClientboundPackets1_14_4.SOUND);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_14_4.AWARD_STATS);
      this.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, (wrapper) -> this.itemRewriter.handleItemToServer(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2)));
      this.tagRewriter.register(ClientboundPackets1_14_4.UPDATE_TAGS, RegistryType.ENTITY);
   }

   protected void onMappingDataLoaded() {
      EntityTypes1_15.initialize(this);
      this.tagRewriter.removeTag(RegistryType.BLOCK, "minecraft:dirt_like");
      this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:lectern_books");
      this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:bee_growables", "minecraft:beehives");
      this.tagRewriter.addEmptyTag(RegistryType.ENTITY, "minecraft:beehive_inhabitors");
      super.onMappingDataLoaded();
   }

   public void init(UserConnection connection) {
      this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_15.PLAYER));
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityPacketRewriter1_15 getEntityRewriter() {
      return this.entityRewriter;
   }

   public ItemPacketRewriter1_15 getItemRewriter() {
      return this.itemRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }
}
