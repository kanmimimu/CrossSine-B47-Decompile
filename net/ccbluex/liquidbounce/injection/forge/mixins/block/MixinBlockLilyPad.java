package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({BlockLilyPad.class})
public abstract class MixinBlockLilyPad extends BlockBush {
   @Overwrite
   public AxisAlignedBB func_180640_a(World worldIn, BlockPos pos, IBlockState state) {
      return ProtocolFixer.newerThan1_8() ? new AxisAlignedBB((double)pos.func_177958_n() + (double)0.0625F, (double)pos.func_177956_o() + (double)0.0F, (double)pos.func_177952_p() + (double)0.0625F, (double)pos.func_177958_n() + (double)0.9375F, (double)pos.func_177956_o() + (double)0.09375F, (double)pos.func_177952_p() + (double)0.9375F) : new AxisAlignedBB((double)pos.func_177958_n() + (double)0.0F, (double)pos.func_177956_o() + (double)0.0F, (double)pos.func_177952_p() + (double)0.0F, (double)pos.func_177958_n() + (double)1.0F, (double)pos.func_177956_o() + (double)0.015625F, (double)pos.func_177952_p() + (double)1.0F);
   }
}
