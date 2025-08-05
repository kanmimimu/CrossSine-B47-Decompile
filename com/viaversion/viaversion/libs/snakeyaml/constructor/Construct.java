package com.viaversion.viaversion.libs.snakeyaml.constructor;

import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;

public interface Construct {
   Object construct(Node var1);

   void construct2ndStep(Node var1, Object var2);
}
