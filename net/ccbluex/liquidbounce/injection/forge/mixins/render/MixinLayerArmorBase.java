package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Glint;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin({LayerArmorBase.class})
public abstract class MixinLayerArmorBase implements LayerRenderer {
   @ModifyArgs(
      method = {"renderGlint"},
      slice = @Slice(
   from = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;disableLighting()V",
   ordinal = 0
)
),
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V",
   ordinal = 0
),
      require = 1,
      allow = 1
   )
   private void renderGlint(Args args) {
      Glint glint = (Glint)CrossSine.moduleManager.getModule(Glint.class);
      if (glint.getState()) {
         int n = glint.getColor().getRGB();
         args.set(0, (float)(n >> 16 & 255) / 255.0F);
         args.set(1, (float)(n >> 8 & 255) / 255.0F);
         args.set(2, (float)(n & 255) / 255.0F);
         args.set(3, (float)(n >> 24 & 255) / 255.0F);
      }

   }
}
