package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class ProfileKeyType extends Type {
   public ProfileKeyType() {
      super(ProfileKey.class);
   }

   public ProfileKey read(ByteBuf buffer) {
      return new ProfileKey(buffer.readLong(), (byte[])Types.BYTE_ARRAY_PRIMITIVE.read(buffer), (byte[])Types.BYTE_ARRAY_PRIMITIVE.read(buffer));
   }

   public void write(ByteBuf buffer, ProfileKey object) {
      buffer.writeLong(object.expiresAt());
      Types.BYTE_ARRAY_PRIMITIVE.write(buffer, object.publicKey());
      Types.BYTE_ARRAY_PRIMITIVE.write(buffer, object.keySignature());
   }

   public static final class OptionalProfileKeyType extends OptionalType {
      public OptionalProfileKeyType() {
         super(Types.PROFILE_KEY);
      }
   }
}
