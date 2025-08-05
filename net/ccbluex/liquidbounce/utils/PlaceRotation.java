package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/PlaceRotation;", "", "placeInfo", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "(Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;Lnet/ccbluex/liquidbounce/utils/Rotation;)V", "getPlaceInfo", "()Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "getRotation", "()Lnet/ccbluex/liquidbounce/utils/Rotation;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "CrossSine"}
)
public final class PlaceRotation {
   @NotNull
   private final PlaceInfo placeInfo;
   @NotNull
   private final Rotation rotation;

   public PlaceRotation(@NotNull PlaceInfo placeInfo, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(placeInfo, "placeInfo");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      super();
      this.placeInfo = placeInfo;
      this.rotation = rotation;
   }

   @NotNull
   public final PlaceInfo getPlaceInfo() {
      return this.placeInfo;
   }

   @NotNull
   public final Rotation getRotation() {
      return this.rotation;
   }

   @NotNull
   public final PlaceInfo component1() {
      return this.placeInfo;
   }

   @NotNull
   public final Rotation component2() {
      return this.rotation;
   }

   @NotNull
   public final PlaceRotation copy(@NotNull PlaceInfo placeInfo, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(placeInfo, "placeInfo");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      return new PlaceRotation(placeInfo, rotation);
   }

   // $FF: synthetic method
   public static PlaceRotation copy$default(PlaceRotation var0, PlaceInfo var1, Rotation var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.placeInfo;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.rotation;
      }

      return var0.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return "PlaceRotation(placeInfo=" + this.placeInfo + ", rotation=" + this.rotation + ')';
   }

   public int hashCode() {
      int result = this.placeInfo.hashCode();
      result = result * 31 + this.rotation.hashCode();
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof PlaceRotation)) {
         return false;
      } else {
         PlaceRotation var2 = (PlaceRotation)other;
         if (!Intrinsics.areEqual((Object)this.placeInfo, (Object)var2.placeInfo)) {
            return false;
         } else {
            return Intrinsics.areEqual((Object)this.rotation, (Object)var2.rotation);
         }
      }
   }
}
