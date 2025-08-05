package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.Map;

public class WindowTracker implements StorableObject {
   public final Map types = new HashMap();

   public short get(short windowId) {
      return (Short)this.types.getOrDefault(windowId, Short.valueOf((short)-1));
   }
}
