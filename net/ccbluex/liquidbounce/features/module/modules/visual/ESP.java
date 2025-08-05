package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.RendererExtensionKt;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.WorldToScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

@ModuleInfo(
   name = "ESP",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001dH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/ESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "colorValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Ljava/awt/Color;", "csgoDirectLineValue", "", "csgoShowHealthValue", "csgoShowHeldItemValue", "csgoShowNameValue", "csgoWidthValue", "", "decimalFormat", "Ljava/text/DecimalFormat;", "modeValue", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "outlineWidthValue", "getColor", "entity", "Lnet/minecraft/entity/Entity;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"}
)
public final class ESP extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final Value outlineWidthValue;
   @NotNull
   private final Value csgoDirectLineValue;
   @NotNull
   private final Value csgoShowHealthValue;
   @NotNull
   private final Value csgoShowHeldItemValue;
   @NotNull
   private final Value csgoShowNameValue;
   @NotNull
   private final Value csgoWidthValue;
   @NotNull
   private final ListValue colorModeValue;
   @NotNull
   private final Value colorValue;
   @NotNull
   private final DecimalFormat decimalFormat;

   public ESP() {
      String[] var1 = new String[]{"Box", "OtherBox", "2D", "Real2D", "CSGO", "CSGO-Old", "Outline", "HealthLine"};
      this.modeValue = new ListValue("Mode", var1, "Outline");
      this.outlineWidthValue = (new FloatValue("Outline-Width", 3.0F, 0.5F, 5.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return ESP.this.getModeValue().equals("Outline");
         }
      });
      this.csgoDirectLineValue = (new BoolValue("CSGO-DirectLine", false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return ESP.this.getModeValue().equals("CSGO");
         }
      });
      this.csgoShowHealthValue = (new BoolValue("CSGO-ShowHealth", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return ESP.this.getModeValue().equals("CSGO");
         }
      });
      this.csgoShowHeldItemValue = (new BoolValue("CSGO-ShowHeldItem", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return ESP.this.getModeValue().equals("CSGO");
         }
      });
      this.csgoShowNameValue = (new BoolValue("CSGO-ShowName", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return ESP.this.getModeValue().equals("CSGO");
         }
      });
      this.csgoWidthValue = (new FloatValue("CSGOOld-Width", 2.0F, 0.5F, 5.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return ESP.this.getModeValue().equals("CSGO-Old");
         }
      });
      var1 = new String[]{"Name", "Armor", "Theme", "OFF"};
      this.colorModeValue = new ListValue("ColorMode", var1, "Name");
      Color var3 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(var3, "WHITE");
      this.colorValue = (new ColorValue("Color", var3, false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Intrinsics.areEqual((Object)ESP.this.colorModeValue.get(), (Object)"OFF");
         }
      });
      this.decimalFormat = new DecimalFormat("0.0");
   }

   @NotNull
   public final ListValue getModeValue() {
      return this.modeValue;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String var4 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      String mode = var4;
      Matrix4f mvMatrix = WorldToScreen.getMatrix(2982);
      Matrix4f projectionMatrix = WorldToScreen.getMatrix(2983);
      boolean need2dTranslate = Intrinsics.areEqual((Object)var4, (Object)"csgo") || Intrinsics.areEqual((Object)var4, (Object)"real2d") || Intrinsics.areEqual((Object)var4, (Object)"csgo-old");
      if (need2dTranslate) {
         GL11.glPushAttrib(8192);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glOrtho((double)0.0F, (double)MinecraftInstance.mc.field_71443_c, (double)MinecraftInstance.mc.field_71440_d, (double)0.0F, (double)-1.0F, (double)1.0F);
         GL11.glMatrixMode(5888);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glDisable(2929);
         GL11.glBlendFunc(770, 771);
         GlStateManager.func_179098_w();
         GlStateManager.func_179132_a(true);
         GL11.glLineWidth(1.0F);
      }

      for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
         EntityUtils var10000 = EntityUtils.INSTANCE;
         Intrinsics.checkNotNullExpressionValue(entity, "entity");
         if (var10000.isSelected(entity, true)) {
            Color color;
            label163: {
               EntityLivingBase entityLiving = (EntityLivingBase)entity;
               color = this.getColor((Entity)entityLiving);
               switch (mode.hashCode()) {
                  case -1518963662:
                     if (!mode.equals("csgo-old")) {
                        continue;
                     }
                     break;
                  case -1171135301:
                     if (!mode.equals("otherbox")) {
                        continue;
                     }
                     break label163;
                  case -1106245566:
                     if (mode.equals("outline")) {
                        RenderUtils.drawEntityBox(entity, color, true, false, ((Number)this.outlineWidthValue.get()).floatValue());
                     }
                     continue;
                  case -934973296:
                     if (!mode.equals("real2d")) {
                        continue;
                     }
                     break;
                  case 1650:
                     if (mode.equals("2d")) {
                        RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
                        Timer timer = MinecraftInstance.mc.field_71428_T;
                        double posX = entityLiving.field_70142_S + (entityLiving.field_70165_t - entityLiving.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
                        double posY = entityLiving.field_70137_T + (entityLiving.field_70163_u - entityLiving.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
                        double posZ = entityLiving.field_70136_U + (entityLiving.field_70161_v - entityLiving.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
                        RenderUtils.draw2D(entityLiving, posX, posY, posZ, color.getRGB(), Color.BLACK.getRGB());
                     }
                     continue;
                  case 97739:
                     if (!mode.equals("box")) {
                        continue;
                     }
                     break label163;
                  case 3063128:
                     if (!mode.equals("csgo")) {
                        continue;
                     }
                     break;
                  case 908534864:
                     if (mode.equals("healthline")) {
                        float r = ((EntityLivingBase)entity).func_110143_aJ() / ((EntityLivingBase)entity).func_110138_aP();
                        GL11.glPushMatrix();
                        RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
                        Timer timer = MinecraftInstance.mc.field_71428_T;
                        GL11.glTranslated(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c - 0.2, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d);
                        GL11.glRotated((double)(-MinecraftInstance.mc.func_175598_ae().field_78735_i), (double)0.0F, (double)1.0F, (double)0.0F);
                        int[] boxVertices = new int[]{2896, 2929};
                        RenderUtils.disableGlCap(boxVertices);
                        GL11.glScalef(0.03F, 0.03F, 0.03F);
                        Gui.func_73734_a(21, -1, 26, 75, Color.black.getRGB());
                        Gui.func_73734_a(22, (int)((float)74 * r), 25, 74, Color.darkGray.getRGB());
                        Gui.func_73734_a(22, 0, 25, (int)((float)74 * r), BlendUtils.getHealthColor(((EntityLivingBase)entity).func_110143_aJ(), ((EntityLivingBase)entity).func_110138_aP()).getRGB());
                        RenderUtils.enableGlCap(3042);
                        GL11.glBlendFunc(770, 771);
                        RenderUtils.resetCaps();
                        GlStateManager.func_179117_G();
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                     }
                  default:
                     continue;
               }

               RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
               Timer timer = MinecraftInstance.mc.field_71428_T;
               AxisAlignedBB bb = entityLiving.func_174813_aQ().func_72317_d(-entityLiving.field_70165_t, -entityLiving.field_70163_u, -entityLiving.field_70161_v).func_72317_d(entityLiving.field_70142_S + (entityLiving.field_70165_t - entityLiving.field_70142_S) * (double)timer.field_74281_c, entityLiving.field_70137_T + (entityLiving.field_70163_u - entityLiving.field_70137_T) * (double)timer.field_74281_c, entityLiving.field_70136_U + (entityLiving.field_70161_v - entityLiving.field_70136_U) * (double)timer.field_74281_c).func_72317_d(-renderManager.field_78725_b, -renderManager.field_78726_c, -renderManager.field_78723_d);
               double[][] minX = new double[8][];
               double[] var16 = new double[]{bb.field_72340_a, bb.field_72338_b, bb.field_72339_c};
               minX[0] = var16;
               var16 = new double[]{bb.field_72340_a, bb.field_72337_e, bb.field_72339_c};
               minX[1] = var16;
               var16 = new double[]{bb.field_72336_d, bb.field_72337_e, bb.field_72339_c};
               minX[2] = var16;
               var16 = new double[]{bb.field_72336_d, bb.field_72338_b, bb.field_72339_c};
               minX[3] = var16;
               var16 = new double[]{bb.field_72340_a, bb.field_72338_b, bb.field_72334_f};
               minX[4] = var16;
               var16 = new double[]{bb.field_72340_a, bb.field_72337_e, bb.field_72334_f};
               minX[5] = var16;
               var16 = new double[]{bb.field_72336_d, bb.field_72337_e, bb.field_72334_f};
               minX[6] = var16;
               var16 = new double[]{bb.field_72336_d, bb.field_72338_b, bb.field_72334_f};
               minX[7] = var16;
               double[][] boxVertices = minX;
               float minX = (float)MinecraftInstance.mc.field_71443_c;
               float minY = (float)MinecraftInstance.mc.field_71440_d;
               float maxX = 0.0F;
               float maxY = 0.0F;
               int var19 = 0;
               int var20 = ((Object[])minX).length;

               while(var19 < var20) {
                  double[] boxVertex = boxVertices[var19];
                  ++var19;
                  Vector2f screenPos = WorldToScreen.worldToScreen(new Vector3f((float)boxVertex[0], (float)boxVertex[1], (float)boxVertex[2]), mvMatrix, projectionMatrix, MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71440_d);
                  if (screenPos != null) {
                     minX = RangesKt.coerceAtMost(screenPos.x, minX);
                     minY = RangesKt.coerceAtMost(screenPos.y, minY);
                     maxX = RangesKt.coerceAtLeast(screenPos.x, maxX);
                     maxY = RangesKt.coerceAtLeast(screenPos.y, maxY);
                  }
               }

               if (minX != (float)MinecraftInstance.mc.field_71443_c && minY != (float)MinecraftInstance.mc.field_71440_d && maxX != 0.0F && maxY != 0.0F) {
                  switch (mode) {
                     case "csgo-old":
                        float width = ((Number)this.csgoWidthValue.get()).floatValue() * ((maxY - minY) / (float)50);
                        RenderUtils.drawRect(minX - width, minY - width, minX, maxY, color);
                        RenderUtils.drawRect(maxX, minY - width, maxX + width, maxY + width, color);
                        RenderUtils.drawRect(minX - width, maxY, maxX, maxY + width, color);
                        RenderUtils.drawRect(minX - width, minY - width, maxX, minY, color);
                        float hpSize = (maxY + width - minY) * (entityLiving.func_110143_aJ() / entityLiving.func_110138_aP());
                        RenderUtils.drawRect(minX - width * (float)3, minY - width, minX - width * (float)2, maxY + width, Color.GRAY);
                        RenderUtils.drawRect(minX - width * (float)3, maxY - hpSize, minX - width * (float)2, maxY + width, ColorUtils.healthColor$default(ColorUtils.INSTANCE, entityLiving.func_110143_aJ(), entityLiving.func_110138_aP(), 0, 4, (Object)null));
                        continue;
                     case "real2d":
                        RenderUtils.drawRect(minX - (float)1, minY - (float)1, minX, maxY, color);
                        RenderUtils.drawRect(maxX, minY - (float)1, maxX + (float)1, maxY + (float)1, color);
                        RenderUtils.drawRect(minX - (float)1, maxY, maxX, maxY + (float)1, color);
                        RenderUtils.drawRect(minX - (float)1, minY - (float)1, maxX, minY, color);
                        continue;
                     case "csgo":
                        RenderUtils.glColor(color);
                        if (!(Boolean)this.csgoDirectLineValue.get()) {
                           float distX = (maxX - minX) / 3.0F;
                           float distY = (maxY - minY) / 3.0F;
                           GL11.glBegin(3);
                           GL11.glVertex2f(minX, minY + distY);
                           GL11.glVertex2f(minX, minY);
                           GL11.glVertex2f(minX + distX, minY);
                           GL11.glEnd();
                           GL11.glBegin(3);
                           GL11.glVertex2f(minX, maxY - distY);
                           GL11.glVertex2f(minX, maxY);
                           GL11.glVertex2f(minX + distX, maxY);
                           GL11.glEnd();
                           GL11.glBegin(3);
                           GL11.glVertex2f(maxX - distX, minY);
                           GL11.glVertex2f(maxX, minY);
                           GL11.glVertex2f(maxX, minY + distY);
                           GL11.glEnd();
                           GL11.glBegin(3);
                           GL11.glVertex2f(maxX - distX, maxY);
                           GL11.glVertex2f(maxX, maxY);
                           GL11.glVertex2f(maxX, maxY - distY);
                           GL11.glEnd();
                        } else {
                           GL11.glBegin(2);
                           GL11.glVertex2f(minX, minY);
                           GL11.glVertex2f(minX, maxY);
                           GL11.glVertex2f(maxX, maxY);
                           GL11.glVertex2f(maxX, minY);
                           GL11.glEnd();
                        }

                        if ((Boolean)this.csgoShowHealthValue.get()) {
                           float barHeight = (maxY - minY) * (1.0F - entityLiving.func_110143_aJ() / entityLiving.func_110138_aP());
                           GL11.glColor4f(0.1F, 1.0F, 0.1F, 1.0F);
                           GL11.glBegin(7);
                           GL11.glVertex2f(maxX + 2.0F, minY + barHeight);
                           GL11.glVertex2f(maxX + 2.0F, maxY);
                           GL11.glVertex2f(maxX + 3.0F, maxY);
                           GL11.glVertex2f(maxX + 3.0F, minY + barHeight);
                           GL11.glEnd();
                           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                           GL11.glEnable(3553);
                           GL11.glEnable(2929);
                           MinecraftInstance.mc.field_71466_p.func_175065_a(Intrinsics.stringPlus(this.decimalFormat.format(entityLiving.func_110143_aJ()), "§c❤"), maxX + 4.0F, minY + barHeight, ColorUtils.healthColor$default(ColorUtils.INSTANCE, entityLiving.func_110143_aJ(), entityLiving.func_110138_aP(), 0, 4, (Object)null).getRGB(), false);
                           GL11.glDisable(3553);
                           GL11.glDisable(2929);
                           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        }

                        if ((Boolean)this.csgoShowHeldItemValue.get()) {
                           ItemStack var52 = entityLiving.func_70694_bm();
                           if ((var52 == null ? null : var52.func_82833_r()) != null) {
                              GL11.glEnable(3553);
                              GL11.glEnable(2929);
                              FontRenderer var45 = MinecraftInstance.mc.field_71466_p;
                              Intrinsics.checkNotNullExpressionValue(var45, "mc.fontRendererObj");
                              String var46 = entityLiving.func_70694_bm().func_82833_r();
                              Intrinsics.checkNotNullExpressionValue(var46, "entityLiving.heldItem.displayName");
                              RendererExtensionKt.drawCenteredString(var45, var46, minX + (maxX - minX) / 2.0F, maxY + 2.0F, -1);
                              GL11.glDisable(3553);
                              GL11.glDisable(2929);
                           }
                        }

                        if ((Boolean)this.csgoShowNameValue.get()) {
                           GL11.glEnable(3553);
                           GL11.glEnable(2929);
                           FontRenderer var47 = MinecraftInstance.mc.field_71466_p;
                           Intrinsics.checkNotNullExpressionValue(var47, "mc.fontRendererObj");
                           String var48 = entityLiving.func_145748_c_().func_150254_d();
                           Intrinsics.checkNotNullExpressionValue(var48, "entityLiving.displayName.formattedText");
                           RendererExtensionKt.drawCenteredString(var47, var48, minX + (maxX - minX) / 2.0F, minY - 12.0F, -1);
                           GL11.glDisable(3553);
                           GL11.glDisable(2929);
                        }
                  }
               }
               continue;
            }

            RenderUtils.drawEntityBox(entity, color, !Intrinsics.areEqual((Object)mode, (Object)"otherbox"), true, ((Number)this.outlineWidthValue.get()).floatValue());
         }
      }

      if (need2dTranslate) {
         GL11.glEnable(2929);
         GL11.glMatrixMode(5889);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         GL11.glPopMatrix();
         GL11.glPopAttrib();
      }

   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String var4 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      float partialTicks = event.getPartialTicks();
      Map entityMap = (Map)(new HashMap());

      for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
         EntityUtils var10000 = EntityUtils.INSTANCE;
         Intrinsics.checkNotNullExpressionValue(entity, "entity");
         if (var10000.isSelected(entity, false)) {
            EntityLivingBase entityLiving = (EntityLivingBase)entity;
            Color color = this.getColor((Entity)entityLiving);
            if (!entityMap.containsKey(color)) {
               ArrayList var10 = new ArrayList();
               entityMap.put(color, var10);
            }

            var10000 = (EntityUtils)entityMap.get(color);
            Intrinsics.checkNotNull(var10000);
            ((ArrayList)var10000).add(entityLiving);
         }
      }

   }

   @NotNull
   public final Color getColor(@NotNull Entity entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      if (entity instanceof EntityLivingBase) {
         if (((EntityLivingBase)entity).field_70737_aN > 0) {
            Color var9 = Color.RED;
            Intrinsics.checkNotNullExpressionValue(var9, "RED");
            return var9;
         }

         if (EntityUtils.INSTANCE.isFriend(entity)) {
            Color var8 = Color.BLUE;
            Intrinsics.checkNotNullExpressionValue(var8, "BLUE");
            return var8;
         }

         if (Intrinsics.areEqual((Object)this.colorModeValue.get(), (Object)"Name")) {
            String var3 = ((EntityLivingBase)entity).func_145748_c_().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(var3, "entity.displayName.formattedText");
            char[] var4 = var3.toCharArray();
            Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toCharArray()");
            char[] chars = var4;
            int var10 = 0;
            int var12 = var4.length;

            while(var10 < var12) {
               int i = var10++;
               if (chars[i] == 167 && i + 1 < chars.length) {
                  int index = GameFontRenderer.Companion.getColorIndex(chars[i + 1]);
                  if (index >= 0 && index <= 15) {
                     return new Color(ColorUtils.hexColors[index]);
                  }
               }
            }
         } else if (Intrinsics.areEqual((Object)this.colorModeValue.get(), (Object)"Armor")) {
            if (entity instanceof EntityPlayer) {
               ItemStack var10000 = ((EntityPlayer)entity).field_71071_by.field_70460_b[3];
               if (var10000 == null) {
                  return new Color(Integer.MAX_VALUE);
               }

               ItemStack entityHead = var10000;
               if (entityHead.func_77973_b() instanceof ItemArmor) {
                  Item var13 = entityHead.func_77973_b();
                  if (var13 == null) {
                     throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
                  }

                  ItemArmor entityItemArmor = (ItemArmor)var13;
                  return new Color(entityItemArmor.func_82814_b(entityHead));
               }
            }
         } else if (Intrinsics.areEqual((Object)this.colorModeValue.get(), (Object)"Theme")) {
            return ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null);
         }
      }

      return (Color)this.colorValue.get();
   }
}
