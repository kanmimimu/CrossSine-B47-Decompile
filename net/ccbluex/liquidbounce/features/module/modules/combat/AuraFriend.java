package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AuraFirend",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AuraFriend;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoClear", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onMotion", "", "e", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "CrossSine"}
)
public final class AuraFriend extends Module {
   @NotNull
   private final FloatValue rangeValue = new FloatValue("Range", 3.5F, 0.0F, 6.0F);
   @NotNull
   private final BoolValue autoClear = new BoolValue("AutoClear", false);

   @EventTarget
   public final void onMotion(@NotNull MotionEvent e) {
      Intrinsics.checkNotNullParameter(e, "e");
      if (e.isPre()) {
         if (MinecraftInstance.mc.field_71439_g.field_70173_aa <= 5 && (Boolean)this.autoClear.get()) {
            CrossSine.INSTANCE.getFileManager().getFriendsConfig().clearFriends();
            CrossSine.INSTANCE.getFileManager().getFriendsConfig().saveConfig();
         }

         for(EntityPlayer ent : MinecraftInstance.mc.field_71441_e.field_73010_i) {
            if (!Intrinsics.areEqual((Object)ent, (Object)MinecraftInstance.mc.field_71439_g)) {
               int var4 = MinecraftInstance.mc.field_71439_g.field_70173_aa;
               if ((6 <= var4 ? var4 < 21 : false) && MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)ent) <= ((Number)this.rangeValue.get()).floatValue()) {
                  FriendsConfig var10000 = CrossSine.INSTANCE.getFileManager().getFriendsConfig();
                  String var5 = ent.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var5, "ent.name");
                  FriendsConfig.addFriend$default(var10000, var5, (String)null, 2, (Object)null);
                  CrossSine.INSTANCE.getFileManager().getFriendsConfig().saveConfig();
               }
            }
         }
      }

   }
}
