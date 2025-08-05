package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.features.module.modules.visual.NoRender;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemCarrotOnAStick;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
public abstract class MixinItemRenderer {
   @Shadow
   private float field_78451_d;
   @Shadow
   private float field_78454_c;
   @Shadow
   private int field_78450_g = -1;
   @Shadow
   @Final
   private Minecraft field_78455_a;
   @Shadow
   private ItemStack field_78453_b;
   @Shadow
   private RenderItem field_178112_h;
   private Animations animations;

   @Shadow
   protected abstract void func_178101_a(float var1, float var2);

   @Shadow
   protected abstract void func_178109_a(AbstractClientPlayer var1);

   @Shadow
   protected abstract void func_178110_a(EntityPlayerSP var1, float var2);

   @Shadow
   public abstract void func_178099_a(EntityLivingBase var1, ItemStack var2, ItemCameraTransforms.TransformType var3);

   @Shadow
   protected abstract void func_178097_a(AbstractClientPlayer var1, float var2, float var3, float var4);

   @Shadow
   protected abstract boolean func_178107_a(Block var1);

   @Shadow
   protected abstract void func_178104_a(AbstractClientPlayer var1, float var2);

   @Shadow
   protected abstract void func_178103_d();

   @Shadow
   protected abstract void func_178098_a(float var1, AbstractClientPlayer var2);

   @Shadow
   protected abstract void func_178105_d(float var1);

   @Shadow
   protected abstract void func_178095_a(AbstractClientPlayer var1, float var2, float var3);

   @Inject(
      method = {"updateEquippedItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void updateEquippedItemHead(CallbackInfo i) {
      this.field_78451_d = this.field_78454_c;
      ItemStack itemstack = SlotUtils.INSTANCE.getStack();
      boolean flag = false;
      if (this.field_78453_b != null && itemstack != null) {
         if (!this.field_78453_b.func_179549_c(itemstack)) {
            if (!this.field_78453_b.func_77973_b().shouldCauseReequipAnimation(this.field_78453_b, itemstack, this.field_78450_g != SlotUtils.INSTANCE.getSlot())) {
               this.field_78453_b = itemstack;
               this.field_78450_g = SlotUtils.INSTANCE.getSlot();
               return;
            }

            flag = true;
         }
      } else {
         flag = this.field_78453_b != null || itemstack != null;
      }

      float f = 0.4F;
      float f1 = flag ? 0.0F : 1.0F;
      float f2 = MathHelper.func_76131_a(f1 - this.field_78454_c, -f, f);
      this.field_78454_c += f2;
      if (this.field_78454_c < 0.1F) {
         this.field_78453_b = itemstack;
         this.field_78450_g = SlotUtils.INSTANCE.getSlot();
      }

      i.cancel();
   }

   @Overwrite
   private void func_178096_b(float equipProgress, float swingProgress) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179109_b(0.0F, equipProgress * -0.6F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      float f = MathHelper.func_76126_a(swingProgress * swingProgress * (float)Math.PI);
      float f1 = MathHelper.func_76126_a(MathHelper.func_76129_c(swingProgress) * (float)Math.PI);
      GlStateManager.func_179114_b(f * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
      this.doItemRenderGLScale();
   }

   private void oldBlockAnimation() {
      GlStateManager.func_179109_b(-0.5F, 0.4F, -0.1F);
      GlStateManager.func_179114_b(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(60.0F, 0.0F, 1.0F, 0.0F);
   }

   @Overwrite
   public void func_78440_a(float partialTicks) {
      if (this.animations == null) {
         this.animations = (Animations)CrossSine.moduleManager.getModule(Animations.class);
      }

      float f = 1.0F - (this.field_78451_d + (this.field_78454_c - this.field_78451_d) * partialTicks);
      EntityPlayerSP abstractclientplayer = this.field_78455_a.field_71439_g;
      float f1 = abstractclientplayer.func_70678_g(partialTicks);
      float f2 = abstractclientplayer.field_70127_C + (abstractclientplayer.field_70125_A - abstractclientplayer.field_70127_C) * partialTicks;
      float f3 = abstractclientplayer.field_70126_B + (abstractclientplayer.field_70177_z - abstractclientplayer.field_70126_B) * partialTicks;
      this.func_178101_a(f2, f3);
      this.func_178109_a(abstractclientplayer);
      this.func_178110_a(abstractclientplayer, partialTicks);
      GlStateManager.func_179091_B();
      GlStateManager.func_179094_E();
      if (this.field_78453_b != null) {
         if ((Boolean)Animations.INSTANCE.getBlockAnimation().get() && (this.field_78453_b.func_77973_b() instanceof ItemCarrotOnAStick || this.field_78453_b.func_77973_b() instanceof ItemFishingRod)) {
            GlStateManager.func_179109_b(0.08F, -0.027F, -0.33F);
            GlStateManager.func_179152_a(0.93F, 1.0F, 1.0F);
         }

         if (this.field_78453_b.func_77973_b() instanceof ItemMap) {
            this.func_178097_a(abstractclientplayer, f2, f, f1);
         } else if (!abstractclientplayer.func_71039_bw() && (!(this.field_78453_b.func_77973_b() instanceof ItemSword) || (!KillAura.INSTANCE.getState() || !KillAura.INSTANCE.getDisplayBlocking() || KillAura.INSTANCE.getCurrentTarget() == null) && (!SilentAura.INSTANCE.getState() || !SilentAura.INSTANCE.getCanBlock() || SilentAura.INSTANCE.getTarget() == null))) {
            if (!this.animations.getState() || !(Boolean)this.animations.getFluxAnimation().get()) {
               this.func_178105_d(f1);
            }

            this.func_178096_b(f, f1);
         } else {
            label172:
            switch (this.field_78453_b.func_77975_n()) {
               case NONE:
                  this.func_178096_b(f, 0.0F);
                  break;
               case EAT:
               case DRINK:
                  this.func_178104_a(abstractclientplayer, partialTicks);
                  if ((Boolean)Animations.INSTANCE.getBlockAnimation().get()) {
                     this.func_178096_b(f, f1);
                  } else {
                     this.func_178096_b(f, 0.0F);
                  }
                  break;
               case BLOCK:
                  if (this.animations.getState()) {
                     switch ((String)this.animations.getBlockingModeValue().get()) {
                        case "1.7":
                           this.func_178096_b(f, f1);
                           this.func_178103_d();
                           break label172;
                        case "Akrien":
                           this.func_178096_b(f1, 0.0F);
                           this.func_178103_d();
                           break label172;
                        case "Avatar":
                           this.avatar(f1);
                           this.func_178103_d();
                           break label172;
                        case "ETB":
                           this.etb(f, f1);
                           this.func_178103_d();
                           break label172;
                        case "Exhibition":
                           this.func_178096_b(f, 0.83F);
                           float f4 = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * 3.83F);
                           GlStateManager.func_179109_b(-0.5F, 0.2F, 0.2F);
                           GlStateManager.func_179114_b(-f4 * 0.0F, 0.0F, 0.0F, 0.0F);
                           GlStateManager.func_179114_b(-f4 * 43.0F, 58.0F, 23.0F, 45.0F);
                           this.func_178103_d();
                           break label172;
                        case "Push":
                           this.push(f1);
                           this.func_178103_d();
                           break label172;
                        case "Reverse":
                           this.func_178096_b(f1, f1);
                           this.func_178103_d();
                           GlStateManager.func_179114_b(0.0F, 1.0F, 0.0F, 0.0F);
                           break label172;
                        case "Shield":
                           this.jello(f1);
                           this.func_178103_d();
                           break label172;
                        case "SigmaNew":
                           this.doItemRenderGLTranslate();
                           GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
                           float var11 = MathHelper.func_76126_a(f1 * f1 * (float)Math.PI);
                           float var12 = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * (float)Math.PI);
                           GlStateManager.func_179114_b(var12 * -5.0F, 1.0F, 0.0F, 0.0F);
                           GlStateManager.func_179114_b(var12 * 0.0F, 0.0F, 0.0F, 1.0F);
                           GlStateManager.func_179114_b(var12 * 25.0F, 0.0F, 1.0F, 0.0F);
                           this.doItemRenderGLScale();
                           this.func_178103_d();
                           break label172;
                        case "SigmaOld":
                           this.sigmaOld(f);
                           float var15 = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * (float)Math.PI);
                           GlStateManager.func_179114_b(-var15 * 55.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                           GlStateManager.func_179114_b(-var15 * 45.0F, 1.0F, var15 / 2.0F, -0.0F);
                           this.func_178103_d();
                           GL11.glTranslated(1.2, 0.3, (double)0.5F);
                           GL11.glTranslatef(-1.0F, this.field_78455_a.field_71439_g.func_70093_af() ? -0.1F : -0.2F, 0.2F);
                           GlStateManager.func_179152_a(1.2F, 1.2F, 1.2F);
                           break label172;
                        case "Slide":
                           this.slide(f1);
                           this.func_178103_d();
                           break label172;
                        case "SlideDown":
                           this.func_178096_b(0.2F, f1);
                           this.func_178103_d();
                           break label172;
                        case "Swong":
                           this.func_178096_b(f / 2.0F, 0.0F);
                           GlStateManager.func_179114_b(-MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * (float)Math.PI) * 40.0F / 2.0F, MathHelper.func_76129_c(f1) / 2.0F, -0.0F, 9.0F);
                           GlStateManager.func_179114_b(-MathHelper.func_76129_c(f1) * 30.0F, 1.0F, MathHelper.func_76129_c(f1) / 2.0F, -0.0F);
                           this.func_178103_d();
                           break label172;
                        case "VisionFX":
                           this.continuity(f1);
                           this.func_178103_d();
                           break label172;
                        case "Swank":
                           GL11.glTranslated(-0.1, 0.15, (double)0.0F);
                           this.func_178096_b(f / 0.15F, f1);
                           float rot = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * (float)Math.PI);
                           GlStateManager.func_179114_b(rot * 30.0F, 2.0F, -rot, 9.0F);
                           GlStateManager.func_179114_b(rot * 35.0F, 1.0F, -rot, -0.0F);
                           this.func_178103_d();
                           break label172;
                        case "Jello":
                           this.func_178096_b(0.0F, 0.0F);
                           this.func_178103_d();
                           int alpha = (int)Math.min(255L, (System.currentTimeMillis() % 255L > 127L ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : System.currentTimeMillis() % 255L) * 2L);
                           GlStateManager.func_179109_b(0.3F, -0.0F, 0.4F);
                           GlStateManager.func_179114_b(0.0F, 0.0F, 0.0F, 1.0F);
                           GlStateManager.func_179109_b(0.0F, 0.5F, 0.0F);
                           GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, -1.0F);
                           GlStateManager.func_179109_b(0.6F, 0.5F, 0.0F);
                           GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, -1.0F);
                           GlStateManager.func_179114_b(-10.0F, 1.0F, 0.0F, -1.0F);
                           GlStateManager.func_179114_b(abstractclientplayer.field_82175_bq ? (float)(-alpha) / 5.0F : 1.0F, 1.0F, -0.0F, 1.0F);
                           break label172;
                        case "HSlide":
                           this.func_178096_b(f1 != 0.0F ? Math.max(1.0F - f1 * 2.0F, 0.0F) * 0.7F : 0.0F, 1.0F);
                           this.func_178103_d();
                           break label172;
                        case "None":
                           this.func_178096_b(0.0F, 0.0F);
                           this.func_178103_d();
                           break label172;
                        case "Rotate":
                           this.rotateSword(f1);
                           break label172;
                        case "Liquid":
                           this.func_178096_b(f + 0.1F, f1);
                           this.func_178103_d();
                           GlStateManager.func_179109_b(-0.5F, 0.2F, 0.0F);
                           break label172;
                        case "Fall":
                           this.doItemRenderGLTranslate();
                           GlStateManager.func_179109_b(0.0F, f * -0.6F, 0.0F);
                           GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
                           this.doItemRenderGLScale();
                           this.func_178103_d();
                           break label172;
                        case "Yeet":
                           this.doItemRenderGLTranslate();
                           GlStateManager.func_179109_b(0.0F, f * -0.6F, 0.0F);
                           GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
                           float var11 = MathHelper.func_76126_a(f1 * f1 * (float)Math.PI);
                           float var12 = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * (float)Math.PI);
                           GlStateManager.func_179114_b(var11 * 0.0F, 0.0F, 1.0F, 0.0F);
                           GlStateManager.func_179114_b(var12 * 0.0F, 0.0F, 0.0F, 1.0F);
                           GlStateManager.func_179114_b(var12 * -40.0F + 10.0F, 1.0F, 0.0F, 0.0F);
                           this.doItemRenderGLScale();
                           this.func_178103_d();
                           break label172;
                        case "Yeet2":
                           this.doItemRenderGLTranslate();
                           GlStateManager.func_179109_b(0.0F, f * -0.8F, 0.0F);
                           GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
                           float var11 = MathHelper.func_76126_a(f1 * f1 * (float)Math.PI);
                           float var12 = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * (float)Math.PI);
                           GlStateManager.func_179114_b(var11 * 0.0F, 0.0F, 1.0F, 0.0F);
                           GlStateManager.func_179114_b(var12 * 0.0F, 0.0F, 0.0F, 1.0F);
                           GlStateManager.func_179114_b(var12 * -20.0F - 9.5F, 1.0F, 0.0F, 0.0F);
                           this.doItemRenderGLScale();
                           this.func_178103_d();
                           break label172;
                        case "Dortware":
                           float var9 = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * (float)Math.PI);
                           GL11.glTranslated(-0.04, (double)0.0F, (double)0.0F);
                           this.func_178096_b(f / 2.5F, 0.0F);
                           GlStateManager.func_179114_b(-var9 * 0.0F / 2.0F, var9 / 2.0F, 1.0F, 4.0F);
                           GlStateManager.func_179114_b(-var9 * 120.0F, 1.0F, var9 / 3.0F, -0.0F);
                           GlStateManager.func_179109_b(-0.5F, 0.2F, 0.0F);
                           GlStateManager.func_179114_b(30.0F, 0.0F, 1.0F, 0.0F);
                           GlStateManager.func_179114_b(-80.0F, 1.0F, 0.0F, 0.0F);
                           GlStateManager.func_179114_b(60.0F, 0.0F, 1.0F, 0.0F);
                     }
                  } else {
                     this.func_178096_b(f + 0.1F, f1);
                     this.func_178103_d();
                     GlStateManager.func_179109_b(-0.5F, 0.2F, 0.0F);
                  }
                  break;
               case BOW:
                  if ((Boolean)Animations.INSTANCE.getBlockAnimation().get()) {
                     this.func_178096_b(f, f1);
                  } else {
                     this.func_178096_b(f, 0.0F);
                  }

                  this.func_178098_a(partialTicks, abstractclientplayer);
            }
         }

         this.func_178099_a(abstractclientplayer, this.field_78453_b, TransformType.FIRST_PERSON);
      } else if (!abstractclientplayer.func_82150_aj()) {
         this.func_178095_a(abstractclientplayer, f, f1);
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_179101_C();
      RenderHelper.func_74518_a();
   }

   private void doItemRenderGLTranslate() {
      if (this.animations.getState()) {
         GlStateManager.func_179109_b(0.56F + (Float)this.animations.getItemPosXValue().get(), -0.52F + (Float)this.animations.getItemPosYValue().get(), -0.71999997F + (Float)this.animations.getItemPosZValue().get());
      } else {
         GlStateManager.func_179109_b(0.56F, -0.52F, -0.71999997F);
      }

   }

   private void doItemRenderGLScale() {
      if (this.animations.getState()) {
         GlStateManager.func_179139_a((double)((float)(Integer)Animations.INSTANCE.getItemScaleValue().get() / 100.0F) * 0.4, (double)((float)(Integer)Animations.INSTANCE.getItemScaleValue().get() / 100.0F) * 0.4, (double)((float)(Integer)Animations.INSTANCE.getItemScaleValue().get() / 100.0F) * 0.4);
      } else {
         GlStateManager.func_179139_a(0.4, 0.4, 0.4);
      }

   }

   private void sigmaOld(float f) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179109_b(0.0F, f * -0.6F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(0.0F, 0.0F, 1.0F, 0.2F);
      GlStateManager.func_179114_b(0.0F, 0.2F, 0.1F, 1.0F);
      GlStateManager.func_179114_b(0.0F, 1.3F, 0.1F, 0.2F);
      this.doItemRenderGLScale();
   }

   private void avatar(float swingProgress) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      float f = MathHelper.func_76126_a(swingProgress * swingProgress * (float)Math.PI);
      float f2 = MathHelper.func_76126_a(MathHelper.func_76129_c(swingProgress) * (float)Math.PI);
      GlStateManager.func_179114_b(f * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(f2 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(f2 * -40.0F, 1.0F, 0.0F, 0.0F);
      this.doItemRenderGLScale();
   }

   private void slide(float var9) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      float var11 = MathHelper.func_76126_a(var9 * var9 * (float)Math.PI);
      float var12 = MathHelper.func_76126_a(MathHelper.func_76129_c(var9) * (float)Math.PI);
      GlStateManager.func_179114_b(var11 * 0.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(var12 * 0.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(var12 * -40.0F, 1.0F, 0.0F, 0.0F);
      this.doItemRenderGLScale();
   }

   private void rotateSword(float f1) {
      this.genCustom();
      this.func_178103_d();
      GlStateManager.func_179109_b(-0.5F, 0.2F, 0.0F);
      GlStateManager.func_179114_b(MathHelper.func_76129_c(f1) * 10.0F * 40.0F, 1.0F, -0.0F, 2.0F);
   }

   private void genCustom() {
      GlStateManager.func_179109_b(0.56F, -0.52F, -0.71999997F);
      GlStateManager.func_179109_b(0.0F, -0.0F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.func_76126_a(0.0F);
      float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(0.0F) * (float)Math.PI);
      GlStateManager.func_179114_b(var3 * -34.0F, 0.0F, 1.0F, 0.2F);
      GlStateManager.func_179114_b(var4 * -20.7F, 0.2F, 0.1F, 1.0F);
      GlStateManager.func_179114_b(var4 * -68.6F, 1.3F, 0.1F, 0.2F);
      GlStateManager.func_179152_a(0.4F, 0.4F, 0.4F);
   }

   private void jello(float var12) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179114_b(48.57F, 0.0F, 0.24F, 0.14F);
      float var13 = MathHelper.func_76126_a(var12 * var12 * (float)Math.PI);
      float var14 = MathHelper.func_76126_a(MathHelper.func_76129_c(var12) * (float)Math.PI);
      GlStateManager.func_179114_b(var13 * -35.0F, 0.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(var14 * 0.0F, 0.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(var14 * 20.0F, 1.0F, 1.0F, 1.0F);
      this.doItemRenderGLScale();
   }

   private void continuity(float var10) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      float var12 = -MathHelper.func_76126_a(var10 * var10 * (float)Math.PI);
      float var13 = MathHelper.func_76134_b(MathHelper.func_76129_c(var10) * (float)Math.PI);
      float var14 = MathHelper.func_76135_e(MathHelper.func_76129_c(0.1F) * (float)Math.PI);
      GlStateManager.func_179114_b(var12 * var14 * 30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(var13 * 0.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(var13 * 20.0F, 1.0F, 0.0F, 0.0F);
      this.doItemRenderGLScale();
   }

   private void etb(float equipProgress, float swingProgress) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179109_b(0.0F, equipProgress * -0.6F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.func_76126_a(swingProgress * swingProgress * (float)Math.PI);
      float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(swingProgress) * (float)Math.PI);
      GlStateManager.func_179114_b(var3 * -34.0F, 0.0F, 1.0F, 0.2F);
      GlStateManager.func_179114_b(var4 * -20.7F, 0.2F, 0.1F, 1.0F);
      GlStateManager.func_179114_b(var4 * -68.6F, 1.3F, 0.1F, 0.2F);
      this.doItemRenderGLScale();
   }

   private void push(float idc) {
      this.doItemRenderGLTranslate();
      GlStateManager.func_179109_b(0.0F, -0.060000002F, 0.0F);
      GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.func_76126_a(idc * idc * (float)Math.PI);
      float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(idc) * (float)Math.PI);
      GlStateManager.func_179114_b(var3 * -10.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179114_b(var4 * -10.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179114_b(var4 * -10.0F, 1.0F, 1.0F, 1.0F);
      this.doItemRenderGLScale();
   }

   @Inject(
      method = {"renderFireInFirstPerson"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderFireInFirstPerson(CallbackInfo callbackInfo) {
      NoRender NoRender = (NoRender)CrossSine.moduleManager.getModule(NoRender.class);
      if (NoRender.getState() && (Boolean)NoRender.getFireEffect().get()) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.9F);
         GlStateManager.func_179143_c(519);
         GlStateManager.func_179132_a(false);
         GlStateManager.func_179147_l();
         GlStateManager.func_179120_a(770, 771, 1, 0);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179084_k();
         GlStateManager.func_179132_a(true);
         GlStateManager.func_179143_c(515);
         callbackInfo.cancel();
      }

   }
}
