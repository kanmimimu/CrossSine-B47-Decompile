package net.ccbluex.liquidbounce.utils.render.shader;

import java.awt.Color;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class FramebufferShader extends Shader {
   private static Framebuffer framebuffer;
   protected float red;
   protected float green;
   protected float blue;
   protected float alpha = 1.0F;
   protected float radius = 2.0F;
   protected float quality = 1.0F;
   private boolean entityShadows;

   public FramebufferShader(String fragmentShader) {
      super(fragmentShader);
   }

   public void startDraw(float partialTicks) {
      GlStateManager.func_179141_d();
      GlStateManager.func_179094_E();
      GlStateManager.func_179123_a();
      framebuffer = this.setupFrameBuffer(framebuffer);
      framebuffer.func_147614_f();
      framebuffer.func_147610_a(true);
      this.entityShadows = mc.field_71474_y.field_181151_V;
      mc.field_71474_y.field_181151_V = false;
      mc.field_71460_t.func_78479_a(partialTicks, 0);
   }

   public void stopDraw(Color color, float radius, float quality) {
      mc.field_71474_y.field_181151_V = this.entityShadows;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      mc.func_147110_a().func_147610_a(true);
      this.red = (float)color.getRed() / 255.0F;
      this.green = (float)color.getGreen() / 255.0F;
      this.blue = (float)color.getBlue() / 255.0F;
      this.alpha = (float)color.getAlpha() / 255.0F;
      this.radius = radius;
      this.quality = quality;
      mc.field_71460_t.func_175072_h();
      RenderHelper.func_74518_a();
      this.startShader();
      mc.field_71460_t.func_78478_c();
      this.drawFramebuffer(framebuffer);
      this.stopShader();
      mc.field_71460_t.func_175072_h();
      GlStateManager.func_179121_F();
      GlStateManager.func_179099_b();
   }

   public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
      if (frameBuffer != null) {
         frameBuffer.func_147608_a();
      }

      frameBuffer = new Framebuffer(mc.field_71443_c, mc.field_71440_d, true);
      return frameBuffer;
   }

   public void drawFramebuffer(Framebuffer framebuffer) {
      ScaledResolution scaledResolution = StaticStorage.scaledResolution;
      if (scaledResolution != null) {
         GL11.glBindTexture(3553, framebuffer.field_147617_g);
         GL11.glBegin(7);
         GL11.glTexCoord2d((double)0.0F, (double)1.0F);
         GL11.glVertex2d((double)0.0F, (double)0.0F);
         GL11.glTexCoord2d((double)0.0F, (double)0.0F);
         GL11.glVertex2d((double)0.0F, (double)scaledResolution.func_78328_b());
         GL11.glTexCoord2d((double)1.0F, (double)0.0F);
         GL11.glVertex2d((double)scaledResolution.func_78326_a(), (double)scaledResolution.func_78328_b());
         GL11.glTexCoord2d((double)1.0F, (double)1.0F);
         GL11.glVertex2d((double)scaledResolution.func_78326_a(), (double)0.0F);
         GL11.glEnd();
         GL20.glUseProgram(0);
      }
   }
}
