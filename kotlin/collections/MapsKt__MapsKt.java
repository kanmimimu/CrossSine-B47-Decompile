package kotlin.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.BuilderInference;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000~\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010(\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\n\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0017\u001a`\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052%\b\u0001\u0010\u0006\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001aX\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032%\b\u0001\u0010\u0006\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u001e\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\u001a1\u0010\f\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\rj\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001a_\u0010\f\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\rj\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0012\u001a1\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0014j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001a_\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0014j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0016\u001a!\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001aO\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0018\u001a!\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001aO\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0018\u001a*\u0010\u001a\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\n¢\u0006\u0002\u0010\u001c\u001a*\u0010\u001d\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\n¢\u0006\u0002\u0010\u001c\u001a9\u0010\u001e\u001a\u00020\u001f\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\"\u001a1\u0010#\u001a\u00020\u001f\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b *\u000e\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0002\b\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\"\u001a7\u0010$\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\t\b\u0001\u0010\u0003¢\u0006\u0002\b *\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010%\u001a\u0002H\u0003H\u0087\b¢\u0006\u0002\u0010\"\u001aV\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001aJ\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001aV\u0010)\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001aq\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001aq\u0010.\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001aJ\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001a;\u00100\u001a\u0004\u0018\u0001H\u0003\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u00101\u001aC\u00102\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0087\bø\u0001\u0000¢\u0006\u0002\u00105\u001aC\u00106\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0080\bø\u0001\u0000¢\u0006\u0002\u00105\u001aC\u00107\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0086\bø\u0001\u0000¢\u0006\u0002\u00105\u001a1\u00108\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0007¢\u0006\u0002\u00101\u001a?\u00109\u001a\u0002H:\"\u0014\b\u0000\u0010+*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0001*\u0002H:\"\u0004\b\u0001\u0010:*\u0002H+2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H:04H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010;\u001a'\u0010<\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u001a:\u0010=\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a9\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b0?\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\n\u001a<\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030A0@\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\bH\u0087\n¢\u0006\u0002\bB\u001a\\\u0010C\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000\u001aw\u0010E\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:\"\u0018\b\u0003\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H:\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001a\\\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H:0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000\u001aw\u0010G\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:\"\u0018\b\u0003\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H:0\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001a@\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\u0002¢\u0006\u0002\u0010I\u001aH\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u000e\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010H\u0087\u0002¢\u0006\u0002\u0010K\u001aA\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020LH\u0087\u0002\u001aA\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0087\u0002\u001a2\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010O\u001a:\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u000e\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010H\u0087\n¢\u0006\u0002\u0010P\u001a3\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020LH\u0087\n\u001a3\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0087\n\u001a0\u0010Q\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0000\u001a3\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001H\u0087\b\u001aT\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010H\u0086\u0002¢\u0006\u0002\u0010T\u001aG\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011H\u0086\u0002\u001aM\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110LH\u0086\u0002\u001aI\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0014\u0010V\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0086\u0002\u001aM\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110MH\u0086\u0002\u001aJ\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010H\u0087\n¢\u0006\u0002\u0010X\u001a=\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011H\u0087\n\u001aC\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110LH\u0087\n\u001a=\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\n\u001aC\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110MH\u0087\n\u001aG\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010¢\u0006\u0002\u0010X\u001a@\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L\u001a@\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M\u001a;\u0010Z\u001a\u0004\u0018\u0001H\u0003\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u00101\u001a:\u0010[\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u00022\u0006\u0010%\u001a\u0002H\u0003H\u0087\n¢\u0006\u0002\u0010\\\u001a;\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010¢\u0006\u0002\u0010\u0018\u001aQ\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u00102\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010^\u001a4\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L\u001aO\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L2\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010_\u001a2\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aM\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+H\u0007¢\u0006\u0002\u0010`\u001a4\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M\u001aO\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M2\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010a\u001a2\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001a1\u0010c\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006d"},
   d2 = {"buildMap", "", "K", "V", "capacity", "", "builderAction", "Lkotlin/Function1;", "", "", "Lkotlin/ExtensionFunctionType;", "emptyMap", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", "value", "filter", "predicate", "filterKeys", "filterNot", "filterNotTo", "M", "destination", "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", "get", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", "set", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"},
   xs = "kotlin/collections/MapsKt"
)
class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
   @NotNull
   public static final Map emptyMap() {
      return EmptyMap.INSTANCE;
   }

   @NotNull
   public static final Map mapOf(@NotNull Pair... pairs) {
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      return pairs.length > 0 ? MapsKt.toMap(pairs, (Map)(new LinkedHashMap(MapsKt.mapCapacity(pairs.length)))) : MapsKt.emptyMap();
   }

   @InlineOnly
   private static final Map mapOf() {
      return MapsKt.emptyMap();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final Map mutableMapOf() {
      return (Map)(new LinkedHashMap());
   }

   @NotNull
   public static final Map mutableMapOf(@NotNull Pair... pairs) {
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      LinkedHashMap $this$mutableMapOf_u24lambda_u2d0 = new LinkedHashMap(MapsKt.mapCapacity(pairs.length));
      int var3 = 0;
      MapsKt.putAll((Map)$this$mutableMapOf_u24lambda_u2d0, pairs);
      return (Map)$this$mutableMapOf_u24lambda_u2d0;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final HashMap hashMapOf() {
      return new HashMap();
   }

   @NotNull
   public static final HashMap hashMapOf(@NotNull Pair... pairs) {
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      HashMap $this$hashMapOf_u24lambda_u2d1 = new HashMap(MapsKt.mapCapacity(pairs.length));
      int var3 = 0;
      MapsKt.putAll((Map)$this$hashMapOf_u24lambda_u2d1, pairs);
      return $this$hashMapOf_u24lambda_u2d1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final LinkedHashMap linkedMapOf() {
      return new LinkedHashMap();
   }

   @NotNull
   public static final LinkedHashMap linkedMapOf(@NotNull Pair... pairs) {
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      return (LinkedHashMap)MapsKt.toMap(pairs, (Map)(new LinkedHashMap(MapsKt.mapCapacity(pairs.length))));
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final Map buildMap(@BuilderInference Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      Map var1 = MapsKt.createMapBuilder();
      builderAction.invoke(var1);
      return MapsKt.build(var1);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final Map buildMap(int capacity, @BuilderInference Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      Map var2 = MapsKt.createMapBuilder(capacity);
      builderAction.invoke(var2);
      return MapsKt.build(var2);
   }

   @InlineOnly
   private static final boolean isNotEmpty(Map $this$isNotEmpty) {
      Intrinsics.checkNotNullParameter($this$isNotEmpty, "<this>");
      return !$this$isNotEmpty.isEmpty();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean isNullOrEmpty(Map $this$isNullOrEmpty) {
      return $this$isNullOrEmpty == null || $this$isNullOrEmpty.isEmpty();
   }

   @InlineOnly
   private static final Map orEmpty(Map $this$orEmpty) {
      return $this$orEmpty == null ? MapsKt.emptyMap() : $this$orEmpty;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifEmpty(Map $this$ifEmpty, Function0 defaultValue) {
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      return $this$ifEmpty.isEmpty() ? defaultValue.invoke() : $this$ifEmpty;
   }

   @InlineOnly
   private static final boolean contains(Map $this$contains, Object key) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return $this$contains.containsKey(key);
   }

   @InlineOnly
   private static final Object get(Map $this$get, Object key) {
      Intrinsics.checkNotNullParameter($this$get, "<this>");
      return $this$get.get(key);
   }

   @InlineOnly
   private static final void set(Map $this$set, Object key, Object value) {
      Intrinsics.checkNotNullParameter($this$set, "<this>");
      $this$set.put(key, value);
   }

   @InlineOnly
   private static final boolean containsKey(Map $this$containsKey, Object key) {
      Intrinsics.checkNotNullParameter($this$containsKey, "<this>");
      return $this$containsKey.containsKey(key);
   }

   @InlineOnly
   private static final boolean containsValue(Map $this$containsValue, Object value) {
      Intrinsics.checkNotNullParameter($this$containsValue, "<this>");
      return $this$containsValue.containsValue(value);
   }

   @InlineOnly
   private static final Object remove(Map $this$remove, Object key) {
      Intrinsics.checkNotNullParameter($this$remove, "<this>");
      return $this$remove.remove(key);
   }

   @InlineOnly
   private static final Object component1(Map.Entry $this$component1) {
      Intrinsics.checkNotNullParameter($this$component1, "<this>");
      return $this$component1.getKey();
   }

   @InlineOnly
   private static final Object component2(Map.Entry $this$component2) {
      Intrinsics.checkNotNullParameter($this$component2, "<this>");
      return $this$component2.getValue();
   }

   @InlineOnly
   private static final Pair toPair(Map.Entry $this$toPair) {
      Intrinsics.checkNotNullParameter($this$toPair, "<this>");
      return new Pair($this$toPair.getKey(), $this$toPair.getValue());
   }

   @InlineOnly
   private static final Object getOrElse(Map $this$getOrElse, Object key, Function0 defaultValue) {
      Intrinsics.checkNotNullParameter($this$getOrElse, "<this>");
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      Object var3 = $this$getOrElse.get(key);
      return var3 == null ? defaultValue.invoke() : var3;
   }

   public static final Object getOrElseNullable(@NotNull Map $this$getOrElseNullable, Object key, @NotNull Function0 defaultValue) {
      Intrinsics.checkNotNullParameter($this$getOrElseNullable, "<this>");
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      int $i$f$getOrElseNullable = 0;
      Object value = $this$getOrElseNullable.get(key);
      return value == null && !$this$getOrElseNullable.containsKey(key) ? defaultValue.invoke() : value;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static final Object getValue(@NotNull Map $this$getValue, Object key) {
      Intrinsics.checkNotNullParameter($this$getValue, "<this>");
      return MapsKt.getOrImplicitDefaultNullable($this$getValue, key);
   }

   public static final Object getOrPut(@NotNull Map $this$getOrPut, Object key, @NotNull Function0 defaultValue) {
      Intrinsics.checkNotNullParameter($this$getOrPut, "<this>");
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      int $i$f$getOrPut = 0;
      Object value = $this$getOrPut.get(key);
      Object var10000;
      if (value == null) {
         Object answer = defaultValue.invoke();
         $this$getOrPut.put(key, answer);
         var10000 = answer;
      } else {
         var10000 = value;
      }

      return var10000;
   }

   @InlineOnly
   private static final Iterator iterator(Map $this$iterator) {
      Intrinsics.checkNotNullParameter($this$iterator, "<this>");
      return $this$iterator.entrySet().iterator();
   }

   @JvmName(
      name = "mutableIterator"
   )
   @InlineOnly
   private static final Iterator mutableIterator(Map $this$iterator) {
      Intrinsics.checkNotNullParameter($this$iterator, "<this>");
      return $this$iterator.entrySet().iterator();
   }

   @NotNull
   public static final Map mapValuesTo(@NotNull Map $this$mapValuesTo, @NotNull Map destination, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$mapValuesTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$mapValuesTo = 0;
      Iterable $this$associateByTo$iv = (Iterable)$this$mapValuesTo.entrySet();
      int $i$f$associateByTo = 0;

      for(Object element$iv : $this$associateByTo$iv) {
         Map.Entry it = (Map.Entry)element$iv;
         int var9 = 0;
         Object var11 = it.getKey();
         destination.put(var11, transform.invoke(element$iv));
      }

      return destination;
   }

   @NotNull
   public static final Map mapKeysTo(@NotNull Map $this$mapKeysTo, @NotNull Map destination, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$mapKeysTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$mapKeysTo = 0;
      Iterable $this$associateByTo$iv = (Iterable)$this$mapKeysTo.entrySet();
      int $i$f$associateByTo = 0;

      for(Object element$iv : $this$associateByTo$iv) {
         Object var10001 = transform.invoke(element$iv);
         Map.Entry it = (Map.Entry)element$iv;
         Object var11 = var10001;
         int var9 = 0;
         Object var12 = it.getValue();
         destination.put(var11, var12);
      }

      return destination;
   }

   public static final void putAll(@NotNull Map $this$putAll, @NotNull Pair[] pairs) {
      Intrinsics.checkNotNullParameter($this$putAll, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      Pair[] var2 = pairs;
      int var3 = 0;
      int var4 = pairs.length;

      while(var3 < var4) {
         Pair var5 = var2[var3];
         ++var3;
         Object key = var5.component1();
         Object value = var5.component2();
         $this$putAll.put(key, value);
      }

   }

   public static final void putAll(@NotNull Map $this$putAll, @NotNull Iterable pairs) {
      Intrinsics.checkNotNullParameter($this$putAll, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");

      for(Pair var3 : pairs) {
         Object key = var3.component1();
         Object value = var3.component2();
         $this$putAll.put(key, value);
      }

   }

   public static final void putAll(@NotNull Map $this$putAll, @NotNull Sequence pairs) {
      Intrinsics.checkNotNullParameter($this$putAll, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");

      for(Pair var3 : pairs) {
         Object key = var3.component1();
         Object value = var3.component2();
         $this$putAll.put(key, value);
      }

   }

   @NotNull
   public static final Map mapValues(@NotNull Map $this$mapValues, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$mapValues, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$mapValues = 0;
      Map destination$iv = (Map)(new LinkedHashMap(MapsKt.mapCapacity($this$mapValues.size())));
      int $i$f$mapValuesTo = 0;
      Iterable $this$associateByTo$iv$iv = (Iterable)$this$mapValues.entrySet();
      int $i$f$associateByTo = 0;

      for(Object element$iv$iv : $this$associateByTo$iv$iv) {
         Map.Entry it$iv = (Map.Entry)element$iv$iv;
         int var12 = 0;
         Object var13 = it$iv.getKey();
         destination$iv.put(var13, transform.invoke(element$iv$iv));
      }

      return destination$iv;
   }

   @NotNull
   public static final Map mapKeys(@NotNull Map $this$mapKeys, @NotNull Function1 transform) {
      Intrinsics.checkNotNullParameter($this$mapKeys, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      int $i$f$mapKeys = 0;
      Map destination$iv = (Map)(new LinkedHashMap(MapsKt.mapCapacity($this$mapKeys.size())));
      int $i$f$mapKeysTo = 0;
      Iterable $this$associateByTo$iv$iv = (Iterable)$this$mapKeys.entrySet();
      int $i$f$associateByTo = 0;

      for(Object element$iv$iv : $this$associateByTo$iv$iv) {
         Object var10001 = transform.invoke(element$iv$iv);
         Map.Entry it$iv = (Map.Entry)element$iv$iv;
         Object var11 = var10001;
         int var13 = 0;
         Object var14 = it$iv.getValue();
         destination$iv.put(var11, var14);
      }

      return destination$iv;
   }

   @NotNull
   public static final Map filterKeys(@NotNull Map $this$filterKeys, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$filterKeys, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$filterKeys = 0;
      LinkedHashMap result = new LinkedHashMap();

      for(Map.Entry entry : $this$filterKeys.entrySet()) {
         if ((Boolean)predicate.invoke(entry.getKey())) {
            result.put(entry.getKey(), entry.getValue());
         }
      }

      return (Map)result;
   }

   @NotNull
   public static final Map filterValues(@NotNull Map $this$filterValues, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$filterValues, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$filterValues = 0;
      LinkedHashMap result = new LinkedHashMap();

      for(Map.Entry entry : $this$filterValues.entrySet()) {
         if ((Boolean)predicate.invoke(entry.getValue())) {
            result.put(entry.getKey(), entry.getValue());
         }
      }

      return (Map)result;
   }

   @NotNull
   public static final Map filterTo(@NotNull Map $this$filterTo, @NotNull Map destination, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$filterTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$filterTo = 0;

      for(Map.Entry element : $this$filterTo.entrySet()) {
         if ((Boolean)predicate.invoke(element)) {
            destination.put(element.getKey(), element.getValue());
         }
      }

      return destination;
   }

   @NotNull
   public static final Map filter(@NotNull Map $this$filter, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$filter, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$filter = 0;
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$filterTo = 0;

      for(Map.Entry element$iv : $this$filter.entrySet()) {
         if ((Boolean)predicate.invoke(element$iv)) {
            destination$iv.put(element$iv.getKey(), element$iv.getValue());
         }
      }

      return destination$iv;
   }

   @NotNull
   public static final Map filterNotTo(@NotNull Map $this$filterNotTo, @NotNull Map destination, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$filterNotTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$filterNotTo = 0;

      for(Map.Entry element : $this$filterNotTo.entrySet()) {
         if (!(Boolean)predicate.invoke(element)) {
            destination.put(element.getKey(), element.getValue());
         }
      }

      return destination;
   }

   @NotNull
   public static final Map filterNot(@NotNull Map $this$filterNot, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$filterNot, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$filterNot = 0;
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$filterNotTo = 0;

      for(Map.Entry element$iv : $this$filterNot.entrySet()) {
         if (!(Boolean)predicate.invoke(element$iv)) {
            destination$iv.put(element$iv.getKey(), element$iv.getValue());
         }
      }

      return destination$iv;
   }

   @NotNull
   public static final Map toMap(@NotNull Iterable $this$toMap) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      if ($this$toMap instanceof Collection) {
         int var1 = ((Collection)$this$toMap).size();
         Map var10000;
         switch (var1) {
            case 0:
               var10000 = MapsKt.emptyMap();
               break;
            case 1:
               var10000 = MapsKt.mapOf($this$toMap instanceof List ? (Pair)((List)$this$toMap).get(0) : (Pair)$this$toMap.iterator().next());
               break;
            default:
               var10000 = MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap(MapsKt.mapCapacity(((Collection)$this$toMap).size()))));
         }

         return var10000;
      } else {
         return MapsKt.optimizeReadOnlyMap(MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap())));
      }
   }

   @NotNull
   public static final Map toMap(@NotNull Iterable $this$toMap, @NotNull Map destination) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      int var4 = 0;
      MapsKt.putAll(destination, $this$toMap);
      return destination;
   }

   @NotNull
   public static final Map toMap(@NotNull Pair[] $this$toMap) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      int var1 = $this$toMap.length;
      Map var10000;
      switch (var1) {
         case 0:
            var10000 = MapsKt.emptyMap();
            break;
         case 1:
            var10000 = MapsKt.mapOf($this$toMap[0]);
            break;
         default:
            var10000 = MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap(MapsKt.mapCapacity($this$toMap.length))));
      }

      return var10000;
   }

   @NotNull
   public static final Map toMap(@NotNull Pair[] $this$toMap, @NotNull Map destination) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      int var4 = 0;
      MapsKt.putAll(destination, $this$toMap);
      return destination;
   }

   @NotNull
   public static final Map toMap(@NotNull Sequence $this$toMap) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      return MapsKt.optimizeReadOnlyMap(MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap())));
   }

   @NotNull
   public static final Map toMap(@NotNull Sequence $this$toMap, @NotNull Map destination) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      int var4 = 0;
      MapsKt.putAll(destination, $this$toMap);
      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map toMap(@NotNull Map $this$toMap) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      int var1 = $this$toMap.size();
      Map var10000;
      switch (var1) {
         case 0:
            var10000 = MapsKt.emptyMap();
            break;
         case 1:
            var10000 = MapsKt.toSingletonMap($this$toMap);
            break;
         default:
            var10000 = MapsKt.toMutableMap($this$toMap);
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map toMutableMap(@NotNull Map $this$toMutableMap) {
      Intrinsics.checkNotNullParameter($this$toMutableMap, "<this>");
      return (Map)(new LinkedHashMap($this$toMutableMap));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map toMap(@NotNull Map $this$toMap, @NotNull Map destination) {
      Intrinsics.checkNotNullParameter($this$toMap, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      int var4 = 0;
      destination.putAll($this$toMap);
      return destination;
   }

   @NotNull
   public static final Map plus(@NotNull Map $this$plus, @NotNull Pair pair) {
      Intrinsics.checkNotNullParameter($this$plus, "<this>");
      Intrinsics.checkNotNullParameter(pair, "pair");
      Map var10000;
      if ($this$plus.isEmpty()) {
         var10000 = MapsKt.mapOf(pair);
      } else {
         LinkedHashMap $this$plus_u24lambda_u2d11 = new LinkedHashMap($this$plus);
         int var4 = 0;
         $this$plus_u24lambda_u2d11.put(pair.getFirst(), pair.getSecond());
         var10000 = (Map)$this$plus_u24lambda_u2d11;
      }

      return var10000;
   }

   @NotNull
   public static final Map plus(@NotNull Map $this$plus, @NotNull Iterable pairs) {
      Intrinsics.checkNotNullParameter($this$plus, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      Map var10000;
      if ($this$plus.isEmpty()) {
         var10000 = MapsKt.toMap(pairs);
      } else {
         LinkedHashMap $this$plus_u24lambda_u2d12 = new LinkedHashMap($this$plus);
         int var4 = 0;
         MapsKt.putAll((Map)$this$plus_u24lambda_u2d12, pairs);
         var10000 = (Map)$this$plus_u24lambda_u2d12;
      }

      return var10000;
   }

   @NotNull
   public static final Map plus(@NotNull Map $this$plus, @NotNull Pair[] pairs) {
      Intrinsics.checkNotNullParameter($this$plus, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      Map var10000;
      if ($this$plus.isEmpty()) {
         var10000 = MapsKt.toMap(pairs);
      } else {
         LinkedHashMap $this$plus_u24lambda_u2d13 = new LinkedHashMap($this$plus);
         int var4 = 0;
         MapsKt.putAll((Map)$this$plus_u24lambda_u2d13, pairs);
         var10000 = (Map)$this$plus_u24lambda_u2d13;
      }

      return var10000;
   }

   @NotNull
   public static final Map plus(@NotNull Map $this$plus, @NotNull Sequence pairs) {
      Intrinsics.checkNotNullParameter($this$plus, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      LinkedHashMap $this$plus_u24lambda_u2d14 = new LinkedHashMap($this$plus);
      int var4 = 0;
      MapsKt.putAll((Map)$this$plus_u24lambda_u2d14, pairs);
      return MapsKt.optimizeReadOnlyMap((Map)$this$plus_u24lambda_u2d14);
   }

   @NotNull
   public static final Map plus(@NotNull Map $this$plus, @NotNull Map map) {
      Intrinsics.checkNotNullParameter($this$plus, "<this>");
      Intrinsics.checkNotNullParameter(map, "map");
      LinkedHashMap $this$plus_u24lambda_u2d15 = new LinkedHashMap($this$plus);
      int var4 = 0;
      $this$plus_u24lambda_u2d15.putAll(map);
      return (Map)$this$plus_u24lambda_u2d15;
   }

   @InlineOnly
   private static final void plusAssign(Map $this$plusAssign, Pair pair) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(pair, "pair");
      $this$plusAssign.put(pair.getFirst(), pair.getSecond());
   }

   @InlineOnly
   private static final void plusAssign(Map $this$plusAssign, Iterable pairs) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      MapsKt.putAll($this$plusAssign, pairs);
   }

   @InlineOnly
   private static final void plusAssign(Map $this$plusAssign, Pair[] pairs) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      MapsKt.putAll($this$plusAssign, pairs);
   }

   @InlineOnly
   private static final void plusAssign(Map $this$plusAssign, Sequence pairs) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      MapsKt.putAll($this$plusAssign, pairs);
   }

   @InlineOnly
   private static final void plusAssign(Map $this$plusAssign, Map map) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(map, "map");
      $this$plusAssign.putAll(map);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map $this$minus, Object key) {
      Intrinsics.checkNotNullParameter($this$minus, "<this>");
      Map $this$minus_u24lambda_u2d16 = MapsKt.toMutableMap($this$minus);
      int var4 = 0;
      $this$minus_u24lambda_u2d16.remove(key);
      return MapsKt.optimizeReadOnlyMap($this$minus_u24lambda_u2d16);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map $this$minus, @NotNull Iterable keys) {
      Intrinsics.checkNotNullParameter($this$minus, "<this>");
      Intrinsics.checkNotNullParameter(keys, "keys");
      Map $this$minus_u24lambda_u2d17 = MapsKt.toMutableMap($this$minus);
      int var4 = 0;
      CollectionsKt.removeAll((Collection)$this$minus_u24lambda_u2d17.keySet(), keys);
      return MapsKt.optimizeReadOnlyMap($this$minus_u24lambda_u2d17);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map $this$minus, @NotNull Object[] keys) {
      Intrinsics.checkNotNullParameter($this$minus, "<this>");
      Intrinsics.checkNotNullParameter(keys, "keys");
      Map $this$minus_u24lambda_u2d18 = MapsKt.toMutableMap($this$minus);
      int var4 = 0;
      CollectionsKt.removeAll((Collection)$this$minus_u24lambda_u2d18.keySet(), keys);
      return MapsKt.optimizeReadOnlyMap($this$minus_u24lambda_u2d18);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map $this$minus, @NotNull Sequence keys) {
      Intrinsics.checkNotNullParameter($this$minus, "<this>");
      Intrinsics.checkNotNullParameter(keys, "keys");
      Map $this$minus_u24lambda_u2d19 = MapsKt.toMutableMap($this$minus);
      int var4 = 0;
      CollectionsKt.removeAll((Collection)$this$minus_u24lambda_u2d19.keySet(), keys);
      return MapsKt.optimizeReadOnlyMap($this$minus_u24lambda_u2d19);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(Map $this$minusAssign, Object key) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      $this$minusAssign.remove(key);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(Map $this$minusAssign, Iterable keys) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      Intrinsics.checkNotNullParameter(keys, "keys");
      CollectionsKt.removeAll((Collection)$this$minusAssign.keySet(), keys);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(Map $this$minusAssign, Object[] keys) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      Intrinsics.checkNotNullParameter(keys, "keys");
      CollectionsKt.removeAll((Collection)$this$minusAssign.keySet(), keys);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(Map $this$minusAssign, Sequence keys) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      Intrinsics.checkNotNullParameter(keys, "keys");
      CollectionsKt.removeAll((Collection)$this$minusAssign.keySet(), keys);
   }

   @NotNull
   public static final Map optimizeReadOnlyMap(@NotNull Map $this$optimizeReadOnlyMap) {
      Intrinsics.checkNotNullParameter($this$optimizeReadOnlyMap, "<this>");
      int var1 = $this$optimizeReadOnlyMap.size();
      Map var10000;
      switch (var1) {
         case 0:
            var10000 = MapsKt.emptyMap();
            break;
         case 1:
            var10000 = MapsKt.toSingletonMap($this$optimizeReadOnlyMap);
            break;
         default:
            var10000 = $this$optimizeReadOnlyMap;
      }

      return var10000;
   }

   public MapsKt__MapsKt() {
   }
}
