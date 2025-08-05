package com.viaversion.viaversion.protocols.v1_20to1_20_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundConfigurationPackets1_20_2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LastTags implements StorableObject {
   final List registryTags = new ArrayList();

   public LastTags(PacketWrapper wrapper) {
      int length = (Integer)wrapper.passthrough(Types.VAR_INT);

      for(int i = 0; i < length; ++i) {
         List<TagData> tags = new ArrayList();
         String registryKey = (String)wrapper.passthrough(Types.STRING);
         int tagsSize = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int j = 0; j < tagsSize; ++j) {
            String key = (String)wrapper.passthrough(Types.STRING);
            int[] ids = (int[])wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
            tags.add(new TagData(key, ids));
         }

         this.registryTags.add(new RegistryTags(registryKey, tags));
      }

   }

   public void sendLastTags(UserConnection connection) {
      if (!this.registryTags.isEmpty()) {
         PacketWrapper packet = PacketWrapper.create(ClientboundConfigurationPackets1_20_2.UPDATE_TAGS, (UserConnection)connection);
         packet.write(Types.VAR_INT, this.registryTags.size());

         for(RegistryTags registryTag : this.registryTags) {
            packet.write(Types.STRING, registryTag.registryKey);
            packet.write(Types.VAR_INT, registryTag.tags.size());

            for(TagData tag : registryTag.tags) {
               packet.write(Types.STRING, tag.identifier());
               packet.write(Types.VAR_INT_ARRAY_PRIMITIVE, Arrays.copyOf(tag.entries(), tag.entries().length));
            }
         }

         packet.send(Protocol1_20To1_20_2.class);
      }
   }

   private static final class RegistryTags {
      final String registryKey;
      final List tags;

      RegistryTags(String registryKey, List tags) {
         this.registryKey = registryKey;
         this.tags = tags;
      }

      public String registryKey() {
         return this.registryKey;
      }

      public List tags() {
         return this.tags;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof RegistryTags)) {
            return false;
         } else {
            RegistryTags var2 = (RegistryTags)var1;
            return Objects.equals(this.registryKey, var2.registryKey) && Objects.equals(this.tags, var2.tags);
         }
      }

      public int hashCode() {
         return (0 * 31 + Objects.hashCode(this.registryKey)) * 31 + Objects.hashCode(this.tags);
      }

      public String toString() {
         return String.format("%s[registryKey=%s, tags=%s]", this.getClass().getSimpleName(), Objects.toString(this.registryKey), Objects.toString(this.tags));
      }
   }
}
