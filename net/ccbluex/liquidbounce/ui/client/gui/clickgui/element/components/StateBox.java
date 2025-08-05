package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.gui.ClickGUIModule;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/StateBox;", "", "()V", "name", "", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "radius", "", "getRadius", "()F", "setRadius", "(F)V", "state", "", "getState", "()Z", "setState", "(Z)V", "width", "getWidth", "setWidth", "onDraw", "", "x", "y", "CrossSine"}
)
public final class StateBox {
   @NotNull
   private String name = "";
   private boolean state;
   private float width;
   private float radius;

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final void setName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.name = var1;
   }

   public final boolean getState() {
      return this.state;
   }

   public final void setState(boolean var1) {
      this.state = var1;
   }

   public final float getWidth() {
      return this.width;
   }

   public final void setWidth(float var1) {
      this.width = var1;
   }

   public final float getRadius() {
      return this.radius;
   }

   public final void setRadius(float var1) {
      this.radius = var1;
   }

   public final void onDraw(float x, float y) {
      this.width = (Boolean)ClickGUIModule.fastRenderValue.get() ? x + (this.state ? (float)Fonts.font40SemiBold.func_78256_a(this.name) + 13.0F : 7.0F) : AnimationUtils.animate(x + (this.state ? (float)Fonts.font40SemiBold.func_78256_a(this.name) + 13.0F : 7.0F), this.width, 0.55F * (float)RenderUtils.deltaTime * 0.025F);
      this.radius = (Boolean)ClickGUIModule.fastRenderValue.get() ? (this.state ? 4.0F : 0.0F) : AnimationUtils.animate(this.state ? 4.0F : 0.0F, this.radius, 0.1F * (float)RenderUtils.deltaTime * 0.025F);
      RenderUtils.drawBloomRoundedRect(x + 7.0F, y + 6.0F, this.width, y + 6.0F + (float)Fonts.font40SemiBold.field_78288_b, this.radius, 1.5F, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 150, false, 4, (Object)null), RenderUtils.ShaderBloom.BOTH);
   }
}
