package com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter;

import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.SerializerVersion;

public class TranslatableRewriter1_16 extends TranslatableRewriter {
   static final ChatColor[] COLORS = new ChatColor[]{new ChatColor("black", 0), new ChatColor("dark_blue", 170), new ChatColor("dark_green", 43520), new ChatColor("dark_aqua", 43690), new ChatColor("dark_red", 11141120), new ChatColor("dark_purple", 11141290), new ChatColor("gold", 16755200), new ChatColor("gray", 11184810), new ChatColor("dark_gray", 5592405), new ChatColor("blue", 5592575), new ChatColor("green", 5635925), new ChatColor("aqua", 5636095), new ChatColor("red", 16733525), new ChatColor("light_purple", 16733695), new ChatColor("yellow", 16777045), new ChatColor("white", 16777215)};

   public TranslatableRewriter1_16(Protocol1_16To1_15_2 protocol) {
      super(protocol, ComponentRewriter.ReadType.JSON);
   }

   public void processText(UserConnection connection, JsonElement value) {
      super.processText(connection, value);
      if (value != null && value.isJsonObject()) {
         JsonObject object = value.getAsJsonObject();
         JsonPrimitive color = object.getAsJsonPrimitive("color");
         if (color != null) {
            String colorName = color.getAsString();
            if (!colorName.isEmpty() && colorName.charAt(0) == '#') {
               int rgb = Integer.parseInt(colorName.substring(1), 16);
               String closestChatColor = this.getClosestChatColor(rgb);
               object.addProperty("color", closestChatColor);
            }
         }

         JsonObject hoverEvent = object.getAsJsonObject("hoverEvent");
         if (hoverEvent != null && hoverEvent.has("contents")) {
            JsonObject convertedObject = (JsonObject)ComponentUtil.convertJson((JsonElement)object, SerializerVersion.V1_16, SerializerVersion.V1_15);
            object.add("hoverEvent", convertedObject.getAsJsonObject("hoverEvent"));
         }
      }
   }

   String getClosestChatColor(int rgb) {
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      ChatColor closest = null;
      int smallestDiff = 0;

      for(ChatColor color : COLORS) {
         if (color.rgb == rgb) {
            return color.colorName;
         }

         int rAverage = (color.r + r) / 2;
         int rDiff = color.r - r;
         int gDiff = color.g - g;
         int bDiff = color.b - b;
         int diff = (2 + (rAverage >> 8)) * rDiff * rDiff + 4 * gDiff * gDiff + (2 + (255 - rAverage >> 8)) * bDiff * bDiff;
         if (closest == null || diff < smallestDiff) {
            closest = color;
            smallestDiff = diff;
         }
      }

      return closest.colorName;
   }

   private static final class ChatColor {
      final String colorName;
      final int rgb;
      final int r;
      final int g;
      final int b;

      ChatColor(String colorName, int rgb) {
         this.colorName = colorName;
         this.rgb = rgb;
         this.r = rgb >> 16 & 255;
         this.g = rgb >> 8 & 255;
         this.b = rgb & 255;
      }
   }
}
