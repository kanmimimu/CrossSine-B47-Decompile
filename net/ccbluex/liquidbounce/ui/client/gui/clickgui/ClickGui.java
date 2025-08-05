package net.ccbluex.liquidbounce.ui.client.gui.clickgui;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.file.config.ConfigManager;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.ui.client.gui.ClickGUIModule;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.CategoryElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.SearchElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.VideoElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.geom.Rectangle;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\f\n\u0002\b\n\u0018\u0000 x2\u00020\u0001:\u0001xB\u0005¢\u0006\u0002\u0010\u0002J$\u0010]\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0'2\u0006\u0010^\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001bH\u0002J\b\u0010`\u001a\u00020\u0006H\u0016J<\u0010a\u001a\u00020b2\u0006\u0010^\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001b2\u0006\u0010c\u001a\u00020\u00042\u0006\u0010d\u001a\u00020Y2\b\b\u0002\u0010e\u001a\u00020\u00042\b\b\u0002\u0010f\u001a\u00020\u0004H\u0002J \u0010g\u001a\u00020b2\u0006\u0010^\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001b2\u0006\u0010c\u001a\u00020\u0004H\u0016J\b\u0010h\u001a\u00020bH\u0002J\u0018\u0010i\u001a\u00020b2\u0006\u0010^\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001bH\u0002J\u0018\u0010j\u001a\u00020b2\u0006\u0010^\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001bH\u0002J\u0010\u0010k\u001a\u00020b2\u0006\u0010^\u001a\u00020\u001bH\u0002J\b\u0010l\u001a\u00020bH\u0016J\u0018\u0010m\u001a\u00020b2\u0006\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020\u001bH\u0014J \u0010q\u001a\u00020b2\u0006\u0010^\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001b2\u0006\u0010r\u001a\u00020\u001bH\u0014J \u0010s\u001a\u00020b2\u0006\u0010^\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001b2\u0006\u0010t\u001a\u00020\u001bH\u0014J\b\u0010u\u001a\u00020bH\u0016J\b\u0010v\u001a\u00020bH\u0002J\b\u0010w\u001a\u00020bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u001f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010#\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0014\u00102\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b3\u0010\u0012R\u000e\u00104\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00109\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u0012\"\u0004\b;\u0010<R\u0014\u0010=\u001a\u00020\u001f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b>\u0010!R\u000e\u0010?\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010B\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\b\"\u0004\bD\u0010\nR\u000e\u0010E\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020GX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020GX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010K\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\bL\u0010\u0012R\u0011\u0010M\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\bN\u0010\u0012R\u000e\u0010O\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010P\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u0012\"\u0004\bR\u0010<R\u000e\u0010S\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010T\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010\u0012\"\u0004\bV\u0010<R\u000e\u0010W\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006y"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/ClickGui;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "animProgress", "", "cant", "", "getCant", "()Z", "setCant", "(Z)V", "categoriesBottommargin", "categoriesTopMargin", "categoryElements", "", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/CategoryElement;", "categoryXOffset", "getCategoryXOffset", "()F", "closed", "elementHeight", "elementsStartY", "endYAnim", "keyBinding", "getKeyBinding", "setKeyBinding", "lastScrollOffset", "", "minWindowHeight", "minWindowWidth", "moveAera", "Lnet/ccbluex/liquidbounce/utils/geom/Rectangle;", "getMoveAera", "()Lnet/ccbluex/liquidbounce/utils/geom/Rectangle;", "moveDragging", "onlineConfigList", "", "Lnet/ccbluex/liquidbounce/file/config/ConfigManager$OnlineConfig;", "quad", "Lkotlin/Pair;", "reloadDelay", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "resizeArea", "resizeDragging", "rotationClickAnim", "scrollOffsetDisplay", "scrollOffsetRaw", "searchElement", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchElement;", "searchHeight", "searchWidth", "getSearchWidth", "searchXOffset", "searchYOffset", "selectedConfig", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/SelectedConfig;", "showOnlineConfigs", "sideWidth", "getSideWidth", "setSideWidth", "(F)V", "splitArea", "getSplitArea", "splitDragging", "startYAnim", "stringWidth", "textEditing", "getTextEditing", "setTextEditing", "tryAgain", "video1", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/VideoElement;", "video2", "warning", "warningAnim", "windowHeight", "getWindowHeight", "windowWidth", "getWindowWidth", "windowXEnd", "windowXStart", "getWindowXStart", "setWindowXStart", "windowYEnd", "windowYStart", "getWindowYStart", "setWindowYStart", "x2", "xButtonColor", "Ljava/awt/Color;", "xHoldOffset", "y2", "yHoldOffset", "determineQuadrant", "mouseX", "mouseY", "doesGuiPauseGame", "drawFullSized", "", "partialTicks", "accentColor", "xOffset", "yOffset", "drawScreen", "handleMisc", "handleMove", "handleResize", "handleSplit", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", "reload", "resetPositions", "Companion", "CrossSine"}
)
public final class ClickGui extends GuiScreen {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final List categoryElements = (List)(new ArrayList());
   private float startYAnim;
   private float endYAnim;
   private float rotationClickAnim;
   @Nullable
   private SearchElement searchElement;
   private float stringWidth;
   private float windowXStart;
   private float windowYStart;
   private float windowXEnd;
   private float windowYEnd;
   private final float minWindowWidth;
   private final float minWindowHeight;
   private boolean warning;
   private float warningAnim;
   private final float searchXOffset;
   private final float searchYOffset;
   @Nullable
   private List onlineConfigList;
   private int scrollOffsetRaw;
   private int lastScrollOffset;
   private float scrollOffsetDisplay;
   @NotNull
   private SelectedConfig selectedConfig;
   private boolean showOnlineConfigs;
   @NotNull
   private TimerMS reloadDelay;
   private boolean tryAgain;
   private float sideWidth;
   private final float searchHeight;
   private final float elementHeight;
   private final float elementsStartY;
   private final float categoriesTopMargin;
   private final float categoriesBottommargin;
   @NotNull
   private final Color xButtonColor;
   private boolean moveDragging;
   private boolean resizeDragging;
   private boolean splitDragging;
   @NotNull
   private Pair quad;
   private final float resizeArea;
   private float x2;
   private float y2;
   private float xHoldOffset;
   private float yHoldOffset;
   private boolean closed;
   private boolean cant;
   private boolean textEditing;
   private boolean keyBinding;
   private float animProgress;
   @NotNull
   private final VideoElement video1;
   @NotNull
   private final VideoElement video2;
   @Nullable
   private static ClickGui instance;

   public ClickGui() {
      this.startYAnim = (float)this.field_146295_m / 2.0F;
      this.endYAnim = (float)this.field_146295_m / 2.0F;
      this.windowXStart = 30.0F;
      this.windowYStart = 30.0F;
      this.windowXEnd = 500.0F;
      this.windowYEnd = 400.0F;
      this.minWindowWidth = 475.0F;
      this.minWindowHeight = 350.0F;
      this.searchXOffset = 10.0F;
      this.searchYOffset = 30.0F;
      this.selectedConfig = new SelectedConfig(CrossSine.INSTANCE.getConfigManager().getNowConfig(), false);
      this.reloadDelay = new TimerMS();
      this.sideWidth = 150.0F;
      this.searchHeight = 20.0F;
      this.elementHeight = 24.0F;
      this.elementsStartY = 55.0F;
      this.categoriesTopMargin = 20.0F;
      this.categoriesBottommargin = 20.0F;
      this.xButtonColor = new Color(0.2F, 0.0F, 0.0F, 1.0F);
      this.quad = new Pair(0, 0);
      this.resizeArea = 12.0F;
      this.video1 = new VideoElement();
      this.video2 = new VideoElement();
      Object[] $this$forEach$iv = ModuleCategory.values();
      int $i$f$forEach = 0;
      Object var3 = $this$forEach$iv;
      int var4 = 0;
      int var5 = $this$forEach$iv.length;

      while(var4 < var5) {
         Object element$iv = ((Object[])var3)[var4];
         ++var4;
         int var8 = 0;
         this.categoryElements.add(new CategoryElement((ModuleCategory)element$iv));
      }

      this.searchElement = new SearchElement(this.windowXStart + this.searchXOffset, this.windowYStart + this.searchYOffset, this.getSearchWidth(), this.searchHeight);
      ((CategoryElement)this.categoryElements.get(0)).setFocused(true);
   }

   public final float getWindowXStart() {
      return this.windowXStart;
   }

   public final void setWindowXStart(float var1) {
      this.windowXStart = var1;
   }

   public final float getWindowYStart() {
      return this.windowYStart;
   }

   public final void setWindowYStart(float var1) {
      this.windowYStart = var1;
   }

   public final float getWindowWidth() {
      return Math.abs(this.windowXEnd - this.windowXStart);
   }

   public final float getWindowHeight() {
      return Math.abs(this.windowYEnd - this.windowYStart);
   }

   public final float getSideWidth() {
      return this.sideWidth;
   }

   public final void setSideWidth(float var1) {
      this.sideWidth = var1;
   }

   private final float getCategoryXOffset() {
      return this.sideWidth;
   }

   private final float getSearchWidth() {
      return this.sideWidth - 10.0F;
   }

   private final Rectangle getMoveAera() {
      return new Rectangle(this.windowXStart, this.windowYStart, this.getWindowWidth() - 20.0F, 20.0F);
   }

   private final Rectangle getSplitArea() {
      return new Rectangle(this.windowXStart + this.sideWidth - (float)5, this.windowYStart, 15.0F, this.getWindowHeight());
   }

   public final boolean getCant() {
      return this.cant;
   }

   public final void setCant(boolean var1) {
      this.cant = var1;
   }

   public final boolean getTextEditing() {
      return this.textEditing;
   }

   public final void setTextEditing(boolean var1) {
      this.textEditing = var1;
   }

   public final boolean getKeyBinding() {
      return this.keyBinding;
   }

   public final void setKeyBinding(boolean var1) {
      this.keyBinding = var1;
   }

   private final void reload() {
      this.categoryElements.clear();
      Object[] $this$forEach$iv = ModuleCategory.values();
      int $i$f$forEach = 0;
      Object var3 = $this$forEach$iv;
      int var4 = 0;
      int var5 = $this$forEach$iv.length;

      while(var4 < var5) {
         Object element$iv = ((Object[])var3)[var4];
         ++var4;
         int var8 = 0;
         this.categoryElements.add(new CategoryElement((ModuleCategory)element$iv));
      }

      ((CategoryElement)this.categoryElements.get(0)).setFocused(true);
   }

   private final Pair determineQuadrant(int mouseX, int mouseY) {
      MutablePair result = new MutablePair(0, 0);
      float offset2 = 0.0F;
      float var5 = this.windowXStart - this.resizeArea;
      float var6 = this.windowXStart - offset2;
      float var7 = (float)mouseX;
      if (var5 <= var7 ? var7 <= var6 : false) {
         result.left = -1;
         this.xHoldOffset = (float)mouseX - this.windowXStart;
      }

      var5 = this.windowXEnd + offset2;
      var6 = this.windowXEnd + this.resizeArea;
      var7 = (float)mouseX;
      if (var5 <= var7 ? var7 <= var6 : false) {
         result.left = 1;
         this.xHoldOffset = (float)mouseX - this.windowXEnd;
      }

      var5 = this.windowYStart - this.resizeArea;
      var6 = this.windowYStart - offset2;
      var7 = (float)mouseY;
      if (var5 <= var7 ? var7 <= var6 : false) {
         result.right = 1;
         this.yHoldOffset = (float)mouseY - this.windowYStart;
      }

      var5 = this.windowYEnd + offset2;
      var6 = this.windowYEnd + this.resizeArea;
      var7 = (float)mouseY;
      if (var5 <= var7 ? var7 <= var6 : false) {
         result.right = -1;
         this.yHoldOffset = (float)mouseY - this.windowYEnd;
      }

      Map.Entry var11 = (Map.Entry)result;
      return new Pair(var11.getKey(), var11.getValue());
   }

   private final void handleMove(int mouseX, int mouseY) {
      if (this.moveDragging) {
         float w = this.getWindowWidth();
         float h = this.getWindowHeight();
         this.windowXStart = (float)mouseX + this.x2;
         this.windowYStart = (float)mouseY + this.y2;
         this.windowXEnd = this.windowXStart + w;
         this.windowYEnd = this.windowYStart + h;
      }

   }

   private final void handleResize(int mouseX, int mouseY) {
      float mouseX = (float)mouseX - this.xHoldOffset;
      float mouseY = (float)mouseY - this.yHoldOffset;
      if (this.resizeDragging) {
         Color triangleColor = new Color(255, 255, 255);
         Pair var6 = TuplesKt.to(this.quad.getFirst(), this.quad.getSecond());
         if (Intrinsics.areEqual((Object)var6, (Object)TuplesKt.to(1, 1))) {
            this.windowXEnd = RangesKt.coerceAtLeast(mouseX, this.windowXStart + this.minWindowWidth);
            this.windowYStart = RangesKt.coerceAtMost(mouseY, this.windowYEnd - this.minWindowHeight);
            RenderUtils.drawSquareTriangle(this.windowXEnd + this.resizeArea, this.windowYStart - this.resizeArea, -this.resizeArea, this.resizeArea, triangleColor, true);
         } else if (Intrinsics.areEqual((Object)var6, (Object)TuplesKt.to(-1, -1))) {
            this.windowXStart = RangesKt.coerceAtMost(mouseX, this.windowXEnd - this.minWindowWidth);
            this.windowYEnd = RangesKt.coerceAtLeast(mouseY, this.windowYStart + this.minWindowHeight);
            RenderUtils.drawSquareTriangle(this.windowXStart - this.resizeArea, this.windowYEnd + this.resizeArea, this.resizeArea, -this.resizeArea, triangleColor, true);
         } else if (Intrinsics.areEqual((Object)var6, (Object)TuplesKt.to(-1, 1))) {
            this.windowXStart = RangesKt.coerceAtMost(mouseX, this.windowXEnd - this.minWindowWidth);
            this.windowYStart = RangesKt.coerceAtMost(mouseY, this.windowYEnd - this.minWindowHeight);
            RenderUtils.drawSquareTriangle(this.windowXStart - this.resizeArea, this.windowYStart - this.resizeArea, this.resizeArea, this.resizeArea, triangleColor, true);
         } else if (Intrinsics.areEqual((Object)var6, (Object)TuplesKt.to(1, -1))) {
            this.windowXEnd = RangesKt.coerceAtLeast(mouseX, this.windowXStart + this.minWindowWidth);
            this.windowYEnd = RangesKt.coerceAtLeast(mouseY, this.windowYStart + this.minWindowHeight);
            RenderUtils.drawSquareTriangle(this.windowXEnd + this.resizeArea, this.windowYEnd + this.resizeArea, -this.resizeArea, -this.resizeArea, triangleColor, true);
         }
      }

   }

   private final void resetPositions() {
      this.windowXStart = 30.0F;
      this.windowYStart = 20.0F;
      this.windowXEnd = (float)this.field_146294_l - 30.0F;
      this.windowYEnd = (float)this.field_146295_m - 20.0F;
      this.resizeDragging = false;
      this.moveDragging = false;
   }

   private final void handleSplit(int mouseX) {
      if (this.splitDragging) {
         this.sideWidth = RangesKt.coerceIn((float)mouseX - this.windowXStart, (Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) > Fonts.font40.func_78256_a("CrossSine B47") ? (float)Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) + 45.0F : (float)Fonts.font40.func_78256_a("CrossSine B47") + 50.0F) + 15.0F, this.getWindowWidth() / (float)2);
      }

   }

   private final void handleMisc() {
      if (Keyboard.isKeyDown(88)) {
         this.resetPositions();
      }

      if (Keyboard.isKeyDown(63)) {
         this.reload();
      }

   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      Iterable $this$forEach$iv = (Iterable)this.categoryElements;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         CategoryElement cat = (CategoryElement)element$iv;
         int var6 = 0;
         Iterable $this$filter$iv = (Iterable)cat.getModuleElements();
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            ModuleElement it = (ModuleElement)element$iv$iv;
            int var15 = 0;
            if (it.listeningKeybind()) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $this$filter$iv = (Iterable)((List)destination$iv$iv);
         $i$f$filter = 0;

         for(Object element$iv : $this$filter$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            int var20 = 0;
            mod.resetState();
         }
      }

      super.func_73866_w_();
   }

   public void func_146281_b() {
      Iterable $this$filter$iv = (Iterable)this.categoryElements;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         CategoryElement it = (CategoryElement)element$iv$iv;
         int var9 = 0;
         if (it.getFocused()) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$filter$iv = (Iterable)((List)destination$iv$iv);
      $i$f$filter = 0;
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10)));
      $i$f$filterTo = 0;

      for(Object item$iv$iv : $this$filter$iv) {
         CategoryElement it = (CategoryElement)item$iv$iv;
         int var18 = 0;
         it.handleMouseRelease(-1, -1, 0, 0.0F, 0.0F, 0.0F, 0.0F);
         destination$iv$iv.add(Unit.INSTANCE);
      }

      List var10000 = (List)destination$iv$iv;
      this.moveDragging = false;
      this.resizeDragging = false;
      this.splitDragging = false;
      this.closed = false;
      this.animProgress = 0.0F;
      Keyboard.enableRepeatEvents(false);
      CrossSine.INSTANCE.getFileManager().saveConfigs();
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.animProgress += 0.001875F * (float)RenderUtils.deltaTime * (this.closed ? -1.0F : 1.0F);
      this.animProgress = RangesKt.coerceIn(this.animProgress, 0.0F, 1.0F);
      this.warningAnim += 0.001875F * (float)RenderUtils.deltaTime * (this.warning ? 1.0F : -1.0F);
      this.warningAnim = RangesKt.coerceIn(this.warningAnim, 0.0F, 1.0F);
      if (this.closed && this.animProgress == 0.0F) {
         this.field_146297_k.func_147108_a((GuiScreen)null);
      }

      if (this.warningAnim == 0.0F) {
         this.warning = true;
      }

      if (this.warningAnim == 1.0F) {
         this.warning = false;
      }

      float percent = (float)EaseUtils.easeOutBack((double)this.animProgress);
      this.field_146297_k.field_71417_B.func_74374_c();
      if (this.moveDragging) {
         this.field_146297_k.field_71417_B.field_74377_a = RangesKt.coerceIn(this.field_146297_k.field_71417_B.field_74377_a, -30, 30);
         this.rotationClickAnim = AnimationUtils.animate(60.0F * ((float)this.field_146297_k.field_71417_B.field_74377_a / 30.0F), this.rotationClickAnim, 0.005F * (float)RenderUtils.deltaTime);
      } else {
         this.rotationClickAnim = AnimationUtils.animate(0.0F, this.rotationClickAnim, 0.005F * (float)RenderUtils.deltaTime);
      }

      GL11.glPushMatrix();
      GL11.glTranslatef(this.windowXStart + this.getWindowWidth() / (float)2, this.windowYStart + this.getWindowHeight() / (float)2, 0.0F);
      GlStateManager.func_179114_b(this.rotationClickAnim, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-(this.windowXStart + this.getWindowWidth() / (float)2), -(this.windowYStart + this.getWindowHeight() / (float)2), 0.0F);
      if (!(Boolean)ClickGUIModule.fastRenderValue.get()) {
         GL11.glScalef(percent, percent, percent);
         GL11.glTranslatef(this.windowXEnd * 0.5F * ((float)1 - percent) / percent, this.windowYEnd * 0.5F * ((float)1 - percent) / percent, 0.0F);
      }

      for(CategoryElement ce : this.categoryElements) {
         SearchElement var10000 = this.searchElement;
         Intrinsics.checkNotNull(var10000);
         String var7 = var10000.getSearchBox().func_146179_b();
         Intrinsics.checkNotNullExpressionValue(var7, "searchElement!!.searchBox.text");
         if (((CharSequence)var7).length() == 0 && Intrinsics.areEqual((Object)ce.getName(), (Object)"CONFIG") && ce.getFocused()) {
            this.lastScrollOffset = Mouse.getDWheel();
         }
      }

      this.handleMisc();
      this.handleMove(mouseX, mouseY);
      this.handleResize(mouseX, mouseY);
      this.handleSplit(mouseX);
      drawFullSized$default(this, mouseX, mouseY, partialTicks, ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null), 0.0F, 0.0F, 48, (Object)null);
      if (this.warningAnim > 0.0F && (this.windowXEnd > (float)this.field_146294_l || this.windowYEnd > (float)this.field_146295_m)) {
         FontLoaders.F24.drawCenteredString("Press F12 to reset scale", (double)this.field_146294_l / (double)2.0F, (double)10.0F, (new Color(255, 0, 0, (int)((float)255 * this.warningAnim))).getRGB());
      }

      GL11.glPopMatrix();
      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   private final void drawFullSized(int mouseX, int mouseY, float partialTicks, Color accentColor, float xOffset, float yOffset) {
      float windowRadius = 9.0F;
      RenderUtils.drawBloomGradientRoundedRect(this.windowXStart + xOffset, this.windowYStart + yOffset, this.windowXEnd + xOffset, this.windowYEnd + yOffset, windowRadius, 7.0F, new Color(10, 10, 10), new Color(30, 30, 30), RenderUtils.ShaderBloom.BOTH);
      float startY = this.windowXEnd - 20.0F;
      float var9 = this.windowXEnd;
      float var10 = (float)mouseX;
      if (startY <= var10 ? var10 <= var9 : false) {
         startY = this.windowYStart;
         var9 = this.windowYStart + 20.0F;
         var10 = (float)mouseY;
         if (startY <= var10 ? var10 <= var9 : false) {
            RenderUtils.drawBloomRoundedRect(this.windowXEnd + xOffset - 20.0F, this.windowYStart + yOffset, this.windowXEnd + xOffset, this.windowYStart + yOffset + 20.0F, windowRadius, 3.0F, this.xButtonColor, RenderUtils.ShaderBloom.BLOOMONLY);
         }
      }

      GlStateManager.func_179118_c();
      RenderUtils.drawImage((ResourceLocation)IconManager.INSTANCE.getRemoveIcon(), (int)(this.windowXEnd + xOffset) - 15, (int)(this.windowYStart + yOffset) + 5, 10, 10);
      GlStateManager.func_179141_d();
      SearchElement var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      var10000.setXPos(this.windowXStart + xOffset + this.searchXOffset);
      var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      var10000.setYPos(this.windowYStart + yOffset + this.searchYOffset);
      var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      var10000.setWidth(this.getSearchWidth());
      var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      var10000.getSearchBox().field_146218_h = (int)this.getSearchWidth() - 4;
      var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      var10000.getSearchBox().field_146209_f = (int)(this.windowXStart + xOffset + this.searchXOffset + (float)2);
      var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      var10000.getSearchBox().field_146210_g = (int)(this.windowYStart + yOffset + this.searchYOffset + (float)2);
      var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      if (var10000.drawBox(mouseX, mouseY, accentColor)) {
         var10000 = this.searchElement;
         Intrinsics.checkNotNull(var10000);
         var10000.drawPanel(mouseX, mouseY, this.windowXStart + xOffset + this.getCategoryXOffset(), this.windowYStart + yOffset + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, Mouse.getDWheel(), this.categoryElements, accentColor);
      } else {
         RenderUtils.drawBloomRoundedRect(this.windowXStart + xOffset + 4.0F + 8.0F, this.startYAnim - 3.0F, this.windowXStart + this.sideWidth, this.endYAnim + 3.0F, 5.0F, 2.5F, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 190, false, 4, (Object)null), RenderUtils.ShaderBloom.BOTH);
         startY = this.windowYStart + yOffset + this.elementsStartY;

         for(CategoryElement ce : this.categoryElements) {
            ce.drawLabel(mouseX, mouseY, this.windowXStart + xOffset, startY, this.getCategoryXOffset(), this.elementHeight);
            if (ce.getFocused()) {
               this.startYAnim = (Boolean)ClickGUIModule.fastRenderValue.get() ? startY + 6.0F : AnimationUtils.animate(startY + 6.0F, this.startYAnim, (this.startYAnim - (startY + 5.0F) > 0.0F ? 0.65F : 0.55F) * (float)RenderUtils.deltaTime * 0.025F);
               this.endYAnim = (Boolean)ClickGUIModule.fastRenderValue.get() ? startY + this.elementHeight - 6.0F : AnimationUtils.animate(startY + this.elementHeight - 6.0F, this.endYAnim, (this.endYAnim - (startY + this.elementHeight - 5.0F) < 0.0F ? 0.65F : 0.55F) * (float)RenderUtils.deltaTime * 0.025F);
               ce.drawPanel(mouseX, mouseY, this.windowXStart + xOffset + this.getCategoryXOffset(), this.windowYStart + yOffset + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, Mouse.getDWheel(), accentColor);
            }

            startY += this.elementHeight;
            if (Intrinsics.areEqual((Object)ce.getName(), (Object)"INFO") && ce.getFocused()) {
               RenderUtils.drawRect(this.windowXStart + this.sideWidth + 5.0F, this.windowYStart + 100.0F, this.windowXEnd, this.windowYStart + 102.0F, (new Color(50, 50, 50)).getRGB());
               GlStateManager.func_179094_E();
               Fonts.font50SemiBold.drawString("CrossSine B47", this.windowXStart + this.sideWidth + 15.0F, this.windowYStart + 20.0F, (new Color(100, 100, 100)).getRGB());
               Fonts.font30SemiBold.drawString("Made with <3 by shxp3", this.windowXStart + this.sideWidth + 15.0F, this.windowYStart + 35.0F, (new Color(100, 100, 100)).getRGB());
               RenderUtils.drawImage((ResourceLocation)(new ResourceLocation("crosssine/ui/icons/discord.png")), (int)(this.windowXStart + this.sideWidth + 15.0F), (int)(this.windowYStart + 45.0F), 28, 28);
               Fonts.font35SemiBold.drawString("Discord", this.windowXStart + this.sideWidth + 45.0F, this.windowYStart + 55.0F, (new Color(50, 50, 50)).getRGB());
               RenderUtils.drawImage((ResourceLocation)(new ResourceLocation("crosssine/ui/icons/github.png")), (int)(this.windowXStart + this.sideWidth + 15.0F), (int)(this.windowYStart + 70.0F), 28, 28);
               Fonts.font35SemiBold.drawString("Github", this.windowXStart + this.sideWidth + 45.0F, this.windowYStart + 82.0F, (new Color(50, 50, 50)).getRGB());
               this.video1.draw("BmCqBr_-qtI", mouseX, mouseY, this.windowXStart + this.sideWidth + 28.0F, this.windowYStart + 123.0F, 150, 84);
               this.video2.draw("QKXBks1Tgh4", mouseX, mouseY, this.windowXStart + this.sideWidth + 28.0F, this.windowYStart + 123.0F + 98.0F, 150, 84);
               GlStateManager.func_179121_F();
            }

            if (Intrinsics.areEqual((Object)ce.getName(), (Object)"CONFIG") && ce.getFocused()) {
               float baseX = this.windowXStart + this.sideWidth + 15.0F;
               float baseY = this.windowYStart + 50.0F;
               if (this.onlineConfigList == null) {
                  (new Thread(ClickGui::drawFullSized$lambda-7)).start();
               }

               File[] var100 = CrossSine.INSTANCE.getFileManager().getConfigsDir().listFiles();
               if (var100 == null) {
                  return;
               }

               File[] $this$filter$iv = var100;
               int $i$f$filter = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList());
               int $i$f$filterTo = 0;
               File[] var19 = $this$filter$iv;
               int var20 = 0;
               int $i$f$mapTo = $this$filter$iv.length;

               while(var20 < $i$f$mapTo) {
                  Object element$iv$iv = var19[var20];
                  ++var20;
                  int var24 = 0;
                  if (((File)element$iv$iv).isFile()) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               Iterable $this$map$iv = (Iterable)((List)destination$iv$iv);
               $i$f$filter = 0;
               destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
               $i$f$filterTo = 0;

               for(Object item$iv$iv : $this$map$iv) {
                  File $i$f$mapTo = (File)item$iv$iv;
                  int var72 = 0;
                  String name = $i$f$mapTo.getName();
                  Intrinsics.checkNotNullExpressionValue(name, "name");
                  String var101;
                  if (StringsKt.endsWith$default(name, ".json", false, 2, (Object)null)) {
                     String it = name.substring(0, name.length() - 5);
                     Intrinsics.checkNotNullExpressionValue(it, "this as java.lang.String…ing(startIndex, endIndex)");
                     var101 = it;
                  } else {
                     var101 = name;
                  }

                  destination$iv$iv.add(var101);
               }

               List configList = (List)destination$iv$iv;
               List var103;
               if (!this.showOnlineConfigs) {
                  var103 = configList;
               } else {
                  var103 = this.onlineConfigList;
                  if (var103 == null) {
                     var103 = null;
                  } else {
                     Iterable $this$map$iv = (Iterable)var103;
                     $i$f$filterTo = 0;
                     Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
                     $i$f$mapTo = 0;

                     for(Object item$iv$iv : $this$map$iv) {
                        ConfigManager.OnlineConfig item$iv = (ConfigManager.OnlineConfig)item$iv$iv;
                        int i = 0;
                        destination$iv$iv.add(item$iv.getName());
                     }

                     var103 = (List)destination$iv$iv;
                  }

                  if (var103 == null) {
                     var103 = CollectionsKt.emptyList();
                  }
               }

               List listToDisplay = var103;
               $i$f$filter = 5;
               float entryHeight = 20.0F;
               int maxScroll = Math.max(0, listToDisplay.size() - $i$f$filter);
               if (this.lastScrollOffset != 0) {
                  this.scrollOffsetRaw -= MathKt.getSign(this.lastScrollOffset);
                  this.scrollOffsetRaw = RangesKt.coerceIn(this.scrollOffsetRaw, 0, maxScroll);
               }

               this.scrollOffsetDisplay += ((float)this.scrollOffsetRaw - this.scrollOffsetDisplay) * 0.2F;
               $i$f$filterTo = MathKt.roundToInt(this.scrollOffsetDisplay);
               RenderUtils.drawBloomRoundedRect(baseX, baseY - 23.0F, baseX + 200.0F, baseY + 20.0F, 5.0F, 1.5F, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
               boolean downloaded = false;

               for(String file : configList) {
                  if (Intrinsics.areEqual((Object)this.selectedConfig.getName(), (Object)file)) {
                     downloaded = true;
                  }
               }

               GlStateManager.func_179117_G();
               Fonts.font40SemiBold.drawString(Intrinsics.stringPlus("Now Config: ", CrossSine.INSTANCE.getConfigManager().getNowConfig()), baseX + 5.0F, baseY - 14.0F, Color.WHITE.getRGB());
               Fonts.font40SemiBold.drawString("Config: " + this.selectedConfig.getName() + ' ' + (this.selectedConfig.isOnline() ? (downloaded ? "(Downloaded)" : "(Online)") : ""), baseX + 5.0F, baseY + 6.0F, Color.WHITE.getRGB());
               if (this.showOnlineConfigs) {
                  if (this.reloadDelay.hasTimePassed(10000L) && this.tryAgain) {
                     this.tryAgain = false;
                  }

                  if (this.tryAgain) {
                     Fonts.font24SemiBold.func_78276_b("Try again later", (int)baseX + 212, (int)baseY + 25, Color.RED.getRGB());
                  }

                  RenderUtils.drawImage((ResourceLocation)(new ResourceLocation("crosssine/ui/icons/reload.png")), (int)baseX + 202, (int)baseY + 25, 10, 10);
               }

               Iterable $this$forEachIndexed$iv = (Iterable)CollectionsKt.take((Iterable)CollectionsKt.drop((Iterable)listToDisplay, $i$f$filterTo), $i$f$filter);
               $i$f$mapTo = 0;
               int index$iv = 0;

               for(Object item$iv : $this$forEachIndexed$iv) {
                  int loadX2 = index$iv++;
                  if (loadX2 < 0) {
                     CollectionsKt.throwIndexOverflow();
                  }

                  String name = (String)item$iv;
                  int var28 = 0;
                  float entryY = baseY + 25.0F + (float)loadX2 * entryHeight;
                  RenderUtils.customRoundedinf(baseX, entryY, baseX + 200.0F, entryY + entryHeight, loadX2 == 0 ? 4.0F : 0.0F, loadX2 == 0 ? 4.0F : 0.0F, loadX2 == listToDisplay.size() - 1 ? 4.0F : 0.0F, loadX2 == listToDisplay.size() - 1 ? 4.0F : 0.0F, (new Color(30, 30, 30, 180)).getRGB());
                  SelectedConfig var104 = this.selectedConfig;
                  Intrinsics.checkNotNullExpressionValue(name, "name");
                  if (Intrinsics.areEqual((Object)var104, (Object)(new SelectedConfig(name, this.showOnlineConfigs)))) {
                     RenderUtils.drawRoundedOutline(baseX, entryY, baseX + 200.0F, entryY + entryHeight, 3.0F, 3.0F, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, (Object)null).getRGB());
                  }

                  List var105 = this.onlineConfigList;
                  ConfigManager.OnlineConfig var107;
                  if (var105 == null) {
                     var107 = null;
                  } else {
                     Iterator var33 = ((Iterable)var105).iterator();

                     while(true) {
                        if (var33.hasNext()) {
                           Object var34 = var33.next();
                           ConfigManager.OnlineConfig it = (ConfigManager.OnlineConfig)var34;
                           int var36 = 0;
                           if (!Intrinsics.areEqual((Object)it.getName(), (Object)name)) {
                              continue;
                           }

                           var107 = (ConfigManager.OnlineConfig)var34;
                           break;
                        }

                        var107 = null;
                        break;
                     }

                     var107 = var107;
                  }

                  ConfigManager.OnlineConfig onlineCfg = var107;
                  float xName = baseX + 5.0F;
                  float yText = entryY + 6.0F;
                  float nameWidth = (float)Fonts.font40SemiBold.func_78256_a(name);
                  Fonts.font40SemiBold.drawString(name, baseX + 5.0F, entryY + 6.0F, Color.LIGHT_GRAY.getRGB());
                  if (this.showOnlineConfigs) {
                     Fonts.font35SemiBold.drawString((onlineCfg == null ? null : onlineCfg.getDaysAgo()) != null ? " (" + onlineCfg.getDaysAgo() + "d ago)" : " (loading...)", xName + nameWidth + 2.0F, yText + 1.0F, (new Color(170, 170, 170)).getRGB());
                  }

                  if (MouseUtils.mouseWithinBounds(mouseX, mouseY, baseX, entryY, baseX + 200.0F, entryY + entryHeight) && Mouse.isButtonDown(0)) {
                     this.selectedConfig = new SelectedConfig(name, this.showOnlineConfigs);
                  }
               }

               if (configList.size() > $i$f$filter) {
                  float barHeight = RangesKt.coerceAtLeast(entryHeight * (float)$i$f$filter * (float)$i$f$filter / (float)configList.size(), 10.0F);
                  float barY = baseY + 25.0F + entryHeight * (float)$i$f$filterTo * (float)$i$f$filter / (float)configList.size();
                  float barX = baseX + 200.0F - 4.0F;
                  RenderUtils.drawRoundedRect(barX + 5.0F, barY, barX + 7.0F, barY + barHeight, 1.0F, (new Color(150, 150, 150, 180)).getRGB());
               }

               float saveX2 = baseX + 95.0F;
               float saveY1 = baseY + 140.0F;
               float saveY2 = saveY1 + 20.0F;
               RenderUtils.drawBloomRoundedRect(baseX, saveY1, saveX2, saveY2, 6.0F, 1.5F, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
               Fonts.font40SemiBold.drawCenteredString("Folder", (baseX + saveX2) / (float)2, saveY1 + 6.0F, Color.WHITE.getRGB());
               float loadX1 = baseX + 105.0F;
               float loadX2 = loadX1 + 95.0F;
               float loadY1 = baseY + 140.0F;
               float loadY2 = loadY1 + 20.0F;
               RenderUtils.drawBloomRoundedRect(loadX1, loadY1, loadX2, loadY2, 6.0F, 1.5F, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
               Fonts.font40SemiBold.drawCenteredString("Load", (loadX1 + loadX2) / (float)2, loadY1 + 6.0F, Color.WHITE.getRGB());
               float toggleX1 = baseX + 105.0F;
               float toggleX2 = toggleX1 + 95.0F;
               float toggleY1 = loadY2 + 5.0F;
               float toggleY2 = toggleY1 + 20.0F;
               RenderUtils.drawBloomRoundedRect(toggleX1, toggleY1, toggleX2, toggleY2, 6.0F, 1.5F, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
               String toggleLabel = this.showOnlineConfigs ? "Show Local" : "Show Online";
               Fonts.font40SemiBold.drawCenteredString(toggleLabel, (toggleX1 + toggleX2) / (float)2, toggleY1 + 6.0F, Color.WHITE.getRGB());
               float deleteY1 = saveY2 + 5.0F;
               float deleteY2 = deleteY1 + 20.0F;
               RenderUtils.drawBloomRoundedRect(baseX, deleteY1, saveX2, deleteY2, 6.0F, 1.5F, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
               Fonts.font40SemiBold.drawCenteredString("Delete", (baseX + saveX2) / (float)2, deleteY1 + 6.0F, Color.WHITE.getRGB());
            }
         }

         for(CategoryElement ce : this.categoryElements) {
            if (ce.getFocused()) {
               this.stringWidth = (Boolean)ClickGUIModule.fastRenderValue.get() ? (float)Fonts.font40SemiBold.func_78256_a(ce.getName()) : AnimationUtils.animate((float)Fonts.font40SemiBold.func_78256_a(ce.getName()), this.stringWidth, 0.55F * (float)RenderUtils.deltaTime * 0.025F);
            }
         }

         RenderUtils.drawRect(this.windowXStart + this.sideWidth + 5.0F, this.windowYStart, this.windowXStart + this.sideWidth + 7.0F, this.windowYEnd, (new Color(50, 50, 50)).getRGB());
         RenderUtils.drawRoundedOutline(this.windowXStart, this.windowYStart, this.windowXEnd, this.windowYEnd, 13.0F, 4.0F, (new Color(50, 50, 50)).getRGB());
         GlStateManager.func_179117_G();
         GL11.glPushMatrix();
         Stencil.write(false);
         GL11.glDisable(3553);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         RenderUtils.fastRoundedRect(this.windowXStart + 10.0F, this.windowYEnd - 10.0F, this.windowXStart + 5.0F + 45.0F, this.windowYEnd - 5.0F - 45.0F, 10.0F);
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         Stencil.erase(true);
         EntityPlayerSP var44 = this.field_146297_k.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var44, "mc.thePlayer");
         RenderUtils.drawHead(EntityExtensionKt.getSkin((EntityLivingBase)var44), (int)(this.windowXStart + (float)10), (int)(this.windowYEnd - (float)50), 40, 40, Color.WHITE.getRGB());
         GlStateManager.func_179117_G();
         Stencil.dispose();
         GL11.glPopMatrix();
         DecimalFormat decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
         Fonts.font32.drawString(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_()), this.windowXStart + 55.0F, this.windowYEnd - 20.0F, Color.white.getRGB());
         Fonts.font32.drawString(Intrinsics.stringPlus("Health : ", decimalFormat3.format(this.field_146297_k.field_71439_g.func_110143_aJ())), this.windowXStart + 55.0F, this.windowYEnd - 20.0F - (float)Fonts.font32.getHeight(), Color.white.getRGB());
         Fonts.font40.drawString("CrossSine B47", this.windowXStart + 55.0F, this.windowYEnd - 20.0F - (float)(Fonts.font40.getHeight() * 2), Color.white.getRGB());
         RenderUtils.drawRoundedOutline(this.windowXStart + 5.0F, this.windowYEnd - 55.0F, this.windowXStart + 15.0F + (Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) > Fonts.font40.func_78256_a("CrossSine B47") ? (float)Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) + 45.0F : (float)Fonts.font40.func_78256_a("CrossSine B47") + 50.0F), this.windowYEnd - 5.0F, 15.0F, 4.0F, (new Color(50, 50, 50)).getRGB());
      }
   }

   // $FF: synthetic method
   static void drawFullSized$default(ClickGui var0, int var1, int var2, float var3, Color var4, float var5, float var6, int var7, Object var8) {
      if ((var7 & 16) != 0) {
         var5 = 0.0F;
      }

      if ((var7 & 32) != 0) {
         var6 = 0.0F;
      }

      var0.drawFullSized(var1, var2, var3, var4, var5, var6);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
      SearchElement var10000 = this.searchElement;
      Intrinsics.checkNotNull(var10000);
      if (var10000.isTyping() && (new Rectangle(this.windowXStart, this.windowYStart, 60.0F, 24.0F)).contains(mouseX, mouseY)) {
         var10000 = this.searchElement;
         Intrinsics.checkNotNull(var10000);
         var10000.getSearchBox().func_146180_a("");
      } else if (this.getMoveAera().contains(mouseX, mouseY) && !this.moveDragging) {
         this.moveDragging = true;
         this.x2 = this.windowXStart - (float)mouseX;
         this.y2 = this.windowYStart - (float)mouseY;
      } else if ((new Rectangle(this.windowXEnd - (float)20, this.windowYStart, 20.0F, 20.0F)).contains(mouseX, mouseY)) {
         this.field_146297_k.func_147108_a((GuiScreen)null);
      } else if (this.getSplitArea().contains(mouseX, mouseY)) {
         this.splitDragging = true;
      } else {
         Pair quad2 = this.determineQuadrant(mouseX, mouseY);
         if (((Number)quad2.getFirst()).intValue() != 0 && ((Number)quad2.getSecond()).intValue() != 0) {
            this.quad = quad2;
            this.resizeDragging = true;
         } else {
            for(CategoryElement ce : this.categoryElements) {
               var10000 = this.searchElement;
               Intrinsics.checkNotNull(var10000);
               String var7 = var10000.getSearchBox().func_146179_b();
               Intrinsics.checkNotNullExpressionValue(var7, "searchElement!!.searchBox.text");
               if (((CharSequence)var7).length() == 0) {
                  if (Intrinsics.areEqual((Object)ce.getName(), (Object)"INFO") && ce.getFocused()) {
                     if (MouseUtils.mouseWithinBounds(mouseX, mouseY, this.windowXStart + this.sideWidth + 15.0F, this.windowYStart + 45.0F, this.windowXStart + this.sideWidth + 45.0F, this.windowYStart + 75.0F)) {
                        HttpUtils.INSTANCE.openWebpage("https://www.discord.gg/E4AbJZsaXq");
                     }

                     if (MouseUtils.mouseWithinBounds(mouseX, mouseY, this.windowXStart + this.sideWidth + 15.0F, this.windowYStart + 77.0F, this.windowXStart + this.sideWidth + 45.0F, this.windowYStart + 107.0F)) {
                        HttpUtils.INSTANCE.openWebpage("https://www.github.com/shxp3");
                     }
                  }

                  if (Intrinsics.areEqual((Object)ce.getName(), (Object)"CONFIG") && ce.getFocused() && mouseButton == 0) {
                     float baseX = this.windowXStart + this.sideWidth + 15.0F;
                     float baseY = this.windowYStart + 50.0F;
                     if (MouseUtils.mouseWithinBounds(mouseX, mouseY, baseX + 202.0F, baseY + 25.0F, baseX + 212.0F, baseY + 35.0F)) {
                        if (this.reloadDelay.hasTimePassed(10000L)) {
                           this.reloadDelay.reset();
                           if (this.onlineConfigList != null) {
                              this.onlineConfigList = null;
                           }
                        } else {
                           this.tryAgain = true;
                        }
                     }

                     float saveX1 = this.windowXStart + this.sideWidth + 15.0F;
                     float saveY1 = this.windowYStart + 50.0F + 140.0F;
                     float saveX2 = saveX1 + 95.0F;
                     float saveY2 = saveY1 + 20.0F;
                     if (MouseUtils.mouseWithinBounds(mouseX, mouseY, saveX1, saveY1, saveX2, saveY2)) {
                        Desktop.getDesktop().open(CrossSine.INSTANCE.getFileManager().getConfigsDir());
                     }

                     float loadX1 = saveX1 + 105.0F;
                     float loadX2 = loadX1 + 95.0F;
                     float loadY2 = saveY1 + 20.0F;
                     if (MouseUtils.mouseWithinBounds(mouseX, mouseY, loadX1, saveY1, loadX2, loadY2)) {
                        SelectedConfig config = this.selectedConfig;
                        int var19 = 0;
                        if (!config.isOnline()) {
                           ConfigManager.load$default(CrossSine.INSTANCE.getConfigManager(), config.getName(), false, 2, (Object)null);
                        } else {
                           List var43 = this.onlineConfigList;
                           Intrinsics.checkNotNull(var43);
                           Iterator var22 = ((Iterable)var43).iterator();

                           while(true) {
                              if (var22.hasNext()) {
                                 Object var23 = var22.next();
                                 ConfigManager.OnlineConfig it = (ConfigManager.OnlineConfig)var23;
                                 int var25 = 0;
                                 if (!Intrinsics.areEqual((Object)it.getName(), (Object)config.getName())) {
                                    continue;
                                 }

                                 var44 = var23;
                                 break;
                              }

                              var44 = null;
                              break;
                           }

                           ConfigManager.OnlineConfig onlineConfig = (ConfigManager.OnlineConfig)var44;
                           if (onlineConfig != null) {
                              int toggleY1 = 0;
                              CrossSine.INSTANCE.getConfigManager().loadOnlineConfig(onlineConfig);
                           }
                        }

                        CrossSine.INSTANCE.getConfigManager().setNowConfig(this.selectedConfig.getName());
                     }

                     float deleteY1 = saveY2 + 5.0F;
                     float deleteY2 = deleteY1 + 20.0F;
                     if (MouseUtils.mouseWithinBounds(mouseX, mouseY, saveX1, deleteY1, saveX2, deleteY2)) {
                        File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.selectedConfig.getName(), ".json"));
                        if (file.exists()) {
                           file.delete();
                           if (Intrinsics.areEqual((Object)CrossSine.INSTANCE.getConfigManager().getNowConfig(), (Object)this.selectedConfig.getName())) {
                              CrossSine.INSTANCE.getConfigManager().setNowConfig("default");
                           }
                        }
                     }

                     float toggleX1 = this.windowXStart + this.sideWidth + 15.0F + 105.0F;
                     float toggleX2 = toggleX1 + 95.0F;
                     float toggleY1 = loadY2 + 5.0F;
                     float toggleY2 = toggleY1 + 20.0F;
                     if (MouseUtils.mouseWithinBounds(mouseX, mouseY, toggleX1, toggleY1, toggleX2, toggleY2) && Mouse.isButtonDown(0)) {
                        this.showOnlineConfigs = !this.showOnlineConfigs;
                     }
                  }
               }
            }

            float startY = 0.0F;
            startY = this.windowYStart + this.elementsStartY;
            var10000 = this.searchElement;
            Intrinsics.checkNotNull(var10000);
            var10000.handleMouseClick(mouseX, mouseY, mouseButton, this.windowXStart + this.getCategoryXOffset(), this.windowYStart + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, this.categoryElements);
            var10000 = this.searchElement;
            Intrinsics.checkNotNull(var10000);
            if (!var10000.isTyping()) {
               Iterable $this$forEach$iv = (Iterable)this.categoryElements;
               int $i$f$forEach = 0;

               for(Object element$iv : $this$forEach$iv) {
                  CategoryElement cat = (CategoryElement)element$iv;
                  int var35 = 0;
                  if (cat.getFocused()) {
                     cat.handleMouseClick(mouseX, mouseY, mouseButton, this.getWindowXStart() + this.getCategoryXOffset(), this.getWindowYStart() + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin);
                  }

                  if (MouseUtils.mouseWithinBounds(mouseX, mouseY, this.getWindowXStart(), startY, this.getWindowXStart() + this.getCategoryXOffset(), startY + this.elementHeight)) {
                     var10000 = this.searchElement;
                     Intrinsics.checkNotNull(var10000);
                     if (!var10000.isTyping()) {
                        this.categoryElements.forEach(ClickGui::mouseClicked$lambda-17$lambda-16);
                        cat.setFocused(true);
                        return;
                     }
                  }

                  startY += this.elementHeight;
               }
            }

         }
      }
   }

   protected void func_146286_b(int mouseX, int mouseY, int state) {
      if (this.moveDragging && this.getMoveAera().contains(mouseX, mouseY)) {
         this.moveDragging = false;
      } else {
         if (this.resizeDragging) {
            this.resizeDragging = false;
         }

         if (this.splitDragging) {
            this.splitDragging = false;
         }

         SearchElement var10000 = this.searchElement;
         Intrinsics.checkNotNull(var10000);
         var10000.handleMouseRelease(mouseX, mouseY, state, this.windowXStart + this.getCategoryXOffset(), this.windowYStart + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, this.categoryElements);
         var10000 = this.searchElement;
         Intrinsics.checkNotNull(var10000);
         if (!var10000.isTyping()) {
            Iterable $this$filter$iv = (Iterable)this.categoryElements;
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : $this$filter$iv) {
               CategoryElement it = (CategoryElement)element$iv$iv;
               int var12 = 0;
               if (it.getFocused()) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $this$filter$iv = (Iterable)((List)destination$iv$iv);
            $i$f$filter = 0;

            for(Object element$iv : $this$filter$iv) {
               CategoryElement cat = (CategoryElement)element$iv;
               int var17 = 0;
               cat.handleMouseRelease(mouseX, mouseY, state, this.getWindowXStart() + this.getCategoryXOffset(), this.getWindowYStart() + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin);
            }
         }

         super.func_146286_b(mouseX, mouseY, state);
      }
   }

   protected void func_73869_a(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1 && !this.cant && !this.textEditing && !this.keyBinding) {
         this.closed = true;
         if ((Boolean)ClickGUIModule.fastRenderValue.get()) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
         }

      } else {
         Iterable $this$filter$iv = (Iterable)this.categoryElements;
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            CategoryElement it = (CategoryElement)element$iv$iv;
            int var11 = 0;
            if (it.getFocused()) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $this$filter$iv = (Iterable)((List)destination$iv$iv);
         $i$f$filter = 0;

         for(Object element$iv : $this$filter$iv) {
            CategoryElement cat = (CategoryElement)element$iv;
            int var16 = 0;
            if (cat.handleKeyTyped(typedChar, keyCode)) {
               return;
            }
         }

         SearchElement var10000 = this.searchElement;
         Intrinsics.checkNotNull(var10000);
         if (!var10000.handleTyping(typedChar, keyCode, this.windowXStart + this.getCategoryXOffset(), this.windowYStart + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, this.categoryElements)) {
            super.func_73869_a(typedChar, keyCode);
         }
      }
   }

   public boolean func_73868_f() {
      return false;
   }

   private static final void drawFullSized$lambda_7/* $FF was: drawFullSized$lambda-7*/(ClickGui this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      List configs = CrossSine.INSTANCE.getConfigManager().fetchOnlineConfigs();
      this$0.onlineConfigList = configs;
   }

   private static final void mouseClicked$lambda_17$lambda_16/* $FF was: mouseClicked$lambda-17$lambda-16*/(CategoryElement e) {
      Intrinsics.checkNotNullParameter(e, "e");
      e.setFocused(false);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/ClickGui$Companion;", "", "()V", "instance", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/ClickGui;", "getInstance", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final ClickGui getInstance() {
         ClickGui var4;
         if (ClickGui.instance == null) {
            ClickGui it = new ClickGui();
            int var3 = 0;
            Companion var10000 = ClickGui.Companion;
            ClickGui.instance = it;
            var4 = it;
         } else {
            var4 = ClickGui.instance;
            Intrinsics.checkNotNull(var4);
         }

         return var4;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
