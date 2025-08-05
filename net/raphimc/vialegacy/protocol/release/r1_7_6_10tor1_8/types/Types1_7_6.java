package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;

public class Types1_7_6 {
   public static final Type INT_ARRAY = new IntArrayType();
   public static final Type NBT = new NBTType();
   public static final Type ITEM = new ItemType();
   public static final Type ITEM_ARRAY;
   public static final Type ENTITY_DATA;
   public static final Type ENTITY_DATA_LIST;
   public static final Type BLOCK_CHANGE_RECORD_ARRAY;
   public static final Type BLOCK_POSITION_BYTE;
   public static final Type BLOCK_POSITION_UBYTE;
   public static final Type BLOCK_POSITION_SHORT;
   public static final Type BLOCK_POSITION_INT;
   public static final Type CHUNK_WITH_SKYLIGHT;
   public static final Type CHUNK_WITHOUT_SKYLIGHT;
   public static final Type CHUNK_BULK;

   public static Type getChunk(Environment dimension) {
      return dimension == Environment.NORMAL ? CHUNK_WITH_SKYLIGHT : CHUNK_WITHOUT_SKYLIGHT;
   }

   static {
      ITEM_ARRAY = new ItemArrayType(ITEM);
      ENTITY_DATA = new EntityDataType();
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
      BLOCK_CHANGE_RECORD_ARRAY = new BlockChangeRecordArrayType();
      BLOCK_POSITION_BYTE = new BlockPositionVarYType(Types.BYTE, (i) -> (byte)i);
      BLOCK_POSITION_UBYTE = new BlockPositionVarYType(Types.UNSIGNED_BYTE, (i) -> (short)i);
      BLOCK_POSITION_SHORT = new BlockPositionVarYType(Types.SHORT, (i) -> (short)i);
      BLOCK_POSITION_INT = new BlockPositionVarYType(Types.INT, (i) -> i);
      CHUNK_WITH_SKYLIGHT = new ChunkType(true);
      CHUNK_WITHOUT_SKYLIGHT = new ChunkType(false);
      CHUNK_BULK = new BulkChunkType();
   }
}
