package com.viaversion.viaversion.libs.snakeyaml.util;

public class EnumUtils {
   public static Enum findEnumInsensitiveCase(Class enumType, String name) {
      for(Enum constant : (Enum[])enumType.getEnumConstants()) {
         if (constant.name().compareToIgnoreCase(name) == 0) {
            return constant;
         }
      }

      throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." + name);
   }
}
