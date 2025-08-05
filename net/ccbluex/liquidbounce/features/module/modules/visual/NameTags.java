package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "NameTags",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0007J\u0018\u0010'\u001a\u00020$2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/NameTags;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "backgroundColorValue", "Ljava/awt/Color;", "borderColorValue", "borderValue", "clearNamesValue", "distanceValue", "enchantValue", "entityKeep", "", "fontShadowValue", "fontValue", "Lnet/ccbluex/liquidbounce/features/value/FontValue;", "healthBarValue", "healthValue", "inventoryBackground", "Lnet/minecraft/util/ResourceLocation;", "jelloAlphaValue", "", "jelloColorValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onlyTarget", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "pingValue", "potionValue", "scaleValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "targetTicks", "translateY", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "renderNameTag", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "tag", "CrossSine"}
)
public final class NameTags extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final Value healthValue;
   @NotNull
   private final Value pingValue;
   @NotNull
   private final Value healthBarValue;
   @NotNull
   private final Value distanceValue;
   @NotNull
   private final Value armorValue;
   @NotNull
   private final Value enchantValue;
   @NotNull
   private final Value potionValue;
   @NotNull
   private final Value clearNamesValue;
   @NotNull
   private final FontValue fontValue;
   @NotNull
   private final Value borderValue;
   @NotNull
   private final Value fontShadowValue;
   @NotNull
   private final Value jelloColorValue;
   @NotNull
   private final Value jelloAlphaValue;
   @NotNull
   private final FloatValue scaleValue;
   @NotNull
   private final BoolValue onlyTarget;
   @NotNull
   private final FloatValue translateY;
   @NotNull
   private final Value backgroundColorValue;
   @NotNull
   private final Value borderColorValue;
   private int targetTicks;
   @NotNull
   private String entityKeep;
   @NotNull
   private final ResourceLocation inventoryBackground;

   public NameTags() {
      String[] var1 = new String[]{"CrossSine", "Simple", "Liquid", "Jello"};
      this.modeValue = new ListValue("Mode", var1, "Liquid");
      this.healthValue = (new BoolValue("Health", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.pingValue = (new BoolValue("Ping", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.healthBarValue = (new BoolValue("Bar", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.distanceValue = (new BoolValue("Distance", false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.armorValue = (new BoolValue("Armor", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.enchantValue = (new BoolValue("Enchant", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.potionValue = (new BoolValue("Potions", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.clearNamesValue = (new BoolValue("ClearNames", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      GameFontRenderer var2 = Fonts.font40;
      Intrinsics.checkNotNullExpressionValue(var2, "font40");
      this.fontValue = new FontValue("Font", var2);
      this.borderValue = (new BoolValue("Border", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.fontShadowValue = (new BoolValue("Shadow", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.jelloColorValue = (new BoolValue("JelloHPColor", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Jello");
         }
      });
      this.jelloAlphaValue = (new IntegerValue("JelloAlpha", 170, 0, 255)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Jello");
         }
      });
      this.scaleValue = new FloatValue("Scale", 1.0F, 1.0F, 4.0F);
      this.onlyTarget = new BoolValue("OnlyTarget", false);
      this.translateY = new FloatValue("TanslateY", 0.55F, -2.0F, 2.0F);
      this.backgroundColorValue = (new ColorValue("Background-Color", new Color(0, 0, 0), true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.borderColorValue = (new ColorValue("Border-Color", new Color(0, 0, 0), false, 4, (DefaultConstructorMarker)null)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return NameTags.this.modeValue.equals("Liquid");
         }
      });
      this.entityKeep = "yes zywl";
      this.inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");

      for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
         EntityUtils var10000 = EntityUtils.INSTANCE;
         Intrinsics.checkNotNullExpressionValue(entity, "entity");
         if (var10000.isSelected(entity, false)) {
            this.renderNameTag((EntityLivingBase)entity, Intrinsics.stringPlus(!this.modeValue.equals("Liquid") && AntiBot.isBot((EntityLivingBase)entity) ? "§e" : "", (Boolean)this.clearNamesValue.get() ? ((EntityLivingBase)entity).func_70005_c_() : entity.func_145748_c_().func_150260_c()));
         }
      }

   }

   private final void renderNameTag(EntityLivingBase entity, String tag) {
      if (!(Boolean)this.onlyTarget.get() || Intrinsics.areEqual((Object)entity, (Object)CrossSine.INSTANCE.getCombatManager().getTarget()) || Intrinsics.areEqual((Object)entity.func_70005_c_(), (Object)this.entityKeep)) {
         if ((Boolean)this.onlyTarget.get() && Intrinsics.areEqual((Object)entity, (Object)CrossSine.INSTANCE.getCombatManager().getTarget())) {
            String var33 = entity.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(var33, "entity.getName()");
            this.entityKeep = var33;
            int var34 = this.targetTicks++;
            if (this.targetTicks >= 5) {
               this.targetTicks = 4;
            }
         } else if ((Boolean)this.onlyTarget.get() && CrossSine.INSTANCE.getCombatManager().getTarget() == null) {
            int var3 = this.targetTicks;
            this.targetTicks = var3 + -1;
            if (this.targetTicks <= -1) {
               this.targetTicks = 0;
               this.entityKeep = "fdp is skidded";
            }
         }

         if (!(Boolean)this.onlyTarget.get() || this.targetTicks != 0) {
            FontRenderer fontRenderer = (FontRenderer)this.fontValue.get();
            GL11.glPushMatrix();
            RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
            Timer timer = MinecraftInstance.mc.field_71428_T;
            GL11.glTranslated(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c + (double)entity.func_70047_e() + (double)((Number)this.translateY.get()).floatValue(), entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d);
            GL11.glRotatef(-MinecraftInstance.mc.func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(MinecraftInstance.mc.func_175598_ae().field_78732_j, 1.0F, 0.0F, 0.0F);
            float distance = MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)entity) / 4.0F;
            if (distance < 1.0F) {
               distance = 1.0F;
            }

            float scale = distance / 150.0F * ((Number)this.scaleValue.get()).floatValue();
            int[] var8 = new int[]{2896, 2929};
            RenderUtils.disableGlCap(var8);
            RenderUtils.enableGlCap(3042);
            GL11.glBlendFunc(770, 771);
            Intrinsics.checkNotNullExpressionValue(healthText, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (healthText) {
               case "liquid":
                  boolean bot = AntiBot.isBot(entity);
                  healthText = bot ? "§3" : (entity.func_82150_aj() ? "§6" : (entity.func_70093_af() ? "§4" : "§7"));
                  int ping = EntityExtensionKt.getPing(entity);
                  String distanceText = (Boolean)this.distanceValue.get() ? "§7 [§a" + MathKt.roundToInt(MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)entity)) + "§7]" : "";
                  String pingText = (Boolean)this.pingValue.get() && entity instanceof EntityPlayer ? " §7[" + (ping > 200 ? "§c" : (ping > 100 ? "§e" : "§a")) + ping + "ms§7]" : "";
                  String healthText = (Boolean)this.healthValue.get() ? "§7 §f" + (int)entity.func_110143_aJ() + "§c❤§7" : "";
                  String botText = bot ? " §7[§6§lBot§7]" : "";
                  String text = distanceText + pingText + healthText + tag + healthText + botText;
                  GL11.glScalef(-scale, -scale, scale);
                  float width = (float)fontRenderer.func_78256_a(text) * 0.5F;
                  float dist = width + 4.0F - (-width - 2.0F);
                  GL11.glDisable(3553);
                  GL11.glEnable(3042);
                  Color bgColor = (Color)this.backgroundColorValue.get();
                  Color borderColor = (Color)this.borderColorValue.get();
                  if ((Boolean)this.borderValue.get()) {
                     RenderUtils.quickDrawBorderedRect(-width - 2.0F, -2.0F, width + 4.0F, (float)fontRenderer.field_78288_b + 2.0F + ((Boolean)this.healthBarValue.get() ? 2.0F : 0.0F), 2.0F, borderColor.getRGB(), bgColor.getRGB());
                  } else {
                     RenderUtils.quickDrawRect(-width - 2.0F, -2.0F, width + 4.0F, (float)fontRenderer.field_78288_b + 2.0F + ((Boolean)this.healthBarValue.get() ? 2.0F : 0.0F), bgColor.getRGB());
                  }

                  if ((Boolean)this.healthBarValue.get()) {
                     RenderUtils.quickDrawRect(-width - 2.0F, (float)fontRenderer.field_78288_b + 3.0F, -width - 2.0F + dist, (float)fontRenderer.field_78288_b + 4.0F, (new Color(10, 155, 10)).getRGB());
                     RenderUtils.quickDrawRect(-width - 2.0F, (float)fontRenderer.field_78288_b + 3.0F, -width - 2.0F + dist * RangesKt.coerceIn(entity.func_110143_aJ() / entity.func_110138_aP(), 0.0F, 1.0F), (float)fontRenderer.field_78288_b + 4.0F, (new Color(10, 255, 10)).getRGB());
                  }

                  GL11.glEnable(3553);
                  fontRenderer.func_175065_a(text, 1.0F + -width, Intrinsics.areEqual((Object)fontRenderer, (Object)Fonts.minecraftFont) ? 1.0F : 1.5F, 16777215, (Boolean)this.fontShadowValue.get());
                  boolean foundPotion = false;
                  if ((Boolean)this.potionValue.get() && entity instanceof EntityPlayer) {
                     Collection var70 = entity.func_70651_bq();
                     if (var70 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Collection<net.minecraft.potion.PotionEffect>");
                     }

                     Iterable $this$map$iv = (Iterable)var70;
                     int $i$f$map = 0;
                     Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
                     int $i$f$mapTo = 0;

                     for(Object item$iv$iv : $this$map$iv) {
                        PotionEffect it = (PotionEffect)item$iv$iv;
                        int var31 = 0;
                        destination$iv$iv.add(Potion.field_76425_a[it.func_76456_a()]);
                     }

                     $this$map$iv = (Iterable)((List)destination$iv$iv);
                     $i$f$map = 0;
                     destination$iv$iv = (Collection)(new ArrayList());
                     $i$f$mapTo = 0;

                     for(Object element$iv$iv : $this$map$iv) {
                        Potion it = (Potion)element$iv$iv;
                        int var67 = 0;
                        if (it.func_76400_d()) {
                           destination$iv$iv.add(element$iv$iv);
                        }
                     }

                     List potions = (List)destination$iv$iv;
                     if (!potions.isEmpty()) {
                        foundPotion = true;
                        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.func_179140_f();
                        GlStateManager.func_179098_w();
                        int minX = potions.size() * -20 / 2;
                        $i$f$map = 0;
                        GL11.glPushMatrix();
                        GlStateManager.func_179091_B();

                        for(Potion potion : potions) {
                           GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
                           MinecraftInstance.mc.func_110434_K().func_110577_a(this.inventoryBackground);
                           $i$f$mapTo = potion.func_76392_e();
                           RenderUtils.drawTexturedModalRect(minX + $i$f$map * 20, -22, 0 + $i$f$mapTo % 8 * 18, 198 + $i$f$mapTo / 8 * 18, 18, 18, 0.0F);
                           ++$i$f$map;
                        }

                        GlStateManager.func_179101_C();
                        GL11.glPopMatrix();
                        GlStateManager.func_179141_d();
                        GlStateManager.func_179084_k();
                        GlStateManager.func_179098_w();
                     }
                  }

                  if ((Boolean)this.armorValue.get() && entity instanceof EntityPlayer) {
                     int var52 = 0;

                     while(var52 < 5) {
                        int index = var52++;
                        if (entity.func_71124_b(index) != null) {
                           MinecraftInstance.mc.func_175599_af().field_77023_b = -147.0F;
                           MinecraftInstance.mc.func_175599_af().func_180450_b(entity.func_71124_b(index), -50 + index * 20, (Boolean)this.potionValue.get() && foundPotion ? -42 : -22);
                        }
                     }

                     GlStateManager.func_179141_d();
                     GlStateManager.func_179084_k();
                     GlStateManager.func_179098_w();
                  }

                  if ((Boolean)this.enchantValue.get() && entity instanceof EntityPlayer) {
                     GL11.glPushMatrix();
                     int var53 = 0;

                     while(var53 < 5) {
                        int index = var53++;
                        if (entity.func_71124_b(index) != null) {
                           MinecraftInstance.mc.func_175599_af().func_175030_a(MinecraftInstance.mc.field_71466_p, entity.func_71124_b(index), -50 + index * 20, (Boolean)this.potionValue.get() && foundPotion ? -42 : -22);
                           RenderUtils.drawExhiEnchants(entity.func_71124_b(index), -50.0F + (float)index * 20.0F, (Boolean)this.potionValue.get() && foundPotion ? -42.0F : -22.0F);
                        }
                     }

                     GL11.glDisable(2896);
                     GL11.glDisable(2929);
                     GL11.glEnable(2848);
                     GL11.glEnable(3042);
                     GL11.glBlendFunc(770, 771);
                     GL11.glPopMatrix();
                  }
                  break;
               case "simple":
                  float healthPercent = RangesKt.coerceAtMost(entity.func_110143_aJ() / entity.func_110138_aP(), 1.0F);
                  int width = RangesKt.coerceAtLeast(fontRenderer.func_78256_a(tag), 30) / 2;
                  float maxWidth = (float)(width * 2) + 12.0F;
                  GL11.glScalef(-scale * (float)2, -scale * (float)2, scale * (float)2);
                  RenderUtils.drawRect((float)(-width) - 6.0F, (float)(-fontRenderer.field_78288_b) * 1.7F, (float)width + 6.0F, -2.0F, new Color(0, 0, 0, ((Number)this.jelloAlphaValue.get()).intValue()));
                  RenderUtils.drawRect((float)(-width) - 6.0F, -2.0F, (float)(-width) - 6.0F + maxWidth * healthPercent, 0.0F, ColorUtils.INSTANCE.healthColor(entity.func_110143_aJ(), entity.func_110138_aP(), ((Number)this.jelloAlphaValue.get()).intValue()));
                  RenderUtils.drawRect((float)(-width) - 6.0F + maxWidth * healthPercent, -2.0F, (float)width + 6.0F, 0.0F, new Color(0, 0, 0, ((Number)this.jelloAlphaValue.get()).intValue()));
                  fontRenderer.func_78276_b(tag, (int)((float)(-fontRenderer.func_78256_a(tag)) * 0.5F), (int)((float)(-fontRenderer.field_78288_b) * 1.4F), Color.WHITE.getRGB());
                  break;
               case "jello":
                  Color hpBarColor = new Color(255, 255, 255, ((Number)this.jelloAlphaValue.get()).intValue());
                  healthText = entity.func_145748_c_().func_150260_c();
                  if ((Boolean)this.jelloColorValue.get()) {
                     Intrinsics.checkNotNullExpressionValue(healthText, "name");
                     if (StringsKt.startsWith$default(healthText, "§", false, 2, (Object)null)) {
                        ColorUtils var69 = ColorUtils.INSTANCE;
                        String healthText = healthText.substring(1, 2);
                        Intrinsics.checkNotNullExpressionValue(healthText, "this as java.lang.String…ing(startIndex, endIndex)");
                        hpBarColor = var69.colorCode(healthText, ((Number)this.jelloAlphaValue.get()).intValue());
                     }
                  }

                  Color bgColor = new Color(20, 20, 20, ((Number)this.jelloAlphaValue.get()).intValue());
                  int width = fontRenderer.func_78256_a(tag) / 2;
                  float maxWidth = (float)width + 4.0F - ((float)(-width) - 4.0F);
                  float healthPercent = entity.func_110143_aJ() / entity.func_110138_aP();
                  GL11.glScalef(-scale * (float)2, -scale * (float)2, scale * (float)2);
                  RenderUtils.drawRect((float)(-width) - 4.0F, (float)(-fontRenderer.field_78288_b) * 3.0F, (float)width + 4.0F, -3.0F, bgColor);
                  if (healthPercent > 1.0F) {
                     healthPercent = 1.0F;
                  }

                  RenderUtils.drawRect((float)(-width) - 4.0F, -3.0F, (float)(-width) - 4.0F + maxWidth * healthPercent, 0.0F, hpBarColor);
                  RenderUtils.drawRect((float)(-width) - 4.0F + maxWidth * healthPercent, -3.0F, (float)width + 4.0F, 0.0F, bgColor);
                  fontRenderer.func_78276_b(tag, -width, -fontRenderer.field_78288_b * 2 - 4, Color.WHITE.getRGB());
                  GL11.glScalef(0.5F, 0.5F, 0.5F);
                  fontRenderer.func_78276_b(Intrinsics.stringPlus("Health: ", (int)entity.func_110143_aJ()), -width * 2, -fontRenderer.field_78288_b * 2, Color.WHITE.getRGB());
                  break;
               case "crosssine":
                  GameFontRenderer var10000 = Fonts.font40SemiBold;
                  healthText = entity.func_145748_c_().func_150254_d();
                  Intrinsics.checkNotNullExpressionValue(healthText, "entity.displayName.formattedText");
                  int width = RangesKt.coerceAtLeast(var10000.func_78256_a(healthText), 30) / 2;
                  healthText = "" + ' ' + (int)entity.func_110143_aJ() + '❤';
                  int healthWidth = Fonts.font40SemiBold.func_78256_a(healthText);
                  GL11.glScalef(-scale * (float)2, -scale * (float)2, scale * (float)2);
                  RenderUtils.drawBloomRoundedRect((float)(-width) - 6.0F, (float)(-Fonts.font40SemiBold.field_78288_b) * 2.0F, (float)width + 6.0F, -2.0F, 4.5F, 1.3F, new Color(0, 0, 0, 100), RenderUtils.ShaderBloom.BLOOMONLY);
                  RenderUtils.drawBloomRoundedRect((float)width + 7.0F, (float)(-Fonts.font40SemiBold.field_78288_b) * 2.0F, (float)width + 6.0F + (float)healthWidth + 10.0F, -2.0F, 4.5F, 1.3F, new Color(0, 0, 0, 100), RenderUtils.ShaderBloom.BLOOMONLY);
                  var10000 = Fonts.font40SemiBold;
                  String var10001 = entity.func_145748_c_().func_150254_d();
                  GameFontRenderer var10002 = Fonts.font40SemiBold;
                  String var12 = entity.func_145748_c_().func_150254_d();
                  Intrinsics.checkNotNullExpressionValue(var12, "entity.displayName.formattedText");
                  var10000.func_78276_b(var10001, (int)((float)(-var10002.func_78256_a(var12)) * 0.5F), (int)((float)(-Fonts.font40SemiBold.field_78288_b) * 1.4F), Color.WHITE.getRGB());
                  Fonts.font40SemiBold.func_78276_b(healthText, (int)((float)width + 6.0F + 5.0F), (int)((float)(-Fonts.font40SemiBold.field_78288_b) * 1.4F), Color.WHITE.getRGB());
            }

            RenderUtils.resetCaps();
            GlStateManager.func_179117_G();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }
   }
}
