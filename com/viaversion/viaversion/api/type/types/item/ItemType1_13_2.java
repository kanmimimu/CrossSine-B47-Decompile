package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemType1_13_2 extends Type {
   public ItemType1_13_2() {
      super(Item.class);
   }

   public @Nullable Item read(ByteBuf buffer) {
      boolean present = buffer.readBoolean();
      if (!present) {
         return null;
      } else {
         Item item = new DataItem();
         item.setIdentifier(Types.VAR_INT.readPrimitive(buffer));
         item.setAmount(buffer.readByte());
         item.setTag((CompoundTag)Types.NAMED_COMPOUND_TAG.read(buffer));
         return item;
      }
   }

   public void write(ByteBuf buffer, @Nullable Item object) {
      if (object == null) {
         buffer.writeBoolean(false);
      } else {
         buffer.writeBoolean(true);
         Types.VAR_INT.writePrimitive(buffer, object.identifier());
         buffer.writeByte(object.amount());
         Types.NAMED_COMPOUND_TAG.write(buffer, object.tag());
      }

   }
}
