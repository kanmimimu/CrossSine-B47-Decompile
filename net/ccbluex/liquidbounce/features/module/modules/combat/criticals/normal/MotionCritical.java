package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.normal;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/normal/MotionCritical;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "motionValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"}
)
public final class MotionCritical extends CriticalMode {
   @NotNull
   private final ListValue motionValue;

   public MotionCritical() {
      super("Motion");
      String[] var1 = new String[]{"RedeSkyLowHop", "Hop", "Jump", "LowJump", "MinemoraTest"};
      this.motionValue = new ListValue("MotionMode", var1, "Jump");
   }

   public void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var3) {
         case "redeskylowhop":
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.35;
            break;
         case "minemoratest":
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.82F;
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.124514;
            break;
         case "hop":
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.1F;
            MinecraftInstance.mc.field_71439_g.field_70122_E = false;
            break;
         case "jump":
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
            break;
         case "lowjump":
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.3425;
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPacket() instanceof C03PacketPlayer) {
         String var2 = ((String)this.motionValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         if (Intrinsics.areEqual((Object)var2, (Object)"minemoratest") && !CrossSine.INSTANCE.getCombatManager().getInCombat()) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         }
      }

   }
}
