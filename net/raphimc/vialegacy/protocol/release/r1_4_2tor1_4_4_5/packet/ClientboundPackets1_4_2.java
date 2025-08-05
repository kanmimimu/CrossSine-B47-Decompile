package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ClientboundPackets1_4_2 implements ClientboundPacketType, PreNettyPacketType {
   KEEP_ALIVE(0, (user, buf) -> buf.skipBytes(4)),
   LOGIN(1, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readString(buf);
      buf.skipBytes(5);
   }),
   CHAT(3, (user, buf) -> PreNettyTypes.readString(buf)),
   SET_TIME(4, (user, buf) -> buf.skipBytes(16)),
   SET_EQUIPPED_ITEM(5, (user, buf) -> {
      buf.skipBytes(6);
      PreNettyTypes.readItemStack1_3_1(buf);
   }),
   SET_DEFAULT_SPAWN_POSITION(6, (user, buf) -> buf.skipBytes(12)),
   SET_HEALTH(8, (user, buf) -> buf.skipBytes(8)),
   RESPAWN(9, (user, buf) -> {
      buf.skipBytes(8);
      PreNettyTypes.readString(buf);
   }),
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
      PreNettyTypes.readEntityDataList1_4_2(buf);
   }),
   SPAWN_ITEM(21, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readItemStack1_3_1(buf);
      buf.skipBytes(15);
   }),
   TAKE_ITEM_ENTITY(22, (user, buf) -> buf.skipBytes(8)),
   ADD_ENTITY(23, (user, buf) -> {
      buf.skipBytes(17);
      int i = buf.readInt();
      if (i > 0) {
         buf.skipBytes(6);
      }

   }),
   ADD_MOB(24, (user, buf) -> {
      buf.skipBytes(26);
      PreNettyTypes.readEntityDataList1_4_2(buf);
   }),
   ADD_PAINTING(25, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readString(buf);
      buf.skipBytes(16);
   }),
   ADD_EXPERIENCE_ORB(26, (user, buf) -> buf.skipBytes(18)),
   SET_ENTITY_MOTION(28, (user, buf) -> buf.skipBytes(10)),
   REMOVE_ENTITIES(29, (user, buf) -> {
      int x = buf.readUnsignedByte();

      for(int i = 0; i < x; ++i) {
         buf.readInt();
      }

   }),
   MOVE_ENTITY(30, (user, buf) -> buf.skipBytes(4)),
   MOVE_ENTITY_POS(31, (user, buf) -> buf.skipBytes(7)),
   MOVE_ENTITY_ROT(32, (user, buf) -> buf.skipBytes(6)),
   MOVE_ENTITY_POS_ROT(33, (user, buf) -> buf.skipBytes(9)),
   TELEPORT_ENTITY(34, (user, buf) -> buf.skipBytes(18)),
   ROTATE_HEAD(35, (user, buf) -> buf.skipBytes(5)),
   ENTITY_EVENT(38, (user, buf) -> buf.skipBytes(5)),
   SET_ENTITY_LINK(39, (user, buf) -> buf.skipBytes(8)),
   SET_ENTITY_DATA(40, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readEntityDataList1_4_2(buf);
   }),
   UPDATE_MOB_EFFECT(41, (user, buf) -> buf.skipBytes(8)),
   REMOVE_MOB_EFFECT(42, (user, buf) -> buf.skipBytes(5)),
   SET_EXPERIENCE(43, (user, buf) -> buf.skipBytes(8)),
   LEVEL_CHUNK(51, (user, buf) -> {
      buf.skipBytes(13);
      int x = buf.readInt();

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

   }),
   CHUNK_BLOCKS_UPDATE(52, (user, buf) -> {
      buf.skipBytes(10);
      int x = buf.readInt();

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

   }),
   BLOCK_UPDATE(53, (user, buf) -> buf.skipBytes(12)),
   BLOCK_EVENT(54, (user, buf) -> buf.skipBytes(14)),
   BLOCK_DESTRUCTION(55, (user, buf) -> buf.skipBytes(17)),
   MAP_BULK_CHUNK(56, (user, buf) -> {
      int x = buf.readShort();
      int y = buf.readInt();

      for(int i = 0; i < y; ++i) {
         buf.readByte();
      }

      for(int i = 0; i < x; ++i) {
         buf.skipBytes(12);
      }

   }),
   EXPLODE(60, (user, buf) -> {
      buf.skipBytes(28);
      int x = buf.readInt();

      for(int i = 0; i < x; ++i) {
         buf.skipBytes(3);
      }

      buf.skipBytes(12);
   }),
   LEVEL_EVENT(61, (user, buf) -> buf.skipBytes(18)),
   CUSTOM_SOUND(62, (user, buf) -> {
      PreNettyTypes.readString(buf);
      buf.skipBytes(17);
   }),
   GAME_EVENT(70, (user, buf) -> buf.skipBytes(2)),
   ADD_GLOBAL_ENTITY(71, (user, buf) -> buf.skipBytes(17)),
   OPEN_SCREEN(100, (user, buf) -> {
      buf.skipBytes(2);
      PreNettyTypes.readString(buf);
      buf.skipBytes(1);
   }),
   CONTAINER_CLOSE(101, (user, buf) -> buf.skipBytes(1)),
   CONTAINER_SET_SLOT(103, (user, buf) -> {
      buf.skipBytes(3);
      PreNettyTypes.readItemStack1_3_1(buf);
   }),
   CONTAINER_SET_CONTENT(104, (user, buf) -> {
      buf.skipBytes(1);
      int x = buf.readShort();

      for(int i = 0; i < x; ++i) {
         PreNettyTypes.readItemStack1_3_1(buf);
      }

   }),
   CONTAINER_SET_DATA(105, (user, buf) -> buf.skipBytes(5)),
   CONTAINER_ACK(106, (user, buf) -> buf.skipBytes(4)),
   SET_CREATIVE_MODE_SLOT(107, (user, buf) -> {
      buf.skipBytes(2);
      PreNettyTypes.readItemStack1_3_1(buf);
   }),
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
   BLOCK_ENTITY_DATA(132, (user, buf) -> {
      buf.skipBytes(11);
      PreNettyTypes.readTag(buf);
   }),
   AWARD_STATS(200, (user, buf) -> buf.skipBytes(5)),
   PLAYER_INFO(201, (user, buf) -> {
      PreNettyTypes.readString(buf);
      buf.skipBytes(3);
   }),
   PLAYER_ABILITIES(202, (user, buf) -> buf.skipBytes(3)),
   COMMAND_SUGGESTIONS(203, (user, buf) -> PreNettyTypes.readString(buf)),
   CUSTOM_PAYLOAD(250, (user, buf) -> {
      PreNettyTypes.readString(buf);
      short s = buf.readShort();

      for(int i = 0; i < s; ++i) {
         buf.readByte();
      }

   }),
   SHARED_KEY(252, (user, buf) -> {
      PreNettyTypes.readByteArray(buf);
      PreNettyTypes.readByteArray(buf);
   }),
   SERVER_AUTH_DATA(253, (user, buf) -> {
      PreNettyTypes.readString(buf);
      PreNettyTypes.readByteArray(buf);
      PreNettyTypes.readByteArray(buf);
   }),
   DISCONNECT(255, (user, buf) -> PreNettyTypes.readString(buf));

   private static final ClientboundPackets1_4_2[] REGISTRY = new ClientboundPackets1_4_2[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ClientboundPackets1_4_2 getPacket(int id) {
      return REGISTRY[id];
   }

   private ClientboundPackets1_4_2(int id, BiConsumer packetReader) {
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
   private static ClientboundPackets1_4_2[] $values() {
      return new ClientboundPackets1_4_2[]{KEEP_ALIVE, LOGIN, CHAT, SET_TIME, SET_EQUIPPED_ITEM, SET_DEFAULT_SPAWN_POSITION, SET_HEALTH, RESPAWN, MOVE_PLAYER_STATUS_ONLY, MOVE_PLAYER_POS, MOVE_PLAYER_ROT, PLAYER_POSITION, PLAYER_SLEEP, ANIMATE, ADD_PLAYER, SPAWN_ITEM, TAKE_ITEM_ENTITY, ADD_ENTITY, ADD_MOB, ADD_PAINTING, ADD_EXPERIENCE_ORB, SET_ENTITY_MOTION, REMOVE_ENTITIES, MOVE_ENTITY, MOVE_ENTITY_POS, MOVE_ENTITY_ROT, MOVE_ENTITY_POS_ROT, TELEPORT_ENTITY, ROTATE_HEAD, ENTITY_EVENT, SET_ENTITY_LINK, SET_ENTITY_DATA, UPDATE_MOB_EFFECT, REMOVE_MOB_EFFECT, SET_EXPERIENCE, LEVEL_CHUNK, CHUNK_BLOCKS_UPDATE, BLOCK_UPDATE, BLOCK_EVENT, BLOCK_DESTRUCTION, MAP_BULK_CHUNK, EXPLODE, LEVEL_EVENT, CUSTOM_SOUND, GAME_EVENT, ADD_GLOBAL_ENTITY, OPEN_SCREEN, CONTAINER_CLOSE, CONTAINER_SET_SLOT, CONTAINER_SET_CONTENT, CONTAINER_SET_DATA, CONTAINER_ACK, SET_CREATIVE_MODE_SLOT, UPDATE_SIGN, MAP_ITEM_DATA, BLOCK_ENTITY_DATA, AWARD_STATS, PLAYER_INFO, PLAYER_ABILITIES, COMMAND_SUGGESTIONS, CUSTOM_PAYLOAD, SHARED_KEY, SERVER_AUTH_DATA, DISCONNECT};
   }

   static {
      for(ClientboundPackets1_4_2 packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
