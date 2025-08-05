package net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.data.EntityDataIndex1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.EntityDataTypes1_6_4;

public class EntityDataRewriter {
   public static void transform(EntityTypes1_8.EntityType type, List list) {
      for(EntityData entry : new ArrayList(list)) {
         EntityDataIndex1_5_2 entityDataIndex = EntityDataIndex1_5_2.searchIndex(type, entry.id());

         try {
            if (entityDataIndex != null) {
               Object value = entry.getValue();
               entry.setTypeAndValue(entityDataIndex.getOldType(), value);
               entry.setDataTypeUnsafe(entityDataIndex.getNewType());
               entry.setId(entityDataIndex.getNewIndex());
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
                  case ITEM:
                  case STRING:
                  case BLOCK_POSITION:
                     break;
                  default:
                     if (!Via.getConfig().isSuppressConversionWarnings()) {
                        Logger var13 = ViaLegacy.getPlatform().getLogger();
                        EntityDataTypes1_6_4 var7 = entityDataIndex.getNewType();
                        var13.warning("1.5.2 EntityDataRewriter: Unhandled Type: " + var7 + " " + entry);
                     }

                     list.remove(entry);
               }
            }
         } catch (Throwable e) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               Logger var10000 = ViaLegacy.getPlatform().getLogger();
               Level var10001 = Level.WARNING;
               String var10 = type.name();
               var10000.log(var10001, "Error rewriting entity data entry for " + var10 + ": " + entry, e);
            }

            list.remove(entry);
         }
      }

   }
}
