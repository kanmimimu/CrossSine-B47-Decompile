package com.viaversion.viaversion.rewriter.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;

@FunctionalInterface
public interface EntityDataHandler {
   void handle(EntityDataHandlerEvent var1, EntityData var2);
}
