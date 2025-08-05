package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;

public class ArrayType extends Type {
   private final Type elementType;

   public ArrayType(Type type) {
      String var4 = type.getTypeName();
      super(var4 + " Array", getArrayClass(type.getOutputClass()));
      this.elementType = type;
   }

   public static Class getArrayClass(Class componentType) {
      return Array.newInstance(componentType, 0).getClass();
   }

   public Object[] read(ByteBuf buffer) {
      int amount = Types.VAR_INT.readPrimitive(buffer);
      T[] array = (T[])((Object[])Array.newInstance(this.elementType.getOutputClass(), amount));

      for(int i = 0; i < amount; ++i) {
         array[i] = this.elementType.read(buffer);
      }

      return array;
   }

   public void write(ByteBuf buffer, Object[] object) {
      Types.VAR_INT.writePrimitive(buffer, object.length);

      for(Object o : object) {
         this.elementType.write(buffer, o);
      }

   }
}
