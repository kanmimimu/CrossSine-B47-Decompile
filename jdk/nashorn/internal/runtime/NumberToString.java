package jdk.nashorn.internal.runtime;

import java.math.BigInteger;

public final class NumberToString {
   private final boolean isNaN;
   private boolean isNegative;
   private int decimalExponent;
   private char[] digits;
   private int nDigits;
   private static final int expMask = 2047;
   private static final long fractMask = 4503599627370495L;
   private static final int expShift = 52;
   private static final int expBias = 1023;
   private static final long fractHOB = 4503599627370496L;
   private static final long expOne = 4607182418800017408L;
   private static final int maxSmallBinExp = 62;
   private static final int minSmallBinExp = -21;
   private static final long[] powersOf5 = new long[]{1L, 5L, 25L, 125L, 625L, 3125L, 15625L, 78125L, 390625L, 1953125L, 9765625L, 48828125L, 244140625L, 1220703125L, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L};
   private static final int[] nBitsPowerOf5 = new int[]{0, 3, 5, 7, 10, 12, 14, 17, 19, 21, 24, 26, 28, 31, 33, 35, 38, 40, 42, 45, 47, 49, 52, 54, 56, 59, 61};
   private static final char[] infinityDigits = new char[]{'I', 'n', 'f', 'i', 'n', 'i', 't', 'y'};
   private static final char[] nanDigits = new char[]{'N', 'a', 'N'};
   private static final char[] zeroes = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
   private static BigInteger[] powerOf5Cache;

   public static String stringFor(double value) {
      return (new NumberToString(value)).toString();
   }

   private NumberToString(double value) {
      // $FF: Couldn't be decompiled
   }

   private static int countSignificantBits(long bits) {
      return bits != 0L ? 64 - Long.numberOfLeadingZeros(bits) - Long.numberOfTrailingZeros(bits) : 0;
   }

   private static BigInteger bigPowerOf5(int power) {
      if (powerOf5Cache == null) {
         powerOf5Cache = new BigInteger[power + 1];
      } else if (powerOf5Cache.length <= power) {
         BigInteger[] t = new BigInteger[power + 1];
         System.arraycopy(powerOf5Cache, 0, t, 0, powerOf5Cache.length);
         powerOf5Cache = t;
      }

      if (powerOf5Cache[power] != null) {
         return powerOf5Cache[power];
      } else if (power < powersOf5.length) {
         return powerOf5Cache[power] = BigInteger.valueOf(powersOf5[power]);
      } else {
         int q = power >> 1;
         int r = power - q;
         BigInteger bigQ = powerOf5Cache[q];
         if (bigQ == null) {
            bigQ = bigPowerOf5(q);
         }

         if (r < powersOf5.length) {
            return powerOf5Cache[power] = bigQ.multiply(BigInteger.valueOf(powersOf5[r]));
         } else {
            BigInteger bigR = powerOf5Cache[r];
            if (bigR == null) {
               bigR = bigPowerOf5(r);
            }

            return powerOf5Cache[power] = bigQ.multiply(bigR);
         }
      }
   }

   private static BigInteger multiplyPowerOf5And2(BigInteger value, int p5, int p2) {
      BigInteger returnValue = value;
      if (p5 != 0) {
         returnValue = value.multiply(bigPowerOf5(p5));
      }

      if (p2 != 0) {
         returnValue = returnValue.shiftLeft(p2);
      }

      return returnValue;
   }

   private static BigInteger constructPowerOf5And2(int p5, int p2) {
      BigInteger v = bigPowerOf5(p5);
      if (p2 != 0) {
         v = v.shiftLeft(p2);
      }

      return v;
   }

   private void roundup() {
      int i;
      int q;
      for(q = this.digits[i = this.nDigits - 1]; q == 57 && i > 0; q = this.digits[i]) {
         if (this.decimalExponent < 0) {
            --this.nDigits;
         } else {
            this.digits[i] = '0';
         }

         --i;
      }

      if (q == 57) {
         ++this.decimalExponent;
         this.digits[0] = '1';
      } else {
         this.digits[i] = (char)(q + 1);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(32);
      if (this.isNegative) {
         sb.append('-');
      }

      if (this.isNaN) {
         sb.append(this.digits, 0, this.nDigits);
      } else if (this.decimalExponent > 0 && this.decimalExponent <= 21) {
         int charLength = Math.min(this.nDigits, this.decimalExponent);
         sb.append(this.digits, 0, charLength);
         if (charLength < this.decimalExponent) {
            sb.append(zeroes, 0, this.decimalExponent - charLength);
         } else if (charLength < this.nDigits) {
            sb.append('.');
            sb.append(this.digits, charLength, this.nDigits - charLength);
         }
      } else if (this.decimalExponent <= 0 && this.decimalExponent > -6) {
         sb.append('0');
         sb.append('.');
         if (this.decimalExponent != 0) {
            sb.append(zeroes, 0, -this.decimalExponent);
         }

         sb.append(this.digits, 0, this.nDigits);
      } else {
         sb.append(this.digits[0]);
         if (this.nDigits > 1) {
            sb.append('.');
            sb.append(this.digits, 1, this.nDigits - 1);
         }

         sb.append('e');
         int exponent;
         int e;
         if (this.decimalExponent <= 0) {
            sb.append('-');
            exponent = e = -this.decimalExponent + 1;
         } else {
            sb.append('+');
            exponent = e = this.decimalExponent - 1;
         }

         if (exponent > 99) {
            sb.append((char)(e / 100 + 48));
            e %= 100;
         }

         if (exponent > 9) {
            sb.append((char)(e / 10 + 48));
            e %= 10;
         }

         sb.append((char)(e + 48));
      }

      return sb.toString();
   }
}
