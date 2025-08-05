package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Objects;
import javax.annotation.Nullable;

public class ScoreComponent extends ATextComponent {
   private String name;
   private String objective;
   @Nullable
   private String value;

   public ScoreComponent(String name, String objective) {
      this(name, objective, (String)null);
   }

   public ScoreComponent(String name, String objective, @Nullable String value) {
      this.name = name;
      this.objective = objective;
      this.value = value;
   }

   public String getName() {
      return this.name;
   }

   public ScoreComponent setName(String name) {
      this.name = name;
      return this;
   }

   public String getObjective() {
      return this.objective;
   }

   public ScoreComponent setObjective(@Nullable String objective) {
      this.objective = objective;
      return this;
   }

   @Nullable
   public String getValue() {
      return this.value;
   }

   public ScoreComponent setValue(@Nullable String value) {
      this.value = value;
      return this;
   }

   public String asSingleString() {
      return this.value;
   }

   public ATextComponent copy() {
      return this.putMetaCopy(this.shallowCopy());
   }

   public ATextComponent shallowCopy() {
      ScoreComponent copy = new ScoreComponent(this.name, this.objective);
      copy.value = this.value;
      return copy.setStyle(this.getStyle().copy());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ScoreComponent that = (ScoreComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.name, that.name) && Objects.equals(this.objective, that.objective) && Objects.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.getSiblings(), this.getStyle(), this.name, this.objective, this.value});
   }

   public String toString() {
      return ToString.of((Object)this).add("siblings", this.getSiblings(), (siblings) -> !siblings.isEmpty()).add("style", this.getStyle(), (style) -> !style.isEmpty()).add("name", this.name).add("objective", this.objective).add("value", this.value, Objects::nonNull).toString();
   }
}
