package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.Objects;

public final class TypeRemapper implements ValueReader, ValueWriter {
   private final Type type;

   public TypeRemapper(Type type) {
      this.type = type;
   }

   public Object read(PacketWrapper wrapper) throws InformativeException {
      return wrapper.read(this.type);
   }

   public void write(PacketWrapper output, Object inputValue) throws InformativeException {
      output.write(this.type, inputValue);
   }

   public Type type() {
      return this.type;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof TypeRemapper)) {
         return false;
      } else {
         TypeRemapper var2 = (TypeRemapper)var1;
         return Objects.equals(this.type, var2.type);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.type);
   }

   public String toString() {
      return String.format("%s[type=%s]", this.getClass().getSimpleName(), Objects.toString(this.type));
   }
}
