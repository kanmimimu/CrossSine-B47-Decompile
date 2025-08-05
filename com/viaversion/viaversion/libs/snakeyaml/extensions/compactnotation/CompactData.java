package com.viaversion.viaversion.libs.snakeyaml.extensions.compactnotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompactData {
   private final String prefix;
   private final List arguments = new ArrayList();
   private final Map properties = new HashMap();

   public CompactData(String prefix) {
      this.prefix = prefix;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public Map getProperties() {
      return this.properties;
   }

   public List getArguments() {
      return this.arguments;
   }

   public String toString() {
      return "CompactData: " + this.prefix + " " + this.properties;
   }
}
