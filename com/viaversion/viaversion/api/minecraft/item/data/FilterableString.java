package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class FilterableString extends Filterable {
   public static final Type TYPE;
   public static final Type ARRAY_TYPE;

   public FilterableString(String raw, @Nullable String filtered) {
      super(raw, filtered);
   }

   static {
      TYPE = new Filterable.FilterableType(Types.STRING, Types.OPTIONAL_STRING, FilterableString.class) {
         protected FilterableString create(String raw, String filtered) {
            return new FilterableString(raw, filtered);
         }
      };
      ARRAY_TYPE = new ArrayType(TYPE);
   }
}
