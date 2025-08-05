package net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityTracker implements StorableObject {
   private final Map entityMap = new ConcurrentHashMap();
   private int playerID;

   public int getPlayerID() {
      return this.playerID;
   }

   public void setPlayerID(int playerID) {
      this.playerID = playerID;
   }

   public void removeEntity(int entityId) {
      this.entityMap.remove(entityId);
   }

   public Map getTrackedEntities() {
      return this.entityMap;
   }
}
