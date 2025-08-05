package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;

public class BukkitViaTaskTask implements PlatformTask {
   private final Task task;

   public BukkitViaTaskTask(Task task) {
      this.task = task;
   }

   public void cancel() {
      this.task.cancel();
   }
}
