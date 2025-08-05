package com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter;

import com.viaversion.viabackwards.protocol.v1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_14 extends CommandRewriter {
   public CommandRewriter1_14(Protocol1_14To1_13_2 protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:nbt_tag", (CommandRewriter.CommandArgumentConsumer)(wrapper) -> wrapper.write(Types.VAR_INT, 2));
      this.parserHandlers.put("minecraft:time", (CommandRewriter.CommandArgumentConsumer)(wrapper) -> {
         wrapper.write(Types.BYTE, (byte)1);
         wrapper.write(Types.INT, 0);
      });
   }

   public @Nullable String handleArgumentType(String argumentType) {
      String var10000;
      switch (argumentType) {
         case "minecraft:nbt_compound_tag":
            var10000 = "minecraft:nbt";
            break;
         case "minecraft:nbt_tag":
            var10000 = "brigadier:string";
            break;
         case "minecraft:time":
            var10000 = "brigadier:integer";
            break;
         default:
            var10000 = super.handleArgumentType(argumentType);
      }

      return var10000;
   }
}
