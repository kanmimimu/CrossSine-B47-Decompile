package net.ccbluex.liquidbounce.features.module.modules.visual;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModuleInfo(
   name = "FullBright",
   category = ModuleCategory.VISUAL
)
public class FullBright extends Module {
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Gamma", "NightVision"}, "Gamma");
   private float prevGamma = -1.0F;

   public void onEnable() {
      this.prevGamma = mc.field_71474_y.field_74333_Y;
   }

   public void onDisable() {
      if (this.prevGamma != -1.0F) {
         mc.field_71474_y.field_74333_Y = this.prevGamma;
         this.prevGamma = -1.0F;
         if (mc.field_71439_g != null) {
            mc.field_71439_g.func_70618_n(Potion.field_76439_r.field_76415_H);
         }

      }
   }

   @EventTarget(
      ignoreCondition = true
   )
   public void onUpdate(UpdateEvent event) {
      if (!this.getState() && !((XRay)CrossSine.moduleManager.getModule(XRay.class)).getState()) {
         if (this.prevGamma != -1.0F) {
            mc.field_71474_y.field_74333_Y = this.prevGamma;
            this.prevGamma = -1.0F;
         }
      } else {
         switch (((String)this.modeValue.get()).toLowerCase()) {
            case "gamma":
               if (mc.field_71474_y.field_74333_Y <= 100.0F) {
                  ++mc.field_71474_y.field_74333_Y;
               }
               break;
            case "nightvision":
               mc.field_71439_g.func_70690_d(new PotionEffect(Potion.field_76439_r.field_76415_H, 1337, 1));
         }
      }

   }

   @EventTarget(
      ignoreCondition = true
   )
   public void onShutdown(ClientShutdownEvent event) {
      this.onDisable();
   }
}
