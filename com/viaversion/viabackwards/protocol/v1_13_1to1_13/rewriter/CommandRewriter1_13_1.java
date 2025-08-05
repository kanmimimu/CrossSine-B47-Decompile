package com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter;

import com.viaversion.viabackwards.protocol.v1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_13_1 extends CommandRewriter {
   public CommandRewriter1_13_1(Protocol1_13_1To1_13 protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:dimension", (CommandRewriter.CommandArgumentConsumer)(wrapper) -> wrapper.write(Types.VAR_INT, 0));
   }

   public @Nullable String handleArgumentType(String argumentType) {
      if (argumentType.equals("minecraft:column_pos")) {
         return "minecraft:vec2";
      } else {
         return argumentType.equals("minecraft:dimension") ? "brigadier:string" : super.handleArgumentType(argumentType);
      }
   }
}
