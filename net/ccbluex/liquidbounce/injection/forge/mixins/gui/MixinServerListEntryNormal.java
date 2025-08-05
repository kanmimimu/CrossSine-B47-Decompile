package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ServerListEntryNormal.class})
public abstract class MixinServerListEntryNormal {
   @Shadow
   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
   @Shadow
   private static ResourceLocation field_178015_c;
   @Shadow
   private static ResourceLocation field_178014_d;
   @Shadow
   private GuiMultiplayer field_148303_c;
   @Shadow
   private Minecraft field_148300_d;
   @Shadow
   private ServerData field_148301_e;
   @Shadow
   private ResourceLocation field_148306_i;
   @Shadow
   private String field_148299_g;
   @Shadow
   private DynamicTexture field_148305_h;
   @Shadow
   private long field_148298_f;

   @Shadow
   public abstract void func_148297_b();

   @Shadow
   public abstract boolean func_178013_b();

   @Shadow
   protected abstract void func_178012_a(int var1, int var2, ResourceLocation var3);

   @Inject(
      method = {"drawEntry"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void drawEntry(int p_drawEntry_1_, int p_drawEntry_2_, int p_drawEntry_3_, int p_drawEntry_4_, int p_drawEntry_5_, int p_drawEntry_6_, int p_drawEntry_7_, boolean p_drawEntry_8_, CallbackInfo ci) {
      if (!this.field_148301_e.field_78841_f) {
         this.field_148301_e.field_78841_f = true;
         this.field_148301_e.field_78844_e = -2L;
         this.field_148301_e.field_78843_d = "";
         this.field_148301_e.field_78846_c = "";
         field_148302_b.submit(new Runnable() {
            public void run() {
               try {
                  MixinServerListEntryNormal.this.field_148303_c.func_146789_i().func_147224_a(MixinServerListEntryNormal.this.field_148301_e);
               } catch (UnknownHostException var2) {
                  MixinServerListEntryNormal.this.field_148301_e.field_78844_e = -1L;
                  MixinServerListEntryNormal.this.field_148301_e.field_78843_d = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
               } catch (Exception var3) {
                  MixinServerListEntryNormal.this.field_148301_e.field_78844_e = -1L;
                  MixinServerListEntryNormal.this.field_148301_e.field_78843_d = EnumChatFormatting.DARK_RED + "Can't connect to server.";
               }

            }
         });
      }

      boolean flag = this.field_148301_e.field_82821_f > 47;
      boolean flag1 = this.field_148301_e.field_82821_f < 47;
      boolean flag2 = flag || flag1;
      this.field_148300_d.field_71466_p.func_175063_a(this.field_148301_e.field_78847_a, (float)(p_drawEntry_2_ + 32 + 3), (float)(p_drawEntry_3_ + 1), 16777215);
      List<String> list = this.field_148300_d.field_71466_p.func_78271_c(FMLClientHandler.instance().fixDescription(this.field_148301_e.field_78843_d), p_drawEntry_4_ - 48 - 2);

      for(int i = 0; i < Math.min(list.size(), 2); ++i) {
         this.field_148300_d.field_71466_p.func_175063_a((String)list.get(i), (float)(p_drawEntry_2_ + 32 + 3), (float)(p_drawEntry_3_ + 12 + this.field_148300_d.field_71466_p.field_78288_b * i), 8421504);
      }

      String s2 = flag2 ? EnumChatFormatting.DARK_RED + this.field_148301_e.field_82822_g : this.field_148301_e.field_78846_c;
      int j = this.field_148300_d.field_71466_p.func_78256_a(s2);
      this.field_148300_d.field_71466_p.func_175063_a(s2, (float)(p_drawEntry_2_ + p_drawEntry_4_ - j - 15 - 2), (float)(p_drawEntry_3_ + 1), 8421504);
      int k = 0;
      String s = null;
      int l;
      String s1;
      if (flag2) {
         l = 5;
         s1 = flag ? "Client out of date!" : "Server out of date!";
         s = this.field_148301_e.field_147412_i;
      } else if (this.field_148301_e.field_78841_f && this.field_148301_e.field_78844_e != -2L) {
         if (this.field_148301_e.field_78844_e < 0L) {
            l = 5;
         } else if (this.field_148301_e.field_78844_e < 150L) {
            l = 0;
         } else if (this.field_148301_e.field_78844_e < 300L) {
            l = 1;
         } else if (this.field_148301_e.field_78844_e < 600L) {
            l = 2;
         } else if (this.field_148301_e.field_78844_e < 1000L) {
            l = 3;
         } else {
            l = 4;
         }

         if (this.field_148301_e.field_78844_e < 0L) {
            s1 = "(no connection)";
         } else {
            s1 = this.field_148301_e.field_78844_e + "ms";
            s = this.field_148301_e.field_147412_i;
         }
      } else {
         k = 1;
         l = (int)(Minecraft.func_71386_F() / 100L + (long)(p_drawEntry_1_ * 2) & 7L);
         if (l > 4) {
            l = 8 - l;
         }

         s1 = "Pinging...";
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_148300_d.func_110434_K().func_110577_a(Gui.field_110324_m);
      Gui.func_146110_a(p_drawEntry_2_ + p_drawEntry_4_ - 15, p_drawEntry_3_, (float)(k * 10), (float)(176 + l * 8), 10, 8, 256.0F, 256.0F);
      if (this.field_148301_e.func_147409_e() != null && !this.field_148301_e.func_147409_e().equals(this.field_148299_g)) {
         this.field_148299_g = this.field_148301_e.func_147409_e();
         this.func_148297_b();
         this.field_148303_c.func_146795_p().func_78855_b();
      }

      if (this.field_148305_h != null) {
         this.func_178012_a(p_drawEntry_2_, p_drawEntry_3_, this.field_148306_i);
      } else {
         this.func_178012_a(p_drawEntry_2_, p_drawEntry_3_, field_178015_c);
      }

      int i1 = p_drawEntry_6_ - p_drawEntry_2_;
      int j1 = p_drawEntry_7_ - p_drawEntry_3_;
      String tooltip = FMLClientHandler.instance().enhanceServerListEntry((ServerListEntryNormal)null, this.field_148301_e, p_drawEntry_2_, p_drawEntry_4_, p_drawEntry_3_, i1, j1);
      if (tooltip != null) {
         this.field_148303_c.func_146793_a(tooltip);
      } else if (i1 >= p_drawEntry_4_ - 15 && i1 <= p_drawEntry_4_ - 5 && j1 >= 0 && j1 <= 8) {
         this.field_148303_c.func_146793_a(s1);
      } else if (i1 >= p_drawEntry_4_ - j - 15 - 2 && i1 <= p_drawEntry_4_ - 15 - 2 && j1 >= 0 && j1 <= 8) {
         this.field_148303_c.func_146793_a(s);
      }

      if (this.field_148300_d.field_71474_y.field_85185_A || p_drawEntry_8_) {
         this.field_148300_d.func_110434_K().func_110577_a(field_178014_d);
         Gui.func_73734_a(p_drawEntry_2_, p_drawEntry_3_, p_drawEntry_2_ + 32, p_drawEntry_3_ + 32, -1601138544);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         int k1 = p_drawEntry_6_ - p_drawEntry_2_;
         int l1 = p_drawEntry_7_ - p_drawEntry_3_;
         if (this.func_178013_b()) {
            if (k1 < 32 && k1 > 16) {
               Gui.func_146110_a(p_drawEntry_2_, p_drawEntry_3_, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(p_drawEntry_2_, p_drawEntry_3_, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175392_a((ServerListEntryNormal)null, p_drawEntry_1_)) {
            if (k1 < 16 && l1 < 16) {
               Gui.func_146110_a(p_drawEntry_2_, p_drawEntry_3_, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(p_drawEntry_2_, p_drawEntry_3_, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175394_b((ServerListEntryNormal)null, p_drawEntry_1_)) {
            if (k1 < 16 && l1 > 16) {
               Gui.func_146110_a(p_drawEntry_2_, p_drawEntry_3_, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(p_drawEntry_2_, p_drawEntry_3_, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }
      }

      ci.cancel();
   }
}
