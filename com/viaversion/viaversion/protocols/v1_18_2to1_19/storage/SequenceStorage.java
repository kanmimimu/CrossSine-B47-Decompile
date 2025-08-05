package com.viaversion.viaversion.protocols.v1_18_2to1_19.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public final class SequenceStorage implements StorableObject {
   private final Object lock = new Object();
   private int sequenceId = -1;

   public int sequenceId() {
      synchronized(this.lock) {
         return this.sequenceId;
      }
   }

   public int setSequenceId(int sequenceId) {
      synchronized(this.lock) {
         int previousSequence = this.sequenceId;
         this.sequenceId = sequenceId;
         return previousSequence;
      }
   }
}
