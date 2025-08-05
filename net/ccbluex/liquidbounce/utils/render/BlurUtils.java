package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00150\u001eH\u0007J8\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0007JJ\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010+\u001a\u00020\u001cH\u0002J\b\u0010,\u001a\u00020\u0015H\u0002J\u001e\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020\b2\u0006\u0010)\u001a\u00020\b2\u0006\u0010*\u001a\u00020\bR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/BlurUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "framebuffer", "Lnet/minecraft/client/shader/Framebuffer;", "kotlin.jvm.PlatformType", "frbuffer", "lastFactor", "", "lastH", "", "lastHeight", "lastStrength", "lastW", "lastWeight", "lastWidth", "lastX", "lastY", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "blur", "", "posX", "posY", "posXEnd", "posYEnd", "blurStrength", "displayClipMask", "", "triggerMethod", "Lkotlin/Function0;", "blurAreaRounded", "x", "y", "x2", "y2", "rad", "setValues", "strength", "w", "h", "width", "height", "force", "setupFramebuffers", "sizeHasChanged", "scaleFactor", "CrossSine"}
)
public final class BlurUtils extends MinecraftInstance {
   @NotNull
   public static final BlurUtils INSTANCE = new BlurUtils();
   @NotNull
   private static final ShaderGroup shaderGroup;
   private static final Framebuffer framebuffer;
   private static final Framebuffer frbuffer;
   private static int lastFactor;
   private static int lastWidth;
   private static int lastHeight;
   private static int lastWeight;
   private static float lastX;
   private static float lastY;
   private static float lastW;
   private static float lastH;
   private static float lastStrength;

   private BlurUtils() {
   }

   private final void setupFramebuffers() {
      try {
         shaderGroup.func_148026_a(MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71440_d);
      } catch (Exception e) {
         ClientUtils.INSTANCE.logError("Exception caught while setting up shader group", (Throwable)e);
      }

   }

   private final void setValues(float strength, float x, float y, float w, float h, float width, float height, boolean force) {
      if (force || strength != lastStrength || lastX != x || lastY != y || lastW != w || lastH != h) {
         lastStrength = strength;
         lastX = x;
         lastY = y;
         lastW = w;
         lastH = h;
         int var9 = 0;

         while(var9 < 2) {
            int i = var9++;
            ((Shader)shaderGroup.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            ((Shader)shaderGroup.field_148031_d.get(i)).func_148043_c().func_147991_a("BlurXY").func_148087_a(x, height - y - h);
            ((Shader)shaderGroup.field_148031_d.get(i)).func_148043_c().func_147991_a("BlurCoord").func_148087_a(w, h);
         }

      }
   }

   // $FF: synthetic method
   static void setValues$default(BlurUtils var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, int var9, Object var10) {
      if ((var9 & 128) != 0) {
         var8 = false;
      }

      var0.setValues(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @JvmStatic
   public static final void blur(float posX, float posY, float posXEnd, float posYEnd, float blurStrength, boolean displayClipMask, @NotNull Function0 triggerMethod) {
      Intrinsics.checkNotNullParameter(triggerMethod, "triggerMethod");
      if (OpenGlHelper.func_148822_b()) {
         if ((Boolean)Interface.INSTANCE.getShaderValue().get() && (Boolean)Interface.INSTANCE.getBlurValue().get()) {
            float x = posX;
            float y = posY;
            float x2 = posXEnd;
            float y2 = posYEnd;
            if (posX > posXEnd) {
               x = posXEnd;
               x2 = posX;
            }

            if (posY > posYEnd) {
               y = posYEnd;
               y2 = posYEnd;
            }

            ScaledResolution sc = new ScaledResolution(MinecraftInstance.mc);
            int scaleFactor = sc.func_78325_e();
            int width = sc.func_78326_a();
            int height = sc.func_78328_b();
            if (INSTANCE.sizeHasChanged(scaleFactor, width, height)) {
               INSTANCE.setupFramebuffers();
               INSTANCE.setValues(blurStrength, x, y, x2 - x, y2 - y, (float)width, (float)height, true);
            }

            BlurUtils var10000 = INSTANCE;
            lastFactor = scaleFactor;
            var10000 = INSTANCE;
            lastWidth = width;
            var10000 = INSTANCE;
            lastHeight = height;
            setValues$default(INSTANCE, blurStrength, x, y, x2 - x, y2 - y, (float)width, (float)height, false, 128, (Object)null);
            framebuffer.func_147610_a(true);
            shaderGroup.func_148018_a(MinecraftInstance.mc.field_71428_T.field_74281_c);
            MinecraftInstance.mc.func_147110_a().func_147610_a(true);
            Stencil.write(displayClipMask);
            triggerMethod.invoke();
            Stencil.erase(true);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b(770, 771);
            GlStateManager.func_179094_E();
            GlStateManager.func_179135_a(true, true, true, false);
            GlStateManager.func_179097_i();
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179098_w();
            GlStateManager.func_179140_f();
            GlStateManager.func_179118_c();
            frbuffer.func_147612_c();
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            double f2 = (double)frbuffer.field_147621_c / (double)frbuffer.field_147622_a;
            double f3 = (double)frbuffer.field_147618_d / (double)frbuffer.field_147620_b;
            Tessellator tessellator = Tessellator.func_178181_a();
            WorldRenderer worldrenderer = tessellator.func_178180_c();
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            worldrenderer.func_181662_b((double)0.0F, (double)height, (double)0.0F).func_181673_a((double)0.0F, (double)0.0F).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)width, (double)height, (double)0.0F).func_181673_a(f2, (double)0.0F).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)width, (double)0.0F, (double)0.0F).func_181673_a(f2, f3).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)0.0F, (double)0.0F, (double)0.0F).func_181673_a((double)0.0F, f3).func_181669_b(255, 255, 255, 255).func_181675_d();
            tessellator.func_78381_a();
            frbuffer.func_147606_d();
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179135_a(true, true, true, true);
            GlStateManager.func_179121_F();
            GlStateManager.func_179084_k();
            Stencil.dispose();
            GlStateManager.func_179141_d();
         }
      }
   }

   @JvmStatic
   public static final void blurAreaRounded(final float x, final float y, final float x2, final float y2, final float rad, float blurStrength) {
      if ((Boolean)Interface.INSTANCE.getShaderValue().get() && (Boolean)Interface.INSTANCE.getBlurValue().get()) {
         BlurUtils var10000 = INSTANCE;
         blur(x, y, x2, y2, blurStrength, false, new Function0() {
            public final void invoke() {
               GlStateManager.func_179147_l();
               GlStateManager.func_179090_x();
               GlStateManager.func_179120_a(770, 771, 1, 0);
               RenderUtils.fastRoundedRect(x, y, x2, y2, rad);
               GlStateManager.func_179098_w();
               GlStateManager.func_179084_k();
            }
         });
      }
   }

   public final boolean sizeHasChanged(int scaleFactor, int width, int height) {
      return lastFactor != scaleFactor || lastWidth != width || lastHeight != height;
   }

   static {
      shaderGroup = new ShaderGroup(MinecraftInstance.mc.func_110434_K(), MinecraftInstance.mc.func_110442_L(), MinecraftInstance.mc.func_147110_a(), new ResourceLocation("shaders/post/blurArea.json"));
      framebuffer = shaderGroup.field_148035_a;
      frbuffer = shaderGroup.func_177066_a("result");
      lastStrength = 5.0F;
   }
}
