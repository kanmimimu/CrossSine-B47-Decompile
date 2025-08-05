package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

@ModuleInfo(
   name = "Breadcrumbs",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001(B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'H\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Breadcrumbs;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "color", "Ljava/awt/Color;", "getColor", "()Ljava/awt/Color;", "colorAlphaValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "drawTargetsValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "drawThePlayerValue", "fadeTimeValue", "fadeValue", "lineWidthValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "onlyThirdPersonValue", "points", "", "", "Lnet/ccbluex/liquidbounce/features/module/modules/visual/Breadcrumbs$BreadcrumbPoint;", "precisionValue", "sphereList", "sphereScaleValue", "", "typeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "updatePoints", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "BreadcrumbPoint", "CrossSine"}
)
public final class Breadcrumbs extends Module {
   @NotNull
   private final ListValue typeValue;
   @NotNull
   private final IntegerValue colorAlphaValue;
   @NotNull
   private final BoolValue fadeValue;
   @NotNull
   private final BoolValue drawThePlayerValue;
   @NotNull
   private final BoolValue drawTargetsValue;
   @NotNull
   private final IntegerValue fadeTimeValue;
   @NotNull
   private final IntegerValue precisionValue;
   @NotNull
   private final Value lineWidthValue;
   @NotNull
   private final Value sphereScaleValue;
   @NotNull
   private final BoolValue onlyThirdPersonValue;
   @NotNull
   private final Map points;
   private final int sphereList;

   public Breadcrumbs() {
      String[] var1 = new String[]{"Line", "Rect", "Sphere", "Rise"};
      this.typeValue = new ListValue("Type", var1, "Line");
      this.colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
      this.fadeValue = new BoolValue("Fade", true);
      this.drawThePlayerValue = new BoolValue("DrawThePlayer", true);
      this.drawTargetsValue = new BoolValue("DrawTargets", true);
      this.fadeTimeValue = new IntegerValue("FadeTime", 5, 1, 20);
      this.precisionValue = new IntegerValue("Precision", 4, 1, 20);
      this.lineWidthValue = (new IntegerValue("LineWidth", 1, 1, 10)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Breadcrumbs.this.typeValue.equals("Line");
         }
      });
      this.sphereScaleValue = (new FloatValue("SphereScale", 0.6F, 0.1F, 2.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Breadcrumbs.this.typeValue.equals("Sphere") || Breadcrumbs.this.typeValue.equals("Rise");
         }
      });
      this.onlyThirdPersonValue = new BoolValue("OnlyThirdPerson", true);
      this.points = (Map)(new LinkedHashMap());
      this.sphereList = GL11.glGenLists(1);
      GL11.glNewList(this.sphereList, 4864);
      Sphere shaft = new Sphere();
      shaft.setDrawStyle(100012);
      shaft.draw(0.3F, 25, 10);
      GL11.glEndList();
   }

   @NotNull
   public final Color getColor() {
      return ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null);
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)this.onlyThirdPersonValue.get() || MinecraftInstance.mc.field_71474_y.field_74320_O != 0) {
         int fTime = ((Number)this.fadeTimeValue.get()).intValue() * 1000;
         long fadeSec = System.currentTimeMillis() - (long)fTime;
         float colorAlpha = ((Number)this.colorAlphaValue.get()).floatValue() / 255.0F;
         GL11.glPushMatrix();
         GL11.glDisable(3553);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(2929);
         MinecraftInstance.mc.field_71460_t.func_175072_h();
         double renderPosX = MinecraftInstance.mc.func_175598_ae().field_78730_l;
         double renderPosY = MinecraftInstance.mc.func_175598_ae().field_78731_m;
         double renderPosZ = MinecraftInstance.mc.func_175598_ae().field_78728_n;
         Map $this$forEach$iv = this.points;
         int $i$f$forEach = 0;

         for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            int var17 = 0;
            List mutableList = (List)element$iv.getValue();
            double lastPosX = (double)114514.0F;
            double lastPosY = (double)114514.0F;
            double lastPosZ = (double)114514.0F;
            String alpha = ((String)this.typeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(alpha, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (Intrinsics.areEqual((Object)alpha, (Object)"line")) {
               GL11.glLineWidth((float)((Number)this.lineWidthValue.get()).intValue());
               GL11.glEnable(2848);
               GL11.glBegin(3);
            } else if (Intrinsics.areEqual((Object)alpha, (Object)"rect")) {
               GL11.glDisable(2884);
            }

            for(BreadcrumbPoint point : CollectionsKt.reversed((Iterable)mutableList)) {
               float var10000;
               if ((Boolean)this.fadeValue.get()) {
                  float pct = (float)(point.getTime() - fadeSec) / (float)fTime;
                  if (pct < 0.0F || pct > 1.0F) {
                     mutableList.remove(point);
                     continue;
                  }

                  var10000 = pct;
               } else {
                  var10000 = 1.0F;
               }

               float alpha = var10000 * colorAlpha;
               if (!this.typeValue.equals("Rise")) {
                  RenderUtils.glColor(point.getColor(), alpha);
               }

               String var29 = ((String)this.typeValue.get()).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var29, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var29.hashCode()) {
                  case -895981619:
                     if (var29.equals("sphere")) {
                        GL11.glPushMatrix();
                        GL11.glTranslated(point.getX() - renderPosX, point.getY() - renderPosY, point.getZ() - renderPosZ);
                        GL11.glScalef(((Number)this.sphereScaleValue.get()).floatValue(), ((Number)this.sphereScaleValue.get()).floatValue(), ((Number)this.sphereScaleValue.get()).floatValue());
                        GL11.glCallList(this.sphereList);
                        GL11.glPopMatrix();
                     }
                     break;
                  case 3321844:
                     if (var29.equals("line")) {
                        GL11.glVertex3d(point.getX() - renderPosX, point.getY() - renderPosY, point.getZ() - renderPosZ);
                     }
                     break;
                  case 3496420:
                     if (!var29.equals("rect")) {
                        break;
                     }

                     if (lastPosX != (double)114514.0F || lastPosY != (double)114514.0F || lastPosZ != (double)114514.0F) {
                        GL11.glBegin(7);
                        GL11.glVertex3d(point.getX() - renderPosX, point.getY() - renderPosY, point.getZ() - renderPosZ);
                        GL11.glVertex3d(lastPosX, lastPosY, lastPosZ);
                        GL11.glVertex3d(lastPosX, lastPosY + (double)MinecraftInstance.mc.field_71439_g.field_70131_O, lastPosZ);
                        GL11.glVertex3d(point.getX() - renderPosX, point.getY() - renderPosY + (double)MinecraftInstance.mc.field_71439_g.field_70131_O, point.getZ() - renderPosZ);
                        GL11.glEnd();
                     }

                     lastPosX = point.getX() - renderPosX;
                     lastPosY = point.getY() - renderPosY;
                     lastPosZ = point.getZ() - renderPosZ;
                     break;
                  case 3500745:
                     if (var29.equals("rise")) {
                        float circleScale = ((Number)this.sphereScaleValue.get()).floatValue();
                        RenderUtils.glColor(point.getColor(), 30);
                        GL11.glPushMatrix();
                        GL11.glTranslated(point.getX() - renderPosX, point.getY() - renderPosY, point.getZ() - renderPosZ);
                        GL11.glScalef(circleScale * 1.3F, circleScale * 1.3F, circleScale * 1.3F);
                        GL11.glCallList(this.sphereList);
                        GL11.glPopMatrix();
                        RenderUtils.glColor(point.getColor(), 50);
                        GL11.glPushMatrix();
                        GL11.glTranslated(point.getX() - renderPosX, point.getY() - renderPosY, point.getZ() - renderPosZ);
                        GL11.glScalef(circleScale * 0.8F, circleScale * 0.8F, circleScale * 0.8F);
                        GL11.glCallList(this.sphereList);
                        GL11.glPopMatrix();
                        RenderUtils.glColor(point.getColor(), alpha);
                        GL11.glPushMatrix();
                        GL11.glTranslated(point.getX() - renderPosX, point.getY() - renderPosY, point.getZ() - renderPosZ);
                        GL11.glScalef(circleScale * 0.4F, circleScale * 0.4F, circleScale * 0.4F);
                        GL11.glCallList(this.sphereList);
                        GL11.glPopMatrix();
                     }
               }
            }

            alpha = ((String)this.typeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(alpha, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (Intrinsics.areEqual((Object)alpha, (Object)"line")) {
               GL11.glEnd();
               GL11.glDisable(2848);
            } else if (Intrinsics.areEqual((Object)alpha, (Object)"rect")) {
               GL11.glEnable(2884);
            }
         }

         GL11.glColor4d((double)1.0F, (double)1.0F, (double)1.0F, (double)1.0F);
         GL11.glEnable(2929);
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glPopMatrix();
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Map $this$forEach$iv = this.points;
      int $i$f$forEach = 0;

      for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
         int var7 = 0;
         int id = ((Number)element$iv.getKey()).intValue();
         if (MinecraftInstance.mc.field_71441_e.func_73045_a(id) == null) {
            this.points.remove(id);
         }
      }

      if (MinecraftInstance.mc.field_71439_g.field_70173_aa % ((Number)this.precisionValue.get()).intValue() == 0) {
         if ((Boolean)this.drawTargetsValue.get()) {
            List $this$forEach$iv = MinecraftInstance.mc.field_71441_e.field_72996_f;
            Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "mc.theWorld.loadedEntityList");
            Iterable $this$forEach$iv = (Iterable)$this$forEach$iv;
            $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Entity it = (Entity)element$iv;
               int var15 = 0;
               EntityUtils var10000 = EntityUtils.INSTANCE;
               Intrinsics.checkNotNullExpressionValue(it, "it");
               if (var10000.isSelected(it, true)) {
                  this.updatePoints((EntityLivingBase)it);
               }
            }
         }

         if ((Boolean)this.drawThePlayerValue.get()) {
            EntityPlayerSP var11 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(var11, "mc.thePlayer");
            this.updatePoints((EntityLivingBase)var11);
         }

      }
   }

   private final void updatePoints(EntityLivingBase entity) {
      List var2 = (List)this.points.get(entity.func_145782_y());
      List var10000;
      if (var2 == null) {
         List it = (List)(new ArrayList());
         int var5 = 0;
         Map var6 = this.points;
         Integer var7 = entity.func_145782_y();
         var6.put(var7, it);
         var10000 = it;
      } else {
         var10000 = var2;
      }

      var10000.add(new BreadcrumbPoint(entity.field_70165_t, entity.func_174813_aQ().field_72338_b, entity.field_70161_v, System.currentTimeMillis(), this.getColor().getRGB()));
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.points.clear();
   }

   public void onDisable() {
      this.points.clear();
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006\u0013"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Breadcrumbs$BreadcrumbPoint;", "", "x", "", "y", "z", "time", "", "color", "", "(DDDJI)V", "getColor", "()I", "getTime", "()J", "getX", "()D", "getY", "getZ", "CrossSine"}
   )
   public static final class BreadcrumbPoint {
      private final double x;
      private final double y;
      private final double z;
      private final long time;
      private final int color;

      public BreadcrumbPoint(double x, double y, double z, long time, int color) {
         this.x = x;
         this.y = y;
         this.z = z;
         this.time = time;
         this.color = color;
      }

      public final double getX() {
         return this.x;
      }

      public final double getY() {
         return this.y;
      }

      public final double getZ() {
         return this.z;
      }

      public final long getTime() {
         return this.time;
      }

      public final int getColor() {
         return this.color;
      }
   }
}
