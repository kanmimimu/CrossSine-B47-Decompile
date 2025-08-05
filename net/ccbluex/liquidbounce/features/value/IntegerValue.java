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
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B5\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bB/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0004¢\u0006\u0002\u0010\rB'\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\u000eB?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0004\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u000e\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "name", "", "value", "minimum", "maximum", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;IIILkotlin/jvm/functions/Function0;)V", "suffix", "(Ljava/lang/String;IIILjava/lang/String;)V", "(Ljava/lang/String;III)V", "(Ljava/lang/String;IIILjava/lang/String;Lkotlin/jvm/functions/Function0;)V", "getMaximum", "()I", "getMinimum", "getSuffix", "()Ljava/lang/String;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "set", "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", "CrossSine"}
)
public class IntegerValue extends Value {
   private final int minimum;
   private final int maximum;
   @NotNull
   private final String suffix;

   public IntegerValue(@NotNull String name, int value, int minimum, int maximum, @NotNull String suffix, @NotNull Function0 displayable) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      Intrinsics.checkNotNullParameter(displayable, "displayable");
      super(name, value, (Function0)null, 4, (DefaultConstructorMarker)null);
      this.minimum = minimum;
      this.maximum = maximum;
      this.suffix = suffix;
   }

   // $FF: synthetic method
   public IntegerValue(String var1, int var2, int var3, int var4, String var5, Function0 var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 4) != 0) {
         var3 = 0;
      }

      if ((var7 & 8) != 0) {
         var4 = Integer.MAX_VALUE;
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
   public final String getSuffix() {
      return this.suffix;
   }

   public IntegerValue(@NotNull String name, int value, int minimum, int maximum, @NotNull Function0 displayable) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(displayable, "displayable");
      this(name, value, minimum, maximum, "", displayable);
   }

   public IntegerValue(@NotNull String name, int value, int minimum, int maximum, @NotNull String suffix) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      this(name, value, minimum, maximum, suffix, null.INSTANCE);
   }

   public IntegerValue(@NotNull String name, int value, int minimum, int maximum) {
      Intrinsics.checkNotNullParameter(name, "name");
      this(name, value, minimum, maximum, null.INSTANCE);
   }

   public final void set(@NotNull Number newValue) {
      Intrinsics.checkNotNullParameter(newValue, "newValue");
      this.set(newValue.intValue());
   }

   @NotNull
   public JsonPrimitive toJson() {
      return new JsonPrimitive((Number)this.getValue());
   }

   public void fromJson(@NotNull JsonElement element) {
      Intrinsics.checkNotNullParameter(element, "element");
      if (element.isJsonPrimitive()) {
         this.setValue(element.getAsInt());
      }

   }
}
