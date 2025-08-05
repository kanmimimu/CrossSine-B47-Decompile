package com.viaversion.viaversion.libs.snakeyaml.nodes;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import java.util.List;

public class SequenceNode extends CollectionNode {
   private final List value;

   public SequenceNode(Tag tag, boolean resolved, List value, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
      super(tag, startMark, endMark, flowStyle);
      if (value == null) {
         throw new NullPointerException("value in a Node is required.");
      } else {
         this.value = value;
         this.resolved = resolved;
      }
   }

   public SequenceNode(Tag tag, List value, DumperOptions.FlowStyle flowStyle) {
      this(tag, true, value, (Mark)null, (Mark)null, flowStyle);
   }

   public NodeId getNodeId() {
      return NodeId.sequence;
   }

   public List getValue() {
      return this.value;
   }

   public void setListType(Class listType) {
      for(Node node : this.value) {
         node.setType(listType);
      }

   }

   public String toString() {
      StringBuilder buf = new StringBuilder();

      for(Node node : this.getValue()) {
         if (node instanceof CollectionNode) {
            buf.append(System.identityHashCode(node));
         } else {
            buf.append(node.toString());
         }

         buf.append(",");
      }

      if (buf.length() > 0) {
         buf.deleteCharAt(buf.length() - 1);
      }

      return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", value=[" + buf + "])>";
   }
}
