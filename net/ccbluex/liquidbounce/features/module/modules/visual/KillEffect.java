package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EntityKilledEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.block.Block;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "KillEffect",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/KillEffect;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockState", "", "lightingSoundValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "timesValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "displayEffectFor", "", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "onKilled", "event", "Lnet/ccbluex/liquidbounce/event/EntityKilledEvent;", "CrossSine"}
)
public final class KillEffect extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final IntegerValue timesValue;
   @NotNull
   private final Value lightingSoundValue;
   private final int blockState;

   public KillEffect() {
      String[] var1 = new String[]{"Lighting", "Blood", "Fire"};
      this.modeValue = new ListValue("Mode", var1, "Lighting");
      this.timesValue = new IntegerValue("Times", 1, 1, 10);
      this.lightingSoundValue = (new BoolValue("LightingSound", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return KillEffect.this.modeValue.equals("Lighting");
         }
      });
      this.blockState = Block.func_176210_f(Blocks.field_150451_bX.func_176223_P());
   }

   @EventTarget
   public final void onKilled(@NotNull EntityKilledEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.displayEffectFor(event.getTargetEntity());
   }

   private final void displayEffectFor(EntityLivingBase entity) {
      int var2 = ((Number)this.timesValue.get()).intValue();
      int var3 = 0;

      while(var3 < var2) {
         ++var3;
         int var6 = 0;
         String var7 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var7.hashCode()) {
            case 3143222:
               if (var7.equals("fire")) {
                  MinecraftInstance.mc.field_71452_i.func_178926_a((Entity)entity, EnumParticleTypes.LAVA);
               }
               break;
            case 93832698:
               if (!var7.equals("blood")) {
                  break;
               }

               byte var9 = 10;
               int var14 = 0;

               while(var14 < var9) {
                  ++var14;
                  int var12 = 0;
                  EffectRenderer var10000 = MinecraftInstance.mc.field_71452_i;
                  int var10001 = EnumParticleTypes.BLOCK_CRACK.func_179348_c();
                  double var10002 = entity.field_70165_t;
                  double var10003 = entity.field_70163_u + (double)(entity.field_70131_O / (float)2);
                  double var10004 = entity.field_70161_v;
                  double var10005 = entity.field_70159_w + (double)RandomUtils.INSTANCE.nextFloat(-0.5F, 0.5F);
                  double var10006 = entity.field_70181_x + (double)RandomUtils.INSTANCE.nextFloat(-0.5F, 0.5F);
                  double var10007 = entity.field_70179_y + (double)RandomUtils.INSTANCE.nextFloat(-0.5F, 0.5F);
                  int[] var13 = new int[]{this.blockState};
                  var10000.func_178927_a(var10001, var10002, var10003, var10004, var10005, var10006, var10007, var13);
               }
               break;
            case 991970060:
               if (var7.equals("lighting")) {
                  MinecraftInstance.mc.func_147114_u().func_147292_a(new S2CPacketSpawnGlobalEntity((Entity)(new EntityLightningBolt((World)MinecraftInstance.mc.field_71441_e, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v))));
                  if ((Boolean)this.lightingSoundValue.get()) {
                     MinecraftInstance.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("random.explode"), 1.0F));
                     MinecraftInstance.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("ambient.weather.thunder"), 1.0F));
                  }
               }
         }
      }

   }
}
