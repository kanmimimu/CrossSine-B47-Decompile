package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.ui.client.gui.GuiMainMenu;
import net.ccbluex.liquidbounce.utils.StatisticsUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiGameOver.class})
public abstract class MixinGuiGameOver extends MixinGuiScreen implements GuiYesNoCallback {
   @Inject(
      method = {"actionPerformed"},
      at = {@At("HEAD")}
   )
   public void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
      switch (button.field_146127_k) {
         case 0:
            StatisticsUtils.addDeaths();
            this.field_146297_k.field_71439_g.func_71004_bE();
            this.field_146297_k.func_147108_a((GuiScreen)null);
            break;
         case 1:
            if (this.field_146297_k.field_71441_e.func_72912_H().func_76093_s()) {
               this.field_146297_k.func_147108_a(new GuiMainMenu());
            } else {
               GuiYesNo lvt_2_1_ = new GuiYesNo(this, I18n.func_135052_a("deathScreen.quit.confirm", new Object[0]), "", I18n.func_135052_a("deathScreen.titleScreen", new Object[0]), I18n.func_135052_a("deathScreen.respawn", new Object[0]), 0);
               this.field_146297_k.func_147108_a(lvt_2_1_);
               lvt_2_1_.func_146350_a(20);
            }
      }

   }

   public void confirmClicked(boolean p_confirmClicked_1_, int p_confirmClicked_2_, CallbackInfo ci) {
      if (p_confirmClicked_1_) {
         StatisticsUtils.addDeaths();
         this.field_146297_k.field_71441_e.func_72882_A();
         this.field_146297_k.func_71403_a((WorldClient)null);
         this.field_146297_k.func_147108_a(new GuiMainMenu());
      } else {
         StatisticsUtils.addDeaths();
         this.field_146297_k.field_71439_g.func_71004_bE();
         this.field_146297_k.func_147108_a((GuiScreen)null);
      }

   }
}
