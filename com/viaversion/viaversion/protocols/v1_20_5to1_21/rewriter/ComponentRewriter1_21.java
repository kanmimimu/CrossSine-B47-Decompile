package com.viaversion.viaversion.protocols.v1_20_5to1_21.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.SerializerVersion;
import com.viaversion.viaversion.util.TagUtil;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.UUID;

public final class ComponentRewriter1_21 extends ComponentRewriter {
   public ComponentRewriter1_21(Protocol1_20_5To1_21 protocol) {
      super(protocol, ComponentRewriter.ReadType.NBT);
   }

   private void convertAttributeModifiersComponent(CompoundTag tag) {
      CompoundTag attributeModifiers = TagUtil.getNamespacedCompoundTag(tag, "minecraft:attribute_modifiers");
      if (attributeModifiers != null) {
         for(CompoundTag modifier : attributeModifiers.getListTag("modifiers", CompoundTag.class)) {
            String name = modifier.getString("name");
            UUID uuid = UUIDUtil.fromIntArray(modifier.getIntArrayTag("uuid").getValue());
            String id = Protocol1_20_5To1_21.mapAttributeUUID(uuid, name);
            modifier.putString("id", id);
         }

      }
   }

   protected void handleShowItem(UserConnection connection, CompoundTag itemTag, CompoundTag componentsTag) {
      super.handleShowItem(connection, itemTag, componentsTag);
      if (componentsTag != null) {
         this.convertAttributeModifiersComponent(componentsTag);
      }

   }

   protected SerializerVersion inputSerializerVersion() {
      return SerializerVersion.V1_20_5;
   }

   protected SerializerVersion outputSerializerVersion() {
      return SerializerVersion.V1_20_5;
   }
}
