package com.jhlabs.image;

public class PointillizeFilter extends CellularFilter {
   private float edgeThickness = 0.4F;
   private boolean fadeEdges = false;
   private int edgeColor = -16777216;
   private float fuzziness = 0.1F;

   public PointillizeFilter() {
      ((CellularFilter)this).setScale(16.0F);
      ((CellularFilter)this).setRandomness(0.0F);
   }

   public void setEdgeThickness(float edgeThickness) {
      this.edgeThickness = edgeThickness;
   }

   public float getEdgeThickness() {
      return this.edgeThickness;
   }

   public void setFadeEdges(boolean fadeEdges) {
      this.fadeEdges = fadeEdges;
   }

   public boolean getFadeEdges() {
      return this.fadeEdges;
   }

   public void setEdgeColor(int edgeColor) {
      this.edgeColor = edgeColor;
   }

   public int getEdgeColor() {
      return this.edgeColor;
   }

   public void setFuzziness(float fuzziness) {
      this.fuzziness = fuzziness;
   }

   public float getFuzziness() {
      return this.fuzziness;
   }

   public int getPixel(int x, int y, int[] inPixels, int width, int height) {
      float nx = super.m00 * (float)x + super.m01 * (float)y;
      float ny = super.m10 * (float)x + super.m11 * (float)y;
      nx /= super.scale;
      ny /= super.scale * super.stretch;
      nx += 1000.0F;
      ny += 1000.0F;
      ((CellularFilter)this).evaluate(nx, ny);
      float f1 = super.results[0].distance;
      int srcx = ImageMath.clamp((int)((super.results[0].x - 1000.0F) * super.scale), 0, width - 1);
      int srcy = ImageMath.clamp((int)((super.results[0].y - 1000.0F) * super.scale), 0, height - 1);
      int v = inPixels[srcy * width + srcx];
      if (this.fadeEdges) {
         float f2 = super.results[1].distance;
         srcx = ImageMath.clamp((int)((super.results[1].x - 1000.0F) * super.scale), 0, width - 1);
         srcy = ImageMath.clamp((int)((super.results[1].y - 1000.0F) * super.scale), 0, height - 1);
         int v2 = inPixels[srcy * width + srcx];
         v = ImageMath.mixColors(0.5F * f1 / f2, v, v2);
      } else {
         float f = 1.0F - ImageMath.smoothStep(this.edgeThickness, this.edgeThickness + this.fuzziness, f1);
         v = ImageMath.mixColors(f, this.edgeColor, v);
      }

      return v;
   }

   public String toString() {
      return "Stylize/Pointillize...";
   }
}
