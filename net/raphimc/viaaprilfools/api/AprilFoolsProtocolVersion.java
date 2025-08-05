package net.raphimc.viaaprilfools.api;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocol.RedirectProtocolVersion;
import java.util.ArrayList;
import java.util.List;

public class AprilFoolsProtocolVersion {
   public static final List PROTOCOLS = new ArrayList();
   public static final List SNAPSHOTS_PROTOCOLS = new ArrayList();
   public static final List APRIL_FOOLS_PROTOCOLS = new ArrayList();
   public static final ProtocolVersion s3d_shareware;
   public static final ProtocolVersion s20w14infinite;
   public static final ProtocolVersion sCombatTest8c;

   private static ProtocolVersion registerSnapshot(int version, String name, ProtocolVersion origin) {
      ProtocolVersion protocolVersion = new RedirectProtocolVersion(version, name, origin);
      ProtocolVersion.register(protocolVersion);
      PROTOCOLS.add(protocolVersion);
      SNAPSHOTS_PROTOCOLS.add(protocolVersion);
      return protocolVersion;
   }

   private static ProtocolVersion registerAprilFools(int version, String name, ProtocolVersion origin) {
      ProtocolVersion protocolVersion = new RedirectProtocolVersion(version, name, origin);
      ProtocolVersion.register(protocolVersion);
      PROTOCOLS.add(protocolVersion);
      APRIL_FOOLS_PROTOCOLS.add(protocolVersion);
      return protocolVersion;
   }

   static {
      s3d_shareware = registerAprilFools(1, "3D Shareware", ProtocolVersion.v1_13_2);
      s20w14infinite = registerAprilFools(709, "20w14infinite", ProtocolVersion.v1_15_2);
      sCombatTest8c = registerSnapshot(803, "Combat Test 8c", ProtocolVersion.v1_16_1);
   }
}
