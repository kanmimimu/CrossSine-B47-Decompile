package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public final class EntityTypeUtil {
   static final EntityType[] EMPTY_ARRAY = new EntityType[0];

   public static EntityType[] toOrderedArray(EntityType[] values) {
      List<EntityType> types = new ArrayList();

      for(EntityType type : values) {
         if (type.getId() != -1) {
            types.add(type);
         }
      }

      types.sort(Comparator.comparingInt(EntityType::getId));
      return (EntityType[])types.toArray(EMPTY_ARRAY);
   }

   public static void initialize(EntityType[] values, EntityType[] typesToFill, Protocol protocol, EntityIdSetter idSetter) {
      FullMappings mappings = protocol.getMappingData().getEntityMappings();

      for(EntityType type : values) {
         if (!type.isAbstractType()) {
            int id = mappings.mappedId(type.identifier());
            Preconditions.checkArgument(id != -1, "Entity type %s has no id", new Object[]{type.identifier()});
            idSetter.setId(type, id);
            typesToFill[id] = type;
         }
      }

      if (typesToFill.length != mappings.mappedSize()) {
         int var10002 = typesToFill.length;
         int var12 = mappings.size();
         int var11 = var10002;
         throw new IllegalArgumentException("typesToFill length doesn't match the amount of entity types: " + var11 + " != " + var12);
      }
   }

   public static EntityType[] createSizedArray(EntityType[] values) {
      int count = 0;

      for(EntityType type : values) {
         if (!type.isAbstractType()) {
            ++count;
         }
      }

      return new EntityType[count];
   }

   public static EntityType getTypeFromId(EntityType[] values, int typeId, EntityType fallback) {
      EntityType type;
      if (typeId >= 0 && typeId < values.length && (type = values[typeId]) != null) {
         return type;
      } else {
         Logger var10000 = Via.getPlatform().getLogger();
         String var5 = fallback.getClass().getSimpleName();
         var10000.severe("Could not find " + var5 + " type id " + typeId);
         return fallback;
      }
   }

   @FunctionalInterface
   public interface EntityIdSetter {
      void setId(EntityType var1, int var2);
   }
}
