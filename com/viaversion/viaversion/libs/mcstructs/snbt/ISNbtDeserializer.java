package com.viaversion.viaversion.libs.mcstructs.snbt;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;

public interface ISNbtDeserializer {
   Tag deserialize(String var1) throws SNbtDeserializeException;

   Tag deserializeValue(String var1) throws SNbtDeserializeException;
}
