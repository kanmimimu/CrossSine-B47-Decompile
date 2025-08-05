package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.move;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;

public class ScaffoldCheck extends Check {
   public ScaffoldCheck(EntityOtherPlayerMP playerMP) {
      super(playerMP);
      this.name = "Scaffold";
      this.checkViolationLevel = (double)3.0F;
   }

   public void onLivingUpdate() {
      if ((Boolean)HackerDetector.INSTANCE.scaffoldValue.get()) {
         data.update(this.handlePlayer);
         boolean b = true;
         if (this.handlePlayer.field_82175_bq && this.handlePlayer.field_70125_A >= 70.0F && this.handlePlayer.func_70694_bm() != null && this.handlePlayer.func_70694_bm().func_77973_b() instanceof ItemBlock && data.c >= 20 && this.handlePlayer.field_70173_aa - data.f >= 30 && this.handlePlayer.field_70173_aa - data.b >= 20) {
            BlockPos blockPos = this.handlePlayer.func_180425_c().func_177979_c(2);

            for(int i = 0; i < 4; ++i) {
               if (!(BlockUtils.getBlock(blockPos) instanceof BlockAir)) {
                  b = false;
                  break;
               }

               blockPos = blockPos.func_177977_b();
            }

            if (b) {
               this.flag("Scaffold flag", (double)3.0F);
            }
         }
      }

   }
}
