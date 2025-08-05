package net.ccbluex.liquidbounce.features.module.modules.movement.flights.matrix;

import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\"H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/matrix/MatrixClipFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "blinkTime", "", "clipMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "clipSmart", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "clipTime", "clipTimes", "customBlink", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "customClip", "disableLogger", "hasWarned", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "shouldClip", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timer2", "yclip", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class MatrixClipFlight extends FlightMode {
   @NotNull
   private final ListValue clipMode;
   @NotNull
   private final Value clipSmart;
   @NotNull
   private final IntegerValue customClip;
   @NotNull
   private final IntegerValue customBlink;
   @NotNull
   private final FloatValue yclip;
   @NotNull
   private final LinkedBlockingQueue packets;
   @NotNull
   private final MSTimer timer;
   @NotNull
   private final MSTimer timer2;
   private int blinkTime;
   private int clipTime;
   private int clipTimes;
   private boolean disableLogger;
   private boolean shouldClip;
   private boolean hasWarned;

   public MatrixClipFlight() {
      super("MatrixClip");
      String var10003 = Intrinsics.stringPlus(this.getValuePrefix(), "BypassMode");
      String[] var1 = new String[]{"Clip1", "Clip2", "Clip3", "CustomClip"};
      this.clipMode = new ListValue(var10003, var1, "Clip2");
      this.clipSmart = (new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Clip2-SmartClip"), true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return MatrixClipFlight.this.clipMode.equals("Clip2");
         }
      });
      this.customClip = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-ClipDelay"), 736, 500, 1500);
      this.customBlink = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-BlinkDelay"), 909, 500, 1500);
      this.yclip = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "YClip"), 10.0F, 5.0F, 20.0F);
      this.packets = new LinkedBlockingQueue();
      this.timer = new MSTimer();
      this.timer2 = new MSTimer();
      this.shouldClip = true;
   }

   public void onEnable() {
      this.timer.reset();
      this.timer2.reset();
      this.hasWarned = false;
      this.clipTimes = 0;
      this.shouldClip = true;
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.shouldClip) {
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var4) {
            case "clip1":
               this.blinkTime = 736;
               this.clipTime = 909;
               break;
            case "clip2":
               this.blinkTime = 1000;
               this.clipTime = 909;
               if (this.clipTimes == 2) {
                  if (!(Boolean)this.clipSmart.get()) {
                     if (!this.hasWarned) {
                        this.hasWarned = true;
                     }
                  } else if (this.timer2.hasTimePassed(350L)) {
                     this.shouldClip = false;

                     try {
                        this.disableLogger = true;

                        while(!this.packets.isEmpty()) {
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)this.packets.take());
                        }

                        this.disableLogger = false;
                     } finally {
                        this.disableLogger = false;
                     }
                  }
               }
               break;
            case "clip3":
               this.blinkTime = 909;
               this.clipTime = 1000;
               break;
            case "CustomClip":
               this.blinkTime = ((Number)this.customBlink.get()).intValue();
               this.clipTime = ((Number)this.customClip.get()).intValue();
         }

         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
         if (this.timer.hasTimePassed((long)this.blinkTime)) {
            this.timer.reset();

            try {
               this.disableLogger = true;

               while(!this.packets.isEmpty()) {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)this.packets.take());
               }

               this.disableLogger = false;
            } finally {
               this.disableLogger = false;
            }
         }

         if (this.timer2.hasTimePassed((long)this.clipTime)) {
            this.timer2.reset();
            int var2 = this.clipTimes++;
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + ((Number)this.yclip.get()).doubleValue(), MinecraftInstance.mc.field_71439_g.field_70161_v);
         }

      }
   }

   public void onDisable() {
   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (MinecraftInstance.mc.field_71439_g != null && !this.disableLogger) {
         if (packet instanceof C03PacketPlayer) {
            event.cancelEvent();
         }

         if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook || packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C0APacketAnimation || packet instanceof C0BPacketEntityAction || packet instanceof C02PacketUseEntity) {
            event.cancelEvent();
            this.packets.add(packet);
         }

      }
   }
}
