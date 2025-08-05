package net.ccbluex.liquidbounce.utils.render;

import akka.actor.Nobody;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MathUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.geom.Rectangle;
import net.ccbluex.liquidbounce.utils.render.glu.DirectTessCallback;
import net.ccbluex.liquidbounce.utils.render.glu.VertexData;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.GLUtessellator;
import org.lwjgl.util.glu.GLUtessellatorCallbackAdapter;
import org.lwjgl.util.glu.Project;

public final class RenderUtils extends MinecraftInstance {
   private static final Map glCapMap = new HashMap();
   private static Framebuffer initialFB;
   private static Framebuffer frameBuffer;
   private static Framebuffer blackBuffer;
   private static ShaderGroup mainShader = null;
   private static float lastWidth = 0.0F;
   private static float lastHeight = 0.0F;
   private static float lastStrength = 0.0F;
   private static final Minecraft mc = Minecraft.func_71410_x();
   private static final ResourceLocation blurDirectory = new ResourceLocation("crosssine/shadow.json");
   public static int deltaTime;
   private static final int[] DISPLAY_LISTS_2D = new int[4];
   private static long startTime;
   private static int animationDuration = 500;
   protected static float zLevel;
   private static final Frustum frustrum;

   public static double safeDivD(double numerator, int denominator) {
      return (double)denominator == (double)0.0F ? (double)0.0F : numerator / (double)denominator;
   }

   public static void drawAxisAlignedBB(AxisAlignedBB axisAlignedBB, Color color) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      glColor(color);
      drawFilledBox(axisAlignedBB);
      GlStateManager.func_179117_G();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static float[] worldToScreen(double x, double y, double z) {
      Minecraft mc = Minecraft.func_71410_x();
      RenderManager renderManager = mc.func_175598_ae();
      double relativeX = x - renderManager.field_78730_l;
      double relativeY = y - renderManager.field_78731_m;
      double relativeZ = z - renderManager.field_78728_n;
      FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
      FloatBuffer projection = BufferUtils.createFloatBuffer(16);
      IntBuffer viewport = BufferUtils.createIntBuffer(16);
      GL11.glGetFloat(2982, modelView);
      GL11.glGetFloat(2983, projection);
      GL11.glGetInteger(2978, viewport);
      FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
      if (Project.gluProject((float)relativeX, (float)relativeY, (float)relativeZ, modelView, projection, viewport, screenCoords)) {
         float screenX = screenCoords.get(0);
         float screenY = (float)mc.field_71440_d - screenCoords.get(1);
         float screenZ = screenCoords.get(2);
         return new float[]{screenX, screenY, screenZ};
      } else {
         return null;
      }
   }

   public static void drawClickGuiArrow(float x, float y, float size, AnimationUnit animation, int color) {
      GL11.glTranslatef(x, y, 0.0F);
      double[] interpolation = new double[1];
      setup2DRendering(() -> render(5, () -> {
            color(color);
            interpolation[0] = interpolate((double)0.0F, (double)size / (double)2.0F, animation.getOutput());
            if (animation.getOutput() >= 0.48) {
               GL11.glVertex2d((double)(size / 2.0F), interpolate((double)size / (double)2.0F, (double)0.0F, animation.getOutput()));
            }

            GL11.glVertex2d((double)0.0F, interpolation[0]);
            if (animation.getOutput() < 0.48) {
               GL11.glVertex2d((double)(size / 2.0F), interpolate((double)size / (double)2.0F, (double)0.0F, animation.getOutput()));
            }

            GL11.glVertex2d((double)size, interpolation[0]);
         }));
      GL11.glTranslatef(-x, -y, 0.0F);
   }

   public static Color getGradientOffset(Color color1, Color color2, double offset) {
      if (offset > (double)1.0F) {
         double inverse_percent = offset % (double)1.0F;
         int redPart = (int)offset;
         offset = redPart % 2 == 0 ? inverse_percent : (double)1.0F - inverse_percent;
      }

      double inverse_percent = (double)1.0F - offset;
      int redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
      int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
      int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
      return new Color(redPart, greenPart, bluePart);
   }

   public static void drawRectBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
      rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
      rectangle(x + width, y, x1 - width, y + width, borderColor);
      rectangle(x, y, x + width, y1, borderColor);
      rectangle(x1 - width, y, x1, y1, borderColor);
      rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
   }

   public static void connectPoints(float xOne, float yOne, float xTwo, float yTwo) {
      GL11.glPushMatrix();
      GL11.glEnable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(0.5F);
      GL11.glBegin(1);
      GL11.glVertex2f(xOne, yOne);
      GL11.glVertex2f(xTwo, yTwo);
      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void drawGradientRect(double left, double top, double right, double bottom, boolean sideways, int startColor, int endColor) {
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      color(startColor);
      if (sideways) {
         GL11.glVertex2d(left, top);
         GL11.glVertex2d(left, bottom);
         color(endColor);
         GL11.glVertex2d(right, bottom);
         GL11.glVertex2d(right, top);
      } else {
         GL11.glVertex2d(left, top);
         color(endColor);
         GL11.glVertex2d(left, bottom);
         GL11.glVertex2d(right, bottom);
         color(startColor);
         GL11.glVertex2d(right, top);
      }

      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glShadeModel(7424);
      GL11.glEnable(3553);
   }

   public static int darker(int hexColor, int factor) {
      float alpha = (float)(hexColor >> 24 & 255);
      float red = Math.max((float)(hexColor >> 16 & 255) - (float)(hexColor >> 16 & 255) / (100.0F / (float)factor), 0.0F);
      float green = Math.max((float)(hexColor >> 8 & 255) - (float)(hexColor >> 8 & 255) / (100.0F / (float)factor), 0.0F);
      float blue = Math.max((float)(hexColor & 255) - (float)(hexColor & 255) / (100.0F / (float)factor), 0.0F);
      return (int)((float)(((int)alpha << 24) + ((int)red << 16) + ((int)green << 8)) + blue);
   }

   public static int darker(int color, float factor) {
      int r = (int)((float)(color >> 16 & 255) * factor);
      int g = (int)((float)(color >> 8 & 255) * factor);
      int b = (int)((float)(color & 255) * factor);
      int a = color >> 24 & 255;
      return (r & 255) << 16 | (g & 255) << 8 | b & 255 | (a & 255) << 24;
   }

   public static void targetHudRect(double x, double y, double x1, double y1, double size) {
      rectangleBordered(x, y + (double)-4.0F, x1 + size, y1 + size, (double)0.5F, (new Color(60, 60, 60)).getRGB(), (new Color(10, 10, 10)).getRGB());
      rectangleBordered(x + (double)1.0F, y + (double)-3.0F, x1 + size - (double)1.0F, y1 + size - (double)1.0F, (double)1.0F, (new Color(40, 40, 40)).getRGB(), (new Color(40, 40, 40)).getRGB());
      rectangleBordered(x + (double)2.5F, y - (double)1.5F, x1 + size - (double)2.5F, y1 + size - (double)2.5F, (double)0.5F, (new Color(40, 40, 40)).getRGB(), (new Color(60, 60, 60)).getRGB());
      rectangleBordered(x + (double)2.5F, y - (double)1.5F, x1 + size - (double)2.5F, y1 + size - (double)2.5F, (double)0.5F, (new Color(22, 22, 22)).getRGB(), (new Color(255, 255, 255, 0)).getRGB());
   }

   public static void targetHudRect1(double x, double y, double x1, double y1, double size) {
      rectangleBordered(x + 4.35, y + (double)0.5F, x1 + size - (double)84.5F, y1 + size - 4.35, (double)0.5F, (new Color(48, 48, 48)).getRGB(), (new Color(10, 10, 10)).getRGB());
      rectangleBordered(x + (double)5.0F, y + (double)1.0F, x1 + size - (double)85.0F, y1 + size - (double)5.0F, (double)0.5F, (new Color(17, 17, 17)).getRGB(), (new Color(255, 255, 255, 0)).getRGB());
   }

   public static int reAlpha(int color, float alpha) {
      Color c = new Color(color);
      float r = 0.003921569F * (float)c.getRed();
      float g = 0.003921569F * (float)c.getGreen();
      float b = 0.003921569F * (float)c.getBlue();
      return (new Color(r, g, b, alpha)).getRGB();
   }

   public static void drawRoundedRect(float x, float y, float width, float height, float edgeRadius, int color, float borderWidth, int borderColor) {
      if (color == 16777215) {
         color = -65794;
      }

      if (borderColor == 16777215) {
         borderColor = -65794;
      }

      if (edgeRadius < 0.0F) {
         edgeRadius = 0.0F;
      }

      if (edgeRadius > width / 2.0F) {
         edgeRadius = width / 2.0F;
      }

      if (edgeRadius > height / 2.0F) {
         edgeRadius = height / 2.0F;
      }

      drawRDRect(x + edgeRadius, y + edgeRadius, width - edgeRadius * 2.0F, height - edgeRadius * 2.0F, color);
      drawRDRect(x + edgeRadius, y, width - edgeRadius * 2.0F, edgeRadius, color);
      drawRDRect(x + edgeRadius, y + height - edgeRadius, width - edgeRadius * 2.0F, edgeRadius, color);
      drawRDRect(x, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0F, color);
      drawRDRect(x + width - edgeRadius, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0F, color);
      enableRender2D();
      color(color);
      GL11.glBegin(6);
      float centerX = x + edgeRadius;
      float centerY = y + edgeRadius;
      GL11.glVertex2d((double)centerX, (double)centerY);
      int vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

      for(int i = 0; i < vertices + 1; ++i) {
         double angleRadians = (Math.PI * 2D) * (double)(i + 180) / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glEnd();
      GL11.glBegin(6);
      centerX = x + width - edgeRadius;
      centerY = y + edgeRadius;
      GL11.glVertex2d((double)centerX, (double)centerY);
      vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

      for(int i = 0; i < vertices + 1; ++i) {
         double angleRadians = (Math.PI * 2D) * (double)(i + 90) / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glEnd();
      GL11.glBegin(6);
      centerX = x + edgeRadius;
      centerY = y + height - edgeRadius;
      GL11.glVertex2d((double)centerX, (double)centerY);
      vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

      for(int i = 0; i < vertices + 1; ++i) {
         double angleRadians = (Math.PI * 2D) * (double)(i + 270) / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glEnd();
      GL11.glBegin(6);
      centerX = x + width - edgeRadius;
      centerY = y + height - edgeRadius;
      GL11.glVertex2d((double)centerX, (double)centerY);
      vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

      for(int i = 0; i < vertices + 1; ++i) {
         double angleRadians = (Math.PI * 2D) * (double)i / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glEnd();
      color(borderColor);
      GL11.glLineWidth(borderWidth);
      GL11.glBegin(3);
      centerX = x + edgeRadius;
      centerY = y + edgeRadius;

      int i;
      for(vertices = i = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F); i >= 0; --i) {
         double angleRadians = (Math.PI * 2D) * (double)(i + 180) / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glVertex2d((double)(x + edgeRadius), (double)y);
      GL11.glVertex2d((double)(x + width - edgeRadius), (double)y);
      centerX = x + width - edgeRadius;
      centerY = y + edgeRadius;

      for(int var36 = vertices; var36 >= 0; --var36) {
         double angleRadians = (Math.PI * 2D) * (double)(var36 + 90) / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glVertex2d((double)(x + width), (double)(y + edgeRadius));
      GL11.glVertex2d((double)(x + width), (double)(y + height - edgeRadius));
      centerX = x + width - edgeRadius;
      centerY = y + height - edgeRadius;

      for(int var37 = vertices; var37 >= 0; --var37) {
         double angleRadians = (Math.PI * 2D) * (double)var37 / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glVertex2d((double)(x + width - edgeRadius), (double)(y + height));
      GL11.glVertex2d((double)(x + edgeRadius), (double)(y + height));
      centerX = x + edgeRadius;
      centerY = y + height - edgeRadius;

      for(int var38 = vertices; var38 >= 0; --var38) {
         double angleRadians = (Math.PI * 2D) * (double)(var38 + 270) / (double)(vertices * 4);
         GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
      }

      GL11.glVertex2d((double)x, (double)(y + height - edgeRadius));
      GL11.glVertex2d((double)x, (double)(y + edgeRadius));
      GL11.glEnd();
      disableRender2D();
   }

   public static void drawRDRect(float left, float top, float width, float height, int color) {
      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f4 = (float)(color >> 16 & 255) / 255.0F;
      float f5 = (float)(color >> 8 & 255) / 255.0F;
      float f6 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(f4, f5, f6, f3);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)left, (double)(top + height), (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)(left + width), (double)(top + height), (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)(left + width), (double)top, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)top, (double)0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void enableRender2D() {
      GL11.glEnable(3042);
      GL11.glDisable(2884);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(1.0F);
   }

   public static void disableRender2D() {
      GL11.glDisable(3042);
      GL11.glEnable(2884);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179098_w();
   }

   public static void prepareGL() {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
   }

   public static void releaseGL() {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
   }

   public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      worldrenderer.func_181662_b((double)x, (double)(y + height), (double)zLevel).func_181673_a((double)((float)textureX * f), (double)((float)(textureY + height) * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)(x + width), (double)(y + height), (double)zLevel).func_181673_a((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)(x + width), (double)y, (double)zLevel).func_181673_a((double)((float)(textureX + width) * f), (double)((float)textureY * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)x, (double)y, (double)zLevel).func_181673_a((double)((float)textureX * f), (double)((float)textureY * f1)).func_181675_d();
      tessellator.func_78381_a();
   }

   public static double getAnimationStateSmooth(double target, double current, double speed) {
      boolean larger = target > current;
      if (speed < (double)0.0F) {
         speed = (double)0.0F;
      } else if (speed > (double)1.0F) {
         speed = (double)1.0F;
      }

      if (target == current) {
         return target;
      } else {
         double dif = Math.max(target, current) - Math.min(target, current);
         double factor = Math.max(dif * speed, (double)1.0F);
         if (factor < 0.1) {
            factor = 0.1;
         }

         if (larger) {
            if (current + factor > target) {
               current = target;
            } else {
               current += factor;
            }
         } else if (current - factor < target) {
            current = target;
         } else {
            current -= factor;
         }

         return current;
      }
   }

   public static void prepareScissorBox(float x, float y, float x2, float y2) {
      ScaledResolution scale = new ScaledResolution(mc);
      int factor = scale.func_78325_e();
      GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.func_78328_b() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
   }

   public static void drawOutlinedRect(float x, float y, float width, float height, float lineSize, int lineColor) {
      drawRect(x, y, width, y + lineSize, lineColor);
      drawRect(x, height - lineSize, width, height, lineColor);
      drawRect(x, y + lineSize, x + lineSize, height - lineSize, lineColor);
      drawRect(width - lineSize, y + lineSize, width, height - lineSize, lineColor);
   }

   public static void drawCircleRect(float x, float y, float x1, float y1, float radius, int color) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      glColor(color);
      quickRenderCircle((double)(x1 - radius), (double)(y1 - radius), (double)0.0F, (double)90.0F, (double)radius, (double)radius);
      quickRenderCircle((double)(x + radius), (double)(y1 - radius), (double)90.0F, (double)180.0F, (double)radius, (double)radius);
      quickRenderCircle((double)(x + radius), (double)(y + radius), (double)180.0F, (double)270.0F, (double)radius, (double)radius);
      quickRenderCircle((double)(x1 - radius), (double)(y + radius), (double)270.0F, (double)360.0F, (double)radius, (double)radius);
      quickDrawRect(x + radius, y + radius, x1 - radius, y1 - radius);
      quickDrawRect(x, y + radius, x + radius, y1 - radius);
      quickDrawRect(x1 - radius, y + radius, x1, y1 - radius);
      quickDrawRect(x + radius, y, x1 - radius, y + radius);
      quickDrawRect(x + radius, y1 - radius, x1 - radius, y1);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static int SkyRainbow(int var2, float st, float bright) {
      double v1 = Math.ceil((double)(System.currentTimeMillis() + (long)(var2 * 109))) / (double)5.0F;
      double var5;
      return Color.getHSBColor((double)((float)((var5 = v1 % (double)360.0F) / (double)360.0F)) < (double)0.5F ? -((float)(var5 / (double)360.0F)) : (float)(var5 / (double)360.0F), st, bright).getRGB();
   }

   public static Color skyRainbow(int index, float st, float bright, float speed) {
      double v1 = Math.ceil((double)(System.currentTimeMillis() + (long)((float)(index * 109) * speed))) / (double)5.0F;
      double var6;
      return Color.getHSBColor((double)((float)((var6 = v1 % (double)360.0F) / (double)360.0F)) < (double)0.5F ? -((float)(var6 / (double)360.0F)) : (float)(var6 / (double)360.0F), st, bright);
   }

   public static void drawCircle(float x, float y, float radius, int color) {
      glColor(color);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glLineWidth(1.0F);
      GL11.glBegin(9);

      for(int i = 0; i <= 360; ++i) {
         GL11.glVertex2d((double)x + Math.sin((double)i * Math.PI / (double)180.0F) * (double)radius, (double)y + Math.cos((double)i * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawCheck(double x, double y, int lineWidth, int color) {
      start2D();
      GL11.glPushMatrix();
      GL11.glLineWidth((float)lineWidth);
      setColor(new Color(color));
      GL11.glBegin(3);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x + (double)2.0F, y + (double)3.0F);
      GL11.glVertex2d(x + (double)6.0F, y - (double)2.0F);
      GL11.glEnd();
      GL11.glPopMatrix();
      stop2D();
   }

   public static void setColor(Color color) {
      float alpha = (float)(color.getRGB() >> 24 & 255) / 255.0F;
      float red = (float)(color.getRGB() >> 16 & 255) / 255.0F;
      float green = (float)(color.getRGB() >> 8 & 255) / 255.0F;
      float blue = (float)(color.getRGB() & 255) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void start2D() {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
   }

   public static void stop2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderCircle(double x, double y, double radius, int color) {
      renderCircle(x, y, (double)0.0F, (double)360.0F, radius - (double)1.0F, color);
   }

   public static void renderCircle(double x, double y, double start, double end, double radius, int color) {
      renderCircle(x, y, start, end, radius, radius, color);
   }

   public static void renderCircle(double x, double y, double start, double end, double w, double h, int color) {
      GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      glColor(color);
      quickRenderCircle(x, y, start, end, w, h);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void quickRenderCircle(double x, double y, double start, double end, double w, double h) {
      if (start > end) {
         double temp = end;
         end = start;
         start = temp;
      }

      GL11.glBegin(6);
      GL11.glVertex2d(x, y);

      for(double i = end; i >= start; i -= (double)4.0F) {
         double ldx = Math.cos(i * Math.PI / (double)180.0F) * w;
         double ldy = Math.sin(i * Math.PI / (double)180.0F) * h;
         GL11.glVertex2d(x + ldx, y + ldy);
      }

      GL11.glVertex2d(x, y);
      GL11.glEnd();
   }

   private static void quickPolygonCircle(float x, float y, float xRadius, float yRadius, int start, int end) {
      for(int i = end; i >= start; i -= 4) {
         GL11.glVertex2d((double)x + Math.sin((double)i * Math.PI / (double)180.0F) * (double)xRadius, (double)y + Math.cos((double)i * Math.PI / (double)180.0F) * (double)yRadius);
      }

   }

   public static int getRainbowOpaque(int seconds, float saturation, float brightness, int index) {
      float hue = (float)((System.currentTimeMillis() + (long)index) % (long)(seconds * 1000)) / (float)(seconds * 1000);
      int color = Color.HSBtoRGB(hue, saturation, brightness);
      return color;
   }

   public static float smoothAnimation(float ani, float finalState, float speed, float scale) {
      return AnimationUtil.getAnimationState(ani, finalState, Math.max(10.0F, Math.abs(ani - finalState) * speed) * scale);
   }

   public static boolean isHovering(int mouseX, int mouseY, float xLeft, float yUp, float xRight, float yBottom) {
      return (float)mouseX > xLeft && (float)mouseX < xRight && (float)mouseY > yUp && (float)mouseY < yBottom;
   }

   public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius, int color) {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      boolean hasCull = GL11.glIsEnabled(2884);
      GL11.glDisable(2884);
      glColor(color);
      drawRoundedCornerRect(x, y, x1, y1, radius);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      setGlState(2884, hasCull);
   }

   public static void fastShadowRoundedRect(float x, float y, float x2, float y2, float rad, float width, float r, float g, float b, float al) {
      Stencil.write(true);
      drawRoundedRect(x, y, x2, y2, rad, (new Color(r, g, b, al)).getRGB());
      Stencil.erase(false);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glColor4f(r, g, b, al);
      GL11.glBegin(8);
      GL11.glVertex2f(x + width / 2.0F, y + width / 2.0F);
      GL11.glColor4f(r, g, b, 0.0F);
      GL11.glVertex2f(x - width, y - width);
      GL11.glColor4f(r, g, b, al);
      GL11.glVertex2f(x2 - width / 2.0F, y + width / 2.0F);
      GL11.glColor4f(r, g, b, 0.0F);
      GL11.glVertex2f(x2 + width, y - width);
      GL11.glColor4f(r, g, b, al);
      GL11.glVertex2f(x2 - width / 2.0F, y2 - width / 2.0F);
      GL11.glColor4f(r, g, b, 0.0F);
      GL11.glVertex2f(x2 + width, y2 + width);
      GL11.glColor4f(r, g, b, al);
      GL11.glVertex2f(x + width / 2.0F, y2 - width / 2.0F);
      GL11.glColor4f(r, g, b, 0.0F);
      GL11.glVertex2f(x - width, y2 + width);
      GL11.glColor4f(r, g, b, al);
      GL11.glVertex2f(x + width / 2.0F, y + width / 2.0F);
      GL11.glColor4f(r, g, b, 0.0F);
      GL11.glVertex2f(x - width, y - width);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      Stencil.dispose();
   }

   public static void fastShadowRoundedRect(float x, float y, float x2, float y2, float rad, float width, Color color) {
      fastShadowRoundedRect(x, y, x2, y2, rad, width, (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
   }

   public static void initBlur(ScaledResolution sc, float strength) throws IOException {
      int w = sc.func_78326_a();
      int h = sc.func_78328_b();
      int f = sc.func_78325_e();
      if (lastWidth != (float)w || lastHeight != (float)h || initialFB == null || frameBuffer == null || mainShader == null) {
         initialFB = new Framebuffer(w * f, h * f, true);
         initialFB.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
         initialFB.func_147607_a(9729);
         mainShader = new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), initialFB, blurDirectory);
         mainShader.func_148026_a(w * f, h * f);
         frameBuffer = mainShader.field_148035_a;
         blackBuffer = mainShader.func_177066_a("braindead");
      }

      lastWidth = (float)w;
      lastHeight = (float)h;
      if (strength != lastStrength) {
         lastStrength = strength;

         for(int i = 0; i < 2; ++i) {
            ((Shader)mainShader.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
         }
      }

   }

   public static void processShadow(boolean begin, float strength) throws IOException {
      if (OpenGlHelper.func_148822_b()) {
         ScaledResolution sc = new ScaledResolution(mc);
         initBlur(sc, strength);
         if (begin) {
            mc.func_147110_a().func_147609_e();
            initialFB.func_147614_f();
            blackBuffer.func_147614_f();
            initialFB.func_147610_a(true);
         } else {
            frameBuffer.func_147610_a(true);
            mainShader.func_148018_a(mc.field_71428_T.field_74281_c);
            mc.func_147110_a().func_147610_a(true);
            float f = (float)sc.func_78326_a();
            float f1 = (float)sc.func_78328_b();
            float f2 = (float)blackBuffer.field_147621_c / (float)blackBuffer.field_147622_a;
            float f3 = (float)blackBuffer.field_147618_d / (float)blackBuffer.field_147620_b;
            GlStateManager.func_179094_E();
            GlStateManager.func_179140_f();
            GlStateManager.func_179118_c();
            GlStateManager.func_179098_w();
            GlStateManager.func_179097_i();
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179135_a(true, true, true, true);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b(770, 771);
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            blackBuffer.func_147612_c();
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            Tessellator tessellator = Tessellator.func_178181_a();
            WorldRenderer worldrenderer = tessellator.func_178180_c();
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            worldrenderer.func_181662_b((double)0.0F, (double)f1, (double)0.0F).func_181673_a((double)0.0F, (double)0.0F).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)f, (double)f1, (double)0.0F).func_181673_a((double)f2, (double)0.0F).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)f, (double)0.0F, (double)0.0F).func_181673_a((double)f2, (double)f3).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)0.0F, (double)0.0F, (double)0.0F).func_181673_a((double)0.0F, (double)f3).func_181669_b(255, 255, 255, 255).func_181675_d();
            tessellator.func_78381_a();
            blackBuffer.func_147606_d();
            GlStateManager.func_179084_k();
            GlStateManager.func_179141_d();
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179098_w();
            GlStateManager.func_179121_F();
            GlStateManager.func_179117_G();
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b(770, 771);
         }

      }
   }

   public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179103_j(7425);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldrenderer.func_181662_b((double)right, (double)top, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)top, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)bottom, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)bottom, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }

   public static void drawGradientRoundedRect(int left, int top, int right, int bottom, int radius, int startColor, int endColor) {
      Stencil.write(false);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      fastRoundedRect((float)left, (float)top, (float)right, (float)bottom, (float)radius);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      Stencil.erase(true);
      drawGradientRect(left, top, right, bottom, startColor, endColor);
      Stencil.dispose();
   }

   public static void drawGradientRoundedRect(float left, float top, float right, float bottom, int radius, int startColor, int endColor) {
      Stencil.write(false);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      fastRoundedRect(left, top, right, bottom, (float)radius);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      Stencil.erase(true);
      drawGradientRect(left, top, right, bottom, startColor, endColor);
      Stencil.dispose();
   }

   public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
      float f = (float)(col1 >> 24 & 255) / 255.0F;
      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
      float f3 = (float)(col1 & 255) / 255.0F;
      float f4 = (float)(col2 >> 24 & 255) / 255.0F;
      float f5 = (float)(col2 >> 16 & 255) / 255.0F;
      float f6 = (float)(col2 >> 8 & 255) / 255.0F;
      float f7 = (float)(col2 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glVertex2d(left, top);
      GL11.glVertex2d(left, bottom);
      GL11.glColor4f(f5, f6, f7, f4);
      GL11.glVertex2d(right, bottom);
      GL11.glVertex2d(right, top);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void drawAnimatedGradient(double left, double top, double right, double bottom, int col1, int col2) {
      long currentTime = System.currentTimeMillis();
      if (startTime == 0L) {
         startTime = currentTime;
      }

      long elapsedTime = currentTime - startTime;
      float progress = (float)(elapsedTime % (long)animationDuration) / (float)animationDuration;
      int color1;
      int color2;
      if (elapsedTime / (long)animationDuration % 2L == 0L) {
         color1 = interpolateColors(col1, col2, progress);
         color2 = interpolateColors(col2, col1, progress);
      } else {
         color1 = interpolateColors(col2, col1, progress);
         color2 = interpolateColors(col1, col2, progress);
      }

      drawGradientSideways(left, top, right, bottom, color1, color2);
      if (elapsedTime >= (long)(2 * animationDuration)) {
         startTime = currentTime;
      }

   }

   public static int interpolateColors(int color1, int color2, float progress) {
      int alpha = (int)(((double)1.0F - (double)progress) * (double)(color1 >>> 24) + (double)(progress * (float)(color2 >>> 24)));
      int red = (int)(((double)1.0F - (double)progress) * (double)(color1 >> 16 & 255) + (double)(progress * (float)(color2 >> 16 & 255)));
      int green = (int)(((double)1.0F - (double)progress) * (double)(color1 >> 8 & 255) + (double)(progress * (float)(color2 >> 8 & 255)));
      int blue = (int)(((double)1.0F - (double)progress) * (double)(color1 & 255) + (double)(progress * (float)(color2 & 255)));
      return alpha << 24 | red << 16 | green << 8 | blue;
   }

   public static void drawShadow(float x, float y, float width, float height) {
      drawTexturedRect(x - 9.0F, y - 9.0F, 9.0F, 9.0F, "paneltopleft");
      drawTexturedRect(x - 9.0F, y + height, 9.0F, 9.0F, "panelbottomleft");
      drawTexturedRect(x + width, y + height, 9.0F, 9.0F, "panelbottomright");
      drawTexturedRect(x + width, y - 9.0F, 9.0F, 9.0F, "paneltopright");
      drawTexturedRect(x - 9.0F, y, 9.0F, height, "panelleft");
      drawTexturedRect(x + width, y, 9.0F, height, "panelright");
      drawTexturedRect(x, y - 9.0F, width, 9.0F, "paneltop");
      drawTexturedRect(x, y + height, width, 9.0F, "panelbottom");
   }

   public static void drawTexturedRect(float x, float y, float width, float height, String image) {
      GL11.glPushMatrix();
      boolean enableBlend = GL11.glIsEnabled(3042);
      boolean disableAlpha = !GL11.glIsEnabled(3008);
      if (!enableBlend) {
         GL11.glEnable(3042);
      }

      if (!disableAlpha) {
         GL11.glDisable(3008);
      }

      mc.func_110434_K().func_110577_a(new ResourceLocation("crosssine/ui/shadow/" + image + ".png"));
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
      if (!enableBlend) {
         GL11.glDisable(3042);
      }

      if (!disableAlpha) {
         GL11.glEnable(3008);
      }

      GL11.glPopMatrix();
   }

   public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
      float f = 1.0F / textureWidth;
      float f1 = 1.0F / textureHeight;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      worldrenderer.func_181662_b((double)x, (double)(y + height), (double)0.0F).func_181673_a((double)(u * f), (double)((v + height) * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)(x + width), (double)(y + height), (double)0.0F).func_181673_a((double)((u + width) * f), (double)((v + height) * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)(x + width), (double)y, (double)0.0F).func_181673_a((double)((u + width) * f), (double)(v * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)x, (double)y, (double)0.0F).func_181673_a((double)(u * f), (double)(v * f1)).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawExhiEnchants(ItemStack stack, float x, float y) {
      RenderHelper.func_74518_a();
      GlStateManager.func_179097_i();
      GlStateManager.func_179084_k();
      GlStateManager.func_179117_G();
      int darkBorder = -16777216;
      if (stack.func_77973_b() instanceof ItemArmor) {
         int prot = EnchantmentHelper.func_77506_a(Enchantment.field_180310_c.field_77352_x, stack);
         int unb = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
         int thorn = EnchantmentHelper.func_77506_a(Enchantment.field_92091_k.field_77352_x, stack);
         if (prot > 0) {
            drawExhiOutlined(prot + "", drawExhiOutlined("P", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(prot), getMainColor(prot), true);
            y += 4.0F;
         }

         if (unb > 0) {
            drawExhiOutlined(unb + "", drawExhiOutlined("U", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(unb), getMainColor(unb), true);
            y += 4.0F;
         }

         if (thorn > 0) {
            drawExhiOutlined(thorn + "", drawExhiOutlined("T", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(thorn), getMainColor(thorn), true);
            y += 4.0F;
         }
      }

      if (stack.func_77973_b() instanceof ItemBow) {
         int power = EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, stack);
         int punch = EnchantmentHelper.func_77506_a(Enchantment.field_77344_u.field_77352_x, stack);
         int flame = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, stack);
         int unb = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
         if (power > 0) {
            drawExhiOutlined(power + "", drawExhiOutlined("Pow", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(power), getMainColor(power), true);
            y += 4.0F;
         }

         if (punch > 0) {
            drawExhiOutlined(punch + "", drawExhiOutlined("Pun", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(punch), getMainColor(punch), true);
            y += 4.0F;
         }

         if (flame > 0) {
            drawExhiOutlined(flame + "", drawExhiOutlined("F", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(flame), getMainColor(flame), true);
            y += 4.0F;
         }

         if (unb > 0) {
            drawExhiOutlined(unb + "", drawExhiOutlined("U", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(unb), getMainColor(unb), true);
            y += 4.0F;
         }
      }

      if (stack.func_77973_b() instanceof ItemSword) {
         int sharp = EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, stack);
         int kb = EnchantmentHelper.func_77506_a(Enchantment.field_180313_o.field_77352_x, stack);
         int fire = EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack);
         int unb = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
         if (sharp > 0) {
            drawExhiOutlined(sharp + "", drawExhiOutlined("S", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(sharp), getMainColor(sharp), true);
            y += 4.0F;
         }

         if (kb > 0) {
            drawExhiOutlined(kb + "", drawExhiOutlined("K", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(kb), getMainColor(kb), true);
            y += 4.0F;
         }

         if (fire > 0) {
            drawExhiOutlined(fire + "", drawExhiOutlined("F", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(fire), getMainColor(fire), true);
            y += 4.0F;
         }

         if (unb > 0) {
            drawExhiOutlined(unb + "", drawExhiOutlined("U", x, y, 0.35F, -16777216, -1, true), y, 0.35F, getBorderColor(unb), getMainColor(unb), true);
            y += 4.0F;
         }
      }

      GlStateManager.func_179126_j();
      RenderHelper.func_74520_c();
   }

   private static float drawExhiOutlined(String text, float x, float y, float borderWidth, int borderColor, int mainColor, boolean drawText) {
      Fonts.fontTahomaSmall.drawString(text, x, y - borderWidth, borderColor);
      Fonts.fontTahomaSmall.drawString(text, x, y + borderWidth, borderColor);
      Fonts.fontTahomaSmall.drawString(text, x - borderWidth, y, borderColor);
      Fonts.fontTahomaSmall.drawString(text, x + borderWidth, y, borderColor);
      if (drawText) {
         Fonts.fontTahomaSmall.drawString(text, x, y, mainColor);
      }

      return x + Fonts.fontTahomaSmall.getWidth(text) - 2.0F;
   }

   public static void drawFilledCircle(int xx, int yy, float radius, int col) {
      float f = (float)(col >> 24 & 255) / 255.0F;
      float f1 = (float)(col >> 16 & 255) / 255.0F;
      float f2 = (float)(col >> 8 & 255) / 255.0F;
      float f3 = (float)(col & 255) / 255.0F;
      int sections = 50;
      double dAngle = (Math.PI * 2D) / (double)sections;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glBegin(6);

      for(int i = 0; i < sections; ++i) {
         float x = (float)((double)radius * Math.sin((double)i * dAngle));
         float y = (float)((double)radius * Math.cos((double)i * dAngle));
         GL11.glColor4f(f1, f2, f3, f);
         GL11.glVertex2f((float)xx + x, (float)yy + y);
      }

      GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179103_j(7425);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldrenderer.func_181662_b((double)right, (double)top, (double)0.0F).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)top, (double)0.0F).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)bottom, (double)0.0F).func_181666_a(f5, f6, f7, f4).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)bottom, (double)0.0F).func_181666_a(f5, f6, f7, f4).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }

   private static int getMainColor(int level) {
      return level == 4 ? -5636096 : -1;
   }

   private static int getBorderColor(int level) {
      if (level == 2) {
         return 1884684117;
      } else if (level == 3) {
         return 1879091882;
      } else if (level == 4) {
         return 1890189312;
      } else {
         return level >= 5 ? 1895803392 : 1895825407;
      }
   }

   public static void customRounded(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float rTL, float rTR, float rBR, float rBL, int color) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      if (paramXStart > paramXEnd) {
         float z = paramXStart;
         paramXStart = paramXEnd;
         paramXEnd = z;
      }

      if (paramYStart > paramYEnd) {
         float z = paramYStart;
         paramYStart = paramYEnd;
         paramYEnd = z;
      }

      double xTL = (double)(paramXStart + rTL);
      double yTL = (double)(paramYStart + rTL);
      double xTR = (double)(paramXEnd - rTR);
      double yTR = (double)(paramYStart + rTR);
      double xBR = (double)(paramXEnd - rBR);
      double yBR = (double)(paramYEnd - rBR);
      double xBL = (double)(paramXStart + rBL);
      double yBL = (double)(paramYEnd - rBL);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(9);
      double degree = (Math.PI / 180D);

      for(double i = (double)0.0F; i <= (double)90.0F; i += (double)0.25F) {
         GL11.glVertex2d(xBR + Math.sin(i * degree) * (double)rBR, yBR + Math.cos(i * degree) * (double)rBR);
      }

      for(double i = (double)90.0F; i <= (double)180.0F; i += (double)0.25F) {
         GL11.glVertex2d(xTR + Math.sin(i * degree) * (double)rTR, yTR + Math.cos(i * degree) * (double)rTR);
      }

      for(double i = (double)180.0F; i <= (double)270.0F; i += (double)0.25F) {
         GL11.glVertex2d(xTL + Math.sin(i * degree) * (double)rTL, yTL + Math.cos(i * degree) * (double)rTL);
      }

      for(double i = (double)270.0F; i <= (double)360.0F; i += (double)0.25F) {
         GL11.glVertex2d(xBL + Math.sin(i * degree) * (double)rBL, yBL + Math.cos(i * degree) * (double)rBL);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius) {
      GL11.glBegin(9);
      float xRadius = (float)Math.min((double)(x1 - x) * (double)0.5F, (double)radius);
      float yRadius = (float)Math.min((double)(y1 - y) * (double)0.5F, (double)radius);
      quickPolygonCircle(x + xRadius, y + yRadius, xRadius, yRadius, 180, 270);
      quickPolygonCircle(x1 - xRadius, y + yRadius, xRadius, yRadius, 90, 180);
      quickPolygonCircle(x1 - xRadius, y1 - yRadius, xRadius, yRadius, 0, 90);
      quickPolygonCircle(x + xRadius, y1 - yRadius, xRadius, yRadius, 270, 360);
      GL11.glEnd();
   }

   public static void renderItemIcon(int x, int y, ItemStack itemStack) {
      if (itemStack != null && itemStack.func_77973_b() != null) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179091_B();
         GlStateManager.func_179147_l();
         GlStateManager.func_179120_a(770, 771, 1, 0);
         RenderHelper.func_74520_c();
         mc.func_175599_af().func_175042_a(itemStack, x, y);
         GlStateManager.func_179101_C();
         GlStateManager.func_179084_k();
         RenderHelper.func_74518_a();
         GlStateManager.func_179121_F();
      }

   }

   public static void customRoundedinf(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float rTL, float rTR, float rBR, float rBL, int color) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      float z = 0.0F;
      if (paramXStart > paramXEnd) {
         z = paramXStart;
         paramXStart = paramXEnd;
         paramXEnd = z;
      }

      if (paramYStart > paramYEnd) {
         z = paramYStart;
         paramYStart = paramYEnd;
         paramYEnd = z;
      }

      double xTL = (double)(paramXStart + rTL);
      double yTL = (double)(paramYStart + rTL);
      double xTR = (double)(paramXEnd - rTR);
      double yTR = (double)(paramYStart + rTR);
      double xBR = (double)(paramXEnd - rBR);
      double yBR = (double)(paramYEnd - rBR);
      double xBL = (double)(paramXStart + rBL);
      double yBL = (double)(paramYEnd - rBL);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(9);
      double degree = (Math.PI / 180D);
      if (rBR <= 0.0F) {
         GL11.glVertex2d(xBR, yBR);
      } else {
         for(double i = (double)0.0F; i <= (double)90.0F; ++i) {
            GL11.glVertex2d(xBR + Math.sin(i * degree) * (double)rBR, yBR + Math.cos(i * degree) * (double)rBR);
         }
      }

      if (rTR <= 0.0F) {
         GL11.glVertex2d(xTR, yTR);
      } else {
         for(double i = (double)90.0F; i <= (double)180.0F; ++i) {
            GL11.glVertex2d(xTR + Math.sin(i * degree) * (double)rTR, yTR + Math.cos(i * degree) * (double)rTR);
         }
      }

      if (rTL <= 0.0F) {
         GL11.glVertex2d(xTL, yTL);
      } else {
         for(double i = (double)180.0F; i <= (double)270.0F; ++i) {
            GL11.glVertex2d(xTL + Math.sin(i * degree) * (double)rTL, yTL + Math.cos(i * degree) * (double)rTL);
         }
      }

      if (rBL <= 0.0F) {
         GL11.glVertex2d(xBL, yBL);
      } else {
         for(double i = (double)270.0F; i <= (double)360.0F; ++i) {
            GL11.glVertex2d(xBL + Math.sin(i * degree) * (double)rBL, yBL + Math.cos(i * degree) * (double)rBL);
         }
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void drawRoundedRect2(float x, float y, float x2, float y2, float round, int color) {
      x += (float)((double)(round / 2.0F) + (double)0.5F);
      y += (float)((double)(round / 2.0F) + (double)0.5F);
      x2 -= (float)((double)(round / 2.0F) + (double)0.5F);
      y2 -= (float)((double)(round / 2.0F) + (double)0.5F);
      drawRect((float)((int)x), (float)((int)y), (float)((int)x2), (float)((int)y2), color);
      circle(x2 - round / 2.0F, y + round / 2.0F, round, color);
      circle(x + round / 2.0F, y2 - round / 2.0F, round, color);
      circle(x + round / 2.0F, y + round / 2.0F, round, color);
      circle(x2 - round / 2.0F, y2 - round / 2.0F, round, color);
      drawRect((float)((int)(x - round / 2.0F - 0.5F)), (float)((int)(y + round / 2.0F)), (float)((int)x2), (float)((int)(y2 - round / 2.0F)), color);
      drawRect((float)((int)x), (float)((int)(y + round / 2.0F)), (float)((int)(x2 + round / 2.0F + 0.5F)), (float)((int)(y2 - round / 2.0F)), color);
      drawRect((float)((int)(x + round / 2.0F)), (float)((int)(y - round / 2.0F - 0.5F)), (float)((int)(x2 - round / 2.0F)), (float)((int)(y2 - round / 2.0F)), color);
      drawRect((float)((int)(x + round / 2.0F)), (float)((int)y), (float)((int)(x2 - round / 2.0F)), (float)((int)(y2 + round / 2.0F + 0.5F)), color);
   }

   public static void setupRender(boolean start) {
      if (start) {
         GlStateManager.func_179147_l();
         GL11.glEnable(2848);
         GlStateManager.func_179097_i();
         GlStateManager.func_179090_x();
         GlStateManager.func_179112_b(770, 771);
         GL11.glHint(3154, 4354);
      } else {
         GlStateManager.func_179084_k();
         GlStateManager.func_179098_w();
         GL11.glDisable(2848);
         GlStateManager.func_179126_j();
      }

      GlStateManager.func_179132_a(!start);
   }

   public static void drawSuperCircle(float x, float y, float radius, int color) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(red, green, blue, alpha);
      if (alpha > 0.5F) {
         GL11.glEnable(2881);
         GL11.glEnable(2848);
         GL11.glBlendFunc(770, 771);
         GL11.glBegin(3);

         for(int i = 0; i <= 180; ++i) {
            GL11.glVertex2d((double)x + Math.sin((double)i * 3.141526 / (double)180.0F) * (double)radius, (double)y + Math.cos((double)i * 3.141526 / (double)180.0F) * (double)radius);
            GL11.glVertex2d((double)x + Math.sin((double)i * 3.141526 / (double)180.0F) * (double)radius, (double)y + Math.cos((double)i * 3.141526 / (double)180.0F) * (double)radius);
         }

         GL11.glEnd();
         GL11.glDisable(2848);
         GL11.glDisable(2881);
      }

      GL11.glBegin(6);

      for(int i = 0; i <= 180; ++i) {
         GL11.glVertex2d((double)x + Math.sin((double)i * 3.141526 / (double)180.0F) * (double)radius, (double)y + Math.cos((double)i * 3.141526 / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void circle(float x, float y, float radius, int fill) {
      arc(x, y, 0.0F, 360.0F, radius, fill);
   }

   public static void circle(float x, float y, float radius, Color fill) {
      arc(x, y, 0.0F, 360.0F, radius, fill);
   }

   public static void arc(float x, float y, float start, float end, float radius, int color) {
      arcEllipse(x, y, start, end, radius, radius, color);
   }

   public static void arc(float x, float y, float start, float end, float radius, Color color) {
      arcEllipse(x, y, start, end, radius, radius, color);
   }

   public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
      GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
      if (start > end) {
         float temp = end;
         end = start;
         start = temp;
      }

      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(red, green, blue, alpha);
      if (alpha > 0.5F) {
         GL11.glEnable(2881);
         GL11.glEnable(2848);
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);

         for(float i = end; i >= start; i -= 4.0F) {
            float ldx = (float)Math.cos((double)i * Math.PI / (double)180.0F) * w * 1.001F;
            float ldy = (float)Math.sin((double)i * Math.PI / (double)180.0F) * h * 1.001F;
            GL11.glVertex2f(x + ldx, y + ldy);
         }

         GL11.glEnd();
         GL11.glDisable(2848);
         GL11.glDisable(2881);
      }

      GL11.glBegin(6);

      for(float i = end; i >= start; i -= 4.0F) {
         float ldx = (float)Math.cos((double)i * Math.PI / (double)180.0F) * w;
         float ldy = (float)Math.sin((double)i * Math.PI / (double)180.0F) * h;
         GL11.glVertex2f(x + ldx, y + ldy);
      }

      GL11.glEnd();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
      GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
      float temp = 0.0F;
      if (start > end) {
         temp = end;
         end = start;
         start = temp;
      }

      Tessellator var9 = Tessellator.func_178181_a();
      var9.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      if ((float)color.getAlpha() > 0.5F) {
         GL11.glEnable(2848);
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);

         for(float i = end; i >= start; i -= 4.0F) {
            float ldx = (float)Math.cos((double)i * Math.PI / (double)180.0F) * w * 1.001F;
            float ldy = (float)Math.sin((double)i * Math.PI / (double)180.0F) * h * 1.001F;
            GL11.glVertex2f(x + ldx, y + ldy);
         }

         GL11.glEnd();
         GL11.glDisable(2848);
      }

      GL11.glBegin(6);

      for(float i = end; i >= start; i -= 4.0F) {
         float ldx = (float)Math.cos((double)i * Math.PI / (double)180.0F) * w;
         float ldy = (float)Math.sin((double)i * Math.PI / (double)180.0F) * h;
         GL11.glVertex2f(x + ldx, y + ldy);
      }

      GL11.glEnd();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius) {
      float z = 0.0F;
      if (paramXStart > paramXEnd) {
         z = paramXStart;
         paramXStart = paramXEnd;
         paramXEnd = z;
      }

      if (paramYStart > paramYEnd) {
         z = paramYStart;
         paramYStart = paramYEnd;
         paramYEnd = z;
      }

      double x1 = (double)(paramXStart + radius);
      double y1 = (double)(paramYStart + radius);
      double x2 = (double)(paramXEnd - radius);
      double y2 = (double)(paramYEnd - radius);
      GL11.glEnable(2848);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(9);
      double degree = (Math.PI / 180D);

      for(double i = (double)0.0F; i <= (double)90.0F; ++i) {
         GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
      }

      for(double i = (double)90.0F; i <= (double)180.0F; ++i) {
         GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
      }

      for(double i = (double)180.0F; i <= (double)270.0F; ++i) {
         GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
      }

      for(double i = (double)270.0F; i <= (double)360.0F; ++i) {
         GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
      }

      GL11.glEnd();
      GL11.glDisable(2848);
   }

   public static double interpolate(double current, double old, double scale) {
      return old + (current - old) * scale;
   }

   public static boolean isInViewFrustrum(Entity entity) {
      return isInViewFrustrum(entity.func_174813_aQ()) || entity.field_70158_ak;
   }

   private static boolean isInViewFrustrum(AxisAlignedBB bb) {
      Entity current = mc.func_175606_aa();
      frustrum.func_78547_a(current.field_70165_t, current.field_70163_u, current.field_70161_v);
      return frustrum.func_78546_a(bb);
   }

   public static void drawFilledCircleNoGL(int x, int y, double r, int c, int quality) {
      float f = (float)(c >> 24 & 255) / 255.0F;
      float f1 = (float)(c >> 16 & 255) / 255.0F;
      float f2 = (float)(c >> 8 & 255) / 255.0F;
      float f3 = (float)(c & 255) / 255.0F;
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(6);

      for(int i = 0; i <= 360 / quality; ++i) {
         double x2 = Math.sin((double)(i * quality) * Math.PI / (double)180.0F) * r;
         double y2 = Math.cos((double)(i * quality) * Math.PI / (double)180.0F) * r;
         GL11.glVertex2d((double)x + x2, (double)y + y2);
      }

      GL11.glEnd();
   }

   public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
      if (framebuffer != null && framebuffer.field_147621_c == mc.field_71443_c && framebuffer.field_147618_d == mc.field_71440_d) {
         return framebuffer;
      } else {
         if (framebuffer != null) {
            framebuffer.func_147608_a();
         }

         return new Framebuffer(mc.field_71443_c, mc.field_71440_d, true);
      }
   }

   public static void bindTexture(int texture) {
      GL11.glBindTexture(3553, texture);
   }

   public static void drawEntityBox(Entity entity, Color color, boolean outline) {
      RenderManager renderManager = mc.func_175598_ae();
      Timer timer = mc.field_71428_T;
      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
      AxisAlignedBB entityBox = entity.func_174813_aQ();
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.field_72340_a - entity.field_70165_t + x - 0.05, entityBox.field_72338_b - entity.field_70163_u + y, entityBox.field_72339_c - entity.field_70161_v + z - 0.05, entityBox.field_72336_d - entity.field_70165_t + x + 0.05, entityBox.field_72337_e - entity.field_70163_u + y + 0.15, entityBox.field_72334_f - entity.field_70161_v + z + 0.05);
      if (outline) {
         GL11.glLineWidth(1.0F);
         enableGlCap(2848);
         glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
         drawSelectionBoundingBox(axisAlignedBB);
      }

      glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 35);
      drawFilledBox(axisAlignedBB);
      GlStateManager.func_179117_G();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void drawFilledCircle(double x, double y, double r, int c, int id) {
      float f = (float)(c >> 24 & 255) / 255.0F;
      float f1 = (float)(c >> 16 & 255) / 255.0F;
      float f2 = (float)(c >> 8 & 255) / 255.0F;
      float f3 = (float)(c & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(9);
      if (id == 1) {
         GL11.glVertex2d(x, y);

         for(int i = 0; i <= 90; ++i) {
            double x2 = Math.sin((double)i * 3.141526 / (double)180.0F) * r;
            double y2 = Math.cos((double)i * 3.141526 / (double)180.0F) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if (id == 2) {
         GL11.glVertex2d(x, y);

         for(int i = 90; i <= 180; ++i) {
            double x2 = Math.sin((double)i * 3.141526 / (double)180.0F) * r;
            double y2 = Math.cos((double)i * 3.141526 / (double)180.0F) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if (id == 3) {
         GL11.glVertex2d(x, y);

         for(int i = 270; i <= 360; ++i) {
            double x2 = Math.sin((double)i * 3.141526 / (double)180.0F) * r;
            double y2 = Math.cos((double)i * 3.141526 / (double)180.0F) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if (id == 4) {
         GL11.glVertex2d(x, y);

         for(int i = 180; i <= 270; ++i) {
            double x2 = Math.sin((double)i * 3.141526 / (double)180.0F) * r;
            double y2 = Math.cos((double)i * 3.141526 / (double)180.0F) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else {
         for(int i = 0; i <= 360; ++i) {
            double x2 = Math.sin((double)i * 3.141526 / (double)180.0F) * r;
            double y2 = Math.cos((double)i * 3.141526 / (double)180.0F) * r;
            GL11.glVertex2f((float)(x - x2), (float)(y - y2));
         }
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawGradientSidewaysH(double left, double top, double right, double bottom, int col1, int col2) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      quickDrawGradientSidewaysH(left, top, right, bottom, col1, col2);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void quickDrawGradientSidewaysH(double left, double top, double right, double bottom, int col1, int col2) {
      GL11.glBegin(7);
      glColor(col1);
      GL11.glVertex2d(left, top);
      GL11.glVertex2d(left, bottom);
      glColor(col2);
      GL11.glVertex2d(right, bottom);
      GL11.glVertex2d(right, top);
      GL11.glEnd();
   }

   public static void drawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      quickDrawGradientSidewaysV(left, top, right, bottom, col1, col2);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void quickDrawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
      GL11.glBegin(7);
      glColor(col1);
      GL11.glVertex2d(right, top);
      GL11.glVertex2d(left, top);
      glColor(col2);
      GL11.glVertex2d(left, bottom);
      GL11.glVertex2d(right, bottom);
      GL11.glEnd();
   }

   public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline, boolean box, float outlineWidth, float animation) {
      RenderManager renderManager = mc.func_175598_ae();
      Timer timer = mc.field_71428_T;
      double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
      double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
      double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
      double adjustedHeight = (double)animation;
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + (double)1.0F, y + adjustedHeight, z + (double)1.0F);
      Block block = BlockUtils.getBlock(blockPos);
      if (block != null) {
         EntityPlayer player = mc.field_71439_g;
         double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
         double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
         double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
         axisAlignedBB = block.func_180646_a(mc.field_71441_e, blockPos).func_72314_b((double)0.002F, (double)0.002F, (double)0.002F).func_72317_d(-posX, -posY, -posZ);
         axisAlignedBB = new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, Math.min(axisAlignedBB.field_72337_e, axisAlignedBB.field_72338_b + adjustedHeight), axisAlignedBB.field_72334_f);
      }

      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      if (box) {
         glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
         drawFilledBox(axisAlignedBB);
      }

      if (outline) {
         GL11.glLineWidth(outlineWidth);
         enableGlCap(2848);
         glColor(color);
         drawSelectionBoundingBox(axisAlignedBB);
      }

      GlStateManager.func_179117_G();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void drawBlockBoxGradient(BlockPos blockPos, Color color1, Color color2, boolean outline, boolean box, float outlineWidth, float animation) {
      RenderManager renderManager = mc.func_175598_ae();
      Timer timer = mc.field_71428_T;
      double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
      double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
      double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
      double adjustedHeight = (double)animation;
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + (double)1.0F, y + adjustedHeight, z + (double)1.0F);
      Block block = BlockUtils.getBlock(blockPos);
      if (block != null) {
         EntityPlayer player = mc.field_71439_g;
         double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
         double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
         double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
         axisAlignedBB = block.func_180646_a(mc.field_71441_e, blockPos).func_72314_b((double)0.002F, (double)0.002F, (double)0.002F).func_72317_d(-posX, -posY, -posZ);
         axisAlignedBB = new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, Math.min(axisAlignedBB.field_72337_e, axisAlignedBB.field_72338_b + adjustedHeight), axisAlignedBB.field_72334_f);
      }

      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      if (box) {
         GL11.glShadeModel(7425);
         GlStateManager.func_179147_l();
         GlStateManager.func_179112_b(770, 771);
         GlStateManager.func_179090_x();
         GlStateManager.func_179129_p();
         Tessellator tessellator = Tessellator.func_178181_a();
         WorldRenderer buffer = tessellator.func_178180_c();
         buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         float r1 = (float)color1.getRed() / 255.0F;
         float g1 = (float)color1.getGreen() / 255.0F;
         float b1 = (float)color1.getBlue() / 255.0F;
         float a1 = (float)color1.getAlpha() / 255.0F;
         float r2 = (float)color2.getRed() / 255.0F;
         float g2 = (float)color2.getGreen() / 255.0F;
         float b2 = (float)color2.getBlue() / 255.0F;
         float a2 = (float)color2.getAlpha() / 255.0F;
         double minX = axisAlignedBB.field_72340_a;
         double minY = axisAlignedBB.field_72338_b;
         double minZ = axisAlignedBB.field_72339_c;
         double maxX = axisAlignedBB.field_72336_d;
         double maxY = axisAlignedBB.field_72337_e;
         double maxZ = axisAlignedBB.field_72334_f;
         buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(minX, minY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(minX, minY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(minX, minY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
         buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
         tessellator.func_78381_a();
         GL11.glShadeModel(7424);
         GlStateManager.func_179098_w();
         GlStateManager.func_179089_o();
      }

      if (outline) {
         GL11.glLineWidth(outlineWidth);
         enableGlCap(2848);
         GL11.glShadeModel(7425);
         float r1 = (float)color1.getRed() / 255.0F;
         float g1 = (float)color1.getGreen() / 255.0F;
         float b1 = (float)color1.getBlue() / 255.0F;
         float a1 = (float)color1.getAlpha() / 255.0F;
         float r2 = (float)color2.getRed() / 255.0F;
         float g2 = (float)color2.getGreen() / 255.0F;
         float b2 = (float)color2.getBlue() / 255.0F;
         float a2 = (float)color2.getAlpha() / 255.0F;
         double minX = axisAlignedBB.field_72340_a;
         double minY = axisAlignedBB.field_72338_b;
         double minZ = axisAlignedBB.field_72339_c;
         double maxX = axisAlignedBB.field_72336_d;
         double maxY = axisAlignedBB.field_72337_e;
         double maxZ = axisAlignedBB.field_72334_f;
         GL11.glBegin(1);
         drawGradientLine(minX, minY, minZ, maxX, minY, minZ, r1, g1, b1, a1, r2, g2, b2, a2);
         drawGradientLine(maxX, minY, minZ, maxX, minY, maxZ, r2, g2, b2, a2, r1, g1, b1, a1);
         drawGradientLine(maxX, minY, maxZ, minX, minY, maxZ, r1, g1, b1, a1, r2, g2, b2, a2);
         drawGradientLine(minX, minY, maxZ, minX, minY, minZ, r2, g2, b2, a2, r1, g1, b1, a1);
         drawGradientLine(minX, maxY, minZ, maxX, maxY, minZ, r1, g1, b1, a1, r2, g2, b2, a2);
         drawGradientLine(maxX, maxY, minZ, maxX, maxY, maxZ, r2, g2, b2, a2, r1, g1, b1, a1);
         drawGradientLine(maxX, maxY, maxZ, minX, maxY, maxZ, r1, g1, b1, a1, r2, g2, b2, a2);
         drawGradientLine(minX, maxY, maxZ, minX, maxY, minZ, r2, g2, b2, a2, r1, g1, b1, a1);
         drawGradientLine(minX, minY, minZ, minX, maxY, minZ, r1, g1, b1, a1, r2, g2, b2, a2);
         drawGradientLine(maxX, minY, minZ, maxX, maxY, minZ, r2, g2, b2, a2, r1, g1, b1, a1);
         drawGradientLine(maxX, minY, maxZ, maxX, maxY, maxZ, r1, g1, b1, a1, r2, g2, b2, a2);
         drawGradientLine(minX, minY, maxZ, minX, maxY, maxZ, r2, g2, b2, a2, r1, g1, b1, a1);
         GL11.glEnd();
         GL11.glShadeModel(7424);
      }

      GlStateManager.func_179117_G();
      GL11.glDepthMask(true);
      resetCaps();
   }

   private static void drawGradientLine(double x1, double y1, double z1, double x2, double y2, double z2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
      GL11.glColor4f(r1, g1, b1, a1);
      GL11.glVertex3d(x1, y1, z1);
      GL11.glColor4f(r2, g2, b2, a2);
      GL11.glVertex3d(x2, y2, z2);
   }

   private static void drawBox(AxisAlignedBB box, Color color) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179129_p();
      GlStateManager.func_179112_b(770, 771);
      GlStateManager.func_179103_j(7425);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer buffer = tessellator.func_178180_c();
      float r = (float)color.getRed() / 255.0F;
      float g = (float)color.getGreen() / 255.0F;
      float b = (float)color.getBlue() / 255.0F;
      float a = (float)color.getAlpha() / 255.0F;
      buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      double minX = box.field_72340_a;
      double minY = box.field_72338_b;
      double minZ = box.field_72339_c;
      double maxX = box.field_72336_d;
      double maxY = box.field_72337_e;
      double maxZ = box.field_72334_f;
      buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
      buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179089_o();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179103_j(7424);
   }

   public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawEntityBox(Entity entity, Color color, boolean outline, boolean box, float outlineWidth) {
      RenderManager renderManager = mc.func_175598_ae();
      Timer timer = mc.field_71428_T;
      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
      AxisAlignedBB entityBox = entity.func_174813_aQ();
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.field_72340_a - entity.field_70165_t + x - 0.05, entityBox.field_72338_b - entity.field_70163_u + y, entityBox.field_72339_c - entity.field_70161_v + z - 0.05, entityBox.field_72336_d - entity.field_70165_t + x + 0.05, entityBox.field_72337_e - entity.field_70163_u + y + 0.15, entityBox.field_72334_f - entity.field_70161_v + z + 0.05);
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(x, y, z);
      GlStateManager.func_179114_b(-(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * timer.field_74281_c), 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179137_b(-x, -y, -z);
      if (outline) {
         GL11.glLineWidth(outlineWidth);
         enableGlCap(2848);
         glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
         drawSelectionBoundingBox(axisAlignedBB);
      }

      if (box) {
         glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 50);
         drawFilledBox(axisAlignedBB);
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_179117_G();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void drawAxisAlignedBB(AxisAlignedBB axisAlignedBB, Color color, boolean outline, boolean box, float outlineWidth) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(outlineWidth);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      glColor(color);
      if (outline) {
         GL11.glLineWidth(outlineWidth);
         enableGlCap(2848);
         glColor(color.getRed(), color.getGreen(), color.getBlue(), 255);
         drawSelectionBoundingBox(axisAlignedBB);
      }

      if (box) {
         glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
         drawFilledBox(axisAlignedBB);
      }

      GlStateManager.func_179117_G();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void drawPlatform(double y, Color color, double size) {
      RenderManager renderManager = mc.func_175598_ae();
      double renderY = y - renderManager.field_78726_c;
      drawAxisAlignedBB(new AxisAlignedBB(size, renderY + 0.02, size, -size, renderY, -size), color, false, true, 2.0F);
   }

   public static void drawPlatform(Entity entity, Color color) {
      RenderManager renderManager = mc.func_175598_ae();
      Timer timer = mc.field_71428_T;
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
      AxisAlignedBB axisAlignedBB = entity.func_174813_aQ().func_72317_d(-entity.field_70165_t, -entity.field_70163_u, -entity.field_70161_v).func_72317_d(x, y, z);
      drawAxisAlignedBB(new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e + 0.2, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e + 0.26, axisAlignedBB.field_72334_f), color, false, true, 2.0F);
   }

   public static void renderBox(AxisAlignedBB axisAlignedBB, Color c) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      GlStateManager.func_179097_i();
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179145_e();
      GlStateManager.func_179089_o();
      GlStateManager.func_179147_l();
      GlStateManager.func_179117_G();
      GlStateManager.func_179121_F();
   }

   public static void drawFilledBox(AxisAlignedBB axisAlignedBB) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void renderOutlines(double x, double y, double z, float width, float height, float heightScale, Color c, float outlineWidth) {
      float halfWidth = width / 2.0F;
      float adjustedHeight = height * heightScale;
      double topY = y + (double)adjustedHeight;
      GlStateManager.func_179094_E();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      GlStateManager.func_179097_i();
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      GL11.glLineWidth(outlineWidth);
      worldRenderer.func_181662_b(x - (double)halfWidth, y, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, y, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, y, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, y, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, y, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, y, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, y, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, y, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, y, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, y, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, y, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, y, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179145_e();
      GlStateManager.func_179089_o();
      GlStateManager.func_179147_l();
      GlStateManager.func_179121_F();
   }

   public static void renderBox(double x, double y, double z, float width, float height, float animation, Color c) {
      float halfwidth = width / 2.0F;
      float baseHeight = height * animation;
      GlStateManager.func_179094_E();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      GlStateManager.func_179097_i();
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b(x - (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)baseHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)baseHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)baseHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)baseHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)baseHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)baseHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)baseHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)baseHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)baseHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)baseHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)baseHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)baseHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x + (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      worldRenderer.func_181662_b(x - (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179145_e();
      GlStateManager.func_179089_o();
      GlStateManager.func_179147_l();
      GlStateManager.func_179117_G();
      GlStateManager.func_179121_F();
   }

   public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline) {
      RenderManager renderManager = mc.func_175598_ae();
      Timer timer = mc.field_71428_T;
      double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
      double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
      double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + (double)1.0F, y + (double)1.0F, z + (double)1.0F);
      Block block = BlockUtils.getBlock(blockPos);
      if (block != null) {
         EntityPlayer player = mc.field_71439_g;
         double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
         double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
         double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
         axisAlignedBB = block.func_180646_a(mc.field_71441_e, blockPos).func_72314_b((double)0.002F, (double)0.002F, (double)0.002F).func_72317_d(-posX, -posY, -posZ);
      }

      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
      drawFilledBox(axisAlignedBB);
      if (outline) {
         GL11.glLineWidth(1.0F);
         enableGlCap(2848);
         glColor(color);
         drawSelectionBoundingBox(axisAlignedBB);
      }

      GlStateManager.func_179117_G();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void drawBlockBox(BlockPos blockPos, BlockPos blockPos2, Color color, boolean outline) {
      RenderManager renderManager = mc.func_175598_ae();
      Timer timer = mc.field_71428_T;
      double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
      double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
      double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + (double)1.0F, y + (double)1.0F, z + (double)1.0F);
      Block block = BlockUtils.getBlock(blockPos);
      if (block != null) {
         EntityPlayer player = mc.field_71439_g;
         double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
         double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
         double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
         AxisAlignedBB axisAlignedBB2 = block.func_180646_a(mc.field_71441_e, blockPos2).func_72314_b((double)0.002F, (double)0.002F, (double)0.002F).func_72317_d(-posX, -posY, -posZ);
         axisAlignedBB = block.func_180646_a(mc.field_71441_e, blockPos).func_72314_b((double)0.002F, (double)0.002F, (double)0.002F).func_72317_d(-posX, -posY, -posZ).func_111270_a(axisAlignedBB2);
      }

      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
      drawFilledBox(axisAlignedBB);
      if (outline) {
         GL11.glLineWidth(1.0F);
         enableGlCap(2848);
         glColor(color);
         drawSelectionBoundingBox(axisAlignedBB);
      }

      GlStateManager.func_179117_G();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void quickDrawRect(float x, float y, float x2, float y2) {
      GL11.glBegin(7);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glEnd();
   }

   public static void quickDrawRect(float x, float y, float x2, float y2, int color) {
      glColor(color);
      quickDrawRect(x, y, x2, y2);
   }

   public static void quickDrawRect(float x, float y, float x2, float y2, Color color) {
      quickDrawRect(x, y, x2, y2, color.getRGB());
   }

   public static void drawRect(float left, float top, float right, float bottom, int color) {
      if (left < right) {
         float i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         float j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(f, f1, f2, f3);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)left, (double)bottom, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)bottom, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)top, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)top, (double)0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawRect(double x, double y, double x2, double y2, int color) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      glColor(color);
      GL11.glBegin(7);
      GL11.glVertex2d(x2, y);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x, y2);
      GL11.glVertex2d(x2, y2);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawRect(Rectangle rect, int color) {
      drawRect(rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), color);
   }

   public static void drawRect(float x, float y, float x2, float y2, Color color) {
      drawRect(x, y, x2, y2, color.getRGB());
   }

   public static void drawBorderedRect(double x, double y, double x2, double y2, double width, int color1, int color2) {
      drawBorderedRect((float)x, (float)y, (float)x2, (float)y2, (float)width, color1, color2);
   }

   public static void drawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
      drawRect(x, y, x2, y2, color2);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      glColor(color1);
      GL11.glLineWidth(width);
      GL11.glBegin(1);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void newDrawRect(float left, float top, float right, float bottom, int color) {
      if (left < right) {
         float i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         float j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(f, f1, f2, f3);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)left, (double)bottom, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)bottom, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)top, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)top, (double)0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void newDrawRect(double left, double top, double right, double bottom, int color) {
      if (left < right) {
         double i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         double j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(f, f1, f2, f3);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(left, bottom, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b(right, bottom, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b(right, top, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b(left, top, (double)0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      float z = 0.0F;
      if (paramXStart > paramXEnd) {
         z = paramXStart;
         paramXStart = paramXEnd;
         paramXEnd = z;
      }

      if (paramYStart > paramYEnd) {
         z = paramYStart;
         paramYStart = paramYEnd;
         paramYEnd = z;
      }

      double x1 = (double)(paramXStart + radius);
      double y1 = (double)(paramYStart + radius);
      double x2 = (double)(paramXEnd - radius);
      double y2 = (double)(paramYEnd - radius);
      if (popPush) {
         GL11.glPushMatrix();
      }

      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(9);
      double degree = (Math.PI / 180D);

      for(double i = (double)0.0F; i <= (double)90.0F; ++i) {
         GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
      }

      for(double i = (double)90.0F; i <= (double)180.0F; ++i) {
         GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
      }

      for(double i = (double)180.0F; i <= (double)270.0F; ++i) {
         GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
      }

      for(double i = (double)270.0F; i <= (double)360.0F; ++i) {
         GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      if (popPush) {
         GL11.glPopMatrix();
      }

   }

   public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush, boolean roundTopLeft, boolean roundTopRight, boolean roundBottomLeft, boolean roundBottomRight) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      float z = 0.0F;
      if (paramXStart > paramXEnd) {
         z = paramXStart;
         paramXStart = paramXEnd;
         paramXEnd = z;
      }

      if (paramYStart > paramYEnd) {
         z = paramYStart;
         paramYStart = paramYEnd;
         paramYEnd = z;
      }

      double x1 = (double)paramXStart;
      double y1 = (double)paramYStart;
      double x2 = (double)paramXEnd;
      double y2 = (double)paramYEnd;
      if (popPush) {
         GL11.glPushMatrix();
      }

      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(9);
      double degree = (Math.PI / 180D);
      if (roundTopLeft) {
         for(double i = (double)0.0F; i <= (double)90.0F; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
         }
      } else {
         GL11.glVertex2d(x1, y1);
      }

      if (roundTopRight) {
         for(double i = (double)90.0F; i <= (double)180.0F; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
         }
      } else {
         GL11.glVertex2d(x2, y1);
      }

      if (roundBottomRight) {
         for(double i = (double)180.0F; i <= (double)270.0F; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
         }
      } else {
         GL11.glVertex2d(x2, y2);
      }

      if (roundBottomLeft) {
         for(double i = (double)270.0F; i <= (double)360.0F; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
         }
      } else {
         GL11.glVertex2d(x1, y2);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      if (popPush) {
         GL11.glPopMatrix();
      }

   }

   public static void drawLoadingCircle(float x, float y) {
      for(int i = 0; i < 4; ++i) {
         int rot = (int)(System.nanoTime() / 5000000L * (long)i % 360L);
         drawCircle(x, y, (float)(i * 10), rot - 180, rot, Color.WHITE);
      }

   }

   public static void drawCircle(float x, float y, float radius, int start, int end, Color color) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      glColor(color.getRGB());
      GL11.glEnable(2848);
      GL11.glLineWidth(2.0F);
      GL11.glBegin(3);

      for(float i = (float)end; i >= (float)start; i -= 4.0F) {
         GL11.glVertex2f((float)((double)x + Math.cos((double)i * Math.PI / (double)180.0F) * (double)(radius * 1.001F)), (float)((double)y + Math.sin((double)i * Math.PI / (double)180.0F) * (double)(radius * 1.001F)));
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179117_G();
   }

   public static void drawLimitedCircle(float lx, float ly, float x2, float y2, int xx, int yy, float radius, Color color) {
      int sections = 50;
      double dAngle = (Math.PI * 2D) / (double)sections;
      GL11.glPushAttrib(8192);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glBegin(6);
      glColor(color);

      for(int i = 0; i < sections; ++i) {
         float x = (float)((double)radius * Math.sin((double)i * dAngle));
         float y = (float)((double)radius * Math.cos((double)i * dAngle));
         GL11.glVertex2f(Math.min(x2, Math.max((float)xx + x, lx)), Math.min(y2, Math.max((float)yy + y, ly)));
      }

      GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
      GL11.glEnd();
      GL11.glPopAttrib();
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      mc.func_110434_K().func_110577_a(image);
      Gui.func_146110_a(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawSquareTriangle(float cx, float cy, float dirX, float dirY, Color color, boolean filled) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179117_G();
      glColor(color);
      worldrenderer.func_181668_a(filled ? 5 : 2, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)(cx + dirX), (double)cy, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)cx, (double)cy, (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)cx, (double)(cy + dirY), (double)0.0F).func_181675_d();
      worldrenderer.func_181662_b((double)(cx + dirX), (double)cy, (double)0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawImage(BufferedImage image, int x, int y, int width, int height) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ByteBuffer buffer = ByteBuffer.allocateDirect(4 * image.getWidth() * image.getHeight());
      int[] pixels = new int[image.getWidth() * image.getHeight()];
      image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

      for(int i = 0; i < pixels.length; ++i) {
         int pixel = pixels[i];
         buffer.put((byte)(pixel >> 16 & 255));
         buffer.put((byte)(pixel >> 8 & 255));
         buffer.put((byte)(pixel & 255));
         buffer.put((byte)(pixel >> 24 & 255));
      }

      buffer.flip();
      int textureId = GL11.glGenTextures();
      GL11.glBindTexture(3553, textureId);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexImage2D(3553, 0, 32856, image.getWidth(), image.getHeight(), 0, 6408, 5121, buffer);
      GL11.glEnable(3553);
      GL11.glBindTexture(3553, textureId);
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2i(x, y);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2i(x, y + height);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2i(x + width, y + height);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2i(x + width, y);
      GL11.glEnd();
      GL11.glDisable(3553);
      GL11.glDeleteTextures(textureId);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height, float alpha) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
      mc.func_110434_K().func_110577_a(image);
      Gui.func_146110_a(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawImage2(ResourceLocation image, float x, float y, int width, int height) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glTranslatef(x, y, x);
      mc.func_110434_K().func_110577_a(image);
      Gui.func_146110_a(0, 0, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glTranslatef(-x, -y, -x);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawImage3(ResourceLocation image, float x, float y, int width, int height, float r, float g, float b, float al) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(r, g, b, al);
      GL11.glTranslatef(x, y, x);
      mc.func_110434_K().func_110577_a(image);
      Gui.func_146110_a(0, 0, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glTranslatef(-x, -y, -x);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawRectBasedBorder(float x, float y, float x2, float y2, float width, int color1) {
      drawRect(x - width / 2.0F, y - width / 2.0F, x2 + width / 2.0F, y + width / 2.0F, color1);
      drawRect(x - width / 2.0F, y + width / 2.0F, x + width / 2.0F, y2 + width / 2.0F, color1);
      drawRect(x2 - width / 2.0F, y + width / 2.0F, x2 + width / 2.0F, y2 + width / 2.0F, color1);
      drawRect(x + width / 2.0F, y2 - width / 2.0F, x2 - width / 2.0F, y2 + width / 2.0F, color1);
   }

   public static void drawRectBasedBorder(double x, double y, double x2, double y2, double width, int color1) {
      newDrawRect(x - width / (double)2.0F, y - width / (double)2.0F, x2 + width / (double)2.0F, y + width / (double)2.0F, color1);
      newDrawRect(x - width / (double)2.0F, y + width / (double)2.0F, x + width / (double)2.0F, y2 + width / (double)2.0F, color1);
      newDrawRect(x2 - width / (double)2.0F, y + width / (double)2.0F, x2 + width / (double)2.0F, y2 + width / (double)2.0F, color1);
      newDrawRect(x + width / (double)2.0F, y2 - width / (double)2.0F, x2 - width / (double)2.0F, y2 + width / (double)2.0F, color1);
   }

   public static void lineWidth(float width) {
      GL11.glLineWidth(width);
   }

   public static void begin(int glMode) {
      GL11.glBegin(glMode);
   }

   public static void vertex(double x, double y) {
      GL11.glVertex2d(x, y);
   }

   public static void end() {
      GL11.glEnd();
   }

   public static void enable(int glTarget) {
      GL11.glEnable(glTarget);
   }

   public static void color(Color color) {
      if (color == null) {
         color = Color.white;
      }

      color((double)((float)color.getRed() / 255.0F), (double)((float)color.getGreen() / 255.0F), (double)((float)color.getBlue() / 255.0F), (double)((float)color.getAlpha() / 255.0F));
   }

   public static void color(double red, double green, double blue, double alpha) {
      GL11.glColor4d(red, green, blue, alpha);
   }

   public static void color(int color, float alpha) {
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      GlStateManager.func_179131_c(r, g, b, alpha);
   }

   public static void color(int color) {
      color(color, (float)(color >> 24 & 255) / 255.0F);
   }

   public static void disable(int glTarget) {
      GL11.glDisable(glTarget);
   }

   public static void lineNoGl(double firstX, double firstY, double secondX, double secondY, Color color) {
      Nobody.start();
      if (color != null) {
         color(color);
      }

      lineWidth(1.0F);
      GL11.glEnable(2848);
      begin(1);
      vertex(firstX, firstY);
      vertex(secondX, secondY);
      end();
      GL11.glDisable(2848);
      Nobody.stop();
   }

   public static void glColor(int red, int green, int blue, int alpha) {
      GlStateManager.func_179131_c((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F, (float)alpha / 255.0F);
   }

   public static void glColor(Color color) {
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      float alpha = (float)color.getAlpha() / 255.0F;
      GlStateManager.func_179131_c(red, green, blue, alpha);
   }

   public static void glColor(Color color, int alpha) {
      glColor(color, (float)alpha / 255.0F);
   }

   public static void glColor(Color color, float alpha) {
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      GlStateManager.func_179131_c(red, green, blue, alpha);
   }

   public static void glColor(int hex) {
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GlStateManager.func_179131_c(red, green, blue, alpha);
   }

   public static void glColor(int hex, int alpha) {
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GlStateManager.func_179131_c(red, green, blue, (float)alpha / 255.0F);
   }

   public static void glColor(int hex, float alpha) {
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GlStateManager.func_179131_c(red, green, blue, alpha);
   }

   public static void drawTriAngle(float cx, float cy, float r, float n, Color color, boolean polygon) {
      cx = (float)((double)cx * (double)2.0F);
      cy = (float)((double)cy * (double)2.0F);
      double b = 6.2831852 / (double)n;
      double p = Math.cos(b);
      double s = Math.sin(b);
      r = (float)((double)r * (double)2.0F);
      double x = (double)r;
      double y = (double)0.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GL11.glLineWidth(1.0F);
      enableGlCap(2848);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179117_G();
      glColor(color);
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      worldrenderer.func_181668_a(polygon ? 9 : 2, DefaultVertexFormats.field_181705_e);

      for(int ii = 0; (float)ii < n; ++ii) {
         worldrenderer.func_181662_b(x + (double)cx, y + (double)cy, (double)0.0F).func_181675_d();
         double t = x;
         x = p * x - s * y;
         y = s * t + p * y;
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawCircle(float x, float y, float radius, float lineWidth, int start, int end, Color color) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      glColor(color);
      GL11.glEnable(2848);
      GL11.glLineWidth(lineWidth);
      GL11.glBegin(3);

      for(float i = (float)end; i >= (float)start; i -= 4.0F) {
         GL11.glVertex2f((float)((double)x + Math.cos((double)i * Math.PI / (double)180.0F) * (double)(radius * 1.001F)), (float)((double)y + Math.sin((double)i * Math.PI / (double)180.0F) * (double)(radius * 1.001F)));
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawCircle(float cx, float cy, float r, int segments, int color) {
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(6);
      GL11.glVertex2f(cx, cy);

      for(int i = 0; i <= segments; ++i) {
         double angle = (Math.PI * 2D) * (double)i / (double)segments;
         float x = (float)((double)cx + (double)r * Math.cos(angle));
         float y = (float)((double)cy + (double)r * Math.sin(angle));
         GL11.glVertex2f(x, y);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawCircle(float x, float y, float radius, float lineWidth, int start, int end) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      glColor(Color.WHITE);
      GL11.glEnable(2848);
      GL11.glLineWidth(lineWidth);
      GL11.glBegin(3);

      for(float i = (float)end; i >= (float)start; i -= 4.0F) {
         GL11.glVertex2f((float)((double)x + Math.cos((double)i * Math.PI / (double)180.0F) * (double)(radius * 1.001F)), (float)((double)y + Math.sin((double)i * Math.PI / (double)180.0F) * (double)(radius * 1.001F)));
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawArrow(double x, double y, int lineWidth, int color, double length) {
      start2D();
      GL11.glPushMatrix();
      GL11.glLineWidth((float)lineWidth);
      setColor(new Color(color));
      GL11.glBegin(3);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x + (double)3.0F, y + length);
      GL11.glVertex2d(x + (double)6.0F, y);
      GL11.glEnd();
      GL11.glPopMatrix();
      stop2D();
   }

   public static void draw2D(EntityLivingBase entity, double posX, double posY, double posZ, int color, int backgroundColor) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(posX, posY, posZ);
      GlStateManager.func_179114_b(-mc.func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179139_a(-0.1, -0.1, 0.1);
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GlStateManager.func_179132_a(true);
      glColor(color);
      GL11.glCallList(DISPLAY_LISTS_2D[0]);
      glColor(backgroundColor);
      GL11.glCallList(DISPLAY_LISTS_2D[1]);
      GlStateManager.func_179137_b((double)0.0F, (double)21.0F + -(entity.func_174813_aQ().field_72337_e - entity.func_174813_aQ().field_72338_b) * (double)12.0F, (double)0.0F);
      glColor(color);
      GL11.glCallList(DISPLAY_LISTS_2D[2]);
      glColor(backgroundColor);
      GL11.glCallList(DISPLAY_LISTS_2D[3]);
      GL11.glEnable(2929);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GlStateManager.func_179121_F();
   }

   public static void draw2D(BlockPos blockPos, int color, int backgroundColor) {
      RenderManager renderManager = mc.func_175598_ae();
      double posX = (double)blockPos.func_177958_n() + (double)0.5F - renderManager.field_78725_b;
      double posY = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
      double posZ = (double)blockPos.func_177952_p() + (double)0.5F - renderManager.field_78723_d;
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(posX, posY, posZ);
      GlStateManager.func_179114_b(-mc.func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179139_a(-0.1, -0.1, 0.1);
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GlStateManager.func_179132_a(true);
      glColor(color);
      GL11.glCallList(DISPLAY_LISTS_2D[0]);
      glColor(backgroundColor);
      GL11.glCallList(DISPLAY_LISTS_2D[1]);
      GlStateManager.func_179109_b(0.0F, 9.0F, 0.0F);
      glColor(color);
      GL11.glCallList(DISPLAY_LISTS_2D[2]);
      glColor(backgroundColor);
      GL11.glCallList(DISPLAY_LISTS_2D[3]);
      GL11.glEnable(2929);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GlStateManager.func_179121_F();
   }

   public static void drawSaturationBrightnessPicker(float x, float y, float width, float height, float hue) {
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      int steps = 100;

      for(int i = 0; i < steps; ++i) {
         float sy = (float)i / (float)steps;
         float ey = (float)(i + 1) / (float)steps;
         GL11.glBegin(8);

         for(int j = 0; j <= steps; ++j) {
            float sx = (float)j / (float)steps;
            Color top = Color.getHSBColor(hue, sx, 1.0F - sy);
            Color bottom = Color.getHSBColor(hue, sx, 1.0F - ey);
            GL11.glColor4f((float)top.getRed() / 255.0F, (float)top.getGreen() / 255.0F, (float)top.getBlue() / 255.0F, 1.0F);
            GL11.glVertex2f(x + sx * width, y + sy * height);
            GL11.glColor4f((float)bottom.getRed() / 255.0F, (float)bottom.getGreen() / 255.0F, (float)bottom.getBlue() / 255.0F, 1.0F);
            GL11.glVertex2f(x + sx * width, y + ey * height);
         }

         GL11.glEnd();
      }

      GL11.glShadeModel(7424);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void drawHueBar(float x, float y, float width, float height) {
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glBegin(7);
      int segments = 100;

      for(int i = 0; i < segments; ++i) {
         float hue1 = (float)i / (float)segments;
         float hue2 = (float)(i + 1) / (float)segments;
         Color color1 = Color.getHSBColor(hue1, 1.0F, 1.0F);
         Color color2 = Color.getHSBColor(hue2, 1.0F, 1.0F);
         GL11.glColor4f((float)color1.getRed() / 255.0F, (float)color1.getGreen() / 255.0F, (float)color1.getBlue() / 255.0F, 1.0F);
         GL11.glVertex2f(x + (float)i * (width / (float)segments), y);
         GL11.glVertex2f(x + (float)i * (width / (float)segments), y + height);
         GL11.glColor4f((float)color2.getRed() / 255.0F, (float)color2.getGreen() / 255.0F, (float)color2.getBlue() / 255.0F, 1.0F);
         float x1 = x + (float)(i + 1) * (width / (float)segments);
         GL11.glVertex2f(x1, y + height);
         GL11.glVertex2f(x1, y);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public static void drawCheckerboard(float x, float y, float width, float height, int squareSize) {
      for(int ix = 0; (float)ix < width; ix += squareSize) {
         for(int iy = 0; (float)iy < height; iy += squareSize) {
            boolean even = (ix / squareSize + iy / squareSize) % 2 == 0;
            Color color = even ? new Color(200, 200, 200) : new Color(255, 255, 255);
            drawRect(x + (float)ix, y + (float)iy, x + (float)ix + (float)squareSize, y + (float)iy + (float)squareSize, color.getRGB());
         }
      }

   }

   public static void drawAlphaBar(float x, float y, float width, float height) {
      drawCheckerboard(x, y, width, height, 4);
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glShadeModel(7425);
      GL11.glBegin(7);

      for(int i = 0; (float)i < width; ++i) {
         float alpha = (float)i / width;
         Color color = new Color(0.0F, 0.0F, 0.0F, alpha);
         GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
         GL11.glVertex2f(x + (float)i, y);
         GL11.glVertex2f(x + (float)i, y + height);
         GL11.glVertex2f(x + (float)i + 1.0F, y + height);
         GL11.glVertex2f(x + (float)i + 1.0F, y);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawCircleOutline(float cx, float cy, float radius, Color color) {
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.5F);
      GL11.glColor3f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F);
      GL11.glBegin(2);

      for(int i = 0; i <= 360; i += 10) {
         double angle = Math.toRadians((double)i);
         double dx = (double)cx + Math.cos(angle) * (double)radius;
         double dy = (double)cy + Math.sin(angle) * (double)radius;
         GL11.glVertex2d(dx, dy);
      }

      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void renderNameTag(String string, double x, double y, double z) {
      RenderManager renderManager = mc.func_175598_ae();
      GL11.glPushMatrix();
      GL11.glTranslated(x - renderManager.field_78725_b, y - renderManager.field_78726_c, z - renderManager.field_78723_d);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-mc.func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(mc.func_175598_ae().field_78732_j, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-0.05F, -0.05F, 0.05F);
      setGlCap(2896, false);
      setGlCap(2929, false);
      setGlCap(3042, true);
      GL11.glBlendFunc(770, 771);
      int width = Fonts.font35.func_78256_a(string) / 2;
      Gui.func_73734_a(-width - 1, -1, width + 1, Fonts.font35.field_78288_b, Integer.MIN_VALUE);
      Fonts.font35.func_175065_a(string, (float)(-width), 1.5F, Color.WHITE.getRGB(), true);
      resetCaps();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void makeScissorBox(float x, float y, float x2, float y2) {
      makeScissorBox(x, y, x2, y2, 1.0F);
   }

   public static void makeScissorBox(float x, float y, float x2, float y2, float scaleOffset) {
      ScaledResolution scaledResolution = StaticStorage.scaledResolution;
      float factor = (float)scaledResolution.func_78325_e() * scaleOffset;
      GL11.glScissor((int)(x * factor), (int)(((float)scaledResolution.func_78328_b() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
   }

   public static void resetCaps(String scale) {
      if (glCapMap.containsKey(scale)) {
         Map<Integer, Boolean> map = (Map)glCapMap.get(scale);
         map.forEach(RenderUtils::setGlState);
         map.clear();
      }
   }

   public static void resetCaps() {
      resetCaps("COMMON");
   }

   public static void clearCaps(String scale) {
      if (glCapMap.containsKey(scale)) {
         Map<Integer, Boolean> map = (Map)glCapMap.get(scale);
         if (!map.isEmpty()) {
            ClientUtils.INSTANCE.logWarn("Cap map is not empty! [" + map.size() + "]");
         }

         map.clear();
      }
   }

   public static void clearCaps() {
      clearCaps("COMMON");
   }

   public static void enableGlCap(int cap, String scale) {
      setGlCap(cap, true, scale);
   }

   public static void enableGlCap(int cap) {
      enableGlCap(cap, "COMMON");
   }

   public static void disableGlCap(int cap, String scale) {
      setGlCap(cap, false, scale);
   }

   public static void disableGlCap(int cap) {
      disableGlCap(cap, "COMMON");
   }

   public static void enableGlCap(int... caps) {
      for(int cap : caps) {
         setGlCap(cap, true, "COMMON");
      }

   }

   public static void disableGlCap(int... caps) {
      for(int cap : caps) {
         setGlCap(cap, false, "COMMON");
      }

   }

   public static void setGlCap(int cap, boolean state, String scale) {
      if (!glCapMap.containsKey(scale)) {
         glCapMap.put(scale, new HashMap());
      }

      ((Map)glCapMap.get(scale)).put(cap, GL11.glGetBoolean(cap));
      setGlState(cap, state);
   }

   public static void setGlCap(int cap, boolean state) {
      setGlCap(cap, state, "COMMON");
   }

   public static void setGlState(int cap, boolean state) {
      if (state) {
         GL11.glEnable(cap);
      } else {
         GL11.glDisable(cap);
      }

   }

   public static double[] convertTo2D(double x, double y, double z) {
      FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
      IntBuffer viewport = BufferUtils.createIntBuffer(16);
      FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
      FloatBuffer projection = BufferUtils.createFloatBuffer(16);
      GL11.glGetFloat(2982, modelView);
      GL11.glGetFloat(2983, projection);
      GL11.glGetInteger(2978, viewport);
      boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
      return result ? new double[]{(double)screenCoords.get(0), (double)((float)Display.getHeight() - screenCoords.get(1)), (double)screenCoords.get(2)} : null;
   }

   public static void rectangle(double left, double top, double right, double bottom, int color) {
      if (left < right) {
         double var5 = left;
         left = right;
         right = var5;
      }

      if (top < bottom) {
         double var5 = top;
         top = bottom;
         bottom = var5;
      }

      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(var6, var7, var8, var11);
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(left, bottom, (double)0.0F).func_181675_d();
      worldRenderer.func_181662_b(right, bottom, (double)0.0F).func_181675_d();
      worldRenderer.func_181662_b(right, top, (double)0.0F).func_181675_d();
      worldRenderer.func_181662_b(left, top, (double)0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static Color reAlpha(Color cIn, float alpha) {
      return new Color((float)cIn.getRed() / 255.0F, (float)cIn.getGreen() / 255.0F, (float)cIn.getBlue() / 255.0F, (float)cIn.getAlpha() / 255.0F * alpha);
   }

   public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
      rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x + width, y, x1 - width, y + width, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x, y, x + width, y1, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x1 - width, y, x1, y1, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void skeetRect(double x, double y, double x1, double y1, double size) {
      rectangleBordered(x, y - (double)4.0F, x1 + size, y1 + size, (double)0.5F, (new Color(60, 60, 60)).getRGB(), (new Color(10, 10, 10)).getRGB());
      rectangleBordered(x + (double)1.0F, y - (double)3.0F, x1 + size - (double)1.0F, y1 + size - (double)1.0F, (double)1.0F, (new Color(40, 40, 40)).getRGB(), (new Color(40, 40, 40)).getRGB());
      rectangleBordered(x + (double)2.5F, y - (double)1.5F, x1 + size - (double)2.5F, y1 + size - (double)2.5F, (double)0.5F, (new Color(40, 40, 40)).getRGB(), (new Color(60, 60, 60)).getRGB());
      rectangleBordered(x + (double)2.5F, y - (double)1.5F, x1 + size - (double)2.5F, y1 + size - (double)2.5F, (double)0.5F, (new Color(22, 22, 22)).getRGB(), (new Color(255, 255, 255, 0)).getRGB());
   }

   public static void skeetRectSmall(double x, double y, double x1, double y1, double size) {
      rectangleBordered(x + 4.35, y + (double)0.5F, x1 + size - (double)84.5F, y1 + size - 4.35, (double)0.5F, (new Color(48, 48, 48)).getRGB(), (new Color(10, 10, 10)).getRGB());
      rectangleBordered(x + (double)5.0F, y + (double)1.0F, x1 + size - (double)85.0F, y1 + size - (double)5.0F, (double)0.5F, (new Color(17, 17, 17)).getRGB(), (new Color(255, 255, 255, 0)).getRGB());
   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawBoundingBox(AxisAlignedBB aa) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
      worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void originalRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      float z = 0.0F;
      if (paramXStart > paramXEnd) {
         z = paramXStart;
         paramXStart = paramXEnd;
         paramXEnd = z;
      }

      if (paramYStart > paramYEnd) {
         z = paramYStart;
         paramYStart = paramYEnd;
         paramYEnd = z;
      }

      double x1 = (double)(paramXStart + radius);
      double y1 = (double)(paramYStart + radius);
      double x2 = (double)(paramXEnd - radius);
      double y2 = (double)(paramYEnd - radius);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(red, green, blue, alpha);
      worldrenderer.func_181668_a(9, DefaultVertexFormats.field_181705_e);
      double degree = (Math.PI / 180D);

      for(double i = (double)0.0F; i <= (double)90.0F; ++i) {
         worldrenderer.func_181662_b(x2 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius, (double)0.0F).func_181675_d();
      }

      for(double i = (double)90.0F; i <= (double)180.0F; ++i) {
         worldrenderer.func_181662_b(x2 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius, (double)0.0F).func_181675_d();
      }

      for(double i = (double)180.0F; i <= (double)270.0F; ++i) {
         worldrenderer.func_181662_b(x1 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius, (double)0.0F).func_181675_d();
      }

      for(double i = (double)270.0F; i <= (double)360.0F; ++i) {
         worldrenderer.func_181662_b(x1 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius, (double)0.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
      int sections = 50;
      double dAngle = (Math.PI * 2D) / (double)sections;
      GL11.glPushAttrib(8192);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glBegin(6);

      for(int i = 0; i < sections; ++i) {
         float x = (float)((double)radius * Math.sin((double)i * dAngle));
         float y = (float)((double)radius * Math.cos((double)i * dAngle));
         GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
         GL11.glVertex2f((float)xx + x, (float)yy + y);
      }

      GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
      GL11.glEnd();
      GL11.glPopAttrib();
   }

   public static void drawFilledCircle(float xx, float yy, float radius, Color color) {
      int sections = 50;
      double dAngle = (Math.PI * 2D) / (double)sections;
      GL11.glPushAttrib(8192);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glBegin(6);

      for(int i = 0; i < sections; ++i) {
         float x = (float)((double)radius * Math.sin((double)i * dAngle));
         float y = (float)((double)radius * Math.cos((double)i * dAngle));
         GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
         GL11.glVertex2f(xx + x, yy + y);
      }

      GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
      GL11.glEnd();
      GL11.glPopAttrib();
   }

   public static void drawLine(double x, double y, double x1, double y1, float width) {
      GL11.glDisable(3553);
      GL11.glLineWidth(width);
      GL11.glBegin(1);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y1);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public static void startDrawing() {
      GL11.glEnable(3042);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      Minecraft.func_71410_x().field_71460_t.func_78479_a(Minecraft.func_71410_x().field_71428_T.field_74281_c, 0);
   }

   public static void stopDrawing() {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
      drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color, true);
   }

   public static void drawBloomRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, float radius2, float shadow, Color color, ShaderBloom bloom) {
      Color color1 = color;
      if (shadow > 8.0F) {
         shadow = 8.0F;
      }

      if (bloom == RenderUtils.ShaderBloom.BLOOMONLY && color.getAlpha() == 255 && !(Boolean)Interface.INSTANCE.getBloomValue().get()) {
         color1 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
      }

      if ((Boolean)Interface.INSTANCE.getShaderValue().get() && (Boolean)Interface.INSTANCE.getBloomValue().get()) {
         switch (bloom) {
            case BLOOMONLY:
               ShaderUtil.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius2, shadow, color);
               break;
            case ROUNDONLY:
               drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color.getRGB(), true);
               break;
            case BOTH:
               ShaderUtil.drawRoundedRect(paramXStart - shadow, paramYStart - shadow, paramXEnd + shadow, paramYEnd + shadow, radius2, shadow, color1);
               drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
         }

      } else {
         drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
      }
   }

   public static void drawRoundedLimitArea(float posX, float posY, float posXEnd, float posYEnd, float rounded, Runnable triggerMethod) {
      Stencil.write(false);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GlStateManager.func_179094_E();
      fastRoundedRect(posX, posY, posXEnd, posYEnd, rounded);
      GlStateManager.func_179121_F();
      GlStateManager.func_179117_G();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      Stencil.erase(true);
      triggerMethod.run();
      Stencil.dispose();
   }

   public static void drawBloomGradientRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, float shadow, Color colorTop, Color colorBottom, ShaderBloom bloom) {
      Color color1 = colorTop;
      Color color2 = colorBottom;
      GlStateManager.func_179094_E();
      if (bloom == RenderUtils.ShaderBloom.BLOOMONLY && colorTop.getAlpha() == 255 && !(Boolean)Interface.INSTANCE.getBloomValue().get()) {
         color1 = new Color(colorTop.getRed(), colorTop.getGreen(), colorTop.getBlue(), 127);
      }

      if (bloom == RenderUtils.ShaderBloom.BLOOMONLY && colorBottom.getAlpha() == 255 && !(Boolean)Interface.INSTANCE.getBloomValue().get()) {
         color2 = new Color(colorBottom.getRed(), colorBottom.getGreen(), colorBottom.getBlue(), 127);
      }

      if ((Boolean)Interface.INSTANCE.getShaderValue().get() && (Boolean)Interface.INSTANCE.getBloomValue().get()) {
         if (shadow > 8.0F) {
            shadow = 8.0F;
         }

         if ((Boolean)Interface.INSTANCE.getShaderValue().get() && (Boolean)Interface.INSTANCE.getBloomValue().get()) {
            switch (bloom) {
               case BLOOMONLY:
                  ShaderUtil.drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, shadow, colorTop, colorBottom);
                  break;
               case ROUNDONLY:
                  drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, (int)radius, color1.getRGB(), color2.getRGB());
                  break;
               case BOTH:
                  ShaderUtil.drawGradientRoundedRect(paramXStart - shadow, paramYStart - shadow, paramXEnd + shadow, paramYEnd + shadow, radius, shadow, color1, color2);
                  drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, (int)radius, color1.getRGB(), color2.getRGB());
            }

            GlStateManager.func_179121_F();
            GlStateManager.func_179117_G();
         } else {
            drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, (int)radius, color1.getRGB(), color2.getRGB());
         }
      } else {
         drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, (int)radius, color1.getRGB(), color2.getRGB());
      }
   }

   public static void drawBloomRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, float shadow, Color color, ShaderBloom bloom) {
      Color color1 = color;
      if (shadow > 8.0F) {
         shadow = 8.0F;
      }

      if (bloom == RenderUtils.ShaderBloom.BLOOMONLY && color.getAlpha() == 255 && !(Boolean)Interface.INSTANCE.getBloomValue().get()) {
         color1 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
      }

      if ((Boolean)Interface.INSTANCE.getShaderValue().get() && (Boolean)Interface.INSTANCE.getBloomValue().get()) {
         switch (bloom) {
            case BLOOMONLY:
               ShaderUtil.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, shadow, color1);
               break;
            case ROUNDONLY:
               drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
               break;
            case BOTH:
               ShaderUtil.drawRoundedRect(paramXStart - shadow, paramYStart - shadow, paramXEnd + shadow, paramYEnd + shadow, radius, shadow, color1);
               drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
         }

      } else {
         drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
      }
   }

   public static void drawEntityOnScreen(double posX, double posY, float scale, EntityLivingBase entity) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179142_g();
      GlStateManager.func_179137_b(posX, posY, (double)50.0F);
      GlStateManager.func_179152_a(-scale, scale, scale);
      GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GlStateManager.func_179114_b(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179137_b((double)0.0F, (double)0.0F, (double)0.0F);
      RenderManager rendermanager = mc.func_175598_ae();
      rendermanager.func_178631_a(180.0F);
      rendermanager.func_178633_a(false);
      rendermanager.func_147940_a(entity, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 1.0F);
      rendermanager.func_178633_a(true);
      GlStateManager.func_179121_F();
      RenderHelper.func_74518_a();
      GlStateManager.func_179101_C();
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179090_x();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }

   public static void drawEntityOnScreen(int posX, int posY, int scale, EntityLivingBase entity) {
      drawEntityOnScreen((double)posX, (double)posY, (float)scale, entity);
   }

   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
      float f = 1.0F / tileWidth;
      float f1 = 1.0F / tileHeight;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      worldrenderer.func_181662_b((double)x, (double)(y + height), (double)0.0F).func_181673_a((double)(u * f), (double)((v + (float)vHeight) * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)(x + width), (double)(y + height), (double)0.0F).func_181673_a((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)(x + width), (double)y, (double)0.0F).func_181673_a((double)((u + (float)uWidth) * f), (double)(v * f1)).func_181675_d();
      worldrenderer.func_181662_b((double)x, (double)y, (double)0.0F).func_181673_a((double)(u * f), (double)(v * f1)).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawScaledCustomSizeModalCircle(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
      float f = 1.0F / tileWidth;
      float f1 = 1.0F / tileHeight;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(9, DefaultVertexFormats.field_181707_g);
      float xRadius = (float)width / 2.0F;
      float yRadius = (float)height / 2.0F;
      float uRadius = ((u + (float)uWidth) * f - u * f) / 2.0F;
      float vRadius = ((v + (float)vHeight) * f1 - v * f1) / 2.0F;

      for(int i = 0; i <= 360; i += 10) {
         double xPosOffset = Math.sin((double)i * Math.PI / (double)180.0F);
         double yPosOffset = Math.cos((double)i * Math.PI / (double)180.0F);
         worldrenderer.func_181662_b((double)((float)x + xRadius) + xPosOffset * (double)xRadius, (double)((float)y + yRadius) + yPosOffset * (double)yRadius, (double)0.0F).func_181673_a((double)(u * f + uRadius) + xPosOffset * (double)uRadius, (double)(v * f1 + vRadius) + yPosOffset * (double)vRadius).func_181675_d();
      }

      tessellator.func_78381_a();
   }

   public static void drawHead(ResourceLocation skin, int x, int y, int width, int height, int color) {
      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      GlStateManager.func_179131_c(f, f1, f2, f3);
      mc.func_110434_K().func_110577_a(skin);
      drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
      drawScaledCustomSizeModalRect(x, y, 40.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
   }

   public static void quickDrawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
      quickDrawRect(x, y, x2, y2, color2);
      glColor(color1);
      GL11.glLineWidth(width);
      GL11.glBegin(2);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glEnd();
   }

   public static void quickDrawHead(ResourceLocation skin, int x, int y, int width, int height) {
      mc.func_110434_K().func_110577_a(skin);
      drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
      drawScaledCustomSizeModalRect(x, y, 40.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
   }

   public static void drawBorder(float x, float y, float x2, float y2, float width, int color1) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      glColor(color1);
      GL11.glLineWidth(width);
      GL11.glBegin(2);
      GL11.glVertex2d((double)(x2 + 1.0F), (double)(y - 1.0F));
      GL11.glVertex2d((double)(x - 1.0F), (double)(y - 1.0F));
      GL11.glVertex2d((double)(x - 1.0F), (double)(y2 + 1.0F));
      GL11.glVertex2d((double)(x2 + 1.0F), (double)(y2 + 1.0F));
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawOutLineRect(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
      drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x + width, y, x1 - width, y + width, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x, y, x + width, y1, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x1 - width, y, x1, y1, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void enableSmoothLine(float width) {
      GL11.glDisable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glEnable(2884);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glLineWidth(width);
   }

   public static void disableSmoothLine() {
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glDepthMask(true);
      GL11.glCullFace(1029);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void directDrawAWTShape(Shape shape, double epsilon) {
      drawAWTShape(shape, epsilon, DirectTessCallback.INSTANCE);
   }

   public static void startSmooth() {
      GL11.glEnable(2848);
      GL11.glEnable(2881);
      GL11.glEnable(2832);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glHint(3153, 4354);
   }

   public static void endSmooth() {
      GL11.glDisable(2848);
      GL11.glDisable(2881);
      GL11.glEnable(2832);
   }

   public static void drawAWTShape(Shape shape, double epsilon, GLUtessellatorCallbackAdapter gluTessCallback) {
      PathIterator path = shape.getPathIterator(new AffineTransform());
      Double[] cp = new Double[2];
      GLUtessellator tess = GLU.gluNewTess();
      tess.gluTessCallback(100100, gluTessCallback);
      tess.gluTessCallback(100102, gluTessCallback);
      tess.gluTessCallback(100101, gluTessCallback);
      tess.gluTessCallback(100105, gluTessCallback);
      switch (path.getWindingRule()) {
         case 0:
            tess.gluTessProperty(100140, (double)100130.0F);
            break;
         case 1:
            tess.gluTessProperty(100140, (double)100131.0F);
      }

      ArrayList<Double[]> pointsCache = new ArrayList();
      tess.gluTessBeginPolygon((Object)null);

      for(; !path.isDone(); path.next()) {
         double[] segment = new double[6];
         int type = path.currentSegment(segment);
         switch (type) {
            case 0:
               tess.gluTessBeginContour();
               pointsCache.add(new Double[]{segment[0], segment[1]});
               cp[0] = segment[0];
               cp[1] = segment[1];
               break;
            case 1:
               pointsCache.add(new Double[]{segment[0], segment[1]});
               cp[0] = segment[0];
               cp[1] = segment[1];
               break;
            case 2:
               Double[][] points = MathUtils.getPointsOnCurve(new Double[][]{{cp[0], cp[1]}, {segment[0], segment[1]}, {segment[2], segment[3]}}, 10);
               pointsCache.addAll(Arrays.asList(points));
               cp[0] = segment[2];
               cp[1] = segment[3];
               break;
            case 3:
               Double[][] points = MathUtils.getPointsOnCurve(new Double[][]{{cp[0], cp[1]}, {segment[0], segment[1]}, {segment[2], segment[3]}, {segment[4], segment[5]}}, 10);
               pointsCache.addAll(Arrays.asList(points));
               cp[0] = segment[4];
               cp[1] = segment[5];
               break;
            case 4:
               for(Double[] point : MathUtils.simplifyPoints((Double[][])pointsCache.toArray(new Double[0][0]), epsilon)) {
                  tessVertex(tess, new double[]{point[0], point[1], (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F});
               }

               pointsCache.clear();
               tess.gluTessEndContour();
         }
      }

      tess.gluEndPolygon();
      tess.gluDeleteTess();
   }

   public static void drawNewRect(double left, double top, double right, double bottom, int color) {
      if (left < right) {
         double i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         double j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer vertexbuffer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 0, 1);
      GlStateManager.func_179131_c(f, f1, f2, f3);
      vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      vertexbuffer.func_181662_b(left, bottom, (double)0.0F).func_181675_d();
      vertexbuffer.func_181662_b(right, bottom, (double)0.0F).func_181675_d();
      vertexbuffer.func_181662_b(right, top, (double)0.0F).func_181675_d();
      vertexbuffer.func_181662_b(left, top, (double)0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawCheckeredBackground(float x, float y, float x2, float y2) {
      drawRect(x, y, x2, y2, getColor(16777215));

      for(boolean offset = false; y < y2; ++y) {
         for(float x3 = x + (float)((offset = !offset) ? 1 : 0); x3 < x2; x3 += 2.0F) {
            if (x3 <= x2 - 1.0F) {
               drawRect(x3, y, x3 + 1.0F, y + 1.0F, getColor(8421504));
            }
         }
      }

   }

   public static int getColor(int color) {
      int r = color >> 16 & 255;
      int g = color >> 8 & 255;
      int b = color & 255;
      int a = 255;
      return (r & 255) << 16 | (g & 255) << 8 | b & 255 | -16777216;
   }

   public static void scissor(double x, double y, double width, double height) {
      int scaleFactor;
      for(scaleFactor = (new ScaledResolution(Minecraft.func_71410_x())).func_78325_e(); scaleFactor < 2 && Minecraft.func_71410_x().field_71443_c / (scaleFactor + 1) >= 320 && Minecraft.func_71410_x().field_71440_d / (scaleFactor + 1) >= 240; ++scaleFactor) {
      }

      GL11.glScissor((int)(x * (double)scaleFactor), (int)((double)Minecraft.func_71410_x().field_71440_d - (y + height) * (double)scaleFactor), (int)(width * (double)scaleFactor), (int)(height * (double)scaleFactor));
   }

   public static void drawRoundedRectd(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
      drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color, true);
   }

   public static void drawGradientRect(double d, double e, double e2, double g, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f2 = (float)(startColor >> 16 & 255) / 255.0F;
      float f3 = (float)(startColor >> 8 & 255) / 255.0F;
      float f4 = (float)(startColor & 255) / 255.0F;
      float f5 = (float)(endColor >> 24 & 255) / 255.0F;
      float f6 = (float)(endColor >> 16 & 255) / 255.0F;
      float f7 = (float)(endColor >> 8 & 255) / 255.0F;
      float f8 = (float)(endColor & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 0, 1);
      GlStateManager.func_179103_j(7425);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b(e2, e, (double)zLevel).func_181666_a(f2, f3, f4, f).func_181675_d();
      bufferbuilder.func_181662_b(d, e, (double)zLevel).func_181666_a(f2, f3, f4, f).func_181675_d();
      bufferbuilder.func_181662_b(d, g, (double)zLevel).func_181666_a(f6, f7, f8, f5).func_181675_d();
      bufferbuilder.func_181662_b(e2, g, (double)zLevel).func_181666_a(f6, f7, f8, f5).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }

   public static void render(int mode, Runnable render) {
      GL11.glBegin(mode);
      render.run();
      GL11.glEnd();
   }

   public static void setup2DRendering(Runnable f) {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      f.run();
      GL11.glEnable(3553);
      GlStateManager.func_179084_k();
   }

   public static void tessVertex(GLUtessellator tessellator, double[] coords) {
      tessellator.gluTessVertex(coords, 0, new VertexData(coords));
   }

   public static boolean inArea(float x, float y, float ax1, float ay1, float ax2, float ay2) {
      return x >= ax1 && x <= ax2 && y >= ay1 && y <= ay2;
   }

   public static boolean inArea(int x, int y, int ax1, int ay1, int ax2, int ay2) {
      return x >= ax1 && x <= ax2 && y >= ay1 && y <= ay2;
   }

   public static int loadGlTexture(BufferedImage bufferedImage) {
      int textureId = GL11.glGenTextures();
      GL11.glBindTexture(3553, textureId);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexImage2D(3553, 0, 6408, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, 6408, 5121, ImageUtils.readImageToBuffer(bufferedImage));
      GL11.glBindTexture(3553, 0);
      return textureId;
   }

   public static void drawModel(float yaw, float pitch, EntityLivingBase entityLivingBase) {
      GlStateManager.func_179117_G();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179142_g();
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 0.0F, 50.0F);
      GlStateManager.func_179152_a(-50.0F, 50.0F, 50.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      float renderYawOffset = entityLivingBase.field_70761_aq;
      float rotationYaw = entityLivingBase.field_70177_z;
      float rotationPitch = entityLivingBase.field_70125_A;
      float prevRotationYawHead = entityLivingBase.field_70758_at;
      float rotationYawHead = entityLivingBase.field_70759_as;
      GlStateManager.func_179114_b(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GlStateManager.func_179114_b(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(-Math.atan((double)(pitch / 40.0F)) * (double)20.0F), 1.0F, 0.0F, 0.0F);
      entityLivingBase.field_70761_aq = yaw - yaw / yaw * 0.4F;
      entityLivingBase.field_70177_z = yaw - yaw / yaw * 0.2F;
      entityLivingBase.field_70125_A = pitch;
      entityLivingBase.field_70759_as = entityLivingBase.field_70177_z;
      entityLivingBase.field_70758_at = entityLivingBase.field_70177_z;
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
      RenderManager renderManager = mc.func_175598_ae();
      renderManager.func_178631_a(180.0F);
      renderManager.func_178633_a(false);
      entityLivingBase.func_174833_aM();
      renderManager.func_147940_a(entityLivingBase, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 1.0F);
      renderManager.func_178633_a(true);
      entityLivingBase.field_70761_aq = renderYawOffset;
      entityLivingBase.field_70177_z = rotationYaw;
      entityLivingBase.field_70125_A = rotationPitch;
      entityLivingBase.field_70758_at = prevRotationYawHead;
      entityLivingBase.field_70759_as = rotationYawHead;
      GlStateManager.func_179121_F();
      RenderHelper.func_74518_a();
      GlStateManager.func_179101_C();
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179090_x();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      GlStateManager.func_179117_G();
   }

   public static void drawOutlinedStringCock(FontRenderer fr, String s, float x, float y, int color, int outlineColor) {
      fr.func_78276_b(ColorUtils.stripColor(s), (int)(x - 1.0F), (int)y, outlineColor);
      fr.func_78276_b(ColorUtils.stripColor(s), (int)x, (int)(y - 1.0F), outlineColor);
      fr.func_78276_b(ColorUtils.stripColor(s), (int)(x + 1.0F), (int)y, outlineColor);
      fr.func_78276_b(ColorUtils.stripColor(s), (int)x, (int)(y + 1.0F), outlineColor);
      fr.func_78276_b(s, (int)x, (int)y, color);
   }

   public static void drawSmoothRoundedRect(float x, float y, float x1, float y1, float radius, Color colour) {
      GL11.glPushAttrib(0);
      GL11.glScaled((double)0.5F, (double)0.5F, (double)0.5F);
      x = (float)((double)x * (double)2.0F);
      y = (float)((double)y * (double)2.0F);
      x1 = (float)((double)x1 * (double)2.0F);
      y1 = (float)((double)y1 * (double)2.0F);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      ColorUtils.setColour(colour.getRGB());
      GL11.glEnable(2848);
      GL11.glBegin(9);

      for(int i = 0; i <= 90; i += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y + radius) + Math.cos((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      for(int var11 = 90; var11 <= 180; var11 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var11 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y1 - radius) + Math.cos((double)var11 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      for(int var12 = 0; var12 <= 90; var12 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var12 * Math.PI / (double)180.0F) * (double)radius, (double)(y1 - radius) + Math.cos((double)var12 * Math.PI / (double)180.0F) * (double)radius);
      }

      for(int var13 = 90; var13 <= 180; var13 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var13 * Math.PI / (double)180.0F) * (double)radius, (double)(y + radius) + Math.cos((double)var13 * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glBegin(2);

      for(int var14 = 0; var14 <= 90; var14 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var14 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y + radius) + Math.cos((double)var14 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      for(int var15 = 90; var15 <= 180; var15 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var15 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y1 - radius) + Math.cos((double)var15 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      for(int var16 = 0; var16 <= 90; var16 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var16 * Math.PI / (double)180.0F) * (double)radius, (double)(y1 - radius) + Math.cos((double)var16 * Math.PI / (double)180.0F) * (double)radius);
      }

      for(int var17 = 90; var17 <= 180; var17 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var17 * Math.PI / (double)180.0F) * (double)radius, (double)(y + radius) + Math.cos((double)var17 * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glScaled((double)2.0F, (double)2.0F, (double)2.0F);
      GL11.glPopAttrib();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawRoundedOutline(float x, float y, float x1, float y1, float radius, float lineWidth, int colour) {
      GL11.glPushAttrib(0);
      GL11.glScaled((double)0.5F, (double)0.5F, (double)0.5F);
      x = (float)((double)x * (double)2.0F);
      y = (float)((double)y * (double)2.0F);
      x1 = (float)((double)x1 * (double)2.0F);
      y1 = (float)((double)y1 * (double)2.0F);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      ColorUtils.setColour(colour);
      GL11.glEnable(2848);
      GL11.glLineWidth(lineWidth);
      GL11.glBegin(2);

      for(int i = 0; i <= 90; i += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y + radius) + Math.cos((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      for(int var12 = 90; var12 <= 180; var12 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var12 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y1 - radius) + Math.cos((double)var12 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      for(int var13 = 0; var13 <= 90; var13 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var13 * Math.PI / (double)180.0F) * (double)radius, (double)(y1 - radius) + Math.cos((double)var13 * Math.PI / (double)180.0F) * (double)radius);
      }

      for(int var14 = 90; var14 <= 180; var14 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var14 * Math.PI / (double)180.0F) * (double)radius, (double)(y + radius) + Math.cos((double)var14 * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glScaled((double)2.0F, (double)2.0F, (double)2.0F);
      GL11.glPopAttrib();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawRoundedGradientRectCorner(float x, float y, float x1, float y1, float radius, int color, int color2, int color3, int color4) {
      ColorUtils.setColour(-1);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushAttrib(0);
      GL11.glScaled((double)0.5F, (double)0.5F, (double)0.5F);
      x = (float)((double)x * (double)2.0F);
      y = (float)((double)y * (double)2.0F);
      x1 = (float)((double)x1 * (double)2.0F);
      y1 = (float)((double)y1 * (double)2.0F);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      ColorUtils.setColour(color);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glBegin(6);

      for(int i = 0; i <= 90; i += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y + radius) + Math.cos((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color2);

      for(int var14 = 90; var14 <= 180; var14 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var14 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y1 - radius) + Math.cos((double)var14 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color3);

      for(int var15 = 0; var15 <= 90; var15 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var15 * Math.PI / (double)180.0F) * (double)radius, (double)(y1 - radius) + Math.cos((double)var15 * Math.PI / (double)180.0F) * (double)radius);
      }

      ColorUtils.setColour(color4);

      for(int var16 = 90; var16 <= 180; var16 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var16 * Math.PI / (double)180.0F) * (double)radius, (double)(y + radius) + Math.cos((double)var16 * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glScaled((double)2.0F, (double)2.0F, (double)2.0F);
      GL11.glPopAttrib();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
      ColorUtils.setColour(-1);
   }

   public static void drawRoundedGradientRectCorner(float x, float y, float x1, float y1, float radius, int color, int color2) {
      ColorUtils.setColour(-1);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushAttrib(0);
      GL11.glScaled((double)0.5F, (double)0.5F, (double)0.5F);
      x = (float)((double)x * (double)2.0F);
      y = (float)((double)y * (double)2.0F);
      x1 = (float)((double)x1 * (double)2.0F);
      y1 = (float)((double)y1 * (double)2.0F);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      ColorUtils.setColour(color);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glBegin(6);

      for(int i = 0; i <= 90; i += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y + radius) + Math.cos((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color);

      for(int var12 = 90; var12 <= 180; var12 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var12 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y1 - radius) + Math.cos((double)var12 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color2);

      for(int var13 = 0; var13 <= 90; var13 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var13 * Math.PI / (double)180.0F) * (double)radius, (double)(y1 - radius) + Math.cos((double)var13 * Math.PI / (double)180.0F) * (double)radius);
      }

      ColorUtils.setColour(color2);

      for(int var14 = 90; var14 <= 180; var14 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var14 * Math.PI / (double)180.0F) * (double)radius, (double)(y + radius) + Math.cos((double)var14 * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glScaled((double)2.0F, (double)2.0F, (double)2.0F);
      GL11.glPopAttrib();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
      ColorUtils.setColour(-1);
   }

   public static void drawRoundedGradientOutlineCorner(float x, float y, float x1, float y1, float width, float radius, int color, int color2, int color3, int color4) {
      ColorUtils.setColour(-1);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushAttrib(0);
      GL11.glScaled((double)0.5F, (double)0.5F, (double)0.5F);
      x = (float)((double)x * (double)2.0F);
      y = (float)((double)y * (double)2.0F);
      x1 = (float)((double)x1 * (double)2.0F);
      y1 = (float)((double)y1 * (double)2.0F);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      ColorUtils.setColour(color);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glLineWidth(width);
      GL11.glBegin(2);

      for(int i = 0; i <= 90; i += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y + radius) + Math.cos((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color2);

      for(int var15 = 90; var15 <= 180; var15 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var15 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y1 - radius) + Math.cos((double)var15 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color3);

      for(int var16 = 0; var16 <= 90; var16 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var16 * Math.PI / (double)180.0F) * (double)radius, (double)(y1 - radius) + Math.cos((double)var16 * Math.PI / (double)180.0F) * (double)radius);
      }

      ColorUtils.setColour(color4);

      for(int var17 = 90; var17 <= 180; var17 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var17 * Math.PI / (double)180.0F) * (double)radius, (double)(y + radius) + Math.cos((double)var17 * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glScaled((double)2.0F, (double)2.0F, (double)2.0F);
      GL11.glPopAttrib();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
      ColorUtils.setColour(-1);
   }

   public static void drawRoundedGradientOutlineCorner(float x, float y, float x1, float y1, float width, float radius, int color, int color2) {
      ColorUtils.setColour(-1);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushAttrib(0);
      GL11.glScaled((double)0.5F, (double)0.5F, (double)0.5F);
      x *= 2.0F;
      y *= 2.0F;
      x1 *= 2.0F;
      y1 *= 2.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      ColorUtils.setColour(color);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glLineWidth(width);
      GL11.glBegin(2);

      for(int i = 0; i <= 90; i += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y + radius) + Math.cos((double)i * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color);

      for(int var13 = 90; var13 <= 180; var13 += 3) {
         GL11.glVertex2d((double)(x + radius) + Math.sin((double)var13 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F, (double)(y1 - radius) + Math.cos((double)var13 * Math.PI / (double)180.0F) * (double)radius * (double)-1.0F);
      }

      ColorUtils.setColour(color2);

      for(int var14 = 0; var14 <= 90; var14 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var14 * Math.PI / (double)180.0F) * (double)radius, (double)(y1 - radius) + Math.cos((double)var14 * Math.PI / (double)180.0F) * (double)radius);
      }

      ColorUtils.setColour(color2);

      for(int var15 = 90; var15 <= 180; var15 += 3) {
         GL11.glVertex2d((double)(x1 - radius) + Math.sin((double)var15 * Math.PI / (double)180.0F) * (double)radius, (double)(y + radius) + Math.cos((double)var15 * Math.PI / (double)180.0F) * (double)radius);
      }

      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glScaled((double)2.0F, (double)2.0F, (double)2.0F);
      GL11.glPopAttrib();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
      ColorUtils.setColour(-1);
   }

   public static void otherDrawOutlinedBoundingBoundingBox(float yaw, double x, double y, double z, double width, double height) {
      width *= (double)1.5F;
      yaw = MathHelper.func_76142_g(yaw) + 45.0F;
      float yaw1;
      if (yaw < 0.0F) {
         yaw1 = 0.0F;
         yaw1 += 360.0F - Math.abs(yaw);
      } else {
         yaw1 = yaw;
      }

      yaw1 *= -1.0F;
      yaw1 = (float)((double)yaw1 * (Math.PI / 180D));
      yaw += 90.0F;
      float yaw2;
      if (yaw < 0.0F) {
         yaw2 = 0.0F;
         yaw2 += 360.0F - Math.abs(yaw);
      } else {
         yaw2 = yaw;
      }

      yaw2 *= -1.0F;
      yaw2 = (float)((double)yaw2 * (Math.PI / 180D));
      yaw += 90.0F;
      float yaw3;
      if (yaw < 0.0F) {
         yaw3 = 0.0F;
         yaw3 += 360.0F - Math.abs(yaw);
      } else {
         yaw3 = yaw;
      }

      yaw3 *= -1.0F;
      yaw3 = (float)((double)yaw3 * (Math.PI / 180D));
      yaw += 90.0F;
      float yaw4;
      if (yaw < 0.0F) {
         yaw4 = 0.0F;
         yaw4 += 360.0F - Math.abs(yaw);
      } else {
         yaw4 = yaw;
      }

      yaw4 *= -1.0F;
      yaw4 = (float)((double)yaw4 * (Math.PI / 180D));
      float x1 = (float)(Math.sin((double)yaw1) * width + x);
      float z1 = (float)(Math.cos((double)yaw1) * width + z);
      float x2 = (float)(Math.sin((double)yaw2) * width + x);
      float z2 = (float)(Math.cos((double)yaw2) * width + z);
      float x3 = (float)(Math.sin((double)yaw3) * width + x);
      float z3 = (float)(Math.cos((double)yaw3) * width + z);
      float x4 = (float)(Math.sin((double)yaw4) * width + x);
      float z4 = (float)(Math.cos((double)yaw4) * width + z);
      float y2 = (float)(y + height);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)x1, y, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x2, y, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x1, y, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x4, y, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x3, y, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x4, y, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x2, y, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x3, y, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x4, y, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x1, y, (double)z1).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void otherDrawBoundingBox(float yaw, double x, double y, double z, double width, double height) {
      width *= (double)1.5F;
      yaw = MathHelper.func_76142_g(yaw) + 45.0F;
      float yaw1;
      if (yaw < 0.0F) {
         yaw1 = 0.0F;
         yaw1 += 360.0F - Math.abs(yaw);
      } else {
         yaw1 = yaw;
      }

      yaw1 *= -1.0F;
      yaw1 = (float)((double)yaw1 * (Math.PI / 180D));
      yaw += 90.0F;
      float yaw2;
      if (yaw < 0.0F) {
         yaw2 = 0.0F;
         yaw2 += 360.0F - Math.abs(yaw);
      } else {
         yaw2 = yaw;
      }

      yaw2 *= -1.0F;
      yaw2 = (float)((double)yaw2 * (Math.PI / 180D));
      yaw += 90.0F;
      float yaw3;
      if (yaw < 0.0F) {
         yaw3 = 0.0F;
         yaw3 += 360.0F - Math.abs(yaw);
      } else {
         yaw3 = yaw;
      }

      yaw3 *= -1.0F;
      yaw3 = (float)((double)yaw3 * (Math.PI / 180D));
      yaw += 90.0F;
      float yaw4;
      if (yaw < 0.0F) {
         yaw4 = 0.0F;
         yaw4 += 360.0F - Math.abs(yaw);
      } else {
         yaw4 = yaw;
      }

      yaw4 *= -1.0F;
      yaw4 = (float)((double)yaw4 * (Math.PI / 180D));
      float x1 = (float)(Math.sin((double)yaw1) * width + x);
      float z1 = (float)(Math.cos((double)yaw1) * width + z);
      float x2 = (float)(Math.sin((double)yaw2) * width + x);
      float z2 = (float)(Math.cos((double)yaw2) * width + z);
      float x3 = (float)(Math.sin((double)yaw3) * width + x);
      float z3 = (float)(Math.cos((double)yaw3) * width + z);
      float x4 = (float)(Math.sin((double)yaw4) * width + x);
      float z4 = (float)(Math.cos((double)yaw4) * width + z);
      float y1 = (float)y;
      float y2 = (float)(y + height);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)x1, (double)y1, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y1, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y1, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y1, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y1, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y1, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y1, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x1, (double)y1, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x1, (double)y1, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y1, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y1, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y1, (double)z4).func_181675_d();
      worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
      worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
      worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
      worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
      tessellator.func_78381_a();
   }

   private static void drawEnchantTag(String text, int x, float y) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179097_i();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      drawOutlinedStringCock(Minecraft.func_71410_x().field_71466_p, text, (float)x, y, -1, (new Color(0, 0, 0, 220)).darker().getRGB());
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GlStateManager.func_179126_j();
      GlStateManager.func_179121_F();
   }

   public static void renderEnchantText(ItemStack stack, int x, float y) {
      RenderHelper.func_74518_a();
      float enchantmentY = y + 24.0F;
      if (stack.func_77973_b() instanceof ItemArmor) {
         int protectionLevel = EnchantmentHelper.func_77506_a(Enchantment.field_180310_c.field_77352_x, stack);
         int unbreakingLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
         int thornLevel = EnchantmentHelper.func_77506_a(Enchantment.field_92091_k.field_77352_x, stack);
         if (protectionLevel > 0) {
            drawEnchantTag("P" + ColorUtils.getColor(protectionLevel) + protectionLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (unbreakingLevel > 0) {
            drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (thornLevel > 0) {
            drawEnchantTag("T" + ColorUtils.getColor(thornLevel) + thornLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }
      }

      if (stack.func_77973_b() instanceof ItemBow) {
         int powerLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, stack);
         int punchLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77344_u.field_77352_x, stack);
         int flameLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, stack);
         int unbreakingLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
         if (powerLevel > 0) {
            drawEnchantTag("Pow" + ColorUtils.getColor(powerLevel) + powerLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (punchLevel > 0) {
            drawEnchantTag("Pun" + ColorUtils.getColor(punchLevel) + punchLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (flameLevel > 0) {
            drawEnchantTag("F" + ColorUtils.getColor(flameLevel) + flameLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (unbreakingLevel > 0) {
            drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }
      }

      if (stack.func_77973_b() instanceof ItemSword) {
         int sharpnessLevel = EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, stack);
         int knockbackLevel = EnchantmentHelper.func_77506_a(Enchantment.field_180313_o.field_77352_x, stack);
         int fireAspectLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, stack);
         int unbreakingLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, stack);
         if (sharpnessLevel > 0) {
            drawEnchantTag("S" + ColorUtils.getColor(sharpnessLevel) + sharpnessLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (knockbackLevel > 0) {
            drawEnchantTag("K" + ColorUtils.getColor(knockbackLevel) + knockbackLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (fireAspectLevel > 0) {
            drawEnchantTag("F" + ColorUtils.getColor(fireAspectLevel) + fireAspectLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }

         if (unbreakingLevel > 0) {
            drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x * 2, enchantmentY);
            enchantmentY += 8.0F;
         }
      }

      if (stack.func_77953_t() == EnumRarity.EPIC) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179097_i();
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         drawOutlinedStringCock(Minecraft.func_71410_x().field_71466_p, "God", (float)(x * 2), enchantmentY, (new Color(255, 255, 0)).getRGB(), (new Color(100, 100, 0, 200)).getRGB());
         GL11.glScalef(1.0F, 1.0F, 1.0F);
         GlStateManager.func_179126_j();
         GlStateManager.func_179121_F();
      }

   }

   public static Vec3 getRenderPos(double x, double y, double z) {
      x -= mc.func_175598_ae().field_78725_b;
      y -= mc.func_175598_ae().field_78726_c;
      z -= mc.func_175598_ae().field_78723_d;
      return new Vec3(x, y, z);
   }

   public static void glVertex3D(Vec3 vector3d) {
      GL11.glVertex3d(vector3d.field_72450_a, vector3d.field_72448_b, vector3d.field_72449_c);
   }

   public static void drawBoundingBlock(AxisAlignedBB aa) {
      GL11.glBegin(7);
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
      end();
      GL11.glBegin(7);
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
      end();
      GL11.glBegin(7);
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
      end();
      GL11.glBegin(7);
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
      end();
      GL11.glBegin(7);
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
      end();
      GL11.glBegin(7);
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
      glVertex3D(getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
      end();
   }

   public static boolean SupportBloom() {
      return (Boolean)Interface.INSTANCE.getBloomValue().get();
   }

   static {
      for(int i = 0; i < DISPLAY_LISTS_2D.length; ++i) {
         DISPLAY_LISTS_2D[i] = GL11.glGenLists(1);
      }

      GL11.glNewList(DISPLAY_LISTS_2D[0], 4864);
      quickDrawRect(-7.0F, 2.0F, -4.0F, 3.0F);
      quickDrawRect(4.0F, 2.0F, 7.0F, 3.0F);
      quickDrawRect(-7.0F, 0.5F, -6.0F, 3.0F);
      quickDrawRect(6.0F, 0.5F, 7.0F, 3.0F);
      GL11.glEndList();
      GL11.glNewList(DISPLAY_LISTS_2D[1], 4864);
      quickDrawRect(-7.0F, 3.0F, -4.0F, 3.3F);
      quickDrawRect(4.0F, 3.0F, 7.0F, 3.3F);
      quickDrawRect(-7.3F, 0.5F, -7.0F, 3.3F);
      quickDrawRect(7.0F, 0.5F, 7.3F, 3.3F);
      GL11.glEndList();
      GL11.glNewList(DISPLAY_LISTS_2D[2], 4864);
      quickDrawRect(4.0F, -20.0F, 7.0F, -19.0F);
      quickDrawRect(-7.0F, -20.0F, -4.0F, -19.0F);
      quickDrawRect(6.0F, -20.0F, 7.0F, -17.5F);
      quickDrawRect(-7.0F, -20.0F, -6.0F, -17.5F);
      GL11.glEndList();
      GL11.glNewList(DISPLAY_LISTS_2D[3], 4864);
      quickDrawRect(7.0F, -20.0F, 7.3F, -17.5F);
      quickDrawRect(-7.3F, -20.0F, -7.0F, -17.5F);
      quickDrawRect(4.0F, -20.3F, 7.3F, -20.0F);
      quickDrawRect(-7.3F, -20.3F, -4.0F, -20.0F);
      GL11.glEndList();
      zLevel = 0.0F;
      frustrum = new Frustum();
   }

   public static enum ShaderBloom {
      BLOOMONLY,
      ROUNDONLY,
      BOTH;
   }
}
