package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001aB\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\u000f\n\u0006\b\u0011(+0\u0001\n\u0005\b\u009920\u0001¨\u00061"},
   d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"},
   xs = "kotlin/io/FilesKt"
)
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
   @InlineOnly
   private static final InputStreamReader reader(File $this$reader, Charset charset) {
      Intrinsics.checkNotNullParameter($this$reader, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      InputStream var2 = (InputStream)(new FileInputStream($this$reader));
      return new InputStreamReader(var2, charset);
   }

   // $FF: synthetic method
   static InputStreamReader reader$default(File $this$reader_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$reader_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      InputStream var4 = (InputStream)(new FileInputStream($this$reader_u24default));
      return new InputStreamReader(var4, charset);
   }

   @InlineOnly
   private static final BufferedReader bufferedReader(File $this$bufferedReader, Charset charset, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$bufferedReader, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      InputStream var4 = (InputStream)(new FileInputStream($this$bufferedReader));
      Reader var3 = (Reader)(new InputStreamReader(var4, charset));
      return var3 instanceof BufferedReader ? (BufferedReader)var3 : new BufferedReader(var3, bufferSize);
   }

   // $FF: synthetic method
   static BufferedReader bufferedReader$default(File $this$bufferedReader_u24default, Charset charset, int bufferSize, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$bufferedReader_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      InputStream var6 = (InputStream)(new FileInputStream($this$bufferedReader_u24default));
      Reader var5 = (Reader)(new InputStreamReader(var6, charset));
      return var5 instanceof BufferedReader ? (BufferedReader)var5 : new BufferedReader(var5, bufferSize);
   }

   @InlineOnly
   private static final OutputStreamWriter writer(File $this$writer, Charset charset) {
      Intrinsics.checkNotNullParameter($this$writer, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OutputStream var2 = (OutputStream)(new FileOutputStream($this$writer));
      return new OutputStreamWriter(var2, charset);
   }

   // $FF: synthetic method
   static OutputStreamWriter writer$default(File $this$writer_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$writer_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OutputStream var4 = (OutputStream)(new FileOutputStream($this$writer_u24default));
      return new OutputStreamWriter(var4, charset);
   }

   @InlineOnly
   private static final BufferedWriter bufferedWriter(File $this$bufferedWriter, Charset charset, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$bufferedWriter, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OutputStream var4 = (OutputStream)(new FileOutputStream($this$bufferedWriter));
      Writer var3 = (Writer)(new OutputStreamWriter(var4, charset));
      return var3 instanceof BufferedWriter ? (BufferedWriter)var3 : new BufferedWriter(var3, bufferSize);
   }

   // $FF: synthetic method
   static BufferedWriter bufferedWriter$default(File $this$bufferedWriter_u24default, Charset charset, int bufferSize, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$bufferedWriter_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      OutputStream var6 = (OutputStream)(new FileOutputStream($this$bufferedWriter_u24default));
      Writer var5 = (Writer)(new OutputStreamWriter(var6, charset));
      return var5 instanceof BufferedWriter ? (BufferedWriter)var5 : new BufferedWriter(var5, bufferSize);
   }

   @InlineOnly
   private static final PrintWriter printWriter(File $this$printWriter, Charset charset) {
      Intrinsics.checkNotNullParameter($this$printWriter, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      short var3 = 8192;
      OutputStream var5 = (OutputStream)(new FileOutputStream($this$printWriter));
      Writer var4 = (Writer)(new OutputStreamWriter(var5, charset));
      return new PrintWriter((Writer)(var4 instanceof BufferedWriter ? (BufferedWriter)var4 : new BufferedWriter(var4, var3)));
   }

   // $FF: synthetic method
   static PrintWriter printWriter$default(File $this$printWriter_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$printWriter_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      short var6 = 8192;
      OutputStream var5 = (OutputStream)(new FileOutputStream($this$printWriter_u24default));
      Writer var4 = (Writer)(new OutputStreamWriter(var5, charset));
      return new PrintWriter((Writer)(var4 instanceof BufferedWriter ? (BufferedWriter)var4 : new BufferedWriter(var4, var6)));
   }

   @NotNull
   public static final byte[] readBytes(@NotNull File $this$readBytes) {
      Intrinsics.checkNotNullParameter($this$readBytes, "<this>");
      Closeable var1 = (Closeable)(new FileInputStream($this$readBytes));
      Throwable var2 = null;

      byte[] var20;
      try {
         FileInputStream input = (FileInputStream)var1;
         int var4 = 0;
         int offset = 0;
         long length = $this$readBytes.length();
         int var10 = 0;
         if (length > 2147483647L) {
            throw new OutOfMemoryError("File " + $this$readBytes + " is too big (" + length + " bytes) to fit in memory.");
         }

         int remaining = (int)length;

         int read;
         for(result = new byte[remaining]; remaining > 0; offset += read) {
            read = input.read(result, offset, remaining);
            if (read < 0) {
               break;
            }

            remaining -= read;
         }

         byte[] var10000;
         if (remaining > 0) {
            byte[] var13 = Arrays.copyOf(result, offset);
            Intrinsics.checkNotNullExpressionValue(var13, "copyOf(this, newSize)");
            var10000 = var13;
         } else {
            read = input.read();
            if (read == -1) {
               var10000 = result;
            } else {
               ExposingBufferByteArrayOutputStream extra = new ExposingBufferByteArrayOutputStream(8193);
               extra.write(read);
               ByteStreamsKt.copyTo$default((InputStream)input, (OutputStream)extra, 0, 2, (Object)null);
               var10 = result.length + extra.size();
               if (var10 < 0) {
                  throw new OutOfMemoryError("File " + $this$readBytes + " is too big to fit in memory.");
               }

               var10000 = extra.getBuffer();
               byte[] var15 = Arrays.copyOf(result, var10);
               Intrinsics.checkNotNullExpressionValue(var15, "copyOf(this, newSize)");
               var10000 = ArraysKt.copyInto(var10000, var15, result.length, 0, extra.size());
            }
         }

         var20 = var10000;
      } catch (Throwable var18) {
         var2 = var18;
         throw var18;
      } finally {
         CloseableKt.closeFinally(var1, var2);
      }

      return var20;
   }

   public static final void writeBytes(@NotNull File $this$writeBytes, @NotNull byte[] array) {
      Intrinsics.checkNotNullParameter($this$writeBytes, "<this>");
      Intrinsics.checkNotNullParameter(array, "array");
      Closeable var2 = (Closeable)(new FileOutputStream($this$writeBytes));
      Throwable var3 = null;

      try {
         FileOutputStream it = (FileOutputStream)var2;
         int var5 = 0;
         it.write(array);
         Unit var10 = Unit.INSTANCE;
      } catch (Throwable var8) {
         var3 = var8;
         throw var8;
      } finally {
         CloseableKt.closeFinally(var2, var3);
      }

   }

   public static final void appendBytes(@NotNull File $this$appendBytes, @NotNull byte[] array) {
      Intrinsics.checkNotNullParameter($this$appendBytes, "<this>");
      Intrinsics.checkNotNullParameter(array, "array");
      Closeable var2 = (Closeable)(new FileOutputStream($this$appendBytes, true));
      Throwable var3 = null;

      try {
         FileOutputStream it = (FileOutputStream)var2;
         int var5 = 0;
         it.write(array);
         Unit var10 = Unit.INSTANCE;
      } catch (Throwable var8) {
         var3 = var8;
         throw var8;
      } finally {
         CloseableKt.closeFinally(var2, var3);
      }

   }

   @NotNull
   public static final String readText(@NotNull File $this$readText, @NotNull Charset charset) {
      Intrinsics.checkNotNullParameter($this$readText, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      InputStream var3 = (InputStream)(new FileInputStream($this$readText));
      Closeable var2 = (Closeable)(new InputStreamReader(var3, charset));
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
   public static String readText$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readText(var0, var1);
   }

   public static final void writeText(@NotNull File $this$writeText, @NotNull String text, @NotNull Charset charset) {
      Intrinsics.checkNotNullParameter($this$writeText, "<this>");
      Intrinsics.checkNotNullParameter(text, "text");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var4 = text.getBytes(charset);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).getBytes(charset)");
      FilesKt.writeBytes($this$writeText, var4);
   }

   // $FF: synthetic method
   public static void writeText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.writeText(var0, var1, var2);
   }

   public static final void appendText(@NotNull File $this$appendText, @NotNull String text, @NotNull Charset charset) {
      Intrinsics.checkNotNullParameter($this$appendText, "<this>");
      Intrinsics.checkNotNullParameter(text, "text");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var4 = text.getBytes(charset);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).getBytes(charset)");
      FilesKt.appendBytes($this$appendText, var4);
   }

   // $FF: synthetic method
   public static void appendText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.appendText(var0, var1, var2);
   }

   public static final void forEachBlock(@NotNull File $this$forEachBlock, @NotNull Function2 action) {
      Intrinsics.checkNotNullParameter($this$forEachBlock, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      FilesKt.forEachBlock($this$forEachBlock, 4096, action);
   }

   public static final void forEachBlock(@NotNull File $this$forEachBlock, int blockSize, @NotNull Function2 action) {
      Intrinsics.checkNotNullParameter($this$forEachBlock, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      byte[] arr = new byte[RangesKt.coerceAtLeast(blockSize, 512)];
      Closeable var4 = (Closeable)(new FileInputStream($this$forEachBlock));
      Throwable var5 = null;

      try {
         FileInputStream input = (FileInputStream)var4;
         int var7 = 0;

         while(true) {
            int size = input.read(arr);
            if (size <= 0) {
               Unit var13 = Unit.INSTANCE;
               return;
            }

            action.invoke(arr, size);
         }
      } catch (Throwable var11) {
         var5 = var11;
         throw var11;
      } finally {
         CloseableKt.closeFinally(var4, var5);
      }
   }

   public static final void forEachLine(@NotNull File $this$forEachLine, @NotNull Charset charset, @NotNull Function1 action) {
      Intrinsics.checkNotNullParameter($this$forEachLine, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(action, "action");
      TextStreamsKt.forEachLine((Reader)(new BufferedReader((Reader)(new InputStreamReader((InputStream)(new FileInputStream($this$forEachLine)), charset)))), action);
   }

   // $FF: synthetic method
   public static void forEachLine$default(File var0, Charset var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      FilesKt.forEachLine(var0, var1, var2);
   }

   @InlineOnly
   private static final FileInputStream inputStream(File $this$inputStream) {
      Intrinsics.checkNotNullParameter($this$inputStream, "<this>");
      return new FileInputStream($this$inputStream);
   }

   @InlineOnly
   private static final FileOutputStream outputStream(File $this$outputStream) {
      Intrinsics.checkNotNullParameter($this$outputStream, "<this>");
      return new FileOutputStream($this$outputStream);
   }

   @NotNull
   public static final List readLines(@NotNull File $this$readLines, @NotNull Charset charset) {
      Intrinsics.checkNotNullParameter($this$readLines, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      final ArrayList result = new ArrayList();
      FilesKt.forEachLine($this$readLines, charset, new Function1() {
         public final void invoke(@NotNull String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            result.add(it);
         }
      });
      return (List)result;
   }

   // $FF: synthetic method
   public static List readLines$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readLines(var0, var1);
   }

   public static final Object useLines(@NotNull File $this$useLines, @NotNull Charset charset, @NotNull Function1 block) {
      Intrinsics.checkNotNullParameter($this$useLines, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Intrinsics.checkNotNullParameter(block, "block");
      int $i$f$useLines = 0;
      short var5 = 8192;
      InputStream var7 = (InputStream)(new FileInputStream($this$useLines));
      Reader var6 = (Reader)(new InputStreamReader(var7, charset));
      Closeable var4 = (Closeable)(var6 instanceof BufferedReader ? (BufferedReader)var6 : new BufferedReader(var6, var5));
      Throwable var12 = null;

      try {
         BufferedReader it = (BufferedReader)var4;
         int var15 = 0;
         var14 = block.invoke(TextStreamsKt.lineSequence(it));
      } catch (Throwable var10) {
         var12 = var10;
         throw var10;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var4, var12);
         InlineMarker.finallyEnd(1);
      }

      return var14;
   }

   // $FF: synthetic method
   public static Object useLines$default(File $this$useLines_u24default, Charset charset, Function1 block, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$useLines = 0;
      short var9 = 8192;
      InputStream var11 = (InputStream)(new FileInputStream($this$useLines_u24default));
      Reader var10 = (Reader)(new InputStreamReader(var11, charset));
      Closeable var8 = (Closeable)(var10 instanceof BufferedReader ? (BufferedReader)var10 : new BufferedReader(var10, var9));
      Throwable var16 = null;

      try {
         BufferedReader it$iv = (BufferedReader)var8;
         int var19 = 0;
         var18 = block.invoke(TextStreamsKt.lineSequence(it$iv));
      } catch (Throwable var14) {
         var16 = var14;
         throw var14;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var8, var16);
         InlineMarker.finallyEnd(1);
      }

      return var18;
   }

   public FilesKt__FileReadWriteKt() {
   }
}
