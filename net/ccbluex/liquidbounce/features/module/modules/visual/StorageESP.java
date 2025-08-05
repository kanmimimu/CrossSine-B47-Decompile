package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.Stealer;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "StorageESP",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/StorageESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "dispenserValue", "enderChestValue", "furnaceValue", "hopperValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "outlineWidthValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getColor", "Ljava/awt/Color;", "tileEntity", "Lnet/minecraft/tileentity/TileEntity;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"}
)
public final class StorageESP extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final Value outlineWidthValue;
   @NotNull
   private final BoolValue chestValue;
   @NotNull
   private final BoolValue enderChestValue;
   @NotNull
   private final BoolValue furnaceValue;
   @NotNull
   private final BoolValue dispenserValue;
   @NotNull
   private final BoolValue hopperValue;

   public StorageESP() {
      String[] var1 = new String[]{"Box", "OtherBox", "Outline", "ShaderOutline", "ShaderGlow", "2D", "WireFrame"};
      this.modeValue = new ListValue("Mode", var1, "Outline");
      this.outlineWidthValue = (new FloatValue("Outline-Width", 3.0F, 0.5F, 5.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return StorageESP.this.modeValue.equals("Outline");
         }
      });
      this.chestValue = new BoolValue("Chest", true);
      this.enderChestValue = new BoolValue("EnderChest", true);
      this.furnaceValue = new BoolValue("Furnace", true);
      this.dispenserValue = new BoolValue("Dispenser", true);
      this.hopperValue = new BoolValue("Hopper", true);
   }

   private final Color getColor(TileEntity tileEntity) {
      if ((Boolean)this.chestValue.get() && tileEntity instanceof TileEntityChest && !Stealer.INSTANCE.getAuraclickedBlocks().contains(tileEntity.func_174877_v())) {
         return new Color(0, 66, 255);
      } else if ((Boolean)this.enderChestValue.get() && tileEntity instanceof TileEntityEnderChest && !Stealer.INSTANCE.getAuraclickedBlocks().contains(tileEntity.func_174877_v())) {
         return Color.MAGENTA;
      } else if ((Boolean)this.furnaceValue.get() && tileEntity instanceof TileEntityFurnace) {
         return Color.BLACK;
      } else if ((Boolean)this.dispenserValue.get() && tileEntity instanceof TileEntityDispenser) {
         return Color.BLACK;
      } else {
         return (Boolean)this.hopperValue.get() && tileEntity instanceof TileEntityHopper ? Color.GRAY : null;
      }
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");

      try {
         String mode = (String)this.modeValue.get();
         float gamma = MinecraftInstance.mc.field_71474_y.field_74333_Y;
         MinecraftInstance.mc.field_71474_y.field_74333_Y = 100000.0F;

         for(TileEntity tileEntity : MinecraftInstance.mc.field_71441_e.field_147482_g) {
            Intrinsics.checkNotNullExpressionValue(tileEntity, "tileEntity");
            Color var10000 = this.getColor(tileEntity);
            if (var10000 != null) {
               Color color = var10000;
               String var8 = mode.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var8, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var8.hashCode()) {
                  case -1171135301:
                     if (!var8.equals("otherbox")) {
                        continue;
                     }
                     break;
                  case -1106245566:
                     if (var8.equals("outline")) {
                        RenderUtils.drawBlockBox(tileEntity.func_174877_v(), color, true, false, ((Number)this.outlineWidthValue.get()).floatValue(), 1.0F);
                     }
                     continue;
                  case -941784056:
                     if (var8.equals("wireframe")) {
                        GL11.glPushMatrix();
                        GL11.glPushAttrib(1048575);
                        GL11.glPolygonMode(1032, 6913);
                        GL11.glDisable(3553);
                        GL11.glDisable(2896);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        RenderUtils.glColor(color);
                        GL11.glLineWidth(1.5F);
                        TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                        GL11.glPopAttrib();
                        GL11.glPopMatrix();
                     }
                     continue;
                  case 1650:
                     if (var8.equals("2d")) {
                        RenderUtils.draw2D(tileEntity.func_174877_v(), color.getRGB(), Color.BLACK.getRGB());
                     }
                     continue;
                  case 97739:
                     if (!var8.equals("box")) {
                        continue;
                     }
                     break;
                  default:
                     continue;
               }

               RenderUtils.drawBlockBox(tileEntity.func_174877_v(), color, !StringsKt.equals(mode, "otherbox", true), true, ((Number)this.outlineWidthValue.get()).floatValue(), 1.0F);
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         MinecraftInstance.mc.field_71474_y.field_74333_Y = gamma;
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String mode = (String)this.modeValue.get();
      RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
      float partialTicks = event.getPartialTicks();
      FramebufferShader var10000;
      if (Intrinsics.areEqual((Object)mode, (Object)"shaderoutline")) {
         var10000 = OutlineShader.OUTLINE_SHADER;
      } else {
         if (!Intrinsics.areEqual((Object)mode, (Object)"shaderglow")) {
            return;
         }

         var10000 = GlowShader.GLOW_SHADER;
      }

      FramebufferShader shader = var10000;
      Map entityMap = (Map)(new HashMap());

      for(TileEntity tileEntity : MinecraftInstance.mc.field_71441_e.field_147482_g) {
         Intrinsics.checkNotNullExpressionValue(tileEntity, "tileEntity");
         Color var17 = this.getColor(tileEntity);
         if (var17 != null) {
            Color color = var17;
            if (!entityMap.containsKey(color)) {
               ArrayList var11 = new ArrayList();
               entityMap.put(color, var11);
            }

            Object var18 = entityMap.get(color);
            Intrinsics.checkNotNull(var18);
            ((ArrayList)var18).add(tileEntity);
         }
      }

      for(Map.Entry var14 : entityMap.entrySet()) {
         Color key = (Color)var14.getKey();
         ArrayList value = (ArrayList)var14.getValue();
         shader.startDraw(partialTicks);

         for(TileEntity tileEntity : value) {
            TileEntityRendererDispatcher.field_147556_a.func_147549_a(tileEntity, (double)tileEntity.func_174877_v().func_177958_n() - renderManager.field_78725_b, (double)tileEntity.func_174877_v().func_177956_o() - renderManager.field_78726_c, (double)tileEntity.func_174877_v().func_177952_p() - renderManager.field_78723_d, partialTicks);
         }

         shader.stopDraw(key, StringsKt.equals(mode, "shaderglow", true) ? 2.5F : 1.5F, 1.0F);
      }

   }
}
