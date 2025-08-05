package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.manage.AccountSerializer;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014J \u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\fH\u0016J\u0018\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0011H\u0014J \u0010\u001a\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0011H\u0014J\b\u0010\u001c\u001a\u00020\fH\u0016J\b\u0010\u001d\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiAdd;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "password", "Lnet/ccbluex/liquidbounce/ui/elements/GuiPasswordField;", "status", "", "username", "Lnet/minecraft/client/gui/GuiTextField;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "CrossSine"}
)
public final class GuiAdd extends GuiScreen {
   @NotNull
   private final GuiAltManager prevGui;
   private GuiTextField username;
   private GuiPasswordField password;
   @Nullable
   private String status;

   public GuiAdd(@NotNull GuiAltManager prevGui) {
      Intrinsics.checkNotNullParameter(prevGui, "prevGui");
      super();
      this.prevGui = prevGui;
      this.status = "§7Idle...";
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, "Add"));
      this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, "ClipBoard"));
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120, "Back"));
      this.username = new GuiTextField(2, Fonts.fontTenacity35, this.field_146294_l / 2 - 100, 60, 200, 20);
      GuiPasswordField var10000 = this.username;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("username");
         var10000 = null;
      }

      ((GuiTextField)var10000).func_146195_b(true);
      var10000 = this.username;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("username");
         var10000 = null;
      }

      var10000.func_146203_f(Integer.MAX_VALUE);
      GameFontRenderer var1 = Fonts.fontTenacity35;
      Intrinsics.checkNotNullExpressionValue(var1, "fontTenacity35");
      this.password = new GuiPasswordField(3, var1, this.field_146294_l / 2 - 100, 85, 200, 20);
      var10000 = this.password;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("password");
         var10000 = null;
      }

      var10000.func_146203_f(Integer.MAX_VALUE);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.func_146278_c(0);
      this.func_73732_a(Fonts.fontTenacity35, "Add", this.field_146294_l / 2, 34, 16777215);
      this.func_73732_a(Fonts.fontTenacity35, this.status == null ? "" : this.status, this.field_146294_l / 2, this.field_146295_m / 4 + 60, 16777215);
      GuiPasswordField var10000 = this.username;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("username");
         var10000 = null;
      }

      ((GuiTextField)var10000).func_146194_f();
      var10000 = this.password;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("password");
         var10000 = null;
      }

      var10000.func_146194_f();
      var10000 = this.username;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("username");
         var10000 = null;
      }

      String it = var10000.func_146179_b();
      Intrinsics.checkNotNullExpressionValue(it, "username.text");
      if (((CharSequence)it).length() == 0) {
         var10000 = this.username;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            var10000 = null;
         }

         if (!var10000.func_146206_l()) {
            this.func_73732_a(Fonts.fontTenacity35, "§7UserName", this.field_146294_l / 2 - 55, 66, 16777215);
         }
      }

      var10000 = this.password;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("password");
         var10000 = null;
      }

      it = var10000.func_146179_b();
      Intrinsics.checkNotNullExpressionValue(it, "password.text");
      if (((CharSequence)it).length() == 0) {
         var10000 = this.password;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            var10000 = null;
         }

         if (!var10000.func_146206_l()) {
            this.func_73732_a(Fonts.fontTenacity35, "§7PassWord", this.field_146294_l / 2 - 74, 91, 16777215);
         }
      }

      it = "Add ms@ before your real username can login microsoft account without browser!";
      int var6 = 0;
      Fonts.fontTenacity35.func_78276_b(it, this.field_146294_l - Fonts.fontTenacity35.func_78256_a(it), this.field_146295_m - Fonts.fontTenacity35.field_78288_b, 16777215);
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
               Iterable $this$any$iv = (Iterable)CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
               int $i$f$any = 0;
               boolean var30;
               if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                  var30 = false;
               } else {
                  label115: {
                     for(Object element$iv : $this$any$iv) {
                        MinecraftAccount it = (MinecraftAccount)element$iv;
                        int var24 = 0;
                        String var29 = it.getName();
                        GuiTextField var33 = this.username;
                        if (var33 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("username");
                           var33 = null;
                        }

                        if (Intrinsics.areEqual((Object)var29, (Object)var33.func_146179_b())) {
                           var30 = true;
                           break label115;
                        }
                     }

                     var30 = false;
                  }
               }

               if (var30) {
                  this.status = "§cAlready Add";
                  return;
               }

               List var31 = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
               AccountSerializer var34 = AccountSerializer.INSTANCE;
               GuiTextField var10002 = this.username;
               if (var10002 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("username");
                  var10002 = null;
               }

               String var11 = var10002.func_146179_b();
               Intrinsics.checkNotNullExpressionValue(var11, "username.text");
               String var35 = var11;
               GuiPasswordField var10003 = this.password;
               if (var10003 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("password");
                  var10003 = null;
               }

               var11 = var10003.func_146179_b();
               Intrinsics.checkNotNullExpressionValue(var11, "password.text");
               var31.add(var34.accountInstance(var35, var11));
               CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getAccountsConfig());
               List var13 = this.field_146292_n;
               Intrinsics.checkNotNullExpressionValue(var13, "buttonList");

               label82: {
                  for(Object var21 : (Iterable)var13) {
                     GuiButton it = (GuiButton)var21;
                     int it = 0;
                     if (it.field_146127_k == 0) {
                        var32 = var21;
                        break label82;
                     }
                  }

                  var32 = null;
               }

               Intrinsics.checkNotNull(var32);
               this.func_146284_a((GuiButton)var32);
               break;
            case 2:
               String $i$f$any = GuiScreen.func_146277_j();
               Intrinsics.checkNotNullExpressionValue($i$f$any, "getClipboardString()");
               CharSequence var10000 = (CharSequence)$i$f$any;
               $i$f$any = new String[]{":"};
               List args = StringsKt.split$default(var10000, $i$f$any, false, 0, 6, (Object)null);
               GuiPasswordField var26 = this.username;
               if (var26 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("username");
                  var26 = null;
               }

               ((GuiTextField)var26).func_146180_a((String)args.get(0));
               var26 = this.password;
               if (var26 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("password");
                  var26 = null;
               }

               String var10001 = (String)CollectionsKt.getOrNull(args, 1);
               if (var10001 == null) {
                  var10001 = "";
               }

               var26.func_146180_a(var10001);
               List var16 = this.field_146292_n;
               Intrinsics.checkNotNullExpressionValue(var16, "buttonList");

               label72: {
                  for(Object it : (Iterable)var16) {
                     GuiButton it = (GuiButton)it;
                     int var8 = 0;
                     if (it.field_146127_k == 1) {
                        var28 = it;
                        break label72;
                     }
                  }

                  var28 = null;
               }

               Intrinsics.checkNotNull(var28);
               this.func_146284_a((GuiButton)var28);
         }

         super.func_146284_a(button);
      }
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      switch (keyCode) {
         case 1:
            this.field_146297_k.func_147108_a(this.prevGui);
            return;
         case 28:
            List var3 = this.field_146292_n;
            Intrinsics.checkNotNullExpressionValue(var3, "buttonList");
            Iterable var9 = (Iterable)var3;
            Iterator var4 = var9.iterator();

            Object var10000;
            while(true) {
               if (var4.hasNext()) {
                  Object var5 = var4.next();
                  GuiButton it = (GuiButton)var5;
                  int var7 = 0;
                  if (it.field_146127_k != 1) {
                     continue;
                  }

                  var10000 = var5;
                  break;
               }

               var10000 = null;
               break;
            }

            Intrinsics.checkNotNull(var10000);
            this.func_146284_a((GuiButton)var10000);
            return;
         default:
            GuiTextField var10 = this.username;
            if (var10 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("username");
               var10 = null;
            }

            if (var10.func_146206_l()) {
               var10 = this.username;
               if (var10 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("username");
                  var10 = null;
               }

               var10.func_146201_a(typedChar, keyCode);
            }

            GuiPasswordField var12 = this.password;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("password");
               var12 = null;
            }

            if (var12.func_146206_l()) {
               var12 = this.password;
               if (var12 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("password");
                  var12 = null;
               }

               var12.func_146201_a(typedChar, keyCode);
            }

            super.func_73869_a(typedChar, keyCode);
      }
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      GuiPasswordField var10000 = this.username;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("username");
         var10000 = null;
      }

      ((GuiTextField)var10000).func_146192_a(mouseX, mouseY, mouseButton);
      var10000 = this.password;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("password");
         var10000 = null;
      }

      var10000.func_146192_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public void func_73876_c() {
      GuiPasswordField var10000 = this.username;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("username");
         var10000 = null;
      }

      ((GuiTextField)var10000).func_146178_a();
      var10000 = this.password;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("password");
         var10000 = null;
      }

      var10000.func_146178_a();
      super.func_73876_c();
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
      super.func_146281_b();
   }
}
