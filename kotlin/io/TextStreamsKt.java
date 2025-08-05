package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\u0087\b\u001a8\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\u0086\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u001f\u0082\u0002\u000f\n\u0006\b\u0011(\u001e0\u0001\n\u0005\b\u009920\u0001¨\u0006 "},
   d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}
)
@JvmName(
   name = "TextStreamsKt"
)
public final class TextStreamsKt {
   @InlineOnly
   private static final BufferedReader buffered(Reader $this$buffered, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$buffered, "<this>");
      return $this$buffered instanceof BufferedReader ? (BufferedReader)$this$buffered : new BufferedReader($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedReader buffered$default(Reader $this$buffered_u24default, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
      return $this$buffered_u24default instanceof BufferedReader ? (BufferedReader)$this$buffered_u24default : new BufferedReader($this$buffered_u24default, bufferSize);
   }

   @InlineOnly
   private static final BufferedWriter buffered(Writer $this$buffered, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$buffered, "<this>");
      return $this$buffered instanceof BufferedWriter ? (BufferedWriter)$this$buffered : new BufferedWriter($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedWriter buffered$default(Writer $this$buffered_u24default, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
      return $this$buffered_u24default instanceof BufferedWriter ? (BufferedWriter)$this$buffered_u24default : new BufferedWriter($this$buffered_u24default, bufferSize);
   }

   public static final void forEachLine(@NotNull Reader $this$forEachLine, @NotNull Function1 action) {
      Intrinsics.checkNotNullParameter($this$forEachLine, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      int $i$f$useLines = 0;
      short var5 = 8192;
      Closeable var4 = (Closeable)($this$forEachLine instanceof BufferedReader ? (BufferedReader)$this$forEachLine : new BufferedReader($this$forEachLine, var5));
      Throwable var18 = null;

      try {
         BufferedReader it$iv = (BufferedReader)var4;
         int var7 = 0;
         Sequence it = lineSequence(it$iv);
         int var9 = 0;
         int $i$f$forEach = 0;

         for(Object element$iv : it) {
            action.invoke(element$iv);
         }

         Unit var19 = Unit.INSTANCE;
      } catch (Throwable var16) {
         var18 = var16;
         throw var16;
      } finally {
         CloseableKt.closeFinally(var4, var18);
      }

   }

   @NotNull
   public static final List readLines(@NotNull Reader $this$readLines) {
      Intrinsics.checkNotNullParameter($this$readLines, "<this>");
      final ArrayList result = new ArrayList();
      forEachLine($this$readLines, new Function1() {
         public final void invoke(@NotNull String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            result.add(it);
         }
      });
      return (List)result;
   }

   public static final Object useLines(@NotNull Reader $this$useLines, @NotNull Function1 block) {
      Intrinsics.checkNotNullParameter($this$useLines, "<this>");
      Intrinsics.checkNotNullParameter(block, "block");
      int $i$f$useLines = 0;
      short var4 = 8192;
      Closeable var3 = (Closeable)($this$useLines instanceof BufferedReader ? (BufferedReader)$this$useLines : new BufferedReader($this$useLines, var4));
      Throwable var11 = null;

      Object var12;
      try {
         BufferedReader it = (BufferedReader)var3;
         int var6 = 0;
         var12 = block.invoke(lineSequence(it));
      } catch (Throwable var9) {
         var11 = var9;
         throw var9;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var3, var11);
         InlineMarker.finallyEnd(1);
      }

      return var12;
   }

   @InlineOnly
   private static final StringReader reader(String $this$reader) {
      Intrinsics.checkNotNullParameter($this$reader, "<this>");
      return new StringReader($this$reader);
   }

   @NotNull
   public static final Sequence lineSequence(@NotNull BufferedReader $this$lineSequence) {
      Intrinsics.checkNotNullParameter($this$lineSequence, "<this>");
      return SequencesKt.constrainOnce(new LinesSequence($this$lineSequence));
   }

   @NotNull
   public static final String readText(@NotNull Reader $this$readText) {
      Intrinsics.checkNotNullParameter($this$readText, "<this>");
      StringWriter buffer = new StringWriter();
      copyTo$default($this$readText, (Writer)buffer, 0, 2, (Object)null);
      String var2 = buffer.toString();
      Intrinsics.checkNotNullExpressionValue(var2, "buffer.toString()");
      return var2;
   }

   public static final long copyTo(@NotNull Reader $this$copyTo, @NotNull Writer out, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
      Intrinsics.checkNotNullParameter(out, "out");
      long charsCopied = 0L;
      char[] buffer = new char[bufferSize];

      for(int chars = $this$copyTo.read(buffer); chars >= 0; chars = $this$copyTo.read(buffer)) {
         out.write(buffer, 0, chars);
         charsCopied += (long)chars;
      }

      return charsCopied;
   }

   // $FF: synthetic method
   public static long copyTo$default(Reader var0, Writer var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      return copyTo(var0, var1, var2);
   }

   @InlineOnly
   private static final String readText(URL $this$readText, Charset charset) {
      Intrinsics.checkNotNullParameter($this$readText, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var2 = readBytes($this$readText);
      return new String(var2, charset);
   }

   // $FF: synthetic method
   static String readText$default(URL $this$readText_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$readText_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var4 = readBytes($this$readText_u24default);
      return new String(var4, charset);
   }

   @NotNull
   public static final byte[] readBytes(@NotNull URL $this$readBytes) {
      Intrinsics.checkNotNullParameter($this$readBytes, "<this>");
      Closeable var1 = (Closeable)$this$readBytes.openStream();
      Throwable var2 = null;

      byte[] var9;
      try {
         InputStream it = (InputStream)var1;
         int var4 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         var9 = ByteStreamsKt.readBytes(it);
      } catch (Throwable var7) {
         var2 = var7;
         throw var7;
      } finally {
         CloseableKt.closeFinally(var1, var2);
      }

      return var9;
   }
}
