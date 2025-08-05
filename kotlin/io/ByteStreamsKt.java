package kotlin.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.collections.ByteIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0007\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u000b\u001a\u00020\f*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\r\u001a\u00020\u000e*\u00020\u000f2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001c\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0014H\u0087\b\u001a\u001d\u0010\u0013\u001a\u00020\u000e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0018*\u00020\u0001H\u0086\u0002\u001a\f\u0010\u0019\u001a\u00020\u0014*\u00020\u0002H\u0007\u001a\u0016\u0010\u0019\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u0004H\u0007\u001a\u0017\u0010\u001b\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u001d\u001a\u00020\u001e*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b¨\u0006\u001f"},
   d2 = {"buffered", "Ljava/io/BufferedInputStream;", "Ljava/io/InputStream;", "bufferSize", "", "Ljava/io/BufferedOutputStream;", "Ljava/io/OutputStream;", "bufferedReader", "Ljava/io/BufferedReader;", "charset", "Ljava/nio/charset/Charset;", "bufferedWriter", "Ljava/io/BufferedWriter;", "byteInputStream", "Ljava/io/ByteArrayInputStream;", "", "copyTo", "", "out", "inputStream", "", "offset", "length", "iterator", "Lkotlin/collections/ByteIterator;", "readBytes", "estimatedSize", "reader", "Ljava/io/InputStreamReader;", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}
)
@JvmName(
   name = "ByteStreamsKt"
)
public final class ByteStreamsKt {
   @NotNull
   public static final ByteIterator iterator(@NotNull final BufferedInputStream $this$iterator) {
      Intrinsics.checkNotNullParameter($this$iterator, "<this>");
      return new ByteIterator() {
         private int nextByte = -1;
         private boolean nextPrepared;
         private boolean finished;

         public final int getNextByte() {
            return this.nextByte;
         }

         public final void setNextByte(int var1) {
            this.nextByte = var1;
         }

         public final boolean getNextPrepared() {
            return this.nextPrepared;
         }

         public final void setNextPrepared(boolean var1) {
            this.nextPrepared = var1;
         }

         public final boolean getFinished() {
            return this.finished;
         }

         public final void setFinished(boolean var1) {
            this.finished = var1;
         }

         private final void prepareNext() {
            if (!this.nextPrepared && !this.finished) {
               this.nextByte = $this$iterator.read();
               this.nextPrepared = true;
               this.finished = this.nextByte == -1;
            }

         }

         public boolean hasNext() {
            this.prepareNext();
            return !this.finished;
         }

         public byte nextByte() {
            this.prepareNext();
            if (this.finished) {
               throw new NoSuchElementException("Input stream is over.");
            } else {
               byte res = (byte)this.nextByte;
               this.nextPrepared = false;
               return res;
            }
         }
      };
   }

   @InlineOnly
   private static final ByteArrayInputStream byteInputStream(String $this$byteInputStream, Charset charset) {
      Intrinsics.checkNotNullParameter($this$byteInputStream, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var3 = $this$byteInputStream.getBytes(charset);
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).getBytes(charset)");
      return new ByteArrayInputStream(var3);
   }

   // $FF: synthetic method
   static ByteArrayInputStream byteInputStream$default(String $this$byteInputStream_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$byteInputStream_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var4 = $this$byteInputStream_u24default.getBytes(charset);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).getBytes(charset)");
      return new ByteArrayInputStream(var4);
   }

   @InlineOnly
   private static final ByteArrayInputStream inputStream(byte[] $this$inputStream) {
      Intrinsics.checkNotNullParameter($this$inputStream, "<this>");
      return new ByteArrayInputStream($this$inputStream);
   }

   @InlineOnly
   private static final ByteArrayInputStream inputStream(byte[] $this$inputStream, int offset, int length) {
      Intrinsics.checkNotNullParameter($this$inputStream, "<this>");
      return new ByteArrayInputStream($this$inputStream, offset, length);
   }

   @InlineOnly
   private static final BufferedInputStream buffered(InputStream $this$buffered, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$buffered, "<this>");
      return $this$buffered instanceof BufferedInputStream ? (BufferedInputStream)$this$buffered : new BufferedInputStream($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedInputStream buffered$default(InputStream $this$buffered_u24default, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
      return $this$buffered_u24default instanceof BufferedInputStream ? (BufferedInputStream)$this$buffered_u24default : new BufferedInputStream($this$buffered_u24default, bufferSize);
   }

   @InlineOnly
   private static final InputStreamReader reader(InputStream $this$reader, Charset charset) {
      Intrinsics.checkNotNullParameter($this$reader, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      return new InputStreamReader($this$reader, charset);
   }

   // $FF: synthetic method
   static InputStreamReader reader$default(InputStream $this$reader_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$reader_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      return new InputStreamReader($this$reader_u24default, charset);
   }

   @InlineOnly
   private static final BufferedReader bufferedReader(InputStream $this$bufferedReader, Charset charset) {
      Intrinsics.checkNotNullParameter($this$bufferedReader, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Reader var2 = (Reader)(new InputStreamReader($this$bufferedReader, charset));
      short var3 = 8192;
      return var2 instanceof BufferedReader ? (BufferedReader)var2 : new BufferedReader(var2, var3);
   }

   // $FF: synthetic method
   static BufferedReader bufferedReader$default(InputStream $this$bufferedReader_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$bufferedReader_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Reader var4 = (Reader)(new InputStreamReader($this$bufferedReader_u24default, charset));
      short var5 = 8192;
      return var4 instanceof BufferedReader ? (BufferedReader)var4 : new BufferedReader(var4, var5);
   }

   @InlineOnly
   private static final BufferedOutputStream buffered(OutputStream $this$buffered, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$buffered, "<this>");
      return $this$buffered instanceof BufferedOutputStream ? (BufferedOutputStream)$this$buffered : new BufferedOutputStream($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedOutputStream buffered$default(OutputStream $this$buffered_u24default, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
      return $this$buffered_u24default instanceof BufferedOutputStream ? (BufferedOutputStream)$this$buffered_u24default : new BufferedOutputStream($this$buffered_u24default, bufferSize);
   }

   @InlineOnly
   private static final OutputStreamWriter writer(OutputStream $this$writer, Charset charset) {
      Intrinsics.checkNotNullParameter($this$writer, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      return new OutputStreamWriter($this$writer, charset);
   }

   // $FF: synthetic method
   static OutputStreamWriter writer$default(OutputStream $this$writer_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$writer_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      return new OutputStreamWriter($this$writer_u24default, charset);
   }

   @InlineOnly
   private static final BufferedWriter bufferedWriter(OutputStream $this$bufferedWriter, Charset charset) {
      Intrinsics.checkNotNullParameter($this$bufferedWriter, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Writer var2 = (Writer)(new OutputStreamWriter($this$bufferedWriter, charset));
      short var3 = 8192;
      return var2 instanceof BufferedWriter ? (BufferedWriter)var2 : new BufferedWriter(var2, var3);
   }

   // $FF: synthetic method
   static BufferedWriter bufferedWriter$default(OutputStream $this$bufferedWriter_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$bufferedWriter_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      Writer var4 = (Writer)(new OutputStreamWriter($this$bufferedWriter_u24default, charset));
      short var5 = 8192;
      return var4 instanceof BufferedWriter ? (BufferedWriter)var4 : new BufferedWriter(var4, var5);
   }

   public static final long copyTo(@NotNull InputStream $this$copyTo, @NotNull OutputStream out, int bufferSize) {
      Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
      Intrinsics.checkNotNullParameter(out, "out");
      long bytesCopied = 0L;
      byte[] buffer = new byte[bufferSize];

      for(int bytes = $this$copyTo.read(buffer); bytes >= 0; bytes = $this$copyTo.read(buffer)) {
         out.write(buffer, 0, bytes);
         bytesCopied += (long)bytes;
      }

      return bytesCopied;
   }

   // $FF: synthetic method
   public static long copyTo$default(InputStream var0, OutputStream var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      return copyTo(var0, var1, var2);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use readBytes() overload without estimatedSize parameter",
      replaceWith = @ReplaceWith(
   expression = "readBytes()",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.5"
   )
   @NotNull
   public static final byte[] readBytes(@NotNull InputStream $this$readBytes, int estimatedSize) {
      Intrinsics.checkNotNullParameter($this$readBytes, "<this>");
      int var3 = $this$readBytes.available();
      ByteArrayOutputStream buffer = new ByteArrayOutputStream(Math.max(estimatedSize, var3));
      copyTo$default($this$readBytes, (OutputStream)buffer, 0, 2, (Object)null);
      byte[] var4 = buffer.toByteArray();
      Intrinsics.checkNotNullExpressionValue(var4, "buffer.toByteArray()");
      return var4;
   }

   /** @deprecated */
   // $FF: synthetic method
   public static byte[] readBytes$default(InputStream var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      return readBytes(var0, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final byte[] readBytes(@NotNull InputStream $this$readBytes) {
      Intrinsics.checkNotNullParameter($this$readBytes, "<this>");
      ByteArrayOutputStream buffer = new ByteArrayOutputStream(Math.max(8192, $this$readBytes.available()));
      copyTo$default($this$readBytes, (OutputStream)buffer, 0, 2, (Object)null);
      byte[] var2 = buffer.toByteArray();
      Intrinsics.checkNotNullExpressionValue(var2, "buffer.toByteArray()");
      return var2;
   }
}
