package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;

public class SoundRewriter extends com.viaversion.viaversion.rewriter.SoundRewriter {
   final BackwardsProtocol protocol;

   public SoundRewriter(BackwardsProtocol protocol) {
      super(protocol);
      this.protocol = protocol;
   }

   public void registerNamedSound(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler(SoundRewriter.this.getNamedSoundHandler());
         }
      });
   }

   public void registerStopSound(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.handler(SoundRewriter.this.getStopSoundHandler());
         }
      });
   }

   public PacketHandler getNamedSoundHandler() {
      return (wrapper) -> {
         String soundId = (String)wrapper.get(Types.STRING, 0);
         String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
         if (mappedId != null) {
            if (!mappedId.isEmpty()) {
               wrapper.set(Types.STRING, 0, mappedId);
            } else {
               wrapper.cancel();
            }

         }
      };
   }

   public PacketHandler getStopSoundHandler() {
      return (wrapper) -> {
         byte flags = (Byte)wrapper.passthrough(Types.BYTE);
         if ((flags & 2) != 0) {
            if ((flags & 1) != 0) {
               wrapper.passthrough(Types.VAR_INT);
            }

            String soundId = (String)wrapper.read(Types.STRING);
            String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
            if (mappedId == null) {
               wrapper.write(Types.STRING, soundId);
            } else {
               if (!mappedId.isEmpty()) {
                  wrapper.write(Types.STRING, mappedId);
               } else {
                  wrapper.cancel();
               }

            }
         }
      };
   }

   public void registerSound1_19_3(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, this.getSoundHandler1_19_3());
   }

   public PacketHandler getSoundHandler1_19_3() {
      return (wrapper) -> {
         Holder<SoundEvent> soundEventHolder = (Holder)wrapper.read(Types.SOUND_EVENT);
         if (soundEventHolder.isDirect()) {
            wrapper.write(Types.SOUND_EVENT, this.rewriteSoundEvent(wrapper, soundEventHolder));
         } else {
            int mappedId = this.idRewriter.rewrite(soundEventHolder.id());
            if (mappedId == -1) {
               wrapper.cancel();
            } else {
               if (mappedId != soundEventHolder.id()) {
                  soundEventHolder = Holder.of(mappedId);
               }

               wrapper.write(Types.SOUND_EVENT, soundEventHolder);
            }
         }
      };
   }

   public Holder rewriteSoundEvent(PacketWrapper wrapper, Holder soundEventHolder) {
      SoundEvent soundEvent = (SoundEvent)soundEventHolder.value();
      String mappedIdentifier = this.protocol.getMappingData().getMappedNamedSound(soundEvent.identifier());
      if (mappedIdentifier != null) {
         if (!mappedIdentifier.isEmpty()) {
            return Holder.of(soundEvent.withIdentifier(mappedIdentifier));
         }

         wrapper.cancel();
      }

      return soundEventHolder;
   }
}
