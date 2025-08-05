package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;

public final class EntityDataTypes1_20_5 extends AbstractEntityDataTypes {
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
   public final EntityDataType blockStateType;
   public final EntityDataType optionalBlockStateType;
   public final EntityDataType compoundTagType;
   public final EntityDataType particleType;
   public final EntityDataType particlesType;
   public final EntityDataType villagerDatatType;
   public final EntityDataType optionalVarIntType;
   public final EntityDataType poseType;
   public final EntityDataType catVariantType;
   public final EntityDataType wolfVariantType;
   public final EntityDataType frogVariantType;
   public final EntityDataType optionalGlobalPosition;
   public final EntityDataType paintingVariantType;
   public final EntityDataType snifferState;
   public final EntityDataType armadilloState;
   public final EntityDataType vector3FType;
   public final EntityDataType quaternionType;

   public EntityDataTypes1_20_5(ParticleType particleType, ArrayType particlesType) {
      super(31);
      this.byteType = this.add(0, Types.BYTE);
      this.varIntType = this.add(1, Types.VAR_INT);
      this.longType = this.add(2, Types.VAR_LONG);
      this.floatType = this.add(3, Types.FLOAT);
      this.stringType = this.add(4, Types.STRING);
      this.componentType = this.add(5, Types.TAG);
      this.optionalComponentType = this.add(6, Types.OPTIONAL_TAG);
      this.itemType = this.add(7, Types1_20_5.ITEM);
      this.booleanType = this.add(8, Types.BOOLEAN);
      this.rotationsType = this.add(9, Types.ROTATIONS);
      this.blockPositionType = this.add(10, Types.BLOCK_POSITION1_14);
      this.optionalBlockPositionType = this.add(11, Types.OPTIONAL_POSITION_1_14);
      this.directionType = this.add(12, Types.VAR_INT);
      this.optionalUUIDType = this.add(13, Types.OPTIONAL_UUID);
      this.blockStateType = this.add(14, Types.VAR_INT);
      this.optionalBlockStateType = this.add(15, Types.VAR_INT);
      this.compoundTagType = this.add(16, Types.COMPOUND_TAG);
      this.villagerDatatType = this.add(19, Types.VILLAGER_DATA);
      this.optionalVarIntType = this.add(20, Types.OPTIONAL_VAR_INT);
      this.poseType = this.add(21, Types.VAR_INT);
      this.catVariantType = this.add(22, Types.VAR_INT);
      this.wolfVariantType = this.add(23, Types.VAR_INT);
      this.frogVariantType = this.add(24, Types.VAR_INT);
      this.optionalGlobalPosition = this.add(25, Types.OPTIONAL_GLOBAL_POSITION);
      this.paintingVariantType = this.add(26, Types.VAR_INT);
      this.snifferState = this.add(27, Types.VAR_INT);
      this.armadilloState = this.add(28, Types.VAR_INT);
      this.vector3FType = this.add(29, Types.VECTOR3F);
      this.quaternionType = this.add(30, Types.QUATERNION);
      this.particleType = this.add(17, particleType);
      this.particlesType = this.add(18, particlesType);
   }
}
