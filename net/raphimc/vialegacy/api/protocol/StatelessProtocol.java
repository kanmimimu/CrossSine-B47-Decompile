package net.raphimc.vialegacy.api.protocol;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;

public abstract class StatelessProtocol extends AbstractProtocol {
   public StatelessProtocol(Class unmappedClientboundPacketType, Class mappedClientboundPacketType, Class mappedServerboundPacketType, Class unmappedServerboundPacketType) {
      super(unmappedClientboundPacketType, mappedClientboundPacketType, mappedServerboundPacketType, unmappedServerboundPacketType);
   }

   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws InformativeException, CancelException {
      super.transform(direction, direction == Direction.SERVERBOUND ? state : State.PLAY, packetWrapper);
   }
}
