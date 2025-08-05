package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "FreeCam",
   category = ModuleCategory.PLAYER,
   autoDisable = EnumAutoDisableType.RESPAWN
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/FreeCam;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "c03SpoofValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "fakePlayer", "Lnet/minecraft/client/entity/EntityOtherPlayerMP;", "flyValue", "motionValue", "motionX", "", "motionY", "motionZ", "noClipValue", "packetCount", "", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class FreeCam extends Module {
   @NotNull
   private final FloatValue speedValue = new FloatValue("Speed", 0.8F, 0.1F, 2.0F);
   @NotNull
   private final BoolValue flyValue = new BoolValue("Fly", true);
   @NotNull
   private final BoolValue noClipValue = new BoolValue("NoClip", true);
   @NotNull
   private final BoolValue motionValue = new BoolValue("RecordMotion", true);
   @NotNull
   private final BoolValue c03SpoofValue = new BoolValue("C03Spoof", false);
   @Nullable
   private EntityOtherPlayerMP fakePlayer;
   private double motionX;
   private double motionY;
   private double motionZ;
   private int packetCount;

   public void onEnable() {
      if (MinecraftInstance.mc.field_71439_g != null) {
         if ((Boolean)this.motionValue.get()) {
            this.motionX = MinecraftInstance.mc.field_71439_g.field_70159_w;
            this.motionY = MinecraftInstance.mc.field_71439_g.field_70181_x;
            this.motionZ = MinecraftInstance.mc.field_71439_g.field_70179_y;
         } else {
            this.motionX = (double)0.0F;
            this.motionY = (double)0.0F;
            this.motionZ = (double)0.0F;
         }

         this.packetCount = 0;
         this.fakePlayer = new EntityOtherPlayerMP((World)MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_146103_bH());
         EntityOtherPlayerMP var10000 = this.fakePlayer;
         Intrinsics.checkNotNull(var10000);
         var10000.func_71049_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, true);
         var10000 = this.fakePlayer;
         Intrinsics.checkNotNull(var10000);
         var10000.field_70759_as = MinecraftInstance.mc.field_71439_g.field_70759_as;
         var10000 = this.fakePlayer;
         Intrinsics.checkNotNull(var10000);
         var10000.func_82149_j((Entity)MinecraftInstance.mc.field_71439_g);
         MinecraftInstance.mc.field_71441_e.func_73027_a(-((int)(Math.random() * (double)10000)), (Entity)this.fakePlayer);
         if ((Boolean)this.noClipValue.get()) {
            MinecraftInstance.mc.field_71439_g.field_70145_X = true;
         }

      }
   }

   public void onDisable() {
      if (MinecraftInstance.mc.field_71439_g != null && this.fakePlayer != null) {
         EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
         EntityOtherPlayerMP var10001 = this.fakePlayer;
         Intrinsics.checkNotNull(var10001);
         double var2 = var10001.field_70165_t;
         EntityOtherPlayerMP var10002 = this.fakePlayer;
         Intrinsics.checkNotNull(var10002);
         double var4 = var10002.field_70163_u;
         EntityOtherPlayerMP var10003 = this.fakePlayer;
         Intrinsics.checkNotNull(var10003);
         var10000.func_70080_a(var2, var4, var10003.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
         WorldClient var1 = MinecraftInstance.mc.field_71441_e;
         EntityOtherPlayerMP var3 = this.fakePlayer;
         Intrinsics.checkNotNull(var3);
         var1.func_73028_b(var3.func_145782_y());
         this.fakePlayer = null;
         MinecraftInstance.mc.field_71439_g.field_70159_w = this.motionX;
         MinecraftInstance.mc.field_71439_g.field_70181_x = this.motionY;
         MinecraftInstance.mc.field_71439_g.field_70179_y = this.motionZ;
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)this.noClipValue.get()) {
         MinecraftInstance.mc.field_71439_g.field_70145_X = true;
      }

      MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0F;
      if ((Boolean)this.flyValue.get()) {
         float value = ((Number)this.speedValue.get()).floatValue();
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
         if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
            var3.field_70181_x += (double)value;
         }

         if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
            EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
            var4.field_70181_x -= (double)value;
         }

         MovementUtils.INSTANCE.strafe(value);
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if ((Boolean)this.c03SpoofValue.get()) {
         if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C05PacketPlayerLook || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            if (this.packetCount >= 20) {
               this.packetCount = 0;
               EntityOtherPlayerMP var10002 = this.fakePlayer;
               Intrinsics.checkNotNull(var10002);
               double var4 = var10002.field_70165_t;
               EntityOtherPlayerMP var10003 = this.fakePlayer;
               Intrinsics.checkNotNull(var10003);
               double var8 = var10003.field_70163_u;
               EntityOtherPlayerMP var10004 = this.fakePlayer;
               Intrinsics.checkNotNull(var10004);
               double var11 = var10004.field_70161_v;
               EntityOtherPlayerMP var10005 = this.fakePlayer;
               Intrinsics.checkNotNull(var10005);
               float var14 = var10005.field_70177_z;
               EntityOtherPlayerMP var10006 = this.fakePlayer;
               Intrinsics.checkNotNull(var10006);
               float var17 = var10006.field_70125_A;
               EntityOtherPlayerMP var10007 = this.fakePlayer;
               Intrinsics.checkNotNull(var10007);
               PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(var4, var8, var11, var14, var17, var10007.field_70122_E)));
            } else {
               int var3 = this.packetCount++;
               EntityOtherPlayerMP var5 = this.fakePlayer;
               Intrinsics.checkNotNull(var5);
               PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer(var5.field_70122_E)));
            }

            event.cancelEvent();
         }
      } else if (packet instanceof C03PacketPlayer) {
         event.cancelEvent();
      }

      if (packet instanceof S08PacketPlayerPosLook) {
         EntityOtherPlayerMP var10000 = this.fakePlayer;
         Intrinsics.checkNotNull(var10000);
         var10000.func_70107_b(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c);
         this.motionX = (double)0.0F;
         this.motionY = (double)0.0F;
         this.motionZ = (double)0.0F;
         EntityOtherPlayerMP var6 = this.fakePlayer;
         Intrinsics.checkNotNull(var6);
         double var7 = var6.field_70165_t;
         EntityOtherPlayerMP var9 = this.fakePlayer;
         Intrinsics.checkNotNull(var9);
         double var10 = var9.field_70163_u;
         EntityOtherPlayerMP var12 = this.fakePlayer;
         Intrinsics.checkNotNull(var12);
         double var13 = var12.field_70161_v;
         EntityOtherPlayerMP var15 = this.fakePlayer;
         Intrinsics.checkNotNull(var15);
         float var16 = var15.field_70177_z;
         EntityOtherPlayerMP var18 = this.fakePlayer;
         Intrinsics.checkNotNull(var18);
         float var19 = var18.field_70125_A;
         EntityOtherPlayerMP var20 = this.fakePlayer;
         Intrinsics.checkNotNull(var20);
         PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(var7, var10, var13, var16, var19, var20.field_70122_E)));
         event.cancelEvent();
      }

   }
}
