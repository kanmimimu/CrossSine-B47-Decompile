package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.bukkit.commands.BukkitCommandHandler;
import com.viaversion.viaversion.bukkit.commands.BukkitCommandSender;
import com.viaversion.viaversion.bukkit.listeners.JoinListener;
import com.viaversion.viaversion.bukkit.platform.BukkitViaAPI;
import com.viaversion.viaversion.bukkit.platform.BukkitViaConfig;
import com.viaversion.viaversion.bukkit.platform.BukkitViaInjector;
import com.viaversion.viaversion.bukkit.platform.BukkitViaLoader;
import com.viaversion.viaversion.bukkit.platform.BukkitViaTask;
import com.viaversion.viaversion.bukkit.platform.BukkitViaTaskTask;
import com.viaversion.viaversion.bukkit.platform.PaperViaInjector;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.unsupported.UnsupportedPlugin;
import com.viaversion.viaversion.unsupported.UnsupportedServerSoftware;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ViaVersionPlugin extends JavaPlugin implements ViaPlatform {
   static final boolean FOLIA = PaperViaInjector.hasClass("io.papermc.paper.threadedregions.RegionizedServer");
   static ViaVersionPlugin instance;
   final BukkitCommandHandler commandHandler = new BukkitCommandHandler();
   final BukkitViaConfig conf;
   final ViaAPI api = new BukkitViaAPI(this);
   boolean lateBind;

   public ViaVersionPlugin() {
      instance = this;
      this.conf = new BukkitViaConfig(this.getDataFolder(), this.getLogger());
      Via.init(ViaManagerImpl.builder().platform(this).commandHandler(this.commandHandler).injector(new BukkitViaInjector()).loader(new BukkitViaLoader(this)).build());
      this.conf.reload();
   }

   public void onLoad() {
      this.lateBind = !((BukkitViaInjector)Via.getManager().getInjector()).isBinded();
      if (!this.lateBind) {
         Logger var10000 = this.getLogger();
         String var3 = this.getDescription().getVersion();
         var10000.info("ViaVersion " + var3 + " is now loaded. Registering protocol transformers and injecting...");
         ((ViaManagerImpl)Via.getManager()).init();
      } else {
         Logger var6 = this.getLogger();
         String var5 = this.getDescription().getVersion();
         var6.info("ViaVersion " + var5 + " is now loaded. Waiting for boot (late-bind).");
      }

   }

   public void onEnable() {
      ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
      if (this.lateBind) {
         this.getLogger().info("Registering protocol transformers and injecting...");
         manager.init();
      }

      if (Via.getConfig().shouldRegisterUserConnectionOnJoin()) {
         this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
      }

      if (FOLIA) {
         Class<? extends Event> serverInitEventClass;
         try {
            serverInitEventClass = Class.forName("io.papermc.paper.threadedregions.RegionizedServerInitEvent");
         } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
         }

         this.getServer().getPluginManager().registerEvent(serverInitEventClass, new Listener() {
         }, EventPriority.HIGHEST, (listener, event) -> manager.onServerLoaded(), this);
      } else if (Via.getManager().getInjector().lateProtocolVersionSetting()) {
         Objects.requireNonNull(manager);
         this.runSync(manager::onServerLoaded);
      } else {
         manager.onServerLoaded();
      }

      this.getCommand("viaversion").setExecutor(this.commandHandler);
      this.getCommand("viaversion").setTabCompleter(this.commandHandler);
   }

   public void onDisable() {
      ((ViaManagerImpl)Via.getManager()).destroy();
   }

   public String getPlatformName() {
      return Bukkit.getServer().getName();
   }

   public String getPlatformVersion() {
      return Bukkit.getServer().getVersion();
   }

   public String getPluginVersion() {
      return this.getDescription().getVersion();
   }

   public PlatformTask runAsync(Runnable runnable) {
      return (PlatformTask)(FOLIA ? new BukkitViaTaskTask(Via.getManager().getScheduler().execute(runnable)) : new BukkitViaTask(this.getServer().getScheduler().runTaskAsynchronously(this, runnable)));
   }

   public PlatformTask runRepeatingAsync(Runnable runnable, long ticks) {
      return (PlatformTask)(FOLIA ? new BukkitViaTaskTask(Via.getManager().getScheduler().scheduleRepeating(runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS)) : new BukkitViaTask(this.getServer().getScheduler().runTaskTimerAsynchronously(this, runnable, 0L, ticks)));
   }

   public PlatformTask runSync(Runnable runnable) {
      return (PlatformTask)(FOLIA ? this.runAsync(runnable) : new BukkitViaTask(this.getServer().getScheduler().runTask(this, runnable)));
   }

   public PlatformTask runSync(Runnable runnable, long delay) {
      return new BukkitViaTask(this.getServer().getScheduler().runTaskLater(this, runnable, delay));
   }

   public PlatformTask runRepeatingSync(Runnable runnable, long period) {
      return new BukkitViaTask(this.getServer().getScheduler().runTaskTimer(this, runnable, 0L, period));
   }

   public ViaCommandSender[] getOnlinePlayers() {
      ViaCommandSender[] array = new ViaCommandSender[Bukkit.getOnlinePlayers().size()];
      int i = 0;

      for(Player player : Bukkit.getOnlinePlayers()) {
         array[i++] = new BukkitCommandSender(player);
      }

      return array;
   }

   public void sendMessage(UUID uuid, String message) {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
         player.sendMessage(message);
      }

   }

   public boolean kickPlayer(UUID uuid, String message) {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
         player.kickPlayer(message);
         return true;
      } else {
         return false;
      }
   }

   public boolean isPluginEnabled() {
      return Bukkit.getPluginManager().getPlugin("ViaVersion").isEnabled();
   }

   public void onReload() {
      if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
         this.getLogger().severe("ViaVersion is already loaded, we're going to kick all the players... because otherwise we'll crash because of ProtocolLib.");

         for(Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', this.conf.getReloadDisconnectMsg()));
         }
      } else {
         this.getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
      }

   }

   public JsonObject getDump() {
      JsonObject platformSpecific = new JsonObject();
      List<PluginInfo> plugins = new ArrayList();

      for(Plugin p : Bukkit.getPluginManager().getPlugins()) {
         plugins.add(new PluginInfo(p.isEnabled(), p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), p.getDescription().getAuthors()));
      }

      platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
      return platformSpecific;
   }

   public BukkitViaConfig getConf() {
      return this.conf;
   }

   public ViaAPI getApi() {
      return this.api;
   }

   public final Collection getUnsupportedSoftwareClasses() {
      List<UnsupportedSoftware> list = new ArrayList(ViaPlatform.super.getUnsupportedSoftwareClasses());
      list.add((new UnsupportedServerSoftware.Builder()).name("Yatopia").reason("You are using server software that - outside of possibly breaking ViaVersion - can also cause severe damage to your server's integrity as a whole.").addClassName("org.yatopiamc.yatopia.server.YatopiaConfig").addClassName("net.yatopia.api.event.PlayerAttackEntityEvent").addClassName("yatopiamc.org.yatopia.server.YatopiaConfig").addMethod("org.bukkit.Server", "getLastTickTime").build());
      list.add((new UnsupportedPlugin.Builder()).name("software to mess with message signing").reason("Instead of doing the obvious (or nothing at all), these kinds of plugins completely break chat message handling, usually then also breaking other plugins.").addPlugin("NoEncryption").addPlugin("NoReport").addPlugin("NoChatReports").addPlugin("NoChatReport").build());
      return Collections.unmodifiableList(list);
   }

   public boolean hasPlugin(String name) {
      return this.getServer().getPluginManager().getPlugin(name) != null;
   }

   public boolean couldBeReloading() {
      return !PaperViaInjector.PAPER_IS_STOPPING_METHOD || !Bukkit.isStopping();
   }

   public boolean isLateBind() {
      return this.lateBind;
   }

   /** @deprecated */
   @Deprecated(
      forRemoval = true
   )
   public static ViaVersionPlugin getInstance() {
      return instance;
   }
}
