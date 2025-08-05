package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ClientboundPacketsa1_2_6 implements ClientboundPacketType, PreNettyPacketType {
   KEEP_ALIVE(0, (user, buf) -> {
   }),
   LOGIN(1, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readUTF(buf);
      PreNettyTypes.readUTF(buf);
      buf.skipBytes(9);
   }),
   HANDSHAKE(2, (user, buf) -> PreNettyTypes.readUTF(buf)),
   CHAT(3, (user, buf) -> PreNettyTypes.readUTF(buf)),
   SET_TIME(4, (user, buf) -> buf.skipBytes(8)),
   PLAYER_INVENTORY(5, (user, buf) -> {
      buf.skipBytes(4);
      int x = buf.readShort();

      for(int i = 0; i < x; ++i) {
         PreNettyTypes.readItemStackb1_2(buf);
      }

   }),
   SET_DEFAULT_SPAWN_POSITION(6, (user, buf) -> buf.skipBytes(12)),
   SET_HEALTH(8, (user, buf) -> buf.skipBytes(1)),
   RESPAWN(9, (user, buf) -> {
   }),
   MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
   MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
   MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
   PLAYER_POSITION(13, (user, buf) -> buf.skipBytes(41)),
   SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(6)),
   ADD_TO_INVENTORY(17, (user, buf) -> buf.skipBytes(5)),
   ANIMATE(18, (user, buf) -> buf.skipBytes(5)),
   ADD_PLAYER(20, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readUTF(buf);
      buf.skipBytes(16);
   }),
   SPAWN_ITEM(21, (user, buf) -> buf.skipBytes(22)),
   TAKE_ITEM_ENTITY(22, (user, buf) -> buf.skipBytes(8)),
   ADD_ENTITY(23, (user, buf) -> buf.skipBytes(17)),
   ADD_MOB(24, (user, buf) -> buf.skipBytes(19)),
   SET_ENTITY_MOTION(28, (user, buf) -> buf.skipBytes(10)),
   REMOVE_ENTITIES(29, (user, buf) -> buf.skipBytes(4)),
   MOVE_ENTITY(30, (user, buf) -> buf.skipBytes(4)),
   MOVE_ENTITY_POS(31, (user, buf) -> buf.skipBytes(7)),
   MOVE_ENTITY_ROT(32, (user, buf) -> buf.skipBytes(6)),
   MOVE_ENTITY_POS_ROT(33, (user, buf) -> buf.skipBytes(9)),
   TELEPORT_ENTITY(34, (user, buf) -> buf.skipBytes(18)),
   ENTITY_EVENT(38, (user, buf) -> buf.skipBytes(5)),
   SET_ENTITY_LINK(39, (user, buf) -> buf.skipBytes(8)),
   PRE_CHUNK(50, (user, buf) -> buf.skipBytes(9)),
   LEVEL_CHUNK(51, (user, buf) -> {
      buf.skipBytes(13);
      int x = buf.readInt();

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

   }),
   CHUNK_BLOCKS_UPDATE(52, (user, buf) -> {
      buf.skipBytes(8);
      short x = buf.readShort();

      for(int i = 0; i < x; ++i) {
         buf.readShort();
      }

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

   }),
   BLOCK_UPDATE(53, (user, buf) -> buf.skipBytes(11)),
   BLOCK_ENTITY_DATA(59, (user, buf) -> {
      buf.skipBytes(10);
      int x = buf.readUnsignedShort();

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

   }),
   EXPLODE(60, (user, buf) -> {
      buf.skipBytes(28);
      int x = buf.readInt();

      for(int i = 0; i < x; ++i) {
         buf.skipBytes(3);
      }

   }),
   DISCONNECT(255, (user, buf) -> PreNettyTypes.readUTF(buf));

   private static final ClientboundPacketsa1_2_6[] REGISTRY = new ClientboundPacketsa1_2_6[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ClientboundPacketsa1_2_6 getPacket(int id) {
      return REGISTRY[id];
   }

   private ClientboundPacketsa1_2_6(int id, BiConsumer packetReader) {
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
   private static ClientboundPacketsa1_2_6[] $values() {
      return new ClientboundPacketsa1_2_6[]{KEEP_ALIVE, LOGIN, HANDSHAKE, CHAT, SET_TIME, PLAYER_INVENTORY, SET_DEFAULT_SPAWN_POSITION, SET_HEALTH, RESPAWN, MOVE_PLAYER_STATUS_ONLY, MOVE_PLAYER_POS, MOVE_PLAYER_ROT, PLAYER_POSITION, SET_CARRIED_ITEM, ADD_TO_INVENTORY, ANIMATE, ADD_PLAYER, SPAWN_ITEM, TAKE_ITEM_ENTITY, ADD_ENTITY, ADD_MOB, SET_ENTITY_MOTION, REMOVE_ENTITIES, MOVE_ENTITY, MOVE_ENTITY_POS, MOVE_ENTITY_ROT, MOVE_ENTITY_POS_ROT, TELEPORT_ENTITY, ENTITY_EVENT, SET_ENTITY_LINK, PRE_CHUNK, LEVEL_CHUNK, CHUNK_BLOCKS_UPDATE, BLOCK_UPDATE, BLOCK_ENTITY_DATA, EXPLODE, DISCONNECT};
   }

   static {
      for(ClientboundPacketsa1_2_6 packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
