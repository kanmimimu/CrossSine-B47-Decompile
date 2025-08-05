package com.viaversion.viaversion.libs.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class UnsafeAllocator {
   public static final UnsafeAllocator INSTANCE = create();

   public abstract Object newInstance(Class var1) throws Exception;

   private static void assertInstantiable(Class c) {
      String exceptionMessage = ConstructorConstructor.checkInstantiable(c);
      if (exceptionMessage != null) {
         throw new AssertionError("UnsafeAllocator is used for non-instantiable type: " + exceptionMessage);
      }
   }

   private static UnsafeAllocator create() {
      try {
         Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
         Field f = unsafeClass.getDeclaredField("theUnsafe");
         f.setAccessible(true);
         final Object unsafe = f.get((Object)null);
         final Method allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
         return new UnsafeAllocator() {
            public Object newInstance(Class c) throws Exception {
               UnsafeAllocator.assertInstantiable(c);
               return allocateInstance.invoke(unsafe, c);
            }
         };
      } catch (Exception var6) {
         try {
            Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
            getConstructorId.setAccessible(true);
            final int constructorId = (Integer)getConstructorId.invoke((Object)null, Object.class);
            final Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
            newInstance.setAccessible(true);
            return new UnsafeAllocator() {
               public Object newInstance(Class c) throws Exception {
                  UnsafeAllocator.assertInstantiable(c);
                  return newInstance.invoke((Object)null, c, constructorId);
               }
            };
         } catch (Exception var5) {
            try {
               final Method newInstance = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
               newInstance.setAccessible(true);
               return new UnsafeAllocator() {
                  public Object newInstance(Class c) throws Exception {
                     UnsafeAllocator.assertInstantiable(c);
                     return newInstance.invoke((Object)null, c, Object.class);
                  }
               };
            } catch (Exception var4) {
               return new UnsafeAllocator() {
                  public Object newInstance(Class c) {
                     throw new UnsupportedOperationException("Cannot allocate " + c + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
                  }
               };
            }
         }
      }
   }
}
