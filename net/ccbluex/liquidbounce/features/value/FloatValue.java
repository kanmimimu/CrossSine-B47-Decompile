package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "name", "", "value", "minimum", "maximum", "(Ljava/lang/String;FFF)V", "getMaximum", "()F", "getMinimum", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "set", "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", "CrossSine"}
)
public class FloatValue extends Value {
   private final float minimum;
   private final float maximum;

   public FloatValue(@NotNull String name, float value, float minimum, float maximum) {
      Intrinsics.checkNotNullParameter(name, "name");
      super(name, value, (Function0)null, 4, (DefaultConstructorMarker)null);
      this.minimum = minimum;
      this.maximum = maximum;
   }

   // $FF: synthetic method
   public FloatValue(String var1, float var2, float var3, float var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 4) != 0) {
         var3 = 0.0F;
      }

      if ((var5 & 8) != 0) {
         var4 = Float.MAX_VALUE;
      }

      this(var1, var2, var3, var4);
   }

   public final float getMinimum() {
      return this.minimum;
   }

   public final float getMaximum() {
      return this.maximum;
   }

   public final void set(@NotNull Number newValue) {
      Intrinsics.checkNotNullParameter(newValue, "newValue");
      this.set(newValue.floatValue());
   }

   @NotNull
   public JsonPrimitive toJson() {
      return new JsonPrimitive((Number)this.getValue());
   }

   public void fromJson(@NotNull JsonElement element) {
      Intrinsics.checkNotNullParameter(element, "element");
      if (element.isJsonPrimitive()) {
         this.setValue(element.getAsFloat());
      }

   }
}
