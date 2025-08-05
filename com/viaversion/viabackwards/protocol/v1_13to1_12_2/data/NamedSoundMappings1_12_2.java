package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.NamedSoundMappings1_13;
import com.viaversion.viaversion.util.Key;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class NamedSoundMappings1_12_2 {
   private static final Map SOUNDS = new HashMap();

   public static String getOldId(String sound1_13) {
      return (String)SOUNDS.get(Key.stripMinecraftNamespace(sound1_13));
   }

   static {
      try {
         Field field = NamedSoundMappings1_13.class.getDeclaredField("oldToNew");
         field.setAccessible(true);
         Map<String, String> sounds = (Map)field.get((Object)null);
         sounds.forEach((sound1_12, sound1_13) -> SOUNDS.put(sound1_13, sound1_12));
      } catch (IllegalAccessException | NoSuchFieldException ex) {
         ((ReflectiveOperationException)ex).printStackTrace();
      }

   }
}
