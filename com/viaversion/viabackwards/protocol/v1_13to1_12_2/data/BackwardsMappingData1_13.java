package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.StatisticMappings1_13;
import java.util.HashMap;
import java.util.Map;

public class BackwardsMappingData1_13 extends BackwardsMappingData {
   private final Int2ObjectMap statisticMappings = new Int2ObjectOpenHashMap();
   private final Map translateMappings = new HashMap();

   public BackwardsMappingData1_13() {
      super("1.13", "1.12", Protocol1_12_2To1_13.class);
   }

   public void loadExtras(CompoundTag data) {
      super.loadExtras(data);

      for(Map.Entry entry : StatisticMappings1_13.CUSTOM_STATS.entrySet()) {
         this.statisticMappings.put((Integer)entry.getValue(), (String)entry.getKey());
      }

      for(Map.Entry entry : Protocol1_12_2To1_13.MAPPINGS.getTranslateMapping().entrySet()) {
         this.translateMappings.put((String)entry.getValue(), (String)entry.getKey());
      }

   }

   public int getNewBlockStateId(int id) {
      if (id >= 5635 && id <= 5650) {
         if (id < 5639) {
            id += 4;
         } else if (id < 5643) {
            id -= 4;
         } else if (id < 5647) {
            id += 4;
         } else {
            id -= 4;
         }
      }

      int mappedId = super.getNewBlockStateId(id);
      int var10000;
      switch (mappedId) {
         case 1595:
         case 1596:
         case 1597:
            var10000 = 1584;
            break;
         case 1598:
         case 1599:
         case 1600:
         case 1601:
         case 1602:
         case 1603:
         case 1604:
         case 1605:
         case 1606:
         case 1607:
         case 1608:
         case 1609:
         case 1610:
         default:
            var10000 = mappedId;
            break;
         case 1611:
         case 1612:
         case 1613:
            var10000 = 1600;
      }

      return var10000;
   }

   protected int checkValidity(int id, int mappedId, String type) {
      return mappedId;
   }

   public Int2ObjectMap getStatisticMappings() {
      return this.statisticMappings;
   }

   public Map getTranslateMappings() {
      return this.translateMappings;
   }
}
