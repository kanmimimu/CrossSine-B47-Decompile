package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.channel.EventLoop;
import java.util.Objects;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage.EntityTracker;

public class EntityTrackerTickTask implements Runnable {
   public void run() {
      for(UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
         EntityTracker entityTracker = (EntityTracker)info.get(EntityTracker.class);
         if (entityTracker != null) {
            EventLoop var10000 = info.getChannel().eventLoop();
            Objects.requireNonNull(entityTracker);
            var10000.submit(entityTracker::tick);
         }
      }

   }
}
