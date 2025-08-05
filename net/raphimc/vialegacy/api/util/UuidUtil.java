package net.raphimc.vialegacy.api.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UuidUtil {
   public static UUID createOfflinePlayerUuid(String name) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
   }
}
