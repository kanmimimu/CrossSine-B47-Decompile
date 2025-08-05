package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/other/MinemenVelo;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "canCancel", "", "lastCancel", "ticks", "", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class MinemenVelo extends VelocityMode {
   private int ticks;
   private boolean lastCancel;
   private boolean canCancel;

   public MinemenVelo() {
      super("Minemen");
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      int var2 = this.ticks++;
      if (this.ticks > 23) {
         this.canCancel = true;
      }

      var2 = this.ticks;
      if ((2 <= var2 ? var2 < 5 : false) && !this.lastCancel) {
         EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70159_w *= 0.99;
         var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70179_y *= 0.99;
      } else if (this.ticks == 5 && !this.lastCancel) {
         MovementUtils.INSTANCE.strafe();
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof S12PacketEntityVelocity) {
         if (MinecraftInstance.mc.field_71439_g == null) {
            return;
         }

         WorldClient var10000 = MinecraftInstance.mc.field_71441_e;
         Entity var3 = var10000 == null ? null : var10000.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
         if (var3 == null) {
            return;
         }

         if (!Intrinsics.areEqual((Object)var3, (Object)MinecraftInstance.mc.field_71439_g)) {
            return;
         }

         this.ticks = 0;
         if (this.canCancel) {
            event.cancelEvent();
            this.lastCancel = true;
            this.canCancel = false;
         } else {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            this.lastCancel = false;
         }
      }

   }
}
