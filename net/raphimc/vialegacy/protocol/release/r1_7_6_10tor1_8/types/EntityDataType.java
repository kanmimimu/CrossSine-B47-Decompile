package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.type.types.entitydata.OldEntityDataType;

public class EntityDataType extends OldEntityDataType {
   protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index) {
      return EntityDataTypes1_7_6.byId(index);
   }
}
