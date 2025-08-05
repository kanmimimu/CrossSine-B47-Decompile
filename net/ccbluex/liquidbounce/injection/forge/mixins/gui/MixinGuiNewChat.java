package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.ChatEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.GuiChatModule;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiNewChat.class})
public abstract class MixinGuiNewChat {
   private final HashMap stringCache = new HashMap();
   private float displayPercent;
   private float animationPercent = 0.0F;
   private int lineBeingDrawn;
   private int newLines;
   @Shadow
   @Final
   private Minecraft field_146247_f;
   @Shadow
   @Final
   private List field_146253_i;
   @Shadow
   private int field_146250_j;
   @Shadow
   private boolean field_146251_k;
   @Shadow
   @Final
   private List field_146252_h;
   private int line;
   private GuiChatModule guiChatModule;
   private String lastMessage;
   private int sameMessageAmount;

   @Shadow
   public abstract int func_146232_i();

   @Shadow
   public abstract boolean func_146241_e();

   @Shadow
   public abstract float func_146244_h();

   @Shadow
   public abstract int func_146228_f();

   @Shadow
   public abstract void func_146242_c(int var1);

   @Shadow
   public abstract void func_146229_b(int var1);

   @Shadow
   public abstract void func_146234_a(IChatComponent var1, int var2);

   private void checkHud() {
      if (this.guiChatModule == null) {
         this.guiChatModule = (GuiChatModule)CrossSine.moduleManager.getModule(GuiChatModule.class);
      }

   }

   @Redirect(
      method = {"deleteChatLine"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/ChatLine;getChatLineID()I"
)
   )
   private int checkIfChatLineIsNull(ChatLine instance) {
      return instance == null ? -1 : instance.func_74539_c();
   }

   @Overwrite
   public void func_146227_a(IChatComponent chatComponent) {
      GuiChatModule guiChatModule = (GuiChatModule)CrossSine.moduleManager.getModule(GuiChatModule.class);
      if (guiChatModule.getState() && (Boolean)guiChatModule.getChatCombine().get()) {
         if ((Boolean)guiChatModule.getChatCombine().get()) {
            String text = this.fixString(chatComponent.func_150254_d());
            if (text.equals(this.lastMessage)) {
               Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146242_c(this.line);
               ++this.sameMessageAmount;
               chatComponent.func_150258_a(ChatFormatting.WHITE + " [x" + this.sameMessageAmount + "]");
            } else {
               this.sameMessageAmount = 1;
            }

            this.lastMessage = text;
            ++this.line;
            if (this.line > 256) {
               this.line = 0;
            }

            this.func_146234_a(chatComponent, this.line);
         }
      } else {
         this.func_146234_a(chatComponent, 0);
      }

   }

   @Inject(
      method = {"printChatMessageWithOptionalDeletion"},
      at = {@At("HEAD")}
   )
   private void resetPercentage(CallbackInfo ci) {
      this.displayPercent = 0.0F;
   }

   @Overwrite
   public void func_146230_a(int updateCounter) {
      this.checkHud();
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, -12.0F, 0.0F);
      if (this.field_146247_f.field_71474_y.field_74343_n != EnumChatVisibility.HIDDEN) {
         int i = this.func_146232_i();
         boolean flag = false;
         int j = 0;
         int k = this.field_146253_i.size();
         float f = this.field_146247_f.field_71474_y.field_74357_r * 0.9F + 0.1F;
         if (k > 0) {
            if (this.func_146241_e()) {
               flag = true;
            }

            if (!this.field_146251_k && this.guiChatModule.getState()) {
               if (this.displayPercent < 1.0F && (Boolean)this.guiChatModule.getChatAnimValue().get()) {
                  this.displayPercent += 0.005F * (float)RenderUtils.deltaTime;
                  this.displayPercent = MathHelper.func_76131_a(this.displayPercent, 0.0F, 1.0F);
               }
            } else {
               this.displayPercent = 1.0F;
            }

            if (this.displayPercent < 1.0F && !(Boolean)this.guiChatModule.getChatAnimValue().get()) {
               this.displayPercent += 10.0F * (float)RenderUtils.deltaTime;
               this.displayPercent = MathHelper.func_76131_a(this.displayPercent, 0.0F, 1.0F);
            }

            float t = this.displayPercent;
            this.animationPercent = MathHelper.func_76131_a(1.0F - --t * t * t * t, 0.0F, 1.0F);
            float f1 = this.func_146244_h();
            int l = MathHelper.func_76123_f((float)this.func_146228_f() / f1);
            GlStateManager.func_179094_E();
            if (this.guiChatModule.getState()) {
               GlStateManager.func_179109_b(0.0F, (1.0F - this.animationPercent) * 9.0F * this.func_146244_h(), 0.0F);
            }

            GlStateManager.func_179109_b(2.0F, 20.0F, 0.0F);
            GlStateManager.func_179152_a(f1, f1, 1.0F);

            for(int i1 = 0; i1 + this.field_146250_j < this.field_146253_i.size() && i1 < i; ++i1) {
               ChatLine chatline = (ChatLine)this.field_146253_i.get(i1 + this.field_146250_j);
               this.lineBeingDrawn = i1 + this.field_146250_j;
               if (chatline != null) {
                  int j1 = updateCounter - chatline.func_74540_b();
                  if (j1 < 200 || flag) {
                     double d0 = (double)j1 / (double)200.0F;
                     d0 = (double)1.0F - d0;
                     d0 *= (double)10.0F;
                     d0 = MathHelper.func_151237_a(d0, (double)0.0F, (double)1.0F);
                     d0 *= d0;
                     int l1 = (int)((double)255.0F * d0);
                     if (flag) {
                        l1 = 255;
                     }

                     l1 = (int)((float)l1 * f);
                     ++j;
                     if (l1 > 3) {
                        int i2 = 0;
                        int j2 = -i1 * 9;
                        if (this.guiChatModule.getState() && (Boolean)this.guiChatModule.getChatRectValue().get()) {
                           if (this.lineBeingDrawn <= this.newLines && !flag) {
                              RenderUtils.drawRect((float)i2, (float)(j2 - 9), (float)(i2 + l + 4), (float)j2, (new Color(0.0F, 0.0F, 0.0F, this.animationPercent * ((float)d0 / 2.0F))).getRGB());
                           } else {
                              RenderUtils.drawRect((float)i2, (float)(j2 - 9), (float)(i2 + l + 4), (float)j2, l1 / 2 << 24);
                           }
                        }

                        GlStateManager.func_179117_G();
                        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
                        String s = this.fixString(chatline.func_151461_a().func_150254_d());
                        ChatEvent event = new ChatEvent(s);
                        GlStateManager.func_179147_l();
                        if (this.guiChatModule.getState() && this.lineBeingDrawn <= this.newLines) {
                           CrossSine.eventManager.callEvent(event);
                           this.field_146247_f.field_71466_p.func_175065_a(s, (float)i2, (float)(j2 - 8), (new Color(1.0F, 1.0F, 1.0F, this.animationPercent * (float)d0)).getRGB(), true);
                        } else {
                           this.field_146247_f.field_71466_p.func_175065_a(s, (float)i2, (float)(j2 - 8), 16777215 + (l1 << 24), true);
                        }

                        GlStateManager.func_179118_c();
                        GlStateManager.func_179084_k();
                     }
                  }
               }
            }

            if (flag) {
               int var21 = this.field_146247_f.field_71466_p.field_78288_b;
               GlStateManager.func_179109_b(-3.0F, 0.0F, 0.0F);
               int l2 = k * var21 + k;
               int j1 = j * var21 + j;
               int j3 = this.field_146250_j * j1 / k;
               int k1 = j1 * j1 / l2;
               if (l2 != j1) {
                  int l1 = j3 > 0 ? 170 : 96;
                  int l3 = this.field_146251_k ? 13382451 : 3355562;
                  RenderUtils.drawRect(0.0F, (float)(-j3), 2.0F, (float)(-j3 - k1), l3 + (l1 << 24));
                  RenderUtils.drawRect(2.0F, (float)(-j3), 1.0F, (float)(-j3 - k1), 13421772 + (l1 << 24));
               }
            }

            GlStateManager.func_179121_F();
         }
      }

      GlStateManager.func_179121_F();
   }

   private String fixString(String str) {
      if (this.stringCache.containsKey(str)) {
         return (String)this.stringCache.get(str);
      } else {
         str = str.replaceAll("\uf8ff", "");
         StringBuilder sb = new StringBuilder();

         for(char c : str.toCharArray()) {
            if (c > '！' && c < '｠') {
               sb.append(Character.toChars(c - 'ﻠ'));
            } else {
               sb.append(c);
            }
         }

         String result = sb.toString();
         this.stringCache.put(str, result);
         return result;
      }
   }

   @ModifyVariable(
      method = {"setChatLine"},
      at = @At("STORE"),
      ordinal = 0
   )
   private List setNewLines(List original) {
      this.newLines = original.size() - 1;
      return original;
   }

   @Overwrite
   public IChatComponent func_146236_a(int p_146236_1_, int p_146236_2_) {
      this.checkHud();
      if (!this.func_146241_e()) {
         return null;
      } else {
         ScaledResolution sc = new ScaledResolution(this.field_146247_f);
         int scaleFactor = sc.func_78325_e();
         float chatScale = this.func_146244_h();
         int mX = p_146236_1_ / scaleFactor - 3;
         int mY = p_146236_2_ / scaleFactor - 27 - 12;
         mX = MathHelper.func_76141_d((float)mX / chatScale);
         mY = MathHelper.func_76141_d((float)mY / chatScale);
         if (mX >= 0 && mY >= 0) {
            int lineCount = Math.min(this.func_146232_i(), this.field_146253_i.size());
            if (mX <= MathHelper.func_76141_d((float)this.func_146228_f() / this.func_146244_h()) && mY < this.field_146247_f.field_71466_p.field_78288_b * lineCount + lineCount) {
               int line = mY / this.field_146247_f.field_71466_p.field_78288_b + this.field_146250_j;
               if (line >= 0 && line < this.field_146253_i.size()) {
                  ChatLine chatLine = (ChatLine)this.field_146253_i.get(line);
                  int maxWidth = 0;

                  for(IChatComponent iterator : chatLine.func_151461_a()) {
                     if (iterator instanceof ChatComponentText) {
                        maxWidth += this.field_146247_f.field_71466_p.func_78256_a(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)iterator).func_150265_g(), false));
                        if (maxWidth > mX) {
                           return iterator;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }
}
