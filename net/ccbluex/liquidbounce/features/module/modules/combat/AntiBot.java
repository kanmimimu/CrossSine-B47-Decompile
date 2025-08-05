package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.NetworkExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S38PacketPlayerListItem.Action;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AntiBot",
   category = ModuleCategory.COMBAT,
   array = false
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010%\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010A\u001a\u00020BH\u0002J\u0010\u0010C\u001a\u00020\n2\u0006\u0010D\u001a\u00020EH\u0007J\u0010\u0010F\u001a\u00020B2\u0006\u0010G\u001a\u00020HH\u0007J\b\u0010I\u001a\u00020BH\u0016J\u0010\u0010J\u001a\u00020B2\u0006\u0010K\u001a\u00020LH\u0007J\u0010\u0010M\u001a\u00020B2\u0006\u0010K\u001a\u00020NH\u0007J\u0010\u0010O\u001a\u00020B2\u0006\u0010K\u001a\u00020PH\u0007J\u0018\u0010Q\u001a\u00020B2\u0006\u0010D\u001a\u00020R2\u0006\u0010S\u001a\u00020\nH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050&X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0&X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00100\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00108\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00190\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006T"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AntiBot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "air", "", "", "airValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "alwaysInRadiusRemoveValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "alwaysInRadiusValue", "alwaysInRadiusWithTicksCheckValue", "alwaysRadiusValue", "", "armorValue", "colorValue", "czechHekGMCheckValue", "czechHekPingCheckValue", "czechHekValue", "debugValue", "derpValue", "duplicate", "Ljava/util/UUID;", "duplicateCompareModeValue", "", "duplicateInTabValue", "duplicateInWorldValue", "entityIDValue", "fastDamageTicksValue", "fastDamageValue", "ground", "groundValue", "hasRemovedEntities", "healthValue", "hiddenNameValue", "hitted", "invalidGround", "", "invalidGroundValue", "invisible", "lastDamage", "lastDamageVl", "livingTimeTicksValue", "livingTimeValue", "needHitValue", "noClip", "noClipValue", "notAlwaysInRadius", "pingValue", "regex", "Lkotlin/text/Regex;", "removeFromWorld", "removeIntervalValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "reusedEntityIdValue", "spawnInCombat", "spawnInCombatValue", "swing", "swingValue", "tabModeValue", "tabValue", "validNameValue", "wasAdded", "wasInvisibleValue", "clearAll", "", "isBot", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "onAttack", "e", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "processEntityMove", "Lnet/minecraft/entity/Entity;", "onGround", "CrossSine"}
)
public final class AntiBot extends Module {
   @NotNull
   public static final AntiBot INSTANCE = new AntiBot();
   @NotNull
   private static final BoolValue tabValue = new BoolValue("Tab", true);
   @NotNull
   private static final Value tabModeValue;
   @NotNull
   private static final BoolValue entityIDValue;
   @NotNull
   private static final BoolValue colorValue;
   @NotNull
   private static final BoolValue livingTimeValue;
   @NotNull
   private static final Value livingTimeTicksValue;
   @NotNull
   private static final BoolValue groundValue;
   @NotNull
   private static final BoolValue airValue;
   @NotNull
   private static final BoolValue invalidGroundValue;
   @NotNull
   private static final BoolValue swingValue;
   @NotNull
   private static final BoolValue healthValue;
   @NotNull
   private static final BoolValue derpValue;
   @NotNull
   private static final BoolValue wasInvisibleValue;
   @NotNull
   private static final BoolValue validNameValue;
   @NotNull
   private static final BoolValue hiddenNameValue;
   @NotNull
   private static final BoolValue armorValue;
   @NotNull
   private static final BoolValue pingValue;
   @NotNull
   private static final BoolValue needHitValue;
   @NotNull
   private static final BoolValue noClipValue;
   @NotNull
   private static final BoolValue czechHekValue;
   @NotNull
   private static final Value czechHekPingCheckValue;
   @NotNull
   private static final Value czechHekGMCheckValue;
   @NotNull
   private static final BoolValue reusedEntityIdValue;
   @NotNull
   private static final BoolValue spawnInCombatValue;
   @NotNull
   private static final BoolValue duplicateInWorldValue;
   @NotNull
   private static final BoolValue duplicateInTabValue;
   @NotNull
   private static final Value duplicateCompareModeValue;
   @NotNull
   private static final BoolValue fastDamageValue;
   @NotNull
   private static final Value fastDamageTicksValue;
   @NotNull
   private static final BoolValue removeFromWorld;
   @NotNull
   private static final IntegerValue removeIntervalValue;
   @NotNull
   private static final BoolValue debugValue;
   @NotNull
   private static final BoolValue alwaysInRadiusValue;
   @NotNull
   private static final Value alwaysRadiusValue;
   @NotNull
   private static final Value alwaysInRadiusRemoveValue;
   @NotNull
   private static final Value alwaysInRadiusWithTicksCheckValue;
   @NotNull
   private static final List ground;
   @NotNull
   private static final List air;
   @NotNull
   private static final Map invalidGround;
   @NotNull
   private static final List swing;
   @NotNull
   private static final List invisible;
   @NotNull
   private static final List hitted;
   @NotNull
   private static final List spawnInCombat;
   @NotNull
   private static final List notAlwaysInRadius;
   @NotNull
   private static final Map lastDamage;
   @NotNull
   private static final Map lastDamageVl;
   @NotNull
   private static final List duplicate;
   @NotNull
   private static final List noClip;
   @NotNull
   private static final List hasRemovedEntities;
   @NotNull
   private static final Regex regex;
   private static boolean wasAdded;

   private AntiBot() {
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71441_e != null) {
         if ((Boolean)removeFromWorld.get() && MinecraftInstance.mc.field_71439_g.field_70173_aa > 0 && MinecraftInstance.mc.field_71439_g.field_70173_aa % ((Number)removeIntervalValue.get()).intValue() == 0) {
            List ent = (List)(new ArrayList());

            for(EntityPlayer entity : MinecraftInstance.mc.field_71441_e.field_73010_i) {
               if (entity != MinecraftInstance.mc.field_71439_g) {
                  Intrinsics.checkNotNullExpressionValue(entity, "entity");
                  if (isBot((EntityLivingBase)entity)) {
                     ent.add(entity);
                  }
               }
            }

            if (ent.isEmpty()) {
               return;
            }

            for(EntityPlayer e : ent) {
               MinecraftInstance.mc.field_71441_e.func_72900_e((Entity)e);
               if ((Boolean)debugValue.get()) {
                  ClientUtils.INSTANCE.displayChatMessage("§7[§a§lAnti Bot§7] §fRemoved §r" + e.func_70005_c_() + " §fdue to it being a bot.");
               }
            }
         }

      }
   }

   @JvmStatic
   public static final boolean isBot(@NotNull EntityLivingBase entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      if (entity instanceof EntityPlayer && entity != MinecraftInstance.mc.field_71439_g) {
         if (!INSTANCE.getState()) {
            return false;
         } else {
            if ((Boolean)validNameValue.get()) {
               String var1 = ((EntityPlayer)entity).func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var1, "entity.name");
               CharSequence var8 = (CharSequence)var1;
               if (!regex.matches(var8)) {
                  return true;
               }
            }

            if ((Boolean)hiddenNameValue.get()) {
               String var9 = entity.func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var9, "entity.getName()");
               if (StringsKt.contains$default((CharSequence)var9, (CharSequence)"§", false, 2, (Object)null)) {
                  return true;
               }

               if (entity.func_145818_k_()) {
                  var9 = entity.func_95999_t();
                  Intrinsics.checkNotNullExpressionValue(var9, "entity.getCustomNameTag()");
                  CharSequence var10000 = (CharSequence)var9;
                  var9 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var9, "entity.getName()");
                  if (StringsKt.contains$default(var10000, (CharSequence)var9, false, 2, (Object)null)) {
                     return true;
                  }
               }
            }

            if ((Boolean)colorValue.get()) {
               String var12 = ((EntityPlayer)entity).func_145748_c_().func_150254_d();
               Intrinsics.checkNotNullExpressionValue(var12, "entity.displayName.formattedText");
               if (!StringsKt.contains$default((CharSequence)StringsKt.replace$default(var12, "§r", "", false, 4, (Object)null), (CharSequence)"§", false, 2, (Object)null)) {
                  return true;
               }
            }

            if ((Boolean)livingTimeValue.get() && entity.field_70173_aa < ((Number)livingTimeTicksValue.get()).intValue()) {
               return true;
            } else if ((Boolean)groundValue.get() && !ground.contains(((EntityPlayer)entity).func_145782_y())) {
               return true;
            } else if ((Boolean)airValue.get() && !air.contains(((EntityPlayer)entity).func_145782_y())) {
               return true;
            } else if ((Boolean)swingValue.get() && !swing.contains(((EntityPlayer)entity).func_145782_y())) {
               return true;
            } else if ((Boolean)noClipValue.get() && noClip.contains(((EntityPlayer)entity).func_145782_y())) {
               return true;
            } else if ((Boolean)reusedEntityIdValue.get() && hasRemovedEntities.contains(((EntityPlayer)entity).func_145782_y())) {
               return false;
            } else if (!(Boolean)healthValue.get() || !(((EntityPlayer)entity).func_110143_aJ() > 20.0F) && !(((EntityPlayer)entity).func_110143_aJ() <= 0.0F)) {
               if ((Boolean)spawnInCombatValue.get() && spawnInCombat.contains(((EntityPlayer)entity).func_145782_y())) {
                  return true;
               } else if (!(Boolean)entityIDValue.get() || ((EntityPlayer)entity).func_145782_y() < 1000000000 && ((EntityPlayer)entity).func_145782_y() > -1) {
                  if (!(Boolean)derpValue.get() || !(entity.field_70125_A > 90.0F) && !(entity.field_70125_A < -90.0F)) {
                     if ((Boolean)wasInvisibleValue.get() && invisible.contains(((EntityPlayer)entity).func_145782_y())) {
                        return true;
                     } else if ((Boolean)armorValue.get() && ((EntityPlayer)entity).field_71071_by.field_70460_b[0] == null && ((EntityPlayer)entity).field_71071_by.field_70460_b[1] == null && ((EntityPlayer)entity).field_71071_by.field_70460_b[2] == null && ((EntityPlayer)entity).field_71071_by.field_70460_b[3] == null) {
                        return true;
                     } else {
                        if ((Boolean)pingValue.get()) {
                           NetworkPlayerInfo var30 = MinecraftInstance.mc.func_147114_u().func_175102_a(((EntityPlayer)entity).func_110124_au());
                           if (var30 == null ? false : var30.func_178853_c() == 0) {
                              return true;
                           }
                        }

                        if ((Boolean)needHitValue.get() && !hitted.contains(((EntityPlayer)entity).func_145782_y())) {
                           return true;
                        } else if ((Boolean)invalidGroundValue.get() && ((Number)invalidGround.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() >= 10) {
                           return true;
                        } else if ((Boolean)tabValue.get()) {
                           boolean equals = tabModeValue.equals("Equals");
                           String var22 = ((EntityPlayer)entity).func_145748_c_().func_150254_d();
                           Intrinsics.checkNotNullExpressionValue(var22, "entity.displayName.formattedText");
                           String targetName = ColorUtils.stripColor(var22);

                           for(NetworkPlayerInfo networkPlayerInfo : MinecraftInstance.mc.func_147114_u().func_175106_d()) {
                              Intrinsics.checkNotNullExpressionValue(networkPlayerInfo, "networkPlayerInfo");
                              String networkName = ColorUtils.stripColor(NetworkExtensionKt.getFullName(networkPlayerInfo));
                              if (equals ? Intrinsics.areEqual((Object)targetName, (Object)networkName) : StringsKt.contains$default((CharSequence)targetName, (CharSequence)networkName, false, 2, (Object)null)) {
                                 return false;
                              }
                           }

                           return true;
                        } else if (duplicateCompareModeValue.equals("WhenSpawn") && duplicate.contains(((EntityPlayer)entity).func_146103_bH().getId())) {
                           return true;
                        } else {
                           if ((Boolean)duplicateInWorldValue.get() && duplicateCompareModeValue.equals("OnTime")) {
                              List var13 = MinecraftInstance.mc.field_71441_e.field_72996_f;
                              Intrinsics.checkNotNullExpressionValue(var13, "mc.theWorld.loadedEntityList");
                              Iterable $this$count$iv = (Iterable)var13;
                              int $i$f$count = 0;
                              int var31;
                              if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                                 var31 = 0;
                              } else {
                                 int count$iv = 0;

                                 for(Object element$iv : $this$count$iv) {
                                    Entity it = (Entity)element$iv;
                                    int var7 = 0;
                                    if (it instanceof EntityPlayer && Intrinsics.areEqual((Object)((EntityPlayer)it).func_70005_c_(), (Object)((EntityPlayer)it).func_70005_c_())) {
                                       ++count$iv;
                                       if (count$iv < 0) {
                                          CollectionsKt.throwCountOverflow();
                                       }
                                    }
                                 }

                                 var31 = count$iv;
                              }

                              if (var31 > 1) {
                                 return true;
                              }
                           }

                           if ((Boolean)duplicateInTabValue.get() && duplicateCompareModeValue.equals("OnTime")) {
                              Collection var15 = MinecraftInstance.mc.func_147114_u().func_175106_d();
                              Intrinsics.checkNotNullExpressionValue(var15, "mc.netHandler.playerInfoMap");
                              Iterable $this$count$iv = (Iterable)var15;
                              int $i$f$count = 0;
                              int var32;
                              if (((Collection)$this$count$iv).isEmpty()) {
                                 var32 = 0;
                              } else {
                                 int count$iv = 0;

                                 for(Object element$iv : $this$count$iv) {
                                    NetworkPlayerInfo it = (NetworkPlayerInfo)element$iv;
                                    int var29 = 0;
                                    if (Intrinsics.areEqual((Object)((EntityPlayer)entity).func_70005_c_(), (Object)it.func_178845_a().getName())) {
                                       ++count$iv;
                                       if (count$iv < 0) {
                                          CollectionsKt.throwCountOverflow();
                                       }
                                    }
                                 }

                                 var32 = count$iv;
                              }

                              if (var32 > 1) {
                                 return true;
                              }
                           }

                           if ((Boolean)fastDamageValue.get() && ((Number)lastDamageVl.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0.0F)).floatValue() > 0.0F) {
                              return true;
                           } else if ((Boolean)alwaysInRadiusValue.get() && !notAlwaysInRadius.contains(((EntityPlayer)entity).func_145782_y())) {
                              if ((Boolean)alwaysInRadiusRemoveValue.get()) {
                                 MinecraftInstance.mc.field_71441_e.func_72900_e((Entity)entity);
                              }

                              return true;
                           } else {
                              String var17 = ((EntityPlayer)entity).func_70005_c_();
                              Intrinsics.checkNotNullExpressionValue(var17, "entity.name");
                              return ((CharSequence)var17).length() == 0 || Intrinsics.areEqual((Object)((EntityPlayer)entity).func_70005_c_(), (Object)MinecraftInstance.mc.field_71439_g.func_70005_c_());
                           }
                        }
                     }
                  } else {
                     return true;
                  }
               } else {
                  return true;
               }
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public void onDisable() {
      this.clearAll();
      super.onDisable();
   }

   private final void processEntityMove(Entity entity, boolean onGround) {
      if (entity instanceof EntityPlayer) {
         if (onGround && !ground.contains(((EntityPlayer)entity).func_145782_y())) {
            ground.add(((EntityPlayer)entity).func_145782_y());
         }

         if (!onGround && !air.contains(((EntityPlayer)entity).func_145782_y())) {
            air.add(((EntityPlayer)entity).func_145782_y());
         }

         if (onGround) {
            if (entity.field_70167_r != entity.field_70163_u) {
               Map var3 = invalidGround;
               Integer var4 = ((EntityPlayer)entity).func_145782_y();
               Integer var5 = ((Number)invalidGround.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() + 1;
               var3.put(var4, var5);
            }
         } else {
            int currentVL = ((Number)invalidGround.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() / 2;
            if (currentVL <= 0) {
               invalidGround.remove(((EntityPlayer)entity).func_145782_y());
            } else {
               Map var9 = invalidGround;
               Integer var10 = ((EntityPlayer)entity).func_145782_y();
               Integer var6 = currentVL;
               var9.put(var10, var6);
            }
         }

         if (((EntityPlayer)entity).func_82150_aj() && !invisible.contains(((EntityPlayer)entity).func_145782_y())) {
            invisible.add(((EntityPlayer)entity).func_145782_y());
         }

         if (!noClip.contains(((EntityPlayer)entity).func_145782_y())) {
            List cb = MinecraftInstance.mc.field_71441_e.func_72945_a(entity, ((EntityPlayer)entity).func_174813_aQ().func_72331_e((double)0.0625F, (double)0.0625F, (double)0.0625F));
            Intrinsics.checkNotNullExpressionValue(cb, "cb");
            if (!((Collection)cb).isEmpty()) {
               noClip.add(((EntityPlayer)entity).func_145782_y());
            }
         }

         if ((!(Boolean)livingTimeValue.get() || entity.field_70173_aa > ((Number)livingTimeTicksValue.get()).intValue() || !(Boolean)alwaysInRadiusWithTicksCheckValue.get()) && !notAlwaysInRadius.contains(((EntityPlayer)entity).func_145782_y()) && MinecraftInstance.mc.field_71439_g.func_70032_d(entity) > ((Number)alwaysRadiusValue.get()).floatValue()) {
            notAlwaysInRadius.add(((EntityPlayer)entity).func_145782_y());
         }
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71441_e != null) {
         if ((Boolean)czechHekValue.get()) {
            Packet packet = event.getPacket();
            if (packet instanceof S41PacketServerDifficulty) {
               wasAdded = false;
            }

            if (packet instanceof S38PacketPlayerListItem) {
               S38PacketPlayerListItem packetListItem = (S38PacketPlayerListItem)event.getPacket();
               S38PacketPlayerListItem.AddPlayerData data = (S38PacketPlayerListItem.AddPlayerData)packetListItem.func_179767_a().get(0);
               if (data.func_179962_a() != null && data.func_179962_a().getName() != null) {
                  if (!wasAdded) {
                     wasAdded = Intrinsics.areEqual((Object)data.func_179962_a().getName(), (Object)MinecraftInstance.mc.field_71439_g.func_70005_c_());
                  } else if (!MinecraftInstance.mc.field_71439_g.func_175149_v() && !MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75101_c && (!(Boolean)czechHekPingCheckValue.get() || data.func_179963_b() != 0) && (!(Boolean)czechHekGMCheckValue.get() || data.func_179960_c() != GameType.NOT_SET)) {
                     event.cancelEvent();
                     if ((Boolean)debugValue.get()) {
                        ClientUtils.INSTANCE.displayChatMessage("§7[§a§lAnti Bot/§6Matrix§7] §fPrevented §r" + data.func_179962_a().getName() + " §ffrom spawning.");
                     }
                  }
               }
            }
         }

         Packet packet = event.getPacket();
         if (packet instanceof S18PacketEntityTeleport) {
            Entity var10001 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S18PacketEntityTeleport)packet).func_149451_c());
            if (var10001 == null) {
               return;
            }

            this.processEntityMove(var10001, ((S18PacketEntityTeleport)packet).func_179697_g());
         } else if (packet instanceof S14PacketEntity) {
            Entity var36 = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e);
            if (var36 == null) {
               return;
            }

            this.processEntityMove(var36, ((S14PacketEntity)packet).func_179742_g());
         } else if (packet instanceof S0BPacketAnimation) {
            Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S0BPacketAnimation)packet).func_148978_c());
            if (entity != null && entity instanceof EntityLivingBase && ((S0BPacketAnimation)packet).func_148977_d() == 0 && !swing.contains(((EntityLivingBase)entity).func_145782_y())) {
               swing.add(((EntityLivingBase)entity).func_145782_y());
            }
         } else if (packet instanceof S38PacketPlayerListItem) {
            if (duplicateCompareModeValue.equals("WhenSpawn") && ((S38PacketPlayerListItem)packet).func_179768_b() == Action.ADD_PLAYER) {
               List $this$forEach$iv = ((S38PacketPlayerListItem)packet).func_179767_a();
               Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "packet.entries");
               Iterable $this$forEach$iv = (Iterable)$this$forEach$iv;
               int $i$f$forEach = 0;

               for(Object element$iv : $this$forEach$iv) {
                  S38PacketPlayerListItem.AddPlayerData entry;
                  label194: {
                     entry = (S38PacketPlayerListItem.AddPlayerData)element$iv;
                     int var8 = 0;
                     String name = entry.func_179962_a().getName();
                     if ((Boolean)duplicateInWorldValue.get()) {
                        List var10 = MinecraftInstance.mc.field_71441_e.field_73010_i;
                        Intrinsics.checkNotNullExpressionValue(var10, "mc.theWorld.playerEntities");
                        Iterable $this$any$iv = (Iterable)var10;
                        int $i$f$any = 0;
                        boolean var10000;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                           var10000 = false;
                        } else {
                           Iterator var12 = $this$any$iv.iterator();

                           while(true) {
                              if (!var12.hasNext()) {
                                 var10000 = false;
                                 break;
                              }

                              Object element$iv = var12.next();
                              EntityPlayer it = (EntityPlayer)element$iv;
                              int var15 = 0;
                              if (Intrinsics.areEqual((Object)it.func_70005_c_(), (Object)name)) {
                                 var10000 = true;
                                 break;
                              }
                           }
                        }

                        if (var10000) {
                           break label194;
                        }
                     }

                     if (!(Boolean)duplicateInTabValue.get()) {
                        continue;
                     }

                     Collection var24 = MinecraftInstance.mc.func_147114_u().func_175106_d();
                     Intrinsics.checkNotNullExpressionValue(var24, "mc.netHandler.playerInfoMap");
                     Iterable $this$any$iv = (Iterable)var24;
                     int $i$f$any = 0;
                     boolean var32;
                     if (((Collection)$this$any$iv).isEmpty()) {
                        var32 = false;
                     } else {
                        Iterator var28 = $this$any$iv.iterator();

                        while(true) {
                           if (!var28.hasNext()) {
                              var32 = false;
                              break;
                           }

                           Object element$iv = var28.next();
                           NetworkPlayerInfo it = (NetworkPlayerInfo)element$iv;
                           int var31 = 0;
                           if (Intrinsics.areEqual((Object)it.func_178845_a().getName(), (Object)name)) {
                              var32 = true;
                              break;
                           }
                        }
                     }

                     if (!var32) {
                        continue;
                     }
                  }

                  List var33 = duplicate;
                  UUID var26 = entry.func_179962_a().getId();
                  Intrinsics.checkNotNullExpressionValue(var26, "entry.profile.id");
                  var33.add(var26);
               }
            }
         } else if (packet instanceof S0CPacketSpawnPlayer) {
            if (CrossSine.INSTANCE.getCombatManager().getInCombat() && !hasRemovedEntities.contains(((S0CPacketSpawnPlayer)packet).func_148943_d())) {
               spawnInCombat.add(((S0CPacketSpawnPlayer)packet).func_148943_d());
            }
         } else if (packet instanceof S13PacketDestroyEntities) {
            Collection var34 = (Collection)hasRemovedEntities;
            int[] var20 = ((S13PacketDestroyEntities)packet).func_149098_c();
            Intrinsics.checkNotNullExpressionValue(var20, "packet.entityIDs");
            CollectionsKt.addAll(var34, ArraysKt.toTypedArray(var20));
         }

         if (packet instanceof S19PacketEntityStatus && ((S19PacketEntityStatus)packet).func_149160_c() == 2 || packet instanceof S0BPacketAnimation && ((S0BPacketAnimation)packet).func_148977_d() == 1) {
            Entity var35;
            if (packet instanceof S19PacketEntityStatus) {
               var35 = ((S19PacketEntityStatus)packet).func_149161_a((World)MinecraftInstance.mc.field_71441_e);
            } else {
               var35 = packet instanceof S0BPacketAnimation ? MinecraftInstance.mc.field_71441_e.func_73045_a(((S0BPacketAnimation)packet).func_148978_c()) : (Entity)null;
               if (var35 == null) {
                  return;
               }
            }

            Entity entity = var35;
            if (entity instanceof EntityPlayer) {
               lastDamageVl.put(((EntityPlayer)entity).func_145782_y(), ((Number)lastDamageVl.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0.0F)).floatValue() + (entity.field_70173_aa - ((Number)lastDamage.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() <= ((Number)fastDamageTicksValue.get()).intValue() ? 1.0F : -0.5F));
               lastDamage.put(((EntityPlayer)entity).func_145782_y(), entity.field_70173_aa);
            }
         }

      }
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent e) {
      Intrinsics.checkNotNullParameter(e, "e");
      Entity entity = e.getTargetEntity();
      if (entity instanceof EntityLivingBase && !hitted.contains(((EntityLivingBase)entity).func_145782_y())) {
         hitted.add(((EntityLivingBase)entity).func_145782_y());
      }

   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.clearAll();
   }

   private final void clearAll() {
      hitted.clear();
      swing.clear();
      ground.clear();
      invalidGround.clear();
      invisible.clear();
      lastDamage.clear();
      lastDamageVl.clear();
      notAlwaysInRadius.clear();
      duplicate.clear();
      spawnInCombat.clear();
      noClip.clear();
      hasRemovedEntities.clear();
   }

   static {
      String[] var0 = new String[]{"Equals", "Contains"};
      tabModeValue = (new ListValue("TabMode", var0, "Contains")).displayable(null.INSTANCE);
      entityIDValue = new BoolValue("EntityID", true);
      colorValue = new BoolValue("Color", false);
      livingTimeValue = new BoolValue("LivingTime", false);
      livingTimeTicksValue = (new IntegerValue("LivingTimeTicks", 40, 1, 200)).displayable(null.INSTANCE);
      groundValue = new BoolValue("Ground", true);
      airValue = new BoolValue("Air", false);
      invalidGroundValue = new BoolValue("InvalidGround", true);
      swingValue = new BoolValue("Swing", false);
      healthValue = new BoolValue("Health", false);
      derpValue = new BoolValue("Derp", true);
      wasInvisibleValue = new BoolValue("WasInvisible", false);
      validNameValue = new BoolValue("ValidName", true);
      hiddenNameValue = new BoolValue("HiddenName", false);
      armorValue = new BoolValue("Armor", false);
      pingValue = new BoolValue("Ping", false);
      needHitValue = new BoolValue("NeedHit", false);
      noClipValue = new BoolValue("NoClip", false);
      czechHekValue = new BoolValue("CzechMatrix", false);
      czechHekPingCheckValue = (new BoolValue("PingCheck", true)).displayable(null.INSTANCE);
      czechHekGMCheckValue = (new BoolValue("GamemodeCheck", true)).displayable(null.INSTANCE);
      reusedEntityIdValue = new BoolValue("ReusedEntityId", false);
      spawnInCombatValue = new BoolValue("SpawnInCombat", false);
      duplicateInWorldValue = new BoolValue("DuplicateInWorld", false);
      duplicateInTabValue = new BoolValue("DuplicateInTab", false);
      var0 = new String[]{"OnTime", "WhenSpawn"};
      duplicateCompareModeValue = (new ListValue("DuplicateCompareMode", var0, "OnTime")).displayable(null.INSTANCE);
      fastDamageValue = new BoolValue("FastDamage", false);
      fastDamageTicksValue = (new IntegerValue("FastDamageTicks", 5, 1, 20)).displayable(null.INSTANCE);
      removeFromWorld = new BoolValue("RemoveFromWorld", false);
      removeIntervalValue = new IntegerValue("Remove-Interval", 20, 1, 100);
      debugValue = new BoolValue("Debug", false);
      alwaysInRadiusValue = new BoolValue("AlwaysInRadius", false);
      alwaysRadiusValue = (new FloatValue("AlwaysInRadiusBlocks", 20.0F, 5.0F, 30.0F)).displayable(null.INSTANCE);
      alwaysInRadiusRemoveValue = (new BoolValue("AlwaysInRadiusRemove", false)).displayable(null.INSTANCE);
      alwaysInRadiusWithTicksCheckValue = (new BoolValue("AlwaysInRadiusWithTicksCheck", false)).displayable(null.INSTANCE);
      ground = (List)(new ArrayList());
      air = (List)(new ArrayList());
      invalidGround = (Map)(new LinkedHashMap());
      swing = (List)(new ArrayList());
      invisible = (List)(new ArrayList());
      hitted = (List)(new ArrayList());
      spawnInCombat = (List)(new ArrayList());
      notAlwaysInRadius = (List)(new ArrayList());
      lastDamage = (Map)(new LinkedHashMap());
      lastDamageVl = (Map)(new LinkedHashMap());
      duplicate = (List)(new ArrayList());
      noClip = (List)(new ArrayList());
      hasRemovedEntities = (List)(new ArrayList());
      regex = new Regex("\\w{3,16}");
      wasAdded = MinecraftInstance.mc.field_71439_g != null;
   }
}
