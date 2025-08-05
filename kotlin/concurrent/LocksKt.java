package kotlin.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a6\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u001a6\u0010\u0006\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00072\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\b\u001a6\u0010\t\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"},
   d2 = {"read", "T", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "write", "kotlin-stdlib"}
)
@JvmName(
   name = "LocksKt"
)
public final class LocksKt {
   @InlineOnly
   private static final Object withLock(Lock $this$withLock, Function0 action) {
      Intrinsics.checkNotNullParameter($this$withLock, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      $this$withLock.lock();

      Object var2;
      try {
         var2 = action.invoke();
      } finally {
         InlineMarker.finallyStart(1);
         $this$withLock.unlock();
         InlineMarker.finallyEnd(1);
      }

      return var2;
   }

   @InlineOnly
   private static final Object read(ReentrantReadWriteLock $this$read, Function0 action) {
      Intrinsics.checkNotNullParameter($this$read, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      ReentrantReadWriteLock.ReadLock rl = $this$read.readLock();
      rl.lock();

      Object var3;
      try {
         var3 = action.invoke();
      } finally {
         InlineMarker.finallyStart(1);
         rl.unlock();
         InlineMarker.finallyEnd(1);
      }

      return var3;
   }

   @InlineOnly
   private static final Object write(ReentrantReadWriteLock $this$write, Function0 action) {
      Intrinsics.checkNotNullParameter($this$write, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      ReentrantReadWriteLock.ReadLock rl = $this$write.readLock();
      int readCount = $this$write.getWriteHoldCount() == 0 ? $this$write.getReadHoldCount() : 0;
      int var4 = 0;

      while(var4 < readCount) {
         ++var4;
         int var7 = 0;
         rl.unlock();
      }

      ReentrantReadWriteLock.WriteLock wl = $this$write.writeLock();
      wl.lock();

      Object var5;
      try {
         var5 = action.invoke();
      } finally {
         InlineMarker.finallyStart(1);
         int it = 0;

         while(it < readCount) {
            ++it;
            int var9 = 0;
            rl.lock();
         }

         wl.unlock();
         InlineMarker.finallyEnd(1);
      }

      return var5;
   }
}
