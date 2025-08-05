package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000n\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\u0010\"\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020)*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\r\u0010*\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u0010*\u001a\u00020\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u0010*\u001a\u00020\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u0010*\u001a\u00020\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020\u0016H\u0087\b¢\u0006\u0002\u0010-\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u0010.\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\b*\u00020!H\u0087\b¢\u0006\u0002\u0010/\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u00100\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\t*\u00020#H\u0087\b¢\u0006\u0002\u00101\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u00102\u001a\n\u00103\u001a\u00020)*\u00020)\u001a\n\u00103\u001a\u00020&*\u00020&\u001a\n\u00103\u001a\u00020(*\u00020(\u001a\u0015\u00104\u001a\u00020)*\u00020)2\u0006\u00104\u001a\u00020\bH\u0086\u0004\u001a\u0015\u00104\u001a\u00020&*\u00020&2\u0006\u00104\u001a\u00020\bH\u0086\u0004\u001a\u0015\u00104\u001a\u00020(*\u00020(2\u0006\u00104\u001a\u00020\tH\u0086\u0004\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u00106\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u00107\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u00108\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u00109\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u0010:\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u0010<\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u0010=\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010?\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u0010@\u001a\u0013\u0010?\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010A\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010C\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010D\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010E\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020\u0016*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004¨\u0006H"},
   d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "randomOrNull", "(Lkotlin/ranges/CharRange;)Ljava/lang/Character;", "(Lkotlin/ranges/CharRange;Lkotlin/random/Random;)Ljava/lang/Character;", "(Lkotlin/ranges/IntRange;)Ljava/lang/Integer;", "(Lkotlin/ranges/IntRange;Lkotlin/random/Random;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongRange;)Ljava/lang/Long;", "(Lkotlin/ranges/LongRange;Lkotlin/random/Random;)Ljava/lang/Long;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"},
   xs = "kotlin/ranges/RangesKt"
)
class RangesKt___RangesKt extends RangesKt__RangesKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int random(IntRange $this$random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      return RangesKt.random($this$random, Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final long random(LongRange $this$random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      return RangesKt.random($this$random, Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final char random(CharRange $this$random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      return RangesKt.random($this$random, Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final int random(@NotNull IntRange $this$random, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");

      try {
         return RandomKt.nextInt(random, $this$random);
      } catch (IllegalArgumentException e) {
         throw new NoSuchElementException(e.getMessage());
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final long random(@NotNull LongRange $this$random, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");

      try {
         return RandomKt.nextLong(random, $this$random);
      } catch (IllegalArgumentException e) {
         throw new NoSuchElementException(e.getMessage());
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final char random(@NotNull CharRange $this$random, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");

      try {
         return (char)random.nextInt($this$random.getFirst(), $this$random.getLast() + 1);
      } catch (IllegalArgumentException e) {
         throw new NoSuchElementException(e.getMessage());
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final Integer randomOrNull(IntRange $this$randomOrNull) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      return RangesKt.randomOrNull($this$randomOrNull, Random.Default);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final Long randomOrNull(LongRange $this$randomOrNull) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      return RangesKt.randomOrNull($this$randomOrNull, Random.Default);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final Character randomOrNull(CharRange $this$randomOrNull) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      return RangesKt.randomOrNull($this$randomOrNull, Random.Default);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @Nullable
   public static final Integer randomOrNull(@NotNull IntRange $this$randomOrNull, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");
      return $this$randomOrNull.isEmpty() ? null : RandomKt.nextInt(random, $this$randomOrNull);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @Nullable
   public static final Long randomOrNull(@NotNull LongRange $this$randomOrNull, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");
      return $this$randomOrNull.isEmpty() ? null : RandomKt.nextLong(random, $this$randomOrNull);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @Nullable
   public static final Character randomOrNull(@NotNull CharRange $this$randomOrNull, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");
      return $this$randomOrNull.isEmpty() ? null : (char)random.nextInt($this$randomOrNull.getFirst(), $this$randomOrNull.getLast() + 1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(IntRange $this$contains, Integer element) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return element != null && $this$contains.contains(element);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(LongRange $this$contains, Long element) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return element != null && $this$contains.contains(element);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(CharRange $this$contains, Character element) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return element != null && $this$contains.contains(element);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange $this$contains, byte value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)Integer.valueOf(value));
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange $this$contains, byte value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(long)value);
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange $this$contains, byte value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(short)value);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(ClosedRange $this$contains, byte value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(ClosedRange $this$contains, byte value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(float)value);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(ClosedRange $this$contains, double value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Integer it = RangesKt.toIntExactOrNull(value);
      int var5 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(ClosedRange $this$contains, double value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Long it = RangesKt.toLongExactOrNull(value);
      int var5 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(ClosedRange $this$contains, double value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Byte it = RangesKt.toByteExactOrNull(value);
      int var5 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(ClosedRange $this$contains, double value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Short it = RangesKt.toShortExactOrNull(value);
      int var5 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange $this$contains, double value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(float)value);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(ClosedRange $this$contains, float value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Integer it = RangesKt.toIntExactOrNull(value);
      int var4 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(ClosedRange $this$contains, float value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Long it = RangesKt.toLongExactOrNull(value);
      int var4 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(ClosedRange $this$contains, float value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Byte it = RangesKt.toByteExactOrNull(value);
      int var4 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(ClosedRange $this$contains, float value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Short it = RangesKt.toShortExactOrNull(value);
      int var4 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange $this$contains, float value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(double)value);
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange $this$contains, int value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(long)value);
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange $this$contains, int value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Byte it = RangesKt.toByteExactOrNull(value);
      int var4 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange $this$contains, int value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Short it = RangesKt.toShortExactOrNull(value);
      int var4 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(ClosedRange $this$contains, int value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(ClosedRange $this$contains, int value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(float)value);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange $this$contains, long value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Integer it = RangesKt.toIntExactOrNull(value);
      int var5 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange $this$contains, long value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Byte it = RangesKt.toByteExactOrNull(value);
      int var5 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange $this$contains, long value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Short it = RangesKt.toShortExactOrNull(value);
      int var5 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(ClosedRange $this$contains, long value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(ClosedRange $this$contains, long value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(float)value);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange $this$contains, short value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)Integer.valueOf(value));
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange $this$contains, short value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(long)value);
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange $this$contains, short value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Byte it = RangesKt.toByteExactOrNull(value);
      int var4 = 0;
      return it != null ? $this$contains.contains((Comparable)it) : false;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(ClosedRange $this$contains, short value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.3",
      errorSince = "1.4",
      hiddenSince = "1.5"
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(ClosedRange $this$contains, short value) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.contains((Comparable)(float)value);
   }

   @NotNull
   public static final IntProgression downTo(int $this$downTo, byte to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, byte to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, (long)to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte $this$downTo, byte to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(short $this$downTo, byte to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final CharProgression downTo(char $this$downTo, char to) {
      return CharProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(int $this$downTo, int to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, int to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, (long)to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte $this$downTo, int to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(short $this$downTo, int to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(int $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange((long)$this$downTo, to, -1L);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, to, -1L);
   }

   @NotNull
   public static final LongProgression downTo(byte $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange((long)$this$downTo, to, -1L);
   }

   @NotNull
   public static final LongProgression downTo(short $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange((long)$this$downTo, to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(int $this$downTo, short to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, short to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, (long)to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte $this$downTo, short to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(short $this$downTo, short to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression reversed(@NotNull IntProgression $this$reversed) {
      Intrinsics.checkNotNullParameter($this$reversed, "<this>");
      return IntProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
   }

   @NotNull
   public static final LongProgression reversed(@NotNull LongProgression $this$reversed) {
      Intrinsics.checkNotNullParameter($this$reversed, "<this>");
      return LongProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
   }

   @NotNull
   public static final CharProgression reversed(@NotNull CharProgression $this$reversed) {
      Intrinsics.checkNotNullParameter($this$reversed, "<this>");
      return CharProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
   }

   @NotNull
   public static final IntProgression step(@NotNull IntProgression $this$step, int step) {
      Intrinsics.checkNotNullParameter($this$step, "<this>");
      RangesKt.checkStepIsPositive(step > 0, (Number)step);
      return IntProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0 ? step : -step);
   }

   @NotNull
   public static final LongProgression step(@NotNull LongProgression $this$step, long step) {
      Intrinsics.checkNotNullParameter($this$step, "<this>");
      RangesKt.checkStepIsPositive(step > 0L, (Number)step);
      return LongProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0L ? step : -step);
   }

   @NotNull
   public static final CharProgression step(@NotNull CharProgression $this$step, int step) {
      Intrinsics.checkNotNullParameter($this$step, "<this>");
      RangesKt.checkStepIsPositive(step > 0, (Number)step);
      return CharProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0 ? step : -step);
   }

   @Nullable
   public static final Byte toByteExactOrNull(int $this$toByteExactOrNull) {
      return ($this$toByteExactOrNull <= 127 ? -128 <= $this$toByteExactOrNull : false) ? (byte)$this$toByteExactOrNull : null;
   }

   @Nullable
   public static final Byte toByteExactOrNull(long $this$toByteExactOrNull) {
      return ($this$toByteExactOrNull <= 127L ? -128L <= $this$toByteExactOrNull : false) ? (byte)((int)$this$toByteExactOrNull) : null;
   }

   @Nullable
   public static final Byte toByteExactOrNull(short $this$toByteExactOrNull) {
      return (-128 <= $this$toByteExactOrNull ? $this$toByteExactOrNull <= 127 : false) ? (byte)$this$toByteExactOrNull : null;
   }

   @Nullable
   public static final Byte toByteExactOrNull(double $this$toByteExactOrNull) {
      return ($this$toByteExactOrNull <= (double)127.0F ? (double)-128.0F <= $this$toByteExactOrNull : false) ? (byte)((int)$this$toByteExactOrNull) : null;
   }

   @Nullable
   public static final Byte toByteExactOrNull(float $this$toByteExactOrNull) {
      return ($this$toByteExactOrNull <= 127.0F ? -128.0F <= $this$toByteExactOrNull : false) ? (byte)((int)$this$toByteExactOrNull) : null;
   }

   @Nullable
   public static final Integer toIntExactOrNull(long $this$toIntExactOrNull) {
      return ($this$toIntExactOrNull <= 2147483647L ? -2147483648L <= $this$toIntExactOrNull : false) ? (int)$this$toIntExactOrNull : null;
   }

   @Nullable
   public static final Integer toIntExactOrNull(double $this$toIntExactOrNull) {
      return ($this$toIntExactOrNull <= (double)Integer.MAX_VALUE ? (double)Integer.MIN_VALUE <= $this$toIntExactOrNull : false) ? (int)$this$toIntExactOrNull : null;
   }

   @Nullable
   public static final Integer toIntExactOrNull(float $this$toIntExactOrNull) {
      return ($this$toIntExactOrNull <= (float)Integer.MAX_VALUE ? (float)Integer.MIN_VALUE <= $this$toIntExactOrNull : false) ? (int)$this$toIntExactOrNull : null;
   }

   @Nullable
   public static final Long toLongExactOrNull(double $this$toLongExactOrNull) {
      return ($this$toLongExactOrNull <= (double)Long.MAX_VALUE ? (double)Long.MIN_VALUE <= $this$toLongExactOrNull : false) ? (long)$this$toLongExactOrNull : null;
   }

   @Nullable
   public static final Long toLongExactOrNull(float $this$toLongExactOrNull) {
      return ($this$toLongExactOrNull <= (float)Long.MAX_VALUE ? (float)Long.MIN_VALUE <= $this$toLongExactOrNull : false) ? (long)$this$toLongExactOrNull : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(int $this$toShortExactOrNull) {
      return ($this$toShortExactOrNull <= 32767 ? -32768 <= $this$toShortExactOrNull : false) ? (short)$this$toShortExactOrNull : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(long $this$toShortExactOrNull) {
      return ($this$toShortExactOrNull <= 32767L ? -32768L <= $this$toShortExactOrNull : false) ? (short)((int)$this$toShortExactOrNull) : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(double $this$toShortExactOrNull) {
      return ($this$toShortExactOrNull <= (double)32767.0F ? (double)-32768.0F <= $this$toShortExactOrNull : false) ? (short)((int)$this$toShortExactOrNull) : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(float $this$toShortExactOrNull) {
      return ($this$toShortExactOrNull <= 32767.0F ? -32768.0F <= $this$toShortExactOrNull : false) ? (short)((int)$this$toShortExactOrNull) : null;
   }

   @NotNull
   public static final IntRange until(int $this$until, byte to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(long $this$until, byte to) {
      return new LongRange($this$until, (long)to - 1L);
   }

   @NotNull
   public static final IntRange until(byte $this$until, byte to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final IntRange until(short $this$until, byte to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final CharRange until(char $this$until, char to) {
      return Intrinsics.compare(to, 0) <= 0 ? CharRange.Companion.getEMPTY() : new CharRange($this$until, (char)(to - 1));
   }

   @NotNull
   public static final IntRange until(int $this$until, int to) {
      return to <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(long $this$until, int to) {
      return new LongRange($this$until, (long)to - 1L);
   }

   @NotNull
   public static final IntRange until(byte $this$until, int to) {
      return to <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final IntRange until(short $this$until, int to) {
      return to <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(int $this$until, long to) {
      return to <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange((long)$this$until, to - 1L);
   }

   @NotNull
   public static final LongRange until(long $this$until, long to) {
      return to <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange($this$until, to - 1L);
   }

   @NotNull
   public static final LongRange until(byte $this$until, long to) {
      return to <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange((long)$this$until, to - 1L);
   }

   @NotNull
   public static final LongRange until(short $this$until, long to) {
      return to <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange((long)$this$until, to - 1L);
   }

   @NotNull
   public static final IntRange until(int $this$until, short to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(long $this$until, short to) {
      return new LongRange($this$until, (long)to - 1L);
   }

   @NotNull
   public static final IntRange until(byte $this$until, short to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final IntRange until(short $this$until, short to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final Comparable coerceAtLeast(@NotNull Comparable $this$coerceAtLeast, @NotNull Comparable minimumValue) {
      Intrinsics.checkNotNullParameter($this$coerceAtLeast, "<this>");
      Intrinsics.checkNotNullParameter(minimumValue, "minimumValue");
      return $this$coerceAtLeast.compareTo(minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
   }

   public static final byte coerceAtLeast(byte $this$coerceAtLeast, byte minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final short coerceAtLeast(short $this$coerceAtLeast, short minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final int coerceAtLeast(int $this$coerceAtLeast, int minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final long coerceAtLeast(long $this$coerceAtLeast, long minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final float coerceAtLeast(float $this$coerceAtLeast, float minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final double coerceAtLeast(double $this$coerceAtLeast, double minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   @NotNull
   public static final Comparable coerceAtMost(@NotNull Comparable $this$coerceAtMost, @NotNull Comparable maximumValue) {
      Intrinsics.checkNotNullParameter($this$coerceAtMost, "<this>");
      Intrinsics.checkNotNullParameter(maximumValue, "maximumValue");
      return $this$coerceAtMost.compareTo(maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
   }

   public static final byte coerceAtMost(byte $this$coerceAtMost, byte maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final short coerceAtMost(short $this$coerceAtMost, short maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final int coerceAtMost(int $this$coerceAtMost, int maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final long coerceAtMost(long $this$coerceAtMost, long maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final float coerceAtMost(float $this$coerceAtMost, float maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final double coerceAtMost(double $this$coerceAtMost, double maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   @NotNull
   public static final Comparable coerceIn(@NotNull Comparable $this$coerceIn, @Nullable Comparable minimumValue, @Nullable Comparable maximumValue) {
      Intrinsics.checkNotNullParameter($this$coerceIn, "<this>");
      if (minimumValue != null && maximumValue != null) {
         if (minimumValue.compareTo(maximumValue) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
         }

         if ($this$coerceIn.compareTo(minimumValue) < 0) {
            return minimumValue;
         }

         if ($this$coerceIn.compareTo(maximumValue) > 0) {
            return maximumValue;
         }
      } else {
         if (minimumValue != null && $this$coerceIn.compareTo(minimumValue) < 0) {
            return minimumValue;
         }

         if (maximumValue != null && $this$coerceIn.compareTo(maximumValue) > 0) {
            return maximumValue;
         }
      }

      return $this$coerceIn;
   }

   public static final byte coerceIn(byte $this$coerceIn, byte minimumValue, byte maximumValue) {
      if (minimumValue > maximumValue) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final short coerceIn(short $this$coerceIn, short minimumValue, short maximumValue) {
      if (minimumValue > maximumValue) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final int coerceIn(int $this$coerceIn, int minimumValue, int maximumValue) {
      if (minimumValue > maximumValue) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final long coerceIn(long $this$coerceIn, long minimumValue, long maximumValue) {
      if (minimumValue > maximumValue) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final float coerceIn(float $this$coerceIn, float minimumValue, float maximumValue) {
      if (minimumValue > maximumValue) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final double coerceIn(double $this$coerceIn, double minimumValue, double maximumValue) {
      if (minimumValue > maximumValue) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable coerceIn(@NotNull Comparable $this$coerceIn, @NotNull ClosedFloatingPointRange range) {
      Intrinsics.checkNotNullParameter($this$coerceIn, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      if (range.isEmpty()) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
      } else {
         return range.lessThanOrEquals($this$coerceIn, range.getStart()) && !range.lessThanOrEquals(range.getStart(), $this$coerceIn) ? range.getStart() : (range.lessThanOrEquals(range.getEndInclusive(), $this$coerceIn) && !range.lessThanOrEquals($this$coerceIn, range.getEndInclusive()) ? range.getEndInclusive() : $this$coerceIn);
      }
   }

   @NotNull
   public static final Comparable coerceIn(@NotNull Comparable $this$coerceIn, @NotNull ClosedRange range) {
      Intrinsics.checkNotNullParameter($this$coerceIn, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return RangesKt.coerceIn($this$coerceIn, (ClosedFloatingPointRange)range);
      } else if (range.isEmpty()) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
      } else {
         return $this$coerceIn.compareTo(range.getStart()) < 0 ? range.getStart() : ($this$coerceIn.compareTo(range.getEndInclusive()) > 0 ? range.getEndInclusive() : $this$coerceIn);
      }
   }

   public static final int coerceIn(int $this$coerceIn, @NotNull ClosedRange range) {
      Intrinsics.checkNotNullParameter(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)$this$coerceIn, (ClosedFloatingPointRange)range)).intValue();
      } else if (range.isEmpty()) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
      } else {
         return $this$coerceIn < ((Number)range.getStart()).intValue() ? ((Number)range.getStart()).intValue() : ($this$coerceIn > ((Number)range.getEndInclusive()).intValue() ? ((Number)range.getEndInclusive()).intValue() : $this$coerceIn);
      }
   }

   public static final long coerceIn(long $this$coerceIn, @NotNull ClosedRange range) {
      Intrinsics.checkNotNullParameter(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)$this$coerceIn, (ClosedFloatingPointRange)range)).longValue();
      } else if (range.isEmpty()) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
      } else {
         return $this$coerceIn < ((Number)range.getStart()).longValue() ? ((Number)range.getStart()).longValue() : ($this$coerceIn > ((Number)range.getEndInclusive()).longValue() ? ((Number)range.getEndInclusive()).longValue() : $this$coerceIn);
      }
   }

   public RangesKt___RangesKt() {
   }
}
