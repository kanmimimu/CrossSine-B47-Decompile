package net.ccbluex.liquidbounce.script.api.global;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.BlockValue;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/global/Setting;", "", "()V", "block", "Lnet/ccbluex/liquidbounce/features/value/BlockValue;", "settingInfo", "Ljdk/nashorn/api/scripting/JSObject;", "bool", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "boolean", "float", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "int", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "integer", "list", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "text", "Lnet/ccbluex/liquidbounce/features/value/TextValue;", "CrossSine"}
)
public final class Setting {
   @NotNull
   public static final Setting INSTANCE = new Setting();

   private Setting() {
   }

   @JvmStatic
   @NotNull
   public static final BoolValue boolean(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Object var10000 = settingInfo.getMember("name");
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         String name = (String)var10000;
         var10000 = settingInfo.getMember("default");
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
         } else {
            boolean var2 = (Boolean)var10000;
            return new BoolValue(name, var2);
         }
      }
   }

   @JvmStatic
   @NotNull
   public static final BoolValue bool(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Setting var10000 = INSTANCE;
      return boolean(settingInfo);
   }

   @JvmStatic
   @NotNull
   public static final IntegerValue integer(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Object var10000 = settingInfo.getMember("name");
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         String name = (String)var10000;
         var10000 = settingInfo.getMember("default");
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Number");
         } else {
            int var2 = ((Number)var10000).intValue();
            var10000 = settingInfo.getMember("min");
            if (var10000 == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.Number");
            } else {
               int min = ((Number)var10000).intValue();
               var10000 = settingInfo.getMember("max");
               if (var10000 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Number");
               } else {
                  int max = ((Number)var10000).intValue();
                  return new IntegerValue(name, var2, min, max);
               }
            }
         }
      }
   }

   @JvmStatic
   @NotNull
   public static final IntegerValue int(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Setting var10000 = INSTANCE;
      return integer(settingInfo);
   }

   @JvmStatic
   @NotNull
   public static final FloatValue float(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Object var10000 = settingInfo.getMember("name");
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         String name = (String)var10000;
         var10000 = settingInfo.getMember("default");
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Number");
         } else {
            float var2 = ((Number)var10000).floatValue();
            var10000 = settingInfo.getMember("min");
            if (var10000 == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.Number");
            } else {
               float min = ((Number)var10000).floatValue();
               var10000 = settingInfo.getMember("max");
               if (var10000 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Number");
               } else {
                  float max = ((Number)var10000).floatValue();
                  return new FloatValue(name, var2, min, max);
               }
            }
         }
      }
   }

   @JvmStatic
   @NotNull
   public static final TextValue text(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Object var10000 = settingInfo.getMember("name");
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         String name = (String)var10000;
         var10000 = settingInfo.getMember("default");
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
         } else {
            String var2 = (String)var10000;
            return new TextValue(name, var2);
         }
      }
   }

   @JvmStatic
   @NotNull
   public static final BlockValue block(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Object var10000 = settingInfo.getMember("name");
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         String name = (String)var10000;
         var10000 = settingInfo.getMember("default");
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Number");
         } else {
            int var2 = ((Number)var10000).intValue();
            return new BlockValue(name, var2);
         }
      }
   }

   @JvmStatic
   @NotNull
   public static final ListValue list(@NotNull JSObject settingInfo) {
      Intrinsics.checkNotNullParameter(settingInfo, "settingInfo");
      Object var10000 = settingInfo.getMember("name");
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         String name = (String)var10000;
         var10000 = ScriptUtils.convert(settingInfo.getMember("values"), String[].class);
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
         } else {
            String[] values = (String[])var10000;
            var10000 = settingInfo.getMember("default");
            if (var10000 == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            } else {
               String var3 = (String)var10000;
               return new ListValue(name, values, var3);
            }
         }
      }
   }
}
