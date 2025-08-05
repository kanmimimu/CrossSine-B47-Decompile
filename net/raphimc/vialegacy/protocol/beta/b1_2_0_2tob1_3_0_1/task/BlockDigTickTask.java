package net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.storage.BlockDigStorage;

public class BlockDigTickTask implements Runnable {
   public void run() {
      for(UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
         BlockDigStorage blockDigStorage = (BlockDigStorage)info.get(BlockDigStorage.class);
         if (blockDigStorage != null) {
            info.getChannel().eventLoop().submit(() -> {
               if (info.getChannel().isActive()) {
                  try {
                     blockDigStorage.tick();
                  } catch (Throwable e) {
                     ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Error while ticking BlockDigStorage", e);
                  }

               }
            });
         }
      }

   }
}
