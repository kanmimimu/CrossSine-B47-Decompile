package com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.BlockEntityProvider;
import com.viaversion.viaversion.util.ComponentUtil;

public class CommandBlockHandler implements BlockEntityProvider.BlockEntityHandler {
   private final Protocol1_12_2To1_13 protocol = (Protocol1_12_2To1_13)Via.getManager().getProtocolManager().getProtocol(Protocol1_12_2To1_13.class);

   public int transform(UserConnection user, CompoundTag tag) {
      StringTag name = tag.getStringTag("CustomName");
      if (name != null) {
         name.setValue(ComponentUtil.legacyToJsonString(name.getValue()));
      }

      StringTag out = tag.getStringTag("LastOutput");
      if (out != null) {
         JsonElement value = JsonParser.parseString(out.getValue());
         this.protocol.getComponentRewriter().processText(user, value);
         out.setValue(value.toString());
      }

      return -1;
   }
}
