package com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.rewriter.RecipeRewriter1_19_3;

public class RecipeRewriter1_19_4 extends RecipeRewriter1_19_3 {
   public RecipeRewriter1_19_4(Protocol protocol) {
      super(protocol);
   }

   public void handleCraftingShaped(PacketWrapper wrapper) {
      super.handleCraftingShaped(wrapper);
      wrapper.passthrough(Types.BOOLEAN);
   }
}
