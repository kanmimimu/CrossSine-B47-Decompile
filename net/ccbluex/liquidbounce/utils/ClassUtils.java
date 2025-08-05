package net.ccbluex.liquidbounce.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.Value;
import org.apache.logging.log4j.core.config.plugins.ResolverUtil;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\u00012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tJ)\u0010\n\u001a\u0011\u0012\r\u0012\u000b\u0012\u0002\b\u00030\f¢\u0006\u0002\b\r0\u000b2\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u000e\u001a\u00020\u0001J\u000e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0005J4\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00120\t0\u000b\"\b\b\u0000\u0010\u0012*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\tR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/ClassUtils;", "", "()V", "cachedClasses", "", "", "", "getObjectInstance", "clazz", "Ljava/lang/Class;", "getValues", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/internal/NoInfer;", "instance", "hasClass", "className", "resolvePackage", "T", "packagePath", "klass", "CrossSine"}
)
public final class ClassUtils {
   @NotNull
   public static final ClassUtils INSTANCE = new ClassUtils();
   @NotNull
   private static final Map cachedClasses = (Map)(new LinkedHashMap());

   private ClassUtils() {
   }

   public final boolean hasClass(@NotNull String className) {
      Intrinsics.checkNotNullParameter(className, "className");
      boolean var8;
      if (cachedClasses.containsKey(className)) {
         Object var10000 = cachedClasses.get(className);
         Intrinsics.checkNotNull(var10000);
         var8 = (Boolean)var10000;
      } else {
         boolean var2;
         try {
            Class.forName(className);
            Map var7 = cachedClasses;
            Boolean var3 = true;
            var7.put(className, var3);
            var2 = true;
         } catch (ClassNotFoundException var6) {
            Map var4 = cachedClasses;
            Boolean var5 = false;
            var4.put(className, var5);
            var2 = false;
         }

         var8 = var2;
      }

      return var8;
   }

   @NotNull
   public final Object getObjectInstance(@NotNull Class clazz) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      Field[] var2 = clazz.getDeclaredFields();
      Intrinsics.checkNotNullExpressionValue(var2, "clazz.declaredFields");
      Object[] $this$forEach$iv = (Object[])var2;
      int $i$f$forEach = 0;
      Object[] var4 = $this$forEach$iv;
      int var5 = 0;
      int var6 = $this$forEach$iv.length;

      while(var5 < var6) {
         Object element$iv = var4[var5];
         ++var5;
         Field it = (Field)element$iv;
         int var9 = 0;
         if (it.getName().equals("INSTANCE")) {
            Object var10 = it.get((Object)null);
            Intrinsics.checkNotNullExpressionValue(var10, "it.get(null)");
            return var10;
         }
      }

      throw new IllegalAccessException("This class not a kotlin object");
   }

   @NotNull
   public final List getValues(@NotNull Class clazz, @NotNull Object instance) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      Intrinsics.checkNotNullParameter(instance, "instance");
      Field[] var3 = clazz.getDeclaredFields();
      Intrinsics.checkNotNullExpressionValue(var3, "clazz.declaredFields");
      Object[] $this$map$iv = (Object[])var3;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.length));
      int $i$f$mapTo = 0;
      Object[] var8 = $this$map$iv;
      int var9 = 0;
      int var10 = $this$map$iv.length;

      while(var9 < var10) {
         Object item$iv$iv = var8[var9];
         ++var9;
         Field valueField = (Field)item$iv$iv;
         int var13 = 0;
         valueField.setAccessible(true);
         destination$iv$iv.add(valueField.get(instance));
      }

      Iterable var16 = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      destination$iv$iv = (Collection)(new ArrayList());
      $i$f$mapTo = 0;

      for(Object element$iv$iv : var16) {
         if (element$iv$iv instanceof Value) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      return (List)destination$iv$iv;
   }

   @NotNull
   public final List resolvePackage(@NotNull String packagePath, @NotNull Class klass) {
      Intrinsics.checkNotNullParameter(packagePath, "packagePath");
      Intrinsics.checkNotNullParameter(klass, "klass");
      ResolverUtil resolver = new ResolverUtil();
      resolver.setClassLoader(klass.getClassLoader());
      resolver.findInPackage((ResolverUtil.Test)(new ResolverUtil.ClassTest() {
         public boolean matches(@NotNull Class type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return true;
         }
      }), packagePath);
      List list = (List)(new ArrayList());

      for(Class resolved : resolver.getClasses()) {
         Method[] var8 = resolved.getDeclaredMethods();
         Intrinsics.checkNotNullExpressionValue(var8, "resolved.declaredMethods");
         Object[] var10 = (Object[])var8;
         int var11 = 0;
         int var12 = var10.length;

         Object var10000;
         while(true) {
            if (var11 >= var12) {
               var10000 = null;
               break;
            }

            Object var13 = var10[var11];
            ++var11;
            Method it = (Method)var13;
            int var15 = 0;
            if (Modifier.isNative(it.getModifiers())) {
               var10000 = var13;
               break;
            }
         }

         Method it = (Method)var10000;
         if (it != null) {
            int var16 = 0;
            String klass1 = it.getDeclaringClass().getTypeName() + '.' + it.getName();
            throw new UnsatisfiedLinkError(klass1 + "\n\tat " + klass1 + "(Native Method)");
         }

         if (klass.isAssignableFrom(resolved) && !resolved.isInterface() && !Modifier.isAbstract(resolved.getModifiers())) {
            if (resolved == null) {
               throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<out T of net.ccbluex.liquidbounce.utils.ClassUtils.resolvePackage>");
            }

            list.add(resolved);
         }
      }

      return list;
   }
}
