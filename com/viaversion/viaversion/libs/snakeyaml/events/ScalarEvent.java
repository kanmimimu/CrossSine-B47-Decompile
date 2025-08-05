package com.viaversion.viaversion.libs.snakeyaml.events;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public final class ScalarEvent extends NodeEvent {
   private final String tag;
   private final DumperOptions.ScalarStyle style;
   private final String value;
   private final ImplicitTuple implicit;

   public ScalarEvent(String anchor, String tag, ImplicitTuple implicit, String value, Mark startMark, Mark endMark, DumperOptions.ScalarStyle style) {
      super(anchor, startMark, endMark);
      this.tag = tag;
      this.implicit = implicit;
      if (value == null) {
         throw new NullPointerException("Value must be provided.");
      } else {
         this.value = value;
         if (style == null) {
            throw new NullPointerException("Style must be provided.");
         } else {
            this.style = style;
         }
      }
   }

   public String getTag() {
      return this.tag;
   }

   public DumperOptions.ScalarStyle getScalarStyle() {
      return this.style;
   }

   public String getValue() {
      return this.value;
   }

   public ImplicitTuple getImplicit() {
      return this.implicit;
   }

   protected String getArguments() {
      return super.getArguments() + ", tag=" + this.tag + ", " + this.implicit + ", value=" + this.value;
   }

   public Event.ID getEventId() {
      return Event.ID.Scalar;
   }

   public boolean isPlain() {
      return this.style == DumperOptions.ScalarStyle.PLAIN;
   }
}
