package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_7;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.DoubleTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.LongTag;
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.ISNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import java.util.Map;

public class SNbtSerializer_v1_7 implements ISNbtSerializer {
   public String serialize(Tag tag) throws SNbtSerializeException {
      if (tag instanceof ByteTag) {
         ByteTag byteTag = (ByteTag)tag;
         return byteTag.getValue() + "b";
      } else if (tag instanceof ShortTag) {
         ShortTag shortTag = (ShortTag)tag;
         return shortTag.getValue() + "s";
      } else if (tag instanceof IntTag) {
         IntTag intTag = (IntTag)tag;
         return String.valueOf(intTag.getValue());
      } else if (tag instanceof LongTag) {
         LongTag longTag = (LongTag)tag;
         return longTag.getValue() + "L";
      } else if (tag instanceof FloatTag) {
         FloatTag floatTag = (FloatTag)tag;
         return floatTag.getValue() + "f";
      } else if (tag instanceof DoubleTag) {
         DoubleTag doubleTag = (DoubleTag)tag;
         return doubleTag.getValue() + "d";
      } else if (tag instanceof StringTag) {
         StringTag stringTag = (StringTag)tag;
         return "\"" + stringTag.getValue() + "\"";
      } else if (tag instanceof ListTag) {
         ListTag<?> listTag = (ListTag)tag;
         StringBuilder out = new StringBuilder("[");

         for(int i = 0; i < listTag.size(); ++i) {
            out.append(i).append(":").append(this.serialize(listTag.get(i))).append(",");
         }

         return out.append("]").toString();
      } else if (tag instanceof CompoundTag) {
         CompoundTag compoundTag = (CompoundTag)tag;
         StringBuilder out = new StringBuilder("{");

         for(Map.Entry entry : compoundTag.getValue().entrySet()) {
            out.append((String)entry.getKey()).append(":").append(this.serialize((Tag)entry.getValue())).append(",");
         }

         return out.append("}").toString();
      } else if (!(tag instanceof IntArrayTag)) {
         throw new SNbtSerializeException(tag);
      } else {
         IntArrayTag intArrayTag = (IntArrayTag)tag;
         StringBuilder out = new StringBuilder("[");

         for(int i : intArrayTag.getValue()) {
            out.append(i).append(",");
         }

         return out.append("]").toString();
      }
   }
}
