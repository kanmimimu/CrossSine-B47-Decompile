package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "BackTrack",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010,\u001a\u00020-2\b\b\u0002\u0010.\u001a\u00020\u0007H\u0002J\u0010\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\u0007H\u0002J \u00101\u001a\u00020-2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002032\u0006\u00105\u001a\u000203H\u0002J\b\u00106\u001a\u000203H\u0002J\b\u00107\u001a\u000203H\u0002J\b\u00108\u001a\u000203H\u0002J\b\u00109\u001a\u00020-H\u0002J\u0010\u0010:\u001a\u00020-2\u0006\u0010;\u001a\u00020<H\u0007J\b\u0010=\u001a\u00020-H\u0016J\u0010\u0010>\u001a\u00020-2\u0006\u0010;\u001a\u00020?H\u0007J\u0010\u0010@\u001a\u00020-2\u0006\u0010;\u001a\u00020\u001eH\u0007J\u0010\u0010A\u001a\u00020-2\u0006\u0010;\u001a\u00020BH\u0007J\u0010\u0010C\u001a\u00020-2\u0006\u0010;\u001a\u00020DH\u0007J\u0010\u0010E\u001a\u00020-2\u0006\u0010;\u001a\u00020FH\u0007J\b\u0010G\u001a\u00020-H\u0002J\b\u0010H\u001a\u00020-H\u0002J\b\u0010I\u001a\u00020-H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006J"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/BackTrack;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "distance", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "lagMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "lagTimer", "lagged", "", "lastTarget", "Lnet/minecraft/entity/player/EntityPlayer;", "lastUpdateTime", "", "lerpPos", "Lnet/minecraft/util/Vec3;", "nextDelayValue", "packetList", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/PacketLog;", "packetPosValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "packets", "Ljava/util/LinkedList;", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "playerModel", "realPos", "sizeTick", "tag", "", "getTag", "()Ljava/lang/String;", "target", "getTarget", "()Lnet/minecraft/entity/player/EntityPlayer;", "setTarget", "(Lnet/minecraft/entity/player/EntityPlayer;)V", "targetPos", "clearPacket", "", "time", "clearPacketTick", "size", "drawEsp", "x", "", "y", "z", "getX", "getY", "getZ", "interpolatePosition", "onAttack", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "reset", "startDrawing", "stopDrawing", "CrossSine"}
)
public final class BackTrack extends Module {
   @NotNull
   public static final BackTrack INSTANCE = new BackTrack();
   @NotNull
   private static final ListValue lagMode;
   @NotNull
   private static final Value delayValue;
   @NotNull
   private static final Value nextDelayValue;
   @NotNull
   private static final Value distance;
   @NotNull
   private static final Value sizeTick;
   @NotNull
   private static final Value playerModel;
   @NotNull
   private static final BoolValue packetPosValue;
   @NotNull
   private static final ConcurrentLinkedQueue packetList;
   @NotNull
   private static final LinkedList packets;
   @Nullable
   private static EntityPlayer target;
   @Nullable
   private static EntityPlayer lastTarget;
   @NotNull
   private static Vec3 realPos;
   @NotNull
   private static Vec3 lerpPos;
   @NotNull
   private static Vec3 targetPos;
   @NotNull
   private static TimerMS attackTimer;
   private static boolean lagged;
   @NotNull
   private static TimerMS lagTimer;
   private static long lastUpdateTime;

   private BackTrack() {
   }

   @Nullable
   public final EntityPlayer getTarget() {
      return target;
   }

   public final void setTarget(@Nullable EntityPlayer var1) {
      target = var1;
   }

   public void onDisable() {
      this.reset();
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      attackTimer.reset();
      Entity entity = event.getTargetEntity();
      if (EntityUtils.INSTANCE.isSelected(entity, true) && entity instanceof EntityPlayer && ((EntityPlayer)entity).func_145782_y() != 1337 && !Intrinsics.areEqual((Object)target, (Object)entity)) {
         lastTarget = target;
         target = (EntityPlayer)entity;
         Vec3 var3 = ((EntityPlayer)entity).func_174791_d();
         Intrinsics.checkNotNullExpressionValue(var3, "entity.positionVector");
         targetPos = var3;
         var3 = ((EntityPlayer)entity).func_174791_d();
         Intrinsics.checkNotNullExpressionValue(var3, "entity.positionVector");
         realPos = var3;
         var3 = ((EntityPlayer)entity).func_174791_d();
         Intrinsics.checkNotNullExpressionValue(var3, "entity.positionVector");
         lerpPos = var3;
         float var10 = ((Number)((ClosedFloatingPointRange)distance.get()).getStart()).floatValue();
         float var4 = ((Number)((ClosedFloatingPointRange)distance.get()).getEndInclusive()).floatValue();
         EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var7, "mc.thePlayer");
         Entity var10000 = (Entity)var7;
         EntityPlayer var10001 = target;
         Intrinsics.checkNotNull(var10001);
         double var5 = EntityExtensionKt.getDistanceToEntityBox(var10000, (Entity)var10001);
         if (((double)var10 <= var5 ? var5 <= (double)var4 : false) && !lagged) {
            lagged = true;
            lagTimer.reset();
         }
      }

   }

   @EventTarget
   public final void onPreUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (lagged) {
         float var2 = ((Number)((ClosedFloatingPointRange)distance.get()).getStart()).floatValue();
         float var3 = ((Number)((ClosedFloatingPointRange)distance.get()).getEndInclusive()).floatValue();
         EntityPlayerSP var6 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var6, "mc.thePlayer");
         Entity var10000 = (Entity)var6;
         EntityPlayer var10001 = target;
         Intrinsics.checkNotNull(var10001);
         double var4 = EntityExtensionKt.getDistanceToEntityBox(var10000, (Entity)var10001);
         if (!((double)var2 <= var4 ? var4 <= (double)var3 : false) || lagTimer.hasTimePassed((long)((Number)nextDelayValue.get()).intValue())) {
            lagged = false;
         }
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71441_e != null) {
         Packet packet = event.getPacket();
         if (!(packet instanceof S03PacketTimeUpdate)) {
            if (!(packet instanceof S01PacketJoinGame) && !(packet instanceof S07PacketRespawn)) {
               if (!event.isCancelled()) {
                  String var3 = packet.getClass().getSimpleName();
                  Intrinsics.checkNotNullExpressionValue(var3, "packet.javaClass.simpleName");
                  if (StringsKt.startsWith(var3, "S", true)) {
                     if (lagMode.equals("Automatic") || !attackTimer.hasTimePassed(1100L)) {
                        if (!lagMode.equals("LegitReach") && (!lagMode.equals("Automatic") || !lagged)) {
                           packets.add(event);
                        } else {
                           packetList.add(new PacketLog(packet, System.currentTimeMillis()));
                        }

                        event.cancelEvent();
                     }

                     if (packet instanceof S14PacketEntity) {
                        if (target != null) {
                           int var10000 = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e).func_145782_y();
                           EntityPlayer var10001 = target;
                           Intrinsics.checkNotNull(var10001);
                           if (var10000 == var10001.func_145782_y()) {
                              Vec3 var4 = targetPos.func_72441_c((double)((S14PacketEntity)packet).func_149062_c() / (double)32.0F, (double)((S14PacketEntity)packet).func_149061_d() / (double)32.0F, (double)((S14PacketEntity)packet).func_149064_e() / (double)32.0F);
                              Intrinsics.checkNotNullExpressionValue(var4, "targetPos.addVector(\n   …                        )");
                              targetPos = var4;
                              var4 = realPos.func_72441_c((double)((S14PacketEntity)packet).func_149062_c() / (double)32.0F, (double)((S14PacketEntity)packet).func_149061_d() / (double)32.0F, (double)((S14PacketEntity)packet).func_149064_e() / (double)32.0F);
                              Intrinsics.checkNotNullExpressionValue(var4, "realPos.addVector(\n     …                        )");
                              realPos = var4;
                           }
                        }
                     } else if (packet instanceof S18PacketEntityTeleport && target != null) {
                        int var6 = ((S18PacketEntityTeleport)packet).func_149451_c();
                        EntityPlayer var7 = target;
                        Intrinsics.checkNotNull(var7);
                        if (var6 == var7.func_145782_y()) {
                           targetPos = new Vec3((double)((S18PacketEntityTeleport)packet).func_149449_d() / (double)32.0F, (double)((S18PacketEntityTeleport)packet).func_149448_e() / (double)32.0F, (double)((S18PacketEntityTeleport)packet).func_149446_f() / (double)32.0F);
                           realPos = new Vec3((double)((S18PacketEntityTeleport)packet).func_149449_d() / (double)32.0F, (double)((S18PacketEntityTeleport)packet).func_149448_e() / (double)32.0F, (double)((S18PacketEntityTeleport)packet).func_149446_f() / (double)32.0F);
                        }
                     }
                  }
               }

            } else {
               if (!lagMode.equals("LegitReach") && !lagMode.equals("Automatic")) {
                  this.clearPacketTick(0);
               } else {
                  this.clearPacket(0);
               }

            }
         }
      }
   }

   private final void reset() {
      target = null;
      lastTarget = null;
      lerpPos = new Vec3((double)0.0F, (double)0.0F, (double)0.0F);
      this.clearPacket(0);
      this.clearPacketTick(0);
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.reset();
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getEventState() == EventState.POST) {
         if (lagMode.equals("Automatic")) {
            clearPacket$default(this, 0, 1, (Object)null);
         }

         if (lagMode.equals("LegitReach")) {
            if (target != null && !attackTimer.hasTimePassed(1000L)) {
               clearPacket$default(this, 0, 1, (Object)null);
            } else {
               this.clearPacket(0);
            }
         } else if (target != null && !attackTimer.hasTimePassed(1000L)) {
            this.clearPacketTick(((Number)sizeTick.get()).intValue());
         } else {
            this.clearPacketTick(0);
         }
      }

   }

   private final void clearPacket(int time) {
      if (!packetList.isEmpty()) {
         for(PacketLog packet : packetList) {
            if (time == 0 || System.currentTimeMillis() > packet.getTime() + (long)time) {
               Packet p = packet.getPacket();
               p.func_148833_a((INetHandler)MinecraftInstance.mc.func_147114_u());
               packetList.remove(packet);
            }
         }

      }
   }

   // $FF: synthetic method
   static void clearPacket$default(BackTrack var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = ((Number)delayValue.get()).intValue();
      }

      var0.clearPacket(var1);
   }

   private final void clearPacketTick(int size) {
      if (!packets.isEmpty()) {
         while(packets.size() > size) {
            PacketEvent var10000 = (PacketEvent)packets.pollFirst();
            if (var10000 != null) {
               PacketEvent event = var10000;

               try {
                  Packet packet = event.getPacket();
                  packet.func_148833_a((INetHandler)MinecraftInstance.mc.func_147114_u());
               } catch (Exception var4) {
               }
            }
         }

      }
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!lagMode.equals("Automatic") || lagged) {
         if (target != null) {
            if ((Boolean)playerModel.get()) {
               GL11.glPushMatrix();
               RenderManager var10000 = MinecraftInstance.mc.func_175598_ae();
               Entity var10001 = (Entity)target;
               double var10002 = this.getX() - MinecraftInstance.mc.func_175598_ae().field_78725_b;
               double var10003 = this.getY() - MinecraftInstance.mc.func_175598_ae().field_78726_c;
               double var10004 = this.getZ() - MinecraftInstance.mc.func_175598_ae().field_78723_d;
               EntityPlayer var10005 = target;
               Intrinsics.checkNotNull(var10005);
               var10000.func_147939_a(var10001, var10002, var10003, var10004, var10005.field_70177_z, event.getPartialTicks(), true);
               GL11.glPopMatrix();
               GlStateManager.func_179117_G();
            } else {
               this.startDrawing();
               this.drawEsp(this.getX(), this.getY(), this.getZ());
               this.stopDrawing();
            }
         }

         if (target != null) {
            this.interpolatePosition();
         }

      }
   }

   private final void interpolatePosition() {
      long currentTime = System.nanoTime();
      double deltaTime = (double)(currentTime - lastUpdateTime) / (double)1.0E9F;
      lastUpdateTime = currentTime;
      int lerpSpeed = 7;
      double lerpAmount = (double)lerpSpeed * deltaTime;
      double clampedLerpAmount = RangesKt.coerceIn(lerpAmount, (double)0.0F, (double)1.0F);
      lerpPos = new Vec3(lerpPos.field_72450_a + (targetPos.field_72450_a - lerpPos.field_72450_a) * clampedLerpAmount, lerpPos.field_72448_b + (targetPos.field_72448_b - lerpPos.field_72448_b) * clampedLerpAmount, lerpPos.field_72449_c + (targetPos.field_72449_c - lerpPos.field_72449_c) * clampedLerpAmount);
   }

   private final void startDrawing() {
      GL11.glPushMatrix();
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
   }

   private final void stopDrawing() {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glPopMatrix();
   }

   private final double getX() {
      return (Boolean)packetPosValue.get() ? realPos.field_72450_a : lerpPos.field_72450_a;
   }

   private final double getY() {
      return (Boolean)packetPosValue.get() ? realPos.field_72448_b : lerpPos.field_72448_b;
   }

   private final double getZ() {
      return (Boolean)packetPosValue.get() ? realPos.field_72449_c : lerpPos.field_72449_c;
   }

   private final void drawEsp(double x, double y, double z) {
      RenderUtils.glColor(ClientTheme.INSTANCE.getColorWithAlpha(0, 100, true));
      RenderUtils.drawBoundingBlock(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(-MinecraftInstance.mc.field_71439_g.field_70165_t, -MinecraftInstance.mc.field_71439_g.field_70163_u, -MinecraftInstance.mc.field_71439_g.field_70161_v).func_72317_d(x, y, z).func_72314_b(0.04, 0.04, 0.04));
      GlStateManager.func_179117_G();
   }

   @NotNull
   public String getTag() {
      return (String)lagMode.get() + ' ' + (!lagMode.equals("LegitReach") && !lagMode.equals("Automatic") ? ((Number)sizeTick.get()).intValue() + " Tick" : ((Number)delayValue.get()).intValue() + " MS");
   }

   static {
      String[] var1 = new String[]{"LegitReach", "Automatic", "LagSize"};
      lagMode = new ListValue(var1) {
         protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
            Intrinsics.checkNotNullParameter(oldValue, "oldValue");
            Intrinsics.checkNotNullParameter(newValue, "newValue");
            BackTrack.INSTANCE.reset();
         }
      };
      delayValue = (new IntegerValue("Ping-MS", 350, 0, 2000)).displayable(null.INSTANCE);
      nextDelayValue = (new IntegerValue("NextDelay-MS", 500, 0, 5000)).displayable(null.INSTANCE);
      distance = (new FloatRangeValue("distance", 5.0F, 3.0F, 8.0F, 0.0F, 16, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      sizeTick = (new IntegerValue("Size", 60, 10, 100)).displayable(null.INSTANCE);
      playerModel = (new BoolValue("PlayerModel", true)).displayable(null.INSTANCE);
      packetPosValue = new BoolValue("UsePacketPos", false);
      packetList = new ConcurrentLinkedQueue();
      packets = new LinkedList();
      realPos = new Vec3((double)0.0F, (double)0.0F, (double)0.0F);
      lerpPos = new Vec3((double)0.0F, (double)0.0F, (double)0.0F);
      targetPos = new Vec3((double)0.0F, (double)0.0F, (double)0.0F);
      attackTimer = new TimerMS();
      lagTimer = new TimerMS();
      lastUpdateTime = System.nanoTime();
   }
}
