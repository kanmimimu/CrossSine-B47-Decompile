package net.ccbluex.liquidbounce.injection.forge;

import java.util.Map;
import net.ccbluex.liquidbounce.injection.transformers.ForgeNetworkTransformer;
import net.ccbluex.liquidbounce.script.remapper.injection.transformers.AbstractJavaLinkerTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

@MCVersion("1.8.9")
public class TransformerLoader implements IFMLLoadingPlugin {
   public TransformerLoader() {
      MixinBootstrap.init();
      Mixins.addConfiguration("mixins.crosssine.json");
      MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
   }

   public String[] getASMTransformerClass() {
      return new String[]{ForgeNetworkTransformer.class.getName(), AbstractJavaLinkerTransformer.class.getName()};
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map data) {
   }

   public String getAccessTransformerClass() {
      return null;
   }
}
