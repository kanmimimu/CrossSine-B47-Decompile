package net.ccbluex.liquidbounce.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.configs.AccountsConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.file.configs.HUDConfig;
import net.ccbluex.liquidbounce.file.configs.ScriptConfig;
import net.ccbluex.liquidbounce.file.configs.SpecialConfig;
import net.ccbluex.liquidbounce.file.configs.ThemeConfig;
import net.ccbluex.liquidbounce.file.configs.XRayConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 :2\u00020\u0001:\u0001:B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.J\u001f\u0010/\u001a\u00020,2\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020.01\"\u00020.¢\u0006\u0002\u00102J\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020,J\u000e\u00106\u001a\u00020,2\u0006\u0010-\u001a\u00020.J\u0018\u00106\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u00107\u001a\u000204H\u0002J\u001f\u00108\u001a\u00020,2\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020.01\"\u00020.¢\u0006\u0002\u00102J\u0006\u00109\u001a\u00020,R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\nR\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*¨\u0006;"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/FileManager;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "accountsConfig", "Lnet/ccbluex/liquidbounce/file/configs/AccountsConfig;", "getAccountsConfig", "()Lnet/ccbluex/liquidbounce/file/configs/AccountsConfig;", "configsDir", "Ljava/io/File;", "getConfigsDir", "()Ljava/io/File;", "dir", "getDir", "fontsDir", "getFontsDir", "friendsConfig", "Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;", "getFriendsConfig", "()Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;", "setFriendsConfig", "(Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;)V", "hudConfig", "Lnet/ccbluex/liquidbounce/file/configs/HUDConfig;", "getHudConfig", "()Lnet/ccbluex/liquidbounce/file/configs/HUDConfig;", "legacySettingsDir", "getLegacySettingsDir", "specialConfig", "Lnet/ccbluex/liquidbounce/file/configs/SpecialConfig;", "getSpecialConfig", "()Lnet/ccbluex/liquidbounce/file/configs/SpecialConfig;", "subscriptsConfig", "Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig;", "getSubscriptsConfig", "()Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig;", "themeConfig", "Lnet/ccbluex/liquidbounce/file/configs/ThemeConfig;", "getThemeConfig", "()Lnet/ccbluex/liquidbounce/file/configs/ThemeConfig;", "xrayConfig", "Lnet/ccbluex/liquidbounce/file/configs/XRayConfig;", "getXrayConfig", "()Lnet/ccbluex/liquidbounce/file/configs/XRayConfig;", "loadConfig", "", "config", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "loadConfigs", "configs", "", "([Lnet/ccbluex/liquidbounce/file/FileConfig;)V", "loadLegacy", "", "saveAllConfigs", "saveConfig", "ignoreStarting", "saveConfigs", "setupFolder", "Companion", "CrossSine"}
)
public final class FileManager extends MinecraftInstance {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final File dir;
   @NotNull
   private final File fontsDir;
   @NotNull
   private final File configsDir;
   @NotNull
   private final File legacySettingsDir;
   @NotNull
   private final AccountsConfig accountsConfig;
   @NotNull
   private FriendsConfig friendsConfig;
   @NotNull
   private final XRayConfig xrayConfig;
   @NotNull
   private final ScriptConfig subscriptsConfig;
   @NotNull
   private final SpecialConfig specialConfig;
   @NotNull
   private final ThemeConfig themeConfig;
   @NotNull
   private final HUDConfig hudConfig;
   private static final Gson PRETTY_GSON = (new GsonBuilder()).setPrettyPrinting().create();

   public FileManager() {
      this.dir = new File(MinecraftInstance.mc.field_71412_D, "CrossSine");
      this.fontsDir = new File(this.dir, "fonts");
      this.configsDir = new File(this.dir, "configs");
      this.legacySettingsDir = new File(this.dir, "legacy-settings.json");
      this.accountsConfig = new AccountsConfig(new File(this.dir, "accounts.json"));
      this.friendsConfig = new FriendsConfig(new File(this.dir, "friends.json"));
      this.xrayConfig = new XRayConfig(new File(this.dir, "xray-blocks.json"));
      this.subscriptsConfig = new ScriptConfig(new File(this.dir, "subscripts.json"));
      this.specialConfig = new SpecialConfig(new File(this.dir, "special.json"));
      this.themeConfig = new ThemeConfig(new File(this.dir, "themeColor.json"));
      this.hudConfig = new HUDConfig(new File(this.dir, "clienthud.json"));
      this.setupFolder();
   }

   @NotNull
   public final File getDir() {
      return this.dir;
   }

   @NotNull
   public final File getFontsDir() {
      return this.fontsDir;
   }

   @NotNull
   public final File getConfigsDir() {
      return this.configsDir;
   }

   @NotNull
   public final File getLegacySettingsDir() {
      return this.legacySettingsDir;
   }

   @NotNull
   public final AccountsConfig getAccountsConfig() {
      return this.accountsConfig;
   }

   @NotNull
   public final FriendsConfig getFriendsConfig() {
      return this.friendsConfig;
   }

   public final void setFriendsConfig(@NotNull FriendsConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.friendsConfig = var1;
   }

   @NotNull
   public final XRayConfig getXrayConfig() {
      return this.xrayConfig;
   }

   @NotNull
   public final ScriptConfig getSubscriptsConfig() {
      return this.subscriptsConfig;
   }

   @NotNull
   public final SpecialConfig getSpecialConfig() {
      return this.specialConfig;
   }

   @NotNull
   public final ThemeConfig getThemeConfig() {
      return this.themeConfig;
   }

   @NotNull
   public final HUDConfig getHudConfig() {
      return this.hudConfig;
   }

   public final void setupFolder() {
      if (!this.dir.exists()) {
         this.dir.mkdir();
      }

      if (!this.fontsDir.exists()) {
         this.fontsDir.mkdir();
      }

      if (!this.configsDir.exists()) {
         this.configsDir.mkdir();
      }

   }

   public final void loadConfigs(@NotNull FileConfig... configs) {
      Intrinsics.checkNotNullParameter(configs, "configs");
      if (!CrossSine.INSTANCE.getDestruced()) {
         int var2 = 0;
         int var3 = configs.length;

         while(var2 < var3) {
            FileConfig fileConfig = configs[var2];
            ++var2;
            this.loadConfig(fileConfig);
         }

      }
   }

   public final void loadConfig(@NotNull FileConfig config) {
      Intrinsics.checkNotNullParameter(config, "config");
      if (!CrossSine.INSTANCE.getDestruced()) {
         if (!config.hasConfig()) {
            ClientUtils.INSTANCE.logInfo("[FileManager] Skipped loading config: " + config.getFile().getName() + '.');
            this.saveConfig(config, true);
         } else {
            try {
               config.loadConfig(config.loadConfigFile());
               ClientUtils.INSTANCE.logInfo("[FileManager] Loaded config: " + config.getFile().getName() + '.');
            } catch (Throwable t) {
               ClientUtils.INSTANCE.logError("[FileManager] Failed to load config file: " + config.getFile().getName() + '.', t);
            }

         }
      }
   }

   public final void saveAllConfigs() {
      if (!CrossSine.INSTANCE.getDestruced()) {
         Field[] var2 = this.getClass().getDeclaredFields();
         Intrinsics.checkNotNullExpressionValue(var2, "javaClass.declaredFields");
         Field[] var1 = var2;
         int var7 = 0;
         int var3 = var2.length;

         while(var7 < var3) {
            Field field = var1[var7];
            ++var7;

            try {
               field.setAccessible(true);
               Object obj = field.get(this);
               if (obj instanceof FileConfig) {
                  this.saveConfig((FileConfig)obj);
               }
            } catch (IllegalAccessException e) {
               ClientUtils.INSTANCE.logError("[FileManager] Failed to save config file of field " + field.getName() + '.', (Throwable)e);
            }
         }

      }
   }

   public final void saveConfigs(@NotNull FileConfig... configs) {
      Intrinsics.checkNotNullParameter(configs, "configs");
      if (!CrossSine.INSTANCE.getDestruced()) {
         int var2 = 0;
         int var3 = configs.length;

         while(var2 < var3) {
            FileConfig fileConfig = configs[var2];
            ++var2;
            this.saveConfig(fileConfig);
         }

      }
   }

   public final void saveConfig(@NotNull FileConfig config) {
      Intrinsics.checkNotNullParameter(config, "config");
      if (!CrossSine.INSTANCE.getDestruced()) {
         this.saveConfig(config, true);
      }
   }

   private final void saveConfig(FileConfig config, boolean ignoreStarting) {
      if (!CrossSine.INSTANCE.getDestruced()) {
         if (ignoreStarting || !CrossSine.INSTANCE.isStarting()) {
            try {
               if (!config.hasConfig()) {
                  config.createConfig();
               }

               config.saveConfigFile(config.saveConfig());
               ClientUtils.INSTANCE.logInfo("[FileManager] Saved config: " + config.getFile().getName() + '.');
            } catch (Throwable t) {
               ClientUtils.INSTANCE.logError("[FileManager] Failed to save config file: " + config.getFile().getName() + '.', t);
            }

         }
      }
   }

   public final boolean loadLegacy() throws IOException {
      if (CrossSine.INSTANCE.getDestruced()) {
         return false;
      } else {
         boolean modified = false;
         File modulesFile = new File(this.dir, "modules.json");
         if (modulesFile.exists()) {
            modified = true;
            FileReader fr = new FileReader(modulesFile);

            try {
               JsonElement jsonElement = (new JsonParser()).parse((Reader)(new BufferedReader((Reader)fr)));

               for(Map.Entry var6 : jsonElement.getAsJsonObject().entrySet()) {
                  Intrinsics.checkNotNullExpressionValue(var6, "jsonElement.asJsonObject.entrySet()");
                  String key = (String)var6.getKey();
                  JsonElement value = (JsonElement)var6.getValue();
                  Module module = CrossSine.INSTANCE.getModuleManager().getModule(key);
                  if (module != null) {
                     if (value == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.google.gson.JsonObject");
                     }

                     JsonObject jsonModule = (JsonObject)value;
                     module.setState(jsonModule.get("State").getAsBoolean());
                     module.setKeyBind(jsonModule.get("KeyBind").getAsInt());
                     if (jsonModule.has("Array")) {
                        module.setArray(jsonModule.get("Array").getAsBoolean());
                     }

                     if (jsonModule.has("AutoDisable")) {
                        String var11 = jsonModule.get("AutoDisable").getAsString();
                        Intrinsics.checkNotNullExpressionValue(var11, "jsonModule[\"AutoDisable\"].asString");
                        module.setAutoDisable(EnumAutoDisableType.valueOf(var11));
                     }
                  }
               }
            } catch (Throwable t) {
               t.printStackTrace();
            }

            try {
               fr.close();
            } catch (IOException e) {
               e.printStackTrace();
            }

            ClientUtils.INSTANCE.logInfo("Deleted Legacy config " + modulesFile.getName() + ' ' + modulesFile.delete());
         }

         File valuesFile = new File(this.dir, "values.json");
         if (valuesFile.exists()) {
            modified = true;
            FileReader fr = new FileReader(valuesFile);

            try {
               JsonObject jsonObject = (new JsonParser()).parse((Reader)(new BufferedReader((Reader)fr))).getAsJsonObject();

               for(Map.Entry var29 : jsonObject.entrySet()) {
                  Intrinsics.checkNotNullExpressionValue(var29, "jsonObject.entrySet()");
                  String key = (String)var29.getKey();
                  JsonElement value = (JsonElement)var29.getValue();
                  Module module = CrossSine.INSTANCE.getModuleManager().getModule(key);
                  if (module != null) {
                     if (value == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.google.gson.JsonObject");
                     }

                     JsonObject jsonModule = (JsonObject)value;

                     for(Value moduleValue : module.getValues()) {
                        JsonElement element = jsonModule.get(moduleValue.getName());
                        if (element != null) {
                           moduleValue.fromJson(element);
                        }
                     }
                  }
               }
            } catch (Throwable t) {
               t.printStackTrace();
            }

            try {
               fr.close();
            } catch (IOException e) {
               e.printStackTrace();
            }

            ClientUtils.INSTANCE.logInfo("Deleted Legacy config " + valuesFile.getName() + ' ' + valuesFile.delete());
         }

         File macrosFile = new File(this.dir, "macros.json");
         if (macrosFile.exists()) {
            modified = true;
            FileReader fr = new FileReader(macrosFile);

            try {
               for(JsonElement jsonElement : (new JsonParser()).parse((Reader)(new BufferedReader((Reader)fr))).getAsJsonArray()) {
                  JsonObject macroJson = jsonElement.getAsJsonObject();
                  ArrayList var10000 = CrossSine.INSTANCE.getMacroManager().getMacros();
                  int var10003 = macroJson.get("key").getAsInt();
                  String var36 = macroJson.get("command").getAsString();
                  Intrinsics.checkNotNullExpressionValue(var36, "macroJson[\"command\"].asString");
                  var10000.add(new Macro(var10003, var36));
               }
            } catch (Throwable t) {
               t.printStackTrace();
            }

            try {
               fr.close();
            } catch (IOException e) {
               e.printStackTrace();
            }

            ClientUtils.INSTANCE.logInfo("Deleted Legacy config " + macrosFile.getName() + ' ' + macrosFile.delete());
         }

         File shortcutsFile = new File(this.dir, "shortcuts.json");
         if (shortcutsFile.exists()) {
            shortcutsFile.delete();
         }

         return modified;
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"},
      d2 = {"Lnet/ccbluex/liquidbounce/file/FileManager$Companion;", "", "()V", "PRETTY_GSON", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "getPRETTY_GSON", "()Lcom/google/gson/Gson;", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      public final Gson getPRETTY_GSON() {
         return FileManager.PRETTY_GSON;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
