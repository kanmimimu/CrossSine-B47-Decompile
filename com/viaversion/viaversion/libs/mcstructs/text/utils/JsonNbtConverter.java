package com.viaversion.viaversion.libs.mcstructs.text.utils;

import com.viaversion.nbt.tag.ByteArrayTag;
import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.DoubleTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.LongTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class JsonNbtConverter {
   @Nullable
   public static JsonElement toJson(@Nullable Tag tag) {
      if (tag == null) {
         return null;
      } else if (tag instanceof NumberTag) {
         return new JsonPrimitive(((NumberTag)tag).getValue());
      } else if (tag instanceof ByteArrayTag) {
         JsonArray byteArray = new JsonArray();

         for(byte b : ((ByteArrayTag)tag).getValue()) {
            byteArray.add((Number)b);
         }

         return byteArray;
      } else if (tag instanceof StringTag) {
         return new JsonPrimitive(((StringTag)tag).getValue());
      } else if (tag instanceof ListTag) {
         JsonArray list = new JsonArray();
         ListTag<Tag> listTag = (ListTag)tag;

         for(Tag tagInList : listTag.getValue()) {
            if (CompoundTag.class == listTag.getElementType()) {
               CompoundTag compound = (CompoundTag)tagInList;
               if (compound.size() == 1) {
                  Tag wrappedTag = compound.get("");
                  if (wrappedTag != null) {
                     tagInList = wrappedTag;
                  }
               }
            }

            list.add(toJson(tagInList));
         }

         return list;
      } else if (tag instanceof CompoundTag) {
         JsonObject compound = new JsonObject();

         for(Map.Entry entry : ((CompoundTag)tag).getValue().entrySet()) {
            compound.add((String)entry.getKey(), toJson((Tag)entry.getValue()));
         }

         return compound;
      } else if (tag instanceof IntArrayTag) {
         JsonArray intArray = new JsonArray();

         for(int i : ((IntArrayTag)tag).getValue()) {
            intArray.add((Number)i);
         }

         return intArray;
      } else if (!(tag instanceof LongArrayTag)) {
         throw new IllegalArgumentException("Unknown Nbt type: " + tag);
      } else {
         JsonArray longArray = new JsonArray();

         for(long l : ((LongArrayTag)tag).getValue()) {
            longArray.add((Number)l);
         }

         return longArray;
      }
   }

   @Nullable
   public static Tag toNbt(@Nullable JsonElement element) {
      if (element == null) {
         return null;
      } else if (element instanceof JsonObject) {
         JsonObject object = element.getAsJsonObject();
         CompoundTag compound = new CompoundTag();

         for(Map.Entry entry : object.entrySet()) {
            compound.put((String)entry.getKey(), toNbt((JsonElement)entry.getValue()));
         }

         return compound;
      } else if (!(element instanceof JsonArray)) {
         if (element instanceof JsonNull) {
            return null;
         } else if (element instanceof JsonPrimitive) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
               return new StringTag(primitive.getAsString());
            } else if (primitive.isBoolean()) {
               return new ByteTag(primitive.getAsBoolean());
            } else {
               BigDecimal number = primitive.getAsBigDecimal();

               try {
                  long l = number.longValueExact();
                  if ((long)((byte)((int)l)) == l) {
                     return new ByteTag((byte)((int)l));
                  } else if ((long)((short)((int)l)) == l) {
                     return new ShortTag((short)((int)l));
                  } else {
                     return (Tag)((long)((int)l) == l ? new IntTag((int)l) : new LongTag(l));
                  }
               } catch (ArithmeticException var9) {
                  double d = number.doubleValue();
                  return (Tag)((double)((float)d) == d ? new FloatTag((float)d) : new DoubleTag(d));
               }
            }
         } else {
            throw new IllegalArgumentException("Unknown JsonElement type: " + element.getClass().getName());
         }
      } else {
         JsonArray array = element.getAsJsonArray();
         List<Tag> nbtTags = new ArrayList();
         Tag listType = null;
         boolean mixedList = false;

         for(JsonElement arrayElement : array) {
            Tag tag = toNbt(arrayElement);
            nbtTags.add(tag);
            listType = getListType(listType, tag);
            if (listType == null) {
               mixedList = true;
            }
         }

         if (listType == null) {
            return new ListTag();
         } else if (mixedList) {
            ListTag<CompoundTag> list = new ListTag();

            for(Tag tag : nbtTags) {
               if (tag instanceof CompoundTag) {
                  list.add((CompoundTag)tag);
               } else {
                  CompoundTag entries = new CompoundTag();
                  entries.put("", tag);
                  list.add(entries);
               }
            }

            return list;
         } else if (listType instanceof ByteTag) {
            byte[] bytes = new byte[nbtTags.size()];

            for(int i = 0; i < nbtTags.size(); ++i) {
               bytes[i] = ((NumberTag)nbtTags.get(i)).asByte();
            }

            return new ByteArrayTag(bytes);
         } else if (listType instanceof IntTag) {
            int[] ints = new int[nbtTags.size()];

            for(int i = 0; i < nbtTags.size(); ++i) {
               ints[i] = ((NumberTag)nbtTags.get(i)).asInt();
            }

            return new IntArrayTag(ints);
         } else if (!(listType instanceof LongTag)) {
            return new ListTag(nbtTags);
         } else {
            long[] longs = new long[nbtTags.size()];

            for(int i = 0; i < nbtTags.size(); ++i) {
               longs[i] = ((NumberTag)nbtTags.get(i)).asLong();
            }

            return new LongArrayTag(longs);
         }
      }
   }

   private static Tag getListType(Tag current, Tag tag) {
      if (current == null) {
         return tag;
      } else {
         return tag != null && current.getClass() == tag.getClass() ? current : null;
      }
   }
}
