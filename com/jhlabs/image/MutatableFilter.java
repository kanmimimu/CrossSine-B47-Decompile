package com.jhlabs.image;

import java.awt.image.BufferedImageOp;

public interface MutatableFilter {
   void mutate(float var1, BufferedImageOp var2, boolean var3, boolean var4);
}
