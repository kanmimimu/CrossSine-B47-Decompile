package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.minecraft.block.BlockLadder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({BlockLadder.class})
public abstract class MixinBlockLadder extends MixinBlock {
   @ModifyConstant(
      method = {"setBlockBoundsBasedOnState"},
      constant = {@Constant(
   floatValue = 0.125F
)}
   )
   private float ViaVersion_LadderBB(float constant) {
      return ProtocolFixer.newerThan1_8() ? 0.1875F : 0.125F;
   }
}
