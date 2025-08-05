package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.util.Key;
import java.util.LinkedHashMap;
import java.util.Map;

public final class WrappedBlockData {
   private final LinkedHashMap blockData = new LinkedHashMap();
   private final String minecraftKey;
   private final int savedBlockStateId;

   public static WrappedBlockData fromString(String s) {
      String[] array = s.split("\\[");
      String key = array[0];
      WrappedBlockData wrappedBlockdata = new WrappedBlockData(key, ConnectionData.getId(s));
      if (array.length > 1) {
         String blockData = array[1];
         blockData = blockData.replace("]", "");
         String[] data = blockData.split(",");

         for(String d : data) {
            String[] a = d.split("=");
            wrappedBlockdata.blockData.put(a[0], a[1]);
         }
      }

      return wrappedBlockdata;
   }

   private WrappedBlockData(String minecraftKey, int savedBlockStateId) {
      this.minecraftKey = Key.namespaced(minecraftKey);
      this.savedBlockStateId = savedBlockStateId;
   }

   public String toString() {
      String var5 = this.minecraftKey;
      StringBuilder sb = new StringBuilder(var5 + "[");

      for(Map.Entry entry : this.blockData.entrySet()) {
         sb.append((String)entry.getKey()).append('=').append((String)entry.getValue()).append(',');
      }

      String var7 = sb.substring(0, sb.length() - 1);
      return var7 + "]";
   }

   public String getMinecraftKey() {
      return this.minecraftKey;
   }

   public int getSavedBlockStateId() {
      return this.savedBlockStateId;
   }

   public int getBlockStateId() {
      return ConnectionData.getId(this.toString());
   }

   public WrappedBlockData set(String data, Object value) {
      if (!this.hasData(data)) {
         String var6 = this.minecraftKey;
         throw new UnsupportedOperationException("No blockdata found for " + data + " at " + var6);
      } else {
         this.blockData.put(data, value.toString());
         return this;
      }
   }

   public String getValue(String data) {
      return (String)this.blockData.get(data);
   }

   public boolean hasData(String key) {
      return this.blockData.containsKey(key);
   }
}
