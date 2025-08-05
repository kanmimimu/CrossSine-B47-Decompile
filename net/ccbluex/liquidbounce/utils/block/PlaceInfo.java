package net.ccbluex.liquidbounce.utils.block;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "", "blockPos", "Lnet/minecraft/util/BlockPos;", "enumFacing", "Lnet/minecraft/util/EnumFacing;", "vec3", "Lnet/minecraft/util/Vec3;", "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/Vec3;)V", "getBlockPos", "()Lnet/minecraft/util/BlockPos;", "getEnumFacing", "()Lnet/minecraft/util/EnumFacing;", "getVec3", "()Lnet/minecraft/util/Vec3;", "setVec3", "(Lnet/minecraft/util/Vec3;)V", "Companion", "CrossSine"}
)
public final class PlaceInfo {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final BlockPos blockPos;
   @NotNull
   private final EnumFacing enumFacing;
   @NotNull
   private Vec3 vec3;

   public PlaceInfo(@NotNull BlockPos blockPos, @NotNull EnumFacing enumFacing, @NotNull Vec3 vec3) {
      Intrinsics.checkNotNullParameter(blockPos, "blockPos");
      Intrinsics.checkNotNullParameter(enumFacing, "enumFacing");
      Intrinsics.checkNotNullParameter(vec3, "vec3");
      super();
      this.blockPos = blockPos;
      this.enumFacing = enumFacing;
      this.vec3 = vec3;
   }

   // $FF: synthetic method
   public PlaceInfo(BlockPos var1, EnumFacing var2, Vec3 var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 4) != 0) {
         var3 = new Vec3((double)var1.func_177958_n() + (double)0.5F, (double)var1.func_177956_o() + (double)0.5F, (double)var1.func_177952_p() + (double)0.5F);
      }

      this(var1, var2, var3);
   }

   @NotNull
   public final BlockPos getBlockPos() {
      return this.blockPos;
   }

   @NotNull
   public final EnumFacing getEnumFacing() {
      return this.enumFacing;
   }

   @NotNull
   public final Vec3 getVec3() {
      return this.vec3;
   }

   public final void setVec3(@NotNull Vec3 var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.vec3 = var1;
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo$Companion;", "", "()V", "get", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "blockPos", "Lnet/minecraft/util/BlockPos;", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      @Nullable
      public final PlaceInfo get(@NotNull BlockPos blockPos) {
         Intrinsics.checkNotNullParameter(blockPos, "blockPos");
         if (BlockUtils.canBeClicked(blockPos.func_177982_a(0, -1, 0))) {
            BlockPos var6 = blockPos.func_177982_a(0, -1, 0);
            Intrinsics.checkNotNullExpressionValue(var6, "blockPos.add(0, -1, 0)");
            return new PlaceInfo(var6, EnumFacing.UP, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else if (BlockUtils.canBeClicked(blockPos.func_177982_a(0, 0, 1))) {
            BlockPos var5 = blockPos.func_177982_a(0, 0, 1);
            Intrinsics.checkNotNullExpressionValue(var5, "blockPos.add(0, 0, 1)");
            return new PlaceInfo(var5, EnumFacing.NORTH, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else if (BlockUtils.canBeClicked(blockPos.func_177982_a(-1, 0, 0))) {
            BlockPos var4 = blockPos.func_177982_a(-1, 0, 0);
            Intrinsics.checkNotNullExpressionValue(var4, "blockPos.add(-1, 0, 0)");
            return new PlaceInfo(var4, EnumFacing.EAST, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else if (BlockUtils.canBeClicked(blockPos.func_177982_a(0, 0, -1))) {
            BlockPos var3 = blockPos.func_177982_a(0, 0, -1);
            Intrinsics.checkNotNullExpressionValue(var3, "blockPos.add(0, 0, -1)");
            return new PlaceInfo(var3, EnumFacing.SOUTH, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else {
            PlaceInfo var10000;
            if (BlockUtils.canBeClicked(blockPos.func_177982_a(1, 0, 0))) {
               BlockPos var2 = blockPos.func_177982_a(1, 0, 0);
               Intrinsics.checkNotNullExpressionValue(var2, "blockPos.add(1, 0, 0)");
               var10000 = new PlaceInfo(var2, EnumFacing.WEST, (Vec3)null, 4, (DefaultConstructorMarker)null);
            } else {
               var10000 = null;
            }

            return var10000;
         }
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
