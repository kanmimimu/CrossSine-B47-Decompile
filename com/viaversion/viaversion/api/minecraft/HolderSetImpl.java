package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.util.EitherImpl;

final class HolderSetImpl extends EitherImpl implements HolderSet {
   HolderSetImpl(String tagKey) {
      super(tagKey, (Object)null);
   }

   HolderSetImpl(int[] ids) {
      super((Object)null, ids);
   }

   public String tagKey() {
      return (String)this.left();
   }

   public boolean hasTagKey() {
      return this.isLeft();
   }

   public int[] ids() {
      return (int[])this.right();
   }

   public boolean hasIds() {
      return this.isRight();
   }

   public HolderSet rewrite(Int2IntFunction idRewriter) {
      if (this.hasTagKey()) {
         return this;
      } else {
         int[] ids = this.ids();
         int[] mappedIds = new int[ids.length];

         for(int i = 0; i < mappedIds.length; ++i) {
            mappedIds[i] = idRewriter.applyAsInt(ids[i]);
         }

         return new HolderSetImpl(mappedIds);
      }
   }
}
