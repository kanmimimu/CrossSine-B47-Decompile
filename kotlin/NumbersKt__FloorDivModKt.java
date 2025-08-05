package kotlin;

import kotlin.internal.InlineOnly;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000 \n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\t\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b¨\u0006\t"},
   d2 = {"floorDiv", "", "", "other", "", "", "mod", "", "", "kotlin-stdlib"},
   xs = "kotlin/NumbersKt"
)
class NumbersKt__FloorDivModKt extends NumbersKt__BigIntegersKt {
   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(byte $this$floorDiv, byte other) {
      int var4 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var4 * other != $this$floorDiv) {
         var4 += -1;
      }

      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final byte mod(byte $this$mod, byte other) {
      int var4 = $this$mod % other;
      return (byte)(var4 + (other & ((var4 ^ other) & (var4 | -var4)) >> 31));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(byte $this$floorDiv, short other) {
      int var4 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var4 * other != $this$floorDiv) {
         var4 += -1;
      }

      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final short mod(byte $this$mod, short other) {
      int var4 = $this$mod % other;
      return (short)(var4 + (other & ((var4 ^ other) & (var4 | -var4)) >> 31));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(byte $this$floorDiv, int other) {
      int var3 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var3 * other != $this$floorDiv) {
         var3 += -1;
      }

      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int mod(byte $this$mod, int other) {
      int var3 = $this$mod % other;
      return var3 + (other & ((var3 ^ other) & (var3 | -var3)) >> 31);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long floorDiv(byte $this$floorDiv, long other) {
      long var3 = (long)$this$floorDiv;
      long var5 = var3 / other;
      if ((var3 ^ other) < 0L && var5 * other != var3) {
         var5 += -1L;
      }

      return var5;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long mod(byte $this$mod, long other) {
      long var3 = (long)$this$mod;
      long var5 = var3 % other;
      return var5 + (other & ((var5 ^ other) & (var5 | -var5)) >> 63);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(short $this$floorDiv, byte other) {
      int var4 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var4 * other != $this$floorDiv) {
         var4 += -1;
      }

      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final byte mod(short $this$mod, byte other) {
      int var4 = $this$mod % other;
      return (byte)(var4 + (other & ((var4 ^ other) & (var4 | -var4)) >> 31));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(short $this$floorDiv, short other) {
      int var4 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var4 * other != $this$floorDiv) {
         var4 += -1;
      }

      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final short mod(short $this$mod, short other) {
      int var4 = $this$mod % other;
      return (short)(var4 + (other & ((var4 ^ other) & (var4 | -var4)) >> 31));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(short $this$floorDiv, int other) {
      int var3 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var3 * other != $this$floorDiv) {
         var3 += -1;
      }

      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int mod(short $this$mod, int other) {
      int var3 = $this$mod % other;
      return var3 + (other & ((var3 ^ other) & (var3 | -var3)) >> 31);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long floorDiv(short $this$floorDiv, long other) {
      long var3 = (long)$this$floorDiv;
      long var5 = var3 / other;
      if ((var3 ^ other) < 0L && var5 * other != var3) {
         var5 += -1L;
      }

      return var5;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long mod(short $this$mod, long other) {
      long var3 = (long)$this$mod;
      long var5 = var3 % other;
      return var5 + (other & ((var5 ^ other) & (var5 | -var5)) >> 63);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(int $this$floorDiv, byte other) {
      int var4 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var4 * other != $this$floorDiv) {
         var4 += -1;
      }

      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final byte mod(int $this$mod, byte other) {
      int var4 = $this$mod % other;
      return (byte)(var4 + (other & ((var4 ^ other) & (var4 | -var4)) >> 31));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(int $this$floorDiv, short other) {
      int var4 = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && var4 * other != $this$floorDiv) {
         var4 += -1;
      }

      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final short mod(int $this$mod, short other) {
      int var4 = $this$mod % other;
      return (short)(var4 + (other & ((var4 ^ other) & (var4 | -var4)) >> 31));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int floorDiv(int $this$floorDiv, int other) {
      int q = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0 && q * other != $this$floorDiv) {
         q += -1;
      }

      return q;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int mod(int $this$mod, int other) {
      int r = $this$mod % other;
      return r + (other & ((r ^ other) & (r | -r)) >> 31);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long floorDiv(int $this$floorDiv, long other) {
      long var3 = (long)$this$floorDiv;
      long var5 = var3 / other;
      if ((var3 ^ other) < 0L && var5 * other != var3) {
         var5 += -1L;
      }

      return var5;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long mod(int $this$mod, long other) {
      long var3 = (long)$this$mod;
      long var5 = var3 % other;
      return var5 + (other & ((var5 ^ other) & (var5 | -var5)) >> 63);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long floorDiv(long $this$floorDiv, byte other) {
      long var5 = (long)other;
      long var7 = $this$floorDiv / var5;
      if (($this$floorDiv ^ var5) < 0L && var7 * var5 != $this$floorDiv) {
         var7 += -1L;
      }

      return var7;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final byte mod(long $this$mod, byte other) {
      long var5 = (long)other;
      long var7 = $this$mod % var5;
      return (byte)((int)(var7 + (var5 & ((var7 ^ var5) & (var7 | -var7)) >> 63)));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long floorDiv(long $this$floorDiv, short other) {
      long var5 = (long)other;
      long var7 = $this$floorDiv / var5;
      if (($this$floorDiv ^ var5) < 0L && var7 * var5 != $this$floorDiv) {
         var7 += -1L;
      }

      return var7;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final short mod(long $this$mod, short other) {
      long var5 = (long)other;
      long var7 = $this$mod % var5;
      return (short)((int)(var7 + (var5 & ((var7 ^ var5) & (var7 | -var7)) >> 63)));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long floorDiv(long $this$floorDiv, int other) {
      long var5 = (long)other;
      long var7 = $this$floorDiv / var5;
      if (($this$floorDiv ^ var5) < 0L && var7 * var5 != $this$floorDiv) {
         var7 += -1L;
      }

      return var7;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final int mod(long $this$mod, int other) {
      long var5 = (long)other;
      long var7 = $this$mod % var5;
      return (int)(var7 + (var5 & ((var7 ^ var5) & (var7 | -var7)) >> 63));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long floorDiv(long $this$floorDiv, long other) {
      long q = $this$floorDiv / other;
      if (($this$floorDiv ^ other) < 0L && q * other != $this$floorDiv) {
         q += -1L;
      }

      return q;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final long mod(long $this$mod, long other) {
      long r = $this$mod % other;
      return r + (other & ((r ^ other) & (r | -r)) >> 63);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final float mod(float $this$mod, float other) {
      float r = $this$mod % other;
      return r != 0.0F && Math.signum(r) != Math.signum(other) ? r + other : r;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final double mod(float $this$mod, double other) {
      double var3 = (double)$this$mod;
      double var5 = var3 % other;
      return var5 != (double)0.0F && Math.signum(var5) != Math.signum(other) ? var5 + other : var5;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final double mod(double $this$mod, float other) {
      double var5 = (double)other;
      double var7 = $this$mod % var5;
      return var7 != (double)0.0F && Math.signum(var7) != Math.signum(var5) ? var7 + var5 : var7;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final double mod(double $this$mod, double other) {
      double r = $this$mod % other;
      return r != (double)0.0F && Math.signum(r) != Math.signum(other) ? r + other : r;
   }

   public NumbersKt__FloorDivModKt() {
   }
}
