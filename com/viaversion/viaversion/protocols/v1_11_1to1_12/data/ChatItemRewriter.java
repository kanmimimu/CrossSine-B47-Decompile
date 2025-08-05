package com.viaversion.viaversion.protocols.v1_11_1to1_12.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.SerializerVersion;

public final class ChatItemRewriter {
   public static void toClient(JsonElement element) {
      if (element instanceof JsonObject) {
         JsonObject obj = (JsonObject)element;
         if (obj.has("hoverEvent")) {
            JsonElement var4 = obj.get("hoverEvent");
            if (!(var4 instanceof JsonObject)) {
               return;
            }

            JsonObject hoverEvent = (JsonObject)var4;
            if (!hoverEvent.has("action") || !hoverEvent.has("value")) {
               return;
            }

            String type = hoverEvent.get("action").getAsString();
            JsonElement value = hoverEvent.get("value");
            if (type.equals("show_item")) {
               CompoundTag compound = ComponentUtil.deserializeLegacyShowItem(value, SerializerVersion.V1_8);
               hoverEvent.addProperty("value", SerializerVersion.V1_12.toSNBT(compound));
            }
         } else if (obj.has("extra")) {
            toClient(obj.get("extra"));
         } else if (obj.has("translate") && obj.has("with")) {
            toClient(obj.get("with"));
         }
      } else if (element instanceof JsonArray) {
         for(JsonElement value : (JsonArray)element) {
            toClient(value);
         }
      }

   }
}
