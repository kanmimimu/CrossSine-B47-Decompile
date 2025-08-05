package net.raphimc.viaaprilfools;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ViaAprilFoolsConfig extends Config implements net.raphimc.viaaprilfools.platform.ViaAprilFoolsConfig {
   public ViaAprilFoolsConfig(File configFile, Logger logger) {
      super(configFile, logger);
   }

   public void reload() {
      super.reload();
      this.loadFields();
   }

   private void loadFields() {
   }

   public URL getDefaultConfigURL() {
      return this.getClass().getClassLoader().getResource("assets/viaaprilfools/viaaprilfools.yml");
   }

   public InputStream getDefaultConfigInputStream() {
      return this.getClass().getClassLoader().getResourceAsStream("assets/viaaprilfools/viaaprilfools.yml");
   }

   protected void handleConfig(Map map) {
   }

   public List getUnsupportedOptions() {
      return Collections.emptyList();
   }
}
