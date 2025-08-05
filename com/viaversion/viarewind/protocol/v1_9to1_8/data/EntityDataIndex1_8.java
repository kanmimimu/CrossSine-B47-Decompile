package com.viaversion.viarewind.protocol.v1_9to1_8.data;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EntityDataIndex1_9;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;

public class EntityDataIndex1_8 {
   private static final HashMap ENTITY_DATA_REWRITES = new HashMap();

   private static Optional getIndex(EntityType type, int index) {
      Pair<EntityType, Integer> pair = new Pair(type, index);
      return Optional.ofNullable((EntityDataIndex1_9)ENTITY_DATA_REWRITES.get(pair));
   }

   public static EntityDataIndex1_9 searchIndex(EntityType type, int index) {
      EntityType currentType = type;

      do {
         Optional<EntityDataIndex1_9> optMeta = getIndex(currentType, index);
         if (optMeta.isPresent()) {
            return (EntityDataIndex1_9)optMeta.get();
         }

         currentType = currentType.getParent();
      } while(currentType != null);

      return null;
   }

   static {
      for(EntityDataIndex1_9 index : EntityDataIndex1_9.values()) {
         ENTITY_DATA_REWRITES.put(new Pair(index.getClazz(), index.getNewIndex()), index);
      }

   }
}
