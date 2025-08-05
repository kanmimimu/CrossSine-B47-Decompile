package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B1\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016J\b\u0010\u0015\u001a\u00020\u0011H\u0016J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0003J\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0003J\b\u0010\u0019\u001a\u00020\u0013H\u0016R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/FloatRangeValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "name", "", "minValue", "maxValue", "minimum", "maximum", "(Ljava/lang/String;FFFF)V", "defaultMax", "defaultMin", "getMaximum", "()F", "getMinimum", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "get", "setDefault", "setMax", "value", "setMin", "toJson", "CrossSine"}
)
public final class FloatRangeValue extends Value {
   private final float minimum;
   private final float maximum;
   private float minValue;
   private float maxValue;
   private final float defaultMin;
   private final float defaultMax;

   public FloatRangeValue(@NotNull String name, float minValue, float maxValue, float minimum, float maximum) {
      Intrinsics.checkNotNullParameter(name, "name");
      super(name, RangesKt.rangeTo(RangesKt.coerceIn(minValue, minimum, maximum), RangesKt.coerceIn(maxValue, minimum, maximum)), (Function0)null, 4, (DefaultConstructorMarker)null);
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
   public FloatRangeValue(String var1, float var2, float var3, float var4, float var5, int var6, DefaultConstructorMarker var7) {
      if ((var6 & 8) != 0) {
         var4 = 0.0F;
      }

      if ((var6 & 16) != 0) {
         var5 = Float.MAX_VALUE;
      }

      this(var1, var2, var3, var4, var5);
   }

   public final float getMinimum() {
      return this.minimum;
   }

   public final float getMaximum() {
      return this.maximum;
   }

   @NotNull
   public ClosedFloatingPointRange get() {
      return RangesKt.rangeTo(this.minValue, this.maxValue);
   }

   public final void setMin(float value) {
      this.minValue = RangesKt.coerceIn(value, this.minimum, this.maximum);
      if (this.minValue > this.maxValue) {
         this.maxValue = this.minValue;
      }

   }

   public final void setMax(float value) {
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
         Float var3 = var10001 == null ? null : var10001.getAsFloat();
         this.setMin(var3 == null ? this.minValue : var3);
         JsonElement var4 = obj.get("max");
         Float var5 = var4 == null ? null : var4.getAsFloat();
         this.setMax(var5 == null ? this.maxValue : var5);
      }

   }
}
