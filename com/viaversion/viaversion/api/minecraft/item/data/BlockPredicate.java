package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockPredicate {
   final @Nullable HolderSet holderSet;
   final StatePropertyMatcher @Nullable [] propertyMatchers;
   final @Nullable CompoundTag tag;
   public static final Type TYPE = new Type(BlockPredicate.class) {
      public BlockPredicate read(ByteBuf buffer) {
         HolderSet holders = (HolderSet)Types.OPTIONAL_HOLDER_SET.read(buffer);
         StatePropertyMatcher[] propertyMatchers = buffer.readBoolean() ? (StatePropertyMatcher[])StatePropertyMatcher.ARRAY_TYPE.read(buffer) : null;
         CompoundTag tag = (CompoundTag)Types.OPTIONAL_COMPOUND_TAG.read(buffer);
         return new BlockPredicate(holders, propertyMatchers, tag);
      }

      public void write(ByteBuf buffer, BlockPredicate value) {
         Types.OPTIONAL_HOLDER_SET.write(buffer, value.holderSet);
         buffer.writeBoolean(value.propertyMatchers != null);
         if (value.propertyMatchers != null) {
            StatePropertyMatcher.ARRAY_TYPE.write(buffer, value.propertyMatchers);
         }

         Types.OPTIONAL_COMPOUND_TAG.write(buffer, value.tag);
      }
   };
   public static final Type ARRAY_TYPE;

   public BlockPredicate(@Nullable HolderSet holderSet, StatePropertyMatcher @Nullable [] propertyMatchers, @Nullable CompoundTag tag) {
      this.holderSet = holderSet;
      this.propertyMatchers = propertyMatchers;
      this.tag = tag;
   }

   public BlockPredicate rewrite(Int2IntFunction blockIdRewriter) {
      if (this.holderSet != null && !this.holderSet.hasTagKey()) {
         HolderSet updatedHolders = this.holderSet.rewrite(blockIdRewriter);
         return new BlockPredicate(updatedHolders, this.propertyMatchers, this.tag);
      } else {
         return this;
      }
   }

   public @Nullable HolderSet holderSet() {
      return this.holderSet;
   }

   public StatePropertyMatcher @Nullable [] propertyMatchers() {
      return this.propertyMatchers;
   }

   public @Nullable CompoundTag tag() {
      return this.tag;
   }

   static {
      ARRAY_TYPE = new ArrayType(TYPE);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BlockPredicate)) {
         return false;
      } else {
         BlockPredicate var2 = (BlockPredicate)var1;
         return Objects.equals(this.holderSet, var2.holderSet) && Objects.equals(this.propertyMatchers, var2.propertyMatchers) && Objects.equals(this.tag, var2.tag);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.holderSet)) * 31 + Objects.hashCode(this.propertyMatchers)) * 31 + Objects.hashCode(this.tag);
   }

   public String toString() {
      return String.format("%s[holderSet=%s, propertyMatchers=%s, tag=%s]", this.getClass().getSimpleName(), Objects.toString(this.holderSet), Objects.toString(this.propertyMatchers), Objects.toString(this.tag));
   }
}
