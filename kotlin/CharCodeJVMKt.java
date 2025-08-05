package kotlin;

import kotlin.internal.InlineOnly;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001b\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006"},
   d2 = {"Char", "", "code", "Lkotlin/UShort;", "Char-xj2QHRw", "(S)C", "kotlin-stdlib"}
)
public final class CharCodeJVMKt {
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final char Char_xj2QHRw/* $FF was: Char-xj2QHRw*/(short code) {
      return (char)(code & '\uffff');
   }
}
