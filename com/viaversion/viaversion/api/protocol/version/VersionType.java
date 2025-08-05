package com.viaversion.viaversion.api.protocol.version;

public enum VersionType {
   CLASSIC,
   ALPHA_INITIAL,
   ALPHA_LATER,
   BETA_INITIAL,
   BETA_LATER,
   RELEASE_INITIAL,
   RELEASE,
   SPECIAL;

   // $FF: synthetic method
   private static VersionType[] $values() {
      return new VersionType[]{CLASSIC, ALPHA_INITIAL, ALPHA_LATER, BETA_INITIAL, BETA_LATER, RELEASE_INITIAL, RELEASE, SPECIAL};
   }
}
