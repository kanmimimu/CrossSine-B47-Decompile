package com.viaversion.viaversion.protocols.v1_16_1to1_16_2.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.util.TagUtil;
import java.util.HashMap;
import java.util.Map;

public class MappingData1_16_2 extends MappingDataBase {
   private final Map dimensionDataMap = new HashMap();
   private CompoundTag dimensionRegistry;

   public MappingData1_16_2() {
      super("1.16", "1.16.2");
   }

   public void loadExtras(CompoundTag data) {
      this.dimensionRegistry = MappingDataLoader.INSTANCE.loadNBTFromFile("dimension-registry-1.16.2.nbt");

      for(CompoundTag dimension : TagUtil.getRegistryEntries(this.dimensionRegistry, "dimension_type")) {
         CompoundTag dimensionData = dimension.getCompoundTag("element").copy();
         this.dimensionDataMap.put(dimension.getStringTag("name").getValue(), dimensionData);
      }

   }

   public Map getDimensionDataMap() {
      return this.dimensionDataMap;
   }

   public CompoundTag getDimensionRegistry() {
      return this.dimensionRegistry.copy();
   }
}
