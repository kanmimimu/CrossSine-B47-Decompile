package com.viaversion.viabackwards.protocol.v1_21to1_20_5.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.Protocol1_21To1_20_5;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.EnchantmentsPaintingsStorage;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.PlayerRotationStorage;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.PaintingVariant;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.WolfVariant;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.data.Paintings1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.KeyMappings;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class EntityPacketRewriter1_21 extends EntityRewriter {
   final Map oldPaintings = new HashMap();

   public EntityPacketRewriter1_21(Protocol1_21To1_20_5 protocol) {
      super(protocol, Types1_20_5.ENTITY_DATA_TYPES.optionalComponentType, Types1_20_5.ENTITY_DATA_TYPES.booleanType);

      for(int i = 0; i < Paintings1_20_5.PAINTINGS.length; ++i) {
         PaintingVariant painting = Paintings1_20_5.PAINTINGS[i];
         this.oldPaintings.put(painting.assetId(), new PaintingData(painting, i));
      }

   }

   public void registerPackets() {
      this.registerTrackerWithData1_19(ClientboundPackets1_21.ADD_ENTITY, EntityTypes1_20_5.FALLING_BLOCK);
      this.registerSetEntityData(ClientboundPackets1_21.SET_ENTITY_DATA, Types1_21.ENTITY_DATA_LIST, Types1_20_5.ENTITY_DATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_21.REMOVE_ENTITIES);
      ((Protocol1_21To1_20_5)this.protocol).registerClientbound(ClientboundConfigurationPackets1_21.REGISTRY_DATA, (wrapper) -> {
         String key = Key.stripMinecraftNamespace((String)wrapper.passthrough(Types.STRING));
         RegistryEntry[] entries = (RegistryEntry[])wrapper.passthrough(Types.REGISTRY_ENTRY_ARRAY);
         boolean paintingVariant = key.equals("painting_variant");
         boolean enchantment = key.equals("enchantment");
         if (!paintingVariant && !enchantment && !key.equals("jukebox_song")) {
            this.handleRegistryData1_20_5(wrapper.user(), key, entries);
         } else {
            String[] keys = new String[entries.length];

            for(int i = 0; i < entries.length; ++i) {
               keys[i] = Key.stripMinecraftNamespace(entries[i].key());
            }

            EnchantmentsPaintingsStorage storage = (EnchantmentsPaintingsStorage)wrapper.user().get(EnchantmentsPaintingsStorage.class);
            if (paintingVariant) {
               storage.setPaintings(new KeyMappings(keys), this.paintingMappingsForEntries(entries));
            } else if (enchantment) {
               Tag[] descriptions = new Tag[entries.length];

               for(int i = 0; i < entries.length; ++i) {
                  RegistryEntry entry = entries[i];
                  if (entry.tag() != null) {
                     descriptions[i] = ((CompoundTag)entry.tag()).get("description");
                  }
               }

               storage.setEnchantments(new KeyMappings(keys), descriptions);
            } else {
               int[] jukeboxSongMappings = new int[keys.length];

               for(int i = 0; i < keys.length; ++i) {
                  FullMappings var10000 = ((Protocol1_21To1_20_5)this.protocol).getMappingData().getFullItemMappings();
                  String var12 = keys[i];
                  int itemId = var10000.mappedId("music_disc_" + var12);
                  jukeboxSongMappings[i] = itemId;
               }

               storage.setJubeboxSongsToItems(jukeboxSongMappings);
            }

            wrapper.cancel();
         }

      });
      ((Protocol1_21To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_21.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(Types.STRING_ARRAY);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.BOOLEAN);
            this.map(Types.BOOLEAN);
            this.map(Types.BOOLEAN);
            this.map(Types.VAR_INT);
            this.map(Types.STRING);
            this.handler(EntityPacketRewriter1_21.this.worldDataTrackerHandlerByKey1_20_5(3));
         }
      });
      ((Protocol1_21To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_21.RESPAWN, (wrapper) -> {
         int dimensionId = (Integer)wrapper.passthrough(Types.VAR_INT);
         String world = (String)wrapper.passthrough(Types.STRING);
         this.trackWorldDataByKey1_20_5(wrapper.user(), dimensionId, world);
      });
      ((Protocol1_21To1_20_5)this.protocol).registerServerbound(ServerboundPackets1_20_5.MOVE_PLAYER_POS_ROT, (wrapper) -> {
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         this.storePlayerRotation(wrapper);
      });
      ((Protocol1_21To1_20_5)this.protocol).registerServerbound(ServerboundPackets1_20_5.MOVE_PLAYER_ROT, this::storePlayerRotation);
   }

   void storePlayerRotation(PacketWrapper wrapper) {
      float yaw = (Float)wrapper.passthrough(Types.FLOAT);
      float pitch = (Float)wrapper.passthrough(Types.FLOAT);
      ((PlayerRotationStorage)wrapper.user().get(PlayerRotationStorage.class)).setRotation(yaw, pitch);
   }

   int[] paintingMappingsForEntries(RegistryEntry[] entries) {
      int[] mappings = new int[entries.length];

      for(int i = 0; i < entries.length; ++i) {
         RegistryEntry entry = entries[i];
         PaintingData paintingData = (PaintingData)this.oldPaintings.get(Key.stripMinecraftNamespace(entry.key()));
         if (paintingData != null) {
            mappings[i] = paintingData.id;
         } else if (entry.tag() != null) {
            CompoundTag tag = (CompoundTag)entry.tag();

            for(int j = 0; j < Paintings1_20_5.PAINTINGS.length; ++j) {
               PaintingVariant painting = Paintings1_20_5.PAINTINGS[j];
               if (painting.width() == tag.getInt("width") && painting.height() == tag.getInt("height")) {
                  mappings[i] = j;
                  break;
               }
            }
         }
      }

      return mappings;
   }

   protected void registerRewrites() {
      this.filter().handler((event, data) -> {
         EntityDataType type = data.dataType();
         if (type == Types1_21.ENTITY_DATA_TYPES.wolfVariantType) {
            Holder<WolfVariant> variant = (Holder)data.value();
            if (variant.hasId()) {
               data.setTypeAndValue(Types1_20_5.ENTITY_DATA_TYPES.wolfVariantType, variant.id());
            } else {
               event.cancel();
            }
         } else if (type == Types1_21.ENTITY_DATA_TYPES.paintingVariantType) {
            Holder<PaintingVariant> variant = (Holder)data.value();
            if (variant.hasId()) {
               EnchantmentsPaintingsStorage storage = (EnchantmentsPaintingsStorage)event.user().get(EnchantmentsPaintingsStorage.class);
               int mappedId = storage.mappedPainting(variant.id());
               data.setTypeAndValue(Types1_20_5.ENTITY_DATA_TYPES.paintingVariantType, mappedId);
            } else {
               event.cancel();
            }
         } else {
            data.setDataType(Types1_20_5.ENTITY_DATA_TYPES.byId(type.typeId()));
         }

      });
      this.registerEntityDataTypeHandler1_20_3(Types1_20_5.ENTITY_DATA_TYPES.itemType, Types1_20_5.ENTITY_DATA_TYPES.blockStateType, Types1_20_5.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_20_5.ENTITY_DATA_TYPES.particleType, Types1_20_5.ENTITY_DATA_TYPES.particlesType, Types1_20_5.ENTITY_DATA_TYPES.componentType, Types1_20_5.ENTITY_DATA_TYPES.optionalComponentType);
      this.registerBlockStateHandler(EntityTypes1_20_5.ABSTRACT_MINECART, 11);
   }

   public EntityType typeFromId(int type) {
      return EntityTypes1_20_5.getTypeFromId(type);
   }

   private static final class PaintingData {
      final PaintingVariant painting;
      final int id;

      PaintingData(PaintingVariant painting, int id) {
         this.painting = painting;
         this.id = id;
      }

      public PaintingVariant painting() {
         return this.painting;
      }

      public int id() {
         return this.id;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof PaintingData)) {
            return false;
         } else {
            PaintingData var2 = (PaintingData)var1;
            return Objects.equals(this.painting, var2.painting) && this.id == var2.id;
         }
      }

      public int hashCode() {
         return (0 * 31 + Objects.hashCode(this.painting)) * 31 + Integer.hashCode(this.id);
      }

      public String toString() {
         return String.format("%s[painting=%s, id=%s]", this.getClass().getSimpleName(), Objects.toString(this.painting), Integer.toString(this.id));
      }
   }
}
