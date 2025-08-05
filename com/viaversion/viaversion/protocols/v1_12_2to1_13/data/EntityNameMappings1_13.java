package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityNameMappings1_13 {
   private static final Map entityNames = new HashMap();

   private static void reg(String past, String future) {
      entityNames.put(Key.namespaced(past), Key.namespaced(future));
   }

   public static String rewrite(String entName) {
      String entityName = (String)entityNames.get(entName);
      if (entityName != null) {
         return entityName;
      } else {
         entityName = (String)entityNames.get(Key.namespaced(entName));
         return (String)(entityName != null ? entityName : Objects.requireNonNull(entName, "defaultObj"));
      }
   }

   static {
      reg("commandblock_minecart", "command_block_minecart");
      reg("ender_crystal", "end_crystal");
      reg("evocation_fangs", "evoker_fangs");
      reg("evocation_illager", "evoker");
      reg("eye_of_ender_signal", "eye_of_ender");
      reg("fireworks_rocket", "firework_rocket");
      reg("illusion_illager", "illusioner");
      reg("snowman", "snow_golem");
      reg("villager_golem", "iron_golem");
      reg("vindication_illager", "vindicator");
      reg("xp_bottle", "experience_bottle");
      reg("xp_orb", "experience_orb");
   }
}
