package net.raphimc.viaaprilfools.api.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.logging.Logger;
import net.raphimc.viaaprilfools.ViaAprilFools;
import org.checkerframework.checker.nullness.qual.Nullable;

public class AprilFoolsMappingData extends BackwardsMappingData {
   public AprilFoolsMappingData(String unmappedVersion, String mappedVersion, @Nullable Class vvProtocolClass) {
      super(unmappedVersion, mappedVersion, vvProtocolClass);
   }

   protected @Nullable CompoundTag readMappingsFile(String name) {
      return AprilFoolsMappingDataLoader.INSTANCE.loadNBTFromDir(name);
   }

   protected @Nullable CompoundTag readUnmappedIdentifiersFile(String name) {
      return AprilFoolsMappingDataLoader.INSTANCE.loadNBT(name, true);
   }

   protected Logger getLogger() {
      return ViaAprilFools.getPlatform().getLogger();
   }
}
