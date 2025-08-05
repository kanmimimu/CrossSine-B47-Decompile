package com.viaversion.viaversion.dump;

import java.util.List;
import java.util.Objects;

public final class PluginInfo {
   private final boolean enabled;
   private final String name;
   private final String version;
   private final String main;
   private final List authors;

   public PluginInfo(boolean enabled, String name, String version, String main, List authors) {
      this.enabled = enabled;
      this.name = name;
      this.version = version;
      this.main = main;
      this.authors = authors;
   }

   public boolean enabled() {
      return this.enabled;
   }

   public String name() {
      return this.name;
   }

   public String version() {
      return this.version;
   }

   public String main() {
      return this.main;
   }

   public List authors() {
      return this.authors;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PluginInfo)) {
         return false;
      } else {
         PluginInfo var2 = (PluginInfo)var1;
         return this.enabled == var2.enabled && Objects.equals(this.name, var2.name) && Objects.equals(this.version, var2.version) && Objects.equals(this.main, var2.main) && Objects.equals(this.authors, var2.authors);
      }
   }

   public int hashCode() {
      return ((((0 * 31 + Boolean.hashCode(this.enabled)) * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.version)) * 31 + Objects.hashCode(this.main)) * 31 + Objects.hashCode(this.authors);
   }

   public String toString() {
      return String.format("%s[enabled=%s, name=%s, version=%s, main=%s, authors=%s]", this.getClass().getSimpleName(), Boolean.toString(this.enabled), Objects.toString(this.name), Objects.toString(this.version), Objects.toString(this.main), Objects.toString(this.authors));
   }
}
