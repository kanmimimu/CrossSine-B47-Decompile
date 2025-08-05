package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "ArrayList",
   category = ModuleCategory.CLIENT,
   loadConfig = false
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0007H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0001H\u0002J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u0001H\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020#2\u0006\u0010$\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020\u00132\u0006\u0010!\u001a\u00020\u0001H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/ArrayListModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animationMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "animationSpeed", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "backgroundValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "caseValue", "fontValue", "Lnet/ccbluex/liquidbounce/features/value/FontValue;", "modeList", "", "modules", "", "noRender", "rectRound", "", "rectValue", "rounded", "sortedModules", "calculateRadius", "prevWidth", "currentWidth", "getColor", "Ljava/awt/Color;", "index", "", "getModName", "mod", "getModuleTag", "module", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "shouldExpect", "CrossSine"}
)
public final class ArrayListModule extends Module {
   @NotNull
   public static final ArrayListModule INSTANCE = new ArrayListModule();
   @NotNull
   private static final BoolValue backgroundValue = new BoolValue("Background", false);
   @NotNull
   private static final Value rounded;
   @NotNull
   private static final Value modeList;
   @NotNull
   private static final Value rectRound;
   @NotNull
   private static final Value rectValue;
   @NotNull
   private static final ListValue animationMode;
   @NotNull
   private static final Value animationSpeed;
   @NotNull
   private static final ListValue caseValue;
   @NotNull
   private static final BoolValue noRender;
   @NotNull
   private static final FontValue fontValue;
   @NotNull
   private static List modules;
   @NotNull
   private static List sortedModules;

   private ArrayListModule() {
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$sortedBy$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$sortedBy$iv) {
         Module it = (Module)element$iv$iv;
         int var10 = 0;
         if (it.getArray() && !INSTANCE.shouldExpect(it) && it.getSlide() > 0.0F) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$sortedBy$iv = (Iterable)((List)destination$iv$iv);
      $i$f$filter = 0;
      modules = CollectionsKt.sortedWith($this$sortedBy$iv, new ArrayListModule$onUpdate$$inlined$sortedBy$1());
      $this$sortedBy$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
      $i$f$filter = 0;
      sortedModules = CollectionsKt.toList((Iterable)CollectionsKt.sortedWith($this$sortedBy$iv, new ArrayListModule$onUpdate$$inlined$sortedBy$2()));
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      FontRenderer fontRenderer = (FontRenderer)fontValue.get();
      int delta = RenderUtils.deltaTime;
      float textHeight = (float)((FontRenderer)fontValue.get()).field_78288_b + 3.0F;
      float textY = (Boolean)rounded.get() && (Boolean)backgroundValue.get() ? 2.2F : 1.3F;
      int inx = 0;

      for(Module module : sortedModules) {
         if (module.getArray() && !this.shouldExpect(module)) {
            String displayString = this.getModName(module);
            int width = fontRenderer.func_78256_a(displayString);
            if (module.getState() || module.getZoom() != 0.0F) {
               if (module.getState()) {
                  module.setZoom(module.getZoom() + ((float)1 - module.getZoom()) * 0.2F);
               } else if (module.getZoom() > 0.0F) {
                  module.setZoom(module.getZoom() + ((float)0 - module.getZoom()) * 0.2F);
               }
            }

            if (module.getState() || module.getSlide() != 0.0F) {
               if (module.getState()) {
                  module.setSlide(module.getSlide() + ((float)width - module.getSlide()) * ((Number)animationSpeed.get()).floatValue());
                  module.setSlideStep((float)delta / 1.0F);
               } else if (module.getSlide() > 0.0F) {
                  module.setSlide(module.getSlide() + ((float)(-width) - module.getSlide()) * ((Number)animationSpeed.get()).floatValue());
                  module.setSlideStep(0.0F);
               }
            }

            module.setZoom(RangesKt.coerceIn(module.getZoom(), 0.0F, 1.0F));
            module.setSlide(RangesKt.coerceIn(module.getSlide(), 0.0F, (float)width));
            module.setSlideStep(RangesKt.coerceIn(module.getSlideStep(), 0.0F, (float)width));
         }

         float yPos = (textHeight + ((Boolean)rounded.get() && (Boolean)backgroundValue.get() && modeList.equals("Modern") ? 1.5F : 0.0F)) * (float)inx;
         if (module.getArray() && !this.shouldExpect(module) && module.getSlide() > 0.0F) {
            if (!animationMode.equals("None")) {
               module.setArrayY(module.getArrayY() + (yPos - module.getArrayY()) * ((Number)animationSpeed.get()).floatValue());
            } else {
               module.setArrayY(yPos);
            }

            ++inx;
         } else {
            module.setArrayY(yPos);
         }
      }

      Iterable $this$forEachIndexed$iv = (Iterable)modules;
      int $i$f$forEachIndexed = 0;
      int index$iv = 0;

      for(Object item$iv : $this$forEachIndexed$iv) {
         int var12 = index$iv++;
         if (var12 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         Module module = (Module)item$iv;
         int var15 = 0;
         String displayString = INSTANCE.getModName(module);
         float xPos = (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a() - (animationMode.equals("Slide") ? module.getSlide() : (float)((FontRenderer)fontValue.get()).func_78256_a(displayString)) - (float)2;
         float currentWidth = (float)((FontRenderer)fontValue.get()).func_78256_a(displayString);
         float nextWidth = var12 < modules.size() - 1 ? (float)((FontRenderer)fontValue.get()).func_78256_a(INSTANCE.getModName((Module)modules.get(var12 + 1))) : currentWidth;
         float topLeftRadius = var12 == 0 ? 4.0F : 0.0F;
         float topRightRadius = var12 == 0 && !(Boolean)rectRound.get() ? 4.0F : 0.0F;
         float bottomLeftRadius = var12 == modules.size() - 1 ? 4.0F : INSTANCE.calculateRadius(currentWidth, nextWidth);
         float bottomRightRadius = var12 == modules.size() - 1 && !(Boolean)rectRound.get() ? 4.0F : 0.0F;
         GlStateManager.func_179094_E();
         if (animationMode.equals("Zoom")) {
            GlStateManager.func_179109_b(xPos + (float)fontRenderer.func_78256_a(displayString) / 2.0F, module.getArrayY() + textY + ((Boolean)backgroundValue.get() && (Boolean)rounded.get() ? 5.0F : (StringsKt.equals((String)rectValue.get(), "Outline", true) ? 1.0F : 0.0F)) + (float)fontRenderer.field_78288_b / 2.0F, 0.0F);
            GlStateManager.func_179152_a(module.getZoom(), module.getZoom(), 1.0F);
            GlStateManager.func_179109_b(-(xPos + (float)fontRenderer.func_78256_a(displayString) / 2.0F), -(module.getArrayY() + textY + ((Boolean)backgroundValue.get() && (Boolean)rounded.get() ? 5.0F : (StringsKt.equals((String)rectValue.get(), "Outline", true) ? 1.0F : 0.0F)) + (float)fontRenderer.field_78288_b / 2.0F), 0.0F);
         }

         if ((Boolean)backgroundValue.get()) {
            if ((Boolean)rounded.get()) {
               if (modeList.equals("Modern")) {
                  RenderUtils.customRoundedinf((float)((int)xPos) - 3.0F - (float)((Boolean)rectRound.get() ? 5 : 4), module.getArrayY() + (float)5, (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a() - (float)2, module.getArrayY() + textHeight + (float)5, 4.0F, (Boolean)rectRound.get() ? 0.0F : 4.0F, (Boolean)rectRound.get() ? 0.0F : 4.0F, 4.0F, (new Color(0, 0, 0, 150)).getRGB());
               } else {
                  RenderUtils.customRoundedinf((float)((int)xPos) - 3.0F - (float)((Boolean)rectRound.get() ? 5 : 4), module.getArrayY() + (float)5, (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a() - (float)2, module.getArrayY() + textHeight + (float)5, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius, (new Color(0, 0, 0, 150)).getRGB());
               }
            } else {
               RenderUtils.drawRect((float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY() + (StringsKt.equals((String)rectValue.get(), "Outline", true) ? 1.0F : 0.0F), (float)((int)xPos) - (StringsKt.equals((String)rectValue.get(), "right", true) ? 3.0F : 2.0F), module.getArrayY() + textHeight + (StringsKt.equals((String)rectValue.get(), "Outline", true) ? 1.0F : 0.0F), (new Color(0, 0, 0, 150)).getRGB());
            }
         }

         fontRenderer.func_175065_a(displayString, xPos - (float)((Boolean)backgroundValue.get() && (Boolean)rounded.get() ? ((Boolean)rectRound.get() ? 4 : 3) : (StringsKt.equals((String)rectValue.get(), "right", true) ? 1 : 0)), module.getArrayY() + textY + ((Boolean)backgroundValue.get() && (Boolean)rounded.get() ? 5.0F : (StringsKt.equals((String)rectValue.get(), "Outline", true) ? 1.0F : 0.0F)), INSTANCE.getColor(var12).getRGB(), true);
         if ((Boolean)backgroundValue.get() && (Boolean)rounded.get() && (Boolean)rectRound.get()) {
            RenderUtils.drawRoundedRect(xPos - 1.4F + (float)((FontRenderer)fontValue.get()).func_78256_a(displayString), module.getArrayY() + 5.0F, xPos + (float)((FontRenderer)fontValue.get()).func_78256_a(displayString), module.getArrayY() + textHeight + 5.0F, modeList.equals("Normal") ? 0.0F : 1.0F, INSTANCE.getColor(var12).getRGB());
            GlStateManager.func_179117_G();
         } else if (!StringsKt.equals((String)rectValue.get(), "none", true) && (!(Boolean)rounded.get() || !(Boolean)backgroundValue.get())) {
            int rectColor = INSTANCE.getColor(var12).getRGB();
            if (StringsKt.equals((String)rectValue.get(), "left", true)) {
               RenderUtils.drawRect(xPos - (float)3, module.getArrayY(), xPos - (float)2, module.getArrayY() + textHeight, rectColor);
            } else if (StringsKt.equals((String)rectValue.get(), "right", true)) {
               RenderUtils.drawRect((float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a() - 1.0F, module.getArrayY(), (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY() + textHeight, rectColor);
            } else if (StringsKt.equals((String)rectValue.get(), "outline", true)) {
               RenderUtils.drawRect((float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a() - 1.0F, module.getArrayY() - 1.0F, (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY() + textHeight, rectColor);
               RenderUtils.drawRect(xPos - (float)3, module.getArrayY(), xPos - (float)2, module.getArrayY() + textHeight, rectColor);
               if (!Intrinsics.areEqual((Object)module, (Object)modules.get(0))) {
                  String displayStrings = INSTANCE.getModName((Module)modules.get(var12 - 1));
                  RenderUtils.drawRect(xPos - (float)3 - (float)(fontRenderer.func_78256_a(displayStrings) - fontRenderer.func_78256_a(displayString)), module.getArrayY(), xPos - (float)2, module.getArrayY() + 1.0F, rectColor);
                  if (Intrinsics.areEqual((Object)module, (Object)modules.get(modules.size() - 1))) {
                     RenderUtils.drawRect(xPos - (float)3, module.getArrayY() + textHeight, (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY() + textHeight + (float)1, rectColor);
                  }
               } else {
                  RenderUtils.drawRect(xPos - (float)3, module.getArrayY() + (float)1, (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY(), rectColor);
               }
            } else if (StringsKt.equals((String)rectValue.get(), "special", true)) {
               if (Intrinsics.areEqual((Object)module, (Object)modules.get(0))) {
                  RenderUtils.drawRect(xPos - (float)2, module.getArrayY(), (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY() - (float)1, rectColor);
               }

               if (Intrinsics.areEqual((Object)module, (Object)modules.get(modules.size() - 1))) {
                  RenderUtils.drawRect(xPos - (float)2, module.getArrayY() + textHeight, (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY() + textHeight + (float)1, rectColor);
               }
            } else if (StringsKt.equals((String)rectValue.get(), "top", true) && Intrinsics.areEqual((Object)module, (Object)modules.get(0))) {
               RenderUtils.drawRect(xPos - (float)2, module.getArrayY(), (float)(new ScaledResolution(MinecraftInstance.mc)).func_78326_a(), module.getArrayY() - (float)1, rectColor);
            }
         }

         GlStateManager.func_179121_F();
      }

   }

   private final float calculateRadius(float prevWidth, float currentWidth) {
      float diff = Math.abs(prevWidth - currentWidth);
      return RangesKt.coerceIn(diff, 0.0F, 5.0F);
   }

   private final String getModuleTag(Module module) {
      if (module.getTag() == null) {
         return "";
      } else {
         String var10000 = module.getTag();
         Intrinsics.checkNotNull(var10000);
         if (StringsKt.contains$default((CharSequence)var10000, (CharSequence)"§", false, 2, (Object)null)) {
            var10000 = module.getTag();
            Intrinsics.checkNotNull(var10000);
            return var10000;
         } else {
            return Intrinsics.stringPlus("§7 ", module.getTag());
         }
      }
   }

   private final String getModName(Module mod) {
      String displayName = Intrinsics.stringPlus(mod.getLocalizedName(), this.getModuleTag(mod));
      String var4 = ((String)caseValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      if (Intrinsics.areEqual((Object)var4, (Object)"lower")) {
         var4 = displayName.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         displayName = var4;
      } else if (Intrinsics.areEqual((Object)var4, (Object)"upper")) {
         var4 = displayName.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toUpperCase(Locale.ROOT)");
         displayName = var4;
      }

      return displayName;
   }

   private final boolean shouldExpect(Module module) {
      return (Boolean)noRender.get() && module.getCategory() == ModuleCategory.VISUAL || Intrinsics.areEqual((Object)module.getName(), (Object)"CustomClientColor") || Intrinsics.areEqual((Object)module.getName(), (Object)"ArrayList") || Intrinsics.areEqual((Object)module.getName(), (Object)"ChatManager") || Intrinsics.areEqual((Object)module.getName(), (Object)"Scoreboard") || Intrinsics.areEqual((Object)module.getName(), (Object)"TargetHUD") || Intrinsics.areEqual((Object)module.getName(), (Object)"Interface") || Intrinsics.areEqual((Object)module.getName(), (Object)"KeyStrokes");
   }

   private final Color getColor(int index) {
      return ClientTheme.getColor$default(ClientTheme.INSTANCE, index, false, 2, (Object)null);
   }

   // $FF: synthetic method
   public static final FontValue access$getFontValue$p() {
      return fontValue;
   }

   // $FF: synthetic method
   public static final String access$getModName(ArrayListModule $this, Module mod) {
      return $this.getModName(mod);
   }

   static {
      rounded = (new BoolValue("Rounded", false)).displayable(null.INSTANCE);
      String[] var0 = new String[]{"Normal", "Modern"};
      modeList = (new ListValue("RoundedMode", var0, "Normal")).displayable(null.INSTANCE);
      rectRound = (new BoolValue("Rect", false)).displayable(null.INSTANCE);
      var0 = new String[]{"None", "Left", "Right", "Outline", "Special", "Top"};
      rectValue = (new ListValue("RectMode", var0, "Outline")).displayable(null.INSTANCE);
      var0 = new String[]{"Slide", "Zoom", "None"};
      animationMode = new ListValue("AnimationMode", var0, "Slide");
      animationSpeed = (new FloatValue("AnimationSpeed", 0.2F, 0.1F, 0.5F)).displayable(null.INSTANCE);
      var0 = new String[]{"None", "Lower", "Upper"};
      caseValue = new ListValue("Case", var0, "None");
      noRender = new BoolValue("NoRenderModule", false);
      FontRenderer var4 = Fonts.minecraftFont;
      Intrinsics.checkNotNullExpressionValue(var4, "minecraftFont");
      fontValue = new FontValue("Font", var4);
      modules = CollectionsKt.emptyList();
      sortedModules = CollectionsKt.emptyList();
   }
}
