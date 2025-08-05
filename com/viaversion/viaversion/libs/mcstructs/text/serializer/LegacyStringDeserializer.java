package com.viaversion.viaversion.libs.mcstructs.text.serializer;

import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import java.util.function.Function;
import java.util.function.Supplier;

public class LegacyStringDeserializer {
   public static ATextComponent parse(String s, boolean unknownWhite) {
      return parse(s, '§', unknownWhite);
   }

   public static ATextComponent parse(String s, char colorChar, boolean unknownWhite) {
      return parse(s, colorChar, (c) -> {
         TextFormatting formatting = TextFormatting.getByCode(c);
         if (formatting == null) {
            return unknownWhite ? TextFormatting.WHITE : null;
         } else {
            return formatting;
         }
      });
   }

   public static ATextComponent parse(String s, char colorChar, Function formattingResolver) {
      return parse(s, colorChar, Style::new, formattingResolver);
   }

   public static ATextComponent parse(String s, char colorChar, Supplier styleSupplier, Function formattingResolver) {
      char[] chars = s.toCharArray();
      Style style = (Style)styleSupplier.get();
      StringBuilder currentPart = new StringBuilder();
      ATextComponent out = new StringComponent("");

      for(int i = 0; i < chars.length; ++i) {
         char c = chars[i];
         if (c == colorChar) {
            if (i + 1 < chars.length) {
               ++i;
               char format = chars[i];
               TextFormatting formatting = (TextFormatting)formattingResolver.apply(format);
               if (formatting != null) {
                  if (currentPart.length() != 0) {
                     out.append((new StringComponent(currentPart.toString())).setStyle(style.copy()));
                     currentPart = new StringBuilder();
                     if (formatting.isColor() || TextFormatting.RESET.equals(formatting)) {
                        style = (Style)styleSupplier.get();
                     }
                  }

                  style.setFormatting(formatting);
               }
            }
         } else {
            currentPart.append(c);
         }
      }

      if (currentPart.length() != 0) {
         out.append((new StringComponent(currentPart.toString())).setStyle(style));
      }

      if (out.getSiblings().size() == 1) {
         return (ATextComponent)out.getSiblings().get(0);
      } else {
         return out;
      }
   }
}
