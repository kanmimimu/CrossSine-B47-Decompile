package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class TranslationComponent extends ATextComponent {
   private static final Function NULL_TRANSLATOR = (s) -> null;
   private static final Pattern ARG_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
   private String key;
   private Object[] args;
   @Nullable
   private String fallback;
   private Function translator;

   public TranslationComponent(String key, List args) {
      this.translator = NULL_TRANSLATOR;
      this.key = key;
      this.args = args.toArray();
   }

   public TranslationComponent(String key, Object... args) {
      this.translator = NULL_TRANSLATOR;
      this.key = key;
      this.args = args;
   }

   public String getKey() {
      return this.key;
   }

   public TranslationComponent setKey(String key) {
      this.key = key;
      return this;
   }

   public Object[] getArgs() {
      return this.args;
   }

   public TranslationComponent setArgs(Object[] args) {
      this.args = args;
      return this;
   }

   @Nullable
   public String getFallback() {
      return this.fallback;
   }

   public TranslationComponent setFallback(@Nullable String fallback) {
      this.fallback = fallback;
      return this;
   }

   public TranslationComponent setTranslator(@Nullable Function translator) {
      if (translator == null) {
         this.translator = NULL_TRANSLATOR;
      } else {
         this.translator = translator;
      }

      return this;
   }

   public ATextComponent resolveIntoComponents() {
      List<ATextComponent> components = new ArrayList();
      String translated = (String)this.translator.apply(this.key);
      if (translated == null) {
         translated = this.fallback;
      }

      if (translated == null) {
         translated = this.key;
      }

      Matcher matcher = ARG_PATTERN.matcher(translated);
      int argIndex = 0;
      int start = 0;

      while(matcher.find(start)) {
         int matchStart = matcher.start();
         int matchEnd = matcher.end();
         if (matchStart > start) {
            components.add(new StringComponent(String.format(translated.substring(start, matchStart))));
         }

         start = matchEnd;
         String argType = matcher.group(2);
         String match = translated.substring(matchStart, matchEnd);
         if (argType.equals("%") && match.equals("%%")) {
            components.add(new StringComponent("%"));
         } else {
            if (!argType.equals("s")) {
               throw new IllegalStateException("Unsupported format: '" + match + "'");
            }

            String rawIndex = matcher.group(1);
            int index;
            if (rawIndex == null) {
               index = argIndex++;
            } else {
               index = Integer.parseInt(rawIndex) - 1;
            }

            if (index < this.args.length) {
               Object arg = this.args[index];
               if (arg instanceof ATextComponent) {
                  components.add((ATextComponent)arg);
               } else if (arg == null) {
                  components.add(new StringComponent("null"));
               } else {
                  components.add(new StringComponent(arg.toString()));
               }
            }
         }
      }

      if (start < translated.length()) {
         components.add(new StringComponent(String.format(translated.substring(start))));
      }

      ATextComponent out = new StringComponent();
      out.setStyle(this.getStyle());
      components.forEach(out::append);
      return out;
   }

   public String asLegacyFormatString() {
      return this.resolveIntoComponents().asLegacyFormatString();
   }

   public String asSingleString() {
      return this.resolveIntoComponents().asUnformattedString();
   }

   public ATextComponent copy() {
      return this.putMetaCopy(this.shallowCopy());
   }

   public ATextComponent shallowCopy() {
      Object[] copyArgs = new Object[this.args.length];

      for(int i = 0; i < this.args.length; ++i) {
         Object arg = this.args[i];
         if (arg instanceof ATextComponent) {
            copyArgs[i] = ((ATextComponent)arg).copy();
         } else {
            copyArgs[i] = arg;
         }
      }

      TranslationComponent copy = new TranslationComponent(this.key, copyArgs);
      copy.translator = this.translator;
      return copy.setStyle(this.getStyle().copy());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TranslationComponent that = (TranslationComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.key, that.key) && Arrays.equals(this.args, that.args) && Objects.equals(this.translator, that.translator);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = Objects.hash(new Object[]{this.getSiblings(), this.getStyle(), this.key, this.translator});
      result = 31 * result + Arrays.hashCode(this.args);
      return result;
   }

   public String toString() {
      return ToString.of((Object)this).add("siblings", this.getSiblings(), (siblings) -> !siblings.isEmpty()).add("style", this.getStyle(), (style) -> !style.isEmpty()).add("key", this.key).add("args", this.args, (args) -> args.length > 0, Arrays::toString).add("translator", this.translator, (translator) -> translator != NULL_TRANSLATOR).toString();
   }
}
