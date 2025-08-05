package net.ccbluex.liquidbounce.utils.render.shader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader extends MinecraftInstance {
   private int program;
   private Map uniformsMap;

   public Shader(String fragmentShader) {
      int vertexShaderID;
      int fragmentShaderID;
      try {
         InputStream vertexStream = this.getClass().getResourceAsStream("/assets/minecraft/crosssine/shader/vertex.vert");
         vertexShaderID = this.createShader(IOUtils.toString(vertexStream), 35633);
         IOUtils.closeQuietly(vertexStream);
         InputStream fragmentStream = this.getClass().getResourceAsStream("/assets/minecraft/crosssine/shader/fragment/" + fragmentShader);
         fragmentShaderID = this.createShader(IOUtils.toString(fragmentStream), 35632);
         IOUtils.closeQuietly(fragmentStream);
      } catch (Exception e) {
         e.printStackTrace();
         return;
      }

      if (vertexShaderID != 0 && fragmentShaderID != 0) {
         this.program = ARBShaderObjects.glCreateProgramObjectARB();
         if (this.program != 0) {
            ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
            ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
            ARBShaderObjects.glLinkProgramARB(this.program);
            ARBShaderObjects.glValidateProgramARB(this.program);
            ClientUtils.INSTANCE.logInfo("[Shader] Successfully loaded: " + fragmentShader);
         }
      }
   }

   public void startShader() {
      GL11.glPushMatrix();
      GL20.glUseProgram(this.program);
      if (this.uniformsMap == null) {
         this.uniformsMap = new HashMap();
         this.setupUniforms();
      }

      this.updateUniforms();
   }

   public void stopShader() {
      GL20.glUseProgram(0);
      GL11.glPopMatrix();
   }

   public abstract void setupUniforms();

   public abstract void updateUniforms();

   private int createShader(String shaderSource, int shaderType) {
      int shader = 0;

      try {
         shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
         if (shader == 0) {
            return 0;
         } else {
            ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
            ARBShaderObjects.glCompileShaderARB(shader);
            if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
               throw new RuntimeException("Error creating shader: " + this.getLogInfo(shader));
            } else {
               return shader;
            }
         }
      } catch (Exception e) {
         ARBShaderObjects.glDeleteObjectARB(shader);
         throw e;
      }
   }

   private String getLogInfo(int i) {
      return ARBShaderObjects.glGetInfoLogARB(i, ARBShaderObjects.glGetObjectParameteriARB(i, 35716));
   }

   public void setUniform(String uniformName, int location) {
      this.uniformsMap.put(uniformName, location);
   }

   public void setupUniform(String uniformName) {
      this.setUniform(uniformName, GL20.glGetUniformLocation(this.program, uniformName));
   }

   public int getUniform(String uniformName) {
      return (Integer)this.uniformsMap.get(uniformName);
   }

   public void setUniformf(String name, float... args) {
      int loc = GL20.glGetUniformLocation(this.program, name);
      switch (args.length) {
         case 1:
            GL20.glUniform1f(loc, args[0]);
            break;
         case 2:
            GL20.glUniform2f(loc, args[0], args[1]);
            break;
         case 3:
            GL20.glUniform3f(loc, args[0], args[1], args[2]);
            break;
         case 4:
            GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
      }

   }

   public static void drawQuads(float x, float y, float width, float height) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
   }

   public static void drawQuad(float x, float y, float width, float height) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2d((double)x, (double)(y + height));
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2d((double)(x + width), (double)(y + height));
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2d((double)(x + width), (double)y);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glEnd();
   }
}
