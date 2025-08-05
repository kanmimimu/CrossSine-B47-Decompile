package com.viaversion.viaversion.api.scheduler;

public enum TaskStatus {
   SCHEDULED,
   RUNNING,
   STOPPED;

   // $FF: synthetic method
   private static TaskStatus[] $values() {
      return new TaskStatus[]{SCHEDULED, RUNNING, STOPPED};
   }
}
