package com.viaversion.viaversion.libs.mcstructs.snbt;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;

public interface ISNbtSerializer {
   String serialize(Tag var1) throws SNbtSerializeException;
}
