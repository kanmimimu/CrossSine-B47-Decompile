package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PathUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "InfiniteAura",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020\u001dH\u0002J\u001a\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\u00072\b\b\u0002\u0010'\u001a\u00020\u001bH\u0002J\b\u0010(\u001a\u00020#H\u0016J\b\u0010)\u001a\u00020#H\u0016J\u0010\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020,H\u0007J\u0010\u0010-\u001a\u00020#2\u0006\u0010+\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020#2\u0006\u0010+\u001a\u000200H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/InfiniteAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cpsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "distValue", "lastTarget", "Lnet/minecraft/entity/EntityLivingBase;", "getLastTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setLastTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "moveDistanceValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "noLagBackValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "noRegenValue", "packetBack", "packetValue", "pathRenderValue", "points", "", "Lnet/minecraft/util/Vec3;", "swingValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "targetsValue", "", "thread", "Ljava/lang/Thread;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "doTpAura", "", "getDelay", "hit", "entity", "force", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class InfiniteAura extends Module {
   @NotNull
   public static final InfiniteAura INSTANCE = new InfiniteAura();
   @NotNull
   private static final ListValue packetValue;
   @NotNull
   private static final BoolValue packetBack;
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final Value targetsValue;
   @NotNull
   private static final IntegerValue cpsValue;
   @NotNull
   private static final IntegerValue distValue;
   @NotNull
   private static final FloatValue moveDistanceValue;
   @NotNull
   private static final BoolValue noRegenValue;
   @NotNull
   private static final BoolValue noLagBackValue;
   @NotNull
   private static final Value swingValue;
   @NotNull
   private static final BoolValue pathRenderValue;
   @Nullable
   private static EntityLivingBase lastTarget;
   @NotNull
   private static final MSTimer timer;
   @NotNull
   private static List points;
   @Nullable
   private static Thread thread;

   private InfiniteAura() {
   }

   @Nullable
   public final EntityLivingBase getLastTarget() {
      return lastTarget;
   }

   public final void setLastTarget(@Nullable EntityLivingBase var1) {
      lastTarget = var1;
   }

   private final int getDelay() {
      return 1000 / ((Number)cpsValue.get()).intValue();
   }

   public void onEnable() {
      timer.reset();
      points.clear();
   }

   public void onDisable() {
      timer.reset();
      points.clear();
      Thread var10000 = thread;
      if (var10000 != null) {
         var10000.stop();
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (timer.hasTimePassed((long)this.getDelay())) {
         Thread var10000 = thread;
         if (!(var10000 == null ? false : var10000.isAlive())) {
            String var3 = ((String)modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (Intrinsics.areEqual((Object)var3, (Object)"aura")) {
               thread = ThreadsKt.thread$default(false, false, (ClassLoader)null, "InfiniteAura", 0, null.INSTANCE, 23, (Object)null);
               points.clear();
               timer.reset();
            } else if (Intrinsics.areEqual((Object)var3, (Object)"click")) {
               if (MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d()) {
                  thread = ThreadsKt.thread$default(false, false, (ClassLoader)null, "InfiniteAura", 0, null.INSTANCE, 23, (Object)null);
                  timer.reset();
               }

               points.clear();
            }

         }
      }
   }

   private final void doTpAura() {
      List var2 = MinecraftInstance.mc.field_71441_e.field_72996_f;
      Intrinsics.checkNotNullExpressionValue(var2, "mc.theWorld.loadedEntityList");
      Iterable $this$filter$iv = (Iterable)var2;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         Entity it = (Entity)element$iv$iv;
         int var10 = 0;
         if (it instanceof EntityLivingBase && EntityUtils.INSTANCE.isSelected(it, true) && MinecraftInstance.mc.field_71439_g.func_70032_d(it) < (float)((Number)distValue.get()).intValue()) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      List targets = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
      if (!targets.isEmpty()) {
         $i$f$filter = 0;
         if (targets.size() > 1) {
            CollectionsKt.sortWith(targets, new InfiniteAura$doTpAura$$inlined$sortBy$1());
         }

         int count = 0;
         Iterator var14 = targets.iterator();

         while(true) {
            if (var14.hasNext()) {
               Entity entity = (Entity)var14.next();
               if (entity == null) {
                  throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
               }

               if (hit$default(this, (EntityLivingBase)entity, false, 2, (Object)null)) {
                  ++count;
               }

               if (count <= ((Number)targetsValue.get()).intValue()) {
                  continue;
               }
            }

            return;
         }
      }
   }

   private final boolean hit(EntityLivingBase entity, boolean force) {
      List path = PathUtils.findBlinkPath(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, (double)((Number)moveDistanceValue.get()).floatValue());
      if (path.isEmpty()) {
         return false;
      } else {
         Intrinsics.checkNotNullExpressionValue(path, "path");
         Vec3 it = (Vec3)CollectionsKt.last(path);
         int var8 = 0;
         double lastDistance = entity.func_70011_f(it.field_72450_a, it.field_72448_b, it.field_72449_c);
         if (!force && lastDistance > (double)10.0F) {
            return false;
         } else {
            Iterable $this$forEach$iv = (Iterable)path;
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Vec3 it = (Vec3)element$iv;
               int var11 = 0;
               if (packetValue.equals("PacketPosition")) {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(it.field_72450_a, it.field_72448_b, it.field_72449_c, true)));
               } else {
                  NetHandlerPlayClient var10000 = MinecraftInstance.mc.func_147114_u();
                  double var10003 = it.field_72450_a;
                  double var10004 = it.field_72448_b;
                  double var10005 = it.field_72449_c;
                  EntityPlayerSP var10006 = MinecraftInstance.mc.field_71439_g;
                  Intrinsics.checkNotNull(var10006);
                  float var22 = var10006.field_70177_z;
                  EntityPlayerSP var10007 = MinecraftInstance.mc.field_71439_g;
                  Intrinsics.checkNotNull(var10007);
                  var10000.func_147297_a((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(var10003, var10004, var10005, var22, var10007.field_70125_A, true)));
               }

               List var17 = points;
               Intrinsics.checkNotNullExpressionValue(it, "it");
               var17.add(it);
            }

            if (lastDistance > (double)3.0F && (Boolean)packetBack.get()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, true)));
            }

            if ((Boolean)swingValue.get()) {
               MinecraftInstance.mc.field_71439_g.func_71038_i();
            } else {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
            }

            MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, (Entity)entity);
            int var12 = path.size() - 1;
            if (0 <= var12) {
               do {
                  $i$f$forEach = var12--;
                  Vec3 vec = (Vec3)path.get($i$f$forEach);
                  if (packetValue.equals("PacketPosition")) {
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c, true)));
                  } else {
                     NetHandlerPlayClient var18 = MinecraftInstance.mc.func_147114_u();
                     double var19 = vec.field_72450_a;
                     double var20 = vec.field_72448_b;
                     double var21 = vec.field_72449_c;
                     EntityPlayerSP var23 = MinecraftInstance.mc.field_71439_g;
                     Intrinsics.checkNotNull(var23);
                     float var24 = var23.field_70177_z;
                     EntityPlayerSP var25 = MinecraftInstance.mc.field_71439_g;
                     Intrinsics.checkNotNull(var25);
                     var18.func_147297_a((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(var19, var20, var21, var24, var25.field_70125_A, true)));
                  }
               } while(0 <= var12);
            }

            if ((Boolean)packetBack.get()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
            }

            return true;
         }
      }
   }

   // $FF: synthetic method
   static boolean hit$default(InfiniteAura var0, EntityLivingBase var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return var0.hit(var1, var2);
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPacket() instanceof S08PacketPlayerPosLook) {
         timer.reset();
      }

      boolean isMovePacket = event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition || event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook;
      if ((Boolean)noRegenValue.get() && event.getPacket() instanceof C03PacketPlayer && !isMovePacket) {
         event.cancelEvent();
      }

      if ((Boolean)noLagBackValue.get() && event.getPacket() instanceof S08PacketPlayerPosLook) {
         PlayerCapabilities capabilities = new PlayerCapabilities();
         capabilities.field_75101_c = true;
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C13PacketPlayerAbilities(capabilities)));
         double x = ((S08PacketPlayerPosLook)event.getPacket()).func_148932_c() - MinecraftInstance.mc.field_71439_g.field_70165_t;
         double y = ((S08PacketPlayerPosLook)event.getPacket()).func_148928_d() - MinecraftInstance.mc.field_71439_g.field_70163_u;
         double z = ((S08PacketPlayerPosLook)event.getPacket()).func_148933_e() - MinecraftInstance.mc.field_71439_g.field_70161_v;
         Math.sqrt(x * x + y * y + z * z);
         event.cancelEvent();
         PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)event.getPacket()).func_148932_c(), ((S08PacketPlayerPosLook)event.getPacket()).func_148928_d(), ((S08PacketPlayerPosLook)event.getPacket()).func_148933_e(), ((S08PacketPlayerPosLook)event.getPacket()).func_148931_f(), ((S08PacketPlayerPosLook)event.getPacket()).func_148930_g(), true)));
      }

   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      List var2 = points;
      synchronized(var2){}

      try {
         int var3 = 0;
         if (!points.isEmpty() && (Boolean)pathRenderValue.get()) {
            double renderPosX = MinecraftInstance.mc.func_175598_ae().field_78730_l;
            double renderPosY = MinecraftInstance.mc.func_175598_ae().field_78731_m;
            double renderPosZ = MinecraftInstance.mc.func_175598_ae().field_78728_n;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glShadeModel(7425);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            RenderUtils.glColor(ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null));

            for(Vec3 vec : points) {
               double x = vec.field_72450_a - renderPosX;
               double y = vec.field_72448_b - renderPosY;
               double z = vec.field_72449_c - renderPosZ;
               double width = 0.3;
               double height = (double)MinecraftInstance.mc.field_71439_g.func_70047_e();
               MinecraftInstance.mc.field_71460_t.func_78479_a(MinecraftInstance.mc.field_71428_T.field_74281_c, 2);
               GL11.glLineWidth(2.0F);
               GL11.glBegin(3);
               GL11.glVertex3d(x - width, y, z - width);
               GL11.glVertex3d(x - width, y, z - width);
               GL11.glVertex3d(x - width, y + height, z - width);
               GL11.glVertex3d(x + width, y + height, z - width);
               GL11.glVertex3d(x + width, y, z - width);
               GL11.glVertex3d(x - width, y, z - width);
               GL11.glVertex3d(x - width, y, z + width);
               GL11.glEnd();
               GL11.glBegin(3);
               GL11.glVertex3d(x + width, y, z + width);
               GL11.glVertex3d(x + width, y + height, z + width);
               GL11.glVertex3d(x - width, y + height, z + width);
               GL11.glVertex3d(x - width, y, z + width);
               GL11.glVertex3d(x + width, y, z + width);
               GL11.glVertex3d(x + width, y, z - width);
               GL11.glEnd();
               GL11.glBegin(3);
               GL11.glVertex3d(x + width, y + height, z + width);
               GL11.glVertex3d(x + width, y + height, z - width);
               GL11.glEnd();
               GL11.glBegin(3);
               GL11.glVertex3d(x - width, y + height, z + width);
               GL11.glVertex3d(x - width, y + height, z - width);
               GL11.glEnd();
            }

            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Unit var24 = Unit.INSTANCE;
            return;
         }
      } finally {
         ;
      }

   }

   static {
      String[] var0 = new String[]{"PacketPosition", "PacketPosLook"};
      packetValue = new ListValue("PacketMode", var0, "PacketPosition");
      packetBack = new BoolValue("DoTeleportBackPacket", false);
      var0 = new String[]{"Aura", "Click"};
      modeValue = new ListValue("Mode", var0, "Aura");
      targetsValue = (new IntegerValue("Targets", 3, 1, 10)).displayable(null.INSTANCE);
      cpsValue = new IntegerValue("CPS", 1, 1, 10);
      distValue = new IntegerValue("Distance", 30, 20, 100);
      moveDistanceValue = new FloatValue("MoveDistance", 5.0F, 2.0F, 15.0F);
      noRegenValue = new BoolValue("NoRegen", true);
      noLagBackValue = new BoolValue("NoLagback", true);
      swingValue = (new BoolValue("Swing", true)).displayable(null.INSTANCE);
      pathRenderValue = new BoolValue("PathRender", true);
      timer = new MSTimer();
      points = (List)(new ArrayList());
   }
}
