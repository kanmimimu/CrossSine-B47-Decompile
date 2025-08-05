package com.viaversion.viabackwards.utils;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.LegacyStringDeserializer;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public final class ChatUtil {
   static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
   static final Pattern UNUSED_COLOR_PATTERN_PREFIX = Pattern.compile("(?>(?>§[0-fk-or])*(§r))|(?>(?>§[0-f])*(§[0-f]))");

   public static String removeUnusedColor(String legacy, char defaultColor) {
      return removeUnusedColor(legacy, defaultColor, false);
   }

   public static String legacyToJsonString(String legacy, String translation, boolean itemData) {
      return legacyToJsonString(legacy, (Consumer)((text) -> {
         text.append(" ");
         text.append((ATextComponent)(new TranslationComponent(translation, new Object[0])));
      }), itemData);
   }

   public static String legacyToJsonString(String legacy, Consumer consumer, boolean itemData) {
      ATextComponent component = LegacyStringDeserializer.parse(legacy, true);
      consumer.accept(component);
      if (itemData) {
         component.setParentStyle((new Style()).setItalic(false));
      }

      return SerializerVersion.V1_12.toString(component);
   }

   public static String fromLegacy(String legacy, char defaultColor, int limit) {
      return fromLegacy(legacy, defaultColor, limit, false);
   }

   public static String fromLegacyPrefix(String legacy, char defaultColor, int limit) {
      return fromLegacy(legacy, defaultColor, limit, true);
   }

   public static String fromLegacy(String legacy, char defaultColor, int limit, boolean isPrefix) {
      legacy = removeUnusedColor(legacy, defaultColor, isPrefix);
      if (legacy.length() > limit) {
         legacy = legacy.substring(0, limit);
      }

      if (legacy.endsWith("§")) {
         legacy = legacy.substring(0, legacy.length() - 1);
      }

      return legacy;
   }

   public static String removeUnusedColor(String legacy, char defaultColor, boolean isPrefix) {
      if (legacy == null) {
         return null;
      } else {
         Pattern pattern = isPrefix ? UNUSED_COLOR_PATTERN_PREFIX : UNUSED_COLOR_PATTERN;
         legacy = pattern.matcher(legacy).replaceAll("$1$2");
         StringBuilder builder = new StringBuilder();
         ChatFormattingState builderState = new ChatFormattingState(defaultColor);
         ChatFormattingState lastState = new ChatFormattingState(defaultColor);

         for(int i = 0; i < legacy.length(); ++i) {
            char current = legacy.charAt(i);
            if (current == 167 && i != legacy.length() - 1) {
               ++i;
               current = legacy.charAt(i);
               lastState.processNextControlChar(current);
            } else {
               if (!lastState.equals(builderState)) {
                  lastState.appendTo(builder);
                  builderState = lastState.copy();
               }

               builder.append(current);
            }
         }

         if (isPrefix && !lastState.equals(builderState)) {
            lastState.appendTo(builder);
         }

         return builder.toString();
      }
   }

   private static class ChatFormattingState {
      final Set formatting;
      final char defaultColor;
      char color;

      ChatFormattingState(char defaultColor) {
         this(new HashSet(), defaultColor, defaultColor);
      }

      public ChatFormattingState(Set formatting, char defaultColor, char color) {
         this.formatting = formatting;
         this.defaultColor = defaultColor;
         this.color = color;
      }

      void setColor(char newColor) {
         this.formatting.clear();
         this.color = newColor;
      }

      public ChatFormattingState copy() {
         return new ChatFormattingState(new HashSet(this.formatting), this.defaultColor, this.color);
      }

      public void appendTo(StringBuilder builder) {
         builder.append('§').append(this.color);

         for(Character formatCharacter : this.formatting) {
            builder.append('§').append(formatCharacter);
         }

      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            ChatFormattingState that = (ChatFormattingState)o;
            return this.defaultColor == that.defaultColor && this.color == that.color && Objects.equals(this.formatting, that.formatting);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.formatting, this.defaultColor, this.color});
      }

      public void processNextControlChar(char controlChar) {
         if (controlChar == 'r') {
            this.setColor(this.defaultColor);
         } else if (controlChar != 'l' && controlChar != 'm' && controlChar != 'n' && controlChar != 'o') {
            this.setColor(controlChar);
         } else {
            this.formatting.add(controlChar);
         }
      }
   }
}
