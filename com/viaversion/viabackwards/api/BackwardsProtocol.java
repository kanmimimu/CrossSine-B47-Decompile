package com.viaversion.viabackwards.api;

import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class BackwardsProtocol extends AbstractProtocol {
   protected BackwardsProtocol() {
   }

   protected BackwardsProtocol(@Nullable Class oldClientboundPacketEnum, @Nullable Class clientboundPacketEnum, @Nullable Class oldServerboundPacketEnum, @Nullable Class serverboundPacketEnum) {
      super(oldClientboundPacketEnum, clientboundPacketEnum, oldServerboundPacketEnum, serverboundPacketEnum);
   }

   protected void executeAsyncAfterLoaded(Class protocolClass, Runnable runnable) {
      Via.getManager().getProtocolManager().addMappingLoaderFuture(this.getClass(), protocolClass, runnable);
   }

   protected void registerPackets() {
      super.registerPackets();
      BackwardsMappingData mappingData = this.getMappingData();
      if (mappingData != null && mappingData.getViaVersionProtocolClass() != null) {
         this.executeAsyncAfterLoaded(mappingData.getViaVersionProtocolClass(), this::loadMappingData);
      }

   }

   public boolean hasMappingDataToLoad() {
      return false;
   }

   public @Nullable BackwardsMappingData getMappingData() {
      return null;
   }

   public @Nullable TranslatableRewriter getComponentRewriter() {
      return null;
   }
}
