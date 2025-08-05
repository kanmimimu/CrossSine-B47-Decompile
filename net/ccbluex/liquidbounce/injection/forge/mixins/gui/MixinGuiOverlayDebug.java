package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.List;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GuiOverlayDebug.class})
public class MixinGuiOverlayDebug {
   @Inject(
      method = {"getDebugInfoRight"},
      at = {@At("TAIL")}
   )
   public void drawVersion(CallbackInfoReturnable cir) {
      ProtocolVersion version = ProtocolBase.getManager().getTargetVersion();
      ((List)cir.getReturnValue()).add("");
      if (!MinecraftInstance.mc.func_71387_A()) {
         ((List)cir.getReturnValue()).add("Protocol: " + version.getName());
      } else {
         ((List)cir.getReturnValue()).add("Protocol: 1.8.x");
      }

   }
}
