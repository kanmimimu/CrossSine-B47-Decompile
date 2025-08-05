package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/matrix/OldMatrixNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "isDmgFalling", "", "matrixFlagWait", "", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"}
)
public final class OldMatrixNofall extends NoFallMode {
   private boolean isDmgFalling;
   private int matrixFlagWait;

   public OldMatrixNofall() {
      super("OldMatrix");
   }

   public void onEnable() {
      this.isDmgFalling = false;
      this.matrixFlagWait = 0;
   }

   public void onNoFall(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g.field_70143_R > 3.0F) {
         this.isDmgFalling = true;
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPacket() instanceof S08PacketPlayerPosLook && this.matrixFlagWait > 0) {
         this.matrixFlagWait = 0;
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         event.cancelEvent();
      }

      if (event.getPacket() instanceof C03PacketPlayer && this.isDmgFalling && ((C03PacketPlayer)event.getPacket()).field_149474_g && MinecraftInstance.mc.field_71439_g.field_70122_E) {
         this.matrixFlagWait = 2;
         this.isDmgFalling = false;
         event.cancelEvent();
         MinecraftInstance.mc.field_71439_g.field_70122_E = false;
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, ((C03PacketPlayer)event.getPacket()).field_149477_b - (double)256, ((C03PacketPlayer)event.getPacket()).field_149478_c, false)));
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, (double)-10.0F, ((C03PacketPlayer)event.getPacket()).field_149478_c, true)));
         MinecraftInstance.mc.field_71428_T.field_74278_d = 0.18F;
      }

   }
}
