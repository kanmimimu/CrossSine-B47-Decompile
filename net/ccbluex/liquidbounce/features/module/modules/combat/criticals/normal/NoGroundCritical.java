package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.normal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0012H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/normal/NoGroundCritical;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "autoJumpValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "morePacketValue", "packetAmount", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "shouldEdit", "", "smartValue", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onEnable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"}
)
public final class NoGroundCritical extends CriticalMode {
   @NotNull
   private final BoolValue autoJumpValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "AutoJump"), false);
   @NotNull
   private final BoolValue smartValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Smart"), true);
   @NotNull
   private final BoolValue morePacketValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "MorePacket"), false);
   @NotNull
   private final Value packetAmount = (new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "PacketAmount"), 2, 1, 5)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)NoGroundCritical.this.morePacketValue.get();
      }
   });
   private boolean shouldEdit;

   public NoGroundCritical() {
      super("NoGround");
   }

   public void onEnable() {
      if ((Boolean)this.autoJumpValue.get()) {
         MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
      }

   }

   public void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)this.morePacketValue.get()) {
         int var2 = ((Number)this.packetAmount.get()).intValue();

         for(int var3 = 0; var3 < var2; this.shouldEdit = true) {
            ++var3;
            int var6 = 0;
            this.shouldEdit = true;
            Criticals.sendCriticalPacket$default(this.getCritical(), (double)0.0F, (double)0.0F, (double)0.0F, false, 7, (Object)null);
         }
      } else {
         this.shouldEdit = true;
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPacket() instanceof C03PacketPlayer) {
         if ((Boolean)this.smartValue.get()) {
            if (this.shouldEdit) {
               ((C03PacketPlayer)event.getPacket()).field_149474_g = false;
               this.shouldEdit = false;
            }
         } else {
            ((C03PacketPlayer)event.getPacket()).field_149474_g = false;
         }
      }

   }
}
