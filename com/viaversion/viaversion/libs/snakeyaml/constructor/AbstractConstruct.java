package com.viaversion.viaversion.libs.snakeyaml.constructor;

import com.viaversion.viaversion.libs.snakeyaml.error.YAMLException;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;

public abstract class AbstractConstruct implements Construct {
   public void construct2ndStep(Node node, Object data) {
      if (node.isTwoStepsConstruction()) {
         throw new IllegalStateException("Not Implemented in " + this.getClass().getName());
      } else {
         throw new YAMLException("Unexpected recursive structure for Node: " + node);
      }
   }
}
