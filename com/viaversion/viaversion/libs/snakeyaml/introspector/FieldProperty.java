package com.viaversion.viaversion.libs.snakeyaml.introspector;

import com.viaversion.viaversion.libs.snakeyaml.error.YAMLException;
import com.viaversion.viaversion.libs.snakeyaml.util.ArrayUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class FieldProperty extends GenericProperty {
   private final Field field;

   public FieldProperty(Field field) {
      super(field.getName(), field.getType(), field.getGenericType());
      this.field = field;
      field.setAccessible(true);
   }

   public void set(Object object, Object value) throws Exception {
      this.field.set(object, value);
   }

   public Object get(Object object) {
      try {
         return this.field.get(object);
      } catch (Exception e) {
         throw new YAMLException("Unable to access field " + this.field.getName() + " on object " + object + " : " + e);
      }
   }

   public List getAnnotations() {
      return ArrayUtils.toUnmodifiableList(this.field.getAnnotations());
   }

   public Annotation getAnnotation(Class annotationType) {
      return this.field.getAnnotation(annotationType);
   }
}
