package com.viaversion.viaversion.protocols.template;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.rewriter.AttributeRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ProtocolUtil;

final class Protocol1_99To_98 extends AbstractProtocol {
   public static final MappingData MAPPINGS = new MappingDataBase("1.98", "1.99");
   private final EntityPacketRewriter1_99 entityRewriter = new EntityPacketRewriter1_99(this);
   private final BlockItemPacketRewriter1_99 itemRewriter = new BlockItemPacketRewriter1_99(this);
   private final TagRewriter tagRewriter = new TagRewriter(this);

   public Protocol1_99To_98() {
      super(ClientboundPacket1_21.class, ClientboundPacket1_21.class, ServerboundPacket1_20_5.class, ServerboundPacket1_20_5.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.tagRewriter.registerGeneric(ClientboundPackets1_21.UPDATE_TAGS);
      this.tagRewriter.registerGeneric(ClientboundConfigurationPackets1_21.UPDATE_TAGS);
      SoundRewriter<ClientboundPacket1_21> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound1_19_3(ClientboundPackets1_21.SOUND);
      soundRewriter.registerSound1_19_3(ClientboundPackets1_21.SOUND_ENTITY);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_21.AWARD_STATS);
      (new AttributeRewriter(this)).register1_21(ClientboundPackets1_21.UPDATE_ATTRIBUTES);
   }

   protected void onMappingDataLoaded() {
      super.onMappingDataLoaded();
   }

   public void init(UserConnection connection) {
      this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_20_5.PLAYER));
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityPacketRewriter1_99 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPacketRewriter1_99 getItemRewriter() {
      return this.itemRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }

   protected PacketTypesProvider createPacketTypesProvider() {
      return new SimplePacketTypesProvider(ProtocolUtil.packetTypeMap(this.unmappedClientboundPacketType, ClientboundPackets1_21.class, ClientboundConfigurationPackets1_21.class), ProtocolUtil.packetTypeMap(this.mappedClientboundPacketType, ClientboundPackets1_21.class, ClientboundConfigurationPackets1_21.class), ProtocolUtil.packetTypeMap(this.mappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class), ProtocolUtil.packetTypeMap(this.unmappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class));
   }
}
