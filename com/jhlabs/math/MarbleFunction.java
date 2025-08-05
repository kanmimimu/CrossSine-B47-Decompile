package com.jhlabs.math;

public class MarbleFunction extends CompoundFunction2D {
   public MarbleFunction() {
      super(new TurbulenceFunction(new Noise(), 6.0F));
   }

   public MarbleFunction(Function2D basis) {
      super(basis);
   }

   public float evaluate(float x, float y) {
      return (float)Math.pow((double)0.5F * (Math.sin((double)8.0F * (double)super.basis.evaluate(x, y)) + (double)1.0F), 0.77);
   }
}
