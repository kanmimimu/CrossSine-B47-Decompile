package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public final class ItemCostType1_20_5 extends Type {
   static final StructuredData[] EMPTY_DATA_ARRAY = new StructuredData[0];
   final Type dataArrayType;

   public ItemCostType1_20_5(Type dataArrayType) {
      super(Item.class);
      this.dataArrayType = dataArrayType;
   }

   public Item read(ByteBuf buffer) {
      int id = Types.VAR_INT.readPrimitive(buffer);
      int amount = Types.VAR_INT.readPrimitive(buffer);
      StructuredData<?>[] dataArray = (StructuredData[])this.dataArrayType.read(buffer);
      return new StructuredItem(id, amount, new StructuredDataContainer(dataArray));
   }

   public void write(ByteBuf buffer, Item object) {
      Types.VAR_INT.writePrimitive(buffer, object.identifier());
      Types.VAR_INT.writePrimitive(buffer, object.amount());
      this.dataArrayType.write(buffer, (StructuredData[])object.dataContainer().data().values().toArray(EMPTY_DATA_ARRAY));
   }

   public static final class OptionalItemCostType extends OptionalType {
      public OptionalItemCostType(Type type) {
         super(type);
      }
   }
}
