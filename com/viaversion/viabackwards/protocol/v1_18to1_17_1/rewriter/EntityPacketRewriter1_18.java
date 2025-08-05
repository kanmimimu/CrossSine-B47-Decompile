package com.viaversion.viabackwards.protocol.v1_18to1_17_1.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.util.TagUtil;

public final class EntityPacketRewriter1_18 extends EntityRewriter {
   public EntityPacketRewriter1_18(Protocol1_18To1_17_1 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      this.registerSetEntityData(ClientboundPackets1_18.SET_ENTITY_DATA, Types1_18.ENTITY_DATA_LIST, Types1_17.ENTITY_DATA_LIST);
      ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_18.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.STRING_ARRAY);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.map(Types.STRING);
            this.map(Types.LONG);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.read(Types.VAR_INT);
            this.handler(EntityPacketRewriter1_18.this.worldDataTrackerHandler(1));
            this.handler((wrapper) -> {
               CompoundTag registry = (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
               ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registry, "worldgen/biome");

               for(CompoundTag biome : biomes) {
                  CompoundTag biomeCompound = biome.getCompoundTag("element");
                  StringTag category = biomeCompound.getStringTag("category");
                  if (category.getValue().equals("mountain")) {
                     category.setValue("extreme_hills");
                  }

                  biomeCompound.putFloat("depth", 0.125F);
                  biomeCompound.putFloat("scale", 0.05F);
               }

               EntityPacketRewriter1_18.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
            });
         }
      });
      ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.NAMED_COMPOUND_TAG);
            this.map(Types.STRING);
            this.handler(EntityPacketRewriter1_18.this.worldDataTrackerHandler(0));
         }
      });
   }

   protected void registerRewrites() {
      this.filter().handler((event, data) -> {
         data.setDataType(Types1_17.ENTITY_DATA_TYPES.byId(data.dataType().typeId()));
         EntityDataType type = data.dataType();
         if (type == Types1_17.ENTITY_DATA_TYPES.particleType) {
            Particle particle = (Particle)data.value();
            if (particle.id() == 3) {
               Particle.ParticleData<?> value = (Particle.ParticleData)particle.getArguments().remove(0);
               int blockState = (Integer)value.getValue();
               if (blockState == 7786) {
                  particle.setId(3);
               } else {
                  particle.setId(2);
               }

               return;
            }

            this.rewriteParticle(event.user(), particle);
         }

      });
      this.registerEntityDataTypeHandler(Types1_17.ENTITY_DATA_TYPES.itemType, (EntityDataType)null, (EntityDataType)null, (EntityDataType)null, Types1_17.ENTITY_DATA_TYPES.componentType, Types1_17.ENTITY_DATA_TYPES.optionalComponentType);
   }

   public EntityType typeFromId(int typeId) {
      return EntityTypes1_17.getTypeFromId(typeId);
   }
}
