package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.util.IdAndData;
import java.util.Objects;

public final class ClassicBlockRemapper implements StorableObject {
   private final Int2ObjectFunction mapper;
   private final Object2IntFunction reverseMapper;

   public ClassicBlockRemapper(Int2ObjectFunction mapper, Object2IntFunction reverseMapper) {
      this.mapper = mapper;
      this.reverseMapper = reverseMapper;
   }

   public Int2ObjectFunction mapper() {
      return this.mapper;
   }

   public Object2IntFunction reverseMapper() {
      return this.reverseMapper;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ClassicBlockRemapper)) {
         return false;
      } else {
         ClassicBlockRemapper var2 = (ClassicBlockRemapper)var1;
         return Objects.equals(this.mapper, var2.mapper) && Objects.equals(this.reverseMapper, var2.reverseMapper);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.mapper)) * 31 + Objects.hashCode(this.reverseMapper);
   }

   public String toString() {
      return String.format("%s[mapper=%s, reverseMapper=%s]", this.getClass().getSimpleName(), Objects.toString(this.mapper), Objects.toString(this.reverseMapper));
   }
}
