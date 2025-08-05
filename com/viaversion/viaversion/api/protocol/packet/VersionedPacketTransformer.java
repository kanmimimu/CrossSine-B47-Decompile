package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface VersionedPacketTransformer {
   boolean send(PacketWrapper var1) throws InformativeException;

   boolean send(UserConnection var1, ClientboundPacketType var2, Consumer var3) throws InformativeException;

   boolean send(UserConnection var1, ServerboundPacketType var2, Consumer var3) throws InformativeException;

   boolean scheduleSend(PacketWrapper var1) throws InformativeException;

   boolean scheduleSend(UserConnection var1, ClientboundPacketType var2, Consumer var3) throws InformativeException;

   boolean scheduleSend(UserConnection var1, ServerboundPacketType var2, Consumer var3) throws InformativeException;

   @Nullable PacketWrapper transform(PacketWrapper var1) throws InformativeException;

   @Nullable PacketWrapper transform(UserConnection var1, ClientboundPacketType var2, Consumer var3) throws InformativeException;

   @Nullable PacketWrapper transform(UserConnection var1, ServerboundPacketType var2, Consumer var3) throws InformativeException;
}
