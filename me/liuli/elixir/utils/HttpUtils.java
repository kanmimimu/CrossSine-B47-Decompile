package me.liuli.elixir.utils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\bJ@\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b2\b\b\u0002\u0010\r\u001a\u00020\u0004J,\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\bJ@\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b2\b\b\u0002\u0010\r\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lme/liuli/elixir/utils/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", "get", "url", "header", "", "make", "Ljava/net/HttpURLConnection;", "method", "data", "agent", "post", "request", "Elixir"}
)
public final class HttpUtils {
   @NotNull
   public static final HttpUtils INSTANCE = new HttpUtils();
   @NotNull
   private static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";

   private HttpUtils() {
   }

   @NotNull
   public final HttpURLConnection make(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull Map header, @NotNull String agent) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(method, "method");
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(header, "header");
      Intrinsics.checkNotNullParameter(agent, "agent");
      URLConnection var10000 = (new URL(url)).openConnection();
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
      } else {
         HttpURLConnection httpConnection = (HttpURLConnection)var10000;
         httpConnection.setRequestMethod(method);
         httpConnection.setConnectTimeout(2000);
         httpConnection.setReadTimeout(10000);
         httpConnection.setRequestProperty("User-Agent", agent);
         int $i$f$forEach = 0;

         for(Map.Entry element$iv : header.entrySet()) {
            int var12 = 0;
            String key = (String)element$iv.getKey();
            String value = (String)element$iv.getValue();
            httpConnection.setRequestProperty(key, value);
         }

         httpConnection.setInstanceFollowRedirects(true);
         httpConnection.setDoOutput(true);
         if (((CharSequence)data).length() > 0) {
            DataOutputStream dataOutputStream = new DataOutputStream(httpConnection.getOutputStream());
            dataOutputStream.writeBytes(data);
            dataOutputStream.flush();
         }

         httpConnection.connect();
         return httpConnection;
      }
   }

   // $FF: synthetic method
   public static HttpURLConnection make$default(HttpUtils var0, String var1, String var2, String var3, Map var4, String var5, int var6, Object var7) {
      if ((var6 & 4) != 0) {
         var3 = "";
      }

      if ((var6 & 8) != 0) {
         var4 = MapsKt.emptyMap();
      }

      if ((var6 & 16) != 0) {
         var5 = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
      }

      return var0.make(var1, var2, var3, var4, var5);
   }

   @NotNull
   public final String request(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull Map header, @NotNull String agent) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(method, "method");
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(header, "header");
      Intrinsics.checkNotNullParameter(agent, "agent");
      HttpURLConnection connection = this.make(url, method, data, header, agent);
      InputStream var7 = connection.getInputStream();
      Intrinsics.checkNotNullExpressionValue(var7, "connection.inputStream");
      Charset var8 = Charsets.UTF_8;
      return TextStreamsKt.readText((Reader)(new InputStreamReader(var7, var8)));
   }

   // $FF: synthetic method
   public static String request$default(HttpUtils var0, String var1, String var2, String var3, Map var4, String var5, int var6, Object var7) {
      if ((var6 & 4) != 0) {
         var3 = "";
      }

      if ((var6 & 8) != 0) {
         var4 = MapsKt.emptyMap();
      }

      if ((var6 & 16) != 0) {
         var5 = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
      }

      return var0.request(var1, var2, var3, var4, var5);
   }

   @NotNull
   public final String get(@NotNull String url, @NotNull Map header) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(header, "header");
      return request$default(this, url, "GET", (String)null, header, (String)null, 20, (Object)null);
   }

   // $FF: synthetic method
   public static String get$default(HttpUtils var0, String var1, Map var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = MapsKt.emptyMap();
      }

      return var0.get(var1, var2);
   }

   @NotNull
   public final String post(@NotNull String url, @NotNull String data, @NotNull Map header) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(header, "header");
      return request$default(this, url, "POST", data, header, (String)null, 16, (Object)null);
   }

   // $FF: synthetic method
   public static String post$default(HttpUtils var0, String var1, String var2, Map var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = MapsKt.emptyMap();
      }

      return var0.post(var1, var2, var3);
   }
}
