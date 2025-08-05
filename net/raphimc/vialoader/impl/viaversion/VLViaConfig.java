package net.raphimc.vialoader.impl.viaversion;

import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class VLViaConfig extends AbstractViaConfig {
   protected final List UNSUPPORTED = new ArrayList();

   public VLViaConfig(File configFile, Logger logger) {
      super(configFile, logger);
      this.UNSUPPORTED.addAll(BUKKIT_ONLY_OPTIONS);
      this.UNSUPPORTED.addAll(VELOCITY_ONLY_OPTIONS);
      this.UNSUPPORTED.add("check-for-updates");
   }

   protected void handleConfig(Map config) {
   }

   public List getUnsupportedOptions() {
      return Collections.unmodifiableList(this.UNSUPPORTED);
   }

   public boolean isCheckForUpdates() {
      return false;
   }
}
