package com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.logging.Level;

public class ComponentRewriter1_13 extends ComponentRewriter {
   public ComponentRewriter1_13(Protocol protocol) {
      super(protocol, ComponentRewriter.ReadType.JSON);
   }

   protected void handleHoverEvent(UserConnection connection, JsonObject hoverEvent) {
      super.handleHoverEvent(connection, hoverEvent);
      String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
      if (action.equals("show_item")) {
         JsonElement value = hoverEvent.get("value");
         if (value != null) {
            CompoundTag tag;
            try {
               tag = ComponentUtil.deserializeLegacyShowItem(value, SerializerVersion.V1_12);
            } catch (Exception e) {
               if (!Via.getConfig().isSuppressConversionWarnings()) {
                  Protocol1_12_2To1_13.LOGGER.log(Level.WARNING, "Error reading NBT in show_item: " + value, e);
               }

               return;
            }

            CompoundTag itemTag = tag.getCompoundTag("tag");
            NumberTag damageTag = tag.getNumberTag("Damage");
            short damage = damageTag != null ? damageTag.asShort() : 0;
            Item item = new DataItem();
            item.setData(damage);
            item.setTag(itemTag);
            this.protocol.getItemRewriter().handleItemToClient((UserConnection)null, item);
            if (damage != item.data()) {
               tag.put("Damage", new ShortTag(item.data()));
            }

            if (itemTag != null) {
               tag.put("tag", itemTag);
            }

            JsonArray newValue = new JsonArray();
            JsonObject showItem = new JsonObject();
            newValue.add((JsonElement)showItem);

            try {
               showItem.addProperty("text", SerializerVersion.V1_13.toSNBT(tag));
               hoverEvent.add("value", newValue);
            } catch (Exception e) {
               if (!Via.getConfig().isSuppressConversionWarnings()) {
                  Protocol1_12_2To1_13.LOGGER.log(Level.WARNING, "Error writing NBT in show_item: " + value, e);
               }
            }

         }
      }
   }

   protected void handleTranslate(JsonObject object, String translate) {
      super.handleTranslate(object, translate);
      String newTranslate = (String)Protocol1_12_2To1_13.MAPPINGS.getTranslateMapping().get(translate);
      if (newTranslate == null) {
         newTranslate = (String)Protocol1_12_2To1_13.MAPPINGS.getMojangTranslation().get(translate);
      }

      if (newTranslate != null) {
         object.addProperty("translate", newTranslate);
      }

   }
}
