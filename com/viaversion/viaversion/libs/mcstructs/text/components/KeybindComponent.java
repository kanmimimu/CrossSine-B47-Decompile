package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KeybindComponent extends ATextComponent {
   private static final Function DEFAULT_TRANSLATOR = (s) -> s;
   private String keybind;
   private Function translator;

   public KeybindComponent(String keybind) {
      this.translator = DEFAULT_TRANSLATOR;
      this.keybind = keybind;
   }

   public KeybindComponent(String keybind, @Nonnull Function translator) {
      this.translator = DEFAULT_TRANSLATOR;
      this.keybind = keybind;
      this.translator = translator;
   }

   public String getKeybind() {
      return this.keybind;
   }

   public KeybindComponent setKeybind(String keybind) {
      this.keybind = keybind;
      return this;
   }

   public KeybindComponent setTranslator(@Nullable Function translator) {
      if (translator == null) {
         this.translator = DEFAULT_TRANSLATOR;
      } else {
         this.translator = translator;
      }

      return this;
   }

   public String asSingleString() {
      return (String)this.translator.apply(this.keybind);
   }

   public ATextComponent copy() {
      return this.putMetaCopy(this.shallowCopy());
   }

   public ATextComponent shallowCopy() {
      KeybindComponent copy = new KeybindComponent(this.keybind);
      copy.translator = this.translator;
      return copy.setStyle(this.getStyle().copy());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         KeybindComponent that = (KeybindComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.keybind, that.keybind) && Objects.equals(this.translator, that.translator);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.getSiblings(), this.getStyle(), this.keybind, this.translator});
   }

   public String toString() {
      return ToString.of((Object)this).add("siblings", this.getSiblings(), (siblings) -> !siblings.isEmpty()).add("style", this.getStyle(), (style) -> !style.isEmpty()).add("keybind", this.keybind).add("translator", this.translator, (translator) -> translator != DEFAULT_TRANSLATOR).toString();
   }
}
