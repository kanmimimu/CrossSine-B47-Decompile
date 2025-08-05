package me.liuli.elixir.account;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import me.liuli.elixir.compat.OAuthServer;
import me.liuli.elixir.compat.Session;
import me.liuli.elixir.exception.LoginException;
import me.liuli.elixir.utils.GsonExtensionKt;
import me.liuli.elixir.utils.HttpUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00192\u00020\u0001:\u0003\u0018\u0019\u001aB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lme/liuli/elixir/account/MicrosoftAccount;", "Lme/liuli/elixir/account/MinecraftAccount;", "()V", "accessToken", "", "authMethod", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "name", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "refreshToken", "session", "Lme/liuli/elixir/compat/Session;", "getSession", "()Lme/liuli/elixir/compat/Session;", "uuid", "fromRawJson", "", "json", "Lcom/google/gson/JsonObject;", "toRawJson", "update", "AuthMethod", "Companion", "OAuthHandler", "Elixir"}
)
public final class MicrosoftAccount extends MinecraftAccount {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private String name = "";
   @NotNull
   private String uuid = "";
   @NotNull
   private String accessToken = "";
   @NotNull
   private String refreshToken = "";
   @NotNull
   private AuthMethod authMethod;
   @NotNull
   public static final String XBOX_PRE_AUTH_URL = "https://login.live.com/oauth20_authorize.srf?client_id=<client_id>&redirect_uri=<redirect_uri>&response_type=code&display=touch&scope=<scope>&prompt=select_account";
   @NotNull
   public static final String XBOX_AUTH_URL = "https://login.live.com/oauth20_token.srf";
   @NotNull
   public static final String XBOX_XBL_URL = "https://user.auth.xboxlive.com/user/authenticate";
   @NotNull
   public static final String XBOX_XSTS_URL = "https://xsts.auth.xboxlive.com/xsts/authorize";
   @NotNull
   public static final String MC_AUTH_URL = "https://api.minecraftservices.com/authentication/login_with_xbox";
   @NotNull
   public static final String MC_PROFILE_URL = "https://api.minecraftservices.com/minecraft/profile";
   @NotNull
   public static final String XBOX_AUTH_DATA = "client_id=<client_id>&redirect_uri=<redirect_uri>&grant_type=authorization_code&code=";
   @NotNull
   public static final String XBOX_REFRESH_DATA = "client_id=<client_id>&scope=<scope>&grant_type=refresh_token&redirect_uri=<redirect_uri>&refresh_token=";
   @NotNull
   public static final String XBOX_XBL_DATA = "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"<rps_ticket>\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}";
   @NotNull
   public static final String XBOX_XSTS_DATA = "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"<xbl_token>\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}";
   @NotNull
   public static final String MC_AUTH_DATA = "{\"identityToken\":\"XBL3.0 x=<userhash>;<xsts_token>\"}";

   public MicrosoftAccount() {
      super("Microsoft");
      this.authMethod = MicrosoftAccount.AuthMethod.Companion.getMICROSOFT();
   }

   @NotNull
   public String getName() {
      return this.name;
   }

   public void setName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.name = var1;
   }

   @NotNull
   public Session getSession() {
      if (((CharSequence)this.uuid).length() == 0 || ((CharSequence)this.accessToken).length() == 0) {
         this.update();
      }

      return new Session(this.getName(), this.uuid, this.accessToken, "mojang");
   }

   public void update() {
      Pair[] var2 = new Pair[]{TuplesKt.to("Content-Type", "application/json"), TuplesKt.to("Accept", "application/json")};
      Map jsonPostHeader = MapsKt.mapOf(var2);
      JsonParser var10000 = new JsonParser();
      InputStream var3 = HttpUtils.make$default(HttpUtils.INSTANCE, "https://login.live.com/oauth20_token.srf", "POST", Intrinsics.stringPlus(Companion.replaceKeys(this.authMethod, "client_id=<client_id>&scope=<scope>&grant_type=refresh_token&redirect_uri=<redirect_uri>&refresh_token="), this.refreshToken), MapsKt.mapOf(TuplesKt.to("Content-Type", "application/x-www-form-urlencoded")), (String)null, 16, (Object)null).getInputStream();
      Intrinsics.checkNotNullExpressionValue(var3, "HttpUtils.make(\n        …urlencoded\")).inputStream");
      JsonObject msRefreshJson = var10000.parse((Reader)(new InputStreamReader(var3, Charsets.UTF_8))).getAsJsonObject();
      Intrinsics.checkNotNullExpressionValue(msRefreshJson, "msRefreshJson");
      String var17 = GsonExtensionKt.string(msRefreshJson, "access_token");
      if (var17 == null) {
         throw new LoginException("Microsoft access token is null");
      } else {
         String msAccessToken = var17;
         String var10001 = GsonExtensionKt.string(msRefreshJson, "refresh_token");
         if (var10001 == null) {
            throw new LoginException("Microsoft new refresh token is null");
         } else {
            this.refreshToken = var10001;
            JsonParser var18 = new JsonParser();
            InputStream var5 = HttpUtils.make$default(HttpUtils.INSTANCE, "https://user.auth.xboxlive.com/user/authenticate", "POST", StringsKt.replace$default("{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"<rps_ticket>\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}", "<rps_ticket>", StringsKt.replace$default(this.authMethod.getRpsTicketRule(), "<access_token>", msAccessToken, false, 4, (Object)null), false, 4, (Object)null), jsonPostHeader, (String)null, 16, (Object)null).getInputStream();
            Intrinsics.checkNotNullExpressionValue(var5, "HttpUtils.make(XBOX_XBL_…onPostHeader).inputStream");
            JsonObject xblJson = var18.parse((Reader)(new InputStreamReader(var5, Charsets.UTF_8))).getAsJsonObject();
            Intrinsics.checkNotNullExpressionValue(xblJson, "xblJson");
            String var19 = GsonExtensionKt.string(xblJson, "Token");
            if (var19 == null) {
               throw new LoginException("Microsoft XBL token is null");
            } else {
               String xblToken = var19;
               JsonObject var20 = GsonExtensionKt.obj(xblJson, "DisplayClaims");
               String var21;
               if (var20 == null) {
                  var21 = null;
               } else {
                  JsonArray var22 = GsonExtensionKt.array(var20, "xui");
                  if (var22 == null) {
                     var21 = null;
                  } else {
                     JsonElement var23 = var22.get(0);
                     if (var23 == null) {
                        var21 = null;
                     } else {
                        JsonObject var24 = var23.getAsJsonObject();
                        var21 = var24 == null ? null : GsonExtensionKt.string(var24, "uhs");
                     }
                  }
               }

               if (var21 == null) {
                  throw new LoginException("Microsoft XBL userhash is null");
               } else {
                  String userhash = var21;
                  JsonParser var25 = new JsonParser();
                  InputStream var8 = HttpUtils.make$default(HttpUtils.INSTANCE, "https://xsts.auth.xboxlive.com/xsts/authorize", "POST", StringsKt.replace$default("{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"<xbl_token>\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}", "<xbl_token>", xblToken, false, 4, (Object)null), jsonPostHeader, (String)null, 16, (Object)null).getInputStream();
                  Intrinsics.checkNotNullExpressionValue(var8, "HttpUtils.make(XBOX_XSTS…onPostHeader).inputStream");
                  JsonObject xstsJson = var25.parse((Reader)(new InputStreamReader(var8, Charsets.UTF_8))).getAsJsonObject();
                  Intrinsics.checkNotNullExpressionValue(xstsJson, "xstsJson");
                  String var26 = GsonExtensionKt.string(xstsJson, "Token");
                  if (var26 == null) {
                     throw new LoginException("Microsoft XSTS token is null");
                  } else {
                     String xstsToken = var26;
                     JsonParser var27 = new JsonParser();
                     InputStream var10 = HttpUtils.make$default(HttpUtils.INSTANCE, "https://api.minecraftservices.com/authentication/login_with_xbox", "POST", StringsKt.replace$default(StringsKt.replace$default("{\"identityToken\":\"XBL3.0 x=<userhash>;<xsts_token>\"}", "<userhash>", userhash, false, 4, (Object)null), "<xsts_token>", xstsToken, false, 4, (Object)null), jsonPostHeader, (String)null, 16, (Object)null).getInputStream();
                     Intrinsics.checkNotNullExpressionValue(var10, "HttpUtils.make(MC_AUTH_U…onPostHeader).inputStream");
                     JsonObject mcJson = var27.parse((Reader)(new InputStreamReader(var10, Charsets.UTF_8))).getAsJsonObject();
                     Intrinsics.checkNotNullExpressionValue(mcJson, "mcJson");
                     var10001 = GsonExtensionKt.string(mcJson, "access_token");
                     if (var10001 == null) {
                        throw new LoginException("Minecraft access token is null");
                     } else {
                        this.accessToken = var10001;
                        var27 = new JsonParser();
                        InputStream var11 = HttpUtils.make$default(HttpUtils.INSTANCE, "https://api.minecraftservices.com/minecraft/profile", "GET", "", MapsKt.mapOf(TuplesKt.to("Authorization", Intrinsics.stringPlus("Bearer ", this.accessToken))), (String)null, 16, (Object)null).getInputStream();
                        Intrinsics.checkNotNullExpressionValue(var11, "HttpUtils.make(MC_PROFIL…ccessToken\")).inputStream");
                        JsonObject mcProfileJson = var27.parse((Reader)(new InputStreamReader(var11, Charsets.UTF_8))).getAsJsonObject();
                        Intrinsics.checkNotNullExpressionValue(mcProfileJson, "mcProfileJson");
                        var10001 = GsonExtensionKt.string(mcProfileJson, "name");
                        if (var10001 == null) {
                           throw new LoginException("Minecraft account name is null");
                        } else {
                           this.setName(var10001);
                           var10001 = GsonExtensionKt.string(mcProfileJson, "id");
                           if (var10001 == null) {
                              throw new LoginException("Minecraft account uuid is null");
                           } else {
                              this.uuid = var10001;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void toRawJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      GsonExtensionKt.set(json, "name", this.getName());
      GsonExtensionKt.set(json, "refreshToken", this.refreshToken);
      Map $this$filterValues$iv = MicrosoftAccount.AuthMethod.Companion.getRegistry();
      String var10 = "authMethod";
      int $i$f$filterValues = 0;
      LinkedHashMap result$iv = new LinkedHashMap();

      for(Map.Entry entry$iv : $this$filterValues$iv.entrySet()) {
         AuthMethod it = (AuthMethod)entry$iv.getValue();
         int var8 = 0;
         if (Intrinsics.areEqual((Object)it, (Object)this.authMethod)) {
            result$iv.put(entry$iv.getKey(), entry$iv.getValue());
         }
      }

      Map var11 = (Map)result$iv;
      String var10002 = (String)CollectionsKt.firstOrNull((Iterable)var11.keySet());
      if (var10002 == null) {
         throw new LoginException("Unregistered auth method");
      } else {
         GsonExtensionKt.set(json, var10, var10002);
      }
   }

   public void fromRawJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      String var10001 = GsonExtensionKt.string(json, "name");
      Intrinsics.checkNotNull(var10001);
      this.setName(var10001);
      var10001 = GsonExtensionKt.string(json, "refreshToken");
      Intrinsics.checkNotNull(var10001);
      this.refreshToken = var10001;
      Map var3 = MicrosoftAccount.AuthMethod.Companion.getRegistry();
      String var10002 = GsonExtensionKt.string(json, "authMethod");
      Intrinsics.checkNotNull(var10002);
      AuthMethod var4 = (AuthMethod)var3.get(var10002);
      if (var4 == null) {
         throw new LoginException("Unregistered auth method");
      } else {
         this.authMethod = var4;
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0013J \u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\b\b\u0002\u0010\u0018\u001a\u00020\u0013J\u0016\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001e"},
      d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$Companion;", "", "()V", "MC_AUTH_DATA", "", "MC_AUTH_URL", "MC_PROFILE_URL", "XBOX_AUTH_DATA", "XBOX_AUTH_URL", "XBOX_PRE_AUTH_URL", "XBOX_REFRESH_DATA", "XBOX_XBL_DATA", "XBOX_XBL_URL", "XBOX_XSTS_DATA", "XBOX_XSTS_URL", "buildFromAuthCode", "Lme/liuli/elixir/account/MicrosoftAccount;", "code", "method", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "buildFromOpenBrowser", "Lme/liuli/elixir/compat/OAuthServer;", "handler", "Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "authMethod", "buildFromPassword", "username", "password", "replaceKeys", "string", "Elixir"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final MicrosoftAccount buildFromAuthCode(@NotNull String code, @NotNull AuthMethod method) {
         Intrinsics.checkNotNullParameter(code, "code");
         Intrinsics.checkNotNullParameter(method, "method");
         JsonParser var10000 = new JsonParser();
         InputStream it = HttpUtils.make$default(HttpUtils.INSTANCE, "https://login.live.com/oauth20_token.srf", "POST", Intrinsics.stringPlus(this.replaceKeys(method, "client_id=<client_id>&redirect_uri=<redirect_uri>&grant_type=authorization_code&code="), code), MapsKt.mapOf(TuplesKt.to("Content-Type", "application/x-www-form-urlencoded")), (String)null, 16, (Object)null).getInputStream();
         Intrinsics.checkNotNullExpressionValue(it, "HttpUtils.make(XBOX_AUTH…urlencoded\")).inputStream");
         JsonObject data = var10000.parse((Reader)(new InputStreamReader(it, Charsets.UTF_8))).getAsJsonObject();
         if (data.has("refresh_token")) {
            MicrosoftAccount it = new MicrosoftAccount();
            int var6 = 0;
            Intrinsics.checkNotNullExpressionValue(data, "data");
            String var10001 = GsonExtensionKt.string(data, "refresh_token");
            Intrinsics.checkNotNull(var10001);
            it.refreshToken = var10001;
            it.authMethod = method;
            it.update();
            return it;
         } else {
            throw new LoginException("Failed to get refresh token");
         }
      }

      @NotNull
      public final MicrosoftAccount buildFromPassword(@NotNull String username, @NotNull String password, @NotNull AuthMethod authMethod) {
         Intrinsics.checkNotNullParameter(username, "username");
         Intrinsics.checkNotNullParameter(password, "password");
         Intrinsics.checkNotNullParameter(authMethod, "authMethod");
         HttpURLConnection preAuthConnection = HttpUtils.make$default(HttpUtils.INSTANCE, this.replaceKeys(authMethod, "https://login.live.com/oauth20_authorize.srf?client_id=<client_id>&redirect_uri=<redirect_uri>&response_type=code&display=touch&scope=<scope>&prompt=select_account"), "GET", (String)null, (Map)null, (String)null, 28, (Object)null);
         InputStream var6 = preAuthConnection.getInputStream();
         Intrinsics.checkNotNullExpressionValue(var6, "preAuthConnection.inputStream");
         Charset var7 = Charsets.UTF_8;
         String html = TextStreamsKt.readText((Reader)(new InputStreamReader(var6, var7)));
         List var10000 = (List)preAuthConnection.getHeaderFields().get("Set-Cookie");
         if (var10000 == null) {
            var10000 = CollectionsKt.emptyList();
         }

         String cookies = CollectionsKt.joinToString$default((Iterable)var10000, (CharSequence)";", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
         String urlPost = buildFromPassword$findArgs(html, "urlPost");
         String it = buildFromPassword$findArgs(html, "sFTTag");
         int var11 = 0;
         String it = it.substring(StringsKt.indexOf$default((CharSequence)it, "value=\"", 0, false, 6, (Object)null) + 7, it.length() - 3);
         Intrinsics.checkNotNullExpressionValue(it, "this as java.lang.String…ing(startIndex, endIndex)");
         preAuthConnection.disconnect();
         HttpUtils var23 = HttpUtils.INSTANCE;
         String var10003 = "login=" + username + "&loginfmt=" + username + "&passwd=" + password + "&PPFT=" + it;
         Pair[] code = new Pair[]{TuplesKt.to("Cookie", cookies), TuplesKt.to("Content-Type", "application/x-www-form-urlencoded")};
         HttpURLConnection authConnection = HttpUtils.make$default(var23, urlPost, "POST", var10003, MapsKt.mapOf(code), (String)null, 16, (Object)null);
         InputStream var20 = authConnection.getInputStream();
         Intrinsics.checkNotNullExpressionValue(var20, "authConnection.inputStream");
         Charset var21 = Charsets.UTF_8;
         TextStreamsKt.readText((Reader)(new InputStreamReader(var20, var21)));
         it = authConnection.getURL().toString();
         int var13 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         if (!StringsKt.contains$default((CharSequence)it, (CharSequence)"code=", false, 2, (Object)null)) {
            throw new LoginException("Failed to get auth code from response");
         } else {
            String pre = it.substring(StringsKt.indexOf$default((CharSequence)it, "code=", 0, false, 6, (Object)null) + 5);
            Intrinsics.checkNotNullExpressionValue(pre, "this as java.lang.String).substring(startIndex)");
            String code = pre.substring(0, StringsKt.indexOf$default((CharSequence)pre, "&", 0, false, 6, (Object)null));
            Intrinsics.checkNotNullExpressionValue(code, "this as java.lang.String…ing(startIndex, endIndex)");
            authConnection.disconnect();
            return this.buildFromAuthCode(code, authMethod);
         }
      }

      // $FF: synthetic method
      public static MicrosoftAccount buildFromPassword$default(Companion var0, String var1, String var2, AuthMethod var3, int var4, Object var5) {
         if ((var4 & 4) != 0) {
            var3 = MicrosoftAccount.AuthMethod.Companion.getMICROSOFT();
         }

         return var0.buildFromPassword(var1, var2, var3);
      }

      @NotNull
      public final OAuthServer buildFromOpenBrowser(@NotNull OAuthHandler handler, @NotNull AuthMethod authMethod) {
         Intrinsics.checkNotNullParameter(handler, "handler");
         Intrinsics.checkNotNullParameter(authMethod, "authMethod");
         OAuthServer it = new OAuthServer(handler, authMethod, (HttpServer)null, (String)null, 12, (DefaultConstructorMarker)null);
         int var5 = 0;
         it.start();
         return it;
      }

      // $FF: synthetic method
      public static OAuthServer buildFromOpenBrowser$default(Companion var0, OAuthHandler var1, AuthMethod var2, int var3, Object var4) {
         if ((var3 & 2) != 0) {
            var2 = MicrosoftAccount.AuthMethod.Companion.getAZURE_APP();
         }

         return var0.buildFromOpenBrowser(var1, var2);
      }

      @NotNull
      public final String replaceKeys(@NotNull AuthMethod method, @NotNull String string) {
         Intrinsics.checkNotNullParameter(method, "method");
         Intrinsics.checkNotNullParameter(string, "string");
         return StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(string, "<client_id>", method.getClientId(), false, 4, (Object)null), "<redirect_uri>", method.getRedirectUri(), false, 4, (Object)null), "<scope>", method.getScope(), false, 4, (Object)null);
      }

      private static final String buildFromPassword$findArgs(String resp, String arg) {
         if (StringsKt.contains$default((CharSequence)resp, (CharSequence)arg, false, 2, (Object)null)) {
            String var3 = resp.substring(StringsKt.indexOf$default((CharSequence)resp, Intrinsics.stringPlus(arg, ":'"), 0, false, 6, (Object)null) + arg.length() + 2);
            Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).substring(startIndex)");
            String it = var3;
            int var5 = 0;
            String var4 = it.substring(0, StringsKt.indexOf$default((CharSequence)it, "',", 0, false, 6, (Object)null));
            Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String…ing(startIndex, endIndex)");
            return var4;
         } else {
            throw new LoginException(Intrinsics.stringPlus("Failed to find argument in response ", arg));
         }
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u0000 \r2\u00020\u0001:\u0001\rB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u000e"},
      d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "", "clientId", "", "redirectUri", "scope", "rpsTicketRule", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getClientId", "()Ljava/lang/String;", "getRedirectUri", "getRpsTicketRule", "getScope", "Companion", "Elixir"}
   )
   public static final class AuthMethod {
      @NotNull
      public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
      @NotNull
      private final String clientId;
      @NotNull
      private final String redirectUri;
      @NotNull
      private final String scope;
      @NotNull
      private final String rpsTicketRule;
      @NotNull
      private static final Map registry = (Map)(new LinkedHashMap());
      @NotNull
      private static final AuthMethod MICROSOFT = new AuthMethod("00000000441cc96b", "https://login.live.com/oauth20_desktop.srf", "service::user.auth.xboxlive.com::MBI_SSL", "<access_token>");
      @NotNull
      private static final AuthMethod AZURE_APP = new AuthMethod("0add8caf-2cc6-4546-b798-c3d171217dd9", "http://localhost:1919/login", "XboxLive.signin%20offline_access", "d=<access_token>");

      public AuthMethod(@NotNull String clientId, @NotNull String redirectUri, @NotNull String scope, @NotNull String rpsTicketRule) {
         Intrinsics.checkNotNullParameter(clientId, "clientId");
         Intrinsics.checkNotNullParameter(redirectUri, "redirectUri");
         Intrinsics.checkNotNullParameter(scope, "scope");
         Intrinsics.checkNotNullParameter(rpsTicketRule, "rpsTicketRule");
         super();
         this.clientId = clientId;
         this.redirectUri = redirectUri;
         this.scope = scope;
         this.rpsTicketRule = rpsTicketRule;
      }

      @NotNull
      public final String getClientId() {
         return this.clientId;
      }

      @NotNull
      public final String getRedirectUri() {
         return this.redirectUri;
      }

      @NotNull
      public final String getScope() {
         return this.scope;
      }

      @NotNull
      public final String getRpsTicketRule() {
         return this.rpsTicketRule;
      }

      static {
         registry.put("MICROSOFT", MICROSOFT);
         registry.put("AZURE_APP", AZURE_APP);
      }

      @Metadata(
         mv = {1, 6, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00040\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"},
         d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod$Companion;", "", "()V", "AZURE_APP", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "getAZURE_APP", "()Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "MICROSOFT", "getMICROSOFT", "registry", "", "", "getRegistry", "()Ljava/util/Map;", "Elixir"}
      )
      public static final class Companion {
         private Companion() {
         }

         @NotNull
         public final Map getRegistry() {
            return MicrosoftAccount.AuthMethod.registry;
         }

         @NotNull
         public final AuthMethod getMICROSOFT() {
            return MicrosoftAccount.AuthMethod.MICROSOFT;
         }

         @NotNull
         public final AuthMethod getAZURE_APP() {
            return MicrosoftAccount.AuthMethod.AZURE_APP;
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
         }
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0005H&¨\u0006\u000b"},
      d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "", "authError", "", "error", "", "authResult", "account", "Lme/liuli/elixir/account/MicrosoftAccount;", "openUrl", "url", "Elixir"}
   )
   public interface OAuthHandler {
      void openUrl(@NotNull String var1);

      void authResult(@NotNull MicrosoftAccount var1);

      void authError(@NotNull String var1);
   }
}
