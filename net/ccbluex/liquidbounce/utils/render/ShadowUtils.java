package net.ccbluex.liquidbounce.utils.render;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
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
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000bJ*\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/ShadowUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "blurDirectory", "Lnet/minecraft/util/ResourceLocation;", "frameBuffer", "Lnet/minecraft/client/shader/Framebuffer;", "initFramebuffer", "lastHeight", "", "lastStrength", "", "lastWidth", "resultBuffer", "getResultBuffer", "()Lnet/minecraft/client/shader/Framebuffer;", "setResultBuffer", "(Lnet/minecraft/client/shader/Framebuffer;)V", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "initShaderIfRequired", "", "sc", "Lnet/minecraft/client/gui/ScaledResolution;", "strength", "shadow", "drawMethod", "Lkotlin/Function0;", "cutMethod", "CrossSine"}
)
public final class ShadowUtils extends MinecraftInstance {
   @NotNull
   public static final ShadowUtils INSTANCE = new ShadowUtils();
   @Nullable
   private static Framebuffer initFramebuffer;
   @Nullable
   private static Framebuffer frameBuffer;
   @Nullable
   private static Framebuffer resultBuffer;
   @Nullable
   private static ShaderGroup shaderGroup;
   private static int lastWidth;
   private static int lastHeight;
   private static float lastStrength;
   @NotNull
   private static final ResourceLocation blurDirectory = new ResourceLocation("crosssine/shadow.json");

   private ShadowUtils() {
   }

   @Nullable
   public final Framebuffer getResultBuffer() {
      return resultBuffer;
   }

   public final void setResultBuffer(@Nullable Framebuffer var1) {
      resultBuffer = var1;
   }

   public final void initShaderIfRequired(@NotNull ScaledResolution sc, float strength) throws IOException {
      Intrinsics.checkNotNullParameter(sc, "sc");
      int width = sc.func_78326_a();
      int height = sc.func_78328_b();
      int factor = sc.func_78325_e();
      if (lastWidth != width || lastHeight != height || initFramebuffer == null || frameBuffer == null || shaderGroup == null) {
         initFramebuffer = new Framebuffer(width * factor, height * factor, true);
         Framebuffer var10000 = initFramebuffer;
         Intrinsics.checkNotNull(var10000);
         var10000.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
         var10000 = initFramebuffer;
         Intrinsics.checkNotNull(var10000);
         var10000.func_147607_a(9729);
         shaderGroup = new ShaderGroup(MinecraftInstance.mc.func_110434_K(), MinecraftInstance.mc.func_110442_L(), initFramebuffer, blurDirectory);
         ShaderGroup var11 = shaderGroup;
         Intrinsics.checkNotNull(var11);
         var11.func_148026_a(width * factor, height * factor);
         var11 = shaderGroup;
         Intrinsics.checkNotNull(var11);
         frameBuffer = var11.field_148035_a;
         var11 = shaderGroup;
         Intrinsics.checkNotNull(var11);
         resultBuffer = var11.func_177066_a("braindead");
         lastWidth = width;
         lastHeight = height;
         lastStrength = strength;
         int var6 = 0;

         while(var6 < 2) {
            int i = var6++;
            var11 = shaderGroup;
            Intrinsics.checkNotNull(var11);
            ((Shader)var11.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
         }
      }

      if (lastStrength != strength) {
         lastStrength = strength;
         int var8 = 0;

         while(var8 < 2) {
            int i = var8++;
            ShaderGroup var15 = shaderGroup;
            Intrinsics.checkNotNull(var15);
            ((Shader)var15.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
         }
      }

   }

   public final void shadow(float strength, @NotNull Function0 drawMethod, @NotNull Function0 cutMethod) {
      Intrinsics.checkNotNullParameter(drawMethod, "drawMethod");
      Intrinsics.checkNotNullParameter(cutMethod, "cutMethod");
      if (OpenGlHelper.func_148822_b()) {
         ScaledResolution sc = new ScaledResolution(MinecraftInstance.mc);
         int width = sc.func_78326_a();
         int height = sc.func_78328_b();
         this.initShaderIfRequired(sc, strength);
         if (initFramebuffer != null) {
            if (resultBuffer != null) {
               if (frameBuffer != null) {
                  MinecraftInstance.mc.func_147110_a().func_147609_e();
                  Framebuffer var10000 = initFramebuffer;
                  Intrinsics.checkNotNull(var10000);
                  var10000.func_147614_f();
                  var10000 = resultBuffer;
                  Intrinsics.checkNotNull(var10000);
                  var10000.func_147614_f();
                  var10000 = initFramebuffer;
                  Intrinsics.checkNotNull(var10000);
                  var10000.func_147610_a(true);
                  drawMethod.invoke();
                  var10000 = frameBuffer;
                  Intrinsics.checkNotNull(var10000);
                  var10000.func_147610_a(true);
                  ShaderGroup var16 = shaderGroup;
                  Intrinsics.checkNotNull(var16);
                  var16.func_148018_a(MinecraftInstance.mc.field_71428_T.field_74281_c);
                  MinecraftInstance.mc.func_147110_a().func_147610_a(true);
                  Framebuffer var17 = resultBuffer;
                  Intrinsics.checkNotNull(var17);
                  double var18 = (double)var17.field_147621_c;
                  Framebuffer var10001 = resultBuffer;
                  Intrinsics.checkNotNull(var10001);
                  double fr_width = var18 / (double)var10001.field_147622_a;
                  Framebuffer var19 = resultBuffer;
                  Intrinsics.checkNotNull(var19);
                  double var20 = (double)var19.field_147618_d;
                  var10001 = resultBuffer;
                  Intrinsics.checkNotNull(var10001);
                  double fr_height = var20 / (double)var10001.field_147620_b;
                  Tessellator tessellator = Tessellator.func_178181_a();
                  WorldRenderer worldrenderer = tessellator.func_178180_c();
                  GL11.glPushMatrix();
                  GlStateManager.func_179140_f();
                  GlStateManager.func_179118_c();
                  GlStateManager.func_179098_w();
                  GlStateManager.func_179097_i();
                  GlStateManager.func_179132_a(false);
                  GlStateManager.func_179135_a(true, true, true, true);
                  Stencil.write(false);
                  cutMethod.invoke();
                  Stencil.erase(false);
                  GlStateManager.func_179147_l();
                  GlStateManager.func_179112_b(770, 771);
                  GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
                  Framebuffer var21 = resultBuffer;
                  Intrinsics.checkNotNull(var21);
                  var21.func_147612_c();
                  GL11.glTexParameteri(3553, 10242, 33071);
                  GL11.glTexParameteri(3553, 10243, 33071);
                  worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                  worldrenderer.func_181662_b((double)0.0F, (double)height, (double)0.0F).func_181673_a((double)0.0F, (double)0.0F).func_181669_b(255, 255, 255, 255).func_181675_d();
                  worldrenderer.func_181662_b((double)width, (double)height, (double)0.0F).func_181673_a(fr_width, (double)0.0F).func_181669_b(255, 255, 255, 255).func_181675_d();
                  worldrenderer.func_181662_b((double)width, (double)0.0F, (double)0.0F).func_181673_a(fr_width, fr_height).func_181669_b(255, 255, 255, 255).func_181675_d();
                  worldrenderer.func_181662_b((double)0.0F, (double)0.0F, (double)0.0F).func_181673_a((double)0.0F, fr_height).func_181669_b(255, 255, 255, 255).func_181675_d();
                  tessellator.func_78381_a();
                  var21 = resultBuffer;
                  Intrinsics.checkNotNull(var21);
                  var21.func_147606_d();
                  GlStateManager.func_179084_k();
                  GlStateManager.func_179141_d();
                  GlStateManager.func_179126_j();
                  GlStateManager.func_179132_a(true);
                  Stencil.dispose();
                  GL11.glPopMatrix();
                  GlStateManager.func_179117_G();
                  GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
                  GlStateManager.func_179147_l();
                  GlStateManager.func_179120_a(770, 771, 1, 0);
               }
            }
         }
      }
   }
}
