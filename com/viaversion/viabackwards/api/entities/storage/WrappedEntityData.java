package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class WrappedEntityData {
   private final List entityDataList;

   public WrappedEntityData(List entityDataList) {
      this.entityDataList = entityDataList;
   }

   public boolean has(EntityData data) {
      return this.entityDataList.contains(data);
   }

   public void remove(EntityData data) {
      this.entityDataList.remove(data);
   }

   public void remove(int index) {
      this.entityDataList.removeIf((data) -> data.id() == index);
   }

   public void add(EntityData data) {
      this.entityDataList.add(data);
   }

   public @Nullable EntityData get(int index) {
      for(EntityData data : this.entityDataList) {
         if (index == data.id()) {
            return data;
         }
      }

      return null;
   }

   public List entityDataList() {
      return this.entityDataList;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof WrappedEntityData)) {
         return false;
      } else {
         WrappedEntityData var2 = (WrappedEntityData)var1;
         return Objects.equals(this.entityDataList, var2.entityDataList);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.entityDataList);
   }

   public String toString() {
      return String.format("%s[entityDataList=%s]", this.getClass().getSimpleName(), Objects.toString(this.entityDataList));
   }
}
