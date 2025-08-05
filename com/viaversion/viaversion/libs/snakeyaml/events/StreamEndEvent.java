package com.viaversion.viaversion.libs.snakeyaml.events;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public final class StreamEndEvent extends Event {
   public StreamEndEvent(Mark startMark, Mark endMark) {
      super(startMark, endMark);
   }

   public Event.ID getEventId() {
      return Event.ID.StreamEnd;
   }
}
