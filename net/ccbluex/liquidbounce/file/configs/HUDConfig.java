package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/configs/HUDConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "config", "", "saveConfig", "CrossSine"}
)
public final class HUDConfig extends FileConfig {
   public HUDConfig(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      super(file);
   }

   public void loadConfig(@NotNull String config) {
      Intrinsics.checkNotNullParameter(config, "config");
      JsonObject json = (new JsonParser()).parse(config).getAsJsonObject();
      Iterable $this$forEach$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Module module = (Module)element$iv;
         int var8 = 0;
         if (!module.getLoadConfig()) {
            JsonObject var10000 = json.getAsJsonObject(module.getName());
            if (var10000 == null) {
               return;
            }

            JsonObject data = var10000;
            JsonElement var10001 = data.get("state");
            boolean var25;
            if (var10001 == null) {
               var25 = false;
            } else {
               boolean var11 = var10001.getAsBoolean();
               var25 = var11;
            }

            module.setState(var25);
            JsonElement var26 = data.get("keybind");
            int var27;
            if (var26 == null) {
               var27 = 0;
            } else {
               int var20 = var26.getAsInt();
               var27 = var20;
            }

            module.setKeyBind(var27);
            JsonElement var28 = data.get("array");
            boolean var29;
            if (var28 == null) {
               var29 = false;
            } else {
               boolean var21 = var28.getAsBoolean();
               var29 = var21;
            }

            module.setArray(var29);
            JsonElement var30 = data.get("trigger");
            String var31;
            if (var30 == null) {
               var31 = "TOGGLE";
            } else {
               var31 = var30.getAsString();
               if (var31 == null) {
                  var31 = "TOGGLE";
               }
            }

            module.setTriggerType(EnumTriggerType.valueOf(var31));
            JsonElement var32 = data.get("autodisable");
            String var33;
            if (var32 == null) {
               var33 = "NONE";
            } else {
               var33 = var32.getAsString();
               if (var33 == null) {
                  var33 = "NONE";
               }
            }

            module.setAutoDisable(EnumAutoDisableType.valueOf(var33));

            try {
               Field posXField = module.getClass().getDeclaredField("posX");
               posXField.setAccessible(true);
               Field posYField = module.getClass().getDeclaredField("posY");
               posYField.setAccessible(true);
               if (data.has("posX")) {
                  posXField.setFloat(module, data.get("posX").getAsFloat());
               }

               if (data.has("posY")) {
                  posYField.setFloat(module, data.get("posY").getAsFloat());
               }
            } catch (Exception var18) {
            }

            var10000 = data.getAsJsonObject("values");
            if (var10000 == null) {
               var10000 = new JsonObject();
            }

            JsonObject values = var10000;
            Iterable $this$forEach$iv = (Iterable)module.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value it = (Value)element$iv;
               int var16 = 0;
               JsonElement jsonValue = values.get(it.getName());
               if (jsonValue != null) {
                  it.fromJson(jsonValue);
               }
            }
         }
      }

   }

   @NotNull
   public String saveConfig() {
      JsonObject json = new JsonObject();
      Iterable $this$forEach$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Module module = (Module)element$iv;
         int var7 = 0;
         if (!module.getLoadConfig()) {
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("state", module.getState());
            moduleJson.addProperty("keybind", (Number)module.getKeyBind());
            moduleJson.addProperty("array", module.getArray());
            moduleJson.addProperty("trigger", module.getTriggerType().toString());
            moduleJson.addProperty("autodisable", module.getAutoDisable().toString());

            try {
               Field posXField = module.getClass().getDeclaredField("posX");
               posXField.setAccessible(true);
               Field posYField = module.getClass().getDeclaredField("posY");
               posYField.setAccessible(true);
               moduleJson.addProperty("posX", (Number)posXField.getFloat(module));
               moduleJson.addProperty("posY", (Number)posYField.getFloat(module));
            } catch (Exception var16) {
            }

            JsonObject valuesJson = new JsonObject();
            Iterable $this$forEach$iv = (Iterable)module.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var15 = 0;
               valuesJson.add(value.getName(), value.toJson());
            }

            moduleJson.add("values", (JsonElement)valuesJson);
            json.add(module.getName(), (JsonElement)moduleJson);
         }
      }

      String var17 = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)json);
      Intrinsics.checkNotNullExpressionValue(var17, "FileManager.PRETTY_GSON.toJson(json)");
      return var17;
   }
}
