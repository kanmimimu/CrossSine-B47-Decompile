package com.viaversion.viaversion.dump;

import java.util.Objects;
import java.util.Set;

public final class VersionInfo {
   private final String javaVersion;
   private final String operatingSystem;
   private final int serverProtocol;
   private final String serverVersion;
   private final Set enabledProtocols;
   private final String platformName;
   private final String platformVersion;
   private final String pluginVersion;
   private final String implementationVersion;
   private final Set subPlatforms;

   public VersionInfo(String javaVersion, String operatingSystem, int serverProtocol, String serverVersion, Set enabledProtocols, String platformName, String platformVersion, String pluginVersion, String implementationVersion, Set subPlatforms) {
      this.javaVersion = javaVersion;
      this.operatingSystem = operatingSystem;
      this.serverProtocol = serverProtocol;
      this.serverVersion = serverVersion;
      this.enabledProtocols = enabledProtocols;
      this.platformName = platformName;
      this.platformVersion = platformVersion;
      this.pluginVersion = pluginVersion;
      this.implementationVersion = implementationVersion;
      this.subPlatforms = subPlatforms;
   }

   public String javaVersion() {
      return this.javaVersion;
   }

   public String operatingSystem() {
      return this.operatingSystem;
   }

   public int serverProtocol() {
      return this.serverProtocol;
   }

   public String serverVersion() {
      return this.serverVersion;
   }

   public Set enabledProtocols() {
      return this.enabledProtocols;
   }

   public String platformName() {
      return this.platformName;
   }

   public String platformVersion() {
      return this.platformVersion;
   }

   public String pluginVersion() {
      return this.pluginVersion;
   }

   public String implementationVersion() {
      return this.implementationVersion;
   }

   public Set subPlatforms() {
      return this.subPlatforms;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof VersionInfo)) {
         return false;
      } else {
         VersionInfo var2 = (VersionInfo)var1;
         return Objects.equals(this.javaVersion, var2.javaVersion) && Objects.equals(this.operatingSystem, var2.operatingSystem) && this.serverProtocol == var2.serverProtocol && Objects.equals(this.serverVersion, var2.serverVersion) && Objects.equals(this.enabledProtocols, var2.enabledProtocols) && Objects.equals(this.platformName, var2.platformName) && Objects.equals(this.platformVersion, var2.platformVersion) && Objects.equals(this.pluginVersion, var2.pluginVersion) && Objects.equals(this.implementationVersion, var2.implementationVersion) && Objects.equals(this.subPlatforms, var2.subPlatforms);
      }
   }

   public int hashCode() {
      return (((((((((0 * 31 + Objects.hashCode(this.javaVersion)) * 31 + Objects.hashCode(this.operatingSystem)) * 31 + Integer.hashCode(this.serverProtocol)) * 31 + Objects.hashCode(this.serverVersion)) * 31 + Objects.hashCode(this.enabledProtocols)) * 31 + Objects.hashCode(this.platformName)) * 31 + Objects.hashCode(this.platformVersion)) * 31 + Objects.hashCode(this.pluginVersion)) * 31 + Objects.hashCode(this.implementationVersion)) * 31 + Objects.hashCode(this.subPlatforms);
   }

   public String toString() {
      return String.format("%s[javaVersion=%s, operatingSystem=%s, serverProtocol=%s, serverVersion=%s, enabledProtocols=%s, platformName=%s, platformVersion=%s, pluginVersion=%s, implementationVersion=%s, subPlatforms=%s]", this.getClass().getSimpleName(), Objects.toString(this.javaVersion), Objects.toString(this.operatingSystem), Integer.toString(this.serverProtocol), Objects.toString(this.serverVersion), Objects.toString(this.enabledProtocols), Objects.toString(this.platformName), Objects.toString(this.platformVersion), Objects.toString(this.pluginVersion), Objects.toString(this.implementationVersion), Objects.toString(this.subPlatforms));
   }
}
