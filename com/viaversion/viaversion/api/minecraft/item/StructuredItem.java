package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StructuredItem implements Item {
   private final StructuredDataContainer data;
   private int identifier;
   private int amount;

   public StructuredItem(int identifier, int amount) {
      this(identifier, amount, new StructuredDataContainer());
   }

   public StructuredItem(int identifier, int amount, StructuredDataContainer data) {
      this.identifier = identifier;
      this.amount = amount;
      this.data = data;
   }

   public static StructuredItem empty() {
      return new StructuredItem(0, 0);
   }

   public static Item[] emptyArray(int size) {
      Item[] items = new Item[size];

      for(int i = 0; i < items.length; ++i) {
         items[i] = empty();
      }

      return items;
   }

   public int identifier() {
      return this.identifier;
   }

   public void setIdentifier(int identifier) {
      this.identifier = identifier;
   }

   public int amount() {
      return this.amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public @Nullable CompoundTag tag() {
      return null;
   }

   public void setTag(@Nullable CompoundTag tag) {
      throw new UnsupportedOperationException();
   }

   public StructuredDataContainer dataContainer() {
      return this.data;
   }

   public StructuredItem copy() {
      return new StructuredItem(this.identifier, this.amount, this.data.copy());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         StructuredItem that = (StructuredItem)o;
         if (this.identifier != that.identifier) {
            return false;
         } else {
            return this.amount != that.amount ? false : this.data.equals(that.data);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.data.hashCode();
      result = 31 * result + this.identifier;
      result = 31 * result + this.amount;
      return result;
   }

   public String toString() {
      int var5 = this.amount;
      int var4 = this.identifier;
      StructuredDataContainer var3 = this.data;
      return "StructuredItem{data=" + var3 + ", identifier=" + var4 + ", amount=" + var5 + "}";
   }
}
