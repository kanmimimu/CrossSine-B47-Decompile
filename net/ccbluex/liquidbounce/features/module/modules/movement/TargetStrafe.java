package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorManager;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "TargetStrafe",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010&\u001a\u00020\u000b2\b\u0010'\u001a\u0004\u0018\u00010 H\u0002J\b\u0010(\u001a\u00020\u000bH\u0002J\u000e\u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020+J\u0018\u0010,\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.H\u0002J\u000e\u00100\u001a\u00020\u000b2\u0006\u0010*\u001a\u000201J\u0010\u00102\u001a\u0002032\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u00104\u001a\u0002032\u0006\u0010*\u001a\u000205H\u0007J\u0010\u00106\u001a\u0002032\u0006\u0010*\u001a\u000207H\u0007J\u0006\u00108\u001a\u00020\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\r\"\u0004\b\u0013\u0010\u000fR\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/TargetStrafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "callBackYaw", "", "getCallBackYaw", "()D", "setCallBackYaw", "(D)V", "direction", "doStrafe", "", "getDoStrafe", "()Z", "setDoStrafe", "(Z)V", "holdSpaceValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "isEnabled", "setEnabled", "lineWidthValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "ongroundValue", "onlyFlightValue", "onlySpeedValue", "radiusModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "renderModeValue", "targetEntity", "Lnet/minecraft/entity/EntityLivingBase;", "getTargetEntity", "()Lnet/minecraft/entity/EntityLivingBase;", "setTargetEntity", "(Lnet/minecraft/entity/EntityLivingBase;)V", "thirdPersonViewValue", "canStrafe", "target", "checkVoid", "doMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "isVoid", "xPos", "", "zPos", "modifyStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onMove", "", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "toggleStrafe", "CrossSine"}
)
public final class TargetStrafe extends Module {
   @NotNull
   private final BoolValue thirdPersonViewValue = new BoolValue("ThirdPersonView", false);
   @NotNull
   private final ListValue renderModeValue;
   @NotNull
   private final Value lineWidthValue;
   @NotNull
   private final ListValue radiusModeValue;
   @NotNull
   private final FloatValue radiusValue;
   @NotNull
   private final BoolValue ongroundValue;
   @NotNull
   private final BoolValue holdSpaceValue;
   @NotNull
   private final BoolValue onlySpeedValue;
   @NotNull
   private final BoolValue onlyFlightValue;
   private double direction;
   @Nullable
   private EntityLivingBase targetEntity;
   private boolean isEnabled;
   private boolean doStrafe;
   private double callBackYaw;

   public TargetStrafe() {
      String[] var1 = new String[]{"Circle", "Polygon", "None"};
      this.renderModeValue = new ListValue("RenderMode", var1, "Polygon");
      this.lineWidthValue = (new FloatValue("LineWidth", 1.0F, 1.0F, 10.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !TargetStrafe.this.renderModeValue.equals("None");
         }
      });
      var1 = new String[]{"Normal", "Strict"};
      this.radiusModeValue = new ListValue("RadiusMode", var1, "Normal");
      this.radiusValue = new FloatValue("Radius", 0.5F, 0.1F, 5.0F);
      this.ongroundValue = new BoolValue("OnlyOnGround", false);
      this.holdSpaceValue = new BoolValue("HoldSpace", false);
      this.onlySpeedValue = new BoolValue("OnlySpeed", true);
      this.onlyFlightValue = new BoolValue("OnlyFlight", true);
      this.direction = (double)-1.0F;
   }

   @Nullable
   public final EntityLivingBase getTargetEntity() {
      return this.targetEntity;
   }

   public final void setTargetEntity(@Nullable EntityLivingBase var1) {
      this.targetEntity = var1;
   }

   public final boolean isEnabled() {
      return this.isEnabled;
   }

   public final void setEnabled(boolean var1) {
      this.isEnabled = var1;
   }

   public final boolean getDoStrafe() {
      return this.doStrafe;
   }

   public final void setDoStrafe(boolean var1) {
      this.doStrafe = var1;
   }

   public final double getCallBackYaw() {
      return this.callBackYaw;
   }

   public final void setCallBackYaw(double var1) {
      this.callBackYaw = var1;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!Intrinsics.areEqual((Object)this.renderModeValue.get(), (Object)"None") && this.canStrafe(this.targetEntity)) {
         if (this.targetEntity == null || !this.doStrafe) {
            return;
         }

         int[] var3 = new int[]{0};
         int[] counter = var3;
         if (StringsKt.equals((String)this.renderModeValue.get(), "Circle", true)) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glEnable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
            GL11.glHint(3153, 4354);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(((Number)this.lineWidthValue.get()).floatValue());
            GL11.glBegin(3);
            EntityLivingBase var10000 = this.targetEntity;
            Intrinsics.checkNotNull(var10000);
            double var17 = var10000.field_70142_S;
            EntityLivingBase var10001 = this.targetEntity;
            Intrinsics.checkNotNull(var10001);
            double var28 = var10001.field_70165_t;
            EntityLivingBase var10002 = this.targetEntity;
            Intrinsics.checkNotNull(var10002);
            double x = var17 + (var28 - var10002.field_70142_S) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78730_l;
            EntityLivingBase var18 = this.targetEntity;
            Intrinsics.checkNotNull(var18);
            double var19 = var18.field_70137_T;
            EntityLivingBase var29 = this.targetEntity;
            Intrinsics.checkNotNull(var29);
            double var30 = var29.field_70163_u;
            var10002 = this.targetEntity;
            Intrinsics.checkNotNull(var10002);
            double y = var19 + (var30 - var10002.field_70137_T) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78731_m;
            EntityLivingBase var20 = this.targetEntity;
            Intrinsics.checkNotNull(var20);
            double var21 = var20.field_70136_U;
            EntityLivingBase var31 = this.targetEntity;
            Intrinsics.checkNotNull(var31);
            double var32 = var31.field_70161_v;
            var10002 = this.targetEntity;
            Intrinsics.checkNotNull(var10002);
            double z = var21 + (var32 - var10002.field_70136_U) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78728_n;
            int var9 = 0;

            while(var9 < 360) {
               int i = var9++;
               Color rainbow = new Color(Color.HSBtoRGB((float)(((double)MinecraftInstance.mc.field_71439_g.field_70173_aa / (double)70.0F + Math.sin((double)i / (double)50.0F * (double)1.75F)) % (double)1.0F), 0.7F, 1.0F));
               GL11.glColor3f((float)rainbow.getRed() / 255.0F, (float)rainbow.getGreen() / 255.0F, (float)rainbow.getBlue() / 255.0F);
               GL11.glVertex3d(x + ((Number)this.radiusValue.get()).doubleValue() * Math.cos((double)i * (Math.PI * 2D) / (double)45.0F), y, z + ((Number)this.radiusValue.get()).doubleValue() * Math.sin((double)i * (Math.PI * 2D) / (double)45.0F));
            }

            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
         } else {
            float rad = ((Number)this.radiusValue.get()).floatValue();
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            RenderUtils.startDrawing();
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(((Number)this.lineWidthValue.get()).floatValue());
            GL11.glBegin(3);
            EntityLivingBase var22 = this.targetEntity;
            Intrinsics.checkNotNull(var22);
            double var23 = var22.field_70142_S;
            EntityLivingBase var33 = this.targetEntity;
            Intrinsics.checkNotNull(var33);
            double var34 = var33.field_70165_t;
            EntityLivingBase var41 = this.targetEntity;
            Intrinsics.checkNotNull(var41);
            double x = var23 + (var34 - var41.field_70142_S) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78730_l;
            EntityLivingBase var24 = this.targetEntity;
            Intrinsics.checkNotNull(var24);
            double var25 = var24.field_70137_T;
            EntityLivingBase var35 = this.targetEntity;
            Intrinsics.checkNotNull(var35);
            double var36 = var35.field_70163_u;
            var41 = this.targetEntity;
            Intrinsics.checkNotNull(var41);
            double y = var25 + (var36 - var41.field_70137_T) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78731_m;
            EntityLivingBase var26 = this.targetEntity;
            Intrinsics.checkNotNull(var26);
            double var27 = var26.field_70136_U;
            EntityLivingBase var37 = this.targetEntity;
            Intrinsics.checkNotNull(var37);
            double var38 = var37.field_70161_v;
            var41 = this.targetEntity;
            Intrinsics.checkNotNull(var41);
            double z = var27 + (var38 - var41.field_70136_U) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78728_n;
            int var15 = 0;

            while(var15 < 11) {
               int i = var15++;
               int var44 = counter[0]++;
               Color rainbow = new Color(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
               GL11.glColor3f((float)rainbow.getRed() / 255.0F, (float)rainbow.getGreen() / 255.0F, (float)rainbow.getBlue() / 255.0F);
               if ((double)rad < 0.8 && (double)rad > (double)0.0F) {
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)3.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)3.0F));
               }

               if ((double)rad < (double)1.5F && (double)rad > 0.7) {
                  var44 = counter[0]++;
                  RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)4.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)4.0F));
               }

               if ((double)rad < (double)2.0F && (double)rad > 1.4) {
                  var44 = counter[0]++;
                  RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)5.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)5.0F));
               }

               if ((double)rad < 2.4 && (double)rad > 1.9) {
                  var44 = counter[0]++;
                  RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)6.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)6.0F));
               }

               if ((double)rad < 2.7 && (double)rad > 2.3) {
                  var44 = counter[0]++;
                  RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)7.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)7.0F));
               }

               if ((double)rad < (double)6.0F && (double)rad > 2.6) {
                  var44 = counter[0]++;
                  RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)8.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)8.0F));
               }

               if ((double)rad < (double)7.0F && (double)rad > 5.9) {
                  var44 = counter[0]++;
                  RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)9.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)9.0F));
               }

               if ((double)rad < (double)11.0F && (double)rad > 6.9) {
                  var44 = counter[0]++;
                  RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                  GL11.glVertex3d(x + (double)rad * Math.cos((double)i * (Math.PI * 2D) / (double)10.0F), y, z + (double)rad * Math.sin((double)i * (Math.PI * 2D) / (double)10.0F));
               }
            }

            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            RenderUtils.stopDrawing();
            GL11.glEnable(3553);
            GL11.glPopMatrix();
         }
      }

   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.doStrafe && (!(Boolean)this.ongroundValue.get() || MinecraftInstance.mc.field_71439_g.field_70122_E)) {
         if (!this.canStrafe(this.targetEntity)) {
            this.isEnabled = false;
            return;
         }

         boolean aroundVoid = false;
         int _1IlIll1 = -1;

         while(_1IlIll1 < 1) {
            int x = _1IlIll1++;
            int var5 = -1;

            while(var5 < 1) {
               int z = var5++;
               if (this.isVoid(x, z)) {
                  aroundVoid = true;
               }
            }
         }

         if (aroundVoid) {
            this.direction *= (double)-1;
         }

         _1IlIll1 = 0;
         if (StringsKt.equals((String)this.radiusModeValue.get(), "Strict", true)) {
            _1IlIll1 = 1;
         }

         MovementUtils var10000 = MovementUtils.INSTANCE;
         EntityLivingBase var10001 = this.targetEntity;
         Intrinsics.checkNotNull(var10001);
         var10000.doTargetStrafe(var10001, (float)this.direction, ((Number)this.radiusValue.get()).floatValue(), event, _1IlIll1);
         this.callBackYaw = (double)RotationUtils.getRotationsEntity(this.targetEntity).getYaw();
         this.isEnabled = true;
         if (!(Boolean)this.thirdPersonViewValue.get()) {
            return;
         }

         MinecraftInstance.mc.field_71474_y.field_74320_O = this.canStrafe(this.targetEntity) ? 3 : 0;
      } else {
         this.isEnabled = false;
         if (!(Boolean)this.thirdPersonViewValue.get()) {
            return;
         }

         MinecraftInstance.mc.field_71474_y.field_74320_O = 3;
      }

   }

   private final boolean canStrafe(EntityLivingBase target) {
      boolean var3;
      label39: {
         if (target != null && (!(Boolean)this.holdSpaceValue.get() || MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d())) {
            label34: {
               if ((Boolean)this.onlySpeedValue.get()) {
                  Module var10000 = CrossSine.INSTANCE.getModuleManager().get(Speed.class);
                  Intrinsics.checkNotNull(var10000);
                  if (!((Speed)var10000).getState()) {
                     break label34;
                  }
               }

               if (!(Boolean)this.onlyFlightValue.get()) {
                  break label39;
               }

               Module var2 = CrossSine.INSTANCE.getModuleManager().get(Flight.class);
               Intrinsics.checkNotNull(var2);
               if (((Flight)var2).getState()) {
                  break label39;
               }
            }
         }

         var3 = false;
         return var3;
      }

      var3 = true;
      return var3;
   }

   public final boolean modifyStrafe(@NotNull StrafeEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.isEnabled && !event.isCancelled()) {
         MovementUtils.INSTANCE.strafe();
         return true;
      } else {
         return false;
      }
   }

   public final boolean toggleStrafe() {
      boolean var2;
      label39: {
         if (this.targetEntity != null && (!(Boolean)this.holdSpaceValue.get() || MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d())) {
            label34: {
               if ((Boolean)this.onlySpeedValue.get()) {
                  Module var10000 = CrossSine.INSTANCE.getModuleManager().get(Speed.class);
                  Intrinsics.checkNotNull(var10000);
                  if (!((Speed)var10000).getState()) {
                     break label34;
                  }
               }

               if (!(Boolean)this.onlyFlightValue.get()) {
                  break label39;
               }

               Module var1 = CrossSine.INSTANCE.getModuleManager().get(Flight.class);
               Intrinsics.checkNotNull(var1);
               if (((Flight)var1).getState()) {
                  break label39;
               }
            }
         }

         var2 = false;
         return var2;
      }

      var2 = true;
      return var2;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.targetEntity = KillAura.INSTANCE.getState() ? KillAura.INSTANCE.getCurrentTarget() : (SilentAura.INSTANCE.getState() ? SilentAura.INSTANCE.getTarget() : CrossSine.INSTANCE.getCombatManager().getTarget());
      if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
         this.direction = -this.direction;
         this.direction = this.direction >= (double)0.0F ? (double)1.0F : (double)-1.0F;
      }

   }

   public final boolean doMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.getState()) {
         return false;
      } else {
         if (this.doStrafe && (!(Boolean)this.ongroundValue.get() || MinecraftInstance.mc.field_71439_g.field_70122_E)) {
            EntityLivingBase var10000 = this.targetEntity;
            if (var10000 == null) {
               return false;
            }

            EntityLivingBase entity = var10000;
            MovementUtils.doTargetStrafe$default(MovementUtils.INSTANCE, entity, (float)this.direction, ((Number)this.radiusValue.get()).floatValue(), event, 0, 16, (Object)null);
            this.callBackYaw = (double)RotationUtils.getRotationsEntity(entity).getYaw();
            this.isEnabled = true;
         } else {
            this.isEnabled = false;
         }

         return true;
      }
   }

   private final boolean checkVoid() {
      int var1 = -2;

      while(var1 < 3) {
         int x = var1++;
         int var3 = -2;

         while(var3 < 3) {
            int z = var3++;
            if (this.isVoid(x, z)) {
               return true;
            }
         }
      }

      return false;
   }

   private final boolean isVoid(int xPos, int zPos) {
      if (MinecraftInstance.mc.field_71439_g.field_70163_u < (double)0.0F) {
         return true;
      } else {
         for(int off = 0; off < (int)MinecraftInstance.mc.field_71439_g.field_70163_u + 2; off += 2) {
            AxisAlignedBB bb = MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)xPos, -((double)off), (double)zPos);
            if (!MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, bb).isEmpty()) {
               return false;
            }
         }

         return true;
      }
   }
}
