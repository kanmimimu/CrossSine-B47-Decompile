package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;

abstract class BaseItemArrayType extends Type {
   protected BaseItemArrayType() {
      super(Item[].class);
   }

   protected BaseItemArrayType(String typeName) {
      super(typeName, Item[].class);
   }

   public Class getBaseClass() {
      return BaseItemArrayType.class;
   }
}
