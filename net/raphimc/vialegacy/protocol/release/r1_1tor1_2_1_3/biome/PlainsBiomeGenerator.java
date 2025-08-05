package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome;

import java.util.Arrays;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;

public class PlainsBiomeGenerator implements IWorldChunkManager {
   private static final byte[] PLAINS_BIOME_DATA = new byte[256];

   public byte[] getBiomeDataAt(int chunkX, int chunkZ) {
      return PLAINS_BIOME_DATA;
   }

   static {
      Arrays.fill(PLAINS_BIOME_DATA, (byte)NewBiomeGenBase.plains.biomeID);
   }
}
