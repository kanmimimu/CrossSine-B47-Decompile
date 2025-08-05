package com.viaversion.viarewind.protocol.v1_9to1_8.task;

import com.viaversion.viarewind.protocol.v1_9to1_8.storage.CooldownStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;

public class CooldownIndicatorTask implements Runnable {
   public void run() {
      for(UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
         ((CooldownStorage)connection.get(CooldownStorage.class)).tick(connection);
      }

   }
}
