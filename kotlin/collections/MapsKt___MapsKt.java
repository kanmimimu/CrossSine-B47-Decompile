package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.internal.HidesMembers;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u0082\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000f\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001aJ\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\bø\u0001\u0000\u001a$\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aJ\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\bø\u0001\u0000\u001a9\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\n\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001a6\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001a'\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001aJ\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\bø\u0001\u0000\u001a[\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a]\u0010\u0014\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a\\\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\n0\u0006H\u0086\bø\u0001\u0000\u001aa\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\f0\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\b\u0017\u001au\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\n0\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001aw\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\f0\u0006H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001c\u001aJ\u0010\u001e\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010 \u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u001f0\u0006H\u0087\bø\u0001\u0000\u001aV\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0086\bø\u0001\u0000\u001a\\\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\u0086\bø\u0001\u0000\u001au\u0010#\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ao\u0010$\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ah\u0010%\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000\u001ah\u0010(\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000\u001a_\u0010)\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010*\u001aJ\u0010)\u001a\u00020+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020+0\u0006H\u0087\bø\u0001\u0000\u001aJ\u0010)\u001a\u00020,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\u0087\bø\u0001\u0000\u001aa\u0010-\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010*\u001aQ\u0010-\u001a\u0004\u0018\u00010+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020+0\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010.\u001aQ\u0010-\u001a\u0004\u0018\u00010,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010/\u001aq\u00100\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00101\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001002j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`32\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u00104\u001as\u00105\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00101\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001002j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`32\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u00104\u001ai\u00106\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00101\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000702j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`3H\u0087\b\u001ai\u00107\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00101\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000702j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`3H\u0087\b\u001ah\u00108\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000\u001ah\u00109\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000\u001a_\u0010:\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010*\u001aJ\u0010:\u001a\u00020+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020+0\u0006H\u0087\bø\u0001\u0000\u001aJ\u0010:\u001a\u00020,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\u0087\bø\u0001\u0000\u001aa\u0010;\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010*\u001aQ\u0010;\u001a\u0004\u0018\u00010+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020+0\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010.\u001aQ\u0010;\u001a\u0004\u0018\u00010,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010/\u001aq\u0010<\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00101\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001002j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`32\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u00104\u001as\u0010=\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00101\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001002j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`32\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u00104\u001ah\u0010>\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00101\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000702j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`3H\u0007\u001ai\u0010?\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00101\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000702j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`3H\u0087\b\u001a$\u0010@\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aJ\u0010@\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\bø\u0001\u0000\u001aY\u0010A\u001a\u0002HB\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010B*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002HB2\u001e\u0010 \u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u001f0\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010C\u001an\u0010D\u001a\u0002HB\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010B*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002HB23\u0010 \u001a/\u0012\u0013\u0012\u00110\u000e¢\u0006\f\bF\u0012\b\bG\u0012\u0004\b\b(H\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u001f0EH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010I\u001a6\u0010J\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030K0\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006L"},
   d2 = {"all", "", "K", "V", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "count", "", "firstNotNullOf", "R", "", "transform", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "firstNotNullOfOrNull", "flatMap", "", "flatMapSequence", "flatMapTo", "C", "", "destination", "(Ljava/util/Map;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "flatMapSequenceTo", "forEach", "", "action", "map", "mapNotNull", "mapNotNullTo", "mapTo", "maxBy", "", "selector", "maxByOrNull", "maxOf", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Comparable;", "", "", "maxOfOrNull", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Double;", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Float;", "maxOfWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/Map;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "maxOfWithOrNull", "maxWith", "maxWithOrNull", "minBy", "minByOrNull", "minOf", "minOfOrNull", "minOfWith", "minOfWithOrNull", "minWith", "minWithOrNull", "none", "onEach", "M", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "onEachIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "index", "(Ljava/util/Map;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "toList", "Lkotlin/Pair;", "kotlin-stdlib"},
   xs = "kotlin/collections/MapsKt"
)
class MapsKt___MapsKt extends MapsKt__MapsKt {
   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final Object firstNotNullOf(Map $this$firstNotNullOf, Function1 transform) {
      Intrinsics.checkNotNullParameter($this$firstNotNullOf, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      Iterator var4 = $this$firstNotNullOf.entrySet().iterator();

      Object var10000;
      while(true) {
         if (var4.hasNext()) {
            Map.Entry var5 = (Map.Entry)var4.next();
            Object var6 = transform.invoke(var5);
            if (var6 == null) {
               continue;
            }

            var10000 = var6;
            break;
         }

         var10000 = null;
         break;
      }

      Object var2 = var10000;
      if (var2 == null) {
         throw new NoSuchElementException("No element of the map was transformed to a non-null value.");
      } else {
         return var2;
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @InlineOnly
   private static final Object firstNotNullOfOrNull(Map $this$firstNotNullOfOrNull, Function1 transform) {
      Intrinsics.checkNotNullParameter($this$firstNotNullOfOrNull, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");

      for(Map.Entry element : $this$firstNotNullOfOrNull.entrySet()) {
         Object result = transform.invoke(element);
         if (result != null) {
            return result;
         }
      }

      return null;
   }

   @NotNull
   public static final List toList(@NotNull Map $this$toList) {
      Intrinsics.checkNotNullParameter($this$toList, "<this>");
      if ($this$toList.size() == 0) {
         return CollectionsKt.emptyList();
      } else {
         Iterator iterator = $this$toList.entrySet().iterator();
         if (!iterator.hasNext()) {
            return CollectionsKt.emptyList();
         } else {
            Map.Entry first = (Map.Entry)iterator.next();
            if (!iterator.hasNext()) {
               return CollectionsKt.listOf(new Pair(first.getKey(), first.getValue()));
            } else {
               ArrayList result = new ArrayList($this$toList.size());
               result.add(new Pair(first.getKey(), first.getValue()));

               do {
                  Map.Entry var4 = (Map.Entry)iterator.next();
                  result.add(new Pair(var4.getKey(), var4.getValue()));
               } while(iterator.hasNext());

               return (List)result;
            }
         }
      }
   }

   @NotNull
   public static final List flatMap(@NotNull Map $this$flatMap, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$flatMap, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$flatMap = 0;
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$flatMapTo = 0;

      for(Map.Entry element$iv : $this$flatMap.entrySet()) {
         Iterable list$iv = (Iterable)transform.invoke(element$iv);
         CollectionsKt.addAll(destination$iv, list$iv);
      }

      return (List)destination$iv;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @JvmName(
      name = "flatMapSequence"
   )
   @NotNull
   public static final List flatMapSequence(@NotNull Map $this$flatMap, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$flatMap, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$flatMapSequence = 0;
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$flatMapSequenceTo = 0;

      for(Map.Entry element$iv : $this$flatMap.entrySet()) {
         Sequence list$iv = (Sequence)transform.invoke(element$iv);
         CollectionsKt.addAll(destination$iv, list$iv);
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final Collection flatMapTo(@NotNull Map $this$flatMapTo, @NotNull Collection destination, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$flatMapTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$flatMapTo = 0;

      for(Map.Entry element : $this$flatMapTo.entrySet()) {
         Iterable list = (Iterable)transform.invoke(element);
         CollectionsKt.addAll(destination, list);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @JvmName(
      name = "flatMapSequenceTo"
   )
   @NotNull
   public static final Collection flatMapSequenceTo(@NotNull Map $this$flatMapTo, @NotNull Collection destination, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$flatMapTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$flatMapSequenceTo = 0;

      for(Map.Entry element : $this$flatMapTo.entrySet()) {
         Sequence list = (Sequence)transform.invoke(element);
         CollectionsKt.addAll(destination, list);
      }

      return destination;
   }

   @NotNull
   public static final List map(@NotNull Map $this$map, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$map, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$map = 0;
      Collection destination$iv = (Collection)(new ArrayList($this$map.size()));
      int $i$f$mapTo = 0;

      for(Map.Entry item$iv : $this$map.entrySet()) {
         destination$iv.add(transform.invoke(item$iv));
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final List mapNotNull(@NotNull Map $this$mapNotNull, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$mapNotNull, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$mapNotNull = 0;
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$mapNotNullTo = 0;
      int $i$f$forEach = 0;

      for(Map.Entry element$iv$iv : $this$mapNotNull.entrySet()) {
         int var11 = 0;
         Object it$iv = transform.invoke(element$iv$iv);
         if (it$iv != null) {
            int var15 = 0;
            destination$iv.add(it$iv);
         }
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final Collection mapNotNullTo(@NotNull Map $this$mapNotNullTo, @NotNull Collection destination, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$mapNotNullTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$mapNotNullTo = 0;
      int $i$f$forEach = 0;

      for(Map.Entry element$iv : $this$mapNotNullTo.entrySet()) {
         int var9 = 0;
         Object it = transform.invoke(element$iv);
         if (it != null) {
            int var13 = 0;
            destination.add(it);
         }
      }

      return destination;
   }

   @NotNull
   public static final Collection mapTo(@NotNull Map $this$mapTo, @NotNull Collection destination, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$mapTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$mapTo = 0;

      for(Map.Entry item : $this$mapTo.entrySet()) {
         destination.add(transform.invoke(item));
      }

      return destination;
   }

   public static final boolean all(@NotNull Map $this$all, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$all, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$all = 0;
      if ($this$all.isEmpty()) {
         return true;
      } else {
         for(Map.Entry element : $this$all.entrySet()) {
            if (!(Boolean)predicate.invoke(element)) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean any(@NotNull Map $this$any) {
      Intrinsics.checkNotNullParameter($this$any, "<this>");
      return !$this$any.isEmpty();
   }

   public static final boolean any(@NotNull Map $this$any, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$any, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$any = 0;
      if ($this$any.isEmpty()) {
         return false;
      } else {
         for(Map.Entry element : $this$any.entrySet()) {
            if ((Boolean)predicate.invoke(element)) {
               return true;
            }
         }

         return false;
      }
   }

   @InlineOnly
   private static final int count(Map $this$count) {
      Intrinsics.checkNotNullParameter($this$count, "<this>");
      return $this$count.size();
   }

   public static final int count(@NotNull Map $this$count, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$count, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$count = 0;
      if ($this$count.isEmpty()) {
         return 0;
      } else {
         int count = 0;

         for(Map.Entry element : $this$count.entrySet()) {
            if ((Boolean)predicate.invoke(element)) {
               ++count;
            }
         }

         return count;
      }
   }

   @HidesMembers
   public static final void forEach(@NotNull Map $this$forEach, @NotNull Function1 action) {
      Intrinsics.checkNotNullParameter($this$forEach, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      int $i$f$forEach = 0;

      for(Map.Entry element : $this$forEach.entrySet()) {
         action.invoke(element);
      }

   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use maxByOrNull instead.",
      replaceWith = @ReplaceWith(
   expression = "this.maxByOrNull(selector)",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.4",
      errorSince = "1.5",
      hiddenSince = "1.6"
   )
   @InlineOnly
   private static final Map.Entry maxBy(Map $this$maxBy, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxBy, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var3 = (Iterable)$this$maxBy.entrySet();
      Iterator var4 = var3.iterator();
      Object var10000;
      if (!var4.hasNext()) {
         var10000 = null;
      } else {
         Object var5 = var4.next();
         if (!var4.hasNext()) {
            var10000 = var5;
         } else {
            Comparable var6 = (Comparable)selector.invoke(var5);

            do {
               Object var7 = var4.next();
               Comparable var8 = (Comparable)selector.invoke(var7);
               if (var6.compareTo(var8) < 0) {
                  var5 = var7;
                  var6 = var8;
               }
            } while(var4.hasNext());

            var10000 = var5;
         }
      }

      return (Map.Entry)var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @InlineOnly
   private static final Map.Entry maxByOrNull(Map $this$maxByOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxByOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable $this$maxByOrNull$iv = (Iterable)$this$maxByOrNull.entrySet();
      int $i$f$maxByOrNull = 0;
      Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
      Object var10000;
      if (!iterator$iv.hasNext()) {
         var10000 = null;
      } else {
         Object maxElem$iv = iterator$iv.next();
         if (!iterator$iv.hasNext()) {
            var10000 = maxElem$iv;
         } else {
            Comparable maxValue$iv = (Comparable)selector.invoke(maxElem$iv);

            do {
               Object e$iv = iterator$iv.next();
               Comparable v$iv = (Comparable)selector.invoke(e$iv);
               if (maxValue$iv.compareTo(v$iv) < 0) {
                  maxElem$iv = e$iv;
                  maxValue$iv = v$iv;
               }
            } while(iterator$iv.hasNext());

            var10000 = maxElem$iv;
         }
      }

      return (Map.Entry)var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final double maxOf(Map $this$maxOf, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOf, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$maxOf.entrySet();
      Iterator var3 = var2.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         double var4;
         double var6;
         for(var4 = ((Number)selector.invoke(var3.next())).doubleValue(); var3.hasNext(); var4 = Math.max(var4, var6)) {
            var6 = ((Number)selector.invoke(var3.next())).doubleValue();
         }

         return var4;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final float maxOf(Map $this$maxOf, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOf, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$maxOf.entrySet();
      Iterator var3 = var2.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         float var4;
         float var5;
         for(var4 = ((Number)selector.invoke(var3.next())).floatValue(); var3.hasNext(); var4 = Math.max(var4, var5)) {
            var5 = ((Number)selector.invoke(var3.next())).floatValue();
         }

         return var4;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Comparable maxOf(Map $this$maxOf, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOf, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$maxOf.entrySet();
      Iterator var3 = var2.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         Comparable var4 = (Comparable)selector.invoke(var3.next());

         while(var3.hasNext()) {
            Comparable var5 = (Comparable)selector.invoke(var3.next());
            if (var4.compareTo(var5) < 0) {
               var4 = var5;
            }
         }

         return var4;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Double maxOfOrNull(Map $this$maxOfOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOfOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$maxOfOrNull.entrySet();
      Iterator var3 = var2.iterator();
      Double var10000;
      if (!var3.hasNext()) {
         var10000 = null;
      } else {
         double var4;
         double var6;
         for(var4 = ((Number)selector.invoke(var3.next())).doubleValue(); var3.hasNext(); var4 = Math.max(var4, var6)) {
            var6 = ((Number)selector.invoke(var3.next())).doubleValue();
         }

         var10000 = var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Float maxOfOrNull(Map $this$maxOfOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOfOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$maxOfOrNull.entrySet();
      Iterator var3 = var2.iterator();
      Float var10000;
      if (!var3.hasNext()) {
         var10000 = null;
      } else {
         float var4;
         float var5;
         for(var4 = ((Number)selector.invoke(var3.next())).floatValue(); var3.hasNext(); var4 = Math.max(var4, var5)) {
            var5 = ((Number)selector.invoke(var3.next())).floatValue();
         }

         var10000 = var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Comparable maxOfOrNull(Map $this$maxOfOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOfOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$maxOfOrNull.entrySet();
      Iterator var3 = var2.iterator();
      Comparable var10000;
      if (!var3.hasNext()) {
         var10000 = null;
      } else {
         Comparable var4 = (Comparable)selector.invoke(var3.next());

         while(var3.hasNext()) {
            Comparable var5 = (Comparable)selector.invoke(var3.next());
            if (var4.compareTo(var5) < 0) {
               var4 = var5;
            }
         }

         var10000 = var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Object maxOfWith(Map $this$maxOfWith, Comparator comparator, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOfWith, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var3 = (Iterable)$this$maxOfWith.entrySet();
      Iterator var4 = var3.iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         Object var5 = selector.invoke(var4.next());

         while(var4.hasNext()) {
            Object var6 = selector.invoke(var4.next());
            if (comparator.compare(var5, var6) < 0) {
               var5 = var6;
            }
         }

         return var5;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Object maxOfWithOrNull(Map $this$maxOfWithOrNull, Comparator comparator, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$maxOfWithOrNull, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var3 = (Iterable)$this$maxOfWithOrNull.entrySet();
      Iterator var4 = var3.iterator();
      Object var10000;
      if (!var4.hasNext()) {
         var10000 = null;
      } else {
         Object var5 = selector.invoke(var4.next());

         while(var4.hasNext()) {
            Object var6 = selector.invoke(var4.next());
            if (comparator.compare(var5, var6) < 0) {
               var5 = var6;
            }
         }

         var10000 = var5;
      }

      return var10000;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use maxWithOrNull instead.",
      replaceWith = @ReplaceWith(
   expression = "this.maxWithOrNull(comparator)",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.4",
      errorSince = "1.5",
      hiddenSince = "1.6"
   )
   @InlineOnly
   private static final Map.Entry maxWith(Map $this$maxWith, Comparator comparator) {
      Intrinsics.checkNotNullParameter($this$maxWith, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return (Map.Entry)CollectionsKt.maxWithOrNull((Iterable)$this$maxWith.entrySet(), comparator);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @InlineOnly
   private static final Map.Entry maxWithOrNull(Map $this$maxWithOrNull, Comparator comparator) {
      Intrinsics.checkNotNullParameter($this$maxWithOrNull, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return (Map.Entry)CollectionsKt.maxWithOrNull((Iterable)$this$maxWithOrNull.entrySet(), comparator);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use minByOrNull instead.",
      replaceWith = @ReplaceWith(
   expression = "this.minByOrNull(selector)",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.4",
      errorSince = "1.5",
      hiddenSince = "1.6"
   )
   public static final Map.Entry minBy(Map $this$minBy, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minBy, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      int $i$f$minBy = 0;
      Iterable var4 = (Iterable)$this$minBy.entrySet();
      Iterator var5 = var4.iterator();
      Object var10000;
      if (!var5.hasNext()) {
         var10000 = null;
      } else {
         Object var6 = var5.next();
         if (!var5.hasNext()) {
            var10000 = var6;
         } else {
            Comparable var7 = (Comparable)selector.invoke(var6);

            do {
               Object var8 = var5.next();
               Comparable var9 = (Comparable)selector.invoke(var8);
               if (var7.compareTo(var9) > 0) {
                  var6 = var8;
                  var7 = var9;
               }
            } while(var5.hasNext());

            var10000 = var6;
         }
      }

      return (Map.Entry)var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @InlineOnly
   private static final Map.Entry minByOrNull(Map $this$minByOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minByOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable $this$minByOrNull$iv = (Iterable)$this$minByOrNull.entrySet();
      int $i$f$minByOrNull = 0;
      Iterator iterator$iv = $this$minByOrNull$iv.iterator();
      Object var10000;
      if (!iterator$iv.hasNext()) {
         var10000 = null;
      } else {
         Object minElem$iv = iterator$iv.next();
         if (!iterator$iv.hasNext()) {
            var10000 = minElem$iv;
         } else {
            Comparable minValue$iv = (Comparable)selector.invoke(minElem$iv);

            do {
               Object e$iv = iterator$iv.next();
               Comparable v$iv = (Comparable)selector.invoke(e$iv);
               if (minValue$iv.compareTo(v$iv) > 0) {
                  minElem$iv = e$iv;
                  minValue$iv = v$iv;
               }
            } while(iterator$iv.hasNext());

            var10000 = minElem$iv;
         }
      }

      return (Map.Entry)var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final double minOf(Map $this$minOf, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOf, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$minOf.entrySet();
      Iterator var3 = var2.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         double var4;
         double var6;
         for(var4 = ((Number)selector.invoke(var3.next())).doubleValue(); var3.hasNext(); var4 = Math.min(var4, var6)) {
            var6 = ((Number)selector.invoke(var3.next())).doubleValue();
         }

         return var4;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final float minOf(Map $this$minOf, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOf, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$minOf.entrySet();
      Iterator var3 = var2.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         float var4;
         float var5;
         for(var4 = ((Number)selector.invoke(var3.next())).floatValue(); var3.hasNext(); var4 = Math.min(var4, var5)) {
            var5 = ((Number)selector.invoke(var3.next())).floatValue();
         }

         return var4;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Comparable minOf(Map $this$minOf, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOf, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$minOf.entrySet();
      Iterator var3 = var2.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         Comparable var4 = (Comparable)selector.invoke(var3.next());

         while(var3.hasNext()) {
            Comparable var5 = (Comparable)selector.invoke(var3.next());
            if (var4.compareTo(var5) > 0) {
               var4 = var5;
            }
         }

         return var4;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Double minOfOrNull(Map $this$minOfOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOfOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$minOfOrNull.entrySet();
      Iterator var3 = var2.iterator();
      Double var10000;
      if (!var3.hasNext()) {
         var10000 = null;
      } else {
         double var4;
         double var6;
         for(var4 = ((Number)selector.invoke(var3.next())).doubleValue(); var3.hasNext(); var4 = Math.min(var4, var6)) {
            var6 = ((Number)selector.invoke(var3.next())).doubleValue();
         }

         var10000 = var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Float minOfOrNull(Map $this$minOfOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOfOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$minOfOrNull.entrySet();
      Iterator var3 = var2.iterator();
      Float var10000;
      if (!var3.hasNext()) {
         var10000 = null;
      } else {
         float var4;
         float var5;
         for(var4 = ((Number)selector.invoke(var3.next())).floatValue(); var3.hasNext(); var4 = Math.min(var4, var5)) {
            var5 = ((Number)selector.invoke(var3.next())).floatValue();
         }

         var10000 = var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Comparable minOfOrNull(Map $this$minOfOrNull, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOfOrNull, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var2 = (Iterable)$this$minOfOrNull.entrySet();
      Iterator var3 = var2.iterator();
      Comparable var10000;
      if (!var3.hasNext()) {
         var10000 = null;
      } else {
         Comparable var4 = (Comparable)selector.invoke(var3.next());

         while(var3.hasNext()) {
            Comparable var5 = (Comparable)selector.invoke(var3.next());
            if (var4.compareTo(var5) > 0) {
               var4 = var5;
            }
         }

         var10000 = var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Object minOfWith(Map $this$minOfWith, Comparator comparator, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOfWith, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var3 = (Iterable)$this$minOfWith.entrySet();
      Iterator var4 = var3.iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         Object var5 = selector.invoke(var4.next());

         while(var4.hasNext()) {
            Object var6 = selector.invoke(var4.next());
            if (comparator.compare(var5, var6) > 0) {
               var5 = var6;
            }
         }

         return var5;
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @OverloadResolutionByLambdaReturnType
   @InlineOnly
   private static final Object minOfWithOrNull(Map $this$minOfWithOrNull, Comparator comparator, Function1 selector) {
      Intrinsics.checkNotNullParameter($this$minOfWithOrNull, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      Iterable var3 = (Iterable)$this$minOfWithOrNull.entrySet();
      Iterator var4 = var3.iterator();
      Object var10000;
      if (!var4.hasNext()) {
         var10000 = null;
      } else {
         Object var5 = selector.invoke(var4.next());

         while(var4.hasNext()) {
            Object var6 = selector.invoke(var4.next());
            if (comparator.compare(var5, var6) > 0) {
               var5 = var6;
            }
         }

         var10000 = var5;
      }

      return var10000;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use minWithOrNull instead.",
      replaceWith = @ReplaceWith(
   expression = "this.minWithOrNull(comparator)",
   imports = {}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.4",
      errorSince = "1.5",
      hiddenSince = "1.6"
   )
   public static final Map.Entry minWith(Map $this$minWith, Comparator comparator) {
      Intrinsics.checkNotNullParameter($this$minWith, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return (Map.Entry)CollectionsKt.minWithOrNull((Iterable)$this$minWith.entrySet(), comparator);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @InlineOnly
   private static final Map.Entry minWithOrNull(Map $this$minWithOrNull, Comparator comparator) {
      Intrinsics.checkNotNullParameter($this$minWithOrNull, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return (Map.Entry)CollectionsKt.minWithOrNull((Iterable)$this$minWithOrNull.entrySet(), comparator);
   }

   public static final boolean none(@NotNull Map $this$none) {
      Intrinsics.checkNotNullParameter($this$none, "<this>");
      return $this$none.isEmpty();
   }

   public static final boolean none(@NotNull Map $this$none, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$none, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$none = 0;
      if ($this$none.isEmpty()) {
         return true;
      } else {
         for(Map.Entry element : $this$none.entrySet()) {
            if ((Boolean)predicate.invoke(element)) {
               return false;
            }
         }

         return true;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map onEach(@NotNull Map $this$onEach, @NotNull Function1 action) {
      Intrinsics.checkNotNullParameter($this$onEach, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      int $i$f$onEach = 0;
      int var5 = 0;

      for(Map.Entry element : $this$onEach.entrySet()) {
         action.invoke(element);
      }

      return $this$onEach;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @NotNull
   public static final Map onEachIndexed(@NotNull Map $this$onEachIndexed, @NotNull Function2 action) {
      Intrinsics.checkNotNullParameter($this$onEachIndexed, "<this>");
      Intrinsics.checkNotNullParameter(action, "action");
      int $i$f$onEachIndexed = 0;
      int var5 = 0;
      Iterable $this$forEachIndexed$iv = (Iterable)$this$onEachIndexed.entrySet();
      int $i$f$forEachIndexed = 0;
      int index$iv = 0;

      for(Object item$iv : $this$forEachIndexed$iv) {
         int var11 = index$iv++;
         if (var11 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         action.invoke(var11, item$iv);
      }

      return $this$onEachIndexed;
   }

   @InlineOnly
   private static final Iterable asIterable(Map $this$asIterable) {
      Intrinsics.checkNotNullParameter($this$asIterable, "<this>");
      return (Iterable)$this$asIterable.entrySet();
   }

   @NotNull
   public static final Sequence asSequence(@NotNull Map $this$asSequence) {
      Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
      return CollectionsKt.asSequence((Iterable)$this$asSequence.entrySet());
   }

   public MapsKt___MapsKt() {
   }
}
