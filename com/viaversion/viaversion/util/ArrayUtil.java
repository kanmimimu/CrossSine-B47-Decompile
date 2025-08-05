package com.viaversion.viaversion.util;

import java.util.Arrays;

public final class ArrayUtil {
   public static Object[] add(Object[] array, Object element) {
      int length = array.length;
      T[] newArray = (T[])Arrays.copyOf(array, length + 1);
      newArray[length] = element;
      return newArray;
   }

   @SafeVarargs
   public static Object[] add(Object[] array, Object... elements) {
      int length = array.length;
      T[] newArray = (T[])Arrays.copyOf(array, length + elements.length);
      System.arraycopy(elements, 0, newArray, length, elements.length);
      return newArray;
   }

   public static Object[] remove(Object[] array, int index) {
      T[] newArray = (T[])Arrays.copyOf(array, array.length - 1);
      System.arraycopy(array, index + 1, newArray, index, newArray.length - index);
      return newArray;
   }
}
