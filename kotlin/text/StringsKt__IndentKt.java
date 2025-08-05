package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"},
   d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__IndentKt extends StringsKt__AppendableKt {
   @NotNull
   public static final String trimMargin(@NotNull String $this$trimMargin, @NotNull String marginPrefix) {
      Intrinsics.checkNotNullParameter($this$trimMargin, "<this>");
      Intrinsics.checkNotNullParameter(marginPrefix, "marginPrefix");
      return StringsKt.replaceIndentByMargin($this$trimMargin, "", marginPrefix);
   }

   // $FF: synthetic method
   public static String trimMargin$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "|";
      }

      return StringsKt.trimMargin(var0, var1);
   }

   @NotNull
   public static final String replaceIndentByMargin(@NotNull String $this$replaceIndentByMargin, @NotNull String newIndent, @NotNull String marginPrefix) {
      Intrinsics.checkNotNullParameter($this$replaceIndentByMargin, "<this>");
      Intrinsics.checkNotNullParameter(newIndent, "newIndent");
      Intrinsics.checkNotNullParameter(marginPrefix, "marginPrefix");
      boolean var3 = !StringsKt.isBlank((CharSequence)marginPrefix);
      if (!var3) {
         int var4 = 0;
         String $this$reindent$iv = "marginPrefix must be non-blank string.";
         throw new IllegalArgumentException($this$reindent$iv.toString());
      } else {
         List lines = StringsKt.lines((CharSequence)$this$replaceIndentByMargin);
         int resultSizeEstimate$iv = $this$replaceIndentByMargin.length() + newIndent.length() * lines.size();
         Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
         int $i$f$reindent = 0;
         int lastIndex$iv = CollectionsKt.getLastIndex(lines);
         Iterable $this$mapIndexedNotNull$iv$iv = (Iterable)lines;
         int $i$f$mapIndexedNotNull = 0;
         Collection destination$iv$iv$iv = (Collection)(new ArrayList());
         int $i$f$mapIndexedNotNullTo = 0;
         int $i$f$forEachIndexed = 0;
         int index$iv$iv$iv$iv = 0;

         for(Object item$iv$iv$iv$iv : $this$mapIndexedNotNull$iv$iv) {
            int index$iv$iv$iv = index$iv$iv$iv$iv++;
            if (index$iv$iv$iv < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            int var22 = 0;
            String value$iv = (String)item$iv$iv$iv$iv;
            int var25 = 0;
            String var48;
            if ((index$iv$iv$iv == 0 || index$iv$iv$iv == lastIndex$iv) && StringsKt.isBlank((CharSequence)value$iv)) {
               var48 = null;
            } else {
               int var27 = 0;
               CharSequence $this$indexOfFirst$iv = (CharSequence)value$iv;
               int $i$f$indexOfFirst = 0;
               int var30 = 0;
               int var31 = $this$indexOfFirst$iv.length();

               while(true) {
                  if (var30 < var31) {
                     int index$iv = var30++;
                     char it = $this$indexOfFirst$iv.charAt(index$iv);
                     int var34 = 0;
                     if (CharsKt.isWhitespace(it)) {
                        continue;
                     }

                     var10000 = index$iv;
                     break;
                  }

                  var10000 = -1;
                  break;
               }

               int firstNonWhitespaceIndex = var10000;
               if (firstNonWhitespaceIndex == -1) {
                  var48 = null;
               } else if (StringsKt.startsWith$default(value$iv, marginPrefix, firstNonWhitespaceIndex, false, 4, (Object)null)) {
                  $i$f$indexOfFirst = firstNonWhitespaceIndex + marginPrefix.length();
                  String var46 = value$iv.substring($i$f$indexOfFirst);
                  Intrinsics.checkNotNullExpressionValue(var46, "this as java.lang.String).substring(startIndex)");
                  var48 = var46;
               } else {
                  var48 = null;
               }

               String var36 = var48;
               var48 = var36 == null ? value$iv : (String)indentAddFunction$iv.invoke(var36);
            }

            String it$iv$iv$iv = var48;
            if (it$iv$iv$iv != null) {
               int var41 = 0;
               destination$iv$iv$iv.add(it$iv$iv$iv);
            }
         }

         String var42 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)destination$iv$iv$iv), (Appendable)(new StringBuilder(resultSizeEstimate$iv)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
         Intrinsics.checkNotNullExpressionValue(var42, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
         return var42;
      }
   }

   // $FF: synthetic method
   public static String replaceIndentByMargin$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = "";
      }

      if ((var3 & 2) != 0) {
         var2 = "|";
      }

      return StringsKt.replaceIndentByMargin(var0, var1, var2);
   }

   @NotNull
   public static final String trimIndent(@NotNull String $this$trimIndent) {
      Intrinsics.checkNotNullParameter($this$trimIndent, "<this>");
      return StringsKt.replaceIndent($this$trimIndent, "");
   }

   @NotNull
   public static final String replaceIndent(@NotNull String $this$replaceIndent, @NotNull String newIndent) {
      Intrinsics.checkNotNullParameter($this$replaceIndent, "<this>");
      Intrinsics.checkNotNullParameter(newIndent, "newIndent");
      List lines = StringsKt.lines((CharSequence)$this$replaceIndent);
      Iterable $this$filter$iv = (Iterable)lines;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         String p0 = (String)element$iv$iv;
         int var13 = 0;
         if (!StringsKt.isBlank((CharSequence)p0)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$filter$iv = (Iterable)((List)destination$iv$iv);
      $i$f$filter = 0;
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10)));
      $i$f$filterTo = 0;

      for(Object item$iv$iv : $this$filter$iv) {
         String p0 = (String)item$iv$iv;
         int var50 = 0;
         Integer var36 = indentWidth$StringsKt__IndentKt(p0);
         destination$iv$iv.add(var36);
      }

      Integer var4 = (Integer)CollectionsKt.minOrNull((Iterable)((List)destination$iv$iv));
      int minCommonIndent = var4 == null ? 0 : var4;
      int resultSizeEstimate$iv = $this$replaceIndent.length() + newIndent.length() * lines.size();
      Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
      int $i$f$reindent = 0;
      int lastIndex$iv = CollectionsKt.getLastIndex(lines);
      Iterable $this$mapIndexedNotNull$iv$iv = (Iterable)lines;
      int $i$f$mapIndexedNotNull = 0;
      Collection destination$iv$iv$iv = (Collection)(new ArrayList());
      int $i$f$mapIndexedNotNullTo = 0;
      int $i$f$forEachIndexed = 0;
      int index$iv$iv$iv$iv = 0;

      for(Object item$iv$iv$iv$iv : $this$mapIndexedNotNull$iv$iv) {
         int index$iv$iv$iv = index$iv$iv$iv$iv++;
         if (index$iv$iv$iv < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         int var22 = 0;
         String value$iv = (String)item$iv$iv$iv$iv;
         int var25 = 0;
         String var10000;
         if ((index$iv$iv$iv == 0 || index$iv$iv$iv == lastIndex$iv) && StringsKt.isBlank((CharSequence)value$iv)) {
            var10000 = null;
         } else {
            int var27 = 0;
            String var28 = StringsKt.drop(value$iv, minCommonIndent);
            var10000 = var28 == null ? value$iv : (String)indentAddFunction$iv.invoke(var28);
         }

         String it$iv$iv$iv = var10000;
         if (it$iv$iv$iv != null) {
            int var33 = 0;
            destination$iv$iv$iv.add(it$iv$iv$iv);
         }
      }

      String var34 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)destination$iv$iv$iv), (Appendable)(new StringBuilder(resultSizeEstimate$iv)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
      Intrinsics.checkNotNullExpressionValue(var34, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return var34;
   }

   // $FF: synthetic method
   public static String replaceIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "";
      }

      return StringsKt.replaceIndent(var0, var1);
   }

   @NotNull
   public static final String prependIndent(@NotNull String $this$prependIndent, @NotNull final String indent) {
      Intrinsics.checkNotNullParameter($this$prependIndent, "<this>");
      Intrinsics.checkNotNullParameter(indent, "indent");
      return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence((CharSequence)$this$prependIndent), new Function1() {
         @NotNull
         public final String invoke(@NotNull String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return StringsKt.isBlank((CharSequence)it) ? (it.length() < indent.length() ? indent : it) : Intrinsics.stringPlus(indent, it);
         }
      }), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
   }

   // $FF: synthetic method
   public static String prependIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "    ";
      }

      return StringsKt.prependIndent(var0, var1);
   }

   private static final int indentWidth$StringsKt__IndentKt(String $this$indentWidth) {
      CharSequence $this$indexOfFirst$iv = (CharSequence)$this$indentWidth;
      int $i$f$indexOfFirst = 0;
      int var3 = 0;
      int var4 = $this$indexOfFirst$iv.length();

      int var10000;
      while(true) {
         if (var3 < var4) {
            int index$iv = var3++;
            char it = $this$indexOfFirst$iv.charAt(index$iv);
            int var7 = 0;
            if (CharsKt.isWhitespace(it)) {
               continue;
            }

            var10000 = index$iv;
            break;
         }

         var10000 = -1;
         break;
      }

      int it = var10000;
      var3 = 0;
      return it == -1 ? $this$indentWidth.length() : it;
   }

   private static final Function1 getIndentFunction$StringsKt__IndentKt(final String indent) {
      return ((CharSequence)indent).length() == 0 ? (Function1)null.INSTANCE : (Function1)(new Function1() {
         @NotNull
         public final String invoke(@NotNull String line) {
            Intrinsics.checkNotNullParameter(line, "line");
            return Intrinsics.stringPlus(indent, line);
         }
      });
   }

   private static final String reindent$StringsKt__IndentKt(List $this$reindent, int resultSizeEstimate, Function1 indentAddFunction, Function1 indentCutFunction) {
      int $i$f$reindent = 0;
      int lastIndex = CollectionsKt.getLastIndex($this$reindent);
      Iterable $this$mapIndexedNotNull$iv = (Iterable)$this$reindent;
      int $i$f$mapIndexedNotNull = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$mapIndexedNotNullTo = 0;
      int $i$f$forEachIndexed = 0;
      int index$iv$iv$iv = 0;

      for(Object item$iv$iv$iv : $this$mapIndexedNotNull$iv) {
         int index$iv$iv = index$iv$iv$iv++;
         if (index$iv$iv < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         int var20 = 0;
         String value = (String)item$iv$iv$iv;
         int var23 = 0;
         String var10000;
         if ((index$iv$iv == 0 || index$iv$iv == lastIndex) && StringsKt.isBlank((CharSequence)value)) {
            var10000 = null;
         } else {
            String var24 = (String)indentCutFunction.invoke(value);
            var10000 = var24 == null ? value : (String)indentAddFunction.invoke(var24);
         }

         String it$iv$iv = var10000;
         if (it$iv$iv != null) {
            int var29 = 0;
            destination$iv$iv.add(it$iv$iv);
         }
      }

      String var6 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)destination$iv$iv), (Appendable)(new StringBuilder(resultSizeEstimate)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
      Intrinsics.checkNotNullExpressionValue(var6, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return var6;
   }

   public StringsKt__IndentKt() {
   }
}
