package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Render {
   public float alpha = 255.0F;
   public Vec3 vec3;
   public long time;
   public float d;
   public Color color;

   public Render(double x, double y, double z, long time, Color color) {
      this.vec3 = new Vec3(x, y, z);
      this.time = time;
      this.color = color;
   }

   public void draw() {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      GL11.glDisable(3553);
      GlStateManager.func_179097_i();
      GL11.glEnable(2848);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(3.0F);
      GL11.glBegin(3);
      double renderPosX = Minecraft.func_71410_x().func_175598_ae().field_78730_l;
      double renderPosY = Minecraft.func_71410_x().func_175598_ae().field_78731_m;
      double renderPosZ = Minecraft.func_71410_x().func_175598_ae().field_78728_n;
      RenderUtils.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), (int)this.alpha));

      for(int i = 0; i <= 360; ++i) {
         GL11.glVertex3d(this.vec3.field_72450_a - renderPosX + Math.cos((double)i * Math.PI / (double)180.0F) * 0.6 * (double)this.d, this.vec3.field_72448_b - renderPosY, this.vec3.field_72449_c - renderPosZ + Math.sin((double)i * Math.PI / (double)180.0F) * 0.6 * (double)this.d);
      }

      GL11.glEnd();
      GL11.glBegin(5);

      for(int i = 0; i <= 360; i += 10) {
         for(int var11 = 0; var11 <= 3; ++var11) {
            GL11.glVertex3d(this.vec3.field_72450_a - renderPosX + -Math.sin(Math.toRadians((double)i)) * (double)this.d, this.vec3.field_72448_b - renderPosY, this.vec3.field_72449_c - renderPosZ + Math.cos(Math.toRadians((double)i)) * (double)this.d);
            GL11.glVertex3d(this.vec3.field_72450_a - renderPosX + -Math.sin(Math.toRadians((double)i)) * ((double)this.d - (double)var11 / (double)10.0F), this.vec3.field_72448_b - renderPosY, this.vec3.field_72449_c - renderPosZ + Math.cos(Math.toRadians((double)i)) * ((double)this.d - (double)var11 / (double)10.0F));
         }
      }

      double var14 = (double)0.0F;
      if (var14 < (double)361.0F) {
         var14 += (double)5.0F;
      }

      double var15 = (double)0.0F;
      if (var15 < (double)255.0F) {
         var15 += (double)3.0F;
      }

      GL11.glEnd();
      GL11.glDepthMask(true);
      GlStateManager.func_179126_j();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      if (this.d == 1.5F) {
         this.alpha = MathHelper.func_76131_a(this.alpha = (float)((double)this.alpha - (double)1.0F * var15), 0.0F, 255.0F);
      }

      this.d = MathHelper.func_76131_a(this.d = (float)((double)this.d + 0.005 * var14), 0.0F, 1.5F);
   }

   public float alpha() {
      return this.alpha;
   }
}
