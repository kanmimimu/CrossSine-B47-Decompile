package com.viaversion.viarewind.protocol.v1_9to1_8.cooldown;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.ViaRewindConfig;
import com.viaversion.viaversion.api.connection.UserConnection;

public interface CooldownVisualization {
   int MAX_PROGRESS_TEXT_LENGTH = 10;

   void show(double var1) throws Exception;

   void hide() throws Exception;

   static String buildProgressText(String symbol, double cooldown) {
      int green = (int)Math.floor((double)10.0F * cooldown);
      int grey = 10 - green;
      StringBuilder builder = new StringBuilder("§8");

      while(green-- > 0) {
         builder.append(symbol);
      }

      builder.append("§7");

      while(grey-- > 0) {
         builder.append(symbol);
      }

      return builder.toString();
   }

   public interface Factory {
      Factory DISABLED = (user) -> new DisabledCooldownVisualization();

      CooldownVisualization create(UserConnection var1);

      static Factory fromConfiguration() {
         try {
            return fromIndicator(ViaRewind.getConfig().getCooldownIndicator());
         } catch (IllegalArgumentException var1) {
            ViaRewind.getPlatform().getLogger().warning("Invalid cooldown-indicator setting");
            return DISABLED;
         }
      }

      static Factory fromIndicator(ViaRewindConfig.CooldownIndicator indicator) {
         Factory var10000;
         switch (indicator) {
            case TITLE:
               var10000 = TitleCooldownVisualization::new;
               break;
            case BOSS_BAR:
               var10000 = BossBarVisualization::new;
               break;
            case ACTION_BAR:
               var10000 = ActionBarVisualization::new;
               break;
            case DISABLED:
               var10000 = DISABLED;
               break;
            default:
               throw new IncompatibleClassChangeError();
         }

         return var10000;
      }
   }
}
