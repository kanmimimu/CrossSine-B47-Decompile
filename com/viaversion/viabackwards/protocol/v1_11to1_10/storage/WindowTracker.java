package com.viaversion.viabackwards.protocol.v1_11to1_10.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class WindowTracker implements StorableObject {
   private String inventory;
   private int entityId = -1;

   public String getInventory() {
      return this.inventory;
   }

   public void setInventory(String inventory) {
      this.inventory = inventory;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public void setEntityId(int entityId) {
      this.entityId = entityId;
   }

   public String toString() {
      int var4 = this.entityId;
      String var3 = this.inventory;
      return "WindowTracker{inventory='" + var3 + "', entityId=" + var4 + "}";
   }
}
