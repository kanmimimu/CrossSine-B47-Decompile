package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;

public class SoundRewriter {
   protected final Protocol protocol;
   protected final IdRewriteFunction idRewriter;

   public SoundRewriter(Protocol protocol) {
      this.protocol = protocol;
      this.idRewriter = (id) -> protocol.getMappingData().getSoundMappings().getNewId(id);
   }

   public SoundRewriter(Protocol protocol, IdRewriteFunction idRewriter) {
      this.protocol = protocol;
      this.idRewriter = idRewriter;
   }

   public void registerSound(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler(SoundRewriter.this.getSoundHandler());
         }
      }));
   }

   public void registerSound1_19_3(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, this.soundHolderHandler());
   }

   public PacketHandler soundHolderHandler() {
      return (wrapper) -> {
         Holder<SoundEvent> soundEventHolder = (Holder)wrapper.read(Types.SOUND_EVENT);
         if (soundEventHolder.isDirect()) {
            wrapper.write(Types.SOUND_EVENT, soundEventHolder);
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

   public PacketHandler getSoundHandler() {
      return (wrapper) -> {
         int soundId = (Integer)wrapper.get(Types.VAR_INT, 0);
         int mappedId = this.idRewriter.rewrite(soundId);
         if (mappedId == -1) {
            wrapper.cancel();
         } else if (soundId != mappedId) {
            wrapper.set(Types.VAR_INT, 0, mappedId);
         }

      };
   }
}
