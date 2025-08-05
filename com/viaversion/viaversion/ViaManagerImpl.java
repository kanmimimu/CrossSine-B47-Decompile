package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.api.scheduler.Scheduler;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.configuration.ConfigurationProviderImpl;
import com.viaversion.viaversion.connection.ConnectionManagerImpl;
import com.viaversion.viaversion.debug.DebugHandlerImpl;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import com.viaversion.viaversion.protocol.ServerProtocolVersionRange;
import com.viaversion.viaversion.protocol.ServerProtocolVersionSingleton;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.task.TabCompleteTask;
import com.viaversion.viaversion.protocols.v1_8to1_9.task.IdlePacketTask;
import com.viaversion.viaversion.scheduler.TaskScheduler;
import com.viaversion.viaversion.update.UpdateUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViaManagerImpl implements ViaManager {
   final ProtocolManagerImpl protocolManager = new ProtocolManagerImpl();
   final ConnectionManager connectionManager = new ConnectionManagerImpl();
   final ConfigurationProvider configurationProvider = new ConfigurationProviderImpl();
   final DebugHandler debugHandler = new DebugHandlerImpl();
   final ViaProviders providers = new ViaProviders();
   final Scheduler scheduler = new TaskScheduler();
   final ViaPlatform platform;
   final ViaInjector injector;
   final ViaCommandHandler commandHandler;
   final ViaPlatformLoader loader;
   final Set subPlatforms = new HashSet();
   List enableListeners = new ArrayList();
   List postEnableListeners = new ArrayList();
   PlatformTask mappingLoadingTask;
   boolean initialized;

   public ViaManagerImpl(ViaPlatform platform, ViaInjector injector, ViaCommandHandler commandHandler, ViaPlatformLoader loader) {
      this.platform = platform;
      this.injector = injector;
      this.commandHandler = commandHandler;
      this.loader = loader;
   }

   public static ViaManagerBuilder builder() {
      return new ViaManagerBuilder();
   }

   public void init() {
      this.configurationProvider.register(this.platform.getConf());
      if (System.getProperty("ViaVersion") != null) {
         this.platform.onReload();
      }

      if (!this.injector.lateProtocolVersionSetting()) {
         this.loadServerProtocol();
      }

      MappingDataLoader.loadGlobalIdentifiers();
      this.protocolManager.registerProtocols();

      try {
         this.injector.inject();
      } catch (Exception e) {
         this.platform.getLogger().log(Level.SEVERE, "ViaVersion failed to inject:", e);
         return;
      }

      System.setProperty("ViaVersion", this.platform.getPluginVersion());

      for(Runnable listener : this.enableListeners) {
         listener.run();
      }

      this.enableListeners = null;
      this.initialized = true;
   }

   public void onServerLoaded() {
      if (this.platform.getConf().isCheckForUpdates()) {
         UpdateUtil.sendUpdateMessage();
      }

      if (!this.protocolManager.getServerProtocolVersion().isKnown()) {
         this.loadServerProtocol();
      }

      ServerProtocolVersion protocolVersion = this.protocolManager.getServerProtocolVersion();
      if (protocolVersion.isKnown()) {
         if (this.platform.isProxy()) {
            Logger var10000 = this.platform.getLogger();
            ProtocolVersion var6 = protocolVersion.lowestSupportedProtocolVersion();
            var10000.info("ViaVersion detected lowest supported version by the proxy: " + var6);
            var10000 = this.platform.getLogger();
            ProtocolVersion var8 = protocolVersion.highestSupportedProtocolVersion();
            var10000.info("Highest supported version by the proxy: " + var8);
            if (this.debugHandler.enabled()) {
               var10000 = this.platform.getLogger();
               String var10 = Arrays.toString(protocolVersion.supportedProtocolVersions().toArray(new ProtocolVersion[0]));
               var10000.info("Supported version range: " + var10);
            }
         } else {
            Logger var15 = this.platform.getLogger();
            ProtocolVersion var12 = protocolVersion.highestSupportedProtocolVersion();
            var15.info("ViaVersion detected server version: " + var12);
         }

         if (!this.protocolManager.isWorkingPipe()) {
            this.platform.getLogger().warning("ViaVersion does not have any compatible versions for this server version!");
            this.platform.getLogger().warning("Please remember that ViaVersion only adds support for versions newer than the server version.");
            this.platform.getLogger().warning("If you need support for older versions you may need to use one or more ViaVersion addons too.");
            this.platform.getLogger().warning("In that case please read the ViaVersion resource page carefully or use https://viaversion.com/setup");
            this.platform.getLogger().warning("and if you're still unsure, feel free to join our Discord-Server for further assistance.");
         } else if (protocolVersion.highestSupportedProtocolVersion().olderThan(ProtocolVersion.v1_13)) {
            this.platform.getLogger().warning("This version of Minecraft is extremely outdated and support for it has reached its end of life. You will still be able to run Via on this Minecraft version, but we will prioritize issues with legacy Minecraft versions less. Please consider updating to give your players a better experience and to avoid issues that have long been fixed.");
         }
      }

      this.checkJavaVersion();
      this.unsupportedSoftwareWarning();
      this.loader.load();
      this.mappingLoadingTask = Via.getPlatform().runRepeatingAsync(() -> {
         if (this.protocolManager.checkForMappingCompletion() && this.mappingLoadingTask != null) {
            this.mappingLoadingTask.cancel();
            this.mappingLoadingTask = null;
         }

      }, 10L);
      ProtocolVersion serverProtocolVersion = this.protocolManager.getServerProtocolVersion().lowestSupportedProtocolVersion();
      if (serverProtocolVersion.olderThan(ProtocolVersion.v1_9) && Via.getConfig().isSimulatePlayerTick()) {
         Via.getPlatform().runRepeatingSync(new IdlePacketTask(), 1L);
      }

      if (serverProtocolVersion.olderThan(ProtocolVersion.v1_13) && Via.getConfig().get1_13TabCompleteDelay() > 0) {
         Via.getPlatform().runRepeatingSync(new TabCompleteTask(), 1L);
      }

      this.protocolManager.refreshVersions();

      for(Runnable listener : this.postEnableListeners) {
         listener.run();
      }

      this.postEnableListeners = null;
   }

   void loadServerProtocol() {
      try {
         ProtocolVersion serverProtocolVersion = this.injector.getServerProtocolVersion();
         ServerProtocolVersion versionInfo;
         if (this.platform.isProxy()) {
            SortedSet<ProtocolVersion> supportedVersions = this.injector.getServerProtocolVersions();
            versionInfo = new ServerProtocolVersionRange((ProtocolVersion)supportedVersions.first(), (ProtocolVersion)supportedVersions.last(), supportedVersions);
         } else {
            versionInfo = new ServerProtocolVersionSingleton(serverProtocolVersion);
         }

         this.protocolManager.setServerProtocol(versionInfo);
      } catch (Exception e) {
         this.platform.getLogger().log(Level.SEVERE, "ViaVersion failed to get the server protocol!", e);
      }

   }

   public void destroy() {
      if (this.platform.couldBeReloading()) {
         this.platform.getLogger().info("ViaVersion is disabling. If this is a reload and you experience issues, please reboot instead.");
      }

      try {
         this.injector.uninject();
      } catch (Exception e) {
         this.platform.getLogger().log(Level.SEVERE, "ViaVersion failed to uninject:", e);
      }

      this.loader.unload();
      this.scheduler.shutdown();
      this.platform.getLogger().info("ViaVersion has been disabled; uninjected the platform and shut down the scheduler.");
   }

   void checkJavaVersion() {
      String javaVersion = System.getProperty("java.version");
      Matcher matcher = Pattern.compile("(?:1\\.)?(\\d+)").matcher(javaVersion);
      if (!matcher.find()) {
         this.platform.getLogger().warning("Failed to determine Java version; could not parse: " + javaVersion);
      } else {
         String versionString = matcher.group(1);

         int version;
         try {
            version = Integer.parseInt(versionString);
         } catch (NumberFormatException e) {
            this.platform.getLogger().log(Level.WARNING, "Failed to determine Java version; could not parse: " + versionString, e);
            return;
         }

         if (version < 17) {
            this.platform.getLogger().warning("You are running an outdated Java version, please update it to at least Java 17 (your version is " + javaVersion + ").");
            this.platform.getLogger().warning("ViaVersion no longer officially supports this version of Java, only offering unsupported compatibility builds.");
            this.platform.getLogger().warning("See https://github.com/ViaVersion/ViaVersion/releases/tag/5.0.0 for more information.");
         }

      }
   }

   void unsupportedSoftwareWarning() {
      boolean found = false;

      for(UnsupportedSoftware software : this.platform.getUnsupportedSoftwareClasses()) {
         String match = software.match();
         if (match != null) {
            if (!found) {
               this.platform.getLogger().severe("************************************************");
               this.platform.getLogger().severe("You are using unsupported software and may encounter unforeseeable issues.");
               this.platform.getLogger().severe("");
               found = true;
            }

            this.platform.getLogger().severe("We strongly advise against using " + match + ":");
            this.platform.getLogger().severe(software.getReason());
            this.platform.getLogger().severe("");
         }
      }

      if (found) {
         this.platform.getLogger().severe("We will not provide support in case you encounter issues possibly related to this software.");
         this.platform.getLogger().severe("************************************************");
      }

   }

   public ViaPlatform getPlatform() {
      return this.platform;
   }

   public ConnectionManager getConnectionManager() {
      return this.connectionManager;
   }

   public ProtocolManager getProtocolManager() {
      return this.protocolManager;
   }

   public ViaProviders getProviders() {
      return this.providers;
   }

   public DebugHandler debugHandler() {
      return this.debugHandler;
   }

   public ViaInjector getInjector() {
      return this.injector;
   }

   public ViaCommandHandler getCommandHandler() {
      return this.commandHandler;
   }

   public ViaPlatformLoader getLoader() {
      return this.loader;
   }

   public Scheduler getScheduler() {
      return this.scheduler;
   }

   public ConfigurationProvider getConfigurationProvider() {
      return this.configurationProvider;
   }

   public Set getSubPlatforms() {
      return this.subPlatforms;
   }

   public void addEnableListener(Runnable runnable) {
      this.enableListeners.add(runnable);
   }

   public void addPostEnableListener(Runnable runnable) {
      this.postEnableListeners.add(runnable);
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public static final class ViaManagerBuilder {
      ViaPlatform platform;
      ViaInjector injector;
      ViaCommandHandler commandHandler;
      ViaPlatformLoader loader;

      public ViaManagerBuilder platform(ViaPlatform platform) {
         this.platform = platform;
         return this;
      }

      public ViaManagerBuilder injector(ViaInjector injector) {
         this.injector = injector;
         return this;
      }

      public ViaManagerBuilder loader(ViaPlatformLoader loader) {
         this.loader = loader;
         return this;
      }

      public ViaManagerBuilder commandHandler(ViaCommandHandler commandHandler) {
         this.commandHandler = commandHandler;
         return this;
      }

      public ViaManagerImpl build() {
         return new ViaManagerImpl(this.platform, this.injector, this.commandHandler, this.loader);
      }
   }
}
