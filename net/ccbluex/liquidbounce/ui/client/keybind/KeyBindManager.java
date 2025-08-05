package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.ui.client.other.PopUI;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u0007H\u0016J \u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u001dH\u0016J\u0018\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0004H\u0014J \u0010'\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004H\u0014J\b\u0010)\u001a\u00020\u001dH\u0016J\u0006\u0010*\u001a\u00020\u001dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006+"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "baseHeight", "", "baseWidth", "clicked", "", "getClicked", "()Z", "setClicked", "(Z)V", "keys", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "Lkotlin/collections/ArrayList;", "nowDisplayKey", "getNowDisplayKey", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "setNowDisplayKey", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;)V", "popUI", "Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "getPopUI", "()Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "setPopUI", "(Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;)V", "doesGuiPauseGame", "drawScreen", "", "mouseX", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateAllKeys", "CrossSine"}
)
public final class KeyBindManager extends GuiScreen {
   private final int baseHeight = 205;
   private final int baseWidth = 500;
   @NotNull
   private final ArrayList keys = new ArrayList();
   @Nullable
   private KeyInfo nowDisplayKey;
   private boolean clicked;
   @Nullable
   private PopUI popUI;

   public KeyBindManager() {
      this.keys.add(new KeyInfo(12.0F, 12.0F, 27.0F, 32.0F, 41, "`"));
      this.keys.add(new KeyInfo(44.0F, 12.0F, 27.0F, 32.0F, 2, "1"));
      this.keys.add(new KeyInfo(76.0F, 12.0F, 27.0F, 32.0F, 3, "2"));
      this.keys.add(new KeyInfo(108.0F, 12.0F, 27.0F, 32.0F, 4, "3"));
      this.keys.add(new KeyInfo(140.0F, 12.0F, 27.0F, 32.0F, 5, "4"));
      this.keys.add(new KeyInfo(172.0F, 12.0F, 27.0F, 32.0F, 6, "5"));
      this.keys.add(new KeyInfo(204.0F, 12.0F, 27.0F, 32.0F, 7, "6"));
      this.keys.add(new KeyInfo(236.0F, 12.0F, 27.0F, 32.0F, 8, "7"));
      this.keys.add(new KeyInfo(268.0F, 12.0F, 27.0F, 32.0F, 9, "8"));
      this.keys.add(new KeyInfo(300.0F, 12.0F, 27.0F, 32.0F, 10, "9"));
      this.keys.add(new KeyInfo(332.0F, 12.0F, 27.0F, 32.0F, 11, "0"));
      this.keys.add(new KeyInfo(364.0F, 12.0F, 27.0F, 32.0F, 12, "-"));
      this.keys.add(new KeyInfo(396.0F, 12.0F, 27.0F, 32.0F, 13, "="));
      this.keys.add(new KeyInfo(428.0F, 12.0F, 59.0F, 32.0F, 14, "Backspace"));
      this.keys.add(new KeyInfo(12.0F, 49.0F, 43.0F, 32.0F, 15, "Tab"));
      this.keys.add(new KeyInfo(60.0F, 49.0F, 27.0F, 32.0F, 16, "Q"));
      this.keys.add(new KeyInfo(92.0F, 49.0F, 27.0F, 32.0F, 17, "W"));
      this.keys.add(new KeyInfo(124.0F, 49.0F, 27.0F, 32.0F, 18, "E"));
      this.keys.add(new KeyInfo(156.0F, 49.0F, 27.0F, 32.0F, 19, "R"));
      this.keys.add(new KeyInfo(188.0F, 49.0F, 27.0F, 32.0F, 20, "T"));
      this.keys.add(new KeyInfo(220.0F, 49.0F, 27.0F, 32.0F, 21, "Y"));
      this.keys.add(new KeyInfo(252.0F, 49.0F, 27.0F, 32.0F, 22, "U"));
      this.keys.add(new KeyInfo(284.0F, 49.0F, 27.0F, 32.0F, 23, "I"));
      this.keys.add(new KeyInfo(316.0F, 49.0F, 27.0F, 32.0F, 24, "O"));
      this.keys.add(new KeyInfo(348.0F, 49.0F, 27.0F, 32.0F, 25, "P"));
      this.keys.add(new KeyInfo(380.0F, 49.0F, 27.0F, 32.0F, 26, "["));
      this.keys.add(new KeyInfo(412.0F, 49.0F, 27.0F, 32.0F, 27, "]"));
      this.keys.add(new KeyInfo(444.0F, 49.0F, 43.0F, 32.0F, 43, "\\"));
      this.keys.add(new KeyInfo(12.0F, 86.0F, 59.0F, 32.0F, 15, "Caps Lock"));
      this.keys.add(new KeyInfo(76.0F, 86.0F, 27.0F, 32.0F, 30, "A"));
      this.keys.add(new KeyInfo(108.0F, 86.0F, 27.0F, 32.0F, 31, "S"));
      this.keys.add(new KeyInfo(140.0F, 86.0F, 27.0F, 32.0F, 32, "D"));
      this.keys.add(new KeyInfo(172.0F, 86.0F, 27.0F, 32.0F, 33, "F"));
      this.keys.add(new KeyInfo(204.0F, 86.0F, 27.0F, 32.0F, 34, "G"));
      this.keys.add(new KeyInfo(236.0F, 86.0F, 27.0F, 32.0F, 35, "H"));
      this.keys.add(new KeyInfo(268.0F, 86.0F, 27.0F, 32.0F, 36, "J"));
      this.keys.add(new KeyInfo(300.0F, 86.0F, 27.0F, 32.0F, 37, "K"));
      this.keys.add(new KeyInfo(332.0F, 86.0F, 27.0F, 32.0F, 38, "L"));
      this.keys.add(new KeyInfo(364.0F, 86.0F, 27.0F, 32.0F, 39, ""));
      this.keys.add(new KeyInfo(396.0F, 86.0F, 27.0F, 32.0F, 40, "'"));
      this.keys.add(new KeyInfo(428.0F, 86.0F, 59.0F, 32.0F, 28, "Enter"));
      this.keys.add(new KeyInfo(12.0F, 123.0F, 75.0F, 32.0F, 42, "Shift", "LShift"));
      this.keys.add(new KeyInfo(92.0F, 123.0F, 27.0F, 32.0F, 44, "Z"));
      this.keys.add(new KeyInfo(124.0F, 123.0F, 27.0F, 32.0F, 45, "X"));
      this.keys.add(new KeyInfo(156.0F, 123.0F, 27.0F, 32.0F, 46, "C"));
      this.keys.add(new KeyInfo(188.0F, 123.0F, 27.0F, 32.0F, 47, "V"));
      this.keys.add(new KeyInfo(220.0F, 123.0F, 27.0F, 32.0F, 48, "B"));
      this.keys.add(new KeyInfo(252.0F, 123.0F, 27.0F, 32.0F, 49, "N"));
      this.keys.add(new KeyInfo(284.0F, 123.0F, 27.0F, 32.0F, 50, "M"));
      this.keys.add(new KeyInfo(316.0F, 123.0F, 27.0F, 32.0F, 51, ","));
      this.keys.add(new KeyInfo(348.0F, 123.0F, 27.0F, 32.0F, 52, "."));
      this.keys.add(new KeyInfo(380.0F, 123.0F, 27.0F, 32.0F, 53, "/"));
      this.keys.add(new KeyInfo(412.0F, 123.0F, 75.0F, 32.0F, 54, "Shift", "RShift"));
      this.keys.add(new KeyInfo(12.0F, 160.0F, 43.0F, 32.0F, 29, "Ctrl", "LCtrl"));
      this.keys.add(new KeyInfo(60.0F, 160.0F, 43.0F, 32.0F, 56, "Alt", "LAlt"));
      this.keys.add(new KeyInfo(108.0F, 160.0F, 251.0F, 32.0F, 57, " ", "Space"));
      this.keys.add(new KeyInfo(364.0F, 160.0F, 43.0F, 32.0F, 184, "Alt", "RAlt"));
      this.keys.add(new KeyInfo(412.0F, 160.0F, 27.0F, 32.0F, 199, "Ø", "Home"));
      this.keys.add(new KeyInfo(444.0F, 160.0F, 43.0F, 32.0F, 157, "Ctrl", "RCtrl"));
   }

   @Nullable
   public final KeyInfo getNowDisplayKey() {
      return this.nowDisplayKey;
   }

   public final void setNowDisplayKey(@Nullable KeyInfo var1) {
      this.nowDisplayKey = var1;
   }

   public final boolean getClicked() {
      return this.clicked;
   }

   public final void setClicked(boolean var1) {
      this.clicked = var1;
   }

   @Nullable
   public final PopUI getPopUI() {
      return this.popUI;
   }

   public final void setPopUI(@Nullable PopUI var1) {
      this.popUI = var1;
   }

   public void func_73866_w_() {
      this.nowDisplayKey = null;
      this.updateAllKeys();
   }

   public final void updateAllKeys() {
      (new Thread(KeyBindManager::updateAllKeys$lambda-0)).start();
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.func_146276_q_();
      int mcWidth = (int)((float)this.field_146294_l * 0.8F - (float)this.field_146294_l * 0.2F);
      GL11.glPushMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      Fonts.font50SemiBold.func_175065_a("KeyBind Manager", (float)this.field_146294_l * 0.21F * 0.5F, (float)this.field_146295_m * 0.2F * 0.5F - 0.5F, Color.WHITE.getRGB(), false);
      GL11.glPopMatrix();
      GL11.glTranslatef((float)this.field_146294_l * 0.2F, (float)this.field_146295_m * 0.2F + (float)Fonts.SFApple40.getHeight() * 2.3F, 0.0F);
      float scale = (float)mcWidth / (float)this.baseWidth;
      GL11.glScalef(scale, scale, scale);
      RenderUtils.drawRoundedRect(0.0F, 0.0F, (float)this.baseWidth, (float)this.baseHeight, 7.0F, Color.WHITE.getRGB());

      for(KeyInfo key : this.keys) {
         key.render();
      }

      if (this.nowDisplayKey != null) {
         KeyInfo var10000 = this.nowDisplayKey;
         Intrinsics.checkNotNull(var10000);
         var10000.renderTab();
      }

      GL11.glPopMatrix();
      if (Mouse.hasWheel()) {
         int wheel = Mouse.getDWheel();
         if (wheel != 0) {
            if (this.popUI != null) {
               PopUI var11 = this.popUI;
               Intrinsics.checkNotNull(var11);
               var11.onStroll(this.field_146294_l, this.field_146295_m, mouseX, mouseY, wheel);
            } else if (this.nowDisplayKey != null) {
               float scaledMouseX = ((float)mouseX - (float)this.field_146294_l * 0.2F) / scale;
               float scaledMouseY = ((float)mouseY - ((float)this.field_146295_m * 0.2F + (float)Fonts.SFApple40.getHeight() * 2.3F)) / scale;
               KeyInfo var12 = this.nowDisplayKey;
               Intrinsics.checkNotNull(var12);
               var12.stroll(scaledMouseX, scaledMouseY, wheel);
            }
         }
      }

      PopUI var13 = this.popUI;
      if (var13 != null) {
         var13.onRender(this.field_146294_l, this.field_146295_m);
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if (this.popUI == null) {
         float scale = ((float)this.field_146294_l * 0.8F - (float)this.field_146294_l * 0.2F) / (float)this.baseWidth;
         float scaledMouseX = ((float)mouseX - (float)this.field_146294_l * 0.2F) / scale;
         float scaledMouseY = ((float)mouseY - ((float)this.field_146295_m * 0.2F + (float)Fonts.SFApple40.getHeight() * 2.3F)) / scale;
         if (this.nowDisplayKey == null) {
            if (scaledMouseX < 0.0F || scaledMouseY < 0.0F || scaledMouseX > (float)this.baseWidth || scaledMouseY > (float)this.baseHeight) {
               this.field_146297_k.func_147108_a((GuiScreen)null);
               return;
            }

            for(KeyInfo key : this.keys) {
               if (scaledMouseX > key.getPosX() && scaledMouseY > key.getPosY() && scaledMouseX < key.getPosX() + key.getWidth() && scaledMouseY < key.getPosY() + key.getHeight()) {
                  key.click(scaledMouseX, scaledMouseY);
                  break;
               }
            }
         } else {
            KeyInfo var10000 = this.nowDisplayKey;
            Intrinsics.checkNotNull(var10000);
            var10000.click(scaledMouseX, scaledMouseY);
         }
      } else {
         PopUI var9 = this.popUI;
         Intrinsics.checkNotNull(var9);
         var9.onClick(this.field_146294_l, this.field_146295_m, mouseX, mouseY);
      }

   }

   public void func_146281_b() {
      CrossSine.INSTANCE.getConfigManager().smartSave();
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      if (1 == keyCode) {
         if (this.popUI != null) {
            PopUI var3 = this.popUI;
            Intrinsics.checkNotNull(var3);
            var3.setAnimatingOut(false);
            var3 = this.popUI;
            Intrinsics.checkNotNull(var3);
            if (var3.getAnimationProgress() >= 1.0F) {
               this.popUI = null;
            }
         } else if (this.nowDisplayKey != null) {
            this.nowDisplayKey = null;
         } else {
            this.field_146297_k.func_147108_a((GuiScreen)null);
         }

      } else {
         PopUI var10000 = this.popUI;
         if (var10000 != null) {
            var10000.onKey(typedChar, keyCode);
         }

      }
   }

   public boolean func_73868_f() {
      return false;
   }

   private static final void updateAllKeys$lambda_0/* $FF was: updateAllKeys$lambda-0*/(KeyBindManager this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");

      for(KeyInfo key : this$0.keys) {
         key.update();
      }

   }
}
