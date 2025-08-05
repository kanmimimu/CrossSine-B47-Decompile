package com.viaversion.viaversion.api.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataBase implements MappingData {
   protected final String unmappedVersion;
   protected final String mappedVersion;
   protected FullMappings argumentTypeMappings;
   protected FullMappings entityMappings;
   protected FullMappings recipeSerializerMappings;
   protected FullMappings itemDataSerializerMappings;
   protected ParticleMappings particleMappings;
   protected BiMappings itemMappings;
   protected BiMappings blockMappings;
   protected BiMappings attributeMappings;
   protected Mappings blockStateMappings;
   protected Mappings blockEntityMappings;
   protected Mappings soundMappings;
   protected Mappings statisticsMappings;
   protected Mappings enchantmentMappings;
   protected Mappings paintingMappings;
   protected Mappings menuMappings;
   protected Map tags;

   public MappingDataBase(String unmappedVersion, String mappedVersion) {
      this.unmappedVersion = unmappedVersion;
      this.mappedVersion = mappedVersion;
   }

   public void load() {
      if (Via.getManager().isDebug()) {
         Logger var10000 = this.getLogger();
         String var9 = this.mappedVersion;
         String var8 = this.unmappedVersion;
         var10000.info("Loading " + var8 + " -> " + var9 + " mappings...");
      }

      String var12 = this.mappedVersion;
      String var11 = this.unmappedVersion;
      CompoundTag data = this.readMappingsFile("mappings-" + var11 + "to" + var12 + ".nbt");
      this.blockMappings = this.loadBiMappings(data, "blocks");
      this.blockStateMappings = this.loadMappings(data, "blockstates");
      this.blockEntityMappings = this.loadMappings(data, "blockentities");
      this.soundMappings = this.loadMappings(data, "sounds");
      this.statisticsMappings = this.loadMappings(data, "statistics");
      this.menuMappings = this.loadMappings(data, "menus");
      this.enchantmentMappings = this.loadMappings(data, "enchantments");
      this.paintingMappings = this.loadMappings(data, "paintings");
      this.attributeMappings = this.loadBiMappings(data, "attributes");
      String var14 = this.unmappedVersion;
      CompoundTag unmappedIdentifierData = this.readUnmappedIdentifiersFile("identifiers-" + var14 + ".nbt");
      String var16 = this.mappedVersion;
      CompoundTag mappedIdentifierData = this.readMappedIdentifiersFile("identifiers-" + var16 + ".nbt");
      if (unmappedIdentifierData != null && mappedIdentifierData != null) {
         this.itemMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "items");
         this.entityMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "entities");
         this.argumentTypeMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "argumenttypes");
         this.recipeSerializerMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "recipe_serializers");
         this.itemDataSerializerMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "data_component_type");
         List<String> unmappedParticles = this.identifiersFromGlobalIds(unmappedIdentifierData, "particles");
         List<String> mappedParticles = this.identifiersFromGlobalIds(mappedIdentifierData, "particles");
         if (unmappedParticles != null && mappedParticles != null) {
            Mappings particleMappings = this.loadMappings(data, "particles");
            if (particleMappings == null) {
               particleMappings = new IdentityMappings(unmappedParticles.size(), mappedParticles.size());
            }

            this.particleMappings = new ParticleMappings(unmappedParticles, mappedParticles, particleMappings);
         }
      } else {
         this.itemMappings = this.loadBiMappings(data, "items");
      }

      CompoundTag tagsTag = data.getCompoundTag("tags");
      if (tagsTag != null) {
         this.tags = new EnumMap(RegistryType.class);
         this.loadTags(RegistryType.ITEM, tagsTag);
         this.loadTags(RegistryType.BLOCK, tagsTag);
         this.loadTags(RegistryType.ENTITY, tagsTag);
      }

      this.loadExtras(data);
   }

   protected @Nullable List identifiersFromGlobalIds(CompoundTag mappingsTag, String key) {
      return MappingDataLoader.INSTANCE.identifiersFromGlobalIds(mappingsTag, key);
   }

   protected @Nullable CompoundTag readMappingsFile(String name) {
      return MappingDataLoader.INSTANCE.loadNBT(name);
   }

   protected @Nullable CompoundTag readUnmappedIdentifiersFile(String name) {
      return MappingDataLoader.INSTANCE.loadNBT(name, true);
   }

   protected @Nullable CompoundTag readMappedIdentifiersFile(String name) {
      return MappingDataLoader.INSTANCE.loadNBT(name, true);
   }

   protected @Nullable Mappings loadMappings(CompoundTag data, String key) {
      return MappingDataLoader.INSTANCE.loadMappings(data, key);
   }

   protected @Nullable FullMappings loadFullMappings(CompoundTag data, CompoundTag unmappedIdentifiersTag, CompoundTag mappedIdentifiersTag, String key) {
      if (unmappedIdentifiersTag.contains(key) && mappedIdentifiersTag.contains(key)) {
         List<String> unmappedIdentifiers = this.identifiersFromGlobalIds(unmappedIdentifiersTag, key);
         List<String> mappedIdentifiers = this.identifiersFromGlobalIds(mappedIdentifiersTag, key);
         Mappings mappings = this.loadBiMappings(data, key);
         if (mappings == null) {
            mappings = new IdentityMappings(unmappedIdentifiers.size(), mappedIdentifiers.size());
         }

         return new FullMappingsBase(unmappedIdentifiers, mappedIdentifiers, mappings);
      } else {
         return null;
      }
   }

   protected @Nullable BiMappings loadBiMappings(CompoundTag data, String key) {
      Mappings mappings = this.loadMappings(data, key);
      return mappings != null ? BiMappings.of(mappings) : null;
   }

   private void loadTags(RegistryType type, CompoundTag data) {
      CompoundTag tag = data.getCompoundTag(type.resourceLocation());
      if (tag != null) {
         List<TagData> tagsList = new ArrayList(this.tags.size());

         for(Map.Entry entry : tag.entrySet()) {
            IntArrayTag entries = (IntArrayTag)entry.getValue();
            tagsList.add(new TagData((String)entry.getKey(), entries.getValue()));
         }

         this.tags.put(type, tagsList);
      }
   }

   public int getNewBlockStateId(int id) {
      return this.checkValidity(id, this.blockStateMappings.getNewId(id), "blockstate");
   }

   public int getNewBlockId(int id) {
      return this.checkValidity(id, this.blockMappings.getNewId(id), "block");
   }

   public int getOldBlockId(int id) {
      return this.blockMappings.getNewIdOrDefault(id, 1);
   }

   public int getNewItemId(int id) {
      return this.checkValidity(id, this.itemMappings.getNewId(id), "item");
   }

   public int getOldItemId(int id) {
      return this.itemMappings.inverse().getNewIdOrDefault(id, 1);
   }

   public int getNewParticleId(int id) {
      return this.checkValidity(id, this.particleMappings.getNewId(id), "particles");
   }

   public int getNewAttributeId(int id) {
      return this.checkValidity(id, this.attributeMappings.getNewId(id), "attributes");
   }

   public int getNewSoundId(int id) {
      return this.checkValidity(id, this.soundMappings.getNewId(id), "sound");
   }

   public int getOldSoundId(int i) {
      return this.soundMappings.getNewIdOrDefault(i, 0);
   }

   public @Nullable List getTags(RegistryType type) {
      return this.tags != null ? (List)this.tags.get(type) : null;
   }

   public @Nullable BiMappings getItemMappings() {
      return this.itemMappings;
   }

   public @Nullable FullMappings getFullItemMappings() {
      return this.itemMappings instanceof FullMappings ? (FullMappings)this.itemMappings : null;
   }

   public @Nullable ParticleMappings getParticleMappings() {
      return this.particleMappings;
   }

   public @Nullable Mappings getBlockMappings() {
      return this.blockMappings;
   }

   public @Nullable Mappings getBlockEntityMappings() {
      return this.blockEntityMappings;
   }

   public @Nullable Mappings getBlockStateMappings() {
      return this.blockStateMappings;
   }

   public @Nullable Mappings getSoundMappings() {
      return this.soundMappings;
   }

   public @Nullable Mappings getStatisticsMappings() {
      return this.statisticsMappings;
   }

   public @Nullable Mappings getMenuMappings() {
      return this.menuMappings;
   }

   public @Nullable Mappings getEnchantmentMappings() {
      return this.enchantmentMappings;
   }

   public @Nullable Mappings getAttributeMappings() {
      return this.attributeMappings;
   }

   public @Nullable FullMappings getEntityMappings() {
      return this.entityMappings;
   }

   public @Nullable FullMappings getArgumentTypeMappings() {
      return this.argumentTypeMappings;
   }

   public @Nullable FullMappings getDataComponentSerializerMappings() {
      return this.itemDataSerializerMappings;
   }

   public @Nullable Mappings getPaintingMappings() {
      return this.paintingMappings;
   }

   public @Nullable FullMappings getRecipeSerializerMappings() {
      return this.recipeSerializerMappings;
   }

   protected Logger getLogger() {
      return Via.getPlatform().getLogger();
   }

   protected int checkValidity(int id, int mappedId, String type) {
      if (mappedId == -1) {
         if (!Via.getConfig().isSuppressConversionWarnings()) {
            this.getLogger().warning(String.format("Missing %s %s for %s %s %d", this.mappedVersion, type, this.unmappedVersion, type, id));
         }

         return 0;
      } else {
         return mappedId;
      }
   }

   protected void loadExtras(CompoundTag data) {
   }
}
