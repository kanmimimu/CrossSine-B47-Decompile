package net.ccbluex.liquidbounce.features.module.modules.client.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/impl/PlayerStats;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "airAnim", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "armorAnim", "foodAnim", "healthAnim", "healthYellowAnim", "draw", "", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "sr", "Lnet/minecraft/client/gui/ScaledResolution;", "CrossSine"}
)
public final class PlayerStats extends MinecraftInstance {
   @NotNull
   public static final PlayerStats INSTANCE = new PlayerStats();
   @NotNull
   private static final Animation armorAnim;
   @NotNull
   private static final Animation healthAnim;
   @NotNull
   private static final Animation healthYellowAnim;
   @NotNull
   private static final Animation foodAnim;
   @NotNull
   private static final Animation airAnim;

   private PlayerStats() {
   }

   public final void draw(@NotNull EntityPlayer player, @NotNull ScaledResolution sr) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(sr, "sr");
      if (!MinecraftInstance.mc.field_71442_b.func_78758_h()) {
         int height = sr.func_78328_b();
         float halfWidth = (float)sr.func_78326_a() / 2.0F;
         int l6 = MinecraftInstance.mc.field_71439_g.func_70086_ai();
         int k7 = MathHelper.func_76143_f((double)(l6 - 2) * (double)10.0F / (double)300.0F);
         armorAnim.run((double)((float)player.func_70658_aO() / 20.0F));
         healthAnim.run((double)(player.func_110143_aJ() / 20.0F));
         healthYellowAnim.run((double)(MinecraftInstance.mc.field_71439_g.func_110139_bj() / 4.0F));
         foodAnim.run((double)((float)player.func_71024_bL().func_75116_a() / 20.0F));
         airAnim.run((double)((float)k7 / 10.0F));
         if (player.func_70658_aO() > 0) {
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0F, (float)height - 52.0F - (healthYellowAnim.value > (double)0.0F ? 12.0F : 0.0F), halfWidth - 6.0F, (float)height - 44.0F - (healthYellowAnim.value > (double)0.0F ? 12.0F : 0.0F), 2.5F, 1.8F, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0F, (float)height - 52.0F - (healthYellowAnim.value > (double)0.0F ? 12.0F : 0.0F), halfWidth - 91.0F + 4.0F + 81.0F * (float)armorAnim.value, (float)height - 44.0F - (healthYellowAnim.value > (double)0.0F ? 12.0F : 0.0F), 2.5F, 1.0F, new Color(100, 100, 255), RenderUtils.ShaderBloom.BOTH);
         }

         if (healthYellowAnim.value > (double)0.0F) {
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0F, (float)height - 52.0F, halfWidth - 6.0F, (float)height - 44.0F, 2.5F, 1.8F, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0F, (float)height - 52.0F, halfWidth - 91.0F + 4.0F + 81.0F * (float)healthYellowAnim.value, (float)height - 44.0F, 2.5F, 1.0F, new Color(255, 255, 20), RenderUtils.ShaderBloom.BOTH);
         }

         RenderUtils.drawBloomRoundedRect(halfWidth - 91.0F, (float)height - 40.0F, halfWidth - 6.0F, (float)height - 32.0F, 2.5F, 1.8F, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
         RenderUtils.drawBloomRoundedRect(halfWidth - 91.0F, (float)height - 40.0F, halfWidth - 91.0F + 4.0F + 81.0F * (float)healthAnim.value, (float)height - 32.0F, 2.5F, 1.0F, new Color(255, 10, 10), RenderUtils.ShaderBloom.BOTH);
         RenderUtils.drawBloomRoundedRect(halfWidth + 6.0F, (float)height - 40.0F, halfWidth + 6.0F + 85.0F, (float)height - 32.0F, 2.5F, 1.8F, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
         RenderUtils.drawBloomRoundedRect(halfWidth + 6.0F, (float)height - 40.0F, halfWidth + 6.0F + 4.0F + 81.0F * (float)foodAnim.value, (float)height - 32.0F, 2.5F, 1.8F, new Color(28, 167, 222), RenderUtils.ShaderBloom.BOTH);
         if (player.func_70055_a(Material.field_151586_h)) {
            airAnim.run((double)((float)k7 / 10.0F));
            RenderUtils.drawBloomRoundedRect(halfWidth + (float)6, (float)height - 52.0F, halfWidth + (float)6 + 85.0F, (float)height - 52.0F + 8.0F, 2.5F, 2.5F, new Color(43, 42, 43), RenderUtils.ShaderBloom.BOTH);
            RenderUtils.drawBloomRoundedRect(halfWidth + (float)6, (float)height - 52.0F, halfWidth + (float)6 + (float)85 * (float)airAnim.value, (float)height - 52.0F + 8.0F, 2.5F, 2.5F, new Color(28, 167, 222), RenderUtils.ShaderBloom.BOTH);
         }

      }
   }

   static {
      armorAnim = new Animation(Easing.LINEAR, 250L);
      healthAnim = new Animation(Easing.LINEAR, 250L);
      healthYellowAnim = new Animation(Easing.LINEAR, 250L);
      foodAnim = new Animation(Easing.LINEAR, 250L);
      airAnim = new Animation(Easing.LINEAR, 250L);
   }
}
