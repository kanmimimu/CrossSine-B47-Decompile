package net.ccbluex.liquidbounce.utils.geom;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0000¢\u0006\u0002\u0010\u0003B-\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00162\u0006\u0010\u0006\u001a\u00020\u0016J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\r¨\u0006\u0019"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/geom/Rectangle;", "", "rect", "(Lnet/ccbluex/liquidbounce/utils/geom/Rectangle;)V", "x", "", "y", "width", "height", "(FFFF)V", "getHeight", "()F", "setHeight", "(F)V", "getWidth", "setWidth", "getX", "setX", "getY", "setY", "contains", "", "", "point", "Lnet/ccbluex/liquidbounce/utils/geom/Point;", "CrossSine"}
)
public final class Rectangle {
   private float x;
   private float y;
   private float width;
   private float height;

   public Rectangle(float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   // $FF: synthetic method
   public Rectangle(float var1, float var2, float var3, float var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 1) != 0) {
         var1 = Float.NaN;
      }

      if ((var5 & 2) != 0) {
         var2 = Float.NaN;
      }

      if ((var5 & 4) != 0) {
         var3 = Float.NaN;
      }

      if ((var5 & 8) != 0) {
         var4 = Float.NaN;
      }

      this(var1, var2, var3, var4);
   }

   public final float getX() {
      return this.x;
   }

   public final void setX(float var1) {
      this.x = var1;
   }

   public final float getY() {
      return this.y;
   }

   public final void setY(float var1) {
      this.y = var1;
   }

   public final float getWidth() {
      return this.width;
   }

   public final void setWidth(float var1) {
      this.width = var1;
   }

   public final float getHeight() {
      return this.height;
   }

   public final void setHeight(float var1) {
      this.height = var1;
   }

   public Rectangle(@NotNull Rectangle rect) {
      Intrinsics.checkNotNullParameter(rect, "rect");
      this(rect.x, rect.y, rect.width, rect.height);
   }

   public final boolean contains(@NotNull Point point) {
      Intrinsics.checkNotNullParameter(point, "point");
      float var2 = this.x;
      float var3 = this.x + this.width;
      float var4 = point.getX();
      boolean var10000;
      if (var2 <= var4 ? var4 <= var3 : false) {
         var2 = this.y;
         var3 = this.y + this.height;
         var4 = point.getY();
         if (var2 <= var4 ? var4 <= var3 : false) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   public final boolean contains(float x, float y) {
      return this.contains(new Point(x, y));
   }

   public final boolean contains(int x, int y) {
      return this.contains(new Point((float)x, (float)y));
   }

   public Rectangle() {
      this(0.0F, 0.0F, 0.0F, 0.0F, 15, (DefaultConstructorMarker)null);
   }
}
