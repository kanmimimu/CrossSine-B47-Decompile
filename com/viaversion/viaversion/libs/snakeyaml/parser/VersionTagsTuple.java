package com.viaversion.viaversion.libs.snakeyaml.parser;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import java.util.Map;

class VersionTagsTuple {
   private final DumperOptions.Version version;
   private final Map tags;

   public VersionTagsTuple(DumperOptions.Version version, Map tags) {
      this.version = version;
      this.tags = tags;
   }

   public DumperOptions.Version getVersion() {
      return this.version;
   }

   public Map getTags() {
      return this.tags;
   }

   public String toString() {
      return String.format("VersionTagsTuple<%s, %s>", this.version, this.tags);
   }
}
