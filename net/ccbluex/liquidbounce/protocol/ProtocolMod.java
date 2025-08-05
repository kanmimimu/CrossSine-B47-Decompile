package net.ccbluex.liquidbounce.protocol;

import net.ccbluex.liquidbounce.protocol.api.VFPlatform;
import net.minecraft.realms.RealmsSharedConstants;
import net.minecraftforge.fml.common.Mod;

@Mod(
   modid = "FDPClient",
   version = "Release",
   acceptedMinecraftVersions = "[1.8.9]"
)
public class ProtocolMod implements VFPlatform {
   public static final ProtocolMod PLATFORM = new ProtocolMod();

   public int getGameVersion() {
      return RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
   }
}
