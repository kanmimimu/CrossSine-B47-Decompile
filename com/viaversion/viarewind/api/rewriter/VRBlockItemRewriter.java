package com.viaversion.viarewind.api.rewriter;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viarewind.api.data.RewindMappingDataLoader;
import com.viaversion.viaversion.libs.gson.JsonObject;

public class VRBlockItemRewriter extends LegacyBlockItemRewriter {
   protected VRBlockItemRewriter(BackwardsProtocol protocol, String name) {
      super(protocol, name);
   }

   protected JsonObject readMappingsFile(String name) {
      return RewindMappingDataLoader.INSTANCE.loadFromDataDir(name);
   }

   public String nbtTagName() {
      String var3 = ((BackwardsProtocol)this.protocol).getClass().getSimpleName();
      return "VR|" + var3;
   }
}
