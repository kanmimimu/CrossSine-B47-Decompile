package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "DamageParticle",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001aH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/DamageParticle;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aliveTicks", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "blue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "customColor", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "green", "healthData", "Ljava/util/HashMap;", "", "particles", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/features/module/modules/visual/SingleParticle;", "red", "sizeValue", "onRender3d", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class DamageParticle extends Module {
   @NotNull
   private final HashMap healthData = new HashMap();
   @NotNull
   private final ArrayList particles = new ArrayList();
   @NotNull
   private final IntegerValue aliveTicks = new IntegerValue("AliveTicks", 20, 10, 50);
   @NotNull
   private final IntegerValue sizeValue = new IntegerValue("Size", 3, 1, 7);
   @NotNull
   private final BoolValue customColor = new BoolValue("CustomColor", false);
   @NotNull
   private final Value red = (new IntegerValue("Red", 255, 0, 255)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)DamageParticle.this.customColor.get();
      }
   });
   @NotNull
   private final Value green = (new IntegerValue("Green", 255, 0, 255)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)DamageParticle.this.customColor.get();
      }
   });
   @NotNull
   private final Value blue = (new IntegerValue("Blue", 255, 0, 255)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)DamageParticle.this.customColor.get();
      }
   });

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      ArrayList var2 = this.particles;
      synchronized(var2) {
         int var3 = 0;

         for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityLivingBase && EntityUtils.INSTANCE.isSelected(entity, true)) {
               Object var6 = this.healthData.getOrDefault(((EntityLivingBase)entity).func_145782_y(), ((EntityLivingBase)entity).func_110138_aP());
               Intrinsics.checkNotNullExpressionValue(var6, "healthData.getOrDefault(…tityId, entity.maxHealth)");
               float lastHealth = ((Number)var6).floatValue();
               ((Map)this.healthData).put(((EntityLivingBase)entity).func_145782_y(), ((EntityLivingBase)entity).func_110143_aJ());
               if (lastHealth != ((EntityLivingBase)entity).func_110143_aJ()) {
                  String prefix = !(Boolean)this.customColor.get() ? (lastHealth > ((EntityLivingBase)entity).func_110143_aJ() ? "§c" : "§a") : (lastHealth > ((EntityLivingBase)entity).func_110143_aJ() ? "-" : "+");
                  this.particles.add(new SingleParticle(Intrinsics.stringPlus(prefix, (new BigDecimal((double)Math.abs(lastHealth - ((EntityLivingBase)entity).func_110143_aJ()))).setScale(1, 4).doubleValue()), entity.field_70165_t - (double)0.5F + (double)(new Random(System.currentTimeMillis())).nextInt(5) * 0.1, ((EntityLivingBase)entity).func_174813_aQ().field_72338_b + (((EntityLivingBase)entity).func_174813_aQ().field_72337_e - ((EntityLivingBase)entity).func_174813_aQ().field_72338_b) / (double)2.0F, entity.field_70161_v - (double)0.5F + (double)(new Random(System.currentTimeMillis() + 1L)).nextInt(5) * 0.1));
               }
            }
         }

         ArrayList needRemove = new ArrayList();

         for(SingleParticle particle : this.particles) {
            int var14 = particle.getTicks();
            particle.setTicks(var14 + 1);
            if (particle.getTicks() > ((Number)this.aliveTicks.get()).intValue()) {
               needRemove.add(particle);
            }
         }

         for(SingleParticle particle : needRemove) {
            this.particles.remove(particle);
         }

         Unit var9 = Unit.INSTANCE;
      }
   }

   @EventTarget
   public final void onRender3d(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      ArrayList var2 = this.particles;
      synchronized(var2) {
         int var3 = 0;
         RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
         double size = ((Number)this.sizeValue.get()).doubleValue() * 0.01;

         for(SingleParticle particle : this.particles) {
            double n = particle.getPosX() - renderManager.field_78725_b;
            double n2 = particle.getPosY() - renderManager.field_78726_c;
            double n3 = particle.getPosZ() - renderManager.field_78723_d;
            GlStateManager.func_179094_E();
            GlStateManager.func_179088_q();
            GlStateManager.func_179136_a(1.0F, -1500000.0F);
            GlStateManager.func_179109_b((float)n, (float)n2, (float)n3);
            GlStateManager.func_179114_b(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
            float textY = MinecraftInstance.mc.field_71474_y.field_74320_O == 2 ? -1.0F : 1.0F;
            GlStateManager.func_179114_b(renderManager.field_78732_j, textY, 0.0F, 0.0F);
            GlStateManager.func_179139_a(-size, -size, size);
            GL11.glDepthMask(false);
            MinecraftInstance.mc.field_71466_p.func_175063_a(particle.getStr(), (float)(-(MinecraftInstance.mc.field_71466_p.func_78256_a(particle.getStr()) / 2)), (float)(-(MinecraftInstance.mc.field_71466_p.field_78288_b - 1)), (Boolean)this.customColor.get() ? (new Color(((Number)this.red.get()).intValue(), ((Number)this.green.get()).intValue(), ((Number)this.blue.get()).intValue())).getRGB() : 0);
            GL11.glColor4f(187.0F, 255.0F, 255.0F, 1.0F);
            GL11.glDepthMask(true);
            GlStateManager.func_179136_a(1.0F, 1500000.0F);
            GlStateManager.func_179113_r();
            GlStateManager.func_179121_F();
         }

         Unit var17 = Unit.INSTANCE;
      }
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.particles.clear();
      this.healthData.clear();
   }
}
