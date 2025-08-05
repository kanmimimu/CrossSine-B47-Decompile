package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class MapDecoration {
   final String type;
   final double x;
   final double z;
   final float rotation;
   public static final Type TYPE = new Type(MapDecoration.class) {
      public MapDecoration read(ByteBuf buffer) {
         String type = (String)Types.STRING.read(buffer);
         double x = Types.DOUBLE.readPrimitive(buffer);
         double z = Types.DOUBLE.readPrimitive(buffer);
         float rotation = Types.FLOAT.readPrimitive(buffer);
         return new MapDecoration(type, x, z, rotation);
      }

      public void write(ByteBuf buffer, MapDecoration value) {
         Types.STRING.write(buffer, value.type);
         buffer.writeDouble(value.x);
         buffer.writeDouble(value.z);
         buffer.writeFloat(value.rotation);
      }
   };

   public MapDecoration(String type, double x, double z, float rotation) {
      this.type = type;
      this.x = x;
      this.z = z;
      this.rotation = rotation;
   }

   public String type() {
      return this.type;
   }

   public double x() {
      return this.x;
   }

   public double z() {
      return this.z;
   }

   public float rotation() {
      return this.rotation;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof MapDecoration)) {
         return false;
      } else {
         MapDecoration var2 = (MapDecoration)var1;
         return Objects.equals(this.type, var2.type) && Double.compare(this.x, var2.x) == 0 && Double.compare(this.z, var2.z) == 0 && Float.compare(this.rotation, var2.rotation) == 0;
      }
   }

   public int hashCode() {
      return (((0 * 31 + Objects.hashCode(this.type)) * 31 + Double.hashCode(this.x)) * 31 + Double.hashCode(this.z)) * 31 + Float.hashCode(this.rotation);
   }

   public String toString() {
      return String.format("%s[type=%s, x=%s, z=%s, rotation=%s]", this.getClass().getSimpleName(), Objects.toString(this.type), Double.toString(this.x), Double.toString(this.z), Float.toString(this.rotation));
   }
}
