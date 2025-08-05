package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/matrix/Matrix663Nofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "firstNfall", "", "matrixSafe", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "matrixSend", "nearGround", "onDisable", "", "onEnable", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"}
)
public final class Matrix663Nofall extends NoFallMode {
   private boolean matrixSend;
   @NotNull
   private final BoolValue matrixSafe = new BoolValue("SafeNoFall", true);
   private boolean firstNfall = true;
   private boolean nearGround;

   public Matrix663Nofall() {
      super("Matrix6.6.3");
   }

   public void onEnable() {
      this.matrixSend = false;
      this.firstNfall = true;
      this.nearGround = false;
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }

   public void onNoFall(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer");
      FallingPlayer fallingPlayer = new FallingPlayer((EntityPlayer)var3);
      BlockPos collLoc = fallingPlayer.findCollision(60);
      if (!((double)MinecraftInstance.mc.field_71439_g.field_70143_R - MinecraftInstance.mc.field_71439_g.field_70181_x > (double)3.0F)) {
         int var10000;
         if (collLoc == null) {
            var10000 = 0;
         } else {
            int var5 = collLoc.func_177956_o();
            var10000 = var5;
         }

         if (!(Math.abs((double)var10000 - MinecraftInstance.mc.field_71439_g.field_70163_u) < (double)3.0F) || !((double)MinecraftInstance.mc.field_71439_g.field_70143_R - MinecraftInstance.mc.field_71439_g.field_70181_x > (double)2.0F)) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
            return;
         }
      }

      MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0F;
      this.matrixSend = true;
      if ((Boolean)this.matrixSafe.get()) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 0.3F;
         EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70159_w *= (double)0.5F;
         var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70179_y *= (double)0.5F;
      } else {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 0.5F;
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPacket() instanceof C03PacketPlayer && this.matrixSend) {
         this.matrixSend = false;
         EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer");
         FallingPlayer fallingPlayer = new FallingPlayer((EntityPlayer)var3);
         BlockPos collLoc = fallingPlayer.findCollision(60);
         int var10000;
         if (collLoc == null) {
            var10000 = 0;
         } else {
            int var4 = collLoc.func_177956_o();
            var10000 = var4;
         }

         if (Math.abs((double)var10000 - MinecraftInstance.mc.field_71439_g.field_70163_u) > (double)2.0F) {
            event.cancelEvent();
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, ((C03PacketPlayer)event.getPacket()).field_149477_b, ((C03PacketPlayer)event.getPacket()).field_149478_c, true)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, ((C03PacketPlayer)event.getPacket()).field_149477_b, ((C03PacketPlayer)event.getPacket()).field_149478_c, false)));
         }
      }

   }
}
