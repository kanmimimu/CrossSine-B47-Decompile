package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.List;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.Reach;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.visual.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.NoSlowBreak;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
public abstract class MixinBlock {
   @Shadow
   @Final
   protected BlockState field_176227_L;

   @Shadow
   public abstract AxisAlignedBB func_180640_a(World var1, BlockPos var2, IBlockState var3);

   @Shadow
   public IBlockState func_180642_a(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return null;
   }

   @Overwrite
   public void func_180638_a(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
      AxisAlignedBB axisalignedbb = this.func_180640_a(worldIn, pos, state);
      BlockBBEvent blockBBEvent = new BlockBBEvent(pos, this.field_176227_L.func_177622_c(), axisalignedbb);
      CrossSine.eventManager.callEvent(blockBBEvent);
      axisalignedbb = blockBBEvent.getBoundingBox();
      if (axisalignedbb != null && mask.func_72326_a(axisalignedbb)) {
         list.add(axisalignedbb);
      }

   }

   @Inject(
      method = {"shouldSideBeRendered"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void shouldSideBeRendered(CallbackInfoReturnable callbackInfoReturnable) {
      XRay xray = (XRay)CrossSine.moduleManager.getModule(XRay.class);
      if (xray.getState()) {
         callbackInfoReturnable.setReturnValue(xray.getXrayBlocks().contains(this));
      }

   }

   @Inject(
      method = {"getAmbientOcclusionLightValue"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getAmbientOcclusionLightValue(CallbackInfoReturnable floatCallbackInfoReturnable) {
      if (((XRay)CrossSine.moduleManager.getModule(XRay.class)).getState()) {
         floatCallbackInfoReturnable.setReturnValue(1.0F);
      }

   }

   @Inject(
      method = {"getPlayerRelativeBlockHardness"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void modifyBreakSpeed(EntityPlayer playerIn, World worldIn, BlockPos pos, CallbackInfoReturnable callbackInfo) {
      float f = (Float)callbackInfo.getReturnValue();
      NoSlowBreak noSlowBreak = (NoSlowBreak)CrossSine.moduleManager.getModule(NoSlowBreak.class);
      if (noSlowBreak.getState()) {
         if ((Boolean)noSlowBreak.getWaterValue().get() && playerIn.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_77510_g(playerIn)) {
            f *= 5.0F;
         }

         if ((Boolean)noSlowBreak.getAirValue().get() && !playerIn.field_70122_E) {
            f *= 5.0F;
         }
      } else if (playerIn.field_70122_E) {
         NoFall noFall = (NoFall)CrossSine.moduleManager.getModule(NoFall.class);
         Criticals criticals = (Criticals)CrossSine.moduleManager.getModule(Criticals.class);
         if (noFall.getState() && noFall.getMode().equals("NoGround") || criticals.getState() && criticals.getModeValue().equals("NoGround")) {
            f /= 5.0F;
         }
      }

      callbackInfo.setReturnValue(f);
   }

   @Overwrite
   public boolean func_149703_v() {
      return !Reach.INSTANCE.getState() || !(Boolean)Reach.INSTANCE.getThroughWall().get() || !Reach.INSTANCE.call();
   }
}
