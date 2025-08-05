package com.viaversion.viarewind.api.type;

import com.viaversion.viarewind.api.type.item.ItemArrayType;
import com.viaversion.viarewind.api.type.item.ItemType;
import com.viaversion.viarewind.api.type.item.NBTType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;

public class RewindTypes {
   public static final Type INT_ARRAY = new IntArrayType();
   public static final Type SHORT_POSITION;
   public static final Type INT_POSITION;
   public static final Type BYTE_POSITION;
   public static final Type U_BYTE_POSITION;
   public static final Type COMPRESSED_NBT;
   public static final Type COMPRESSED_NBT_ITEM;
   public static final Type COMPRESSED_NBT_ITEM_ARRAY;

   static {
      SHORT_POSITION = new PositionVarYType(Types.SHORT, (value) -> (short)value);
      INT_POSITION = new PositionVarYType(Types.INT, (value) -> value);
      BYTE_POSITION = new PositionVarYType(Types.BYTE, (value) -> (byte)value);
      U_BYTE_POSITION = new PositionVarYType(Types.UNSIGNED_BYTE, (value) -> (short)value);
      COMPRESSED_NBT = new NBTType();
      COMPRESSED_NBT_ITEM = new ItemType();
      COMPRESSED_NBT_ITEM_ARRAY = new ItemArrayType();
   }
}
