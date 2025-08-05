package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.Locale;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityReplacement {
   final BackwardsProtocol protocol;
   final int id;
   final int replacementId;
   final String key;
   ComponentType componentType;
   EntityDataCreator defaultData;

   public EntityReplacement(BackwardsProtocol protocol, EntityType type, int replacementId) {
      this(protocol, type.name(), type.getId(), replacementId);
   }

   public EntityReplacement(BackwardsProtocol protocol, String key, int id, int replacementId) {
      this.componentType = EntityReplacement.ComponentType.NONE;
      this.protocol = protocol;
      this.id = id;
      this.replacementId = replacementId;
      this.key = key.toLowerCase(Locale.ROOT);
   }

   public EntityReplacement jsonName() {
      this.componentType = EntityReplacement.ComponentType.JSON;
      return this;
   }

   public EntityReplacement tagName() {
      this.componentType = EntityReplacement.ComponentType.TAG;
      return this;
   }

   public EntityReplacement plainName() {
      this.componentType = EntityReplacement.ComponentType.PLAIN;
      return this;
   }

   public EntityReplacement spawnEntityData(EntityDataCreator handler) {
      this.defaultData = handler;
      return this;
   }

   public boolean hasBaseData() {
      return this.defaultData != null;
   }

   public int typeId() {
      return this.id;
   }

   public @Nullable Object entityName() {
      if (this.componentType == EntityReplacement.ComponentType.NONE) {
         return null;
      } else {
         String name = this.protocol.getMappingData().mappedEntityName(this.key);
         if (name == null) {
            return null;
         } else if (this.componentType == EntityReplacement.ComponentType.JSON) {
            return ComponentUtil.legacyToJson(name);
         } else {
            return this.componentType == EntityReplacement.ComponentType.TAG ? new StringTag(name) : name;
         }
      }
   }

   public int replacementId() {
      return this.replacementId;
   }

   public @Nullable EntityDataCreator defaultData() {
      return this.defaultData;
   }

   public boolean isObjectType() {
      return false;
   }

   public int objectData() {
      return -1;
   }

   public String toString() {
      EntityDataCreator var8 = this.defaultData;
      ComponentType var7 = this.componentType;
      String var6 = this.key;
      int var5 = this.replacementId;
      int var4 = this.id;
      BackwardsProtocol var3 = this.protocol;
      return "EntityReplacement{protocol=" + var3 + ", id=" + var4 + ", replacementId=" + var5 + ", key='" + var6 + "', componentType=" + var7 + ", defaultData=" + var8 + "}";
   }

   private static enum ComponentType {
      PLAIN,
      JSON,
      TAG,
      NONE;

      // $FF: synthetic method
      static ComponentType[] $values() {
         return new ComponentType[]{PLAIN, JSON, TAG, NONE};
      }
   }

   @FunctionalInterface
   public interface EntityDataCreator {
      void createData(WrappedEntityData var1);
   }
}
