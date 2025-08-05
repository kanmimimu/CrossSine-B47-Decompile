package net.ccbluex.liquidbounce.script.remapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\u00020\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eH\u0002J\u001a\u0010\u000f\u001a\u00020\u00052\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u0005J\"\u0010\u0013\u001a\u00020\u00052\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005RR\u0010\u0003\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000RR\u0010\u0007\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/Remapper;", "", "()V", "fields", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "methods", "srgLoaded", "", "loadSrg", "", "parseSrg", "srgData", "", "remapField", "clazz", "Ljava/lang/Class;", "name", "remapMethod", "desc", "CrossSine"}
)
public final class Remapper {
   @NotNull
   public static final Remapper INSTANCE = new Remapper();
   private static boolean srgLoaded;
   @NotNull
   private static final HashMap fields = new HashMap();
   @NotNull
   private static final HashMap methods = new HashMap();

   private Remapper() {
   }

   public final void loadSrg() {
      if (!srgLoaded) {
         ClientUtils.INSTANCE.logInfo("[Remapper] Loading srg...");
         List var1 = IOUtils.readLines(Remapper.class.getClassLoader().getResourceAsStream("assets/minecraft/crosssine/scriptapi/mcp-stable_22.srg"));
         Intrinsics.checkNotNullExpressionValue(var1, "readLines(Remapper::clas…tapi/mcp-stable_22.srg\"))");
         this.parseSrg(var1);
         srgLoaded = true;
         ClientUtils.INSTANCE.logInfo("[Remapper] Loaded srg.");
      }
   }

   private final void parseSrg(List srgData) {
      Iterable $this$forEach$iv = (Iterable)srgData;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         String it = (String)element$iv;
         int var7 = 0;
         CharSequence var10000 = (CharSequence)it;
         String name = new String[]{" "};
         List args = StringsKt.split$default(var10000, name, false, 0, 6, (Object)null);
         if (StringsKt.startsWith$default(it, "FD:", false, 2, (Object)null)) {
            name = (String)args.get(1);
            String srg = (String)args.get(2);
            byte var12 = 0;
            int var13 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, (Object)null);
            String var14 = name.substring(var12, var13);
            Intrinsics.checkNotNullExpressionValue(var14, "this as java.lang.String…ing(startIndex, endIndex)");
            String className = StringsKt.replace$default(var14, '/', '.', false, 4, (Object)null);
            var13 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, (Object)null) + 1;
            var14 = name.substring(var13);
            Intrinsics.checkNotNullExpressionValue(var14, "this as java.lang.String).substring(startIndex)");
            String fieldName = var14;
            int var28 = StringsKt.lastIndexOf$default((CharSequence)srg, '/', 0, false, 6, (Object)null) + 1;
            String fieldSrg = srg.substring(var28);
            Intrinsics.checkNotNullExpressionValue(fieldSrg, "this as java.lang.String).substring(startIndex)");
            if (!((Map)fields).containsKey(className)) {
               Map var24 = (Map)fields;
               HashMap var29 = new HashMap();
               var24.put(className, var29);
            }

            Object var41 = fields.get(className);
            Intrinsics.checkNotNull(var41);
            Object var25 = var41;
            Intrinsics.checkNotNullExpressionValue(var25, "fields[className]!!");
            ((Map)var25).put(fieldSrg, fieldName);
         } else if (StringsKt.startsWith$default(it, "MD:", false, 2, (Object)null)) {
            name = (String)args.get(1);
            String desc = (String)args.get(2);
            String srg = (String)args.get(3);
            byte var26 = 0;
            int var30 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, (Object)null);
            String fieldSrg = name.substring(var26, var30);
            Intrinsics.checkNotNullExpressionValue(fieldSrg, "this as java.lang.String…ing(startIndex, endIndex)");
            String className = StringsKt.replace$default(fieldSrg, '/', '.', false, 4, (Object)null);
            var30 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, (Object)null) + 1;
            fieldSrg = name.substring(var30);
            Intrinsics.checkNotNullExpressionValue(fieldSrg, "this as java.lang.String).substring(startIndex)");
            String methodName = fieldSrg;
            int fieldSrg = StringsKt.lastIndexOf$default((CharSequence)srg, '/', 0, false, 6, (Object)null) + 1;
            String methodSrg = srg.substring(fieldSrg);
            Intrinsics.checkNotNullExpressionValue(methodSrg, "this as java.lang.String).substring(startIndex)");
            if (!((Map)methods).containsKey(className)) {
               Map var32 = (Map)methods;
               HashMap fieldSrg = new HashMap();
               var32.put(className, fieldSrg);
            }

            Object var42 = methods.get(className);
            Intrinsics.checkNotNull(var42);
            Object var33 = var42;
            Intrinsics.checkNotNullExpressionValue(var33, "methods[className]!!");
            Map var34 = (Map)var33;
            String fieldSrg = Intrinsics.stringPlus(methodSrg, desc);
            var34.put(fieldSrg, methodName);
         }
      }

   }

   @NotNull
   public final String remapField(@NotNull Class clazz, @NotNull String name) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      Intrinsics.checkNotNullParameter(name, "name");
      HashMap var10000 = (HashMap)fields.get(clazz.getName());
      String var3;
      if (var10000 == null) {
         var3 = name;
      } else {
         var3 = (String)var10000.getOrDefault(name, name);
         if (var3 == null) {
            var3 = name;
         }
      }

      return var3;
   }

   @NotNull
   public final String remapMethod(@NotNull Class clazz, @NotNull String name, @NotNull String desc) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(desc, "desc");
      HashMap var10000 = (HashMap)methods.get(clazz.getName());
      String var4;
      if (var10000 == null) {
         var4 = name;
      } else {
         var4 = (String)var10000.getOrDefault(Intrinsics.stringPlus(name, desc), name);
         if (var4 == null) {
            var4 = name;
         }
      }

      return var4;
   }
}
