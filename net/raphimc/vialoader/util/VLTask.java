package net.raphimc.vialoader.util;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;
import java.util.Objects;

public final class VLTask implements PlatformTask {
   private final Task task;

   public VLTask(Task task) {
      this.task = task;
   }

   public void cancel() {
      this.task.cancel();
   }

   public Task task() {
      return this.task;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof VLTask)) {
         return false;
      } else {
         VLTask var2 = (VLTask)var1;
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
