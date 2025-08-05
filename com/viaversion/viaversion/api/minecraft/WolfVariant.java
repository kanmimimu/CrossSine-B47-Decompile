package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class WolfVariant {
   final String wildTexture;
   final String tameTexture;
   final String angryTexture;
   final HolderSet biomes;
   public static HolderType TYPE = new HolderType() {
      public WolfVariant readDirect(ByteBuf buffer) {
         String wildTexture = (String)Types.STRING.read(buffer);
         String tameTexture = (String)Types.STRING.read(buffer);
         String angryTexture = (String)Types.STRING.read(buffer);
         HolderSet biomes = (HolderSet)Types.HOLDER_SET.read(buffer);
         return new WolfVariant(wildTexture, tameTexture, angryTexture, biomes);
      }

      public void writeDirect(ByteBuf buffer, WolfVariant variant) {
         Types.STRING.write(buffer, variant.wildTexture());
         Types.STRING.write(buffer, variant.tameTexture());
         Types.STRING.write(buffer, variant.angryTexture());
         Types.HOLDER_SET.write(buffer, variant.biomes());
      }
   };

   public WolfVariant(String wildTexture, String tameTexture, String angryTexture, HolderSet biomes) {
      this.wildTexture = wildTexture;
      this.tameTexture = tameTexture;
      this.angryTexture = angryTexture;
      this.biomes = biomes;
   }

   public String wildTexture() {
      return this.wildTexture;
   }

   public String tameTexture() {
      return this.tameTexture;
   }

   public String angryTexture() {
      return this.angryTexture;
   }

   public HolderSet biomes() {
      return this.biomes;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof WolfVariant)) {
         return false;
      } else {
         WolfVariant var2 = (WolfVariant)var1;
         return Objects.equals(this.wildTexture, var2.wildTexture) && Objects.equals(this.tameTexture, var2.tameTexture) && Objects.equals(this.angryTexture, var2.angryTexture) && Objects.equals(this.biomes, var2.biomes);
      }
   }

   public int hashCode() {
      return (((0 * 31 + Objects.hashCode(this.wildTexture)) * 31 + Objects.hashCode(this.tameTexture)) * 31 + Objects.hashCode(this.angryTexture)) * 31 + Objects.hashCode(this.biomes);
   }

   public String toString() {
      return String.format("%s[wildTexture=%s, tameTexture=%s, angryTexture=%s, biomes=%s]", this.getClass().getSimpleName(), Objects.toString(this.wildTexture), Objects.toString(this.tameTexture), Objects.toString(this.angryTexture), Objects.toString(this.biomes));
   }
}
