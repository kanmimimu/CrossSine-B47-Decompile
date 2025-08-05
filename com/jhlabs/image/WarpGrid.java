package com.jhlabs.image;

import java.io.Serializable;

public class WarpGrid implements Serializable {
   static final long serialVersionUID = 4312410199770201968L;
   public float[] xGrid = null;
   public float[] yGrid = null;
   public int rows;
   public int cols;
   private static final float m00 = -0.5F;
   private static final float m01 = 1.5F;
   private static final float m02 = -1.5F;
   private static final float m03 = 0.5F;
   private static final float m10 = 1.0F;
   private static final float m11 = -2.5F;
   private static final float m12 = 2.0F;
   private static final float m13 = -0.5F;
   private static final float m20 = -0.5F;
   private static final float m22 = 0.5F;
   private static final float m31 = 1.0F;

   public WarpGrid(int rows, int cols, int w, int h) {
      this.rows = rows;
      this.cols = cols;
      this.xGrid = new float[rows * cols];
      this.yGrid = new float[rows * cols];
      int index = 0;

      for(int row = 0; row < rows; ++row) {
         for(int col = 0; col < cols; ++col) {
            this.xGrid[index] = (float)col * (float)(w - 1) / (float)(cols - 1);
            this.yGrid[index] = (float)row * (float)(h - 1) / (float)(rows - 1);
            ++index;
         }
      }

   }

   public void addRow(int before) {
      int size = (this.rows + 1) * this.cols;
      float[] x = new float[size];
      float[] y = new float[size];
      ++this.rows;
      int i = 0;
      int j = 0;

      for(int row = 0; row < this.rows; ++row) {
         for(int col = 0; col < this.cols; ++col) {
            int k = j + col;
            int l = i + col;
            if (row == before) {
               x[k] = (this.xGrid[l] + this.xGrid[k]) / 2.0F;
               y[k] = (this.yGrid[l] + this.yGrid[k]) / 2.0F;
            } else {
               x[k] = this.xGrid[l];
               y[k] = this.yGrid[l];
            }
         }

         if (row != before - 1) {
            i += this.cols;
         }

         j += this.cols;
      }

      this.xGrid = x;
      this.yGrid = y;
   }

   public void addCol(int before) {
      int size = this.rows * (this.cols + 1);
      float[] x = new float[size];
      float[] y = new float[size];
      ++this.cols;
      int i = 0;
      int j = 0;

      for(int row = 0; row < this.rows; ++row) {
         for(int col = 0; col < this.cols; ++col) {
            if (col == before) {
               x[j] = (this.xGrid[i] + this.xGrid[i - 1]) / 2.0F;
               y[j] = (this.yGrid[i] + this.yGrid[i - 1]) / 2.0F;
            } else {
               x[j] = this.xGrid[i];
               y[j] = this.yGrid[i];
               ++i;
            }

            ++j;
         }
      }

      this.xGrid = x;
      this.yGrid = y;
   }

   public void removeRow(int r) {
      int size = (this.rows - 1) * this.cols;
      float[] x = new float[size];
      float[] y = new float[size];
      --this.rows;
      int i = 0;
      int j = 0;

      for(int row = 0; row < this.rows; ++row) {
         for(int col = 0; col < this.cols; ++col) {
            int k = j + col;
            int l = i + col;
            x[k] = this.xGrid[l];
            y[k] = this.yGrid[l];
         }

         if (row == r - 1) {
            i += this.cols;
         }

         i += this.cols;
         j += this.cols;
      }

      this.xGrid = x;
      this.yGrid = y;
   }

   public void removeCol(int r) {
      int size = this.rows * (this.cols + 1);
      float[] x = new float[size];
      float[] y = new float[size];
      --this.cols;

      for(int row = 0; row < this.rows; ++row) {
         int i = row * (this.cols + 1);
         int j = row * this.cols;

         for(int col = 0; col < this.cols; ++col) {
            x[j] = this.xGrid[i];
            y[j] = this.yGrid[i];
            if (col == r - 1) {
               ++i;
            }

            ++i;
            ++j;
         }
      }

      this.xGrid = x;
      this.yGrid = y;
   }

   public void lerp(float t, WarpGrid destination, WarpGrid intermediate) {
      if (this.rows == destination.rows && this.cols == destination.cols) {
         if (this.rows == intermediate.rows && this.cols == intermediate.cols) {
            int index = 0;

            for(int row = 0; row < this.rows; ++row) {
               for(int col = 0; col < this.cols; ++col) {
                  intermediate.xGrid[index] = ImageMath.lerp(t, this.xGrid[index], destination.xGrid[index]);
                  intermediate.yGrid[index] = ImageMath.lerp(t, this.yGrid[index], destination.yGrid[index]);
                  ++index;
               }
            }

         } else {
            throw new IllegalArgumentException("source and intermediate are different sizes");
         }
      } else {
         throw new IllegalArgumentException("source and destination are different sizes");
      }
   }

   public void warp(int[] inPixels, int cols, int rows, WarpGrid sourceGrid, WarpGrid destGrid, int[] outPixels) {
      try {
         if (sourceGrid.rows != destGrid.rows || sourceGrid.cols != destGrid.cols) {
            throw new IllegalArgumentException("source and destination grids are different sizes");
         }

         int size = Math.max(cols, rows);
         float[] xrow = new float[size];
         float[] yrow = new float[size];
         float[] scale = new float[size + 1];
         float[] interpolated = new float[size + 1];
         int gridCols = sourceGrid.cols;
         int gridRows = sourceGrid.rows;
         WarpGrid splines = new WarpGrid(rows, gridCols, 1, 1);

         for(int u = 0; u < gridCols; ++u) {
            int i = u;

            for(int v = 0; v < gridRows; ++v) {
               xrow[v] = sourceGrid.xGrid[i];
               yrow[v] = sourceGrid.yGrid[i];
               i += gridCols;
            }

            this.interpolateSpline(yrow, xrow, 0, gridRows, interpolated, 0, rows);
            i = u;

            for(int y = 0; y < rows; ++y) {
               splines.xGrid[i] = interpolated[y];
               i += gridCols;
            }
         }

         for(int var26 = 0; var26 < gridCols; ++var26) {
            int i = var26;

            for(int v = 0; v < gridRows; ++v) {
               xrow[v] = destGrid.xGrid[i];
               yrow[v] = destGrid.yGrid[i];
               i += gridCols;
            }

            this.interpolateSpline(yrow, xrow, 0, gridRows, interpolated, 0, rows);
            i = var26;

            for(int y = 0; y < rows; ++y) {
               splines.yGrid[i] = interpolated[y];
               i += gridCols;
            }
         }

         int[] intermediate = new int[rows * cols];
         int offset = 0;

         for(int y = 0; y < rows; ++y) {
            this.interpolateSpline(splines.xGrid, splines.yGrid, offset, gridCols, scale, 0, cols);
            scale[cols] = (float)cols;
            ImageMath.resample(inPixels, intermediate, cols, y * cols, 1, scale);
            offset += gridCols;
         }

         splines = new WarpGrid(gridRows, cols, 1, 1);
         offset = 0;
         int offset2 = 0;

         for(int v = 0; v < gridRows; ++v) {
            this.interpolateSpline(sourceGrid.xGrid, sourceGrid.yGrid, offset, gridCols, splines.xGrid, offset2, cols);
            offset += gridCols;
            offset2 += cols;
         }

         offset = 0;
         offset2 = 0;

         for(int var29 = 0; var29 < gridRows; ++var29) {
            this.interpolateSpline(destGrid.xGrid, destGrid.yGrid, offset, gridCols, splines.yGrid, offset2, cols);
            offset += gridCols;
            offset2 += cols;
         }

         for(int x = 0; x < cols; ++x) {
            int i = x;

            for(int var30 = 0; var30 < gridRows; ++var30) {
               xrow[var30] = splines.xGrid[i];
               yrow[var30] = splines.yGrid[i];
               i += cols;
            }

            this.interpolateSpline(xrow, yrow, 0, gridRows, scale, 0, rows);
            scale[rows] = (float)rows;
            ImageMath.resample(intermediate, outPixels, rows, x, cols, scale);
         }
      } catch (Exception e) {
         ((Throwable)e).printStackTrace();
      }

   }

   protected void interpolateSpline(float[] xKnots, float[] yKnots, int offset, int length, float[] splineY, int splineOffset, int splineLength) {
      int index = offset;
      int end = offset + length - 1;
      float x0 = xKnots[offset];
      float k1;
      float k2;
      float k0 = k1 = k2 = yKnots[offset];
      float x1 = xKnots[offset + 1];
      float k3 = yKnots[offset + 1];

      for(int i = 0; i < splineLength; ++i) {
         if (index <= end && (float)i > xKnots[index]) {
            k0 = k1;
            k1 = k2;
            k2 = k3;
            x0 = xKnots[index];
            ++index;
            if (index <= end) {
               x1 = xKnots[index];
            }

            if (index < end) {
               k3 = yKnots[index + 1];
            } else {
               k3 = k3;
            }
         }

         float t = ((float)i - x0) / (x1 - x0);
         float c3 = -0.5F * k0 + 1.5F * k1 + -1.5F * k2 + 0.5F * k3;
         float c2 = 1.0F * k0 + -2.5F * k1 + 2.0F * k2 + -0.5F * k3;
         float c1 = -0.5F * k0 + 0.5F * k2;
         float c0 = 1.0F * k1;
         splineY[splineOffset + i] = ((c3 * t + c2) * t + c1) * t + c0;
      }

   }

   protected void interpolateSpline2(float[] xKnots, float[] yKnots, int offset, float[] splineY, int splineOffset, int splineLength) {
      int index = offset;
      float leftX = xKnots[offset];
      float leftY = yKnots[offset];
      float rightX = xKnots[offset + 1];
      float rightY = yKnots[offset + 1];

      for(int i = 0; i < splineLength; ++i) {
         if ((float)i > xKnots[index]) {
            leftX = xKnots[index];
            leftY = yKnots[index];
            ++index;
            rightX = xKnots[index];
            rightY = yKnots[index];
         }

         float f = ((float)i - leftX) / (rightX - leftX);
         splineY[splineOffset + i] = leftY + f * (rightY - leftY);
      }

   }
}
