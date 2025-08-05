package net.ccbluex.liquidbounce.script;

import com.sun.jna.Native;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import jdk.nashorn.api.scripting.ClassFilter;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u000eJ\u001a\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0014\u001a\u00020\u0005J\u0012\u0010\u0015\u001a\u00020\u00122\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000eJ\u001a\u0010\u0015\u001a\u00020\u00122\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0014\u001a\u00020\u0005J\u001a\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/ScriptSafetyManager;", "", "()V", "alerted", "", "", "classFilter", "Ljdk/nashorn/api/scripting/ClassFilter;", "getClassFilter", "()Ljdk/nashorn/api/scripting/ClassFilter;", "level", "", "restrictedChilds", "", "Ljava/lang/Class;", "Lkotlin/Pair;", "restrictedClasses", "isRestricted", "", "classIn", "child", "isRestrictedSimple", "klass", "warnRestricted", "", "ProtectionLevel", "CrossSine"}
)
public final class ScriptSafetyManager {
   @NotNull
   public static final ScriptSafetyManager INSTANCE = new ScriptSafetyManager();
   private static final int level;
   @NotNull
   private static final Map restrictedClasses;
   @NotNull
   private static final Map restrictedChilds;
   @NotNull
   private static final ClassFilter classFilter;
   @NotNull
   private static final List alerted;

   private ScriptSafetyManager() {
   }

   @NotNull
   public final ClassFilter getClassFilter() {
      return classFilter;
   }

   public final boolean isRestricted(@NotNull Class classIn) {
      Intrinsics.checkNotNullParameter(classIn, "classIn");

      Class var3;
      for(Class klass = classIn; !Intrinsics.areEqual((Object)klass, (Object)Object.class); klass = var3) {
         if (this.isRestrictedSimple(klass)) {
            return true;
         }

         if (klass.getSuperclass() == null) {
            break;
         }

         var3 = klass.getSuperclass();
         Intrinsics.checkNotNullExpressionValue(var3, "klass.superclass");
      }

      return false;
   }

   public final boolean isRestricted(@NotNull Class classIn, @NotNull String child) {
      Intrinsics.checkNotNullParameter(classIn, "classIn");
      Intrinsics.checkNotNullParameter(child, "child");

      Class var4;
      for(Class klass = classIn; !Intrinsics.areEqual((Object)klass, (Object)Object.class); klass = var4) {
         if (this.isRestrictedSimple(klass, child)) {
            return true;
         }

         if (klass.getSuperclass() == null) {
            break;
         }

         var4 = klass.getSuperclass();
         Intrinsics.checkNotNullExpressionValue(var4, "klass.superclass");
      }

      return false;
   }

   public final boolean isRestrictedSimple(@NotNull Class klass) {
      Intrinsics.checkNotNullParameter(klass, "klass");
      boolean var3;
      if (restrictedClasses.containsKey(klass)) {
         Object var10000 = restrictedClasses.get(klass);
         Intrinsics.checkNotNull(var10000);
         if (((Number)var10000).intValue() > level) {
            String var2 = klass.getName();
            Intrinsics.checkNotNullExpressionValue(var2, "klass.name");
            this.warnRestricted(var2, "");
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   public final boolean isRestrictedSimple(@NotNull Class klass, @NotNull String child) {
      Intrinsics.checkNotNullParameter(klass, "klass");
      Intrinsics.checkNotNullParameter(child, "child");
      boolean var10000;
      if (this.isRestrictedSimple(klass)) {
         String var3 = klass.getName();
         Intrinsics.checkNotNullExpressionValue(var3, "klass.name");
         this.warnRestricted(var3, "");
         var10000 = true;
      } else {
         if (restrictedChilds.containsKey(klass)) {
            Object var5 = restrictedChilds.get(klass);
            Intrinsics.checkNotNull(var5);
            if (Intrinsics.areEqual((Object)((Pair)var5).getFirst(), (Object)child)) {
               var5 = restrictedChilds.get(klass);
               Intrinsics.checkNotNull(var5);
               if (((Number)((Pair)var5).getSecond()).intValue() > level) {
                  String var4 = klass.getName();
                  Intrinsics.checkNotNullExpressionValue(var4, "klass.name");
                  this.warnRestricted(var4, child);
                  var10000 = true;
                  return var10000;
               }
            }
         }

         var10000 = false;
      }

      return var10000;
   }

   private final void warnRestricted(String klass, String child) {
      String message = Intrinsics.stringPlus(klass, ((CharSequence)child).length() > 0 ? Intrinsics.stringPlus(".", child) : "");
      if (!alerted.contains(message)) {
         alerted.add(message);
         ClientUtils.INSTANCE.logWarn("[ScriptAPI] \n========= WARNING =========\nThe script tried to make a restricted call: " + message + ",\nplease add a jvm argument to disable this check: -Dcrosssine.script.safety=HARMFUL\n===========================");
      }

   }

   // $FF: synthetic method
   static void warnRestricted$default(ScriptSafetyManager var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = "";
      }

      var0.warnRestricted(var1, var2);
   }

   private static final boolean classFilter$lambda_0/* $FF was: classFilter$lambda-0*/(String name) {
      boolean var1;
      try {
         ScriptSafetyManager var10000 = INSTANCE;
         Class var4 = Class.forName(name);
         Intrinsics.checkNotNullExpressionValue(var4, "forName(name)");
         var1 = !var10000.isRestricted(var4);
      } catch (ClassNotFoundException var3) {
         var1 = false;
      }

      return var1;
   }

   static {
      String var10000 = System.getProperty("crosssine.script.safety");
      if (var10000 == null) {
         var10000 = "safe";
      }

      String var1 = var10000.toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.String).toUpperCase(Locale.ROOT)");
      level = ScriptSafetyManager.ProtectionLevel.valueOf(var1).getLevel();
      classFilter = ScriptSafetyManager::classFilter$lambda-0;
      ClientUtils var15 = ClientUtils.INSTANCE;
      StringBuilder var10001 = (new StringBuilder()).append("[ScriptAPI] Script safety level: ");
      ProtectionLevel[] var12 = ScriptSafetyManager.ProtectionLevel.values();
      StringBuilder var10 = var10001;
      ClientUtils var9 = var15;
      ProtectionLevel[] var3 = var12;
      int var4 = 0;
      int var5 = var12.length;

      while(true) {
         if (var4 < var5) {
            ProtectionLevel it = var3[var4];
            ++var4;
            int var8 = 0;
            if (it.getLevel() != level) {
               continue;
            }

            var16 = it;
            break;
         }

         var16 = null;
         break;
      }

      ProtectionLevel var11 = var16;
      var9.logInfo(var10.append(var11 == null ? null : var11.name()).append('(').append(level).append(')').toString());
      Map restrictedClasses = (Map)(new LinkedHashMap());
      Map restrictedChilds = (Map)(new LinkedHashMap());
      restrictedClasses.put(ScriptSafetyManager.class, ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel());
      restrictedClasses.put(ClassLoader.class, ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel());
      restrictedClasses.put(Native.class, ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel());
      restrictedClasses.put(Runtime.class, ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel());
      restrictedChilds.put(System.class, new Pair("loadLibrary", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(System.class, new Pair("load", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("forName", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getDeclaredField", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getDeclaredMethod", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getField", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getMethod", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getDeclaredFields", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getDeclaredMethods", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getFields", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getMethods", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getConstructor", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getDeclaredConstructor", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getConstructors", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("getDeclaredConstructors", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedChilds.put(Class.class, new Pair("newInstance", ScriptSafetyManager.ProtectionLevel.HARMFUL.getLevel()));
      restrictedClasses.put(URL.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(Socket.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(URLConnection.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(HttpUtils.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(File.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(Path.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(FileUtils.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(Files.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(InputStream.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(OutputStream.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(com.google.common.io.Files.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      restrictedClasses.put(net.ccbluex.liquidbounce.utils.FileUtils.class, ScriptSafetyManager.ProtectionLevel.DANGER.getLevel());
      Map var2 = Collections.unmodifiableMap(restrictedClasses);
      Intrinsics.checkNotNullExpressionValue(var2, "unmodifiableMap(restrictedClasses)");
      restrictedClasses = var2;
      var2 = Collections.unmodifiableMap(restrictedChilds);
      Intrinsics.checkNotNullExpressionValue(var2, "unmodifiableMap(restrictedChilds)");
      restrictedChilds = var2;
      alerted = (List)(new ArrayList());
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"},
      d2 = {"Lnet/ccbluex/liquidbounce/script/ScriptSafetyManager$ProtectionLevel;", "", "level", "", "(Ljava/lang/String;II)V", "getLevel", "()I", "SAFE", "DANGER", "HARMFUL", "CrossSine"}
   )
   public static enum ProtectionLevel {
      private final int level;
      SAFE(0),
      DANGER(1),
      HARMFUL(2);

      private ProtectionLevel(int level) {
         this.level = level;
      }

      public final int getLevel() {
         return this.level;
      }

      // $FF: synthetic method
      private static final ProtectionLevel[] $values() {
         ProtectionLevel[] var0 = new ProtectionLevel[]{SAFE, DANGER, HARMFUL};
         return var0;
      }
   }
}
