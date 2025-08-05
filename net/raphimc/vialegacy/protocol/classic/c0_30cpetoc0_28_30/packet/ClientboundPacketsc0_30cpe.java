package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ClientboundPacketsc0_30cpe implements ClientboundPacketType, PreNettyPacketType {
   LOGIN(0, (user, buf) -> buf.skipBytes(130)),
   KEEP_ALIVE(1, (user, buf) -> {
   }),
   LEVEL_INIT(2, (user, buf) -> {
   }),
   LEVEL_DATA(3, (user, buf) -> buf.skipBytes(1027)),
   LEVEL_FINALIZE(4, (user, buf) -> buf.skipBytes(6)),
   BLOCK_UPDATE(6, (user, buf) -> buf.skipBytes(7)),
   ADD_PLAYER(7, (user, buf) -> buf.skipBytes(73)),
   TELEPORT_ENTITY(8, (user, buf) -> buf.skipBytes(9)),
   MOVE_ENTITY_POS_ROT(9, (user, buf) -> buf.skipBytes(6)),
   MOVE_ENTITY_POS(10, (user, buf) -> buf.skipBytes(4)),
   MOVE_ENTITY_ROT(11, (user, buf) -> buf.skipBytes(3)),
   REMOVE_ENTITIES(12, (user, buf) -> buf.skipBytes(1)),
   CHAT(13, (user, buf) -> buf.skipBytes(65)),
   DISCONNECT(14, (user, buf) -> buf.skipBytes(64)),
   OP_LEVEL_UPDATE(15, (user, buf) -> buf.skipBytes(1)),
   EXTENSION_PROTOCOL_INFO(16, (user, buf) -> buf.skipBytes(66)),
   EXTENSION_PROTOCOL_ENTRY(17, (user, buf) -> buf.skipBytes(68)),
   EXT_CUSTOM_BLOCKS_SUPPORT_LEVEL(19, (user, buf) -> buf.skipBytes(1)),
   EXT_SET_BLOCK_PERMISSION(28, (user, buf) -> buf.skipBytes(3)),
   EXT_HACK_CONTROL(32, (user, buf) -> buf.skipBytes(7)),
   EXT_BULK_BLOCK_UPDATE(38, (user, buf) -> buf.skipBytes(1281)),
   EXT_TWO_WAY_PING(43, (user, buf) -> buf.skipBytes(3));

   private static final ClientboundPacketsc0_30cpe[] REGISTRY = new ClientboundPacketsc0_30cpe[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ClientboundPacketsc0_30cpe getPacket(int id) {
      return REGISTRY[id];
   }

   private ClientboundPacketsc0_30cpe(int id, BiConsumer packetReader) {
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
   private static ClientboundPacketsc0_30cpe[] $values() {
      return new ClientboundPacketsc0_30cpe[]{LOGIN, KEEP_ALIVE, LEVEL_INIT, LEVEL_DATA, LEVEL_FINALIZE, BLOCK_UPDATE, ADD_PLAYER, TELEPORT_ENTITY, MOVE_ENTITY_POS_ROT, MOVE_ENTITY_POS, MOVE_ENTITY_ROT, REMOVE_ENTITIES, CHAT, DISCONNECT, OP_LEVEL_UPDATE, EXTENSION_PROTOCOL_INFO, EXTENSION_PROTOCOL_ENTRY, EXT_CUSTOM_BLOCKS_SUPPORT_LEVEL, EXT_SET_BLOCK_PERMISSION, EXT_HACK_CONTROL, EXT_BULK_BLOCK_UPDATE, EXT_TWO_WAY_PING};
   }

   static {
      for(ClientboundPacketsc0_30cpe packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
