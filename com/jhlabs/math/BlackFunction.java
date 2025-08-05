package com.jhlabs.math;

public class BlackFunction implements BinaryFunction {
   public boolean isBlack(int rgb) {
      return rgb == -16777216;
   }
}
