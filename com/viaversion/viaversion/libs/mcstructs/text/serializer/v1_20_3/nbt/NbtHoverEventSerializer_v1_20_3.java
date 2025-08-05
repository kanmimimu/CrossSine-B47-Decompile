package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.EntityHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.ItemHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentCodec;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.CodecUtils_v1_20_3;
import java.util.List;
import java.util.UUID;

public class NbtHoverEventSerializer_v1_20_3 implements ITypedSerializer, CodecUtils_v1_20_3 {
   private static final String ACTION = "action";
   private static final String CONTENTS = "contents";
   private static final String VALUE = "value";
   private final TextComponentCodec codec;
   private final ITypedSerializer textSerializer;
   private final SNbtSerializer sNbtSerializer;

   public NbtHoverEventSerializer_v1_20_3(TextComponentCodec codec, ITypedSerializer textSerializer, SNbtSerializer sNbtSerializer) {
      this.codec = codec;
      this.textSerializer = textSerializer;
      this.sNbtSerializer = sNbtSerializer;
   }

   public Tag serialize(AHoverEvent object) {
      CompoundTag out = new CompoundTag();
      out.putString("action", object.getAction().getName());
      if (object instanceof TextHoverEvent) {
         TextHoverEvent textHoverEvent = (TextHoverEvent)object;
         out.put("contents", (Tag)this.textSerializer.serialize(textHoverEvent.getText()));
      } else if (object instanceof ItemHoverEvent) {
         ItemHoverEvent itemHoverEvent = (ItemHoverEvent)object;
         CompoundTag contents = new CompoundTag();
         contents.putString("id", itemHoverEvent.getItem().get());
         if (itemHoverEvent.getCount() != 1) {
            contents.putInt("count", itemHoverEvent.getCount());
         }

         if (itemHoverEvent.getNbt() != null) {
            try {
               contents.putString("tag", this.sNbtSerializer.serialize(itemHoverEvent.getNbt()));
            } catch (SNbtSerializeException e) {
               throw new IllegalStateException("Failed to serialize nbt", e);
            }
         }

         out.put("contents", contents);
      } else {
         if (!(object instanceof EntityHoverEvent)) {
            throw new IllegalArgumentException("Unknown hover event type: " + object.getClass().getName());
         }

         EntityHoverEvent entityHoverEvent = (EntityHoverEvent)object;
         CompoundTag contents = new CompoundTag();
         contents.putString("type", entityHoverEvent.getEntityType().get());
         contents.put("id", new IntArrayTag(new int[]{(int)(entityHoverEvent.getUuid().getMostSignificantBits() >> 32), (int)(entityHoverEvent.getUuid().getMostSignificantBits() & 4294967295L), (int)(entityHoverEvent.getUuid().getLeastSignificantBits() >> 32), (int)(entityHoverEvent.getUuid().getLeastSignificantBits() & 4294967295L)}));
         if (entityHoverEvent.getName() != null) {
            contents.put("name", (Tag)this.textSerializer.serialize(entityHoverEvent.getName()));
         }

         out.put("contents", contents);
      }

      return out;
   }

   public AHoverEvent deserialize(Tag object) {
      if (!(object instanceof CompoundTag)) {
         throw new IllegalArgumentException("Nbt tag is not a compound tag");
      } else {
         CompoundTag tag = (CompoundTag)object;
         HoverEventAction action = HoverEventAction.getByName(this.requiredString(tag, "action"), false);
         if (action == null) {
            throw new IllegalArgumentException("Unknown hover event action: " + (tag.get("action") instanceof StringTag ? ((StringTag)tag.get("action")).getValue() : ""));
         } else if (!action.isUserDefinable()) {
            throw new IllegalArgumentException("Hover event action is not user definable: " + action);
         } else if (tag.contains("contents")) {
            switch (action) {
               case SHOW_TEXT:
                  return new TextHoverEvent(action, (ATextComponent)this.textSerializer.deserialize(tag.get("contents")));
               case SHOW_ITEM:
                  if (tag.get("contents") instanceof StringTag) {
                     return new ItemHoverEvent(action, Identifier.of(tag.get("contents") instanceof StringTag ? ((StringTag)tag.get("contents")).getValue() : ""), 1, (CompoundTag)null);
                  } else if (!(tag.get("contents") instanceof CompoundTag)) {
                     throw new IllegalArgumentException("Expected string or compound tag for 'contents' tag");
                  } else {
                     CompoundTag contents = tag.get("contents") instanceof CompoundTag ? (CompoundTag)tag.get("contents") : new CompoundTag();
                     String id = this.requiredString(contents, "id");
                     Integer count = this.optionalInt(contents, "count");
                     String itemTag = this.optionalString(contents, "tag");

                     try {
                        return new ItemHoverEvent(action, Identifier.of(id), count == null ? 1 : count, itemTag == null ? null : (CompoundTag)this.sNbtSerializer.deserialize(itemTag));
                     } catch (Throwable t) {
                        this.sneak(t);
                     }
                  }
               case SHOW_ENTITY:
                  CompoundTag contents = this.requiredCompound(tag, "contents");
                  Identifier type = Identifier.of(this.requiredString(contents, "type"));
                  UUID id = this.getUUID(contents.get("id"));
                  ATextComponent name = contents.contains("name") ? (ATextComponent)this.textSerializer.deserialize(contents.get("name")) : null;
                  return new EntityHoverEvent(action, type, id, name);
               default:
                  throw new IllegalArgumentException("Unknown hover event action: " + action);
            }
         } else {
            if (tag.contains("value")) {
               ATextComponent value = (ATextComponent)this.textSerializer.deserialize(tag.get("value"));

               try {
                  switch (action) {
                     case SHOW_TEXT:
                        return new TextHoverEvent(action, value);
                     case SHOW_ITEM:
                        CompoundTag parsed = (CompoundTag)this.sNbtSerializer.deserialize(value.asUnformattedString());
                        Identifier id = Identifier.of(parsed.get("id") instanceof StringTag ? ((StringTag)parsed.get("id")).getValue() : "");
                        int count = parsed.get("Count") instanceof ByteTag ? ((ByteTag)parsed.get("Count")).asByte() : 0;
                        CompoundTag itemTag = parsed.get("tag") instanceof CompoundTag ? (CompoundTag)parsed.get("tag") : null;
                        return new ItemHoverEvent(action, id, count, itemTag);
                     case SHOW_ENTITY:
                        CompoundTag parsed = (CompoundTag)this.sNbtSerializer.deserialize(value.asUnformattedString());
                        ATextComponent name = this.codec.deserializeJson(parsed.get("name") instanceof StringTag ? ((StringTag)parsed.get("name")).getValue() : "");
                        Identifier type = Identifier.of(parsed.get("type") instanceof StringTag ? ((StringTag)parsed.get("type")).getValue() : "");
                        UUID uuid = UUID.fromString(parsed.get("id") instanceof StringTag ? ((StringTag)parsed.get("id")).getValue() : "");
                        return new EntityHoverEvent(action, type, uuid, name);
                     default:
                        throw new IllegalArgumentException("Unknown hover event action: " + action);
                  }
               } catch (Throwable t) {
                  this.sneak(t);
               }
            }

            throw new IllegalArgumentException("Missing 'contents' or 'value' tag");
         }
      }
   }

   protected void sneak(Throwable t) throws Throwable {
      throw t;
   }

   protected UUID getUUID(Tag tag) {
      if (!(tag instanceof IntArrayTag) && !(tag instanceof ListTag) && !(tag instanceof StringTag)) {
         throw new IllegalArgumentException("Expected int array, list or string tag for 'id' tag");
      } else if (tag instanceof StringTag) {
         return UUID.fromString(((StringTag)tag).getValue());
      } else {
         int[] value;
         if (tag instanceof IntArrayTag) {
            value = ((IntArrayTag)tag).getValue();
            if (value.length != 4) {
               throw new IllegalArgumentException("Expected int array with 4 values for 'id' tag");
            }
         } else {
            ListTag<?> list = (ListTag)tag;
            if (list.size() != 4) {
               throw new IllegalArgumentException("Expected list with 4 values for 'id' tag");
            }

            if (!list.getElementType().isAssignableFrom(NumberTag.class)) {
               throw new IllegalArgumentException("Expected list with number values for 'id' tag");
            }

            List<Tag> values = this.unwrapMarkers(list);
            value = new int[4];

            for(int i = 0; i < 4; ++i) {
               value[i] = ((NumberTag)values.get(i)).asInt();
            }
         }

         return new UUID((long)value[0] << 32 | (long)value[1] & 4294967295L, (long)value[2] << 32 | (long)value[3] & 4294967295L);
      }
   }
}
