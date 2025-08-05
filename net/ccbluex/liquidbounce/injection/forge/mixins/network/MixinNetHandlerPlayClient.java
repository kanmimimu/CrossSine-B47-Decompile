package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.event.TeleportEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiExploit;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.script.api.global.Chat;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S08PacketPlayerPosLook.EnumFlags;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({NetHandlerPlayClient.class})
public abstract class MixinNetHandlerPlayClient {
   @Shadow
   @Final
   private NetworkManager field_147302_e;
   @Shadow
   private Minecraft field_147299_f;
   @Shadow
   private WorldClient field_147300_g;
   @Shadow
   public int field_147304_c;
   @Shadow
   private boolean field_147309_h;
   public boolean silentConfirm = false;

   @Shadow
   public abstract void func_147244_a(S12PacketEntityVelocity var1);

   @Redirect(
      method = {"handleExplosion"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S27PacketExplosion;getStrength()F"
)
   )
   private float onExplosionVelocity(S27PacketExplosion packetExplosion) {
      if (AntiExploit.INSTANCE.getState() && (Boolean)AntiExploit.INSTANCE.getLimitExplosionStrength().get()) {
         float strength = packetExplosion.func_149146_i();
         float fixedStrength = MathHelper.func_76131_a(strength, -1000.0F, 1000.0F);
         if (fixedStrength != strength) {
            Chat.print("Limited too strong explosion");
            return fixedStrength;
         }
      }

      return packetExplosion.func_149146_i();
   }

   @Inject(
      method = {"handleConfirmTransaction"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleConfirmTransaction(S32PacketConfirmTransaction packetIn, CallbackInfo ci) {
      if (ProtocolFixer.newerThanOrEqualsTo1_17()) {
         MinecraftInstance.mc.func_147114_u().func_147297_a(new C0FPacketConfirmTransaction(packetIn.func_148889_c(), (short)0, false));
         ci.cancel();
      }

   }

   @Redirect(
      method = {"handleExplosion"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S27PacketExplosion;func_149149_c()F"
)
   )
   private float onExplosionWorld(S27PacketExplosion packetExplosion) {
      if (AntiExploit.INSTANCE.getState() && (Boolean)AntiExploit.INSTANCE.getLimitExplosionRange().get()) {
         float originalRadius = packetExplosion.func_149149_c();
         float radius = MathHelper.func_76131_a(originalRadius, -1000.0F, 1000.0F);
         if (radius != originalRadius) {
            Chat.print("Limited too big TNT explosion radius");
            return radius;
         }
      }

      return packetExplosion.func_149149_c();
   }

   @Redirect(
      method = {"handleParticles"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S2APacketParticles;getParticleCount()I",
   ordinal = 1
)
   )
   private int onParticleAmount(S2APacketParticles packetParticles) {
      if (AntiExploit.INSTANCE.getState() && (Boolean)AntiExploit.INSTANCE.getLimitParticlesAmount().get() && packetParticles.func_149222_k() >= 500) {
         Chat.print("Limited too many particles");
         return 100;
      } else {
         return packetParticles.func_149222_k();
      }
   }

   @Redirect(
      method = {"handleParticles"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S2APacketParticles;getParticleSpeed()F"
)
   )
   private float onParticleSpeed(S2APacketParticles packetParticles) {
      if (AntiExploit.INSTANCE.getState() && (Boolean)AntiExploit.INSTANCE.getLimitParticlesSpeed().get() && packetParticles.func_149227_j() >= 10.0F) {
         Chat.print("Limited too fast particles speed");
         return 5.0F;
      } else {
         return packetParticles.func_149227_j();
      }
   }

   @Redirect(
      method = {"handleSpawnObject"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S0EPacketSpawnObject;getType()I"
)
   )
   private int onSpawnObjectType(S0EPacketSpawnObject packet) {
      if (AntiExploit.INSTANCE.getState() && (Boolean)AntiExploit.INSTANCE.getLimitedArrowsSpawned().get() && packet.func_148993_l() == 60) {
         int arrows = AntiExploit.INSTANCE.getArrowMax();
         ++arrows;
         if (arrows >= (Integer)AntiExploit.INSTANCE.getMaxArrowsSpawned().get()) {
            return -1;
         }
      }

      return packet.func_148993_l();
   }

   @Redirect(
      method = {"handleChangeGameState"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S2BPacketChangeGameState;getGameState()I"
)
   )
   private int onChangeGameState(S2BPacketChangeGameState packet) {
      return AntiExploit.INSTANCE.getState() && (Boolean)AntiExploit.INSTANCE.getCancelDemo().get() && MinecraftInstance.mc.func_71355_q() ? -1 : packet.func_149138_c();
   }

   @ModifyArg(
      method = {"handleJoinGame", "handleRespawn"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"
)
   )
   private GuiScreen handleJoinGame(GuiScreen guiScreen) {
      return null;
   }

   @Inject(
      method = {"handleAnimation"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleAnimation(S0BPacketAnimation s0BPacketAnimation, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleEntityTeleport"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleEntityTeleport(S18PacketEntityTeleport s18PacketEntityTeleport, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleEntityMovement"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/entity/Entity;onGround:Z"
)}
   )
   private void handleEntityMovementEvent(S14PacketEntity packetIn, CallbackInfo callbackInfo) {
      Entity entity = packetIn.func_149065_a(this.field_147300_g);
      if (entity != null) {
         CrossSine.eventManager.callEvent(new EntityMovementEvent(entity));
      }

   }

   @Inject(
      method = {"handleEntityHeadLook"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleEntityHeadLook(S19PacketEntityHeadLook s19PacketEntityHeadLook, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleEntityProperties"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleEntityProperties(S20PacketEntityProperties s20PacketEntityProperties, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleEntityMetadata"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleEntityMetadata(S1CPacketEntityMetadata s1CPacketEntityMetadata, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleEntityEquipment"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleEntityEquipment(S04PacketEntityEquipment s04PacketEntityEquipment, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleDestroyEntities"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleDestroyEntities(S13PacketDestroyEntities s13PacketDestroyEntities, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleScoreboardObjective"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
   shift = At.Shift.AFTER
)},
      cancellable = true
   )
   private void handleScoreboardObjective(S3BPacketScoreboardObjective s3BPacketScoreboardObjective, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147300_g, callbackInfo);
   }

   @Inject(
      method = {"handleConfirmTransaction"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/play/server/S32PacketConfirmTransaction;getWindowId()I",
   ordinal = 0
)},
      cancellable = true,
      locals = LocalCapture.CAPTURE_FAILEXCEPTION
   )
   private void handleConfirmTransaction(S32PacketConfirmTransaction s32PacketConfirmTransaction, CallbackInfo callbackInfo, Container container, EntityPlayer entityPlayer) {
      this.cancelIfNull(entityPlayer, callbackInfo);
   }

   @Inject(
      method = {"handleSoundEffect"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V"
)},
      cancellable = true
   )
   private void handleSoundEffect(S29PacketSoundEffect s29PacketSoundEffect, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147299_f.field_71441_e, callbackInfo);
   }

   @Inject(
      method = {"handleTimeUpdate"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V"
)},
      cancellable = true
   )
   private void handleTimeUpdate(S03PacketTimeUpdate s03PacketTimeUpdate, CallbackInfo callbackInfo) {
      this.cancelIfNull(this.field_147299_f.field_71441_e, callbackInfo);
   }

   @Overwrite
   public void func_147258_a(S08PacketPlayerPosLook packetIn) {
      PacketThreadUtil.func_180031_a(packetIn, (NetHandlerPlayClient)this, this.field_147299_f);
      EntityPlayer entityplayer = this.field_147299_f.field_71439_g;
      double d0 = packetIn.func_148932_c();
      double d1 = packetIn.func_148928_d();
      double d2 = packetIn.func_148933_e();
      float f = packetIn.func_148931_f();
      float f1 = packetIn.func_148930_g();
      TeleportEvent event = new TeleportEvent(new C03PacketPlayer.C06PacketPlayerPosLook(entityplayer.field_70165_t, entityplayer.field_70163_u, entityplayer.field_70161_v, entityplayer.field_70177_z, entityplayer.field_70125_A, false), d0, d1, d2, f, f1);
      CrossSine.eventManager.callEvent(event);
      PlayerUtils.setSinceTeleportTicks(0);
      if (!event.isCancelled()) {
         d0 = event.getX();
         d1 = event.getY();
         d2 = event.getZ();
         f = event.getYaw();
         f1 = event.getPitch();
         if (packetIn.func_179834_f().contains(EnumFlags.X)) {
            d0 += entityplayer.field_70165_t;
         } else {
            entityplayer.field_70159_w = (double)0.0F;
         }

         if (packetIn.func_179834_f().contains(EnumFlags.Y)) {
            d1 += entityplayer.field_70163_u;
         } else {
            entityplayer.field_70181_x = (double)0.0F;
         }

         if (packetIn.func_179834_f().contains(EnumFlags.Z)) {
            d2 += entityplayer.field_70161_v;
         } else {
            entityplayer.field_70179_y = (double)0.0F;
         }

         if (packetIn.func_179834_f().contains(EnumFlags.X_ROT)) {
            f1 += entityplayer.field_70125_A;
         }

         if (packetIn.func_179834_f().contains(EnumFlags.Y_ROT)) {
            f += entityplayer.field_70177_z;
         }

         entityplayer.func_70080_a(d0, d1, d2, f, f1);
         this.field_147302_e.func_179290_a(new C03PacketPlayer.C06PacketPlayerPosLook(d0, d1, d2, f, f1, false));
         if (!this.field_147309_h) {
            this.field_147299_f.field_71439_g.field_70169_q = this.field_147299_f.field_71439_g.field_70165_t;
            this.field_147299_f.field_71439_g.field_70167_r = this.field_147299_f.field_71439_g.field_70163_u;
            this.field_147299_f.field_71439_g.field_70166_s = this.field_147299_f.field_71439_g.field_70161_v;
            this.field_147309_h = true;
            this.field_147299_f.func_147108_a((GuiScreen)null);
         }

      }
   }

   private void cancelIfNull(Object t, CallbackInfo callbackInfo) {
      if (t == null) {
         callbackInfo.cancel();
      }

   }
}
