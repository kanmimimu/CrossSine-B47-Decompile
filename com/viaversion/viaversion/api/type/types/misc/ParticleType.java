package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.Key;
import io.netty.buffer.ByteBuf;

public class ParticleType extends DynamicType {
   public ParticleType() {
      super(Particle.class);
   }

   public void write(ByteBuf buffer, Particle object) {
      Types.VAR_INT.writePrimitive(buffer, object.id());

      for(Particle.ParticleData data : object.getArguments()) {
         data.write(buffer);
      }

   }

   public Particle read(ByteBuf buffer) {
      int type = Types.VAR_INT.readPrimitive(buffer);
      Particle particle = new Particle(type);
      this.readData(buffer, particle);
      return particle;
   }

   protected FullMappings mappings(Protocol protocol) {
      return protocol.getMappingData().getParticleMappings();
   }

   public static DynamicType.DataReader itemHandler(Type itemType) {
      return (buf, particle) -> particle.add(itemType, (Item)itemType.read(buf));
   }

   public static final class Readers {
      public static final DynamicType.DataReader BLOCK = (buf, particle) -> particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
      public static final DynamicType.DataReader ITEM1_13;
      public static final DynamicType.DataReader ITEM1_13_2;
      public static final DynamicType.DataReader DUST;
      public static final DynamicType.DataReader DUST_TRANSITION;
      public static final DynamicType.DataReader VIBRATION;
      public static final DynamicType.DataReader VIBRATION1_19;
      public static final DynamicType.DataReader VIBRATION1_20_3;
      public static final DynamicType.DataReader SCULK_CHARGE;
      public static final DynamicType.DataReader SHRIEK;
      public static final DynamicType.DataReader COLOR;

      public static DynamicType.DataReader item(Type item) {
         return (buf, particle) -> particle.add(item, (Item)item.read(buf));
      }

      static {
         ITEM1_13 = ParticleType.itemHandler(Types.ITEM1_13);
         ITEM1_13_2 = ParticleType.itemHandler(Types.ITEM1_13_2);
         DUST = (buf, particle) -> {
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
         };
         DUST_TRANSITION = (buf, particle) -> {
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
         };
         VIBRATION = (buf, particle) -> {
            particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            String resourceLocation = (String)Types.STRING.read(buf);
            particle.add(Types.STRING, resourceLocation);
            resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
            if (resourceLocation.equals("block")) {
               particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            } else if (resourceLocation.equals("entity")) {
               particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
            } else {
               Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
            }

            particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
         };
         VIBRATION1_19 = (buf, particle) -> {
            String resourceLocation = (String)Types.STRING.read(buf);
            particle.add(Types.STRING, resourceLocation);
            resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
            if (resourceLocation.equals("block")) {
               particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            } else if (resourceLocation.equals("entity")) {
               particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
               particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            } else {
               Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
            }

            particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
         };
         VIBRATION1_20_3 = (buf, particle) -> {
            int sourceTypeId = Types.VAR_INT.readPrimitive(buf);
            particle.add(Types.VAR_INT, sourceTypeId);
            if (sourceTypeId == 0) {
               particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            } else if (sourceTypeId == 1) {
               particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
               particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
            } else {
               Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + sourceTypeId);
            }

            particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
         };
         SCULK_CHARGE = (buf, particle) -> particle.add(Types.FLOAT, Types.FLOAT.readPrimitive(buf));
         SHRIEK = (buf, particle) -> particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
         COLOR = (buf, particle) -> particle.add(Types.INT, buf.readInt());
      }
   }
}
