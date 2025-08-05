package com.viaversion.viaversion.api.minecraft;

import java.util.Objects;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class GameProfile {
   final @Nullable String name;
   final @Nullable UUID id;
   final Property[] properties;

   public GameProfile(@Nullable String name, @Nullable UUID id, Property[] properties) {
      this.name = name;
      this.id = id;
      this.properties = properties;
   }

   public @Nullable String name() {
      return this.name;
   }

   public @Nullable UUID id() {
      return this.id;
   }

   public Property[] properties() {
      return this.properties;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof GameProfile)) {
         return false;
      } else {
         GameProfile var2 = (GameProfile)var1;
         return Objects.equals(this.name, var2.name) && Objects.equals(this.id, var2.id) && Objects.equals(this.properties, var2.properties);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.id)) * 31 + Objects.hashCode(this.properties);
   }

   public String toString() {
      return String.format("%s[name=%s, id=%s, properties=%s]", this.getClass().getSimpleName(), Objects.toString(this.name), Objects.toString(this.id), Objects.toString(this.properties));
   }

   public static final class Property {
      final String name;
      final String value;
      final @Nullable String signature;

      public Property(String name, String value, @Nullable String signature) {
         this.name = name;
         this.value = value;
         this.signature = signature;
      }

      public String name() {
         return this.name;
      }

      public String value() {
         return this.value;
      }

      public @Nullable String signature() {
         return this.signature;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof Property)) {
            return false;
         } else {
            Property var2 = (Property)var1;
            return Objects.equals(this.name, var2.name) && Objects.equals(this.value, var2.value) && Objects.equals(this.signature, var2.signature);
         }
      }

      public int hashCode() {
         return ((0 * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.value)) * 31 + Objects.hashCode(this.signature);
      }

      public String toString() {
         return String.format("%s[name=%s, value=%s, signature=%s]", this.getClass().getSimpleName(), Objects.toString(this.name), Objects.toString(this.value), Objects.toString(this.signature));
      }
   }
}
