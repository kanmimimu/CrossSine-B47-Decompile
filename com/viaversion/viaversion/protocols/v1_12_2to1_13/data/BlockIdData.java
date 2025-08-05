package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.google.common.collect.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class BlockIdData {
   public static final String[] PREVIOUS = new String[0];
   public static final Map blockIdMapping = new HashMap();
   public static final Map fallbackReverseMapping = new HashMap();
   public static final Int2ObjectMap numberIdToString = new Int2ObjectOpenHashMap();

   public static void init() {
      InputStream stream = MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockIds1.12to1.13.json");

      try {
         InputStreamReader reader = new InputStreamReader(stream);

         try {
            Map<String, String[]> map = (Map)GsonUtil.getGson().fromJson((Reader)reader, (Type)(new TypeToken() {
            }).getType());
            blockIdMapping.putAll(map);

            for(Map.Entry entry : blockIdMapping.entrySet()) {
               for(String val : (String[])entry.getValue()) {
                  String[] previous = (String[])fallbackReverseMapping.get(val);
                  if (previous == null) {
                     previous = PREVIOUS;
                  }

                  fallbackReverseMapping.put(val, (String[])ObjectArrays.concat(previous, (String)entry.getKey()));
               }
            }
         } catch (Throwable var14) {
            try {
               reader.close();
            } catch (Throwable var13) {
               var14.addSuppressed(var13);
            }

            throw var14;
         }

         reader.close();
      } catch (IOException e) {
         Protocol1_12_2To1_13.LOGGER.log(Level.SEVERE, "Failed to load block id mappings", e);
      }

      InputStream blockS = MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockNumberToString1.12.json");

      try {
         InputStreamReader blockR = new InputStreamReader(blockS);

         try {
            Map<Integer, String> map = (Map)GsonUtil.getGson().fromJson((Reader)blockR, (Type)(new TypeToken() {
            }).getType());
            numberIdToString.putAll(map);
         } catch (Throwable var11) {
            try {
               blockR.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }

            throw var11;
         }

         blockR.close();
      } catch (IOException e) {
         Protocol1_12_2To1_13.LOGGER.log(Level.SEVERE, "Failed to load block number to string mappings", e);
      }

   }
}
