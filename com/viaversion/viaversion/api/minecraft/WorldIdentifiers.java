package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;

public final class WorldIdentifiers implements StorableObject {
   private final String overworld;
   private final String nether;
   private final String end;
   public static final String OVERWORLD_DEFAULT = "minecraft:overworld";
   public static final String NETHER_DEFAULT = "minecraft:the_nether";
   public static final String END_DEFAULT = "minecraft:the_end";

   public WorldIdentifiers(String overworld) {
      this(overworld, "minecraft:the_nether", "minecraft:the_end");
   }

   public WorldIdentifiers(String overworld, String nether, String end) {
      this.overworld = overworld;
      this.nether = nether;
      this.end = end;
   }

   public String overworld() {
      return this.overworld;
   }

   public String nether() {
      return this.nether;
   }

   public String end() {
      return this.end;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof WorldIdentifiers)) {
         return false;
      } else {
         WorldIdentifiers var2 = (WorldIdentifiers)var1;
         return Objects.equals(this.overworld, var2.overworld) && Objects.equals(this.nether, var2.nether) && Objects.equals(this.end, var2.end);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.overworld)) * 31 + Objects.hashCode(this.nether)) * 31 + Objects.hashCode(this.end);
   }

   public String toString() {
      return String.format("%s[overworld=%s, nether=%s, end=%s]", this.getClass().getSimpleName(), Objects.toString(this.overworld), Objects.toString(this.nether), Objects.toString(this.end));
   }
}
