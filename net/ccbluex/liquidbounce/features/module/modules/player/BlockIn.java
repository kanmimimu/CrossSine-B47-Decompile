package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "BlockIn",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$H\u0002J\b\u0010\u0006\u001a\u00020&H\u0002J\u000e\u0010'\u001a\b\u0012\u0004\u0012\u00020$0(H\u0002J(\u0010)\u001a\u00020\r2\u0006\u0010*\u001a\u00020+2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010,\u001a\u00020-H\u0002J\b\u0010.\u001a\u00020&H\u0016J\b\u0010/\u001a\u00020&H\u0016J\u0010\u00100\u001a\u00020&2\u0006\u00101\u001a\u000202H\u0007J\u0010\u00103\u001a\u00020&2\u0006\u00101\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020&2\u0006\u00101\u001a\u000206H\u0007J\u0010\u00107\u001a\u00020&2\u0006\u00101\u001a\u000208H\u0007J\b\u00109\u001a\u00020&H\u0002J\u0010\u0010:\u001a\u00020\r2\u0006\u0010;\u001a\u00020+H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/BlockIn;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoPosition", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "blockPlaceDelay", "findBlock", "getDelay", "", "getGetDelay", "()J", "hitable", "jump", "", "lastPlace", "", "placeDelay", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/ranges/IntRange;", "placeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "rotationSpeedValue", "rotationValue", "silentRotationValue", "sneakValue", "speedValue", "", "swingValue", "targetPlace", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "turnSpeedValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "calculateRotation", "eyesPos", "Lnet/minecraft/util/Vec3;", "hitVec", "", "generateOffsets", "Lkotlin/sequences/Sequence;", "isValidBlockRotation", "neighbor", "Lnet/minecraft/util/BlockPos;", "facing", "Lnet/minecraft/util/EnumFacing;", "onDisable", "onEnable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "place", "search", "blockPosition", "CrossSine"}
)
public final class BlockIn extends Module {
   @NotNull
   public static final BlockIn INSTANCE = new BlockIn();
   @NotNull
   private static final BoolValue hitable = new BoolValue("Hitable", false);
   @NotNull
   private static final BoolValue swingValue = new BoolValue("Swing", false);
   @NotNull
   private static final BoolValue rotationValue = new BoolValue("Rotation", false);
   @NotNull
   private static final Value silentRotationValue;
   @NotNull
   private static final BoolValue sneakValue;
   @NotNull
   private static final BoolValue blockPlaceDelay;
   @NotNull
   private static final BoolValue findBlock;
   @NotNull
   private static final BoolValue autoPosition;
   @NotNull
   private static final Value speedValue;
   @NotNull
   private static final Value placeDelay;
   @NotNull
   private static final Value rotationSpeedValue;
   @NotNull
   private static final IntegerRangeValue turnSpeedValue;
   @Nullable
   private static Rotation rotation;
   @Nullable
   private static PlaceInfo targetPlace;
   @NotNull
   private static final TimerMS placeTimer;
   private static int lastPlace;
   private static boolean jump;

   private BlockIn() {
   }

   public void onEnable() {
      if (InventoryUtils.INSTANCE.findAutoBlockBlock(true) == -1) {
         ClientUtils.INSTANCE.displayAlert("BlockIn : NO BLOCK FOUND");
         this.setState(false);
      }

      jump = false;
      if ((Boolean)sneakValue.get() && !MinecraftInstance.mc.field_71439_g.func_70093_af()) {
         ClientUtils.INSTANCE.displayAlert("Sneak before enable module");
         this.setState(false);
      }

   }

   public void onDisable() {
      SlotUtils.INSTANCE.stopSet();
      jump = false;
      targetPlace = null;
      rotation = null;
      this.setState(false);
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C08PacketPlayerBlockPlacement) {
         ((C08PacketPlayerBlockPlacement)packet).field_149577_f = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149577_f, -1.0F, 1.0F);
         ((C08PacketPlayerBlockPlacement)packet).field_149578_g = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149578_g, -1.0F, 1.0F);
         ((C08PacketPlayerBlockPlacement)packet).field_149584_h = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149584_h, -1.0F, 1.0F);
      }

   }

   @EventTarget
   public final void onPreUpdate(@NotNull PreUpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.findBlock();
      if ((Boolean)findBlock.get()) {
         if (InventoryUtils.INSTANCE.findAutoBlockBlock(true) == -1) {
            return;
         }

         SlotUtils.INSTANCE.setSlot(InventoryUtils.INSTANCE.findAutoBlockBlock(true) - 36, true, this.getName());
         MinecraftInstance.mc.field_71442_b.func_78765_e();
      }

   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)autoPosition.get()) {
         PlayerUtils.INSTANCE.setCorrectBlockPos(((Number)speedValue.get()).floatValue());
      }

   }

   @EventTarget
   public final void onTick(@NotNull TickEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (lastPlace == 1) {
         MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G));
         lastPlace = 0;
      }

      if ((Boolean)sneakValue.get() && !MinecraftInstance.mc.field_71439_g.func_70093_af()) {
         this.onDisable();
      }

      if (!(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(-1, 0, 0)) instanceof BlockAir) && !(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(1, 0, 0)) instanceof BlockAir) && !(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(0, 0, -1)) instanceof BlockAir) && !(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(0, 0, 1)) instanceof BlockAir) && !(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(-1, 1, 0)) instanceof BlockAir) && !(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(1, 1, 0)) instanceof BlockAir) && !(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(0, 1, -1)) instanceof BlockAir) && !(BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(0, 1, 1)) instanceof BlockAir)) {
         if (BlockUtils.getBlock((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(0, 2, 0)) instanceof BlockAir) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && !jump) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
               jump = true;
            }
         } else {
            this.onDisable();
         }
      }

      this.place();
   }

   private final void place() {
      if (!(Boolean)blockPlaceDelay.get() || placeTimer.hasTimePassed(this.getGetDelay())) {
         if ((Boolean)hitable.get()) {
            BlockPos var10000 = MinecraftInstance.mc.field_71476_x.func_178782_a();
            PlaceInfo var10001 = targetPlace;
            Intrinsics.checkNotNull(var10001);
            if (!Intrinsics.areEqual((Object)var10000, (Object)var10001.getBlockPos())) {
               return;
            }

            EnumFacing var2 = MinecraftInstance.mc.field_71476_x.field_178784_b;
            var10001 = targetPlace;
            Intrinsics.checkNotNull(var10001);
            if (var2 != var10001.getEnumFacing()) {
               return;
            }
         }

         if ((Boolean)findBlock.get() && InventoryUtils.INSTANCE.findAutoBlockBlock(true) != -1 || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
            PlayerControllerMP var3 = MinecraftInstance.mc.field_71442_b;
            EntityPlayerSP var5 = MinecraftInstance.mc.field_71439_g;
            WorldClient var10002 = MinecraftInstance.mc.field_71441_e;
            ItemStack var10003 = MinecraftInstance.mc.field_71439_g.func_70694_bm();
            PlaceInfo var10004 = targetPlace;
            Intrinsics.checkNotNull(var10004);
            BlockPos var6 = var10004.getBlockPos();
            PlaceInfo var10005 = targetPlace;
            Intrinsics.checkNotNull(var10005);
            EnumFacing var7 = var10005.getEnumFacing();
            PlaceInfo var10006 = targetPlace;
            Intrinsics.checkNotNull(var10006);
            if (var3.func_178890_a(var5, var10002, var10003, var6, var7, var10006.getVec3())) {
               if ((Boolean)swingValue.get()) {
                  MinecraftInstance.mc.field_71439_g.func_71038_i();
               } else {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
               }

               CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
               MouseUtils.INSTANCE.setRightClicked(true);
               placeTimer.reset();
               int var1 = lastPlace++;
            }
         }

         targetPlace = null;
      }

   }

   private final long getGetDelay() {
      return TimeUtils.INSTANCE.randomDelay(((IntRange)placeDelay.get()).getFirst(), ((IntRange)placeDelay.get()).getLast());
   }

   private final void findBlock() {
      BlockPos blockPos = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(-1, 1, 0), new BlockPos(1, 1, 0), new BlockPos(0, 1, -1), new BlockPos(0, 1, 1), new BlockPos(0, 2, 0)};
      List offsets = CollectionsKt.listOf(blockPos);
      Iterable $this$map$iv = (Iterable)offsets;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         BlockPos offset = (BlockPos)item$iv$iv;
         int var11 = 0;
         destination$iv$iv.add((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177982_a(offset.func_177958_n(), offset.func_177956_o(), offset.func_177952_p()));
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      Iterator $this$mapTo$iv$iv = $this$map$iv.iterator();

      Object var10000;
      while(true) {
         if ($this$mapTo$iv$iv.hasNext()) {
            Object element$iv = $this$mapTo$iv$iv.next();
            BlockPos pos = (BlockPos)element$iv;
            int var23 = 0;
            if (!(BlockUtils.getBlock(pos) instanceof BlockAir)) {
               continue;
            }

            var10000 = element$iv;
            break;
         }

         var10000 = null;
         break;
      }

      blockPos = (BlockPos)var10000;
      if (blockPos != null) {
         if (!BlockUtils.isReplaceable(blockPos) || this.search(blockPos)) {
            return;
         }

         int var15 = -1;

         while(var15 < 2) {
            $i$f$map = var15++;
            int var18 = -1;

            while(var18 < 2) {
               int z = var18++;
               BlockPos var22 = blockPos.func_177982_a($i$f$map, 0, z);
               Intrinsics.checkNotNullExpressionValue(var22, "blockPos.add(x, 0, z)");
               if (this.search(var22)) {
                  return;
               }
            }
         }
      }

   }

   private final boolean search(BlockPos blockPosition) {
      BlockPos blockPos = blockPosition;
      if (!BlockUtils.isReplaceable(blockPosition)) {
         return false;
      } else {
         Vec3 eyesPos = new Vec3(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)MinecraftInstance.mc.field_71439_g.func_70047_e(), MinecraftInstance.mc.field_71439_g.field_70161_v);
         PlaceRotation placeRotation = null;
         Set checkedPositions = (Set)(new LinkedHashSet());
         EnumFacing[] var7 = StaticStorage.facings();
         Intrinsics.checkNotNullExpressionValue(var7, "facings()");
         EnumFacing[] var6 = var7;
         int var19 = 0;
         int var8 = var7.length;

         while(var19 < var8) {
            EnumFacing side = var6[var19];
            ++var19;
            BlockPos neighbor = blockPos.func_177972_a(side);
            if (BlockUtils.canBeClicked(neighbor)) {
               Vec3 dirVec = new Vec3(side.func_176730_m());

               for(Vec3 offset : this.generateOffsets()) {
                  Vec3 posVec = (new Vec3((Vec3i)blockPos)).func_178787_e(offset);
                  if (!checkedPositions.contains(posVec)) {
                     Intrinsics.checkNotNullExpressionValue(posVec, "posVec");
                     checkedPositions.add(posVec);
                     Vec3 hitVec = posVec.func_178787_e(new Vec3(dirVec.field_72450_a * (double)0.5F, dirVec.field_72448_b * (double)0.5F, dirVec.field_72449_c * (double)0.5F));
                     Intrinsics.checkNotNullExpressionValue(hitVec, "hitVec");
                     Rotation rotation = this.calculateRotation(eyesPos, hitVec);
                     Intrinsics.checkNotNullExpressionValue(neighbor, "neighbor");
                     EnumFacing var17 = side.func_176734_d();
                     Intrinsics.checkNotNullExpressionValue(var17, "side.opposite");
                     if (this.isValidBlockRotation(neighbor, eyesPos, rotation, var17) && (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation()))) {
                        var17 = side.func_176734_d();
                        Intrinsics.checkNotNullExpressionValue(var17, "side.opposite");
                        placeRotation = new PlaceRotation(new PlaceInfo(neighbor, var17, hitVec), rotation);
                     }
                  }
               }
            }
         }

         if (placeRotation == null) {
            return false;
         } else {
            BlockIn.rotation = placeRotation.getRotation();
            double diffAngle = RotationUtils.getRotationDifference(RotationUtils.serverRotation, BlockIn.rotation);
            if (diffAngle < (double)0.0F) {
               diffAngle = -diffAngle;
            }

            if (diffAngle > (double)180.0F) {
               diffAngle = (double)180.0F;
            }

            Rotation rotationSmooth = RotationUtils.limitAngleChange(RotationUtils.serverRotation, BlockIn.rotation, (float)(diffAngle / (double)360 * (double)turnSpeedValue.get().getLast() + ((double)1 - diffAngle / (double)360) * (double)turnSpeedValue.get().getFirst()));
            Intrinsics.checkNotNullExpressionValue(rotationSmooth, "limitAngleChange(\n      …).toFloat()\n            )");
            if ((Boolean)rotationValue.get()) {
               if ((Boolean)silentRotationValue.get()) {
                  RotationUtils.setTargetRotation((Boolean)rotationSpeedValue.get() ? rotationSmooth : BlockIn.rotation, 1);
               } else {
                  Rotation var10000 = (Boolean)rotationSpeedValue.get() ? rotationSmooth : BlockIn.rotation;
                  Intrinsics.checkNotNull(var10000);
                  EntityPlayerSP rotationSmooth = MinecraftInstance.mc.field_71439_g;
                  Intrinsics.checkNotNullExpressionValue(rotationSmooth, "mc.thePlayer");
                  var10000.toPlayer((EntityPlayer)rotationSmooth);
               }
            }

            targetPlace = placeRotation.getPlaceInfo();
            return true;
         }
      }
   }

   private final Sequence generateOffsets() {
      return SequencesKt.sequence(new Function2((Continuation)null) {
         double D$0;
         double D$1;
         double D$2;
         double D$3;
         int label;
         // $FF: synthetic field
         private Object L$0;

         @Nullable
         public final Object invokeSuspend(@NotNull Object param1) {
            // $FF: Couldn't be decompiled
         }

         @NotNull
         public final Continuation create(@Nullable Object value, @NotNull Continuation $completion) {
            Function2 var3 = new <anonymous constructor>($completion);
            var3.L$0 = value;
            return (Continuation)var3;
         }

         @Nullable
         public final Object invoke(@NotNull SequenceScope p1, @Nullable Continuation p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }
      });
   }

   private final Rotation calculateRotation(Vec3 eyesPos, Vec3 hitVec) {
      double diffX = hitVec.field_72450_a - eyesPos.field_72450_a;
      double diffY = hitVec.field_72448_b - eyesPos.field_72448_b;
      double diffZ = hitVec.field_72449_c - eyesPos.field_72449_c;
      double diffXZ = (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
      return new Rotation(MathHelper.func_76142_g((float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - (double)90)), MathHelper.func_76142_g((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
   }

   private final boolean isValidBlockRotation(BlockPos neighbor, Vec3 eyesPos, Rotation rotation, EnumFacing facing) {
      Vec3 rotationVector = RotationUtils.getVectorForRotation(rotation);
      Vec3 vector = eyesPos.func_72441_c(rotationVector.field_72450_a * (double)4, rotationVector.field_72448_b * (double)4, rotationVector.field_72449_c * (double)4);
      MovingObjectPosition obj = MinecraftInstance.mc.field_71441_e.func_147447_a(eyesPos, vector, false, false, true);
      return obj.field_72313_a == MovingObjectType.BLOCK && Intrinsics.areEqual((Object)obj.func_178782_a(), (Object)neighbor) && obj.field_178784_b == facing;
   }

   static {
      silentRotationValue = (new BoolValue("SilentRotation", true)).displayable(null.INSTANCE);
      sneakValue = new BoolValue("Sneak", false);
      blockPlaceDelay = new BoolValue("PlaceDelay", false);
      findBlock = new BoolValue("AutoBlock", false);
      autoPosition = new BoolValue("AutoPosition", false);
      speedValue = (new FloatValue("Speed", 1.0F, 0.1F, 1.0F)).displayable(null.INSTANCE);
      placeDelay = (new IntegerRangeValue("PlaceDelay", 0, 0, 1000, 0, (Function0)null, 48, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      rotationSpeedValue = (new BoolValue("RotationSpeed", true)).displayable(null.INSTANCE);
      turnSpeedValue = new IntegerRangeValue("TurnSpeed", 180, 0, 180, 0, (Function0)null, 48, (DefaultConstructorMarker)null);
      placeTimer = new TimerMS();
   }
}
