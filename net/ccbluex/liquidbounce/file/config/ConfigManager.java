package net.ccbluex.liquidbounce.file.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.world.Target;
import net.ccbluex.liquidbounce.features.special.NotificationUtil;
import net.ccbluex.liquidbounce.features.special.TYPE;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001)B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tH\u0002J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\t2\b\b\u0002\u0010\u001b\u001a\u00020\u0007J\u0006\u0010\u001c\u001a\u00020\u0019J\u0006\u0010\u001d\u001a\u00020\u0019J\u000e\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0017J\u0018\u0010 \u001a\u00020\u00192\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\"H\u0002J\u0010\u0010 \u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0011H\u0002J$\u0010\u001b\u001a\u00020\u00192\b\b\u0002\u0010$\u001a\u00020\u00072\b\b\u0002\u0010%\u001a\u00020\u00072\b\b\u0002\u0010&\u001a\u00020\u0007J\u0006\u0010%\u001a\u00020\u0019J\b\u0010'\u001a\u00020\u0019H\u0002J\u0006\u0010(\u001a\u00020\u0019R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/config/ConfigManager;", "", "()V", "configFile", "Ljava/io/File;", "configSetFile", "needSave", "", "nowConfig", "", "getNowConfig", "()Ljava/lang/String;", "setNowConfig", "(Ljava/lang/String;)V", "nowConfigInFile", "sections", "", "Lnet/ccbluex/liquidbounce/file/config/ConfigSection;", "calculateDaysAgo", "", "dateStr", "fetchOnlineConfigs", "", "Lnet/ccbluex/liquidbounce/file/config/ConfigManager$OnlineConfig;", "load", "", "name", "save", "loadConfigSet", "loadLegacySupport", "loadOnlineConfig", "config", "registerSection", "sectionClass", "Ljava/lang/Class;", "section", "autoSave", "saveConfigSet", "forceSave", "saveTicker", "smartSave", "OnlineConfig", "CrossSine"}
)
public final class ConfigManager {
   @NotNull
   private final File configSetFile;
   @NotNull
   private final List sections;
   @NotNull
   private String nowConfig;
   @NotNull
   private String nowConfigInFile;
   @NotNull
   private File configFile;
   private boolean needSave;

   public ConfigManager() {
      this.configSetFile = new File(CrossSine.INSTANCE.getFileManager().getDir(), "config-settings.json");
      this.sections = (List)(new ArrayList());
      this.nowConfig = "default";
      this.nowConfigInFile = "default";
      this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json"));
      Iterable $this$forEach$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".sections"), ConfigSection.class);
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Class p0 = (Class)element$iv;
         int var6 = 0;
         this.registerSection(p0);
      }

      Timer var7 = new Timer();
      long var8 = 30000L;
      long var9 = 30000L;
      TimerTask var10 = new ConfigManager$special$$inlined$schedule$1(this);
      var7.schedule(var10, var8, var9);
   }

   @NotNull
   public final String getNowConfig() {
      return this.nowConfig;
   }

   public final void setNowConfig(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.nowConfig = var1;
   }

   public final void load(@NotNull String name, boolean save) {
      Intrinsics.checkNotNullParameter(name, "name");
      CrossSine.INSTANCE.setLoadingConfig(true);
      if (save && !Intrinsics.areEqual((Object)this.nowConfig, (Object)name)) {
         save$default(this, false, true, true, 1, (Object)null);
      }

      this.nowConfig = name;
      this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json"));

      JsonObject var4;
      try {
         JsonObject var13;
         if (this.configFile.exists()) {
            JsonParser var10000 = new JsonParser();
            File var9 = this.configFile;
            Charset var5 = Charsets.UTF_8;
            InputStream var6 = (InputStream)(new FileInputStream(var9));
            var13 = var10000.parse((Reader)(new InputStreamReader(var6, var5))).getAsJsonObject();
         } else {
            var13 = new JsonObject();
         }

         var4 = var13;
      } catch (Exception e) {
         ClientUtils.INSTANCE.logError("Error parsing config file '" + name + "': " + e.getMessage());
         var4 = new JsonObject();
      }

      JsonObject json = var4;

      try {
         for(ConfigSection section : this.sections) {
            JsonObject var12 = json.has(section.getSectionName()) ? json.getAsJsonObject(section.getSectionName()) : new JsonObject();
            Intrinsics.checkNotNullExpressionValue(var12, "if (json.has(section.sec…) } else { JsonObject() }");
            section.load(var12);
         }
      } catch (Exception e) {
         ClientUtils.INSTANCE.logError("Error loading sections for config '" + name + "': " + e.getMessage());
      }

      if (!this.configFile.exists()) {
         save$default(this, false, false, true, 3, (Object)null);
      }

      if (save) {
         this.saveConfigSet();
      }

      ClientUtils.INSTANCE.logInfo("Config " + this.nowConfig + ".json loaded.");
      CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Config", "Config " + this.nowConfig + " loaded.", TYPE.SUCCESS, System.currentTimeMillis(), 3500));
      CrossSine.INSTANCE.setLoadingConfig(false);
   }

   // $FF: synthetic method
   public static void load$default(ConfigManager var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = true;
      }

      var0.load(var1, var2);
   }

   @NotNull
   public final List fetchOnlineConfigs() {
      String apiUrl = "https://api.github.com/repos/crosssine/crosssine.github.io/contents/file/config";
      String commitsApiUrl = "https://api.github.com/repos/crosssine/crosssine.github.io/commits?path=file/config&per_page=100";
      List configs = (List)(new ArrayList());
      String token = "114514";

      try {
         URLConnection $this$fetchOnlineConfigs_u24lambda_u2d1 = (new URL(apiUrl)).openConnection();
         int var9 = 0;
         $this$fetchOnlineConfigs_u24lambda_u2d1.setRequestProperty("User-Agent", "CrossSineClient47-" + RandomUtils.INSTANCE.randomString(4) + RandomUtils.INSTANCE.randomNumber(4));
         $this$fetchOnlineConfigs_u24lambda_u2d1.setRequestProperty("Authorization", Intrinsics.stringPlus("token ", token));
         InputStream var6 = $this$fetchOnlineConfigs_u24lambda_u2d1.getInputStream();
         Intrinsics.checkNotNullExpressionValue(var6, "URL(apiUrl).openConnecti…       }.getInputStream()");
         Charset $this$fetchOnlineConfigs_u24lambda_u2d1 = Charsets.UTF_8;
         Reader $this$fetchOnlineConfigs_u24lambda_u2d1 = (Reader)(new InputStreamReader(var6, $this$fetchOnlineConfigs_u24lambda_u2d1));
         var9 = 8192;
         Closeable var37 = (Closeable)($this$fetchOnlineConfigs_u24lambda_u2d1 instanceof BufferedReader ? (BufferedReader)$this$fetchOnlineConfigs_u24lambda_u2d1 : new BufferedReader($this$fetchOnlineConfigs_u24lambda_u2d1, var9));
         Throwable $this$fetchOnlineConfigs_u24lambda_u2d1 = null;

         try {
            BufferedReader it = (BufferedReader)var37;
            var9 = 0;
            commitArray = TextStreamsKt.readText((Reader)it);
         } catch (Throwable var34) {
            $this$fetchOnlineConfigs_u24lambda_u2d1 = var34;
            throw var34;
         } finally {
            CloseableKt.closeFinally(var37, $this$fetchOnlineConfigs_u24lambda_u2d1);
         }

         JsonArray var38 = (new JsonParser()).parse(commitArray).getAsJsonArray();
         URLConnection var48 = (new URL(commitsApiUrl)).openConnection();
         int var11 = 0;
         var48.setRequestProperty("User-Agent", "CrossSineClient47-" + RandomUtils.INSTANCE.randomString(4) + RandomUtils.INSTANCE.randomNumber(4));
         var48.setRequestProperty("Authorization", Intrinsics.stringPlus("token ", token));
         InputStream var43 = var48.getInputStream();
         Intrinsics.checkNotNullExpressionValue(var43, "URL(commitsApiUrl).openC…       }.getInputStream()");
         Charset var49 = Charsets.UTF_8;
         Reader $this$fetchOnlineConfigs_u24lambda_u2d3 = (Reader)(new InputStreamReader(var43, var49));
         var11 = 8192;
         Closeable var44 = (Closeable)($this$fetchOnlineConfigs_u24lambda_u2d3 instanceof BufferedReader ? (BufferedReader)$this$fetchOnlineConfigs_u24lambda_u2d3 : new BufferedReader($this$fetchOnlineConfigs_u24lambda_u2d3, var11));
         Throwable var50 = null;

         try {
            BufferedReader it = (BufferedReader)var44;
            var11 = 0;
            var53 = TextStreamsKt.readText((Reader)it);
         } catch (Throwable var32) {
            var50 = var32;
            throw var32;
         } finally {
            CloseableKt.closeFinally(var44, var50);
         }

         JsonArray var45 = (new JsonParser()).parse(var53).getAsJsonArray();
         Map var51 = (Map)(new LinkedHashMap());

         for(JsonElement commit : var45) {
            JsonObject commitObj = commit.getAsJsonObject();
            String commitDate = commitObj.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString();
            JsonElement var10000 = commitObj.get("files");
            JsonArray var66 = var10000 == null ? null : var10000.getAsJsonArray();
            if (var66 != null) {
               for(JsonElement file : var66) {
                  String filename = file.getAsJsonObject().get("filename").getAsString();
                  Intrinsics.checkNotNullExpressionValue(filename, "filename");
                  if (StringsKt.startsWith$default(filename, "file/config/", false, 2, (Object)null) && StringsKt.endsWith$default(filename, ".json", false, 2, (Object)null)) {
                     String name = StringsKt.removeSuffix(StringsKt.substringAfterLast$default(filename, "/", (String)null, 2, (Object)null), (CharSequence)".json");
                     if (!var51.containsKey(name)) {
                        Intrinsics.checkNotNullExpressionValue(commitDate, "commitDate");
                        var51.put(name, commitDate);
                     }
                  }
               }
            }
         }

         for(JsonElement element : var38) {
            JsonObject json = element.getAsJsonObject();
            String name = json.get("name").getAsString();
            String downloadUrl = json.get("download_url").getAsString();
            Intrinsics.checkNotNullExpressionValue(name, "name");
            if (StringsKt.endsWith$default(name, ".json", false, 2, (Object)null)) {
               String nameNoExt = StringsKt.removeSuffix(name, (CharSequence)".json");
               String dateString = (String)var51.get(nameNoExt);
               Integer var67;
               if (dateString == null) {
                  var67 = null;
               } else {
                  int var21 = 0;
                  var67 = this.calculateDaysAgo(dateString);
               }

               Integer daysAgo = var67;
               Intrinsics.checkNotNullExpressionValue(downloadUrl, "downloadUrl");
               configs.add(new OnlineConfig(nameNoExt, downloadUrl, daysAgo == null ? -1 : daysAgo));
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return configs;
   }

   private final int calculateDaysAgo(String dateStr) {
      DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
      OffsetDateTime commitDate = OffsetDateTime.parse((CharSequence)dateStr, formatter);
      OffsetDateTime now = OffsetDateTime.now((ZoneId)ZoneOffset.UTC);
      return (int)ChronoUnit.DAYS.between((Temporal)commitDate, (Temporal)now);
   }

   public final boolean loadOnlineConfig(@NotNull OnlineConfig config) {
      Intrinsics.checkNotNullParameter(config, "config");

      boolean url;
      try {
         URL url = new URL(config.getUrl());
         URLConnection connection = url.openConnection();
         connection.setRequestProperty("User-Agent", "CrossSineClient");
         InputStream input = connection.getInputStream();
         File outputFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(config.getName(), ".json"));
         CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Config", Intrinsics.stringPlus("Downloading ", config.getName()), TYPE.INFO, System.currentTimeMillis(), 2000));
         Closeable var6 = (Closeable)input;
         Throwable var7 = null;

         try {
            InputStream in = (InputStream)var6;
            int var10 = 0;
            Closeable var11 = (Closeable)(new FileOutputStream(outputFile));
            Throwable var12 = null;

            try {
               FileOutputStream out = (FileOutputStream)var11;
               int var15 = 0;
               Intrinsics.checkNotNullExpressionValue(in, "`in`");
               long var32 = ByteStreamsKt.copyTo$default(in, (OutputStream)out, 0, 2, (Object)null);
            } catch (Throwable var26) {
               var12 = var26;
               throw var26;
            } finally {
               CloseableKt.closeFinally(var11, var12);
            }
         } catch (Throwable var28) {
            var7 = var28;
            throw var28;
         } finally {
            CloseableKt.closeFinally(var6, var7);
         }

         CrossSine.INSTANCE.getConfigManager().nowConfig = config.getName();
         load$default(CrossSine.INSTANCE.getConfigManager(), config.getName(), false, 2, (Object)null);
         url = true;
      } catch (Exception e) {
         e.printStackTrace();
         url = false;
      }

      return url;
   }

   public final void save(boolean autoSave, boolean saveConfigSet, boolean forceSave) {
      if (!CrossSine.INSTANCE.isLoadingConfig() || forceSave) {
         JsonObject config = new JsonObject();

         for(ConfigSection section : this.sections) {
            config.add(section.getSectionName(), (JsonElement)section.save());
         }

         File var10000 = this.configFile;
         String var7 = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)config);
         Intrinsics.checkNotNullExpressionValue(var7, "FileManager.PRETTY_GSON.toJson(config)");
         FilesKt.writeText(var10000, var7, Charsets.UTF_8);
         if (saveConfigSet || forceSave) {
            this.saveConfigSet();
         }

         this.needSave = false;
         ClientUtils.INSTANCE.logInfo("Config " + this.nowConfig + ".json saved.");
         if (!autoSave) {
            CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Config", "Config " + this.nowConfig + " saved.", TYPE.SUCCESS, System.currentTimeMillis(), 3500));
         }

      }
   }

   // $FF: synthetic method
   public static void save$default(ConfigManager var0, boolean var1, boolean var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = false;
      }

      if ((var4 & 2) != 0) {
         var2 = !Intrinsics.areEqual((Object)var0.nowConfigInFile, (Object)var0.nowConfig);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      var0.save(var1, var2, var3);
   }

   private final void saveTicker() {
      if (this.needSave) {
         save$default(this, true, false, false, 6, (Object)null);
      }
   }

   public final void smartSave() {
      this.needSave = true;
   }

   public final void loadConfigSet() {
      JsonObject var9;
      if (this.configSetFile.exists()) {
         JsonObject var2;
         try {
            JsonParser var10000 = new JsonParser();
            File var7 = this.configSetFile;
            Charset var3 = Charsets.UTF_8;
            InputStream var4 = (InputStream)(new FileInputStream(var7));
            var2 = var10000.parse((Reader)(new InputStreamReader(var4, var3))).getAsJsonObject();
         } catch (Exception var6) {
            var2 = new JsonObject();
         }

         var9 = var2;
      } else {
         var9 = new JsonObject();
      }

      JsonObject configSet = var9;
      String var10001;
      if (configSet.has("file")) {
         String var8 = configSet.get("file").getAsString();
         Intrinsics.checkNotNullExpressionValue(var8, "{\n            configSet.…file\").asString\n        }");
         var10001 = var8;
      } else {
         var10001 = "default";
      }

      this.nowConfigInFile = var10001;

      try {
         this.load(this.nowConfigInFile, false);
      } catch (Exception e) {
         ClientUtils.INSTANCE.logError("Error loading config '" + this.nowConfigInFile + "', falling back to default: " + e.getMessage());
         this.load("default", false);
      }

   }

   public final void saveConfigSet() {
      JsonObject configSet = new JsonObject();
      configSet.addProperty("file", this.nowConfig);
      File var10000 = this.configSetFile;
      String var2 = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)configSet);
      Intrinsics.checkNotNullExpressionValue(var2, "FileManager.PRETTY_GSON.toJson(configSet)");
      FilesKt.writeText(var10000, var2, Charsets.UTF_8);
   }

   public final void loadLegacySupport() {
      if (CrossSine.INSTANCE.getFileManager().loadLegacy()) {
         if ((new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json"))).exists()) {
            this.nowConfig = "legacy";
            this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json"));
            save$default(this, false, false, true, 3, (Object)null);
         } else {
            save$default(this, false, false, true, 3, (Object)null);
         }

         ClientUtils.INSTANCE.logWarn("Converted legacy config");
      }

      File oldSettingDir = new File(CrossSine.INSTANCE.getFileManager().getDir(), "settings");
      if (oldSettingDir.exists()) {
         File[] var10000 = oldSettingDir.listFiles();
         if (var10000 != null) {
            Object[] $this$forEach$iv = var10000;
            int $i$f$forEach = 0;
            Object var4 = $this$forEach$iv;
            int var5 = 0;

            Object element$iv;
            for(int var6 = $this$forEach$iv.length; var5 < var6; ((File)element$iv).renameTo(new File(CrossSine.INSTANCE.getFileManager().getLegacySettingsDir(), ((File)element$iv).getName()))) {
               element$iv = ((Object[])var4)[var5];
               ++var5;
               int var9 = 0;
               if (((File)element$iv).isFile()) {
                  String name = this.getNowConfig();
                  ClientUtils.INSTANCE.logWarn("Converting legacy setting \"" + ((File)element$iv).getName() + '"');
                  this.load("default", false);
                  String var11 = ((File)element$iv).getName();
                  Intrinsics.checkNotNullExpressionValue(var11, "it.name");
                  this.setNowConfig(var11);
                  this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.getNowConfig(), ".json"));
                  byte[] var12 = Files.readAllBytes(((File)element$iv).toPath());
                  Intrinsics.checkNotNullExpressionValue(var12, "readAllBytes(it.toPath())");
                  loadLegacySupport$executeScript(new String(var12, Charsets.UTF_8));
                  save$default(this, false, false, true, 1, (Object)null);
                  this.setNowConfig(name);
                  this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.getNowConfig(), ".json"));
                  this.saveConfigSet();
               }

               if (!CrossSine.INSTANCE.getFileManager().getLegacySettingsDir().exists()) {
                  CrossSine.INSTANCE.getFileManager().getLegacySettingsDir().mkdir();
               }
            }
         }

         oldSettingDir.delete();
      }

   }

   private final boolean registerSection(ConfigSection section) {
      return this.sections.add(section);
   }

   private final void registerSection(Class sectionClass) {
      try {
         Object var2 = sectionClass.newInstance();
         Intrinsics.checkNotNullExpressionValue(var2, "sectionClass.newInstance()");
         this.registerSection((ConfigSection)var2);
      } catch (Throwable e) {
         ClientUtils.INSTANCE.logError("Failed to load config section: " + sectionClass.getName() + " (" + e.getClass().getName() + ": " + e.getMessage() + ')');
      }

   }

   private static final void loadLegacySupport$executeScript(String script) {
      Iterable $this$filter$iv = (Iterable)StringsKt.lines((CharSequence)script);
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         String it = (String)element$iv$iv;
         int var9 = 0;
         if (((CharSequence)it).length() > 0 && !StringsKt.startsWith$default((CharSequence)it, '#', false, 2, (Object)null)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$filter$iv = (Iterable)((List)destination$iv$iv);
      $i$f$filter = 0;
      int index$iv = 0;

      for(Object item$iv : $this$filter$iv) {
         int var24 = index$iv++;
         if (var24 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         String s = (String)item$iv;
         int var26 = 0;
         CharSequence var10000 = (CharSequence)s;
         String[] thisCollection$iv = new String[]{" "};
         Collection $this$toTypedArray$iv = (Collection)StringsKt.split$default(var10000, thisCollection$iv, false, 0, 6, (Object)null);
         int $i$f$toTypedArray = 0;
         Object[] var31 = $this$toTypedArray$iv.toArray(new String[0]);
         if (var31 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
         }

         String[] args = (String[])var31;
         if (args.length > 1) {
            label137: {
               switch (thisCollection$iv) {
                  case "targetPlayer":
                  case "load":
                     String url = StringUtils.toCompleteString(args, 1);

                     try {
                        HttpUtils var32 = HttpUtils.INSTANCE;
                        Intrinsics.checkNotNullExpressionValue(url, "url");
                        loadLegacySupport$executeScript(var32.get(url));
                     } catch (Exception e) {
                        e.printStackTrace();
                     }
                     continue;
                     break;
                  case "targetInvisible":
                     Target.INSTANCE.getInvisibleValue().set(StringsKt.equals(args[1], "true", true));
                     continue;
                     break;
                  case "targetDead":
                     Target.INSTANCE.getDeadValue().set(StringsKt.equals(args[1], "true", true));
                     continue;
                     break;
                  case "targetMobs":
                     Target.INSTANCE.getMobValue().set(StringsKt.equals(args[1], "true", true));
                     continue;
                     break;
                  case "targetAnimals":
                     Target.INSTANCE.getAnimalValue().set(StringsKt.equals(args[1], "true", true));
                     continue;
                     break;
                  case "targetPlayers":
               }

               if (args.length != 3) {
                  continue;
               }

               String moduleName = args[0];
               String valueName = args[1];
               String value = args[2];
               Module var33 = CrossSine.INSTANCE.getModuleManager().getModule(moduleName);
               if (var33 == null) {
                  continue;
               }

               Module module = var33;
               if (StringsKt.equals(valueName, "toggle", true)) {
                  module.setState(StringsKt.equals(value, "true", true));
                  continue;
               }

               if (StringsKt.equals(valueName, "bind", true)) {
                  module.setKeyBind(Keyboard.getKeyIndex(value));
                  continue;
               }

               Value var34 = module.getValue(valueName);
               if (var34 == null) {
                  continue;
               }

               Value moduleValue = var34;

               try {
                  if (moduleValue instanceof BoolValue) {
                     ((BoolValue)moduleValue).changeValue(Boolean.parseBoolean(value));
                  } else if (moduleValue instanceof FloatValue) {
                     ((FloatValue)moduleValue).changeValue(Float.parseFloat(value));
                  } else if (moduleValue instanceof IntegerValue) {
                     ((IntegerValue)moduleValue).changeValue(Integer.parseInt(value));
                  } else if (moduleValue instanceof TextValue) {
                     ((TextValue)moduleValue).changeValue(value);
                  } else if (moduleValue instanceof ListValue) {
                     ((ListValue)moduleValue).changeValue(value);
                  }
               } catch (Exception e) {
                  e.printStackTrace();
               }
               continue;
            }

            Target.INSTANCE.getPlayerValue().set(StringsKt.equals(args[1], "true", true));
         }
      }

   }

   // $FF: synthetic method
   public static final void access$saveTicker(ConfigManager $this) {
      $this.saveTicker();
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0018"},
      d2 = {"Lnet/ccbluex/liquidbounce/file/config/ConfigManager$OnlineConfig;", "", "name", "", "url", "daysAgo", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getDaysAgo", "()I", "setDaysAgo", "(I)V", "getName", "()Ljava/lang/String;", "getUrl", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "CrossSine"}
   )
   public static final class OnlineConfig {
      @NotNull
      private final String name;
      @NotNull
      private final String url;
      private int daysAgo;

      public OnlineConfig(@NotNull String name, @NotNull String url, int daysAgo) {
         Intrinsics.checkNotNullParameter(name, "name");
         Intrinsics.checkNotNullParameter(url, "url");
         super();
         this.name = name;
         this.url = url;
         this.daysAgo = daysAgo;
      }

      // $FF: synthetic method
      public OnlineConfig(String var1, String var2, int var3, int var4, DefaultConstructorMarker var5) {
         if ((var4 & 4) != 0) {
            var3 = -1;
         }

         this(var1, var2, var3);
      }

      @NotNull
      public final String getName() {
         return this.name;
      }

      @NotNull
      public final String getUrl() {
         return this.url;
      }

      public final int getDaysAgo() {
         return this.daysAgo;
      }

      public final void setDaysAgo(int var1) {
         this.daysAgo = var1;
      }

      @NotNull
      public final String component1() {
         return this.name;
      }

      @NotNull
      public final String component2() {
         return this.url;
      }

      public final int component3() {
         return this.daysAgo;
      }

      @NotNull
      public final OnlineConfig copy(@NotNull String name, @NotNull String url, int daysAgo) {
         Intrinsics.checkNotNullParameter(name, "name");
         Intrinsics.checkNotNullParameter(url, "url");
         return new OnlineConfig(name, url, daysAgo);
      }

      // $FF: synthetic method
      public static OnlineConfig copy$default(OnlineConfig var0, String var1, String var2, int var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.name;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.url;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.daysAgo;
         }

         return var0.copy(var1, var2, var3);
      }

      @NotNull
      public String toString() {
         return "OnlineConfig(name=" + this.name + ", url=" + this.url + ", daysAgo=" + this.daysAgo + ')';
      }

      public int hashCode() {
         int result = this.name.hashCode();
         result = result * 31 + this.url.hashCode();
         result = result * 31 + Integer.hashCode(this.daysAgo);
         return result;
      }

      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof OnlineConfig)) {
            return false;
         } else {
            OnlineConfig var2 = (OnlineConfig)other;
            if (!Intrinsics.areEqual((Object)this.name, (Object)var2.name)) {
               return false;
            } else if (!Intrinsics.areEqual((Object)this.url, (Object)var2.url)) {
               return false;
            } else {
               return this.daysAgo == var2.daysAgo;
            }
         }
      }
   }
}
