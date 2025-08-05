package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class Fireworks {
   final int flightDuration;
   final FireworkExplosion[] explosions;
   public static final Type TYPE = new Type(Fireworks.class) {
      public Fireworks read(ByteBuf buffer) {
         int flightDuration = Types.VAR_INT.readPrimitive(buffer);
         FireworkExplosion[] explosions = (FireworkExplosion[])FireworkExplosion.ARRAY_TYPE.read(buffer);
         return new Fireworks(flightDuration, explosions);
      }

      public void write(ByteBuf buffer, Fireworks value) {
         Types.VAR_INT.writePrimitive(buffer, value.flightDuration);
         FireworkExplosion.ARRAY_TYPE.write(buffer, value.explosions);
      }
   };

   public Fireworks(int flightDuration, FireworkExplosion[] explosions) {
      this.flightDuration = flightDuration;
      this.explosions = explosions;
   }

   public int flightDuration() {
      return this.flightDuration;
   }

   public FireworkExplosion[] explosions() {
      return this.explosions;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Fireworks)) {
         return false;
      } else {
         Fireworks var2 = (Fireworks)var1;
         return this.flightDuration == var2.flightDuration && Objects.equals(this.explosions, var2.explosions);
      }
   }

   public int hashCode() {
      return (0 * 31 + Integer.hashCode(this.flightDuration)) * 31 + Objects.hashCode(this.explosions);
   }

   public String toString() {
      return String.format("%s[flightDuration=%s, explosions=%s]", this.getClass().getSimpleName(), Integer.toString(this.flightDuration), Objects.toString(this.explosions));
   }
}
