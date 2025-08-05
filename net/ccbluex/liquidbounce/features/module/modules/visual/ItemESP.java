package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
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
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "ItemESP",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0007H\u0002J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/ItemESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorThemeClient", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "colorValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Ljava/awt/Color;", "entityConvertedPointsMap", "", "Lnet/minecraft/entity/item/EntityItem;", "", "itemCount", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "nameTags", "onlyCount", "outlineWidth", "", "getColor", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"}
)
public final class ItemESP extends Module {
   @NotNull
   private final Map entityConvertedPointsMap = (Map)(new HashMap());
   @NotNull
   private final BoolValue nameTags = new BoolValue("NameTag", false);
   @NotNull
   private final Value itemCount = (new BoolValue("ItemCount", false)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)ItemESP.this.nameTags.get();
      }
   });
   @NotNull
   private final Value onlyCount = (new BoolValue("OnlyCount", false)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)ItemESP.this.nameTags.get();
      }
   });
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final Value outlineWidth;
   @NotNull
   private final Value colorValue;
   @NotNull
   private final BoolValue colorThemeClient;

   public ItemESP() {
      String[] var1 = new String[]{"Box", "OtherBox", "Outline", "LightBox"};
      this.modeValue = new ListValue("Mode", var1, "Box");
      this.outlineWidth = (new FloatValue("Outline-Width", 3.0F, 0.5F, 5.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return ItemESP.this.modeValue.equals("Outline");
         }
      });
      this.colorValue = (new ColorValue("Color", new Color(0, 255, 0), false, 4, (DefaultConstructorMarker)null)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)ItemESP.this.colorThemeClient.get();
         }
      });
      this.colorThemeClient = new BoolValue("ClientTheme", true);
   }

   private final Color getColor() {
      return (Boolean)this.colorThemeClient.get() ? ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null) : (Color)this.colorValue.get();
   }

   @EventTarget
   public final void onRender3D(@Nullable Render3DEvent event) {
      Color color = this.getColor();

      for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
         if (entity instanceof EntityItem || entity instanceof EntityArrow) {
            Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (var7) {
               case "otherbox":
                  RenderUtils.drawEntityBox(entity, color, false, true, ((Number)this.outlineWidth.get()).floatValue());
                  break;
               case "outline":
                  RenderUtils.drawEntityBox(entity, color, true, false, ((Number)this.outlineWidth.get()).floatValue());
                  break;
               case "box":
                  RenderUtils.drawEntityBox(entity, color, true, true, ((Number)this.outlineWidth.get()).floatValue());
            }
         }
      }

      if (StringsKt.equals((String)this.modeValue.get(), "LightBox", true)) {
         for(Entity o : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (o instanceof EntityItem) {
               double x = o.field_70165_t - MinecraftInstance.mc.func_175598_ae().field_78725_b;
               double y = o.field_70163_u + (double)0.5F - MinecraftInstance.mc.func_175598_ae().field_78726_c;
               double z = o.field_70161_v - MinecraftInstance.mc.func_175598_ae().field_78723_d;
               GL11.glEnable(3042);
               GL11.glLineWidth(2.0F);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
               GL11.glDisable(3553);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.2, y - 0.3, z - 0.2, x + 0.2, y - 0.4, z + 0.2));
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
               RenderUtils.drawBoundingBox(new AxisAlignedBB(x - 0.2, y - 0.3, z - 0.2, x + 0.2, y - 0.4, z + 0.2));
               GL11.glEnable(3553);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               GL11.glDisable(3042);
            }
         }
      }

      if (StringsKt.equals((String)this.modeValue.get(), "Exhibition", true)) {
         this.entityConvertedPointsMap.clear();
         float pTicks = MinecraftInstance.mc.field_71428_T.field_74281_c;

         for(Entity e2 : MinecraftInstance.mc.field_71441_e.func_72910_y()) {
            if (e2 instanceof EntityItem) {
               double x = e2.field_70142_S + (e2.field_70165_t - e2.field_70142_S) * (double)pTicks;
               double var10000 = -MinecraftInstance.mc.func_175598_ae().field_78730_l + 0.36;
               double y = e2.field_70137_T + (e2.field_70163_u - e2.field_70137_T) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78731_m;
               double z = e2.field_70136_U + (e2.field_70161_v - e2.field_70136_U) * (double)pTicks;
               var10000 = -MinecraftInstance.mc.func_175598_ae().field_78728_n + 0.36;
               double topY = (double)0.0F;
               double var13 = (double)e2.field_70131_O + 0.15;
               int var17 = 0;
               topY = var13;
               Unit var30 = Unit.INSTANCE;
               y += var13;
               double[] convertedPoints = RenderUtils.convertTo2D(x, y, z);
               double[] convertedPoints2 = RenderUtils.convertTo2D(x - 0.36, y, z - 0.36);
               double xd = (double)0.0F;
               boolean var18 = convertedPoints2 != null;
               if (_Assertions.ENABLED && !var18) {
                  String var54 = "Assertion failed";
                  throw new AssertionError(var54);
               }

               Intrinsics.checkNotNull(convertedPoints2);
               if (!(convertedPoints2[2] < (double)0.0F) && !(convertedPoints2[2] >= (double)1.0F)) {
                  x = e2.field_70142_S + (e2.field_70165_t - e2.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l - 0.36;
                  z = e2.field_70136_U + (e2.field_70161_v - e2.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n - 0.36;
                  double[] convertedPointsBottom = RenderUtils.convertTo2D(x, y, z);
                  y = e2.field_70137_T + (e2.field_70163_u - e2.field_70137_T) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78731_m - 0.05;
                  double[] convertedPointsx = RenderUtils.convertTo2D(x, y, z);
                  x = e2.field_70142_S + (e2.field_70165_t - e2.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l - 0.36;
                  z = e2.field_70136_U + (e2.field_70161_v - e2.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n + 0.36;
                  double[] convertedPointsTop1 = RenderUtils.convertTo2D(x, topY, z);
                  double[] convertedPointsx2 = RenderUtils.convertTo2D(x, y, z);
                  x = e2.field_70142_S + (e2.field_70165_t - e2.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l + 0.36;
                  z = e2.field_70136_U + (e2.field_70161_v - e2.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n + 0.36;
                  double[] convertedPointsz = RenderUtils.convertTo2D(x, y, z);
                  x = e2.field_70142_S + (e2.field_70165_t - e2.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l + 0.36;
                  z = e2.field_70136_U + (e2.field_70161_v - e2.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n - 0.36;
                  double[] convertedPointsTop2 = RenderUtils.convertTo2D(x, topY, z);
                  double[] convertedPointsz2 = RenderUtils.convertTo2D(x, y, z);
                  boolean var25 = convertedPoints != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var68 = "Assertion failed";
                     throw new AssertionError(var68);
                  }

                  var25 = convertedPointsx != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var67 = "Assertion failed";
                     throw new AssertionError(var67);
                  }

                  var25 = convertedPointsTop1 != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var66 = "Assertion failed";
                     throw new AssertionError(var66);
                  }

                  var25 = convertedPointsTop2 != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var65 = "Assertion failed";
                     throw new AssertionError(var65);
                  }

                  var25 = convertedPointsz2 != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var64 = "Assertion failed";
                     throw new AssertionError(var64);
                  }

                  var25 = convertedPointsz != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var63 = "Assertion failed";
                     throw new AssertionError(var63);
                  }

                  var25 = convertedPointsx2 != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var62 = "Assertion failed";
                     throw new AssertionError(var62);
                  }

                  var25 = convertedPointsBottom != null;
                  if (_Assertions.ENABLED && !var25) {
                     String var26 = "Assertion failed";
                     throw new AssertionError(var26);
                  }

                  Map var71 = this.entityConvertedPointsMap;
                  double[] var27 = new double[25];
                  Intrinsics.checkNotNull(convertedPoints);
                  var27[0] = convertedPoints[0];
                  var27[1] = convertedPoints[1];
                  var27[2] = xd;
                  var27[3] = convertedPoints[2];
                  Intrinsics.checkNotNull(convertedPointsBottom);
                  var27[4] = convertedPointsBottom[0];
                  var27[5] = convertedPointsBottom[1];
                  var27[6] = convertedPointsBottom[2];
                  Intrinsics.checkNotNull(convertedPointsx);
                  var27[7] = convertedPointsx[0];
                  var27[8] = convertedPointsx[1];
                  var27[9] = convertedPointsx[2];
                  Intrinsics.checkNotNull(convertedPointsx2);
                  var27[10] = convertedPointsx2[0];
                  var27[11] = convertedPointsx2[1];
                  var27[12] = convertedPointsx2[2];
                  Intrinsics.checkNotNull(convertedPointsz);
                  var27[13] = convertedPointsz[0];
                  var27[14] = convertedPointsz[1];
                  var27[15] = convertedPointsz[2];
                  Intrinsics.checkNotNull(convertedPointsz2);
                  var27[16] = convertedPointsz2[0];
                  var27[17] = convertedPointsz2[1];
                  var27[18] = convertedPointsz2[2];
                  Intrinsics.checkNotNull(convertedPointsTop1);
                  var27[19] = convertedPointsTop1[0];
                  var27[20] = convertedPointsTop1[1];
                  var27[21] = convertedPointsTop1[2];
                  Intrinsics.checkNotNull(convertedPointsTop2);
                  var27[22] = convertedPointsTop2[0];
                  var27[23] = convertedPointsTop2[1];
                  var27[24] = convertedPointsTop2[2];
                  var71.put(e2, var27);
               }
            }
         }
      }

      if ((Boolean)this.nameTags.get()) {
         for(Entity item : MinecraftInstance.mc.field_71441_e.func_72910_y()) {
            if (item instanceof EntityItem) {
               String string = !(Boolean)this.onlyCount.get() ? Intrinsics.stringPlus(((EntityItem)item).func_92059_d().func_82833_r(), (Boolean)this.itemCount.get() && ((EntityItem)item).func_92059_d().field_77994_a > 1 ? Intrinsics.stringPlus(" x", ((EntityItem)item).func_92059_d().field_77994_a) : String.valueOf(((EntityItem)item).func_92059_d().field_77994_a)) : "";
               GL11.glPushMatrix();
               GL11.glTranslated(item.field_70142_S + (item.field_70165_t - item.field_70142_S) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78725_b, item.field_70137_T + (item.field_70163_u - item.field_70137_T) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78726_c - 0.2, item.field_70136_U + (item.field_70161_v - item.field_70136_U) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78723_d);
               GL11.glRotated((double)(-MinecraftInstance.mc.func_175598_ae().field_78735_i), (double)0.0F, (double)1.0F, (double)0.0F);
               int[] ent = new int[]{2896, 2929};
               RenderUtils.disableGlCap(ent);
               GL11.glScalef(-0.03F, -0.03F, -0.03F);
               MinecraftInstance.mc.field_71466_p.func_175065_a(string, -6.0F, -30.0F, (new Color(255, 255, 255)).getRGB(), true);
               RenderUtils.enableGlCap(3042);
               GL11.glBlendFunc(770, 771);
               RenderUtils.resetCaps();
               GlStateManager.func_179117_G();
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            }
         }
      }

   }
}
