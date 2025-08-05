package kotlin.io;

import kotlin.Metadata;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
   d2 = {"Lkotlin/io/FileWalkDirection;", "", "(Ljava/lang/String;I)V", "TOP_DOWN", "BOTTOM_UP", "kotlin-stdlib"}
)
public enum FileWalkDirection {
   TOP_DOWN,
   BOTTOM_UP;

   // $FF: synthetic method
   private static final FileWalkDirection[] $values() {
      FileWalkDirection[] var0 = new FileWalkDirection[]{TOP_DOWN, BOTTOM_UP};
      return var0;
   }
}
