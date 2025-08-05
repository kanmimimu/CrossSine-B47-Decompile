package com.viaversion.viaversion.libs.snakeyaml.events;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import java.util.Map;

public final class DocumentStartEvent extends Event {
   private final boolean explicit;
   private final DumperOptions.Version version;
   private final Map tags;

   public DocumentStartEvent(Mark startMark, Mark endMark, boolean explicit, DumperOptions.Version version, Map tags) {
      super(startMark, endMark);
      this.explicit = explicit;
      this.version = version;
      this.tags = tags;
   }

   public boolean getExplicit() {
      return this.explicit;
   }

   public DumperOptions.Version getVersion() {
      return this.version;
   }

   public Map getTags() {
      return this.tags;
   }

   public Event.ID getEventId() {
      return Event.ID.DocumentStart;
   }
}
