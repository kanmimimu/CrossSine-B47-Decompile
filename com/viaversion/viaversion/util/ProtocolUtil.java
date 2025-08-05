package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ProtocolUtil {
   @SafeVarargs
   public static Map packetTypeMap(@Nullable Class parent, Class... packetTypeClasses) {
      if (parent == null) {
         return Collections.emptyMap();
      } else {
         Map<State, PacketTypeMap<P>> map = new EnumMap(State.class);

         for(Class packetTypeClass : packetTypeClasses) {
            P[] types = (P[])((PacketType[])packetTypeClass.getEnumConstants());
            Preconditions.checkArgument(types != null, "%s not an enum", new Object[]{packetTypeClass});
            Preconditions.checkArgument(types.length > 0, "Enum %s has no types", new Object[]{packetTypeClass});
            State state = types[0].state();
            map.put(state, PacketTypeMap.of(packetTypeClass));
         }

         return map;
      }
   }

   public static String toNiceHex(int id) {
      String hex = Integer.toHexString(id).toUpperCase(Locale.ROOT);
      String var3 = hex.length() == 1 ? "0x0" : "0x";
      return var3 + hex;
   }

   public static String toNiceName(Class protocol) {
      return protocol.getSimpleName().replace("Protocol", "").replace("To", "->").replace("_", ".");
   }
}
