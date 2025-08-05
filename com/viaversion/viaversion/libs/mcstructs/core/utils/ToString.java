package com.viaversion.viaversion.libs.mcstructs.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ToString {
   private final Class clazz;
   private final List fields;

   public static ToString of(Object object) {
      return of(object.getClass());
   }

   public static ToString of(Class clazz) {
      return new ToString(clazz);
   }

   private ToString(Class clazz) {
      this.clazz = clazz;
      this.fields = new ArrayList();
   }

   public ToString add(String name, Object value) {
      return this.add(name, value, (v) -> true);
   }

   public ToString add(String name, Object value, Predicate condition) {
      return this.add(name, value, condition, String::valueOf);
   }

   public ToString add(String name, Object value, Predicate condition, Function mapper) {
      if (condition.test(value)) {
         String val = (String)mapper.apply(value);
         if (value instanceof String) {
            val = "'" + val + "'";
         }

         this.fields.add(name + "=" + val);
      }

      return this;
   }

   public String toString() {
      return this.clazz.getSimpleName() + "{" + String.join(", ", this.fields) + "}";
   }
}
