package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BA\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0002H\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0016J\u000e\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u0006J\u000e\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u0006J\b\u0010\u001c\u001a\u00020\u0016H\u0016R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011¨\u0006\u001d"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/ranges/IntRange;", "name", "", "minValue", "", "maxValue", "minimum", "maximum", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;IIIILkotlin/jvm/functions/Function0;)V", "defaultMax", "defaultMin", "getMaximum", "()I", "getMinimum", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "get", "setDefault", "setMax", "value", "setMin", "toJson", "CrossSine"}
)
public final class IntegerRangeValue extends Value {
   private final int minimum;
   private final int maximum;
   private int minValue;
   private int maxValue;
   private final int defaultMin;
   private final int defaultMax;

   public IntegerRangeValue(@NotNull String name, int minValue, int maxValue, int minimum, int maximum, @NotNull Function0 displayable) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(displayable, "displayable");
      super(name, new IntRange(RangesKt.coerceIn(minValue, minimum, maximum), RangesKt.coerceIn(maxValue, minimum, maximum)), (Function0)null, 4, (DefaultConstructorMarker)null);
      this.minimum = minimum;
      this.maximum = maximum;
      this.minValue = RangesKt.coerceIn(minValue, this.minimum, this.maximum);
      this.maxValue = RangesKt.coerceIn(maxValue, this.minimum, this.maximum);
      this.defaultMin = this.minValue;
      this.defaultMax = this.maxValue;
      if (this.minValue > this.maxValue) {
         this.maxValue = this.minValue;
      }

   }

   // $FF: synthetic method
   public IntegerRangeValue(String var1, int var2, int var3, int var4, int var5, Function0 var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 8) != 0) {
         var4 = 0;
      }

      if ((var7 & 16) != 0) {
         var5 = Integer.MAX_VALUE;
      }

      if ((var7 & 32) != 0) {
         var6 = null.INSTANCE;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   public final int getMinimum() {
      return this.minimum;
   }

   public final int getMaximum() {
      return this.maximum;
   }

   @NotNull
   public IntRange get() {
      return new IntRange(this.minValue, this.maxValue);
   }

   public final void setMin(int value) {
      this.minValue = RangesKt.coerceIn(value, this.minimum, this.maximum);
      if (this.minValue > this.maxValue) {
         this.maxValue = this.minValue;
      }

   }

   public final void setMax(int value) {
      this.maxValue = RangesKt.coerceIn(value, this.minimum, this.maximum);
      if (this.maxValue < this.minValue) {
         this.minValue = this.maxValue;
      }

   }

   public void setDefault() {
      this.minValue = this.defaultMin;
      this.maxValue = this.defaultMax;
   }

   @NotNull
   public JsonElement toJson() {
      JsonObject obj = new JsonObject();
      obj.addProperty("min", (Number)this.minValue);
      obj.addProperty("max", (Number)this.maxValue);
      return (JsonElement)obj;
   }

   public void fromJson(@NotNull JsonElement element) {
      Intrinsics.checkNotNullParameter(element, "element");
      if (element.isJsonObject()) {
         JsonObject obj = element.getAsJsonObject();
         JsonElement var10001 = obj.get("min");
         Integer var3 = var10001 == null ? null : var10001.getAsInt();
         this.setMin(var3 == null ? this.minValue : var3);
         JsonElement var4 = obj.get("max");
         Integer var5 = var4 == null ? null : var4.getAsInt();
         this.setMax(var5 == null ? this.maxValue : var5);
      }

   }
}
