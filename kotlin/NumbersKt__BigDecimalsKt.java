package kotlin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0002\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\t\u001a\u00020\u0001*\u00020\nH\u0087\b\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0087\b\u001a\r\u0010\t\u001a\u00020\u0001*\u00020\rH\u0087\b\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u000b\u001a\u00020\fH\u0087\b\u001a\r\u0010\t\u001a\u00020\u0001*\u00020\u000eH\u0087\b\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0087\b\u001a\r\u0010\t\u001a\u00020\u0001*\u00020\u000fH\u0087\b\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0087\b\u001a\r\u0010\u0010\u001a\u00020\u0001*\u00020\u0001H\u0087\n¨\u0006\u0011"},
   d2 = {"dec", "Ljava/math/BigDecimal;", "div", "other", "inc", "minus", "plus", "rem", "times", "toBigDecimal", "", "mathContext", "Ljava/math/MathContext;", "", "", "", "unaryMinus", "kotlin-stdlib"},
   xs = "kotlin/NumbersKt"
)
class NumbersKt__BigDecimalsKt {
   @InlineOnly
   private static final BigDecimal plus(BigDecimal $this$plus, BigDecimal other) {
      Intrinsics.checkNotNullParameter($this$plus, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      BigDecimal var2 = $this$plus.add(other);
      Intrinsics.checkNotNullExpressionValue(var2, "this.add(other)");
      return var2;
   }

   @InlineOnly
   private static final BigDecimal minus(BigDecimal $this$minus, BigDecimal other) {
      Intrinsics.checkNotNullParameter($this$minus, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      BigDecimal var2 = $this$minus.subtract(other);
      Intrinsics.checkNotNullExpressionValue(var2, "this.subtract(other)");
      return var2;
   }

   @InlineOnly
   private static final BigDecimal times(BigDecimal $this$times, BigDecimal other) {
      Intrinsics.checkNotNullParameter($this$times, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      BigDecimal var2 = $this$times.multiply(other);
      Intrinsics.checkNotNullExpressionValue(var2, "this.multiply(other)");
      return var2;
   }

   @InlineOnly
   private static final BigDecimal div(BigDecimal $this$div, BigDecimal other) {
      Intrinsics.checkNotNullParameter($this$div, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      BigDecimal var2 = $this$div.divide(other, RoundingMode.HALF_EVEN);
      Intrinsics.checkNotNullExpressionValue(var2, "this.divide(other, RoundingMode.HALF_EVEN)");
      return var2;
   }

   @InlineOnly
   private static final BigDecimal rem(BigDecimal $this$rem, BigDecimal other) {
      Intrinsics.checkNotNullParameter($this$rem, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      BigDecimal var2 = $this$rem.remainder(other);
      Intrinsics.checkNotNullExpressionValue(var2, "this.remainder(other)");
      return var2;
   }

   @InlineOnly
   private static final BigDecimal unaryMinus(BigDecimal $this$unaryMinus) {
      Intrinsics.checkNotNullParameter($this$unaryMinus, "<this>");
      BigDecimal var1 = $this$unaryMinus.negate();
      Intrinsics.checkNotNullExpressionValue(var1, "this.negate()");
      return var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal inc(BigDecimal $this$inc) {
      Intrinsics.checkNotNullParameter($this$inc, "<this>");
      BigDecimal var1 = $this$inc.add(BigDecimal.ONE);
      Intrinsics.checkNotNullExpressionValue(var1, "this.add(BigDecimal.ONE)");
      return var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal dec(BigDecimal $this$dec) {
      Intrinsics.checkNotNullParameter($this$dec, "<this>");
      BigDecimal var1 = $this$dec.subtract(BigDecimal.ONE);
      Intrinsics.checkNotNullExpressionValue(var1, "this.subtract(BigDecimal.ONE)");
      return var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(int $this$toBigDecimal) {
      BigDecimal var1 = BigDecimal.valueOf((long)$this$toBigDecimal);
      Intrinsics.checkNotNullExpressionValue(var1, "valueOf(this.toLong())");
      return var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(int $this$toBigDecimal, MathContext mathContext) {
      Intrinsics.checkNotNullParameter(mathContext, "mathContext");
      return new BigDecimal($this$toBigDecimal, mathContext);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(long $this$toBigDecimal) {
      BigDecimal var2 = BigDecimal.valueOf($this$toBigDecimal);
      Intrinsics.checkNotNullExpressionValue(var2, "valueOf(this)");
      return var2;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(long $this$toBigDecimal, MathContext mathContext) {
      Intrinsics.checkNotNullParameter(mathContext, "mathContext");
      return new BigDecimal($this$toBigDecimal, mathContext);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(float $this$toBigDecimal) {
      return new BigDecimal(String.valueOf($this$toBigDecimal));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(float $this$toBigDecimal, MathContext mathContext) {
      Intrinsics.checkNotNullParameter(mathContext, "mathContext");
      return new BigDecimal(String.valueOf($this$toBigDecimal), mathContext);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(double $this$toBigDecimal) {
      return new BigDecimal(String.valueOf($this$toBigDecimal));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(double $this$toBigDecimal, MathContext mathContext) {
      Intrinsics.checkNotNullParameter(mathContext, "mathContext");
      return new BigDecimal(String.valueOf($this$toBigDecimal), mathContext);
   }

   public NumbersKt__BigDecimalsKt() {
   }
}
