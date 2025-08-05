package com.jhlabs.image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.util.Hashtable;

public class BicubicScaleFilter extends AbstractBufferedImageOp {
   private int width;
   private int height;

   public BicubicScaleFilter() {
      this(32, 32);
   }

   public BicubicScaleFilter(int width, int height) {
      this.width = width;
      this.height = height;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int w = src.getWidth();
      int h = src.getHeight();
      if (dst == null) {
         ColorModel dstCM = src.getColorModel();
         dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(this.width, this.height), dstCM.isAlphaPremultiplied(), (Hashtable)null);
      }

      Graphics2D g = dst.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      ((Graphics)g).drawImage(src, 0, 0, this.width, this.height, (ImageObserver)null);
      ((Graphics)g).dispose();
      return dst;
   }

   public String toString() {
      return "Distort/Bicubic Scale";
   }
}
