package com.viaversion.viaversion.api.minecraft.entitydata;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;

public interface EntityDataType {
   Type type();

   int typeId();

   static EntityDataType create(int typeId, Type type) {
      return new EntityDataTypeImpl(typeId, type);
   }

   public static final class EntityDataTypeImpl implements EntityDataType {
      final int typeId;
      final Type type;

      EntityDataTypeImpl(int typeId, Type type) {
         Preconditions.checkNotNull(type);
         this.typeId = typeId;
         this.type = type;
      }

      public int typeId() {
         return this.typeId;
      }

      public Type type() {
         return this.type;
      }

      public String toString() {
         Type var4 = this.type;
         int var3 = this.typeId;
         return "EntityDataTypeImpl{typeId=" + var3 + ", type=" + var4 + "}";
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            EntityDataTypeImpl dataType = (EntityDataTypeImpl)o;
            return this.typeId != dataType.typeId ? false : this.type.equals(dataType.type);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.typeId;
         result = 31 * result + this.type.hashCode();
         return result;
      }
   }
}
