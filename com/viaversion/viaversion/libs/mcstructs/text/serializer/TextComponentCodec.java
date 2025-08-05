package com.viaversion.viaversion.libs.mcstructs.text.serializer;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonSerializer;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonHoverEventSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonStyleSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonTextSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtHoverEventSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;
import java.io.StringReader;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TextComponentCodec {
   private static final Gson GSON = (new GsonBuilder()).disableHtmlEscaping().create();
   public static final TextComponentCodec V1_20_3 = new TextComponentCodec(() -> SNbtSerializer.V1_14, (codec, sNbtSerializer) -> new JsonTextSerializer_v1_20_3((textSerializer) -> new JsonStyleSerializer_v1_20_3((styleSerializer) -> new JsonHoverEventSerializer_v1_20_3(codec, textSerializer, sNbtSerializer))), (codec, sNbtSerializer) -> new NbtTextSerializer_v1_20_3((textSerializer) -> new NbtStyleSerializer_v1_20_3((styleSerializer) -> new NbtHoverEventSerializer_v1_20_3(codec, textSerializer, sNbtSerializer))));
   public static final TextComponentCodec_v1_20_5 V1_20_5 = new TextComponentCodec_v1_20_5();
   public static final TextComponentCodec_v1_21_2 V1_21_2 = new TextComponentCodec_v1_21_2();
   public static final TextComponentCodec LATEST;
   private final Supplier sNbtSerializerSupplier;
   private final BiFunction jsonSerializerSupplier;
   private final BiFunction nbtSerializerSupplier;
   private SNbtSerializer sNbtSerializer;
   private ITextComponentSerializer jsonSerializer;
   private ITextComponentSerializer nbtSerializer;

   public TextComponentCodec(Supplier sNbtSerializerSupplier, BiFunction jsonSerializerSupplier, BiFunction nbtSerializerSupplier) {
      this.sNbtSerializerSupplier = sNbtSerializerSupplier;
      this.jsonSerializerSupplier = jsonSerializerSupplier;
      this.nbtSerializerSupplier = nbtSerializerSupplier;
   }

   private SNbtSerializer getSNbtSerializer() {
      if (this.sNbtSerializer == null) {
         this.sNbtSerializer = (SNbtSerializer)this.sNbtSerializerSupplier.get();
      }

      return (SNbtSerializer)this.sNbtSerializerSupplier.get();
   }

   public ITextComponentSerializer getJsonSerializer() {
      if (this.jsonSerializer == null) {
         this.jsonSerializer = (ITextComponentSerializer)this.jsonSerializerSupplier.apply(this, this.getSNbtSerializer());
      }

      return this.jsonSerializer;
   }

   public ITextComponentSerializer getNbtSerializer() {
      if (this.nbtSerializer == null) {
         this.nbtSerializer = (ITextComponentSerializer)this.nbtSerializerSupplier.apply(this, this.getSNbtSerializer());
      }

      return this.nbtSerializer;
   }

   public ATextComponent deserializeJson(String json) {
      return this.deserializeJsonTree(JsonParser.parseString(json));
   }

   public ATextComponent deserializeJsonReader(String json) {
      JsonReader reader = new JsonReader(new StringReader(json));
      reader.setLenient(false);

      try {
         return this.deserialize(Streams.parse(reader));
      } catch (StackOverflowError e) {
         throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
      }
   }

   public ATextComponent deserializeLenientJson(String json) {
      JsonReader reader = new JsonReader(new StringReader(json));
      reader.setLenient(true);
      return this.deserializeJsonTree(JsonParser.parseReader(reader));
   }

   public ATextComponent deserializeNbt(String nbt) {
      try {
         return this.deserialize(this.getSNbtSerializer().getDeserializer().deserializeValue(nbt));
      } catch (SNbtDeserializeException e) {
         throw new RuntimeException("Failed to deserialize SNbt", e);
      }
   }

   public ATextComponent deserializeJsonTree(@Nullable JsonElement element) {
      return element == null ? null : this.deserialize(element);
   }

   public ATextComponent deserializeNbtTree(@Nullable Tag nbt) {
      return nbt == null ? null : this.deserialize(nbt);
   }

   public ATextComponent deserialize(JsonElement json) {
      return (ATextComponent)this.getJsonSerializer().deserialize(json);
   }

   public ATextComponent deserialize(Tag nbt) {
      return (ATextComponent)this.getNbtSerializer().deserialize(nbt);
   }

   public JsonElement serializeJsonTree(ATextComponent component) {
      return (JsonElement)this.getJsonSerializer().serialize(component);
   }

   public Tag serializeNbt(ATextComponent component) {
      return (Tag)this.getNbtSerializer().serialize(component);
   }

   public String serializeJsonString(ATextComponent component) {
      return GSON.toJson(this.serializeJsonTree(component));
   }

   public String serializeNbtString(ATextComponent component) {
      try {
         return this.getSNbtSerializer().serialize(this.serializeNbt(component));
      } catch (SNbtSerializeException e) {
         throw new RuntimeException("Failed to serialize SNbt", e);
      }
   }

   public TextComponentSerializer asSerializer() {
      return new TextComponentSerializer(this, () -> (new GsonBuilder()).registerTypeHierarchyAdapter(ATextComponent.class, (JsonSerializer)(src, typeOfSrc, context) -> this.serializeJsonTree(src)).registerTypeHierarchyAdapter(ATextComponent.class, (JsonDeserializer)(src, typeOfSrc, context) -> this.deserializeJsonTree(src)).disableHtmlEscaping().create());
   }

   static {
      LATEST = V1_21_2;
   }
}
