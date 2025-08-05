package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import java.util.SortedSet;

public interface ServerProtocolVersion {
   ProtocolVersion lowestSupportedProtocolVersion();

   ProtocolVersion highestSupportedProtocolVersion();

   SortedSet supportedProtocolVersions();

   default boolean isKnown() {
      return this.lowestSupportedProtocolVersion().isKnown() && this.highestSupportedProtocolVersion().isKnown();
   }

   /** @deprecated */
   @Deprecated
   default int lowestSupportedVersion() {
      return this.lowestSupportedProtocolVersion().getVersion();
   }

   /** @deprecated */
   @Deprecated
   default int highestSupportedVersion() {
      return this.highestSupportedProtocolVersion().getVersion();
   }

   /** @deprecated */
   @Deprecated
   default IntSortedSet supportedVersions() {
      return (IntSortedSet)this.supportedProtocolVersions().stream().mapToInt(ProtocolVersion::getVersion).collect(IntLinkedOpenHashSet::new, IntCollection::add, IntCollection::addAll);
   }
}
