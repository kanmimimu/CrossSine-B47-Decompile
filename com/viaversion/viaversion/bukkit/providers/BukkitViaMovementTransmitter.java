package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.MovementTracker;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.channel.ChannelHandlerContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitViaMovementTransmitter extends MovementTransmitterProvider {
   private static boolean USE_NMS = true;
   private Object idlePacket;
   private Object idlePacket2;
   private Method getHandle;
   private Field connection;
   private Method handleFlying;

   public BukkitViaMovementTransmitter() {
      USE_NMS = Via.getConfig().isNMSPlayerTicking();

      Class<?> idlePacketClass;
      try {
         idlePacketClass = NMSUtil.nms("PacketPlayInFlying");
      } catch (ClassNotFoundException var7) {
         return;
      }

      try {
         this.idlePacket = idlePacketClass.newInstance();
         this.idlePacket2 = idlePacketClass.newInstance();
         Field flying = idlePacketClass.getDeclaredField("f");
         flying.setAccessible(true);
         flying.set(this.idlePacket2, true);
      } catch (InstantiationException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
         throw new RuntimeException("Couldn't make player idle packet, help!", e);
      }

      if (USE_NMS) {
         try {
            this.getHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle");
         } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find CraftPlayer", e);
         }

         try {
            this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
         } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException("Couldn't find Player Connection", e);
         }

         try {
            this.handleFlying = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", idlePacketClass);
         } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find CraftPlayer", e);
         }
      }

   }

   public Object getFlyingPacket() {
      if (this.idlePacket == null) {
         throw new NullPointerException("Could not locate flying packet");
      } else {
         return this.idlePacket;
      }
   }

   public Object getGroundPacket() {
      if (this.idlePacket == null) {
         throw new NullPointerException("Could not locate flying packet");
      } else {
         return this.idlePacket2;
      }
   }

   public void sendPlayer(UserConnection info) {
      if (USE_NMS) {
         Player player = Bukkit.getPlayer(info.getProtocolInfo().getUuid());
         if (player != null) {
            try {
               Object entityPlayer = this.getHandle.invoke(player);
               Object pc = this.connection.get(entityPlayer);
               if (pc != null) {
                  this.handleFlying.invoke(pc, ((MovementTracker)info.get(MovementTracker.class)).isGround() ? this.idlePacket2 : this.idlePacket);
                  ((MovementTracker)info.get(MovementTracker.class)).incrementIdlePacket();
               }
            } catch (InvocationTargetException | IllegalAccessException e) {
               Via.getPlatform().getLogger().log(Level.WARNING, "Failed to handle idle packet", e);
            }
         }
      } else {
         ChannelHandlerContext context = PipelineUtil.getContextBefore("decoder", info.getChannel().pipeline());
         if (context != null) {
            if (((MovementTracker)info.get(MovementTracker.class)).isGround()) {
               context.fireChannelRead(this.getGroundPacket());
            } else {
               context.fireChannelRead(this.getFlyingPacket());
            }

            ((MovementTracker)info.get(MovementTracker.class)).incrementIdlePacket();
         }
      }

   }
}
