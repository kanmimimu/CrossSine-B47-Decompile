package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.FontUtils;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0004J\b\u0010\u0017\u001a\u00020\u0014H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/FontValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lnet/minecraft/client/gui/FontRenderer;", "valueName", "", "value", "(Ljava/lang/String;Lnet/minecraft/client/gui/FontRenderer;)V", "openList", "", "getOpenList", "()Z", "setOpenList", "(Z)V", "values", "", "getValues", "()Ljava/util/List;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "set", "name", "toJson", "CrossSine"}
)
public final class FontValue extends Value {
   private boolean openList;

   public FontValue(@NotNull String valueName, @NotNull FontRenderer value) {
      Intrinsics.checkNotNullParameter(valueName, "valueName");
      Intrinsics.checkNotNullParameter(value, "value");
      super(valueName, value, (Function0)null, 4, (DefaultConstructorMarker)null);
   }

   public final boolean getOpenList() {
      return this.openList;
   }

   public final void setOpenList(boolean var1) {
      this.openList = var1;
   }

   @NotNull
   public JsonElement toJson() {
      Object[] fontDetails = Fonts.getFontDetails((FontRenderer)this.getValue());
      JsonObject valueObject = new JsonObject();
      Object var10002 = fontDetails[0];
      if (fontDetails[0] == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         valueObject.addProperty("fontName", (String)var10002);
         var10002 = fontDetails[1];
         if (fontDetails[1] == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
         } else {
            valueObject.addProperty("fontSize", (Number)(Integer)var10002);
            return (JsonElement)valueObject;
         }
      }
   }

   public void fromJson(@NotNull JsonElement element) {
      Intrinsics.checkNotNullParameter(element, "element");
      if (element.isJsonObject()) {
         JsonObject valueObject = element.getAsJsonObject();
         FontRenderer var3 = Fonts.getFontRenderer(valueObject.get("fontName").getAsString(), valueObject.get("fontSize").getAsInt());
         Intrinsics.checkNotNullExpressionValue(var3, "getFontRenderer(valueObj…Object[\"fontSize\"].asInt)");
         this.setValue(var3);
      }
   }

   @NotNull
   public final List getValues() {
      Object[] $this$map$iv = FontUtils.INSTANCE.getAllFontDetails();
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.length));
      int $i$f$mapTo = 0;
      Object var6 = $this$map$iv;
      int var7 = 0;
      int var8 = $this$map$iv.length;

      while(var7 < var8) {
         Object item$iv$iv = ((Object[])var6)[var7];
         ++var7;
         int var11 = 0;
         destination$iv$iv.add((FontRenderer)((Pair)item$iv$iv).getSecond());
      }

      return (List)destination$iv$iv;
   }

   public final boolean set(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      if (StringsKt.equals(name, "Minecraft", true)) {
         FontRenderer var4 = Fonts.minecraftFont;
         Intrinsics.checkNotNullExpressionValue(var4, "minecraftFont");
         this.set(var4);
         return true;
      } else if (StringsKt.contains$default((CharSequence)name, (CharSequence)" - ", false, 2, (Object)null)) {
         CharSequence var10000 = (CharSequence)name;
         String[] var3 = new String[]{" - "};
         List spiced = StringsKt.split$default(var10000, var3, false, 0, 6, (Object)null);
         FontRenderer var10001 = Fonts.getFontRenderer((String)spiced.get(0), Integer.parseInt((String)spiced.get(1)));
         if (var10001 == null) {
            return false;
         } else {
            this.set(var10001);
            return true;
         }
      } else {
         return false;
      }
   }
}
