package net.ccbluex.liquidbounce.utils.misc;

import com.google.common.io.ByteStreams;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J*\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u0014J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u0004J\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004J*\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0004J\"\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0019"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", "download", "", "url", "file", "Ljava/io/File;", "get", "make", "Ljava/net/HttpURLConnection;", "method", "data", "agent", "openWebpage", "", "uri", "Ljava/net/URI;", "Ljava/net/URL;", "post", "request", "requestStream", "Ljava/io/InputStream;", "CrossSine"}
)
public final class HttpUtils {
   @NotNull
   public static final HttpUtils INSTANCE = new HttpUtils();
   @NotNull
   public static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.7113.93 Safari/537.36 Java/1.8.0_191";

   private HttpUtils() {
   }

   @NotNull
   public final HttpURLConnection make(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull String agent) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(method, "method");
      Intrinsics.checkNotNullParameter(data, "data");
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
         httpConnection.setInstanceFollowRedirects(true);
         httpConnection.setDoOutput(true);
         if (((CharSequence)data).length() > 0) {
            DataOutputStream dataOutputStream = new DataOutputStream(httpConnection.getOutputStream());
            dataOutputStream.writeBytes(data);
            dataOutputStream.flush();
         }

         return httpConnection;
      }
   }

   // $FF: synthetic method
   public static HttpURLConnection make$default(HttpUtils var0, String var1, String var2, String var3, String var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = "";
      }

      if ((var5 & 8) != 0) {
         var4 = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.7113.93 Safari/537.36 Java/1.8.0_191";
      }

      return var0.make(var1, var2, var3, var4);
   }

   @NotNull
   public final String request(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull String agent) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(method, "method");
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(agent, "agent");
      HttpURLConnection connection = this.make(url, method, data, agent);
      InputStream var6 = connection.getInputStream();
      Intrinsics.checkNotNullExpressionValue(var6, "connection.inputStream");
      Charset var7 = Charsets.UTF_8;
      return TextStreamsKt.readText((Reader)(new InputStreamReader(var6, var7)));
   }

   // $FF: synthetic method
   public static String request$default(HttpUtils var0, String var1, String var2, String var3, String var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = "";
      }

      if ((var5 & 8) != 0) {
         var4 = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.7113.93 Safari/537.36 Java/1.8.0_191";
      }

      return var0.request(var1, var2, var3, var4);
   }

   @Nullable
   public final InputStream requestStream(@NotNull String url, @NotNull String method, @NotNull String agent) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(method, "method");
      Intrinsics.checkNotNullParameter(agent, "agent");
      HttpURLConnection connection = make$default(this, url, method, agent, (String)null, 8, (Object)null);
      return connection.getInputStream();
   }

   // $FF: synthetic method
   public static InputStream requestStream$default(HttpUtils var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.7113.93 Safari/537.36 Java/1.8.0_191";
      }

      return var0.requestStream(var1, var2, var3);
   }

   public final boolean openWebpage(@NotNull String url) {
      Intrinsics.checkNotNullParameter(url, "url");

      try {
         URL linkURL = null;
         linkURL = new URL(url);
         return this.openWebpage(linkURL.toURI());
      } catch (URISyntaxException e) {
         e.printStackTrace();
      } catch (MalformedURLException e) {
         e.printStackTrace();
      }

      return false;
   }

   public final boolean openWebpage(@NotNull URL url) {
      Intrinsics.checkNotNullParameter(url, "url");

      try {
         return this.openWebpage(url.toURI());
      } catch (URISyntaxException e) {
         e.printStackTrace();
         return false;
      }
   }

   public final boolean openWebpage(@Nullable URI uri) {
      Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
      if (desktop != null && desktop.isSupported(Action.BROWSE)) {
         try {
            desktop.browse(uri);
            return true;
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      return false;
   }

   public final void download(@NotNull String url, @NotNull File file) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(file, "file");
      ClientUtils.INSTANCE.logWarn("Downloading " + url + " to " + file.getAbsolutePath());
      Closeable var3 = (Closeable)(new FileOutputStream(file));
      Throwable var4 = null;

      try {
         FileOutputStream it = (FileOutputStream)var3;
         int var7 = 0;
         long var12 = ByteStreams.copy(make$default(INSTANCE, url, "GET", (String)null, (String)null, 12, (Object)null).getInputStream(), (OutputStream)it);
      } catch (Throwable var10) {
         var4 = var10;
         throw var10;
      } finally {
         CloseableKt.closeFinally(var3, var4);
      }

   }

   @NotNull
   public final String get(@NotNull String url) {
      Intrinsics.checkNotNullParameter(url, "url");
      return request$default(this, url, "GET", (String)null, (String)null, 12, (Object)null);
   }

   @NotNull
   public final String post(@NotNull String url, @NotNull String data) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(data, "data");
      return request$default(this, url, "POST", data, (String)null, 8, (Object)null);
   }

   static {
      HttpURLConnection.setFollowRedirects(true);
   }
}
