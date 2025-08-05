package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_21;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.types.item.ItemCostType1_20_5;
import com.viaversion.viaversion.api.type.types.item.ItemType1_20_5;
import com.viaversion.viaversion.api.type.types.item.StructuredDataType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import java.util.Objects;

public final class Types1_21 {
   public static final StructuredDataType STRUCTURED_DATA = new StructuredDataType();
   public static final Type STRUCTURED_DATA_ARRAY;
   public static final ItemType1_20_5 ITEM;
   public static final Type OPTIONAL_ITEM;
   public static final Type ITEM_ARRAY;
   public static final Type ITEM_COST;
   public static final Type OPTIONAL_ITEM_COST;
   public static final ParticleType PARTICLE;
   public static final ArrayType PARTICLES;
   public static final EntityDataTypes1_21 ENTITY_DATA_TYPES;
   public static final Type ENTITY_DATA;
   public static final Type ENTITY_DATA_LIST;

   static {
      STRUCTURED_DATA_ARRAY = new ArrayType(STRUCTURED_DATA);
      ITEM = new ItemType1_20_5(STRUCTURED_DATA);
      ItemType1_20_5 var10002 = ITEM;
      Objects.requireNonNull(var10002);
      OPTIONAL_ITEM = var10002.new OptionalItemType();
      ITEM_ARRAY = new ArrayType(ITEM);
      ITEM_COST = new ItemCostType1_20_5(STRUCTURED_DATA_ARRAY);
      OPTIONAL_ITEM_COST = new ItemCostType1_20_5.OptionalItemCostType(ITEM_COST);
      PARTICLE = new ParticleType();
      PARTICLES = new ArrayType(PARTICLE);
      ENTITY_DATA_TYPES = new EntityDataTypes1_21(PARTICLE, PARTICLES);
      ENTITY_DATA = new EntityDataType(ENTITY_DATA_TYPES);
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
