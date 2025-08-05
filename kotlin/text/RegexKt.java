package kotlin.text;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000>\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002¨\u0006\u0014"},
   d2 = {"fromInt", "", "T", "Lkotlin/text/FlagEnum;", "", "value", "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", "input", "", "matchEntire", "range", "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"}
)
public final class RegexKt {
   private static final int toInt(Iterable $this$toInt) {
      int initial$iv = 0;
      int $i$f$fold = 0;
      int accumulator$iv = initial$iv;

      for(Object element$iv : $this$toInt) {
         FlagEnum option = (FlagEnum)element$iv;
         int var9 = 0;
         accumulator$iv |= option.getValue();
      }

      return accumulator$iv;
   }

   // $FF: synthetic method
   private static final Set fromInt(final int value) {
      int $i$f$fromInt = 0;
      Intrinsics.reifiedOperationMarker(4, "T");
      EnumSet var3 = EnumSet.allOf(Enum.class);
      EnumSet $this$fromInt_u24lambda_u2d1 = var3;
      int var5 = 0;
      Intrinsics.checkNotNullExpressionValue($this$fromInt_u24lambda_u2d1, "");
      Iterable var10000 = (Iterable)$this$fromInt_u24lambda_u2d1;
      Intrinsics.needClassReification();
      CollectionsKt.retainAll(var10000, new Function1() {
         @NotNull
         public final Boolean invoke(Enum it) {
            return (value & ((FlagEnum)it).getMask()) == ((FlagEnum)it).getValue();
         }
      });
      Set var2 = Collections.unmodifiableSet((Set)var3);
      Intrinsics.checkNotNullExpressionValue(var2, "unmodifiableSet(EnumSet.…mask == it.value }\n    })");
      return var2;
   }

   private static final MatchResult findNext(Matcher $this$findNext, int from, CharSequence input) {
      return !$this$findNext.find(from) ? null : (MatchResult)(new MatcherMatchResult($this$findNext, input));
   }

   private static final MatchResult matchEntire(Matcher $this$matchEntire, CharSequence input) {
      return !$this$matchEntire.matches() ? null : (MatchResult)(new MatcherMatchResult($this$matchEntire, input));
   }

   private static final IntRange range(java.util.regex.MatchResult $this$range) {
      return RangesKt.until($this$range.start(), $this$range.end());
   }

   private static final IntRange range(java.util.regex.MatchResult $this$range, int groupIndex) {
      return RangesKt.until($this$range.start(groupIndex), $this$range.end(groupIndex));
   }

   // $FF: synthetic method
   public static final int access$toInt(Iterable $receiver) {
      return toInt($receiver);
   }

   // $FF: synthetic method
   public static final MatchResult access$findNext(Matcher $receiver, int from, CharSequence input) {
      return findNext($receiver, from, input);
   }

   // $FF: synthetic method
   public static final MatchResult access$matchEntire(Matcher $receiver, CharSequence input) {
      return matchEntire($receiver, input);
   }

   // $FF: synthetic method
   public static final IntRange access$range(java.util.regex.MatchResult $receiver) {
      return range($receiver);
   }

   // $FF: synthetic method
   public static final IntRange access$range(java.util.regex.MatchResult $receiver, int groupIndex) {
      return range($receiver, groupIndex);
   }
}
