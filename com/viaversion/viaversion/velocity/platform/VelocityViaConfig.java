package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class VelocityViaConfig extends AbstractViaConfig {
   private int velocityPingInterval;
   private boolean velocityPingSave;
   private Map velocityServerProtocols;

   public VelocityViaConfig(File folder, Logger logger) {
      super(new File(folder, "config.yml"), logger);
   }

   protected void loadFields() {
      super.loadFields();
      this.velocityPingInterval = this.getInt("velocity-ping-interval", 60);
      this.velocityPingSave = this.getBoolean("velocity-ping-save", true);
      this.velocityServerProtocols = (Map)this.get("velocity-servers", new HashMap());
   }

   protected void handleConfig(Map config) {
      Object var4 = config.get("velocity-servers");
      Map<String, Object> servers;
      if (var4 instanceof Map) {
         Map velocityServers = (Map)var4;
         servers = velocityServers;
      } else {
         servers = new HashMap();
      }

      for(Map.Entry entry : new HashSet(servers.entrySet())) {
         if (!(entry.getValue() instanceof Integer)) {
            Object var6 = entry.getValue();
            if (var6 instanceof String) {
               String protocol = (String)var6;
               ProtocolVersion found = ProtocolVersion.getClosest(protocol);
               if (found != null) {
                  servers.put((String)entry.getKey(), found.getVersion());
               } else {
                  servers.remove(entry.getKey());
               }
            } else {
               servers.remove(entry.getKey());
            }
         }
      }

      if (!servers.containsKey("default")) {
         try {
            servers.put("default", VelocityViaInjector.getLowestSupportedProtocolVersion());
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      config.put("velocity-servers", servers);
   }

   public List getUnsupportedOptions() {
      return BUKKIT_ONLY_OPTIONS;
   }

   public int getVelocityPingInterval() {
      return this.velocityPingInterval;
   }

   public boolean isVelocityPingSave() {
      return this.velocityPingSave;
   }

   public Map getVelocityServerProtocols() {
      return this.velocityServerProtocols;
   }
}
