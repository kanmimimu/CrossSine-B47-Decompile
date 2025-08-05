package com.viaversion.viaversion.protocols.v1_8to1_9.provider;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.CommandBlockStorage;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import io.netty.buffer.ByteBuf;
import java.util.Optional;

public class CommandBlockProvider implements Provider {
   public void addOrUpdateBlock(UserConnection user, BlockPosition position, CompoundTag tag) {
      this.checkPermission(user);
      this.getStorage(user).addOrUpdateBlock(position, tag);
   }

   public Optional get(UserConnection user, BlockPosition position) {
      this.checkPermission(user);
      return this.getStorage(user).getCommandBlock(position);
   }

   public void unloadChunk(UserConnection user, int x, int z) {
      this.checkPermission(user);
      this.getStorage(user).unloadChunk(x, z);
   }

   private CommandBlockStorage getStorage(UserConnection connection) {
      return (CommandBlockStorage)connection.get(CommandBlockStorage.class);
   }

   public void sendPermission(UserConnection user) {
      PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EVENT, (ByteBuf)null, user);
      EntityTracker1_9 tracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_8To1_9.class);
      wrapper.write(Types.INT, tracker.getProvidedEntityId());
      wrapper.write(Types.BYTE, (byte)26);
      wrapper.scheduleSend(Protocol1_8To1_9.class);
      ((CommandBlockStorage)user.get(CommandBlockStorage.class)).setPermissions(true);
   }

   private void checkPermission(UserConnection user) {
      CommandBlockStorage storage = this.getStorage(user);
      if (!storage.isPermissions()) {
         this.sendPermission(user);
      }

   }

   public void unloadChunks(UserConnection userConnection) {
      this.getStorage(userConnection).unloadChunks();
   }
}
