package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicLevelStorage;

public class ClassicLevelStorageTickTask implements Runnable {
   public void run() {
      for(UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
         ClassicLevelStorage classicLevelStorage = (ClassicLevelStorage)info.get(ClassicLevelStorage.class);
         if (classicLevelStorage != null) {
            info.getChannel().eventLoop().submit(() -> {
               if (info.getChannel().isActive()) {
                  try {
                     classicLevelStorage.tick();
                  } catch (Throwable e) {
                     ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Error while ticking ClassicLevelStorage", e);
                  }

               }
            });
         }
      }

   }
}
