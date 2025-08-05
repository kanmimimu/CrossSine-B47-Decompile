package com.viaversion.viaversion.protocols.v1_11_1to1_12.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.logging.Level;

public class TranslateRewriter {
   static final ComponentRewriter ACHIEVEMENT_TEXT_REWRITER;

   public static void toClient(UserConnection connection, JsonElement element) {
      if (element instanceof JsonObject) {
         JsonObject obj = (JsonObject)element;
         JsonElement translate = obj.get("translate");
         if (translate != null && translate.getAsString().startsWith("chat.type.achievement")) {
            ACHIEVEMENT_TEXT_REWRITER.processText(connection, (JsonElement)obj);
         }
      }

   }

   static {
      ACHIEVEMENT_TEXT_REWRITER = new ComponentRewriter((Protocol)null, ComponentRewriter.ReadType.JSON) {
         protected void handleTranslate(JsonObject object, String translate) {
            String text = AchievementTranslations1_12.get(translate);
            if (text != null) {
               object.addProperty("translate", text);
            }

         }

         protected void handleHoverEvent(UserConnection connection, JsonObject hoverEvent) {
            String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
            if (!action.equals("show_achievement")) {
               super.handleHoverEvent(connection, hoverEvent);
            } else {
               String textValue = SerializerVersion.V1_9.toComponent(hoverEvent.get("value")).asUnformattedString();
               if (AchievementTranslations1_12.get(textValue) == null) {
                  JsonObject invalidText = new JsonObject();
                  invalidText.addProperty("text", "Invalid statistic/achievement!");
                  invalidText.addProperty("color", "red");
                  hoverEvent.addProperty("action", "show_text");
                  hoverEvent.add("value", invalidText);
                  super.handleHoverEvent(connection, hoverEvent);
               } else {
                  try {
                     JsonObject newLine = new JsonObject();
                     newLine.addProperty("text", "\n");
                     JsonArray baseArray = new JsonArray();
                     baseArray.add("");
                     JsonObject namePart = new JsonObject();
                     JsonObject typePart = new JsonObject();
                     baseArray.add((JsonElement)namePart);
                     baseArray.add((JsonElement)newLine);
                     baseArray.add((JsonElement)typePart);
                     if (textValue.startsWith("achievement")) {
                        namePart.addProperty("translate", textValue);
                        namePart.addProperty("color", AchievementTranslations1_12.isSpecial(textValue) ? "dark_purple" : "green");
                        typePart.addProperty("translate", "stats.tooltip.type.achievement");
                        JsonObject description = new JsonObject();
                        typePart.addProperty("italic", true);
                        description.addProperty("translate", textValue + ".desc");
                        baseArray.add((JsonElement)newLine);
                        baseArray.add((JsonElement)description);
                     } else if (textValue.startsWith("stat")) {
                        namePart.addProperty("translate", textValue);
                        namePart.addProperty("color", "gray");
                        typePart.addProperty("translate", "stats.tooltip.type.statistic");
                        typePart.addProperty("italic", true);
                     }

                     hoverEvent.addProperty("action", "show_text");
                     hoverEvent.add("value", baseArray);
                  } catch (Exception e) {
                     if (!Via.getConfig().isSuppressConversionWarnings()) {
                        this.protocol.getLogger().log(Level.WARNING, "Error rewriting show_achievement: " + hoverEvent, e);
                     }

                     JsonObject invalidText = new JsonObject();
                     invalidText.addProperty("text", "Invalid statistic/achievement!");
                     invalidText.addProperty("color", "red");
                     hoverEvent.addProperty("action", "show_text");
                     hoverEvent.add("value", invalidText);
                  }

                  super.handleHoverEvent(connection, hoverEvent);
               }
            }
         }
      };
   }
}
