package net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions;

import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.ui.client.gui.ClickGUIModule;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001\u001a\u001a\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001¨\u0006\u0007"},
   d2 = {"animLinear", "", "speed", "min", "max", "animSmooth", "target", "CrossSine"}
)
public final class AnimHelperKt {
   public static final float animSmooth(float $this$animSmooth, float target, float speed) {
      return (Boolean)ClickGUIModule.fastRenderValue.get() ? target : AnimationUtils.animate(target, $this$animSmooth, speed * (float)RenderUtils.deltaTime * 0.025F);
   }

   public static final float animLinear(float $this$animLinear, float speed, float min, float max) {
      return (Boolean)ClickGUIModule.fastRenderValue.get() ? (speed < 0.0F ? min : max) : RangesKt.coerceIn($this$animLinear + speed, min, max);
   }
}
