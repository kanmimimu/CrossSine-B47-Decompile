package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

public final class RecipeData {
   public static Map recipes;

   public static void init() {
      InputStream stream = MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/itemrecipes1_12_2to1_13.json");

      try {
         InputStreamReader reader = new InputStreamReader(stream);

         try {
            recipes = (Map)GsonUtil.getGson().fromJson((Reader)reader, (Type)(new TypeToken() {
            }).getType());
         } catch (Throwable var5) {
            try {
               reader.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }

            throw var5;
         }

         reader.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static final class Recipe {
      final String type;
      final String group;
      final int width;
      final int height;
      final float experience;
      final int cookingTime;
      final DataItem[] ingredient;
      final DataItem[][] ingredients;
      final DataItem result;

      public Recipe(String type, String group, int width, int height, float experience, int cookingTime, DataItem[] ingredient, DataItem[][] ingredients, DataItem result) {
         this.type = type;
         this.group = group;
         this.width = width;
         this.height = height;
         this.experience = experience;
         this.cookingTime = cookingTime;
         this.ingredient = ingredient;
         this.ingredients = ingredients;
         this.result = result;
      }

      public String type() {
         return this.type;
      }

      public String group() {
         return this.group;
      }

      public int width() {
         return this.width;
      }

      public int height() {
         return this.height;
      }

      public float experience() {
         return this.experience;
      }

      public int cookingTime() {
         return this.cookingTime;
      }

      public DataItem[] ingredient() {
         return this.ingredient;
      }

      public DataItem[][] ingredients() {
         return this.ingredients;
      }

      public DataItem result() {
         return this.result;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof Recipe)) {
            return false;
         } else {
            Recipe var2 = (Recipe)var1;
            return Objects.equals(this.type, var2.type) && Objects.equals(this.group, var2.group) && this.width == var2.width && this.height == var2.height && Float.compare(this.experience, var2.experience) == 0 && this.cookingTime == var2.cookingTime && Objects.equals(this.ingredient, var2.ingredient) && Objects.equals(this.ingredients, var2.ingredients) && Objects.equals(this.result, var2.result);
         }
      }

      public int hashCode() {
         return ((((((((0 * 31 + Objects.hashCode(this.type)) * 31 + Objects.hashCode(this.group)) * 31 + Integer.hashCode(this.width)) * 31 + Integer.hashCode(this.height)) * 31 + Float.hashCode(this.experience)) * 31 + Integer.hashCode(this.cookingTime)) * 31 + Objects.hashCode(this.ingredient)) * 31 + Objects.hashCode(this.ingredients)) * 31 + Objects.hashCode(this.result);
      }

      public String toString() {
         return String.format("%s[type=%s, group=%s, width=%s, height=%s, experience=%s, cookingTime=%s, ingredient=%s, ingredients=%s, result=%s]", this.getClass().getSimpleName(), Objects.toString(this.type), Objects.toString(this.group), Integer.toString(this.width), Integer.toString(this.height), Float.toString(this.experience), Integer.toString(this.cookingTime), Objects.toString(this.ingredient), Objects.toString(this.ingredients), Objects.toString(this.result));
      }
   }
}
