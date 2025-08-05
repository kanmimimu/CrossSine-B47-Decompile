package net.raphimc.vialegacy.protocol.classic.c0_0_15a_1toc0_0_16a_02.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ClientboundPacketsc0_15a implements ClientboundPacketType, PreNettyPacketType {
   LOGIN(0, (user, buf) -> buf.skipBytes(64)),
   KEEP_ALIVE(1, (user, buf) -> {
   }),
   LEVEL_INIT(2, (user, buf) -> {
   }),
   LEVEL_DATA(3, (user, buf) -> buf.skipBytes(1027)),
   LEVEL_FINALIZE(4, (user, buf) -> buf.skipBytes(6)),
   BLOCK_UPDATE(6, (user, buf) -> buf.skipBytes(7)),
   ADD_PLAYER(7, (user, buf) -> buf.skipBytes(73)),
   TELEPORT_ENTITY(8, (user, buf) -> buf.skipBytes(9)),
   REMOVE_ENTITIES(9, (user, buf) -> buf.skipBytes(1));

   private static final ClientboundPacketsc0_15a[] REGISTRY = new ClientboundPacketsc0_15a[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ClientboundPacketsc0_15a getPacket(int id) {
      return REGISTRY[id];
   }

   private ClientboundPacketsc0_15a(int id, BiConsumer packetReader) {
      this.id = id;
      this.packetReader = packetReader;
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return this.name();
   }

   public BiConsumer getPacketReader() {
      return this.packetReader;
   }

   // $FF: synthetic method
   private static ClientboundPacketsc0_15a[] $values() {
      return new ClientboundPacketsc0_15a[]{LOGIN, KEEP_ALIVE, LEVEL_INIT, LEVEL_DATA, LEVEL_FINALIZE, BLOCK_UPDATE, ADD_PLAYER, TELEPORT_ENTITY, REMOVE_ENTITIES};
   }

   static {
      for(ClientboundPacketsc0_15a packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
