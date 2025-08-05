package kotlin.io.path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u0082\u0001\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a%\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a%\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u000b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001e\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a:\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u0010\u0015\u001a:\u0010\u0016\u001a\u00020\u0017*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u0010\u0018\u001a=\u0010\u0019\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2!\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00010\u001bH\u0087\bø\u0001\u0000\u001a&\u0010 \u001a\u00020!*\u00020\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u0010\"\u001a&\u0010#\u001a\u00020$*\u00020\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u0010%\u001a\r\u0010&\u001a\u00020\u0004*\u00020\u0002H\u0087\b\u001a\u001d\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001c0(*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0016\u0010)\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a0\u0010*\u001a\u00020+*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u0010,\u001a?\u0010-\u001a\u0002H.\"\u0004\b\u0000\u0010.*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0018\u0010/\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u000b\u0012\u0004\u0012\u0002H.0\u001bH\u0087\bø\u0001\u0000¢\u0006\u0002\u00100\u001a.\u00101\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u00102\u001a>\u00103\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u00104\u001a>\u00103\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u000b2\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u00105\u001a7\u00106\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0007¢\u0006\u0002\u00107\u001a0\u00108\u001a\u000209*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b¢\u0006\u0002\u0010:\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006;"},
   d2 = {"appendBytes", "", "Ljava/nio/file/Path;", "array", "", "appendLines", "lines", "", "", "charset", "Ljava/nio/charset/Charset;", "Lkotlin/sequences/Sequence;", "appendText", "text", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedReader;", "bufferedWriter", "Ljava/io/BufferedWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;", "forEachLine", "action", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "line", "inputStream", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;", "outputStream", "Ljava/io/OutputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStream;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/InputStreamReader;", "useLines", "T", "block", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "(Ljava/nio/file/Path;[B[Ljava/nio/file/OpenOption;)V", "writeLines", "(Ljava/nio/file/Path;Ljava/lang/Iterable;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "(Ljava/nio/file/Path;Lkotlin/sequences/Sequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "writeText", "(Ljava/nio/file/Path;Ljava/lang/CharSequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)V", "writer", "Ljava/io/OutputStreamWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStreamWriter;", "kotlin-stdlib-jdk7"},
   xs = "kotlin/io/path/PathsKt"
)
class PathsKt__PathReadWriteKt {
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final InputStreamReader reader(Path $this$reader, Charset charset, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$reader, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new InputStreamReader(Files.newInputStream($this$reader, (OpenOption[])Arrays.copyOf(options, options.length)), charset);
   }

   // $FF: synthetic method
   static InputStreamReader reader$default(Path $this$reader_u24default, Charset charset, OpenOption[] options, int var3, Object var4) throws IOException {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$reader_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new InputStreamReader(Files.newInputStream($this$reader_u24default, (OpenOption[])Arrays.copyOf(options, options.length)), charset);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final BufferedReader bufferedReader(Path $this$bufferedReader, Charset charset, int bufferSize, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$bufferedReader, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new BufferedReader((Reader)(new InputStreamReader(Files.newInputStream($this$bufferedReader, (OpenOption[])Arrays.copyOf(options, options.length)), charset)), bufferSize);
   }

   // $FF: synthetic method
   static BufferedReader bufferedReader$default(Path $this$bufferedReader_u24default, Charset charset, int bufferSize, OpenOption[] options, int var4, Object var5) throws IOException {
      if ((var4 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      if ((var4 & 2) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$bufferedReader_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new BufferedReader((Reader)(new InputStreamReader(Files.newInputStream($this$bufferedReader_u24default, (OpenOption[])Arrays.copyOf(options, options.length)), charset)), bufferSize);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final OutputStreamWriter writer(Path $this$writer, Charset charset, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$writer, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new OutputStreamWriter(Files.newOutputStream($this$writer, (OpenOption[])Arrays.copyOf(options, options.length)), charset);
   }

   // $FF: synthetic method
   static OutputStreamWriter writer$default(Path $this$writer_u24default, Charset charset, OpenOption[] options, int var3, Object var4) throws IOException {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$writer_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new OutputStreamWriter(Files.newOutputStream($this$writer_u24default, (OpenOption[])Arrays.copyOf(options, options.length)), charset);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final BufferedWriter bufferedWriter(Path $this$bufferedWriter, Charset charset, int bufferSize, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$bufferedWriter, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new BufferedWriter((Writer)(new OutputStreamWriter(Files.newOutputStream($this$bufferedWriter, (OpenOption[])Arrays.copyOf(options, options.length)), charset)), bufferSize);
   }

   // $FF: synthetic method
   static BufferedWriter bufferedWriter$default(Path $this$bufferedWriter_u24default, Charset charset, int bufferSize, OpenOption[] options, int var4, Object var5) throws IOException {
      if ((var4 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      if ((var4 & 2) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$bufferedWriter_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      return new BufferedWriter((Writer)(new OutputStreamWriter(Files.newOutputStream($this$bufferedWriter_u24default, (OpenOption[])Arrays.copyOf(options, options.length)), charset)), bufferSize);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final byte[] readBytes(Path $this$readBytes) throws IOException {
      Intrinsics.checkNotNullParameter($this$readBytes, "<this>");
      byte[] var1 = Files.readAllBytes($this$readBytes);
      Intrinsics.checkNotNullExpressionValue(var1, "readAllBytes(this)");
      return var1;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final void writeBytes(Path $this$writeBytes, byte[] array, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$writeBytes, "<this>");
      Intrinsics.checkNotNullParameter(array, "array");
      Intrinsics.checkNotNullParameter(options, "options");
      Files.write($this$writeBytes, array, (OpenOption[])Arrays.copyOf(options, options.length));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final void appendBytes(Path $this$appendBytes, byte[] array) throws IOException {
      Intrinsics.checkNotNullParameter($this$appendBytes, "<this>");
      Intrinsics.checkNotNullParameter(array, "array");
      OpenOption[] var2 = new OpenOption[]{(OpenOption)StandardOpenOption.APPEND};
      Files.write($this$appendBytes, array, var2);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @NotNull
   public static final String readText(@NotNull Path $this$readText, @NotNull Charset charset) throws IOException {
      Intrinsics.checkNotNullParameter($this$readText, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OpenOption[] var3 = new OpenOption[0];
      Closeable var2 = (Closeable)(new InputStreamReader(Files.newInputStream($this$readText, (OpenOption[])Arrays.copyOf(var3, var3.length)), charset));
      Throwable var10 = null;

      String var11;
      try {
         InputStreamReader it = (InputStreamReader)var2;
         int var5 = 0;
         var11 = TextStreamsKt.readText((Reader)it);
      } catch (Throwable var8) {
         var10 = var8;
         throw var8;
      } finally {
         CloseableKt.closeFinally(var2, var10);
      }

      return var11;
   }

   // $FF: synthetic method
   public static String readText$default(Path var0, Charset var1, int var2, Object var3) throws IOException {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return PathsKt.readText(var0, var1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   public static final void writeText(@NotNull Path $this$writeText, @NotNull CharSequence text, @NotNull Charset charset, @NotNull OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$writeText, "<this>");
      Intrinsics.checkNotNullParameter(text, "text");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      OutputStream var4 = Files.newOutputStream($this$writeText, (OpenOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var4, "newOutputStream(this, *options)");
      Closeable var12 = (Closeable)(new OutputStreamWriter(var4, charset));
      Throwable var5 = null;

      try {
         OutputStreamWriter it = (OutputStreamWriter)var12;
         int var7 = 0;
         it.append(text);
      } catch (Throwable var10) {
         var5 = var10;
         throw var10;
      } finally {
         CloseableKt.closeFinally(var12, var5);
      }

   }

   // $FF: synthetic method
   public static void writeText$default(Path var0, CharSequence var1, Charset var2, OpenOption[] var3, int var4, Object var5) throws IOException {
      if ((var4 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      PathsKt.writeText(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   public static final void appendText(@NotNull Path $this$appendText, @NotNull CharSequence text, @NotNull Charset charset) throws IOException {
      Intrinsics.checkNotNullParameter($this$appendText, "<this>");
      Intrinsics.checkNotNullParameter(text, "text");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OpenOption[] var4 = new OpenOption[]{(OpenOption)StandardOpenOption.APPEND};
      OutputStream var3 = Files.newOutputStream($this$appendText, var4);
      Intrinsics.checkNotNullExpressionValue(var3, "newOutputStream(this, StandardOpenOption.APPEND)");
      Closeable var11 = (Closeable)(new OutputStreamWriter(var3, charset));
      Throwable var12 = null;

      try {
         OutputStreamWriter it = (OutputStreamWriter)var11;
         int var6 = 0;
         it.append(text);
      } catch (Throwable var9) {
         var12 = var9;
         throw var9;
      } finally {
         CloseableKt.closeFinally(var11, var12);
      }

   }

   // $FF: synthetic method
   public static void appendText$default(Path var0, CharSequence var1, Charset var2, int var3, Object var4) throws IOException {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      PathsKt.appendText(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final void forEachLine(Path $this$forEachLine, Charset charset, Function1 action) throws IOException {
      Intrinsics.checkNotNullParameter($this$forEachLine, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(action, "action");
      BufferedReader var3 = Files.newBufferedReader($this$forEachLine, charset);
      Intrinsics.checkNotNullExpressionValue(var3, "newBufferedReader(this, charset)");
      Reader $this$useLines$iv = (Reader)var3;
      int $i$f$useLines = 0;
      boolean var6 = true;
      Closeable var5 = (Closeable)((BufferedReader)$this$useLines$iv);
      Throwable var20 = null;

      try {
         BufferedReader it$iv = (BufferedReader)var5;
         int var8 = 0;
         Sequence it = TextStreamsKt.lineSequence(it$iv);
         int var10 = 0;
         int $i$f$forEach = 0;

         for(Object element$iv : it) {
            action.invoke(element$iv);
         }

         Unit var21 = Unit.INSTANCE;
      } catch (Throwable var17) {
         var20 = var17;
         throw var17;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var5, var20);
         InlineMarker.finallyEnd(1);
      }

   }

   // $FF: synthetic method
   static void forEachLine$default(Path $this$forEachLine_u24default, Charset charset, Function1 action, int $this$useLines$iv, Object $i$f$useLines) throws IOException {
      if (($this$useLines$iv & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$forEachLine_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(action, "action");
      BufferedReader var19 = Files.newBufferedReader($this$forEachLine_u24default, charset);
      Intrinsics.checkNotNullExpressionValue(var19, "newBufferedReader(this, charset)");
      Reader $this$useLines$iv = (Reader)var19;
      int $i$f$useLines = 0;
      boolean var6 = true;
      Closeable var5 = (Closeable)((BufferedReader)$this$useLines$iv);
      Throwable var22 = null;

      try {
         BufferedReader it$iv = (BufferedReader)var5;
         int var8 = 0;
         Sequence it = TextStreamsKt.lineSequence(it$iv);
         int var10 = 0;
         int $i$f$forEach = 0;

         for(Object element$iv : it) {
            action.invoke(element$iv);
         }

         Unit var23 = Unit.INSTANCE;
      } catch (Throwable var17) {
         var22 = var17;
         throw var17;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var5, var22);
         InlineMarker.finallyEnd(1);
      }

   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final InputStream inputStream(Path $this$inputStream, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$inputStream, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      InputStream var2 = Files.newInputStream($this$inputStream, (OpenOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var2, "newInputStream(this, *options)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final OutputStream outputStream(Path $this$outputStream, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$outputStream, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      OutputStream var2 = Files.newOutputStream($this$outputStream, (OpenOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var2, "newOutputStream(this, *options)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final List readLines(Path $this$readLines, Charset charset) throws IOException {
      Intrinsics.checkNotNullParameter($this$readLines, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      List var2 = Files.readAllLines($this$readLines, charset);
      Intrinsics.checkNotNullExpressionValue(var2, "readAllLines(this, charset)");
      return var2;
   }

   // $FF: synthetic method
   static List readLines$default(Path $this$readLines_u24default, Charset charset, int var2, Object var3) throws IOException {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$readLines_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      List var4 = Files.readAllLines($this$readLines_u24default, charset);
      Intrinsics.checkNotNullExpressionValue(var4, "readAllLines(this, charset)");
      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Object useLines(Path $this$useLines, Charset charset, Function1 block) throws IOException {
      Intrinsics.checkNotNullParameter($this$useLines, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(block, "block");
      Closeable var3 = (Closeable)Files.newBufferedReader($this$useLines, charset);
      Throwable var4 = null;

      Object var11;
      try {
         BufferedReader it = (BufferedReader)var3;
         int var6 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         var11 = block.invoke(TextStreamsKt.lineSequence(it));
      } catch (Throwable var9) {
         var4 = var9;
         throw var9;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var3, var4);
         InlineMarker.finallyEnd(1);
      }

      return var11;
   }

   // $FF: synthetic method
   static Object useLines$default(Path $this$useLines_u24default, Charset charset, Function1 block, int var3, Object var4) throws IOException {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$useLines_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(block, "block");
      Closeable var11 = (Closeable)Files.newBufferedReader($this$useLines_u24default, charset);
      Throwable var12 = null;

      Object var13;
      try {
         BufferedReader it = (BufferedReader)var11;
         int var6 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         var13 = block.invoke(TextStreamsKt.lineSequence(it));
      } catch (Throwable var9) {
         var12 = var9;
         throw var9;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var11, var12);
         InlineMarker.finallyEnd(1);
      }

      return var13;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path writeLines(Path $this$writeLines, Iterable lines, Charset charset, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$writeLines, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      Path var4 = Files.write($this$writeLines, lines, charset, (OpenOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var4, "write(this, lines, charset, *options)");
      return var4;
   }

   // $FF: synthetic method
   static Path writeLines$default(Path $this$writeLines_u24default, Iterable lines, Charset charset, OpenOption[] options, int var4, Object var5) throws IOException {
      if ((var4 & 2) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$writeLines_u24default, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      Path var6 = Files.write($this$writeLines_u24default, lines, charset, (OpenOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var6, "write(this, lines, charset, *options)");
      return var6;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path writeLines(Path $this$writeLines, Sequence lines, Charset charset, OpenOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$writeLines, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      Path var4 = Files.write($this$writeLines, SequencesKt.asIterable(lines), charset, (OpenOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var4, "write(this, lines.asIterable(), charset, *options)");
      return var4;
   }

   // $FF: synthetic method
   static Path writeLines$default(Path $this$writeLines_u24default, Sequence lines, Charset charset, OpenOption[] options, int var4, Object var5) throws IOException {
      if ((var4 & 2) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$writeLines_u24default, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(options, "options");
      Path var6 = Files.write($this$writeLines_u24default, SequencesKt.asIterable(lines), charset, (OpenOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var6, "write(this, lines.asIterable(), charset, *options)");
      return var6;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path appendLines(Path $this$appendLines, Iterable lines, Charset charset) throws IOException {
      Intrinsics.checkNotNullParameter($this$appendLines, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OpenOption[] var4 = new OpenOption[]{(OpenOption)StandardOpenOption.APPEND};
      Path var3 = Files.write($this$appendLines, lines, charset, var4);
      Intrinsics.checkNotNullExpressionValue(var3, "write(this, lines, chars…tandardOpenOption.APPEND)");
      return var3;
   }

   // $FF: synthetic method
   static Path appendLines$default(Path $this$appendLines_u24default, Iterable lines, Charset charset, int var3, Object var4) throws IOException {
      if ((var3 & 2) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$appendLines_u24default, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OpenOption[] var6 = new OpenOption[]{(OpenOption)StandardOpenOption.APPEND};
      Path var5 = Files.write($this$appendLines_u24default, lines, charset, var6);
      Intrinsics.checkNotNullExpressionValue(var5, "write(this, lines, chars…tandardOpenOption.APPEND)");
      return var5;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path appendLines(Path $this$appendLines, Sequence lines, Charset charset) throws IOException {
      Intrinsics.checkNotNullParameter($this$appendLines, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Iterable var10001 = SequencesKt.asIterable(lines);
      OpenOption[] var4 = new OpenOption[]{(OpenOption)StandardOpenOption.APPEND};
      Path var3 = Files.write($this$appendLines, var10001, charset, var4);
      Intrinsics.checkNotNullExpressionValue(var3, "write(this, lines.asIter…tandardOpenOption.APPEND)");
      return var3;
   }

   // $FF: synthetic method
   static Path appendLines$default(Path $this$appendLines_u24default, Sequence lines, Charset charset, int var3, Object var4) throws IOException {
      if ((var3 & 2) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$appendLines_u24default, "<this>");
      Intrinsics.checkNotNullParameter(lines, "lines");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Iterable var10001 = SequencesKt.asIterable(lines);
      OpenOption[] var6 = new OpenOption[]{(OpenOption)StandardOpenOption.APPEND};
      Path var5 = Files.write($this$appendLines_u24default, var10001, charset, var6);
      Intrinsics.checkNotNullExpressionValue(var5, "write(this, lines.asIter…tandardOpenOption.APPEND)");
      return var5;
   }

   public PathsKt__PathReadWriteKt() {
   }
}
