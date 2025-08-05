package net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ServerboundPacketsc0_19a implements ServerboundPacketType, PreNettyPacketType {
   LOGIN(0, (user, buf) -> buf.skipBytes(129)),
   USE_ITEM_ON(5, (user, buf) -> buf.skipBytes(8)),
   MOVE_PLAYER_POS_ROT(8, (user, buf) -> buf.skipBytes(9)),
   CHAT(13, (user, buf) -> buf.skipBytes(65));

   private static final ServerboundPacketsc0_19a[] REGISTRY = new ServerboundPacketsc0_19a[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ServerboundPacketsc0_19a getPacket(int id) {
      return REGISTRY[id];
   }

   private ServerboundPacketsc0_19a(int id, BiConsumer packetReader) {
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
   private static ServerboundPacketsc0_19a[] $values() {
      return new ServerboundPacketsc0_19a[]{LOGIN, USE_ITEM_ON, MOVE_PLAYER_POS_ROT, CHAT};
   }

   static {
      for(ServerboundPacketsc0_19a packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
