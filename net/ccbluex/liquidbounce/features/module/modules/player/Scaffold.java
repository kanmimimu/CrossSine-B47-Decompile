package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PostUpdateEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.SprintEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.CPSCounter;
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
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.extensions.OtherExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "Scaffold",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0013\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0002J\u0013\u0010\u0085\u0001\u001a\u00030\u0084\u00012\u0007\u0010\u0086\u0001\u001a\u00020\u0005H\u0002JP\u0010\u0087\u0001\u001a\u0005\u0018\u00010\u0088\u00012\b\u0010\u0089\u0001\u001a\u00030\u008a\u00012\b\u0010\u008b\u0001\u001a\u00030\u008a\u00012\b\u0010\u008c\u0001\u001a\u00030\u008d\u00012\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\b\u0010\u0090\u0001\u001a\u00030\u008d\u00012\u0007\u0010\u0091\u0001\u001a\u00020#2\u0007\u0010\u0092\u0001\u001a\u00020\u0005H\u0002J\u0013\u0010\u0093\u0001\u001a\u00030\u0094\u00012\u0007\u0010\u0095\u0001\u001a\u00020\tH\u0002J\t\u0010\u0096\u0001\u001a\u00020\tH\u0002J\u0013\u0010\u0097\u0001\u001a\u00030\u0094\u00012\u0007\u0010\u0095\u0001\u001a\u00020\tH\u0002J1\u0010\u0098\u0001\u001a\u00030\u008d\u00012\b\u0010\u0099\u0001\u001a\u00030\u008d\u00012\b\u0010\u009a\u0001\u001a\u00030\u008f\u00012\b\u0010\u0089\u0001\u001a\u00030\u008d\u00012\u0007\u0010\u009b\u0001\u001a\u00020\u0005H\u0002J\n\u0010\u009c\u0001\u001a\u00030\u0084\u0001H\u0002J\n\u0010\u009d\u0001\u001a\u00030\u0084\u0001H\u0016J\n\u0010\u009e\u0001\u001a\u00030\u0084\u0001H\u0016J\t\u0010\u009f\u0001\u001a\u00020\u0005H\u0002J\u0014\u0010 \u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030¢\u0001H\u0007J\u0014\u0010£\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030¤\u0001H\u0007J\u0014\u0010¥\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030¦\u0001H\u0007J\u0014\u0010§\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030¨\u0001H\u0007J\u0014\u0010©\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030ª\u0001H\u0007J\u0014\u0010«\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030¬\u0001H\u0007J\u0014\u0010\u00ad\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030®\u0001H\u0007J\u0014\u0010¯\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030°\u0001H\u0007J\u0014\u0010±\u0001\u001a\u00030\u0084\u00012\b\u0010¡\u0001\u001a\u00030°\u0001H\u0007J\u001e\u0010²\u0001\u001a\u0005\u0018\u00010³\u00012\u0007\u0010´\u0001\u001a\u00020\u00162\u0007\u0010\u0091\u0001\u001a\u00020#H\u0002J\n\u0010µ\u0001\u001a\u00030\u0084\u0001H\u0002J\n\u0010¶\u0001\u001a\u00030\u0084\u0001H\u0002J\u001c\u0010·\u0001\u001a\u00020\u00052\b\u0010¸\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u0092\u0001\u001a\u00020\u0005H\u0002J\u0014\u0010¹\u0001\u001a\u00030\u0084\u00012\b\u0010º\u0001\u001a\u00030\u0094\u0001H\u0002J\t\u0010»\u0001\u001a\u00020\u0005H\u0002J\t\u0010¼\u0001\u001a\u00020\u0005H\u0002J\n\u0010½\u0001\u001a\u00030\u0084\u0001H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u001f\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\u00020\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u001e\u00100\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u0010\n\u0002\u00105\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u000e\u00106\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00108\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00109\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020?X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010A\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u000b\"\u0004\bC\u0010\rR\u000e\u0010D\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010H\u001a\u00020#8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u000e\u0010K\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010L\u001a\b\u0012\u0004\u0012\u00020M0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010T\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010U\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010X\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\bY\u0010!R\u0010\u0010Z\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010^\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010a\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010b\u001a\u00020M8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bc\u0010dR\u000e\u0010e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010f\u001a\u0004\u0018\u00010gX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010h\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010i\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010j\u001a\u00020kX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010/\"\u0004\bp\u0010qR\u000e\u0010r\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020kX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010t\u001a\b\u0012\u0004\u0012\u00020u0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010v\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010x\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010y\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010z\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010{\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010|\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u0010\n\u0002\u00105\u001a\u0004\b}\u00102\"\u0004\b~\u00104R\u000e\u0010\u007f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0080\u0001\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0081\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006¾\u0001"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Scaffold;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "andJump", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "autoBlockValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "blockAmount", "", "getBlockAmount", "()I", "setBlockAmount", "(I)V", "blocksToEagleValue", "bmcTicks", "bridgeMode", "canSameY", "cancelSprint", "cancelSprintCustom", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "currRotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "getCurrRotation", "()Lnet/ccbluex/liquidbounce/utils/Rotation;", "delay", "", "delayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "downValue", "eagleSneaking", "eagleValue", "getEagleValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "edgeDistanceValue", "", "expandLengthValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "floatSpeedLevels", "", "getDelay", "getGetDelay", "()J", "highBlock", "highBlockMode", "hitableCheckValue", "isLookingDiagonally", "()Z", "lastGroundY", "getLastGroundY", "()Ljava/lang/Integer;", "setLastGroundY", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "lastPlace", "lockRotation", "lowHopblocksmc", "motionCustom", "motionSpeedCustom", "motionSpeedEffectCustom", "motionSpeedSpeedEffectCustom", "omniDirectionalExpand", "placeDelay", "Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "placeMethod", "placeTick", "getPlaceTick", "setPlaceTick", "placedBlocksWithoutEagle", "prevItem", "prevTowered", "rightSide", "rotationSpeed", "getRotationSpeed", "()F", "rotationSpeedValue", "rotationsValue", "", "safeWalkValue", "sameYSpeed", "searchValue", "shouldGoDown", "shouldJump", "slot", "speedDiagonallyVanilla", "speedVanilla", "spoofGround", "sprintCustom", "sprintModeValue", "getSprintModeValue", "staticRotation", "strafeCustom", "strafeFix", "strafeSpeedCustom", "strafeSpeedCustomValue", "swingValue", "switchPlaceTick", "switchTickValue", "tag", "getTag", "()Ljava/lang/String;", "takeVelo", "targetPlace", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "tellyPlaceTicks", "tellyTicks", "timerValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "towerModeValue", "towerSpeedLevels", "towerStatus", "getTowerStatus", "setTowerStatus", "(Z)V", "towerTicks", "towerTimerValue", "turnSpeedValue", "Lkotlin/ranges/IntRange;", "waitRotation", "watchdogJumped", "watchdogSpeed", "watchdogStarted", "watchdogTower", "watchdogWasEnabled", "y", "getY", "setY", "zitterDirection", "zitterModeValue", "zitterTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "calculateSide", "", "findBlock", "expand", "findTargetPlace", "Lnet/ccbluex/liquidbounce/utils/PlaceRotation;", "pos", "Lnet/minecraft/util/BlockPos;", "offsetPos", "vec3", "Lnet/minecraft/util/Vec3;", "side", "Lnet/minecraft/util/EnumFacing;", "eyes", "maxReach", "raycast", "getFloatSpeed", "", "speedLevel", "getSpeedLevel", "getTowerSpeed", "modifyVec", "original", "direction", "shouldModify", "move", "onDisable", "onEnable", "onGround", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPostUpdate", "Lnet/ccbluex/liquidbounce/event/PostUpdateEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onSprint", "Lnet/ccbluex/liquidbounce/event/SprintEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onTick2", "performBlockRaytrace", "Lnet/minecraft/util/MovingObjectPosition;", "rotation", "place", "rotationStatic", "search", "blockPos", "setMotion", "d", "shouldPlace", "sprint", "towerMove", "CrossSine"}
)
public final class Scaffold extends Module {
   @NotNull
   public static final Scaffold INSTANCE = new Scaffold();
   @NotNull
   private static final Value rotationsValue;
   @NotNull
   private static final ListValue towerModeValue;
   @NotNull
   private static final Value speedVanilla;
   @NotNull
   private static final Value speedDiagonallyVanilla;
   @NotNull
   private static final ListValue placeMethod;
   @NotNull
   private static final ListValue autoBlockValue;
   @NotNull
   private static final BoolValue highBlock;
   @NotNull
   private static final Value highBlockMode;
   @NotNull
   private static final Value switchTickValue;
   @NotNull
   private static final ListValue sprintModeValue;
   @NotNull
   private static final Value lowHopblocksmc;
   @NotNull
   private static final Value sprintCustom;
   @NotNull
   private static final BoolValue cancelSprintCustom;
   @NotNull
   private static final Value motionCustom;
   @NotNull
   private static final Value motionSpeedCustom;
   @NotNull
   private static final Value motionSpeedEffectCustom;
   @NotNull
   private static final Value motionSpeedSpeedEffectCustom;
   @NotNull
   private static final Value strafeCustom;
   @NotNull
   private static final Value strafeSpeedCustom;
   @NotNull
   private static final Value strafeSpeedCustomValue;
   @NotNull
   private static final ListValue bridgeMode;
   @NotNull
   private static final Value waitRotation;
   @NotNull
   private static final Value tellyTicks;
   @NotNull
   private static final Value sameYSpeed;
   @NotNull
   private static final Value andJump;
   @NotNull
   private static final BoolValue strafeFix;
   @NotNull
   private static final BoolValue swingValue;
   @NotNull
   private static final BoolValue searchValue;
   @NotNull
   private static final BoolValue downValue;
   @NotNull
   private static final BoolValue safeWalkValue;
   @NotNull
   private static final BoolValue zitterModeValue;
   @NotNull
   private static final BoolValue rotationSpeedValue;
   @NotNull
   private static final Value turnSpeedValue;
   @NotNull
   private static final IntegerRangeValue placeDelay;
   @NotNull
   private static final IntegerValue expandLengthValue;
   @NotNull
   private static final Value omniDirectionalExpand;
   @NotNull
   private static final FloatValue timerValue;
   @NotNull
   private static final FloatValue towerTimerValue;
   @NotNull
   private static final ListValue eagleValue;
   @NotNull
   private static final Value blocksToEagleValue;
   @NotNull
   private static final Value edgeDistanceValue;
   @NotNull
   private static final ListValue hitableCheckValue;
   @Nullable
   private static PlaceInfo targetPlace;
   @Nullable
   private static Integer lastGroundY;
   @Nullable
   private static Integer y;
   @Nullable
   private static Rotation lockRotation;
   @Nullable
   private static Rotation staticRotation;
   private static int prevItem;
   private static int slot;
   private static boolean cancelSprint;
   private static boolean zitterDirection;
   private static boolean watchdogJumped;
   private static boolean watchdogStarted;
   private static boolean watchdogTower;
   private static boolean watchdogSpeed;
   private static boolean watchdogWasEnabled;
   @NotNull
   private static final MSTimer zitterTimer;
   @NotNull
   private static final TimerMS delayTimer;
   private static int lastPlace;
   private static long delay;
   private static boolean rightSide;
   private static int placedBlocksWithoutEagle;
   private static boolean eagleSneaking;
   private static boolean shouldGoDown;
   private static boolean towerStatus;
   private static int towerTicks;
   private static boolean canSameY;
   private static boolean prevTowered;
   private static boolean shouldJump;
   private static boolean takeVelo;
   private static int bmcTicks;
   private static boolean spoofGround;
   private static int tellyPlaceTicks;
   private static int switchPlaceTick;
   private static int placeTick;
   private static int blockAmount;
   @NotNull
   private static double[] floatSpeedLevels;
   @NotNull
   private static final double[] towerSpeedLevels;

   private Scaffold() {
   }

   @NotNull
   public final ListValue getSprintModeValue() {
      return sprintModeValue;
   }

   @NotNull
   public final ListValue getEagleValue() {
      return eagleValue;
   }

   @Nullable
   public final Integer getLastGroundY() {
      return lastGroundY;
   }

   public final void setLastGroundY(@Nullable Integer var1) {
      lastGroundY = var1;
   }

   @Nullable
   public final Integer getY() {
      return y;
   }

   public final void setY(@Nullable Integer var1) {
      y = var1;
   }

   private final Rotation getCurrRotation() {
      Rotation var10000 = RotationUtils.targetRotation;
      if (var10000 == null) {
         var10000 = new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
      }

      return var10000;
   }

   public final boolean getTowerStatus() {
      return towerStatus;
   }

   public final void setTowerStatus(boolean var1) {
      towerStatus = var1;
   }

   public final int getPlaceTick() {
      return placeTick;
   }

   public final void setPlaceTick(int var1) {
      placeTick = var1;
   }

   public final int getBlockAmount() {
      return blockAmount;
   }

   public final void setBlockAmount(int var1) {
      blockAmount = var1;
   }

   private final boolean isLookingDiagonally() {
      float directionDegree = (float)(MovementUtils.INSTANCE.getDirection() * 57.295779513);
      float yaw = (float)Math.rint((double)(Math.abs(MathHelper.func_76142_g(directionDegree)) / 45.0F)) * 45.0F;
      boolean isYawDiagonal = yaw % (float)90 != 0.0F;
      return isYawDiagonal;
   }

   public void onEnable() {
      prevTowered = false;
      rightSide = false;
      shouldJump = false;
      watchdogStarted = false;
      watchdogJumped = false;
      watchdogWasEnabled = false;
      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         y = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
      }

      if ((Boolean)cancelSprintCustom.get() && sprintModeValue.equals("Custom") || sprintModeValue.equals("BlocksMC")) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.STOP_SPRINTING)));
         cancelSprint = true;
      }

      prevItem = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
      slot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
      if (MinecraftInstance.mc.field_71439_g != null) {
         lastGroundY = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
         zitterTimer.reset();
         tellyPlaceTicks = 0;
         if (towerModeValue.equals("BlocksMC")) {
            bmcTicks = 5;
         }

      }
   }

   @EventTarget
   public final void onSprint(@NotNull SprintEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      event.setSprint(this.sprint());
   }

   @EventTarget
   public final void onTick2(@NotNull TickEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get()) != -1) {
         this.findBlock(((Number)expandLengthValue.get()).intValue() > 1);
         if (towerStatus) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)towerTimerValue.get()).floatValue();
            this.move();
            prevTowered = true;
            canSameY = false;
            lastGroundY = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
            y = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
            towerTicks = 0;
         }
      }

      if (takeVelo && MinecraftInstance.mc.field_71439_g.field_70737_aN <= 0) {
         takeVelo = false;
      }

      if (bridgeMode.equals("GodBridge") && (Boolean)waitRotation.get()) {
         MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = placeTick == 0;
      }

      this.rotationStatic();
      if (placeMethod.equals("GameTick")) {
         this.place();
      }

   }

   @EventTarget
   public final void onPreUpdate(@NotNull PreUpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.calculateSide();
      if (towerModeValue.equals("WatchDog") && towerStatus) {
         if (MovementUtils.INSTANCE.isMoving()) {
            watchdogSpeed = false;
            int simpleY = (int)Math.round(MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1.0F * (double)100.0F);
            if (MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1 == (double)0.0F && MinecraftInstance.mc.field_71439_g.field_70122_E) {
               watchdogTower = true;
            }

            if (watchdogTower) {
               switch (simpleY) {
                  case 0:
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                     if (PlayerUtils.getOffGroundTicks() == 6) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = -0.078400001525879;
                     }

                     MovementUtils.INSTANCE.strafe(this.getTowerSpeed(this.getSpeedLevel()));
                     watchdogSpeed = true;
                     break;
                  case 42:
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.33;
                     MovementUtils.INSTANCE.strafe(this.getTowerSpeed(this.getSpeedLevel()));
                     watchdogSpeed = true;
                     break;
                  case 75:
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)1 - MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1.0F;
               }
            }
         } else {
            watchdogTower = false;
         }
      }

      if (placeMethod.equals("Pre")) {
         this.place();
      }

   }

   @EventTarget
   public final void onPostUpdate(@NotNull PostUpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (placeMethod.equals("Post")) {
         this.place();
      }

   }

   @EventTarget
   public final void onTick(@NotNull TickEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (eagleValue.equals("Silent") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
         y = null;
      }

      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         if (y == null) {
            y = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
         }

         if (lastGroundY == null) {
            lastGroundY = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
         }
      }

      if (MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
         if (!(Boolean)highBlock.get()) {
            if (blockAmount == 0) {
               blockAmount = MinecraftInstance.mc.field_71439_g.func_70694_bm().field_77994_a;
            }
         } else {
            blockAmount = MinecraftInstance.mc.field_71439_g.func_70694_bm().field_77994_a;
         }
      }

      if (bridgeMode.equals("GodBridge") && PlayerUtils.INSTANCE.BlockUnderPlayerIsEmpty() && !towerStatus && MinecraftInstance.mc.field_71439_g.field_70122_E) {
         MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 4, (Object)null);
      }

      label229: {
         label238: {
            if (lastGroundY != null) {
               double var10000 = MinecraftInstance.mc.field_71439_g.field_70163_u;
               Integer var10001 = lastGroundY;
               Intrinsics.checkNotNull(var10001);
               if (var10000 < (double)var10001) {
                  break label238;
               }
            }

            if (y == null) {
               break label229;
            }

            double var14 = MinecraftInstance.mc.field_71439_g.field_70163_u;
            Integer var15 = y;
            Intrinsics.checkNotNull(var15);
            if (!(var14 < (double)var15)) {
               break label229;
            }
         }

         y = null;
         lastGroundY = null;
      }

      if (lastPlace == 1) {
         delayTimer.reset();
         delay = this.getGetDelay();
         MouseUtils.INSTANCE.setRightClicked(false);
         lastPlace = 0;
      }

      if (!towerStatus) {
         if (bridgeMode.equals("AutoJump")) {
            canSameY = true;
            if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            }
         }

         if (sprintModeValue.equals("WatchDog")) {
            canSameY = true;
            if (GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G)) {
               MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = false;
               if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
                  MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
               }
            }
         }

         if (bridgeMode.equals("SameY") && (!(Boolean)sameYSpeed.get() || Speed.INSTANCE.getState())) {
            canSameY = true;
            if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            }
         }

         if (bridgeMode.equals("Telly") && this.onGround() && MovementUtils.INSTANCE.isMoving()) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
         }

         if (bridgeMode.equals("KeepUP")) {
            canSameY = false;
            if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            }
         }
      }

      if (bridgeMode.equals("Andromeda") && !(BlockUtils.getBlock((new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177977_b()) instanceof BlockAir) && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
         if ((Boolean)andJump.get() && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
         }

         lockRotation = null;
      }

      if (!towerStatus) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)timerValue.get()).floatValue();
      }

      shouldGoDown = (Boolean)downValue.get() && GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74311_E);
      if (shouldGoDown) {
         MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
      }

      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         if ((Boolean)zitterModeValue.get()) {
            if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74366_z)) {
               MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = false;
            }

            if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74370_x)) {
               MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = false;
            }

            if (zitterTimer.hasTimePassed(100L)) {
               zitterDirection = !zitterDirection;
               zitterTimer.reset();
            }

            if (zitterDirection) {
               MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = true;
               MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = false;
            } else {
               MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = false;
               MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = true;
            }
         }

         if (!StringsKt.equals((String)eagleValue.get(), "Off", true) && !shouldGoDown) {
            double dif = (double)0.5F;
            BlockPos blockPos = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v);
            if (((Number)edgeDistanceValue.get()).floatValue() > 0.0F) {
               EnumFacing[] var5 = EnumFacing.values();
               int var6 = 0;
               int var7 = var5.length;

               while(var6 < var7) {
                  EnumFacing facingType = var5[var6];
                  ++var6;
                  if (facingType != EnumFacing.UP && facingType != EnumFacing.DOWN) {
                     BlockPos neighbor = blockPos.func_177972_a(facingType);
                     if (BlockUtils.isReplaceable(neighbor)) {
                        double calcDif = (facingType != EnumFacing.NORTH && facingType != EnumFacing.SOUTH ? Math.abs((double)neighbor.func_177958_n() + (double)0.5F - MinecraftInstance.mc.field_71439_g.field_70165_t) : Math.abs((double)neighbor.func_177952_p() + (double)0.5F - MinecraftInstance.mc.field_71439_g.field_70161_v)) - (double)0.5F;
                        if (calcDif < dif) {
                           dif = calcDif;
                        }
                     }
                  }
               }
            }

            if (placedBlocksWithoutEagle >= ((Number)blocksToEagleValue.get()).intValue()) {
               boolean shouldEagle = BlockUtils.isReplaceable(blockPos) || ((Number)edgeDistanceValue.get()).floatValue() > 0.0F && dif < (double)((Number)edgeDistanceValue.get()).floatValue();
               if (StringsKt.equals((String)eagleValue.get(), "Packet", true)) {
                  if (eagleSneaking != shouldEagle) {
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, shouldEagle ? Action.START_SNEAKING : Action.STOP_SNEAKING)));
                  }

                  eagleSneaking = shouldEagle;
               } else {
                  MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = shouldEagle;
               }

               placedBlocksWithoutEagle = 0;
            } else {
               int var13 = placedBlocksWithoutEagle++;
            }
         }
      }

   }

   @EventTarget
   public final void onStrafe(@NotNull StrafeEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)strafeFix.get()) {
         Float yaw = RotationUtils.playerYaw;
         float var10000 = MinecraftInstance.mc.field_71439_g.field_70177_z;
         Intrinsics.checkNotNullExpressionValue(yaw, "yaw");
         int dif = (int)((MathHelper.func_76142_g(var10000 - yaw - 23.5F - (float)135) + (float)180) / (float)45);
         float strafe = event.getStrafe();
         float forward = event.getForward();
         float friction = event.getFriction();
         float calcForward = 0.0F;
         float calcStrafe = 0.0F;
         switch (dif) {
            case 0:
               calcForward = forward;
               calcStrafe = strafe;
               break;
            case 1:
               calcForward += forward;
               calcStrafe -= forward;
               calcForward += strafe;
               calcStrafe += strafe;
               break;
            case 2:
               calcForward = strafe;
               calcStrafe = -forward;
               break;
            case 3:
               calcForward -= forward;
               calcStrafe -= forward;
               calcForward += strafe;
               calcStrafe -= strafe;
               break;
            case 4:
               calcForward = -forward;
               calcStrafe = -strafe;
               break;
            case 5:
               calcForward -= forward;
               calcStrafe += forward;
               calcForward -= strafe;
               calcStrafe -= strafe;
               break;
            case 6:
               calcForward = -strafe;
               calcStrafe = forward;
               break;
            case 7:
               calcForward += forward;
               calcStrafe += forward;
               calcForward -= strafe;
               calcStrafe += strafe;
         }

         if (calcForward > 1.0F || calcForward < 0.9F && calcForward > 0.3F || calcForward < -1.0F || calcForward > -0.9F && calcForward < -0.3F) {
            calcForward *= 0.5F;
         }

         if (calcStrafe > 1.0F || calcStrafe < 0.9F && calcStrafe > 0.3F || calcStrafe < -1.0F || calcStrafe > -0.9F && calcStrafe < -0.3F) {
            calcStrafe *= 0.5F;
         }

         float f = calcStrafe * calcStrafe + calcForward * calcForward;
         if (f >= 1.0E-4F) {
            f = MathHelper.func_76129_c(f);
            if (f < 1.0F) {
               f = 1.0F;
            }

            f = friction / f;
            calcStrafe *= f;
            calcForward *= f;
            float yawSin = MathHelper.func_76126_a((float)((double)yaw * Math.PI / (double)180.0F));
            float yawCos = MathHelper.func_76134_b((float)((double)yaw * Math.PI / (double)180.0F));
            EntityPlayerSP var12 = MinecraftInstance.mc.field_71439_g;
            var12.field_70159_w += (double)(calcStrafe * yawCos - calcForward * yawSin);
            var12 = MinecraftInstance.mc.field_71439_g;
            var12.field_70179_y += (double)(calcForward * yawCos + calcStrafe * yawSin);
         }

         event.cancelEvent();
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null) {
         Packet packet = event.getPacket();
         if (packet instanceof C08PacketPlayerBlockPlacement) {
            ((C08PacketPlayerBlockPlacement)packet).field_149577_f = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149577_f, -1.0F, 1.0F);
            ((C08PacketPlayerBlockPlacement)packet).field_149578_g = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149578_g, -1.0F, 1.0F);
            ((C08PacketPlayerBlockPlacement)packet).field_149584_h = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149584_h, -1.0F, 1.0F);
         }

         if (packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).func_149412_c() == MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            takeVelo = true;
         }

         if ((sprintModeValue.equals("Custom") || sprintModeValue.equals("BlocksMC")) && (Boolean)cancelSprintCustom.get() && packet instanceof C0BPacketEntityAction && cancelSprint) {
            event.cancelEvent();
         }

      }
   }

   private final void rotationStatic() {
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var3) {
         case "normal":
            staticRotation = lockRotation == null ? new Rotation(MovementUtils.INSTANCE.getMovingYaw() - 180.0F, 80.0F) : lockRotation;
            break;
         case "telly":
            float var5;
            if (prevTowered) {
               if (lockRotation == null) {
                  var5 = MovementUtils.INSTANCE.getMovingYaw() - (float)180;
               } else {
                  Rotation var6 = lockRotation;
                  Intrinsics.checkNotNull(var6);
                  var5 = var6.getYaw();
               }
            } else if (!this.shouldPlace()) {
               var5 = MovementUtils.INSTANCE.getMovingYaw();
            } else if (lockRotation == null) {
               var5 = MovementUtils.INSTANCE.getMovingYaw() - (float)180;
            } else {
               Rotation var7 = lockRotation;
               Intrinsics.checkNotNull(var7);
               var5 = var7.getYaw();
            }

            float rotationYaw = var5;
            Rotation var8 = new Rotation;
            float var10003;
            if (lockRotation == null) {
               var10003 = 85.0F;
            } else {
               Rotation var14 = lockRotation;
               Intrinsics.checkNotNull(var14);
               var10003 = var14.getPitch();
            }

            var8.<init>(rotationYaw, var10003);
            staticRotation = var8;
            break;
         case "vanilla":
            PlaceInfo var10000 = targetPlace;
            Intrinsics.checkNotNull(var10000);
            EnumFacing var4 = var10000.getEnumFacing();
            PlaceInfo var10001 = targetPlace;
            Intrinsics.checkNotNull(var10001);
            staticRotation = RotationUtils.getFaceRotation(var4, var10001.getBlockPos());
            break;
         case "watchdog":
            staticRotation = new Rotation(MovementUtils.INSTANCE.getMovingYaw() + (float)130, towerStatus ? 90.0F : 88.0F);
            break;
         case "stabilized":
            staticRotation = lockRotation == null ? new Rotation(MovementUtils.INSTANCE.getMovingYaw() - (float)180, 80.0F) : lockRotation;
      }

      if (bridgeMode.equals("GodBridge")) {
         Rotation var11;
         if (!prevTowered && !takeVelo) {
            var11 = new Rotation(this.isLookingDiagonally() ? MovementUtils.INSTANCE.getMovingYaw() - (float)180 : MovementUtils.INSTANCE.getMovingYaw() + (rightSide ? 135.0F : -135.0F), 75.5F);
         } else {
            PlaceInfo var9 = targetPlace;
            Intrinsics.checkNotNull(var9);
            double var10 = (double)var9.getBlockPos().func_177958_n() + (double)0.5F;
            PlaceInfo var12 = targetPlace;
            Intrinsics.checkNotNull(var12);
            double var13 = (double)var12.getBlockPos().func_177956_o() + (double)0.5F;
            PlaceInfo var10002 = targetPlace;
            Intrinsics.checkNotNull(var10002);
            var11 = RotationUtils.getRotations(var10, var13, (double)var10002.getBlockPos().func_177952_p() + (double)0.5F);
         }

         staticRotation = var11;
      }

      if (staticRotation != null) {
         RotationUtils.setTargetRotation(RotationUtils.limitAngleChange(RotationUtils.serverRotation, staticRotation, this.getRotationSpeed()), 20);
      }

   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get()) != -1) {
         label147: {
            if (MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
               InventoryUtils var10000 = InventoryUtils.INSTANCE;
               Item var10001 = MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b();
               if (var10001 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
               }

               if (!var10000.isBlockListBlock((ItemBlock)var10001) && (!(Boolean)highBlock.get() || (Boolean)highBlockMode.get()) && ((Boolean)highBlock.get() || placeTick < blockAmount) && (!(Boolean)highBlock.get() || !(Boolean)highBlockMode.get() || switchPlaceTick < ((Number)switchTickValue.get()).intValue())) {
                  break label147;
               }
            }

            SlotUtils.INSTANCE.setSlot(InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get() && !(Boolean)highBlockMode.get() || !(Boolean)highBlock.get() && placeTick >= blockAmount || (Boolean)highBlock.get() && (Boolean)highBlockMode.get() && switchPlaceTick >= ((Number)switchTickValue.get()).intValue()) - 36, autoBlockValue.equals("Spoof"), this.getName());
            if (!(Boolean)highBlock.get()) {
               blockAmount = 0;
            }

            placeTick = 0;
            switchPlaceTick = 0;
            MinecraftInstance.mc.field_71442_b.func_78765_e();
         }
      }

      if (towerModeValue.equals("BlocksMC") && bmcTicks > 0) {
         bmcTicks += -1;
         int var2 = bmcTicks;
      }

      if (event.isPre()) {
         if (sprintModeValue.equals("WatchDog") && !Speed.INSTANCE.getState() && !towerStatus) {
            watchdogWasEnabled = true;
            if (!watchdogStarted) {
               if (PlayerUtils.getGroundTicks() > 8 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  MinecraftInstance.mc.field_71439_g.func_70664_aZ();
                  MovementUtils.INSTANCE.strafe((double)MovementUtils.INSTANCE.getSpeed() - 0.1);
                  watchdogJumped = true;
               } else if (PlayerUtils.getGroundTicks() <= 8 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  watchdogStarted = true;
               }

               if (watchdogJumped && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  watchdogStarted = true;
               }
            }

            if (watchdogStarted && MinecraftInstance.mc.field_71439_g.field_70122_E) {
               event.setY(event.getY() + (double)1.0E-12F);
               if (MovementUtils.INSTANCE.isMoving()) {
                  MovementUtils.INSTANCE.strafe(this.getFloatSpeed(this.getSpeedLevel()));
               }
            }
         } else if (watchdogWasEnabled) {
            watchdogStarted = false;
            watchdogJumped = false;
            watchdogWasEnabled = false;
         }

         if (towerModeValue.equals("WatchDog") && towerStatus && PlayerUtils.getOffGroundTicks() == 6) {
            event.setY(event.getY() + 3.83527E-4);
         }
      }

      if (PlayerUtils.getOffGroundTicks() <= 3 && !towerStatus) {
         towerStatus = MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d();
      }

      if (!MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
         towerStatus = false;
      }

   }

   private final int getSpeedLevel() {
      Iterator var1 = MinecraftInstance.mc.field_71439_g.func_70651_bq().iterator();
      if (var1.hasNext()) {
         PotionEffect potionEffect = (PotionEffect)var1.next();
         return Intrinsics.areEqual((Object)potionEffect.func_76453_d(), (Object)"potion.moveSpeed") ? potionEffect.func_76458_c() + 1 : 0;
      } else {
         return 0;
      }
   }

   private final double getFloatSpeed(int speedLevel) {
      return speedLevel >= 0 ? floatSpeedLevels[speedLevel] : floatSpeedLevels[0];
   }

   private final void move() {
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var2) {
         case "blocksmc":
            MovementUtils.INSTANCE.strafe();
            if (!MovementUtils.INSTANCE.isMoving()) {
               if (PlayerUtils.INSTANCE.blockNear(2)) {
                  if (MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1 <= 0.00153598) {
                     MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                  } else if (MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1 < 0.1 && PlayerUtils.getOffGroundTicks() != 0) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
                  }
               }
            } else {
               if (bmcTicks != 0) {
                  towerTicks = 0;
                  return;
               }

               if (towerTicks > 0) {
                  ++towerTicks;
                  int var10000 = towerTicks;
                  if (towerTicks > 6) {
                     this.setMotion((double)MovementUtils.INSTANCE.getSpeed() * (this.isLookingDiagonally() ? 0.15 : 0.3));
                  }

                  if (towerTicks > 16) {
                     towerTicks = 0;
                  }
               }

               this.towerMove();
            }
            break;
         case "ncp":
            MovementUtils.INSTANCE.strafe();
            if (PlayerUtils.INSTANCE.blockNear(2)) {
               if (MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1 <= 0.00153598) {
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
                  MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
               } else if (MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1 < 0.1 && PlayerUtils.getOffGroundTicks() != 0) {
                  MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
               }
            }
            break;
         case "vanilla":
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.41965;
      }

   }

   private final void setMotion(double d) {
      float f = MathHelper.func_76142_g((float)Math.toDegrees(Math.atan2(MinecraftInstance.mc.field_71439_g.field_70179_y, MinecraftInstance.mc.field_71439_g.field_70159_w)) - 90.0F);
      MovementUtils.INSTANCE.setMotion2(d, f);
   }

   private final double getTowerSpeed(int speedLevel) {
      return speedLevel >= 0 ? towerSpeedLevels[speedLevel] : towerSpeedLevels[0];
   }

   private final void findBlock(boolean expand) {
      if (this.shouldPlace()) {
         BlockPos var55;
         if (shouldGoDown) {
            EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
            Double var54 = var10000 == null ? null : var10000.field_70163_u;
            EntityPlayerSP var10001 = MinecraftInstance.mc.field_71439_g;
            Double var71;
            if (var10001 == null) {
               var71 = null;
            } else {
               double var4 = var10001.field_70163_u;
               int var6 = (int)var4;
               var71 = (double)var6 + (double)0.5F;
            }

            var55 = Intrinsics.areEqual(var54, var71) ? new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.6, MinecraftInstance.mc.field_71439_g.field_70161_v) : (new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.6, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177977_b();
         } else if (bridgeMode.equals("Telly") && !towerStatus) {
            var55 = new BlockPos;
            EntityPlayerSP var86 = MinecraftInstance.mc.field_71439_g;
            double var87;
            if (var86 == null) {
               var87 = (double)0.0F;
            } else {
               double var29 = var86.field_70165_t;
               var87 = var29;
            }

            Integer var101 = lastGroundY;
            double var102;
            if (var101 == null) {
               var102 = (double)0.0F;
            } else {
               double var30 = (double)var101 - (double)1.0F;
               var102 = var30;
            }

            EntityPlayerSP var114 = MinecraftInstance.mc.field_71439_g;
            double var115;
            if (var114 == null) {
               var115 = (double)0.0F;
            } else {
               double var31 = var114.field_70161_v;
               var115 = var31;
            }

            var55.<init>(var87, var102, var115);
         } else if (bridgeMode.equals("UpSideDown") && !towerStatus) {
            var55 = new BlockPos;
            EntityPlayerSP var84 = MinecraftInstance.mc.field_71439_g;
            double var85;
            if (var84 == null) {
               var85 = (double)0.0F;
            } else {
               double var26 = var84.field_70165_t;
               var85 = var26;
            }

            EntityPlayerSP var98 = MinecraftInstance.mc.field_71439_g;
            double var99;
            if (var98 == null) {
               var99 = (double)0.0F;
            } else {
               double var27 = var98.field_70163_u;
               var99 = var27;
            }

            var99 += (double)2;
            EntityPlayerSP var112 = MinecraftInstance.mc.field_71439_g;
            double var113;
            if (var112 == null) {
               var113 = (double)0.0F;
            } else {
               double var28 = var112.field_70161_v;
               var113 = var28;
            }

            var55.<init>(var85, var99, var113);
         } else if (bridgeMode.equals("Andromeda") && !towerStatus) {
            var55 = new BlockPos;
            EntityPlayerSP var78 = MinecraftInstance.mc.field_71439_g;
            double var79;
            if (var78 == null) {
               var79 = (double)0.0F;
            } else {
               double var34 = var78.field_70165_t;
               var79 = var34;
            }

            EntityPlayerSP var91 = MinecraftInstance.mc.field_71439_g;
            double var92;
            if (var91 == null) {
               var92 = (double)0.0F;
            } else {
               double var35 = var91.field_70163_u;
               var92 = var35;
            }

            EntityPlayerSP var106 = MinecraftInstance.mc.field_71439_g;
            double var107;
            if (var106 == null) {
               var107 = (double)0.0F;
            } else {
               double var36 = var106.field_70161_v;
               var107 = var36;
            }

            var55.<init>(var79, var92, var107);
            Block block = BlockUtils.getBlock(var55.func_177977_b());
            if (block instanceof BlockAir) {
               var55 = new BlockPos;
               EntityPlayerSP var80 = MinecraftInstance.mc.field_71439_g;
               double var81;
               if (var80 == null) {
                  var81 = (double)0.0F;
               } else {
                  double var37 = var80.field_70165_t;
                  var81 = var37;
               }

               EntityPlayerSP var93 = MinecraftInstance.mc.field_71439_g;
               double var94;
               if (var93 == null) {
                  var94 = (double)0.0F;
               } else {
                  double var38 = var93.field_70163_u;
                  var94 = var38;
               }

               EntityPlayerSP var108 = MinecraftInstance.mc.field_71439_g;
               double var109;
               if (var108 == null) {
                  var109 = (double)0.0F;
               } else {
                  double var39 = var108.field_70161_v;
                  var109 = var39;
               }

               var55.<init>(var81, var94, var109);
               var55 = var55.func_177977_b();
            } else {
               var55 = new BlockPos;
               EntityPlayerSP var82 = MinecraftInstance.mc.field_71439_g;
               double var83;
               if (var82 == null) {
                  var83 = (double)0.0F;
               } else {
                  double var40 = var82.field_70165_t;
                  var83 = var40;
               }

               EntityPlayerSP var95 = MinecraftInstance.mc.field_71439_g;
               double var96;
               if (var95 == null) {
                  var96 = (double)0.0F;
               } else {
                  double var41 = var95.field_70163_u;
                  var96 = var41;
               }

               var96 += (double)2.5F;
               EntityPlayerSP var110 = MinecraftInstance.mc.field_71439_g;
               double var111;
               if (var110 == null) {
                  var111 = (double)0.0F;
               } else {
                  double var42 = var110.field_70161_v;
                  var111 = var42;
               }

               var55.<init>(var83, var96, var111);
            }
         } else {
            EntityPlayerSP var56 = MinecraftInstance.mc.field_71439_g;
            Double var57 = var56 == null ? null : var56.field_70163_u;
            EntityPlayerSP var72 = MinecraftInstance.mc.field_71439_g;
            Double var73;
            if (var72 == null) {
               var73 = null;
            } else {
               double var19 = var72.field_70163_u;
               int var33 = (int)var19;
               var73 = (double)var33 + (double)0.5F;
            }

            if (Intrinsics.areEqual(var57, var73) && !canSameY) {
               EntityPlayerSP var77 = MinecraftInstance.mc.field_71439_g;
               Intrinsics.checkNotNull(var77);
               var55 = new BlockPos((Entity)var77);
            } else if (canSameY) {
               var55 = new BlockPos;
               EntityPlayerSP var10002 = MinecraftInstance.mc.field_71439_g;
               double var74;
               if (var10002 == null) {
                  var74 = (double)0.0F;
               } else {
                  double var20 = var10002.field_70165_t;
                  var74 = var20;
               }

               Integer var10003 = lastGroundY;
               double var88;
               if (var10003 == null) {
                  var88 = (double)0.0F;
               } else {
                  double var21 = (double)var10003 - (double)1.0F;
                  var88 = var21;
               }

               EntityPlayerSP var10004 = MinecraftInstance.mc.field_71439_g;
               double var103;
               if (var10004 == null) {
                  var103 = (double)0.0F;
               } else {
                  double var22 = var10004.field_70161_v;
                  var103 = var22;
               }

               var55.<init>(var74, var88, var103);
            } else {
               BlockPos var58 = new BlockPos;
               EntityPlayerSP var75 = MinecraftInstance.mc.field_71439_g;
               double var76;
               if (var75 == null) {
                  var76 = (double)0.0F;
               } else {
                  double var23 = var75.field_70165_t;
                  var76 = var23;
               }

               EntityPlayerSP var89 = MinecraftInstance.mc.field_71439_g;
               double var90;
               if (var89 == null) {
                  var90 = (double)0.0F;
               } else {
                  double var24 = var89.field_70163_u;
                  var90 = var24;
               }

               EntityPlayerSP var104 = MinecraftInstance.mc.field_71439_g;
               double var105;
               if (var104 == null) {
                  var105 = (double)0.0F;
               } else {
                  double var25 = var104.field_70161_v;
                  var105 = var25;
               }

               var58.<init>(var76, var90, var105);
               var55 = var58.func_177977_b();
            }
         }

         BlockPos blockPosition = var55;
         if (blockPosition != null) {
            if (expand || BlockUtils.isReplaceable(blockPosition) && !this.search(blockPosition, !shouldGoDown)) {
               if (expand) {
                  EntityPlayerSP var61 = MinecraftInstance.mc.field_71439_g;
                  double var62;
                  if (var61 == null) {
                     var62 = (double)0.0F;
                  } else {
                     float var8 = var61.field_70177_z;
                     double var9 = OtherExtensionKt.toRadiansD(var8);
                     var62 = var9;
                  }

                  double yaw = var62;
                  int var63;
                  if ((Boolean)omniDirectionalExpand.get()) {
                     var63 = -MathKt.roundToInt(Math.sin(yaw));
                  } else {
                     EntityPlayerSP var64 = MinecraftInstance.mc.field_71439_g;
                     if (var64 == null) {
                        var63 = 0;
                     } else {
                        EnumFacing var65 = var64.func_174811_aO();
                        if (var65 == null) {
                           var63 = 0;
                        } else {
                           Vec3i var66 = var65.func_176730_m();
                           if (var66 == null) {
                              var63 = 0;
                           } else {
                              int var12 = var66.func_177958_n();
                              var63 = var12;
                           }
                        }
                     }
                  }

                  int x = var63;
                  if ((Boolean)omniDirectionalExpand.get()) {
                     var63 = MathKt.roundToInt(Math.cos(yaw));
                  } else {
                     EntityPlayerSP var68 = MinecraftInstance.mc.field_71439_g;
                     if (var68 == null) {
                        var63 = 0;
                     } else {
                        EnumFacing var69 = var68.func_174811_aO();
                        if (var69 == null) {
                           var63 = 0;
                        } else {
                           Vec3i var70 = var69.func_176730_m();
                           if (var70 == null) {
                              var63 = 0;
                           } else {
                              int var13 = var70.func_177952_p();
                              var63 = var13;
                           }
                        }
                     }
                  }

                  int z = var63;
                  int var47 = 0;
                  int var11 = ((Number)expandLengthValue.get()).intValue();

                  while(var47 < var11) {
                     int i = var47++;
                     BlockPos var52 = blockPosition.func_177982_a(x * i, 0, z * i);
                     Intrinsics.checkNotNullExpressionValue(var52, "blockPosition.add(x * i, 0, z * i)");
                     if (this.search(var52, false)) {
                        return;
                     }
                  }
               } else if ((Boolean)searchValue.get()) {
                  Pair var18 = bridgeMode.equals("Telly") ? TuplesKt.to(2, 2) : TuplesKt.to(1, 1);
                  int horizontal = ((Number)var18.component1()).intValue();
                  int vertical = ((Number)var18.component2()).intValue();
                  int var46 = -horizontal;
                  int x;
                  if (var46 <= horizontal) {
                     do {
                        x = var46++;
                        int var49 = 0;
                        int var51 = -vertical;
                        int y;
                        if (var51 <= 0) {
                           do {
                              y = var49--;
                              int var14 = -horizontal;
                              int z;
                              if (var14 <= horizontal) {
                                 do {
                                    z = var14++;
                                    BlockPos var16 = blockPosition.func_177982_a(x, y, z);
                                    Intrinsics.checkNotNullExpressionValue(var16, "blockPosition.add(x, y, z)");
                                    if (this.search(var16, !shouldGoDown)) {
                                       return;
                                    }
                                 } while(z != horizontal);
                              }
                           } while(y != var51);
                        }
                     } while(x != horizontal);
                  }
               }

            }
         }
      }
   }

   private final boolean shouldPlace() {
      if (!delayTimer.hasTimePassed(delay) && !towerStatus) {
         return false;
      } else {
         return prevTowered || !bridgeMode.equals("Telly") || PlayerUtils.getOffGroundTicks() >= ((Number)tellyTicks.get()).intValue() && PlayerUtils.getOffGroundTicks() < 11;
      }
   }

   private final void place() {
      if (this.shouldPlace()) {
         if (!rotationsValue.equals("None")) {
            EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(var2, "mc.thePlayer");
            MovingObjectPosition rayTraceInfo = EntityExtensionKt.rayTraceWithServerSideRotation((Entity)var2, (double)MinecraftInstance.mc.field_71442_b.func_78757_d());
            String var3 = ((String)hitableCheckValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (var3.hashCode()) {
               case -902286926:
                  if (var3.equals("simple") && rayTraceInfo != null) {
                     BlockPos var11 = rayTraceInfo.func_178782_a();
                     PlaceInfo var16 = targetPlace;
                     Intrinsics.checkNotNull(var16);
                     if (!var11.equals(var16.getBlockPos())) {
                        return;
                     }
                  }
                  break;
               case -891986231:
                  if (var3.equals("strict") && rayTraceInfo != null) {
                     BlockPos var9 = rayTraceInfo.func_178782_a();
                     PlaceInfo var14 = targetPlace;
                     Intrinsics.checkNotNull(var14);
                     if (!var9.equals(var14.getBlockPos())) {
                        return;
                     }

                     EnumFacing var10 = rayTraceInfo.field_178784_b;
                     var14 = targetPlace;
                     Intrinsics.checkNotNull(var14);
                     if (var10 != var14.getEnumFacing()) {
                        return;
                     }
                  }
                  break;
               case 102851513:
                  if (var3.equals("legit") && MinecraftInstance.mc.field_71476_x != null) {
                     BlockPos var10000 = MinecraftInstance.mc.field_71476_x.func_178782_a();
                     PlaceInfo var10001 = targetPlace;
                     Intrinsics.checkNotNull(var10001);
                     if (!Intrinsics.areEqual((Object)var10000, (Object)var10001.getBlockPos())) {
                        return;
                     }

                     EnumFacing var8 = MinecraftInstance.mc.field_71476_x.field_178784_b;
                     var10001 = targetPlace;
                     Intrinsics.checkNotNull(var10001);
                     if (var8 != var10001.getEnumFacing()) {
                        return;
                     }
                  }
            }
         }

         if (InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get()) != -1 && targetPlace != null) {
            PlayerControllerMP var12 = MinecraftInstance.mc.field_71442_b;
            EntityPlayerSP var17 = MinecraftInstance.mc.field_71439_g;
            WorldClient var10002 = MinecraftInstance.mc.field_71441_e;
            ItemStack var10003 = MinecraftInstance.mc.field_71439_g.func_70694_bm();
            PlaceInfo var10004 = targetPlace;
            Intrinsics.checkNotNull(var10004);
            BlockPos var18 = var10004.getBlockPos();
            PlaceInfo var10005 = targetPlace;
            Intrinsics.checkNotNull(var10005);
            EnumFacing var19 = var10005.getEnumFacing();
            PlaceInfo var10006 = targetPlace;
            Intrinsics.checkNotNull(var10006);
            if (var12.func_178890_a(var17, var10002, var10003, var18, var19, var10006.getVec3())) {
               if ((Boolean)swingValue.get()) {
                  MinecraftInstance.mc.field_71439_g.func_71038_i();
               } else {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
               }

               int var4 = tellyPlaceTicks++;
               var4 = lastPlace++;
               MouseUtils.INSTANCE.setRightClicked(true);
               CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
               if ((Boolean)highBlockMode.get() && (Boolean)highBlock.get()) {
                  var4 = switchPlaceTick++;
               }

               if (!(Boolean)highBlock.get() && MinecraftInstance.mc.field_71442_b.func_78762_g()) {
                  var4 = placeTick++;
               }
            }
         }

         targetPlace = null;
      }
   }

   public void onDisable() {
      MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G));
      takeVelo = false;
      y = null;
      tellyPlaceTicks = 0;
      cancelSprint = false;
      if (MinecraftInstance.mc.field_71439_g != null) {
         if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74311_E)) {
            MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
            if (eagleSneaking) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.STOP_SNEAKING)));
            }
         }

         if (sprintModeValue.equals("WatchDog")) {
            if (this.onGround()) {
               EntityPlayerSP var1 = MinecraftInstance.mc.field_71439_g;
               var1.field_70159_w *= 0.15;
               var1 = MinecraftInstance.mc.field_71439_g;
               var1.field_70179_y *= 0.15;
            } else {
               EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
               var3.field_70159_w *= (double)0.5F;
               var3 = MinecraftInstance.mc.field_71439_g;
               var3.field_70179_y *= (double)0.5F;
            }
         }

         canSameY = false;
         if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74366_z)) {
            MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = false;
         }

         if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74370_x)) {
            MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = false;
         }

         lockRotation = null;
         staticRotation = null;
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         shouldGoDown = false;
         RotationUtils.reset();
         SlotUtils.INSTANCE.stopSet();
         placeTick = 0;
         switchPlaceTick = 0;
         blockAmount = 0;
         towerStatus = false;
      }
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.shouldPlace()) {
         if (towerModeValue.equals("BlocksMC") && towerStatus) {
            if (!MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
               event.setX(event.getX() * (double)0.75F);
               event.setZ(event.getZ() * (double)0.75F);
            } else {
               event.setX(event.getX() * 0.7);
               event.setZ(event.getZ() * 0.7);
            }
         }

         if (towerModeValue.equals("WatchDog") && towerStatus) {
            event.setX(event.getX() * (double)(this.isLookingDiagonally() ? ((Number)speedDiagonallyVanilla.get()).floatValue() : ((Number)speedVanilla.get()).floatValue()));
            event.setZ(event.getZ() * (double)(this.isLookingDiagonally() ? ((Number)speedDiagonallyVanilla.get()).floatValue() : ((Number)speedVanilla.get()).floatValue()));
         }

         if (sprintModeValue.equals("BlocksMC") && bridgeMode.equals("AutoJump") && (Boolean)lowHopblocksmc.get() && !towerStatus && PlayerUtils.getOffGroundTicks() == 4) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.098;
         }

         if (sprintModeValue.equals("BlocksMC") && !towerStatus) {
            MovementUtils.INSTANCE.strafe();
            if (bridgeMode.equals("AutoJump")) {
               if (!MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
                  if ((Boolean)lowHopblocksmc.get()) {
                     event.setX(event.getX() * 0.867);
                     event.setZ(event.getZ() * 0.867);
                  } else {
                     event.setX(event.getX() * 0.941);
                     event.setZ(event.getZ() * 0.941);
                  }
               } else if ((Boolean)lowHopblocksmc.get()) {
                  if (!this.isLookingDiagonally()) {
                     event.setX(event.getX() * 1.04);
                     event.setZ(event.getZ() * 1.04);
                  }
               } else {
                  event.setX(event.getX() * (double)1.125F);
                  event.setZ(event.getZ() * (double)1.125F);
               }
            }
         }

         if (sprintModeValue.equals("Custom")) {
            if (!(Boolean)motionCustom.get() || (Boolean)motionSpeedEffectCustom.get() && MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
               if ((Boolean)motionSpeedEffectCustom.get() && MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
                  event.setX(event.getX() * ((Number)motionSpeedSpeedEffectCustom.get()).doubleValue());
                  event.setZ(event.getZ() * ((Number)motionSpeedSpeedEffectCustom.get()).doubleValue());
               }
            } else {
               event.setX(event.getX() * ((Number)motionSpeedCustom.get()).doubleValue());
               event.setZ(event.getZ() * ((Number)motionSpeedCustom.get()).doubleValue());
            }
         }

         if ((Boolean)safeWalkValue.get() && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            event.setSafeWalk(true);
         }

         if (!towerStatus && prevTowered && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            prevTowered = false;
         }

      }
   }

   private final void towerMove() {
      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         if (towerTicks == 0 || towerTicks == 5) {
            float f = MinecraftInstance.mc.field_71439_g.field_70177_z * ((float)Math.PI / 180F);
            EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
            var2.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F * (this.isLookingDiagonally() ? 0.15F : 0.3F));
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
            var2 = MinecraftInstance.mc.field_71439_g;
            var2.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F * (this.isLookingDiagonally() ? 0.15F : 0.3F));
            towerTicks = 1;
         }
      } else if (MinecraftInstance.mc.field_71439_g.field_70181_x > -0.0784000015258789) {
         int n = (int)Math.round(MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1.0F * (double)100.0F);
         switch (n) {
            case 0:
               MinecraftInstance.mc.field_71439_g.field_70181_x = -0.0784000015258789;
               break;
            case 42:
               MinecraftInstance.mc.field_71439_g.field_70181_x = 0.33;
               break;
            case 75:
               MinecraftInstance.mc.field_71439_g.field_70181_x = (double)1.0F - MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1.0F;
               spoofGround = true;
         }
      }

   }

   private final boolean search(BlockPos blockPos, boolean raycast) {
      EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
      if (var10000 == null) {
         return false;
      } else {
         EntityPlayerSP player = var10000;
         if (!BlockUtils.isReplaceable(blockPos)) {
            return false;
         } else {
            float maxReach = MinecraftInstance.mc.field_71442_b.func_78757_d();
            Vec3 eyes = EntityExtensionKt.getEyesLoc((Entity)player);
            PlaceRotation currPlaceRotation = null;
            PlaceRotation placeRotation = null;
            EnumFacing[] var8 = EnumFacing.values();
            int var9 = 0;
            int var10 = var8.length;

            while(var9 < var10) {
               EnumFacing side = var8[var9];
               ++var9;
               BlockPos neighbor = blockPos.func_177972_a(side);
               if (BlockUtils.canBeClicked(neighbor)) {
                  for(double x = 0.1; x < 0.9; x += 0.1) {
                     for(double y = 0.1; y < 0.9; y += 0.1) {
                        double z = 0.1;

                        while(z < 0.9) {
                           Intrinsics.checkNotNullExpressionValue(neighbor, "neighbor");
                           currPlaceRotation = this.findTargetPlace(blockPos, neighbor, new Vec3(x, y, z), side, eyes, maxReach, raycast);
                           if (currPlaceRotation == null) {
                              z += 0.1;
                           } else {
                              if (placeRotation == null || RotationUtils.getRotationDifference(currPlaceRotation.getRotation(), this.getCurrRotation()) < RotationUtils.getRotationDifference(placeRotation.getRotation(), this.getCurrRotation())) {
                                 placeRotation = currPlaceRotation;
                              }

                              z += 0.1;
                           }
                        }
                     }
                  }
               }
            }

            if (placeRotation == null) {
               return false;
            } else {
               label109: {
                  label108: {
                     Intrinsics.checkNotNullExpressionValue(var20, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     switch (var20) {
                        case "normal":
                        case "snap":
                        case "telly":
                           var21 = PlayerUtils.getOffGroundTicks() >= ((Number)tellyTicks.get()).intValue() && PlayerUtils.getOffGroundTicks() < 11 ? new Rotation(RotationUtils.getRotations((double)blockPos.func_177958_n() + (double)0.5F, (double)blockPos.func_177956_o() + (double)0.5F, (double)blockPos.func_177952_p() + (double)0.5F).getYaw(), placeRotation.getRotation().getPitch()) : new Rotation(MovementUtils.INSTANCE.getMovingYaw(), placeRotation.getRotation().getPitch());
                           break label109;
                        case "vanilla":
                           var21 = RotationUtils.getFaceRotation(placeRotation.getPlaceInfo().getEnumFacing(), placeRotation.getPlaceInfo().getBlockPos());
                           break label109;
                        case "watchdog":
                           var21 = new Rotation(MovementUtils.INSTANCE.getMovingYaw() + (float)(this.isLookingDiagonally() ? 145 : 130), towerStatus ? 90.0F : 88.0F);
                           break label109;
                        case "stabilized":
                     }

                     var21 = null;
                     break label109;
                  }

                  var21 = placeRotation.getRotation();
               }

               lockRotation = var21;
               if (bridgeMode.equals("GodBridge")) {
                  lockRotation = !prevTowered && !takeVelo ? new Rotation(this.isLookingDiagonally() ? MovementUtils.INSTANCE.getMovingYaw() - (float)180 : MovementUtils.INSTANCE.getMovingYaw() + (rightSide ? 135.0F : -135.0F), 75.5F) : RotationUtils.getRotations((double)placeRotation.getPlaceInfo().getBlockPos().func_177958_n() + (double)0.5F, (double)placeRotation.getPlaceInfo().getBlockPos().func_177956_o() + (double)0.5F, (double)placeRotation.getPlaceInfo().getBlockPos().func_177952_p() + (double)0.5F);
               }

               RotationUtils.setTargetRotation(lockRotation, rotationsValue.equals("Snap") ? 0 : 20);
               targetPlace = placeRotation.getPlaceInfo();
               return true;
            }
         }
      }
   }

   private final void calculateSide() {
      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         rightSide = Math.floor(MinecraftInstance.mc.field_71439_g.field_70165_t + (double)((float)Math.cos((double)OtherExtensionKt.toRadians(MovementUtils.INSTANCE.getMovingYaw()))) * (double)0.5F) != Math.floor(MinecraftInstance.mc.field_71439_g.field_70165_t) || Math.floor(MinecraftInstance.mc.field_71439_g.field_70161_v + (double)((float)Math.sin((double)OtherExtensionKt.toRadians(MovementUtils.INSTANCE.getMovingYaw()))) * (double)0.5F) != Math.floor(MinecraftInstance.mc.field_71439_g.field_70161_v);
      }

   }

   private final boolean sprint() {
      ListValue sprint = sprintModeValue;
      if (!MovementUtils.INSTANCE.isMoving()) {
         return false;
      } else if (!sprint.equals("Normal") || towerStatus && towerModeValue.equals("BlocksMC")) {
         if (sprint.equals("WatchDog")) {
            return towerStatus || GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G);
         } else if (sprint.equals("Air") && !this.onGround()) {
            return true;
         } else if (sprint.equals("BlocksMC")) {
            return true;
         } else if (sprint.equals("Ground") && this.onGround()) {
            return true;
         } else if (sprint.equals("Telly") && !prevTowered && bridgeMode.equals("Telly")) {
            return PlayerUtils.getOffGroundTicks() >= ((Number)tellyTicks.get()).intValue() && PlayerUtils.getOffGroundTicks() < 11;
         } else if (sprint.equals("Legit") && Math.abs((double)(MathHelper.func_76142_g(MinecraftInstance.mc.field_71439_g.field_70177_z) - MathHelper.func_76142_g(RotationUtils.serverRotation.getYaw()))) < (double)90.0F) {
            return true;
         } else if (sprint.equals("Custom")) {
            if ((Boolean)strafeCustom.get()) {
               MovementUtils.INSTANCE.strafe((Boolean)strafeSpeedCustom.get() ? ((Number)strafeSpeedCustomValue.get()).floatValue() : MovementUtils.INSTANCE.getSpeed());
            }

            return (Boolean)sprintCustom.get();
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   private final Vec3 modifyVec(Vec3 original, EnumFacing direction, Vec3 pos, boolean shouldModify) {
      if (!shouldModify) {
         return original;
      } else {
         double x = original.field_72450_a;
         double y = original.field_72448_b;
         double z = original.field_72449_c;
         EnumFacing side = direction.func_176734_d();
         EnumFacing.Axis var10000 = side.func_176740_k();
         if (var10000 == null) {
            return original;
         } else {
            Vec3 var12;
            switch (Scaffold.WhenMappings.$EnumSwitchMapping$0[var10000.ordinal()]) {
               case 1:
                  var12 = new Vec3(x, pos.field_72448_b + (double)RangesKt.coerceAtLeast(side.func_176730_m().func_177956_o(), 0), z);
                  break;
               case 2:
                  var12 = new Vec3(pos.field_72450_a + (double)RangesKt.coerceAtLeast(side.func_176730_m().func_177958_n(), 0), y, z);
                  break;
               case 3:
                  var12 = new Vec3(x, y, pos.field_72449_c + (double)RangesKt.coerceAtLeast(side.func_176730_m().func_177952_p(), 0));
                  break;
               default:
                  throw new NoWhenBranchMatchedException();
            }

            return var12;
         }
      }
   }

   private final PlaceRotation findTargetPlace(BlockPos pos, BlockPos offsetPos, Vec3 vec3, EnumFacing side, Vec3 eyes, float maxReach, boolean raycast) {
      WorldClient var10000 = MinecraftInstance.mc.field_71441_e;
      if (var10000 == null) {
         return null;
      } else {
         WorldClient world = var10000;
         Vec3 vec = (new Vec3((Vec3i)pos)).func_178787_e(vec3).func_72441_c((double)side.func_176730_m().func_177958_n() * vec3.field_72450_a, (double)side.func_176730_m().func_177956_o() * vec3.field_72448_b, (double)side.func_176730_m().func_177952_p() * vec3.field_72449_c);
         double distance = eyes.func_72438_d(vec);
         if (!raycast || !(distance > (double)maxReach) && world.func_147447_a(eyes, vec, false, true, false) == null) {
            Vec3 diff = vec.func_178788_d(eyes);
            if (side.func_176740_k() != Axis.Y) {
               double dist = Math.abs(side.func_176740_k() == Axis.Z ? diff.field_72449_c : diff.field_72450_a);
               if (dist < (double)0.0F) {
                  return null;
               }
            }

            Rotation rotation = RotationUtils.toRotation(vec, false);
            rotation = rotationsValue.equals("Stabilized") ? new Rotation((float)Math.rint((double)(rotation.getYaw() / 45.0F)) * 45.0F, rotation.getPitch()) : rotation;
            MovingObjectPosition var25 = this.performBlockRaytrace(this.getCurrRotation(), maxReach);
            if (var25 != null) {
               MovingObjectPosition raytrace = var25;
               int var17 = 0;
               if (raytrace.field_72313_a == MovingObjectType.BLOCK && Intrinsics.areEqual((Object)raytrace.func_178782_a(), (Object)offsetPos) && (!raycast || raytrace.field_178784_b == side.func_176734_d())) {
                  BlockPos var18 = raytrace.func_178782_a();
                  Intrinsics.checkNotNullExpressionValue(var18, "raytrace.blockPos");
                  BlockPos var27 = var18;
                  EnumFacing var23 = side.func_176734_d();
                  Intrinsics.checkNotNullExpressionValue(var23, "side.opposite");
                  EnumFacing var28 = var23;
                  Scaffold var10006 = INSTANCE;
                  Vec3 var24 = raytrace.field_72307_f;
                  Intrinsics.checkNotNullExpressionValue(var24, "raytrace.hitVec");
                  return new PlaceRotation(new PlaceInfo(var27, var28, var10006.modifyVec(var24, side, new Vec3((Vec3i)offsetPos), !raycast)), INSTANCE.getCurrRotation());
               }
            }

            Intrinsics.checkNotNullExpressionValue(rotation, "rotation");
            var25 = this.performBlockRaytrace(rotation, maxReach);
            if (var25 == null) {
               return null;
            } else {
               MovingObjectPosition raytrace = var25;
               if (raytrace.field_72313_a == MovingObjectType.BLOCK && Intrinsics.areEqual((Object)raytrace.func_178782_a(), (Object)offsetPos) && (!raycast || raytrace.field_178784_b == side.func_176734_d())) {
                  BlockPos var15 = raytrace.func_178782_a();
                  Intrinsics.checkNotNullExpressionValue(var15, "raytrace.blockPos");
                  BlockPos var10004 = var15;
                  EnumFacing var21 = side.func_176734_d();
                  Intrinsics.checkNotNullExpressionValue(var21, "side.opposite");
                  EnumFacing var10005 = var21;
                  Vec3 var22 = raytrace.field_72307_f;
                  Intrinsics.checkNotNullExpressionValue(var22, "raytrace.hitVec");
                  PlaceInfo var10002 = new PlaceInfo(var10004, var10005, this.modifyVec(var22, side, new Vec3((Vec3i)offsetPos), !raycast));
                  Intrinsics.checkNotNullExpressionValue(rotation, "rotation");
                  return new PlaceRotation(var10002, rotation);
               } else {
                  return null;
               }
            }
         } else {
            return null;
         }
      }
   }

   private final MovingObjectPosition performBlockRaytrace(Rotation rotation, float maxReach) {
      EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
      if (var10000 == null) {
         return null;
      } else {
         EntityPlayerSP player = var10000;
         WorldClient var8 = MinecraftInstance.mc.field_71441_e;
         if (var8 == null) {
            return null;
         } else {
            WorldClient world = var8;
            Vec3 eyes = EntityExtensionKt.getEyesLoc((Entity)player);
            Vec3 rotationVec = RotationUtils.getVectorForRotation(rotation);
            Vec3 reach = eyes.func_72441_c(rotationVec.field_72450_a * (double)maxReach, rotationVec.field_72448_b * (double)maxReach, rotationVec.field_72449_c * (double)maxReach);
            return world.func_147447_a(eyes, reach, false, false, true);
         }
      }
   }

   private final float getRotationSpeed() {
      return (Boolean)rotationSpeedValue.get() ? (float)(Math.random() * (double)(((IntRange)turnSpeedValue.get()).getLast() - ((IntRange)turnSpeedValue.get()).getFirst()) + (double)((IntRange)turnSpeedValue.get()).getFirst()) : Float.MAX_VALUE;
   }

   private final long getGetDelay() {
      return TimeUtils.INSTANCE.randomDelay(placeDelay.get().getFirst(), placeDelay.get().getLast());
   }

   @NotNull
   public String getTag() {
      return (String)bridgeMode.get();
   }

   private final boolean onGround() {
      return MinecraftInstance.mc.field_71439_g.field_70122_E || PlayerUtils.getOffGroundTicks() == 0;
   }

   static {
      String[] var0 = new String[]{"Normal", "Stabilized", "Vanilla", "WatchDog", "Telly", "Snap", "None"};
      rotationsValue = (new ListValue("Rotations", var0, "Normal")).displayable(null.INSTANCE);
      var0 = new String[]{"None", "NCP", "BlocksMC", "WatchDog", "Vanilla"};
      towerModeValue = new ListValue("TowerMode", var0, "None");
      speedVanilla = (new FloatValue("Speed", 1.0F, 0.1F, 1.0F)).displayable(null.INSTANCE);
      speedDiagonallyVanilla = (new FloatValue("Speed-Diagonally", 1.0F, 0.1F, 1.0F)).displayable(null.INSTANCE);
      var0 = new String[]{"Post", "Pre", "GameTick"};
      placeMethod = new ListValue("PlaceEvent", var0, "GameTick");
      var0 = new String[]{"Spoof", "Switch"};
      autoBlockValue = new ListValue("AutoBlock", var0, "Switch");
      highBlock = new BoolValue("BiggestStack", false);
      highBlockMode = (new BoolValue("BiggestStackSwitchTick", false)).displayable(null.INSTANCE);
      switchTickValue = (new IntegerValue("SwitchPlaceTick", 0, 0, 10)).displayable(null.INSTANCE);
      String[] var1 = new String[]{"Normal", "Air", "Ground", "WatchDog", "BlocksMC", "Telly", "Legit", "Custom", "None"};
      sprintModeValue = new ListValue(var1) {
         protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
            Intrinsics.checkNotNullParameter(oldValue, "oldValue");
            Intrinsics.checkNotNullParameter(newValue, "newValue");
            if (Intrinsics.areEqual((Object)newValue, (Object)"BlocksMC")) {
               Scaffold var10000 = Scaffold.INSTANCE;
               Scaffold.cancelSprint = true;
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.STOP_SPRINTING)));
            }

         }
      };
      lowHopblocksmc = (new BoolValue("BlocksMC-LowHop", false)).displayable(null.INSTANCE);
      sprintCustom = (new BoolValue("CustomSprint", true)).displayable(null.INSTANCE);
      cancelSprintCustom = (BoolValue)(new BoolValue() {
         protected void onChanged(boolean oldValue, boolean newValue) {
            if ((Boolean)Scaffold.sprintCustom.get()) {
               Scaffold var10000 = Scaffold.INSTANCE;
               Scaffold.cancelSprint = true;
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.STOP_SPRINTING)));
            }

         }
      }).displayable(null.INSTANCE);
      motionCustom = (new BoolValue("CustomMotion", false)).displayable(null.INSTANCE);
      motionSpeedCustom = (new FloatValue("CustomMotionSpeed", 1.0F, 0.1F, 2.0F)).displayable(null.INSTANCE);
      motionSpeedEffectCustom = (new BoolValue("CustomMotion-SpeedEffect", false)).displayable(null.INSTANCE);
      motionSpeedSpeedEffectCustom = (new FloatValue("CustomMotionSpeed-SpeedEffect", 1.0F, 0.1F, 2.0F)).displayable(null.INSTANCE);
      strafeCustom = (new BoolValue("CustomStrafe", false)).displayable(null.INSTANCE);
      strafeSpeedCustom = (new BoolValue("CustomStrafeSpeed", false)).displayable(null.INSTANCE);
      strafeSpeedCustomValue = (new FloatValue("CustomStrafeSpeed", 0.1F, 0.1F, 1.0F)).displayable(null.INSTANCE);
      var0 = new String[]{"UpSideDown", "Andromeda", "Normal", "Telly", "GodBridge", "AutoJump", "KeepUP", "SameY"};
      bridgeMode = new ListValue("BridgeMode", var0, "Normal");
      waitRotation = (new BoolValue("WaitRotation", false)).displayable(null.INSTANCE);
      tellyTicks = (new IntegerValue("TellyTicks", 0, 0, 10)).displayable(null.INSTANCE);
      sameYSpeed = (new BoolValue("SameY-OnlySpeed", false)).displayable(null.INSTANCE);
      andJump = (new BoolValue("Andromeda-Jump", false)).displayable(null.INSTANCE);
      strafeFix = new BoolValue("StrafeFix", false);
      swingValue = new BoolValue("Swing", false);
      searchValue = new BoolValue("Search", true);
      downValue = new BoolValue("Downward", false);
      safeWalkValue = new BoolValue("SafeWalk", false);
      zitterModeValue = new BoolValue("Zitter", false);
      rotationSpeedValue = new BoolValue("RotationSpeed", true);
      turnSpeedValue = (new IntegerRangeValue("TurnSpeed", 180, 180, 0, 180, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      placeDelay = new IntegerRangeValue("PlaceDelay", 0, 0, 0, 1000, (Function0)null, 32, (DefaultConstructorMarker)null);
      expandLengthValue = new IntegerValue("ExpandLength", 1, 1, 6);
      omniDirectionalExpand = (new BoolValue("OmniDirectionalExpand", false)).displayable(null.INSTANCE);
      timerValue = new FloatValue("Timer", 1.0F, 0.1F, 5.0F);
      towerTimerValue = new FloatValue("TowerTimer", 1.0F, 0.1F, 5.0F);
      var0 = new String[]{"Packet", "Silent", "Normal", "Off"};
      eagleValue = new ListValue("Eagle", var0, "Off");
      blocksToEagleValue = (new IntegerValue("BlocksToEagle", 0, 0, 10)).displayable(null.INSTANCE);
      edgeDistanceValue = (new FloatValue("EagleEdgeDistance", 0.0F, 0.0F, 0.5F)).displayable(null.INSTANCE);
      var0 = new String[]{"Simple", "Strict", "Legit", "Off"};
      hitableCheckValue = new ListValue("HitableCheck", var0, "Simple");
      zitterTimer = new MSTimer();
      delayTimer = new TimerMS();
      double[] var8 = new double[]{0.2, 0.22, 0.28, 0.29, 0.3};
      floatSpeedLevels = var8;
      var8 = new double[]{0.3, 0.34, 0.38, 0.42, 0.42};
      towerSpeedLevels = var8;
   }

   // $FF: synthetic class
   @Metadata(
      mv = {1, 6, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[Axis.values().length];
         var0[Axis.Y.ordinal()] = 1;
         var0[Axis.X.ordinal()] = 2;
         var0[Axis.Z.ordinal()] = 3;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
