package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

public class ClassicServerTitleStorage extends StoredObject {
   private final String title;
   private final String motd;

   public ClassicServerTitleStorage(UserConnection user, String title, String motd) {
      super(user);
      this.title = title;
      this.motd = motd;
   }

   public String getTitle() {
      return this.title;
   }

   public String getMotd() {
      return this.motd;
   }

   public boolean isHaxEnabled() {
      return this.motd.contains("+hax");
   }

   public boolean isHaxDisabled() {
      return this.motd.contains("-hax");
   }

   public boolean isFlyEnabled() {
      return this.motd.contains("+fly");
   }

   public boolean isFlyDisabled() {
      return this.motd.contains("-fly");
   }

   public boolean isFlyEffectivelyEnabled() {
      boolean var10000;
      label30: {
         boolean isOp = ((ClassicOpLevelStorage)this.user().get(ClassicOpLevelStorage.class)).getOpLevel() >= 100;
         if (this.isHaxDisabled()) {
            if (this.isFlyEnabled()) {
               break label30;
            }
         } else if (!this.isFlyDisabled()) {
            break label30;
         }

         if (!isOp || !this.isOphaxEnabled()) {
            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   public boolean isNoclipEnabled() {
      return this.motd.contains("+noclip");
   }

   public boolean isNoclipDisabled() {
      return this.motd.contains("-noclip");
   }

   public boolean isNoclipEffectivelyEnabled() {
      boolean var10000;
      label30: {
         boolean isOp = ((ClassicOpLevelStorage)this.user().get(ClassicOpLevelStorage.class)).getOpLevel() >= 100;
         if (this.isHaxDisabled()) {
            if (this.isNoclipEnabled()) {
               break label30;
            }
         } else if (!this.isNoclipDisabled()) {
            break label30;
         }

         if (!isOp || !this.isOphaxEnabled()) {
            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   public boolean isRespawnEnabled() {
      return this.motd.contains("+respawn");
   }

   public boolean isRespawnDisabled() {
      return this.motd.contains("-respawn");
   }

   public boolean isRespawnEffectivelyEnabled() {
      boolean var10000;
      label30: {
         boolean isOp = ((ClassicOpLevelStorage)this.user().get(ClassicOpLevelStorage.class)).getOpLevel() >= 100;
         if (this.isHaxDisabled()) {
            if (this.isRespawnEnabled()) {
               break label30;
            }
         } else if (!this.isRespawnDisabled()) {
            break label30;
         }

         if (!isOp || !this.isOphaxEnabled()) {
            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   public boolean isSpeedEnabled() {
      return this.motd.contains("+speed");
   }

   public boolean isSpeedDisabled() {
      return this.motd.contains("-speed");
   }

   public boolean isSpeedEffectivelyEnabled() {
      boolean var10000;
      label30: {
         boolean isOp = ((ClassicOpLevelStorage)this.user().get(ClassicOpLevelStorage.class)).getOpLevel() >= 100;
         if (this.isHaxDisabled()) {
            if (this.isSpeedEnabled()) {
               break label30;
            }
         } else if (!this.isSpeedDisabled()) {
            break label30;
         }

         if (!isOp || !this.isOphaxEnabled()) {
            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   public boolean isOphaxEnabled() {
      return this.motd.contains("+ophax");
   }
}
