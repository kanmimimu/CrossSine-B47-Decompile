package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.Map;
import java.util.WeakHashMap;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateModelEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "Skeletal",
   category = ModuleCategory.VISUAL
)
public class Skeletal extends Module {
   private final Map playerRotationMap = new WeakHashMap();
   private final BoolValue clientTheme = new BoolValue("ClientTheme", false);
   private final IntegerValue red = (IntegerValue)(new IntegerValue("Red", 255, 0, 255)).displayable(() -> !(Boolean)this.clientTheme.get());
   private final IntegerValue green = (IntegerValue)(new IntegerValue("Green", 255, 0, 255)).displayable(() -> !(Boolean)this.clientTheme.get());
   private final IntegerValue blue = (IntegerValue)(new IntegerValue("Blue", 255, 0, 255)).displayable(() -> !(Boolean)this.clientTheme.get());
   private final BoolValue smoothLines = new BoolValue("SmoothLines", true);

   @EventTarget
   public final void onModelUpdate(UpdateModelEvent event) {
      ModelPlayer model = event.getModel();
      this.playerRotationMap.put(event.getPlayer(), new float[][]{{model.field_78116_c.field_78795_f, model.field_78116_c.field_78796_g, model.field_78116_c.field_78808_h}, {model.field_178723_h.field_78795_f, model.field_178723_h.field_78796_g, model.field_178723_h.field_78808_h}, {model.field_178724_i.field_78795_f, model.field_178724_i.field_78796_g, model.field_178724_i.field_78808_h}, {model.field_178721_j.field_78795_f, model.field_178721_j.field_78796_g, model.field_178721_j.field_78808_h}, {model.field_178722_k.field_78795_f, model.field_178722_k.field_78796_g, model.field_178722_k.field_78808_h}});
   }

   @EventTarget
   public void onRender(Render3DEvent event) {
      this.setupRender(true);
      GL11.glEnable(2903);
      GL11.glDisable(2848);
      this.playerRotationMap.keySet().removeIf((var0) -> this.contain((EntityPlayer)var0));
      Map playerRotationMap = this.playerRotationMap;
      Object[] players = playerRotationMap.keySet().toArray();
      int playersLength = players.length;

      for(int i = 0; i < playersLength; ++i) {
         EntityPlayer player = (EntityPlayer)players[i];
         float[][] entPos = (float[][])playerRotationMap.get(player);
         if (entPos != null && player.func_145782_y() != -1488 && player.func_70089_S() && RenderUtils.isInViewFrustrum((Entity)player) && !player.field_70128_L && player != mc.field_71439_g && EntityUtils.INSTANCE.isSelected(player, true) && !player.func_70608_bn() && !player.func_82150_aj()) {
            GL11.glPushMatrix();
            float[][] modelRotations = (float[][])playerRotationMap.get(player);
            GL11.glLineWidth(1.0F);
            if ((Boolean)this.clientTheme.get()) {
               RenderUtils.setColor(ClientTheme.INSTANCE.getColor(0, false));
            } else {
               GL11.glColor4f((float)(Integer)this.red.get() / 255.0F, (float)(Integer)this.green.get() / 255.0F, (float)(Integer)this.blue.get() / 255.0F, 1.0F);
            }

            double x = interpolate(player.field_70165_t, player.field_70142_S, (double)event.getPartialTicks()) - mc.func_175598_ae().field_78725_b;
            double y = interpolate(player.field_70163_u, player.field_70137_T, (double)event.getPartialTicks()) - mc.func_175598_ae().field_78726_c;
            double z = interpolate(player.field_70161_v, player.field_70136_U, (double)event.getPartialTicks()) - mc.func_175598_ae().field_78723_d;
            GL11.glTranslated(x, y, z);
            float bodyYawOffset = player.field_70760_ar + (player.field_70761_aq - player.field_70760_ar) * mc.field_71428_T.field_74281_c;
            GL11.glRotatef(-bodyYawOffset, 0.0F, 1.0F, 0.0F);
            GL11.glTranslated((double)0.0F, (double)0.0F, player.func_70093_af() ? -0.235 : (double)0.0F);
            float legHeight = player.func_70093_af() ? 0.6F : 0.75F;
            float rad = 57.29578F;
            GL11.glPushMatrix();
            GL11.glTranslated((double)-0.125F, (double)legHeight, (double)0.0F);
            if (modelRotations[3][0] != 0.0F) {
               GL11.glRotatef(modelRotations[3][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[3][1] != 0.0F) {
               GL11.glRotatef(modelRotations[3][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[3][2] != 0.0F) {
               GL11.glRotatef(modelRotations[3][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d((double)0.0F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.0F, (double)(-legHeight), (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.125F, (double)legHeight, (double)0.0F);
            if (modelRotations[4][0] != 0.0F) {
               GL11.glRotatef(modelRotations[4][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[4][1] != 0.0F) {
               GL11.glRotatef(modelRotations[4][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[4][2] != 0.0F) {
               GL11.glRotatef(modelRotations[4][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d((double)0.0F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.0F, (double)(-legHeight), (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated((double)0.0F, (double)0.0F, player.func_70093_af() ? (double)0.25F : (double)0.0F);
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0F, player.func_70093_af() ? -0.05 : (double)0.0F, player.func_70093_af() ? -0.01725 : (double)0.0F);
            GL11.glPushMatrix();
            GL11.glTranslated((double)-0.375F, (double)legHeight + 0.55, (double)0.0F);
            if (modelRotations[1][0] != 0.0F) {
               GL11.glRotatef(modelRotations[1][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[1][1] != 0.0F) {
               GL11.glRotatef(modelRotations[1][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[1][2] != 0.0F) {
               GL11.glRotatef(-modelRotations[1][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d((double)0.0F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.0F, (double)-0.5F, (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.375F, (double)legHeight + 0.55, (double)0.0F);
            if (modelRotations[2][0] != 0.0F) {
               GL11.glRotatef(modelRotations[2][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[2][1] != 0.0F) {
               GL11.glRotatef(modelRotations[2][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[2][2] != 0.0F) {
               GL11.glRotatef(-modelRotations[2][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d((double)0.0F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.0F, (double)-0.5F, (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(bodyYawOffset - player.field_70759_as, 0.0F, 1.0F, 0.0F);
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0F, (double)legHeight + 0.55, (double)0.0F);
            if (modelRotations[0][0] != 0.0F) {
               GL11.glRotatef(modelRotations[0][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d((double)0.0F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.0F, 0.3, (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(player.func_70093_af() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated((double)0.0F, player.func_70093_af() ? -0.16175 : (double)0.0F, player.func_70093_af() ? -0.48025 : (double)0.0F);
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0F, (double)legHeight, (double)0.0F);
            GL11.glBegin(3);
            GL11.glVertex3d((double)-0.125F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.125F, (double)0.0F, (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0F, (double)legHeight, (double)0.0F);
            GL11.glBegin(3);
            GL11.glVertex3d((double)0.0F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.0F, 0.55, (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0F, (double)legHeight + 0.55, (double)0.0F);
            GL11.glBegin(3);
            GL11.glVertex3d((double)-0.375F, (double)0.0F, (double)0.0F);
            GL11.glVertex3d((double)0.375F, (double)0.0F, (double)0.0F);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

      this.setupRender(false);
   }

   private void setupRender(boolean start) {
      boolean smooth = (Boolean)this.smoothLines.get();
      if (start) {
         if (smooth) {
            RenderUtils.startSmooth();
         } else {
            GL11.glDisable(2848);
         }

         GL11.glDisable(2929);
         GL11.glDisable(3553);
      } else {
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         if (smooth) {
            RenderUtils.endSmooth();
         }
      }

      GL11.glDepthMask(!start);
   }

   private boolean contain(EntityPlayer var0) {
      return !mc.field_71441_e.field_73010_i.contains(var0);
   }

   public static double interpolate(double current, double old, double scale) {
      return old + (current - old) * scale;
   }
}
