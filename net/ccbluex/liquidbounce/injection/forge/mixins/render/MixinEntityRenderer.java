package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Reach;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.visual.CameraModule;
import net.ccbluex.liquidbounce.features.module.modules.visual.FreeLook;
import net.ccbluex.liquidbounce.features.module.modules.visual.HurtCam;
import net.ccbluex.liquidbounce.features.module.modules.visual.Tracers;
import net.ccbluex.liquidbounce.features.module.modules.visual.ViewBobing;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public abstract class MixinEntityRenderer {
   @Shadow
   private final int[] field_78504_Q;
   @Shadow
   private final DynamicTexture field_78513_d;
   @Shadow
   private float field_78514_e;
   @Shadow
   private float field_82831_U;
   @Shadow
   private float field_82832_V;
   @Shadow
   private Entity field_78528_u;
   @Shadow
   private boolean field_78536_aa;
   @Shadow
   private Minecraft field_78531_r;
   @Shadow
   private float field_78491_C;
   @Shadow
   private float field_78490_B;
   @Shadow
   private boolean field_78500_U;
   public double prevRenderX = (double)0.0F;
   public double prevRenderY = (double)0.0F;
   public double prevRenderZ = (double)0.0F;
   private static final double SMOOTHING_FACTOR = 0.05;

   protected MixinEntityRenderer(int[] lightmapColors, DynamicTexture lightmapTexture, float torchFlickerX, float bossColorModifier, float bossColorModifierPrev, Minecraft mc, float thirdPersonDistanceTemp, float thirdPersonDistance) {
      this.field_78504_Q = lightmapColors;
      this.field_78513_d = lightmapTexture;
      this.field_78514_e = torchFlickerX;
      this.field_82831_U = bossColorModifier;
      this.field_82832_V = bossColorModifierPrev;
      this.field_78531_r = mc;
      this.field_78491_C = thirdPersonDistanceTemp;
      this.field_78490_B = thirdPersonDistance;
   }

   @Shadow
   public abstract void func_175069_a(ResourceLocation var1);

   @Shadow
   public abstract void func_78479_a(float var1, int var2);

   @Inject(
      method = {"renderWorldPass"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z",
   shift = At.Shift.BEFORE
)}
   )
   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
      CrossSine.eventManager.callEvent(new Render3DEvent(partialTicks));
   }

   @Inject(
      method = {"hurtCameraEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void injectHurtCameraEffect(CallbackInfo callbackInfo) {
      if (!((String)((HurtCam)CrossSine.moduleManager.getModule(HurtCam.class)).getModeValue().get()).equalsIgnoreCase("Vanilla")) {
         callbackInfo.cancel();
      }

   }

   private float getRotationYaw(float yaw) {
      return (!((FreeLook)CrossSine.moduleManager.getModule(FreeLook.class)).getState() || RotationUtils.freeLookRotation != null) && (RotationUtils.freeLookRotation == null || ((FreeLook)CrossSine.moduleManager.getModule(FreeLook.class)).getState()) ? (((FreeLook)CrossSine.moduleManager.getModule(FreeLook.class)).getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : yaw) : FreeLook.cameraYaw;
   }

   private float getRotationPitch(float pitch) {
      return (!((FreeLook)CrossSine.moduleManager.getModule(FreeLook.class)).getState() || RotationUtils.freeLookRotation != null) && (RotationUtils.freeLookRotation == null || ((FreeLook)CrossSine.moduleManager.getModule(FreeLook.class)).getState()) ? (((FreeLook)CrossSine.moduleManager.getModule(FreeLook.class)).getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraPitch2 : pitch) : FreeLook.cameraPitch;
   }

   @Overwrite
   private void func_78467_g(float p_orientCamera_1_) {
      Entity entity = this.field_78531_r.func_175606_aa();
      float f = entity.func_70047_e();
      double d0 = entity.field_70169_q + (entity.field_70165_t - entity.field_70169_q) * (double)p_orientCamera_1_;
      double d1 = entity.field_70167_r + (entity.field_70163_u - entity.field_70167_r) * (double)p_orientCamera_1_ + (double)f;
      double d2 = entity.field_70166_s + (entity.field_70161_v - entity.field_70166_s) * (double)p_orientCamera_1_;
      this.prevRenderX = this.prevRenderX * 0.95 + d0 * 0.05;
      this.prevRenderY = this.prevRenderY * 0.95 + d1 * 0.05;
      this.prevRenderZ = this.prevRenderZ * 0.95 + d2 * 0.05;
      if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70608_bn()) {
         f = (float)((double)f + (double)1.0F);
         GlStateManager.func_179109_b(0.0F, 0.3F, 0.0F);
         if (!this.field_78531_r.field_71474_y.field_74325_U) {
            BlockPos blockpos = new BlockPos(entity);
            IBlockState iblockstate = this.field_78531_r.field_71441_e.func_180495_p(blockpos);
            ForgeHooksClient.orientBedCamera(this.field_78531_r.field_71441_e, blockpos, iblockstate, entity);
            GlStateManager.func_179114_b(this.getRotationYaw(entity.field_70126_B) + (this.getRotationYaw(entity.field_70177_z) - this.getRotationYaw(entity.field_70126_B)) * p_orientCamera_1_ + 180.0F, 0.0F, -1.0F, 0.0F);
            GlStateManager.func_179114_b(this.getRotationPitch(entity.field_70127_C) + (this.getRotationPitch(entity.field_70125_A) - this.getRotationPitch(entity.field_70125_A)) * p_orientCamera_1_, -1.0F, 0.0F, 0.0F);
         }
      } else if (this.field_78531_r.field_71474_y.field_74320_O > 0) {
         double d3 = (double)(this.field_78491_C + (this.field_78490_B - this.field_78491_C) * p_orientCamera_1_);
         if (this.field_78531_r.field_71474_y.field_74325_U) {
            GlStateManager.func_179109_b(0.0F, 0.0F, (float)(-d3));
         } else {
            float f1 = this.getRotationYaw(entity.field_70177_z);
            float f2 = this.getRotationPitch(entity.field_70125_A);
            if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
               f2 += 180.0F;
            }

            double d4 = (double)(-MathHelper.func_76126_a(f1 / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(f2 / 180.0F * (float)Math.PI)) * d3;
            double d5 = (double)(MathHelper.func_76134_b(f1 / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(f2 / 180.0F * (float)Math.PI)) * d3;
            double d6 = (double)(-MathHelper.func_76126_a(f2 / 180.0F * (float)Math.PI)) * d3;

            for(int i = 0; i < 8; ++i) {
               float f3 = (float)((i & 1) * 2 - 1);
               float f4 = (float)((i >> 1 & 1) * 2 - 1);
               float f5 = (float)((i >> 2 & 1) * 2 - 1);
               f3 *= 0.1F;
               f4 *= 0.1F;
               f5 *= 0.1F;
               MovingObjectPosition movingobjectposition = this.field_78531_r.field_71441_e.func_72933_a(new Vec3(d0 + (double)f3, d1 + (double)f4, d2 + (double)f5), new Vec3(d0 - d4 + (double)f3 + (double)f5, d1 - d6 + (double)f4, d2 - d5 + (double)f5));
               if (movingobjectposition != null) {
                  double d7 = movingobjectposition.field_72307_f.func_72438_d(new Vec3(d0, d1, d2));
                  if ((!CameraModule.INSTANCE.getState() || !(Boolean)CameraModule.INSTANCE.getClipValue().get()) && d7 < d3) {
                     d3 = d7;
                  }
               }
            }

            if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
               GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.func_179114_b(this.getRotationPitch(entity.field_70125_A) - f2, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(this.getRotationYaw(entity.field_70177_z) - f1, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, 0.0F, (float)(-d3));
            GlStateManager.func_179114_b(f1 - this.getRotationYaw(entity.field_70177_z), 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(f2 - this.getRotationPitch(entity.field_70125_A), 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(this.getRotationYaw(entity.field_70177_z), 0.0F, 1.0F, 0.0F);
            if (CameraModule.INSTANCE.getState() && (Boolean)CameraModule.INSTANCE.getSmoothValue().get()) {
               GlStateManager.func_179137_b(this.prevRenderX - d0, d1 - this.prevRenderY, this.prevRenderZ - d2);
            }

            GlStateManager.func_179114_b(-this.getRotationYaw(entity.field_70177_z), 0.0F, 1.0F, 0.0F);
         }
      } else {
         GlStateManager.func_179109_b(0.0F, 0.0F, -0.1F);
      }

      if (!this.field_78531_r.field_71474_y.field_74325_U) {
         float yaw = this.getRotationYaw(entity.field_70126_B) + (this.getRotationYaw(entity.field_70177_z) - this.getRotationYaw(entity.field_70126_B)) * p_orientCamera_1_ + 180.0F;
         float pitch = this.getRotationPitch(entity.field_70127_C) + (this.getRotationPitch(entity.field_70125_A) - this.getRotationPitch(entity.field_70127_C)) * p_orientCamera_1_;
         float roll = 0.0F;
         if (entity instanceof EntityAnimal) {
            EntityAnimal entityanimal = (EntityAnimal)entity;
            yaw = entityanimal.field_70758_at + (entityanimal.field_70759_as - entityanimal.field_70758_at) * p_orientCamera_1_ + 180.0F;
         }

         Block block = ActiveRenderInfo.func_180786_a(this.field_78531_r.field_71441_e, entity, p_orientCamera_1_);
         EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup((EntityRenderer)this, entity, block, (double)p_orientCamera_1_, yaw, pitch, roll);
         MinecraftForge.EVENT_BUS.post(event);
         GlStateManager.func_179114_b(event.roll, 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179114_b(event.pitch, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(event.yaw, 0.0F, 1.0F, 0.0F);
      }

      GlStateManager.func_179109_b(0.0F, -f, 0.0F);
      d0 = entity.field_70169_q + (entity.field_70165_t - entity.field_70169_q) * (double)p_orientCamera_1_;
      d1 = entity.field_70167_r + (entity.field_70163_u - entity.field_70167_r) * (double)p_orientCamera_1_ + (double)f;
      d2 = entity.field_70166_s + (entity.field_70161_v - entity.field_70166_s) * (double)p_orientCamera_1_;
      this.field_78500_U = this.field_78531_r.field_71438_f.func_72721_a(d0, d1, d2, p_orientCamera_1_);
   }

   @Inject(
      method = {"setupCameraTransform"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V",
   shift = At.Shift.BEFORE
)}
   )
   private void setupCameraViewBobbingBefore(CallbackInfo callbackInfo) {
      if (((Tracers)CrossSine.moduleManager.getModule(Tracers.class)).getState()) {
         GL11.glPushMatrix();
      }

   }

   @Inject(
      method = {"setupCameraTransform"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V",
   shift = At.Shift.AFTER
)}
   )
   private void setupCameraViewBobbingAfter(CallbackInfo callbackInfo) {
      if (((Tracers)CrossSine.moduleManager.getModule(Tracers.class)).getState()) {
         GL11.glPopMatrix();
      }

   }

   @Redirect(
      method = {"updateCameraAndRender"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/Minecraft;inGameHasFocus:Z",
   opcode = 180
)
   )
   public boolean updateCameraAndRender(Minecraft minecraft) {
      if (FreeLook.isEnabled) {
         return !(Boolean)((FreeLook)CrossSine.moduleManager.getModule(FreeLook.class)).getReverse().get() ? FreeLook.overrideMouse() : true;
      } else {
         return this.field_78531_r.field_71415_G && Display.isActive();
      }
   }

   @Redirect(
      method = {"setupCameraTransform"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/settings/GameSettings;viewBobbing:Z",
   ordinal = 0
)
   )
   public boolean setupCameraTransform(GameSettings instance) {
      return (!(Boolean)((ViewBobing)CrossSine.moduleManager.getModule(ViewBobing.class)).getMiniViewBobing().get() || ((ViewBobing)CrossSine.moduleManager.getModule(ViewBobing.class)).getState()) && this.field_78531_r.field_71474_y.field_74336_f;
   }

   @Inject(
      method = {"getMouseOver"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getMouseOver(float p_getMouseOver_1_, CallbackInfo ci) {
      Entity entity = this.field_78531_r.func_175606_aa();
      if (entity != null && this.field_78531_r.field_71441_e != null) {
         this.field_78531_r.field_71424_I.func_76320_a("pick");
         this.field_78531_r.field_147125_j = null;
         double d0 = (double)this.field_78531_r.field_71442_b.func_78757_d();
         Vec3 vec3 = entity.func_174824_e(p_getMouseOver_1_);
         Rotation rotation = new Rotation(this.field_78531_r.field_71439_g.field_70177_z, this.field_78531_r.field_71439_g.field_70125_A);
         Vec3 vec31 = RotationUtils.getVectorForRotation(RotationUtils.targetRotation != null ? RotationUtils.targetRotation : rotation);
         Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0);
         this.field_78531_r.field_71476_x = entity.field_70170_p.func_147447_a(vec3, vec32, false, false, true);
         double d1 = d0;
         boolean flag = false;
         if (this.field_78531_r.field_71442_b.func_78749_i()) {
            d1 = (double)6.0F;
         } else if (d0 > (double)3.0F) {
            flag = true;
         }

         if (this.field_78531_r.field_71476_x != null) {
            d1 = this.field_78531_r.field_71476_x.field_72307_f.func_72438_d(vec3);
         }

         this.field_78528_u = null;
         Vec3 vec33 = null;
         List<Entity> list = this.field_78531_r.field_71441_e.func_175644_a(Entity.class, Predicates.and(EntitySelectors.field_180132_d, (p_apply_1_) -> p_apply_1_ != null && p_apply_1_.func_70067_L() && p_apply_1_ != entity));
         double d2 = d1;

         for(Entity entity1 : list) {
            float f1 = entity1.func_70111_Y();
            ArrayList<AxisAlignedBB> boxes = new ArrayList();
            boxes.add(entity1.func_174813_aQ().func_72314_b((double)f1, (double)f1, (double)f1));

            for(AxisAlignedBB axisalignedbb : boxes) {
               MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(vec3, vec32);
               if (axisalignedbb.func_72318_a(vec3)) {
                  if (d2 >= (double)0.0F) {
                     this.field_78528_u = entity1;
                     vec33 = movingobjectposition == null ? vec3 : movingobjectposition.field_72307_f;
                     d2 = (double)0.0F;
                  }
               } else if (movingobjectposition != null) {
                  double d3 = vec3.func_72438_d(movingobjectposition.field_72307_f);
                  if (d3 < d2 || d2 == (double)0.0F) {
                     if (entity1 == entity.field_70154_o && !entity.canRiderInteract()) {
                        if (d2 == (double)0.0F) {
                           this.field_78528_u = entity1;
                           vec33 = movingobjectposition.field_72307_f;
                        }
                     } else {
                        this.field_78528_u = entity1;
                        vec33 = movingobjectposition.field_72307_f;
                        d2 = d3;
                     }
                  }
               }
            }
         }

         if (this.field_78528_u != null && flag && vec3.func_72438_d(vec33) > (SilentAura.INSTANCE.getState() ? SilentAura.INSTANCE.getReach() : (Reach.INSTANCE.getState() ? Reach.INSTANCE.getReach() : (double)3.0F))) {
            this.field_78528_u = null;
            this.field_78531_r.field_71476_x = new MovingObjectPosition(MovingObjectType.MISS, (Vec3)Objects.requireNonNull(vec33), (EnumFacing)null, new BlockPos(vec33));
         }

         if (this.field_78528_u != null && (d2 < d1 || this.field_78531_r.field_71476_x == null)) {
            this.field_78531_r.field_71476_x = new MovingObjectPosition(this.field_78528_u, vec33);
            if (this.field_78528_u instanceof EntityLivingBase || this.field_78528_u instanceof EntityItemFrame) {
               this.field_78531_r.field_147125_j = this.field_78528_u;
            }
         }

         this.field_78531_r.field_71424_I.func_76319_b();
      }

      ci.cancel();
   }

   private float getNightVisionBrightness(EntityLivingBase p_getNightVisionBrightness_1_, float p_getNightVisionBrightness_2_) {
      int i = p_getNightVisionBrightness_1_.func_70660_b(Potion.field_76439_r).func_76459_b();
      return i > 200 ? 1.0F : 0.7F + MathHelper.func_76126_a(((float)i - p_getNightVisionBrightness_2_) * (float)Math.PI * 0.2F) * 0.3F;
   }
}
