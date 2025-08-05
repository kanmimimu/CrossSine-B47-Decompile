package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.manage.AccountSerializer;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000f"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/configs/AccountsConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "altManagerMinecraftAccounts", "", "Lme/liuli/elixir/account/MinecraftAccount;", "getAltManagerMinecraftAccounts", "()Ljava/util/List;", "loadConfig", "", "config", "", "saveConfig", "CrossSine"}
)
public final class AccountsConfig extends FileConfig {
   @NotNull
   private final List altManagerMinecraftAccounts;

   public AccountsConfig(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      super(file);
      this.altManagerMinecraftAccounts = (List)(new ArrayList());
   }

   @NotNull
   public final List getAltManagerMinecraftAccounts() {
      return this.altManagerMinecraftAccounts;
   }

   public void loadConfig(@NotNull String config) {
      Intrinsics.checkNotNullParameter(config, "config");
      this.altManagerMinecraftAccounts.clear();

      JsonArray json;
      try {
         json = (new JsonParser()).parse(config).getAsJsonArray();
      } catch (JsonSyntaxException var16) {
         JsonArray var5 = new JsonArray();
         JsonArray it = var5;
         int var7 = 0;
         CharSequence var10000 = (CharSequence)config;
         String[] var8 = new String[]{"\n"};
         Iterable $this$forEach$iv = (Iterable)StringsKt.split$default(var10000, var8, false, 0, 6, (Object)null);
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            String str = (String)element$iv;
            int var13 = 0;
            var10000 = (CharSequence)str;
            String[] var14 = new String[]{":"};
            List information = StringsKt.split$default(var10000, var14, false, 0, 6, (Object)null);
            it.add((JsonElement)AccountSerializer.INSTANCE.toJson(AccountSerializer.INSTANCE.accountInstance((String)information.get(0), (String)information.get(1))));
         }

         json = var5;
      }

      Intrinsics.checkNotNullExpressionValue(json, "json");
      Iterable $this$forEach$iv = (Iterable)json;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         JsonElement jsonElement = (JsonElement)element$iv;
         int var22 = 0;
         AccountSerializer var27 = AccountSerializer.INSTANCE;
         JsonObject it = jsonElement.getAsJsonObject();
         Intrinsics.checkNotNullExpressionValue(it, "jsonElement.asJsonObject");
         MinecraftAccount it = var27.fromJson(it);
         int var25 = 0;
         this.getAltManagerMinecraftAccounts().add(it);
      }

   }

   @NotNull
   public String saveConfig() {
      JsonArray json = new JsonArray();
      Iterable $this$forEach$iv = (Iterable)this.altManagerMinecraftAccounts;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         MinecraftAccount it = (MinecraftAccount)element$iv;
         int var7 = 0;
         json.add((JsonElement)AccountSerializer.INSTANCE.toJson(it));
      }

      String var8 = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)json);
      Intrinsics.checkNotNullExpressionValue(var8, "FileManager.PRETTY_GSON.toJson(json)");
      return var8;
   }
}
