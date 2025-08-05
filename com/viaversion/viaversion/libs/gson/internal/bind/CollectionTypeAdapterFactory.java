package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Types;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.ObjectConstructor;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
   private final ConstructorConstructor constructorConstructor;

   public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
      this.constructorConstructor = constructorConstructor;
   }

   public TypeAdapter create(Gson gson, TypeToken typeToken) {
      Type type = typeToken.getType();
      Class<? super T> rawType = typeToken.getRawType();
      if (!Collection.class.isAssignableFrom(rawType)) {
         return null;
      } else {
         Type elementType = $Gson$Types.getCollectionElementType(type, rawType);
         TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
         ObjectConstructor<T> constructor = this.constructorConstructor.get(typeToken);
         TypeAdapter<T> result = new Adapter(gson, elementType, elementTypeAdapter, constructor);
         return result;
      }
   }

   private static final class Adapter extends TypeAdapter {
      private final TypeAdapter elementTypeAdapter;
      private final ObjectConstructor constructor;

      public Adapter(Gson context, Type elementType, TypeAdapter elementTypeAdapter, ObjectConstructor constructor) {
         this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper(context, elementTypeAdapter, elementType);
         this.constructor = constructor;
      }

      public Collection read(JsonReader in) throws IOException {
         if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
         } else {
            Collection<E> collection = (Collection)this.constructor.construct();
            in.beginArray();

            while(in.hasNext()) {
               E instance = (E)this.elementTypeAdapter.read(in);
               collection.add(instance);
            }

            in.endArray();
            return collection;
         }
      }

      public void write(JsonWriter out, Collection collection) throws IOException {
         if (collection == null) {
            out.nullValue();
         } else {
            out.beginArray();

            for(Object element : collection) {
               this.elementTypeAdapter.write(out, element);
            }

            out.endArray();
         }
      }
   }
}
