package net.ccbluex.liquidbounce.file.configs;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.file.FileConfig;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\rH\u0007J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\rH\u0016J\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u0015\u001a\u00020\rH\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0017"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "subscripts", "", "Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig$Subscript;", "getSubscripts", "()Ljava/util/List;", "addSubscripts", "", "url", "", "name", "clearSubscripts", "", "isSubscript", "loadConfig", "config", "removeSubscript", "saveConfig", "Subscript", "CrossSine"}
)
public final class ScriptConfig extends FileConfig {
   @NotNull
   private final List subscripts;

   public ScriptConfig(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      super(file);
      this.subscripts = (List)(new ArrayList());
   }

   @NotNull
   public final List getSubscripts() {
      return this.subscripts;
   }

   public void loadConfig(@NotNull String config) {
      Intrinsics.checkNotNullParameter(config, "config");
      this.clearSubscripts();
      CharSequence var10000 = (CharSequence)config;
      String[] var2 = new String[]{"\n"};
      Iterable $this$forEach$iv = (Iterable)StringsKt.split$default(var10000, var2, false, 0, 6, (Object)null);
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         String line = (String)element$iv;
         int var7 = 0;
         if (StringsKt.contains$default((CharSequence)line, (CharSequence)":", false, 2, (Object)null)) {
            var10000 = (CharSequence)line;
            String[] thisCollection$iv = new String[]{":"};
            Collection $this$toTypedArray$iv = (Collection)StringsKt.split$default(var10000, thisCollection$iv, false, 0, 6, (Object)null);
            int $i$f$toTypedArray = 0;
            Object[] var21 = $this$toTypedArray$iv.toArray(new String[0]);
            if (var21 == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }

            String[] data = (String[])var21;
            byte[] thisCollection$iv = Base64.getDecoder().decode(data[0]);
            Intrinsics.checkNotNullExpressionValue(thisCollection$iv, "getDecoder().decode(data[0])");
            Charset var17 = Charset.defaultCharset();
            Intrinsics.checkNotNullExpressionValue(var17, "defaultCharset()");
            String var10001 = new String(thisCollection$iv, var17);
            thisCollection$iv = Base64.getDecoder().decode(data[1]);
            Intrinsics.checkNotNullExpressionValue(thisCollection$iv, "getDecoder().decode(data[1])");
            var17 = Charset.defaultCharset();
            Intrinsics.checkNotNullExpressionValue(var17, "defaultCharset()");
            this.addSubscripts(var10001, new String(thisCollection$iv, var17));
         } else {
            byte[] var19 = Base64.getDecoder().decode(line);
            Intrinsics.checkNotNullExpressionValue(var19, "getDecoder().decode(line)");
            Charset thisCollection$iv = Charset.defaultCharset();
            Intrinsics.checkNotNullExpressionValue(thisCollection$iv, "defaultCharset()");
            new String(var19, thisCollection$iv);
         }
      }

   }

   @NotNull
   public String saveConfig() {
      StringBuilder builder = new StringBuilder();

      for(Subscript subscript : this.subscripts) {
         Base64.Encoder var10001 = Base64.getEncoder();
         String var4 = subscript.getUrl();
         byte[] var5 = var4.getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).getBytes(charset)");
         StringBuilder var10000 = builder.append(var10001.encode(var5)).append(":");
         var10001 = Base64.getEncoder();
         var4 = subscript.getName();
         var5 = var4.getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).getBytes(charset)");
         var10000.append(var10001.encode(var5)).append("\n");
      }

      String var6 = builder.toString();
      Intrinsics.checkNotNullExpressionValue(var6, "builder.toString()");
      return var6;
   }

   @JvmOverloads
   public final boolean addSubscripts(@NotNull String url, @NotNull String name) {
      Intrinsics.checkNotNullParameter(url, "url");
      Intrinsics.checkNotNullParameter(name, "name");
      if (this.isSubscript(url)) {
         return false;
      } else {
         this.subscripts.add(new Subscript(url, name));
         return true;
      }
   }

   // $FF: synthetic method
   public static boolean addSubscripts$default(ScriptConfig var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var1;
      }

      return var0.addSubscripts(var1, var2);
   }

   public final boolean removeSubscript(@NotNull String url) {
      Intrinsics.checkNotNullParameter(url, "url");
      if (!this.isSubscript(url)) {
         return false;
      } else {
         this.subscripts.removeIf(ScriptConfig::removeSubscript$lambda-1);
         return true;
      }
   }

   public final boolean isSubscript(@NotNull String url) {
      Intrinsics.checkNotNullParameter(url, "url");

      for(Subscript subscript : this.subscripts) {
         if (Intrinsics.areEqual((Object)subscript.getUrl(), (Object)url)) {
            return true;
         }
      }

      return false;
   }

   public final void clearSubscripts() {
      this.subscripts.clear();
   }

   @JvmOverloads
   public final boolean addSubscripts(@NotNull String url) {
      Intrinsics.checkNotNullParameter(url, "url");
      return addSubscripts$default(this, url, (String)null, 2, (Object)null);
   }

   private static final boolean removeSubscript$lambda_1/* $FF was: removeSubscript$lambda-1*/(String $url, Subscript friend) {
      Intrinsics.checkNotNullParameter($url, "$url");
      Intrinsics.checkNotNullParameter(friend, "friend");
      return Intrinsics.areEqual((Object)friend.getUrl(), (Object)$url);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"},
      d2 = {"Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig$Subscript;", "", "url", "", "name", "(Ljava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "getUrl", "CrossSine"}
   )
   public static final class Subscript {
      @NotNull
      private final String url;
      @NotNull
      private final String name;

      public Subscript(@NotNull String url, @NotNull String name) {
         Intrinsics.checkNotNullParameter(url, "url");
         Intrinsics.checkNotNullParameter(name, "name");
         super();
         this.url = url;
         this.name = name;
      }

      @NotNull
      public final String getUrl() {
         return this.url;
      }

      @NotNull
      public final String getName() {
         return this.name;
      }
   }
}
