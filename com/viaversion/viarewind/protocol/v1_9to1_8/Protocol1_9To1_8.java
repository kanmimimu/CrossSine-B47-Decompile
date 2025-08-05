package com.viaversion.viarewind.protocol.v1_9to1_8;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viarewind.protocol.v1_9to1_8.data.RewindMappingData1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.rewriter.BlockItemPacketRewriter1_9;
import com.viaversion.viarewind.protocol.v1_9to1_8.rewriter.EntityPacketRewriter1_9;
import com.viaversion.viarewind.protocol.v1_9to1_8.rewriter.PlayerPacketRewriter1_9;
import com.viaversion.viarewind.protocol.v1_9to1_8.rewriter.WorldPacketRewriter1_9;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.BlockPlaceDestroyTracker;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.BossBarStorage;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.CooldownStorage;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.LevitationStorage;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.PlayerPositionTracker;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.WindowTracker;
import com.viaversion.viarewind.protocol.v1_9to1_8.task.CooldownIndicatorTask;
import com.viaversion.viarewind.protocol.v1_9to1_8.task.LevitationUpdateTask;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import java.util.concurrent.TimeUnit;

public class Protocol1_9To1_8 extends BackwardsProtocol {
   public static final ValueTransformer DOUBLE_TO_INT_TIMES_32;
   public static final ValueTransformer DEGREES_TO_ANGLE;
   public static final RewindMappingData1_8 MAPPINGS;
   final BlockItemPacketRewriter1_9 itemRewriter = new BlockItemPacketRewriter1_9(this);
   final EntityPacketRewriter1_9 entityRewriter = new EntityPacketRewriter1_9(this);

   public Protocol1_9To1_8() {
      super(ClientboundPackets1_9.class, ClientboundPackets1_8.class, ServerboundPackets1_9.class, ServerboundPackets1_8.class);
   }

   protected void registerPackets() {
      this.entityRewriter.register();
      this.itemRewriter.register();
      (new PlayerPacketRewriter1_9(this)).register();
      (new WorldPacketRewriter1_9(this)).register();
   }

   public void init(UserConnection connection) {
      connection.addEntityTracker(this.getClass(), new EntityTracker1_9(connection));
      connection.addClientWorld(this.getClass(), new ClientWorld());
      connection.put(new WindowTracker(connection));
      connection.put(new LevitationStorage());
      connection.put(new PlayerPositionTracker());
      connection.put(new CooldownStorage());
      connection.put(new BlockPlaceDestroyTracker());
      connection.put(new BossBarStorage(connection));
   }

   public void register(ViaProviders providers) {
      Via.getManager().getScheduler().scheduleRepeating(new LevitationUpdateTask(), 0L, 50L, TimeUnit.MILLISECONDS);
      Via.getManager().getScheduler().scheduleRepeating(new CooldownIndicatorTask(), 0L, 50L, TimeUnit.MILLISECONDS);
   }

   public RewindMappingData1_8 getMappingData() {
      return MAPPINGS;
   }

   public BlockItemPacketRewriter1_9 getItemRewriter() {
      return this.itemRewriter;
   }

   public EntityPacketRewriter1_9 getEntityRewriter() {
      return this.entityRewriter;
   }

   public boolean hasMappingDataToLoad() {
      return true;
   }

   static {
      DOUBLE_TO_INT_TIMES_32 = new ValueTransformer(Types.INT) {
         public Integer transform(PacketWrapper wrapper, Double inputValue) {
            return (int)(inputValue * (double)32.0F);
         }
      };
      DEGREES_TO_ANGLE = new ValueTransformer(Types.BYTE) {
         public Byte transform(PacketWrapper packetWrapper, Float degrees) {
            return (byte)((int)(degrees / 360.0F * 256.0F));
         }
      };
      MAPPINGS = new RewindMappingData1_8();
   }
}
