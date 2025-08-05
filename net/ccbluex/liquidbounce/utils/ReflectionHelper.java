package net.ccbluex.liquidbounce.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ReflectionHelper {
   private static final Map HANDLE_CACHE = new HashMap();

   private ReflectionHelper() {
   }

   public static MethodHandle lookupStaticFieldHandle(Class targetClass, String fieldName) {
      try {
         Field f = targetClass.getDeclaredField(fieldName);
         if (!f.isAccessible()) {
            f.setAccessible(true);
         }

         return MethodHandles.lookup().unreflectGetter(f);
      } catch (IllegalAccessException | NoSuchFieldException var3) {
         return null;
      }
   }

   public static Object invokeOrNull(MethodHandle handle) {
      if (handle == null) {
         return null;
      } else {
         try {
            return handle.invoke((Void)null);
         } catch (Throwable var2) {
            return null;
         }
      }
   }

   public static Object getStaticField(Class targetClass, String fieldName) {
      String cacheName = targetClass.getTypeName() + "." + fieldName;
      MethodHandle handle;
      if (HANDLE_CACHE.containsKey(cacheName)) {
         handle = (MethodHandle)HANDLE_CACHE.get(cacheName);
      } else {
         handle = lookupStaticFieldHandle(targetClass, fieldName);
         HANDLE_CACHE.put(cacheName, handle);
      }

      return invokeOrNull(handle);
   }

   public static Method findMethod(Class clazz, Object instance, String[] methodNames, Class... methodTypes) {
      Exception failed = null;

      for(String methodName : methodNames) {
         try {
            Method m = clazz.getDeclaredMethod(methodName, methodTypes);
            m.setAccessible(true);
            return m;
         } catch (Exception var10) {
            failed = var10;
         }
      }

      throw new net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToFindMethodException(methodNames, failed);
   }
}
