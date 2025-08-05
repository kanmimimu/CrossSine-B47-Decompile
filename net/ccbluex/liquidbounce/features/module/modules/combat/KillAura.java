package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.SprintEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.MovementFix;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.player.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatRangeValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.CooldownHelper;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.WorldSettings.GameType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "KillAura",
   category = ModuleCategory.COMBAT,
   keyBind = 19
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000Í\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n*\u0001d\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020'2\u0006\u0010{\u001a\u00020\u0005H\u0002J\u0016\u0010|\u001a\u00020y2\u0006\u0010z\u001a\u00020'2\u0006\u0010}\u001a\u00020~J\u001a\u0010\u007f\u001a\u00020\t2\u0007\u0010\u0080\u0001\u001a\u00020\u00072\u0007\u0010\u0081\u0001\u001a\u00020\u0007H\u0002J\u0011\u0010\u0082\u0001\u001a\u00020\u00052\u0006\u0010z\u001a\u00020'H\u0002J\t\u0010\u0083\u0001\u001a\u00020\u0007H\u0002J\t\u0010\u0084\u0001\u001a\u00020\u0007H\u0002J\t\u0010\u0085\u0001\u001a\u00020yH\u0016J\t\u0010\u0086\u0001\u001a\u00020yH\u0016J\u0012\u0010\u0087\u0001\u001a\u00020y2\u0007\u0010}\u001a\u00030\u0088\u0001H\u0007J\u0012\u0010\u0089\u0001\u001a\u00020y2\u0007\u0010}\u001a\u00030\u008a\u0001H\u0007J\u0012\u0010\u008b\u0001\u001a\u00020y2\u0007\u0010}\u001a\u00030\u008c\u0001H\u0007J\u0011\u0010\u008d\u0001\u001a\u00020y2\u0006\u0010}\u001a\u00020~H\u0007J\u0012\u0010\u008e\u0001\u001a\u00020y2\u0007\u0010}\u001a\u00030\u008f\u0001H\u0007J\u0012\u0010\u0090\u0001\u001a\u00020y2\u0007\u0010}\u001a\u00030\u0091\u0001H\u0007J\u0013\u0010\u0092\u0001\u001a\u00020y2\b\u0010\u0093\u0001\u001a\u00030\u0094\u0001H\u0007J\u0011\u0010\u0095\u0001\u001a\u00020y2\u0006\u0010{\u001a\u00020\u0005H\u0002J\u0011\u0010\u0096\u0001\u001a\u00020y2\u0006\u0010{\u001a\u00020\u0005H\u0002J\t\u0010\u0097\u0001\u001a\u00020yH\u0002J\t\u0010\u0098\u0001\u001a\u00020yH\u0002J\t\u0010\u0099\u0001\u001a\u00020yH\u0002J\t\u0010\u009a\u0001\u001a\u00020yH\u0002J\t\u0010\u009b\u0001\u001a\u00020yH\u0002J\u0011\u0010\u009c\u0001\u001a\u00020\u00052\u0006\u0010z\u001a\u000209H\u0002J\t\u0010\u009d\u0001\u001a\u00020yH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0019R\u0014\u0010\u001e\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0019R\u000e\u0010 \u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u0019R\u000e\u0010#\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010-\u001a\u00020.¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0014\u00101\u001a\b\u0012\u0004\u0012\u00020'02X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u00103\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b4\u0010\u0019R\u0014\u00105\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00107\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020:08X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010=\u001a\b\u0012\u0004\u0012\u00020'02X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010C\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u000e\u0010F\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010M\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0N0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010P\u001a\b\u0012\u0004\u0012\u00020\u000702X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010R\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010T\u001a\u00020.¢\u0006\b\n\u0000\u001a\u0004\bU\u00100R\u0014\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010]\u001a\b\u0012\u0004\u0012\u00020^0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010`\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010c\u001a\u00020dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010eR\u000e\u0010f\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010g\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010h\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010i\u001a\u00020^8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bj\u0010kR\u000e\u0010l\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010n\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010t\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010u\u001a\b\u0012\u0004\u0012\u00020v0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u009e\u0001"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "CpsReduceValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "addCps", "", "attackDelay", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoBlockRangeValue", "", "autoBlockValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getAutoBlockValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "blinkCheck", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "blinkLag", "blinking", "blockTicks", "blockingStatus", "getBlockingStatus", "()Z", "setBlockingStatus", "(Z)V", "canBlock", "getCanBlock", "canFakeBlock", "getCanFakeBlock", "canSwing", "cancelRun", "getCancelRun", "clicks", "cpsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "currentTarget", "Lnet/minecraft/entity/EntityLivingBase;", "getCurrentTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setCurrentTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "cycle", "discoverRangeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "getDiscoverRangeValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "discoveredTargets", "", "displayBlocking", "getDisplayBlocking", "fovDisValue", "fovValue", "getAABB", "Lkotlin/Function1;", "Lnet/minecraft/entity/Entity;", "Lnet/minecraft/util/AxisAlignedBB;", "hitAbleValue", "hitable", "inRangeDiscoveredTargets", "keepDirectionTickValue", "keepDirectionValue", "lastCanBeSeen", "limitedMultiTargetsValue", "markValue", "maxRange", "getMaxRange", "()F", "nineCombat", "noEventBlocking", "noFlyValue", "noScaffValue", "onWeapon", "pitch", "predictAmount", "predictSizeValue", "Lkotlin/ranges/ClosedFloatingPointRange;", "predictValue", "prevTargetEntities", "priorityValue", "randomCenRangeValue", "randomCenterModeValue", "rangeValue", "getRangeValue", "raycastTargetValue", "raycastValue", "render", "Lnet/ccbluex/liquidbounce/features/value/TitleValue;", "rotationModeValue", "rotationRevTickValue", "rotationRevValue", "rotationStrafeValue", "", "rotationTimer", "silentRotationValue", "spinRotation", "swapped", "swingRangeValue", "net/ccbluex/liquidbounce/features/module/modules/combat/KillAura$swingRangeValue$1", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura$swingRangeValue$1;", "swingValue", "switchDelayValue", "switchTimer", "tag", "getTag", "()Ljava/lang/String;", "targetModeValue", "text1", "text11", "text13", "text15", "text3", "text5", "text7", "text9", "turnSpeedValue", "Lkotlin/ranges/IntRange;", "yaw", "attackEntity", "", "entity", "interact", "draw", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "getAttackDelay", "minCps", "maxCps", "isAlive", "maxSpeedRot", "minSpeedRot", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onRender3D", "onSprint", "Lnet/ccbluex/liquidbounce/event/SprintEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "ignoredEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "runAttackLoop", "startBlocking", "startBlockingNoEvent", "stopBlocking", "stopBlockingNoEvent", "updateHitable", "updateRotations", "updateTarget", "CrossSine"}
)
public final class KillAura extends Module {
   @NotNull
   public static final KillAura INSTANCE = new KillAura();
   @NotNull
   private static final TitleValue text1 = new TitleValue("Range");
   @NotNull
   private static final FloatValue rangeValue = new FloatValue() {
      protected void onChanged(float oldValue, float newValue) {
         float i = ((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue();
         if (i < newValue) {
            this.set(i);
         }

      }
   };
   @NotNull
   private static final <undefinedtype> swingRangeValue = new FloatValue() {
      protected void onChanged(float oldValue, float newValue) {
         float i = ((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue();
         if (i < newValue) {
            this.set(i);
         }

         if (KillAura.INSTANCE.getMaxRange() > newValue) {
            this.set(KillAura.INSTANCE.getMaxRange());
         }

      }
   };
   @NotNull
   private static final FloatValue discoverRangeValue = new FloatValue("Discover-Range", 6.0F, 0.0F, 8.0F);
   @NotNull
   private static final BoolValue fovValue = new BoolValue("Fov", false);
   @NotNull
   private static final Value fovDisValue;
   @NotNull
   private static final TitleValue text3;
   @NotNull
   private static final BoolValue nineCombat;
   @NotNull
   private static final Value CpsReduceValue;
   @NotNull
   private static final Value addCps;
   @NotNull
   private static final IntegerRangeValue cpsValue;
   @NotNull
   private static final TitleValue text5;
   @NotNull
   private static final ListValue priorityValue;
   @NotNull
   private static final ListValue targetModeValue;
   @NotNull
   private static final Value switchDelayValue;
   @NotNull
   private static final Value limitedMultiTargetsValue;
   @NotNull
   private static final TitleValue text7;
   @NotNull
   private static final BoolValue blinkCheck;
   @NotNull
   private static final BoolValue noScaffValue;
   @NotNull
   private static final BoolValue noFlyValue;
   @NotNull
   private static final BoolValue onWeapon;
   @NotNull
   private static final TitleValue text9;
   @NotNull
   private static final ListValue swingValue;
   @NotNull
   private static final Value rotationStrafeValue;
   @NotNull
   private static final BoolValue hitAbleValue;
   @NotNull
   private static final TitleValue text11;
   @NotNull
   private static final ListValue autoBlockValue;
   @NotNull
   private static final Value autoBlockRangeValue;
   @NotNull
   private static final TitleValue text13;
   @NotNull
   private static final ListValue rotationModeValue;
   @NotNull
   private static final BoolValue spinRotation;
   @NotNull
   private static final Value silentRotationValue;
   @NotNull
   private static final Value turnSpeedValue;
   @NotNull
   private static final Value rotationRevValue;
   @NotNull
   private static final Value rotationRevTickValue;
   @NotNull
   private static final Value keepDirectionValue;
   @NotNull
   private static final Value keepDirectionTickValue;
   @NotNull
   private static final BoolValue randomCenterModeValue;
   @NotNull
   private static final Value randomCenRangeValue;
   @NotNull
   private static final TitleValue text15;
   @NotNull
   private static final BoolValue raycastValue;
   @NotNull
   private static final Value raycastTargetValue;
   @NotNull
   private static final Value predictValue;
   @NotNull
   private static final Value predictSizeValue;
   @NotNull
   private static final TitleValue render;
   @NotNull
   private static final ListValue markValue;
   @Nullable
   private static EntityLivingBase currentTarget;
   private static boolean hitable;
   @NotNull
   private static final List prevTargetEntities;
   @NotNull
   private static final List discoveredTargets;
   @NotNull
   private static final List inRangeDiscoveredTargets;
   @NotNull
   private static final MSTimer attackTimer;
   @NotNull
   private static final MSTimer switchTimer;
   @NotNull
   private static final MSTimer rotationTimer;
   private static long attackDelay;
   private static int clicks;
   private static int blockTicks;
   private static boolean blinkLag;
   private static boolean blinking;
   private static boolean cycle;
   private static boolean swapped;
   private static float yaw;
   private static float pitch;
   private static boolean canSwing;
   private static boolean lastCanBeSeen;
   private static boolean blockingStatus;
   private static boolean noEventBlocking;
   private static float predictAmount;
   @NotNull
   private static final Function1 getAABB;

   private KillAura() {
   }

   @NotNull
   public final FloatValue getRangeValue() {
      return rangeValue;
   }

   @NotNull
   public final FloatValue getDiscoverRangeValue() {
      return discoverRangeValue;
   }

   @NotNull
   public final ListValue getAutoBlockValue() {
      return autoBlockValue;
   }

   @Nullable
   public final EntityLivingBase getCurrentTarget() {
      return currentTarget;
   }

   public final void setCurrentTarget(@Nullable EntityLivingBase var1) {
      currentTarget = var1;
   }

   private final boolean getCanFakeBlock() {
      return !((Collection)inRangeDiscoveredTargets).isEmpty();
   }

   public final boolean getBlockingStatus() {
      return blockingStatus;
   }

   public final void setBlockingStatus(boolean var1) {
      blockingStatus = var1;
   }

   public final boolean getDisplayBlocking() {
      return blockingStatus || (autoBlockValue.equals("Fake") || autoBlockValue.contains("WatchDog") || autoBlockValue.equals("BlocksMC")) && this.getCanFakeBlock() && this.getCanBlock();
   }

   public void onEnable() {
      if (MinecraftInstance.mc.field_71439_g != null) {
         if (MinecraftInstance.mc.field_71441_e != null) {
            lastCanBeSeen = false;
            blinking = false;
            this.updateTarget();
         }
      }
   }

   public void onDisable() {
      Module var10000 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
      Intrinsics.checkNotNull(var10000);
      ((TargetStrafe)var10000).setDoStrafe(false);
      currentTarget = null;
      hitable = false;
      if (blinking) {
         BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
         blinking = false;
      }

      blinkLag = false;
      prevTargetEntities.clear();
      discoveredTargets.clear();
      inRangeDiscoveredTargets.clear();
      attackTimer.reset();
      clicks = 0;
      canSwing = false;
      if (swapped) {
         MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
         swapped = false;
      }

      this.stopBlocking();
      this.stopBlockingNoEvent();
      RotationUtils.setTargetRotationReverse(RotationUtils.serverRotation, (Boolean)keepDirectionValue.get() ? ((Number)keepDirectionTickValue.get()).intValue() + 1 : 1, (Boolean)rotationRevValue.get() ? ((Number)rotationRevTickValue.get()).intValue() + 1 : 0);
      hitable = false;
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!rotationStrafeValue.equals("Off") && currentTarget == null) {
         event.setYaw(RotationUtils.targetRotation.getYaw());
      }
   }

   @EventTarget
   public final void onStrafe(@NotNull StrafeEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!rotationStrafeValue.equals("Off") && currentTarget == null) {
         if (rotationStrafeValue.equals("FullStrafe")) {
            event.setYaw(RotationUtils.targetRotation.getYaw());
         } else {
            MovementFix.INSTANCE.applyForceStrafe(true, true);
         }

      }
   }

   @EventTarget
   public final void onSprint(@NotNull SprintEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (rotationStrafeValue.equals("LessStrafe") && currentTarget == null) {
         event.setSprint(Math.abs((double)(MathHelper.func_76142_g(MinecraftInstance.mc.field_71439_g.field_70177_z) - MathHelper.func_76142_g(RotationUtils.serverRotation.getYaw()))) < (double)90.0F && MovementUtils.INSTANCE.isMoving());
      }
   }

   @EventTarget
   public final void onPreUpdate(@NotNull PreUpdateEvent event) {
      label122: {
         Intrinsics.checkNotNullParameter(event, "event");
         if (!this.getCancelRun() && currentTarget != null) {
            EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(var2, "mc.thePlayer");
            Entity var10000 = (Entity)var2;
            EntityLivingBase var10001 = currentTarget;
            Intrinsics.checkNotNull(var10001);
            if (EntityExtensionKt.getDistanceToEntityBox(var10000, (Entity)var10001) <= (double)((Number)autoBlockRangeValue.get()).floatValue()) {
               Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var4) {
                  case "watchdogswap":
                     if (blockTicks >= 3) {
                        blockTicks = 0;
                     }

                     int var7 = blockTicks++;
                     switch (blockTicks) {
                        case 2:
                           BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
                           blinking = true;
                           swapped = true;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(InventoryUtils.INSTANCE.getBestSwapSlot())));
                           break label122;
                        case 3:
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                           swapped = false;
                           this.runAttackLoop(true);
                           this.startBlockingNoEvent();
                           BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                           blinking = false;
                     }
                     break label122;
                  case "blocksmc":
                     if (blockTicks >= 3) {
                        blockTicks = 0;
                     }

                     int var6 = blockTicks++;
                     switch (blockTicks) {
                        case 1:
                           BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
                           blinking = true;
                           break label122;
                        case 2:
                           this.runAttackLoop(true);
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(InventoryUtils.INSTANCE.getBestSwapSlot())));
                           swapped = true;
                           break label122;
                        case 3:
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                           swapped = false;
                           BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                           blinking = false;
                           this.startBlockingNoEvent();
                     }
                     break label122;
                  case "watchdoga":
                     if (blockTicks >= 3) {
                        blockTicks = 0;
                     }

                     int var5 = blockTicks++;
                     if (cycle) {
                        switch (blockTicks) {
                           case 1:
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
                              blinking = true;
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(InventoryUtils.INSTANCE.getBestSwapSlot())));
                              swapped = true;
                              blinkLag = false;
                              break label122;
                           case 2:
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                              swapped = false;
                              this.runAttackLoop(true);
                              break label122;
                           case 3:
                              this.startBlockingNoEvent();
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                              blinking = false;
                              cycle = false;
                              blinkLag = true;
                        }
                     } else {
                        switch (blockTicks) {
                           case 1:
                           default:
                              break label122;
                           case 2:
                              blinkLag = false;
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
                              blinking = true;
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(InventoryUtils.INSTANCE.getBestSwapSlot())));
                              swapped = true;
                              break label122;
                           case 3:
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                              swapped = false;
                              this.runAttackLoop(true);
                              this.startBlockingNoEvent();
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                              blinking = false;
                              blinkLag = true;
                        }
                     }
                     break label122;
                  case "watchdogb":
                     if (blockTicks >= 3) {
                        blockTicks = 0;
                     }

                     int var3 = blockTicks++;
                     if (cycle) {
                        switch (blockTicks) {
                           case 1:
                              blinking = true;
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
                              this.runAttackLoop(true);
                              this.startBlockingNoEvent();
                              blinking = false;
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                              blinkLag = true;
                              break label122;
                           case 2:
                              if (!blinkLag) {
                                 this.runAttackLoop(true);
                                 this.startBlockingNoEvent();
                                 blinking = false;
                                 BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                                 blinkLag = true;
                              }
                              break label122;
                           case 3:
                              cycle = false;
                        }
                     } else {
                        switch (blockTicks) {
                           case 1:
                              blinking = true;
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
                              this.runAttackLoop(true);
                              this.startBlockingNoEvent();
                              blinking = false;
                              BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                              blinkLag = true;
                              break label122;
                           case 2:
                              if (!blinkLag) {
                                 this.runAttackLoop(true);
                                 this.startBlockingNoEvent();
                                 blinking = false;
                                 BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                                 blinkLag = true;
                              }

                              cycle = true;
                              blockTicks = 0;
                        }
                     }
                     break label122;
                  case "vanilla":
                     this.startBlocking();
                     this.runAttackLoop(false);
                  default:
                     break label122;
               }
            }
         }

         if (blinking || blinkLag) {
            blinking = false;
            blinkLag = false;
            blockTicks = 0;
            this.stopBlockingNoEvent();
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
         }
      }

      if (autoBlockValue.equals("None") || autoBlockValue.equals("Fake")) {
         this.runAttackLoop(false);
      }

      this.updateHitable();
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C09PacketHeldItemChange) {
         this.stopBlocking();
         this.stopBlockingNoEvent();
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent ignoredEvent) {
      Intrinsics.checkNotNullParameter(ignoredEvent, "ignoredEvent");
      if (this.getCancelRun()) {
         currentTarget = null;
         hitable = false;
         this.stopBlocking();
         this.stopBlockingNoEvent();
         discoveredTargets.clear();
         inRangeDiscoveredTargets.clear();
      } else {
         this.updateTarget();
         if (currentTarget == null) {
            this.stopBlocking();
            this.stopBlockingNoEvent();
         }

         if (discoveredTargets.isEmpty()) {
            this.stopBlocking();
            this.stopBlockingNoEvent();
         } else {
            TargetStrafe var10000 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(var10000);
            var10000 = var10000;
            EntityLivingBase var10001 = currentTarget;
            if (var10001 != null) {
               var10000.setTargetEntity(var10001);
            }
         }
      }
   }

   private final void runAttackLoop(boolean interact) {
      if (!(Boolean)nineCombat.get() || !(CooldownHelper.INSTANCE.getAttackCooldownProgress() < (double)1.0F)) {
         if ((Boolean)nineCombat.get() && clicks > 0) {
            clicks = 1;
         }

         if ((Boolean)CpsReduceValue.get() && MinecraftInstance.mc.field_71439_g.field_70737_aN > 8) {
            clicks += ((Number)addCps.get()).intValue();
         }

         try {
            while(clicks > 0) {
               this.runAttack(interact);
               int var2 = clicks;
               clicks = var2 + -1;
            }

         } catch (IllegalStateException var3) {
         }
      }
   }

   private final void runAttack(boolean interact) {
      if (!this.getCancelRun()) {
         if (currentTarget != null) {
            if (hitable) {
               if (!targetModeValue.equals("Multi")) {
                  EntityLivingBase var14;
                  if ((Boolean)raycastValue.get()) {
                     Entity var11 = RaycastUtils.raycastEntity((double)this.getMaxRange(), KillAura::runAttack$lambda-0);
                     Entity var13;
                     if (var11 == null) {
                        EntityLivingBase var10001 = currentTarget;
                        Intrinsics.checkNotNull(var10001);
                        var13 = (Entity)var10001;
                     } else {
                        var13 = var11;
                     }

                     var14 = (EntityLivingBase)var13;
                  } else {
                     var14 = currentTarget;
                     Intrinsics.checkNotNull(var14);
                  }

                  this.attackEntity(var14, interact);
               } else {
                  Iterable $this$forEachIndexed$iv = (Iterable)inRangeDiscoveredTargets;
                  int $i$f$forEachIndexed = 0;
                  int index$iv = 0;

                  for(Object item$iv : $this$forEachIndexed$iv) {
                     int index = index$iv++;
                     if (index < 0) {
                        CollectionsKt.throwIndexOverflow();
                     }

                     EntityLivingBase entity = (EntityLivingBase)item$iv;
                     int var10 = 0;
                     if (((Number)limitedMultiTargetsValue.get()).intValue() == 0 || index < ((Number)limitedMultiTargetsValue.get()).intValue()) {
                        INSTANCE.attackEntity(entity, interact);
                     }
                  }
               }

               if (targetModeValue.equals("Switch")) {
                  if (switchTimer.hasTimePassed((long)((Number)switchDelayValue.get()).intValue())) {
                     List var10000 = prevTargetEntities;
                     EntityLivingBase var15 = currentTarget;
                     Intrinsics.checkNotNull(var15);
                     var10000.add(var15.func_145782_y());
                     switchTimer.reset();
                  }
               } else {
                  List var12 = prevTargetEntities;
                  EntityLivingBase var16 = currentTarget;
                  Intrinsics.checkNotNull(var16);
                  var12.add(var16.func_145782_y());
               }
            }

         }
      }
   }

   private final void updateTarget() {
      float fov = (Boolean)fovValue.get() ? ((Number)fovDisValue.get()).floatValue() : 180.0F;
      boolean switchMode = targetModeValue.equals("Switch");
      discoveredTargets.clear();

      for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
         if (entity instanceof EntityLivingBase && EntityUtils.INSTANCE.isSelected(entity, true) && (!switchMode || !prevTargetEntities.contains(((EntityLivingBase)entity).func_145782_y()))) {
            EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(var7, "mc.thePlayer");
            double distance = EntityExtensionKt.getDistanceToEntityBox((Entity)var7, entity);
            double entityFov = RotationUtils.getRotationDifference(entity);
            if (distance <= (double)((Number)discoverRangeValue.get()).floatValue() && (fov == 180.0F || entityFov <= (double)fov)) {
               discoveredTargets.add(entity);
            }
         }
      }

      Intrinsics.checkNotNullExpressionValue($i$f$sortBy, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch ($i$f$sortBy) {
         case "health":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$2());
            }
            break;
         case "direction":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$3());
            }
            break;
         case "regenamplifier":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$9());
            }
            break;
         case "healthabsorption":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$8());
            }
            break;
         case "armor":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$5());
            }
            break;
         case "distance":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$1());
            }
            break;
         case "hurtresistance":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$6());
            }
            break;
         case "hurttime":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$7());
            }
            break;
         case "livingtime":
            List $this$sortBy$iv = discoveredTargets;
            int $i$f$sortBy = 0;
            if ($this$sortBy$iv.size() > 1) {
               CollectionsKt.sortWith($this$sortBy$iv, new KillAura$updateTarget$$inlined$sortBy$4());
            }
      }

      inRangeDiscoveredTargets.clear();
      Iterable var14 = (Iterable)discoveredTargets;
      List var13 = inRangeDiscoveredTargets;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : var14) {
         EntityLivingBase it = (EntityLivingBase)element$iv$iv;
         int var11 = 0;
         EntityPlayerSP var12 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var12, "mc.thePlayer");
         if (EntityExtensionKt.getDistanceToEntityBox((Entity)var12, (Entity)it) < (double)((Number)INSTANCE.getDiscoverRangeValue().get()).floatValue()) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      var13.addAll((Collection)((List)destination$iv$iv));
      if (inRangeDiscoveredTargets.isEmpty() && !((Collection)prevTargetEntities).isEmpty()) {
         prevTargetEntities.clear();
         this.updateTarget();
      } else {
         for(EntityLivingBase entity : discoveredTargets) {
            if (!this.updateRotations((Entity)entity)) {
               boolean success = false;
            } else {
               EntityPlayerSP var38 = MinecraftInstance.mc.field_71439_g;
               Intrinsics.checkNotNullExpressionValue(var38, "mc.thePlayer");
               if (EntityExtensionKt.getDistanceToEntityBox((Entity)var38, (Entity)entity) < (double)((Number)discoverRangeValue.get()).floatValue()) {
                  currentTarget = entity;
                  TargetStrafe var10000 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
                  Intrinsics.checkNotNull(var10000);
                  var10000 = var10000;
                  EntityLivingBase var10001 = currentTarget;
                  if (var10001 == null) {
                     return;
                  }

                  var10000.setTargetEntity(var10001);
                  var10000 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
                  Intrinsics.checkNotNull(var10000);
                  var10000 = var10000;
                  Module var45 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
                  Intrinsics.checkNotNull(var45);
                  var10000.setDoStrafe(((TargetStrafe)var45).toggleStrafe());
                  return;
               }
            }
         }

         currentTarget = null;
         Module var44 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
         Intrinsics.checkNotNull(var44);
         ((TargetStrafe)var44).setDoStrafe(false);
      }
   }

   private final void attackEntity(EntityLivingBase entity, boolean interact) {
      AttackEvent event = new AttackEvent((Entity)entity);
      CrossSine.INSTANCE.getEventManager().callEvent(event);
      if (!event.isCancelled()) {
         ProtocolFixer.sendFixedAttack((EntityPlayer)MinecraftInstance.mc.field_71439_g, (Entity)entity, StringsKt.equals((String)swingValue.get(), "packet", true));
         if (interact) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C02PacketUseEntity((Entity)entity, Action.INTERACT)));
         }

         if (MinecraftInstance.mc.field_71442_b.field_78779_k != GameType.SPECTATOR) {
            MinecraftInstance.mc.field_71439_g.func_71059_n((Entity)entity);
         }

         CooldownHelper.INSTANCE.resetLastAttackedTicks();
      }
   }

   private final boolean updateRotations(Entity entity) {
      if (rotationModeValue.equals("None")) {
         return true;
      } else {
         double entityFov = RotationUtils.getRotationDifference(RotationUtils.toRotation(RotationUtils.getCenter(EntityExtensionKt.getHitBox(entity)), true), RotationUtils.serverRotation);
         if (entityFov <= (double)MinecraftInstance.mc.field_71474_y.field_74334_X) {
            lastCanBeSeen = true;
         } else if (lastCanBeSeen) {
            rotationTimer.reset();
            lastCanBeSeen = false;
         }

         if ((Boolean)predictValue.get()) {
            predictAmount = RandomUtils.INSTANCE.nextFloat(((Number)((ClosedFloatingPointRange)predictSizeValue.get()).getEndInclusive()).floatValue(), ((Number)((ClosedFloatingPointRange)predictSizeValue.get()).getStart()).floatValue());
         }

         AxisAlignedBB boundingBox;
         String var10000;
         label132: {
            label131: {
               label130: {
                  boundingBox = (AxisAlignedBB)getAABB.invoke(entity);
                  switch (var6) {
                     case "Normal":
                        var10000 = "HalfUp";
                        break label132;
                     case "Smooth":
                     case "Smooth2":
                     case "SmoothCenter2":
                        var10000 = "CenterBody";
                        break label132;
                     case "SmoothCenter":
                     case "Center":
                  }

                  var10000 = "HalfUp";
                  break label132;
               }

               var10000 = "CenterHead";
               break label132;
            }

            var10000 = "CenterLine";
         }

         String rModes = var10000;
         VecRotation var7 = RotationUtils.calculateCenter(rModes, (Boolean)randomCenterModeValue.get(), (double)((Number)randomCenRangeValue.get()).floatValue(), false, boundingBox, (Boolean)predictValue.get(), true);
         if (var7 == null) {
            return false;
         } else {
            Rotation directRotation = var7.component2();
            double diffAngle = RotationUtils.getRotationDifference(RotationUtils.serverRotation, directRotation);
            if (diffAngle < (double)0.0F) {
               diffAngle = -diffAngle;
            }

            if (diffAngle > (double)180.0F) {
               diffAngle = (double)180.0F;
            }

            double calculateSpeed = diffAngle / (double)360 * (double)((IntRange)turnSpeedValue.get()).getLast() + ((double)1 - diffAngle / (double)360) * (double)((IntRange)turnSpeedValue.get()).getFirst();
            String var14 = (String)rotationModeValue.get();
            Rotation var17;
            switch (var14.hashCode()) {
               case -1955878649:
                  if (!var14.equals("Normal")) {
                     return true;
                  }

                  var17 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)diffAngle);
                  break;
               case -1814666802:
                  if (!var14.equals("Smooth")) {
                     return true;
                  }

                  var17 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
                  break;
               case -420095964:
                  if (!var14.equals("Smooth2")) {
                     return true;
                  }

                  var17 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)(diffAngle / (double)1.5F));
                  break;
               case 58454223:
                  if (!var14.equals("SmoothCenter2")) {
                     return true;
                  }

                  var17 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
                  break;
               case 556074947:
                  if (!var14.equals("SmoothCenter")) {
                     return true;
                  }

                  var17 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
                  break;
               case 2014820469:
                  if (var14.equals("Center")) {
                     var17 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)(Math.random() * (double)(this.maxSpeedRot() - this.minSpeedRot()) + (double)this.minSpeedRot()));
                     break;
                  }

                  return true;
               default:
                  return true;
            }

            Rotation rotation = var17;
            Intrinsics.checkNotNullExpressionValue(rotation, "when (rotationModeValue.… -> return true\n        }");
            if ((Boolean)silentRotationValue.get() && (Boolean)spinRotation.get()) {
               RotationUtils.setFakeRotationReverse(new Rotation(yaw, pitch), rotation, (Boolean)keepDirectionValue.get() ? ((Number)keepDirectionTickValue.get()).intValue() : 1, (Boolean)rotationRevValue.get() ? ((Number)rotationRevTickValue.get()).intValue() : 0);
            } else if ((Boolean)silentRotationValue.get()) {
               RotationUtils.setTargetRotationReverse(rotation, (Boolean)keepDirectionValue.get() ? ((Number)keepDirectionTickValue.get()).intValue() : 1, (Boolean)rotationRevValue.get() ? ((Number)rotationRevTickValue.get()).intValue() : 0);
            } else {
               var17 = rotation;
               EntityPlayerSP rotation = MinecraftInstance.mc.field_71439_g;
               Intrinsics.checkNotNullExpressionValue(rotation, "mc.thePlayer");
               var17.toPlayer((EntityPlayer)rotation);
            }

            return true;
         }
      }
   }

   private final void updateHitable() {
      if (currentTarget == null) {
         canSwing = false;
         hitable = false;
      } else {
         EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer");
         Entity var10000 = (Entity)var3;
         EntityLivingBase var10001 = currentTarget;
         if (var10001 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.Entity");
         } else {
            double entityDist = EntityExtensionKt.getDistanceToEntityBox(var10000, (Entity)var10001);
            canSwing = entityDist <= (double)((Number)swingRangeValue.get()).floatValue();
            if ((Boolean)hitAbleValue.get()) {
               hitable = entityDist <= (double)this.getMaxRange();
            } else if ((float)this.maxSpeedRot() <= 0.0F) {
               hitable = true;
            } else {
               EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
               Intrinsics.checkNotNullExpressionValue(var4, "mc.thePlayer");
               MovingObjectPosition wallTrace = EntityExtensionKt.rayTraceWithServerSideRotation((Entity)var4, entityDist);
               hitable = RotationUtils.isFaced((Entity)currentTarget, (double)this.getMaxRange()) && (entityDist < (double)((Number)discoverRangeValue.get()).floatValue() || (wallTrace == null ? null : wallTrace.field_72313_a) != MovingObjectType.BLOCK);
            }
         }
      }
   }

   private final void startBlocking() {
      if (!blockingStatus) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g())));
         blockingStatus = true;
      }

   }

   private final void stopBlocking() {
      if (blockingStatus) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN)));
         blockingStatus = false;
      }

   }

   private final void startBlockingNoEvent() {
      if (!noEventBlocking) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g())));
         noEventBlocking = true;
      }

   }

   private final void stopBlockingNoEvent() {
      if (noEventBlocking) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN)));
         noEventBlocking = false;
      }

   }

   private final int maxSpeedRot() {
      return ((IntRange)turnSpeedValue.get()).getLast();
   }

   private final int minSpeedRot() {
      return ((IntRange)turnSpeedValue.get()).getFirst();
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      yaw += (float)5;
      if (yaw > 180.0F) {
         yaw = -180.0F;
      } else if (yaw < -180.0F) {
         yaw = 180.0F;
      }

      pitch = 150.0F;
      if (currentTarget != null && attackTimer.hasTimePassed(attackDelay)) {
         int var2 = clicks++;
         attackTimer.reset();
         MouseUtils.INSTANCE.setLeftClicked(true);
         CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
         attackDelay = this.getAttackDelay(cpsValue.get().getFirst(), cpsValue.get().getLast());
      } else if (attackTimer.hasTimePassed(30L)) {
         MouseUtils.INSTANCE.setLeftClicked(GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74312_F));
      }

      if (currentTarget != null) {
         String var3 = ((String)markValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         if (Intrinsics.areEqual((Object)var3, (Object)"modern")) {
            EntityLivingBase var10001 = currentTarget;
            Intrinsics.checkNotNull(var10001);
            this.draw(var10001, event);
         } else if (Intrinsics.areEqual((Object)var3, (Object)"box")) {
            EntityLivingBase var10000 = currentTarget;
            Intrinsics.checkNotNull(var10000);
            RenderUtils.drawEntityBox((Entity)var10000, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 120, false, 4, (Object)null), false, true, 1.0F);
         }

         GlStateManager.func_179117_G();
      }

   }

   private final long getAttackDelay(int minCps, int maxCps) {
      return TimeUtils.INSTANCE.randomClickDelay(RangesKt.coerceAtMost(minCps, maxCps), RangesKt.coerceAtLeast(minCps, maxCps));
   }

   private final boolean getCancelRun() {
      boolean var5;
      if (!MinecraftInstance.mc.field_71439_g.func_175149_v()) {
         EntityPlayerSP var1 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var1, "mc.thePlayer");
         if (this.isAlive((EntityLivingBase)var1)) {
            label49: {
               if ((Boolean)blinkCheck.get()) {
                  Module var10000 = CrossSine.INSTANCE.getModuleManager().get(Blink.class);
                  Intrinsics.checkNotNull(var10000);
                  if (((Blink)var10000).getState()) {
                     break label49;
                  }
               }

               Module var2 = CrossSine.INSTANCE.getModuleManager().get(FreeCam.class);
               Intrinsics.checkNotNull(var2);
               if (!((FreeCam)var2).getState()) {
                  label50: {
                     if ((Boolean)noScaffValue.get()) {
                        var2 = CrossSine.INSTANCE.getModuleManager().get(Scaffold.class);
                        Intrinsics.checkNotNull(var2);
                        if (((Scaffold)var2).getState()) {
                           break label50;
                        }
                     }

                     if ((Boolean)noFlyValue.get()) {
                        var2 = CrossSine.INSTANCE.getModuleManager().get(Flight.class);
                        Intrinsics.checkNotNull(var2);
                        if (((Flight)var2).getState()) {
                           break label50;
                        }
                     }

                     if (!(Boolean)onWeapon.get() || MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPickaxe || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemAxe)) {
                        var5 = false;
                        return var5;
                     }
                  }
               }
            }
         }
      }

      var5 = true;
      return var5;
   }

   public final void draw(@NotNull EntityLivingBase entity, @NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      Intrinsics.checkNotNullParameter(event, "event");
      int everyTime = 3000;
      int drawTime = (int)(System.currentTimeMillis() % (long)everyTime);
      boolean drawMode = drawTime > everyTime / 2;
      double drawPercent = (double)drawTime / ((double)everyTime / (double)2.0F);
      if (!drawMode) {
         drawPercent = (double)1 - drawPercent;
      } else {
         drawPercent -= (double)1;
      }

      drawPercent = EaseUtils.INSTANCE.easeInOutQuad(drawPercent);
      MinecraftInstance.mc.field_71460_t.func_175072_h();
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glEnable(3042);
      GL11.glDisable(2929);
      GL11.glDisable(2884);
      GL11.glShadeModel(7425);
      MinecraftInstance.mc.field_71460_t.func_175072_h();
      AxisAlignedBB bb = entity.func_174813_aQ();
      double radius = (bb.field_72336_d - bb.field_72340_a + (bb.field_72334_f - bb.field_72339_c)) * (double)0.5F;
      double height = bb.field_72337_e - bb.field_72338_b;
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78730_l;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78731_m + height * drawPercent;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78728_n;
      double eased = height / (double)3 * (drawPercent > (double)0.5F ? (double)1 - drawPercent : drawPercent) * (double)(drawMode ? -1 : 1);
      int var21 = 5;
      int var22 = ProgressionUtilKt.getProgressionLastElement(5, 360, 5);
      int i;
      if (var21 <= var22) {
         do {
            i = var21;
            var21 += 5;
            double x1 = x - Math.sin((double)i * Math.PI / (double)180.0F) * radius;
            double z1 = z + Math.cos((double)i * Math.PI / (double)180.0F) * radius;
            double x2 = x - Math.sin((double)(i - 5) * Math.PI / (double)180.0F) * radius;
            double z2 = z + Math.cos((double)(i - 5) * Math.PI / (double)180.0F) * radius;
            GL11.glBegin(7);
            RenderUtils.glColor(ClientTheme.INSTANCE.getColorWithAlpha(0, 0, true));
            GL11.glVertex3d(x1, y + eased, z1);
            GL11.glVertex3d(x2, y + eased, z2);
            RenderUtils.glColor(ClientTheme.INSTANCE.getColorWithAlpha(0, 150, true));
            GL11.glVertex3d(x2, y, z2);
            GL11.glVertex3d(x1, y, z1);
            GL11.glEnd();
         } while(i != var22);
      }

      GL11.glEnable(2884);
      GL11.glShadeModel(7424);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   private final boolean isAlive(EntityLivingBase entity) {
      return entity.func_70089_S() && entity.func_110143_aJ() > 0.0F;
   }

   private final boolean getCanBlock() {
      return MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword;
   }

   private final float getMaxRange() {
      return Math.max(((Number)rangeValue.get()).floatValue(), ((Number)rangeValue.get()).floatValue());
   }

   @NotNull
   public String getTag() {
      return !autoBlockValue.equals("Fake") && !autoBlockValue.equals("None") ? (String)autoBlockValue.get() : (String)targetModeValue.get();
   }

   private static final boolean runAttack$lambda_0/* $FF was: runAttack$lambda-0*/(Entity it) {
      return it instanceof EntityLivingBase && !(it instanceof EntityArmorStand) && (!(Boolean)raycastTargetValue.get() || EntityUtils.INSTANCE.canRayCast(it)) && !EntityUtils.INSTANCE.isFriend(it);
   }

   static {
      fovDisValue = (new FloatValue("FOV-Disttance", 180.0F, 0.0F, 180.0F)).displayable(null.INSTANCE);
      text3 = new TitleValue("CPS");
      nineCombat = new BoolValue("1.9-Combat-Check", false);
      CpsReduceValue = (new BoolValue("CPS-Reduce", false)).displayable(null.INSTANCE);
      addCps = (new IntegerValue("Add-CPS", 1, 1, 20)).displayable(null.INSTANCE);
      cpsValue = new IntegerRangeValue("CPS", 9, 12, 1, 20, (Function0)null, 32, (DefaultConstructorMarker)null);
      text5 = new TitleValue("Target-Mode");
      String[] var0 = new String[]{"Health", "Distance", "Direction", "LivingTime", "Armor", "HurtResistance", "HurtTime", "HealthAbsorption", "RegenAmplifier"};
      priorityValue = new ListValue("Priority", var0, "Distance");
      var0 = new String[]{"Single", "Switch", "Multi"};
      targetModeValue = new ListValue("Target-Mode", var0, "Switch");
      switchDelayValue = (new IntegerValue("Switch-Delay", 15, 1, 2000)).displayable(null.INSTANCE);
      limitedMultiTargetsValue = (new IntegerValue("Limited-Multi-Targets", 0, 0, 50)).displayable(null.INSTANCE);
      text7 = new TitleValue("Limit-Use");
      blinkCheck = new BoolValue("Blink-Check", true);
      noScaffValue = new BoolValue("No-Scaffold", true);
      noFlyValue = new BoolValue("No-Fly", false);
      onWeapon = new BoolValue("On-Weapon", false);
      text9 = new TitleValue("Bypass");
      var0 = new String[]{"Normal", "Packet", "None"};
      swingValue = new ListValue("Swing", var0, "Normal");
      var0 = new String[]{"Off", "FullStrafe", "LessStrafe"};
      rotationStrafeValue = (new ListValue("Fix-Movement", var0, "Off")).displayable(null.INSTANCE);
      hitAbleValue = new BoolValue("Always-Attack", true);
      text11 = new TitleValue("AutoBlock");
      String[] var1 = new String[]{"Vanilla", "WatchDogA", "WatchDogB", "WatchDogSwap", "BlocksMC", "Fake", "None"};
      autoBlockValue = new ListValue(var1) {
         protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
            Intrinsics.checkNotNullParameter(oldValue, "oldValue");
            Intrinsics.checkNotNullParameter(newValue, "newValue");
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
            KillAura var10000 = KillAura.INSTANCE;
            KillAura.blinking = false;
            var10000 = KillAura.INSTANCE;
            KillAura.blinkLag = false;
         }
      };
      autoBlockRangeValue = (new FloatValue() {
         protected void onChanged(float oldValue, float newValue) {
            float i = ((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue();
            if (i < newValue) {
               this.set(i);
            }

         }
      }).displayable(null.INSTANCE);
      text13 = new TitleValue("Rotation");
      var0 = new String[]{"None", "Center", "Normal", "Smooth", "Smooth2", "SmoothCenter", "SmoothCenter2"};
      rotationModeValue = new ListValue("RotationMode", var0, "Smooth");
      spinRotation = new BoolValue("SpinRotation", false);
      silentRotationValue = (new BoolValue("SilentRotation", true)).displayable(null.INSTANCE);
      turnSpeedValue = (new IntegerRangeValue("TurnSpeed", 90, 90, 1, 90, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      rotationRevValue = (new BoolValue("RotationReverse", false)).displayable(null.INSTANCE);
      rotationRevTickValue = (new IntegerValue("RotationReverseTick", 5, 1, 20)).displayable(null.INSTANCE);
      keepDirectionValue = (new BoolValue("KeepDirection", true)).displayable(null.INSTANCE);
      keepDirectionTickValue = (new IntegerValue("KeepDirectionTick", 15, 1, 20)).displayable(null.INSTANCE);
      randomCenterModeValue = new BoolValue("RandomCenter", false);
      randomCenRangeValue = (new FloatValue("RandomRange", 0.0F, 0.0F, 1.2F)).displayable(null.INSTANCE);
      text15 = new TitleValue("MoreBypass");
      raycastValue = new BoolValue("RayCast", true);
      raycastTargetValue = (new BoolValue("RaycastOnlyTarget", false)).displayable(null.INSTANCE);
      predictValue = (new BoolValue("Predict", true)).displayable(null.INSTANCE);
      predictSizeValue = (new FloatRangeValue("PredictSize", 1.0F, 1.0F, -2.0F, 5.0F)).displayable(null.INSTANCE);
      render = new TitleValue("Render");
      var0 = new String[]{"Modern", "Box", "None"};
      markValue = new ListValue("TargetESP", var0, "Box");
      prevTargetEntities = (List)(new ArrayList());
      discoveredTargets = (List)(new ArrayList());
      inRangeDiscoveredTargets = (List)(new ArrayList());
      attackTimer = new MSTimer();
      switchTimer = new MSTimer();
      rotationTimer = new MSTimer();
      predictAmount = 1.0F;
      getAABB = null.INSTANCE;
   }
}
