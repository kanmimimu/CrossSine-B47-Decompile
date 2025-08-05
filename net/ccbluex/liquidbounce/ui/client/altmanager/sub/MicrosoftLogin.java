package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.liuli.elixir.account.MicrosoftAccount;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.compat.OAuthServer;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.extensions.RendererExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J \u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/MicrosoftLogin;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "server", "Lme/liuli/elixir/compat/OAuthServer;", "stage", "", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "CrossSine"}
)
public final class MicrosoftLogin extends GuiScreen {
   @NotNull
   private final GuiScreen prevGui;
   @NotNull
   private String stage;
   private OAuthServer server;

   public MicrosoftLogin(@NotNull GuiScreen prevGui) {
      Intrinsics.checkNotNullParameter(prevGui, "prevGui");
      super();
      this.prevGui = prevGui;
      this.stage = "Initializing...";
   }

   public void func_73866_w_() {
      this.server = MicrosoftAccount.Companion.buildFromOpenBrowser$default(MicrosoftAccount.Companion, new MicrosoftAccount.OAuthHandler() {
         public void openUrl(@NotNull String url) {
            Intrinsics.checkNotNullParameter(url, "url");
            MicrosoftLogin.this.stage = "Check your browser for continue...";
            ClientUtils.INSTANCE.logInfo(Intrinsics.stringPlus("Opening URL: ", url));
            MiscUtils.INSTANCE.showURL(url);
         }

         public void authError(@NotNull String error) {
            Intrinsics.checkNotNullParameter(error, "error");
            MicrosoftLogin.this.stage = Intrinsics.stringPlus("Error: ", error);
         }

         public void authResult(@NotNull MicrosoftAccount account) {
            Intrinsics.checkNotNullParameter(account, "account");
            Iterable $this$any$iv = (Iterable)CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
            int $i$f$any = 0;
            boolean var10000;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
               var10000 = false;
            } else {
               Iterator var4 = $this$any$iv.iterator();

               while(true) {
                  if (!var4.hasNext()) {
                     var10000 = false;
                     break;
                  }

                  Object element$iv = var4.next();
                  MinecraftAccount it = (MinecraftAccount)element$iv;
                  int var7 = 0;
                  if (Intrinsics.areEqual((Object)it.getName(), (Object)account.getName())) {
                     var10000 = true;
                     break;
                  }
               }
            }

            if (var10000) {
               MicrosoftLogin.this.stage = "§cAlready Add";
            } else {
               CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().add(account);
               CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getAccountsConfig());
               MicrosoftLogin.this.field_146297_k.func_147108_a(MicrosoftLogin.this.prevGui);
            }
         }
      }, (MicrosoftAccount.AuthMethod)null, 2, (Object)null);
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, "Cancel"));
   }

   protected void func_146284_a(@NotNull GuiButton button) {
      Intrinsics.checkNotNullParameter(button, "button");
      if (button.field_146127_k == 0) {
         OAuthServer var10000 = this.server;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("server");
            var10000 = null;
         }

         var10000.stop(true);
         this.field_146297_k.func_147108_a(this.prevGui);
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.func_146276_q_();
      FontRenderer var4 = this.field_146289_q;
      Intrinsics.checkNotNullExpressionValue(var4, "fontRendererObj");
      RendererExtensionKt.drawCenteredString(var4, this.stage, (float)this.field_146294_l / 2.0F, (float)this.field_146295_m / 2.0F - (float)50, 16777215);
      super.func_73863_a(mouseX, mouseY, partialTicks);
   }
}
