package net.raphimc.vialegacy.api.model;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.util.Objects;

public class Location {
   private double x;
   private double y;
   private double z;

   public Location(BlockPosition position) {
      this((double)position.x(), (double)position.y(), (double)position.z());
   }

   public Location(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getX() {
      return this.x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getY() {
      return this.y;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public double getZ() {
      return this.z;
   }

   public double distanceTo(Location p2) {
      return Math.sqrt(Math.pow(p2.getX() - this.x, (double)2.0F) + Math.pow(p2.getY() - this.y, (double)2.0F) + Math.pow(p2.getZ() - this.z, (double)2.0F));
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Location location = (Location)o;
         return Double.compare(location.x, this.x) == 0 && Double.compare(location.y, this.y) == 0 && Double.compare(location.z, this.z) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.x, this.y, this.z});
   }

   public String toString() {
      double var7 = this.z;
      double var5 = this.y;
      double var3 = this.x;
      return "Location{x=" + var3 + ", y=" + var5 + ", z=" + var7 + "}";
   }
}
