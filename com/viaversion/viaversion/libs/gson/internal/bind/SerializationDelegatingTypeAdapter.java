package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.TypeAdapter;

public abstract class SerializationDelegatingTypeAdapter extends TypeAdapter {
   public abstract TypeAdapter getSerializationDelegate();
}
