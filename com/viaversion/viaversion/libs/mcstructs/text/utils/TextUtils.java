package com.viaversion.viaversion.libs.mcstructs.text.utils;

import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.KeybindComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.EntityHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class TextUtils {
   private static final String URL_PATTERN = "(?:https?://)?[\\w._-]+\\.\\w{2,}(?:/\\S*)?";

   public static ATextComponent makeURLsClickable(ATextComponent component) {
      return replace(component, "(?:https?://)?[\\w._-]+\\.\\w{2,}(?:/\\S*)?", (comp) -> {
         comp.getStyle().setClickEvent(new ClickEvent(ClickEventAction.OPEN_URL, comp.asSingleString()));
         return comp;
      });
   }

   public static ATextComponent replace(ATextComponent component, Function replaceFunction) {
      ATextComponent out = component.shallowCopy();
      out = (ATextComponent)replaceFunction.apply(out);
      if (out instanceof TranslationComponent) {
         Object[] args = ((TranslationComponent)out).getArgs();

         for(int i = 0; i < args.length; ++i) {
            if (args[i] instanceof ATextComponent) {
               args[i] = replace((ATextComponent)args[i], replaceFunction);
            }
         }
      }

      for(ATextComponent sibling : component.getSiblings()) {
         out.append(replace(sibling, replaceFunction));
      }

      return out;
   }

   public static ATextComponent replace(ATextComponent component, String searchRegex, Function replaceFunction) {
      Pattern pattern = Pattern.compile(searchRegex);
      ATextComponent out;
      if (component instanceof StringComponent) {
         String text = component.asSingleString();
         Matcher matcher = pattern.matcher(text);
         List<ATextComponent> parts = new ArrayList();

         int last;
         for(last = 0; matcher.find(); last = matcher.end()) {
            int start = matcher.start();
            String match = matcher.group();
            if (start > last) {
               parts.add((new StringComponent(text.substring(last, start))).setStyle(component.getStyle().copy()));
            }

            ATextComponent replace = (ATextComponent)replaceFunction.apply((new StringComponent(match)).setStyle(component.getStyle().copy()));
            if (replace != null) {
               parts.add(replace);
            }
         }

         if (last < text.length()) {
            parts.add((new StringComponent(text.substring(last))).setStyle(component.getStyle().copy()));
         }

         if (parts.size() > 1) {
            out = new StringComponent("");

            for(ATextComponent part : parts) {
               out.append(part);
            }
         } else if (parts.size() == 1) {
            out = ((ATextComponent)parts.get(0)).shallowCopy();
         } else {
            out = component.shallowCopy();
         }
      } else {
         out = component.shallowCopy();
      }

      for(ATextComponent sibling : component.getSiblings()) {
         ATextComponent replace = replace(sibling, searchRegex, replaceFunction);
         out.append(replace);
      }

      return out;
   }

   public static ATextComponent replaceRGBColors(ATextComponent component) {
      ATextComponent out = component.copy();
      out.forEach((comp) -> {
         if (comp.getStyle().getColor() != null && comp.getStyle().getColor().isRGBColor()) {
            comp.getStyle().setFormatting(TextFormatting.getClosestFormattingColor(comp.getStyle().getColor().getRgbValue()));
         }

      });
      return out;
   }

   public static ATextComponent join(ATextComponent separator, ATextComponent... components) {
      if (components.length == 0) {
         return new StringComponent("");
      } else if (components.length == 1) {
         return components[0].copy();
      } else {
         ATextComponent out = null;

         for(ATextComponent component : components) {
            if (out == null) {
               out = (new StringComponent("")).append(component.copy());
            } else {
               out.append(separator.copy()).append(component.copy());
            }
         }

         return out;
      }
   }

   public static void iterateAll(ATextComponent component, Consumer consumer) {
      consumer.accept(component);
      if (component instanceof TranslationComponent) {
         TranslationComponent translationComponent = (TranslationComponent)component;

         for(Object arg : translationComponent.getArgs()) {
            if (arg instanceof ATextComponent) {
               iterateAll((ATextComponent)arg, consumer);
            }
         }
      }

      if (component.getStyle().getHoverEvent() != null) {
         AHoverEvent hoverEvent = component.getStyle().getHoverEvent();
         if (hoverEvent instanceof TextHoverEvent) {
            iterateAll(((TextHoverEvent)hoverEvent).getText(), consumer);
         } else if (hoverEvent instanceof EntityHoverEvent) {
            ATextComponent name = ((EntityHoverEvent)hoverEvent).getName();
            if (name != null) {
               iterateAll(name, consumer);
            }
         }
      }

      for(ATextComponent sibling : component.getSiblings()) {
         iterateAll(sibling, consumer);
      }

   }

   public static void setTranslator(ATextComponent component, @Nullable Function translator) {
      setTranslator(component, translator, translator);
   }

   public static void setTranslator(ATextComponent component, @Nullable Function textTranslator, @Nullable Function keyTranslator) {
      iterateAll(component, (comp) -> {
         if (comp instanceof TranslationComponent) {
            TranslationComponent translationComponent = (TranslationComponent)comp;
            translationComponent.setTranslator(textTranslator);
         } else if (comp instanceof KeybindComponent) {
            KeybindComponent keybindComponent = (KeybindComponent)comp;
            keybindComponent.setTranslator(keyTranslator);
         }

      });
   }

   public static ATextComponent[] split(ATextComponent component, String split, boolean resolveTranslations) {
      ATextComponent rootCopy = component.copy();
      rootCopy.applyParentStyle();
      List<ATextComponent> components = new ArrayList();
      List<ATextComponent> current = new ArrayList();
      Runnable addCurrent = () -> {
         boolean wasEmpty = current.isEmpty();
         current.removeIf((comp) -> comp instanceof StringComponent && comp.asSingleString().isEmpty());
         if (current.size() == 1) {
            components.add(current.get(0));
         } else if (!wasEmpty) {
            ATextComponent part = new StringComponent("");

            for(ATextComponent textComponent : current) {
               part.append(textComponent);
            }

            components.add(part);
         }

         current.clear();
      };
      rootCopy.forEach((comp) -> {
         if (comp instanceof StringComponent || comp instanceof TranslationComponent && resolveTranslations) {
            String text = comp.asSingleString();
            if (text.contains(split)) {
               String[] parts = text.split(split, -1);

               for(int i = 0; i < parts.length; ++i) {
                  String part = parts[i];
                  ATextComponent partComp = (new StringComponent(part)).setStyle(comp.getStyle().copy());
                  current.add(partComp);
                  if (i != parts.length - 1) {
                     addCurrent.run();
                  }
               }
            } else {
               current.add(comp.shallowCopy());
            }
         } else {
            current.add(comp.shallowCopy());
         }

      });
      addCurrent.run();
      return (ATextComponent[])components.toArray(new ATextComponent[0]);
   }
}
