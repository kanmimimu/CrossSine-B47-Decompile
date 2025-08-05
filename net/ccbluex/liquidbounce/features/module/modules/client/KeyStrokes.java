package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.BlockHit;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.RightClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@ModuleInfo(
   name = "KeyStrokes",
   category = ModuleCategory.CLIENT,
   loadConfig = false
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u00104\u001a\u00020\u000f2\u0006\u00105\u001a\u00020\u000fH\u0002J\u0010\u00106\u001a\u0002072\u0006\u00108\u001a\u000209H\u0007JP\u0010:\u001a\u0002072\u0006\u0010;\u001a\u00020\u00182\u0006\u0010<\u001a\u00020\u00062\u0006\u0010=\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u00062\u0006\u0010?\u001a\u00020\u00062\u0006\u0010@\u001a\u00020\u00062\u0006\u0010A\u001a\u00020BH\u0002J\u0010\u0010C\u001a\u00020\u000f2\u0006\u00105\u001a\u00020\u000fH\u0002J\u0018\u0010D\u001a\u0002072\u0006\u00105\u001a\u00020\u00182\u0006\u0010E\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00060\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\b\"\u0004\b\u001e\u0010\nR\u001a\u0010\u001f\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\b\"\u0004\b!\u0010\nR\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\b\"\u0004\b*\u0010\nR\u001a\u0010+\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\b\"\u0004\b-\u0010\nR\u001a\u0010.\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\b\"\u0004\b0\u0010\nR\u001a\u00101\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\b\"\u0004\b3\u0010\n¨\u0006F"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/KeyStrokes;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "backgroundAlpha", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "dragOffsetX", "", "getDragOffsetX", "()F", "setDragOffsetX", "(F)V", "dragOffsetY", "getDragOffsetY", "setDragOffsetY", "draging", "", "getDraging", "()Z", "setDraging", "(Z)V", "keyColor", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "keyStates", "", "", "lineSpace", "Lnet/ccbluex/liquidbounce/features/value/Value;", "outlineProgress", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "roundValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "showCPS", "showMouse", "showSpace", "sizeBox", "ux2_size", "getUx2_size", "setUx2_size", "ux_size", "getUx_size", "setUx_size", "uy2_size", "getUy2_size", "setUy2_size", "uy_size", "getUy_size", "setUy_size", "leftClick", "key", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "renderKey", "keyString", "textPosX", "textPosY", "size", "size2", "keyTick", "index", "", "rightClick", "updateKeyState", "isPressed", "CrossSine"}
)
public final class KeyStrokes extends Module {
   @NotNull
   public static final KeyStrokes INSTANCE = new KeyStrokes();
   @NotNull
   private static final BoolValue keyColor = new BoolValue("Key Rainbow Color", false);
   @NotNull
   private static final BoolValue showMouse = new BoolValue("Show Mouse", false);
   @NotNull
   private static final Value showCPS;
   @NotNull
   private static final BoolValue showSpace;
   @NotNull
   private static final Value lineSpace;
   @NotNull
   private static final FloatValue roundValue;
   @NotNull
   private static final IntegerValue backgroundAlpha;
   @NotNull
   private static final FloatValue sizeBox;
   @NotNull
   private static final Map keyStates;
   private static boolean draging;
   private static float posX;
   private static float posY;
   private static float dragOffsetX;
   private static float dragOffsetY;
   private static float ux_size;
   private static float uy_size;
   private static float ux2_size;
   private static float uy2_size;
   private static float outlineProgress;

   private KeyStrokes() {
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
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.updateKeyState("w", MinecraftInstance.mc.field_71474_y.field_74351_w.func_151470_d());
      this.updateKeyState("a", MinecraftInstance.mc.field_71474_y.field_74370_x.func_151470_d());
      this.updateKeyState("s", MinecraftInstance.mc.field_71474_y.field_74368_y.func_151470_d());
      this.updateKeyState("d", MinecraftInstance.mc.field_71474_y.field_74366_z.func_151470_d());
      this.updateKeyState("space", MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d());
      this.updateKeyState("lmb", this.leftClick(MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d()));
      this.updateKeyState("rmb", this.rightClick(MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d()));
      Object var10008 = keyStates.get("w");
      Intrinsics.checkNotNull(var10008);
      this.renderKey("W", 16.5F, 13.0F, 33.0F, 0.0F, 65.0F, 32.0F, ((Number)var10008).floatValue(), 90);
      var10008 = keyStates.get("a");
      Intrinsics.checkNotNull(var10008);
      this.renderKey("A", 16.5F, 13.0F, 0.0F, 33.0F, 32.0F, 65.0F, ((Number)var10008).floatValue(), 0);
      var10008 = keyStates.get("s");
      Intrinsics.checkNotNull(var10008);
      this.renderKey("S", 16.5F, 13.0F, 33.0F, 33.0F, 65.0F, 65.0F, ((Number)var10008).floatValue(), 90);
      var10008 = keyStates.get("d");
      Intrinsics.checkNotNull(var10008);
      this.renderKey("D", 16.5F, 13.0F, 66.0F, 33.0F, 98.0F, 65.0F, ((Number)var10008).floatValue(), 180);
      outlineProgress += 0.00375F * (float)RenderUtils.deltaTime * (MinecraftInstance.mc.field_71462_r instanceof GuiChat && MouseUtils.mouseWithinBounds(Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1, ux_size, uy_size, ux2_size, uy2_size) ? 1.0F : -1.0F);
      outlineProgress = RangesKt.coerceIn(outlineProgress, 0.0F, 1.0F);
      float baseY = 66.0F;
      if ((Boolean)showMouse.get()) {
         String var10001 = CPSCounter.getCPS(CPSCounter.MouseButton.LEFT) != 0 && (Boolean)showCPS.get() ? String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT)) : "LMB";
         float var10007 = baseY + 32.0F;
         var10008 = keyStates.get("lmb");
         Intrinsics.checkNotNull(var10008);
         this.renderKey(var10001, 25.0F, 13.0F, 0.0F, baseY, 48.0F, var10007, ((Number)var10008).floatValue(), 0);
         var10001 = CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT) != 0 && (Boolean)showCPS.get() ? String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT)) : "RMB";
         var10007 = baseY + 32.0F;
         var10008 = keyStates.get("rmb");
         Intrinsics.checkNotNull(var10008);
         this.renderKey(var10001, 25.0F, 13.0F, 49.0F, baseY, 98.0F, var10007, ((Number)var10008).floatValue(), 180);
      }

      if ((Boolean)showSpace.get()) {
         float spaceY = baseY + ((Boolean)showMouse.get() ? 33.0F : 0.0F);
         String var8 = (Boolean)lineSpace.get() ? "-" : "SPACE";
         float var10 = spaceY + 19.0F;
         var10008 = keyStates.get("space");
         Intrinsics.checkNotNull(var10008);
         this.renderKey(var8, 49.0F, 4.175F, 0.0F, spaceY, 98.0F, var10, ((Number)var10008).floatValue(), 90);
      }

      float result = (Boolean)showMouse.get() && (Boolean)showSpace.get() ? 118.0F : ((Boolean)showMouse.get() ? 98.0F : ((Boolean)showSpace.get() ? 85.0F : 65.0F));
      GlStateManager.func_179094_E();
      RenderUtils.drawRoundedOutline(ux_size, uy_size, ux2_size, uy2_size, 7.0F, 2.5F, (new Color(255, 255, 255, (int)((float)255 * outlineProgress))).getRGB());
      GlStateManager.func_179117_G();
      GlStateManager.func_179121_F();
      ux_size = posX;
      uy_size = posY;
      ux2_size = posX + 98.0F * ((Number)sizeBox.get()).floatValue();
      uy2_size = posY + result * ((Number)sizeBox.get()).floatValue();
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

   private final void updateKeyState(String key, boolean isPressed) {
      Map var3 = keyStates;
      Object var10000 = keyStates.get(key);
      Intrinsics.checkNotNull(var10000);
      Float var4 = ((Number)var10000).floatValue() + 0.03F * (float)RenderUtils.deltaTime * (isPressed ? 1.0F : -1.0F);
      var3.put(key, var4);
      var3 = keyStates;
      var10000 = keyStates.get(key);
      Intrinsics.checkNotNull(var10000);
      var4 = RangesKt.coerceIn(((Number)var10000).floatValue(), 0.0F, 1.0F);
      var3.put(key, var4);
   }

   private final boolean leftClick(boolean key) {
      return MinecraftInstance.mc.field_71462_r != null ? false : ((!KillAura.INSTANCE.getState() || KillAura.INSTANCE.getCurrentTarget() == null) && (!SilentAura.INSTANCE.getState() || SilentAura.INSTANCE.getTarget() == null) && (!AutoClicker.INSTANCE.getState() || !AutoClicker.INSTANCE.getCanLeftClick()) ? key : MouseUtils.INSTANCE.getLeftClicked());
   }

   private final boolean rightClick(boolean key) {
      return MinecraftInstance.mc.field_71462_r != null ? false : (!BlockHit.INSTANCE.getState() && !Scaffold.INSTANCE.getState() && (!RightClicker.INSTANCE.getState() || !RightClicker.INSTANCE.getCanRightClick()) ? key : MouseUtils.INSTANCE.getRightClicked());
   }

   private final void renderKey(String keyString, float textPosX, float textPosY, float posX, float posY, float size, float size2, float keyTick, int index) {
      float adjustedPosX = posX * ((Number)sizeBox.get()).floatValue();
      float adjustedPosY = posY * ((Number)sizeBox.get()).floatValue();
      float adjustedSizeX = size * ((Number)sizeBox.get()).floatValue();
      float adjustedSizeY = size2 * ((Number)sizeBox.get()).floatValue();
      float adjustedTextPosX = textPosX * ((Number)sizeBox.get()).floatValue();
      float adjustedTextPosY = textPosY * ((Number)sizeBox.get()).floatValue();
      int color = (int)((float)255 * RangesKt.coerceIn(keyTick, 0.1F, 1.0F));
      int rectColor = (new Color(color, color, color, ((Number)backgroundAlpha.get()).intValue())).getRGB();
      int textColor = (Boolean)keyColor.get() ? (new Color(RangesKt.coerceIn(ClientTheme.getColor$default(ClientTheme.INSTANCE, index, false, 2, (Object)null).getRed() - color, 0, 255), RangesKt.coerceIn(ClientTheme.getColor$default(ClientTheme.INSTANCE, index, false, 2, (Object)null).getGreen() - color, 0, 255), RangesKt.coerceIn(ClientTheme.getColor$default(ClientTheme.INSTANCE, index, false, 2, (Object)null).getBlue() - color, 0, 255))).getRGB() : (new Color(255 - color, 255 - color, 255 - color, 255)).getRGB();
      GlStateManager.func_179094_E();
      if ((Boolean)Interface.INSTANCE.getShaderValue().get()) {
         BlurUtils.blurAreaRounded(adjustedPosX + KeyStrokes.posX, adjustedPosY + KeyStrokes.posY, adjustedSizeX + KeyStrokes.posX, adjustedSizeY + KeyStrokes.posY, ((Number)roundValue.get()).floatValue(), 10.0F);
      }

      RenderUtils.drawRoundedRect(adjustedPosX + KeyStrokes.posX, adjustedPosY + KeyStrokes.posY, adjustedSizeX + KeyStrokes.posX, adjustedSizeY + KeyStrokes.posY, ((Number)roundValue.get()).floatValue(), rectColor);
      Fonts.SFBold40.drawCenteredString(keyString, adjustedPosX + KeyStrokes.posX + adjustedTextPosX, adjustedPosY + KeyStrokes.posY + adjustedTextPosY, textColor, true);
      GlStateManager.func_179121_F();
      GlStateManager.func_179117_G();
   }

   static {
      showCPS = (new BoolValue("Show CPS", false)).displayable(null.INSTANCE);
      showSpace = new BoolValue("Show Space", true);
      lineSpace = (new BoolValue("Line Space", false)).displayable(null.INSTANCE);
      roundValue = new FloatValue("Rounded Size", 6.0F, 0.0F, 6.0F);
      backgroundAlpha = new IntegerValue("Background Alpha", 180, 0, 255);
      sizeBox = new FloatValue("Size Box", 1.0F, 0.5F, 1.0F);
      Pair[] var0 = new Pair[]{TuplesKt.to("w", 0.0F), TuplesKt.to("a", 0.0F), TuplesKt.to("s", 0.0F), TuplesKt.to("d", 0.0F), TuplesKt.to("space", 0.0F), TuplesKt.to("lmb", 0.0F), TuplesKt.to("rmb", 0.0F)};
      keyStates = MapsKt.mutableMapOf(var0);
   }
}
