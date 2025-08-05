package com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;

public class RecipeRewriter1_20_2 extends RecipeRewriter1_19_4 {
   public RecipeRewriter1_20_2(Protocol protocol) {
      super(protocol);
   }

   protected Type itemType() {
      return Types.ITEM1_20_2;
   }

   protected Type itemArrayType() {
      return Types.ITEM1_20_2_ARRAY;
   }
}
