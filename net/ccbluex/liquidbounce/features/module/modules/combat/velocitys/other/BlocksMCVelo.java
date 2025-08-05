package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/other/BlocksMCVelo;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "send", "", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class BlocksMCVelo extends VelocityMode {
   private boolean send;

   public BlocksMCVelo() {
      super("BlocksMC");
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.send) {
         this.send = false;
         PacketUtils.sendPacketNoEvent((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.STOP_SNEAKING)));
      } else {
         if (KillAura.INSTANCE.getState() && KillAura.INSTANCE.getCurrentTarget() != null && MinecraftInstance.mc.field_71439_g.field_70737_aN >= 9 && !this.send) {
            this.send = true;
            PacketUtils.sendPacketNoEvent((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.START_SNEAKING)));
         }

      }
   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71441_e != null) {
         if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)event.getPacket()).func_149412_c() == MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            event.cancelEvent();
         }

         if (event.getPacket() instanceof C0BPacketEntityAction && (((C0BPacketEntityAction)event.getPacket()).func_180764_b() == Action.STOP_SNEAKING || ((C0BPacketEntityAction)event.getPacket()).func_180764_b() == Action.START_SNEAKING) && this.send) {
            this.send = false;
         }

      }
   }
}
