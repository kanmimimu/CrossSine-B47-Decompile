package com.jhlabs.image;

import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class RenderTextFilter extends AbstractBufferedImageOp {
   private String text;
   private Font font;
   private Paint paint;
   private Composite composite;
   private AffineTransform transform;

   public RenderTextFilter() {
   }

   public RenderTextFilter(String text, Font font, Paint paint, Composite composite, AffineTransform transform) {
      this.text = text;
      this.font = font;
      this.composite = composite;
      this.paint = paint;
      this.transform = transform;
   }

   public void setComposite(String text) {
      this.text = text;
   }

   public String getText() {
      return this.text;
   }

   public void setComposite(Composite composite) {
      this.composite = composite;
   }

   public Composite getComposite() {
      return this.composite;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      if (dst == null) {
         dst = ((AbstractBufferedImageOp)this).createCompatibleDestImage(src, (ColorModel)null);
      }

      Graphics2D g = dst.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      if (this.font != null) {
         ((Graphics)g).setFont(this.font);
      }

      if (this.transform != null) {
         g.setTransform(this.transform);
      }

      if (this.composite != null) {
         g.setComposite(this.composite);
      }

      if (this.paint != null) {
         g.setPaint(this.paint);
      }

      if (this.text != null) {
         g.drawString(this.text, 10, 100);
      }

      ((Graphics)g).dispose();
      return dst;
   }
}
