package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.util.Objects;
import java.util.UUID;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LayerHeldItem.class})
public class MixinLayerHeldItem {
   @Shadow
   @Final
   private RendererLivingEntity field_177206_a;

   @Overwrite
   public void func_177141_a(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
      ItemStack itemstack = entitylivingbaseIn.func_70694_bm();
      if (itemstack != null) {
         GlStateManager.func_179094_E();
         if (this.field_177206_a.func_177087_b().field_78091_s) {
            float f = 0.5F;
            GlStateManager.func_179109_b(0.0F, 0.625F, 0.0F);
            GlStateManager.func_179114_b(-20.0F, -1.0F, 0.0F, 0.0F);
            GlStateManager.func_179152_a(f, f, f);
         }

         UUID uuid = entitylivingbaseIn.func_110124_au();
         EntityPlayer entityplayer = Minecraft.func_71410_x().field_71441_e.func_152378_a(uuid);
         KillAura killAura = (KillAura)CrossSine.moduleManager.getModule(KillAura.class);
         Item item = itemstack.func_77973_b();
         if (entityplayer != null && entityplayer.func_70632_aY() || entityplayer != null && (killAura.getDisplayBlocking() && killAura.getCurrentTarget() != null || SilentAura.INSTANCE.getCanBlock() && SilentAura.INSTANCE.getTarget() != null) && ((Animations)CrossSine.moduleManager.getModule(Animations.class)).getState() && item instanceof ItemSword && Objects.equals(entityplayer.func_146103_bH().getName(), Minecraft.func_71410_x().field_71439_g.func_146103_bH().getName())) {
            if (entitylivingbaseIn.func_70093_af()) {
               ((ModelBiped)this.field_177206_a.func_177087_b()).func_178718_a(0.0325F);
               GlStateManager.func_179109_b(-0.58F, 0.3F, -0.2F);
               GlStateManager.func_179114_b(-24390.0F, 137290.0F, -2009900.0F, -2054900.0F);
            } else {
               ((ModelBiped)this.field_177206_a.func_177087_b()).func_178718_a(0.0325F);
               GlStateManager.func_179109_b(-0.48F, 0.2F, -0.2F);
               GlStateManager.func_179114_b(-24390.0F, 137290.0F, -2009900.0F, -2054900.0F);
            }
         } else {
            ((ModelBiped)this.field_177206_a.func_177087_b()).func_178718_a(0.0625F);
         }

         GlStateManager.func_179109_b(-0.0625F, 0.4375F, 0.0625F);
         if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer)entitylivingbaseIn).field_71104_cf != null) {
            itemstack = new ItemStack(Items.field_151112_aM, 0);
         }

         Minecraft minecraft = Minecraft.func_71410_x();
         if (item instanceof ItemBlock && Block.func_149634_a(item).func_149645_b() == 2) {
            GlStateManager.func_179109_b(0.0F, 0.1875F, -0.3125F);
            GlStateManager.func_179114_b(20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
            float f1 = 0.375F;
            GlStateManager.func_179152_a(-f1, -f1, f1);
         }

         if (entitylivingbaseIn.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.203125F, 0.0F);
         }

         minecraft.func_175597_ag().func_178099_a(entitylivingbaseIn, itemstack, TransformType.THIRD_PERSON);
         GlStateManager.func_179121_F();
      }

   }
}
