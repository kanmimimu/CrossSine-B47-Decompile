package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;

public interface ObjectCollection extends Collection, ObjectIterable {
   ObjectIterator iterator();

   default ObjectSpliterator spliterator() {
      return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Collection)this), 64);
   }
}
