package com.viaversion.viabackwards.protocol.v1_12_2to1_12_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class KeepAliveTracker implements StorableObject {
   private long keepAlive = 2147483647L;

   public long getKeepAlive() {
      return this.keepAlive;
   }

   public void setKeepAlive(long keepAlive) {
      this.keepAlive = keepAlive;
   }

   public String toString() {
      long var3 = this.keepAlive;
      return "KeepAliveTracker{keepAlive=" + var3 + "}";
   }
}
