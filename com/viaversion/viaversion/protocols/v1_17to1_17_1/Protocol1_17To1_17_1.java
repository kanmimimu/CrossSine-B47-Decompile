package com.viaversion.viaversion.protocols.v1_17to1_17_1;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;

public final class Protocol1_17To1_17_1 extends AbstractProtocol {
   static final StringType PAGE_STRING_TYPE = new StringType(8192);
   static final StringType TITLE_STRING_TYPE = new StringType(128);

   public Protocol1_17To1_17_1() {
      super(ClientboundPackets1_17.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_17_1.REMOVE_ENTITIES, (wrapper) -> {
         int entityId = (Integer)wrapper.read(Types.VAR_INT);
         wrapper.write(Types.VAR_INT_ARRAY_PRIMITIVE, new int[]{entityId});
      });
      this.registerClientbound(ClientboundPackets1_17.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.create(Types.VAR_INT, 0);
         }
      });
      this.registerClientbound(ClientboundPackets1_17.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.create(Types.VAR_INT, 0);
            this.handler((wrapper) -> {
               wrapper.write(Types.ITEM1_13_2_ARRAY, (Item[])wrapper.read(Types.ITEM1_13_2_SHORT_ARRAY));
               wrapper.write(Types.ITEM1_13_2, (Object)null);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_17.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.read(Types.VAR_INT);
         }
      });
      this.registerServerbound(ServerboundPackets1_17.EDIT_BOOK, (wrapper) -> {
         CompoundTag tag = new CompoundTag();
         Item item = new DataItem(942, (byte)1, tag);
         wrapper.write(Types.ITEM1_13_2, item);
         int slot = (Integer)wrapper.read(Types.VAR_INT);
         int pages = (Integer)wrapper.read(Types.VAR_INT);
         ListTag<StringTag> pagesTag = new ListTag(StringTag.class);

         for(int i = 0; i < pages; ++i) {
            String page = (String)wrapper.read(PAGE_STRING_TYPE);
            pagesTag.add(new StringTag(page));
         }

         if (pagesTag.isEmpty()) {
            pagesTag.add(new StringTag(""));
         }

         tag.put("pages", pagesTag);
         if ((Boolean)wrapper.read(Types.BOOLEAN)) {
            String title = (String)wrapper.read(TITLE_STRING_TYPE);
            tag.put("title", new StringTag(title));
            tag.put("author", new StringTag(wrapper.user().getProtocolInfo().getUsername()));
            wrapper.write(Types.BOOLEAN, true);
         } else {
            wrapper.write(Types.BOOLEAN, false);
         }

         wrapper.write(Types.VAR_INT, slot);
      });
   }
}
