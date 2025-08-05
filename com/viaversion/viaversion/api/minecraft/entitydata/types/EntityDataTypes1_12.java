package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;

public enum EntityDataTypes1_12 implements EntityDataType {
   BYTE(Types.BYTE),
   VAR_INT(Types.VAR_INT),
   FLOAT(Types.FLOAT),
   STRING(Types.STRING),
   COMPONENT(Types.COMPONENT),
   ITEM(Types.ITEM1_8),
   BOOLEAN(Types.BOOLEAN),
   ROTATIONS(Types.ROTATIONS),
   BLOCK_POSITION(Types.BLOCK_POSITION1_8),
   OPTIONAL_BLOCK_POSITION(Types.OPTIONAL_POSITION1_8),
   DIRECTION(Types.VAR_INT),
   OPTIONAL_UUID(Types.OPTIONAL_UUID),
   OPTIONAL_BLOCK_STATE(Types.VAR_INT),
   COMPOUND_TAG(Types.NAMED_COMPOUND_TAG);

   private final Type type;

   private EntityDataTypes1_12(Type type) {
      this.type = type;
   }

   public static EntityDataTypes1_12 byId(int id) {
      return values()[id];
   }

   public int typeId() {
      return this.ordinal();
   }

   public Type type() {
      return this.type;
   }

   // $FF: synthetic method
   private static EntityDataTypes1_12[] $values() {
      return new EntityDataTypes1_12[]{BYTE, VAR_INT, FLOAT, STRING, COMPONENT, ITEM, BOOLEAN, ROTATIONS, BLOCK_POSITION, OPTIONAL_BLOCK_POSITION, DIRECTION, OPTIONAL_UUID, OPTIONAL_BLOCK_STATE, COMPOUND_TAG};
   }
}
