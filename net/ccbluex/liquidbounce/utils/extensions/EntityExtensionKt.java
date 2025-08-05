package net.ccbluex.liquidbounce.utils.extensions;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3d;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MathUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\u0084\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0016\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u0012\u001a\u0012\u0010,\u001a\u00020-*\u00020\u00012\u0006\u0010.\u001a\u00020\u0012\u001a\u0012\u0010/\u001a\u00020-*\u00020\u00022\u0006\u00100\u001a\u00020\u0002\u001a\"\u00101\u001a\b\u0012\u0004\u0012\u00020\u000202*\u0002032\u0006\u00100\u001a\u00020\u00022\b\b\u0002\u00104\u001a\u00020-\u001a\n\u00105\u001a\u00020\u0001*\u000206\u001a*\u00107\u001a\u00020-*\u00020\u00022\b\b\u0002\u00100\u001a\u00020\u00022\n\b\u0002\u00108\u001a\u0004\u0018\u0001092\b\b\u0002\u0010:\u001a\u00020-\u001a$\u0010;\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006\u001a$\u0010>\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001c\u0010>\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-2\u0006\u00108\u001a\u000209\u001a\u0014\u0010?\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-\u001a\u0012\u0010@\u001a\u00020A*\u00020\b2\u0006\u0010B\u001a\u00020C\u001a'\u0010D\u001a\u00020E*\u00020\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010F\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"(\u0010\u0007\u001a\u00020\u0006*\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\"(\u0010\u000e\u001a\u00020\u0006*\u00020\b2\u0006\u0010\r\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\f\"\u0015\u0010\u0011\u001a\u00020\u0012*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u0015\u0010\u0015\u001a\u00020\u0006*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\"\u0015\u0010\u0019\u001a\u00020\u001a*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\"\u0015\u0010\u001d\u001a\u00020\u0012*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0014\"\u0015\u0010\u001f\u001a\u00020\u0006*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b \u0010\u0018\"\u0015\u0010!\u001a\u00020\"*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b#\u0010$\"\u0015\u0010%\u001a\u00020&*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b'\u0010(¨\u0006G"},
   d2 = {"eyesLoc", "Lnet/minecraft/util/Vec3;", "Lnet/minecraft/entity/Entity;", "getEyesLoc", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/Vec3;", "pitch", "", "fixedSensitivityPitch", "Lnet/minecraft/client/entity/EntityPlayerSP;", "getFixedSensitivityPitch", "(Lnet/minecraft/client/entity/EntityPlayerSP;)F", "setFixedSensitivityPitch", "(Lnet/minecraft/client/entity/EntityPlayerSP;F)V", "yaw", "fixedSensitivityYaw", "getFixedSensitivityYaw", "setFixedSensitivityYaw", "hitBox", "Lnet/minecraft/util/AxisAlignedBB;", "getHitBox", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/AxisAlignedBB;", "hurtPercent", "Lnet/minecraft/entity/EntityLivingBase;", "getHurtPercent", "(Lnet/minecraft/entity/EntityLivingBase;)F", "ping", "", "getPing", "(Lnet/minecraft/entity/EntityLivingBase;)I", "renderBoundingBox", "getRenderBoundingBox", "renderHurtTime", "getRenderHurtTime", "renderPos", "Ljavax/vecmath/Vector3d;", "getRenderPos", "(Lnet/minecraft/entity/Entity;)Ljavax/vecmath/Vector3d;", "skin", "Lnet/minecraft/util/ResourceLocation;", "getSkin", "(Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/util/ResourceLocation;", "getNearestPointBB", "eye", "box", "distanceTo", "", "bb", "getDistanceToEntityBox", "entity", "getEntitiesInRadius", "", "Lnet/minecraft/world/World;", "radius", "getEyeVec3", "Lnet/minecraft/entity/player/EntityPlayer;", "getLookDistanceToEntityBox", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "range", "rayTraceCustom", "Lnet/minecraft/util/MovingObjectPosition;", "blockReachDistance", "rayTraceWithCustomRotation", "rayTraceWithServerSideRotation", "sendUseItem", "", "stack", "Lnet/minecraft/item/ItemStack;", "setFixedSensitivityAngles", "", "(Lnet/minecraft/client/entity/EntityPlayerSP;Ljava/lang/Float;Ljava/lang/Float;)V", "CrossSine"}
)
public final class EntityExtensionKt {
   public static final double getDistanceToEntityBox(@NotNull Entity $this$getDistanceToEntityBox, @NotNull Entity entity) {
      Intrinsics.checkNotNullParameter($this$getDistanceToEntityBox, "<this>");
      Intrinsics.checkNotNullParameter(entity, "entity");
      Vec3 eyes = $this$getDistanceToEntityBox.func_174824_e(0.0F);
      Intrinsics.checkNotNullExpressionValue(eyes, "eyes");
      Vec3 pos = getNearestPointBB(eyes, getHitBox(entity));
      return eyes.func_72438_d(pos);
   }

   @NotNull
   public static final Vec3 getNearestPointBB(@NotNull Vec3 eye, @NotNull AxisAlignedBB box) {
      Intrinsics.checkNotNullParameter(eye, "eye");
      Intrinsics.checkNotNullParameter(box, "box");
      double[] destMins = new double[]{eye.field_72450_a, eye.field_72448_b, eye.field_72449_c};
      double[] origin = destMins;
      double[] destMaxs = new double[]{box.field_72340_a, box.field_72338_b, box.field_72339_c};
      destMins = destMaxs;
      double[] var5 = new double[]{box.field_72336_d, box.field_72337_e, box.field_72334_f};
      destMaxs = var5;
      int var9 = 0;

      while(var9 < 3) {
         int i = var9++;
         if (origin[i] > destMaxs[i]) {
            origin[i] = destMaxs[i];
         } else if (origin[i] < destMins[i]) {
            origin[i] = destMins[i];
         }
      }

      return new Vec3(origin[0], origin[1], origin[2]);
   }

   @Nullable
   public static final MovingObjectPosition rayTraceWithCustomRotation(@NotNull Entity $this$rayTraceWithCustomRotation, double blockReachDistance, float yaw, float pitch) {
      Intrinsics.checkNotNullParameter($this$rayTraceWithCustomRotation, "<this>");
      Vec3 vec3 = $this$rayTraceWithCustomRotation.func_174824_e(1.0F);
      Vec3 vec31 = $this$rayTraceWithCustomRotation.func_174806_f(pitch, yaw);
      Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * blockReachDistance, vec31.field_72448_b * blockReachDistance, vec31.field_72449_c * blockReachDistance);
      return $this$rayTraceWithCustomRotation.field_70170_p.func_147447_a(vec3, vec32, false, false, true);
   }

   @Nullable
   public static final MovingObjectPosition rayTraceWithCustomRotation(@NotNull Entity $this$rayTraceWithCustomRotation, double blockReachDistance, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter($this$rayTraceWithCustomRotation, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      return rayTraceWithCustomRotation($this$rayTraceWithCustomRotation, blockReachDistance, rotation.getYaw(), rotation.getPitch());
   }

   @Nullable
   public static final MovingObjectPosition rayTraceWithServerSideRotation(@NotNull Entity $this$rayTraceWithServerSideRotation, double blockReachDistance) {
      Intrinsics.checkNotNullParameter($this$rayTraceWithServerSideRotation, "<this>");
      Rotation var3 = RotationUtils.serverRotation;
      Intrinsics.checkNotNullExpressionValue(var3, "serverRotation");
      return rayTraceWithCustomRotation($this$rayTraceWithServerSideRotation, blockReachDistance, var3);
   }

   public static final void setFixedSensitivityAngles(@NotNull EntityPlayerSP $this$setFixedSensitivityAngles, @Nullable Float yaw, @Nullable Float pitch) {
      Intrinsics.checkNotNullParameter($this$setFixedSensitivityAngles, "<this>");
      if (yaw != null) {
         setFixedSensitivityYaw($this$setFixedSensitivityAngles, yaw);
      }

      if (pitch != null) {
         setFixedSensitivityPitch($this$setFixedSensitivityAngles, pitch);
      }

   }

   // $FF: synthetic method
   public static void setFixedSensitivityAngles$default(EntityPlayerSP var0, Float var1, Float var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = null;
      }

      if ((var3 & 2) != 0) {
         var2 = null;
      }

      setFixedSensitivityAngles(var0, var1, var2);
   }

   public static final float getFixedSensitivityYaw(@NotNull EntityPlayerSP $this$fixedSensitivityYaw) {
      Intrinsics.checkNotNullParameter($this$fixedSensitivityYaw, "<this>");
      return Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, ClientUtils.INSTANCE.getMc().field_71439_g.field_70177_z, 0.0F, 0.0F, 6, (Object)null);
   }

   public static final void setFixedSensitivityYaw(@NotNull EntityPlayerSP $this$fixedSensitivityYaw, float yaw) {
      Intrinsics.checkNotNullParameter($this$fixedSensitivityYaw, "<this>");
      $this$fixedSensitivityYaw.field_70177_z = Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, yaw, $this$fixedSensitivityYaw.field_70177_z, 0.0F, 4, (Object)null);
   }

   public static final float getFixedSensitivityPitch(@NotNull EntityPlayerSP $this$fixedSensitivityPitch) {
      Intrinsics.checkNotNullParameter($this$fixedSensitivityPitch, "<this>");
      return Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, $this$fixedSensitivityPitch.field_70125_A, 0.0F, 0.0F, 6, (Object)null);
   }

   public static final void setFixedSensitivityPitch(@NotNull EntityPlayerSP $this$fixedSensitivityPitch, float pitch) {
      Intrinsics.checkNotNullParameter($this$fixedSensitivityPitch, "<this>");
      $this$fixedSensitivityPitch.field_70125_A = Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, RangesKt.coerceIn(pitch, -90.0F, 90.0F), $this$fixedSensitivityPitch.field_70125_A, 0.0F, 4, (Object)null);
   }

   public static final boolean sendUseItem(@NotNull EntityPlayerSP $this$sendUseItem, @NotNull ItemStack stack) {
      Intrinsics.checkNotNullParameter($this$sendUseItem, "<this>");
      Intrinsics.checkNotNullParameter(stack, "stack");
      if (ClientUtils.INSTANCE.getMc().field_71442_b.func_78747_a()) {
         return false;
      } else {
         PacketUtils.sendPacketNoEvent((Packet)(new C08PacketPlayerBlockPlacement(stack)));
         int prevSize = stack.field_77994_a;
         ItemStack newStack = stack.func_77957_a($this$sendUseItem.field_70170_p, (EntityPlayer)$this$sendUseItem);
         boolean var10000;
         if (Intrinsics.areEqual((Object)newStack, (Object)stack) && newStack.field_77994_a == prevSize) {
            var10000 = false;
         } else {
            if (newStack.field_77994_a <= 0) {
               ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70462_a[ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70461_c] = null;
               ForgeEventFactory.onPlayerDestroyItem((EntityPlayer)ClientUtils.INSTANCE.getMc().field_71439_g, newStack);
            } else {
               ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70462_a[ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70461_c] = newStack;
            }

            var10000 = true;
         }

         return var10000;
      }
   }

   @NotNull
   public static final Vec3 getEyeVec3(@NotNull EntityPlayer $this$getEyeVec3) {
      Intrinsics.checkNotNullParameter($this$getEyeVec3, "<this>");
      return new Vec3($this$getEyeVec3.field_70165_t, $this$getEyeVec3.func_174813_aQ().field_72338_b + (double)$this$getEyeVec3.func_70047_e(), $this$getEyeVec3.field_70161_v);
   }

   public static final float getRenderHurtTime(@NotNull EntityLivingBase $this$renderHurtTime) {
      Intrinsics.checkNotNullParameter($this$renderHurtTime, "<this>");
      return (float)$this$renderHurtTime.field_70737_aN - ($this$renderHurtTime.field_70737_aN != 0 ? Minecraft.func_71410_x().field_71428_T.field_74281_c : 0.0F);
   }

   public static final float getHurtPercent(@NotNull EntityLivingBase $this$hurtPercent) {
      Intrinsics.checkNotNullParameter($this$hurtPercent, "<this>");
      return getRenderHurtTime($this$hurtPercent) / (float)10;
   }

   @NotNull
   public static final ResourceLocation getSkin(@NotNull EntityLivingBase $this$skin) {
      Intrinsics.checkNotNullParameter($this$skin, "<this>");
      ResourceLocation var2;
      if ($this$skin instanceof EntityPlayer) {
         NetworkPlayerInfo var10000 = Minecraft.func_71410_x().func_147114_u().func_175102_a(((EntityPlayer)$this$skin).func_110124_au());
         var2 = var10000 == null ? null : var10000.func_178837_g();
      } else {
         var2 = (ResourceLocation)null;
      }

      if (var2 == null) {
         ResourceLocation var1 = DefaultPlayerSkin.func_177335_a();
         Intrinsics.checkNotNullExpressionValue(var1, "getDefaultSkinLegacy()");
         var2 = var1;
      }

      return var2;
   }

   public static final int getPing(@NotNull EntityLivingBase $this$ping) {
      Intrinsics.checkNotNullParameter($this$ping, "<this>");
      Integer var2;
      if ($this$ping instanceof EntityPlayer) {
         NetworkPlayerInfo var10000 = Minecraft.func_71410_x().func_147114_u().func_175102_a(((EntityPlayer)$this$ping).func_110124_au());
         if (var10000 == null) {
            var2 = null;
         } else {
            int var1 = var10000.func_178853_c();
            var2 = RangesKt.coerceAtLeast(var1, 0);
         }
      } else {
         var2 = (Integer)null;
      }

      return var2 == null ? -1 : var2;
   }

   @NotNull
   public static final Vector3d getRenderPos(@NotNull Entity $this$renderPos) {
      Intrinsics.checkNotNullParameter($this$renderPos, "<this>");
      double x = MathUtils.INSTANCE.interpolate($this$renderPos.field_70142_S, $this$renderPos.field_70165_t, (double)ClientUtils.INSTANCE.getMc().field_71428_T.field_74281_c) - ClientUtils.INSTANCE.getMc().func_175598_ae().field_78730_l;
      double y = MathUtils.INSTANCE.interpolate($this$renderPos.field_70137_T, $this$renderPos.field_70163_u, (double)ClientUtils.INSTANCE.getMc().field_71428_T.field_74281_c) - ClientUtils.INSTANCE.getMc().func_175598_ae().field_78731_m;
      double z = MathUtils.INSTANCE.interpolate($this$renderPos.field_70136_U, $this$renderPos.field_70161_v, (double)ClientUtils.INSTANCE.getMc().field_71428_T.field_74281_c) - ClientUtils.INSTANCE.getMc().func_175598_ae().field_78728_n;
      return new Vector3d(x, y, z);
   }

   @NotNull
   public static final AxisAlignedBB getRenderBoundingBox(@NotNull Entity $this$renderBoundingBox) {
      Intrinsics.checkNotNullParameter($this$renderBoundingBox, "<this>");
      AxisAlignedBB var1 = $this$renderBoundingBox.func_174813_aQ().func_72317_d(-$this$renderBoundingBox.field_70165_t, -$this$renderBoundingBox.field_70163_u, -$this$renderBoundingBox.field_70161_v).func_72317_d(getRenderPos($this$renderBoundingBox).x, getRenderPos($this$renderBoundingBox).y, getRenderPos($this$renderBoundingBox).z);
      Intrinsics.checkNotNullExpressionValue(var1, "this.entityBoundingBox\n …rPos.y, this.renderPos.z)");
      return var1;
   }

   @NotNull
   public static final AxisAlignedBB getHitBox(@NotNull Entity $this$hitBox) {
      Intrinsics.checkNotNullParameter($this$hitBox, "<this>");
      double borderSize = (double)$this$hitBox.func_70111_Y();
      AxisAlignedBB var3 = $this$hitBox.func_174813_aQ().func_72314_b(borderSize, borderSize, borderSize);
      Intrinsics.checkNotNullExpressionValue(var3, "entityBoundingBox.expand…, borderSize, borderSize)");
      return var3;
   }

   @Nullable
   public static final MovingObjectPosition rayTraceCustom(@NotNull Entity $this$rayTraceCustom, double blockReachDistance, float yaw, float pitch) {
      Intrinsics.checkNotNullParameter($this$rayTraceCustom, "<this>");
      Vec3 vec3 = ClientUtils.INSTANCE.getMc().field_71439_g.func_174824_e(1.0F);
      Vec3 vec31 = ClientUtils.INSTANCE.getMc().field_71439_g.func_174806_f(yaw, pitch);
      Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * blockReachDistance, vec31.field_72448_b * blockReachDistance, vec31.field_72449_c * blockReachDistance);
      return ClientUtils.INSTANCE.getMc().field_71441_e.func_147447_a(vec3, vec32, false, false, true);
   }

   @NotNull
   public static final List getEntitiesInRadius(@NotNull final World $this$getEntitiesInRadius, @NotNull Entity entity, double radius) {
      Intrinsics.checkNotNullParameter($this$getEntitiesInRadius, "<this>");
      Intrinsics.checkNotNullParameter(entity, "entity");
      AxisAlignedBB box = entity.func_174813_aQ().func_72314_b(radius, radius, radius);
      int chunkMinX = (int)Math.floor(box.field_72340_a * (double)0.0625F);
      int chunkMaxX = (int)Math.ceil(box.field_72336_d * (double)0.0625F);
      int chunkMinZ = (int)Math.floor(box.field_72339_c * (double)0.0625F);
      int chunkMaxZ = (int)Math.ceil(box.field_72334_f * (double)0.0625F);
      List entities = (List)(new ArrayList());
      Iterable $this$forEach$iv = new IntRange(chunkMinX, chunkMaxX);
      int $i$f$forEach = 0;
      Iterator var12 = $this$forEach$iv.iterator();

      while(var12.hasNext()) {
         final int element$iv = ((IntIterator)var12).nextInt();
         int var15 = 0;
         Sequence $this$forEach$iv = SequencesKt.filter(SequencesKt.map(CollectionsKt.asSequence(new IntRange(chunkMinZ, chunkMaxZ)), new Function1() {
            public final Chunk invoke(int z) {
               return $this$getEntitiesInRadius.func_72964_e(element$iv, z);
            }
         }), null.INSTANCE);
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            Chunk it = (Chunk)element$iv;
            int var21 = 0;
            it.func_177414_a(entity, box, entities, (Predicate)null);
         }
      }

      return entities;
   }

   // $FF: synthetic method
   public static List getEntitiesInRadius$default(World var0, Entity var1, double var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = (double)16.0F;
      }

      return getEntitiesInRadius(var0, var1, var2);
   }

   public static final double getLookDistanceToEntityBox(@NotNull Entity $this$getLookDistanceToEntityBox, @NotNull Entity entity, @Nullable Rotation rotation, double range) {
      Intrinsics.checkNotNullParameter($this$getLookDistanceToEntityBox, "<this>");
      Intrinsics.checkNotNullParameter(entity, "entity");
      Vec3 eyes = $this$getLookDistanceToEntityBox.func_174824_e(1.0F);
      Rotation var10000 = rotation;
      if (rotation == null) {
         var10000 = RotationUtils.targetRotation;
      }

      Vec3 end = OtherExtensionKt.multiply(var10000.toDirection(), range).func_178787_e(eyes);
      MovingObjectPosition var9 = entity.func_174813_aQ().func_72327_a(eyes, end);
      double var10;
      if (var9 == null) {
         var10 = Double.MAX_VALUE;
      } else {
         Vec3 var11 = var9.field_72307_f;
         if (var11 == null) {
            var10 = Double.MAX_VALUE;
         } else {
            double var7 = var11.func_72438_d(eyes);
            var10 = var7;
         }
      }

      return var10;
   }

   // $FF: synthetic method
   public static double getLookDistanceToEntityBox$default(Entity var0, Entity var1, Rotation var2, double var3, int var5, Object var6) {
      if ((var5 & 1) != 0) {
         var1 = var0;
      }

      if ((var5 & 2) != 0) {
         var2 = null;
      }

      if ((var5 & 4) != 0) {
         var3 = (double)10.0F;
      }

      return getLookDistanceToEntityBox(var0, var1, var2, var3);
   }

   @NotNull
   public static final Vec3 getEyesLoc(@NotNull Entity $this$eyesLoc) {
      Intrinsics.checkNotNullParameter($this$eyesLoc, "<this>");
      Vec3 var1 = $this$eyesLoc.func_174824_e(1.0F);
      Intrinsics.checkNotNullExpressionValue(var1, "getPositionEyes(1f)");
      return var1;
   }

   public static final double distanceTo(@NotNull Vec3 $this$distanceTo, @NotNull AxisAlignedBB bb) {
      Intrinsics.checkNotNullParameter($this$distanceTo, "<this>");
      Intrinsics.checkNotNullParameter(bb, "bb");
      Vec3 pos = getNearestPointBB($this$distanceTo, bb);
      double xDist = Math.abs(pos.field_72450_a - $this$distanceTo.field_72450_a);
      double yDist = Math.abs(pos.field_72448_b - $this$distanceTo.field_72448_b);
      double zDist = Math.abs(pos.field_72449_c - $this$distanceTo.field_72449_c);
      return Math.sqrt(Math.pow(xDist, (double)2) + Math.pow(yDist, (double)2) + Math.pow(zDist, (double)2));
   }
}
