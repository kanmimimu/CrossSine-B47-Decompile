package com.viaversion.viaversion.protocols.v1_20_5to1_21.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EfficiencyAttributeStorage implements StorableObject {
   static final EnchantAttributeModifier EFFICIENCY = new EnchantAttributeModifier("minecraft:enchantment.efficiency/mainhand", 19, (double)0.0F, (level) -> (double)(level * level + 1));
   static final EnchantAttributeModifier SOUL_SPEED = new EnchantAttributeModifier("minecraft:enchantment.soul_speed", 21, 0.1, (level) -> 0.04 + (double)(level - 1) * 0.01);
   static final EnchantAttributeModifier SWIFT_SNEAK = new EnchantAttributeModifier("minecraft:enchantment.swift_sneak", 25, 0.3, (level) -> (double)level * 0.15);
   static final EnchantAttributeModifier AQUA_AFFINITY = new EnchantAttributeModifier("minecraft:enchantment.aqua_affinity", 28, 0.2, (level) -> (double)(level * 4), (byte)2);
   static final EnchantAttributeModifier DEPTH_STRIDER = new EnchantAttributeModifier("minecraft:enchantment.depth_strider", 30, (double)0.0F, (level) -> (double)level / (double)3.0F);
   static final ActiveEnchants DEFAULT;
   final Object lock = new Object();
   volatile boolean attributesSent = true;
   volatile boolean loginSent;
   ActiveEnchants activeEnchants;

   public EfficiencyAttributeStorage() {
      this.activeEnchants = DEFAULT;
   }

   public void setEnchants(int entityId, UserConnection connection, int efficiency, int soulSpeed, int swiftSneak, int aquaAffinity, int depthStrider) {
      if (efficiency != this.activeEnchants.efficiency.level || soulSpeed != this.activeEnchants.soulSpeed.level || swiftSneak != this.activeEnchants.swiftSneak.level || aquaAffinity != this.activeEnchants.aquaAffinity.level || depthStrider != this.activeEnchants.depthStrider.level) {
         synchronized(this.lock) {
            this.activeEnchants = new ActiveEnchants(entityId, new ActiveEnchant(this.activeEnchants.efficiency, efficiency), new ActiveEnchant(this.activeEnchants.soulSpeed, soulSpeed), new ActiveEnchant(this.activeEnchants.swiftSneak, swiftSneak), new ActiveEnchant(this.activeEnchants.aquaAffinity, aquaAffinity), new ActiveEnchant(this.activeEnchants.depthStrider, depthStrider));
            this.attributesSent = false;
         }

         this.sendAttributesPacket(connection, false);
      }
   }

   public ActiveEnchants activeEnchants() {
      return this.activeEnchants;
   }

   public void onLoginSent(UserConnection connection) {
      this.loginSent = true;
      this.sendAttributesPacket(connection, false);
   }

   public void onRespawn(UserConnection connection) {
      this.sendAttributesPacket(connection, true);
   }

   void sendAttributesPacket(UserConnection connection, boolean forceSendAll) {
      ActiveEnchants enchants;
      synchronized(this.lock) {
         if (!forceSendAll && (!this.loginSent || this.attributesSent)) {
            return;
         }

         enchants = this.activeEnchants;
         this.attributesSent = true;
      }

      PacketWrapper attributesPacket = PacketWrapper.create(ClientboundPackets1_21.UPDATE_ATTRIBUTES, (UserConnection)connection);
      attributesPacket.write(Types.VAR_INT, enchants.entityId());
      List<ActiveEnchant> list = Collections.unmodifiableList((List)Stream.of(enchants.efficiency(), enchants.soulSpeed(), enchants.swiftSneak(), enchants.aquaAffinity(), enchants.depthStrider()).filter((enchantx) -> forceSendAll || enchantx.previousLevel != enchantx.level).collect(Collectors.toList()));
      attributesPacket.write(Types.VAR_INT, list.size());

      for(ActiveEnchant enchant : list) {
         EnchantAttributeModifier modifier = enchant.modifier;
         attributesPacket.write(Types.VAR_INT, modifier.attributeId);
         attributesPacket.write(Types.DOUBLE, modifier.baseValue);
         if (enchant.level > 0) {
            attributesPacket.write(Types.VAR_INT, 1);
            attributesPacket.write(Types.STRING, modifier.key);
            attributesPacket.write(Types.DOUBLE, enchant.modifier.modifierFunction.get(enchant.level));
            attributesPacket.write(Types.BYTE, modifier.operation);
         } else {
            attributesPacket.write(Types.VAR_INT, 0);
         }
      }

      attributesPacket.scheduleSend(Protocol1_20_5To1_21.class);
   }

   static {
      DEFAULT = new ActiveEnchants(-1, new ActiveEnchant(EFFICIENCY, 0, 0), new ActiveEnchant(SOUL_SPEED, 0, 0), new ActiveEnchant(SWIFT_SNEAK, 0, 0), new ActiveEnchant(AQUA_AFFINITY, 0, 0), new ActiveEnchant(DEPTH_STRIDER, 0, 0));
   }

   public static final class ActiveEnchants {
      final int entityId;
      final ActiveEnchant efficiency;
      final ActiveEnchant soulSpeed;
      final ActiveEnchant swiftSneak;
      final ActiveEnchant aquaAffinity;
      final ActiveEnchant depthStrider;

      public ActiveEnchants(int entityId, ActiveEnchant efficiency, ActiveEnchant soulSpeed, ActiveEnchant swiftSneak, ActiveEnchant aquaAffinity, ActiveEnchant depthStrider) {
         this.entityId = entityId;
         this.efficiency = efficiency;
         this.soulSpeed = soulSpeed;
         this.swiftSneak = swiftSneak;
         this.aquaAffinity = aquaAffinity;
         this.depthStrider = depthStrider;
      }

      public int entityId() {
         return this.entityId;
      }

      public ActiveEnchant efficiency() {
         return this.efficiency;
      }

      public ActiveEnchant soulSpeed() {
         return this.soulSpeed;
      }

      public ActiveEnchant swiftSneak() {
         return this.swiftSneak;
      }

      public ActiveEnchant aquaAffinity() {
         return this.aquaAffinity;
      }

      public ActiveEnchant depthStrider() {
         return this.depthStrider;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ActiveEnchants)) {
            return false;
         } else {
            ActiveEnchants var2 = (ActiveEnchants)var1;
            return this.entityId == var2.entityId && Objects.equals(this.efficiency, var2.efficiency) && Objects.equals(this.soulSpeed, var2.soulSpeed) && Objects.equals(this.swiftSneak, var2.swiftSneak) && Objects.equals(this.aquaAffinity, var2.aquaAffinity) && Objects.equals(this.depthStrider, var2.depthStrider);
         }
      }

      public int hashCode() {
         return (((((0 * 31 + Integer.hashCode(this.entityId)) * 31 + Objects.hashCode(this.efficiency)) * 31 + Objects.hashCode(this.soulSpeed)) * 31 + Objects.hashCode(this.swiftSneak)) * 31 + Objects.hashCode(this.aquaAffinity)) * 31 + Objects.hashCode(this.depthStrider);
      }

      public String toString() {
         return String.format("%s[entityId=%s, efficiency=%s, soulSpeed=%s, swiftSneak=%s, aquaAffinity=%s, depthStrider=%s]", this.getClass().getSimpleName(), Integer.toString(this.entityId), Objects.toString(this.efficiency), Objects.toString(this.soulSpeed), Objects.toString(this.swiftSneak), Objects.toString(this.aquaAffinity), Objects.toString(this.depthStrider));
      }
   }

   public static final class ActiveEnchant {
      final EnchantAttributeModifier modifier;
      final int previousLevel;
      final int level;

      public ActiveEnchant(ActiveEnchant from, int level) {
         this(from.modifier, from.level, level);
      }

      public ActiveEnchant(EnchantAttributeModifier modifier, int previousLevel, int level) {
         this.modifier = modifier;
         this.previousLevel = previousLevel;
         this.level = level;
      }

      public EnchantAttributeModifier modifier() {
         return this.modifier;
      }

      public int previousLevel() {
         return this.previousLevel;
      }

      public int level() {
         return this.level;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ActiveEnchant)) {
            return false;
         } else {
            ActiveEnchant var2 = (ActiveEnchant)var1;
            return Objects.equals(this.modifier, var2.modifier) && this.previousLevel == var2.previousLevel && this.level == var2.level;
         }
      }

      public int hashCode() {
         return ((0 * 31 + Objects.hashCode(this.modifier)) * 31 + Integer.hashCode(this.previousLevel)) * 31 + Integer.hashCode(this.level);
      }

      public String toString() {
         return String.format("%s[modifier=%s, previousLevel=%s, level=%s]", this.getClass().getSimpleName(), Objects.toString(this.modifier), Integer.toString(this.previousLevel), Integer.toString(this.level));
      }
   }

   public static final class EnchantAttributeModifier {
      final String key;
      final int attributeId;
      final double baseValue;
      final LevelToModifier modifierFunction;
      final byte operation;

      EnchantAttributeModifier(String key, int attributeId, double baseValue, LevelToModifier modifierFunction) {
         this(key, attributeId, baseValue, modifierFunction, (byte)0);
      }

      public EnchantAttributeModifier(String key, int attributeId, double baseValue, LevelToModifier modifierFunction, byte operation) {
         this.key = key;
         this.attributeId = attributeId;
         this.baseValue = baseValue;
         this.modifierFunction = modifierFunction;
         this.operation = operation;
      }

      public String key() {
         return this.key;
      }

      public int attributeId() {
         return this.attributeId;
      }

      public double baseValue() {
         return this.baseValue;
      }

      public LevelToModifier modifierFunction() {
         return this.modifierFunction;
      }

      public byte operation() {
         return this.operation;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof EnchantAttributeModifier)) {
            return false;
         } else {
            EnchantAttributeModifier var2 = (EnchantAttributeModifier)var1;
            return Objects.equals(this.key, var2.key) && this.attributeId == var2.attributeId && Double.compare(this.baseValue, var2.baseValue) == 0 && Objects.equals(this.modifierFunction, var2.modifierFunction) && this.operation == var2.operation;
         }
      }

      public int hashCode() {
         return ((((0 * 31 + Objects.hashCode(this.key)) * 31 + Integer.hashCode(this.attributeId)) * 31 + Double.hashCode(this.baseValue)) * 31 + Objects.hashCode(this.modifierFunction)) * 31 + Byte.hashCode(this.operation);
      }

      public String toString() {
         return String.format("%s[key=%s, attributeId=%s, baseValue=%s, modifierFunction=%s, operation=%s]", this.getClass().getSimpleName(), Objects.toString(this.key), Integer.toString(this.attributeId), Double.toString(this.baseValue), Objects.toString(this.modifierFunction), Byte.toString(this.operation));
      }
   }

   @FunctionalInterface
   private interface LevelToModifier {
      double get(int var1);
   }
}
