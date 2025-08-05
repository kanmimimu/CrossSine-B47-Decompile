package net.ccbluex.liquidbounce.utils.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004BU\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\r¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/FallingPlayer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "(Lnet/minecraft/entity/player/EntityPlayer;)V", "x", "", "y", "z", "motionX", "motionY", "motionZ", "yaw", "", "strafe", "forward", "jumpMovementFactor", "(DDDDDDFFFF)V", "calculateForTick", "", "findCollision", "Lnet/minecraft/util/BlockPos;", "ticks", "", "rayTrace", "start", "Lnet/minecraft/util/Vec3;", "end", "CrossSine"}
)
public final class FallingPlayer extends MinecraftInstance {
   private double x;
   private double y;
   private double z;
   private double motionX;
   private double motionY;
   private double motionZ;
   private final float yaw;
   private float strafe;
   private float forward;
   private final float jumpMovementFactor;

   public FallingPlayer(double x, double y, double z, double motionX, double motionY, double motionZ, float yaw, float strafe, float forward, float jumpMovementFactor) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.motionX = motionX;
      this.motionY = motionY;
      this.motionZ = motionZ;
      this.yaw = yaw;
      this.strafe = strafe;
      this.forward = forward;
      this.jumpMovementFactor = jumpMovementFactor;
   }

   public FallingPlayer(@NotNull EntityPlayer player) {
      Intrinsics.checkNotNullParameter(player, "player");
      this(player.field_70165_t, player.field_70163_u, player.field_70161_v, player.field_70159_w, player.field_70181_x, player.field_70179_y, player.field_70177_z, player.field_70702_br, player.field_70701_bs, player.field_70747_aH);
   }

   private final void calculateForTick() {
      this.strafe *= 0.98F;
      this.forward *= 0.98F;
      float v = this.strafe * this.strafe + this.forward * this.forward;
      if (v >= 1.0E-4F) {
         v = MathHelper.func_76129_c(v);
         if (v < 1.0F) {
            v = 1.0F;
         }

         float fixedJumpFactor = this.jumpMovementFactor;
         if (MinecraftInstance.mc.field_71439_g.func_70051_ag()) {
            fixedJumpFactor = (float)((double)fixedJumpFactor * 1.3);
         }

         v = fixedJumpFactor / v;
         this.strafe *= v;
         this.forward *= v;
         float f1 = MathHelper.func_76126_a(this.yaw * (float)Math.PI / 180.0F);
         float f2 = MathHelper.func_76134_b(this.yaw * (float)Math.PI / 180.0F);
         this.motionX += (double)(this.strafe * f2 - this.forward * f1);
         this.motionZ += (double)(this.forward * f2 + this.strafe * f1);
      }

      this.motionY -= 0.08;
      this.motionX *= 0.91;
      this.motionY *= (double)0.98F;
      this.motionZ *= 0.91;
      this.x += this.motionX;
      this.y += this.motionY;
      this.z += this.motionZ;
   }

   @Nullable
   public final BlockPos findCollision(int ticks) {
      int var2 = 0;

      while(var2 < ticks) {
         ++var2;
         Vec3 start = new Vec3(this.x, this.y, this.z);
         this.calculateForTick();
         Vec3 end = new Vec3(this.x, this.y, this.z);
         Object raytracedBlock = null;
         float w = MinecraftInstance.mc.field_71439_g.field_70130_N / 2.0F;
         BlockPos it = this.rayTrace(start, end);
         int var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c((double)w, (double)0.0F, (double)w);
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector(w.toDouble(), 0.0, w.toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c(-((double)w), (double)0.0F, (double)w);
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector(-w.toDouble(), 0.0, w.toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c((double)w, (double)0.0F, -((double)w));
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector(w.toDouble(), 0.0, -w.toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c(-((double)w), (double)0.0F, -((double)w));
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector(-w.toDouble(), 0.0, -w.toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c((double)w, (double)0.0F, (double)(w / 2.0F));
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector(w.toDoub…0.0, (w / 2f).toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c(-((double)w), (double)0.0F, (double)(w / 2.0F));
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector(-w.toDou…0.0, (w / 2f).toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c((double)(w / 2.0F), (double)0.0F, (double)w);
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector((w / 2f)…ble(), 0.0, w.toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }

         Vec3 it = start.func_72441_c((double)(w / 2.0F), (double)0.0F, -((double)w));
         Intrinsics.checkNotNullExpressionValue(it, "start.addVector((w / 2f)…le(), 0.0, -w.toDouble())");
         BlockPos it = this.rayTrace(it, end);
         var10 = 0;
         if (it != null) {
            return it;
         }
      }

      return null;
   }

   private final BlockPos rayTrace(Vec3 start, Vec3 end) {
      MovingObjectPosition result = MinecraftInstance.mc.field_71441_e.func_72901_a(start, end, true);
      return result != null && result.field_72313_a == MovingObjectType.BLOCK && result.field_178784_b == EnumFacing.UP ? result.func_178782_a() : null;
   }
}
