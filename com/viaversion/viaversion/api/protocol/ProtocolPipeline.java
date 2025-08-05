package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.packet.Direction;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolPipeline extends SimpleProtocol {
   void add(Protocol var1);

   void add(Collection var1);

   boolean contains(Class var1);

   /** @deprecated */
   @Deprecated
   @Nullable Protocol getProtocol(Class var1);

   List pipes(@Nullable Class var1, boolean var2, Direction var3);

   List pipes();

   List reversedPipes();

   int baseProtocolCount();

   boolean hasNonBaseProtocols();

   void cleanPipes();
}
