package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import me.liuli.elixir.account.MinecraftAccount;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.SessionUtils;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiDisconnected.class})
public abstract class MixinGuiDisconnected extends MixinGuiScreen {
   private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0");
   @Shadow
   private int field_175353_i;
   @Shadow
   @Final
   private GuiScreen field_146307_h;
   private GuiButton reconnectButton;
   private GuiSlider autoReconnectDelaySlider;
   private GuiButton forgeBypassButton;
   private int reconnectTimer;

   @Inject(
      method = {"initGui"},
      at = {@At("RETURN")}
   )
   private void initGui(CallbackInfo callbackInfo) {
      this.reconnectTimer = 0;
      SessionUtils.handleConnection();
      ServerData server = ServerUtils.serverData;
      this.field_146292_n.add(this.reconnectButton = new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 22, 98, 20, "Reconnect"));
      this.field_146292_n.add(this.autoReconnectDelaySlider = new GuiSlider(2, this.field_146294_l / 2 + 2, this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 22, 98, 20, "AutoReconnect: ", "ms", (double)1000.0F, (double)60000.0F, (double)AutoReconnect.INSTANCE.getDelay(), false, true, (guiSlider) -> {
         AutoReconnect.INSTANCE.setDelay(guiSlider.getValueInt());
         this.reconnectTimer = 0;
         this.updateReconnectButton();
         this.updateSliderText();
      }));
      this.field_146292_n.add(new GuiButton(3, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 44, 98, 20, "Random Alts"));
      this.field_146292_n.add(new GuiButton(4, this.field_146294_l / 2 + 2, this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 44, 98, 20, "Random Crack"));
      this.updateSliderText();
   }

   @Inject(
      method = {"actionPerformed"},
      at = {@At("HEAD")}
   )
   private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
      switch (button.field_146127_k) {
         case 1:
            ServerUtils.connectToLastServer();
         case 2:
         default:
            break;
         case 3:
            List<MinecraftAccount> accounts = CrossSine.fileManager.getAccountsConfig().getAltManagerMinecraftAccounts();
            if (!accounts.isEmpty()) {
               MinecraftAccount minecraftAccount = (MinecraftAccount)accounts.get((new Random()).nextInt(accounts.size()));
               GuiAltManager.Companion.login(minecraftAccount);
               ServerUtils.connectToLastServer();
            }
            break;
         case 4:
            LoginUtils.INSTANCE.randomCracked();
            ServerUtils.connectToLastServer();
      }

   }

   public void func_73876_c() {
      if (AutoReconnect.INSTANCE.isEnabled()) {
         ++this.reconnectTimer;
         if (this.reconnectTimer > AutoReconnect.INSTANCE.getDelay() / 50) {
            ServerUtils.connectToLastServer();
         }
      }

   }

   @Inject(
      method = {"drawScreen"},
      at = {@At("RETURN")}
   )
   private void drawScreen(CallbackInfo callbackInfo) {
      if (AutoReconnect.INSTANCE.isEnabled()) {
         this.updateReconnectButton();
      }

      Fonts.font35.func_175063_a("§7Username: §a" + this.field_146297_k.func_110432_I().func_111285_a(), 6.0F, 6.0F, 16777215);
      Fonts.font35.func_175063_a("§7Play Time: §a" + SessionUtils.getFormatLastSessionTime(), 6.0F, 16.0F, 16777215);
      ProtocolVersion version = ProtocolBase.getManager().getTargetVersion();
      Fonts.font35.func_175063_a("§7Protocol: §b" + version.getName(), 6.0F, 26.0F, 16777215);
   }

   private void updateSliderText() {
      if (this.autoReconnectDelaySlider != null) {
         if (!AutoReconnect.INSTANCE.isEnabled()) {
            this.autoReconnectDelaySlider.field_146126_j = "AutoReconnect: Off";
         } else {
            this.autoReconnectDelaySlider.field_146126_j = "AutoReconnect: " + DECIMAL_FORMAT.format((double)AutoReconnect.INSTANCE.getDelay() / (double)1000.0F) + "s";
         }

      }
   }

   private void updateReconnectButton() {
      if (this.reconnectButton != null) {
         this.reconnectButton.field_146126_j = "Reconnect" + (AutoReconnect.INSTANCE.isEnabled() ? " (" + (AutoReconnect.INSTANCE.getDelay() / 1000 - this.reconnectTimer / 20) + ")" : "");
      }

   }

   @Inject(
      method = {"keyTyped"},
      at = {@At("HEAD")}
   )
   private void keyTyped(char typedChar, int keyCode, CallbackInfo callbackInfo) {
      if (keyCode == 1) {
         this.field_146297_k.func_147108_a(this.field_146307_h);
      }

   }
}
