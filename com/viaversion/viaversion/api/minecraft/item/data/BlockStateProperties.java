package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Map;
import java.util.Objects;

public final class BlockStateProperties {
   final Map properties;
   public static final Type TYPE = new Type(BlockStateProperties.class) {
      public BlockStateProperties read(ByteBuf buffer) {
         int size = Types.VAR_INT.readPrimitive(buffer);
         Map<String, String> properties = new Object2ObjectOpenHashMap(size);

         for(int i = 0; i < size; ++i) {
            properties.put((String)Types.STRING.read(buffer), (String)Types.STRING.read(buffer));
         }

         return new BlockStateProperties(properties);
      }

      public void write(ByteBuf buffer, BlockStateProperties value) {
         Types.VAR_INT.writePrimitive(buffer, value.properties.size());

         for(Map.Entry entry : value.properties.entrySet()) {
            Types.STRING.write(buffer, (String)entry.getKey());
            Types.STRING.write(buffer, (String)entry.getValue());
         }

      }
   };

   public BlockStateProperties(Map properties) {
      this.properties = properties;
   }

   public Map properties() {
      return this.properties;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BlockStateProperties)) {
         return false;
      } else {
         BlockStateProperties var2 = (BlockStateProperties)var1;
         return Objects.equals(this.properties, var2.properties);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.properties);
   }

   public String toString() {
      return String.format("%s[properties=%s]", this.getClass().getSimpleName(), Objects.toString(this.properties));
   }
}
