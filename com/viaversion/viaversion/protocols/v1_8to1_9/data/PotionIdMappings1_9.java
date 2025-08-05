package com.viaversion.viaversion.protocols.v1_8to1_9.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import java.util.HashMap;
import java.util.Map;

public class PotionIdMappings1_9 {
   public static final Map POTION_NAME_TO_ID = new HashMap();
   public static final Map POTION_ID_TO_NAME = new HashMap();
   public static final Int2IntMap POTION_INDEX = new Int2IntOpenHashMap(36);

   public static String potionNameFromDamage(short damage) {
      String cached = (String)POTION_ID_TO_NAME.get(Integer.valueOf(damage));
      if (cached != null) {
         return cached;
      } else if (damage == 0) {
         return "water";
      } else {
         int effect = damage & 15;
         int name = damage & 63;
         boolean enhanced = (damage & 32) > 0;
         boolean extended = (damage & 64) > 0;
         boolean canEnhance = true;
         boolean canExtend = true;
         String id;
         switch (effect) {
            case 1:
               id = "regeneration";
               break;
            case 2:
               id = "swiftness";
               break;
            case 3:
               id = "fire_resistance";
               canEnhance = false;
               break;
            case 4:
               id = "poison";
               break;
            case 5:
               id = "healing";
               canExtend = false;
               break;
            case 6:
               id = "night_vision";
               canEnhance = false;
               break;
            case 7:
            default:
               canEnhance = false;
               canExtend = false;
               String var10000;
               switch (name) {
                  case 0:
                     var10000 = "mundane";
                     break;
                  case 16:
                     var10000 = "awkward";
                     break;
                  case 32:
                     var10000 = "thick";
                     break;
                  default:
                     var10000 = "empty";
               }

               id = var10000;
               break;
            case 8:
               id = "weakness";
               canEnhance = false;
               break;
            case 9:
               id = "strength";
               break;
            case 10:
               id = "slowness";
               canEnhance = false;
               break;
            case 11:
               id = "leaping";
               break;
            case 12:
               id = "harming";
               canExtend = false;
               break;
            case 13:
               id = "water_breathing";
               canEnhance = false;
               break;
            case 14:
               id = "invisibility";
               canEnhance = false;
         }

         if (effect > 0) {
            if (canEnhance && enhanced) {
               id = "strong_" + id;
            } else if (canExtend && extended) {
               id = "long_" + id;
            }
         }

         return id;
      }
   }

   public static int getNewPotionID(int oldID) {
      if (oldID >= 16384) {
         oldID -= 8192;
      }

      int index = POTION_INDEX.get(oldID);
      if (index != -1) {
         return index;
      } else {
         oldID = (Integer)POTION_NAME_TO_ID.get(potionNameFromDamage((short)oldID));
         return (index = POTION_INDEX.get(oldID)) != -1 ? index : 0;
      }
   }

   private static void register(int id, String name) {
      POTION_INDEX.put(id, POTION_ID_TO_NAME.size());
      POTION_ID_TO_NAME.put(id, name);
      POTION_NAME_TO_ID.put(name, id);
   }

   static {
      register(-1, "empty");
      register(0, "water");
      register(64, "mundane");
      register(32, "thick");
      register(16, "awkward");
      register(8198, "night_vision");
      register(8262, "long_night_vision");
      register(8206, "invisibility");
      register(8270, "long_invisibility");
      register(8203, "leaping");
      register(8267, "long_leaping");
      register(8235, "strong_leaping");
      register(8195, "fire_resistance");
      register(8259, "long_fire_resistance");
      register(8194, "swiftness");
      register(8258, "long_swiftness");
      register(8226, "strong_swiftness");
      register(8202, "slowness");
      register(8266, "long_slowness");
      register(8205, "water_breathing");
      register(8269, "long_water_breathing");
      register(8261, "healing");
      register(8229, "strong_healing");
      register(8204, "harming");
      register(8236, "strong_harming");
      register(8196, "poison");
      register(8260, "long_poison");
      register(8228, "strong_poison");
      register(8193, "regeneration");
      register(8257, "long_regeneration");
      register(8225, "strong_regeneration");
      register(8201, "strength");
      register(8265, "long_strength");
      register(8233, "strong_strength");
      register(8200, "weakness");
      register(8264, "long_weakness");
   }
}
