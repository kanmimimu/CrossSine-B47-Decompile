package com.viaversion.viaversion.api.data;

import com.google.common.annotations.Beta;
import com.viaversion.nbt.io.NBTIO;
import com.viaversion.nbt.io.TagReader;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataLoader {
   public static final MappingDataLoader INSTANCE = new MappingDataLoader(MappingDataLoader.class, "assets/viaversion/data/");
   public static final TagReader MAPPINGS_READER = NBTIO.reader(CompoundTag.class).named();
   static final Map GLOBAL_IDENTIFIER_INDEXES = new HashMap();
   static final byte DIRECT_ID = 0;
   static final byte SHIFTS_ID = 1;
   static final byte CHANGES_ID = 2;
   static final byte IDENTITY_ID = 3;
   final Map mappingsCache = new HashMap();
   final Class dataLoaderClass;
   final String dataPath;
   boolean cacheValid = true;

   public MappingDataLoader(Class dataLoaderClass, String dataPath) {
      this.dataLoaderClass = dataLoaderClass;
      this.dataPath = dataPath;
   }

   public static void loadGlobalIdentifiers() {
      CompoundTag globalIdentifiers = INSTANCE.loadNBT("identifier-table.nbt");

      for(Map.Entry entry : globalIdentifiers.entrySet()) {
         ListTag<StringTag> value = (ListTag)entry.getValue();
         String[] array = new String[value.size()];
         int i = 0;

         for(int size = value.size(); i < size; ++i) {
            array[i] = ((StringTag)value.get(i)).getValue();
         }

         GLOBAL_IDENTIFIER_INDEXES.put((String)entry.getKey(), array);
      }

   }

   public @Nullable String identifierFromGlobalId(String registry, int globalId) {
      String[] array = (String[])GLOBAL_IDENTIFIER_INDEXES.get(registry);
      if (array == null) {
         throw new IllegalArgumentException("Unknown global identifier key: " + registry);
      } else if (globalId >= 0 && globalId < array.length) {
         return array[globalId];
      } else {
         throw new IllegalArgumentException("Unknown global identifier index: " + globalId);
      }
   }

   public void clearCache() {
      this.mappingsCache.clear();
      this.cacheValid = false;
   }

   public @Nullable JsonObject loadFromDataDir(String name) {
      File file = new File(this.getDataFolder(), name);
      if (!file.exists()) {
         return this.loadData(name);
      } else {
         try {
            FileReader reader = new FileReader(file);

            JsonObject var4;
            try {
               var4 = (JsonObject)GsonUtil.getGson().fromJson((Reader)reader, (Class)JsonObject.class);
            } catch (Throwable var9) {
               try {
                  reader.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }

               throw var9;
            }

            reader.close();
            return var4;
         } catch (JsonSyntaxException e) {
            this.getLogger().warning(name + " is badly formatted!");
            throw new RuntimeException(e);
         } catch (JsonIOException | IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public @Nullable JsonObject loadData(String name) {
      InputStream stream = this.getResource(name);
      if (stream == null) {
         return null;
      } else {
         try {
            InputStreamReader reader = new InputStreamReader(stream);

            JsonObject var4;
            try {
               var4 = (JsonObject)GsonUtil.getGson().fromJson((Reader)reader, (Class)JsonObject.class);
            } catch (Throwable var7) {
               try {
                  reader.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }

               throw var7;
            }

            reader.close();
            return var4;
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public @Nullable CompoundTag loadNBT(String name, boolean cache) {
      if (!this.cacheValid) {
         return this.loadNBTFromFile(name);
      } else {
         CompoundTag data = (CompoundTag)this.mappingsCache.get(name);
         if (data != null) {
            return data;
         } else {
            data = this.loadNBTFromFile(name);
            if (cache && data != null) {
               this.mappingsCache.put(name, data);
            }

            return data;
         }
      }
   }

   public @Nullable CompoundTag loadNBT(String name) {
      return this.loadNBT(name, false);
   }

   public @Nullable CompoundTag loadNBTFromFile(String name) {
      InputStream resource = this.getResource(name);
      if (resource == null) {
         return null;
      } else {
         try {
            InputStream stream = new BufferedInputStream(resource);

            CompoundTag var4;
            try {
               var4 = (CompoundTag)MAPPINGS_READER.read(stream);
            } catch (Throwable var7) {
               try {
                  stream.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }

               throw var7;
            }

            stream.close();
            return var4;
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public @Nullable Mappings loadMappings(CompoundTag mappingsTag, String key) {
      return this.loadMappings(mappingsTag, key, (size) -> {
         int[] array = new int[size];
         Arrays.fill(array, -1);
         return array;
      }, (array, id, mappedId) -> array[id] = mappedId, IntArrayMappings::of);
   }

   @Beta
   public @Nullable Mappings loadMappings(CompoundTag mappingsTag, String key, MappingHolderSupplier holderSupplier, AddConsumer addConsumer, MappingsSupplier mappingsSupplier) {
      CompoundTag tag = mappingsTag.getCompoundTag(key);
      if (tag == null) {
         return null;
      } else {
         int mappedSize = tag.getIntTag("mappedSize").asInt();
         byte strategy = tag.getByteTag("id").asByte();
         if (strategy == 0) {
            IntArrayTag valuesTag = tag.getIntArrayTag("val");
            return IntArrayMappings.of(valuesTag.getValue(), mappedSize);
         } else {
            V mappings;
            if (strategy == 1) {
               int[] shiftsAt = tag.getIntArrayTag("at").getValue();
               int[] shiftsTo = tag.getIntArrayTag("to").getValue();
               int size = tag.getIntTag("size").asInt();
               mappings = (V)holderSupplier.get(size);
               if (shiftsAt[0] != 0) {
                  int to = shiftsAt[0];

                  for(int id = 0; id < to; ++id) {
                     addConsumer.addTo(mappings, id, id);
                  }
               }

               for(int i = 0; i < shiftsAt.length; ++i) {
                  boolean isLast = i == shiftsAt.length - 1;
                  int from = shiftsAt[i];
                  int to = isLast ? size : shiftsAt[i + 1];
                  int mappedId = shiftsTo[i];

                  for(int id = from; id < to; ++id) {
                     addConsumer.addTo(mappings, id, mappedId++);
                  }
               }
            } else {
               if (strategy != 2) {
                  if (strategy == 3) {
                     IntTag sizeTag = tag.getIntTag("size");
                     return new IdentityMappings(sizeTag.asInt(), mappedSize);
                  }

                  throw new IllegalArgumentException((new StringBuilder()).append("Unknown serialization strategy: ").append(strategy).toString());
               }

               int[] changesAt = tag.getIntArrayTag("at").getValue();
               int[] values = tag.getIntArrayTag("val").getValue();
               int size = tag.getIntTag("size").asInt();
               boolean fillBetween = tag.get("nofill") == null;
               mappings = (V)holderSupplier.get(size);
               int nextUnhandledId = 0;

               for(int i = 0; i < changesAt.length; ++i) {
                  int changedId = changesAt[i];
                  if (fillBetween) {
                     for(int id = nextUnhandledId; id < changedId; ++id) {
                        addConsumer.addTo(mappings, id, id);
                     }

                     nextUnhandledId = changedId + 1;
                  }

                  addConsumer.addTo(mappings, changedId, values[i]);
               }
            }

            return mappingsSupplier.create(mappings, mappedSize);
         }
      }
   }

   public @Nullable List identifiersFromGlobalIds(CompoundTag mappingsTag, String key) {
      Mappings mappings = this.loadMappings(mappingsTag, key);
      if (mappings == null) {
         return null;
      } else {
         List<String> identifiers = new ArrayList(mappings.size());

         for(int i = 0; i < mappings.size(); ++i) {
            identifiers.add(this.identifierFromGlobalId(key, mappings.getNewId(i)));
         }

         return identifiers;
      }
   }

   public Object2IntMap indexedObjectToMap(JsonObject object) {
      Object2IntMap<String> map = new Object2IntOpenHashMap(object.size());
      map.defaultReturnValue(-1);

      for(Map.Entry entry : object.entrySet()) {
         map.put(((JsonElement)entry.getValue()).getAsString(), Integer.parseInt((String)entry.getKey()));
      }

      return map;
   }

   public Object2IntMap arrayToMap(JsonArray array) {
      Object2IntMap<String> map = new Object2IntOpenHashMap(array.size());
      map.defaultReturnValue(-1);

      for(int i = 0; i < array.size(); ++i) {
         map.put(array.get(i).getAsString(), i);
      }

      return map;
   }

   public Logger getLogger() {
      return Via.getPlatform().getLogger();
   }

   public File getDataFolder() {
      return Via.getPlatform().getDataFolder();
   }

   public @Nullable InputStream getResource(String name) {
      ClassLoader var10000 = this.dataLoaderClass.getClassLoader();
      String var4 = this.dataPath;
      return var10000.getResourceAsStream(var4 + name);
   }

   @FunctionalInterface
   public interface AddConsumer {
      void addTo(Object var1, int var2, int var3);
   }

   @FunctionalInterface
   public interface MappingHolderSupplier {
      Object get(int var1);
   }

   @FunctionalInterface
   public interface MappingsSupplier {
      Mappings create(Object var1, int var2);
   }
}
