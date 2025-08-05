package net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ServerboundPacketsa1_0_15 implements ServerboundPacketType, PreNettyPacketType {
   KEEP_ALIVE(0, (user, buf) -> {
   }),
   LOGIN(1, (user, buf) -> {
      buf.skipBytes(4);
      PreNettyTypes.readUTF(buf);
      PreNettyTypes.readUTF(buf);
   }),
   CHAT(3, (user, buf) -> PreNettyTypes.readUTF(buf)),
   MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
   MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
   MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
   MOVE_PLAYER_POS_ROT(13, (user, buf) -> buf.skipBytes(41)),
   PLAYER_ACTION(14, (user, buf) -> buf.skipBytes(11)),
   USE_ITEM_ON(15, (user, buf) -> buf.skipBytes(12)),
   SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(6)),
   SWING(18, (user, buf) -> buf.skipBytes(5)),
   SPAWN_ITEM(21, (user, buf) -> buf.skipBytes(22)),
   DISCONNECT(255, (user, buf) -> PreNettyTypes.readUTF(buf));

   private static final ServerboundPacketsa1_0_15[] REGISTRY = new ServerboundPacketsa1_0_15[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ServerboundPacketsa1_0_15 getPacket(int id) {
      return REGISTRY[id];
   }

   private ServerboundPacketsa1_0_15(int id, BiConsumer packetReader) {
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
   private static ServerboundPacketsa1_0_15[] $values() {
      return new ServerboundPacketsa1_0_15[]{KEEP_ALIVE, LOGIN, CHAT, MOVE_PLAYER_STATUS_ONLY, MOVE_PLAYER_POS, MOVE_PLAYER_ROT, MOVE_PLAYER_POS_ROT, PLAYER_ACTION, USE_ITEM_ON, SET_CARRIED_ITEM, SWING, SPAWN_ITEM, DISCONNECT};
   }

   static {
      for(ServerboundPacketsa1_0_15 packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
