package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.viaversion.viaversion.api.platform.PlatformTask;
import java.util.Objects;

public final class VelocityViaTask implements PlatformTask {
   private final ScheduledTask task;

   public VelocityViaTask(ScheduledTask task) {
      this.task = task;
   }

   public void cancel() {
      this.task.cancel();
   }

   public ScheduledTask task() {
      return this.task;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof VelocityViaTask)) {
         return false;
      } else {
         VelocityViaTask var2 = (VelocityViaTask)var1;
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
