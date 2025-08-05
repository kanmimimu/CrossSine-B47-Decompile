package com.viaversion.viaversion.libs.fastutil.objects;

public interface ObjectIterable extends Iterable {
   ObjectIterator iterator();

   default ObjectSpliterator spliterator() {
      return ObjectSpliterators.asSpliteratorUnknownSize(this.iterator(), 0);
   }
}
