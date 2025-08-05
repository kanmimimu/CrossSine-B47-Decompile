package com.viaversion.viaversion.api.data;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.util.Key;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class FullMappingsBase implements FullMappings {
   private static final String[] EMPTY_ARRAY = new String[0];
   private final Object2IntMap stringToId;
   private final Object2IntMap mappedStringToId;
   private final String[] idToString;
   private final String[] mappedIdToString;
   private final Mappings mappings;

   public FullMappingsBase(List unmappedIdentifiers, List mappedIdentifiers, Mappings mappings) {
      Preconditions.checkNotNull(mappings, "Mappings cannot be null");
      this.mappings = mappings;
      this.stringToId = toInverseMap(unmappedIdentifiers);
      this.mappedStringToId = toInverseMap(mappedIdentifiers);
      this.idToString = (String[])unmappedIdentifiers.toArray(EMPTY_ARRAY);
      this.mappedIdToString = (String[])mappedIdentifiers.toArray(EMPTY_ARRAY);
   }

   private FullMappingsBase(Object2IntMap stringToId, Object2IntMap mappedStringToId, String[] idToString, String[] mappedIdToString, Mappings mappings) {
      this.stringToId = stringToId;
      this.mappedStringToId = mappedStringToId;
      this.idToString = idToString;
      this.mappedIdToString = mappedIdToString;
      this.mappings = mappings;
   }

   private static Object2IntMap toInverseMap(List list) {
      Object2IntMap<String> map = new Object2IntOpenHashMap(list.size());
      map.defaultReturnValue(-1);

      for(int i = 0; i < list.size(); ++i) {
         map.put((String)list.get(i), i);
      }

      return map;
   }

   public int id(String identifier) {
      return this.stringToId.getInt(Key.stripMinecraftNamespace(identifier));
   }

   public int mappedId(String mappedIdentifier) {
      return this.mappedStringToId.getInt(Key.stripMinecraftNamespace(mappedIdentifier));
   }

   public String identifier(int id) {
      if (id >= 0 && id < this.idToString.length) {
         String identifier = this.idToString[id];
         return Key.namespaced(identifier);
      } else {
         return null;
      }
   }

   public String mappedIdentifier(int mappedId) {
      if (mappedId >= 0 && mappedId < this.mappedIdToString.length) {
         String identifier = this.mappedIdToString[mappedId];
         return Key.namespaced(identifier);
      } else {
         return null;
      }
   }

   public @Nullable String mappedIdentifier(String identifier) {
      int id = this.id(identifier);
      if (id == -1) {
         return null;
      } else {
         int mappedId = this.mappings.getNewId(id);
         return mappedId != -1 ? this.mappedIdentifier(mappedId) : null;
      }
   }

   public int getNewId(int id) {
      return this.mappings.getNewId(id);
   }

   public void setNewId(int id, int mappedId) {
      this.mappings.setNewId(id, mappedId);
   }

   public int size() {
      return this.mappings.size();
   }

   public int mappedSize() {
      return this.mappings.mappedSize();
   }

   public FullMappings inverse() {
      return new FullMappingsBase(this.mappedStringToId, this.stringToId, this.mappedIdToString, this.idToString, this.mappings.inverse());
   }
}
