package net.raphimc.vialoader.impl.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.VersionInfo;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import net.raphimc.vialoader.commands.UserCommandSender;
import net.raphimc.vialoader.impl.viaversion.VLApiBase;
import net.raphimc.vialoader.impl.viaversion.VLViaConfig;
import net.raphimc.vialoader.util.JLoggerToSLF4J;
import net.raphimc.vialoader.util.VLTask;
import org.slf4j.LoggerFactory;

public class ViaVersionPlatformImpl implements ViaPlatform {
   private static final Logger LOGGER = new JLoggerToSLF4J(LoggerFactory.getLogger("ViaVersion"));
   private final File dataFolder;
   private final AbstractViaConfig config;
   private final ViaAPI api;

   public ViaVersionPlatformImpl(File rootFolder) {
      this.dataFolder = new File(rootFolder, "ViaLoader");
      this.config = this.createConfig();
      this.api = this.createApi();
   }

   public Logger getLogger() {
      return LOGGER;
   }

   public String getPlatformName() {
      return "ViaLoader";
   }

   public String getPlatformVersion() {
      return "3.0.3-SNAPSHOT";
   }

   public String getPluginVersion() {
      return VersionInfo.getVersion();
   }

   public VLTask runAsync(Runnable runnable) {
      return new VLTask(Via.getManager().getScheduler().execute(runnable));
   }

   public VLTask runRepeatingAsync(Runnable runnable, long period) {
      return new VLTask(Via.getManager().getScheduler().scheduleRepeating(runnable, 0L, period * 50L, TimeUnit.MILLISECONDS));
   }

   public VLTask runSync(Runnable runnable) {
      return this.runAsync(runnable);
   }

   public VLTask runSync(Runnable runnable, long delay) {
      return new VLTask(Via.getManager().getScheduler().schedule(runnable, delay * 50L, TimeUnit.MILLISECONDS));
   }

   public VLTask runRepeatingSync(Runnable runnable, long period) {
      return this.runRepeatingAsync(runnable, period);
   }

   public ViaCommandSender[] getOnlinePlayers() {
      return (ViaCommandSender[])Via.getManager().getConnectionManager().getConnectedClients().values().stream().map(UserCommandSender::new).toArray((x$0) -> new ViaCommandSender[x$0]);
   }

   public void sendMessage(UUID uuid, String msg) {
      if (uuid == null) {
         this.getLogger().info(msg);
      } else {
         this.getLogger().info("[" + uuid + "] " + msg);
      }

   }

   public boolean kickPlayer(UUID uuid, String s) {
      return false;
   }

   public boolean isPluginEnabled() {
      return true;
   }

   public boolean hasPlugin(String s) {
      return false;
   }

   public boolean couldBeReloading() {
      return false;
   }

   public boolean isProxy() {
      return true;
   }

   public ViaAPI getApi() {
      return this.api;
   }

   public ViaVersionConfig getConf() {
      return this.config;
   }

   public File getDataFolder() {
      return this.dataFolder;
   }

   public void onReload() {
   }

   public JsonObject getDump() {
      return new JsonObject();
   }

   protected AbstractViaConfig createConfig() {
      return new VLViaConfig(new File(this.dataFolder, "viaversion.yml"), this.getLogger());
   }

   protected ViaAPI createApi() {
      return new VLApiBase();
   }
}
