package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;

public abstract class PacketHandlers implements PacketHandler {
   final List packetHandlers = new ArrayList();

   protected PacketHandlers() {
      this.register();
   }

   static PacketHandler fromRemapper(List valueRemappers) {
      PacketHandlers handlers = new PacketHandlers() {
         public void register() {
         }
      };
      handlers.packetHandlers.addAll(valueRemappers);
      return handlers;
   }

   public void map(Type type) {
      this.handler((wrapper) -> wrapper.passthrough(type));
   }

   public void map(Type oldType, Type newType) {
      this.handler((wrapper) -> wrapper.passthroughAndMap(oldType, newType));
   }

   public void map(Type oldType, Type newType, final Function transformer) {
      this.map(oldType, new ValueTransformer(newType) {
         public Object transform(PacketWrapper wrapper, Object inputValue) {
            return transformer.apply(inputValue);
         }
      });
   }

   public void map(ValueTransformer transformer) {
      if (transformer.getInputType() == null) {
         throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
      } else {
         this.map(transformer.getInputType(), transformer);
      }
   }

   public void map(Type oldType, ValueTransformer transformer) {
      this.map((ValueReader)(new TypeRemapper(oldType)), (ValueWriter)transformer);
   }

   public void map(ValueReader inputReader, ValueWriter outputWriter) {
      this.handler((wrapper) -> outputWriter.write(wrapper, inputReader.read(wrapper)));
   }

   public void handler(PacketHandler handler) {
      this.packetHandlers.add(handler);
   }

   public void handlerSoftFail(PacketHandler handler) {
      this.packetHandlers.add((PacketHandler)(h) -> {
         try {
            handler.handle(h);
         } catch (Exception e) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               Via.getPlatform().getLogger().log(Level.WARNING, "Failed to handle packet", e);
            }

            h.cancel();
         }

      });
   }

   public void create(Type type, Object value) {
      this.handler((wrapper) -> wrapper.write(type, value));
   }

   public void read(Type type) {
      this.handler((wrapper) -> wrapper.read(type));
   }

   protected abstract void register();

   public final void handle(PacketWrapper wrapper) throws InformativeException {
      for(PacketHandler handler : this.packetHandlers) {
         handler.handle(wrapper);
      }

   }

   public int handlersSize() {
      return this.packetHandlers.size();
   }
}
