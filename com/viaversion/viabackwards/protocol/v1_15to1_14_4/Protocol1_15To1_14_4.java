package com.viaversion.viabackwards.protocol.v1_15to1_14_4;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.rewriter.BlockItemPacketRewriter1_15;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.rewriter.EntityPacketRewriter1_15;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.storage.ImmediateRespawnStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.packet.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_15To1_14_4 extends BackwardsProtocol {
   public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.15", "1.14", Protocol1_14_4To1_15.class);
   final EntityPacketRewriter1_15 entityRewriter = new EntityPacketRewriter1_15(this);
   final BlockItemPacketRewriter1_15 blockItemPackets = new BlockItemPacketRewriter1_15(this);
   final TranslatableRewriter translatableRewriter;
   final TagRewriter tagRewriter;

   public Protocol1_15To1_14_4() {
      super(ClientboundPackets1_15.class, ClientboundPackets1_14_4.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
      this.translatableRewriter = new TranslatableRewriter(this, ComponentRewriter.ReadType.JSON);
      this.tagRewriter = new TagRewriter(this);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerBossEvent(ClientboundPackets1_15.BOSS_EVENT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_15.CHAT);
      this.translatableRewriter.registerPlayerCombat(ClientboundPackets1_15.PLAYER_COMBAT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_15.DISCONNECT);
      this.translatableRewriter.registerOpenScreen(ClientboundPackets1_15.OPEN_SCREEN);
      this.translatableRewriter.registerTabList(ClientboundPackets1_15.TAB_LIST);
      this.translatableRewriter.registerTitle(ClientboundPackets1_15.SET_TITLES);
      this.translatableRewriter.registerPing();
      SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
      soundRewriter.registerSound(ClientboundPackets1_15.SOUND_ENTITY);
      soundRewriter.registerNamedSound(ClientboundPackets1_15.CUSTOM_SOUND);
      soundRewriter.registerStopSound(ClientboundPackets1_15.STOP_SOUND);
      this.registerClientbound(ClientboundPackets1_15.EXPLODE, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               PacketWrapper soundPacket = wrapper.create(ClientboundPackets1_14_4.SOUND);
               soundPacket.write(Types.VAR_INT, 243);
               soundPacket.write(Types.VAR_INT, 4);
               soundPacket.write(Types.INT, this.toEffectCoordinate((Float)wrapper.get(Types.FLOAT, 0)));
               soundPacket.write(Types.INT, this.toEffectCoordinate((Float)wrapper.get(Types.FLOAT, 1)));
               soundPacket.write(Types.INT, this.toEffectCoordinate((Float)wrapper.get(Types.FLOAT, 2)));
               soundPacket.write(Types.FLOAT, 4.0F);
               soundPacket.write(Types.FLOAT, 1.0F);
               soundPacket.send(Protocol1_15To1_14_4.class);
            });
         }

         int toEffectCoordinate(float coordinate) {
            return (int)(coordinate * 8.0F);
         }
      });
      this.tagRewriter.register(ClientboundPackets1_15.UPDATE_TAGS, RegistryType.ENTITY);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_15.AWARD_STATS);
   }

   public void init(UserConnection user) {
      user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_15.PLAYER));
      user.addClientWorld(this.getClass(), new ClientWorld());
      user.put(new ImmediateRespawnStorage());
   }

   public BackwardsMappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityPacketRewriter1_15 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPacketRewriter1_15 getItemRewriter() {
      return this.blockItemPackets;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }

   public TranslatableRewriter getComponentRewriter() {
      return this.translatableRewriter;
   }
}
