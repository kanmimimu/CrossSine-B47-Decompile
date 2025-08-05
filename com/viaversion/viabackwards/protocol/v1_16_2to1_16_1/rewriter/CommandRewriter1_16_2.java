package com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.rewriter;

import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16_2 extends CommandRewriter {
   public CommandRewriter1_16_2(Protocol1_16_2To1_16_1 protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:angle", (CommandRewriter.CommandArgumentConsumer)(wrapper) -> wrapper.write(Types.VAR_INT, 0));
   }

   public @Nullable String handleArgumentType(String argumentType) {
      return argumentType.equals("minecraft:angle") ? "brigadier:string" : super.handleArgumentType(argumentType);
   }
}
