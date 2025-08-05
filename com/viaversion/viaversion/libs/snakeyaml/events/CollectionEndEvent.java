package com.viaversion.viaversion.libs.snakeyaml.events;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public abstract class CollectionEndEvent extends Event {
   public CollectionEndEvent(Mark startMark, Mark endMark) {
      super(startMark, endMark);
   }
}
