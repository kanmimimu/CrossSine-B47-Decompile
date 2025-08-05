package com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.CommandRewriter;

public class CommandRewriter1_19_4 extends CommandRewriter {
   public CommandRewriter1_19_4(Protocol protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:time", (CommandRewriter.CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.INT));
   }
}
