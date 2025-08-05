package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class BannerPattern {
   final String assetId;
   final String translationKey;
   public static final HolderType TYPE = new HolderType() {
      public BannerPattern readDirect(ByteBuf buffer) {
         String assetId = (String)Types.STRING.read(buffer);
         String translationKey = (String)Types.STRING.read(buffer);
         return new BannerPattern(assetId, translationKey);
      }

      public void writeDirect(ByteBuf buffer, BannerPattern value) {
         Types.STRING.write(buffer, value.assetId);
         Types.STRING.write(buffer, value.translationKey);
      }
   };

   public BannerPattern(String assetId, String translationKey) {
      this.assetId = assetId;
      this.translationKey = translationKey;
   }

   public String assetId() {
      return this.assetId;
   }

   public String translationKey() {
      return this.translationKey;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BannerPattern)) {
         return false;
      } else {
         BannerPattern var2 = (BannerPattern)var1;
         return Objects.equals(this.assetId, var2.assetId) && Objects.equals(this.translationKey, var2.translationKey);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.assetId)) * 31 + Objects.hashCode(this.translationKey);
   }

   public String toString() {
      return String.format("%s[assetId=%s, translationKey=%s]", this.getClass().getSimpleName(), Objects.toString(this.assetId), Objects.toString(this.translationKey));
   }
}
