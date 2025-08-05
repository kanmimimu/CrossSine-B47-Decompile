package com.viaversion.viaversion.classgenerator.generated;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.handlers.BukkitDecodeHandler;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

public interface HandlerSupplier {
   MessageToMessageEncoder newEncodeHandler(UserConnection var1);

   MessageToMessageDecoder newDecodeHandler(UserConnection var1);

   public static final class DefaultHandlerSupplier implements HandlerSupplier {
      public MessageToMessageEncoder newEncodeHandler(UserConnection connection) {
         return new BukkitEncodeHandler(connection);
      }

      public MessageToMessageDecoder newDecodeHandler(UserConnection connection) {
         return new BukkitDecodeHandler(connection);
      }
   }
}
