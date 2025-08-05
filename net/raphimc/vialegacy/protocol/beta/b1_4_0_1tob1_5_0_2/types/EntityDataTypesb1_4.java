package net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.types;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;

public enum EntityDataTypesb1_4 implements com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType {
   BYTE(0, Types.BYTE),
   SHORT(1, Types.SHORT),
   INT(2, Types.INT),
   FLOAT(3, Types.FLOAT),
   STRING(4, Typesb1_7_0_3.STRING),
   ITEM(5, new Type(Item.class) {
      public Item read(ByteBuf buffer) {
         Types1_3_1.NBTLESS_ITEM.read(buffer);
         Types.VECTOR.read(buffer);
         return null;
      }

      public void write(ByteBuf buffer, Item value) {
         throw new UnsupportedOperationException();
      }
   }),
   BLOCK_POSITION(6, Types.VECTOR);

   final int typeID;
   final Type type;

   EntityDataTypesb1_4(int typeID, Type type) {
      this.typeID = typeID;
      this.type = type;
   }

   public static EntityDataTypesb1_4 byId(int id) {
      return values()[id];
   }

   public int typeId() {
      return this.typeID;
   }

   public Type type() {
      return this.type;
   }

   // $FF: synthetic method
   static EntityDataTypesb1_4[] $values() {
      return new EntityDataTypesb1_4[]{BYTE, SHORT, INT, FLOAT, STRING, ITEM, BLOCK_POSITION};
   }
}
