package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PotionEffectData {
   final int amplifier;
   final int duration;
   final boolean ambient;
   final boolean showParticles;
   final boolean showIcon;
   final @Nullable PotionEffectData hiddenEffect;
   public static final Type TYPE = new Type(PotionEffectData.class) {
      public PotionEffectData read(ByteBuf buffer) {
         int amplifier = Types.VAR_INT.readPrimitive(buffer);
         int duration = Types.VAR_INT.readPrimitive(buffer);
         boolean ambient = buffer.readBoolean();
         boolean showParticles = buffer.readBoolean();
         boolean showIcon = buffer.readBoolean();
         PotionEffectData hiddenEffect = (PotionEffectData)PotionEffectData.OPTIONAL_TYPE.read(buffer);
         return new PotionEffectData(amplifier, duration, ambient, showParticles, showIcon, hiddenEffect);
      }

      public void write(ByteBuf buffer, PotionEffectData value) {
         Types.VAR_INT.writePrimitive(buffer, value.amplifier);
         Types.VAR_INT.writePrimitive(buffer, value.duration);
         buffer.writeBoolean(value.ambient);
         buffer.writeBoolean(value.showParticles);
         buffer.writeBoolean(value.showIcon);
         PotionEffectData.OPTIONAL_TYPE.write(buffer, value.hiddenEffect);
      }
   };
   public static final Type OPTIONAL_TYPE;

   public PotionEffectData(int amplifier, int duration, boolean ambient, boolean showParticles, boolean showIcon, @Nullable PotionEffectData hiddenEffect) {
      this.amplifier = amplifier;
      this.duration = duration;
      this.ambient = ambient;
      this.showParticles = showParticles;
      this.showIcon = showIcon;
      this.hiddenEffect = hiddenEffect;
   }

   public int amplifier() {
      return this.amplifier;
   }

   public int duration() {
      return this.duration;
   }

   public boolean ambient() {
      return this.ambient;
   }

   public boolean showParticles() {
      return this.showParticles;
   }

   public boolean showIcon() {
      return this.showIcon;
   }

   public @Nullable PotionEffectData hiddenEffect() {
      return this.hiddenEffect;
   }

   static {
      OPTIONAL_TYPE = new OptionalType(TYPE) {
      };
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PotionEffectData)) {
         return false;
      } else {
         PotionEffectData var2 = (PotionEffectData)var1;
         return this.amplifier == var2.amplifier && this.duration == var2.duration && this.ambient == var2.ambient && this.showParticles == var2.showParticles && this.showIcon == var2.showIcon && Objects.equals(this.hiddenEffect, var2.hiddenEffect);
      }
   }

   public int hashCode() {
      return (((((0 * 31 + Integer.hashCode(this.amplifier)) * 31 + Integer.hashCode(this.duration)) * 31 + Boolean.hashCode(this.ambient)) * 31 + Boolean.hashCode(this.showParticles)) * 31 + Boolean.hashCode(this.showIcon)) * 31 + Objects.hashCode(this.hiddenEffect);
   }

   public String toString() {
      return String.format("%s[amplifier=%s, duration=%s, ambient=%s, showParticles=%s, showIcon=%s, hiddenEffect=%s]", this.getClass().getSimpleName(), Integer.toString(this.amplifier), Integer.toString(this.duration), Boolean.toString(this.ambient), Boolean.toString(this.showParticles), Boolean.toString(this.showIcon), Objects.toString(this.hiddenEffect));
   }
}
