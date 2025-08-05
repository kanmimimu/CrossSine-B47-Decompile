package com.viaversion.viaversion.api.minecraft.entitydata;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EntityData {
   private int id;
   private EntityDataType dataType;
   private Object value;

   public EntityData(int id, EntityDataType dataType, @Nullable Object value) {
      this.id = id;
      this.dataType = dataType;
      this.value = this.checkValue(dataType, value);
   }

   public int id() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public EntityDataType dataType() {
      return this.dataType;
   }

   public void setDataType(EntityDataType dataType) {
      this.checkValue(dataType, this.value);
      this.dataType = dataType;
   }

   public @Nullable Object value() {
      return this.value;
   }

   public @Nullable Object getValue() {
      return this.value;
   }

   public void setValue(@Nullable Object value) {
      this.value = this.checkValue(this.dataType, value);
   }

   public void setTypeAndValue(EntityDataType dataType, @Nullable Object value) {
      this.value = this.checkValue(dataType, value);
      this.dataType = dataType;
   }

   private Object checkValue(EntityDataType dataType, @Nullable Object value) {
      Preconditions.checkNotNull(dataType);
      if (value != null && !dataType.type().getOutputClass().isAssignableFrom(value.getClass())) {
         String var7 = value.getClass().getSimpleName();
         throw new IllegalArgumentException("Entity data value and dataType are incompatible. Type=" + dataType + ", value=" + value + " (" + var7 + ")");
      } else {
         return value;
      }
   }

   /** @deprecated */
   @Deprecated
   public void setDataTypeUnsafe(EntityDataType type) {
      this.dataType = type;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EntityData entityData = (EntityData)o;
         if (this.id != entityData.id) {
            return false;
         } else {
            return this.dataType != entityData.dataType ? false : Objects.equals(this.value, entityData.value);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.id;
      result = 31 * result + this.dataType.hashCode();
      result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
      return result;
   }

   public String toString() {
      Object var5 = this.value;
      EntityDataType var4 = this.dataType;
      int var3 = this.id;
      return "EntityData{id=" + var3 + ", dataType=" + var4 + ", value=" + var5 + "}";
   }
}
