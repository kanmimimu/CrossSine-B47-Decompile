package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Chams;
import net.ccbluex.liquidbounce.features.module.modules.visual.ItemPhysics;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderEntityItem.class})
public abstract class MixinRenderEntityItem extends Render {
   protected MixinRenderEntityItem(RenderManager p_i46179_1_) {
      super(p_i46179_1_);
   }

   @Shadow
   protected abstract int func_177078_a(ItemStack var1);

   @Inject(
      method = {"doRender"},
      at = {@At("HEAD")}
   )
   private void injectChamsPre(CallbackInfo callbackInfo) {
      Chams chams = (Chams)CrossSine.moduleManager.getModule(Chams.class);
      if (chams.getState() && (Boolean)chams.getItemsValue().get()) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1000000.0F);
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("RETURN")}
   )
   private void injectChamsPost(CallbackInfo callbackInfo) {
      Chams chams = (Chams)CrossSine.moduleManager.getModule(Chams.class);
      if (chams.getState() && (Boolean)chams.getItemsValue().get()) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }

   @Overwrite
   private int func_177077_a(EntityItem itemIn, double x, double y, double z, float p_177077_8_, IBakedModel ibakedmodel) {
      ItemStack itemStack = itemIn.func_92059_d();
      Item item = itemStack.func_77973_b();
      if (item != null && itemStack != null) {
         boolean isGui3d = ibakedmodel.func_177556_c();
         int count = this.getItemCount(itemStack);
         float yOffset = 0.25F;
         float age = (float)itemIn.func_174872_o() + p_177077_8_;
         float hoverStart = itemIn.field_70290_d;
         boolean isPhysicsState = ItemPhysics.INSTANCE.getState();
         float weight = isPhysicsState ? 0.5F : 0.0F;
         float sinValue = (float)(Math.sin((double)(age / 10.0F + hoverStart)) * (double)0.1F + (double)0.1F);
         if (isPhysicsState) {
            sinValue = 0.0F;
         }

         float scaleY = ibakedmodel.func_177552_f().func_181688_b(TransformType.GROUND).field_178363_d.y;
         if (isPhysicsState) {
            GlStateManager.func_179109_b((float)x, (float)y, (float)z);
         } else {
            GlStateManager.func_179109_b((float)x, (float)y + sinValue + yOffset * scaleY, (float)z);
         }

         if (isGui3d) {
            GlStateManager.func_179137_b((double)0.0F, (double)0.0F, -0.08);
         } else {
            GlStateManager.func_179137_b((double)0.0F, (double)0.0F, -0.04);
         }

         if (isGui3d || this.field_76990_c.field_78733_k != null) {
            float rotationYaw = (age / 20.0F + hoverStart) * (180F / (float)Math.PI);
            rotationYaw *= (Float)ItemPhysics.INSTANCE.getRotationSpeed().get() * (1.0F + Math.min(age / 360.0F, 1.0F));
            if (isPhysicsState) {
               if (itemIn.field_70122_E) {
                  GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                  GL11.glRotatef(itemIn.field_70177_z, 0.0F, 0.0F, 1.0F);
               } else {
                  for(int a = 0; a < 10; ++a) {
                     GL11.glRotatef(rotationYaw, weight, weight, 1.0F);
                  }
               }
            } else {
               GlStateManager.func_179114_b(rotationYaw, 0.0F, 1.0F, 0.0F);
            }
         }

         if (!isGui3d) {
            float offsetX = -0.0F * (float)(count - 1) * 0.5F;
            float offsetY = -0.0F * (float)(count - 1) * 0.5F;
            float offsetZ = -0.09375F * (float)(count - 1) * 0.5F;
            GlStateManager.func_179109_b(offsetX, offsetY, offsetZ);
         }

         RenderUtils.color((double)1.0F, (double)1.0F, (double)1.0F, (double)1.0F);
         return count;
      } else {
         return 0;
      }
   }

   private int getItemCount(ItemStack stack) {
      int size = stack.field_77994_a;
      if (size > 48) {
         return 5;
      } else if (size > 32) {
         return 4;
      } else if (size > 16) {
         return 3;
      } else {
         return size > 1 ? 2 : 1;
      }
   }
}
