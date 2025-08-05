package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Cape",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Cape;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "capeCache", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/features/module/modules/visual/Cape$CapeStyle;", "Lkotlin/collections/HashMap;", "styleValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getStyleValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "getTag", "()Ljava/lang/String;", "getCapeLocation", "Lnet/minecraft/util/ResourceLocation;", "value", "CapeStyle", "CrossSine"}
)
public final class Cape extends Module {
   @NotNull
   private final ListValue styleValue;
   @NotNull
   private final HashMap capeCache;

   public Cape() {
      String[] var1 = new String[]{"CrossSine", "Astolfo", "Black", "Rise", "Novoline", "Styles", "None"};
      this.styleValue = new ListValue("Style", var1, "None");
      this.capeCache = new HashMap();
      this.setState(true);
   }

   @NotNull
   public final ListValue getStyleValue() {
      return this.styleValue;
   }

   @NotNull
   public final ResourceLocation getCapeLocation(@NotNull String value) {
      Intrinsics.checkNotNullParameter(value, "value");
      HashMap var10000 = this.capeCache;
      Locale var3 = Locale.getDefault();
      Intrinsics.checkNotNullExpressionValue(var3, "getDefault()");
      String var4 = value.toUpperCase(var3);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toUpperCase(locale)");
      if (var10000.get(var4) == null) {
         try {
            Map var15 = (Map)this.capeCache;
            Locale var9 = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(var9, "getDefault()");
            String var11 = value.toUpperCase(var9);
            Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toUpperCase(locale)");
            String var10001 = var11;
            Locale var12 = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(var12, "getDefault()");
            String var13 = value.toUpperCase(var12);
            Intrinsics.checkNotNullExpressionValue(var13, "this as java.lang.String).toUpperCase(locale)");
            var15.put(var10001, Cape.CapeStyle.valueOf(var13));
         } catch (Exception var7) {
            Map var14 = (Map)this.capeCache;
            Locale var5 = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(var5, "getDefault()");
            String var6 = value.toUpperCase(var5);
            Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).toUpperCase(locale)");
            var14.put(var6, Cape.CapeStyle.CROSSSINE);
         }
      }

      var10000 = this.capeCache;
      var3 = Locale.getDefault();
      Intrinsics.checkNotNullExpressionValue(var3, "getDefault()");
      var4 = value.toUpperCase(var3);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toUpperCase(locale)");
      Object var17 = var10000.get(var4);
      Intrinsics.checkNotNull(var17);
      return ((CapeStyle)var17).getLocation();
   }

   @NotNull
   public String getTag() {
      return (String)this.styleValue.get();
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Cape$CapeStyle;", "", "location", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILnet/minecraft/util/ResourceLocation;)V", "getLocation", "()Lnet/minecraft/util/ResourceLocation;", "CROSSSINE", "ASTOLFO", "BLACK", "RISE", "NOVOLINE", "STYLES", "CrossSine"}
   )
   public static enum CapeStyle {
      @NotNull
      private final ResourceLocation location;
      CROSSSINE(new ResourceLocation("crosssine/cape/crosssine.png")),
      ASTOLFO(new ResourceLocation("crosssine/cape/astolfo.png")),
      BLACK(new ResourceLocation("crosssine/cape/black.png")),
      RISE(new ResourceLocation("crosssine/cape/risecape.png")),
      NOVOLINE(new ResourceLocation("crosssine/cape/novoline.png")),
      STYLES(new ResourceLocation("crosssine/cape/styles.png"));

      private CapeStyle(ResourceLocation location) {
         this.location = location;
      }

      @NotNull
      public final ResourceLocation getLocation() {
         return this.location;
      }

      // $FF: synthetic method
      private static final CapeStyle[] $values() {
         CapeStyle[] var0 = new CapeStyle[]{CROSSSINE, ASTOLFO, BLACK, RISE, NOVOLINE, STYLES};
         return var0;
      }
   }
}
