package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

public class NetherFenceConnectionHandler extends AbstractFenceConnectionHandler {
   static ConnectionData.ConnectorInitAction init() {
      return (new NetherFenceConnectionHandler("netherFence")).getInitAction("minecraft:nether_brick_fence");
   }

   public NetherFenceConnectionHandler(String blockConnections) {
      super(blockConnections);
   }
}
