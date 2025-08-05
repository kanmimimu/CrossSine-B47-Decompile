package com.viaversion.viaversion.velocity.storage;

import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class VelocityStorage implements StorableObject {
   private final Player player;
   private String currentServer;
   private List cachedBossbar;
   private static Method getServerBossBars;
   private static Class clientPlaySessionHandler;
   private static Method getMinecraftConnection;

   public VelocityStorage(Player player) {
      this.player = player;
      this.currentServer = "";
   }

   public List getBossbar() {
      if (this.cachedBossbar == null) {
         if (clientPlaySessionHandler == null) {
            return null;
         }

         if (getServerBossBars == null) {
            return null;
         }

         if (getMinecraftConnection == null) {
            return null;
         }

         try {
            Object connection = getMinecraftConnection.invoke(this.player);
            Object sessionHandler = ReflectionUtil.invoke(connection, "getSessionHandler");
            if (clientPlaySessionHandler.isInstance(sessionHandler)) {
               this.cachedBossbar = (List)getServerBossBars.invoke(sessionHandler);
            }
         } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to get bossbar list", e);
         }
      }

      return this.cachedBossbar;
   }

   public Player getPlayer() {
      return this.player;
   }

   public String getCurrentServer() {
      return this.currentServer;
   }

   public void setCurrentServer(String currentServer) {
      this.currentServer = currentServer;
   }

   public List getCachedBossbar() {
      return this.cachedBossbar;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         VelocityStorage that = (VelocityStorage)o;
         if (!Objects.equals(this.player, that.player)) {
            return false;
         } else {
            return !Objects.equals(this.currentServer, that.currentServer) ? false : Objects.equals(this.cachedBossbar, that.cachedBossbar);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.player != null ? this.player.hashCode() : 0;
      result = 31 * result + (this.currentServer != null ? this.currentServer.hashCode() : 0);
      result = 31 * result + (this.cachedBossbar != null ? this.cachedBossbar.hashCode() : 0);
      return result;
   }

   static {
      try {
         clientPlaySessionHandler = Class.forName("com.velocitypowered.proxy.connection.client.ClientPlaySessionHandler");
         getServerBossBars = clientPlaySessionHandler.getDeclaredMethod("getServerBossBars");
         getMinecraftConnection = Class.forName("com.velocitypowered.proxy.connection.client.ConnectedPlayer").getDeclaredMethod("getMinecraftConnection");
      } catch (ClassNotFoundException | NoSuchMethodException e) {
         Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to initialize Velocity bossbar support, bossbars will not work.", e);
      }

   }
}
