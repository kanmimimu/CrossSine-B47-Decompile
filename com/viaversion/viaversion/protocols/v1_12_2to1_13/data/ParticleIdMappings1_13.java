package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter.WorldPacketRewriter1_13;
import com.viaversion.viaversion.util.ProtocolLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ParticleIdMappings1_13 {
   static final List particles = new ArrayList();

   public static Particle rewriteParticle(int particleId, Integer[] data) {
      if (particleId >= particles.size()) {
         ProtocolLogger var10000 = Protocol1_12_2To1_13.LOGGER;
         String var5 = Arrays.toString(data);
         var10000.severe("Failed to transform particles with id " + particleId + " and data " + var5);
         return null;
      } else {
         NewParticle rewrite = (NewParticle)particles.get(particleId);
         return rewrite.handle(new Particle(rewrite.id()), data);
      }
   }

   static void add(int newId) {
      particles.add(new NewParticle(newId, (ParticleDataHandler)null));
   }

   static void add(int newId, ParticleDataHandler dataHandler) {
      particles.add(new NewParticle(newId, dataHandler));
   }

   static ParticleDataHandler reddustHandler() {
      return (particle, data) -> {
         particle.add(Types.FLOAT, randomBool() ? 1.0F : 0.0F);
         particle.add(Types.FLOAT, 0.0F);
         particle.add(Types.FLOAT, randomBool() ? 1.0F : 0.0F);
         particle.add(Types.FLOAT, 1.0F);
         return particle;
      };
   }

   static boolean randomBool() {
      return ThreadLocalRandom.current().nextBoolean();
   }

   static ParticleDataHandler iconcrackHandler() {
      return (particle, data) -> {
         Item item;
         if (data.length == 1) {
            item = new DataItem(data[0], (byte)1, (short)0, (CompoundTag)null);
         } else {
            if (data.length != 2) {
               return particle;
            }

            item = new DataItem(data[0], (byte)1, data[1].shortValue(), (CompoundTag)null);
         }

         ((Protocol1_12_2To1_13)Via.getManager().getProtocolManager().getProtocol(Protocol1_12_2To1_13.class)).getItemRewriter().handleItemToClient((UserConnection)null, item);
         particle.add(Types.ITEM1_13, item);
         return particle;
      };
   }

   static ParticleDataHandler blockHandler() {
      return (particle, data) -> {
         int value = data[0];
         int combined = (value & 4095) << 4 | value >> 12 & 15;
         int newId = WorldPacketRewriter1_13.toNewId(combined);
         particle.add(Types.VAR_INT, newId);
         return particle;
      };
   }

   static {
      add(34);
      add(19);
      add(18);
      add(21);
      add(4);
      add(43);
      add(22);
      add(42);
      add(32);
      add(6);
      add(14);
      add(37);
      add(30);
      add(12);
      add(26);
      add(17);
      add(0);
      add(44);
      add(10);
      add(9);
      add(1);
      add(24);
      add(32);
      add(33);
      add(35);
      add(15);
      add(23);
      add(31);
      add(-1);
      add(5);
      add(11, reddustHandler());
      add(29);
      add(34);
      add(28);
      add(25);
      add(2);
      add(27, iconcrackHandler());
      add(3, blockHandler());
      add(3, blockHandler());
      add(36);
      add(-1);
      add(13);
      add(8);
      add(16);
      add(7);
      add(40);
      add(20, blockHandler());
      add(41);
      add(38);
   }

   private static final class NewParticle {
      final int id;
      final @Nullable ParticleDataHandler handler;

      NewParticle(int id, @Nullable ParticleDataHandler handler) {
         this.id = id;
         this.handler = handler;
      }

      public Particle handle(Particle particle, Integer[] data) {
         return this.handler != null ? this.handler.handler(particle, data) : particle;
      }

      public int id() {
         return this.id;
      }

      public @Nullable ParticleDataHandler handler() {
         return this.handler;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof NewParticle)) {
            return false;
         } else {
            NewParticle var2 = (NewParticle)var1;
            return this.id == var2.id && Objects.equals(this.handler, var2.handler);
         }
      }

      public int hashCode() {
         return (0 * 31 + Integer.hashCode(this.id)) * 31 + Objects.hashCode(this.handler);
      }

      public String toString() {
         return String.format("%s[id=%s, handler=%s]", this.getClass().getSimpleName(), Integer.toString(this.id), Objects.toString(this.handler));
      }
   }

   @FunctionalInterface
   interface ParticleDataHandler {
      Particle handler(Particle var1, Integer[] var2);
   }
}
