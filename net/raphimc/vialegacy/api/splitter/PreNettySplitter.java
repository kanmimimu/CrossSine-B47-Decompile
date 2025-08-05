package net.raphimc.vialegacy.api.splitter;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.function.IntFunction;

public class PreNettySplitter implements StorableObject {
   private final IntFunction packetTypeSupplier;
   private final Class protocolClass;

   public PreNettySplitter(Class protocolClass, IntFunction packetTypeSupplier) {
      this.protocolClass = protocolClass;
      this.packetTypeSupplier = packetTypeSupplier;
   }

   public PreNettyPacketType getPacketType(int packetId) {
      return (PreNettyPacketType)this.packetTypeSupplier.apply(packetId);
   }

   public String getProtocolName() {
      return this.protocolClass.getSimpleName();
   }
}
