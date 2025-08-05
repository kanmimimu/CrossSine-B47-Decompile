package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "AntiVoid",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010,\u001a\u00020\u0005H\u0002J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020.H\u0016J\u0010\u00100\u001a\u00020.2\u0006\u00101\u001a\u000202H\u0007J\u0010\u00103\u001a\u00020.2\u0006\u00101\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020.2\u0006\u00101\u001a\u000206H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00067"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiVoid;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoScaffoldValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "blink", "canBlink", "canCancel", "canSpoof", "flagged", "lastRecY", "", "maxFallDistValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "motionX", "motionY", "motionZ", "motionflagValue", "", "packetCache", "Ljava/util/ArrayList;", "Lnet/minecraft/network/play/client/C03PacketPlayer;", "Lkotlin/collections/ArrayList;", "pos", "Lnet/minecraft/util/BlockPos;", "posX", "posY", "posZ", "prediction", "resetMotionValue", "startFallDistValue", "tag", "", "getTag", "()Ljava/lang/String;", "tried", "voidOnlyValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "x", "y", "z", "checkVoid", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class AntiVoid extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final FloatValue maxFallDistValue;
   @NotNull
   private final Value resetMotionValue;
   @NotNull
   private final Value startFallDistValue;
   @NotNull
   private final Value autoScaffoldValue;
   @NotNull
   private final Value motionflagValue;
   @NotNull
   private final BoolValue voidOnlyValue;
   @NotNull
   private final Value prediction;
   @NotNull
   private final ArrayList packetCache;
   private boolean blink;
   private boolean canBlink;
   private boolean canCancel;
   private boolean canSpoof;
   private boolean tried;
   private boolean flagged;
   private double posX;
   private double posY;
   private double posZ;
   private double motionX;
   private double motionY;
   private double motionZ;
   private double lastRecY;
   private double x;
   private double y;
   private double z;
   @Nullable
   private BlockPos pos;

   public AntiVoid() {
      String[] var1 = new String[]{"Blink", "TPBack", "MotionFlag", "PacketFlag", "GroundSpoof", "OldHypixel", "Jartex", "OldCubecraft", "Packet", "Watchdog", "Vulcan", "Freeze"};
      this.modeValue = new ListValue("Mode", var1, "Blink");
      this.maxFallDistValue = new FloatValue("MaxFallDistance", 3.0F, 1.0F, 20.0F);
      this.resetMotionValue = (new BoolValue("ResetMotion", false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AntiVoid.this.modeValue.equals("Blink");
         }
      });
      this.startFallDistValue = (new FloatValue("BlinkStartFallDistance", 2.0F, 0.0F, 5.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AntiVoid.this.modeValue.equals("Blink");
         }
      });
      this.autoScaffoldValue = (new BoolValue("BlinkAutoScaffold", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AntiVoid.this.modeValue.equals("Blink");
         }
      });
      this.motionflagValue = (new FloatValue("MotionFlag-MotionY", 1.0F, 0.0F, 5.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AntiVoid.this.modeValue.equals("MotionFlag");
         }
      });
      this.voidOnlyValue = new BoolValue("OnlyVoid", true);
      this.prediction = (new BoolValue("Prediction", false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AntiVoid.this.voidOnlyValue.get();
         }
      });
      this.packetCache = new ArrayList();
   }

   public void onEnable() {
      this.canCancel = false;
      this.pos = null;
      this.blink = false;
      this.canBlink = false;
      this.canSpoof = false;
      this.lastRecY = MinecraftInstance.mc.field_71439_g != null ? MinecraftInstance.mc.field_71439_g.field_70163_u : (double)0.0F;
      this.tried = false;
      this.flagged = false;
      if (this.modeValue.equals("Freeze")) {
         if (MinecraftInstance.mc.field_71439_g == null) {
            return;
         }

         this.x = MinecraftInstance.mc.field_71439_g.field_70165_t;
         this.y = MinecraftInstance.mc.field_71439_g.field_70163_u;
         this.z = MinecraftInstance.mc.field_71439_g.field_70161_v;
         this.motionX = MinecraftInstance.mc.field_71439_g.field_70159_w;
         this.motionY = MinecraftInstance.mc.field_71439_g.field_70181_x;
         this.motionZ = MinecraftInstance.mc.field_71439_g.field_70179_y;
      }

   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.lastRecY == (double)0.0F) {
         this.lastRecY = MinecraftInstance.mc.field_71439_g.field_70163_u;
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         this.tried = false;
         this.flagged = false;
      }

      BlockPos var15;
      if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
         EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer");
         FallingPlayer fallingPlayer = new FallingPlayer((EntityPlayer)var3);
         BlockPos collLoc = fallingPlayer.findCollision(60);
         int var10001;
         if (collLoc == null) {
            var10001 = 0;
         } else {
            int var5 = collLoc.func_177956_o();
            var10001 = var5;
         }

         var15 = Math.abs((double)var10001 - MinecraftInstance.mc.field_71439_g.field_70163_u) > (double)(((Number)this.maxFallDistValue.get()).floatValue() + (float)1) ? collLoc : (BlockPos)null;
      } else {
         var15 = (BlockPos)null;
      }

      this.pos = var15;
      String var4 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var4.hashCode()) {
         case -1266402665:
            if (var4.equals("freeze")) {
               MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
               MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
               MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
               MinecraftInstance.mc.field_71439_g.func_70080_a(this.x, this.y, this.z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
            }
            break;
         case -1167184852:
            if (var4.equals("jartex")) {
               this.canSpoof = false;
               if ((!(Boolean)this.voidOnlyValue.get() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && MinecraftInstance.mc.field_71439_g.field_70163_u < this.lastRecY + 0.01 && MinecraftInstance.mc.field_71439_g.field_70181_x <= (double)0.0F && !MinecraftInstance.mc.field_71439_g.field_70122_E && !this.flagged) {
                  MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                  EntityPlayerSP var11 = MinecraftInstance.mc.field_71439_g;
                  var11.field_70179_y *= 0.838;
                  var11 = MinecraftInstance.mc.field_71439_g;
                  var11.field_70159_w *= 0.838;
                  this.canSpoof = true;
               }

               this.lastRecY = MinecraftInstance.mc.field_71439_g.field_70163_u;
            }
            break;
         case -995865464:
            if (var4.equals("packet")) {
               if (this.checkVoid()) {
                  this.canCancel = true;
               }

               if (this.canCancel) {
                  if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                     for(C03PacketPlayer packet : this.packetCache) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)packet);
                     }

                     this.packetCache.clear();
                  }

                  this.canCancel = false;
               }
            }
            break;
         case -867535517:
            if (var4.equals("tpback")) {
               if (MinecraftInstance.mc.field_71439_g.field_70122_E && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                  this.posX = MinecraftInstance.mc.field_71439_g.field_70169_q;
                  this.posY = MinecraftInstance.mc.field_71439_g.field_70167_r;
                  this.posZ = MinecraftInstance.mc.field_71439_g.field_70166_s;
               }

               if ((!(Boolean)this.voidOnlyValue.get() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && !this.tried) {
                  MinecraftInstance.mc.field_71439_g.func_70634_a(this.posX, this.posY, this.posZ);
                  MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                  this.tried = true;
               }
            }
            break;
         case -805359837:
            if (var4.equals("vulcan")) {
               if (MinecraftInstance.mc.field_71439_g.field_70122_E && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                  this.posX = MinecraftInstance.mc.field_71439_g.field_70169_q;
                  this.posY = MinecraftInstance.mc.field_71439_g.field_70167_r;
                  this.posZ = MinecraftInstance.mc.field_71439_g.field_70166_s;
               }

               if ((!(Boolean)this.voidOnlyValue.get() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && !this.tried) {
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, this.posY, MinecraftInstance.mc.field_71439_g.field_70161_v);
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
                  MinecraftInstance.mc.field_71439_g.func_70107_b(this.posX, this.posY, this.posZ);
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
                  MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0F;
                  MovementUtils.INSTANCE.resetMotion(true);
                  this.tried = true;
               }
            }
            break;
         case -720374750:
            if (var4.equals("motionflag") && (!(Boolean)this.voidOnlyValue.get() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && !this.tried) {
               EntityPlayerSP var9 = MinecraftInstance.mc.field_71439_g;
               var9.field_70181_x += ((Number)this.motionflagValue.get()).doubleValue();
               MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0F;
               this.tried = true;
            }
            break;
         case -529978910:
            if (var4.equals("groundspoof") && (!(Boolean)this.voidOnlyValue.get() || this.checkVoid())) {
               this.canSpoof = MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue();
            }
            break;
         case 93826908:
            if (var4.equals("blink")) {
               if (!this.blink) {
                  BlockPos collide = (new FallingPlayer(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 0.0F, 0.0F, 0.0F)).findCollision(60);
                  if (this.canBlink && (collide == null || MinecraftInstance.mc.field_71439_g.field_70163_u - (double)collide.func_177956_o() > (double)((Number)this.startFallDistValue.get()).floatValue())) {
                     this.posX = MinecraftInstance.mc.field_71439_g.field_70165_t;
                     this.posY = MinecraftInstance.mc.field_71439_g.field_70163_u;
                     this.posZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
                     this.motionX = MinecraftInstance.mc.field_71439_g.field_70159_w;
                     this.motionY = MinecraftInstance.mc.field_71439_g.field_70181_x;
                     this.motionZ = MinecraftInstance.mc.field_71439_g.field_70179_y;
                     this.packetCache.clear();
                     this.blink = true;
                  }

                  if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                     this.canBlink = true;
                  }
               } else if (MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue()) {
                  MinecraftInstance.mc.field_71439_g.func_70634_a(this.posX, this.posY, this.posZ);
                  if ((Boolean)this.resetMotionValue.get()) {
                     MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0F;
                  } else {
                     MinecraftInstance.mc.field_71439_g.field_70159_w = this.motionX;
                     MinecraftInstance.mc.field_71439_g.field_70181_x = this.motionY;
                     MinecraftInstance.mc.field_71439_g.field_70179_y = this.motionZ;
                     MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0F;
                  }

                  if ((Boolean)this.autoScaffoldValue.get()) {
                     Module var10000 = CrossSine.INSTANCE.getModuleManager().get(Scaffold.class);
                     Intrinsics.checkNotNull(var10000);
                     ((Scaffold)var10000).setState(true);
                  }

                  this.packetCache.clear();
                  this.blink = false;
                  this.canBlink = false;
               } else if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  this.blink = false;

                  for(C03PacketPlayer packet : this.packetCache) {
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)packet);
                  }
               }
            }
            break;
         case 155895796:
            if (var4.equals("packetflag") && (!(Boolean)this.voidOnlyValue.get() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && !this.tried) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + (double)1, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v + (double)1, false)));
               this.tried = true;
            }
            break;
         case 1438074052:
            if (var4.equals("oldcubecraft")) {
               this.canSpoof = false;
               if ((!(Boolean)this.voidOnlyValue.get() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && MinecraftInstance.mc.field_71439_g.field_70163_u < this.lastRecY + 0.01 && MinecraftInstance.mc.field_71439_g.field_70181_x <= (double)0.0F && !MinecraftInstance.mc.field_71439_g.field_70122_E && !this.flagged) {
                  MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0F;
                  this.canSpoof = true;
                  if (!this.tried) {
                     this.tried = true;
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, (double)32000.0F, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
                  }
               }

               this.lastRecY = MinecraftInstance.mc.field_71439_g.field_70163_u;
            }
      }

   }

   private final boolean checkVoid() {
      if ((Boolean)this.prediction.get() && MinecraftInstance.mc.field_71439_g.field_70143_R >= ((Number)this.maxFallDistValue.get()).floatValue() && this.pos == null) {
         return false;
      } else {
         int i = (int)(-(MinecraftInstance.mc.field_71439_g.field_70163_u - 1.4857625));
         boolean dangerous = true;

         while(i <= 0) {
            dangerous = MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(MinecraftInstance.mc.field_71439_g.field_70159_w * (double)0.5F, (double)i, MinecraftInstance.mc.field_71439_g.field_70179_y * (double)0.5F)).isEmpty();
            ++i;
            if (!dangerous) {
               break;
            }
         }

         return dangerous;
      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      String var4 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var4.hashCode()) {
         case -1266402665:
            if (var4.equals("freeze")) {
               if (event.getPacket() instanceof C03PacketPlayer) {
                  event.cancelEvent();
               }

               if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                  this.x = ((S08PacketPlayerPosLook)event.getPacket()).field_148940_a;
                  this.y = ((S08PacketPlayerPosLook)event.getPacket()).field_148938_b;
                  this.z = ((S08PacketPlayerPosLook)event.getPacket()).field_148939_c;
                  this.motionX = (double)0.0F;
                  this.motionY = (double)0.0F;
                  this.motionZ = (double)0.0F;
               }
            }
            break;
         case -1167184852:
            if (var4.equals("jartex")) {
               if (this.canSpoof && packet instanceof C03PacketPlayer) {
                  ((C03PacketPlayer)packet).field_149474_g = true;
               }

               if (this.canSpoof && packet instanceof S08PacketPlayerPosLook) {
                  this.flagged = true;
               }
            }
            break;
         case -995865464:
            if (var4.equals("packet")) {
               if (this.canCancel && packet instanceof C03PacketPlayer) {
                  this.packetCache.add(packet);
                  event.cancelEvent();
               }

               if (packet instanceof S08PacketPlayerPosLook) {
                  this.packetCache.clear();
                  this.canCancel = false;
               }
            }
            break;
         case -529978910:
            if (var4.equals("groundspoof") && this.canSpoof && packet instanceof C03PacketPlayer) {
               ((C03PacketPlayer)packet).field_149474_g = true;
            }
            break;
         case 93826908:
            if (var4.equals("blink") && this.blink && packet instanceof C03PacketPlayer) {
               this.packetCache.add(packet);
               event.cancelEvent();
            }
            break;
         case 1438074052:
            if (var4.equals("oldcubecraft")) {
               if (this.canSpoof && packet instanceof C03PacketPlayer && ((C03PacketPlayer)packet).field_149477_b < 1145.14191981) {
                  event.cancelEvent();
               }

               if (this.canSpoof && packet instanceof S08PacketPlayerPosLook) {
                  this.flagged = true;
               }
            }
            break;
         case 1594535950:
            if (var4.equals("oldhypixel")) {
               if (packet instanceof S08PacketPlayerPosLook && (double)MinecraftInstance.mc.field_71439_g.field_70143_R > (double)3.125F) {
                  MinecraftInstance.mc.field_71439_g.field_70143_R = 3.125F;
               }

               if (packet instanceof C03PacketPlayer) {
                  if ((Boolean)this.voidOnlyValue.get() && MinecraftInstance.mc.field_71439_g.field_70143_R >= ((Number)this.maxFallDistValue.get()).floatValue() && MinecraftInstance.mc.field_71439_g.field_70181_x <= (double)0.0F && this.checkVoid()) {
                     ((C03PacketPlayer)packet).field_149477_b += (double)11.0F;
                  }

                  if (!(Boolean)this.voidOnlyValue.get() && MinecraftInstance.mc.field_71439_g.field_70143_R >= ((Number)this.maxFallDistValue.get()).floatValue()) {
                     ((C03PacketPlayer)packet).field_149477_b += (double)11.0F;
                  }
               }
            }
      }

   }

   public void onDisable() {
      if (this.modeValue.equals("Freeze")) {
         MinecraftInstance.mc.field_71439_g.field_70159_w = this.motionX;
         MinecraftInstance.mc.field_71439_g.field_70181_x = this.motionY;
         MinecraftInstance.mc.field_71439_g.field_70179_y = this.motionZ;
         MinecraftInstance.mc.field_71439_g.func_70080_a(this.x, this.y, this.z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
      }

   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }
}
