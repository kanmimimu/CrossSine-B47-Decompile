package net.ccbluex.liquidbounce.utils.render.glu;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLUtessellatorCallbackAdapter;

public class DirectTessCallback extends GLUtessellatorCallbackAdapter {
   public static final DirectTessCallback INSTANCE = new DirectTessCallback();

   public void begin(int type) {
      GL11.glBegin(type);
   }

   public void combine(double[] coords, Object[] data, float[] weight, Object[] outData) {
      double[] combined = new double[]{coords[0], coords[1], coords[2], (double)1.0F, (double)1.0F, (double)1.0F};

      for(int i = 0; i < outData.length; ++i) {
         outData[i] = new VertexData(combined);
      }

   }

   public void end() {
      GL11.glEnd();
   }

   public void vertex(Object vertexData) {
      VertexData vertex = (VertexData)vertexData;
      GL11.glVertex3f((float)vertex.data[0], (float)vertex.data[1], (float)vertex.data[2]);
   }
}
