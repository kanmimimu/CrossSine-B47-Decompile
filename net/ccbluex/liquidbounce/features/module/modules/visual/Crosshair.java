package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "Crosshair",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Crosshair;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "crosshairColor", "Ljava/awt/Color;", "getCrosshairColor", "()Ljava/awt/Color;", "dynamicValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "gapValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "hitMarkerValue", "sizeValue", "widthValue", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"}
)
public final class Crosshair extends Module {
   @NotNull
   public static final Crosshair INSTANCE = new Crosshair();
   @NotNull
   private static final FloatValue widthValue = new FloatValue("Width", 0.5F, 0.25F, 10.0F);
   @NotNull
   private static final FloatValue sizeValue = new FloatValue("Length", 7.0F, 0.25F, 15.0F);
   @NotNull
   private static final FloatValue gapValue = new FloatValue("Gap", 5.0F, 0.25F, 15.0F);
   @NotNull
   private static final BoolValue dynamicValue = new BoolValue("Dynamic", true);
   @NotNull
   private static final BoolValue hitMarkerValue = new BoolValue("HitMarker", true);

   private Crosshair() {
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      ScaledResolution sr = event.getScaledResolution();
      float width = ((Number)widthValue.get()).floatValue();
      float size = ((Number)sizeValue.get()).floatValue();
      float gap = ((Number)gapValue.get()).floatValue();
      boolean isMoving = (Boolean)dynamicValue.get() && MovementUtils.INSTANCE.isMoving();
      GL11.glPushMatrix();
      RenderUtils.drawBorderedRect((float)sr.func_78326_a() / 2.0F - width, (float)sr.func_78328_b() / 2.0F - gap - size - (float)(isMoving ? 2 : 0), (float)sr.func_78326_a() / 2.0F + 1.0F + width, (float)sr.func_78328_b() / 2.0F - gap - (float)(isMoving ? 2 : 0), 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
      RenderUtils.drawBorderedRect((float)sr.func_78326_a() / 2.0F - width, (float)sr.func_78328_b() / 2.0F + gap + (float)1 + (float)(isMoving ? 2 : 0) - 0.15F, (float)sr.func_78326_a() / 2.0F + 1.0F + width, (float)sr.func_78328_b() / 2.0F + (float)1 + gap + size + (float)(isMoving ? 2 : 0) - 0.15F, 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
      RenderUtils.drawBorderedRect((float)sr.func_78326_a() / 2.0F - gap - size - (float)(isMoving ? 2 : 0) + 0.15F, (float)sr.func_78328_b() / 2.0F - width, (float)sr.func_78326_a() / 2.0F - gap - (float)(isMoving ? 2 : 0) + 0.15F, (float)(sr.func_78328_b() / 2) + 1.0F + width, 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
      RenderUtils.drawBorderedRect((float)sr.func_78326_a() / 2.0F + (float)1 + gap + (float)(isMoving ? 2 : 0), (float)sr.func_78328_b() / 2.0F - width, (float)sr.func_78326_a() / 2.0F + size + gap + 1.0F + (float)(isMoving ? 2 : 0), (float)(sr.func_78328_b() / 2) + 1.0F + width, 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
      GL11.glPopMatrix();
      GlStateManager.func_179117_G();
      EntityLivingBase target = CrossSine.INSTANCE.getCombatManager().getTarget();
      if ((Boolean)hitMarkerValue.get() && target != null && target.field_70737_aN > 0) {
         GL11.glPushMatrix();
         GlStateManager.func_179147_l();
         GlStateManager.func_179090_x();
         GlStateManager.func_179120_a(770, 771, 1, 0);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)target.field_70737_aN / (float)target.field_70738_aO);
         GL11.glEnable(2848);
         GL11.glLineWidth(1.0F);
         GL11.glBegin(3);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F + gap, (float)sr.func_78328_b() / 2.0F + gap);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F + gap + size, (float)sr.func_78328_b() / 2.0F + gap + size);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F - gap, (float)sr.func_78328_b() / 2.0F - gap);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F - gap - size, (float)sr.func_78328_b() / 2.0F - gap - size);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F - gap, (float)sr.func_78328_b() / 2.0F + gap);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F - gap - size, (float)sr.func_78328_b() / 2.0F + gap + size);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F + gap, (float)sr.func_78328_b() / 2.0F - gap);
         GL11.glVertex2f((float)sr.func_78326_a() / 2.0F + gap + size, (float)sr.func_78328_b() / 2.0F - gap - size);
         GL11.glEnd();
         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
         GL11.glPopMatrix();
      }

   }

   private final Color getCrosshairColor() {
      return ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null);
   }
}
