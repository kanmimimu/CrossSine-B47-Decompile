package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.nbt;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.EntityHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.ItemHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtHoverEventSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import java.util.UUID;

public class NbtHoverEventSerializer_v1_20_5 extends NbtHoverEventSerializer_v1_20_3 {
   private static final String ACTION = "action";
   private static final String CONTENTS = "contents";
   private static final String VALUE = "value";
   private final TextComponentCodec_v1_20_5 codec;
   private final ITypedSerializer textSerializer;
   private final SNbtSerializer sNbtSerializer;

   public NbtHoverEventSerializer_v1_20_5(TextComponentCodec_v1_20_5 codec, ITypedSerializer textSerializer, SNbtSerializer sNbtSerializer) {
      super(codec, textSerializer, sNbtSerializer);
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
            contents.put("components", itemHoverEvent.getNbt());
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
                     Identifier id = Identifier.of(tag.get("contents") instanceof StringTag ? ((StringTag)tag.get("contents")).getValue() : "");
                     this.verifyItem(id);
                     return new ItemHoverEvent(action, id, 1, (CompoundTag)null);
                  } else {
                     if (tag.get("contents") instanceof CompoundTag) {
                        return this.parseItemHoverEvent(action, tag.get("contents") instanceof CompoundTag ? (CompoundTag)tag.get("contents") : new CompoundTag());
                     }

                     throw new IllegalArgumentException("Expected string or compound tag for 'contents' tag");
                  }
               case SHOW_ENTITY:
                  CompoundTag contents = this.requiredCompound(tag, "contents");
                  Identifier type = Identifier.of(this.requiredString(contents, "type"));
                  this.codec.verifyEntity(type);
                  UUID id = this.getUUID(contents.get("id"));
                  ATextComponent name;
                  if (contents.contains("name")) {
                     try {
                        name = (ATextComponent)this.textSerializer.deserialize(contents.get("name"));
                     } catch (Throwable var9) {
                        name = null;
                     }
                  } else {
                     name = null;
                  }

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
                        return this.parseItemHoverEvent(action, (CompoundTag)this.sNbtSerializer.deserialize(value.asUnformattedString()));
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

   protected ItemHoverEvent parseItemHoverEvent(HoverEventAction action, CompoundTag tag) {
      Identifier id = Identifier.of(this.requiredString(tag, "id"));
      this.verifyItem(id);
      Integer count = this.optionalInt(tag, "count");
      CompoundTag components = this.optionalCompound(tag, "components");
      if (components != null) {
         this.codec.verifyItemComponents(components);
      }

      return new ItemHoverEvent(action, id, count == null ? 1 : count, components);
   }

   protected void verifyItem(Identifier id) {
      this.codec.verifyItem(id);
      if (id.equals(Identifier.of("minecraft:air"))) {
         throw new IllegalArgumentException("Item hover component id is 'minecraft:air'");
      }
   }
}
