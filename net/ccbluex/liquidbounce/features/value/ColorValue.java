package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/ColorValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Ljava/awt/Color;", "name", "", "value", "alpha", "", "(Ljava/lang/String;Ljava/awt/Color;Z)V", "getAlpha", "()Z", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "CrossSine"}
)
public class ColorValue extends Value {
   private final boolean alpha;

   public ColorValue(@NotNull String name, @NotNull Color value, boolean alpha) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(value, "value");
      super(name, value, (Function0)null, 4, (DefaultConstructorMarker)null);
      this.alpha = alpha;
   }

   // $FF: synthetic method
   public ColorValue(String var1, Color var2, boolean var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 4) != 0) {
         var3 = true;
      }

      this(var1, var2, var3);
   }

   public final boolean getAlpha() {
      return this.alpha;
   }

   @NotNull
   public JsonElement toJson() {
      Color color = (Color)this.getValue();
      JsonObject $this$toJson_u24lambda_u2d0 = new JsonObject();
      int var4 = 0;
      $this$toJson_u24lambda_u2d0.addProperty("r", (Number)color.getRed());
      $this$toJson_u24lambda_u2d0.addProperty("g", (Number)color.getGreen());
      $this$toJson_u24lambda_u2d0.addProperty("b", (Number)color.getBlue());
      $this$toJson_u24lambda_u2d0.addProperty("a", (Number)color.getAlpha());
      return (JsonElement)$this$toJson_u24lambda_u2d0;
   }

   public void fromJson(@NotNull JsonElement element) {
      Intrinsics.checkNotNullParameter(element, "element");
      if (element.isJsonObject()) {
         JsonObject obj = element.getAsJsonObject();
         JsonElement var10000 = obj.get("r");
         int var11;
         if (var10000 == null) {
            var11 = 255;
         } else {
            int var5 = var10000.getAsInt();
            var11 = var5;
         }

         int r = var11;
         JsonElement var12 = obj.get("g");
         int var13;
         if (var12 == null) {
            var13 = 255;
         } else {
            int var6 = var12.getAsInt();
            var13 = var6;
         }

         int g = var13;
         JsonElement var14 = obj.get("b");
         int var15;
         if (var14 == null) {
            var15 = 255;
         } else {
            int var7 = var14.getAsInt();
            var15 = var7;
         }

         int b = var15;
         JsonElement var16 = obj.get("a");
         int var17;
         if (var16 == null) {
            var17 = 255;
         } else {
            int var8 = var16.getAsInt();
            var17 = var8;
         }

         int a = var17;
         this.setValue(new Color(r, g, b, a));
      }

   }
}
