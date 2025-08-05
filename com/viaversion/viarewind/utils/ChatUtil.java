package com.viaversion.viarewind.utils;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.logging.Level;
import java.util.regex.Pattern;

/** @deprecated */
@Deprecated
public class ChatUtil {
   static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
   static final ComponentRewriter LEGACY_REWRITER;

   public static String jsonToLegacy(String json) {
      if (json != null && !json.equals("null") && !json.isEmpty()) {
         try {
            return jsonToLegacy(JsonParser.parseString(json));
         } catch (Exception e) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Could not convert component to legacy text: " + json, e);
            }

            return "";
         }
      } else {
         return "";
      }
   }

   public static String jsonToLegacy(JsonElement component) {
      if (!component.isJsonNull() && (!component.isJsonArray() || !component.getAsJsonArray().isEmpty()) && (!component.isJsonObject() || !component.getAsJsonObject().isEmpty())) {
         if (component.isJsonPrimitive()) {
            return component.getAsString();
         } else {
            try {
               LEGACY_REWRITER.processText((UserConnection)null, (JsonElement)component);

               String legacy;
               for(legacy = ComponentUtil.jsonToLegacy(component); legacy.startsWith("§f"); legacy = legacy.substring(2)) {
               }

               return legacy;
            } catch (Exception ex) {
               if (!Via.getConfig().isSuppressConversionWarnings()) {
                  ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Could not convert component to legacy text: " + component, ex);
               }

               return "";
            }
         }
      } else {
         return "";
      }
   }

   public static String removeUnusedColor(String legacy, char last) {
      if (legacy == null) {
         return null;
      } else {
         legacy = UNUSED_COLOR_PATTERN.matcher(legacy).replaceAll("$1$2");
         StringBuilder builder = new StringBuilder();

         for(int i = 0; i < legacy.length(); ++i) {
            char current = legacy.charAt(i);
            if (current == 167 && i != legacy.length() - 1) {
               ++i;
               current = legacy.charAt(i);
               if (current != last) {
                  builder.append('§').append(current);
                  last = current;
               }
            } else {
               builder.append(current);
            }
         }

         return builder.toString();
      }
   }

   static {
      LEGACY_REWRITER = new ComponentRewriter((Protocol)null, ComponentRewriter.ReadType.JSON) {
         protected void handleTranslate(JsonObject object, String translate) {
            String text = (String)Protocol1_12_2To1_13.MAPPINGS.getMojangTranslation().get(translate);
            if (text != null) {
               object.addProperty("translate", text);
            }

         }
      };
   }
}
