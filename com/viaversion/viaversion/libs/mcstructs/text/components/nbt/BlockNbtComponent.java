package com.viaversion.viaversion.libs.mcstructs.text.components.nbt;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.NbtComponent;
import java.util.Objects;

public class BlockNbtComponent extends NbtComponent {
   private String pos;

   public BlockNbtComponent(String rawComponent, boolean resolve, String pos) {
      super(rawComponent, resolve);
      this.pos = pos;
   }

   public BlockNbtComponent(String rawComponent, boolean resolve, ATextComponent separator, String pos) {
      super(rawComponent, resolve, separator);
      this.pos = pos;
   }

   public String getPos() {
      return this.pos;
   }

   public BlockNbtComponent setPos(String pos) {
      this.pos = pos;
      return this;
   }

   public ATextComponent copy() {
      return this.putMetaCopy(this.shallowCopy());
   }

   public ATextComponent shallowCopy() {
      return this.getSeparator() == null ? (new BlockNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator(), this.pos)).setStyle(this.getStyle().copy()) : (new BlockNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator().copy(), this.pos)).setStyle(this.getStyle().copy());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         BlockNbtComponent that = (BlockNbtComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.pos, that.pos);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.getSiblings(), this.getStyle(), this.pos});
   }

   public String toString() {
      return ToString.of((Object)this).add("siblings", this.getSiblings(), (siblings) -> !siblings.isEmpty()).add("style", this.getStyle(), (style) -> !style.isEmpty()).add("pos", this.pos).toString();
   }
}
