package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0017H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/PacketCounterUtils;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "avgInBound", "", "getAvgInBound", "()I", "setAvgInBound", "(I)V", "avgOutBound", "getAvgOutBound", "setAvgOutBound", "inBound", "outBound", "packetTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "handleEvents", "", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "CrossSine"}
)
public final class PacketCounterUtils implements Listenable {
   @NotNull
   public static final PacketCounterUtils INSTANCE = new PacketCounterUtils();
   private static int inBound;
   private static int outBound;
   private static int avgInBound;
   private static int avgOutBound;
   @NotNull
   private static final MSTimer packetTimer;

   private PacketCounterUtils() {
   }

   public final int getAvgInBound() {
      return avgInBound;
   }

   public final void setAvgInBound(int var1) {
      avgInBound = var1;
   }

   public final int getAvgOutBound() {
      return avgOutBound;
   }

   public final void setAvgOutBound(int var1) {
      avgOutBound = var1;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.isServerSide()) {
         int var2 = inBound++;
      } else {
         int var3 = outBound++;
      }

   }

   @EventTarget
   public final void onTick(@NotNull TickEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (packetTimer.hasTimePassed(1000L)) {
         avgInBound = inBound;
         avgOutBound = outBound;
         outBound = 0;
         inBound = 0;
         packetTimer.reset();
      }

   }

   public boolean handleEvents() {
      return true;
   }

   static {
      CrossSine.INSTANCE.getEventManager().registerListener(INSTANCE);
      packetTimer = new MSTimer();
   }
}
