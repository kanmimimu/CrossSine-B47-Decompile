package com.viaversion.viaversion.libs.snakeyaml.nodes;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import java.util.List;

public abstract class CollectionNode extends Node {
   private DumperOptions.FlowStyle flowStyle;

   public CollectionNode(Tag tag, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
      super(tag, startMark, endMark);
      this.setFlowStyle(flowStyle);
   }

   public abstract List getValue();

   public DumperOptions.FlowStyle getFlowStyle() {
      return this.flowStyle;
   }

   public void setFlowStyle(DumperOptions.FlowStyle flowStyle) {
      if (flowStyle == null) {
         throw new NullPointerException("Flow style must be provided.");
      } else {
         this.flowStyle = flowStyle;
      }
   }

   public void setEndMark(Mark endMark) {
      this.endMark = endMark;
   }
}
