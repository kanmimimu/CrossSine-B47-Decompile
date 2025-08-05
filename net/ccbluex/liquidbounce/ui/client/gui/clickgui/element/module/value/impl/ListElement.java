package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ColorManager;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u001d2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J@\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0016J0\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/ListElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "", "saveValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "(Lnet/ccbluex/liquidbounce/features/value/ListValue;)V", "expandHeight", "", "expansion", "", "maxSubWidth", "getSaveValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "unusedValues", "", "getUnusedValues", "()Ljava/util/List;", "drawElement", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "Companion", "CrossSine"}
)
public final class ListElement extends ValueElement {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final ListValue saveValue;
   private float expandHeight;
   private boolean expansion;
   private final float maxSubWidth;
   @NotNull
   private static final ResourceLocation expanding = new ResourceLocation("crosssine/ui/clickgui/expand.png");

   public ListElement(@NotNull ListValue saveValue) {
      Intrinsics.checkNotNullParameter(saveValue, "saveValue");
      super(saveValue);
      this.saveValue = saveValue;
      String[] $this$map$iv = this.saveValue.getValues();
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.length));
      int $i$f$mapTo = 0;
      String[] var7 = $this$map$iv;
      int var8 = 0;
      int var9 = $this$map$iv.length;

      while(var8 < var9) {
         Object item$iv$iv = var7[var8];
         ++var8;
         int var12 = 0;
         destination$iv$iv.add(-Fonts.font40SemiBold.func_78256_a((String)item$iv$iv));
      }

      Object var10001 = (Integer)CollectionsKt.firstOrNull(CollectionsKt.sorted((Iterable)((List)destination$iv$iv)));
      if (var10001 == null) {
         var10001 = 0.0F;
      }

      this.maxSubWidth = -((Number)var10001).floatValue() + 20.0F;
   }

   @NotNull
   public final ListValue getSaveValue() {
      return this.saveValue;
   }

   public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(bgColor, "bgColor");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      this.expandHeight = AnimHelperKt.animSmooth(this.expandHeight, this.expansion ? 16.0F * ((float)this.saveValue.getValues().length - 1.0F) : 0.0F, 0.5F);
      float percent = this.expandHeight / (16.0F * ((float)this.saveValue.getValues().length - 1.0F));
      Fonts.font40SemiBold.func_175063_a(this.getValue().getName(), x + 10.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, -1);
      RenderUtils.drawBloomRoundedRect(x + width - 18.0F - this.maxSubWidth, y + 2.0F, x + width - 10.0F, y + 18.0F + this.expandHeight, 4.0F, 2.5F, ColorManager.INSTANCE.getButton(), RenderUtils.ShaderBloom.BOTH);
      GlStateManager.func_179117_G();
      GL11.glPushMatrix();
      GL11.glTranslatef(x + width - 20.0F, y + 10.0F, 0.0F);
      GL11.glPushMatrix();
      GL11.glRotatef(180.0F * percent, 0.0F, 0.0F, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderUtils.drawImage((ResourceLocation)expanding, -4, -4, 8, 8);
      GL11.glPopMatrix();
      GL11.glPopMatrix();
      Fonts.font40SemiBold.func_175063_a((String)this.getValue().get(), x + width - 14.0F - this.maxSubWidth, y + 6.0F, -1);
      GL11.glPushMatrix();
      GlStateManager.func_179109_b(x + width - 14.0F - this.maxSubWidth, y + 7.0F, 0.0F);
      GlStateManager.func_179152_a(percent, percent, percent);
      float vertHeight = 0.0F;
      if (percent > 0.0F) {
         for(String subV : this.getUnusedValues()) {
            Fonts.font40SemiBold.func_175063_a(subV, 0.0F, (16.0F + vertHeight) * percent - 1.0F, (new Color(0.5F, 0.5F, 0.5F, RangesKt.coerceIn(percent, 0.0F, 1.0F))).getRGB());
            vertHeight += 16.0F;
         }
      }

      GL11.glPopMatrix();
      this.setValueHeight(20.0F + this.expandHeight);
      return this.getValueHeight();
   }

   public void onClick(int mouseX, int mouseY, float x, float y, float width) {
      if (this.isDisplayable() && MouseUtils.mouseWithinBounds(mouseX, mouseY, x, y + 2.0F, x + width, y + 18.0F)) {
         this.expansion = !this.expansion;
      }

      if (this.expansion) {
         float vertHeight = 0.0F;

         for(String subV : this.getUnusedValues()) {
            if (MouseUtils.mouseWithinBounds(mouseX, mouseY, x + width - 14.0F - this.maxSubWidth, y + 18.0F + vertHeight, x + width - 10.0F, y + 34.0F + vertHeight)) {
               this.getValue().set(subV);
               this.expansion = false;
               break;
            }

            vertHeight += 16.0F;
         }
      }

   }

   @NotNull
   public final List getUnusedValues() {
      Object[] $this$filter$iv = this.saveValue.getValues();
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;
      Object var6 = $this$filter$iv;
      int var7 = 0;
      int var8 = $this$filter$iv.length;

      while(var7 < var8) {
         Object element$iv$iv = ((Object[])var6)[var7];
         ++var7;
         int var11 = 0;
         if (!Intrinsics.areEqual(element$iv$iv, this.getValue().get())) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      return (List)destination$iv$iv;
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/ListElement$Companion;", "", "()V", "expanding", "Lnet/minecraft/util/ResourceLocation;", "getExpanding", "()Lnet/minecraft/util/ResourceLocation;", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final ResourceLocation getExpanding() {
         return ListElement.expanding;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
