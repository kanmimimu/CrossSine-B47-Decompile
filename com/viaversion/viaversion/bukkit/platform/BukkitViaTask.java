package com.viaversion.viaversion.bukkit.platform;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.platform.PlatformTask;
import java.util.Objects;
import org.bukkit.scheduler.BukkitTask;

public final class BukkitViaTask implements PlatformTask {
   private final BukkitTask task;

   public BukkitViaTask(BukkitTask task) {
      this.task = task;
   }

   public void cancel() {
      Preconditions.checkArgument(this.task != null, "Task cannot be cancelled");
      this.task.cancel();
   }

   public BukkitTask task() {
      return this.task;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BukkitViaTask)) {
         return false;
      } else {
         BukkitViaTask var2 = (BukkitViaTask)var1;
         return Objects.equals(this.task, var2.task);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.task);
   }

   public String toString() {
      return String.format("%s[task=%s]", this.getClass().getSimpleName(), Objects.toString(this.task));
   }
}
