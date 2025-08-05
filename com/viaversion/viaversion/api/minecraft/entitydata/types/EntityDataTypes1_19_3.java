package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

public final class EntityDataTypes1_19_3 extends AbstractEntityDataTypes {
   public final EntityDataType byteType;
   public final EntityDataType varIntType;
   public final EntityDataType longType;
   public final EntityDataType floatType;
   public final EntityDataType stringType;
   public final EntityDataType componentType;
   public final EntityDataType optionalComponentType;
   public final EntityDataType itemType;
   public final EntityDataType booleanType;
   public final EntityDataType rotationsType;
   public final EntityDataType blockPositionType;
   public final EntityDataType optionalBlockPositionType;
   public final EntityDataType directionType;
   public final EntityDataType optionalUUIDType;
   public final EntityDataType optionalBlockStateType;
   public final EntityDataType compoundTagType;
   public final EntityDataType particleType;
   public final EntityDataType villagerDatatType;
   public final EntityDataType optionalVarIntType;
   public final EntityDataType poseType;
   public final EntityDataType catVariantType;
   public final EntityDataType frogVariantType;
   public final EntityDataType optionalGlobalPosition;
   public final EntityDataType paintingVariantType;

   public EntityDataTypes1_19_3(ParticleType particleType) {
      super(24);
      this.byteType = this.add(0, Types.BYTE);
      this.varIntType = this.add(1, Types.VAR_INT);
      this.longType = this.add(2, Types.VAR_LONG);
      this.floatType = this.add(3, Types.FLOAT);
      this.stringType = this.add(4, Types.STRING);
      this.componentType = this.add(5, Types.COMPONENT);
      this.optionalComponentType = this.add(6, Types.OPTIONAL_COMPONENT);
      this.itemType = this.add(7, Types.ITEM1_13_2);
      this.booleanType = this.add(8, Types.BOOLEAN);
      this.rotationsType = this.add(9, Types.ROTATIONS);
      this.blockPositionType = this.add(10, Types.BLOCK_POSITION1_14);
      this.optionalBlockPositionType = this.add(11, Types.OPTIONAL_POSITION_1_14);
      this.directionType = this.add(12, Types.VAR_INT);
      this.optionalUUIDType = this.add(13, Types.OPTIONAL_UUID);
      this.optionalBlockStateType = this.add(14, Types.VAR_INT);
      this.compoundTagType = this.add(15, Types.NAMED_COMPOUND_TAG);
      this.villagerDatatType = this.add(17, Types.VILLAGER_DATA);
      this.optionalVarIntType = this.add(18, Types.OPTIONAL_VAR_INT);
      this.poseType = this.add(19, Types.VAR_INT);
      this.catVariantType = this.add(20, Types.VAR_INT);
      this.frogVariantType = this.add(21, Types.VAR_INT);
      this.optionalGlobalPosition = this.add(22, Types.OPTIONAL_GLOBAL_POSITION);
      this.paintingVariantType = this.add(23, Types.VAR_INT);
      this.particleType = this.add(16, particleType);
   }
}
