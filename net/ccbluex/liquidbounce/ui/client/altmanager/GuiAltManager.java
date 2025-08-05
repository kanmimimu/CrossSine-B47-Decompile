package net.ccbluex.liquidbounce.ui.client.altmanager;

import com.google.gson.JsonObject;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import me.liuli.elixir.account.MinecraftAccount;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiAdd;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDirectLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.MicrosoftLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.SessionGui;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.cookie.CookieUtil;
import net.ccbluex.liquidbounce.utils.cookie.LoginData;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u0000 %2\u00020\u0001:\u0002%&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0012H\u0014J \u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\tH\u0016J\b\u0010\u001c\u001a\u00020\u0015H\u0016J\b\u0010\u001d\u001a\u00020\u0015H\u0016J\u0018\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0019H\u0014J \u0010\"\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0019H\u0014J\b\u0010$\u001a\u00020\u0015H\u0016R\u0012\u0010\u0004\u001a\u00060\u0005R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000¨\u0006'"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "altsList", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList;", "altsSlider", "Lnet/minecraftforge/fml/client/config/GuiSlider;", "currentX", "", "currentY", "status", "", "getStatus", "()Ljava/lang/String;", "setStatus", "(Ljava/lang/String;)V", "stylisedAltsButton", "Lnet/minecraft/client/gui/GuiButton;", "unformattedAltsButton", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "updateScreen", "Companion", "GuiList", "CrossSine"}
)
public final class GuiAltManager extends GuiScreen {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final GuiScreen prevGui;
   @NotNull
   private String status;
   private GuiList altsList;
   private GuiSlider altsSlider;
   private GuiButton stylisedAltsButton;
   private GuiButton unformattedAltsButton;
   private float currentX;
   private float currentY;
   private static int altsLength = 16;
   private static boolean unformattedAlts = true;
   private static boolean stylisedAlts = true;
   @NotNull
   private static Session originalSession;

   public GuiAltManager(@NotNull GuiScreen prevGui) {
      Intrinsics.checkNotNullParameter(prevGui, "prevGui");
      super();
      this.prevGui = prevGui;
      this.status = "§7Idle";
   }

   @NotNull
   public final String getStatus() {
      return this.status;
   }

   public final void setStatus(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.status = var1;
   }

   public void func_73866_w_() {
      this.altsList = new GuiList(this);
      GuiList var10000 = this.altsList;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("altsList");
         var10000 = null;
      }

      var10000.func_148134_d(7, 8);
      var10000 = this.altsList;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("altsList");
         var10000 = null;
      }

      var10000.func_148144_a(-1, false, 0, 0);
      var10000 = this.altsList;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("altsList");
         var10000 = null;
      }

      GuiList var10002 = this.altsList;
      if (var10002 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("altsList");
         var10002 = null;
      }

      var10000.func_148145_f(-1 * var10002.field_148149_f);
      int j = 22;
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l - 80, j + 24, 70, 20, "Add"));
      this.field_146292_n.add(new GuiButton(2, this.field_146294_l - 80, j + 48, 70, 20, "Remove"));
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l - 80, this.field_146295_m - 65, 70, 20, "Back"));
      this.field_146292_n.add(new GuiButton(3, 5, j + 24, 90, 20, "Login"));
      this.field_146292_n.add(new GuiButton(6, 5, j + 48, 90, 20, "DirectLogin"));
      this.field_146292_n.add(new GuiButton(4, 5, j + 72, 90, 20, "RandomAlt"));
      this.field_146292_n.add(new GuiButton(92, 5, j + 96, 90, 20, "Microsoft"));
      this.field_146292_n.add(new GuiButton(93, 5, j + 120, 90, 20, "CookiesLogin"));
      this.field_146292_n.add(new GuiButton(89, 5, j + 144, 90, 20, "RandomCrack"));
      List var14 = this.field_146292_n;
      GuiButton var2 = new GuiButton(81, 5, j + 168, 90, 20, stylisedAlts ? "Stylised" : "Legecy");
      List var5 = var14;
      int var4 = 0;
      this.stylisedAltsButton = var2;
      var5.add(var2);
      var14 = this.field_146292_n;
      var2 = new GuiButton(82, 5, j + 192, 90, 20, unformattedAlts ? "UNFORMATTEDALTS" : "FORMATTEDALTS");
      var5 = var14;
      var4 = 0;
      this.unformattedAltsButton = var2;
      var5.add(var2);
      var14 = this.field_146292_n;
      GuiSlider var7 = new GuiSlider(-1, 5, j + 216, 90, 20, "length (", ")", (double)6.0F, (double)16.0F, (double)altsLength, false, true, GuiAltManager::initGui$lambda-2);
      var5 = var14;
      var4 = 0;
      this.altsSlider = var7;
      var5.add(var7);
      this.field_146292_n.add(new GuiButton(123, 5, j + 240, 90, 20, "TokenLogin"));
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      int h = this.field_146295_m;
      RenderUtils.drawImage((ResourceLocation)(new ResourceLocation("crosssine/background.png")), -30, -30, this.field_146294_l + 60, this.field_146295_m + 60);
      ParticleUtils.INSTANCE.drawParticles(mouseX, mouseY);
      GuiList var10000 = this.altsList;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("altsList");
         var10000 = null;
      }

      var10000.func_148128_a(mouseX, mouseY, partialTicks);
      Fonts.fontTenacityBold35.drawCenteredString("AltManager", (float)(this.field_146294_l / 2), 6.0F, 16777215);
      Fonts.fontTenacityBold35.drawCenteredString("Alts", (float)(this.field_146294_l / 2), 18.0F, 16777215);
      Fonts.fontTenacityBold35.drawCenteredString(this.status, (float)(this.field_146294_l / 2), 32.0F, 16777215);
      Fonts.fontTenacityBold35.func_175063_a(Intrinsics.stringPlus("UserName : ", this.field_146297_k.func_110432_I().func_111285_a()), 6.0F, 6.0F, 16777215);
      Fonts.fontTenacityBold35.func_175063_a(this.field_146297_k.func_110432_I().func_148254_d().length() >= 32 ? "Premuim" : "Cracked", 6.0F, 15.0F, 16777215);
      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   protected void func_146284_a(@NotNull GuiButton button) {
      Intrinsics.checkNotNullParameter(button, "button");
      if (button.field_146124_l) {
         switch (button.field_146127_k) {
            case 0:
               this.field_146297_k.func_147108_a(this.prevGui);
               break;
            case 1:
               this.field_146297_k.func_147108_a(new GuiAdd(this));
               break;
            case 2:
               GuiList var11 = this.altsList;
               if (var11 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("altsList");
                  var11 = null;
               }

               label106: {
                  if (var11.getSelectedSlot() != -1) {
                     var11 = this.altsList;
                     if (var11 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        var11 = null;
                     }

                     int var13 = var11.getSelectedSlot();
                     GuiList var10002 = this.altsList;
                     if (var10002 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        var10002 = null;
                     }

                     if (var13 < var10002.func_148127_b()) {
                        List var15 = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
                        var10002 = this.altsList;
                        if (var10002 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("altsList");
                           var10002 = null;
                        }

                        var15.remove(var10002.getSelectedSlot());
                        CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getAccountsConfig());
                        var14 = "§aRemove";
                        break label106;
                     }
                  }

                  var14 = "§cNeed Select";
               }

               this.status = var14;
               break;
            case 3:
               GuiList var7 = this.altsList;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("altsList");
                  var7 = null;
               }

               if (var7.getSelectedSlot() != -1) {
                  var7 = this.altsList;
                  if (var7 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("altsList");
                     var7 = null;
                  }

                  int var9 = var7.getSelectedSlot();
                  GuiList var10 = this.altsList;
                  if (var10 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("altsList");
                     var10 = null;
                  }

                  if (var9 < var10.func_148127_b()) {
                     (new Thread(GuiAltManager::actionPerformed$lambda-4)).start();
                     break;
                  }
               }

               this.status = "§cNeed Select";
               break;
            case 4:
               if (CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size() <= 0) {
                  this.status = "§cEmpty List";
                  return;
               }

               int randomInteger = (new Random()).nextInt(CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size());
               GuiList var10001 = this.altsList;
               if (var10001 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("altsList");
                  var10001 = null;
               }

               if (randomInteger < var10001.func_148127_b()) {
                  GuiList var6 = this.altsList;
                  if (var6 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("altsList");
                     var6 = null;
                  }

                  var6.setSelectedSlot(randomInteger);
               }

               (new Thread(GuiAltManager::actionPerformed$lambda-5)).start();
               break;
            case 6:
               this.field_146297_k.func_147108_a(new GuiDirectLogin(this));
               break;
            case 81:
               Companion var4 = Companion;
               stylisedAlts = !stylisedAlts;
               GuiButton var5 = this.stylisedAltsButton;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("stylisedAltsButton");
                  var5 = null;
               }

               var5.field_146126_j = stylisedAlts ? "Stylised" : "Legecy";
               break;
            case 82:
               Companion var10000 = Companion;
               unformattedAlts = !unformattedAlts;
               GuiButton var3 = this.unformattedAltsButton;
               if (var3 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("unformattedAltsButton");
                  var3 = null;
               }

               var3.field_146126_j = unformattedAlts ? "UNFORMATTEDALTS" : "FORMATTEDALTS";
               break;
            case 89:
               (new Thread(GuiAltManager::actionPerformed$lambda-6)).start();
               break;
            case 92:
               this.field_146297_k.func_147108_a(new MicrosoftLogin(this));
               break;
            case 93:
               (new Thread(GuiAltManager::actionPerformed$lambda-8)).start();
               break;
            case 123:
               this.field_146297_k.func_147108_a(new SessionGui(this));
         }

      }
   }

   public void func_73876_c() {
      super.func_73876_c();
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      switch (keyCode) {
         case 1:
            CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getSpecialConfig());
            this.field_146297_k.func_147108_a(this.prevGui);
            return;
         case 28:
            GuiList var11 = this.altsList;
            if (var11 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var11 = null;
            }

            GuiList var12 = this.altsList;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var12 = null;
            }

            var11.func_148144_a(var12.getSelectedSlot(), true, 0, 0);
            break;
         case 200:
            GuiList var9 = this.altsList;
            if (var9 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var9 = null;
            }

            int i = var9.getSelectedSlot() - 1;
            if (i < 0) {
               i = 0;
            }

            var9 = this.altsList;
            if (var9 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var9 = null;
            }

            var9.func_148144_a(i, false, 0, 0);
            break;
         case 201:
            GuiList var8 = this.altsList;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var8 = null;
            }

            var8.func_148145_f(-this.field_146295_m + 100);
            return;
         case 208:
            GuiList var5 = this.altsList;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var5 = null;
            }

            int i = var5.getSelectedSlot() + 1;
            GuiList var10001 = this.altsList;
            if (var10001 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var10001 = null;
            }

            if (i >= var10001.func_148127_b()) {
               var5 = this.altsList;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("altsList");
                  var5 = null;
               }

               i = var5.func_148127_b() - 1;
            }

            var5 = this.altsList;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var5 = null;
            }

            var5.func_148144_a(i, false, 0, 0);
            break;
         case 209:
            GuiList var10000 = this.altsList;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var10000 = null;
            }

            var10000.func_148145_f(this.field_146295_m - 100);
      }

      super.func_73869_a(typedChar, keyCode);
   }

   public void func_146274_d() {
      super.func_146274_d();
      GuiList var10000 = this.altsList;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("altsList");
         var10000 = null;
      }

      var10000.func_178039_p();
   }

   private static final void initGui$lambda_2/* $FF was: initGui$lambda-2*/(GuiSlider it) {
      Companion var10000 = Companion;
      altsLength = it.getValueInt();
   }

   private static final void actionPerformed$lambda_4/* $FF was: actionPerformed$lambda-4*/(GuiAltManager this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      List var10000 = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
      GuiList var10001 = this$0.altsList;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("altsList");
         var10001 = null;
      }

      MinecraftAccount minecraftAccount = (MinecraftAccount)var10000.get(var10001.getSelectedSlot());
      this$0.status = "§aLogging in";
      this$0.status = Companion.login(minecraftAccount);
   }

   private static final void actionPerformed$lambda_5/* $FF was: actionPerformed$lambda-5*/(int $randomInteger, GuiAltManager this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      MinecraftAccount minecraftAccount = (MinecraftAccount)CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().get($randomInteger);
      this$0.status = "§aLoggin in";
      this$0.status = Companion.login(minecraftAccount);
   }

   private static final void actionPerformed$lambda_6/* $FF was: actionPerformed$lambda-6*/() {
      LoginUtils.INSTANCE.randomCracked();
   }

   private static final void actionPerformed$lambda_8/* $FF was: actionPerformed$lambda-8*/(GuiAltManager this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      this$0.status = EnumChatFormatting.YELLOW + "Waiting for login...";

      try {
         UIManager.LookAndFeelInfo[] $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7 = UIManager.getInstalledLookAndFeels();
         Intrinsics.checkNotNullExpressionValue($this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7, "getInstalledLookAndFeels()");
         UIManager.LookAndFeelInfo[] var1 = $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7;
         int $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7 = 0;
         int var3 = $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7.length;

         while($this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7 < var3) {
            UIManager.LookAndFeelInfo info = var1[$this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7];
            ++$this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7;
            if (Intrinsics.areEqual((Object)info.getName(), (Object)"Windows")) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      try {
         JFileChooser $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7 = new JFileChooser();
         int var13 = 0;
         $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7.setDialogTitle("Select your login cookie file");
         String[] var5 = new String[]{"txt"};
         $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7.setFileFilter((FileFilter)(new FileNameExtensionFilter("Text Files (*.txt)", var5)));
         $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7.setAcceptAllFileFilterUsed(false);
         int returnVal = $this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7.showOpenDialog((Component)null);
         if (returnVal == 0) {
            this$0.status = EnumChatFormatting.YELLOW + "Logging in...";
            final LoginData loginData = CookieUtil.INSTANCE.loginWithCookie($this$actionPerformed_u24lambda_u2d8_u24lambda_u2d7.getSelectedFile());
            if (loginData == null) {
               this$0.status = EnumChatFormatting.RED + "Failed to login with cookie!";
               return;
            }

            this$0.status = EnumChatFormatting.GREEN + "Logged in to " + loginData.username;
            this$0.field_146297_k.field_71449_j = new Session(loginData.username, loginData.uuid, loginData.mcToken, "Cookies");
            <undefinedtype> newAccount = new MinecraftAccount() {
               @NotNull
               private final String name;
               @NotNull
               private final me.liuli.elixir.compat.Session session;

               {
                  String var2 = loginData.username;
                  Intrinsics.checkNotNullExpressionValue(var2, "loginData.username");
                  this.name = var2;
                  var2 = loginData.username;
                  Intrinsics.checkNotNullExpressionValue(var2, "loginData.username");
                  String var10003 = var2;
                  var2 = loginData.uuid;
                  Intrinsics.checkNotNullExpressionValue(var2, "loginData.uuid");
                  String var10004 = var2;
                  var2 = loginData.mcToken;
                  Intrinsics.checkNotNullExpressionValue(var2, "loginData.mcToken");
                  this.session = new me.liuli.elixir.compat.Session(var10003, var10004, var2, "legacy");
               }

               @NotNull
               public String getName() {
                  return this.name;
               }

               @NotNull
               public me.liuli.elixir.compat.Session getSession() {
                  return this.session;
               }

               public void update() {
               }

               public void fromRawJson(@NotNull JsonObject json) {
                  Intrinsics.checkNotNullParameter(json, "json");
               }

               public void toRawJson(@NotNull JsonObject json) {
                  Intrinsics.checkNotNullParameter(json, "json");
               }
            };
            CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().add(newAccount);
            CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getAccountsConfig());
         }

      } catch (Exception e) {
         throw new RuntimeException((Throwable)e);
      }
   }

   static {
      Session var0 = Minecraft.func_71410_x().field_71449_j;
      Intrinsics.checkNotNullExpressionValue(var0, "getMinecraft().session");
      originalSession = var0;
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0014J8\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006H\u0014J(\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\b\u0010\u0019\u001a\u00020\u0006H\u0016J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u0006H\u0014R\u001c\u0010\u0005\u001a\u00020\u00068FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u001b"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList;", "Lnet/minecraft/client/gui/GuiSlot;", "prevGui", "Lnet/minecraft/client/gui/GuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;Lnet/minecraft/client/gui/GuiScreen;)V", "selectedSlot", "", "getSelectedSlot", "()I", "setSelectedSlot", "(I)V", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "var1", "doubleClick", "", "var3", "getSize", "isSelected", "CrossSine"}
   )
   private final class GuiList extends GuiSlot {
      private int selectedSlot;

      public GuiList(@NotNull GuiScreen prevGui) {
         Intrinsics.checkNotNullParameter(GuiAltManager.this, "this$0");
         Intrinsics.checkNotNullParameter(prevGui, "prevGui");
         super(GuiAltManager.this.field_146297_k, prevGui.field_146294_l, prevGui.field_146295_m, 40, prevGui.field_146295_m - 40, 30);
      }

      public final int getSelectedSlot() {
         if (this.selectedSlot > CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size()) {
            this.selectedSlot = -1;
         }

         return this.selectedSlot;
      }

      public final void setSelectedSlot(int var1) {
         this.selectedSlot = var1;
      }

      protected boolean func_148131_a(int id) {
         return this.getSelectedSlot() == id;
      }

      public int func_148127_b() {
         return CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size();
      }

      public void func_148144_a(int var1, boolean doubleClick, int var3, int var4) {
         this.selectedSlot = var1;
         if (doubleClick) {
            GuiList var10000 = GuiAltManager.this.altsList;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("altsList");
               var10000 = null;
            }

            if (var10000.getSelectedSlot() != -1) {
               var10000 = GuiAltManager.this.altsList;
               if (var10000 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("altsList");
                  var10000 = null;
               }

               int var6 = var10000.getSelectedSlot();
               GuiList var10001 = GuiAltManager.this.altsList;
               if (var10001 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("altsList");
                  var10001 = null;
               }

               if (var6 < var10001.func_148127_b()) {
                  (new Thread(GuiList::elementClicked$lambda-0)).start();
                  return;
               }
            }

            GuiAltManager.this.setStatus("§cNeed Select");
         }

      }

      protected void func_180791_a(int id, int x, int y, int var4, int var5, int var6) {
         MinecraftAccount minecraftAccount = (MinecraftAccount)CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().get(id);
         Fonts.fontTenacityBold35.drawCenteredString(minecraftAccount.getName(), (float)this.field_148155_a / 2.0F, (float)y + 2.0F, Color.WHITE.getRGB(), true);
         Fonts.fontTenacityBold35.drawCenteredString(minecraftAccount.getType(), (float)this.field_148155_a / 2.0F, (float)y + 15.0F, Color.LIGHT_GRAY.getRGB(), true);
      }

      protected void func_148123_a() {
      }

      private static final void elementClicked$lambda_0/* $FF was: elementClicked$lambda-0*/(GuiAltManager this$0) {
         Intrinsics.checkNotNullParameter(this$0, "this$0");
         List var10000 = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
         GuiList var10001 = this$0.altsList;
         if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            var10001 = null;
         }

         MinecraftAccount minecraftAccount = (MinecraftAccount)var10000.get(var10001.getSelectedSlot());
         this$0.setStatus("§aLogging in");
         this$0.setStatus(GuiAltManager.Companion.login(minecraftAccount));
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014¨\u0006\u001c"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$Companion;", "", "()V", "altsLength", "", "getAltsLength", "()I", "setAltsLength", "(I)V", "originalSession", "Lnet/minecraft/util/Session;", "getOriginalSession", "()Lnet/minecraft/util/Session;", "setOriginalSession", "(Lnet/minecraft/util/Session;)V", "stylisedAlts", "", "getStylisedAlts", "()Z", "setStylisedAlts", "(Z)V", "unformattedAlts", "getUnformattedAlts", "setUnformattedAlts", "login", "", "account", "Lme/liuli/elixir/account/MinecraftAccount;", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      public final int getAltsLength() {
         return GuiAltManager.altsLength;
      }

      public final void setAltsLength(int var1) {
         GuiAltManager.altsLength = var1;
      }

      public final boolean getUnformattedAlts() {
         return GuiAltManager.unformattedAlts;
      }

      public final void setUnformattedAlts(boolean var1) {
         GuiAltManager.unformattedAlts = var1;
      }

      public final boolean getStylisedAlts() {
         return GuiAltManager.stylisedAlts;
      }

      public final void setStylisedAlts(boolean var1) {
         GuiAltManager.stylisedAlts = var1;
      }

      @NotNull
      public final Session getOriginalSession() {
         return GuiAltManager.originalSession;
      }

      public final void setOriginalSession(@NotNull Session var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         GuiAltManager.originalSession = var1;
      }

      @NotNull
      public final String login(@NotNull MinecraftAccount account) {
         Intrinsics.checkNotNullParameter(account, "account");

         String mc;
         try {
            Minecraft mc = Minecraft.func_71410_x();
            me.liuli.elixir.compat.Session it = account.getSession();
            int var5 = 0;
            mc.field_71449_j = new Session(it.getUsername(), it.getUuid(), it.getToken(), it.getType());
            CrossSine.INSTANCE.getEventManager().callEvent(new SessionEvent());
            mc = Intrinsics.stringPlus("§CName Changed §F", mc.field_71449_j.func_111285_a());
         } catch (Exception e) {
            e.printStackTrace();
            mc = "ERROR";
         }

         return mc;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
