package kotlin.io;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\u0010\u0010#\u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"},
   d2 = {"Lkotlin/io/LineReader;", "", "()V", "BUFFER_SIZE", "", "byteBuf", "Ljava/nio/ByteBuffer;", "bytes", "", "charBuf", "Ljava/nio/CharBuffer;", "chars", "", "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "compactBytes", "decode", "endOfInput", "decodeEndOfInput", "nBytes", "nChars", "readLine", "", "inputStream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "resetAll", "", "trimStringBuilder", "updateCharset", "kotlin-stdlib"}
)
public final class LineReader {
   @NotNull
   public static final LineReader INSTANCE = new LineReader();
   private static final int BUFFER_SIZE = 32;
   private static CharsetDecoder decoder;
   private static boolean directEOL;
   @NotNull
   private static final byte[] bytes = new byte[32];
   @NotNull
   private static final char[] chars = new char[32];
   @NotNull
   private static final ByteBuffer byteBuf;
   @NotNull
   private static final CharBuffer charBuf;
   @NotNull
   private static final StringBuilder sb;

   private LineReader() {
   }

   @Nullable
   public final synchronized String readLine(@NotNull InputStream inputStream, @NotNull Charset charset) {
      label75: {
         Intrinsics.checkNotNullParameter(inputStream, "inputStream");
         Intrinsics.checkNotNullParameter(charset, "charset");
         if (decoder != null) {
            CharsetDecoder var3 = decoder;
            CharsetDecoder var10000;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("decoder");
               var10000 = null;
            } else {
               var10000 = var3;
            }

            if (Intrinsics.areEqual((Object)var10000.charset(), (Object)charset)) {
               break label75;
            }
         }

         this.updateCharset(charset);
      }

      int nBytes = 0;
      int nChars = 0;

      while(true) {
         int readByte = inputStream.read();
         if (readByte == -1) {
            CharSequence result = (CharSequence)sb;
            if (result.length() == 0 && nBytes == 0 && nChars == 0) {
               return null;
            }

            nChars = this.decodeEndOfInput(nBytes, nChars);
            break;
         }

         int result = nBytes++;
         bytes[result] = (byte)readByte;
         result = 10;
         if (readByte == result || nBytes == 32 || !directEOL) {
            byteBuf.limit(nBytes);
            charBuf.position(nChars);
            nChars = this.decode(false);
            if (nChars > 0 && chars[nChars - 1] == '\n') {
               byteBuf.position(0);
               break;
            }

            nBytes = this.compactBytes();
         }
      }

      if (nChars > 0 && chars[nChars - 1] == '\n') {
         nChars += -1;
         if (nChars > 0 && chars[nChars - 1] == '\r') {
            nChars += -1;
         }
      }

      CharSequence result = (CharSequence)sb;
      if (result.length() == 0) {
         char[] var9 = chars;
         byte result = 0;
         return new String(var9, result, nChars);
      } else {
         sb.append(chars, 0, nChars);
         String result = sb.toString();
         Intrinsics.checkNotNullExpressionValue(result, "sb.toString()");
         if (sb.length() > 32) {
            this.trimStringBuilder();
         }

         sb.setLength(0);
         return result;
      }
   }

   private final int decode(boolean endOfInput) {
      while(true) {
         CharsetDecoder var4 = decoder;
         CharsetDecoder var10000;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            var10000 = null;
         } else {
            var10000 = var4;
         }

         CoderResult var3 = var10000.decode(byteBuf, charBuf, endOfInput);
         Intrinsics.checkNotNullExpressionValue(var3, "decoder.decode(byteBuf, charBuf, endOfInput)");
         CoderResult coderResult = var3;
         if (var3.isError()) {
            this.resetAll();
            var3.throwException();
         }

         int nChars = charBuf.position();
         if (!coderResult.isOverflow()) {
            return nChars;
         }

         sb.append(chars, 0, nChars - 1);
         charBuf.position(0);
         charBuf.limit(32);
         charBuf.put(chars[nChars - 1]);
      }
   }

   private final int compactBytes() {
      ByteBuffer $this$compactBytes_u24lambda_u2d1 = byteBuf;
      int var3 = 0;
      $this$compactBytes_u24lambda_u2d1.compact();
      int var4 = $this$compactBytes_u24lambda_u2d1.position();
      int var6 = 0;
      $this$compactBytes_u24lambda_u2d1.position(0);
      return var4;
   }

   private final int decodeEndOfInput(int nBytes, int nChars) {
      byteBuf.limit(nBytes);
      charBuf.position(nChars);
      int var3 = this.decode(true);
      int var5 = 0;
      CharsetDecoder var6 = decoder;
      CharsetDecoder var10000;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("decoder");
         var10000 = null;
      } else {
         var10000 = var6;
      }

      var10000.reset();
      byteBuf.position(0);
      return var3;
   }

   private final void updateCharset(Charset charset) {
      CharsetDecoder var2 = charset.newDecoder();
      Intrinsics.checkNotNullExpressionValue(var2, "charset.newDecoder()");
      decoder = var2;
      byteBuf.clear();
      charBuf.clear();
      byteBuf.put((byte)10);
      byteBuf.flip();
      var2 = decoder;
      CharsetDecoder var10000;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("decoder");
         var10000 = null;
      } else {
         var10000 = var2;
      }

      var10000.decode(byteBuf, charBuf, false);
      directEOL = charBuf.position() == 1 && charBuf.get(0) == '\n';
      this.resetAll();
   }

   private final void resetAll() {
      CharsetDecoder var1 = decoder;
      CharsetDecoder var10000;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("decoder");
         var10000 = null;
      } else {
         var10000 = var1;
      }

      var10000.reset();
      byteBuf.position(0);
      sb.setLength(0);
   }

   private final void trimStringBuilder() {
      sb.setLength(32);
      sb.trimToSize();
   }

   static {
      ByteBuffer var0 = ByteBuffer.wrap(bytes);
      Intrinsics.checkNotNullExpressionValue(var0, "wrap(bytes)");
      byteBuf = var0;
      CharBuffer var1 = CharBuffer.wrap(chars);
      Intrinsics.checkNotNullExpressionValue(var1, "wrap(chars)");
      charBuf = var1;
      sb = new StringBuilder();
   }
}
