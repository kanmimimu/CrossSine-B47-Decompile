package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;

public interface Rewriter extends MappingDataListener {
   void register();

   Protocol protocol();
}
