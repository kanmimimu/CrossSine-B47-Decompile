package com.jhlabs.composite;

import java.awt.Color;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public final class HueComposite extends RGBComposite {
   public HueComposite(float alpha) {
      super(alpha);
   }

   public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
      return new Context(super.extraAlpha, srcColorModel, dstColorModel);
   }

   static class Context extends RGBComposite.RGBCompositeContext {
      private float[] sHSB = new float[3];
      private float[] dHSB = new float[3];

      public Context(float alpha, ColorModel srcColorModel, ColorModel dstColorModel) {
         super(alpha, srcColorModel, dstColorModel);
      }

      public void composeRGB(int[] src, int[] dst, float alpha) {
         int w = src.length;

         for(int i = 0; i < w; i += 4) {
            int sr = src[i];
            int dir = dst[i];
            int sg = src[i + 1];
            int dig = dst[i + 1];
            int sb = src[i + 2];
            int dib = dst[i + 2];
            int sa = src[i + 3];
            int dia = dst[i + 3];
            Color.RGBtoHSB(sr, sg, sb, this.sHSB);
            Color.RGBtoHSB(dir, dig, dib, this.dHSB);
            this.dHSB[0] = this.sHSB[0];
            int doRGB = Color.HSBtoRGB(this.dHSB[0], this.dHSB[1], this.dHSB[2]);
            int dor = (doRGB & 16711680) >> 16;
            int dog = (doRGB & '\uff00') >> 8;
            int dob = doRGB & 255;
            float a = alpha * (float)sa / 255.0F;
            float ac = 1.0F - a;
            dst[i] = (int)(a * (float)dor + ac * (float)dir);
            dst[i + 1] = (int)(a * (float)dog + ac * (float)dig);
            dst[i + 2] = (int)(a * (float)dob + ac * (float)dib);
            dst[i + 3] = (int)((float)sa * alpha + (float)dia * ac);
         }

      }
   }
}
