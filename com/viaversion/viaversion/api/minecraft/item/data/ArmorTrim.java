package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class ArmorTrim {
   final Holder material;
   final Holder pattern;
   final boolean showInTooltip;
   public static final Type TYPE = new Type(ArmorTrim.class) {
      public ArmorTrim read(ByteBuf buffer) {
         Holder<ArmorTrimMaterial> material = ArmorTrimMaterial.TYPE.read(buffer);
         Holder<ArmorTrimPattern> pattern = ArmorTrimPattern.TYPE.read(buffer);
         boolean showInTooltip = buffer.readBoolean();
         return new ArmorTrim(material, pattern, showInTooltip);
      }

      public void write(ByteBuf buffer, ArmorTrim value) {
         ArmorTrimMaterial.TYPE.write(buffer, value.material);
         ArmorTrimPattern.TYPE.write(buffer, value.pattern);
         buffer.writeBoolean(value.showInTooltip);
      }
   };

   public ArmorTrim(Holder material, Holder pattern, boolean showInTooltip) {
      this.material = material;
      this.pattern = pattern;
      this.showInTooltip = showInTooltip;
   }

   public ArmorTrim rewrite(Int2IntFunction idRewriteFunction) {
      Holder<ArmorTrimMaterial> material = this.material;
      if (material.isDirect()) {
         material = Holder.of(((ArmorTrimMaterial)material.value()).rewrite(idRewriteFunction));
      }

      Holder<ArmorTrimPattern> pattern = this.pattern;
      if (pattern.isDirect()) {
         pattern = Holder.of(((ArmorTrimPattern)pattern.value()).rewrite(idRewriteFunction));
      }

      return new ArmorTrim(material, pattern, this.showInTooltip);
   }

   public Holder material() {
      return this.material;
   }

   public Holder pattern() {
      return this.pattern;
   }

   public boolean showInTooltip() {
      return this.showInTooltip;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ArmorTrim)) {
         return false;
      } else {
         ArmorTrim var2 = (ArmorTrim)var1;
         return Objects.equals(this.material, var2.material) && Objects.equals(this.pattern, var2.pattern) && this.showInTooltip == var2.showInTooltip;
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.material)) * 31 + Objects.hashCode(this.pattern)) * 31 + Boolean.hashCode(this.showInTooltip);
   }

   public String toString() {
      return String.format("%s[material=%s, pattern=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.material), Objects.toString(this.pattern), Boolean.toString(this.showInTooltip));
   }
}
