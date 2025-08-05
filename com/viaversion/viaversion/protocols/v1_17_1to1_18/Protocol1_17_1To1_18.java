package com.viaversion.viaversion.protocols.v1_17_1to1_18;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.rewriter.EntityPacketRewriter1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.rewriter.ItemPacketRewriter1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.rewriter.WorldPacketRewriter1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.storage.ChunkLightStorage;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ProtocolLogger;

public final class Protocol1_17_1To1_18 extends AbstractProtocol {
   public static final MappingData MAPPINGS = new MappingDataBase("1.17", "1.18");
   public static final ProtocolLogger LOGGER = new ProtocolLogger(Protocol1_17_1To1_18.class);
   final EntityPacketRewriter1_18 entityRewriter = new EntityPacketRewriter1_18(this);
   final ItemPacketRewriter1_18 itemRewriter = new ItemPacketRewriter1_18(this);
   final TagRewriter tagRewriter = new TagRewriter(this);

   public Protocol1_17_1To1_18() {
      super(ClientboundPackets1_17_1.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
   }

   protected void registerPackets() {
      this.entityRewriter.register();
      this.itemRewriter.register();
      WorldPacketRewriter1_18.register(this);
      SoundRewriter<ClientboundPackets1_17_1> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound(ClientboundPackets1_17_1.SOUND);
      soundRewriter.registerSound(ClientboundPackets1_17_1.SOUND_ENTITY);
      this.tagRewriter.registerGeneric(ClientboundPackets1_17_1.UPDATE_TAGS);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_17_1.AWARD_STATS);
      this.registerServerbound(ServerboundPackets1_17.CLIENT_INFORMATION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.map(Types.VAR_INT);
            this.map(Types.BOOLEAN);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
            this.map(Types.BOOLEAN);
            this.read(Types.BOOLEAN);
         }
      });
   }

   protected void onMappingDataLoaded() {
      Types1_18.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.ITEM1_13_2).reader("vibration", ParticleType.Readers.VIBRATION);
      this.tagRewriter.renameTag(RegistryType.BLOCK, "minecraft:lava_pool_stone_replaceables", "minecraft:lava_pool_stone_cannot_replace");
      super.onMappingDataLoaded();
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public ProtocolLogger getLogger() {
      return LOGGER;
   }

   public void init(UserConnection connection) {
      this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_17.PLAYER));
      connection.put(new ChunkLightStorage());
   }

   public EntityPacketRewriter1_18 getEntityRewriter() {
      return this.entityRewriter;
   }

   public ItemPacketRewriter1_18 getItemRewriter() {
      return this.itemRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }
}
