package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeMapping implements PacketMapping {
   private final PacketType mappedPacketType;
   private PacketHandler handler;

   PacketTypeMapping(@Nullable PacketType mappedPacketType, @Nullable PacketHandler handler) {
      this.mappedPacketType = mappedPacketType;
      this.handler = handler;
   }

   public void applyType(PacketWrapper wrapper) {
      if (this.mappedPacketType != null) {
         wrapper.setPacketType(this.mappedPacketType);
      }

   }

   public void appendHandler(PacketHandler handler) {
      if (this.handler == null) {
         this.handler = handler;
      } else {
         this.handler = this.handler.then(handler);
      }

   }

   public @Nullable PacketHandler handler() {
      return this.handler;
   }
}
