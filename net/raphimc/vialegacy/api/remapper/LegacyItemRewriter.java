package net.raphimc.vialegacy.api.remapper;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import java.util.Objects;

public abstract class LegacyItemRewriter extends RewriterBase implements ItemRewriter {
   final ObjectList rewriteEntries;
   final ObjectList nonExistentItems;
   protected final String protocolName;
   final Type itemType;
   final Type mappedItemType;
   final Type itemArrayType;
   final Type mappedItemArrayType;

   public LegacyItemRewriter(Protocol protocol, String protocolName, Type itemType, Type itemArrayType) {
      this(protocol, protocolName, itemType, itemArrayType, itemType, itemArrayType);
   }

   public LegacyItemRewriter(Protocol protocol, String protocolName, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType) {
      super(protocol);
      this.rewriteEntries = new ObjectArrayList();
      this.nonExistentItems = new ObjectArrayList();
      this.protocolName = protocolName;
      this.itemType = itemType;
      this.itemArrayType = itemArrayType;
      this.mappedItemType = mappedItemType;
      this.mappedItemArrayType = mappedItemArrayType;
   }

   protected void addRemappedItem(int oldItemId, int newItemId, String newItemName) {
      this.addRemappedItem(oldItemId, newItemId, -1, newItemName);
   }

   protected void addRemappedItem(int oldItemId, int newItemId, int newItemMeta, String newItemName) {
      this.addRemappedItem(oldItemId, -1, newItemId, newItemMeta, newItemName);
   }

   protected void addRemappedItem(int oldItemId, int oldItemMeta, int newItemId, int newItemMeta, String newItemName) {
      this.rewriteEntries.add(new RewriteEntry(oldItemId, (short)oldItemMeta, newItemId, (short)newItemMeta, newItemName));
   }

   protected void addNonExistentItem(int itemId, int itemMeta) {
      this.nonExistentItems.add(new NonExistentEntry(itemId, (short)itemMeta));
   }

   protected void addNonExistentItem(int itemId, int startItemMeta, int endItemMeta) {
      for(int i = startItemMeta; i <= endItemMeta; ++i) {
         this.nonExistentItems.add(new NonExistentEntry(itemId, (short)i));
      }

   }

   protected void addNonExistentItems(int... itemIds) {
      for(int itemId : itemIds) {
         this.nonExistentItems.add(new NonExistentEntry(itemId, (short)-1));
      }

   }

   protected void addNonExistentItemRange(int startItemId, int endItemId) {
      for(int i = startItemId; i <= endItemId; ++i) {
         this.nonExistentItems.add(new NonExistentEntry(i, (short)-1));
      }

   }

   public void registerCreativeInventoryAction(ServerboundPacketType packetType) {
      this.protocol.registerServerbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.handler((wrapper) -> LegacyItemRewriter.this.handleServerboundItem(wrapper));
         }
      }));
   }

   public Item handleItemToClient(UserConnection user, Item item) {
      if (item == null) {
         return null;
      } else {
         for(RewriteEntry rewriteEntry : this.rewriteEntries) {
            if (rewriteEntry.rewrites(item)) {
               this.setRemappedNameRead(item, rewriteEntry.newItemName);
               if (rewriteEntry.newItemMeta != -1) {
                  item.setData(rewriteEntry.newItemMeta);
               }

               item.setIdentifier(rewriteEntry.newItemID);
            }
         }

         return item;
      }
   }

   public Item handleItemToServer(UserConnection user, Item item) {
      if (item == null) {
         return null;
      } else {
         for(NonExistentEntry nonExistentEntry : this.nonExistentItems) {
            if (nonExistentEntry.rewrites(item)) {
               item.setIdentifier(1);
               item.setData((short)0);
               return item;
            }
         }

         this.setRemappedTagWrite(item);
         return item;
      }
   }

   public Type itemType() {
      return this.itemType;
   }

   public Type itemArrayType() {
      return this.itemArrayType;
   }

   public Type mappedItemType() {
      return this.mappedItemType;
   }

   public Type mappedItemArrayType() {
      return this.mappedItemArrayType;
   }

   public String nbtTagName() {
      String var3 = this.protocol.getClass().getSimpleName();
      return "VL|" + var3;
   }

   void handleClientboundItem(PacketWrapper wrapper) {
      Item item = this.handleItemToClient(wrapper.user(), (Item)wrapper.read(this.itemType));
      wrapper.write(this.mappedItemType, item);
   }

   void handleServerboundItem(PacketWrapper wrapper) {
      Item item = this.handleItemToServer(wrapper.user(), (Item)wrapper.read(this.mappedItemType));
      wrapper.write(this.itemType, item);
   }

   void setRemappedNameRead(Item item, String name) {
      CompoundTag viaLegacyTag = new CompoundTag();
      viaLegacyTag.putInt("Id", item.identifier());
      viaLegacyTag.putShort("Meta", item.data());
      CompoundTag tag = item.tag();
      if (tag == null) {
         tag = new CompoundTag();
         item.setTag(tag);
         viaLegacyTag.putBoolean("RemoveTag", true);
      }

      tag.put(this.nbtTagName(), viaLegacyTag);
      CompoundTag display = tag.getCompoundTag("display");
      if (display == null) {
         display = new CompoundTag();
         tag.put("display", display);
         viaLegacyTag.putBoolean("RemoveDisplayTag", true);
      }

      if (display.contains("Name")) {
         ListTag<StringTag> lore = display.getListTag("Lore", StringTag.class);
         if (lore == null) {
            lore = new ListTag(StringTag.class);
            display.put("Lore", lore);
            viaLegacyTag.putBoolean("RemoveLore", true);
         }

         String var10003 = this.protocolName;
         int var9 = item.identifier();
         String var8 = var10003;
         lore.add(new StringTag("§r " + var8 + " Item ID: " + var9 + " (" + name + ")"));
         viaLegacyTag.putBoolean("RemoveLastLore", true);
      } else {
         String var12 = this.protocolName;
         display.putString("Name", "§r" + var12 + " " + name);
         viaLegacyTag.putBoolean("RemoveDisplayName", true);
      }

   }

   void setRemappedTagWrite(Item item) {
      CompoundTag tag = item.tag();
      if (tag != null) {
         CompoundTag viaLegacyTag = (CompoundTag)tag.removeUnchecked(this.nbtTagName());
         if (viaLegacyTag != null) {
            item.setIdentifier(viaLegacyTag.getNumberTag("Id").asInt());
            item.setData(viaLegacyTag.getNumberTag("Meta").asShort());
            if (viaLegacyTag.contains("RemoveLastLore")) {
               ListTag<StringTag> lore = tag.getCompoundTag("display").getListTag("Lore", StringTag.class);
               lore.remove(lore.size() - 1);
            }

            if (viaLegacyTag.contains("RemoveLore")) {
               tag.getCompoundTag("display").remove("Lore");
            }

            if (viaLegacyTag.contains("RemoveDisplayName")) {
               tag.getCompoundTag("display").remove("Name");
            }

            if (viaLegacyTag.contains("RemoveDisplayTag")) {
               tag.remove("display");
            }

            if (viaLegacyTag.contains("RemoveTag")) {
               item.setTag((CompoundTag)null);
            }

         }
      }
   }

   private static final class RewriteEntry {
      final int oldItemID;
      final short oldItemMeta;
      final int newItemID;
      final short newItemMeta;
      final String newItemName;

      RewriteEntry(int oldItemID, short oldItemMeta, int newItemID, short newItemMeta, String newItemName) {
         this.oldItemID = oldItemID;
         this.oldItemMeta = oldItemMeta;
         this.newItemID = newItemID;
         this.newItemMeta = newItemMeta;
         this.newItemName = newItemName;
      }

      public boolean rewrites(Item item) {
         return item.identifier() == this.oldItemID && (this.oldItemMeta == -1 || this.oldItemMeta == item.data());
      }

      public int oldItemID() {
         return this.oldItemID;
      }

      public short oldItemMeta() {
         return this.oldItemMeta;
      }

      public int newItemID() {
         return this.newItemID;
      }

      public short newItemMeta() {
         return this.newItemMeta;
      }

      public String newItemName() {
         return this.newItemName;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof RewriteEntry)) {
            return false;
         } else {
            RewriteEntry var2 = (RewriteEntry)var1;
            return this.oldItemID == var2.oldItemID && this.oldItemMeta == var2.oldItemMeta && this.newItemID == var2.newItemID && this.newItemMeta == var2.newItemMeta && Objects.equals(this.newItemName, var2.newItemName);
         }
      }

      public int hashCode() {
         return ((((0 * 31 + Integer.hashCode(this.oldItemID)) * 31 + Short.hashCode(this.oldItemMeta)) * 31 + Integer.hashCode(this.newItemID)) * 31 + Short.hashCode(this.newItemMeta)) * 31 + Objects.hashCode(this.newItemName);
      }

      public String toString() {
         return String.format("%s[oldItemID=%s, oldItemMeta=%s, newItemID=%s, newItemMeta=%s, newItemName=%s]", this.getClass().getSimpleName(), Integer.toString(this.oldItemID), Short.toString(this.oldItemMeta), Integer.toString(this.newItemID), Short.toString(this.newItemMeta), Objects.toString(this.newItemName));
      }
   }

   private static final class NonExistentEntry {
      final int itemId;
      final short itemMeta;

      NonExistentEntry(int itemId, short itemMeta) {
         this.itemId = itemId;
         this.itemMeta = itemMeta;
      }

      public boolean rewrites(Item item) {
         return item.identifier() == this.itemId && (this.itemMeta == -1 || this.itemMeta == item.data());
      }

      public int itemId() {
         return this.itemId;
      }

      public short itemMeta() {
         return this.itemMeta;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof NonExistentEntry)) {
            return false;
         } else {
            NonExistentEntry var2 = (NonExistentEntry)var1;
            return this.itemId == var2.itemId && this.itemMeta == var2.itemMeta;
         }
      }

      public int hashCode() {
         return (0 * 31 + Integer.hashCode(this.itemId)) * 31 + Short.hashCode(this.itemMeta);
      }

      public String toString() {
         return String.format("%s[itemId=%s, itemMeta=%s]", this.getClass().getSimpleName(), Integer.toString(this.itemId), Short.toString(this.itemMeta));
      }
   }
}
