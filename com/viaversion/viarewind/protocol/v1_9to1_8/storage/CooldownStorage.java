package com.viaversion.viarewind.protocol.v1_9to1_8.storage;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_9to1_8.cooldown.CooldownVisualization;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.util.Pair;
import java.util.List;
import java.util.logging.Level;

public class CooldownStorage implements StorableObject {
   private CooldownVisualization.Factory visualizationFactory = CooldownVisualization.Factory.fromConfiguration();
   private CooldownVisualization current;
   private double attackSpeed = (double)4.0F;
   private long lastHit = 0L;

   public void tick(UserConnection connection) {
      if (!this.hasCooldown()) {
         this.endCurrentVisualization();
      } else {
         BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)connection.get(BlockPlaceDestroyTracker.class);
         if (tracker.isMining()) {
            this.lastHit = 0L;
            this.endCurrentVisualization();
         } else {
            if (this.current == null) {
               this.current = this.visualizationFactory.create(connection);
            }

            try {
               this.current.show(this.getCooldown());
            } catch (Exception exception) {
               ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Unable to show cooldown visualization", exception);
            }

         }
      }
   }

   private void endCurrentVisualization() {
      if (this.current != null) {
         try {
            this.current.hide();
         } catch (Exception exception) {
            ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Unable to hide cooldown visualization", exception);
         }

         this.current = null;
      }

   }

   public boolean hasCooldown() {
      long time = System.currentTimeMillis() - this.lastHit;
      double cooldown = this.restrain((double)time * this.attackSpeed / (double)1000.0F, (double)1.5F);
      return cooldown > 0.1 && cooldown < 1.1;
   }

   public double getCooldown() {
      long time = System.currentTimeMillis() - this.lastHit;
      return this.restrain((double)time * this.attackSpeed / (double)1000.0F, (double)1.0F);
   }

   private double restrain(double x, double b) {
      return x < (double)0.0F ? (double)0.0F : Math.min(x, b);
   }

   public void setAttackSpeed(double base, List modifiers) {
      this.attackSpeed = base;

      for(int j = 0; j < modifiers.size(); ++j) {
         if ((Byte)((Pair)modifiers.get(j)).key() == 0) {
            this.attackSpeed += (Double)((Pair)modifiers.get(j)).value();
            modifiers.remove(j--);
         }
      }

      for(int j = 0; j < modifiers.size(); ++j) {
         if ((Byte)((Pair)modifiers.get(j)).key() == 1) {
            this.attackSpeed += base * (Double)((Pair)modifiers.get(j)).value();
            modifiers.remove(j--);
         }
      }

      for(int j = 0; j < modifiers.size(); ++j) {
         if ((Byte)((Pair)modifiers.get(j)).key() == 2) {
            this.attackSpeed *= (double)1.0F + (Double)((Pair)modifiers.get(j)).value();
            modifiers.remove(j--);
         }
      }

   }

   public void hit() {
      this.lastHit = System.currentTimeMillis();
   }

   public void setLastHit(long lastHit) {
      this.lastHit = lastHit;
   }

   public CooldownVisualization.Factory getVisualizationFactory() {
      return this.visualizationFactory;
   }

   public void setVisualizationFactory(CooldownVisualization.Factory visualizationFactory) {
      this.visualizationFactory = visualizationFactory;
   }
}
