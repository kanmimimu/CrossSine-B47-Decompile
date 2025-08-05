package com.viaversion.viaversion.protocols.v1_19_3to1_19_4;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.data.MappingData1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.EntityPacketRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.ItemPacketRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.storage.PlayerVehicleTracker;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Protocol1_19_3To1_19_4 extends AbstractProtocol {
   public static final MappingData1_19_4 MAPPINGS = new MappingData1_19_4();
   final EntityPacketRewriter1_19_4 entityRewriter = new EntityPacketRewriter1_19_4(this);
   final ItemPacketRewriter1_19_4 itemRewriter = new ItemPacketRewriter1_19_4(this);
   final TagRewriter tagRewriter = new TagRewriter(this);

   public Protocol1_19_3To1_19_4() {
      super(ClientboundPackets1_19_3.class, ClientboundPackets1_19_4.class, ServerboundPackets1_19_3.class, ServerboundPackets1_19_4.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.tagRewriter.registerGeneric(ClientboundPackets1_19_3.UPDATE_TAGS);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_19_3.AWARD_STATS);
      SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound1_19_3(ClientboundPackets1_19_3.SOUND_ENTITY);
      soundRewriter.registerSound1_19_3(ClientboundPackets1_19_3.SOUND);
      (new CommandRewriter(this) {
         public void handleArgument(PacketWrapper wrapper, String argumentType) {
            if (argumentType.equals("minecraft:time")) {
               wrapper.write(Types.INT, 0);
            } else {
               super.handleArgument(wrapper, argumentType);
            }

         }
      }).registerDeclareCommands1_19(ClientboundPackets1_19_3.COMMANDS);
      this.registerClientbound(ClientboundPackets1_19_3.SERVER_DATA, (wrapper) -> {
         JsonElement element = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
         if (element != null) {
            wrapper.write(Types.COMPONENT, element);
         } else {
            wrapper.write(Types.COMPONENT, ComponentUtil.emptyJsonComponent());
         }

         String iconBase64 = (String)wrapper.read(Types.OPTIONAL_STRING);
         byte[] iconBytes = null;
         if (iconBase64 != null && iconBase64.startsWith("data:image/png;base64,")) {
            iconBytes = Base64.getDecoder().decode(iconBase64.substring("data:image/png;base64,".length()).getBytes(StandardCharsets.UTF_8));
         }

         wrapper.write(Types.OPTIONAL_BYTE_ARRAY_PRIMITIVE, iconBytes);
      });
   }

   protected void onMappingDataLoaded() {
      EntityTypes1_19_4.initialize(this);
      Types1_19_4.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.ITEM1_13_2).reader("vibration", ParticleType.Readers.VIBRATION1_19).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK);
      super.onMappingDataLoaded();
   }

   public void init(UserConnection user) {
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_4.PLAYER));
      user.put(new PlayerVehicleTracker());
   }

   public MappingData1_19_4 getMappingData() {
      return MAPPINGS;
   }

   public EntityPacketRewriter1_19_4 getEntityRewriter() {
      return this.entityRewriter;
   }

   public ItemPacketRewriter1_19_4 getItemRewriter() {
      return this.itemRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }
}
