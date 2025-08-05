package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ItemRewriter extends Rewriter {
   @Nullable Item handleItemToClient(UserConnection var1, @Nullable Item var2);

   @Nullable Item handleItemToServer(UserConnection var1, @Nullable Item var2);

   default @Nullable Type itemType() {
      return null;
   }

   default @Nullable Type itemArrayType() {
      return null;
   }

   default @Nullable Type mappedItemType() {
      return this.itemType();
   }

   default @Nullable Type mappedItemArrayType() {
      return this.itemArrayType();
   }

   default String nbtTagName() {
      String var3 = this.protocol().getClass().getSimpleName();
      return "VV|" + var3;
   }

   default String nbtTagName(String nbt) {
      String var4 = this.nbtTagName();
      return var4 + "|" + nbt;
   }
}
