package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_19_4;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

public final class Types1_20 {
   public static final ParticleType PARTICLE = new ParticleType();
   public static final EntityDataTypes1_19_4 ENTITY_DATA_TYPES;
   public static final Type ENTITY_DATA;
   public static final Type ENTITY_DATA_LIST;

   static {
      ENTITY_DATA_TYPES = new EntityDataTypes1_19_4(PARTICLE);
      ENTITY_DATA = new EntityDataType(ENTITY_DATA_TYPES);
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
