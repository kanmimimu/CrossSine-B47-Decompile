package me.liuli.elixir.manage;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.CrackedAccount;
import me.liuli.elixir.account.MicrosoftAccount;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.account.MojangAccount;
import me.liuli.elixir.utils.GsonExtensionKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004¨\u0006\r"},
   d2 = {"Lme/liuli/elixir/manage/AccountSerializer;", "", "()V", "accountInstance", "Lme/liuli/elixir/account/MinecraftAccount;", "name", "", "password", "fromJson", "json", "Lcom/google/gson/JsonObject;", "toJson", "account", "Elixir"}
)
public final class AccountSerializer {
   @NotNull
   public static final AccountSerializer INSTANCE = new AccountSerializer();

   private AccountSerializer() {
   }

   @NotNull
   public final JsonObject toJson(@NotNull MinecraftAccount account) {
      Intrinsics.checkNotNullParameter(account, "account");
      JsonObject json = new JsonObject();
      account.toRawJson(json);
      String var3 = account.getClass().getCanonicalName();
      Intrinsics.checkNotNullExpressionValue(var3, "account.javaClass.canonicalName");
      GsonExtensionKt.set(json, "type", var3);
      return json;
   }

   @NotNull
   public final MinecraftAccount fromJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      String var10000 = GsonExtensionKt.string(json, "type");
      Intrinsics.checkNotNull(var10000);
      Object var3 = Class.forName(var10000).newInstance();
      if (var3 == null) {
         throw new NullPointerException("null cannot be cast to non-null type me.liuli.elixir.account.MinecraftAccount");
      } else {
         MinecraftAccount account = (MinecraftAccount)var3;
         account.fromRawJson(json);
         return account;
      }
   }

   @NotNull
   public final MinecraftAccount accountInstance(@NotNull String name, @NotNull String password) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(password, "password");
      MinecraftAccount var10000;
      if (StringsKt.startsWith$default(name, "ms@", false, 2, (Object)null)) {
         String realName = name.substring(3);
         Intrinsics.checkNotNullExpressionValue(realName, "this as java.lang.String).substring(startIndex)");
         var10000 = ((CharSequence)password).length() == 0 ? MicrosoftAccount.Companion.buildFromAuthCode(realName, MicrosoftAccount.AuthMethod.Companion.getMICROSOFT()) : MicrosoftAccount.Companion.buildFromPassword$default(MicrosoftAccount.Companion, realName, password, (MicrosoftAccount.AuthMethod)null, 4, (Object)null);
      } else if (((CharSequence)password).length() == 0) {
         CrackedAccount realName = new CrackedAccount();
         int var5 = 0;
         realName.setName(name);
         var10000 = realName;
      } else {
         MojangAccount it = new MojangAccount();
         int var8 = 0;
         it.setName(name);
         it.setPassword(password);
         var10000 = it;
      }

      return var10000;
   }
}
