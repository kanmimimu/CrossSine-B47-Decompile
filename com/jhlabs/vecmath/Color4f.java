package com.jhlabs.vecmath;

import java.awt.Color;

public class Color4f extends Tuple4f {
   public Color4f() {
      this(0.0F, 0.0F, 0.0F, 0.0F);
   }

   public Color4f(float[] x) {
      super.x = x[0];
      super.y = x[1];
      super.z = x[2];
      super.w = x[3];
   }

   public Color4f(float x, float y, float z, float w) {
      super.x = x;
      super.y = y;
      super.z = z;
      super.w = w;
   }

   public Color4f(Color4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public Color4f(Tuple4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public Color4f(Color c) {
      this.set(c);
   }

   public void set(Color c) {
      ((Tuple4f)this).set(c.getRGBComponents((float[])null));
   }

   public Color get() {
      return new Color(super.x, super.y, super.z, super.w);
   }
}
