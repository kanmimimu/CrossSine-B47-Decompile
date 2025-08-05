package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "StaffChecker",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020%H\u0007J\b\u0010&\u001a\u00020\u001cH\u0002J\u0010\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\nH\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0006R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/StaffChecker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Custom", "", "getCustom", "()Z", "antiV", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "bmcStaffList", "", "chat", "csstaffs", "", "customName", "Lnet/ccbluex/liquidbounce/features/value/Value;", "customValue", "leave", "leavemsg", "Lnet/ccbluex/liquidbounce/features/value/TextValue;", "onBMC", "getOnBMC", "staffs", "staffsInWorld", "isStaff", "entity", "Lnet/minecraft/entity/Entity;", "onEnable", "", "onInitialize", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "e", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "vanish", "warn", "name", "CrossSine"}
)
public final class StaffChecker extends Module {
   @NotNull
   private final BoolValue antiV = new BoolValue("AntiVanish", false);
   @NotNull
   private final BoolValue chat = new BoolValue("AlertChat", true);
   @NotNull
   private final BoolValue leave = new BoolValue("Leave", true);
   @NotNull
   private final TextValue leavemsg = new TextValue("LeaveMessage", "leave");
   @NotNull
   private final BoolValue customValue = new BoolValue("CustomName", false);
   @NotNull
   private final Value customName = (new TextValue("Name-of-staff", "")).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)StaffChecker.this.customValue.get();
      }
   });
   @NotNull
   private List staffs = (List)(new ArrayList());
   @NotNull
   private List csstaffs = (List)(new ArrayList());
   @NotNull
   private List staffsInWorld = (List)(new ArrayList());
   @NotNull
   private String bmcStaffList = "https://crosssine.github.io/cloud/StaffList/bmcstaff.txt";

   private final boolean getOnBMC() {
      boolean var10000;
      if (!MinecraftInstance.mc.func_71356_B() && ServerUtils.serverData != null) {
         String var1 = ServerUtils.serverData.field_78845_b;
         Intrinsics.checkNotNullExpressionValue(var1, "serverData.serverIP");
         if (StringsKt.contains$default((CharSequence)var1, (CharSequence)"blocksmc.com", false, 2, (Object)null)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   private final boolean getCustom() {
      return !MinecraftInstance.mc.func_71356_B() && ServerUtils.serverData != null && (Boolean)this.customValue.get();
   }

   public void onInitialize() {
      ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, new Function0() {
         public final void invoke() {
            List var10000 = StaffChecker.this.staffs;
            CharSequence var10001 = (CharSequence)HttpUtils.INSTANCE.get(StaffChecker.this.bmcStaffList);
            String[] var1 = new String[]{","};
            var10000.addAll((Collection)StringsKt.split$default(var10001, var1, false, 0, 6, (Object)null));
            var10000 = StaffChecker.this.csstaffs;
            String var2 = ((String)StaffChecker.this.customName.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            List var7;
            if (StringsKt.contains$default((CharSequence)var2, (CharSequence)"https", false, 2, (Object)null)) {
               CharSequence var6 = (CharSequence)HttpUtils.INSTANCE.get((String)StaffChecker.this.customName.get());
               var1 = new String[]{","};
               var7 = StringsKt.split$default(var6, var1, false, 0, 6, (Object)null);
            } else {
               CharSequence var8 = (CharSequence)StaffChecker.this.customName.get();
               var1 = new String[]{","};
               var7 = StringsKt.split$default(var8, var1, false, 0, 6, (Object)null);
            }

            var10000.addAll((Collection)var7);
         }
      }, 31, (Object)null);
   }

   public void onEnable() {
      this.staffsInWorld.clear();
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent e) {
      Intrinsics.checkNotNullParameter(e, "e");
      this.staffsInWorld.clear();
   }

   private final void warn(String name) {
      if (!this.staffsInWorld.contains(name)) {
         if ((Boolean)this.chat.get()) {
            this.chat(Intrinsics.stringPlus("[§CAntiStaff§F] Detected staff: §C", name));
         }

         if ((Boolean)this.leave.get()) {
            MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/", this.leavemsg.get()));
         }

         this.staffsInWorld.add(name);
      }
   }

   private final void vanish() {
      if ((Boolean)this.chat.get()) {
         this.chat("[§CAntiStaff§F] Detected someone vanished!");
      }

      if ((Boolean)this.leave.get()) {
         MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/", this.leavemsg.get()));
      }

   }

   private final boolean isStaff(Entity entity) {
      if (this.getCustom()) {
         boolean var28;
         if (!this.csstaffs.contains(entity.func_70005_c_()) && !this.csstaffs.contains(entity.func_145748_c_().func_150260_c())) {
            String var7 = entity.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(var7, "entity.name");
            if (!StringsKt.contains$default((CharSequence)var7, (CharSequence)this.csstaffs.toString(), false, 2, (Object)null)) {
               String var16 = this.csstaffs.toString().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               CharSequence var25 = (CharSequence)var16;
               var7 = entity.func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var7, "entity.name");
               var16 = var7.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               if (!StringsKt.contains$default(var25, (CharSequence)var16, false, 2, (Object)null)) {
                  var16 = this.csstaffs.toString().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  var25 = (CharSequence)var16;
                  var7 = entity.func_145748_c_().func_150260_c();
                  Intrinsics.checkNotNullExpressionValue(var7, "entity.displayName.unformattedText");
                  var16 = var7.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  if (!StringsKt.contains$default(var25, (CharSequence)var16, false, 2, (Object)null)) {
                     var7 = entity.func_70005_c_();
                     Intrinsics.checkNotNullExpressionValue(var7, "entity.name");
                     var16 = var7.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     var25 = (CharSequence)var16;
                     var16 = this.csstaffs.toString().toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     if (!StringsKt.contains$default(var25, (CharSequence)var16, false, 2, (Object)null)) {
                        var28 = false;
                        return var28;
                     }
                  }
               }
            }
         }

         var28 = true;
         return var28;
      } else if (!this.getOnBMC()) {
         return false;
      } else {
         boolean var24;
         if (!this.staffs.contains(entity.func_70005_c_()) && !this.staffs.contains(entity.func_145748_c_().func_150260_c())) {
            String var2 = entity.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(var2, "entity.name");
            if (!StringsKt.contains$default((CharSequence)var2, (CharSequence)this.staffs.toString(), false, 2, (Object)null)) {
               String var3 = this.staffs.toString().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               CharSequence var10000 = (CharSequence)var3;
               var2 = entity.func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var2, "entity.name");
               var3 = var2.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               if (!StringsKt.contains$default(var10000, (CharSequence)var3, false, 2, (Object)null)) {
                  var3 = this.staffs.toString().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  var10000 = (CharSequence)var3;
                  var2 = entity.func_145748_c_().func_150260_c();
                  Intrinsics.checkNotNullExpressionValue(var2, "entity.displayName.unformattedText");
                  var3 = var2.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  if (!StringsKt.contains$default(var10000, (CharSequence)var3, false, 2, (Object)null)) {
                     var2 = entity.func_70005_c_();
                     Intrinsics.checkNotNullExpressionValue(var2, "entity.name");
                     var3 = var2.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     var10000 = (CharSequence)var3;
                     var3 = this.staffs.toString().toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     if (!StringsKt.contains$default(var10000, (CharSequence)var3, false, 2, (Object)null)) {
                        var24 = false;
                        return var24;
                     }
                  }
               }
            }
         }

         var24 = true;
         return var24;
      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71441_e != null && MinecraftInstance.mc.field_71439_g != null) {
         if (this.getCustom() || this.getOnBMC()) {
            Packet packet = event.getPacket();
            if (packet instanceof S0CPacketSpawnPlayer) {
               Entity var10000 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S0CPacketSpawnPlayer)packet).func_148943_d());
               if (var10000 == null) {
                  return;
               }

               Entity entity = var10000;
               if (this.isStaff(entity)) {
                  String var4 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var4, "entity.name");
                  this.warn(var4);
               }
            } else if (packet instanceof S1EPacketRemoveEntityEffect) {
               Entity var31 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S1EPacketRemoveEntityEffect)packet).func_149076_c());
               if (var31 == null) {
                  return;
               }

               Entity entity = var31;
               if (this.isStaff(entity)) {
                  String var18 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var18, "entity.name");
                  this.warn(var18);
               }
            } else if (packet instanceof S12PacketEntityVelocity) {
               Entity var32 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
               if (var32 == null) {
                  return;
               }

               Entity entity = var32;
               if (this.isStaff(entity)) {
                  String var19 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var19, "entity.name");
                  this.warn(var19);
               }
            } else if (packet instanceof S01PacketJoinGame) {
               Entity var33 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S01PacketJoinGame)packet).func_149197_c());
               if (var33 == null) {
                  return;
               }

               Entity entity = var33;
               if (this.isStaff(entity)) {
                  String var20 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var20, "entity.name");
                  this.warn(var20);
               }
            } else if (packet instanceof S04PacketEntityEquipment) {
               Entity var34 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S04PacketEntityEquipment)packet).func_149389_d());
               if (var34 == null) {
                  return;
               }

               Entity entity = var34;
               if (this.isStaff(entity)) {
                  String var21 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var21, "entity.name");
                  this.warn(var21);
               }
            } else if (packet instanceof S1CPacketEntityMetadata) {
               Entity var35 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S1CPacketEntityMetadata)packet).func_149375_d());
               if (var35 == null) {
                  return;
               }

               Entity entity = var35;
               if (this.isStaff(entity)) {
                  String var22 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var22, "entity.name");
                  this.warn(var22);
               }
            } else if (packet instanceof S1DPacketEntityEffect) {
               if ((Boolean)this.antiV.get() && MinecraftInstance.mc.field_71441_e.func_73045_a(((S1DPacketEntityEffect)packet).func_149426_d()) == null) {
                  this.vanish();
               }

               Entity var36 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S1DPacketEntityEffect)packet).func_149426_d());
               if (var36 == null) {
                  return;
               }

               Entity entity = var36;
               if (this.isStaff(entity)) {
                  String var23 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var23, "entity.name");
                  this.warn(var23);
               }
            } else if (packet instanceof S18PacketEntityTeleport) {
               Entity var37 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S18PacketEntityTeleport)packet).func_149451_c());
               if (var37 == null) {
                  return;
               }

               Entity entity = var37;
               if (this.isStaff(entity)) {
                  String var24 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var24, "entity.name");
                  this.warn(var24);
               }
            } else if (packet instanceof S20PacketEntityProperties) {
               Entity var38 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S20PacketEntityProperties)packet).func_149442_c());
               if (var38 == null) {
                  return;
               }

               Entity entity = var38;
               if (this.isStaff(entity)) {
                  String var25 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var25, "entity.name");
                  this.warn(var25);
               }
            } else if (packet instanceof S0BPacketAnimation) {
               Entity var39 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S0BPacketAnimation)packet).func_148978_c());
               if (var39 == null) {
                  return;
               }

               Entity entity = var39;
               if (this.isStaff(entity)) {
                  String var26 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var26, "entity.name");
                  this.warn(var26);
               }
            } else if (packet instanceof S14PacketEntity) {
               if (((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e) == null) {
                  this.vanish();
               }

               Entity var40 = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e);
               if (var40 == null) {
                  return;
               }

               Entity entity = var40;
               if (this.isStaff(entity)) {
                  String var27 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var27, "entity.name");
                  this.warn(var27);
               }
            } else if (packet instanceof S19PacketEntityStatus) {
               Entity var41 = ((S19PacketEntityStatus)packet).func_149161_a((World)MinecraftInstance.mc.field_71441_e);
               if (var41 == null) {
                  return;
               }

               Entity entity = var41;
               if (this.isStaff(entity)) {
                  String var28 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var28, "entity.name");
                  this.warn(var28);
               }
            } else if (packet instanceof S19PacketEntityHeadLook) {
               Entity var42 = ((S19PacketEntityHeadLook)packet).func_149381_a((World)MinecraftInstance.mc.field_71441_e);
               if (var42 == null) {
                  return;
               }

               Entity entity = var42;
               if (this.isStaff(entity)) {
                  String var29 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var29, "entity.name");
                  this.warn(var29);
               }
            } else if (packet instanceof S49PacketUpdateEntityNBT) {
               Entity var43 = ((S49PacketUpdateEntityNBT)packet).func_179764_a((World)MinecraftInstance.mc.field_71441_e);
               if (var43 == null) {
                  return;
               }

               Entity entity = var43;
               if (this.isStaff(entity)) {
                  String var30 = entity.func_70005_c_();
                  Intrinsics.checkNotNullExpressionValue(var30, "entity.name");
                  this.warn(var30);
               }
            }
         }

      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71441_e != null && MinecraftInstance.mc.field_71439_g != null && this.getOnBMC()) {
         Collection var2 = MinecraftInstance.mc.func_147114_u().func_175106_d();
         Intrinsics.checkNotNullExpressionValue(var2, "mc.netHandler.playerInfoMap");
         Iterable $this$forEach$iv = (Iterable)var2;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            NetworkPlayerInfo it = (NetworkPlayerInfo)element$iv;
            int var7 = 0;
            EntityUtils var10000 = EntityUtils.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            CharSequence var19 = (CharSequence)ColorUtils.stripColor(var10000.getName(it));
            String[] var8 = new String[]{" "};
            String networkName = (String)StringsKt.split$default(var19, var8, false, 0, 6, (Object)null).get(0);
            if (this.staffs.contains(networkName)) {
               this.warn(networkName);
            }
         }

         List var11 = MinecraftInstance.mc.field_71441_e.field_72996_f;
         Intrinsics.checkNotNullExpressionValue(var11, "mc.theWorld.loadedEntityList");
         Iterable $this$forEach$iv = (Iterable)var11;
         $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            Entity it = (Entity)element$iv;
            int var17 = 0;
            if (this.staffs.contains(it.func_70005_c_())) {
               String var18 = it.func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var18, "it.name");
               this.warn(var18);
            }
         }

      }
   }
}
