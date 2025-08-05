package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model;

import com.google.common.base.Charsets;
import java.util.UUID;

public class TabListEntry {
   public GameProfile gameProfile;
   public int ping;
   public int gameMode;
   public boolean resolved;

   public TabListEntry(String name, UUID uuid) {
      this.gameProfile = new GameProfile(name, uuid);
      this.resolved = true;
   }

   public TabListEntry(String name, short ping) {
      this.gameProfile = new GameProfile(name, UUID.nameUUIDFromBytes(("LegacyPlayer:" + name).getBytes(Charsets.UTF_8)));
      this.ping = ping;
   }
}
