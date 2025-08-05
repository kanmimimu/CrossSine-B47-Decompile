package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}
)
public final class PlatformImplementationsKt {
   @JvmField
   @NotNull
   public static final PlatformImplementations IMPLEMENTATIONS;

   // $FF: synthetic method
   @InlineOnly
   private static final Object castToBaseType(Object instance) {
      try {
         Intrinsics.reifiedOperationMarker(1, "T");
         return instance;
      } catch (ClassCastException e) {
         ClassLoader instanceCL = instance.getClass().getClassLoader();
         Intrinsics.reifiedOperationMarker(4, "T");
         ClassLoader baseTypeCL = ((Class)Object.class).getClassLoader();
         Throwable var4 = (new ClassCastException("Instance classloader: " + instanceCL + ", base type classloader: " + baseTypeCL)).initCause((Throwable)e);
         Intrinsics.checkNotNullExpressionValue(var4, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
         throw (Throwable)var4;
      }
   }

   private static final int getJavaVersion() {
      int var0 = 65542;
      String var2 = System.getProperty("java.specification.version");
      if (var2 == null) {
         return var0;
      } else {
         String version = var2;
         int firstDot = StringsKt.indexOf$default((CharSequence)var2, '.', 0, false, 6, (Object)null);
         if (firstDot < 0) {
            int var12;
            try {
               var12 = Integer.parseInt(version) * 65536;
            } catch (NumberFormatException var9) {
               var12 = var0;
            }

            return var12;
         } else {
            int secondDot = StringsKt.indexOf$default((CharSequence)version, '.', firstDot + 1, false, 4, (Object)null);
            if (secondDot < 0) {
               secondDot = version.length();
            }

            int var6 = 0;
            String var7 = version.substring(var6, firstDot);
            Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String…ing(startIndex, endIndex)");
            String firstPart = var7;
            int var14 = firstDot + 1;
            String var8 = version.substring(var14, secondDot);
            Intrinsics.checkNotNullExpressionValue(var8, "this as java.lang.String…ing(startIndex, endIndex)");
            String secondPart = var8;

            try {
               var6 = Integer.parseInt(firstPart) * 65536 + Integer.parseInt(secondPart);
            } catch (NumberFormatException var10) {
               var6 = var0;
            }

            return var6;
         }
      }
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.2"
   )
   public static final boolean apiVersionIsAtLeast(int major, int minor, int patch) {
      return KotlinVersion.CURRENT.isAtLeast(major, minor, patch);
   }

   static {
      PlatformImplementations var10000;
      label62: {
         int var0 = 0;
         int version = getJavaVersion();
         if (version >= 65544) {
            try {
               Object var20 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
               Intrinsics.checkNotNullExpressionValue(var20, "forName(\"kotlin.internal…entations\").newInstance()");
               var20 = var20;

               try {
                  var10000 = (PlatformImplementations)var20;
                  break label62;
               } catch (ClassCastException var10) {
                  ClassLoader var24 = var20.getClass().getClassLoader();
                  ClassLoader var27 = PlatformImplementations.class.getClassLoader();
                  Throwable var30 = (new ClassCastException("Instance classloader: " + var24 + ", base type classloader: " + var27)).initCause((Throwable)var10);
                  Intrinsics.checkNotNullExpressionValue(var30, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                  throw var30;
               }
            } catch (ClassNotFoundException var14) {
               try {
                  Object var18 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                  Intrinsics.checkNotNullExpressionValue(var18, "forName(\"kotlin.internal…entations\").newInstance()");
                  var18 = var18;

                  try {
                     var10000 = (PlatformImplementations)var18;
                     break label62;
                  } catch (ClassCastException var9) {
                     ClassLoader var23 = var18.getClass().getClassLoader();
                     ClassLoader var26 = PlatformImplementations.class.getClassLoader();
                     Throwable var29 = (new ClassCastException("Instance classloader: " + var23 + ", base type classloader: " + var26)).initCause((Throwable)var9);
                     Intrinsics.checkNotNullExpressionValue(var29, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                     throw var29;
                  }
               } catch (ClassNotFoundException var13) {
               }
            }
         }

         if (version >= 65543) {
            try {
               Object var16 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
               Intrinsics.checkNotNullExpressionValue(var16, "forName(\"kotlin.internal…entations\").newInstance()");
               var16 = var16;

               try {
                  var10000 = (PlatformImplementations)var16;
                  break label62;
               } catch (ClassCastException var8) {
                  ClassLoader var22 = var16.getClass().getClassLoader();
                  ClassLoader var25 = PlatformImplementations.class.getClassLoader();
                  Throwable var28 = (new ClassCastException("Instance classloader: " + var22 + ", base type classloader: " + var25)).initCause((Throwable)var8);
                  Intrinsics.checkNotNullExpressionValue(var28, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                  throw var28;
               }
            } catch (ClassNotFoundException var12) {
               try {
                  Object var2 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                  Intrinsics.checkNotNullExpressionValue(var2, "forName(\"kotlin.internal…entations\").newInstance()");
                  var2 = var2;

                  try {
                     var10000 = (PlatformImplementations)var2;
                     break label62;
                  } catch (ClassCastException var7) {
                     ClassLoader var4 = var2.getClass().getClassLoader();
                     ClassLoader var5 = PlatformImplementations.class.getClassLoader();
                     Throwable var6 = (new ClassCastException("Instance classloader: " + var4 + ", base type classloader: " + var5)).initCause((Throwable)var7);
                     Intrinsics.checkNotNullExpressionValue(var6, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                     throw var6;
                  }
               } catch (ClassNotFoundException var11) {
               }
            }
         }

         var10000 = new PlatformImplementations();
      }

      IMPLEMENTATIONS = var10000;
   }
}
