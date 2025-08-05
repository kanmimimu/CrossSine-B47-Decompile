package com.viaversion.viaversion.protocols.v1_11_1to1_12.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.provider.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemPacketRewriter1_12 extends ItemRewriter {
   public ItemPacketRewriter1_12(Protocol1_11_1To1_12 protocol) {
      super(protocol, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY);
   }

   public void registerPackets() {
      this.registerSetSlot(ClientboundPackets1_9_3.CONTAINER_SET_SLOT);
      this.registerSetContent(ClientboundPackets1_9_3.CONTAINER_SET_CONTENT);
      this.registerSetEquippedItem(ClientboundPackets1_9_3.SET_EQUIPPED_ITEM);
      this.registerCustomPayloadTradeList(ClientboundPackets1_9_3.CUSTOM_PAYLOAD);
      ((Protocol1_11_1To1_12)this.protocol).registerServerbound(ServerboundPackets1_12.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.VAR_INT);
            this.map(Types.ITEM1_8);
            this.handler((wrapper) -> {
               Item item = (Item)wrapper.get(Types.ITEM1_8, 0);
               if (!Via.getConfig().is1_12QuickMoveActionFix()) {
                  ItemPacketRewriter1_12.this.handleItemToServer(wrapper.user(), item);
               } else {
                  byte button = (Byte)wrapper.get(Types.BYTE, 0);
                  int mode = (Integer)wrapper.get(Types.VAR_INT, 0);
                  if (mode == 1 && button == 0 && item == null) {
                     short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
                     short slotId = (Short)wrapper.get(Types.SHORT, 0);
                     short actionId = (Short)wrapper.get(Types.SHORT, 1);
                     InventoryQuickMoveProvider provider = (InventoryQuickMoveProvider)Via.getManager().getProviders().get(InventoryQuickMoveProvider.class);
                     boolean succeed = provider.registerQuickMoveAction(windowId, slotId, actionId, wrapper.user());
                     if (succeed) {
                        wrapper.cancel();
                     }
                  } else {
                     ItemPacketRewriter1_12.this.handleItemToServer(wrapper.user(), item);
                  }

               }
            });
         }
      });
      this.registerSetCreativeModeSlot(ServerboundPackets1_12.SET_CREATIVE_MODE_SLOT);
   }

   public Item handleItemToServer(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         if (item.identifier() == 355) {
            item.setData((short)0);
         }

         boolean newItem = item.identifier() >= 235 && item.identifier() <= 252;
         newItem |= item.identifier() == 453;
         if (newItem) {
            item.setIdentifier(1);
            item.setData((short)0);
         }

         return item;
      }
   }

   public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         if (item.identifier() == 355) {
            item.setData((short)14);
         }

         return item;
      }
   }
}
