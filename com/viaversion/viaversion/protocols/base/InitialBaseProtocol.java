package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.protocol.RedirectProtocolVersion;
import com.viaversion.viaversion.protocol.version.BaseVersionProvider;
import com.viaversion.viaversion.protocols.base.packet.BaseClientboundPacket;
import com.viaversion.viaversion.protocols.base.packet.BasePacketTypesProvider;
import com.viaversion.viaversion.protocols.base.packet.BaseServerboundPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class InitialBaseProtocol extends AbstractProtocol {
   private static final int STATUS_INTENT = 1;
   private static final int LOGIN_INTENT = 2;
   private static final int TRANSFER_INTENT = 3;

   public InitialBaseProtocol() {
      super(BaseClientboundPacket.class, BaseClientboundPacket.class, BaseServerboundPacket.class, BaseServerboundPacket.class);
   }

   protected void registerPackets() {
      this.registerServerbound(ServerboundHandshakePackets.CLIENT_INTENTION, (wrapper) -> {
         int protocolVersion = (Integer)wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.STRING);
         wrapper.passthrough(Types.UNSIGNED_SHORT);
         int state = (Integer)wrapper.passthrough(Types.VAR_INT);
         VersionProvider versionProvider = (VersionProvider)Via.getManager().getProviders().get(VersionProvider.class);
         if (versionProvider == null) {
            wrapper.user().setActive(false);
         } else {
            ProtocolInfo info = wrapper.user().getProtocolInfo();
            info.setProtocolVersion(ProtocolVersion.getProtocol(protocolVersion));
            ProtocolVersion clientVersion = versionProvider.getClientProtocol(wrapper.user());
            if (clientVersion != null) {
               info.setProtocolVersion(clientVersion);
            }

            ProtocolVersion serverProtocol;
            try {
               serverProtocol = versionProvider.getClosestServerProtocol(wrapper.user());
            } catch (Exception e) {
               throw new RuntimeException("Error getting server protocol", e);
            }

            info.setServerProtocolVersion(serverProtocol);
            ProtocolManager protocolManager = Via.getManager().getProtocolManager();
            List<ProtocolPathEntry> protocolPath = protocolManager.getProtocolPath(info.protocolVersion(), serverProtocol);
            ProtocolPipeline pipeline = info.getPipeline();
            List<Protocol> alreadyAdded = new ArrayList(pipeline.pipes());
            ProtocolVersion clientboundBaseProtocolVersion = null;
            if (serverProtocol.getVersionType() != VersionType.SPECIAL) {
               clientboundBaseProtocolVersion = serverProtocol;
            } else if (serverProtocol instanceof RedirectProtocolVersion) {
               RedirectProtocolVersion version = (RedirectProtocolVersion)serverProtocol;
               clientboundBaseProtocolVersion = version.getBaseProtocolVersion();
            }

            for(Protocol protocol : protocolManager.getBaseProtocols(info.protocolVersion(), clientboundBaseProtocolVersion)) {
               pipeline.add(protocol);
            }

            if (protocolPath != null) {
               List<Protocol> protocols = new ArrayList(protocolPath.size());

               for(ProtocolPathEntry entry : protocolPath) {
                  protocols.add(entry.protocol());
                  protocolManager.completeMappingDataLoading(entry.protocol().getClass());
               }

               pipeline.add((Collection)protocols);
               wrapper.set(Types.VAR_INT, 0, serverProtocol.getOriginalVersion());
            }

            try {
               List<Protocol> protocols = new ArrayList(pipeline.pipes());
               protocols.removeAll(alreadyAdded);
               wrapper.resetReader();
               wrapper.apply(Direction.SERVERBOUND, State.HANDSHAKE, protocols);
            } catch (CancelException var20) {
               wrapper.cancel();
            }

            if (Via.getManager().isDebug()) {
               Logger var10000 = Via.getPlatform().getLogger();
               ProtocolVersion var10001 = info.protocolVersion();
               ProtocolVersion var17 = info.serverProtocolVersion();
               ProtocolVersion var16 = var10001;
               var10000.info("User connected with protocol: " + var16 + " and serverProtocol: " + var17);
               var10000 = Via.getPlatform().getLogger();
               List var19 = pipeline.pipes();
               var10000.info("Protocol pipeline: " + var19);
            }

            if (state == 1) {
               info.setState(State.STATUS);
            } else if (state == 2) {
               info.setState(State.LOGIN);
            } else if (state == 3) {
               info.setState(State.LOGIN);
               if (serverProtocol.olderThan(ProtocolVersion.v1_20_5)) {
                  wrapper.set(Types.VAR_INT, 1, 2);
               }
            }

         }
      });
   }

   public boolean isBaseProtocol() {
      return true;
   }

   public void register(ViaProviders providers) {
      providers.register(VersionProvider.class, new BaseVersionProvider());
   }

   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws InformativeException, CancelException {
      super.transform(direction, state, packetWrapper);
      if (direction == Direction.SERVERBOUND && state == State.HANDSHAKE && packetWrapper.getId() != 0) {
         packetWrapper.user().setActive(false);
      }

   }

   protected PacketTypesProvider createPacketTypesProvider() {
      return BasePacketTypesProvider.INSTANCE;
   }
}
