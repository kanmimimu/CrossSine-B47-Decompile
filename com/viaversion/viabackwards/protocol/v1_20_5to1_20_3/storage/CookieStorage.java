package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.Map;

public final class CookieStorage implements StorableObject {
   private final Map cookies = new HashMap();

   public Map cookies() {
      return this.cookies;
   }

   public boolean clearOnServerSwitch() {
      return false;
   }
}
