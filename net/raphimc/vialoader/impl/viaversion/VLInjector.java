package net.raphimc.vialoader.impl.viaversion;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLinkedOpenHashSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.SortedSet;

public class VLInjector implements ViaInjector {
   public void inject() {
   }

   public void uninject() {
   }

   public ProtocolVersion getServerProtocolVersion() {
      return (ProtocolVersion)this.getServerProtocolVersions().first();
   }

   public SortedSet getServerProtocolVersions() {
      SortedSet<ProtocolVersion> versions = new ObjectLinkedOpenHashSet();
      versions.addAll(ProtocolVersion.getProtocols());
      return versions;
   }

   public String getEncoderName() {
      return "via-codec";
   }

   public String getDecoderName() {
      return "via-codec";
   }

   public JsonObject getDump() {
      return new JsonObject();
   }
}
