package com.viaversion.viaversion.libs.snakeyaml.events;

import com.viaversion.viaversion.libs.snakeyaml.comments.CommentType;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public final class CommentEvent extends Event {
   private final CommentType type;
   private final String value;

   public CommentEvent(CommentType type, String value, Mark startMark, Mark endMark) {
      super(startMark, endMark);
      if (type == null) {
         throw new NullPointerException("Event Type must be provided.");
      } else {
         this.type = type;
         if (value == null) {
            throw new NullPointerException("Value must be provided.");
         } else {
            this.value = value;
         }
      }
   }

   public String getValue() {
      return this.value;
   }

   public CommentType getCommentType() {
      return this.type;
   }

   protected String getArguments() {
      return super.getArguments() + "type=" + this.type + ", value=" + this.value;
   }

   public Event.ID getEventId() {
      return Event.ID.Comment;
   }
}
