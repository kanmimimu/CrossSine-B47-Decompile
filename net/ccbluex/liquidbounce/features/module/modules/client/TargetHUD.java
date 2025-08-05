package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.InfiniteAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "TargetHUD",
   category = ModuleCategory.CLIENT,
   loadConfig = false
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010>\u001a\u00020?H\u0002J\b\u0010@\u001a\u00020?H\u0002J\b\u0010A\u001a\u00020?H\u0002J\u0010\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020CH\u0002J\u0010\u0010E\u001a\u00020?2\u0006\u0010F\u001a\u00020GH\u0007J\u0010\u0010H\u001a\u00020?2\u0006\u0010F\u001a\u00020IH\u0007J\u0010\u0010J\u001a\u00020?2\u0006\u0010F\u001a\u00020KH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\t\"\u0004\b&\u0010\u000bR\u000e\u0010'\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\t\"\u0004\b*\u0010\u000bR\u000e\u0010+\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0016\u0010,\u001a\u0004\u0018\u00010-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00102\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\t\"\u0004\b4\u0010\u000bR\u001a\u00105\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\t\"\u0004\b7\u0010\u000bR\u001a\u00108\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\t\"\u0004\b:\u0010\u000bR\u001a\u0010;\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\t\"\u0004\b=\u0010\u000b¨\u0006L"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/TargetHUD;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animProgress", "", "attackTarget", "Lnet/minecraft/entity/EntityLivingBase;", "dragOffsetX", "getDragOffsetX", "()F", "setDragOffsetX", "(F)V", "dragOffsetY", "getDragOffsetY", "setDragOffsetY", "draging", "", "getDraging", "()Z", "setDraging", "(Z)V", "easingHealth", "followTarget", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getFollowTarget", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "lastX", "", "lastY", "lastZ", "mainTarget", "mode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getMode", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "outlineProgress", "posX", "getPosX", "setPosX", "posX2", "posY", "getPosY", "setPosY", "posY2", "tag", "", "getTag", "()Ljava/lang/String;", "targetTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "ux2_size", "getUx2_size", "setUx2_size", "ux_size", "getUx_size", "setUx_size", "uy2_size", "getUy2_size", "setUy2_size", "uy_size", "getUy_size", "setUy_size", "drawCrossSine", "", "drawRavenB4", "drawSimple", "fadeAlpha", "", "alpha", "onAttack", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"}
)
public final class TargetHUD extends Module {
   @NotNull
   public static final TargetHUD INSTANCE = new TargetHUD();
   @NotNull
   private static final ListValue mode;
   @NotNull
   private static final BoolValue followTarget;
   @Nullable
   private static EntityLivingBase attackTarget;
   @NotNull
   private static final TimerMS targetTimer;
   @Nullable
   private static EntityLivingBase mainTarget;
   private static float animProgress;
   private static float easingHealth;
   private static boolean draging;
   private static float posX;
   private static float posY;
   private static final float posY2;
   private static final float posX2;
   private static float dragOffsetX;
   private static float dragOffsetY;
   private static float ux_size;
   private static float uy_size;
   private static float ux2_size;
   private static float uy2_size;
   private static double lastX;
   private static double lastY;
   private static double lastZ;
   private static float outlineProgress;

   private TargetHUD() {
   }

   @NotNull
   public final ListValue getMode() {
      return mode;
   }

   @NotNull
   public final BoolValue getFollowTarget() {
      return followTarget;
   }

   public final boolean getDraging() {
      return draging;
   }

   public final void setDraging(boolean var1) {
      draging = var1;
   }

   public final float getPosX() {
      return posX;
   }

   public final void setPosX(float var1) {
      posX = var1;
   }

   public final float getPosY() {
      return posY;
   }

   public final void setPosY(float var1) {
      posY = var1;
   }

   public final float getDragOffsetX() {
      return dragOffsetX;
   }

   public final void setDragOffsetX(float var1) {
      dragOffsetX = var1;
   }

   public final float getDragOffsetY() {
      return dragOffsetY;
   }

   public final void setDragOffsetY(float var1) {
      dragOffsetY = var1;
   }

   public final float getUx_size() {
      return ux_size;
   }

   public final void setUx_size(float var1) {
      ux_size = var1;
   }

   public final float getUy_size() {
      return uy_size;
   }

   public final void setUy_size(float var1) {
      uy_size = var1;
   }

   public final float getUx2_size() {
      return ux2_size;
   }

   public final void setUx2_size(float var1) {
      ux2_size = var1;
   }

   public final float getUy2_size() {
      return uy2_size;
   }

   public final void setUy2_size(float var1) {
      uy2_size = var1;
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!Intrinsics.areEqual((Object)attackTarget, (Object)event.getTargetEntity()) && EntityUtils.INSTANCE.isSelected(event.getTargetEntity(), true)) {
         attackTarget = (EntityLivingBase)event.getTargetEntity();
      }

      targetTimer.reset();
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      EntityLivingBase actualTarget = InfiniteAura.INSTANCE.getLastTarget() != null ? InfiniteAura.INSTANCE.getLastTarget() : (MinecraftInstance.mc.field_71462_r instanceof GuiChat ? (EntityLivingBase)MinecraftInstance.mc.field_71439_g : (KillAura.INSTANCE.getState() && KillAura.INSTANCE.getCurrentTarget() != null ? KillAura.INSTANCE.getCurrentTarget() : (SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() != null ? SilentAura.INSTANCE.getTarget() : attackTarget)));
      animProgress += 0.00375F * (float)RenderUtils.deltaTime * (actualTarget != null ? -1.0F : 1.0F);
      animProgress = RangesKt.coerceIn(animProgress, 0.0F, 1.0F);
      outlineProgress += 0.00375F * (float)RenderUtils.deltaTime * (MinecraftInstance.mc.field_71462_r instanceof GuiChat && MouseUtils.mouseWithinBounds(Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1, ux_size, uy_size, ux2_size, uy2_size) ? 1.0F : -1.0F);
      outlineProgress = RangesKt.coerceIn(outlineProgress, 0.0F, 1.0F);
      if (actualTarget != null) {
         mainTarget = actualTarget;
      } else if (animProgress >= 1.0F) {
         mainTarget = null;
      }

      if (mainTarget == null) {
         easingHealth = 0.0F;
      }

      if (targetTimer.hasTimePassed(500L)) {
         attackTarget = null;
      }

      float var10000 = easingHealth;
      float var12;
      if (mainTarget != null) {
         EntityLivingBase var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         var12 = (var10001.func_110143_aJ() - easingHealth) / (float)Math.pow((double)2.0F, (double)7.0F) * (float)RenderUtils.deltaTime;
      } else {
         var12 = 0.0F;
      }

      easingHealth = var10000 + var12;
      double percent = EaseUtils.INSTANCE.easeInBack((double)animProgress);
      double tranXZ = (double)((ux2_size + posX) / 2.0F) * percent;
      double tranY = (double)((uy2_size + posY) / 2.0F) * percent;
      if (!(Boolean)followTarget.get() || Intrinsics.areEqual((Object)mainTarget, (Object)MinecraftInstance.mc.field_71439_g)) {
         GL11.glPushMatrix();
         if (!mode.equals("RavenB4")) {
            GL11.glTranslated(tranXZ, tranY, (double)0.0F);
            GL11.glScaled((double)1.0F - percent, (double)1.0F - percent, (double)1.0F - percent);
         }

         Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var11) {
            case "simple":
               this.drawSimple();
               break;
            case "ravenb4":
               this.drawRavenB4();
               break;
            case "crosssine":
               this.drawCrossSine();
         }

         GlStateManager.func_179117_G();
         GlStateManager.func_179121_F();
      }

      GlStateManager.func_179094_E();
      RenderUtils.drawRoundedOutline(ux_size, uy_size, ux2_size, uy2_size, 7.0F, 2.5F, (new Color(255, 255, 255, (int)((float)255 * outlineProgress))).getRGB());
      GlStateManager.func_179117_G();
      GlStateManager.func_179121_F();
      int mouseX = MinecraftInstance.mc.field_71462_r == null ? 0 : Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c;
      int mouseY = MinecraftInstance.mc.field_71462_r == null ? 0 : MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1;
      if (draging) {
         posX = (float)mouseX - dragOffsetX;
         posY = (float)mouseY - dragOffsetY;
         if (!Mouse.isButtonDown(0) || MinecraftInstance.mc.field_71462_r == null) {
            draging = false;
         }
      }

   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)followTarget.get() && !Intrinsics.areEqual((Object)mainTarget, (Object)MinecraftInstance.mc.field_71439_g) && mainTarget != null) {
         EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
         EntityLivingBase var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         float distance = var10000.func_70032_d((Entity)var10001) / 4.0F;
         if (distance < 1.0F) {
            distance = 1.0F;
         }

         float scale = distance / 150.0F;
         double var6 = lastX;
         var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         double var10 = var10001.field_70142_S;
         EntityLivingBase var10002 = mainTarget;
         Intrinsics.checkNotNull(var10002);
         double var16 = var10002.field_70165_t;
         EntityLivingBase var10003 = mainTarget;
         Intrinsics.checkNotNull(var10003);
         lastX = var6 + (var10 + (var16 - var10003.field_70142_S) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78725_b - lastX) / (double)2;
         var6 = lastY;
         EntityLivingBase var11 = mainTarget;
         Intrinsics.checkNotNull(var11);
         double var12 = var11.field_70137_T;
         EntityLivingBase var17 = mainTarget;
         Intrinsics.checkNotNull(var17);
         double var18 = var17.field_70163_u;
         var10003 = mainTarget;
         Intrinsics.checkNotNull(var10003);
         var12 = var12 + (var18 - var10003.field_70137_T) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78726_c;
         EntityLivingBase var19 = mainTarget;
         Intrinsics.checkNotNull(var19);
         lastY = var6 + (var12 + (double)var19.func_70047_e() - lastY) / (double)2;
         var6 = lastZ;
         EntityLivingBase var14 = mainTarget;
         Intrinsics.checkNotNull(var14);
         double var15 = var14.field_70136_U;
         var19 = mainTarget;
         Intrinsics.checkNotNull(var19);
         double var21 = var19.field_70161_v;
         var10003 = mainTarget;
         Intrinsics.checkNotNull(var10003);
         lastZ = var6 + (var15 + (var21 - var10003.field_70136_U) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78723_d - lastZ) / (double)2;
         GlStateManager.func_179094_E();
         GL11.glTranslated(lastX, lastY, lastZ);
         GL11.glRotatef(-MinecraftInstance.mc.func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(MinecraftInstance.mc.func_175598_ae().field_78732_j, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-scale * (float)2, -scale * (float)2, scale * (float)2);
         int[] var4 = new int[]{2896, 2929};
         RenderUtils.disableGlCap(var4);
         RenderUtils.enableGlCap(3042);
         GL11.glBlendFunc(770, 771);
         GlStateManager.func_179094_E();
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var5) {
            case "simple":
               this.drawSimple();
               break;
            case "ravenb4":
               this.drawRavenB4();
               break;
            case "crosssine":
               this.drawCrossSine();
         }

         GlStateManager.func_179121_F();
         RenderUtils.resetCaps();
         GlStateManager.func_179117_G();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179121_F();
         GlStateManager.func_179117_G();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private final void drawCrossSine() {
      if (mainTarget != null) {
         if ((Boolean)Interface.INSTANCE.getShaderValue().get()) {
            BlurUtils.blurAreaRounded(ux_size, uy_size, ux2_size, uy2_size, 2.0F, 10.0F);
         }

         GameFontRenderer fonts = Fonts.font35;
         GameFontRenderer fonts2 = Fonts.font24;
         DecimalFormat decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
         StringBuilder var10000 = new StringBuilder();
         EntityPlayerSP var10002 = MinecraftInstance.mc.field_71439_g;
         EntityLivingBase var10003 = mainTarget;
         Intrinsics.checkNotNull(var10003);
         String string = var10000.append(decimalFormat3.format(var10002.func_70032_d((Entity)var10003))).append("m - ").append(MinecraftInstance.mc.field_71439_g.func_110143_aJ() > easingHealth ? Intrinsics.stringPlus("+", decimalFormat3.format(MinecraftInstance.mc.field_71439_g.func_110143_aJ() - easingHealth)) : Intrinsics.stringPlus("-", decimalFormat3.format(easingHealth - MinecraftInstance.mc.field_71439_g.func_110143_aJ()))).toString();
         EntityLivingBase var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         String var6 = var10001.func_70005_c_();
         Intrinsics.checkNotNullExpressionValue(var6, "mainTarget!!.name");
         float width = (float)fonts.func_78256_a(var6) + 30.0F;
         RenderUtils.drawBloomRoundedRect(-2.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), -2.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 6.0F + width + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 33.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 2.0F, 2.0F, new Color(0, 0, 0, this.fadeAlpha(180)), RenderUtils.ShaderBloom.BLOOMONLY);
         GlStateManager.func_179147_l();
         EntityLivingBase var9 = mainTarget;
         Intrinsics.checkNotNull(var9);
         RenderUtils.drawHead(EntityExtensionKt.getSkin(var9), 2 + (int)((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 2 + (int)((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 23, 23, (new Color(255, 255, 255, this.fadeAlpha(255))).getRGB());
         var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         var6 = var10001.func_70005_c_();
         Intrinsics.checkNotNullExpressionValue(var6, "mainTarget!!.name");
         fonts.drawString(var6, 28.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 11.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), (new Color(150, 150, 150, this.fadeAlpha(255))).getRGB());
         fonts2.drawString(string, width - (float)fonts2.func_78256_a(string) + 2.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 23.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), (new Color(150, 150, 150, this.fadeAlpha(255))).getRGB());
         RenderUtils.drawRoundedRect(2.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 27.5F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 2.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX) + width, 27.5F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY) + 2.0F, 1.0F, (new Color(0, 0, 0, this.fadeAlpha(180))).getRGB());
         float var10 = 2.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX);
         float var12 = 27.5F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY);
         float var14 = 2.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX);
         float var15 = width * easingHealth;
         EntityLivingBase var10004 = mainTarget;
         Intrinsics.checkNotNull(var10004);
         RenderUtils.drawGradientRoundedRect(var10, var12, var14 + var15 / var10004.func_110138_aP(), 27.5F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY) + 2.0F, 1, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, this.fadeAlpha(255), false, 4, (Object)null).getRGB(), ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 360, this.fadeAlpha(255), false, 4, (Object)null).getRGB());
         GlStateManager.func_179118_c();
         GlStateManager.func_179084_k();
         ux_size = (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX;
         EntityLivingBase var13 = mainTarget;
         Intrinsics.checkNotNull(var13);
         var6 = var13.func_70005_c_();
         Intrinsics.checkNotNullExpressionValue(var6, "mainTarget!!.name");
         ux2_size = (float)fonts.func_78256_a(var6) + 30.0F + ux_size;
         uy_size = (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY;
         uy2_size = 31.0F + uy_size;
      }
   }

   private final void drawSimple() {
      if (mainTarget != null) {
         if ((Boolean)Interface.INSTANCE.getShaderValue().get()) {
            BlurUtils.blurAreaRounded(ux_size, uy_size, ux2_size, uy2_size, 2.0F, 10.0F);
         }

         FontRenderer fonts = Fonts.minecraftFont;
         EntityLivingBase var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         int leagth = fonts.func_78256_a(var10001.func_70005_c_());
         RenderUtils.drawRoundedRect((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX, (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY, ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX) + 40.0F + (float)leagth, ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY) + 24.0F, 2.0F, (new Color(0, 0, 0, this.fadeAlpha(180))).getRGB());
         float var10000 = 28.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX);
         float var5 = 20.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY);
         float var10003 = (float)leagth + 6.0F;
         float var10004 = easingHealth;
         EntityLivingBase var10005 = mainTarget;
         Intrinsics.checkNotNull(var10005);
         float var10002 = 28.0F + var10003 * (var10004 / var10005.func_110138_aP()) + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX);
         var10003 = 21.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY);
         EntityLivingBase var10 = mainTarget;
         Intrinsics.checkNotNull(var10);
         float var11 = var10.func_110143_aJ();
         var10005 = mainTarget;
         Intrinsics.checkNotNull(var10005);
         Color var3 = BlendUtils.getHealthColor(var11, var10005.func_110138_aP());
         Intrinsics.checkNotNullExpressionValue(var3, "getHealthColor(mainTarge…, mainTarget!!.maxHealth)");
         RenderUtils.drawRect(var10000, var5, var10002, var10003, ColorUtils.reAlpha(var3, this.fadeAlpha(255)).getRGB());
         GlStateManager.func_179147_l();
         EntityLivingBase var6 = mainTarget;
         Intrinsics.checkNotNull(var6);
         fonts.func_175065_a(var6.func_70005_c_(), 31.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 5.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), (new Color(255, 255, 255, this.fadeAlpha(255))).getRGB(), true);
         EntityLivingBase var4 = mainTarget;
         Intrinsics.checkNotNull(var4);
         RenderUtils.drawHead(EntityExtensionKt.getSkin(var4), 2 + (int)((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 2 + (int)((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 20, 20, (new Color(255, 255, 255, this.fadeAlpha(255))).getRGB());
         GlStateManager.func_179118_c();
         GlStateManager.func_179084_k();
         ux_size = (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX;
         FontRenderer var7 = Fonts.minecraftFont;
         EntityLivingBase var8 = mainTarget;
         Intrinsics.checkNotNull(var8);
         ux2_size = 40.0F + (float)var7.func_78256_a(var8.func_70005_c_()) + ux_size;
         uy_size = (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY;
         uy2_size = 24.0F + uy_size;
      }
   }

   private final void drawRavenB4() {
      if (mainTarget != null) {
         DecimalFormat decimalFormat2 = new DecimalFormat("##0.0", new DecimalFormatSymbols(Locale.ENGLISH));
         FontRenderer font = Fonts.minecraftFont;
         EntityLivingBase var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         String hp = decimalFormat2.format(var10001.func_110143_aJ());
         EntityLivingBase var10002 = mainTarget;
         Intrinsics.checkNotNull(var10002);
         int hplength = font.func_78256_a(decimalFormat2.format(var10002.func_110143_aJ()));
         var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         int length = font.func_78256_a(var10001.func_145748_c_().func_150254_d());
         if ((Boolean)Interface.INSTANCE.getShaderValue().get()) {
            BlurUtils.blurAreaRounded(ux_size, uy_size, ux2_size, uy2_size, 4.0F, 10.0F);
         }

         GlStateManager.func_179094_E();
         RenderUtils.drawRoundedGradientOutlineCorner((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX, (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY, (float)(length + hplength) + 23.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 35.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 2.0F, 8.0F, ClientTheme.setColor$default(ClientTheme.INSTANCE, true, this.fadeAlpha(255), false, 4, (Object)null).getRGB(), ClientTheme.setColor$default(ClientTheme.INSTANCE, false, this.fadeAlpha(255), false, 4, (Object)null).getRGB());
         RenderUtils.drawRoundedRect((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX, (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY, (float)(length + hplength) + 23.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 35.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 4.0F, (new Color(0, 0, 0, this.fadeAlpha(100))).getRGB());
         GlStateManager.func_179147_l();
         var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         font.func_175063_a(var10001.func_145748_c_().func_150254_d(), 6.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 8.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), (new Color(255, 255, 255, this.fadeAlpha(255))).getRGB());
         var10001 = mainTarget;
         Intrinsics.checkNotNull(var10001);
         String var10 = var10001.func_110143_aJ() > MinecraftInstance.mc.field_71439_g.func_110143_aJ() ? "L" : "W";
         float var13 = (float)(length + hplength) + 11.6F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX);
         float var10003 = 8.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY);
         EntityLivingBase var10004 = mainTarget;
         Intrinsics.checkNotNull(var10004);
         font.func_175063_a(var10, var13, var10003, var10004.func_110143_aJ() > MinecraftInstance.mc.field_71439_g.func_110143_aJ() ? (new Color(255, 0, 0, this.fadeAlpha(255))).getRGB() : (new Color(0, 255, 0, this.fadeAlpha(255))).getRGB());
         var13 = (float)length + 8.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX);
         var10003 = 8.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY);
         var10004 = mainTarget;
         Intrinsics.checkNotNull(var10004);
         float var19 = var10004.func_110143_aJ();
         EntityLivingBase var10005 = mainTarget;
         Intrinsics.checkNotNull(var10005);
         Color var6 = BlendUtils.getHealthColor(var19, var10005.func_110138_aP());
         Intrinsics.checkNotNullExpressionValue(var6, "getHealthColor(mainTarge…, mainTarget!!.maxHealth)");
         font.func_175063_a(hp, var13, var10003, ColorUtils.reAlpha(var6, this.fadeAlpha(255)).getRGB());
         GlStateManager.func_179118_c();
         GlStateManager.func_179084_k();
         RenderUtils.drawRoundedRect(5.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 29.55F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), (float)(length + hplength) + 18.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 25.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 2.0F, (new Color(0, 0, 0, this.fadeAlpha(110))).getRGB());
         RenderUtils.drawRoundedGradientRectCorner(5.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 25.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 8.0F + easingHealth / (float)20 * ((float)(length + hplength) + 10.0F) + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 29.5F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 4.0F, ClientTheme.setColor$default(ClientTheme.INSTANCE, true, this.fadeAlpha(100), false, 4, (Object)null).getRGB(), ClientTheme.setColor$default(ClientTheme.INSTANCE, false, this.fadeAlpha(100), false, 4, (Object)null).getRGB());
         float var10000 = 5.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX);
         float var11 = 25.0F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY);
         EntityLivingBase var17 = mainTarget;
         Intrinsics.checkNotNull(var17);
         RenderUtils.drawRoundedGradientRectCorner(var10000, var11, 8.0F + var17.func_110143_aJ() / (float)20 * ((float)(length + hplength) + 10.0F) + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX), 29.5F + ((Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY), 4.0F, ClientTheme.setColor$default(ClientTheme.INSTANCE, true, this.fadeAlpha(255), false, 4, (Object)null).getRGB(), ClientTheme.setColor$default(ClientTheme.INSTANCE, false, this.fadeAlpha(255), false, 4, (Object)null).getRGB());
         GlStateManager.func_179121_F();
         ux_size = (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posX2 : posX;
         FontRenderer var12 = MinecraftInstance.mc.field_71466_p;
         EntityLivingBase var15 = mainTarget;
         Intrinsics.checkNotNull(var15);
         ux2_size = 40.0F + (float)var12.func_78256_a(var15.func_145748_c_().func_150254_d()) + ux_size;
         uy_size = (Boolean)followTarget.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) ? posY2 : posY;
         uy2_size = 35.0F + uy_size;
      }
   }

   private final int fadeAlpha(int alpha) {
      return alpha - (int)(animProgress * (float)alpha);
   }

   @Nullable
   public String getTag() {
      return (String)mode.get();
   }

   static {
      String[] var0 = new String[]{"CrossSine", "Simple", "RavenB4"};
      mode = new ListValue("Mode", var0, "CrossSine");
      followTarget = new BoolValue("FollowTarget", false);
      targetTimer = new TimerMS();
      posX = (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a() / 2.0F + 40.0F;
      posY = (float)(new ScaledResolution(MinecraftInstance.mc)).func_78328_b() / 2.0F;
      posY2 = -40.0F;
      posX2 = 40.0F;
   }
}
