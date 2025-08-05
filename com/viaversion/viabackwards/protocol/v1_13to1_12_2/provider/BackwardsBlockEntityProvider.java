package com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.BannerHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.BedHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.PistonHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.SkullHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.SpawnerHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;

public class BackwardsBlockEntityProvider implements Provider {
   final Map handlers = new HashMap();

   public BackwardsBlockEntityProvider() {
      this.handlers.put("flower_pot", new FlowerPotHandler());
      this.handlers.put("bed", new BedHandler());
      this.handlers.put("banner", new BannerHandler());
      this.handlers.put("skull", new SkullHandler());
      this.handlers.put("mob_spawner", new SpawnerHandler());
      this.handlers.put("piston", new PistonHandler());
   }

   public boolean isHandled(String key) {
      return this.handlers.containsKey(Key.stripMinecraftNamespace(key));
   }

   public CompoundTag transform(UserConnection user, BlockPosition position, CompoundTag tag) {
      StringTag idTag = tag.getStringTag("id");
      if (idTag == null) {
         return tag;
      } else {
         String id = idTag.getValue();
         BackwardsBlockEntityHandler handler = (BackwardsBlockEntityHandler)this.handlers.get(Key.stripMinecraftNamespace(id));
         if (handler == null) {
            return tag;
         } else {
            BackwardsBlockStorage storage = (BackwardsBlockStorage)user.get(BackwardsBlockStorage.class);
            Integer blockId = storage.get(position);
            return blockId == null ? tag : handler.transform(blockId, tag);
         }
      }
   }

   public CompoundTag transform(UserConnection user, BlockPosition position, String id) {
      CompoundTag tag = new CompoundTag();
      tag.putString("id", id);
      tag.putInt("x", Math.toIntExact((long)position.x()));
      tag.putInt("y", Math.toIntExact((long)position.y()));
      tag.putInt("z", Math.toIntExact((long)position.z()));
      return this.transform(user, position, tag);
   }

   @FunctionalInterface
   public interface BackwardsBlockEntityHandler {
      CompoundTag transform(int var1, CompoundTag var2);
   }
}
