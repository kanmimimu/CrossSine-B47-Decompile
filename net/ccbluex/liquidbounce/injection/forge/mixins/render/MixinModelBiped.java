package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.features.module.modules.client.RotationModule;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ModelBiped.class})
public class MixinModelBiped extends MixinModelBase {
   @Shadow
   public ModelRenderer field_78116_c;
   @Shadow
   public ModelRenderer field_178720_f;
   @Shadow
   public ModelRenderer field_78115_e;
   @Shadow
   public ModelRenderer field_178723_h;
   @Shadow
   public ModelRenderer field_178724_i;
   @Shadow
   public ModelRenderer field_178721_j;
   @Shadow
   public ModelRenderer field_178722_k;
   @Shadow
   public int field_78119_l;
   @Shadow
   public int field_78120_m;
   @Shadow
   public boolean field_78117_n;
   @Shadow
   public boolean field_78118_o;

   @Overwrite
   public void func_78087_a(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_) {
      this.field_78116_c.field_78796_g = p_setRotationAngles_4_ / (180F / (float)Math.PI);
      this.field_78116_c.field_78795_f = p_setRotationAngles_5_ / (180F / (float)Math.PI);
      if (RotationModule.INSTANCE.getState() && RotationUtils.serverRotation != null && p_setRotationAngles_7_ instanceof EntityPlayer && p_setRotationAngles_7_.equals(Minecraft.func_71410_x().field_71439_g)) {
         this.field_78116_c.field_78795_f = (float)Math.toRadians(RotationUtils.fakeRotation != null ? (double)RotationUtils.fakeRotation.getPitch() : (RotationUtils.targetRotation != null ? RotationUtils.smoothPitch : RenderUtils.interpolate((double)RotationUtils.headPitch, (double)RotationUtils.prevHeadPitch, (double)Minecraft.func_71410_x().field_71428_T.field_74281_c)));
      }

      this.field_178723_h.field_78795_f = MathHelper.func_76134_b(p_setRotationAngles_1_ * 0.6662F + (float)Math.PI) * 2.0F * p_setRotationAngles_2_ * 0.5F;
      this.field_178724_i.field_78795_f = MathHelper.func_76134_b(p_setRotationAngles_1_ * 0.6662F) * 2.0F * p_setRotationAngles_2_ * 0.5F;
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178721_j.field_78795_f = MathHelper.func_76134_b(p_setRotationAngles_1_ * 0.6662F) * 1.4F * p_setRotationAngles_2_;
      this.field_178722_k.field_78795_f = MathHelper.func_76134_b(p_setRotationAngles_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_setRotationAngles_2_;
      this.field_178721_j.field_78796_g = 0.0F;
      this.field_178722_k.field_78796_g = 0.0F;
      if (this.field_78093_q) {
         ModelRenderer var10000 = this.field_178723_h;
         var10000.field_78795_f += (-(float)Math.PI / 5F);
         var10000 = this.field_178724_i;
         var10000.field_78795_f += (-(float)Math.PI / 5F);
         this.field_178721_j.field_78795_f = -1.2566371F;
         this.field_178722_k.field_78795_f = -1.2566371F;
         this.field_178721_j.field_78796_g = ((float)Math.PI / 10F);
         this.field_178722_k.field_78796_g = (-(float)Math.PI / 10F);
      }

      if (this.field_78119_l != 0) {
         this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5F - ((float)Math.PI / 10F) * (float)this.field_78119_l;
      }

      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178723_h.field_78808_h = 0.0F;
      switch (this.field_78120_m) {
         case 0:
         case 2:
         default:
            break;
         case 1:
            this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5F - ((float)Math.PI / 10F) * (float)this.field_78120_m;
            break;
         case 3:
            this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5F - ((float)Math.PI / 10F) * (float)this.field_78120_m;
            if (Animations.INSTANCE.getState() && (Boolean)Animations.INSTANCE.getBlockAnimation().get()) {
               this.field_178723_h.field_78796_g = 0.0F;
            } else {
               this.field_178723_h.field_78796_g = (-(float)Math.PI / 6F);
            }
      }

      this.field_178724_i.field_78796_g = 0.0F;
      if (this.field_78095_p > -9990.0F) {
         float lvt_8_2_ = this.field_78095_p;
         this.field_78115_e.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(lvt_8_2_) * (float)Math.PI * 2.0F) * 0.2F;
         this.field_178723_h.field_78798_e = MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178723_h.field_78800_c = -MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178724_i.field_78798_e = -MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178724_i.field_78800_c = MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0F;
         ModelRenderer var10000 = this.field_178723_h;
         var10000.field_78796_g += this.field_78115_e.field_78796_g;
         var10000 = this.field_178724_i;
         var10000.field_78796_g += this.field_78115_e.field_78796_g;
         var10000 = this.field_178724_i;
         var10000.field_78795_f += this.field_78115_e.field_78796_g;
         lvt_8_2_ = 1.0F - this.field_78095_p;
         lvt_8_2_ *= lvt_8_2_;
         lvt_8_2_ *= lvt_8_2_;
         lvt_8_2_ = 1.0F - lvt_8_2_;
         float lvt_9_2_ = MathHelper.func_76126_a(lvt_8_2_ * (float)Math.PI);
         float lvt_10_1_ = MathHelper.func_76126_a(this.field_78095_p * (float)Math.PI) * -(this.field_78116_c.field_78795_f - 0.7F) * 0.75F;
         var10000 = this.field_178723_h;
         var10000.field_78795_f = (float)((double)var10000.field_78795_f - ((double)lvt_9_2_ * 1.2 + (double)lvt_10_1_));
         var10000 = this.field_178723_h;
         var10000.field_78796_g += this.field_78115_e.field_78796_g * 2.0F;
         var10000 = this.field_178723_h;
         var10000.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * (float)Math.PI) * -0.4F;
      }

      if (this.field_78117_n) {
         this.field_78115_e.field_78795_f = 0.5F;
         ModelRenderer var10000 = this.field_178723_h;
         var10000.field_78795_f += 0.4F;
         var10000 = this.field_178724_i;
         var10000.field_78795_f += 0.4F;
         this.field_178721_j.field_78798_e = 4.0F;
         this.field_178722_k.field_78798_e = 4.0F;
         this.field_178721_j.field_78797_d = 9.0F;
         this.field_178722_k.field_78797_d = 9.0F;
         this.field_78116_c.field_78797_d = 1.0F;
      } else {
         this.field_78115_e.field_78795_f = 0.0F;
         this.field_178721_j.field_78798_e = 0.1F;
         this.field_178722_k.field_78798_e = 0.1F;
         this.field_178721_j.field_78797_d = 12.0F;
         this.field_178722_k.field_78797_d = 12.0F;
         this.field_78116_c.field_78797_d = 0.0F;
      }

      ModelRenderer var10000 = this.field_178723_h;
      var10000.field_78808_h += MathHelper.func_76134_b(p_setRotationAngles_3_ * 0.09F) * 0.05F + 0.05F;
      var10000 = this.field_178724_i;
      var10000.field_78808_h -= MathHelper.func_76134_b(p_setRotationAngles_3_ * 0.09F) * 0.05F + 0.05F;
      var10000 = this.field_178723_h;
      var10000.field_78795_f += MathHelper.func_76126_a(p_setRotationAngles_3_ * 0.067F) * 0.05F;
      var10000 = this.field_178724_i;
      var10000.field_78795_f -= MathHelper.func_76126_a(p_setRotationAngles_3_ * 0.067F) * 0.05F;
      if (this.field_78118_o) {
         float lvt_8_2_ = 0.0F;
         float lvt_9_2_ = 0.0F;
         this.field_178723_h.field_78808_h = 0.0F;
         this.field_178724_i.field_78808_h = 0.0F;
         this.field_178723_h.field_78796_g = -(0.1F - lvt_8_2_ * 0.6F) + this.field_78116_c.field_78796_g;
         this.field_178724_i.field_78796_g = 0.1F - lvt_8_2_ * 0.6F + this.field_78116_c.field_78796_g + 0.4F;
         this.field_178723_h.field_78795_f = (-(float)Math.PI / 2F) + this.field_78116_c.field_78795_f;
         this.field_178724_i.field_78795_f = (-(float)Math.PI / 2F) + this.field_78116_c.field_78795_f;
         var10000 = this.field_178723_h;
         var10000.field_78795_f -= lvt_8_2_ * 1.2F - lvt_9_2_ * 0.4F;
         var10000 = this.field_178724_i;
         var10000.field_78795_f -= lvt_8_2_ * 1.2F - lvt_9_2_ * 0.4F;
         var10000 = this.field_178723_h;
         var10000.field_78808_h += MathHelper.func_76134_b(p_setRotationAngles_3_ * 0.09F) * 0.05F + 0.05F;
         var10000 = this.field_178724_i;
         var10000.field_78808_h -= MathHelper.func_76134_b(p_setRotationAngles_3_ * 0.09F) * 0.05F + 0.05F;
         var10000 = this.field_178723_h;
         var10000.field_78795_f += MathHelper.func_76126_a(p_setRotationAngles_3_ * 0.067F) * 0.05F;
         var10000 = this.field_178724_i;
         var10000.field_78795_f -= MathHelper.func_76126_a(p_setRotationAngles_3_ * 0.067F) * 0.05F;
      }

      func_178685_a(this.field_78116_c, this.field_178720_f);
   }
}
