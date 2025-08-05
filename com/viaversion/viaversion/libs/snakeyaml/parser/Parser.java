package com.viaversion.viaversion.libs.snakeyaml.parser;

import com.viaversion.viaversion.libs.snakeyaml.events.Event;

public interface Parser {
   boolean checkEvent(Event.ID var1);

   Event peekEvent();

   Event getEvent();
}
