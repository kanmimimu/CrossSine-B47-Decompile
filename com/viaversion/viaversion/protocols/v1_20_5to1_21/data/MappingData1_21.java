package com.viaversion.viaversion.protocols.v1_20_5to1_21.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import java.util.Map;

public final class MappingData1_21 extends MappingDataBase {
   private ListTag enchantments;
   private CompoundTag jukeboxSongs;

   public MappingData1_21() {
      super("1.20.5", "1.21");
   }

   protected void loadExtras(CompoundTag data) {
      CompoundTag extraMappings = MappingDataLoader.INSTANCE.loadNBT("enchantments-1.21.nbt");
      this.enchantments = extraMappings.getListTag("entries", CompoundTag.class);
      this.jukeboxSongs = MappingDataLoader.INSTANCE.loadNBT("jukebox-songs-1.21.nbt");
   }

   public CompoundTag enchantment(int id) {
      return ((CompoundTag)this.enchantments.get(id)).copy();
   }

   public RegistryEntry[] jukeboxSongs() {
      RegistryEntry[] entries = new RegistryEntry[this.jukeboxSongs.size()];
      int i = 0;

      for(Map.Entry entry : this.jukeboxSongs.entrySet()) {
         CompoundTag tag = (CompoundTag)entry.getValue();
         entries[i++] = new RegistryEntry((String)entry.getKey(), tag.copy());
      }

      return entries;
   }
}
