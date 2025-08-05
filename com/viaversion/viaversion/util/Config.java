package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.LoaderOptions;
import com.viaversion.viaversion.libs.snakeyaml.Yaml;
import com.viaversion.viaversion.libs.snakeyaml.constructor.SafeConstructor;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeId;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Tag;
import com.viaversion.viaversion.libs.snakeyaml.representer.Representer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class Config {
   static final ThreadLocal YAML = ThreadLocal.withInitial(() -> {
      DumperOptions options = new DumperOptions();
      options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
      options.setPrettyFlow(false);
      options.setIndent(2);
      return new Yaml(new CustomSafeConstructor(), new Representer(options), options);
   });
   final CommentStore commentStore = new CommentStore('.', 2);
   final File configFile;
   protected final Logger logger;
   Map config;

   protected Config(File configFile, Logger logger) {
      this.configFile = configFile;
      this.logger = logger;
   }

   public URL getDefaultConfigURL() {
      return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
   }

   public InputStream getDefaultConfigInputStream() {
      return this.getClass().getClassLoader().getResourceAsStream("assets/viaversion/config.yml");
   }

   public Map loadConfig(File location) {
      URL defaultConfigUrl = this.getDefaultConfigURL();
      return defaultConfigUrl != null ? this.loadConfig(location, defaultConfigUrl) : this.loadConfig(location, this::getDefaultConfigInputStream);
   }

   public synchronized Map loadConfig(File location, URL jarConfigFile) {
      Objects.requireNonNull(jarConfigFile);
      return this.loadConfig(location, jarConfigFile::openStream);
   }

   synchronized Map loadConfig(File location, InputStreamSupplier configSupplier) {
      List<String> unsupported = this.getUnsupportedOptions();

      try {
         InputStream inputStream = configSupplier.get();

         try {
            this.commentStore.storeComments(inputStream);

            for(String option : unsupported) {
               List<String> comments = this.commentStore.header(option);
               if (comments != null) {
                  comments.clear();
               }
            }
         } catch (Throwable var17) {
            if (inputStream != null) {
               try {
                  inputStream.close();
               } catch (Throwable var11) {
                  var17.addSuppressed(var11);
               }
            }

            throw var17;
         }

         if (inputStream != null) {
            inputStream.close();
         }
      } catch (IOException e) {
         throw new RuntimeException("Failed to load default config comments", e);
      }

      Map<String, Object> config = null;
      if (location.exists()) {
         try {
            FileInputStream input = new FileInputStream(location);

            try {
               config = (Map)((Yaml)YAML.get()).load((InputStream)input);
            } catch (Throwable var12) {
               try {
                  input.close();
               } catch (Throwable var10) {
                  var12.addSuppressed(var10);
               }

               throw var12;
            }

            input.close();
         } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
         } catch (Exception e) {
            this.logger.severe("Failed to load config, make sure your input is valid");
            throw new RuntimeException("Failed to load config (malformed input?)", e);
         }
      }

      if (config == null) {
         config = new HashMap();
      }

      Map<String, Object> mergedConfig;
      try {
         InputStream stream = configSupplier.get();

         try {
            mergedConfig = (Map)((Yaml)YAML.get()).load(stream);
         } catch (Throwable var15) {
            if (stream != null) {
               try {
                  stream.close();
               } catch (Throwable var9) {
                  var15.addSuppressed(var9);
               }
            }

            throw var15;
         }

         if (stream != null) {
            stream.close();
         }
      } catch (IOException e) {
         throw new RuntimeException("Failed to load default config", e);
      }

      for(String option : unsupported) {
         mergedConfig.remove(option);
      }

      for(Map.Entry entry : config.entrySet()) {
         mergedConfig.computeIfPresent((String)entry.getKey(), (key, value) -> entry.getValue());
      }

      this.handleConfig(mergedConfig);
      this.save(location, mergedConfig);
      return mergedConfig;
   }

   protected abstract void handleConfig(Map var1);

   public synchronized void save(File location, Map config) {
      try {
         this.commentStore.writeComments(((Yaml)YAML.get()).dump(config), location);
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public abstract List getUnsupportedOptions();

   public void set(String path, Object value) {
      this.config.put(path, value);
   }

   public void save() {
      if (this.configFile.getParentFile() != null) {
         this.configFile.getParentFile().mkdirs();
      }

      this.save(this.configFile, this.config);
   }

   public void save(File file) {
      this.save(file, this.config);
   }

   public void reload() {
      if (this.configFile.getParentFile() != null) {
         this.configFile.getParentFile().mkdirs();
      }

      this.config = new ConcurrentSkipListMap(this.loadConfig(this.configFile));
   }

   public Map getValues() {
      return this.config;
   }

   public @Nullable Object get(String key, Object def) {
      Object o = this.config.get(key);
      return o != null ? o : def;
   }

   public boolean getBoolean(String key, boolean def) {
      Object o = this.config.get(key);
      return o instanceof Boolean ? (Boolean)o : def;
   }

   public @Nullable String getString(String key, @Nullable String def) {
      Object o = this.config.get(key);
      return o instanceof String ? (String)o : def;
   }

   public int getInt(String key, int def) {
      Object o = this.config.get(key);
      return o instanceof Number ? ((Number)o).intValue() : def;
   }

   public double getDouble(String key, double def) {
      Object o = this.config.get(key);
      return o instanceof Number ? ((Number)o).doubleValue() : def;
   }

   public List getIntegerList(String key) {
      Object o = this.config.get(key);
      return (List)(o != null ? (List)o : new ArrayList());
   }

   public List getStringList(String key) {
      Object o = this.config.get(key);
      return (List)(o != null ? (List)o : new ArrayList());
   }

   public List getListSafe(String key, Class type, String invalidValueMessage) {
      Object o = this.config.get(key);
      if (o instanceof List) {
         List<?> list = (List)o;
         List<T> filteredValues = new ArrayList();

         for(Object o1 : list) {
            if (type.isInstance(o1)) {
               filteredValues.add(type.cast(o1));
            } else if (invalidValueMessage != null) {
               this.logger.warning(String.format(invalidValueMessage, o1));
            }
         }

         return filteredValues;
      } else {
         return new ArrayList();
      }
   }

   public @Nullable JsonElement getSerializedComponent(String key) {
      Object o = this.config.get(key);
      return o != null && !((String)o).isEmpty() ? ComponentUtil.legacyToJson((String)o) : null;
   }

   private static final class CustomSafeConstructor extends SafeConstructor {
      public CustomSafeConstructor() {
         super(new LoaderOptions());
         this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap());
         this.yamlConstructors.put(Tag.OMAP, new SafeConstructor.ConstructYamlOmap());
      }
   }
}
