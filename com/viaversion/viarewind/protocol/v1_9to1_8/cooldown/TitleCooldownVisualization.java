package com.viaversion.viarewind.protocol.v1_9to1_8.cooldown;

import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.function.Consumer;

public class TitleCooldownVisualization implements CooldownVisualization {
   private static final int ACTION_SET_TITLE = 0;
   private static final int ACTION_SET_SUBTITLE = 1;
   private static final int ACTION_SET_TIMES_AND_DISPLAY = 2;
   private static final int ACTION_HIDE = 3;
   private final UserConnection user;

   public TitleCooldownVisualization(UserConnection user) {
      this.user = user;
   }

   public void show(double progress) throws Exception {
      String text = CooldownVisualization.buildProgressText("˙", progress);
      this.sendTitlePacket(0, (wrapper) -> wrapper.write(Types.COMPONENT, new JsonPrimitive("")));
      this.sendTitlePacket(1, (wrapper) -> wrapper.write(Types.COMPONENT, new JsonPrimitive(text)));
      this.sendTitlePacket(2, (wrapper) -> {
         wrapper.write(Types.INT, 0);
         wrapper.write(Types.INT, 2);
         wrapper.write(Types.INT, 5);
      });
   }

   public void hide() throws Exception {
      this.sendTitlePacket(3, (wrapper) -> {
      });
   }

   private void sendTitlePacket(int action, Consumer writer) {
      PacketWrapper title = PacketWrapper.create(ClientboundPackets1_8.SET_TITLES, (UserConnection)this.user);
      title.write(Types.VAR_INT, action);
      writer.accept(title);
      title.scheduleSend(Protocol1_9To1_8.class);
   }
}
