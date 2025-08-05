package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.channel.EventLoop;
import java.util.Objects;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.storage.PendingBlocksTracker;

public class BlockReceiveInvalidatorTask implements Runnable {
   public void run() {
      for(UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
         PendingBlocksTracker pendingBlocksTracker = (PendingBlocksTracker)info.get(PendingBlocksTracker.class);
         if (pendingBlocksTracker != null) {
            EventLoop var10000 = info.getChannel().eventLoop();
            Objects.requireNonNull(pendingBlocksTracker);
            var10000.submit(pendingBlocksTracker::tick);
         }
      }

   }
}
