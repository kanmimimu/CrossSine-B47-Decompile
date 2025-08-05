package net.raphimc.vialegacy.protocol.classic.c0_0_15a_1toc0_0_16a_02.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ServerboundPacketsc0_15a implements ServerboundPacketType, PreNettyPacketType {
   LOGIN(0, (user, buf) -> buf.skipBytes(64)),
   USE_ITEM_ON(5, (user, buf) -> buf.skipBytes(8)),
   MOVE_PLAYER_POS_ROT(8, (user, buf) -> buf.skipBytes(9));

   private static final ServerboundPacketsc0_15a[] REGISTRY = new ServerboundPacketsc0_15a[256];
   private final int id;
   private final BiConsumer packetReader;

   public static ServerboundPacketsc0_15a getPacket(int id) {
      return REGISTRY[id];
   }

   private ServerboundPacketsc0_15a(int id, BiConsumer packetReader) {
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
   private static ServerboundPacketsc0_15a[] $values() {
      return new ServerboundPacketsc0_15a[]{LOGIN, USE_ITEM_ON, MOVE_PLAYER_POS_ROT};
   }

   static {
      for(ServerboundPacketsc0_15a packet : values()) {
         REGISTRY[packet.id] = packet;
      }

   }
}
