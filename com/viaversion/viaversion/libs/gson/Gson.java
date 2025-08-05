package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.Excluder;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import com.viaversion.viaversion.libs.gson.internal.Primitives;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.internal.bind.ArrayTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.CollectionTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.DefaultDateTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeWriter;
import com.viaversion.viaversion.libs.gson.internal.bind.MapTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.NumberTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.ObjectTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.SerializationDelegatingTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
import com.viaversion.viaversion.libs.gson.internal.sql.SqlTypesSupport;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
   static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
   static final Strictness DEFAULT_STRICTNESS = null;
   static final FormattingStyle DEFAULT_FORMATTING_STYLE;
   static final boolean DEFAULT_ESCAPE_HTML = true;
   static final boolean DEFAULT_SERIALIZE_NULLS = false;
   static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
   static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
   static final boolean DEFAULT_USE_JDK_UNSAFE = true;
   static final String DEFAULT_DATE_PATTERN;
   static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY;
   static final ToNumberStrategy DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
   static final ToNumberStrategy DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
   private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
   private final ThreadLocal threadLocalAdapterResults;
   private final ConcurrentMap typeTokenCache;
   private final ConstructorConstructor constructorConstructor;
   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
   final List factories;
   final Excluder excluder;
   final FieldNamingStrategy fieldNamingStrategy;
   final Map instanceCreators;
   final boolean serializeNulls;
   final boolean complexMapKeySerialization;
   final boolean generateNonExecutableJson;
   final boolean htmlSafe;
   final FormattingStyle formattingStyle;
   final Strictness strictness;
   final boolean serializeSpecialFloatingPointValues;
   final boolean useJdkUnsafe;
   final String datePattern;
   final int dateStyle;
   final int timeStyle;
   final LongSerializationPolicy longSerializationPolicy;
   final List builderFactories;
   final List builderHierarchyFactories;
   final ToNumberStrategy objectToNumberStrategy;
   final ToNumberStrategy numberToNumberStrategy;
   final List reflectionFilters;

   public Gson() {
      this(Excluder.DEFAULT, DEFAULT_FIELD_NAMING_STRATEGY, Collections.emptyMap(), false, false, false, true, DEFAULT_FORMATTING_STYLE, DEFAULT_STRICTNESS, false, true, LongSerializationPolicy.DEFAULT, DEFAULT_DATE_PATTERN, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), DEFAULT_OBJECT_TO_NUMBER_STRATEGY, DEFAULT_NUMBER_TO_NUMBER_STRATEGY, Collections.emptyList());
   }

   Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map instanceCreators, boolean serializeNulls, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe, FormattingStyle formattingStyle, Strictness strictness, boolean serializeSpecialFloatingPointValues, boolean useJdkUnsafe, LongSerializationPolicy longSerializationPolicy, String datePattern, int dateStyle, int timeStyle, List builderFactories, List builderHierarchyFactories, List factoriesToBeAdded, ToNumberStrategy objectToNumberStrategy, ToNumberStrategy numberToNumberStrategy, List reflectionFilters) {
      this.threadLocalAdapterResults = new ThreadLocal();
      this.typeTokenCache = new ConcurrentHashMap();
      this.excluder = excluder;
      this.fieldNamingStrategy = fieldNamingStrategy;
      this.instanceCreators = instanceCreators;
      this.constructorConstructor = new ConstructorConstructor(instanceCreators, useJdkUnsafe, reflectionFilters);
      this.serializeNulls = serializeNulls;
      this.complexMapKeySerialization = complexMapKeySerialization;
      this.generateNonExecutableJson = generateNonExecutableGson;
      this.htmlSafe = htmlSafe;
      this.formattingStyle = formattingStyle;
      this.strictness = strictness;
      this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
      this.useJdkUnsafe = useJdkUnsafe;
      this.longSerializationPolicy = longSerializationPolicy;
      this.datePattern = datePattern;
      this.dateStyle = dateStyle;
      this.timeStyle = timeStyle;
      this.builderFactories = builderFactories;
      this.builderHierarchyFactories = builderHierarchyFactories;
      this.objectToNumberStrategy = objectToNumberStrategy;
      this.numberToNumberStrategy = numberToNumberStrategy;
      this.reflectionFilters = reflectionFilters;
      List<TypeAdapterFactory> factories = new ArrayList();
      factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
      factories.add(ObjectTypeAdapter.getFactory(objectToNumberStrategy));
      factories.add(excluder);
      factories.addAll(factoriesToBeAdded);
      factories.add(TypeAdapters.STRING_FACTORY);
      factories.add(TypeAdapters.INTEGER_FACTORY);
      factories.add(TypeAdapters.BOOLEAN_FACTORY);
      factories.add(TypeAdapters.BYTE_FACTORY);
      factories.add(TypeAdapters.SHORT_FACTORY);
      TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
      factories.add(TypeAdapters.newFactory(Long.TYPE, Long.class, longAdapter));
      factories.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(serializeSpecialFloatingPointValues)));
      factories.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(serializeSpecialFloatingPointValues)));
      factories.add(NumberTypeAdapter.getFactory(numberToNumberStrategy));
      factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
      factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
      factories.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
      factories.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
      factories.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
      factories.add(TypeAdapters.CHARACTER_FACTORY);
      factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
      factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
      factories.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
      factories.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
      factories.add(TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
      factories.add(TypeAdapters.URL_FACTORY);
      factories.add(TypeAdapters.URI_FACTORY);
      factories.add(TypeAdapters.UUID_FACTORY);
      factories.add(TypeAdapters.CURRENCY_FACTORY);
      factories.add(TypeAdapters.LOCALE_FACTORY);
      factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
      factories.add(TypeAdapters.BIT_SET_FACTORY);
      factories.add(DefaultDateTypeAdapter.DEFAULT_STYLE_FACTORY);
      factories.add(TypeAdapters.CALENDAR_FACTORY);
      if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
         factories.add(SqlTypesSupport.TIME_FACTORY);
         factories.add(SqlTypesSupport.DATE_FACTORY);
         factories.add(SqlTypesSupport.TIMESTAMP_FACTORY);
      }

      factories.add(ArrayTypeAdapter.FACTORY);
      factories.add(TypeAdapters.CLASS_FACTORY);
      factories.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
      factories.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
      this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
      factories.add(this.jsonAdapterFactory);
      factories.add(TypeAdapters.ENUM_FACTORY);
      factories.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory, reflectionFilters));
      this.factories = Collections.unmodifiableList(factories);
   }

   public GsonBuilder newBuilder() {
      return new GsonBuilder(this);
   }

   /** @deprecated */
   @Deprecated
   public Excluder excluder() {
      return this.excluder;
   }

   public FieldNamingStrategy fieldNamingStrategy() {
      return this.fieldNamingStrategy;
   }

   public boolean serializeNulls() {
      return this.serializeNulls;
   }

   public boolean htmlSafe() {
      return this.htmlSafe;
   }

   private TypeAdapter doubleAdapter(boolean serializeSpecialFloatingPointValues) {
      return serializeSpecialFloatingPointValues ? TypeAdapters.DOUBLE : new TypeAdapter() {
         public Double read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
               in.nextNull();
               return null;
            } else {
               return in.nextDouble();
            }
         }

         public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
               out.nullValue();
            } else {
               double doubleValue = value.doubleValue();
               Gson.checkValidFloatingPoint(doubleValue);
               out.value(doubleValue);
            }
         }
      };
   }

   private TypeAdapter floatAdapter(boolean serializeSpecialFloatingPointValues) {
      return serializeSpecialFloatingPointValues ? TypeAdapters.FLOAT : new TypeAdapter() {
         public Float read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
               in.nextNull();
               return null;
            } else {
               return (float)in.nextDouble();
            }
         }

         public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
               out.nullValue();
            } else {
               float floatValue = value.floatValue();
               Gson.checkValidFloatingPoint((double)floatValue);
               Number floatNumber = (Number)(value instanceof Float ? value : floatValue);
               out.value(floatNumber);
            }
         }
      };
   }

   static void checkValidFloatingPoint(double value) {
      if (Double.isNaN(value) || Double.isInfinite(value)) {
         throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
      }
   }

   private static TypeAdapter longAdapter(LongSerializationPolicy longSerializationPolicy) {
      return longSerializationPolicy == LongSerializationPolicy.DEFAULT ? TypeAdapters.LONG : new TypeAdapter() {
         public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
               in.nextNull();
               return null;
            } else {
               return in.nextLong();
            }
         }

         public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
               out.nullValue();
            } else {
               out.value(value.toString());
            }
         }
      };
   }

   private static TypeAdapter atomicLongAdapter(final TypeAdapter longAdapter) {
      return (new TypeAdapter() {
         public void write(JsonWriter out, AtomicLong value) throws IOException {
            longAdapter.write(out, value.get());
         }

         public AtomicLong read(JsonReader in) throws IOException {
            Number value = (Number)longAdapter.read(in);
            return new AtomicLong(value.longValue());
         }
      }).nullSafe();
   }

   private static TypeAdapter atomicLongArrayAdapter(final TypeAdapter longAdapter) {
      return (new TypeAdapter() {
         public void write(JsonWriter out, AtomicLongArray value) throws IOException {
            out.beginArray();
            int i = 0;

            for(int length = value.length(); i < length; ++i) {
               longAdapter.write(out, value.get(i));
            }

            out.endArray();
         }

         public AtomicLongArray read(JsonReader in) throws IOException {
            List<Long> list = new ArrayList();
            in.beginArray();

            while(in.hasNext()) {
               long value = ((Number)longAdapter.read(in)).longValue();
               list.add(value);
            }

            in.endArray();
            int length = list.size();
            AtomicLongArray array = new AtomicLongArray(length);

            for(int i = 0; i < length; ++i) {
               array.set(i, (Long)list.get(i));
            }

            return array;
         }
      }).nullSafe();
   }

   public TypeAdapter getAdapter(TypeToken type) {
      Objects.requireNonNull(type, "type must not be null");
      TypeAdapter<?> cached = (TypeAdapter)this.typeTokenCache.get(type);
      if (cached != null) {
         return cached;
      } else {
         Map<TypeToken<?>, TypeAdapter<?>> threadCalls = (Map)this.threadLocalAdapterResults.get();
         boolean isInitialAdapterRequest = false;
         if (threadCalls == null) {
            threadCalls = new HashMap();
            this.threadLocalAdapterResults.set(threadCalls);
            isInitialAdapterRequest = true;
         } else {
            TypeAdapter<T> ongoingCall = (TypeAdapter)threadCalls.get(type);
            if (ongoingCall != null) {
               return ongoingCall;
            }
         }

         TypeAdapter<T> candidate = null;

         try {
            FutureTypeAdapter<T> call = new FutureTypeAdapter();
            threadCalls.put(type, call);

            for(TypeAdapterFactory factory : this.factories) {
               candidate = factory.create(this, type);
               if (candidate != null) {
                  call.setDelegate(candidate);
                  threadCalls.put(type, candidate);
                  break;
               }
            }
         } finally {
            if (isInitialAdapterRequest) {
               this.threadLocalAdapterResults.remove();
            }

         }

         if (candidate == null) {
            throw new IllegalArgumentException("GSON (2.11.0) cannot handle " + type);
         } else {
            if (isInitialAdapterRequest) {
               this.typeTokenCache.putAll(threadCalls);
            }

            return candidate;
         }
      }
   }

   public TypeAdapter getAdapter(Class type) {
      return this.getAdapter(TypeToken.get(type));
   }

   public TypeAdapter getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken type) {
      Objects.requireNonNull(skipPast, "skipPast must not be null");
      Objects.requireNonNull(type, "type must not be null");
      if (this.jsonAdapterFactory.isClassJsonAdapterFactory(type, skipPast)) {
         skipPast = this.jsonAdapterFactory;
      }

      boolean skipPastFound = false;

      for(TypeAdapterFactory factory : this.factories) {
         if (!skipPastFound) {
            if (factory == skipPast) {
               skipPastFound = true;
            }
         } else {
            TypeAdapter<T> candidate = factory.create(this, type);
            if (candidate != null) {
               return candidate;
            }
         }
      }

      if (skipPastFound) {
         throw new IllegalArgumentException("GSON cannot serialize or deserialize " + type);
      } else {
         return this.getAdapter(type);
      }
   }

   public JsonElement toJsonTree(Object src) {
      return (JsonElement)(src == null ? JsonNull.INSTANCE : this.toJsonTree(src, src.getClass()));
   }

   public JsonElement toJsonTree(Object src, Type typeOfSrc) {
      JsonTreeWriter writer = new JsonTreeWriter();
      this.toJson(src, typeOfSrc, (JsonWriter)writer);
      return writer.get();
   }

   public String toJson(Object src) {
      return src == null ? this.toJson((JsonElement)JsonNull.INSTANCE) : this.toJson((Object)src, (Type)src.getClass());
   }

   public String toJson(Object src, Type typeOfSrc) {
      StringWriter writer = new StringWriter();
      this.toJson(src, typeOfSrc, (Appendable)writer);
      return writer.toString();
   }

   public void toJson(Object src, Appendable writer) throws JsonIOException {
      if (src != null) {
         this.toJson(src, src.getClass(), (Appendable)writer);
      } else {
         this.toJson((JsonElement)JsonNull.INSTANCE, (Appendable)writer);
      }

   }

   public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
      try {
         JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(writer));
         this.toJson(src, typeOfSrc, jsonWriter);
      } catch (IOException e) {
         throw new JsonIOException(e);
      }
   }

   public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
      TypeAdapter<Object> adapter = this.getAdapter(TypeToken.get(typeOfSrc));
      Strictness oldStrictness = writer.getStrictness();
      if (this.strictness != null) {
         writer.setStrictness(this.strictness);
      } else if (writer.getStrictness() == Strictness.LEGACY_STRICT) {
         writer.setStrictness(Strictness.LENIENT);
      }

      boolean oldHtmlSafe = writer.isHtmlSafe();
      boolean oldSerializeNulls = writer.getSerializeNulls();
      writer.setHtmlSafe(this.htmlSafe);
      writer.setSerializeNulls(this.serializeNulls);

      try {
         adapter.write(writer, src);
      } catch (IOException e) {
         throw new JsonIOException(e);
      } catch (AssertionError e) {
         throw new AssertionError("AssertionError (GSON 2.11.0): " + e.getMessage(), e);
      } finally {
         writer.setStrictness(oldStrictness);
         writer.setHtmlSafe(oldHtmlSafe);
         writer.setSerializeNulls(oldSerializeNulls);
      }

   }

   public String toJson(JsonElement jsonElement) {
      StringWriter writer = new StringWriter();
      this.toJson((JsonElement)jsonElement, (Appendable)writer);
      return writer.toString();
   }

   public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
      try {
         JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(writer));
         this.toJson(jsonElement, jsonWriter);
      } catch (IOException e) {
         throw new JsonIOException(e);
      }
   }

   public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
      Strictness oldStrictness = writer.getStrictness();
      boolean oldHtmlSafe = writer.isHtmlSafe();
      boolean oldSerializeNulls = writer.getSerializeNulls();
      writer.setHtmlSafe(this.htmlSafe);
      writer.setSerializeNulls(this.serializeNulls);
      if (this.strictness != null) {
         writer.setStrictness(this.strictness);
      } else if (writer.getStrictness() == Strictness.LEGACY_STRICT) {
         writer.setStrictness(Strictness.LENIENT);
      }

      try {
         Streams.write(jsonElement, writer);
      } catch (IOException e) {
         throw new JsonIOException(e);
      } catch (AssertionError e) {
         throw new AssertionError("AssertionError (GSON 2.11.0): " + e.getMessage(), e);
      } finally {
         writer.setStrictness(oldStrictness);
         writer.setHtmlSafe(oldHtmlSafe);
         writer.setSerializeNulls(oldSerializeNulls);
      }

   }

   public JsonWriter newJsonWriter(Writer writer) throws IOException {
      if (this.generateNonExecutableJson) {
         writer.write(")]}'\n");
      }

      JsonWriter jsonWriter = new JsonWriter(writer);
      jsonWriter.setFormattingStyle(this.formattingStyle);
      jsonWriter.setHtmlSafe(this.htmlSafe);
      jsonWriter.setStrictness(this.strictness == null ? Strictness.LEGACY_STRICT : this.strictness);
      jsonWriter.setSerializeNulls(this.serializeNulls);
      return jsonWriter;
   }

   public JsonReader newJsonReader(Reader reader) {
      JsonReader jsonReader = new JsonReader(reader);
      jsonReader.setStrictness(this.strictness == null ? Strictness.LEGACY_STRICT : this.strictness);
      return jsonReader;
   }

   public Object fromJson(String json, Class classOfT) throws JsonSyntaxException {
      T object = (T)this.fromJson(json, TypeToken.get(classOfT));
      return Primitives.wrap(classOfT).cast(object);
   }

   public Object fromJson(String json, Type typeOfT) throws JsonSyntaxException {
      return this.fromJson(json, TypeToken.get(typeOfT));
   }

   public Object fromJson(String json, TypeToken typeOfT) throws JsonSyntaxException {
      if (json == null) {
         return null;
      } else {
         StringReader reader = new StringReader(json);
         return this.fromJson((Reader)reader, (TypeToken)typeOfT);
      }
   }

   public Object fromJson(Reader json, Class classOfT) throws JsonSyntaxException, JsonIOException {
      T object = (T)this.fromJson(json, TypeToken.get(classOfT));
      return Primitives.wrap(classOfT).cast(object);
   }

   public Object fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
      return this.fromJson(json, TypeToken.get(typeOfT));
   }

   public Object fromJson(Reader json, TypeToken typeOfT) throws JsonIOException, JsonSyntaxException {
      JsonReader jsonReader = this.newJsonReader(json);
      T object = (T)this.fromJson(jsonReader, typeOfT);
      assertFullConsumption(object, jsonReader);
      return object;
   }

   public Object fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
      return this.fromJson(reader, TypeToken.get(typeOfT));
   }

   public Object fromJson(JsonReader reader, TypeToken typeOfT) throws JsonIOException, JsonSyntaxException {
      boolean isEmpty = true;
      Strictness oldStrictness = reader.getStrictness();
      if (this.strictness != null) {
         reader.setStrictness(this.strictness);
      } else if (reader.getStrictness() == Strictness.LEGACY_STRICT) {
         reader.setStrictness(Strictness.LENIENT);
      }

      TypeAdapter<T> typeAdapter;
      try {
         JsonToken unused = reader.peek();
         isEmpty = false;
         typeAdapter = this.getAdapter(typeOfT);
         Object var7 = typeAdapter.read(reader);
         return var7;
      } catch (EOFException e) {
         if (!isEmpty) {
            throw new JsonSyntaxException(e);
         }

         typeAdapter = null;
      } catch (IllegalStateException e) {
         throw new JsonSyntaxException(e);
      } catch (IOException e) {
         throw new JsonSyntaxException(e);
      } catch (AssertionError e) {
         throw new AssertionError("AssertionError (GSON 2.11.0): " + e.getMessage(), e);
      } finally {
         reader.setStrictness(oldStrictness);
      }

      return typeAdapter;
   }

   public Object fromJson(JsonElement json, Class classOfT) throws JsonSyntaxException {
      T object = (T)this.fromJson(json, TypeToken.get(classOfT));
      return Primitives.wrap(classOfT).cast(object);
   }

   public Object fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
      return this.fromJson(json, TypeToken.get(typeOfT));
   }

   public Object fromJson(JsonElement json, TypeToken typeOfT) throws JsonSyntaxException {
      return json == null ? null : this.fromJson((JsonReader)(new JsonTreeReader(json)), (TypeToken)typeOfT);
   }

   private static void assertFullConsumption(Object obj, JsonReader reader) {
      try {
         if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
            throw new JsonSyntaxException("JSON document was not fully consumed.");
         }
      } catch (MalformedJsonException e) {
         throw new JsonSyntaxException(e);
      } catch (IOException e) {
         throw new JsonIOException(e);
      }
   }

   public String toString() {
      return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
   }

   static {
      DEFAULT_FORMATTING_STYLE = FormattingStyle.COMPACT;
      DEFAULT_DATE_PATTERN = null;
      DEFAULT_FIELD_NAMING_STRATEGY = FieldNamingPolicy.IDENTITY;
      DEFAULT_OBJECT_TO_NUMBER_STRATEGY = ToNumberPolicy.DOUBLE;
      DEFAULT_NUMBER_TO_NUMBER_STRATEGY = ToNumberPolicy.LAZILY_PARSED_NUMBER;
   }

   static class FutureTypeAdapter extends SerializationDelegatingTypeAdapter {
      private TypeAdapter delegate = null;

      public void setDelegate(TypeAdapter typeAdapter) {
         if (this.delegate != null) {
            throw new AssertionError("Delegate is already set");
         } else {
            this.delegate = typeAdapter;
         }
      }

      private TypeAdapter delegate() {
         TypeAdapter<T> delegate = this.delegate;
         if (delegate == null) {
            throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
         } else {
            return delegate;
         }
      }

      public TypeAdapter getSerializationDelegate() {
         return this.delegate();
      }

      public Object read(JsonReader in) throws IOException {
         return this.delegate().read(in);
      }

      public void write(JsonWriter out, Object value) throws IOException {
         this.delegate().write(out, value);
      }
   }
}
