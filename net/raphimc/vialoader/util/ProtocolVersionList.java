package net.raphimc.vialoader.util;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProtocolVersionList {
   public static List getProtocolsNewToOld() {
      List<ProtocolVersion> protocolVersions = new ArrayList(ProtocolVersion.getProtocols());
      Collections.reverse(protocolVersions);
      return Collections.unmodifiableList(protocolVersions);
   }
}
