package com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;

public class RecipeRewriter1_20_3 extends RecipeRewriter1_19_4 {
   public RecipeRewriter1_20_3(Protocol protocol) {
      super(protocol);
   }

   public void handleCraftingShaped(PacketWrapper wrapper) {
      wrapper.passthrough(Types.STRING);
      wrapper.passthrough(Types.VAR_INT);
      int ingredients = (Integer)wrapper.passthrough(Types.VAR_INT) * (Integer)wrapper.passthrough(Types.VAR_INT);

      for(int i = 0; i < ingredients; ++i) {
         this.handleIngredient(wrapper);
      }

      Item item = this.rewrite(wrapper.user(), (Item)wrapper.read(this.itemType()));
      wrapper.write(this.mappedItemType(), item);
      wrapper.passthrough(Types.BOOLEAN);
   }

   protected Type itemType() {
      return this.protocol.getItemRewriter().itemType();
   }

   protected Type itemArrayType() {
      return this.protocol.getItemRewriter().itemArrayType();
   }

   protected Type mappedItemType() {
      return this.protocol.getItemRewriter().mappedItemType();
   }

   protected Type mappedItemArrayType() {
      return this.protocol.getItemRewriter().mappedItemArrayType();
   }
}
