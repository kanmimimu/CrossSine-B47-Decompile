package net.ccbluex.liquidbounce.utils;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.visual.FreeLook;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u001d\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004J\b\u0010\u0018\u001a\u00020\u0004H\u0002J\u0006\u0010\u0019\u001a\u00020\u0004J0\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u0004J\u0006\u0010%\u001a\u00020\u0004J\b\u0010&\u001a\u00020\u0004H\u0007J\u000e\u0010'\u001a\u00020\u00042\u0006\u0010(\u001a\u00020)J\u0006\u0010*\u001a\u00020\u000bJ\u000e\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020\u001cJ\u0006\u0010,\u001a\u00020\"J\u000e\u0010-\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004J\u0006\u0010.\u001a\u00020\u0014J\u0006\u0010/\u001a\u000200J\u0006\u00101\u001a\u000200J\u0006\u00102\u001a\u000200J\u000e\u00103\u001a\u0002002\u0006\u00104\u001a\u00020\u0004J\u0006\u00105\u001a\u000200J\"\u00106\u001a\u00020\u00142\u0006\u00107\u001a\u0002002\b\b\u0002\u00108\u001a\u0002002\b\b\u0002\u0010\u0017\u001a\u00020\u0004J\u000e\u00106\u001a\u00020\u00142\u0006\u00109\u001a\u00020 J\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010:\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010;\u001a\u00020\u00142\u0006\u0010<\u001a\u00020\u000bJ\u0006\u0010=\u001a\u00020\u0014J\u000e\u0010=\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010>\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u0004J\u0016\u0010>\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u00042\u0006\u0010?\u001a\u00020\"J\u000e\u0010@\u001a\u00020\u00142\u0006\u0010A\u001a\u000200J\u000e\u0010B\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004J\u0016\u0010C\u001a\u00020\u00142\u0006\u0010D\u001a\u00020\u00042\u0006\u0010E\u001a\u00020\u000bJ\u000e\u0010F\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ.\u0010F\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010G\u001a\u00020\u00042\u0006\u0010H\u001a\u00020\u000b2\u0006\u0010I\u001a\u00020\u00042\u0006\u0010J\u001a\u00020\u0004J\u0006\u0010K\u001a\u00020\u0014J\u000e\u0010K\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004J\u000e\u0010K\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u0016\u0010K\u001a\u00020\u00142\u0006\u00109\u001a\u00020 2\u0006\u0010\u0015\u001a\u00020\u000bJ\u0006\u0010L\u001a\u00020\u0014R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\r¨\u0006M"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/MovementUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "<set-?>", "", "bps", "getBps", "()D", "direction", "getDirection", "jumpMotion", "", "getJumpMotion", "()F", "lastX", "lastY", "lastZ", "movingYaw", "getMovingYaw", "FlyBasic", "", "speed", "JumpBoost", "motionY", "calculateGround", "defaultSpeed", "doTargetStrafe", "curTarget", "Lnet/minecraft/entity/EntityLivingBase;", "direction_", "radius", "moveEvent", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "mathRadius", "", "forward", "length", "getBaseMoveSpeed", "getDirectioon", "getFallDistance", "entity", "Lnet/minecraft/entity/Entity;", "getSpeed", "e", "getSpeedAmplifier", "getSpeedWithPotionEffects", "handleVanillaKickBypass", "hasMotion", "", "isBlockUnder", "isMoving", "isOnGround", "height", "isStrafing", "jump", "checkSpeed", "motion", "event", "limitSpeed", "limitSpeedByPercent", "percent", "move", "predictedMotion", "ticks", "resetMotion", "y", "setMotion", "setMotion2", "d", "f", "setSpeed", "moveSpeed", "pseudoYaw", "pseudoStrafe", "pseudoForward", "strafe", "updateBlocksPerSecond", "CrossSine"}
)
public final class MovementUtils extends MinecraftInstance {
   @NotNull
   public static final MovementUtils INSTANCE = new MovementUtils();
   private static double bps;
   private static double lastX;
   private static double lastY;
   private static double lastZ;

   private MovementUtils() {
   }

   public final void resetMotion(boolean y) {
      MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
      MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
      if (y) {
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
      }

   }

   public final double predictedMotion(double motion) {
      return (motion - 0.08) * (double)0.98F;
   }

   public final double getFallDistance(@NotNull Entity entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      double fallDist = (double)-1.0F;
      Vec3 pos = new Vec3(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
      int y = (int)Math.floor(pos.field_72448_b);
      if ((int)(pos.field_72448_b % (double)1) == 0) {
         y += -1;
      }

      int var6 = y;
      int i;
      if (0 <= y) {
         do {
            i = var6--;
            Block block = BlockUtils.getBlock(new BlockPos((int)Math.floor(pos.field_72450_a), i, (int)Math.floor(pos.field_72449_c)));
            if (!(block instanceof BlockAir) && !(block instanceof BlockSign)) {
               fallDist = (double)(y - i);
               break;
            }
         } while(i != 0);
      }

      return fallDist;
   }

   public final double predictedMotion(double motion, int ticks) {
      if (ticks == 0) {
         return motion;
      } else {
         double predicted = motion;

         for(int var6 = 0; var6 < ticks; predicted = (predicted - 0.08) * (double)0.98F) {
            ++var6;
         }

         return predicted;
      }
   }

   public final float getSpeed() {
      return (float)Math.sqrt(MinecraftInstance.mc.field_71439_g.field_70159_w * MinecraftInstance.mc.field_71439_g.field_70159_w + MinecraftInstance.mc.field_71439_g.field_70179_y * MinecraftInstance.mc.field_71439_g.field_70179_y);
   }

   public final void setSpeed(float speed) {
      float currentSpeed = this.getSpeed();
      if (currentSpeed > 0.0F) {
         float factor = speed / currentSpeed;
         EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70159_w *= (double)factor;
         var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70179_y *= (double)factor;
      }

   }

   public final void FlyBasic(float speed) {
      if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
         EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
         var2.field_70181_x += (double)speed;
      }

      if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
         EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
         var3.field_70181_x -= (double)speed;
      }

      this.strafe(speed);
   }

   public final void jump(boolean checkSpeed, boolean motion, double motionY) {
      if (!MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() && (!checkSpeed || !Speed.INSTANCE.getState())) {
         if (motion) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = motionY;
         } else {
            MinecraftInstance.mc.field_71439_g.func_70664_aZ();
         }
      }

   }

   // $FF: synthetic method
   public static void jump$default(MovementUtils var0, boolean var1, boolean var2, double var3, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = false;
      }

      if ((var5 & 4) != 0) {
         var3 = 0.42;
      }

      var0.jump(var1, var2, var3);
   }

   public final void jump(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      double jumpY = (double)MinecraftInstance.mc.field_71439_g.field_70747_aH;
      if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76430_j)) {
         jumpY += (double)((float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1F);
      }

      MinecraftInstance.mc.field_71439_g.field_70181_x = jumpY;
      event.setY(MinecraftInstance.mc.field_71439_g.field_70181_x);
   }

   public final double getSpeedWithPotionEffects(double speed) {
      PotionEffect it = MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c);
      double var10000;
      if (it == null) {
         var10000 = speed;
      } else {
         int var7 = 0;
         double var4 = speed * ((double)1 + (double)(it.func_76458_c() + 1) * 0.2);
         var10000 = var4;
      }

      return var10000;
   }

   public final void strafe() {
      this.strafe(this.getSpeed());
   }

   public final void move() {
      this.move(this.getSpeed());
   }

   public final int getSpeedAmplifier() {
      return MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c) ? 1 + MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() : 0;
   }

   public final boolean isMoving() {
      return MinecraftInstance.mc.field_71439_g != null && (MinecraftInstance.mc.field_71439_g.field_71158_b.field_78900_b != 0.0F || MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a != 0.0F);
   }

   public final boolean isStrafing() {
      return MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a != 0.0F;
   }

   public final boolean hasMotion() {
      return MinecraftInstance.mc.field_71439_g.field_70159_w != (double)0.0F && MinecraftInstance.mc.field_71439_g.field_70179_y != (double)0.0F && MinecraftInstance.mc.field_71439_g.field_70181_x != (double)0.0F;
   }

   public final void strafe(float speed) {
      if (this.isMoving()) {
         MinecraftInstance.mc.field_71439_g.field_70159_w = -Math.sin(this.getDirection()) * (double)speed;
         MinecraftInstance.mc.field_71439_g.field_70179_y = Math.cos(this.getDirection()) * (double)speed;
      }
   }

   public final void strafe(@NotNull MoveEvent event, float speed) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.isMoving()) {
         event.setX(-Math.sin(this.getDirection()) * (double)speed);
         event.setZ(Math.cos(this.getDirection()) * (double)speed);
      }
   }

   public final void strafe(double speed) {
      if (this.isMoving()) {
         MinecraftInstance.mc.field_71439_g.field_70159_w = -Math.sin(this.getDirection()) * speed;
         MinecraftInstance.mc.field_71439_g.field_70179_y = Math.cos(this.getDirection()) * speed;
      }
   }

   public final double defaultSpeed() {
      double baseSpeed = 0.2873;
      if (Minecraft.func_71410_x().field_71439_g.func_70644_a(Potion.field_76424_c)) {
         int amplifier = Minecraft.func_71410_x().field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c();
         baseSpeed *= (double)1.0F + 0.2 * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public final float getSpeed(@NotNull EntityLivingBase e) {
      Intrinsics.checkNotNullParameter(e, "e");
      return (float)Math.sqrt((e.field_70165_t - e.field_70169_q) * (e.field_70165_t - e.field_70169_q) + (e.field_70161_v - e.field_70166_s) * (e.field_70161_v - e.field_70166_s));
   }

   public final void doTargetStrafe(@NotNull EntityLivingBase curTarget, float direction_, float radius, @NotNull MoveEvent moveEvent, int mathRadius) {
      Intrinsics.checkNotNullParameter(curTarget, "curTarget");
      Intrinsics.checkNotNullParameter(moveEvent, "moveEvent");
      if (this.isMoving()) {
         double forward_ = (double)0.0F;
         double strafe_ = (double)0.0F;
         double speed_ = Math.sqrt(moveEvent.getX() * moveEvent.getX() + moveEvent.getZ() * moveEvent.getZ());
         if (!(speed_ <= 1.0E-4)) {
            double _direction = (double)0.0F;
            if ((double)direction_ > 0.001) {
               _direction = (double)1.0F;
            } else if ((double)direction_ < -0.001) {
               _direction = (double)-1.0F;
            }

            float curDistance = 0.01F;
            switch (mathRadius) {
               case 0:
                  curDistance = (float)Math.sqrt((MinecraftInstance.mc.field_71439_g.field_70165_t - curTarget.field_70165_t) * (MinecraftInstance.mc.field_71439_g.field_70165_t - curTarget.field_70165_t) + (MinecraftInstance.mc.field_71439_g.field_70161_v - curTarget.field_70161_v) * (MinecraftInstance.mc.field_71439_g.field_70161_v - curTarget.field_70161_v));
                  break;
               case 1:
                  curDistance = MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)curTarget);
            }

            if ((double)curDistance < (double)radius - speed_) {
               forward_ = (double)-1.0F;
            } else if ((double)curDistance > (double)radius + speed_) {
               forward_ = (double)1.0F;
            } else {
               forward_ = (double)(curDistance - radius) / speed_;
            }

            if ((double)curDistance < (double)radius + speed_ * (double)2 && (double)curDistance > (double)radius - speed_ * (double)2) {
               strafe_ = (double)1.0F;
            }

            strafe_ *= _direction;
            double strafeYaw = (double)RotationUtils.getRotationsEntity(curTarget).getYaw();
            double covert_ = Math.sqrt(forward_ * forward_ + strafe_ * strafe_);
            forward_ /= covert_;
            strafe_ /= covert_;
            double turnAngle = Math.toDegrees(Math.asin(strafe_));
            if (turnAngle > (double)0.0F) {
               if (forward_ < (double)0.0F) {
                  turnAngle = (double)180.0F - turnAngle;
               }
            } else if (forward_ < (double)0.0F) {
               turnAngle = (double)-180.0F - turnAngle;
            }

            strafeYaw = Math.toRadians(strafeYaw + turnAngle);
            moveEvent.setX(-Math.sin(strafeYaw) * speed_);
            moveEvent.setZ(Math.cos(strafeYaw) * speed_);
            MinecraftInstance.mc.field_71439_g.field_70159_w = moveEvent.getX();
            MinecraftInstance.mc.field_71439_g.field_70179_y = moveEvent.getZ();
         }
      }
   }

   // $FF: synthetic method
   public static void doTargetStrafe$default(MovementUtils var0, EntityLivingBase var1, float var2, float var3, MoveEvent var4, int var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = 0;
      }

      var0.doTargetStrafe(var1, var2, var3, var4, var5);
   }

   public final void move(float speed) {
      if (this.isMoving()) {
         double yaw = this.getDirection();
         EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70159_w += -Math.sin(yaw) * (double)speed;
         var4 = MinecraftInstance.mc.field_71439_g;
         var4.field_70179_y += Math.cos(yaw) * (double)speed;
      }
   }

   public final double JumpBoost(double motionY) {
      return MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76430_j) ? motionY + (double)((float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1F) : motionY;
   }

   public final double jumpMotion() {
      return this.JumpBoost(0.42);
   }

   public final void limitSpeed(float speed) {
      double yaw = this.getDirection();
      double maxXSpeed = -Math.sin(yaw) * (double)speed;
      double maxZSpeed = Math.cos(yaw) * (double)speed;
      if (MinecraftInstance.mc.field_71439_g.field_70159_w > maxZSpeed) {
         MinecraftInstance.mc.field_71439_g.field_70159_w = maxXSpeed;
      }

      if (MinecraftInstance.mc.field_71439_g.field_70179_y > maxZSpeed) {
         MinecraftInstance.mc.field_71439_g.field_70179_y = maxZSpeed;
      }

   }

   public final void limitSpeedByPercent(float percent) {
      EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
      var2.field_70159_w *= (double)percent;
      var2 = MinecraftInstance.mc.field_71439_g;
      var2.field_70179_y *= (double)percent;
   }

   public final void forward(double length) {
      Module var10000 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
      Intrinsics.checkNotNull(var10000);
      float var6;
      if (!((FreeLook)var10000).getState() && FreeLook.perspectiveToggled) {
         var6 = FreeLook.cameraYaw;
      } else {
         Module var5 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
         Intrinsics.checkNotNull(var5);
         var6 = ((FreeLook)var5).getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : MinecraftInstance.mc.field_71439_g.field_70177_z;
      }

      double yaw = Math.toRadians((double)var6);
      MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * length, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(yaw) * length);
   }

   public final double getDirection() {
      Module var10000 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
      Intrinsics.checkNotNull(var10000);
      float var5;
      if (!((FreeLook)var10000).getState() && FreeLook.perspectiveToggled) {
         var5 = FreeLook.cameraYaw;
      } else {
         Module var4 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
         Intrinsics.checkNotNull(var4);
         var5 = ((FreeLook)var4).getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : MinecraftInstance.mc.field_71439_g.field_70177_z;
      }

      double rotationYaw = (double)var5;
      if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0F) {
         rotationYaw += (double)180.0F;
      }

      float forward = 1.0F;
      if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0F) {
         forward = -0.5F;
      } else if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0F) {
         forward = 0.5F;
      }

      if (MinecraftInstance.mc.field_71439_g.field_70702_br > 0.0F) {
         rotationYaw -= (double)(90.0F * forward);
      }

      if (MinecraftInstance.mc.field_71439_g.field_70702_br < 0.0F) {
         rotationYaw += (double)(90.0F * forward);
      }

      return Math.toRadians(rotationYaw);
   }

   public final float getJumpMotion() {
      float mot = 0.42F;
      if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76430_j)) {
         mot += (float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1F;
      }

      return mot;
   }

   public final float getMovingYaw() {
      return (float)(this.getDirection() * (double)180.0F / Math.PI);
   }

   public final double getBps() {
      return bps;
   }

   public final void setMotion(double speed) {
      double forward = (double)MinecraftInstance.mc.field_71439_g.field_71158_b.field_78900_b;
      double strafe = (double)MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a;
      Module var10000 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
      Intrinsics.checkNotNull(var10000);
      float var14;
      if (!((FreeLook)var10000).getState() && FreeLook.perspectiveToggled) {
         var14 = FreeLook.cameraYaw;
      } else {
         Module var13 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
         Intrinsics.checkNotNull(var13);
         var14 = ((FreeLook)var13).getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : MinecraftInstance.mc.field_71439_g.field_70177_z;
      }

      double yaw = (double)var14;
      if (forward == (double)0.0F && strafe == (double)0.0F) {
         MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
      } else {
         if (forward != (double)0.0F) {
            if (strafe > (double)0.0F) {
               yaw += (double)((float)(forward > (double)0.0F ? -45 : 45));
            } else if (strafe < (double)0.0F) {
               yaw += (double)((float)(forward > (double)0.0F ? 45 : -45));
            }

            strafe = (double)0.0F;
            if (forward > (double)0.0F) {
               forward = (double)1.0F;
            } else if (forward < (double)0.0F) {
               forward = (double)-1.0F;
            }
         }

         double cos = Math.cos(Math.toRadians(yaw + (double)90.0F));
         double sin = Math.sin(Math.toRadians(yaw + (double)90.0F));
         MinecraftInstance.mc.field_71439_g.field_70159_w = forward * speed * cos + strafe * speed * sin;
         MinecraftInstance.mc.field_71439_g.field_70179_y = forward * speed * sin - strafe * speed * cos;
      }

   }

   public final void updateBlocksPerSecond() {
      if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71439_g.field_70173_aa < 1) {
         bps = (double)0.0F;
      }

      double distance = MinecraftInstance.mc.field_71439_g.func_70011_f(lastX, lastY, lastZ);
      lastX = MinecraftInstance.mc.field_71439_g.field_70165_t;
      lastY = MinecraftInstance.mc.field_71439_g.field_70163_u;
      lastZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
      bps = distance * (double)((float)20 * MinecraftInstance.mc.field_71428_T.field_74278_d);
   }

   public final void setSpeed(@NotNull MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
      Intrinsics.checkNotNullParameter(moveEvent, "moveEvent");
      double forward = pseudoForward;
      double strafe = pseudoStrafe;
      float yaw = pseudoYaw;
      if (pseudoForward == (double)0.0F && pseudoStrafe == (double)0.0F) {
         moveEvent.setZ((double)0.0F);
         moveEvent.setX((double)0.0F);
      } else {
         if (pseudoForward != (double)0.0F) {
            if (pseudoStrafe > (double)0.0F) {
               yaw = pseudoYaw + (float)(pseudoForward > (double)0.0F ? -45 : 45);
            } else if (pseudoStrafe < (double)0.0F) {
               yaw = pseudoYaw + (float)(pseudoForward > (double)0.0F ? 45 : -45);
            }

            strafe = (double)0.0F;
            if (pseudoForward > (double)0.0F) {
               forward = (double)1.0F;
            } else if (pseudoForward < (double)0.0F) {
               forward = (double)-1.0F;
            }
         }

         double cos = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
         double sin = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         moveEvent.setX(forward * moveSpeed * cos + strafe * moveSpeed * sin);
         moveEvent.setZ(forward * moveSpeed * sin - strafe * moveSpeed * cos);
      }

   }

   private final double calculateGround() {
      AxisAlignedBB playerBoundingBox = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
      double blockHeight = (double)1.0F;

      for(double ground = MinecraftInstance.mc.field_71439_g.field_70163_u; ground > (double)0.0F; ground -= blockHeight) {
         AxisAlignedBB customBox = new AxisAlignedBB(playerBoundingBox.field_72336_d, ground + blockHeight, playerBoundingBox.field_72334_f, playerBoundingBox.field_72340_a, ground, playerBoundingBox.field_72339_c);
         if (MinecraftInstance.mc.field_71441_e.func_72829_c(customBox)) {
            if (blockHeight <= 0.05) {
               return ground + blockHeight;
            }

            ground += blockHeight;
            blockHeight = 0.05;
         }
      }

      return (double)0.0F;
   }

   public final boolean isOnGround(double height) {
      List var3 = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, -height, (double)0.0F));
      Intrinsics.checkNotNullExpressionValue(var3, "mc.theWorld.getColliding…, -height, 0.0)\n        )");
      return !((Collection)var3).isEmpty();
   }

   public final double getBaseMoveSpeed() {
      double baseSpeed = 0.2875;
      if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
         baseSpeed *= (double)1.0F + 0.2 * (double)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1);
      }

      return baseSpeed;
   }

   public final void handleVanillaKickBypass() {
      double ground = this.calculateGround();
      MovementUtils $this$handleVanillaKickBypass_u24lambda_u2d1 = this;
      int var5 = 0;

      for(double posY = MinecraftInstance.mc.field_71439_g.field_70163_u; posY > ground; posY -= (double)8.0F) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, posY, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
         if (posY - (double)8.0F < ground) {
            break;
         }
      }

      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, ground, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));

      for(double posY = ground; posY < MinecraftInstance.mc.field_71439_g.field_70163_u; posY += (double)8.0F) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, posY, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
         if (posY + (double)8.0F > MinecraftInstance.mc.field_71439_g.field_70163_u) {
            break;
         }
      }

      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
   }

   @JvmStatic
   public static final double getDirectioon() {
      Module var10000 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
      Intrinsics.checkNotNull(var10000);
      float var5;
      if (!((FreeLook)var10000).getState() && FreeLook.perspectiveToggled) {
         var5 = FreeLook.cameraYaw;
      } else {
         Module var3 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
         Intrinsics.checkNotNull(var3);
         if (((FreeLook)var3).getState() && RotationUtils.freeLookRotation != null) {
            var5 = FreeLook.cameraYaw2;
         } else {
            var3 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
            Intrinsics.checkNotNull(var3);
            var5 = ((FreeLook)var3).getState() ? FreeLook.cameraYaw : MinecraftInstance.mc.field_71439_g.field_70177_z;
         }
      }

      double rotationYaw = (double)var5;
      if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0F) {
         rotationYaw += (double)180.0F;
      }

      float forward = 1.0F;
      if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0F) {
         forward = -0.5F;
      } else if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0F) {
         forward = 0.5F;
      }

      if (MinecraftInstance.mc.field_71439_g.field_70702_br > 0.0F) {
         rotationYaw -= (double)(90.0F * forward);
      }

      if (MinecraftInstance.mc.field_71439_g.field_70702_br < 0.0F) {
         rotationYaw += (double)(90.0F * forward);
      }

      return Math.toRadians(rotationYaw);
   }

   public final boolean isBlockUnder() {
      if (MinecraftInstance.mc.field_71439_g == null) {
         return false;
      } else if (MinecraftInstance.mc.field_71439_g.field_70163_u < (double)0.0F) {
         return false;
      } else {
         for(int off = 0; off < (int)MinecraftInstance.mc.field_71439_g.field_70163_u + 2; off += 2) {
            AxisAlignedBB bb = MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)(-off), (double)0.0F);
            List var3 = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, bb);
            Intrinsics.checkNotNullExpressionValue(var3, "mc.theWorld.getColliding…ngBoxes(mc.thePlayer, bb)");
            if (!((Collection)var3).isEmpty()) {
               return true;
            }
         }

         return false;
      }
   }

   public final void setMotion2(double d, float f) {
      MinecraftInstance.mc.field_71439_g.field_70159_w = -Math.sin(Math.toRadians((double)f)) * d;
      MinecraftInstance.mc.field_71439_g.field_70179_y = Math.cos(Math.toRadians((double)f)) * d;
   }
}
