package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSerializationContext;
import com.viaversion.viaversion.libs.gson.JsonSerializer;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Preconditions;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class TreeTypeAdapter extends SerializationDelegatingTypeAdapter {
   private final JsonSerializer serializer;
   private final JsonDeserializer deserializer;
   final Gson gson;
   private final TypeToken typeToken;
   private final TypeAdapterFactory skipPastForGetDelegateAdapter;
   private final GsonContextImpl context;
   private final boolean nullSafe;
   private volatile TypeAdapter delegate;

   public TreeTypeAdapter(JsonSerializer serializer, JsonDeserializer deserializer, Gson gson, TypeToken typeToken, TypeAdapterFactory skipPast, boolean nullSafe) {
      this.context = new GsonContextImpl();
      this.serializer = serializer;
      this.deserializer = deserializer;
      this.gson = gson;
      this.typeToken = typeToken;
      this.skipPastForGetDelegateAdapter = skipPast;
      this.nullSafe = nullSafe;
   }

   public TreeTypeAdapter(JsonSerializer serializer, JsonDeserializer deserializer, Gson gson, TypeToken typeToken, TypeAdapterFactory skipPast) {
      this(serializer, deserializer, gson, typeToken, skipPast, true);
   }

   public Object read(JsonReader in) throws IOException {
      if (this.deserializer == null) {
         return this.delegate().read(in);
      } else {
         JsonElement value = Streams.parse(in);
         return this.nullSafe && value.isJsonNull() ? null : this.deserializer.deserialize(value, this.typeToken.getType(), this.context);
      }
   }

   public void write(JsonWriter out, Object value) throws IOException {
      if (this.serializer == null) {
         this.delegate().write(out, value);
      } else if (this.nullSafe && value == null) {
         out.nullValue();
      } else {
         JsonElement tree = this.serializer.serialize(value, this.typeToken.getType(), this.context);
         Streams.write(tree, out);
      }
   }

   private TypeAdapter delegate() {
      TypeAdapter<T> d = this.delegate;
      return d != null ? d : (this.delegate = this.gson.getDelegateAdapter(this.skipPastForGetDelegateAdapter, this.typeToken));
   }

   public TypeAdapter getSerializationDelegate() {
      return (TypeAdapter)(this.serializer != null ? this : this.delegate());
   }

   public static TypeAdapterFactory newFactory(TypeToken exactType, Object typeAdapter) {
      return new SingleTypeFactory(typeAdapter, exactType, false, (Class)null);
   }

   public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken exactType, Object typeAdapter) {
      boolean matchRawType = exactType.getType() == exactType.getRawType();
      return new SingleTypeFactory(typeAdapter, exactType, matchRawType, (Class)null);
   }

   public static TypeAdapterFactory newTypeHierarchyFactory(Class hierarchyType, Object typeAdapter) {
      return new SingleTypeFactory(typeAdapter, (TypeToken)null, false, hierarchyType);
   }

   private static final class SingleTypeFactory implements TypeAdapterFactory {
      private final TypeToken exactType;
      private final boolean matchRawType;
      private final Class hierarchyType;
      private final JsonSerializer serializer;
      private final JsonDeserializer deserializer;

      SingleTypeFactory(Object typeAdapter, TypeToken exactType, boolean matchRawType, Class hierarchyType) {
         this.serializer = typeAdapter instanceof JsonSerializer ? (JsonSerializer)typeAdapter : null;
         this.deserializer = typeAdapter instanceof JsonDeserializer ? (JsonDeserializer)typeAdapter : null;
         $Gson$Preconditions.checkArgument(this.serializer != null || this.deserializer != null);
         this.exactType = exactType;
         this.matchRawType = matchRawType;
         this.hierarchyType = hierarchyType;
      }

      public TypeAdapter create(Gson gson, TypeToken type) {
         boolean matches = this.exactType != null ? this.exactType.equals(type) || this.matchRawType && this.exactType.getType() == type.getRawType() : this.hierarchyType.isAssignableFrom(type.getRawType());
         return matches ? new TreeTypeAdapter(this.serializer, this.deserializer, gson, type, this) : null;
      }
   }

   private final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
      private GsonContextImpl() {
      }

      public JsonElement serialize(Object src) {
         return TreeTypeAdapter.this.gson.toJsonTree(src);
      }

      public JsonElement serialize(Object src, Type typeOfSrc) {
         return TreeTypeAdapter.this.gson.toJsonTree(src, typeOfSrc);
      }

      public Object deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
         return TreeTypeAdapter.this.gson.fromJson(json, typeOfT);
      }
   }
}
