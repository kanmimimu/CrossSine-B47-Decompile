package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types;

import com.viaversion.viaversion.api.type.Type;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ItemArrayType;

public class Types1_2_4 {
   public static final Type NBT_ITEM = new ItemType();
   public static final Type NBT_ITEM_ARRAY;
   public static final Type CHUNK;

   static {
      NBT_ITEM_ARRAY = new ItemArrayType(NBT_ITEM);
      CHUNK = new ChunkType();
   }
}
