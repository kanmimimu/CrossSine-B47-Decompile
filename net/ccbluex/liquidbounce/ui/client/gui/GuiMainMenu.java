package net.ccbluex.liquidbounce.ui.client.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J \u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0007H\u0016J \u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u0016H\u0014R*\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/GuiMainMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/minecraft/client/gui/GuiYesNoCallback;", "()V", "hoverAnimMap", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "hoverTextAlphaMap", "hoverYOffsetMap", "jdkCancel", "", "jdkFailed", "radiusAnimMap", "shouldDownload", "time", "checkJdkVersion", "", "downloadJdk", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "mouseClicked", "mouseButton", "CrossSine"}
)
public final class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
   @NotNull
   private final HashMap hoverAnimMap = new HashMap();
   @NotNull
   private final HashMap radiusAnimMap = new HashMap();
   @NotNull
   private final HashMap hoverTextAlphaMap = new HashMap();
   @NotNull
   private final HashMap hoverYOffsetMap = new HashMap();
   private float time;
   private boolean shouldDownload;
   private boolean jdkFailed;
   private boolean jdkCancel;

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.time += partialTicks;
      int h = this.field_146295_m;
      int w = this.field_146294_l;
      RenderUtils.drawImage((ResourceLocation)(new ResourceLocation("crosssine/background.png")), -30, -30, this.field_146294_l + 60, this.field_146295_m + 60);
      ParticleUtils.INSTANCE.drawParticles(mouseX, mouseY);
      Fonts.font40SemiBold.drawString("by shxp3", 2.0F, (float)(this.field_146295_m - Fonts.font40SemiBold.getHeight()) - 1.0F, Color.white.getRGB());
      String[] var7 = new String[]{"singleplayer", "multiplayer", "settings", "altmanager", "quit"};
      String[] buttons = var7;
      int iconWidth = 25;
      int iconHeight = 25;
      int var9 = 0;
      int var10 = var7.length;

      while(var9 < var10) {
         int index = var9;
         String name = buttons[var9];
         ++var9;
         int iconX = this.field_146294_l / 5 * index + this.field_146294_l / 7 / 2;
         int iconY = this.field_146295_m / 2;
         boolean isHovered = (iconX <= mouseX ? mouseX <= iconX + iconWidth : false) && (iconY <= mouseY ? mouseY <= iconY + iconHeight : false);
         float animationSpeed = 0.3F;
         Object var18 = this.hoverAnimMap.getOrDefault(name, 0.0F);
         Intrinsics.checkNotNullExpressionValue(var18, "hoverAnimMap.getOrDefault(name, 0f)");
         float prevAnim = ((Number)var18).floatValue();
         float targetLength = isHovered ? (float)iconWidth + 4.0F : 0.0F;
         float updatedAnim = prevAnim + (targetLength - prevAnim) * animationSpeed;
         Map var20 = (Map)this.hoverAnimMap;
         Float var21 = updatedAnim;
         var20.put(name, var21);
         Object var40 = this.radiusAnimMap.getOrDefault(name, 0.0F);
         Intrinsics.checkNotNullExpressionValue(var40, "radiusAnimMap.getOrDefault(name, 0f)");
         float prevRadius = ((Number)var40).floatValue();
         float targetRadius = isHovered ? 1.0F : 0.0F;
         float updatedRadius = prevRadius + (targetRadius - prevRadius) * animationSpeed;
         Map var23 = (Map)this.radiusAnimMap;
         Float var24 = updatedRadius;
         var23.put(name, var24);
         Object var43 = this.hoverTextAlphaMap.getOrDefault(name, 0.0F);
         Intrinsics.checkNotNullExpressionValue(var43, "hoverTextAlphaMap.getOrDefault(name, 0f)");
         float prevAlpha = ((Number)var43).floatValue();
         float targetAlpha = isHovered ? 255.0F : 0.0F;
         float updatedAlpha = prevAlpha + (targetAlpha - prevAlpha) * 0.2F;
         Map var26 = (Map)this.hoverTextAlphaMap;
         Float var27 = updatedAlpha;
         var26.put(name, var27);
         Object var46 = this.hoverYOffsetMap.getOrDefault(name, 0.0F);
         Intrinsics.checkNotNullExpressionValue(var46, "hoverYOffsetMap.getOrDefault(name, 0f)");
         float currentOffset = ((Number)var46).floatValue();
         float targetOffset = isHovered ? (float)Math.sin(((double)this.time * 0.05 + (double)index) * Math.PI) * 4.0F : 0.0F;
         float updatedOffset = currentOffset + (targetOffset - currentOffset) * 0.08F;
         Map var29 = (Map)this.hoverYOffsetMap;
         Float var30 = updatedOffset;
         var29.put(name, var30);
         if (updatedAnim > 0.5F) {
            float animX = (float)iconX + (float)iconWidth / 2.0F - updatedAnim / 2.0F;
            float barY1 = (float)(iconY + iconHeight) + 2.0F;
            float barY2 = barY1 + 2.0F;
            RenderUtils.drawRoundedRect(animX, barY1, animX + updatedAnim, barY2, updatedRadius, (new Color(255, 255, 255)).getRGB());
         }

         RenderUtils.drawImage(new ResourceLocation("crosssine/ui/icons/" + name + ".png"), iconX - (Intrinsics.areEqual((Object)name, (Object)"multiplayer") ? 5 : 0), (int)((float)iconY + updatedOffset - (float)(Intrinsics.areEqual((Object)name, (Object)"multiplayer") ? 5 : 0)), iconWidth + (Intrinsics.areEqual((Object)name, (Object)"multiplayer") ? 10 : 0), iconHeight + (Intrinsics.areEqual((Object)name, (Object)"multiplayer") ? 10 : 0));
         if (updatedAlpha > 10.0F) {
            int textAlpha = (int)RangesKt.coerceIn(updatedAlpha, 0.0F, 255.0F);
            int textColor = textAlpha << 24 | 16777215;
            String var10000;
            if (((CharSequence)name).length() > 0) {
               char it = name.charAt(0);
               int var34 = 0;
               it = Character.toUpperCase(it);
               byte var35 = 1;
               String var36 = name.substring(var35);
               Intrinsics.checkNotNullExpressionValue(var36, "this as java.lang.String).substring(startIndex)");
               var10000 = it + var36;
            } else {
               var10000 = name;
            }

            String text = var10000;
            Fonts.font40SemiBold.func_175065_a(text, (float)(iconX + iconWidth / 2 - Fonts.font40SemiBold.func_78256_a(text) / 2), (float)(iconY - 10) + updatedOffset, textColor, true);
         }
      }

      this.checkJdkVersion();
      if (this.jdkFailed) {
         RenderUtils.drawRect(0.0F, 0.0F, (float)this.field_146294_l, (float)this.field_146295_m, new Color(0, 0, 0, 100));
         RenderUtils.drawRoundedRect((float)this.field_146294_l / 2.0F - 150.0F, (float)this.field_146295_m / 2.0F - 50.0F, 300.0F, 100.0F, 7.0F, (new Color(50, 50, 50, 255)).getRGB(), 6.0F, (new Color(80, 80, 80, 255)).getRGB());
         Fonts.font40Bold.drawCenteredString(EnumChatFormatting.RED + "Outdated Java Runtime Environment " + EnumChatFormatting.WHITE + '(' + System.getProperty("java.version") + ')', (float)this.field_146294_l / 2.0F, (float)this.field_146295_m / 2.0F - 40.0F, (new Color(255, 255, 255)).getRGB());
         Fonts.font32Bold.drawString("Many features will not be available, please install java 1.8", (float)this.field_146294_l / 2.0F - 140.0F, (float)this.field_146295_m / 2.0F - 10.0F, (new Color(255, 255, 255)).getRGB());
         RenderUtils.drawRoundedRect((float)this.field_146294_l / 2.0F - 140.0F, (float)this.field_146295_m / 2.0F + 20.0F, (float)this.field_146294_l / 2.0F - 10.0F, (float)this.field_146295_m / 2.0F + 40.0F, 3.0F, (new Color(90, 90, 90, 255)).getRGB());
         Fonts.font32SemiBold.drawCenteredString("Download", (float)this.field_146294_l / 2.0F - 75.0F, (float)this.field_146295_m / 2.0F + 28.0F, (new Color(10, 255, 10)).getRGB());
         RenderUtils.drawRoundedRect((float)this.field_146294_l / 2.0F + 10.0F, (float)this.field_146295_m / 2.0F + 20.0F, (float)this.field_146294_l / 2.0F + 140.0F, (float)this.field_146295_m / 2.0F + 40.0F, 3.0F, (new Color(90, 90, 90, 255)).getRGB());
         Fonts.font32SemiBold.drawCenteredString("Ignore", (float)this.field_146294_l / 2.0F + 75.0F, (float)this.field_146295_m / 2.0F + 28.0F, (new Color(255, 255, 10)).getRGB());
      }

      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      String[] var5 = new String[]{"singleplayer", "multiplayer", "settings", "altmanager", "quit"};
      String[] buttons = var5;
      int iconWidth = 25;
      int iconHeight = 25;
      if (!this.jdkFailed || this.jdkCancel) {
         int var7 = 0;
         int var8 = var5.length;

         while(var7 < var8) {
            int index = var7;
            ++var7;
            int iconX = this.field_146294_l / 5 * index + this.field_146294_l / 7 / 2;
            int iconY = this.field_146295_m / 2;
            boolean isHovered = (iconX <= mouseX ? mouseX <= iconX + iconWidth : false) && (iconY <= mouseY ? mouseY <= iconY + iconHeight : false);
            if (isHovered) {
               switch (name) {
                  case "singleplayer":
                     this.field_146297_k.func_147108_a((GuiScreen)(new GuiSelectWorld(this)));
                     break;
                  case "quit":
                     this.field_146297_k.func_71400_g();
                     break;
                  case "multiplayer":
                     this.field_146297_k.func_147108_a((GuiScreen)(new GuiMultiplayer(this)));
                     break;
                  case "settings":
                     this.field_146297_k.func_147108_a((GuiScreen)(new GuiOptions(this, this.field_146297_k.field_71474_y)));
                     break;
                  case "altmanager":
                     this.field_146297_k.func_147108_a(new GuiAltManager(this));
               }

               return;
            }
         }
      }

      if (this.jdkFailed && !this.jdkCancel) {
         if (MouseUtils.mouseWithinBounds(mouseX, mouseY, (float)this.field_146294_l / 2.0F - 140.0F, (float)this.field_146295_m / 2.0F + 20.0F, (float)this.field_146294_l / 2.0F - 10.0F, (float)this.field_146295_m / 2.0F + 40.0F)) {
            this.downloadJdk();
         }

         if (MouseUtils.mouseWithinBounds(mouseX, mouseY, (float)this.field_146294_l / 2.0F + 10.0F, (float)this.field_146295_m / 2.0F + 20.0F, (float)this.field_146294_l / 2.0F + 140.0F, (float)this.field_146295_m / 2.0F + 40.0F)) {
            this.jdkCancel = true;
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   private final void downloadJdk() {
      (new Thread(GuiMainMenu::downloadJdk$lambda-1)).start();
   }

   private final void checkJdkVersion() {
      String javaVersion = System.getProperty("java.version");
      if (!Intrinsics.areEqual((Object)javaVersion, (Object)"1.8.0_202") && !Intrinsics.areEqual((Object)javaVersion, (Object)"1.8.0_442") && !this.jdkCancel && !this.jdkFailed) {
         this.jdkFailed = true;
         if (this.shouldDownload) {
            this.downloadJdk();
         }
      }

   }

   private static final void downloadJdk$lambda_1/* $FF was: downloadJdk$lambda-1*/(GuiMainMenu this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");

      try {
         HttpUtils.INSTANCE.openWebpage("https://drive.usercontent.google.com/download?id=1cHjivGZb3mq4eOJbZ0poT5Y1b6gvcC__&export=download&authuser=0&confirm=t&uuid=a9b9686e-749a-4330-88bd-1b9b0e2bd51c&at=APcmpox1rSyhvujugNZ18llmcOYm%3A1745940666795");
         Thread.sleep(3000L);
         this$0.field_146297_k.func_71400_g();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
}
