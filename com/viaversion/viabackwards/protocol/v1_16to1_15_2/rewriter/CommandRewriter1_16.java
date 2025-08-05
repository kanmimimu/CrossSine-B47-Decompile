package com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter;

import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16 extends CommandRewriter {
   public CommandRewriter1_16(Protocol1_16To1_15_2 protocol) {
      super(protocol);
   }

   public @Nullable String handleArgumentType(String argumentType) {
      return argumentType.equals("minecraft:uuid") ? "minecraft:game_profile" : super.handleArgumentType(argumentType);
   }
}
