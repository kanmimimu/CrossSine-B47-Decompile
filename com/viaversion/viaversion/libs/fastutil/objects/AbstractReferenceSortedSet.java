package com.viaversion.viaversion.libs.fastutil.objects;

public abstract class AbstractReferenceSortedSet extends AbstractReferenceSet implements ReferenceSortedSet {
   protected AbstractReferenceSortedSet() {
   }

   public abstract ObjectBidirectionalIterator iterator();
}
