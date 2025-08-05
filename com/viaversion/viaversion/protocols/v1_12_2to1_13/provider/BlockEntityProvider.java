package com.viaversion.viaversion.protocols.v1_12_2to1_13.provider;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities.BannerHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities.BedHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities.CommandBlockHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities.FlowerPotHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities.SkullHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities.SpawnerHandler;
import com.viaversion.viaversion.util.ProtocolLogger;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Map;

public class BlockEntityProvider implements Provider {
   final Map handlers = new HashMap();

   public BlockEntityProvider() {
      this.handlers.put("minecraft:flower_pot", new FlowerPotHandler());
      this.handlers.put("minecraft:bed", new BedHandler());
      this.handlers.put("minecraft:banner", new BannerHandler());
      this.handlers.put("minecraft:skull", new SkullHandler());
      this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
      this.handlers.put("minecraft:command_block", new CommandBlockHandler());
   }

   public int transform(UserConnection user, BlockPosition position, CompoundTag tag, boolean sendUpdate) {
      StringTag idTag = tag.getStringTag("id");
      if (idTag == null) {
         return -1;
      } else {
         BlockEntityHandler handler = (BlockEntityHandler)this.handlers.get(idTag.getValue());
         if (handler == null) {
            if (Via.getManager().isDebug()) {
               ProtocolLogger var10000 = Protocol1_12_2To1_13.LOGGER;
               String var9 = idTag.getValue();
               var10000.warning("Unhandled BlockEntity " + var9 + " full tag: " + tag);
            }

            return -1;
         } else {
            int newBlock = handler.transform(user, tag);
            if (sendUpdate && newBlock != -1) {
               this.sendBlockChange(user, position, newBlock);
            }

            return newBlock;
         }
      }
   }

   void sendBlockChange(UserConnection user, BlockPosition position, int blockId) {
      PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_13.BLOCK_UPDATE, (ByteBuf)null, user);
      wrapper.write(Types.BLOCK_POSITION1_8, position);
      wrapper.write(Types.VAR_INT, blockId);
      wrapper.send(Protocol1_12_2To1_13.class);
   }

   @FunctionalInterface
   public interface BlockEntityHandler {
      int transform(UserConnection var1, CompoundTag var2);
   }
}
