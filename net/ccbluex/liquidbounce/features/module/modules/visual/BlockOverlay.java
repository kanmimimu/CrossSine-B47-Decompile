package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.BedAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "BlockOverlay",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/BlockOverlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animation", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "clientTheme", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "colorBlockAlphaValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "colorBlockAlphaValue2", "colorBlockValue", "Ljava/awt/Color;", "colorBlockValue2", "currentBlock", "Lnet/minecraft/util/BlockPos;", "getCurrentBlock", "()Lnet/minecraft/util/BlockPos;", "currentDamage", "", "getCurrentDamage", "()D", "outlineValue", "widthValue", "", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"}
)
public final class BlockOverlay extends Module {
   @NotNull
   private final BoolValue clientTheme = new BoolValue("ClientTheme", false);
   @NotNull
   private final BoolValue outlineValue = new BoolValue("Outline", false);
   @NotNull
   private final Value widthValue = (new FloatValue("LineWidth", 2.0F, 0.0F, 10.0F)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)BlockOverlay.this.outlineValue.get();
      }
   });
   @NotNull
   private final Value colorBlockValue;
   @NotNull
   private final Value colorBlockAlphaValue;
   @NotNull
   private final Value colorBlockValue2;
   @NotNull
   private final Value colorBlockAlphaValue2;
   @Nullable
   private Animation animation;

   public BlockOverlay() {
      Color var1 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(var1, "WHITE");
      this.colorBlockValue = (new ColorValue("Block-Color", var1, false, 4, (DefaultConstructorMarker)null)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)BlockOverlay.this.clientTheme.get();
         }
      });
      this.colorBlockAlphaValue = (new IntegerValue("Block-Alpha", 255, 0, 255)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)BlockOverlay.this.clientTheme.get();
         }
      });
      var1 = Color.BLACK;
      Intrinsics.checkNotNullExpressionValue(var1, "BLACK");
      this.colorBlockValue2 = (new ColorValue("Block-Color2", var1, false, 4, (DefaultConstructorMarker)null)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)BlockOverlay.this.clientTheme.get();
         }
      });
      this.colorBlockAlphaValue2 = (new IntegerValue("Block-Alpha2", 255, 0, 255)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)BlockOverlay.this.clientTheme.get();
         }
      });
   }

   private final BlockPos getCurrentBlock() {
      MovingObjectPosition var10000 = MinecraftInstance.mc.field_71476_x;
      BlockPos var2 = var10000 == null ? null : var10000.func_178782_a();
      if (var2 == null) {
         return null;
      } else {
         BlockPos blockPos = var2;
         if (BedAura.INSTANCE.getState() && BedAura.INSTANCE.getPos() != null) {
            return BedAura.INSTANCE.getPos();
         } else {
            return BlockUtils.canBeClicked(blockPos) && MinecraftInstance.mc.field_71441_e.func_175723_af().func_177746_a(blockPos) ? blockPos : null;
         }
      }
   }

   private final double getCurrentDamage() {
      return MinecraftInstance.mc.field_71442_b.field_78770_f == 0.0F ? (double)1.0F : (double)MinecraftInstance.mc.field_71442_b.field_78770_f;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      BlockPos var10000 = this.getCurrentBlock();
      if (var10000 != null) {
         BlockPos blockPos = var10000;
         Color color = (Boolean)this.clientTheme.get() ? ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, ((Number)this.colorBlockAlphaValue.get()).intValue(), false, 4, (Object)null) : (Color)this.colorBlockValue.get();
         Color color2 = (Boolean)this.clientTheme.get() ? ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 180, ((Number)this.colorBlockAlphaValue2.get()).intValue(), false, 4, (Object)null) : (Color)this.colorBlockValue2.get();
         if (this.animation == null) {
            this.animation = new Animation(Easing.LINEAR, 40L);
            Animation var5 = this.animation;
            Intrinsics.checkNotNull(var5);
            var5.value = this.getCurrentDamage();
         }

         Animation var6 = this.animation;
         Intrinsics.checkNotNull(var6);
         var6.run(this.getCurrentDamage());
         GlStateManager.func_179094_E();
         boolean var10003 = (Boolean)this.outlineValue.get();
         boolean var10004 = !(Boolean)this.outlineValue.get();
         float var10005 = ((Number)this.widthValue.get()).floatValue();
         Animation var10006 = this.animation;
         Intrinsics.checkNotNull(var10006);
         RenderUtils.drawBlockBoxGradient(blockPos, color, color2, var10003, var10004, var10005, (float)var10006.value);
         GlStateManager.func_179121_F();
         GlStateManager.func_179117_G();
      }
   }
}
