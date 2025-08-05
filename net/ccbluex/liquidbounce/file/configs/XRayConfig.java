package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.visual.XRay;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/configs/XRayConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "config", "", "saveConfig", "CrossSine"}
)
public final class XRayConfig extends FileConfig {
   public XRayConfig(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      super(file);
   }

   public void loadConfig(@NotNull String config) {
      Intrinsics.checkNotNullParameter(config, "config");
      Module var10000 = CrossSine.INSTANCE.getModuleManager().get(XRay.class);
      Intrinsics.checkNotNull(var10000);
      XRay xRay = (XRay)var10000;
      JsonArray jsonArray = (new JsonParser()).parse(config).getAsJsonArray();
      xRay.getXrayBlocks().clear();

      for(JsonElement jsonElement : jsonArray) {
         try {
            Block block = Block.func_149684_b(jsonElement.getAsString());
            if (xRay.getXrayBlocks().contains(block)) {
               ClientUtils.INSTANCE.logError("[FileManager] Skipped xray block '" + block.getRegistryName() + "' because the block is already added.");
            } else {
               List var8 = xRay.getXrayBlocks();
               Intrinsics.checkNotNullExpressionValue(block, "block");
               var8.add(block);
            }
         } catch (Throwable throwable) {
            ClientUtils.INSTANCE.logError("[FileManager] Failed to add block to xray.", throwable);
         }
      }

   }

   @NotNull
   public String saveConfig() {
      Module var10000 = CrossSine.INSTANCE.getModuleManager().get(XRay.class);
      Intrinsics.checkNotNull(var10000);
      XRay xRay = (XRay)var10000;
      JsonArray jsonArray = new JsonArray();

      for(Block block : xRay.getXrayBlocks()) {
         jsonArray.add(FileManager.Companion.getPRETTY_GSON().toJsonTree(Block.func_149682_b(block)));
      }

      String var5 = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)jsonArray);
      Intrinsics.checkNotNullExpressionValue(var5, "FileManager.PRETTY_GSON.toJson(jsonArray)");
      return var5;
   }
}
