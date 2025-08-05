package me.liuli.elixir.compat;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.MicrosoftAccount;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"Lme/liuli/elixir/compat/OAuthServer;", "", "handler", "Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "authMethod", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "httpServer", "Lcom/sun/net/httpserver/HttpServer;", "context", "", "(Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;Lcom/sun/net/httpserver/HttpServer;Ljava/lang/String;)V", "getHandler", "()Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "threadPoolExecutor", "Ljava/util/concurrent/ThreadPoolExecutor;", "start", "", "stop", "isInterrupt", "", "OAuthHttpHandler", "Elixir"}
)
public final class OAuthServer {
   @NotNull
   private final MicrosoftAccount.OAuthHandler handler;
   @NotNull
   private final MicrosoftAccount.AuthMethod authMethod;
   @NotNull
   private final HttpServer httpServer;
   @NotNull
   private final String context;
   @NotNull
   private final ThreadPoolExecutor threadPoolExecutor;

   public OAuthServer(@NotNull MicrosoftAccount.OAuthHandler handler, @NotNull MicrosoftAccount.AuthMethod authMethod, @NotNull HttpServer httpServer, @NotNull String context) {
      Intrinsics.checkNotNullParameter(handler, "handler");
      Intrinsics.checkNotNullParameter(authMethod, "authMethod");
      Intrinsics.checkNotNullParameter(httpServer, "httpServer");
      Intrinsics.checkNotNullParameter(context, "context");
      super();
      this.handler = handler;
      this.authMethod = authMethod;
      this.httpServer = httpServer;
      this.context = context;
      ExecutorService var10001 = Executors.newFixedThreadPool(10);
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type java.util.concurrent.ThreadPoolExecutor");
      } else {
         this.threadPoolExecutor = (ThreadPoolExecutor)var10001;
      }
   }

   // $FF: synthetic method
   public OAuthServer(MicrosoftAccount.OAuthHandler var1, MicrosoftAccount.AuthMethod var2, HttpServer var3, String var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 2) != 0) {
         var2 = MicrosoftAccount.AuthMethod.Companion.getAZURE_APP();
      }

      if ((var5 & 4) != 0) {
         HttpServer var7 = HttpServer.create(new InetSocketAddress("localhost", 1919), 0);
         Intrinsics.checkNotNullExpressionValue(var7, "create(InetSocketAddress(\"localhost\", 1919), 0)");
         var3 = var7;
      }

      if ((var5 & 8) != 0) {
         var4 = "/login";
      }

      this(var1, var2, var3, var4);
   }

   @NotNull
   public final MicrosoftAccount.OAuthHandler getHandler() {
      return this.handler;
   }

   public final void start() {
      this.httpServer.createContext(this.context, new OAuthHttpHandler(this, this.authMethod));
      this.httpServer.setExecutor((Executor)this.threadPoolExecutor);
      this.httpServer.start();
      this.handler.openUrl(MicrosoftAccount.Companion.replaceKeys(this.authMethod, "https://login.live.com/oauth20_authorize.srf?client_id=<client_id>&redirect_uri=<redirect_uri>&response_type=code&display=touch&scope=<scope>&prompt=select_account"));
   }

   public final void stop(boolean isInterrupt) {
      this.httpServer.stop(0);
      this.threadPoolExecutor.shutdown();
      if (isInterrupt) {
         this.handler.authError("Has been interrupted");
      }

   }

   // $FF: synthetic method
   public static void stop$default(OAuthServer var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = true;
      }

      var0.stop(var1);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
      d2 = {"Lme/liuli/elixir/compat/OAuthServer$OAuthHttpHandler;", "Lcom/sun/net/httpserver/HttpHandler;", "server", "Lme/liuli/elixir/compat/OAuthServer;", "authMethod", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "(Lme/liuli/elixir/compat/OAuthServer;Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;)V", "handle", "", "exchange", "Lcom/sun/net/httpserver/HttpExchange;", "response", "message", "", "code", "", "Elixir"}
   )
   public static final class OAuthHttpHandler implements HttpHandler {
      @NotNull
      private final OAuthServer server;
      @NotNull
      private final MicrosoftAccount.AuthMethod authMethod;

      public OAuthHttpHandler(@NotNull OAuthServer server, @NotNull MicrosoftAccount.AuthMethod authMethod) {
         Intrinsics.checkNotNullParameter(server, "server");
         Intrinsics.checkNotNullParameter(authMethod, "authMethod");
         super();
         this.server = server;
         this.authMethod = authMethod;
      }

      public void handle(@NotNull HttpExchange exchange) {
         Intrinsics.checkNotNullParameter(exchange, "exchange");
         String var3 = exchange.getRequestURI().getQuery();
         Intrinsics.checkNotNullExpressionValue(var3, "exchange.requestURI.query");
         CharSequence var10000 = (CharSequence)var3;
         var3 = new String[]{"&"};
         Iterable $this$map$iv = (Iterable)StringsKt.split$default(var10000, var3, false, 0, 6, (Object)null);
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            String it = (String)item$iv$iv;
            int var11 = 0;
            var10000 = (CharSequence)it;
            String[] var12 = new String[]{"="};
            destination$iv$iv.add(StringsKt.split$default(var10000, var12, false, 0, 6, (Object)null));
         }

         $this$map$iv = (Iterable)((List)destination$iv$iv);
         $i$f$map = 0;
         int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)), 16);
         Map destination$iv$iv = (Map)(new LinkedHashMap(capacity$iv));
         int $i$f$associateTo = 0;

         for(Object element$iv$iv : $this$map$iv) {
            List it = (List)element$iv$iv;
            int var13 = 0;
            Pair var27 = TuplesKt.to(it.get(0), it.get(1));
            destination$iv$iv.put(var27.getFirst(), var27.getSecond());
         }

         Map query = destination$iv$iv;
         if (destination$iv$iv.containsKey("code")) {
            try {
               MicrosoftAccount.OAuthHandler var29 = this.server.getHandler();
               MicrosoftAccount.Companion var10001 = MicrosoftAccount.Companion;
               Object var10002 = query.get("code");
               Intrinsics.checkNotNull(var10002);
               var29.authResult(var10001.buildFromAuthCode((String)var10002, this.authMethod));
               this.response(exchange, "Login Success", 200);
            } catch (FileNotFoundException var15) {
               String errorMessage = "No minecraft account associated with this Microsoft account. Please check your account and try again.";
               this.server.getHandler().authError(errorMessage);
               this.response(exchange, Intrinsics.stringPlus("Error: ", errorMessage), 500);
            } catch (Exception e) {
               this.server.getHandler().authError(e.toString());
               this.response(exchange, Intrinsics.stringPlus("Error: ", e), 500);
            }
         } else {
            this.server.getHandler().authError("No code in the query");
            this.response(exchange, "No code in the query", 500);
         }

         this.server.stop(false);
      }

      private final void response(HttpExchange exchange, String message, int code) {
         byte[] var4_1 = message.getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var4_1, "this as java.lang.String).getBytes(charset)");
         exchange.sendResponseHeaders(code, (long)var4_1.length);
         exchange.getResponseBody().write(var4_1);
         exchange.close();
      }
   }
}
