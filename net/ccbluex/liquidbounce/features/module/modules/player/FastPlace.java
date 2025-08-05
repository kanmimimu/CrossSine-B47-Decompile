package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "FastPlace",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/FastPlace;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockonlyValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tickDelay", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"}
)
public final class FastPlace extends Module {
   @NotNull
   private final IntegerValue tickDelay = new IntegerValue("Tick", 0, 0, 4);
   @NotNull
   private final BoolValue blockonlyValue = new BoolValue("BlockOnly", false);

   @EventTarget
   public final void onRender2D(@Nullable Render2DEvent event) {
      if ((Boolean)this.blockonlyValue.get()) {
         if (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null) {
            return;
         }

         EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
         Item var2;
         if (var10000 == null) {
            var2 = null;
         } else {
            InventoryPlayer var3 = var10000.field_71071_by;
            if (var3 == null) {
               var2 = null;
            } else {
               ItemStack var4 = var3.func_70448_g();
               var2 = var4 == null ? null : var4.func_77973_b();
            }
         }

         if (!(var2 instanceof ItemBlock)) {
            return;
         }
      }

      MinecraftInstance.mc.field_71467_ac = ((Number)this.tickDelay.get()).intValue();
   }
}
