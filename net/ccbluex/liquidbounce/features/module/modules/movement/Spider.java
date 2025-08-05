package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Spider",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Spider;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "glitch", "", "groundHeight", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "motionValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "ticks", "", "wasTimer", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class Spider extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final FloatValue motionValue;
   private double groundHeight;
   private boolean glitch;
   private boolean wasTimer;
   private int ticks;

   public Spider() {
      String[] var1 = new String[]{"Collide", "Motion", "AAC3.3.12", "AAC4", "Checker", "Vulcan"};
      this.modeValue = new ListValue("Mode", var1, "Collide");
      this.motionValue = new FloatValue("Motion", 0.42F, 0.1F, 1.0F);
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.wasTimer) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      }

      if (!MinecraftInstance.mc.field_71439_g.field_70123_F || !MovementUtils.INSTANCE.isMoving()) {
         AxisAlignedBB var2 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
         Intrinsics.checkNotNullExpressionValue(var2, "mc.thePlayer.entityBoundingBox");
         if (!BlockUtils.collideBlockIntersects(var2, null.INSTANCE) || !MovementUtils.INSTANCE.isMoving()) {
            this.ticks = 0;
            return;
         }
      }

      if (Intrinsics.areEqual((Object)this.modeValue.get(), (Object)"AAC4") && (MinecraftInstance.mc.field_71439_g.field_70181_x < (double)0.0F || MinecraftInstance.mc.field_71439_g.field_70122_E)) {
         this.glitch = true;
      }

      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         this.groundHeight = MinecraftInstance.mc.field_71439_g.field_70163_u;
      }

      String var4 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var4.hashCode()) {
         case -1068318794:
            if (var4.equals("motion")) {
               MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.motionValue.get()).floatValue();
            }
            break;
         case -805359837:
            if (var4.equals("vulcan")) {
               if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  this.ticks = 0;
                  MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
               }

               if (this.ticks >= 3) {
                  this.ticks = 0;
               }

               int var5 = this.ticks++;
               switch (this.ticks) {
                  case 2:
                  case 3:
                     MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
                     MovementUtils.INSTANCE.resetMotion(false);
               }
            }
            break;
         case 2986065:
            if (var4.equals("aac4") && MinecraftInstance.mc.field_71439_g.field_70122_E) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
               this.wasTimer = true;
               MinecraftInstance.mc.field_71428_T.field_74278_d = 0.4F;
            }
            break;
         case 742313909:
            if (var4.equals("checker") && MinecraftInstance.mc.field_71439_g.field_70123_F && MinecraftInstance.mc.field_71439_g.field_70122_E) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            }
            break;
         case 949448766:
            if (var4.equals("collide") && MinecraftInstance.mc.field_71439_g.field_70122_E) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            }
            break;
         case 1492139162:
            if (var4.equals("aac3.3.12")) {
               if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  this.ticks = 0;
               }

               int var3 = this.ticks++;
               switch (this.ticks) {
                  case 1:
                  case 12:
                  case 23:
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.43;
                     break;
                  case 29:
                     MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)0.5F, MinecraftInstance.mc.field_71439_g.field_70161_v);
                     break;
                  default:
                     if (this.ticks >= 30) {
                        this.ticks = 0;
                     }
               }
            }
      }

   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      AxisAlignedBB var3 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
      Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer.entityBoundingBox");
      boolean isInsideBlock = BlockUtils.collideBlockIntersects(var3, null.INSTANCE);
      if (isInsideBlock && Intrinsics.areEqual((Object)this.modeValue.get(), (Object)"Checker") && (double)MinecraftInstance.mc.field_71439_g.field_71158_b.field_78900_b > (double)0.0F) {
         event.setX((double)0.0F);
         event.setZ((double)0.0F);
         event.setY((double)((Number)this.motionValue.get()).floatValue());
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C03PacketPlayer && this.glitch) {
         this.glitch = false;
         float yaw = (float)MovementUtils.INSTANCE.getDirection();
         ((C03PacketPlayer)packet).field_149479_a -= (double)((float)Math.sin((double)yaw)) * 1.0E-8;
         ((C03PacketPlayer)packet).field_149478_c += (double)((float)Math.cos((double)yaw)) * 1.0E-8;
      }

      if (packet instanceof C03PacketPlayer && Intrinsics.areEqual((Object)this.modeValue.get(), (Object)"Vulcan")) {
         switch (this.ticks) {
            case 2:
               ((C03PacketPlayer)packet).field_149474_g = true;
               break;
            case 3:
               float yaw = (float)MovementUtils.INSTANCE.getDirection();
               double randomModulo = Math.random() * 0.03 + 0.22;
               ((C03PacketPlayer)packet).field_149477_b -= 0.1;
               ((C03PacketPlayer)packet).field_149479_a += (double)((float)Math.sin((double)yaw)) * randomModulo;
               ((C03PacketPlayer)packet).field_149478_c -= (double)((float)Math.cos((double)yaw)) * randomModulo;
         }
      }

   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      this.wasTimer = false;
   }

   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (Intrinsics.areEqual((Object)this.modeValue.get(), (Object)"Checker")) {
         AxisAlignedBB var2 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
         Intrinsics.checkNotNullExpressionValue(var2, "mc.thePlayer.entityBoundingBox");
         if ((BlockUtils.collideBlockIntersects(var2, null.INSTANCE) || MinecraftInstance.mc.field_71439_g.field_70123_F) && (double)event.getY() > MinecraftInstance.mc.field_71439_g.field_70163_u) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a((double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F));
         }
      }

      if (MinecraftInstance.mc.field_71439_g.field_70123_F && MovementUtils.INSTANCE.isMoving()) {
         if (!(MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F)) {
            String var3 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (Intrinsics.areEqual((Object)var3, (Object)"collide") ? true : Intrinsics.areEqual((Object)var3, (Object)"aac4")) {
               event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)event.getX() + (double)1.0F, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), (double)event.getZ() + (double)1.0F));
            }

         }
      }
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }
}
