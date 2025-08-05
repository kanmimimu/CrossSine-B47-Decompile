package com.viaversion.viaversion.libs.snakeyaml.introspector;

import com.viaversion.viaversion.libs.snakeyaml.error.YAMLException;
import com.viaversion.viaversion.libs.snakeyaml.util.ArrayUtils;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public class MethodProperty extends GenericProperty {
   private final PropertyDescriptor property;
   private final boolean readable;
   private final boolean writable;

   private static Type discoverGenericType(PropertyDescriptor property) {
      Method readMethod = property.getReadMethod();
      if (readMethod != null) {
         return readMethod.getGenericReturnType();
      } else {
         Method writeMethod = property.getWriteMethod();
         if (writeMethod != null) {
            Type[] paramTypes = writeMethod.getGenericParameterTypes();
            if (paramTypes.length > 0) {
               return paramTypes[0];
            }
         }

         return null;
      }
   }

   public MethodProperty(PropertyDescriptor property) {
      super(property.getName(), property.getPropertyType(), discoverGenericType(property));
      this.property = property;
      this.readable = property.getReadMethod() != null;
      this.writable = property.getWriteMethod() != null;
   }

   public void set(Object object, Object value) throws Exception {
      if (!this.writable) {
         throw new YAMLException("No writable property '" + this.getName() + "' on class: " + object.getClass().getName());
      } else {
         this.property.getWriteMethod().invoke(object, value);
      }
   }

   public Object get(Object object) {
      try {
         this.property.getReadMethod().setAccessible(true);
         return this.property.getReadMethod().invoke(object);
      } catch (Exception e) {
         throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + object + ":" + e);
      }
   }

   public List getAnnotations() {
      List<Annotation> annotations;
      if (this.isReadable() && this.isWritable()) {
         annotations = ArrayUtils.toUnmodifiableCompositeList(this.property.getReadMethod().getAnnotations(), this.property.getWriteMethod().getAnnotations());
      } else if (this.isReadable()) {
         annotations = ArrayUtils.toUnmodifiableList(this.property.getReadMethod().getAnnotations());
      } else {
         annotations = ArrayUtils.toUnmodifiableList(this.property.getWriteMethod().getAnnotations());
      }

      return annotations;
   }

   public Annotation getAnnotation(Class annotationType) {
      A annotation = (A)null;
      if (this.isReadable()) {
         annotation = (A)this.property.getReadMethod().getAnnotation(annotationType);
      }

      if (annotation == null && this.isWritable()) {
         annotation = (A)this.property.getWriteMethod().getAnnotation(annotationType);
      }

      return annotation;
   }

   public boolean isWritable() {
      return this.writable;
   }

   public boolean isReadable() {
      return this.readable;
   }
}
