package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface MappingData {
   void load();

   int getNewBlockStateId(int var1);

   int getNewBlockId(int var1);

   int getOldBlockId(int var1);

   int getNewItemId(int var1);

   int getOldItemId(int var1);

   int getNewParticleId(int var1);

   int getNewAttributeId(int var1);

   int getNewSoundId(int var1);

   int getOldSoundId(int var1);

   @Nullable List getTags(RegistryType var1);

   @Nullable BiMappings getItemMappings();

   @Nullable FullMappings getFullItemMappings();

   @Nullable ParticleMappings getParticleMappings();

   @Nullable Mappings getBlockMappings();

   @Nullable Mappings getBlockEntityMappings();

   @Nullable Mappings getBlockStateMappings();

   @Nullable Mappings getSoundMappings();

   @Nullable Mappings getStatisticsMappings();

   @Nullable Mappings getMenuMappings();

   @Nullable Mappings getEnchantmentMappings();

   @Nullable Mappings getAttributeMappings();

   @Nullable Mappings getPaintingMappings();

   @Nullable FullMappings getEntityMappings();

   @Nullable FullMappings getArgumentTypeMappings();

   @Nullable FullMappings getRecipeSerializerMappings();

   @Nullable FullMappings getDataComponentSerializerMappings();
}
