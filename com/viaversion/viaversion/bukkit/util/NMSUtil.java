package com.viaversion.viaversion.bukkit.util;

import org.bukkit.Bukkit;

public final class NMSUtil {
   private static final String BASE = Bukkit.getServer().getClass().getPackage().getName();
   private static final String NMS;
   private static final boolean DEBUG_PROPERTY;

   private static boolean loadDebugProperty() {
      try {
         Class<?> serverClass = nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
         Object server = serverClass.getDeclaredMethod("getServer").invoke((Object)null);
         return (Boolean)serverClass.getMethod("isDebugging").invoke(server);
      } catch (ReflectiveOperationException var2) {
         return false;
      }
   }

   public static Class nms(String className) throws ClassNotFoundException {
      String var3 = NMS;
      return Class.forName(var3 + "." + className);
   }

   public static Class nms(String className, String fallbackFullClassName) throws ClassNotFoundException {
      try {
         String var4 = NMS;
         return Class.forName(var4 + "." + className);
      } catch (ClassNotFoundException var6) {
         return Class.forName(fallbackFullClassName);
      }
   }

   public static Class obc(String className) throws ClassNotFoundException {
      String var3 = BASE;
      return Class.forName(var3 + "." + className);
   }

   public static boolean isDebugPropertySet() {
      return DEBUG_PROPERTY;
   }

   static {
      NMS = BASE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
      DEBUG_PROPERTY = loadDebugProperty();
   }
}
