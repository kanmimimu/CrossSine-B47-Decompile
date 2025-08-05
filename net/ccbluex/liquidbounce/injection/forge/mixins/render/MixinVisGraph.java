package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.XRay;
import net.minecraft.client.renderer.chunk.VisGraph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({VisGraph.class})
public class MixinVisGraph {
   @Inject(
      method = {"func_178606_a"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void func_178606_a(CallbackInfo callbackInfo) {
      if (((XRay)CrossSine.moduleManager.getModule(XRay.class)).getState()) {
         callbackInfo.cancel();
      }

   }
}
