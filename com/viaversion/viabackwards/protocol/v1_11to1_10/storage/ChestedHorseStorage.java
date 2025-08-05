package com.viaversion.viabackwards.protocol.v1_11to1_10.storage;

public class ChestedHorseStorage {
   private boolean chested;
   private int liamaStrength;
   private int liamaCarpetColor = -1;
   private int liamaVariant;

   public boolean isChested() {
      return this.chested;
   }

   public void setChested(boolean chested) {
      this.chested = chested;
   }

   public int getLiamaStrength() {
      return this.liamaStrength;
   }

   public void setLiamaStrength(int liamaStrength) {
      this.liamaStrength = liamaStrength;
   }

   public int getLiamaCarpetColor() {
      return this.liamaCarpetColor;
   }

   public void setLiamaCarpetColor(int liamaCarpetColor) {
      this.liamaCarpetColor = liamaCarpetColor;
   }

   public int getLiamaVariant() {
      return this.liamaVariant;
   }

   public void setLiamaVariant(int liamaVariant) {
      this.liamaVariant = liamaVariant;
   }

   public String toString() {
      int var6 = this.liamaVariant;
      int var5 = this.liamaCarpetColor;
      int var4 = this.liamaStrength;
      boolean var3 = this.chested;
      return "ChestedHorseStorage{chested=" + var3 + ", liamaStrength=" + var4 + ", liamaCarpetColor=" + var5 + ", liamaVariant=" + var6 + "}";
   }
}
