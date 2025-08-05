package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ToolRule {
   final HolderSet blocks;
   final @Nullable Float speed;
   final @Nullable Boolean correctForDrops;
   public static final Type TYPE = new Type(ToolRule.class) {
      public ToolRule read(ByteBuf buffer) {
         HolderSet blocks = (HolderSet)Types.HOLDER_SET.read(buffer);
         Float speed = (Float)Types.OPTIONAL_FLOAT.read(buffer);
         Boolean correctForDrops = (Boolean)Types.OPTIONAL_BOOLEAN.read(buffer);
         return new ToolRule(blocks, speed, correctForDrops);
      }

      public void write(ByteBuf buffer, ToolRule value) {
         Types.HOLDER_SET.write(buffer, value.blocks);
         Types.OPTIONAL_FLOAT.write(buffer, value.speed);
         Types.OPTIONAL_BOOLEAN.write(buffer, value.correctForDrops);
      }
   };
   public static final Type ARRAY_TYPE;

   public ToolRule(HolderSet blocks, @Nullable Float speed, @Nullable Boolean correctForDrops) {
      this.blocks = blocks;
      this.speed = speed;
      this.correctForDrops = correctForDrops;
   }

   public ToolRule rewrite(Int2IntFunction blockIdRewriter) {
      return this.blocks.hasIds() ? new ToolRule(this.blocks.rewrite(blockIdRewriter), this.speed, this.correctForDrops) : this;
   }

   public HolderSet blocks() {
      return this.blocks;
   }

   public @Nullable Float speed() {
      return this.speed;
   }

   public @Nullable Boolean correctForDrops() {
      return this.correctForDrops;
   }

   static {
      ARRAY_TYPE = new ArrayType(TYPE);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ToolRule)) {
         return false;
      } else {
         ToolRule var2 = (ToolRule)var1;
         return Objects.equals(this.blocks, var2.blocks) && Objects.equals(this.speed, var2.speed) && Objects.equals(this.correctForDrops, var2.correctForDrops);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.blocks)) * 31 + Objects.hashCode(this.speed)) * 31 + Objects.hashCode(this.correctForDrops);
   }

   public String toString() {
      return String.format("%s[blocks=%s, speed=%s, correctForDrops=%s]", this.getClass().getSimpleName(), Objects.toString(this.blocks), Objects.toString(this.speed), Objects.toString(this.correctForDrops));
   }
}
