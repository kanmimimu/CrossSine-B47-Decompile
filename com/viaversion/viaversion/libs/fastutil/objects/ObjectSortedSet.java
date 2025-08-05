package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;
import java.util.SortedSet;

public interface ObjectSortedSet extends ObjectSet, SortedSet, ObjectBidirectionalIterable {
   ObjectBidirectionalIterator iterator(Object var1);

   ObjectBidirectionalIterator iterator();

   default ObjectSpliterator spliterator() {
      return ObjectSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf((Collection)this), 85, this.comparator());
   }

   ObjectSortedSet subSet(Object var1, Object var2);

   ObjectSortedSet headSet(Object var1);

   ObjectSortedSet tailSet(Object var1);
}
