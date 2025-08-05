package com.viaversion.viaversion.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.util.SynchronizedListWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyViaInjector implements ViaInjector {
   protected final List injectedFutures = new ArrayList();
   protected final List injectedLists = new ArrayList();

   public void inject() throws ReflectiveOperationException {
      Object connection = this.getServerConnection();
      if (connection == null) {
         throw new RuntimeException("Failed to find the core component 'ServerConnection'");
      } else {
         for(Field field : connection.getClass().getDeclaredFields()) {
            if (List.class.isAssignableFrom(field.getType()) && field.getGenericType().getTypeName().contains(ChannelFuture.class.getName())) {
               field.setAccessible(true);
               List<ChannelFuture> list = (List)field.get(connection);
               List<ChannelFuture> wrappedList = new SynchronizedListWrapper(list, (o) -> {
                  try {
                     this.injectChannelFuture((ChannelFuture)o);
                  } catch (ReflectiveOperationException e) {
                     throw new RuntimeException(e);
                  }
               });
               synchronized(list) {
                  for(ChannelFuture future : list) {
                     this.injectChannelFuture(future);
                  }

                  field.set(connection, wrappedList);
               }

               this.injectedLists.add(new Pair(field, connection));
            }
         }

      }
   }

   private void injectChannelFuture(ChannelFuture future) throws ReflectiveOperationException {
      List<String> names = future.channel().pipeline().names();
      ChannelHandler bootstrapAcceptor = null;

      for(String name : names) {
         ChannelHandler handler = future.channel().pipeline().get(name);

         try {
            ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class);
            bootstrapAcceptor = handler;
            break;
         } catch (ReflectiveOperationException var9) {
         }
      }

      if (bootstrapAcceptor == null) {
         bootstrapAcceptor = future.channel().pipeline().first();
      }

      try {
         ChannelInitializer<Channel> oldInitializer = (ChannelInitializer)ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
         ReflectionUtil.set(bootstrapAcceptor, "childHandler", this.createChannelInitializer(oldInitializer));
         this.injectedFutures.add(future);
      } catch (NoSuchFieldException var8) {
         this.blame(bootstrapAcceptor);
      }

   }

   public void uninject() throws ReflectiveOperationException {
      for(ChannelFuture future : this.injectedFutures) {
         ChannelPipeline pipeline = future.channel().pipeline();
         ChannelHandler bootstrapAcceptor = pipeline.first();
         if (bootstrapAcceptor == null) {
            Via.getPlatform().getLogger().info("Empty pipeline, nothing to uninject");
         } else {
            for(String name : pipeline.names()) {
               ChannelHandler handler = pipeline.get(name);
               if (handler == null) {
                  Via.getPlatform().getLogger().warning("Could not get handler " + name);
               } else {
                  try {
                     if (ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class) instanceof WrappedChannelInitializer) {
                        bootstrapAcceptor = handler;
                        break;
                     }
                  } catch (ReflectiveOperationException var15) {
                  }
               }
            }

            try {
               ChannelInitializer<Channel> initializer = (ChannelInitializer)ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
               if (initializer instanceof WrappedChannelInitializer) {
                  WrappedChannelInitializer wrappedChannelInitializer = (WrappedChannelInitializer)initializer;
                  ReflectionUtil.set(bootstrapAcceptor, "childHandler", wrappedChannelInitializer.original());
               }
            } catch (Exception e) {
               Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to remove injection handler, reload won't work with connections, please reboot!", e);
            }
         }
      }

      this.injectedFutures.clear();

      for(Pair pair : this.injectedLists) {
         try {
            Field field = (Field)pair.key();
            Object o = field.get(pair.value());
            if (o instanceof SynchronizedListWrapper) {
               List<ChannelFuture> originalList = ((SynchronizedListWrapper)o).originalList();
               synchronized(originalList) {
                  field.set(pair.value(), originalList);
               }
            }
         } catch (ReflectiveOperationException var13) {
            Via.getPlatform().getLogger().severe("Failed to remove injection, reload won't work with connections, please reboot!");
         }
      }

      this.injectedLists.clear();
   }

   public boolean lateProtocolVersionSetting() {
      return true;
   }

   public JsonObject getDump() {
      JsonObject data = new JsonObject();
      JsonArray injectedChannelInitializers = new JsonArray();
      data.add("injectedChannelInitializers", injectedChannelInitializers);

      for(ChannelFuture future : this.injectedFutures) {
         JsonObject futureInfo = new JsonObject();
         injectedChannelInitializers.add((JsonElement)futureInfo);
         futureInfo.addProperty("futureClass", future.getClass().getName());
         futureInfo.addProperty("channelClass", future.channel().getClass().getName());
         JsonArray pipeline = new JsonArray();
         futureInfo.add("pipeline", pipeline);

         for(String pipeName : future.channel().pipeline().names()) {
            JsonObject handlerInfo = new JsonObject();
            pipeline.add((JsonElement)handlerInfo);
            handlerInfo.addProperty("name", pipeName);
            ChannelHandler channelHandler = future.channel().pipeline().get(pipeName);
            if (channelHandler == null) {
               handlerInfo.addProperty("status", "INVALID");
            } else {
               handlerInfo.addProperty("class", channelHandler.getClass().getName());

               try {
                  Object child = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
                  handlerInfo.addProperty("childClass", child.getClass().getName());
                  if (child instanceof WrappedChannelInitializer) {
                     WrappedChannelInitializer wrappedChannelInitializer = (WrappedChannelInitializer)child;
                     handlerInfo.addProperty("oldInit", wrappedChannelInitializer.original().getClass().getName());
                  }
               } catch (ReflectiveOperationException var13) {
               }
            }
         }
      }

      JsonObject wrappedLists = new JsonObject();
      JsonObject currentLists = new JsonObject();

      try {
         for(Pair pair : this.injectedLists) {
            Field field = (Field)pair.key();
            Object list = field.get(pair.value());
            currentLists.addProperty(field.getName(), list.getClass().getName());
            if (list instanceof SynchronizedListWrapper) {
               SynchronizedListWrapper<?> wrapper = (SynchronizedListWrapper)list;
               wrappedLists.addProperty(field.getName(), wrapper.originalList().getClass().getName());
            }
         }

         data.add("wrappedLists", wrappedLists);
         data.add("currentLists", currentLists);
      } catch (ReflectiveOperationException var14) {
      }

      return data;
   }

   protected abstract @Nullable Object getServerConnection() throws ReflectiveOperationException;

   protected abstract WrappedChannelInitializer createChannelInitializer(ChannelInitializer var1);

   protected abstract void blame(ChannelHandler var1) throws ReflectiveOperationException;
}
