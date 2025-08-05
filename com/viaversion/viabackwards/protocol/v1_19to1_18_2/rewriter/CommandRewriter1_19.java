package com.viaversion.viabackwards.protocol.v1_19to1_18_2.rewriter;

import com.viaversion.viabackwards.protocol.v1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.CommandRewriter;

public final class CommandRewriter1_19 extends CommandRewriter {
   public CommandRewriter1_19(Protocol1_19To1_18_2 protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:template_mirror", (CommandRewriter.CommandArgumentConsumer)(wrapper) -> wrapper.write(Types.VAR_INT, 0));
      this.parserHandlers.put("minecraft:template_rotation", (CommandRewriter.CommandArgumentConsumer)(wrapper) -> wrapper.write(Types.VAR_INT, 0));
   }
}
