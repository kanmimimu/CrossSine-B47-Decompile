package net.ccbluex.liquidbounce.injection.forge.mixins.accessors;

import com.google.common.cache.LoadingCache;
import net.minecraftforge.client.MinecraftForgeClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({MinecraftForgeClient.class})
public interface MinecraftForgeClientAccessor {
   @Accessor(
      remap = false
   )
   static LoadingCache getRegionCache() {
      throw new AssertionError();
   }
}
