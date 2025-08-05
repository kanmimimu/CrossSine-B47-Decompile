package com.viaversion.viaversion.api.minecraft;

public final class VillagerData {
   private final int type;
   private final int profession;
   private final int level;

   public VillagerData(int type, int profession, int level) {
      this.type = type;
      this.profession = profession;
      this.level = level;
   }

   public int type() {
      return this.type;
   }

   public int profession() {
      return this.profession;
   }

   public int level() {
      return this.level;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof VillagerData)) {
         return false;
      } else {
         VillagerData var2 = (VillagerData)var1;
         return this.type == var2.type && this.profession == var2.profession && this.level == var2.level;
      }
   }

   public int hashCode() {
      return ((0 * 31 + Integer.hashCode(this.type)) * 31 + Integer.hashCode(this.profession)) * 31 + Integer.hashCode(this.level);
   }

   public String toString() {
      return String.format("%s[type=%s, profession=%s, level=%s]", this.getClass().getSimpleName(), Integer.toString(this.type), Integer.toString(this.profession), Integer.toString(this.level));
   }
}
