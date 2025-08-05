package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;
import java.util.Set;

public interface ReferenceSet extends ReferenceCollection, Set {
   ObjectIterator iterator();

   default ObjectSpliterator spliterator() {
      return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Collection)this), 65);
   }

   static ReferenceSet of() {
      return ReferenceSets.UNMODIFIABLE_EMPTY_SET;
   }

   static ReferenceSet of(Object e) {
      return ReferenceSets.singleton(e);
   }

   static ReferenceSet of(Object e0, Object e1) {
      ReferenceArraySet<K> innerSet = new ReferenceArraySet(2);
      innerSet.add(e0);
      if (!innerSet.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else {
         return ReferenceSets.unmodifiable(innerSet);
      }
   }

   static ReferenceSet of(Object e0, Object e1, Object e2) {
      ReferenceArraySet<K> innerSet = new ReferenceArraySet(3);
      innerSet.add(e0);
      if (!innerSet.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else if (!innerSet.add(e2)) {
         throw new IllegalArgumentException("Duplicate element: " + e2);
      } else {
         return ReferenceSets.unmodifiable(innerSet);
      }
   }

   @SafeVarargs
   static ReferenceSet of(Object... a) {
      switch (a.length) {
         case 0:
            return of();
         case 1:
            return of(a[0]);
         case 2:
            return of(a[0], a[1]);
         case 3:
            return of(a[0], a[1], a[2]);
         default:
            ReferenceSet<K> innerSet = (ReferenceSet<K>)(a.length <= 4 ? new ReferenceArraySet(a.length) : new ReferenceOpenHashSet(a.length));

            for(Object element : a) {
               if (!innerSet.add(element)) {
                  throw new IllegalArgumentException("Duplicate element: " + element);
               }
            }

            return ReferenceSets.unmodifiable(innerSet);
      }
   }
}
