package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class Enchantments {
   final Int2IntMap enchantments;
   final boolean showInTooltip;
   public static final Type TYPE = new Type(Enchantments.class) {
      public Enchantments read(ByteBuf buffer) {
         Int2IntMap enchantments = new Int2IntOpenHashMap();
         int size = Types.VAR_INT.readPrimitive(buffer);

         for(int i = 0; i < size; ++i) {
            int id = Types.VAR_INT.readPrimitive(buffer);
            int level = Types.VAR_INT.readPrimitive(buffer);
            enchantments.put(id, level);
         }

         return new Enchantments(enchantments, buffer.readBoolean());
      }

      public void write(ByteBuf buffer, Enchantments value) {
         Types.VAR_INT.writePrimitive(buffer, value.enchantments.size());

         for(Int2IntMap.Entry entry : value.enchantments.int2IntEntrySet()) {
            Types.VAR_INT.writePrimitive(buffer, entry.getIntKey());
            Types.VAR_INT.writePrimitive(buffer, entry.getIntValue());
         }

         buffer.writeBoolean(value.showInTooltip());
      }
   };

   public Enchantments(boolean showInTooltip) {
      this(new Int2IntOpenHashMap(), showInTooltip);
   }

   public Enchantments(Int2IntMap enchantments, boolean showInTooltip) {
      this.enchantments = enchantments;
      this.showInTooltip = showInTooltip;
   }

   public int size() {
      return this.enchantments.size();
   }

   public void add(int id, int level) {
      this.enchantments.put(id, level);
   }

   public void remove(int id) {
      this.enchantments.remove(id);
   }

   public void clear() {
      this.enchantments.clear();
   }

   public int getLevel(int id) {
      return this.enchantments.getOrDefault(id, -1);
   }

   public Int2IntMap enchantments() {
      return this.enchantments;
   }

   public boolean showInTooltip() {
      return this.showInTooltip;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Enchantments)) {
         return false;
      } else {
         Enchantments var2 = (Enchantments)var1;
         return Objects.equals(this.enchantments, var2.enchantments) && this.showInTooltip == var2.showInTooltip;
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.enchantments)) * 31 + Boolean.hashCode(this.showInTooltip);
   }

   public String toString() {
      return String.format("%s[enchantments=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.enchantments), Boolean.toString(this.showInTooltip));
   }
}
