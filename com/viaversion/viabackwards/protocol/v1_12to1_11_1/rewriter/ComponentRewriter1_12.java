package com.viaversion.viabackwards.protocol.v1_12to1_11_1.rewriter;

import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class ComponentRewriter1_12 extends TranslatableRewriter {
   public ComponentRewriter1_12(Protocol1_12To1_11_1 protocol) {
      super(protocol, ComponentRewriter.ReadType.JSON);
   }

   public void processText(UserConnection connection, JsonElement element) {
      super.processText(connection, element);
      if (element != null && element.isJsonObject()) {
         JsonObject object = element.getAsJsonObject();
         JsonElement keybind = object.remove("keybind");
         if (keybind != null) {
            object.addProperty("text", keybind.getAsString());
         }
      }
   }
}
