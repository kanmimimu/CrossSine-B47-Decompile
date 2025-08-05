package net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ServerboundPacketsa1_1_0 implements ServerboundPacketType, PreNettyPacketType {
   KEEP_ALIVE(0, (user, buf) -> {
   }),
   LOGIN(1, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readUTF(buf);
      PreNettyTypes.readUTF(buf);
   }),
   HANDSHAKE(2, (user, buf) -> PreNettyTypes.readUTF(buf)),
   CHAT(3, (user, buf) -> PreNettyTypes.readUTF(buf)),
   PLAYER_INVENTORY(5, (user, buf) -> {
      buf.skipBytes(4);
      int x = buf.readShort();

      for(int i = 0; i < x; ++i) {
         PreNettyTypes.readItemStackb1_2(buf);
      }

   }),
   MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
   MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
   MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
   MOVE_PLAYER_POS_ROT(13, (user, buf) -> buf.skipBytes(41)),
   PLAYER_ACTION(14, (user, buf) -> buf.skipBytes(11)),
   USE_ITEM_ON(15, (user, buf) -> buf.skipBytes(12)),
   SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(6)),
   SWING(18, (user, buf) -> buf.skipBytes(5)),
   SPAWN_ITEM(21, (user, buf) -> buf.skipBytes(22)),
   BLOCK_ENTITY_DATA(59, (user, buf) -> {
      buf.skipBytes(10);
      int x = buf.readUnsignedShort();

      for(int i = 0; i < x; ++i) {
         buf.readByte();
      }

   }),
   DISCONNECT(255, (user, buf) -> PreNettyTypes.readUTF(buf));

   private static final ServerboundPacketsa1_1_0[] REGISTRY = new ServerboundPacketsa1_1_0[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ServerboundPacketsa1_1_0 getPacket(int id) {
      return REGISTRY[id];
   }

   private ServerboundPacketsa1_1_0(int id, BiConsumer packetReader) {
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
   private static ServerboundPacketsa1_1_0[] $values() {
      return new ServerboundPacketsa1_1_0[]{KEEP_ALIVE, LOGIN, HANDSHAKE, CHAT, PLAYER_INVENTORY, MOVE_PLAYER_STATUS_ONLY, MOVE_PLAYER_POS, MOVE_PLAYER_ROT, MOVE_PLAYER_POS_ROT, PLAYER_ACTION, USE_ITEM_ON, SET_CARRIED_ITEM, SWING, SPAWN_ITEM, BLOCK_ENTITY_DATA, DISCONNECT};
   }

   static {
      for(ServerboundPacketsa1_1_0 packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
