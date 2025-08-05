package com.viaversion.viaversion.api.type.types.item;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Map;

public class ItemType1_20_5 extends Type {
   final StructuredDataType dataType;

   public ItemType1_20_5(StructuredDataType dataType) {
      super(Item.class);
      this.dataType = dataType;
   }

   public Item read(ByteBuf buffer) {
      int amount = Types.VAR_INT.readPrimitive(buffer);
      if (amount <= 0) {
         return StructuredItem.empty();
      } else {
         int id = Types.VAR_INT.readPrimitive(buffer);
         Map<StructuredDataKey<?>, StructuredData<?>> data = this.readData(buffer);
         return new StructuredItem(id, amount, new StructuredDataContainer(data));
      }
   }

   Map readData(ByteBuf buffer) {
      int valuesSize = Types.VAR_INT.readPrimitive(buffer);
      int markersSize = Types.VAR_INT.readPrimitive(buffer);
      if (valuesSize == 0 && markersSize == 0) {
         return new Reference2ObjectOpenHashMap();
      } else {
         Map<StructuredDataKey<?>, StructuredData<?>> map = new Reference2ObjectOpenHashMap();

         for(int i = 0; i < valuesSize; ++i) {
            StructuredData<?> value = this.dataType.read(buffer);
            StructuredDataKey<?> key = this.dataType.key(value.id());
            Preconditions.checkNotNull(key, "No data component serializer found for %s", new Object[]{value});
            map.put(key, value);
         }

         for(int i = 0; i < markersSize; ++i) {
            int id = Types.VAR_INT.readPrimitive(buffer);
            StructuredDataKey<?> key = this.dataType.key(id);
            Preconditions.checkNotNull(key, "No data component serializer found for empty id %s", new Object[]{id});
            map.put(key, StructuredData.empty(key, id));
         }

         return map;
      }
   }

   public void write(ByteBuf buffer, Item object) {
      if (object.isEmpty()) {
         Types.VAR_INT.writePrimitive(buffer, 0);
      } else {
         Types.VAR_INT.writePrimitive(buffer, object.amount());
         Types.VAR_INT.writePrimitive(buffer, object.identifier());
         Map<StructuredDataKey<?>, StructuredData<?>> data = object.dataContainer().data();
         int valuesSize = 0;
         int markersSize = 0;

         for(StructuredData value : data.values()) {
            if (value.isPresent()) {
               ++valuesSize;
            } else {
               ++markersSize;
            }
         }

         Types.VAR_INT.writePrimitive(buffer, valuesSize);
         Types.VAR_INT.writePrimitive(buffer, markersSize);

         for(StructuredData value : data.values()) {
            if (value.isPresent()) {
               this.dataType.write(buffer, value);
            }
         }

         for(StructuredData value : data.values()) {
            if (value.isEmpty()) {
               Types.VAR_INT.writePrimitive(buffer, value.id());
            }
         }

      }
   }

   public final class OptionalItemType extends OptionalType {
      public OptionalItemType() {
         super(ItemType1_20_5.this);
      }
   }
}
