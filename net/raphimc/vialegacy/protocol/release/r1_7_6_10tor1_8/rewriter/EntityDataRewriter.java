package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.util.IdAndData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage.ChunkTracker;

public class EntityDataRewriter {
   final Protocolr1_7_6_10Tor1_8 protocol;

   public EntityDataRewriter(Protocolr1_7_6_10Tor1_8 protocol) {
      this.protocol = protocol;
   }

   public void transform(UserConnection user, EntityTypes1_8.EntityType type, List list) {
      for(EntityData entry : new ArrayList(list)) {
         EntityDataIndex1_7_6 entityDataIndex = EntityDataIndex1_7_6.searchIndex(type, entry.id());

         try {
            if (entityDataIndex == null) {
               if (!Via.getConfig().isSuppressConversionWarnings()) {
                  Logger var28 = ViaLegacy.getPlatform().getLogger();
                  String var13 = type.name();
                  var28.warning("Could not find valid entity data index entry for " + var13 + ": " + entry);
               }

               list.remove(entry);
            } else {
               Object value = entry.getValue();
               entry.setTypeAndValue(entityDataIndex.getOldType(), value);
               entry.setDataTypeUnsafe(entityDataIndex.getNewType());
               entry.setId(entityDataIndex.getNewIndex());
               if (entityDataIndex == EntityDataIndex1_7_6.ENTITY_AGEABLE_AGE) {
                  entry.setValue(Byte.valueOf((byte)((Integer)value < 0 ? -1 : 0)));
               } else if (entityDataIndex == EntityDataIndex1_7_6.ITEM_FRAME_ROTATION) {
                  entry.setValue(Integer.valueOf((Byte)value * 2).byteValue());
               } else if (entityDataIndex != EntityDataIndex1_7_6.ENDERMAN_CARRIED_BLOCK) {
                  if (entityDataIndex == EntityDataIndex1_7_6.HUMAN_SKIN_FLAGS) {
                     byte flags = (Byte)value;
                     boolean cape = (flags & 2) == 0;
                     flags = (byte)(cape ? 127 : 126);
                     entry.setValue(flags);
                  } else {
                     switch (entityDataIndex.getNewType()) {
                        case BYTE:
                           entry.setValue(((Number)value).byteValue());
                           break;
                        case SHORT:
                           entry.setValue(((Number)value).shortValue());
                           break;
                        case INT:
                           entry.setValue(((Number)value).intValue());
                           break;
                        case FLOAT:
                           entry.setValue(((Number)value).floatValue());
                           break;
                        case ITEM:
                           this.protocol.getItemRewriter().handleItemToClient(user, (Item)value);
                        case STRING:
                        case BLOCK_POSITION:
                        case ROTATIONS:
                           break;
                        default:
                           if (!Via.getConfig().isSuppressConversionWarnings()) {
                              Logger var27 = ViaLegacy.getPlatform().getLogger();
                              EntityDataTypes1_8 var16 = entityDataIndex.getNewType();
                              var27.warning("1.7.10 EntityDataRewriter: Unhandled Type: " + var16 + " " + entry);
                           }

                           list.remove(entry);
                     }
                  }
               } else {
                  byte id = (Byte)value;
                  EntityData blockDataMeta = null;

                  for(EntityData entityData : list) {
                     if (entityData.id() == EntityDataIndex1_7_6.ENDERMAN_CARRIED_BLOCK_DATA.getOldIndex()) {
                        blockDataMeta = entityData;
                        list.remove(entityData);
                        break;
                     }
                  }

                  byte data = blockDataMeta != null ? (Byte)blockDataMeta.getValue() : 0;
                  IdAndData block = new IdAndData(id, data);
                  ((ChunkTracker)user.get(ChunkTracker.class)).remapBlockParticle(block);
                  entry.setValue((short)(block.getId() | block.getData() << 12));
               }
            }
         } catch (Throwable e) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               Logger var10000 = ViaLegacy.getPlatform().getLogger();
               Level var10001 = Level.WARNING;
               String var19 = type.name();
               var10000.log(var10001, "Error rewriting entity data entry for " + var19 + ": " + entry, e);
            }

            list.remove(entry);
         }
      }

   }
}
