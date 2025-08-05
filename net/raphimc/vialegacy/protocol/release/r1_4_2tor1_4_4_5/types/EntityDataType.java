package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types;

import com.viaversion.viaversion.api.type.types.entitydata.OldEntityDataType;

public class EntityDataType extends OldEntityDataType {
   protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index) {
      return EntityDataTypes1_4_2.byId(index);
   }
}
