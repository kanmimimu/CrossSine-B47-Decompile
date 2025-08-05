package net.ccbluex.liquidbounce.features.module.modules.client.impl;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.special.NotificationUtil;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/impl/Notification;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "fadeStates", "Ljava/util/HashMap;", "Lnet/ccbluex/liquidbounce/features/special/NotificationUtil;", "", "offsetXStates", "offsetYStates", "draw", "", "CrossSine"}
)
public final class Notification extends MinecraftInstance {
   @NotNull
   public static final Notification INSTANCE = new Notification();
   @NotNull
   private static final HashMap fadeStates = new HashMap();
   @NotNull
   private static final HashMap offsetXStates = new HashMap();
   @NotNull
   private static final HashMap offsetYStates = new HashMap();

   private Notification() {
   }

   public final void draw() {
      int width = (new ScaledResolution(MinecraftInstance.mc)).func_78326_a();
      int height = (new ScaledResolution(MinecraftInstance.mc)).func_78328_b();
      if (!((Collection)CrossSine.INSTANCE.getNotification().getList()).isEmpty()) {
         float accumulatedOffsetY = 0.0F;
         Iterator var5 = CrossSine.INSTANCE.getNotification().getList().iterator();
         Intrinsics.checkNotNullExpressionValue(var5, "CrossSine.notification.list.iterator()");
         Iterator iterator = var5;

         while(iterator.hasNext()) {
            Object var6 = iterator.next();
            Intrinsics.checkNotNullExpressionValue(var6, "iterator.next()");
            NotificationUtil noti = (NotificationUtil)var6;
            Object var7 = fadeStates.getOrDefault(noti, 0.0F);
            Intrinsics.checkNotNullExpressionValue(var7, "fadeStates.getOrDefault(noti, 0F)");
            float fadeState = ((Number)var7).floatValue();
            Object var8 = offsetXStates.getOrDefault(noti, 200.0F);
            Intrinsics.checkNotNullExpressionValue(var8, "offsetXStates.getOrDefault(noti, 200F)");
            float offsetX = ((Number)var8).floatValue();
            Object var9 = offsetYStates.getOrDefault(noti, 50.0F);
            Intrinsics.checkNotNullExpressionValue(var9, "offsetYStates.getOrDefault(noti, 50F)");
            float offsetY = ((Number)var9).floatValue();
            float fadeDelta = 0.00525F * (float)RenderUtils.deltaTime * (System.currentTimeMillis() > noti.getSystem() + (long)noti.getTimer() ? 1.0F : -1.0F);
            float newFadeState = RangesKt.coerceIn(fadeState + fadeDelta, 0.0F, 1.0F);
            Map var11 = (Map)fadeStates;
            Float var12 = newFadeState;
            var11.put(noti, var12);
            float targetOffsetX = 0.0F;
            float offsetXDelta = (targetOffsetX - offsetX) * 0.15F;
            float newOffsetX = offsetX + offsetXDelta;
            Map var14 = (Map)offsetXStates;
            Float var15 = newOffsetX;
            var14.put(noti, var15);
            float offsetYDelta = (accumulatedOffsetY - offsetY) * 0.15F;
            float newOffsetY = offsetY + offsetYDelta;
            Map var17 = (Map)offsetYStates;
            Float var18 = newOffsetY;
            var17.put(noti, var18);
            float percent = (float)EaseUtils.INSTANCE.easeInCirc((double)newFadeState);
            if (newFadeState >= 1.0F && System.currentTimeMillis() > noti.getSystem() + (long)noti.getTimer()) {
               iterator.remove();
               fadeStates.remove(noti);
               offsetXStates.remove(noti);
               offsetYStates.remove(noti);
            } else {
               int contentWidth = Fonts.font40SemiBold.func_78256_a(noti.getContent());
               float x1 = (float)width - 8.0F - (float)contentWidth - 32.0F + newOffsetX + (32.0F + (float)contentWidth) * percent;
               float x2 = (float)width + newOffsetX + (8.0F + (float)contentWidth) * percent;
               float y1 = (float)height - 8.0F - (float)Fonts.font40SemiBold.getHeight() - 16.0F - newOffsetY;
               float y2 = (float)height - 4.0F - newOffsetY;
               float timeProgress = RangesKt.coerceIn((float)(System.currentTimeMillis() - noti.getSystem()) / (float)noti.getTimer(), 0.0F, 1.0F);
               float xchange = x1 + (x2 - x1) * ((float)1 - timeProgress);
               RenderUtils.drawBloomRoundedRect(x1, y1, xchange, y2, noti.getSystem() + (long)noti.getTimer() - System.currentTimeMillis() <= 50L ? 0.0F : 4.0F, 2.5F, new Color(255, 255, 255, 80 - (int)((float)80 * newFadeState)), RenderUtils.ShaderBloom.ROUNDONLY);
               RenderUtils.drawBloomRoundedRect(x1, y1, x2, y2, 4.0F, 2.5F, new Color(0, 0, 0, 120 - (int)((float)120 * newFadeState)), RenderUtils.ShaderBloom.BOTH);
               RenderUtils.drawImage(new ResourceLocation("crosssine/ui/notifications/" + noti.getType().name() + ".png"), width - 37 - contentWidth + (int)((float)(contentWidth + 4) * percent) + (int)newOffsetX, height - 32 - (int)newOffsetY, 27, 27, 1.0F - newFadeState);
               Fonts.font40SemiBold.drawString(noti.getContent(), (float)width - 8.0F - (float)contentWidth + ((float)contentWidth + 4.0F) * percent + newOffsetX, (float)height - 6.0F - (float)Fonts.font40SemiBold.getHeight() - newOffsetY, (new Color(255, 255, 255, 255 - (int)((float)255 * newFadeState))).getRGB());
               Fonts.font40SemiBold.drawString(noti.getTitle(), (float)width - 8.0F - (float)contentWidth + ((float)contentWidth + 4.0F) * percent + newOffsetX, (float)height - 8.0F - (float)(Fonts.font40SemiBold.getHeight() * 2) - newOffsetY, (new Color(255, 255, 255, 255 - (int)((float)255 * newFadeState))).getRGB());
               accumulatedOffsetY += 35.0F;
            }
         }
      }

   }
}
