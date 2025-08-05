package com.viaversion.viaversion.api.protocol.packet.provider;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketTypeMap {
   @Nullable PacketType typeByName(String var1);

   @Nullable PacketType typeById(int var1);

   Collection types();

   static PacketTypeMap of(Class enumClass) {
      T[] types = (T[])((PacketType[])enumClass.getEnumConstants());
      Preconditions.checkArgument(types != null, "%s is not an enum", new Object[]{enumClass});
      Map<String, T> byName = new HashMap(types.length);

      for(PacketType type : types) {
         byName.put(type.getName(), type);
      }

      return of(byName, types);
   }

   static PacketTypeMap of(Map packetsByName, Int2ObjectMap packetsById) {
      return new PacketTypeMapMap(packetsByName, packetsById);
   }

   static PacketTypeMap of(Map packetsByName, PacketType[] packets) {
      return new PacketTypeArrayMap(packetsByName, packets);
   }
}
