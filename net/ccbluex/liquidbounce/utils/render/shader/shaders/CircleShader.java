package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL20;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/CircleShader;", "Lnet/ccbluex/liquidbounce/utils/render/shader/Shader;", "()V", "setupUniforms", "", "updateUniforms", "CrossSine"}
)
public final class CircleShader extends Shader {
   @NotNull
   public static final CircleShader INSTANCE = new CircleShader();

   private CircleShader() {
      super("circle.frag");
   }

   public void setupUniforms() {
      this.setupUniform("color");
      this.setupUniform("size");
   }

   public void updateUniforms() {
      GL20.glUniform4f(this.getUniform("color"), 1.0F, 1.0F, 1.0F, 1.0F);
      GL20.glUniform2f(this.getUniform("size"), 100.0F, 100.0F);
   }
}
