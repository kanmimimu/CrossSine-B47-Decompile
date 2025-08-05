package com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes;

import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;

public interface ITextComponentSerializer extends ITypedSerializer {
   IStyleSerializer getStyleSerializer();
}
