package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B7\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n¢\u0006\u0002\u0010\rJ\u0016\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u0003J\u0006\u0010-\u001a\u00020*J\u0006\u0010.\u001a\u00020*J\u001e\u0010%\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\bJ\u0006\u00100\u001a\u00020*R\u000e\u0010\u000e\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u001e\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u001cj\b\u0012\u0004\u0012\u00020\u001d`\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010 \u001a\u0012\u0012\u0004\u0012\u00020!0\u001cj\b\u0012\u0004\u0012\u00020!`\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0014R\u000e\u0010$\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0014¨\u00061"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "posX", "", "posY", "width", "height", "key", "", "keyName", "", "(FFFFILjava/lang/String;)V", "keyDisplayName", "(FFFFILjava/lang/String;Ljava/lang/String;)V", "baseTabHeight", "baseTabWidth", "direction", "", "hasKeyBind", "getHeight", "()F", "getKey", "()I", "keyColor", "getKeyDisplayName", "()Ljava/lang/String;", "getKeyName", "macros", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/features/macro/Macro;", "Lkotlin/collections/ArrayList;", "maxStroll", "modules", "Lnet/ccbluex/liquidbounce/features/module/Module;", "getPosX", "getPosY", "shadowColor", "stroll", "unusedColor", "usedColor", "getWidth", "click", "", "mouseX", "mouseY", "render", "renderTab", "wheel", "update", "CrossSine"}
)
public final class KeyInfo extends MinecraftInstance {
   private final float posX;
   private final float posY;
   private final float width;
   private final float height;
   private final int key;
   @NotNull
   private final String keyName;
   @NotNull
   private final String keyDisplayName;
   private final int keyColor;
   private final int shadowColor;
   private final int unusedColor;
   private final int usedColor;
   private final int baseTabHeight;
   private final int baseTabWidth;
   private final boolean direction;
   @NotNull
   private ArrayList modules;
   @NotNull
   private ArrayList macros;
   private boolean hasKeyBind;
   private int stroll;
   private int maxStroll;

   public KeyInfo(float posX, float posY, float width, float height, int key, @NotNull String keyName, @NotNull String keyDisplayName) {
      Intrinsics.checkNotNullParameter(keyName, "keyName");
      Intrinsics.checkNotNullParameter(keyDisplayName, "keyDisplayName");
      super();
      this.posX = posX;
      this.posY = posY;
      this.width = width;
      this.height = height;
      this.key = key;
      this.keyName = keyName;
      this.keyDisplayName = keyDisplayName;
      this.keyColor = (new Color(240, 240, 240)).getRGB();
      this.shadowColor = (new Color(210, 210, 210)).getRGB();
      this.unusedColor = (new Color(200, 200, 200)).getRGB();
      this.usedColor = (new Color(0, 0, 0)).getRGB();
      this.baseTabHeight = 150;
      this.baseTabWidth = 100;
      this.direction = this.posY >= 100.0F;
      this.modules = new ArrayList();
      this.macros = new ArrayList();
   }

   public final float getPosX() {
      return this.posX;
   }

   public final float getPosY() {
      return this.posY;
   }

   public final float getWidth() {
      return this.width;
   }

   public final float getHeight() {
      return this.height;
   }

   public final int getKey() {
      return this.key;
   }

   @NotNull
   public final String getKeyName() {
      return this.keyName;
   }

   @NotNull
   public final String getKeyDisplayName() {
      return this.keyDisplayName;
   }

   public KeyInfo(float posX, float posY, float width, float height, int key, @NotNull String keyName) {
      Intrinsics.checkNotNullParameter(keyName, "keyName");
      this(posX, posY, width, height, key, keyName, keyName);
   }

   public final void render() {
      GL11.glPushMatrix();
      GL11.glTranslatef(this.posX, this.posY, 0.0F);
      RenderUtils.drawRoundedRect(0.0F, 2.0F, this.width, this.height + (float)8, 6.0F, this.shadowColor);
      RenderUtils.drawRoundedRect(0.0F, 0.0F, this.width, this.height, 6.0F, this.keyColor);
      Fonts.SFApple40.drawCenteredString(this.keyName, this.width * 0.5F, this.height * 0.9F * 0.5F - (float)Fonts.SFApple35.field_78288_b * 0.5F + 3.0F, this.hasKeyBind ? this.usedColor : this.unusedColor, false);
      GL11.glPopMatrix();
   }

   public final void renderTab() {
      GL11.glPushMatrix();
      GL11.glTranslatef(this.posX + this.width * 0.5F - (float)this.baseTabWidth * 0.5F, this.direction ? this.posY - (float)this.baseTabHeight : this.posY + this.height, 0.0F);
      RenderUtils.drawRoundedRect(0.0F, 0.0F, (float)this.baseTabWidth, (float)this.baseTabHeight, 4.0F, Color.WHITE.getRGB());
      float fontHeight = 10.0F - (float)Fonts.SFApple40.getHeight() * 0.5F;
      float yOffset = 12.0F + (float)Fonts.SFApple40.getHeight() + 10.0F - (float)this.stroll;

      for(Module module : this.modules) {
         if (yOffset > 0.0F && yOffset - (float)20 < 100.0F) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, yOffset, 0.0F);
            Fonts.SFApple40.func_175065_a(module.getLocalizedName(), 12.0F, fontHeight, Color.DARK_GRAY.getRGB(), false);
            Fonts.SFApple40.func_175065_a("-", (float)this.baseTabWidth - 12.0F - (float)Fonts.SFApple40.func_78256_a("-"), fontHeight, Color.RED.getRGB(), false);
            GL11.glPopMatrix();
         }

         yOffset += (float)20;
      }

      for(Macro macro : this.macros) {
         if (yOffset > 0.0F && yOffset - (float)20 < 100.0F) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, yOffset, 0.0F);
            Fonts.SFApple40.func_175065_a(macro.getCommand(), 12.0F, fontHeight, Color.DARK_GRAY.getRGB(), false);
            Fonts.SFApple40.func_175065_a("-", (float)this.baseTabWidth - 12.0F - (float)Fonts.SFApple40.func_78256_a("-"), fontHeight, Color.RED.getRGB(), false);
            GL11.glPopMatrix();
         }

         yOffset += (float)20;
      }

      RenderUtils.drawRoundedRect(0.0F, 0.0F, (float)this.baseTabWidth, 12.0F + (float)Fonts.SFApple40.getHeight() + 10.0F, 6.0F, Color.WHITE.getRGB());
      RenderUtils.drawRoundedRect(0.0F, (float)this.baseTabHeight - 22.0F - (float)Fonts.SFApple40.getHeight(), (float)this.baseTabWidth, (float)this.baseTabHeight, 6.0F, Color.WHITE.getRGB());
      Fonts.SFApple40.func_175065_a(Intrinsics.stringPlus("Key ", this.keyDisplayName), 12.0F, 12.0F, Color.BLACK.getRGB(), false);
      Fonts.SFApple40.func_175065_a("Add", (float)this.baseTabWidth - 12.0F - (float)Fonts.SFApple40.func_78256_a("Add"), (float)this.baseTabHeight - 12.0F - (float)Fonts.SFApple40.getHeight(), (new Color(0, 191, 255)).getRGB(), false);
      GL11.glPopMatrix();
   }

   public final void stroll(float mouseX, float mouseY, int wheel) {
      float scaledMouseX = mouseX - (this.posX + this.width * 0.5F - (float)this.baseTabWidth * 0.5F);
      float scaledMouseY = mouseY - (this.direction ? this.posY - (float)this.baseTabHeight : this.posY + this.height);
      if (!(scaledMouseX < 0.0F) && !(scaledMouseY < 0.0F) && !(scaledMouseX > (float)this.baseTabWidth) && !(scaledMouseY > (float)this.baseTabHeight)) {
         int afterStroll = this.stroll - wheel / 40;
         if (afterStroll > 0 && afterStroll < this.maxStroll - 150) {
            this.stroll = afterStroll;
         }

      }
   }

   public final void update() {
      this.modules = (ArrayList)CrossSine.INSTANCE.getModuleManager().getKeyBind(this.key);
      Iterable $this$filter$iv = (Iterable)CrossSine.INSTANCE.getMacroManager().getMacros();
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         Macro it = (Macro)element$iv$iv;
         int var9 = 0;
         if (it.getKey() == this.getKey()) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      this.macros = (ArrayList)((List)destination$iv$iv);
      this.hasKeyBind = this.modules.size() + this.macros.size() > 0;
      this.stroll = 0;
      this.maxStroll = this.modules.size() * 30 + this.macros.size() * 30;
   }

   public final void click(float mouseX, float mouseY) {
      KeyBindManager keyBindMgr = CrossSine.INSTANCE.getKeyBindManager();
      if (keyBindMgr.getNowDisplayKey() == null) {
         keyBindMgr.setNowDisplayKey(this);
         keyBindMgr.setClicked(true);
         MinecraftInstance.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("random.click"), 1.0F));
      } else {
         float scaledMouseX = mouseX - (this.posX + this.width * 0.5F - (float)this.baseTabWidth * 0.5F);
         float scaledMouseY = mouseY - (this.direction ? this.posY - (float)this.baseTabHeight : this.posY + this.height);
         if (scaledMouseX < 0.0F || scaledMouseY < 0.0F || scaledMouseX > (float)this.baseTabWidth || scaledMouseY > (float)this.baseTabHeight) {
            keyBindMgr.setNowDisplayKey((KeyInfo)null);
            keyBindMgr.setClicked(false);
            return;
         }

         if (scaledMouseY > 22.0F + (float)Fonts.SFApple40.getHeight() && scaledMouseX > (float)this.baseTabWidth - 12.0F - (float)Fonts.SFApple40.func_78256_a("Add")) {
            if (scaledMouseY > (float)this.baseTabHeight - 22.0F - (float)Fonts.SFApple40.getHeight()) {
               keyBindMgr.setPopUI(new KeySelectUI(this));
            } else {
               float yOffset = 12.0F + (float)Fonts.SFApple40.getHeight() + 10.0F - (float)this.stroll;

               for(Module module : this.modules) {
                  if (scaledMouseY > yOffset + (float)5 && scaledMouseY < yOffset + (float)15) {
                     module.setKeyBind(0);
                     this.update();
                     break;
                  }

                  yOffset += (float)20;
               }

               for(Macro macro : this.macros) {
                  if (scaledMouseY > yOffset + (float)5 && scaledMouseY < yOffset + (float)15) {
                     CrossSine.INSTANCE.getMacroManager().getMacros().remove(macro);
                     this.update();
                     break;
                  }

                  yOffset += (float)20;
               }
            }
         }
      }

   }
}
