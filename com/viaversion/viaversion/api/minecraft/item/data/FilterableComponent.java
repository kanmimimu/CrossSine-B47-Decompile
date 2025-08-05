package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class FilterableComponent extends Filterable {
   public static final Type TYPE;
   public static final Type ARRAY_TYPE;

   public FilterableComponent(Tag raw, @Nullable Tag filtered) {
      super(raw, filtered);
   }

   static {
      TYPE = new Filterable.FilterableType(Types.TAG, Types.OPTIONAL_TAG, FilterableComponent.class) {
         protected FilterableComponent create(Tag raw, Tag filtered) {
            return new FilterableComponent(raw, filtered);
         }
      };
      ARRAY_TYPE = new ArrayType(TYPE);
   }
}
