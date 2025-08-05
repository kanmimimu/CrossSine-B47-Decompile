package com.jhlabs.image;

public interface Quantizer {
   void setup(int var1);

   void addPixels(int[] var1, int var2, int var3);

   int[] buildColorTable();

   int getIndexForColor(int var1);
}
