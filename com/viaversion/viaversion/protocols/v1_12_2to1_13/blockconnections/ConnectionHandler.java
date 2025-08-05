package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;

@FunctionalInterface
public interface ConnectionHandler {
   int connect(UserConnection var1, BlockPosition var2, int var3);

   default int getBlockData(UserConnection user, BlockPosition position) {
      return ConnectionData.blockConnectionProvider.getBlockData(user, position.x(), position.y(), position.z());
   }
}
