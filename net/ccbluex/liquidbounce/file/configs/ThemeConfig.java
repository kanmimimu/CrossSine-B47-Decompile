package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.client.ClientThemeModule;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/configs/ThemeConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "config", "", "saveConfig", "CrossSine"}
)
public final class ThemeConfig extends FileConfig {
   public ThemeConfig(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      super(file);
   }

   public void loadConfig(@NotNull String config) {
      Intrinsics.checkNotNullParameter(config, "config");
      JsonObject json = (new JsonParser()).parse(config).getAsJsonObject();
      if (json.has("Theme")) {
         ListValue var10000 = ClientTheme.INSTANCE.getClientColorMode();
         String var3 = json.get("Theme").getAsString();
         Intrinsics.checkNotNullExpressionValue(var3, "json.get(\"Theme\").asString");
         var10000.set(var3);
         var10000 = ClientThemeModule.INSTANCE.getMode();
         var3 = json.get("Theme").getAsString();
         Intrinsics.checkNotNullExpressionValue(var3, "json.get(\"Theme\").asString");
         var10000.set(var3);
      }

      if (json.has("Fade-Speed")) {
         ClientTheme.INSTANCE.getFadespeed().set(json.get("Fade-Speed").getAsInt());
         ClientThemeModule.INSTANCE.getFadespeed().set(json.get("Fade-Speed").getAsInt());
      }

      if (json.has("Index")) {
         ClientTheme.INSTANCE.getIndex().set(json.get("Index").getAsInt());
         ClientThemeModule.INSTANCE.getIndex().set(json.get("Index").getAsInt());
      }

   }

   @NotNull
   public String saveConfig() {
      JsonObject json = new JsonObject();
      json.addProperty("Theme", (String)ClientTheme.INSTANCE.getClientColorMode().get());
      json.addProperty("Fade-Speed", (Number)ClientTheme.INSTANCE.getFadespeed().get());
      json.addProperty("Index", (Number)ClientTheme.INSTANCE.getIndex().get());
      String var2 = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)json);
      Intrinsics.checkNotNullExpressionValue(var2, "FileManager.PRETTY_GSON.toJson(json)");
      return var2;
   }
}
