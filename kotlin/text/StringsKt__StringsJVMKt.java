package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\f\u0010\u0017\u001a\u00020\u0002*\u00020\u0002H\u0007\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0019\u0010&\u001a\u00020#*\u0004\u0018\u00010'2\b\u0010!\u001a\u0004\u0018\u00010'H\u0087\u0004\u001a \u0010&\u001a\u00020#*\u0004\u0018\u00010'2\b\u0010!\u001a\u0004\u0018\u00010'2\u0006\u0010\"\u001a\u00020#H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010(\u001a\u00020'H\u0087\b\u001a\f\u0010)\u001a\u00020\u0002*\u00020\u0002H\u0007\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u00100\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00104\u001a6\u00100\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0004\b5\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00106\u001a:\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00107\u001a>\u00100\u001a\u00020\u0002*\u00020\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0004\b5\u00107\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00108\u001a\r\u00109\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u0010:\u001a\u00020#*\u00020'\u001a\r\u0010;\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010;\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u001d\u0010<\u001a\u00020\u0011*\u00020\u00022\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010<\u001a\u00020\u0011*\u00020\u00022\u0006\u0010@\u001a\u00020\u00022\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010A\u001a\u00020\u0011*\u00020\u00022\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010A\u001a\u00020\u0011*\u00020\u00022\u0006\u0010@\u001a\u00020\u00022\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010B\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010C\u001a\u00020\u0011H\u0087\b\u001a4\u0010D\u001a\u00020#*\u00020'2\u0006\u0010E\u001a\u00020\u00112\u0006\u0010!\u001a\u00020'2\u0006\u0010F\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010D\u001a\u00020#*\u00020\u00022\u0006\u0010E\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010F\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010G\u001a\u00020\u0002*\u00020'2\u0006\u0010H\u001a\u00020\u0011\u001a$\u0010I\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020>2\u0006\u0010K\u001a\u00020>2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010I\u001a\u00020\u0002*\u00020\u00022\u0006\u0010L\u001a\u00020\u00022\u0006\u0010M\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020>2\u0006\u0010K\u001a\u00020>2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010L\u001a\u00020\u00022\u0006\u0010M\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00020P*\u00020'2\u0006\u0010Q\u001a\u00020R2\b\b\u0002\u0010S\u001a\u00020\u0011\u001a\u001c\u0010T\u001a\u00020#*\u00020\u00022\u0006\u0010U\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010T\u001a\u00020#*\u00020\u00022\u0006\u0010U\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010V\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010V\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010W\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010X\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010X\u001a\u00020\u0014*\u00020\u00022\u0006\u0010Y\u001a\u00020\u00142\b\b\u0002\u0010Z\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010X\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010[\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010[\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010\\\u001a\u00020R*\u00020\u00022\b\b\u0002\u0010]\u001a\u00020\u0011H\u0087\b\u001a\r\u0010^\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010^\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\r\u0010_\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010_\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006`"},
   d2 = {"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "", "charSequence", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "formatNullable", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "lowercase", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "uppercase", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
   @InlineOnly
   private static final int nativeIndexOf(String $this$nativeIndexOf, char ch, int fromIndex) {
      Intrinsics.checkNotNullParameter($this$nativeIndexOf, "<this>");
      return $this$nativeIndexOf.indexOf(ch, fromIndex);
   }

   @InlineOnly
   private static final int nativeIndexOf(String $this$nativeIndexOf, String str, int fromIndex) {
      Intrinsics.checkNotNullParameter($this$nativeIndexOf, "<this>");
      Intrinsics.checkNotNullParameter(str, "str");
      return $this$nativeIndexOf.indexOf(str, fromIndex);
   }

   @InlineOnly
   private static final int nativeLastIndexOf(String $this$nativeLastIndexOf, char ch, int fromIndex) {
      Intrinsics.checkNotNullParameter($this$nativeLastIndexOf, "<this>");
      return $this$nativeLastIndexOf.lastIndexOf(ch, fromIndex);
   }

   @InlineOnly
   private static final int nativeLastIndexOf(String $this$nativeLastIndexOf, String str, int fromIndex) {
      Intrinsics.checkNotNullParameter($this$nativeLastIndexOf, "<this>");
      Intrinsics.checkNotNullParameter(str, "str");
      return $this$nativeLastIndexOf.lastIndexOf(str, fromIndex);
   }

   public static final boolean equals(@Nullable String $this$equals, @Nullable String other, boolean ignoreCase) {
      if ($this$equals == null) {
         return other == null;
      } else {
         return !ignoreCase ? $this$equals.equals(other) : $this$equals.equalsIgnoreCase(other);
      }
   }

   // $FF: synthetic method
   public static boolean equals$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.equals(var0, var1, var2);
   }

   @NotNull
   public static final String replace(@NotNull String $this$replace, char oldChar, char newChar, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$replace, "<this>");
      if (!ignoreCase) {
         String var16 = $this$replace.replace(oldChar, newChar);
         Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String…replace(oldChar, newChar)");
         return var16;
      } else {
         int var4 = $this$replace.length();
         StringBuilder var5 = new StringBuilder(var4);
         StringBuilder $this$replace_u24lambda_u2d1 = var5;
         int var7 = 0;
         CharSequence $this$forEach$iv = (CharSequence)$this$replace;
         int $i$f$forEach = 0;
         CharSequence var10 = $this$forEach$iv;
         int var11 = 0;

         while(var11 < var10.length()) {
            char element$iv = var10.charAt(var11);
            ++var11;
            int var14 = 0;
            $this$replace_u24lambda_u2d1.append(CharsKt.equals(element$iv, oldChar, ignoreCase) ? newChar : element$iv);
         }

         String var15 = var5.toString();
         Intrinsics.checkNotNullExpressionValue(var15, "StringBuilder(capacity).…builderAction).toString()");
         return var15;
      }
   }

   // $FF: synthetic method
   public static String replace$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replace(@NotNull String $this$replace, @NotNull String oldValue, @NotNull String newValue, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$replace, "<this>");
      Intrinsics.checkNotNullParameter(oldValue, "oldValue");
      Intrinsics.checkNotNullParameter(newValue, "newValue");
      String $this$replace_u24lambda_u2d2 = $this$replace;
      int var6 = 0;
      int occurrenceIndex = StringsKt.indexOf((CharSequence)$this$replace, oldValue, 0, ignoreCase);
      if (occurrenceIndex < 0) {
         return $this$replace;
      } else {
         int oldValueLength = oldValue.length();
         int searchStep = RangesKt.coerceAtLeast(oldValueLength, 1);
         int newLengthHint = $this$replace.length() - oldValueLength + newValue.length();
         if (newLengthHint < 0) {
            throw new OutOfMemoryError();
         } else {
            StringBuilder stringBuilder = new StringBuilder(newLengthHint);
            int i = 0;

            do {
               stringBuilder.append((CharSequence)$this$replace_u24lambda_u2d2, i, occurrenceIndex).append(newValue);
               i = occurrenceIndex + oldValueLength;
               if (occurrenceIndex >= $this$replace_u24lambda_u2d2.length()) {
                  break;
               }

               occurrenceIndex = StringsKt.indexOf((CharSequence)$this$replace_u24lambda_u2d2, oldValue, occurrenceIndex + searchStep, ignoreCase);
            } while(occurrenceIndex > 0);

            String var13 = stringBuilder.append((CharSequence)$this$replace_u24lambda_u2d2, i, $this$replace_u24lambda_u2d2.length()).toString();
            Intrinsics.checkNotNullExpressionValue(var13, "stringBuilder.append(this, i, length).toString()");
            return var13;
         }
      }
   }

   // $FF: synthetic method
   public static String replace$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceFirst(@NotNull String $this$replaceFirst, char oldChar, char newChar, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$replaceFirst, "<this>");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceFirst, oldChar, 0, ignoreCase, 2, (Object)null);
      String var10000;
      if (index < 0) {
         var10000 = $this$replaceFirst;
      } else {
         int var6 = index + 1;
         CharSequence var7 = (CharSequence)String.valueOf(newChar);
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceFirst, index, var6, var7).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceFirst$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceFirst(@NotNull String $this$replaceFirst, @NotNull String oldValue, @NotNull String newValue, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$replaceFirst, "<this>");
      Intrinsics.checkNotNullParameter(oldValue, "oldValue");
      Intrinsics.checkNotNullParameter(newValue, "newValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceFirst, oldValue, 0, ignoreCase, 2, (Object)null);
      String var10000;
      if (index < 0) {
         var10000 = $this$replaceFirst;
      } else {
         int var6 = index + oldValue.length();
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceFirst, index, var6, (CharSequence)newValue).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceFirst$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use uppercase() instead.",
      replaceWith = @ReplaceWith(
   expression = "uppercase(Locale.getDefault())",
   imports = {"java.util.Locale"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @InlineOnly
   private static final String toUpperCase(String $this$toUpperCase) {
      Intrinsics.checkNotNullParameter($this$toUpperCase, "<this>");
      String var1 = $this$toUpperCase.toUpperCase();
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.String).toUpperCase()");
      return var1;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final String uppercase(String $this$uppercase) {
      Intrinsics.checkNotNullParameter($this$uppercase, "<this>");
      String var1 = $this$uppercase.toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.String).toUpperCase(Locale.ROOT)");
      return var1;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use lowercase() instead.",
      replaceWith = @ReplaceWith(
   expression = "lowercase(Locale.getDefault())",
   imports = {"java.util.Locale"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @InlineOnly
   private static final String toLowerCase(String $this$toLowerCase) {
      Intrinsics.checkNotNullParameter($this$toLowerCase, "<this>");
      String var1 = $this$toLowerCase.toLowerCase();
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.String).toLowerCase()");
      return var1;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final String lowercase(String $this$lowercase) {
      Intrinsics.checkNotNullParameter($this$lowercase, "<this>");
      String var1 = $this$lowercase.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      return var1;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @NotNull
   public static final String concatToString(@NotNull char[] $this$concatToString) {
      Intrinsics.checkNotNullParameter($this$concatToString, "<this>");
      return new String($this$concatToString);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @NotNull
   public static final String concatToString(@NotNull char[] $this$concatToString, int startIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$concatToString, "<this>");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$concatToString.length);
      int var3 = endIndex - startIndex;
      return new String($this$concatToString, startIndex, var3);
   }

   // $FF: synthetic method
   public static String concatToString$default(char[] var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length;
      }

      return StringsKt.concatToString(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @NotNull
   public static final char[] toCharArray(@NotNull String $this$toCharArray, int startIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$toCharArray, "<this>");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$toCharArray.length());
      char[] var4 = new char[endIndex - startIndex];
      byte var5 = 0;
      $this$toCharArray.getChars(startIndex, endIndex, var4, var5);
      return var4;
   }

   // $FF: synthetic method
   public static char[] toCharArray$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return StringsKt.toCharArray(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @NotNull
   public static final String decodeToString(@NotNull byte[] $this$decodeToString) {
      Intrinsics.checkNotNullParameter($this$decodeToString, "<this>");
      return new String($this$decodeToString, Charsets.UTF_8);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @NotNull
   public static final String decodeToString(@NotNull byte[] $this$decodeToString, int startIndex, int endIndex, boolean throwOnInvalidSequence) {
      Intrinsics.checkNotNullParameter($this$decodeToString, "<this>");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$decodeToString.length);
      if (!throwOnInvalidSequence) {
         int var6 = endIndex - startIndex;
         return new String($this$decodeToString, startIndex, var6, Charsets.UTF_8);
      } else {
         CharsetDecoder decoder = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
         String var5 = decoder.decode(ByteBuffer.wrap($this$decodeToString, startIndex, endIndex - startIndex)).toString();
         Intrinsics.checkNotNullExpressionValue(var5, "decoder.decode(ByteBuffe…- startIndex)).toString()");
         return var5;
      }
   }

   // $FF: synthetic method
   public static String decodeToString$default(byte[] var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.decodeToString(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @NotNull
   public static final byte[] encodeToByteArray(@NotNull String $this$encodeToByteArray) {
      Intrinsics.checkNotNullParameter($this$encodeToByteArray, "<this>");
      Charset var2 = Charsets.UTF_8;
      byte[] var3 = $this$encodeToByteArray.getBytes(var2);
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).getBytes(charset)");
      return var3;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @NotNull
   public static final byte[] encodeToByteArray(@NotNull String $this$encodeToByteArray, int startIndex, int endIndex, boolean throwOnInvalidSequence) {
      Intrinsics.checkNotNullParameter($this$encodeToByteArray, "<this>");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$encodeToByteArray.length());
      if (!throwOnInvalidSequence) {
         String var10 = $this$encodeToByteArray.substring(startIndex, endIndex);
         Intrinsics.checkNotNullExpressionValue(var10, "this as java.lang.String…ing(startIndex, endIndex)");
         String var9 = var10;
         Charset var11 = Charsets.UTF_8;
         byte[] it = var9.getBytes(var11);
         Intrinsics.checkNotNullExpressionValue(it, "this as java.lang.String).getBytes(charset)");
         return it;
      } else {
         CharsetEncoder encoder = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
         ByteBuffer byteBuffer = encoder.encode(CharBuffer.wrap((CharSequence)$this$encodeToByteArray, startIndex, endIndex));
         byte[] var14;
         if (byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
            int var10000 = byteBuffer.remaining();
            byte[] var10001 = byteBuffer.array();
            Intrinsics.checkNotNull(var10001);
            if (var10000 == var10001.length) {
               byte[] it = byteBuffer.array();
               Intrinsics.checkNotNullExpressionValue(it, "{\n        byteBuffer.array()\n    }");
               var14 = it;
               return var14;
            }
         }

         byte[] it = new byte[byteBuffer.remaining()];
         int var8 = 0;
         byteBuffer.get(it);
         var14 = it;
         return var14;
      }
   }

   // $FF: synthetic method
   public static byte[] encodeToByteArray$default(String var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length();
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.encodeToByteArray(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final char[] toCharArray(String $this$toCharArray) {
      Intrinsics.checkNotNullParameter($this$toCharArray, "<this>");
      char[] var1 = $this$toCharArray.toCharArray();
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.String).toCharArray()");
      return var1;
   }

   @InlineOnly
   private static final char[] toCharArray(String $this$toCharArray, char[] destination, int destinationOffset, int startIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$toCharArray, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      $this$toCharArray.getChars(startIndex, endIndex, destination, destinationOffset);
      return destination;
   }

   // $FF: synthetic method
   static char[] toCharArray$default(String $this$toCharArray_u24default, char[] destination, int destinationOffset, int startIndex, int endIndex, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         destinationOffset = 0;
      }

      if ((var5 & 4) != 0) {
         startIndex = 0;
      }

      if ((var5 & 8) != 0) {
         endIndex = $this$toCharArray_u24default.length();
      }

      Intrinsics.checkNotNullParameter($this$toCharArray_u24default, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      $this$toCharArray_u24default.getChars(startIndex, endIndex, destination, destinationOffset);
      return destination;
   }

   @InlineOnly
   private static final String format(String $this$format, Object... args) {
      Intrinsics.checkNotNullParameter($this$format, "<this>");
      Intrinsics.checkNotNullParameter(args, "args");
      String var2 = String.format($this$format, Arrays.copyOf(args, args.length));
      Intrinsics.checkNotNullExpressionValue(var2, "format(this, *args)");
      return var2;
   }

   @InlineOnly
   private static final String format(StringCompanionObject $this$format, String format, Object... args) {
      Intrinsics.checkNotNullParameter($this$format, "<this>");
      Intrinsics.checkNotNullParameter(format, "format");
      Intrinsics.checkNotNullParameter(args, "args");
      String var3 = String.format(format, Arrays.copyOf(args, args.length));
      Intrinsics.checkNotNullExpressionValue(var3, "format(format, *args)");
      return var3;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use Kotlin compiler 1.4 to avoid deprecation warning."
   )
   @DeprecatedSinceKotlin(
      hiddenSince = "1.4"
   )
   @InlineOnly
   private static final String format(String $this$format, Locale locale, Object... args) {
      Intrinsics.checkNotNullParameter($this$format, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      Intrinsics.checkNotNullParameter(args, "args");
      String var3 = String.format(locale, $this$format, Arrays.copyOf(args, args.length));
      Intrinsics.checkNotNullExpressionValue(var3, "format(locale, this, *args)");
      return var3;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @JvmName(
      name = "formatNullable"
   )
   @InlineOnly
   private static final String formatNullable(String $this$format, Locale locale, Object... args) {
      Intrinsics.checkNotNullParameter($this$format, "<this>");
      Intrinsics.checkNotNullParameter(args, "args");
      String var3 = String.format(locale, $this$format, Arrays.copyOf(args, args.length));
      Intrinsics.checkNotNullExpressionValue(var3, "format(locale, this, *args)");
      return var3;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use Kotlin compiler 1.4 to avoid deprecation warning."
   )
   @DeprecatedSinceKotlin(
      hiddenSince = "1.4"
   )
   @InlineOnly
   private static final String format(StringCompanionObject $this$format, Locale locale, String format, Object... args) {
      Intrinsics.checkNotNullParameter($this$format, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      Intrinsics.checkNotNullParameter(format, "format");
      Intrinsics.checkNotNullParameter(args, "args");
      String var4 = String.format(locale, format, Arrays.copyOf(args, args.length));
      Intrinsics.checkNotNullExpressionValue(var4, "format(locale, format, *args)");
      return var4;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @JvmName(
      name = "formatNullable"
   )
   @InlineOnly
   private static final String formatNullable(StringCompanionObject $this$format, Locale locale, String format, Object... args) {
      Intrinsics.checkNotNullParameter($this$format, "<this>");
      Intrinsics.checkNotNullParameter(format, "format");
      Intrinsics.checkNotNullParameter(args, "args");
      String var4 = String.format(locale, format, Arrays.copyOf(args, args.length));
      Intrinsics.checkNotNullExpressionValue(var4, "format(locale, format, *args)");
      return var4;
   }

   @NotNull
   public static final List split(@NotNull CharSequence $this$split, @NotNull Pattern regex, int limit) {
      Intrinsics.checkNotNullParameter($this$split, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      StringsKt.requireNonNegativeLimit(limit);
      String[] var3 = regex.split($this$split, limit == 0 ? -1 : limit);
      Intrinsics.checkNotNullExpressionValue(var3, "regex.split(this, if (limit == 0) -1 else limit)");
      return ArraysKt.asList((Object[])var3);
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, Pattern var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return StringsKt.split(var0, var1, var2);
   }

   @InlineOnly
   private static final String substring(String $this$substring, int startIndex) {
      Intrinsics.checkNotNullParameter($this$substring, "<this>");
      String var2 = $this$substring.substring(startIndex);
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).substring(startIndex)");
      return var2;
   }

   @InlineOnly
   private static final String substring(String $this$substring, int startIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$substring, "<this>");
      String var3 = $this$substring.substring(startIndex, endIndex);
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String…ing(startIndex, endIndex)");
      return var3;
   }

   public static final boolean startsWith(@NotNull String $this$startsWith, @NotNull String prefix, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      return !ignoreCase ? $this$startsWith.startsWith(prefix) : StringsKt.regionMatches($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull String $this$startsWith, @NotNull String prefix, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      return !ignoreCase ? $this$startsWith.startsWith(prefix, startIndex) : StringsKt.regionMatches($this$startsWith, startIndex, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(String var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   public static final boolean endsWith(@NotNull String $this$endsWith, @NotNull String suffix, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$endsWith, "<this>");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      return !ignoreCase ? $this$endsWith.endsWith(suffix) : StringsKt.regionMatches($this$endsWith, $this$endsWith.length() - suffix.length(), suffix, 0, suffix.length(), true);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   @InlineOnly
   private static final String String(byte[] bytes, int offset, int length, Charset charset) {
      Intrinsics.checkNotNullParameter(bytes, "bytes");
      Intrinsics.checkNotNullParameter(charset, "charset");
      return new String(bytes, offset, length, charset);
   }

   @InlineOnly
   private static final String String(byte[] bytes, Charset charset) {
      Intrinsics.checkNotNullParameter(bytes, "bytes");
      Intrinsics.checkNotNullParameter(charset, "charset");
      return new String(bytes, charset);
   }

   @InlineOnly
   private static final String String(byte[] bytes, int offset, int length) {
      Intrinsics.checkNotNullParameter(bytes, "bytes");
      return new String(bytes, offset, length, Charsets.UTF_8);
   }

   @InlineOnly
   private static final String String(byte[] bytes) {
      Intrinsics.checkNotNullParameter(bytes, "bytes");
      return new String(bytes, Charsets.UTF_8);
   }

   @InlineOnly
   private static final String String(char[] chars) {
      Intrinsics.checkNotNullParameter(chars, "chars");
      return new String(chars);
   }

   @InlineOnly
   private static final String String(char[] chars, int offset, int length) {
      Intrinsics.checkNotNullParameter(chars, "chars");
      return new String(chars, offset, length);
   }

   @InlineOnly
   private static final String String(int[] codePoints, int offset, int length) {
      Intrinsics.checkNotNullParameter(codePoints, "codePoints");
      return new String(codePoints, offset, length);
   }

   @InlineOnly
   private static final String String(StringBuffer stringBuffer) {
      Intrinsics.checkNotNullParameter(stringBuffer, "stringBuffer");
      return new String(stringBuffer);
   }

   @InlineOnly
   private static final String String(StringBuilder stringBuilder) {
      Intrinsics.checkNotNullParameter(stringBuilder, "stringBuilder");
      return new String(stringBuilder);
   }

   @InlineOnly
   private static final int codePointAt(String $this$codePointAt, int index) {
      Intrinsics.checkNotNullParameter($this$codePointAt, "<this>");
      return $this$codePointAt.codePointAt(index);
   }

   @InlineOnly
   private static final int codePointBefore(String $this$codePointBefore, int index) {
      Intrinsics.checkNotNullParameter($this$codePointBefore, "<this>");
      return $this$codePointBefore.codePointBefore(index);
   }

   @InlineOnly
   private static final int codePointCount(String $this$codePointCount, int beginIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$codePointCount, "<this>");
      return $this$codePointCount.codePointCount(beginIndex, endIndex);
   }

   public static final int compareTo(@NotNull String $this$compareTo, @NotNull String other, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$compareTo, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      return ignoreCase ? $this$compareTo.compareToIgnoreCase(other) : $this$compareTo.compareTo(other);
   }

   // $FF: synthetic method
   public static int compareTo$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.compareTo(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean contentEquals(String $this$contentEquals, CharSequence charSequence) {
      Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
      Intrinsics.checkNotNullParameter(charSequence, "charSequence");
      return $this$contentEquals.contentEquals(charSequence);
   }

   @InlineOnly
   private static final boolean contentEquals(String $this$contentEquals, StringBuffer stringBuilder) {
      Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
      Intrinsics.checkNotNullParameter(stringBuilder, "stringBuilder");
      return $this$contentEquals.contentEquals(stringBuilder);
   }

   @SinceKotlin(
      version = "1.5"
   )
   public static final boolean contentEquals(@Nullable CharSequence $this$contentEquals, @Nullable CharSequence other) {
      boolean var10000;
      if ($this$contentEquals instanceof String && other != null) {
         String var2 = (String)$this$contentEquals;
         var10000 = var2.contentEquals(other);
      } else {
         var10000 = StringsKt.contentEqualsImpl($this$contentEquals, other);
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.5"
   )
   public static final boolean contentEquals(@Nullable CharSequence $this$contentEquals, @Nullable CharSequence other, boolean ignoreCase) {
      return ignoreCase ? StringsKt.contentEqualsIgnoreCaseImpl($this$contentEquals, other) : StringsKt.contentEquals($this$contentEquals, other);
   }

   @InlineOnly
   private static final String intern(String $this$intern) {
      Intrinsics.checkNotNullParameter($this$intern, "<this>");
      String var1 = $this$intern.intern();
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.String).intern()");
      return var1;
   }

   public static final boolean isBlank(@NotNull CharSequence $this$isBlank) {
      Intrinsics.checkNotNullParameter($this$isBlank, "<this>");
      boolean var7;
      if ($this$isBlank.length() != 0) {
         Iterable $this$all$iv = StringsKt.getIndices($this$isBlank);
         int $i$f$all = 0;
         if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
            var7 = true;
         } else {
            Iterator var3 = $this$all$iv.iterator();

            while(true) {
               if (!var3.hasNext()) {
                  var7 = true;
                  break;
               }

               int element$iv = ((IntIterator)var3).nextInt();
               int var6 = 0;
               if (!CharsKt.isWhitespace($this$isBlank.charAt(element$iv))) {
                  var7 = false;
                  break;
               }
            }
         }

         if (!var7) {
            var7 = false;
            return var7;
         }
      }

      var7 = true;
      return var7;
   }

   @InlineOnly
   private static final int offsetByCodePoints(String $this$offsetByCodePoints, int index, int codePointOffset) {
      Intrinsics.checkNotNullParameter($this$offsetByCodePoints, "<this>");
      return $this$offsetByCodePoints.offsetByCodePoints(index, codePointOffset);
   }

   public static final boolean regionMatches(@NotNull CharSequence $this$regionMatches, int thisOffset, @NotNull CharSequence other, int otherOffset, int length, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$regionMatches, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      return $this$regionMatches instanceof String && other instanceof String ? StringsKt.regionMatches((String)$this$regionMatches, thisOffset, (String)other, otherOffset, length, ignoreCase) : StringsKt.regionMatchesImpl($this$regionMatches, thisOffset, other, otherOffset, length, ignoreCase);
   }

   // $FF: synthetic method
   public static boolean regionMatches$default(CharSequence var0, int var1, CharSequence var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   public static final boolean regionMatches(@NotNull String $this$regionMatches, int thisOffset, @NotNull String other, int otherOffset, int length, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$regionMatches, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      return !ignoreCase ? $this$regionMatches.regionMatches(thisOffset, other, otherOffset, length) : $this$regionMatches.regionMatches(ignoreCase, thisOffset, other, otherOffset, length);
   }

   // $FF: synthetic method
   public static boolean regionMatches$default(String var0, int var1, String var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use lowercase() instead.",
      replaceWith = @ReplaceWith(
   expression = "lowercase(locale)",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @InlineOnly
   private static final String toLowerCase(String $this$toLowerCase, Locale locale) {
      Intrinsics.checkNotNullParameter($this$toLowerCase, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      String var3 = $this$toLowerCase.toLowerCase(locale);
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(locale)");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final String lowercase(String $this$lowercase, Locale locale) {
      Intrinsics.checkNotNullParameter($this$lowercase, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      String var2 = $this$lowercase.toLowerCase(locale);
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toLowerCase(locale)");
      return var2;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use uppercase() instead.",
      replaceWith = @ReplaceWith(
   expression = "uppercase(locale)",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @InlineOnly
   private static final String toUpperCase(String $this$toUpperCase, Locale locale) {
      Intrinsics.checkNotNullParameter($this$toUpperCase, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      String var3 = $this$toUpperCase.toUpperCase(locale);
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toUpperCase(locale)");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final String uppercase(String $this$uppercase, Locale locale) {
      Intrinsics.checkNotNullParameter($this$uppercase, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      String var2 = $this$uppercase.toUpperCase(locale);
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toUpperCase(locale)");
      return var2;
   }

   @InlineOnly
   private static final byte[] toByteArray(String $this$toByteArray, Charset charset) {
      Intrinsics.checkNotNullParameter($this$toByteArray, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var2 = $this$toByteArray.getBytes(charset);
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).getBytes(charset)");
      return var2;
   }

   // $FF: synthetic method
   static byte[] toByteArray$default(String $this$toByteArray_u24default, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      Intrinsics.checkNotNullParameter($this$toByteArray_u24default, "<this>");
      Intrinsics.checkNotNullParameter(charset, "charset");
      byte[] var4 = $this$toByteArray_u24default.getBytes(charset);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).getBytes(charset)");
      return var4;
   }

   @InlineOnly
   private static final Pattern toPattern(String $this$toPattern, int flags) {
      Intrinsics.checkNotNullParameter($this$toPattern, "<this>");
      Pattern var2 = Pattern.compile($this$toPattern, flags);
      Intrinsics.checkNotNullExpressionValue(var2, "compile(this, flags)");
      return var2;
   }

   // $FF: synthetic method
   static Pattern toPattern$default(String $this$toPattern_u24default, int flags, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         flags = 0;
      }

      Intrinsics.checkNotNullParameter($this$toPattern_u24default, "<this>");
      Pattern var4 = Pattern.compile($this$toPattern_u24default, flags);
      Intrinsics.checkNotNullExpressionValue(var4, "compile(this, flags)");
      return var4;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use replaceFirstChar instead.",
      replaceWith = @ReplaceWith(
   expression = "replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }",
   imports = {"java.util.Locale"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @NotNull
   public static final String capitalize(@NotNull String $this$capitalize) {
      Intrinsics.checkNotNullParameter($this$capitalize, "<this>");
      Locale var1 = Locale.getDefault();
      Intrinsics.checkNotNullExpressionValue(var1, "getDefault()");
      return StringsKt.capitalize($this$capitalize, var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use replaceFirstChar instead.",
      replaceWith = @ReplaceWith(
   expression = "replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @LowPriorityInOverloadResolution
   @NotNull
   public static final String capitalize(@NotNull String $this$capitalize, @NotNull Locale locale) {
      Intrinsics.checkNotNullParameter($this$capitalize, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      if (((CharSequence)$this$capitalize).length() > 0) {
         char firstChar = $this$capitalize.charAt(0);
         if (Character.isLowerCase(firstChar)) {
            StringBuilder var3 = new StringBuilder();
            int var5 = 0;
            char titleChar = Character.toTitleCase(firstChar);
            if (titleChar != Character.toUpperCase(firstChar)) {
               var3.append(titleChar);
            } else {
               byte var8 = 0;
               byte var9 = 1;
               String var10 = $this$capitalize.substring(var8, var9);
               Intrinsics.checkNotNullExpressionValue(var10, "this as java.lang.String…ing(startIndex, endIndex)");
               String var12 = var10.toUpperCase(locale);
               Intrinsics.checkNotNullExpressionValue(var12, "this as java.lang.String).toUpperCase(locale)");
               var3.append(var12);
            }

            byte var13 = 1;
            String var14 = $this$capitalize.substring(var13);
            Intrinsics.checkNotNullExpressionValue(var14, "this as java.lang.String).substring(startIndex)");
            var3.append(var14);
            String var11 = var3.toString();
            Intrinsics.checkNotNullExpressionValue(var11, "StringBuilder().apply(builderAction).toString()");
            return var11;
         }
      }

      return $this$capitalize;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use replaceFirstChar instead.",
      replaceWith = @ReplaceWith(
   expression = "replaceFirstChar { it.lowercase(Locale.getDefault()) }",
   imports = {"java.util.Locale"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @NotNull
   public static final String decapitalize(@NotNull String $this$decapitalize) {
      Intrinsics.checkNotNullParameter($this$decapitalize, "<this>");
      String var10000;
      if (((CharSequence)$this$decapitalize).length() > 0 && !Character.isLowerCase($this$decapitalize.charAt(0))) {
         byte var2 = 0;
         byte var3 = 1;
         String var4 = $this$decapitalize.substring(var2, var3);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String…ing(startIndex, endIndex)");
         String var5 = var4.toLowerCase();
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase()");
         var10000 = var5;
         byte var6 = 1;
         String var7 = $this$decapitalize.substring(var6);
         Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String).substring(startIndex)");
         var10000 = Intrinsics.stringPlus(var10000, var7);
      } else {
         var10000 = $this$decapitalize;
      }

      return var10000;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use replaceFirstChar instead.",
      replaceWith = @ReplaceWith(
   expression = "replaceFirstChar { it.lowercase(locale) }",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @LowPriorityInOverloadResolution
   @NotNull
   public static final String decapitalize(@NotNull String $this$decapitalize, @NotNull Locale locale) {
      Intrinsics.checkNotNullParameter($this$decapitalize, "<this>");
      Intrinsics.checkNotNullParameter(locale, "locale");
      String var10000;
      if (((CharSequence)$this$decapitalize).length() > 0 && !Character.isLowerCase($this$decapitalize.charAt(0))) {
         byte var3 = 0;
         byte var4 = 1;
         String var5 = $this$decapitalize.substring(var3, var4);
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String…ing(startIndex, endIndex)");
         String var6 = var5.toLowerCase(locale);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).toLowerCase(locale)");
         var10000 = var6;
         byte var7 = 1;
         String var8 = $this$decapitalize.substring(var7);
         Intrinsics.checkNotNullExpressionValue(var8, "this as java.lang.String).substring(startIndex)");
         var10000 = Intrinsics.stringPlus(var10000, var8);
      } else {
         var10000 = $this$decapitalize;
      }

      return var10000;
   }

   @NotNull
   public static final String repeat(@NotNull CharSequence $this$repeat, int n) {
      Intrinsics.checkNotNullParameter($this$repeat, "<this>");
      boolean var2 = n >= 0;
      if (!var2) {
         int var12 = 0;
         String var13 = "Count 'n' must be non-negative, but was " + n + '.';
         throw new IllegalArgumentException(var13.toString());
      } else {
         String var10000;
         switch (n) {
            case 0:
               var10000 = "";
               break;
            case 1:
               var10000 = $this$repeat.toString();
               break;
            default:
               int var3 = $this$repeat.length();
               switch (var3) {
                  case 0:
                     var10000 = "";
                     break;
                  case 1:
                     char var4 = $this$repeat.charAt(0);
                     char var5 = var4;
                     int var6 = 0;
                     int var7 = 0;
                     int var8 = n;

                     char[] var9;
                     for(var9 = new char[n]; var7 < var8; ++var7) {
                        var9[var7] = var5;
                     }

                     var10000 = new String(var9);
                     break;
                  default:
                     StringBuilder sb = new StringBuilder(n * $this$repeat.length());
                     int var16 = 1;
                     int i;
                     if (var16 <= n) {
                        do {
                           i = var16++;
                           sb.append($this$repeat);
                        } while(i != n);
                     }

                     String var14 = sb.toString();
                     Intrinsics.checkNotNullExpressionValue(var14, "{\n                    va…tring()\n                }");
                     var10000 = var14;
               }
         }

         return var10000;
      }
   }

   @NotNull
   public static final Comparator getCASE_INSENSITIVE_ORDER(@NotNull StringCompanionObject $this$CASE_INSENSITIVE_ORDER) {
      Intrinsics.checkNotNullParameter($this$CASE_INSENSITIVE_ORDER, "<this>");
      Comparator var1 = String.CASE_INSENSITIVE_ORDER;
      Intrinsics.checkNotNullExpressionValue(var1, "CASE_INSENSITIVE_ORDER");
      return var1;
   }

   public StringsKt__StringsJVMKt() {
   }
}
