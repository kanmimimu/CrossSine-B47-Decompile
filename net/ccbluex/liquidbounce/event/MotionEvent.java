package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001d\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n¢\u0006\u0002\u0010\rJ\u0006\u0010*\u001a\u00020\nJ\u0006\u0010+\u001a\u00020\nR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017R\u001a\u0010\u000b\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0015\"\u0004\b\u001f\u0010\u0017R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010!\"\u0004\b%\u0010#R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0019\"\u0004\b'\u0010\u001bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010!\"\u0004\b)\u0010#¨\u0006,"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/MotionEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "x", "", "y", "z", "yaw", "", "pitch", "onGround", "", "sprint", "sneak", "(DDDFFZZZ)V", "eventState", "Lnet/ccbluex/liquidbounce/event/EventState;", "getEventState", "()Lnet/ccbluex/liquidbounce/event/EventState;", "setEventState", "(Lnet/ccbluex/liquidbounce/event/EventState;)V", "getOnGround", "()Z", "setOnGround", "(Z)V", "getPitch", "()F", "setPitch", "(F)V", "getSneak", "setSneak", "getSprint", "setSprint", "getX", "()D", "setX", "(D)V", "getY", "setY", "getYaw", "setYaw", "getZ", "setZ", "isPost", "isPre", "CrossSine"}
)
public final class MotionEvent extends Event {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;
   private boolean onGround;
   private boolean sprint;
   private boolean sneak;
   @NotNull
   private EventState eventState;

   public MotionEvent(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean sprint, boolean sneak) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.yaw = yaw;
      this.pitch = pitch;
      this.onGround = onGround;
      this.sprint = sprint;
      this.sneak = sneak;
      this.eventState = EventState.PRE;
   }

   public final double getX() {
      return this.x;
   }

   public final void setX(double var1) {
      this.x = var1;
   }

   public final double getY() {
      return this.y;
   }

   public final void setY(double var1) {
      this.y = var1;
   }

   public final double getZ() {
      return this.z;
   }

   public final void setZ(double var1) {
      this.z = var1;
   }

   public final float getYaw() {
      return this.yaw;
   }

   public final void setYaw(float var1) {
      this.yaw = var1;
   }

   public final float getPitch() {
      return this.pitch;
   }

   public final void setPitch(float var1) {
      this.pitch = var1;
   }

   public final boolean getOnGround() {
      return this.onGround;
   }

   public final void setOnGround(boolean var1) {
      this.onGround = var1;
   }

   public final boolean getSprint() {
      return this.sprint;
   }

   public final void setSprint(boolean var1) {
      this.sprint = var1;
   }

   public final boolean getSneak() {
      return this.sneak;
   }

   public final void setSneak(boolean var1) {
      this.sneak = var1;
   }

   @NotNull
   public final EventState getEventState() {
      return this.eventState;
   }

   public final void setEventState(@NotNull EventState var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.eventState = var1;
   }

   public final boolean isPre() {
      return this.eventState == EventState.PRE;
   }

   public final boolean isPost() {
      return this.eventState == EventState.POST;
   }
}
