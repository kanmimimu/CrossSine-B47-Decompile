package com.viaversion.viaversion.libs.snakeyaml.nodes;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import java.util.List;

public class MappingNode extends CollectionNode {
   private List value;
   private boolean merged;

   public MappingNode(Tag tag, boolean resolved, List value, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
      super(tag, startMark, endMark, flowStyle);
      this.merged = false;
      if (value == null) {
         throw new NullPointerException("value in a Node is required.");
      } else {
         this.value = value;
         this.resolved = resolved;
      }
   }

   public MappingNode(Tag tag, List value, DumperOptions.FlowStyle flowStyle) {
      this(tag, true, value, (Mark)null, (Mark)null, flowStyle);
   }

   public NodeId getNodeId() {
      return NodeId.mapping;
   }

   public List getValue() {
      return this.value;
   }

   public void setValue(List mergedValue) {
      this.value = mergedValue;
   }

   public void setOnlyKeyType(Class keyType) {
      for(NodeTuple nodes : this.value) {
         nodes.getKeyNode().setType(keyType);
      }

   }

   public void setTypes(Class keyType, Class valueType) {
      for(NodeTuple nodes : this.value) {
         nodes.getValueNode().setType(valueType);
         nodes.getKeyNode().setType(keyType);
      }

   }

   public String toString() {
      StringBuilder buf = new StringBuilder();

      for(NodeTuple node : this.getValue()) {
         buf.append("{ key=");
         buf.append(node.getKeyNode());
         buf.append("; value=");
         if (node.getValueNode() instanceof CollectionNode) {
            buf.append(System.identityHashCode(node.getValueNode()));
         } else {
            buf.append(node);
         }

         buf.append(" }");
      }

      String values = buf.toString();
      return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", values=" + values + ")>";
   }

   public void setMerged(boolean merged) {
      this.merged = merged;
   }

   public boolean isMerged() {
      return this.merged;
   }
}
