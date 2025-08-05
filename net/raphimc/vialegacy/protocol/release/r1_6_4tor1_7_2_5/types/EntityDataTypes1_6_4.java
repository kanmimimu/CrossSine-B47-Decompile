package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public enum EntityDataTypes1_6_4 implements com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType {
   BYTE(0, Types.BYTE),
   SHORT(1, Types.SHORT),
   INT(2, Types.INT),
   FLOAT(3, Types.FLOAT),
   STRING(4, Types1_6_4.STRING),
   ITEM(5, Types1_7_6.ITEM),
   BLOCK_POSITION(6, Types.VECTOR);

   private final int typeID;
   private final Type type;

   private EntityDataTypes1_6_4(int typeID, Type type) {
      this.typeID = typeID;
      this.type = type;
   }

   public static EntityDataTypes1_6_4 byId(int id) {
      return values()[id];
   }

   public int typeId() {
      return this.typeID;
   }

   public Type type() {
      return this.type;
   }

   // $FF: synthetic method
   private static EntityDataTypes1_6_4[] $values() {
      return new EntityDataTypes1_6_4[]{BYTE, SHORT, INT, FLOAT, STRING, ITEM, BLOCK_POSITION};
   }
}
