package com.viaversion.viabackwards.protocol.v1_14_3to1_14_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public class Protocol1_14_3To1_14_2 extends BackwardsProtocol {
   public Protocol1_14_3To1_14_2() {
      super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_14.MERCHANT_OFFERS, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int size = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.ITEM1_13_2);
            wrapper.passthrough(Types.ITEM1_13_2);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               wrapper.passthrough(Types.ITEM1_13_2);
            }

            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.FLOAT);
         }

         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.BOOLEAN);
         wrapper.read(Types.BOOLEAN);
      });
      RecipeRewriter<ClientboundPackets1_14> recipeHandler = new RecipeRewriter(this);
      this.registerClientbound(ClientboundPackets1_14.UPDATE_RECIPES, (wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);
         int deleted = 0;

         for(int i = 0; i < size; ++i) {
            String fullType = (String)wrapper.read(Types.STRING);
            String type = Key.stripMinecraftNamespace(fullType);
            String id = (String)wrapper.read(Types.STRING);
            if (type.equals("crafting_special_repairitem")) {
               ++deleted;
            } else {
               wrapper.write(Types.STRING, fullType);
               wrapper.write(Types.STRING, id);
               recipeHandler.handleRecipeType(wrapper, type);
            }
         }

         wrapper.set(Types.VAR_INT, 0, size - deleted);
      });
   }
}
