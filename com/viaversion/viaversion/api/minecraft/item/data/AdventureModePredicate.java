package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class AdventureModePredicate {
   final BlockPredicate[] predicates;
   final boolean showInTooltip;
   public static final Type TYPE = new Type(AdventureModePredicate.class) {
      public AdventureModePredicate read(ByteBuf buffer) {
         BlockPredicate[] predicates = (BlockPredicate[])BlockPredicate.ARRAY_TYPE.read(buffer);
         boolean showInTooltip = buffer.readBoolean();
         return new AdventureModePredicate(predicates, showInTooltip);
      }

      public void write(ByteBuf buffer, AdventureModePredicate value) {
         BlockPredicate.ARRAY_TYPE.write(buffer, value.predicates);
         buffer.writeBoolean(value.showInTooltip);
      }
   };

   public AdventureModePredicate(BlockPredicate[] predicates, boolean showInTooltip) {
      this.predicates = predicates;
      this.showInTooltip = showInTooltip;
   }

   public AdventureModePredicate rewrite(Int2IntFunction blockIdRewriter) {
      BlockPredicate[] predicates = new BlockPredicate[this.predicates.length];

      for(int i = 0; i < predicates.length; ++i) {
         predicates[i] = this.predicates[i].rewrite(blockIdRewriter);
      }

      return new AdventureModePredicate(predicates, this.showInTooltip);
   }

   public BlockPredicate[] predicates() {
      return this.predicates;
   }

   public boolean showInTooltip() {
      return this.showInTooltip;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof AdventureModePredicate)) {
         return false;
      } else {
         AdventureModePredicate var2 = (AdventureModePredicate)var1;
         return Objects.equals(this.predicates, var2.predicates) && this.showInTooltip == var2.showInTooltip;
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.predicates)) * 31 + Boolean.hashCode(this.showInTooltip);
   }

   public String toString() {
      return String.format("%s[predicates=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.predicates), Boolean.toString(this.showInTooltip));
   }
}
