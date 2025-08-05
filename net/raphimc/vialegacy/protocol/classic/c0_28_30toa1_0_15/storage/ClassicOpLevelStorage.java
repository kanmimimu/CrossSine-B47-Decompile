package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.Protocolc0_28_30Toa1_0_15;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.Protocolr1_2_1_3Tor1_2_4_5;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ClientboundPackets1_2_4;

public class ClassicOpLevelStorage extends StoredObject {
   private byte opLevel;
   private final boolean haxEnabled;
   private boolean flying = false;
   private boolean noClip = false;
   private boolean speed = false;
   private boolean respawn = false;

   public ClassicOpLevelStorage(UserConnection user, boolean haxEnabled) {
      super(user);
      this.haxEnabled = haxEnabled;
      if (haxEnabled) {
         this.flying = true;
         this.noClip = true;
         this.speed = true;
         this.respawn = true;
      }

   }

   public void updateHax(boolean flying, boolean noClip, boolean speed, boolean respawn) {
      if (this.haxEnabled) {
         boolean changed = this.flying != flying;
         changed |= this.noClip != noClip;
         changed |= this.speed != speed;
         changed |= this.respawn != respawn;
         if (this.flying != flying) {
            this.flying = flying;
            this.updateAbilities();
         }

         this.noClip = noClip;
         this.speed = speed;
         this.respawn = respawn;
         if (changed) {
            String statusMessage = "§6Hack control: ";
            String var10 = this.flying ? "§aFlying" : "§cFlying";
            statusMessage = statusMessage + var10;
            statusMessage = statusMessage + " ";
            String var15 = this.noClip ? "§aNoClip" : "§cNoClip";
            statusMessage = statusMessage + var15;
            statusMessage = statusMessage + " ";
            String var20 = this.speed ? "§aSpeed" : "§cSpeed";
            statusMessage = statusMessage + var20;
            statusMessage = statusMessage + " ";
            String var25 = this.respawn ? "§aRespawn" : "§cRespawn";
            statusMessage = statusMessage + var25;
            PacketWrapper chatMessage = PacketWrapper.create(ClientboundPacketsa1_0_15.CHAT, (UserConnection)this.user());
            chatMessage.write(Typesb1_7_0_3.STRING, statusMessage);
            chatMessage.send(Protocolc0_28_30Toa1_0_15.class);
         }

      }
   }

   public void setOpLevel(byte opLevel) {
      this.opLevel = opLevel;
      if (this.haxEnabled) {
         ClassicServerTitleStorage serverTitleStorage = (ClassicServerTitleStorage)this.user().get(ClassicServerTitleStorage.class);
         if (serverTitleStorage != null) {
            this.updateHax(serverTitleStorage.isFlyEffectivelyEnabled(), serverTitleStorage.isNoclipEffectivelyEnabled(), serverTitleStorage.isSpeedEffectivelyEnabled(), serverTitleStorage.isRespawnEffectivelyEnabled());
         }
      }

   }

   public byte getOpLevel() {
      return this.opLevel;
   }

   public void updateAbilities() {
      if (this.user().getProtocolInfo().getPipeline().contains(Protocolr1_2_1_3Tor1_2_4_5.class)) {
         PacketWrapper playerAbilities = PacketWrapper.create(ClientboundPackets1_2_4.PLAYER_ABILITIES, (UserConnection)this.user());
         playerAbilities.write(Types.BOOLEAN, true);
         playerAbilities.write(Types.BOOLEAN, false);
         playerAbilities.write(Types.BOOLEAN, this.flying);
         playerAbilities.write(Types.BOOLEAN, true);
         playerAbilities.scheduleSend(Protocolr1_2_1_3Tor1_2_4_5.class);
      }

   }
}
