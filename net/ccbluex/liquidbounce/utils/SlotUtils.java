package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0019\u001a\u00020\u0012J\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\b\u0010\u001c\u001a\u00020\u0006H\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0007J\b\u0010!\u001a\u00020\u001eH\u0002J\b\u0010\"\u001a\u00020\u001eH\u0002J\u000e\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u0012J\u000e\u0010%\u001a\u00020\u001e2\u0006\u0010&\u001a\u00020\u0012J\u001e\u0010'\u001a\u00020\u001e2\u0006\u0010&\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010)\u001a\u00020\u001eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\b\"\u0004\b\u0015\u0010\nR\u001a\u0010\u0016\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\b\"\u0004\b\u0018\u0010\n¨\u0006*"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/SlotUtils;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "animProgress", "", "changed", "", "getChanged", "()Z", "setChanged", "(Z)V", "module", "", "getModule", "()Ljava/lang/String;", "setModule", "(Ljava/lang/String;)V", "prevSlot", "", "prevSpoofing", "getPrevSpoofing", "setPrevSpoofing", "spoofing", "getSpoofing", "setSpoofing", "getSlot", "getStack", "Lnet/minecraft/item/ItemStack;", "handleEvents", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "renderItem", "renderRect", "scrollChangeItem", "currentSlot", "setPrevSlot", "slot", "setSlot", "spoof", "stopSet", "CrossSine"}
)
public final class SlotUtils implements Listenable {
   @NotNull
   public static final SlotUtils INSTANCE = new SlotUtils();
   private static int prevSlot = -1;
   private static boolean spoofing;
   private static boolean prevSpoofing;
   private static boolean changed;
   @NotNull
   private static String module = "";
   private static float animProgress;

   private SlotUtils() {
   }

   public final boolean getSpoofing() {
      return spoofing;
   }

   public final void setSpoofing(boolean var1) {
      spoofing = var1;
   }

   public final boolean getPrevSpoofing() {
      return prevSpoofing;
   }

   public final void setPrevSpoofing(boolean var1) {
      prevSpoofing = var1;
   }

   public final boolean getChanged() {
      return changed;
   }

   public final void setChanged(boolean var1) {
      changed = var1;
   }

   @NotNull
   public final String getModule() {
      return module;
   }

   public final void setModule(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      module = var1;
   }

   public final void setSlot(int slot, boolean spoof, @NotNull String module) {
      Intrinsics.checkNotNullParameter(module, "module");
      if (!changed) {
         prevSlot = MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.field_70461_c;
         changed = true;
      }

      MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.field_70461_c = slot;
      spoofing = spoof;
      prevSpoofing = spoofing;
      SlotUtils.module = module;
   }

   public final void stopSet() {
      if (changed) {
         if (prevSlot != -1) {
            MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.field_70461_c = prevSlot;
            prevSlot = -1;
         }

         spoofing = false;
         changed = false;
      }

      module = "";
   }

   public final int getSlot() {
      return spoofing ? prevSlot : MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.field_70461_c;
   }

   @Nullable
   public final ItemStack getStack() {
      return spoofing ? MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.func_70301_a(prevSlot) : MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.func_70448_g();
   }

   public final void setPrevSlot(int slot) {
      prevSlot = slot;
   }

   public final void scrollChangeItem(int currentSlot) {
      int currentSlot1 = currentSlot;
      if (currentSlot > 0) {
         currentSlot1 = 1;
      }

      if (currentSlot1 < 0) {
         currentSlot1 = -1;
      }

      if (changed) {
         for(prevSlot -= currentSlot1; prevSlot < 0; prevSlot += 9) {
         }

         while(prevSlot >= 9) {
            prevSlot -= 9;
         }
      }

   }

   public boolean handleEvents() {
      return true;
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)Interface.INSTANCE.getDynamicIsland().get() && prevSpoofing) {
         this.renderRect();
         GlStateManager.func_179117_G();
         if (MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.func_70448_g() != null) {
            this.renderItem();
            GlStateManager.func_179117_G();
         }

      }
   }

   private final void renderRect() {
      ItemStack itemStack = MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.func_70448_g();
      animProgress += 0.00375F * (float)RenderUtils.deltaTime * (changed && prevSlot != MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.field_70461_c ? 1.0F : -1.0F);
      animProgress = RangesKt.coerceIn(animProgress, 0.0F, 1.0F);
      double percent = EaseUtils.easeOutBack((double)animProgress);
      int width = (new ScaledResolution(MinecraftInstanceKt.getMc())).func_78326_a();
      float height = (float)(new ScaledResolution(MinecraftInstanceKt.getMc())).func_78328_b() + 3.0F;
      if (animProgress > 0.0F) {
         if (itemStack != null && itemStack.func_77973_b() instanceof ItemBlock) {
            String string = Intrinsics.stringPlus("Amount: ", Scaffold.INSTANCE.getState() ? Scaffold.INSTANCE.getBlockAmount() - Scaffold.INSTANCE.getPlaceTick() : itemStack.field_77994_a);
            float stringWidth = (float)Fonts.font35.func_78256_a(string) + (itemStack.field_77994_a < 10 ? 3.0F : 0.0F);
            if ((Boolean)Interface.INSTANCE.getShaderValue().get()) {
               BlurUtils.blurAreaRounded((float)width / 2.0F + -35.0F, height - 2.0F - 80.0F * (float)percent, (float)width / 2.0F + -35.0F + 32.0F + stringWidth, height - 2.0F - 80.0F * (float)percent + 20.0F, 5.0F, 10.0F);
            }

            RenderUtils.drawBloomRoundedRect((float)width / 2.0F + -35.0F, height - 2.0F - 80.0F * (float)percent, (float)width / 2.0F + -35.0F + 32.0F + stringWidth, height - 2.0F - 80.0F * (float)percent + 20.0F, 5.0F, 4.0F, new Color(0, 0, 0, (int)((double)80 * percent)), RenderUtils.ShaderBloom.BOTH);
            Fonts.font35.drawCenteredString(string, (float)(width / 2) + 15.0F, height + 5.0F - (float)((double)80.0F * percent), Color.WHITE.getRGB(), true);
         } else {
            if ((Boolean)Interface.INSTANCE.getShaderValue().get()) {
               BlurUtils.blurAreaRounded((float)width / 2.0F + -11.0F, height - 2.0F - 80.0F * (float)percent, (float)width / 2.0F + -11.0F + 20.0F, height - 2.0F - 80.0F * (float)percent + 20.0F, 5.0F, 10.0F);
            }

            RenderUtils.drawBloomRoundedRect((float)width / 2.0F + -11.0F, height - 2.0F - 80.0F * (float)percent, (float)width / 2.0F + -11.0F + 20.0F, height - 2.0F - 80.0F * (float)percent + 20.0F, 5.0F, 4.0F, new Color(0, 0, 0, (int)((double)80 * percent)), RenderUtils.ShaderBloom.BOTH);
         }
      }

      if (animProgress == 0.0F && prevSpoofing) {
         prevSpoofing = false;
      }

   }

   private final void renderItem() {
      int width = (new ScaledResolution(MinecraftInstanceKt.getMc())).func_78326_a();
      float height = (float)(new ScaledResolution(MinecraftInstanceKt.getMc())).func_78328_b() + 3.0F;
      ItemStack itemStack = MinecraftInstanceKt.getMc().field_71439_g.field_71071_by.func_70448_g();
      double percent = EaseUtils.easeOutBack((double)animProgress);
      if (itemStack != null && animProgress >= 0.0F) {
         if (itemStack.func_77973_b() instanceof ItemBlock) {
            RenderUtils.renderItemIcon(width / 2 - 30, (int)((double)height - (double)80.0F * percent), itemStack);
         } else {
            RenderUtils.renderItemIcon(width / 2 - 9, (int)((double)height - (double)80.0F * percent), itemStack);
         }
      }

   }
}
