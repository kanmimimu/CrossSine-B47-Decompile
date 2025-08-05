package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000F\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0005\n\u0002\u0010\u0012\n\u0002\u0010\u0006\n\u0002\u0010\u0013\n\u0002\u0010\u0007\n\u0002\u0010\u0014\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\n\n\u0002\u0010\u0017\n\u0002\b\u0002\u001a-\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0005\u001a5\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0006\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0007\u001a9\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\t\"\u0002H\u0001H\u0007¢\u0006\u0002\u0010\n\u001a\u0019\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0087\b\u001a!\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u000bH\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\n\u0010\b\u001a\u00020\f\"\u00020\u000bH\u0007\u001a\u0019\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\rH\u0087\b\u001a!\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\n\u0010\b\u001a\u00020\u000e\"\u00020\rH\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0000\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\n\u0010\b\u001a\u00020\u0010\"\u00020\u000fH\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0000\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\n\u0010\b\u001a\u00020\u0012\"\u00020\u0011H\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0013H\u0087\b\u001a!\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\n\u0010\b\u001a\u00020\u0014\"\u00020\u0013H\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0015H\u0087\b\u001a!\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\n\u0010\b\u001a\u00020\u0016\"\u00020\u0015H\u0007\u001a-\u0010\u0017\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0005\u001a5\u0010\u0017\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0006\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0007\u001a9\u0010\u0017\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\t\"\u0002H\u0001H\u0007¢\u0006\u0002\u0010\n\u001a\u0019\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0087\b\u001a!\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u000bH\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\n\u0010\b\u001a\u00020\f\"\u00020\u000bH\u0007\u001a\u0019\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\rH\u0087\b\u001a!\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\n\u0010\b\u001a\u00020\u000e\"\u00020\rH\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\n\u0010\b\u001a\u00020\u0010\"\u00020\u000fH\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\n\u0010\b\u001a\u00020\u0012\"\u00020\u0011H\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0013H\u0087\b\u001a!\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\n\u0010\b\u001a\u00020\u0014\"\u00020\u0013H\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0015H\u0087\b\u001a!\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\n\u0010\b\u001a\u00020\u0016\"\u00020\u0015H\u0007¨\u0006\u0018"},
   d2 = {"maxOf", "T", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "c", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "other", "", "(Ljava/lang/Comparable;[Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "", "", "", "", "", "", "minOf", "kotlin-stdlib"},
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt___ComparisonsJvmKt extends ComparisonsKt__ComparisonsKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable maxOf(@NotNull Comparable a, @NotNull Comparable b) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(b, "b");
      return a.compareTo(b) >= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte maxOf(byte a, byte b) {
      return (byte)Math.max(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short maxOf(short a, short b) {
      return (short)Math.max(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int maxOf(int a, int b) {
      return Math.max(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long maxOf(long a, long b) {
      return Math.max(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float maxOf(float a, float b) {
      return Math.max(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double maxOf(double a, double b) {
      return Math.max(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable maxOf(@NotNull Comparable a, @NotNull Comparable b, @NotNull Comparable c) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(b, "b");
      Intrinsics.checkNotNullParameter(c, "c");
      return ComparisonsKt.maxOf(a, ComparisonsKt.maxOf(b, c));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte maxOf(byte a, byte b, byte c) {
      return (byte)Math.max(a, Math.max(b, c));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short maxOf(short a, short b, short c) {
      return (short)Math.max(a, Math.max(b, c));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int maxOf(int a, int b, int c) {
      int var3 = Math.max(b, c);
      return Math.max(a, var3);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long maxOf(long a, long b, long c) {
      long var6 = Math.max(b, c);
      return Math.max(a, var6);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float maxOf(float a, float b, float c) {
      float var3 = Math.max(b, c);
      return Math.max(a, var3);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double maxOf(double a, double b, double c) {
      double var6 = Math.max(b, c);
      return Math.max(a, var6);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @NotNull
   public static final Comparable maxOf(@NotNull Comparable a, @NotNull Comparable... other) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(other, "other");
      Comparable max = a;
      Comparable[] var3 = other;
      int var4 = 0;

      Comparable e;
      for(int var5 = other.length; var4 < var5; max = ComparisonsKt.maxOf(max, e)) {
         e = var3[var4];
         ++var4;
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final byte maxOf(byte a, @NotNull byte... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      byte max = a;
      byte[] var3 = other;
      int var4 = 0;

      byte e;
      for(int var5 = other.length; var4 < var5; max = (byte)Math.max(max, e)) {
         e = var3[var4];
         ++var4;
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final short maxOf(short a, @NotNull short... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      short max = a;
      short[] var3 = other;
      int var4 = 0;

      short e;
      for(int var5 = other.length; var4 < var5; max = (short)Math.max(max, e)) {
         e = var3[var4];
         ++var4;
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final int maxOf(int a, @NotNull int... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      int max = a;
      int[] var3 = other;
      int var4 = 0;

      int e;
      for(int var5 = other.length; var4 < var5; max = Math.max(max, e)) {
         e = var3[var4];
         ++var4;
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final long maxOf(long a, @NotNull long... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      long max = a;
      long[] var5 = other;
      int var6 = 0;

      long e;
      for(int var7 = other.length; var6 < var7; max = Math.max(max, e)) {
         e = var5[var6];
         ++var6;
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final float maxOf(float a, @NotNull float... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      float max = a;
      float[] var3 = other;
      int var4 = 0;

      float e;
      for(int var5 = other.length; var4 < var5; max = Math.max(max, e)) {
         e = var3[var4];
         ++var4;
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final double maxOf(double a, @NotNull double... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      double max = a;
      double[] var5 = other;
      int var6 = 0;

      double e;
      for(int var7 = other.length; var6 < var7; max = Math.max(max, e)) {
         e = var5[var6];
         ++var6;
      }

      return max;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable minOf(@NotNull Comparable a, @NotNull Comparable b) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(b, "b");
      return a.compareTo(b) <= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte minOf(byte a, byte b) {
      return (byte)Math.min(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short minOf(short a, short b) {
      return (short)Math.min(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int minOf(int a, int b) {
      return Math.min(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long minOf(long a, long b) {
      return Math.min(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float minOf(float a, float b) {
      return Math.min(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double minOf(double a, double b) {
      return Math.min(a, b);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable minOf(@NotNull Comparable a, @NotNull Comparable b, @NotNull Comparable c) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(b, "b");
      Intrinsics.checkNotNullParameter(c, "c");
      return ComparisonsKt.minOf(a, ComparisonsKt.minOf(b, c));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte minOf(byte a, byte b, byte c) {
      return (byte)Math.min(a, Math.min(b, c));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short minOf(short a, short b, short c) {
      return (short)Math.min(a, Math.min(b, c));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int minOf(int a, int b, int c) {
      int var3 = Math.min(b, c);
      return Math.min(a, var3);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long minOf(long a, long b, long c) {
      long var6 = Math.min(b, c);
      return Math.min(a, var6);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float minOf(float a, float b, float c) {
      float var3 = Math.min(b, c);
      return Math.min(a, var3);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double minOf(double a, double b, double c) {
      double var6 = Math.min(b, c);
      return Math.min(a, var6);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @NotNull
   public static final Comparable minOf(@NotNull Comparable a, @NotNull Comparable... other) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(other, "other");
      Comparable min = a;
      Comparable[] var3 = other;
      int var4 = 0;

      Comparable e;
      for(int var5 = other.length; var4 < var5; min = ComparisonsKt.minOf(min, e)) {
         e = var3[var4];
         ++var4;
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final byte minOf(byte a, @NotNull byte... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      byte min = a;
      byte[] var3 = other;
      int var4 = 0;

      byte e;
      for(int var5 = other.length; var4 < var5; min = (byte)Math.min(min, e)) {
         e = var3[var4];
         ++var4;
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final short minOf(short a, @NotNull short... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      short min = a;
      short[] var3 = other;
      int var4 = 0;

      short e;
      for(int var5 = other.length; var4 < var5; min = (short)Math.min(min, e)) {
         e = var3[var4];
         ++var4;
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final int minOf(int a, @NotNull int... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      int min = a;
      int[] var3 = other;
      int var4 = 0;

      int e;
      for(int var5 = other.length; var4 < var5; min = Math.min(min, e)) {
         e = var3[var4];
         ++var4;
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final long minOf(long a, @NotNull long... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      long min = a;
      long[] var5 = other;
      int var6 = 0;

      long e;
      for(int var7 = other.length; var6 < var7; min = Math.min(min, e)) {
         e = var5[var6];
         ++var6;
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final float minOf(float a, @NotNull float... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      float min = a;
      float[] var3 = other;
      int var4 = 0;

      float e;
      for(int var5 = other.length; var4 < var5; min = Math.min(min, e)) {
         e = var3[var4];
         ++var4;
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final double minOf(double a, @NotNull double... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      double min = a;
      double[] var5 = other;
      int var6 = 0;

      double e;
      for(int var7 = other.length; var6 < var7; min = Math.min(min, e)) {
         e = var5[var6];
         ++var6;
      }

      return min;
   }

   public ComparisonsKt___ComparisonsJvmKt() {
   }
}
