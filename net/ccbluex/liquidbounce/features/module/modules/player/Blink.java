package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.visual.Breadcrumbs;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.FakePlayer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "Blink",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\b\u0010\u001e\u001a\u00020\u001cH\u0016J\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!H\u0007J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020#H\u0007J\u0010\u0010$\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020%H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006&"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Blink;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fakePlayer", "Lnet/ccbluex/liquidbounce/utils/FakePlayer;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayClient;", "positions", "Ljava/util/LinkedList;", "", "pulseDelayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "pulseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "pulseValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "renderPlayer", "serverPacket", "tag", "", "getTag", "()Ljava/lang/String;", "clearPackets", "", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class Blink extends Module {
   @NotNull
   public static final Blink INSTANCE = new Blink();
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final BoolValue renderPlayer;
   @NotNull
   private static final BoolValue pulseValue;
   @NotNull
   private static final BoolValue serverPacket;
   @NotNull
   private static final Value pulseDelayValue;
   @NotNull
   private static final MSTimer pulseTimer;
   @NotNull
   private static FakePlayer fakePlayer;
   @NotNull
   private static final LinkedList positions;
   @NotNull
   private static final LinkedBlockingQueue packets;

   private Blink() {
   }

   public void onEnable() {
      if (MinecraftInstance.mc.field_71439_g != null) {
         if (modeValue.equals("All")) {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
         } else {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, false, true, false, false, false, false, false, false, false, 2039, (Object)null);
         }

         packets.clear();
         LinkedList var1 = positions;
         synchronized(var1) {
            int var2 = 0;
            LinkedList var10000 = positions;
            double[] var3 = new double[]{MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)(MinecraftInstance.mc.field_71439_g.func_70047_e() / (float)2), MinecraftInstance.mc.field_71439_g.field_70161_v};
            var10000.add(var3);
            var10000 = positions;
            var3 = new double[]{MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b, MinecraftInstance.mc.field_71439_g.field_70161_v};
            var2 = var10000.add(var3);
         }

         pulseTimer.reset();
      }
   }

   public void onDisable() {
      LinkedList var1 = positions;
      synchronized(var1) {
         int var2 = 0;
         positions.clear();
         Unit var4 = Unit.INSTANCE;
      }

      if (MinecraftInstance.mc.field_71439_g != null) {
         BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
         this.clearPackets();
         fakePlayer.disable();
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      LinkedList var2 = positions;
      synchronized(var2) {
         int var3 = 0;
         LinkedList var10000 = positions;
         double[] var4 = new double[]{MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b, MinecraftInstance.mc.field_71439_g.field_70161_v};
         var3 = var10000.add(var4);
      }

      FakePlayer var12 = fakePlayer;
      EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNullExpressionValue(var7, "mc.thePlayer");
      var12.setup((EntityPlayer)var7);
      if ((Boolean)pulseValue.get() && pulseTimer.hasTimePassed((long)((Number)pulseDelayValue.get()).intValue())) {
         LinkedList var8 = positions;
         synchronized(var8) {
            int var10 = 0;
            positions.clear();
            Unit var11 = Unit.INSTANCE;
         }

         BlinkUtils.releasePacket$default(BlinkUtils.INSTANCE, (String)null, false, 0, 0, 15, (Object)null);
         this.clearPackets();
         fakePlayer.disable();
         pulseTimer.reset();
      }

   }

   private final void clearPackets() {
      while(!packets.isEmpty()) {
         PacketUtils var10000 = PacketUtils.INSTANCE;
         Object var10001 = packets.take();
         if (var10001 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.network.Packet<net.minecraft.network.play.INetHandlerPlayClient?>");
         }

         var10000.handlePacket((Packet)var10001);
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if ((Boolean)serverPacket.get()) {
         String var3 = packet.getClass().getSimpleName();
         Intrinsics.checkNotNullExpressionValue(var3, "packet.javaClass.simpleName");
         if (StringsKt.startsWith(var3, "S", true)) {
            if (MinecraftInstance.mc.field_71439_g.field_70173_aa < 20) {
               return;
            }

            event.cancelEvent();
            packets.add(packet);
         }
      }

   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)renderPlayer.get()) {
         Module var10000 = CrossSine.INSTANCE.getModuleManager().get(Breadcrumbs.class);
         Intrinsics.checkNotNull(var10000);
         Breadcrumbs breadcrumbs = (Breadcrumbs)var10000;
         LinkedList var3 = positions;
         synchronized(var3) {
            int var4 = 0;
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            MinecraftInstance.mc.field_71460_t.func_175072_h();
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            RenderUtils.glColor(breadcrumbs.getColor());
            double renderPosX = MinecraftInstance.mc.func_175598_ae().field_78730_l;
            double renderPosY = MinecraftInstance.mc.func_175598_ae().field_78731_m;
            double renderPosZ = MinecraftInstance.mc.func_175598_ae().field_78728_n;

            for(double[] pos : positions) {
               GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
            }

            GL11.glColor4d((double)1.0F, (double)1.0F, (double)1.0F, (double)1.0F);
            GL11.glEnd();
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
            Unit var14 = Unit.INSTANCE;
         }
      }
   }

   @NotNull
   public String getTag() {
      return Intrinsics.stringPlus("", BlinkUtils.bufferSize$default(BlinkUtils.INSTANCE, (String)null, 1, (Object)null));
   }

   static {
      String[] var0 = new String[]{"All", "Movement"};
      modeValue = new ListValue("Blink-Mode", var0, "All");
      renderPlayer = new BoolValue("Render", false);
      pulseValue = new BoolValue("Pulse", false);
      serverPacket = new BoolValue("Server-Packet", false);
      pulseDelayValue = (new IntegerValue("PulseDelay", 1000, 100, 5000)).displayable(null.INSTANCE);
      pulseTimer = new MSTimer();
      fakePlayer = new FakePlayer();
      positions = new LinkedList();
      packets = new LinkedBlockingQueue();
   }
}
