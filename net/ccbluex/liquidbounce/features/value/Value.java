package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0015\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0005\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u000e\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u0004J\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u0013\u0010 \u001a\u00020\b2\b\u0010!\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020$H&J\r\u0010%\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\fJ\b\u0010&\u001a\u00020\bH\u0016J\u001d\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00028\u00002\u0006\u0010)\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010*J\u001d\u0010+\u001a\u00020\u001c2\u0006\u0010(\u001a\u00028\u00002\u0006\u0010)\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010*J\u0015\u0010,\u001a\u00020\u001c2\u0006\u0010)\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\b\u0010-\u001a\u00020\u001cH\u0016J\u0010\u0010.\u001a\u00020\u001c2\u0006\u0010\u0005\u001a\u00020\bH\u0016J\n\u0010/\u001a\u0004\u0018\u00010$H&R\u0013\u0010\n\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u000e\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0010\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0005\u001a\u00028\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\u0018\u0010\f\"\u0004\b\u0019\u0010\u001a¨\u00060"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/Value;", "T", "", "name", "", "value", "displayableFunc", "Lkotlin/Function0;", "", "(Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "default", "getDefault", "()Ljava/lang/Object;", "Ljava/lang/Object;", "displayable", "getDisplayable", "()Z", "expanded", "getName", "()Ljava/lang/String;", "textHovered", "getTextHovered", "setTextHovered", "(Z)V", "getValue", "setValue", "(Ljava/lang/Object;)V", "changeValue", "", "contains", "text", "func", "equals", "other", "fromJson", "element", "Lcom/google/gson/JsonElement;", "get", "isExpanded", "onChange", "oldValue", "newValue", "(Ljava/lang/Object;Ljava/lang/Object;)V", "onChanged", "set", "setDefault", "setExpanded", "toJson", "CrossSine"}
)
public abstract class Value {
   @NotNull
   private final String name;
   private Object value;
   @NotNull
   private Function0 displayableFunc;
   private final Object default;
   private boolean textHovered;
   private boolean expanded;

   public Value(@NotNull String name, Object value, @NotNull Function0 displayableFunc) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(displayableFunc, "displayableFunc");
      super();
      this.name = name;
      this.value = value;
      this.displayableFunc = displayableFunc;
      this.default = this.value;
   }

   // $FF: synthetic method
   public Value(String var1, Object var2, Function0 var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 4) != 0) {
         var3 = null.INSTANCE;
      }

      this(var1, var2, var3);
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final Object getValue() {
      return this.value;
   }

   public final void setValue(Object var1) {
      this.value = var1;
   }

   public final Object getDefault() {
      return this.default;
   }

   public final boolean getTextHovered() {
      return this.textHovered;
   }

   public final void setTextHovered(boolean var1) {
      this.textHovered = var1;
   }

   public final boolean getDisplayable() {
      return (Boolean)this.displayableFunc.invoke();
   }

   @NotNull
   public final Value displayable(@NotNull Function0 func) {
      Intrinsics.checkNotNullParameter(func, "func");
      this.displayableFunc = func;
      return this;
   }

   public Object get() {
      return this.value;
   }

   public void changeValue(Object value) {
      this.value = value;
   }

   public void set(Object newValue) {
      if (!Intrinsics.areEqual(newValue, this.value)) {
         Object oldValue = this.value;

         try {
            this.onChange(oldValue, newValue);
            this.value = newValue;
            this.onChanged(oldValue, newValue);
            CrossSine.INSTANCE.getConfigManager().smartSave();
         } catch (Exception e) {
            ClientUtils.INSTANCE.logError("[ValueSystem (" + this.name + ")]: " + e.getClass().getName() + " (" + e.getMessage() + ") [" + oldValue + " >> " + newValue + ']');
         }

      }
   }

   public void setDefault() {
      this.value = this.default;
   }

   @Nullable
   public abstract JsonElement toJson();

   public abstract void fromJson(@NotNull JsonElement var1);

   public boolean equals(@Nullable Object other) {
      if (other == null) {
         return false;
      } else if (this.value instanceof String && other instanceof String) {
         Object var4 = this.value;
         if (var4 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
         } else {
            return StringsKt.equals((String)var4, (String)other, true);
         }
      } else {
         Object var10000 = this.value;
         boolean var3;
         if (var10000 == null) {
            var3 = false;
         } else {
            boolean var2 = var10000.equals(other);
            var3 = var2;
         }

         return var3;
      }
   }

   public final boolean contains(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      boolean var2;
      if (this.value instanceof String) {
         Object var10000 = this.value;
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
         }

         var2 = StringsKt.contains((CharSequence)((String)var10000), (CharSequence)text, true);
      } else {
         var2 = false;
      }

      return var2;
   }

   protected void onChange(Object oldValue, Object newValue) {
   }

   protected void onChanged(Object oldValue, Object newValue) {
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public void setExpanded(boolean value) {
      this.expanded = value;
   }
}
