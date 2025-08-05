package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.connection.StorableObject;

public class ClientWorld implements StorableObject {
   private Environment environment;

   public ClientWorld() {
      this.environment = Environment.NORMAL;
   }

   public ClientWorld(Environment environment) {
      this.environment = Environment.NORMAL;
      this.environment = environment;
   }

   public Environment getEnvironment() {
      return this.environment;
   }

   public boolean setEnvironment(int environmentId) {
      int previousEnvironmentId = this.environment.id();
      this.environment = Environment.getEnvironmentById(environmentId);
      return previousEnvironmentId != environmentId;
   }
}
