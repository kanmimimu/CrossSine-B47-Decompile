package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JI\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0086\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RoundedRectGradientShader;", "Lnet/ccbluex/liquidbounce/utils/render/shader/Shader;", "()V", "draw", "x", "", "y", "x2", "y2", "radius", "shadow", "colortop", "Ljava/awt/Color;", "colorbottom", "setupUniforms", "", "updateUniforms", "CrossSine"}
)
public final class RoundedRectGradientShader extends Shader {
   @NotNull
   public static final RoundedRectGradientShader INSTANCE = new RoundedRectGradientShader();

   private RoundedRectGradientShader() {
      super("roundedrectgradient.frag");
   }

   public void setupUniforms() {
      this.setupUniform("u_size");
      this.setupUniform("u_radius");
      this.setupUniform("u_color");
   }

   public void updateUniforms() {
   }

   @NotNull
   public final RoundedRectGradientShader draw(float x, float y, float x2, float y2, float radius, float shadow, @NotNull Color colortop, @NotNull Color colorbottom) {
      Intrinsics.checkNotNullParameter(colortop, "colortop");
      Intrinsics.checkNotNullParameter(colorbottom, "colorbottom");
      int $i$f$draw = 0;
      float width = Math.abs(x2 - x);
      float height = Math.abs(y2 - y);
      INSTANCE.startShader();
      float[] var12 = new float[]{width, height};
      INSTANCE.setUniformf("u_size", var12);
      var12 = new float[]{radius};
      INSTANCE.setUniformf("u_radius", var12);
      RoundedRectGradientShader var10000 = INSTANCE;
      var12 = new float[]{(float)colortop.getRed() / 255.0F, (float)colortop.getGreen() / 255.0F, (float)colortop.getBlue() / 255.0F, (float)colortop.getAlpha() / 255.0F};
      var10000.setUniformf("u_colorTop", var12);
      var10000 = INSTANCE;
      var12 = new float[]{(float)colorbottom.getRed() / 255.0F, (float)colorbottom.getGreen() / 255.0F, (float)colorbottom.getBlue() / 255.0F, (float)colorbottom.getAlpha() / 255.0F};
      var10000.setUniformf("u_colorBottom", var12);
      var12 = new float[]{shadow};
      INSTANCE.setUniformf("u_shadow", var12);
      GlStateManager.func_179147_l();
      GlStateManager.func_179112_b(770, 771);
      GlStateManager.func_179141_d();
      GlStateManager.func_179092_a(516, 0.0F);
      Shader.drawQuad(x, y, width, height);
      GlStateManager.func_179084_k();
      INSTANCE.stopShader();
      return INSTANCE;
   }
}
