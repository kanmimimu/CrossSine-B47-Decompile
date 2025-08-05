package net.ccbluex.liquidbounce.file.config.sections;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.config.ConfigSection;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/config/sections/ModulesSection;", "Lnet/ccbluex/liquidbounce/file/config/ConfigSection;", "()V", "load", "", "json", "Lcom/google/gson/JsonObject;", "save", "CrossSine"}
)
public final class ModulesSection extends ConfigSection {
   public ModulesSection() {
      super("modules");
   }

   public void load(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      Iterable $this$forEach$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Module it = (Module)element$iv;
         int var7 = 0;
         if (it.getLoadConfig()) {
            ModuleInfo moduleInfo = it.getModuleInfo();
            it.setState(moduleInfo.defaultOn());
            it.setKeyBind(moduleInfo.keyBind());
            it.setArray(moduleInfo.array());
            it.setAutoDisable(moduleInfo.autoDisable());
            Iterable $this$forEach$iv = (Iterable)it.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var14 = 0;
               value.setDefault();
            }
         }
      }

      for(Map.Entry entrySet : json.entrySet()) {
         Module var10000 = CrossSine.INSTANCE.getModuleManager().getModule((String)entrySet.getKey());
         if (var10000 != null) {
            Module module = var10000;
            JsonObject data = ((JsonElement)entrySet.getValue()).getAsJsonObject();
            if (data.has("state")) {
               module.setState(data.get("state").getAsBoolean());
            }

            if (data.has("keybind")) {
               module.setKeyBind(data.get("keybind").getAsInt());
            }

            if (data.has("array")) {
               module.setArray(data.get("array").getAsBoolean());
            }

            if (data.has("trigger")) {
               String values = data.get("trigger").getAsString();
               Intrinsics.checkNotNullExpressionValue(values, "data.get(\"trigger\").asString");
               module.setTriggerType(EnumTriggerType.valueOf(values));
            }

            if (data.has("autodisable")) {
               String var20 = data.get("autodisable").getAsString();
               Intrinsics.checkNotNullExpressionValue(var20, "data.get(\"autodisable\").asString");
               module.setAutoDisable(EnumAutoDisableType.valueOf(var20));
            }

            JsonObject values = data.getAsJsonObject("values");
            Iterable $this$forEach$iv = (Iterable)module.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value it = (Value)element$iv;
               int var27 = 0;
               if (values.has(it.getName())) {
                  JsonElement var28 = values.get(it.getName());
                  Intrinsics.checkNotNullExpressionValue(var28, "values.get(it.name)");
                  it.fromJson(var28);
               }
            }
         }
      }

   }

   @NotNull
   public JsonObject save() {
      JsonObject json = new JsonObject();
      Iterable $this$forEach$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Module it = (Module)element$iv;
         int var7 = 0;
         JsonObject moduleJson = new JsonObject();
         if (it.getLoadConfig()) {
            if (it.getCanEnable() || it.getTriggerType() != EnumTriggerType.PRESS) {
               moduleJson.addProperty("state", it.getState());
            }

            moduleJson.addProperty("keybind", (Number)it.getKeyBind());
            if (it.getCanEnable()) {
               moduleJson.addProperty("array", it.getArray());
            }

            if (it.getCanEnable()) {
               moduleJson.addProperty("autodisable", it.getAutoDisable().toString());
            }

            moduleJson.addProperty("trigger", it.getTriggerType().toString());
            JsonObject valuesJson = new JsonObject();
            Iterable $this$forEach$iv = (Iterable)it.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var15 = 0;
               if (it.getLoadConfig()) {
                  valuesJson.add(value.getName(), value.toJson());
               }
            }

            moduleJson.add("values", (JsonElement)valuesJson);
            json.add(it.getName(), (JsonElement)moduleJson);
         }
      }

      return json;
   }
}
