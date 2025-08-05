package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.UUID;
import java.util.logging.Logger;

public class ProtocolInfoImpl implements ProtocolInfo {
   private State clientState;
   private State serverState;
   private ProtocolVersion serverProtocolVersion;
   private ProtocolVersion protocolVersion;
   private String username;
   private UUID uuid;
   private ProtocolPipeline pipeline;

   public ProtocolInfoImpl() {
      this.clientState = State.HANDSHAKE;
      this.serverState = State.HANDSHAKE;
      this.serverProtocolVersion = ProtocolVersion.unknown;
      this.protocolVersion = ProtocolVersion.unknown;
   }

   public State getClientState() {
      return this.clientState;
   }

   public void setClientState(State clientState) {
      if (Via.getManager().debugHandler().enabled()) {
         Logger var10000 = Via.getPlatform().getLogger();
         UUID var6 = this.uuid;
         State var4 = this.clientState;
         var10000.info("Client state changed from " + var4 + " to " + clientState + " for " + var6);
      }

      this.clientState = clientState;
   }

   public State getServerState() {
      return this.serverState;
   }

   public void setServerState(State serverState) {
      if (Via.getManager().debugHandler().enabled()) {
         Logger var10000 = Via.getPlatform().getLogger();
         UUID var6 = this.uuid;
         State var4 = this.serverState;
         var10000.info("Server state changed from " + var4 + " to " + serverState + " for " + var6);
      }

      this.serverState = serverState;
   }

   public ProtocolVersion protocolVersion() {
      return this.protocolVersion;
   }

   public void setProtocolVersion(ProtocolVersion protocolVersion) {
      this.protocolVersion = protocolVersion;
   }

   public ProtocolVersion serverProtocolVersion() {
      return this.serverProtocolVersion;
   }

   public void setServerProtocolVersion(ProtocolVersion serverProtocolVersion) {
      this.serverProtocolVersion = serverProtocolVersion;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public void setUuid(UUID uuid) {
      this.uuid = uuid;
   }

   public ProtocolPipeline getPipeline() {
      return this.pipeline;
   }

   public void setPipeline(ProtocolPipeline pipeline) {
      this.pipeline = pipeline;
   }

   public String toString() {
      UUID var8 = this.uuid;
      String var7 = this.username;
      ProtocolVersion var6 = this.serverProtocolVersion;
      ProtocolVersion var5 = this.protocolVersion;
      State var4 = this.serverState;
      State var3 = this.clientState;
      return "ProtocolInfo{clientState=" + var3 + ", serverState=" + var4 + ", protocolVersion=" + var5 + ", serverProtocolVersion=" + var6 + ", username='" + var7 + "', uuid=" + var8 + "}";
   }
}
