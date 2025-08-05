package net.ccbluex.liquidbounce.features.module.modules.client.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.TargetHUD;
import net.ccbluex.liquidbounce.features.module.modules.combat.InfiniteAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.BedAura;
import net.ccbluex.liquidbounce.features.module.modules.world.Disabler;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J(\u0010#\u001a\u00020 2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010$\u001a\u00020\f2\u0006\u0010%\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'H\u0002J2\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020\f2\u0006\u0010*\u001a\u00020\f2\u0006\u0010+\u001a\u00020\f2\u0006\u0010,\u001a\u00020\f2\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\f0\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/impl/DynamicIsland;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "alpha", "", "animAlpha", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "animPosX", "animPosX2", "animPosY", "animPosY2", "disabler", "", "getDisabler", "()Ljava/lang/Float;", "setDisabler", "(Ljava/lang/Float;)V", "Ljava/lang/Float;", "healthMap", "", "Lnet/minecraft/entity/EntityLivingBase;", "mainTarget", "getMainTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setMainTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "posX", "posX2", "posY", "posY2", "prevBlock", "draw", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "drawTH", "stringWidth", "target", "string", "", "set", "startX", "startY", "endX", "endY", "CrossSine"}
)
public final class DynamicIsland extends MinecraftInstance {
   @NotNull
   public static final DynamicIsland INSTANCE = new DynamicIsland();
   @Nullable
   private static Float disabler;
   @Nullable
   private static EntityLivingBase mainTarget;
   private static float posX;
   private static float posY;
   private static float posX2;
   private static float posY2;
   private static int alpha;
   @NotNull
   private static final Animation animPosX;
   @NotNull
   private static final Animation animPosY;
   @NotNull
   private static final Animation animPosX2;
   @NotNull
   private static final Animation animPosY2;
   @NotNull
   private static final Animation animAlpha;
   @NotNull
   private static final Map healthMap;
   private static float prevBlock;

   private DynamicIsland() {
   }

   @Nullable
   public final Float getDisabler() {
      return disabler;
   }

   public final void setDisabler(@Nullable Float var1) {
      disabler = var1;
   }

   @Nullable
   public final EntityLivingBase getMainTarget() {
      return mainTarget;
   }

   public final void setMainTarget(@Nullable EntityLivingBase var1) {
      mainTarget = var1;
   }

   public final void draw(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      animPosX.run((double)posX);
      animPosY.run((double)DynamicIsland.posY);
      animPosX2.run((double)posX2);
      animPosY2.run((double)posY2);
      animAlpha.run((double)alpha);
      if ((Boolean)Interface.INSTANCE.getShaderValue().get()) {
         BlurUtils.blurAreaRounded((float)animPosX.value + 1.0F, (float)animPosY.value + 1.0F, (float)animPosX2.value - 1.0F, (float)animPosY2.value - 1.0F, 8.0F, 10.0F);
      }

      RenderUtils.drawBloomRoundedRect((float)animPosX.value, (float)animPosY.value, (float)animPosX2.value, (float)animPosY2.value, 12.0F, 8.0F, 3.0F, new Color(10, 10, 10, (int)animAlpha.value), RenderUtils.ShaderBloom.BOTH);
      GlStateManager.func_179117_G();
      GL11.glPushMatrix();
      Stencil.write(false);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      RenderUtils.fastRoundedRect((float)animPosX.value, (float)animPosY.value, (float)animPosX2.value, (float)animPosY2.value, 0.0F);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      Stencil.erase(true);
      String string = "";
      int width = event.getScaledResolution().func_78326_a();
      float halfWidth = (float)width / 2.0F;
      mainTarget = MinecraftInstance.mc.field_71462_r instanceof GuiChat ? (EntityLivingBase)MinecraftInstance.mc.field_71439_g : (InfiniteAura.INSTANCE.getState() && InfiniteAura.INSTANCE.getLastTarget() != null ? InfiniteAura.INSTANCE.getLastTarget() : (Interface.INSTANCE.getAttackTarget() != null ? Interface.INSTANCE.getAttackTarget() : null));
      float stringWidth = 0.0F;
      if (!TargetHUD.INSTANCE.getState() && mainTarget != null) {
         EntityLivingBase var35 = mainTarget;
         Intrinsics.checkNotNull(var35);
         String var21 = var35.func_70005_c_();
         Intrinsics.checkNotNullExpressionValue(var21, "mainTarget!!.name");
         string = var21;
         float posY = 0.0F;
         if ((KillAura.INSTANCE.getState() || SilentAura.INSTANCE.getState()) && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat)) {
            for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
               EntityUtils var36 = EntityUtils.INSTANCE;
               Intrinsics.checkNotNullExpressionValue(entity, "entity");
               if (var36.isSelected(entity, true)) {
                  if (KillAura.INSTANCE.getState()) {
                     EntityPlayerSP var9 = MinecraftInstance.mc.field_71439_g;
                     Intrinsics.checkNotNullExpressionValue(var9, "mc.thePlayer");
                     if (EntityExtensionKt.getDistanceToEntityBox((Entity)var9, entity) <= (double)((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue()) {
                        GameFontRenderer var37 = Fonts.font40SemiBold;
                        String var27 = entity.func_70005_c_();
                        Intrinsics.checkNotNullExpressionValue(var27, "entity.name");
                        if ((float)var37.func_78256_a(var27) > stringWidth) {
                           var37 = Fonts.font40SemiBold;
                           var27 = entity.func_70005_c_();
                           Intrinsics.checkNotNullExpressionValue(var27, "entity.name");
                           stringWidth = (float)var37.func_78256_a(var27);
                        }

                        EntityLivingBase var45 = (EntityLivingBase)entity;
                        var27 = ((EntityLivingBase)entity).func_70005_c_();
                        Intrinsics.checkNotNullExpressionValue(var27, "entity.name");
                        this.drawTH(posY, stringWidth, var45, var27);
                        posY += 30.0F;
                     }
                  }

                  if (SilentAura.INSTANCE.getState()) {
                     EntityPlayerSP var30 = MinecraftInstance.mc.field_71439_g;
                     Intrinsics.checkNotNullExpressionValue(var30, "mc.thePlayer");
                     if (EntityExtensionKt.getDistanceToEntityBox((Entity)var30, entity) <= (double)((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue()) {
                        GameFontRenderer var39 = Fonts.font40SemiBold;
                        String var31 = entity.func_70005_c_();
                        Intrinsics.checkNotNullExpressionValue(var31, "entity.name");
                        if ((float)var39.func_78256_a(var31) > stringWidth) {
                           var39 = Fonts.font40SemiBold;
                           var31 = entity.func_70005_c_();
                           Intrinsics.checkNotNullExpressionValue(var31, "entity.name");
                           stringWidth = (float)var39.func_78256_a(var31);
                        }

                        EntityLivingBase var46 = (EntityLivingBase)entity;
                        var31 = ((EntityLivingBase)entity).func_70005_c_();
                        Intrinsics.checkNotNullExpressionValue(var31, "entity.name");
                        this.drawTH(posY, stringWidth, var46, var31);
                        posY += 30.0F;
                     }
                  }
               }
            }
         } else {
            float var43 = (float)Fonts.font40SemiBold.func_78256_a(string);
            EntityLivingBase var44 = mainTarget;
            Intrinsics.checkNotNull(var44);
            this.drawTH(0.0F, var43, var44, string);
         }
      } else if (Flight.INSTANCE.getState()) {
         string = "Flight";
         String string2 = "Speed : " + (new DecimalFormat("#.##")).format(MovementUtils.INSTANCE.getBps()) + " BPS";
         boolean var7 = Fonts.font40SemiBold.func_78256_a(string) < Fonts.font35SemiBold.func_78256_a(string2);
         set$default(this, halfWidth - (float)(var7 ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0F - (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0F : 13.0F) + (var7 ? 2.0F : 0.0F), 11.0F, halfWidth + (float)(var7 ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0F + (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0F : 13.0F) - (var7 ? 2.0F : 0.0F), 14.0F + (float)Fonts.font40SemiBold.getHeight() + 12.0F + 4.0F + (float)Fonts.font35SemiBold.getHeight(), 0, 16, (Object)null);
         Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)animPosY.value + 9.0F, (new Color(255, 255, 255, 255)).getRGB());
         Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)animPosY.value + 9.0F + (float)Fonts.font35SemiBold.getHeight() + 7.0F, (new Color(255, 255, 255, 255)).getRGB());
      } else if (Speed.INSTANCE.getState() && Speed.INSTANCE.getFlagged() && (Boolean)Speed.INSTANCE.getFlagCheck().get()) {
         string = Intrinsics.stringPlus("Disable Speed : ", (new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH))).format((double)(Speed.INSTANCE.getTimerMS().time + ((Number)Speed.INSTANCE.getFlagMS().get()).longValue() - System.currentTimeMillis()) / (double)1000.0F));
         set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F - 9.0F, 11.0F, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F + 9.0F, 14.0F + (float)Fonts.font40SemiBold.getHeight() + 12.0F, 0, 16, (Object)null);
         Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)animPosY.value + 9.0F, (new Color(255, 255, 255, 255)).getRGB());
      } else if (BedAura.INSTANCE.getState() && !BedAura.INSTANCE.getDelay().hasTimePassed(1000L)) {
         DecimalFormat d = new DecimalFormat("0", new DecimalFormatSymbols(Locale.ENGLISH));
         string = "BedAura";
         StringBuilder var34 = (new StringBuilder()).append("Blocks : ");
         String var41;
         if (BedAura.INSTANCE.getPos() == null) {
            var41 = "None";
         } else {
            Block var42 = BlockUtils.getBlock(BedAura.INSTANCE.getPos());
            Intrinsics.checkNotNull(var42);
            var41 = var42.func_149732_F();
         }

         String string2 = var34.append(var41).append(" | Progress : ").append(d.format(BedAura.INSTANCE.getAnimation().value * (double)100)).append('%').toString();
         set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F - 60.0F, 11.0F, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F + 60.0F, 14.0F + (float)Fonts.font40SemiBold.getHeight() + (float)Fonts.font35SemiBold.getHeight() + 2.0F + 12.0F + 7.0F, 0, 16, (Object)null);
         Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)animPosY.value + 9.0F, (new Color(255, 255, 255, 255)).getRGB());
         Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)animPosY.value + 9.0F + (float)Fonts.font40SemiBold.getHeight() + 2.0F, (new Color(255, 255, 255, 255)).getRGB());
         RenderUtils.drawRoundedRect((float)animPosX.value + 8.0F, (float)animPosY2.value - 12.0F, (float)animPosX2.value - 8.0F, (float)animPosY2.value - 5.0F, 3.5F, (new Color(50, 50, 50, 255)).getRGB());
         RenderUtils.drawGradientRoundedRect((float)animPosX.value + 8.0F, (float)animPosY2.value - 12.0F, (float)animPosX.value + (float)(animPosX2.value - animPosX.value - (double)8.0F) * (float)BedAura.INSTANCE.getAnimation().value, (float)animPosY2.value - 5.0F, 3, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 2, (Object)null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, (Object)null).getRGB());
      } else if (Scaffold.INSTANCE.getState() && MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
         string = "Scaffold";
         String string2 = Scaffold.INSTANCE.getBlockAmount() - Scaffold.INSTANCE.getPlaceTick() + " Blocks left - " + (new DecimalFormat("#.##")).format(MovementUtils.INSTANCE.getBps()) + " BPS";
         boolean var24 = Fonts.font40SemiBold.func_78256_a(string) < Fonts.font35SemiBold.func_78256_a(string2);
         set$default(this, halfWidth - (float)(var24 ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0F - (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0F : 13.0F) + (var24 ? 2.0F : 0.0F), 11.0F, halfWidth + (float)(var24 ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0F + (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0F : 13.0F) - (var24 ? 2.0F : 0.0F), 14.0F + (float)Fonts.font40SemiBold.getHeight() + 12.0F + 4.0F + (float)Fonts.font35SemiBold.getHeight() + 7.0F, 0, 16, (Object)null);
         Fonts.font40SemiBold.drawCenteredString(string, halfWidth + 10.0F, (float)animPosY.value + 9.0F, (new Color(255, 255, 255, 255)).getRGB());
         Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)animPosY.value + 9.0F + (float)Fonts.font35SemiBold.getHeight() + 7.0F, (new Color(255, 255, 255, 255)).getRGB());
         RenderUtils.renderItemIcon((int)halfWidth - 37, 15, MinecraftInstance.mc.field_71439_g.func_70694_bm());
         RenderUtils.drawRoundedRect((float)animPosX.value + 8.0F, (float)animPosY2.value - 12.0F, (float)animPosX2.value - 8.0F, (float)animPosY2.value - 5.0F, 3.5F, (new Color(50, 50, 50, 255)).getRGB());
         RenderUtils.drawGradientRoundedRect((float)animPosX.value + 8.0F, (float)animPosY2.value - 12.0F, (float)animPosX.value + (float)(animPosX2.value - animPosX.value - (double)8.0F) * prevBlock, (float)animPosY2.value - 5.0F, 3, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 2, (Object)null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, (Object)null).getRGB());
         prevBlock = AnimHelperKt.animSmooth(prevBlock, ((float)Scaffold.INSTANCE.getBlockAmount() - (float)Scaffold.INSTANCE.getPlaceTick()) / 64.0F, 0.001F);
      } else if (Disabler.INSTANCE.getState() && disabler != null) {
         string = "WatchDog Disabler";
         set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F - 70.0F, 11.0F, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F + 70.0F, 14.0F + (float)Fonts.font40SemiBold.getHeight() + 12.0F + 20.0F, 0, 16, (Object)null);
         Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)animPosY.value + 9.0F, (new Color(255, 255, 255, 255)).getRGB());
         RenderUtils.drawRoundedRect((float)animPosX.value + 8.0F, (float)animPosY2.value - 12.0F, (float)animPosX2.value - 8.0F, (float)animPosY2.value - 5.0F, 3.5F, (new Color(50, 50, 50, 255)).getRGB());
         float var10000 = (float)animPosX.value + 8.0F;
         float var10001 = (float)animPosY2.value - 12.0F;
         float var10002 = (float)animPosX.value;
         float var10003 = (float)(animPosX2.value - animPosX.value - (double)8.0F);
         Float var10004 = disabler;
         Intrinsics.checkNotNull(var10004);
         RenderUtils.drawGradientRoundedRect(var10000, var10001, var10002 + var10003 * var10004, (float)animPosY2.value - 5.0F, 3, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 2, (Object)null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, (Object)null).getRGB());
      } else if (SlotUtils.INSTANCE.getSpoofing() && MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g() != null) {
         string = MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? MinecraftInstance.mc.field_71439_g.func_70694_bm().field_77994_a + " Blocks left" : "";
         String string2 = Intrinsics.stringPlus("Using : ", SlotUtils.INSTANCE.getModule());
         boolean string2 = Fonts.font40SemiBold.func_78256_a(string) < Fonts.font35SemiBold.func_78256_a(string2);
         set$default(this, halfWidth - (float)(string2 ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0F - (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0F : 13.0F) + (string2 ? 2.0F : 0.0F), 11.0F, halfWidth + (float)(string2 ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0F + (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0F : 13.0F) - (string2 ? 2.0F : 0.0F), 14.0F + (float)Fonts.font40SemiBold.getHeight() + 12.0F + 4.0F + (float)Fonts.font35SemiBold.getHeight(), 0, 16, (Object)null);
         Fonts.font40SemiBold.drawCenteredString(string, halfWidth + 10.0F, (float)animPosY.value + 9.0F, (new Color(255, 255, 255, 255)).getRGB());
         Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)animPosY.value + 9.0F + (float)Fonts.font35SemiBold.getHeight() + 7.0F, (new Color(255, 255, 255, 255)).getRGB());
         RenderUtils.renderItemIcon(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? (int)halfWidth - 37 : (int)halfWidth - 8, 15, MinecraftInstance.mc.field_71439_g.func_70694_bm());
      } else {
         string = "CrossSine §f| " + MinecraftInstance.mc.field_71439_g.func_70005_c_() + " | " + ServerUtils.getRemoteIp() + " | " + Minecraft.func_175610_ah() + "fps";
         set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F - 9.0F, 11.0F, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0F + 9.0F, 14.0F + (float)Fonts.font40SemiBold.getHeight() + 12.0F, 0, 16, (Object)null);
         Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)animPosY.value + 9.0F, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 255, false, 4, (Object)null).getRGB());
      }

      GlStateManager.func_179117_G();
      Stencil.dispose();
      GL11.glPopMatrix();
   }

   private final void set(float startX, float startY, float endX, float endY, int alpha) {
      posX = startX;
      posY = startY;
      posX2 = endX;
      posY2 = endY;
      DynamicIsland.alpha = alpha;
   }

   // $FF: synthetic method
   static void set$default(DynamicIsland var0, float var1, float var2, float var3, float var4, int var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = 180;
      }

      var0.set(var1, var2, var3, var4, var5);
   }

   private final void drawTH(float posY, float stringWidth, EntityLivingBase target, String string) {
      int width = (new ScaledResolution(MinecraftInstance.mc)).func_78326_a();
      int height = (new ScaledResolution(MinecraftInstance.mc)).func_78328_b();
      float halfWidth = (float)width / 2.0F;
      float halfHeight = (float)height / 2.0F;
      float maxHealth = target.func_110138_aP();
      float currentHealth = RangesKt.coerceAtMost(target.func_110143_aJ(), maxHealth);
      Map $this$getOrPut$iv = healthMap;
      int $i$f$getOrPut = 0;
      Object value$iv = $this$getOrPut$iv.get(target);
      Object var10000;
      if (value$iv == null) {
         int var15 = 0;
         Object answer$iv = currentHealth;
         $this$getOrPut$iv.put(target, answer$iv);
         var10000 = answer$iv;
      } else {
         var10000 = value$iv;
      }

      float lastHealth = ((Number)var10000).floatValue();
      float speed = 0.2F;
      float animatedHealth = lastHealth + (currentHealth - lastHealth) * speed;
      Map var18 = healthMap;
      Float var20 = animatedHealth;
      var18.put(target, var20);
      this.set(halfWidth + 60.0F, halfHeight - 18.0F - posY / (float)2, halfWidth + 60.0F + 60.0F + stringWidth + 5.0F, halfHeight + 18.0F + posY / (float)2, 100);
      Fonts.font40SemiBold.drawCenteredString(string, (float)animPosX.value + ((float)animPosX2.value - (float)animPosX.value) / (float)2 + 15.0F, (float)animPosY.value + 7.0F + posY, (new Color(255, 255, 255, 255)).getRGB());
      Fonts.font40SemiBold.drawCenteredString(string, (float)animPosX.value + ((float)animPosX2.value - (float)animPosX.value) / (float)2 + 15.0F, (float)animPosY.value + 7.0F + posY, (new Color(255, 255, 255, 255)).getRGB());
      GL11.glPushMatrix();
      Stencil.write(false);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      RenderUtils.fastRoundedRect((float)animPosX.value + 6.0F, (float)animPosY.value + 5.0F + posY, (float)animPosX.value + 6.0F + 26.0F, (float)animPosY.value + 5.0F + 26.0F + posY, 6.0F);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      Stencil.erase(true);
      RenderUtils.drawHead(EntityExtensionKt.getSkin(target), (int)animPosX.value + 6, (int)animPosY.value + 5 + (int)posY, 26, 26, Color.WHITE.getRGB());
      GlStateManager.func_179117_G();
      Stencil.dispose();
      GL11.glPopMatrix();
      RenderUtils.drawRoundedRect((float)animPosX.value + 40.0F, (float)animPosY.value + 24.5F + posY, (float)animPosX2.value - 5.0F - ((float)animPosX.value + 40.0F) - 4.0F, 4.5F, 2.0F, (new Color(0, 0, 0, 200)).getRGB(), 1.0F, Color.WHITE.getRGB());
      float var21 = (float)animPosX.value + 40.0F;
      float var10001 = (float)animPosY.value + 24.5F + posY;
      float var10002 = (float)animPosX.value + 30.0F;
      float var10003 = (float)animPosX2.value - 5.0F - ((float)animPosX.value + 30.0F);
      Object var10004 = healthMap.get(target);
      Intrinsics.checkNotNull(var10004);
      RenderUtils.drawGradientRoundedRect(var21, var10001, var10002 + (var10003 * (((Number)var10004).floatValue() / target.func_110138_aP()) - 4.0F), (float)animPosY.value + 29.0F + posY, 2, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, (Object)null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, (Object)null).getRGB());
   }

   static {
      animPosX = new Animation(Easing.EASE_OUT_CIRC, 500L);
      animPosY = new Animation(Easing.EASE_OUT_CIRC, 500L);
      animPosX2 = new Animation(Easing.EASE_OUT_CIRC, 500L);
      animPosY2 = new Animation(Easing.EASE_OUT_CIRC, 500L);
      animAlpha = new Animation(Easing.EASE_OUT_CIRC, 1000L);
      healthMap = (Map)(new LinkedHashMap());
   }
}
