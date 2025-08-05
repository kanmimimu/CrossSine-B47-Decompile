package net.ccbluex.liquidbounce.injection.forge.mixins.accessors;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SideOnly(Side.CLIENT)
@Mixin({EntityPlayerSP.class})
public interface IAccessorEntityPlayerSP {
   @Accessor("lastReportedYaw")
   float getLastReportedYaw();
}
