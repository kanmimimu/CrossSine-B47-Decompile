package com.viaversion.viarewind.api.rewriter;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;

public abstract class VREntityRewriter extends LegacyEntityRewriter {
   public VREntityRewriter(BackwardsProtocol protocol) {
      super(protocol, EntityDataTypes1_8.STRING, EntityDataTypes1_8.BYTE);
   }

   public VREntityRewriter(BackwardsProtocol protocol, EntityDataType displayType, EntityDataType displayVisibilityType) {
      super(protocol, displayType, displayVisibilityType);
   }

   protected void registerJoinGame1_8(ClientboundPacketType packetType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         protected void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.BYTE);
            this.handler(VREntityRewriter.this.playerTrackerHandler());
            this.handler(VREntityRewriter.this.getDimensionHandler());
         }
      });
   }

   protected void removeEntities(UserConnection connection, int[] entities) {
      EntityTrackerBase tracker = (EntityTrackerBase)this.tracker(connection);

      for(int entityId : entities) {
         tracker.removeEntity(entityId);
      }

   }

   protected PacketHandler getDimensionHandler() {
      return (wrapper) -> {
         int dimension = (Byte)wrapper.get(Types.BYTE, 0);
         ClientWorld clientWorld = wrapper.user().getClientWorld(((BackwardsProtocol)this.protocol).getClass());
         clientWorld.setEnvironment(dimension);
      };
   }

   protected Object getDisplayVisibilityDataValue() {
      return 1;
   }

   protected boolean alwaysShowOriginalMobName() {
      return ViaRewind.getConfig().alwaysShowOriginalMobName();
   }
}
