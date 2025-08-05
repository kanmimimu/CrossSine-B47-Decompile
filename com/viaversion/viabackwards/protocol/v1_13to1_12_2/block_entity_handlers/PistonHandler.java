package com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import java.util.Map;
import java.util.StringJoiner;

public class PistonHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
   private final Object2IntMap pistonIds = new Object2IntOpenHashMap();

   public PistonHandler() {
      this.pistonIds.defaultReturnValue(-1);
      if (Via.getConfig().isServersideBlockConnections()) {
         Map<String, Integer> keyToId = ConnectionData.getKeyToId();

         for(Map.Entry entry : keyToId.entrySet()) {
            if (((String)entry.getKey()).contains("piston")) {
               this.addEntries((String)entry.getKey(), (Integer)entry.getValue());
            }
         }
      } else {
         ListTag<StringTag> blockStates = MappingDataLoader.INSTANCE.loadNBT("blockstates-1.13.nbt").getListTag("blockstates", StringTag.class);

         for(int id = 0; id < blockStates.size(); ++id) {
            StringTag state = (StringTag)blockStates.get(id);
            String key = state.getValue();
            if (key.contains("piston")) {
               this.addEntries(key, id);
            }
         }
      }

   }

   private void addEntries(String data, int id) {
      id = Protocol1_13To1_12_2.MAPPINGS.getNewBlockStateId(id);
      this.pistonIds.put(data, id);
      String substring = data.substring(10);
      if (substring.startsWith("piston") || substring.startsWith("sticky_piston")) {
         String[] split = data.substring(0, data.length() - 1).split("\\[");
         String[] properties = split[1].split(",");
         String var10000 = split[0];
         String var10001 = properties[1];
         String var9 = properties[0];
         String var8 = var10001;
         String var7 = var10000;
         data = var7 + "[" + var8 + "," + var9 + "]";
         this.pistonIds.put(data, id);
      }
   }

   public CompoundTag transform(int blockId, CompoundTag tag) {
      CompoundTag blockState = tag.getCompoundTag("blockState");
      if (blockState == null) {
         return tag;
      } else {
         String dataFromTag = this.getDataFromTag(blockState);
         if (dataFromTag == null) {
            return tag;
         } else {
            int id = this.pistonIds.getInt(dataFromTag);
            if (id == -1) {
               return tag;
            } else {
               tag.putInt("blockId", id >> 4);
               tag.putInt("blockData", id & 15);
               return tag;
            }
         }
      }
   }

   private String getDataFromTag(CompoundTag tag) {
      StringTag name = tag.getStringTag("Name");
      if (name == null) {
         return null;
      } else {
         CompoundTag properties = tag.getCompoundTag("Properties");
         if (properties == null) {
            return name.getValue();
         } else {
            String var8 = name.getValue();
            StringJoiner joiner = new StringJoiner(",", var8 + "[", "]");

            for(Map.Entry entry : properties) {
               if (entry.getValue() instanceof StringTag) {
                  String var10001 = (String)entry.getKey();
                  String var11 = ((StringTag)entry.getValue()).getValue();
                  String var10 = var10001;
                  joiner.add(var10 + "=" + var11);
               }
            }

            return joiner.toString();
         }
      }
   }
}
