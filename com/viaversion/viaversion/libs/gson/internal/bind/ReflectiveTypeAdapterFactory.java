package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.FieldNamingStrategy;
import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.ReflectionAccessFilter;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.annotations.JsonAdapter;
import com.viaversion.viaversion.libs.gson.annotations.SerializedName;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Types;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.Excluder;
import com.viaversion.viaversion.libs.gson.internal.ObjectConstructor;
import com.viaversion.viaversion.libs.gson.internal.Primitives;
import com.viaversion.viaversion.libs.gson.internal.ReflectionAccessFilterHelper;
import com.viaversion.viaversion.libs.gson.internal.TroubleshootingGuide;
import com.viaversion.viaversion.libs.gson.internal.reflect.ReflectionHelper;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
   private final ConstructorConstructor constructorConstructor;
   private final FieldNamingStrategy fieldNamingPolicy;
   private final Excluder excluder;
   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
   private final List reflectionFilters;

   public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory, List reflectionFilters) {
      this.constructorConstructor = constructorConstructor;
      this.fieldNamingPolicy = fieldNamingPolicy;
      this.excluder = excluder;
      this.jsonAdapterFactory = jsonAdapterFactory;
      this.reflectionFilters = reflectionFilters;
   }

   private boolean includeField(Field f, boolean serialize) {
      return !this.excluder.excludeField(f, serialize);
   }

   private List getFieldNames(Field f) {
      SerializedName annotation = (SerializedName)f.getAnnotation(SerializedName.class);
      if (annotation == null) {
         String name = this.fieldNamingPolicy.translateName(f);
         return Collections.singletonList(name);
      } else {
         String serializedName = annotation.value();
         String[] alternates = annotation.alternate();
         if (alternates.length == 0) {
            return Collections.singletonList(serializedName);
         } else {
            List<String> fieldNames = new ArrayList(alternates.length + 1);
            fieldNames.add(serializedName);
            Collections.addAll(fieldNames, alternates);
            return fieldNames;
         }
      }
   }

   public TypeAdapter create(Gson gson, TypeToken type) {
      Class<? super T> raw = type.getRawType();
      if (!Object.class.isAssignableFrom(raw)) {
         return null;
      } else if (ReflectionHelper.isAnonymousOrNonStaticLocal(raw)) {
         return new TypeAdapter() {
            public Object read(JsonReader in) throws IOException {
               in.skipValue();
               return null;
            }

            public void write(JsonWriter out, Object value) throws IOException {
               out.nullValue();
            }

            public String toString() {
               return "AnonymousOrNonStaticLocalClassAdapter";
            }
         };
      } else {
         ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, raw);
         if (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
            throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + raw + ". Register a TypeAdapter for this type or adjust the access filter.");
         } else {
            boolean blockInaccessible = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
            if (ReflectionHelper.isRecord(raw)) {
               TypeAdapter<T> adapter = new RecordAdapter(raw, this.getBoundFields(gson, type, raw, blockInaccessible, true), blockInaccessible);
               return adapter;
            } else {
               ObjectConstructor<T> constructor = this.constructorConstructor.get(type);
               return new FieldReflectionAdapter(constructor, this.getBoundFields(gson, type, raw, blockInaccessible, false));
            }
         }
      }
   }

   private static void checkAccessible(Object object, AccessibleObject member) {
      if (!ReflectionAccessFilterHelper.canAccess(member, Modifier.isStatic(((Member)member).getModifiers()) ? null : object)) {
         String memberDescription = ReflectionHelper.getAccessibleObjectDescription(member, true);
         throw new JsonIOException(memberDescription + " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type.");
      }
   }

   private BoundField createBoundField(Gson context, Field field, final Method accessor, String serializedName, TypeToken fieldType, boolean serialize, final boolean blockInaccessible) {
      final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());
      int modifiers = field.getModifiers();
      final boolean isStaticFinalField = Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
      JsonAdapter annotation = (JsonAdapter)field.getAnnotation(JsonAdapter.class);
      final TypeAdapter<?> mapped = null;
      if (annotation != null) {
         mapped = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, context, fieldType, annotation, false);
      }

      boolean jsonAdapterPresent = mapped != null;
      if (mapped == null) {
         mapped = context.getAdapter(fieldType);
      }

      final TypeAdapter<Object> writeTypeAdapter;
      if (serialize) {
         writeTypeAdapter = (TypeAdapter<Object>)(jsonAdapterPresent ? mapped : new TypeAdapterRuntimeTypeWrapper(context, mapped, fieldType.getType()));
      } else {
         writeTypeAdapter = mapped;
      }

      return new BoundField(serializedName, field) {
         void write(JsonWriter writer, Object source) throws IOException, IllegalAccessException {
            if (blockInaccessible) {
               if (accessor == null) {
                  ReflectiveTypeAdapterFactory.checkAccessible(source, this.field);
               } else {
                  ReflectiveTypeAdapterFactory.checkAccessible(source, accessor);
               }
            }

            Object fieldValue;
            if (accessor != null) {
               try {
                  fieldValue = accessor.invoke(source);
               } catch (InvocationTargetException e) {
                  String accessorDescription = ReflectionHelper.getAccessibleObjectDescription(accessor, false);
                  throw new JsonIOException("Accessor " + accessorDescription + " threw exception", e.getCause());
               }
            } else {
               fieldValue = this.field.get(source);
            }

            if (fieldValue != source) {
               writer.name(this.serializedName);
               writeTypeAdapter.write(writer, fieldValue);
            }
         }

         void readIntoArray(JsonReader reader, int index, Object[] target) throws IOException, JsonParseException {
            Object fieldValue = mapped.read(reader);
            if (fieldValue == null && isPrimitive) {
               throw new JsonParseException("null is not allowed as value for record component '" + this.fieldName + "' of primitive type; at path " + reader.getPath());
            } else {
               target[index] = fieldValue;
            }
         }

         void readIntoField(JsonReader reader, Object target) throws IOException, IllegalAccessException {
            Object fieldValue = mapped.read(reader);
            if (fieldValue != null || !isPrimitive) {
               if (blockInaccessible) {
                  ReflectiveTypeAdapterFactory.checkAccessible(target, this.field);
               } else if (isStaticFinalField) {
                  String fieldDescription = ReflectionHelper.getAccessibleObjectDescription(this.field, false);
                  throw new JsonIOException("Cannot set value of 'static final' " + fieldDescription);
               }

               this.field.set(target, fieldValue);
            }

         }
      };
   }

   private static IllegalArgumentException createDuplicateFieldException(Class declaringType, String duplicateName, Field field1, Field field2) {
      throw new IllegalArgumentException("Class " + declaringType.getName() + " declares multiple JSON fields named '" + duplicateName + "'; conflict is caused by fields " + ReflectionHelper.fieldToString(field1) + " and " + ReflectionHelper.fieldToString(field2) + "\nSee " + TroubleshootingGuide.createUrl("duplicate-fields"));
   }

   private FieldsData getBoundFields(Gson context, TypeToken type, Class raw, boolean blockInaccessible, boolean isRecord) {
      if (raw.isInterface()) {
         return ReflectiveTypeAdapterFactory.FieldsData.EMPTY;
      } else {
         Map<String, BoundField> deserializedFields = new LinkedHashMap();
         Map<String, BoundField> serializedFields = new LinkedHashMap();

         for(Class<?> originalRaw = raw; raw != Object.class; raw = type.getRawType()) {
            Field[] fields = raw.getDeclaredFields();
            if (raw != originalRaw && fields.length > 0) {
               ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, raw);
               if (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
                  throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + raw + " (supertype of " + originalRaw + "). Register a TypeAdapter for this type or adjust the access filter.");
               }

               blockInaccessible = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
            }

            for(Field field : fields) {
               boolean serialize = this.includeField(field, true);
               boolean deserialize = this.includeField(field, false);
               if (serialize || deserialize) {
                  Method accessor = null;
                  if (isRecord) {
                     if (Modifier.isStatic(field.getModifiers())) {
                        deserialize = false;
                     } else {
                        accessor = ReflectionHelper.getAccessor(raw, field);
                        if (!blockInaccessible) {
                           ReflectionHelper.makeAccessible(accessor);
                        }

                        if (accessor.getAnnotation(SerializedName.class) != null && field.getAnnotation(SerializedName.class) == null) {
                           String methodDescription = ReflectionHelper.getAccessibleObjectDescription(accessor, false);
                           throw new JsonIOException("@SerializedName on " + methodDescription + " is not supported");
                        }
                     }
                  }

                  if (!blockInaccessible && accessor == null) {
                     ReflectionHelper.makeAccessible(field);
                  }

                  Type fieldType = $Gson$Types.resolve(type.getType(), raw, field.getGenericType());
                  List<String> fieldNames = this.getFieldNames(field);
                  String serializedName = (String)fieldNames.get(0);
                  BoundField boundField = this.createBoundField(context, field, accessor, serializedName, TypeToken.get(fieldType), serialize, blockInaccessible);
                  if (deserialize) {
                     for(String name : fieldNames) {
                        BoundField replaced = (BoundField)deserializedFields.put(name, boundField);
                        if (replaced != null) {
                           throw createDuplicateFieldException(originalRaw, name, replaced.field, field);
                        }
                     }
                  }

                  if (serialize) {
                     BoundField replaced = (BoundField)serializedFields.put(serializedName, boundField);
                     if (replaced != null) {
                        throw createDuplicateFieldException(originalRaw, serializedName, replaced.field, field);
                     }
                  }
               }
            }

            type = TypeToken.get($Gson$Types.resolve(type.getType(), raw, raw.getGenericSuperclass()));
         }

         return new FieldsData(deserializedFields, new ArrayList(serializedFields.values()));
      }
   }

   private static class FieldsData {
      public static final FieldsData EMPTY = new FieldsData(Collections.emptyMap(), Collections.emptyList());
      public final Map deserializedFields;
      public final List serializedFields;

      public FieldsData(Map deserializedFields, List serializedFields) {
         this.deserializedFields = deserializedFields;
         this.serializedFields = serializedFields;
      }
   }

   abstract static class BoundField {
      final String serializedName;
      final Field field;
      final String fieldName;

      protected BoundField(String serializedName, Field field) {
         this.serializedName = serializedName;
         this.field = field;
         this.fieldName = field.getName();
      }

      abstract void write(JsonWriter var1, Object var2) throws IOException, IllegalAccessException;

      abstract void readIntoArray(JsonReader var1, int var2, Object[] var3) throws IOException, JsonParseException;

      abstract void readIntoField(JsonReader var1, Object var2) throws IOException, IllegalAccessException;
   }

   public abstract static class Adapter extends TypeAdapter {
      private final FieldsData fieldsData;

      Adapter(FieldsData fieldsData) {
         this.fieldsData = fieldsData;
      }

      public void write(JsonWriter out, Object value) throws IOException {
         if (value == null) {
            out.nullValue();
         } else {
            out.beginObject();

            try {
               for(BoundField boundField : this.fieldsData.serializedFields) {
                  boundField.write(out, value);
               }
            } catch (IllegalAccessException e) {
               throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            }

            out.endObject();
         }
      }

      public Object read(JsonReader in) throws IOException {
         if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
         } else {
            A accumulator = (A)this.createAccumulator();
            Map<String, BoundField> deserializedFields = this.fieldsData.deserializedFields;

            try {
               in.beginObject();

               while(in.hasNext()) {
                  String name = in.nextName();
                  BoundField field = (BoundField)deserializedFields.get(name);
                  if (field == null) {
                     in.skipValue();
                  } else {
                     this.readField(accumulator, in, field);
                  }
               }
            } catch (IllegalStateException e) {
               throw new JsonSyntaxException(e);
            } catch (IllegalAccessException e) {
               throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            }

            in.endObject();
            return this.finalize(accumulator);
         }
      }

      abstract Object createAccumulator();

      abstract void readField(Object var1, JsonReader var2, BoundField var3) throws IllegalAccessException, IOException;

      abstract Object finalize(Object var1);
   }

   private static final class FieldReflectionAdapter extends Adapter {
      private final ObjectConstructor constructor;

      FieldReflectionAdapter(ObjectConstructor constructor, FieldsData fieldsData) {
         super(fieldsData);
         this.constructor = constructor;
      }

      Object createAccumulator() {
         return this.constructor.construct();
      }

      void readField(Object accumulator, JsonReader in, BoundField field) throws IllegalAccessException, IOException {
         field.readIntoField(in, accumulator);
      }

      Object finalize(Object accumulator) {
         return accumulator;
      }
   }

   private static final class RecordAdapter extends Adapter {
      static final Map PRIMITIVE_DEFAULTS = primitiveDefaults();
      private final Constructor constructor;
      private final Object[] constructorArgsDefaults;
      private final Map componentIndices = new HashMap();

      RecordAdapter(Class raw, FieldsData fieldsData, boolean blockInaccessible) {
         super(fieldsData);
         this.constructor = ReflectionHelper.getCanonicalRecordConstructor(raw);
         if (blockInaccessible) {
            ReflectiveTypeAdapterFactory.checkAccessible((Object)null, this.constructor);
         } else {
            ReflectionHelper.makeAccessible(this.constructor);
         }

         String[] componentNames = ReflectionHelper.getRecordComponentNames(raw);

         for(int i = 0; i < componentNames.length; ++i) {
            this.componentIndices.put(componentNames[i], i);
         }

         Class<?>[] parameterTypes = this.constructor.getParameterTypes();
         this.constructorArgsDefaults = new Object[parameterTypes.length];

         for(int i = 0; i < parameterTypes.length; ++i) {
            this.constructorArgsDefaults[i] = PRIMITIVE_DEFAULTS.get(parameterTypes[i]);
         }

      }

      private static Map primitiveDefaults() {
         Map<Class<?>, Object> zeroes = new HashMap();
         zeroes.put(Byte.TYPE, (byte)0);
         zeroes.put(Short.TYPE, Short.valueOf((short)0));
         zeroes.put(Integer.TYPE, 0);
         zeroes.put(Long.TYPE, 0L);
         zeroes.put(Float.TYPE, 0.0F);
         zeroes.put(Double.TYPE, (double)0.0F);
         zeroes.put(Character.TYPE, '\u0000');
         zeroes.put(Boolean.TYPE, false);
         return zeroes;
      }

      Object[] createAccumulator() {
         return this.constructorArgsDefaults.clone();
      }

      void readField(Object[] accumulator, JsonReader in, BoundField field) throws IOException {
         Integer componentIndex = (Integer)this.componentIndices.get(field.fieldName);
         if (componentIndex == null) {
            throw new IllegalStateException("Could not find the index in the constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' for field with name '" + field.fieldName + "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters.");
         } else {
            field.readIntoArray(in, componentIndex, accumulator);
         }
      }

      Object finalize(Object[] accumulator) {
         try {
            return this.constructor.newInstance(accumulator);
         } catch (IllegalAccessException e) {
            throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
         } catch (IllegalArgumentException | InstantiationException e) {
            throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(accumulator), e);
         } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(accumulator), e.getCause());
         }
      }
   }
}
