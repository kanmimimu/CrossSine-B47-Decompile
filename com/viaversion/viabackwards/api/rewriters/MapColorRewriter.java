package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;

public final class MapColorRewriter {
   public static PacketHandler getRewriteHandler(IdRewriteFunction rewriter) {
      return (wrapper) -> {
         int iconCount = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < iconCount; ++i) {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.OPTIONAL_COMPONENT);
         }

         short columns = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         if (columns >= 1) {
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            byte[] data = (byte[])wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);

            for(int i = 0; i < data.length; ++i) {
               int color = data[i] & 255;
               int mappedColor = rewriter.rewrite(color);
               if (mappedColor != -1) {
                  data[i] = (byte)mappedColor;
               }
            }

         }
      };
   }
}
