package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.IdAndData;
import io.netty.buffer.ByteBuf;

public class BlockChangeRecordArrayType extends Type {
   public BlockChangeRecordArrayType() {
      super(BlockChangeRecord[].class);
   }

   public BlockChangeRecord[] read(ByteBuf buffer) {
      int length = buffer.readUnsignedShort();
      short[] positions = new short[length];
      short[] blocks = new short[length];
      byte[] metas = new byte[length];

      for(int i = 0; i < length; ++i) {
         positions[i] = buffer.readShort();
      }

      for(int i = 0; i < length; ++i) {
         blocks[i] = buffer.readUnsignedByte();
      }

      for(int i = 0; i < length; ++i) {
         metas[i] = buffer.readByte();
      }

      BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[length];

      for(int i = 0; i < length; ++i) {
         blockChangeRecords[i] = new BlockChangeRecord1_8(positions[i] >> 12 & 15, positions[i] & 255, positions[i] >> 8 & 15, IdAndData.toRawData(blocks[i], metas[i]));
      }

      return blockChangeRecords;
   }

   public void write(ByteBuf buffer, BlockChangeRecord[] records) {
      buffer.writeShort(records.length);

      for(BlockChangeRecord record : records) {
         buffer.writeShort(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY(-1));
      }

      for(BlockChangeRecord record : records) {
         buffer.writeByte(record.getBlockId() >> 4);
      }

      for(BlockChangeRecord record : records) {
         buffer.writeByte(record.getBlockId() & 15);
      }

   }
}
