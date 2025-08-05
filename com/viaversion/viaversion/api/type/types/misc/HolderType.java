package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public abstract class HolderType extends Type {
   protected HolderType() {
      super(Holder.class);
   }

   public Holder read(ByteBuf buffer) {
      int id = Types.VAR_INT.readPrimitive(buffer) - 1;
      return id == -1 ? Holder.of(this.readDirect(buffer)) : Holder.of(id);
   }

   public void write(ByteBuf buffer, Holder object) {
      if (object.hasId()) {
         Types.VAR_INT.writePrimitive(buffer, object.id() + 1);
      } else {
         Types.VAR_INT.writePrimitive(buffer, 0);
         this.writeDirect(buffer, object.value());
      }

   }

   public abstract Object readDirect(ByteBuf var1);

   public abstract void writeDirect(ByteBuf var1, Object var2);
}
