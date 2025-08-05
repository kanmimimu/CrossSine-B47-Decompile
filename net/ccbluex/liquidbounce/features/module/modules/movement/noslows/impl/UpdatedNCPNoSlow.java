package net.ccbluex.liquidbounce.features.module.modules.movement.noslows.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.noslows.NoSlowMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/impl/UpdatedNCPNoSlow;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/NoSlowMode;", "()V", "swap", "", "swapPacket", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onPostMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPreMotion", "slow", "", "swapSlot", "CrossSine"}
)
public final class UpdatedNCPNoSlow extends NoSlowMode {
   @NotNull
   private final BoolValue swapPacket = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "SwapPacket"), true);
   private boolean swap;

   public UpdatedNCPNoSlow() {
      super("UpdatedNCP");
   }

   public void onPreMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((this.getHoldConsume() || this.getHoldBow()) && this.swap) {
         this.swapSlot();
         PacketUtils.sendPacketNoEvent((Packet)(new C08PacketPlayerBlockPlacement(BlockPos.field_177992_a, 255, MinecraftInstance.mc.field_71439_g.func_70694_bm(), 0.0F, 0.0F, 0.0F)));
         this.swap = false;
      }

   }

   public void onPostMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getHoldSword()) {
         PacketUtils.sendPacketNoEvent((Packet)(new C08PacketPlayerBlockPlacement(BlockPos.field_177992_a, 255, MinecraftInstance.mc.field_71439_g.func_70694_bm(), 0.0F, 0.0F, 0.0F)));
      }

   }

   private final void swapSlot() {
      if ((Boolean)this.swapPacket.get()) {
         PacketUtils.sendPacketNoEvent((Packet)(new C09PacketHeldItemChange((MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c + 1) % 9)));
         PacketUtils.sendPacketNoEvent((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
      } else {
         SlotUtils.INSTANCE.setSlot((MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c + 1) % 9, true, this.getNoslow().getName());
         SlotUtils.INSTANCE.stopSet();
      }

   }

   public float slow() {
      return 1.0F;
   }
}
