package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;
import java.util.SortedSet;

public interface ReferenceSortedSet extends ReferenceSet, SortedSet, ObjectBidirectionalIterable {
   ObjectBidirectionalIterator iterator(Object var1);

   ObjectBidirectionalIterator iterator();

   default ObjectSpliterator spliterator() {
      return ObjectSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf((Collection)this), 85, this.comparator());
   }

   ReferenceSortedSet subSet(Object var1, Object var2);

   ReferenceSortedSet headSet(Object var1);

   ReferenceSortedSet tailSet(Object var1);
}
