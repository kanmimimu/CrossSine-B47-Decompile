package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Item {
   int identifier();

   void setIdentifier(int var1);

   int amount();

   void setAmount(int var1);

   default short data() {
      return 0;
   }

   default void setData(short data) {
      throw new UnsupportedOperationException();
   }

   @Nullable CompoundTag tag();

   void setTag(@Nullable CompoundTag var1);

   StructuredDataContainer dataContainer();

   Item copy();

   default boolean isEmpty() {
      return this.identifier() == 0 || this.amount() <= 0;
   }
}
