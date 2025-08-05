package com.viaversion.viaversion.configuration;

import com.viaversion.viaversion.api.configuration.Config;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConfigurationProviderImpl implements ConfigurationProvider {
   private final List configs = new ArrayList();

   public void register(Config config) {
      this.configs.add(config);
   }

   public Collection configs() {
      return Collections.unmodifiableCollection(this.configs);
   }

   public void reloadConfigs() {
      for(Config config : this.configs) {
         config.reload();
      }

   }
}
