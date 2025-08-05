package com.viaversion.viaversion.dump;

import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Map;
import java.util.Objects;

public final class DumpTemplate {
   private final VersionInfo versionInfo;
   private final Map configuration;
   private final JsonObject platformDump;
   private final JsonObject injectionDump;
   private final JsonObject playerSample;

   public DumpTemplate(VersionInfo versionInfo, Map configuration, JsonObject platformDump, JsonObject injectionDump, JsonObject playerSample) {
      this.versionInfo = versionInfo;
      this.configuration = configuration;
      this.platformDump = platformDump;
      this.injectionDump = injectionDump;
      this.playerSample = playerSample;
   }

   public VersionInfo versionInfo() {
      return this.versionInfo;
   }

   public Map configuration() {
      return this.configuration;
   }

   public JsonObject platformDump() {
      return this.platformDump;
   }

   public JsonObject injectionDump() {
      return this.injectionDump;
   }

   public JsonObject playerSample() {
      return this.playerSample;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DumpTemplate)) {
         return false;
      } else {
         DumpTemplate var2 = (DumpTemplate)var1;
         return Objects.equals(this.versionInfo, var2.versionInfo) && Objects.equals(this.configuration, var2.configuration) && Objects.equals(this.platformDump, var2.platformDump) && Objects.equals(this.injectionDump, var2.injectionDump) && Objects.equals(this.playerSample, var2.playerSample);
      }
   }

   public int hashCode() {
      return ((((0 * 31 + Objects.hashCode(this.versionInfo)) * 31 + Objects.hashCode(this.configuration)) * 31 + Objects.hashCode(this.platformDump)) * 31 + Objects.hashCode(this.injectionDump)) * 31 + Objects.hashCode(this.playerSample);
   }

   public String toString() {
      return String.format("%s[versionInfo=%s, configuration=%s, platformDump=%s, injectionDump=%s, playerSample=%s]", this.getClass().getSimpleName(), Objects.toString(this.versionInfo), Objects.toString(this.configuration), Objects.toString(this.platformDump), Objects.toString(this.injectionDump), Objects.toString(this.playerSample));
   }
}
