package com.viaversion.viaversion.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtil {
   public static Object invokeStatic(Class clazz, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Method m = clazz.getDeclaredMethod(method);
      return m.invoke((Object)null);
   }

   public static Object invoke(Object o, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Method m = o.getClass().getDeclaredMethod(method);
      return m.invoke(o);
   }

   public static Object getStatic(Class clazz, String f, Class type) throws NoSuchFieldException, IllegalAccessException {
      Field field = clazz.getDeclaredField(f);
      field.setAccessible(true);
      return type.cast(field.get((Object)null));
   }

   public static void setStatic(Class clazz, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
      Field field = clazz.getDeclaredField(f);
      field.setAccessible(true);
      field.set((Object)null, value);
   }

   public static Object getSuper(Object o, String f, Class type) throws NoSuchFieldException, IllegalAccessException {
      Field field = o.getClass().getSuperclass().getDeclaredField(f);
      field.setAccessible(true);
      return type.cast(field.get(o));
   }

   public static Object get(Object instance, Class clazz, String f, Class type) throws NoSuchFieldException, IllegalAccessException {
      Field field = clazz.getDeclaredField(f);
      field.setAccessible(true);
      return type.cast(field.get(instance));
   }

   public static Object get(Object o, String f, Class type) throws NoSuchFieldException, IllegalAccessException {
      Field field = o.getClass().getDeclaredField(f);
      field.setAccessible(true);
      return type.cast(field.get(o));
   }

   public static Object getPublic(Object o, String f, Class type) throws NoSuchFieldException, IllegalAccessException {
      Field field = o.getClass().getField(f);
      field.setAccessible(true);
      return type.cast(field.get(o));
   }

   public static void set(Object o, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
      Field field = o.getClass().getDeclaredField(f);
      field.setAccessible(true);
      field.set(o, value);
   }
}
