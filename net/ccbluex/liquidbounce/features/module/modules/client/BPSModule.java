package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@ModuleInfo(
   name = "BPS",
   category = ModuleCategory.CLIENT,
   loadConfig = false
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010*\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020.H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0006\"\u0004\b\u0018\u0010\bR\u001a\u0010\u0019\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0006\"\u0004\b\u001b\u0010\bR\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\r0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001a\u0010$\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001a\u0010'\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\b¨\u0006/"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/BPSModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "dragOffsetX", "", "getDragOffsetX", "()F", "setDragOffsetX", "(F)V", "dragOffsetY", "getDragOffsetY", "setDragOffsetY", "draging", "", "getDraging", "()Z", "setDraging", "(Z)V", "highBPS", "highValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "outlineProgress", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "resetHigh", "Lnet/ccbluex/liquidbounce/features/value/Value;", "ux2_size", "getUx2_size", "setUx2_size", "ux_size", "getUx_size", "setUx_size", "uy2_size", "getUy2_size", "setUy2_size", "uy_size", "getUy_size", "setUy_size", "onEnable", "", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"}
)
public final class BPSModule extends Module {
   @NotNull
   public static final BPSModule INSTANCE = new BPSModule();
   @NotNull
   private static final BoolValue highValue = new BoolValue("Show High BPS", false);
   @NotNull
   private static final Value resetHigh;
   private static boolean draging;
   private static float posY;
   private static float posX;
   private static float dragOffsetX;
   private static float dragOffsetY;
   private static float ux_size;
   private static float uy_size;
   private static float ux2_size;
   private static float uy2_size;
   private static float highBPS;
   private static float outlineProgress;

   private BPSModule() {
   }

   public final boolean getDraging() {
      return draging;
   }

   public final void setDraging(boolean var1) {
      draging = var1;
   }

   public final float getPosY() {
      return posY;
   }

   public final void setPosY(float var1) {
      posY = var1;
   }

   public final float getPosX() {
      return posX;
   }

   public final void setPosX(float var1) {
      posX = var1;
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

   public void onEnable() {
      highBPS = 0.0F;
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)resetHigh.get()) {
         highBPS = 0.0F;
         resetHigh.set(false);
      }

      outlineProgress += 0.00375F * (float)RenderUtils.deltaTime * (MinecraftInstance.mc.field_71462_r instanceof GuiChat && MouseUtils.mouseWithinBounds(Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1, ux_size, uy_size, ux2_size, uy2_size) ? 1.0F : -1.0F);
      if (MovementUtils.INSTANCE.getBps() > (double)highBPS) {
         String var2 = (new DecimalFormat("#.##")).format(MovementUtils.INSTANCE.getBps());
         Intrinsics.checkNotNullExpressionValue(var2, "DecimalFormat(\"#.##\").format(MovementUtils.bps)");
         highBPS = Float.parseFloat(var2);
      }

      outlineProgress = RangesKt.coerceIn(outlineProgress, 0.0F, 1.0F);
      GlStateManager.func_179094_E();
      RenderUtils.drawBloomRoundedRect(posX, posY, posX + ((Boolean)highValue.get() && Fonts.SFBold40.func_78256_a(Intrinsics.stringPlus("BPS: ", (new DecimalFormat("#.##")).format(MovementUtils.INSTANCE.getBps()))) <= Fonts.SFBold40.func_78256_a(Intrinsics.stringPlus("High BPS: ", highBPS)) ? (float)Fonts.SFBold40.func_78256_a(Intrinsics.stringPlus("High BPS: ", highBPS)) : (float)Fonts.SFBold40.func_78256_a(Intrinsics.stringPlus("BPS: ", (new DecimalFormat("#.##")).format(MovementUtils.INSTANCE.getBps())))) + (float)19, posY + 20.0F + ((Boolean)highValue.get() ? 10.0F : 0.0F), 4.0F, 1.5F, new Color(0, 0, 0, 100), RenderUtils.ShaderBloom.BLOOMONLY);
      Fonts.SFBold40.drawString(Intrinsics.stringPlus("BPS: ", (new DecimalFormat("#.##")).format(MovementUtils.INSTANCE.getBps())), posX + (float)10, posY + (float)7, Color.WHITE.getRGB());
      if ((Boolean)highValue.get()) {
         Fonts.SFBold40.drawString(Intrinsics.stringPlus("High BPS: ", highBPS), posX + (float)10, posY + (float)17, Color.WHITE.getRGB());
      }

      GlStateManager.func_179117_G();
      GlStateManager.func_179121_F();
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

      ux_size = posX;
      uy_size = posY;
      ux2_size = posX + 150.0F;
      uy2_size = posY + 65.0F;
   }

   static {
      resetHigh = (new BoolValue("Reset Value", false)).displayable(null.INSTANCE);
   }
}
