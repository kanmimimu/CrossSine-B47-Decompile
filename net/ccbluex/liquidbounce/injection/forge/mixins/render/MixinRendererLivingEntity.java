package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Chams;
import net.ccbluex.liquidbounce.features.module.modules.visual.HitColor;
import net.ccbluex.liquidbounce.features.module.modules.visual.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.visual.TrueSight;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({RendererLivingEntity.class})
public abstract class MixinRendererLivingEntity extends MixinRender {
   @Shadow
   protected ModelBase field_77045_g;
   @Shadow
   protected FloatBuffer field_177095_g = GLAllocation.func_74529_h(4);
   private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);

   @Shadow
   public abstract int func_77030_a(EntityLivingBase var1, float var2, float var3);

   @Shadow
   public ModelBase func_177087_b() {
      return this.field_77045_g;
   }

   @Shadow
   protected float func_77037_a(EntityLivingBase p_getDeathMaxRotation_1_) {
      return 90.0F;
   }

   @Overwrite
   protected boolean func_177092_a(EntityLivingBase entitylivingbaseIn, float partialTicks, boolean combineTextures) {
      HitColor hitColor = (HitColor)CrossSine.moduleManager.getModule(HitColor.class);
      float f = entitylivingbaseIn.func_70013_c(partialTicks);
      int i = this.func_77030_a(entitylivingbaseIn, f, partialTicks);
      boolean flag = (i >> 24 & 255) > 0;
      boolean flag1 = entitylivingbaseIn.field_70737_aN > 0 || entitylivingbaseIn.field_70725_aQ > 0;
      if (!flag && !flag1) {
         return false;
      } else if (!flag && !combineTextures) {
         return false;
      } else {
         GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
         GlStateManager.func_179098_w();
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_77478_a);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_77478_a);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
         GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
         GlStateManager.func_179098_w();
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, OpenGlHelper.field_176094_t);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176092_v);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176080_A, OpenGlHelper.field_176092_v);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176076_D, 770);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
         this.field_177095_g.position(0);
         if (flag1) {
            assert hitColor != null;

            if (hitColor.getState()) {
               int color = (Boolean)hitColor.getHitColorTheme().get() ? ClientTheme.INSTANCE.getColorWithAlpha(1, (Integer)hitColor.getHitColorAlphaValue().get(), true).getRGB() : (new Color((Integer)hitColor.getHitColorRValue().get(), (Integer)hitColor.getHitColorGValue().get(), (Integer)hitColor.getHitColorBValue().get(), (Integer)hitColor.getHitColorAlphaValue().get())).getRGB();
               float red = (float)(color >> 16 & 255) / 255.0F;
               float green = (float)(color >> 8 & 255) / 255.0F;
               float blue = (float)(color & 255) / 255.0F;
               float alpha = (float)(color >> 24 & 255) / 255.0F;
               this.field_177095_g.put(red);
               this.field_177095_g.put(green);
               this.field_177095_g.put(blue);
               this.field_177095_g.put(alpha);
            } else {
               this.field_177095_g.put(1.0F);
               this.field_177095_g.put(0.0F);
               this.field_177095_g.put(0.0F);
               this.field_177095_g.put(0.3F);
            }
         } else {
            float f1 = (float)(i >> 24 & 255) / 255.0F;
            float f2 = (float)(i >> 16 & 255) / 255.0F;
            float f3 = (float)(i >> 8 & 255) / 255.0F;
            float f4 = (float)(i & 255) / 255.0F;
            this.field_177095_g.put(f2);
            this.field_177095_g.put(f3);
            this.field_177095_g.put(f4);
            this.field_177095_g.put(1.0F - f1);
         }

         this.field_177095_g.flip();
         GL11.glTexEnv(8960, 8705, this.field_177095_g);
         GlStateManager.func_179138_g(OpenGlHelper.field_176096_r);
         GlStateManager.func_179098_w();
         GlStateManager.func_179144_i(field_177096_e.func_110552_b());
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_77476_b);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
         GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
         return true;
      }
   }

   @Overwrite
   protected void func_77043_a(EntityLivingBase p_rotateCorpse_1_, float p_rotateCorpse_2_, float p_rotateCorpse_3_, float p_rotateCorpse_4_) {
      GlStateManager.func_179114_b(180.0F - p_rotateCorpse_3_, 0.0F, 1.0F, 0.0F);
      if (p_rotateCorpse_1_.field_70725_aQ > 0) {
         float f = ((float)p_rotateCorpse_1_.field_70725_aQ + p_rotateCorpse_4_ - 1.0F) / 20.0F * 1.6F;
         f = MathHelper.func_76129_c(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         GlStateManager.func_179114_b(f * this.func_77037_a(p_rotateCorpse_1_), 0.0F, 0.0F, 1.0F);
      } else {
         String var7 = EnumChatFormatting.func_110646_a(p_rotateCorpse_1_.func_70005_c_());
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("HEAD")}
   )
   private void injectChamsPre(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
      Chams chams = (Chams)CrossSine.moduleManager.getModule(Chams.class);
      if (chams.getState() && (Boolean)chams.getTargetsValue().get() && (Boolean)chams.getLegacyMode().get() && ((Boolean)chams.getLocalPlayerValue().get() && entity == Minecraft.func_71410_x().field_71439_g || EntityUtils.INSTANCE.isSelected(entity, false))) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1000000.0F);
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("RETURN")}
   )
   private void injectChamsPost(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
      Chams chams = (Chams)CrossSine.moduleManager.getModule(Chams.class);
      if (chams.getState() && (Boolean)chams.getTargetsValue().get() && (Boolean)chams.getLegacyMode().get() && ((Boolean)chams.getLocalPlayerValue().get() && entity == Minecraft.func_71410_x().field_71439_g || EntityUtils.INSTANCE.isSelected(entity, false))) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }

   @Inject(
      method = {"canRenderName"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void canRenderName(EntityLivingBase entity, CallbackInfoReturnable callbackInfoReturnable) {
      if (((NameTags)CrossSine.moduleManager.getModule(NameTags.class)).getState() && EntityUtils.INSTANCE.isSelected(entity, false)) {
         callbackInfoReturnable.setReturnValue(false);
      }

   }

   @Inject(
      method = {"renderModel"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void renderModel(EntityLivingBase p_renderModel_1_, float p_renderModel_2_, float p_renderModel_3_, float p_renderModel_4_, float p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_, CallbackInfo ci) {
      boolean visible = !p_renderModel_1_.func_82150_aj();
      Chams chams = (Chams)CrossSine.moduleManager.getModule(Chams.class);
      TrueSight trueSight = (TrueSight)CrossSine.moduleManager.getModule(TrueSight.class);
      boolean chamsFlag = chams.getState() && (Boolean)chams.getTargetsValue().get() && !(Boolean)chams.getLegacyMode().get() && ((Boolean)chams.getLocalPlayerValue().get() && p_renderModel_1_ == Minecraft.func_71410_x().field_71439_g || EntityUtils.INSTANCE.isSelected(p_renderModel_1_, false));
      boolean semiVisible = !visible && (!p_renderModel_1_.func_98034_c(Minecraft.func_71410_x().field_71439_g) || trueSight.getState() && (Boolean)trueSight.getEntitiesValue().get());
      if (visible || semiVisible) {
         if (!this.func_180548_c(p_renderModel_1_)) {
            return;
         }

         if (semiVisible) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b(770, 771);
            GlStateManager.func_179092_a(516, 0.003921569F);
         }

         int blend = 3042;
         int depth = 2929;
         int srcAlpha = 770;
         int srcAlphaPlus1 = 771;
         int polygonOffsetLine = 10754;
         int texture2D = 3553;
         int lighting = 2896;
         boolean textured = (Boolean)chams.getTexturedValue().get();
         Color chamsColor = new Color(0);
         switch ((String)chams.getColorModeValue().get()) {
            case "Custom":
               chamsColor = (Color)chams.getColorValue().get();
               break;
            case "Slowly":
               chamsColor = ColorUtils.slowlyRainbow(System.nanoTime(), 0, (Float)chams.getSaturationValue().get(), (Float)chams.getBrightnessValue().get());
               break;
            case "Fade":
               chamsColor = ColorUtils.fade((Color)chams.getColorValue().get(), 0, 100);
         }

         chamsColor = ColorUtils.reAlpha(chamsColor, ((Color)chams.getColorValue().get()).getAlpha());
         if (chamsFlag) {
            Color chamsColor2 = new Color(0);
            switch ((String)chams.getBehindColorModeValue().get()) {
               case "Same":
                  chamsColor2 = chamsColor;
                  break;
               case "Opposite":
                  chamsColor2 = ColorUtils.getOppositeColor(chamsColor);
                  break;
               case "Red":
                  chamsColor2 = new Color(-1104346);
            }

            GL11.glPushMatrix();
            GL11.glEnable(10754);
            GL11.glPolygonOffset(1.0F, 1000000.0F);
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
            if (!textured) {
               GL11.glEnable(3042);
               GL11.glDisable(3553);
               GL11.glDisable(2896);
               GL11.glBlendFunc(770, 771);
               GL11.glColor4f((float)chamsColor2.getRed() / 255.0F, (float)chamsColor2.getGreen() / 255.0F, (float)chamsColor2.getBlue() / 255.0F, (float)chamsColor2.getAlpha() / 255.0F);
            }

            GL11.glDisable(2929);
            GL11.glDepthMask(false);
         }

         this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
         if (chamsFlag) {
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            if (!textured) {
               GL11.glColor4f((float)chamsColor.getRed() / 255.0F, (float)chamsColor.getGreen() / 255.0F, (float)chamsColor.getBlue() / 255.0F, (float)chamsColor.getAlpha() / 255.0F);
            }

            this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
            if (!textured) {
               GL11.glEnable(3553);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glDisable(3042);
               GL11.glEnable(2896);
            }

            GL11.glPolygonOffset(1.0F, -1000000.0F);
            GL11.glDisable(10754);
            GL11.glPopMatrix();
         }

         if (semiVisible) {
            GlStateManager.func_179084_k();
            GlStateManager.func_179092_a(516, 0.1F);
            GlStateManager.func_179121_F();
            GlStateManager.func_179132_a(true);
         }
      }

      ci.cancel();
   }

   @Redirect(
      method = {"renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/renderer/entity/RenderManager;playerViewX:F"
)
   )
   private float renderName(RenderManager renderManager) {
      return Minecraft.func_71410_x().field_71474_y.field_74320_O == 2 ? -renderManager.field_78732_j : renderManager.field_78732_j;
   }
}
