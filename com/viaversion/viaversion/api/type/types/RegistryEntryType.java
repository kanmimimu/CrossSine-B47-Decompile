package com.viaversion.viaversion.api.type.types;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class RegistryEntryType extends Type {
   public RegistryEntryType() {
      super(RegistryEntry.class);
   }

   public RegistryEntry read(ByteBuf buffer) {
      return new RegistryEntry((String)Types.STRING.read(buffer), (Tag)Types.OPTIONAL_TAG.read(buffer));
   }

   public void write(ByteBuf buffer, RegistryEntry entry) {
      Types.STRING.write(buffer, entry.key());
      Types.OPTIONAL_TAG.write(buffer, entry.tag());
   }
}
