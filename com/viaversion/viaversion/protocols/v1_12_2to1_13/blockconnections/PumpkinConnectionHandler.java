package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

public class PumpkinConnectionHandler extends AbstractStempConnectionHandler {
   static ConnectionData.ConnectorInitAction init() {
      return (new PumpkinConnectionHandler("minecraft:pumpkin_stem[age=7]")).getInitAction("minecraft:carved_pumpkin", "minecraft:attached_pumpkin_stem");
   }

   public PumpkinConnectionHandler(String baseStateId) {
      super(baseStateId);
   }
}
