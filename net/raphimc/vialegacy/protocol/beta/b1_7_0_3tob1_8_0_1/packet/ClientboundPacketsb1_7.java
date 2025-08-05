package net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ClientboundPacketsb1_7 implements ClientboundPacketType, PreNettyPacketType {
   KEEP_ALIVE(0, (user, buf) -> {
   }),
   LOGIN(1, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readString(buf);
      buf.skipBytes(9);
   }),
   HANDSHAKE(2, (user, buf) -> PreNettyTypes.readString(buf)),
   CHAT(3, (user, buf) -> PreNettyTypes.readString(buf)),
   SET_TIME(4, (user, buf) -> buf.skipBytes(8)),
   SET_EQUIPPED_ITEM(5, (user, buf) -> buf.skipBytes(10)),
   SET_DEFAULT_SPAWN_POSITION(6, (user, buf) -> buf.skipBytes(12)),
   SET_HEALTH(8, (user, buf) -> buf.skipBytes(2)),
   RESPAWN(9, (user, buf) -> buf.skipBytes(1)),
   MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
   MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
   MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
   PLAYER_POSITION(13, (user, buf) -> buf.skipBytes(41)),
   PLAYER_SLEEP(17, (user, buf) -> buf.skipBytes(14)),
   ANIMATE(18, (user, buf) -> buf.skipBytes(5)),
   ADD_PLAYER(20, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readString(buf);
      buf.skipBytes(16);
   }),
   SPAWN_ITEM(21, (user, buf) -> buf.skipBytes(24)),
   TAKE_ITEM_ENTITY(22, (user, buf) -> buf.skipBytes(8)),
   ADD_ENTITY(23, (user, buf) -> {
      buf.skipBytes(17);
      int i = buf.readInt();
      if (i > 0) {
         buf.skipBytes(6);
      }

   }),
   ADD_MOB(24, (user, buf) -> {
      buf.skipBytes(19);
      PreNettyTypes.readEntityDataListb1_5(buf);
   }),
   ADD_PAINTING(25, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readString(buf);
      buf.skipBytes(16);
   }),
   SET_ENTITY_MOTION(28, (user, buf) -> buf.skipBytes(10)),
   REMOVE_ENTITIES(29, (user, buf) -> buf.skipBytes(4)),
   MOVE_ENTITY(30, (user, buf) -> buf.skipBytes(4)),
   MOVE_ENTITY_POS(31, (user, buf) -> buf.skipBytes(7)),
   MOVE_ENTITY_ROT(32, (user, buf) -> buf.skipBytes(6)),
   MOVE_ENTITY_POS_ROT(33, (user, buf) -> buf.skipBytes(9)),
   TELEPORT_ENTITY(34, (user, buf) -> buf.skipBytes(18)),
   ENTITY_EVENT(38, (user, buf) -> buf.skipBytes(5)),
   SET_ENTITY_LINK(39, (user, buf) -> buf.skipBytes(8)),
   SET_ENTITY_DATA(40, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readEntityDataListb1_5(buf);
   }),
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
   BLOCK_EVENT(54, (user, buf) -> buf.skipBytes(12)),
   EXPLODE(60, (user, buf) -> {
      buf.skipBytes(28);
      int x = buf.readInt();

      for(int i = 0; i < x; ++i) {
         buf.skipBytes(3);
      }

   }),
   LEVEL_EVENT(61, (user, buf) -> buf.skipBytes(17)),
   GAME_EVENT(70, (user, buf) -> buf.skipBytes(1)),
   ADD_GLOBAL_ENTITY(71, (user, buf) -> buf.skipBytes(17)),
   OPEN_SCREEN(100, (user, buf) -> {
      buf.skipBytes(2);
      PreNettyTypes.readUTF(buf);
      buf.skipBytes(1);
   }),
   CONTAINER_CLOSE(101, (user, buf) -> buf.skipBytes(1)),
   CONTAINER_SET_SLOT(103, (user, buf) -> {
      buf.skipBytes(3);
      PreNettyTypes.readItemStackb1_2(buf);
   }),
   CONTAINER_SET_CONTENT(104, (user, buf) -> {
      buf.skipBytes(1);
      int x = buf.readShort();

      for(int i = 0; i < x; ++i) {
         PreNettyTypes.readItemStackb1_2(buf);
      }

   }),
   CONTAINER_SET_DATA(105, (user, buf) -> buf.skipBytes(5)),
   CONTAINER_ACK(106, (user, buf) -> buf.skipBytes(4)),
   UPDATE_SIGN(130, (user, buf) -> {
      buf.skipBytes(10);
      PreNettyTypes.readString(buf);
      PreNettyTypes.readString(buf);
      PreNettyTypes.readString(buf);
      PreNettyTypes.readString(buf);
   }),
   MAP_ITEM_DATA(131, (user, buf) -> {
      buf.skipBytes(4);
      short x = buf.readUnsignedByte();

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

   }),
   AWARD_STATS(200, (user, buf) -> buf.skipBytes(5)),
   DISCONNECT(255, (user, buf) -> PreNettyTypes.readString(buf));

   private static final ClientboundPacketsb1_7[] REGISTRY = new ClientboundPacketsb1_7[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ClientboundPacketsb1_7 getPacket(int id) {
      return REGISTRY[id];
   }

   private ClientboundPacketsb1_7(int id, BiConsumer packetReader) {
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
   private static ClientboundPacketsb1_7[] $values() {
      return new ClientboundPacketsb1_7[]{KEEP_ALIVE, LOGIN, HANDSHAKE, CHAT, SET_TIME, SET_EQUIPPED_ITEM, SET_DEFAULT_SPAWN_POSITION, SET_HEALTH, RESPAWN, MOVE_PLAYER_STATUS_ONLY, MOVE_PLAYER_POS, MOVE_PLAYER_ROT, PLAYER_POSITION, PLAYER_SLEEP, ANIMATE, ADD_PLAYER, SPAWN_ITEM, TAKE_ITEM_ENTITY, ADD_ENTITY, ADD_MOB, ADD_PAINTING, SET_ENTITY_MOTION, REMOVE_ENTITIES, MOVE_ENTITY, MOVE_ENTITY_POS, MOVE_ENTITY_ROT, MOVE_ENTITY_POS_ROT, TELEPORT_ENTITY, ENTITY_EVENT, SET_ENTITY_LINK, SET_ENTITY_DATA, PRE_CHUNK, LEVEL_CHUNK, CHUNK_BLOCKS_UPDATE, BLOCK_UPDATE, BLOCK_EVENT, EXPLODE, LEVEL_EVENT, GAME_EVENT, ADD_GLOBAL_ENTITY, OPEN_SCREEN, CONTAINER_CLOSE, CONTAINER_SET_SLOT, CONTAINER_SET_CONTENT, CONTAINER_SET_DATA, CONTAINER_ACK, UPDATE_SIGN, MAP_ITEM_DATA, AWARD_STATS, DISCONNECT};
   }

   static {
      for(ClientboundPacketsb1_7 packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
