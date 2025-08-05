package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.WasExperimental;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u0084\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b!\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0000\u001a\u001c\u0010\f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010\u0011\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001f\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0086\u0002\u001a\u001f\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0086\u0002\u001a\u0015\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0087\n\u001a\u0018\u0010\u0017\u001a\u00020\u0010*\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0000\u001a\u0018\u0010\u0018\u001a\u00020\u0010*\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0000\u001a\u001c\u0010\u0019\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010\u0019\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a:\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001aE\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0010H\u0002¢\u0006\u0002\b!\u001a:\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010#\u001a\u00020\u0010*\u00020\u00022\u0006\u0010$\u001a\u00020\u0006\u001a7\u0010%\u001a\u0002H&\"\f\b\u0000\u0010'*\u00020\u0002*\u0002H&\"\u0004\b\u0001\u0010&*\u0002H'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H&0)H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010*\u001a7\u0010+\u001a\u0002H&\"\f\b\u0000\u0010'*\u00020\u0002*\u0002H&\"\u0004\b\u0001\u0010&*\u0002H'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H&0)H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010*\u001a&\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a;\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010 \u001a\u00020\u0010H\u0002¢\u0006\u0002\b.\u001a&\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010/\u001a\u00020\r2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u00100\u001a\u00020\u0006*\u00020\u00022\u0006\u00101\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a,\u00100\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\r\u00103\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a\r\u00104\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a\r\u00105\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a \u00106\u001a\u00020\u0010*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a \u00107\u001a\u00020\u0010*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\r\u00108\u001a\u000209*\u00020\u0002H\u0086\u0002\u001a&\u0010:\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u0010:\u001a\u00020\u0006*\u00020\u00022\u0006\u0010/\u001a\u00020\r2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u0010;\u001a\u00020\u0006*\u00020\u00022\u0006\u00101\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a,\u0010;\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0010\u0010<\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u0002\u001a\u0010\u0010>\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u0002\u001a\u0015\u0010@\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0087\f\u001a\u000f\u0010A\u001a\u00020\r*\u0004\u0018\u00010\rH\u0087\b\u001a\u001c\u0010B\u001a\u00020\u0002*\u00020\u00022\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010B\u001a\u00020\r*\u00020\r2\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010E\u001a\u00020\u0002*\u00020\u00022\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010E\u001a\u00020\r*\u00020\r2\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001aG\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00010=*\u00020\u00022\u000e\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0002¢\u0006\u0004\bI\u0010J\u001a=\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00010=*\u00020\u00022\u0006\u0010G\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0002¢\u0006\u0002\bI\u001a4\u0010K\u001a\u00020\u0010*\u00020\u00022\u0006\u0010L\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010M\u001a\u00020\u00062\u0006\u0010C\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0000\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u0002\u001a\u0012\u0010N\u001a\u00020\r*\u00020\r2\u0006\u0010O\u001a\u00020\u0002\u001a\u001a\u0010P\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006\u001a\u0012\u0010P\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u001d\u0010P\u001a\u00020\r*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010P\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010R\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010R\u001a\u00020\r*\u00020\r2\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010S\u001a\u00020\u0002*\u00020\u00022\u0006\u0010T\u001a\u00020\u0002\u001a\u001a\u0010S\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010S\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u0002\u001a\u001a\u0010S\u001a\u00020\r*\u00020\r2\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a.\u0010U\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0014\b\b\u0010V\u001a\u000e\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020\u00020WH\u0087\bø\u0001\u0000\u001a\u001d\u0010U\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010Y\u001a\u00020\rH\u0087\b\u001a$\u0010Z\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010Z\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010\\\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010\\\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010]\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010]\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010^\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010^\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001d\u0010_\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010Y\u001a\u00020\rH\u0087\b\u001a)\u0010`\u001a\u00020\r*\u00020\r2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140WH\u0087\bø\u0001\u0000¢\u0006\u0002\ba\u001a)\u0010`\u001a\u00020\r*\u00020\r2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00020WH\u0087\bø\u0001\u0000¢\u0006\u0002\bb\u001a\"\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020\u0002\u001a\u001a\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u00012\u0006\u0010Y\u001a\u00020\u0002\u001a%\u0010c\u001a\u00020\r*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010c\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u00012\u0006\u0010Y\u001a\u00020\u0002H\u0087\b\u001a=\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0012\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H\"\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010e\u001a0\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\n\u0010G\u001a\u000202\"\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u001a/\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0006\u0010T\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0006H\u0002¢\u0006\u0002\bf\u001a%\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0087\b\u001a=\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\u0012\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H\"\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010h\u001a0\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\n\u0010G\u001a\u000202\"\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u001a%\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0087\b\u001a\u001c\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a$\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010j\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u001d\u0010j\u001a\u00020\u0002*\u00020\r2\u0006\u0010k\u001a\u00020\u00062\u0006\u0010l\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010m\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010-\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010m\u001a\u00020\r*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u0012\u0010m\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u0001\u001a\u001c\u0010n\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010n\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010o\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010o\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010p\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010p\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010q\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010q\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\f\u0010r\u001a\u00020\u0010*\u00020\rH\u0007\u001a\u0013\u0010s\u001a\u0004\u0018\u00010\u0010*\u00020\rH\u0007¢\u0006\u0002\u0010t\u001a\n\u0010u\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010u\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001\u0000\u001a\u0016\u0010u\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010u\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010u\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001\u0000\u001a\u0016\u0010u\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\u001a\n\u0010w\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010w\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001\u0000\u001a\u0016\u0010w\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010w\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010w\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001\u0000\u001a\u0016\u0010w\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\u001a\n\u0010x\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010x\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001\u0000\u001a\u0016\u0010x\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010x\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010x\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001\u0000\u001a\u0016\u0010x\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006y"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "requireNonNegativeLimit", "", "limit", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "contentEqualsIgnoreCaseImpl", "contentEqualsImpl", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", "range", "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceFirstChar", "replaceFirstCharWithChar", "replaceFirstCharWithCharSequence", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "toBooleanStrict", "toBooleanStrictOrNull", "(Ljava/lang/String;)Ljava/lang/Boolean;", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsKt extends StringsKt__StringsJVMKt {
   @NotNull
   public static final CharSequence trim(@NotNull CharSequence $this$trim, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$trim, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$trim = 0;
      int startIndex = 0;
      int endIndex = $this$trim.length() - 1;
      boolean startFound = false;

      while(startIndex <= endIndex) {
         int index = !startFound ? startIndex : endIndex;
         boolean match = (Boolean)predicate.invoke($this$trim.charAt(index));
         if (!startFound) {
            if (!match) {
               startFound = true;
            } else {
               ++startIndex;
            }
         } else {
            if (!match) {
               break;
            }

            --endIndex;
         }
      }

      return $this$trim.subSequence(startIndex, endIndex + 1);
   }

   @NotNull
   public static final String trim(@NotNull String $this$trim, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$trim, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$trim = 0;
      CharSequence $this$trim$iv = (CharSequence)$this$trim;
      int $i$f$trim = 0;
      int startIndex$iv = 0;
      int endIndex$iv = $this$trim$iv.length() - 1;
      boolean startFound$iv = false;

      while(startIndex$iv <= endIndex$iv) {
         int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
         boolean match$iv = (Boolean)predicate.invoke($this$trim$iv.charAt(index$iv));
         if (!startFound$iv) {
            if (!match$iv) {
               startFound$iv = true;
            } else {
               ++startIndex$iv;
            }
         } else {
            if (!match$iv) {
               break;
            }

            --endIndex$iv;
         }
      }

      return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$trimStart = 0;
      int var3 = 0;
      int var4 = $this$trimStart.length();

      while(var3 < var4) {
         int index = var3++;
         if (!(Boolean)predicate.invoke($this$trimStart.charAt(index))) {
            return $this$trimStart.subSequence(index, $this$trimStart.length());
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String trimStart(@NotNull String $this$trimStart, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$trimStart = 0;
      CharSequence $this$trimStart$iv = (CharSequence)$this$trimStart;
      int $i$f$trimStart = 0;
      int var5 = 0;
      int var6 = $this$trimStart$iv.length();

      CharSequence var10000;
      while(true) {
         if (var5 < var6) {
            int index$iv = var5++;
            if ((Boolean)predicate.invoke($this$trimStart$iv.charAt(index$iv))) {
               continue;
            }

            var10000 = $this$trimStart$iv.subSequence(index$iv, $this$trimStart$iv.length());
            break;
         }

         var10000 = (CharSequence)"";
         break;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$trimEnd = 0;
      int var3 = $this$trimEnd.length() + -1;
      if (0 <= var3) {
         do {
            int index = var3--;
            if (!(Boolean)predicate.invoke($this$trimEnd.charAt(index))) {
               return $this$trimEnd.subSequence(0, index + 1);
            }
         } while(0 <= var3);
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String trimEnd(@NotNull String $this$trimEnd, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$trimEnd = 0;
      CharSequence $this$trimEnd$iv = (CharSequence)$this$trimEnd;
      int $i$f$trimEnd = 0;
      int var5 = $this$trimEnd$iv.length() + -1;
      CharSequence var10000;
      if (0 <= var5) {
         do {
            int index$iv = var5--;
            if (!(Boolean)predicate.invoke($this$trimEnd$iv.charAt(index$iv))) {
               var10000 = $this$trimEnd$iv.subSequence(0, index$iv + 1);
               return var10000.toString();
            }
         } while(0 <= var5);
      }

      var10000 = (CharSequence)"";
      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trim(@NotNull CharSequence $this$trim, @NotNull char... chars) {
      Intrinsics.checkNotNullParameter($this$trim, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      CharSequence $this$trim$iv = $this$trim;
      int $i$f$trim = 0;
      int startIndex$iv = 0;
      int endIndex$iv = $this$trim.length() - 1;
      boolean startFound$iv = false;

      while(startIndex$iv <= endIndex$iv) {
         int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
         char it = $this$trim$iv.charAt(index$iv);
         int var9 = 0;
         it = ArraysKt.contains(chars, it);
         if (!startFound$iv) {
            if (!it) {
               startFound$iv = true;
            } else {
               ++startIndex$iv;
            }
         } else {
            if (!it) {
               break;
            }

            --endIndex$iv;
         }
      }

      return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1);
   }

   @NotNull
   public static final String trim(@NotNull String $this$trim, @NotNull char... chars) {
      Intrinsics.checkNotNullParameter($this$trim, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      int $i$f$trim = 0;
      CharSequence $this$trim$iv$iv = (CharSequence)$this$trim;
      int $i$f$trim = 0;
      int startIndex$iv$iv = 0;
      int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
      boolean startFound$iv$iv = false;

      while(startIndex$iv$iv <= endIndex$iv$iv) {
         int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
         char it = $this$trim$iv$iv.charAt(index$iv$iv);
         int var11 = 0;
         it = ArraysKt.contains(chars, it);
         if (!startFound$iv$iv) {
            if (!it) {
               startFound$iv$iv = true;
            } else {
               ++startIndex$iv$iv;
            }
         } else {
            if (!it) {
               break;
            }

            --endIndex$iv$iv;
         }
      }

      return $this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart, @NotNull char... chars) {
      Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      CharSequence $this$trimStart$iv = $this$trimStart;
      int $i$f$trimStart = 0;
      int var4 = 0;
      int var5 = $this$trimStart.length();

      CharSequence var10000;
      while(true) {
         if (var4 < var5) {
            int index$iv = var4++;
            char it = $this$trimStart$iv.charAt(index$iv);
            int var8 = 0;
            if (ArraysKt.contains(chars, it)) {
               continue;
            }

            var10000 = $this$trimStart$iv.subSequence(index$iv, $this$trimStart$iv.length());
            break;
         }

         var10000 = (CharSequence)"";
         break;
      }

      return var10000;
   }

   @NotNull
   public static final String trimStart(@NotNull String $this$trimStart, @NotNull char... chars) {
      Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      int $i$f$trimStart = 0;
      CharSequence $this$trimStart$iv$iv = (CharSequence)$this$trimStart;
      int $i$f$trimStart = 0;
      int var6 = 0;
      int var7 = $this$trimStart$iv$iv.length();

      CharSequence var10000;
      while(true) {
         if (var6 < var7) {
            int index$iv$iv = var6++;
            char it = $this$trimStart$iv$iv.charAt(index$iv$iv);
            int var10 = 0;
            if (ArraysKt.contains(chars, it)) {
               continue;
            }

            var10000 = $this$trimStart$iv$iv.subSequence(index$iv$iv, $this$trimStart$iv$iv.length());
            break;
         }

         var10000 = (CharSequence)"";
         break;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd, @NotNull char... chars) {
      Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      CharSequence $this$trimEnd$iv = $this$trimEnd;
      int $i$f$trimEnd = 0;
      int var4 = $this$trimEnd.length() + -1;
      CharSequence var10000;
      if (0 <= var4) {
         do {
            int index$iv = var4--;
            char it = $this$trimEnd$iv.charAt(index$iv);
            int var7 = 0;
            if (!ArraysKt.contains(chars, it)) {
               var10000 = $this$trimEnd$iv.subSequence(0, index$iv + 1);
               return var10000;
            }
         } while(0 <= var4);
      }

      var10000 = (CharSequence)"";
      return var10000;
   }

   @NotNull
   public static final String trimEnd(@NotNull String $this$trimEnd, @NotNull char... chars) {
      Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      int $i$f$trimEnd = 0;
      CharSequence $this$trimEnd$iv$iv = (CharSequence)$this$trimEnd;
      int $i$f$trimEnd = 0;
      int var6 = $this$trimEnd$iv$iv.length() + -1;
      CharSequence var10000;
      if (0 <= var6) {
         do {
            int index$iv$iv = var6--;
            char it = $this$trimEnd$iv$iv.charAt(index$iv$iv);
            int var9 = 0;
            if (!ArraysKt.contains(chars, it)) {
               var10000 = $this$trimEnd$iv$iv.subSequence(0, index$iv$iv + 1);
               return var10000.toString();
            }
         } while(0 <= var6);
      }

      var10000 = (CharSequence)"";
      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trim(@NotNull CharSequence $this$trim) {
      Intrinsics.checkNotNullParameter($this$trim, "<this>");
      CharSequence $this$trim$iv = $this$trim;
      int $i$f$trim = 0;
      int startIndex$iv = 0;
      int endIndex$iv = $this$trim.length() - 1;
      boolean startFound$iv = false;

      while(startIndex$iv <= endIndex$iv) {
         int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
         char p0 = $this$trim$iv.charAt(index$iv);
         int var8 = 0;
         p0 = CharsKt.isWhitespace(p0);
         if (!startFound$iv) {
            if (!p0) {
               startFound$iv = true;
            } else {
               ++startIndex$iv;
            }
         } else {
            if (!p0) {
               break;
            }

            --endIndex$iv;
         }
      }

      return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1);
   }

   @InlineOnly
   private static final String trim(String $this$trim) {
      Intrinsics.checkNotNullParameter($this$trim, "<this>");
      return StringsKt.trim((CharSequence)$this$trim).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart) {
      Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
      CharSequence $this$trimStart$iv = $this$trimStart;
      int $i$f$trimStart = 0;
      int var3 = 0;
      int var4 = $this$trimStart.length();

      CharSequence var10000;
      while(true) {
         if (var3 < var4) {
            int index$iv = var3++;
            char p0 = $this$trimStart$iv.charAt(index$iv);
            int var7 = 0;
            if (CharsKt.isWhitespace(p0)) {
               continue;
            }

            var10000 = $this$trimStart$iv.subSequence(index$iv, $this$trimStart$iv.length());
            break;
         }

         var10000 = (CharSequence)"";
         break;
      }

      return var10000;
   }

   @InlineOnly
   private static final String trimStart(String $this$trimStart) {
      Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
      return StringsKt.trimStart((CharSequence)$this$trimStart).toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd) {
      Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
      CharSequence $this$trimEnd$iv = $this$trimEnd;
      int $i$f$trimEnd = 0;
      int var3 = $this$trimEnd.length() + -1;
      CharSequence var10000;
      if (0 <= var3) {
         do {
            int index$iv = var3--;
            char p0 = $this$trimEnd$iv.charAt(index$iv);
            int var6 = 0;
            if (!CharsKt.isWhitespace(p0)) {
               var10000 = $this$trimEnd$iv.subSequence(0, index$iv + 1);
               return var10000;
            }
         } while(0 <= var3);
      }

      var10000 = (CharSequence)"";
      return var10000;
   }

   @InlineOnly
   private static final String trimEnd(String $this$trimEnd) {
      Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
      return StringsKt.trimEnd((CharSequence)$this$trimEnd).toString();
   }

   @NotNull
   public static final CharSequence padStart(@NotNull CharSequence $this$padStart, int length, char padChar) {
      Intrinsics.checkNotNullParameter($this$padStart, "<this>");
      if (length < 0) {
         throw new IllegalArgumentException("Desired length " + length + " is less than zero.");
      } else if (length <= $this$padStart.length()) {
         return $this$padStart.subSequence(0, $this$padStart.length());
      } else {
         StringBuilder sb = new StringBuilder(length);
         int var4 = 1;
         int var5 = length - $this$padStart.length();
         int i;
         if (var4 <= var5) {
            do {
               i = var4++;
               sb.append(padChar);
            } while(i != var5);
         }

         sb.append($this$padStart);
         return (CharSequence)sb;
      }
   }

   // $FF: synthetic method
   public static CharSequence padStart$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padStart(var0, var1, var2);
   }

   @NotNull
   public static final String padStart(@NotNull String $this$padStart, int length, char padChar) {
      Intrinsics.checkNotNullParameter($this$padStart, "<this>");
      return StringsKt.padStart((CharSequence)$this$padStart, length, padChar).toString();
   }

   // $FF: synthetic method
   public static String padStart$default(String var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padStart(var0, var1, var2);
   }

   @NotNull
   public static final CharSequence padEnd(@NotNull CharSequence $this$padEnd, int length, char padChar) {
      Intrinsics.checkNotNullParameter($this$padEnd, "<this>");
      if (length < 0) {
         throw new IllegalArgumentException("Desired length " + length + " is less than zero.");
      } else if (length <= $this$padEnd.length()) {
         return $this$padEnd.subSequence(0, $this$padEnd.length());
      } else {
         StringBuilder sb = new StringBuilder(length);
         sb.append($this$padEnd);
         int var4 = 1;
         int var5 = length - $this$padEnd.length();
         int i;
         if (var4 <= var5) {
            do {
               i = var4++;
               sb.append(padChar);
            } while(i != var5);
         }

         return (CharSequence)sb;
      }
   }

   // $FF: synthetic method
   public static CharSequence padEnd$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padEnd(var0, var1, var2);
   }

   @NotNull
   public static final String padEnd(@NotNull String $this$padEnd, int length, char padChar) {
      Intrinsics.checkNotNullParameter($this$padEnd, "<this>");
      return StringsKt.padEnd((CharSequence)$this$padEnd, length, padChar).toString();
   }

   // $FF: synthetic method
   public static String padEnd$default(String var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padEnd(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean isNullOrEmpty(CharSequence $this$isNullOrEmpty) {
      return $this$isNullOrEmpty == null || $this$isNullOrEmpty.length() == 0;
   }

   @InlineOnly
   private static final boolean isEmpty(CharSequence $this$isEmpty) {
      Intrinsics.checkNotNullParameter($this$isEmpty, "<this>");
      return $this$isEmpty.length() == 0;
   }

   @InlineOnly
   private static final boolean isNotEmpty(CharSequence $this$isNotEmpty) {
      Intrinsics.checkNotNullParameter($this$isNotEmpty, "<this>");
      return $this$isNotEmpty.length() > 0;
   }

   @InlineOnly
   private static final boolean isNotBlank(CharSequence $this$isNotBlank) {
      Intrinsics.checkNotNullParameter($this$isNotBlank, "<this>");
      return !StringsKt.isBlank($this$isNotBlank);
   }

   @InlineOnly
   private static final boolean isNullOrBlank(CharSequence $this$isNullOrBlank) {
      return $this$isNullOrBlank == null || StringsKt.isBlank($this$isNullOrBlank);
   }

   @NotNull
   public static final CharIterator iterator(@NotNull final CharSequence $this$iterator) {
      Intrinsics.checkNotNullParameter($this$iterator, "<this>");
      return new CharIterator() {
         private int index;

         public char nextChar() {
            CharSequence var10000 = $this$iterator;
            int var2 = this.index++;
            return var10000.charAt(var2);
         }

         public boolean hasNext() {
            return this.index < $this$iterator.length();
         }
      };
   }

   @InlineOnly
   private static final String orEmpty(String $this$orEmpty) {
      return $this$orEmpty == null ? "" : $this$orEmpty;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifEmpty(CharSequence $this$ifEmpty, Function0 defaultValue) {
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      return $this$ifEmpty.length() == 0 ? defaultValue.invoke() : $this$ifEmpty;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifBlank(CharSequence $this$ifBlank, Function0 defaultValue) {
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      return StringsKt.isBlank($this$ifBlank) ? defaultValue.invoke() : $this$ifBlank;
   }

   @NotNull
   public static final IntRange getIndices(@NotNull CharSequence $this$indices) {
      Intrinsics.checkNotNullParameter($this$indices, "<this>");
      return new IntRange(0, $this$indices.length() - 1);
   }

   public static final int getLastIndex(@NotNull CharSequence $this$lastIndex) {
      Intrinsics.checkNotNullParameter($this$lastIndex, "<this>");
      return $this$lastIndex.length() - 1;
   }

   public static final boolean hasSurrogatePairAt(@NotNull CharSequence $this$hasSurrogatePairAt, int index) {
      Intrinsics.checkNotNullParameter($this$hasSurrogatePairAt, "<this>");
      return (0 <= index ? index <= $this$hasSurrogatePairAt.length() - 2 : false) && Character.isHighSurrogate($this$hasSurrogatePairAt.charAt(index)) && Character.isLowSurrogate($this$hasSurrogatePairAt.charAt(index + 1));
   }

   @NotNull
   public static final String substring(@NotNull String $this$substring, @NotNull IntRange range) {
      Intrinsics.checkNotNullParameter($this$substring, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      int var3 = range.getStart();
      int var4 = range.getEndInclusive() + 1;
      String var5 = $this$substring.substring(var3, var4);
      Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String…ing(startIndex, endIndex)");
      return var5;
   }

   @NotNull
   public static final CharSequence subSequence(@NotNull CharSequence $this$subSequence, @NotNull IntRange range) {
      Intrinsics.checkNotNullParameter($this$subSequence, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      return $this$subSequence.subSequence(range.getStart(), range.getEndInclusive() + 1);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use parameters named startIndex and endIndex.",
      replaceWith = @ReplaceWith(
   expression = "subSequence(startIndex = start, endIndex = end)",
   imports = {}
)
   )
   @InlineOnly
   private static final CharSequence subSequence(String $this$subSequence, int start, int end) {
      Intrinsics.checkNotNullParameter($this$subSequence, "<this>");
      return $this$subSequence.subSequence(start, end);
   }

   @InlineOnly
   private static final String substring(CharSequence $this$substring, int startIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$substring, "<this>");
      return $this$substring.subSequence(startIndex, endIndex).toString();
   }

   // $FF: synthetic method
   static String substring$default(CharSequence $this$substring_u24default, int startIndex, int endIndex, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         endIndex = $this$substring_u24default.length();
      }

      Intrinsics.checkNotNullParameter($this$substring_u24default, "<this>");
      return $this$substring_u24default.subSequence(startIndex, endIndex).toString();
   }

   @NotNull
   public static final String substring(@NotNull CharSequence $this$substring, @NotNull IntRange range) {
      Intrinsics.checkNotNullParameter($this$substring, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      return $this$substring.subSequence(range.getStart(), range.getEndInclusive() + 1).toString();
   }

   @NotNull
   public static final String substringBefore(@NotNull String $this$substringBefore, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringBefore, "<this>");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         String var6 = $this$substringBefore.substring(var5, index);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var6;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBefore$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   @NotNull
   public static final String substringBefore(@NotNull String $this$substringBefore, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringBefore, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         String var6 = $this$substringBefore.substring(var5, index);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var6;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBefore$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfter(@NotNull String $this$substringAfter, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringAfter, "<this>");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + 1;
         int var6 = $this$substringAfter.length();
         String var7 = $this$substringAfter.substring(var5, var6);
         Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var7;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfter$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfter(@NotNull String $this$substringAfter, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringAfter, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + delimiter.length();
         int var6 = $this$substringAfter.length();
         String var7 = $this$substringAfter.substring(var5, var6);
         Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var7;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfter$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   @NotNull
   public static final String substringBeforeLast(@NotNull String $this$substringBeforeLast, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringBeforeLast, "<this>");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         String var6 = $this$substringBeforeLast.substring(var5, index);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var6;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBeforeLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringBeforeLast(@NotNull String $this$substringBeforeLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringBeforeLast, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         String var6 = $this$substringBeforeLast.substring(var5, index);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var6;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBeforeLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfterLast(@NotNull String $this$substringAfterLast, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringAfterLast, "<this>");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + 1;
         int var6 = $this$substringAfterLast.length();
         String var7 = $this$substringAfterLast.substring(var5, var6);
         Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var7;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfterLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfterLast(@NotNull String $this$substringAfterLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$substringAfterLast, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + delimiter.length();
         int var6 = $this$substringAfterLast.length();
         String var7 = $this$substringAfterLast.substring(var5, var6);
         Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String…ing(startIndex, endIndex)");
         var10000 = var7;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfterLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   @NotNull
   public static final CharSequence replaceRange(@NotNull CharSequence $this$replaceRange, int startIndex, int endIndex, @NotNull CharSequence replacement) {
      Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      if (endIndex < startIndex) {
         throw new IndexOutOfBoundsException("End index (" + endIndex + ") is less than start index (" + startIndex + ").");
      } else {
         StringBuilder sb = new StringBuilder();
         int var6 = 0;
         StringBuilder var7 = sb.append($this$replaceRange, var6, startIndex);
         Intrinsics.checkNotNullExpressionValue(var7, "this.append(value, startIndex, endIndex)");
         sb.append(replacement);
         var6 = $this$replaceRange.length();
         var7 = sb.append($this$replaceRange, endIndex, var6);
         Intrinsics.checkNotNullExpressionValue(var7, "this.append(value, startIndex, endIndex)");
         return (CharSequence)sb;
      }
   }

   @InlineOnly
   private static final String replaceRange(String $this$replaceRange, int startIndex, int endIndex, CharSequence replacement) {
      Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      return StringsKt.replaceRange((CharSequence)$this$replaceRange, startIndex, endIndex, replacement).toString();
   }

   @NotNull
   public static final CharSequence replaceRange(@NotNull CharSequence $this$replaceRange, @NotNull IntRange range, @NotNull CharSequence replacement) {
      Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      return StringsKt.replaceRange($this$replaceRange, range.getStart(), range.getEndInclusive() + 1, replacement);
   }

   @InlineOnly
   private static final String replaceRange(String $this$replaceRange, IntRange range, CharSequence replacement) {
      Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      return StringsKt.replaceRange((CharSequence)$this$replaceRange, range, replacement).toString();
   }

   @NotNull
   public static final CharSequence removeRange(@NotNull CharSequence $this$removeRange, int startIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
      if (endIndex < startIndex) {
         throw new IndexOutOfBoundsException("End index (" + endIndex + ") is less than start index (" + startIndex + ").");
      } else if (endIndex == startIndex) {
         return $this$removeRange.subSequence(0, $this$removeRange.length());
      } else {
         StringBuilder sb = new StringBuilder($this$removeRange.length() - (endIndex - startIndex));
         int var5 = 0;
         StringBuilder var6 = sb.append($this$removeRange, var5, startIndex);
         Intrinsics.checkNotNullExpressionValue(var6, "this.append(value, startIndex, endIndex)");
         var5 = $this$removeRange.length();
         var6 = sb.append($this$removeRange, endIndex, var5);
         Intrinsics.checkNotNullExpressionValue(var6, "this.append(value, startIndex, endIndex)");
         return (CharSequence)sb;
      }
   }

   @InlineOnly
   private static final String removeRange(String $this$removeRange, int startIndex, int endIndex) {
      Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
      return StringsKt.removeRange((CharSequence)$this$removeRange, startIndex, endIndex).toString();
   }

   @NotNull
   public static final CharSequence removeRange(@NotNull CharSequence $this$removeRange, @NotNull IntRange range) {
      Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      return StringsKt.removeRange($this$removeRange, range.getStart(), range.getEndInclusive() + 1);
   }

   @InlineOnly
   private static final String removeRange(String $this$removeRange, IntRange range) {
      Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      return StringsKt.removeRange((CharSequence)$this$removeRange, range).toString();
   }

   @NotNull
   public static final CharSequence removePrefix(@NotNull CharSequence $this$removePrefix, @NotNull CharSequence prefix) {
      Intrinsics.checkNotNullParameter($this$removePrefix, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      return StringsKt.startsWith$default($this$removePrefix, prefix, false, 2, (Object)null) ? $this$removePrefix.subSequence(prefix.length(), $this$removePrefix.length()) : $this$removePrefix.subSequence(0, $this$removePrefix.length());
   }

   @NotNull
   public static final String removePrefix(@NotNull String $this$removePrefix, @NotNull CharSequence prefix) {
      Intrinsics.checkNotNullParameter($this$removePrefix, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      if (StringsKt.startsWith$default((CharSequence)$this$removePrefix, prefix, false, 2, (Object)null)) {
         int var3 = prefix.length();
         String var4 = $this$removePrefix.substring(var3);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).substring(startIndex)");
         return var4;
      } else {
         return $this$removePrefix;
      }
   }

   @NotNull
   public static final CharSequence removeSuffix(@NotNull CharSequence $this$removeSuffix, @NotNull CharSequence suffix) {
      Intrinsics.checkNotNullParameter($this$removeSuffix, "<this>");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      return StringsKt.endsWith$default($this$removeSuffix, suffix, false, 2, (Object)null) ? $this$removeSuffix.subSequence(0, $this$removeSuffix.length() - suffix.length()) : $this$removeSuffix.subSequence(0, $this$removeSuffix.length());
   }

   @NotNull
   public static final String removeSuffix(@NotNull String $this$removeSuffix, @NotNull CharSequence suffix) {
      Intrinsics.checkNotNullParameter($this$removeSuffix, "<this>");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      if (StringsKt.endsWith$default((CharSequence)$this$removeSuffix, suffix, false, 2, (Object)null)) {
         byte var3 = 0;
         int var4 = $this$removeSuffix.length() - suffix.length();
         String var5 = $this$removeSuffix.substring(var3, var4);
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String…ing(startIndex, endIndex)");
         return var5;
      } else {
         return $this$removeSuffix;
      }
   }

   @NotNull
   public static final CharSequence removeSurrounding(@NotNull CharSequence $this$removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
      Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      return $this$removeSurrounding.length() >= prefix.length() + suffix.length() && StringsKt.startsWith$default($this$removeSurrounding, prefix, false, 2, (Object)null) && StringsKt.endsWith$default($this$removeSurrounding, suffix, false, 2, (Object)null) ? $this$removeSurrounding.subSequence(prefix.length(), $this$removeSurrounding.length() - suffix.length()) : $this$removeSurrounding.subSequence(0, $this$removeSurrounding.length());
   }

   @NotNull
   public static final String removeSurrounding(@NotNull String $this$removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
      Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      if ($this$removeSurrounding.length() >= prefix.length() + suffix.length() && StringsKt.startsWith$default((CharSequence)$this$removeSurrounding, prefix, false, 2, (Object)null) && StringsKt.endsWith$default((CharSequence)$this$removeSurrounding, suffix, false, 2, (Object)null)) {
         int var4 = prefix.length();
         int var5 = $this$removeSurrounding.length() - suffix.length();
         String var6 = $this$removeSurrounding.substring(var4, var5);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String…ing(startIndex, endIndex)");
         return var6;
      } else {
         return $this$removeSurrounding;
      }
   }

   @NotNull
   public static final CharSequence removeSurrounding(@NotNull CharSequence $this$removeSurrounding, @NotNull CharSequence delimiter) {
      Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      return StringsKt.removeSurrounding($this$removeSurrounding, delimiter, delimiter);
   }

   @NotNull
   public static final String removeSurrounding(@NotNull String $this$removeSurrounding, @NotNull CharSequence delimiter) {
      Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      return StringsKt.removeSurrounding($this$removeSurrounding, delimiter, delimiter);
   }

   @NotNull
   public static final String replaceBefore(@NotNull String $this$replaceBefore, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceBefore, "<this>");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBefore, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBefore$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBefore(@NotNull String $this$replaceBefore, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceBefore, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBefore, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBefore$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfter(@NotNull String $this$replaceAfter, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceAfter, "<this>");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + 1;
         int var7 = $this$replaceAfter.length();
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfter, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfter$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfter(@NotNull String $this$replaceAfter, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceAfter, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + delimiter.length();
         int var7 = $this$replaceAfter.length();
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfter, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfter$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfterLast(@NotNull String $this$replaceAfterLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceAfterLast, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + delimiter.length();
         int var7 = $this$replaceAfterLast.length();
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfterLast, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfterLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfterLast(@NotNull String $this$replaceAfterLast, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceAfterLast, "<this>");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + 1;
         int var7 = $this$replaceAfterLast.length();
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfterLast, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfterLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBeforeLast(@NotNull String $this$replaceBeforeLast, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceBeforeLast, "<this>");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBeforeLast, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBeforeLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBeforeLast(@NotNull String $this$replaceBeforeLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkNotNullParameter($this$replaceBeforeLast, "<this>");
      Intrinsics.checkNotNullParameter(delimiter, "delimiter");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBeforeLast, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBeforeLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final String replace(CharSequence $this$replace, Regex regex, String replacement) {
      Intrinsics.checkNotNullParameter($this$replace, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      return regex.replace($this$replace, replacement);
   }

   @InlineOnly
   private static final String replace(CharSequence $this$replace, Regex regex, Function1 transform) {
      Intrinsics.checkNotNullParameter($this$replace, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      Intrinsics.checkNotNullParameter(transform, "transform");
      return regex.replace($this$replace, transform);
   }

   @InlineOnly
   private static final String replaceFirst(CharSequence $this$replaceFirst, Regex regex, String replacement) {
      Intrinsics.checkNotNullParameter($this$replaceFirst, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      return regex.replaceFirst($this$replaceFirst, replacement);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @OverloadResolutionByLambdaReturnType
   @JvmName(
      name = "replaceFirstCharWithChar"
   )
   @InlineOnly
   private static final String replaceFirstCharWithChar(String $this$replaceFirstChar, Function1 transform) {
      Intrinsics.checkNotNullParameter($this$replaceFirstChar, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      String var10000;
      if (((CharSequence)$this$replaceFirstChar).length() > 0) {
         char var2 = (Character)transform.invoke($this$replaceFirstChar.charAt(0));
         byte var4 = 1;
         String var5 = $this$replaceFirstChar.substring(var4);
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).substring(startIndex)");
         var10000 = var2 + var5;
      } else {
         var10000 = $this$replaceFirstChar;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @OverloadResolutionByLambdaReturnType
   @JvmName(
      name = "replaceFirstCharWithCharSequence"
   )
   @InlineOnly
   private static final String replaceFirstCharWithCharSequence(String $this$replaceFirstChar, Function1 transform) {
      Intrinsics.checkNotNullParameter($this$replaceFirstChar, "<this>");
      Intrinsics.checkNotNullParameter(transform, "transform");
      String var5;
      if (((CharSequence)$this$replaceFirstChar).length() > 0) {
         StringBuilder var10000 = (new StringBuilder()).append(transform.invoke($this$replaceFirstChar.charAt(0)));
         byte var3 = 1;
         String var4 = $this$replaceFirstChar.substring(var3);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).substring(startIndex)");
         var5 = var10000.append(var4).toString();
      } else {
         var5 = $this$replaceFirstChar;
      }

      return var5;
   }

   @InlineOnly
   private static final boolean matches(CharSequence $this$matches, Regex regex) {
      Intrinsics.checkNotNullParameter($this$matches, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      return regex.matches($this$matches);
   }

   public static final boolean regionMatchesImpl(@NotNull CharSequence $this$regionMatchesImpl, int thisOffset, @NotNull CharSequence other, int otherOffset, int length, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$regionMatchesImpl, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      if (otherOffset >= 0 && thisOffset >= 0 && thisOffset <= $this$regionMatchesImpl.length() - length && otherOffset <= other.length() - length) {
         int var6 = 0;

         while(var6 < length) {
            int index = var6++;
            if (!CharsKt.equals($this$regionMatchesImpl.charAt(thisOffset + index), other.charAt(otherOffset + index), ignoreCase)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static final boolean startsWith(@NotNull CharSequence $this$startsWith, char var1, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
      return $this$startsWith.length() > 0 && CharsKt.equals($this$startsWith.charAt(0), var1, ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean endsWith(@NotNull CharSequence $this$endsWith, char var1, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$endsWith, "<this>");
      return $this$endsWith.length() > 0 && CharsKt.equals($this$endsWith.charAt(StringsKt.getLastIndex($this$endsWith)), var1, ignoreCase);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      return !ignoreCase && $this$startsWith instanceof String && prefix instanceof String ? StringsKt.startsWith$default((String)$this$startsWith, (String)prefix, false, 2, (Object)null) : StringsKt.regionMatchesImpl($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
      Intrinsics.checkNotNullParameter(prefix, "prefix");
      return !ignoreCase && $this$startsWith instanceof String && prefix instanceof String ? StringsKt.startsWith$default((String)$this$startsWith, (String)prefix, startIndex, false, 4, (Object)null) : StringsKt.regionMatchesImpl($this$startsWith, startIndex, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, CharSequence var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   public static final boolean endsWith(@NotNull CharSequence $this$endsWith, @NotNull CharSequence suffix, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$endsWith, "<this>");
      Intrinsics.checkNotNullParameter(suffix, "suffix");
      return !ignoreCase && $this$endsWith instanceof String && suffix instanceof String ? StringsKt.endsWith$default((String)$this$endsWith, (String)suffix, false, 2, (Object)null) : StringsKt.regionMatchesImpl($this$endsWith, $this$endsWith.length() - suffix.length(), suffix, 0, suffix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   @NotNull
   public static final String commonPrefixWith(@NotNull CharSequence $this$commonPrefixWith, @NotNull CharSequence other, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$commonPrefixWith, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      int shortestLength = Math.min($this$commonPrefixWith.length(), other.length());

      int i;
      for(i = 0; i < shortestLength && CharsKt.equals($this$commonPrefixWith.charAt(i), other.charAt(i), ignoreCase); ++i) {
      }

      if (StringsKt.hasSurrogatePairAt($this$commonPrefixWith, i - 1) || StringsKt.hasSurrogatePairAt(other, i - 1)) {
         i += -1;
      }

      return $this$commonPrefixWith.subSequence(0, i).toString();
   }

   // $FF: synthetic method
   public static String commonPrefixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonPrefixWith(var0, var1, var2);
   }

   @NotNull
   public static final String commonSuffixWith(@NotNull CharSequence $this$commonSuffixWith, @NotNull CharSequence other, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$commonSuffixWith, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      int thisLength = $this$commonSuffixWith.length();
      int otherLength = other.length();
      int shortestLength = Math.min(thisLength, otherLength);

      int i;
      for(i = 0; i < shortestLength && CharsKt.equals($this$commonSuffixWith.charAt(thisLength - i - 1), other.charAt(otherLength - i - 1), ignoreCase); ++i) {
      }

      if (StringsKt.hasSurrogatePairAt($this$commonSuffixWith, thisLength - i - 1) || StringsKt.hasSurrogatePairAt(other, otherLength - i - 1)) {
         i += -1;
      }

      return $this$commonSuffixWith.subSequence(thisLength - i, thisLength).toString();
   }

   // $FF: synthetic method
   public static String commonSuffixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonSuffixWith(var0, var1, var2);
   }

   public static final int indexOfAny(@NotNull CharSequence $this$indexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$indexOfAny, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      if (!ignoreCase && chars.length == 1 && $this$indexOfAny instanceof String) {
         char var16 = ArraysKt.single(chars);
         String var17 = (String)$this$indexOfAny;
         return var17.indexOf(var16, startIndex);
      } else {
         int var4 = RangesKt.coerceAtLeast(startIndex, 0);
         int var5 = StringsKt.getLastIndex($this$indexOfAny);
         int index;
         if (var4 <= var5) {
            do {
               index = var4++;
               char charAtIndex = $this$indexOfAny.charAt(index);
               int $i$f$any = 0;
               char[] var10 = chars;
               int var11 = 0;
               int var12 = chars.length;

               boolean var10000;
               while(true) {
                  if (var11 >= var12) {
                     var10000 = false;
                     break;
                  }

                  char element$iv = var10[var11];
                  ++var11;
                  int var15 = 0;
                  if (CharsKt.equals(element$iv, charAtIndex, ignoreCase)) {
                     var10000 = true;
                     break;
                  }
               }

               if (var10000) {
                  return index;
               }
            } while(index != var5);
         }

         return -1;
      }
   }

   // $FF: synthetic method
   public static int indexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   public static final int lastIndexOfAny(@NotNull CharSequence $this$lastIndexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$lastIndexOfAny, "<this>");
      Intrinsics.checkNotNullParameter(chars, "chars");
      if (!ignoreCase && chars.length == 1 && $this$lastIndexOfAny instanceof String) {
         char var15 = ArraysKt.single(chars);
         String var16 = (String)$this$lastIndexOfAny;
         return var16.lastIndexOf(var15, startIndex);
      } else {
         int var4 = RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$lastIndexOfAny));
         if (0 <= var4) {
            do {
               int index = var4--;
               char charAtIndex = $this$lastIndexOfAny.charAt(index);
               int $i$f$any = 0;
               char[] var9 = chars;
               int var10 = 0;
               int var11 = chars.length;

               boolean var10000;
               while(true) {
                  if (var10 >= var11) {
                     var10000 = false;
                     break;
                  }

                  char element$iv = var9[var10];
                  ++var10;
                  int var14 = 0;
                  if (CharsKt.equals(element$iv, charAtIndex, ignoreCase)) {
                     var10000 = true;
                     break;
                  }
               }

               if (var10000) {
                  return index;
               }
            } while(0 <= var4);
         }

         return -1;
      }
   }

   // $FF: synthetic method
   public static int lastIndexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   private static final int indexOf$StringsKt__StringsKt(CharSequence $this$indexOf, CharSequence other, int startIndex, int endIndex, boolean ignoreCase, boolean last) {
      IntProgression indices = !last ? (IntProgression)(new IntRange(RangesKt.coerceAtLeast(startIndex, 0), RangesKt.coerceAtMost(endIndex, $this$indexOf.length()))) : RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$indexOf)), RangesKt.coerceAtLeast(endIndex, 0));
      if ($this$indexOf instanceof String && other instanceof String) {
         int var11 = indices.getFirst();
         int var12 = indices.getLast();
         int var13 = indices.getStep();
         int index;
         if (var13 > 0 && var11 <= var12 || var13 < 0 && var12 <= var11) {
            do {
               index = var11;
               var11 += var13;
               if (StringsKt.regionMatches((String)other, 0, (String)$this$indexOf, index, other.length(), ignoreCase)) {
                  return index;
               }
            } while(index != var12);
         }
      } else {
         int var7 = indices.getFirst();
         int var8 = indices.getLast();
         int var9 = indices.getStep();
         int index;
         if (var9 > 0 && var7 <= var8 || var9 < 0 && var8 <= var7) {
            do {
               index = var7;
               var7 += var9;
               if (StringsKt.regionMatchesImpl(other, 0, $this$indexOf, index, other.length(), ignoreCase)) {
                  return index;
               }
            } while(index != var8);
         }
      }

      return -1;
   }

   // $FF: synthetic method
   static int indexOf$StringsKt__StringsKt$default(CharSequence var0, CharSequence var1, int var2, int var3, boolean var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return indexOf$StringsKt__StringsKt(var0, var1, var2, var3, var4, var5);
   }

   private static final Pair findAnyOf$StringsKt__StringsKt(CharSequence $this$findAnyOf, Collection strings, int startIndex, boolean ignoreCase, boolean last) {
      if (!ignoreCase && strings.size() == 1) {
         String string = (String)CollectionsKt.single((Iterable)strings);
         int index = !last ? StringsKt.indexOf$default($this$findAnyOf, string, startIndex, false, 4, (Object)null) : StringsKt.lastIndexOf$default($this$findAnyOf, string, startIndex, false, 4, (Object)null);
         return index < 0 ? null : TuplesKt.to(index, string);
      } else {
         IntProgression indices = !last ? (IntProgression)(new IntRange(RangesKt.coerceAtLeast(startIndex, 0), $this$findAnyOf.length())) : RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$findAnyOf)), 0);
         if ($this$findAnyOf instanceof String) {
            int index = indices.getFirst();
            int var7 = indices.getLast();
            int var8 = indices.getStep();
            int index;
            if (var8 > 0 && index <= var7 || var8 < 0 && var7 <= index) {
               do {
                  index = index;
                  index += var8;
                  Iterable $this$firstOrNull$iv = (Iterable)strings;
                  int $i$f$firstOrNull = 0;
                  Iterator var13 = $this$firstOrNull$iv.iterator();

                  Object var10000;
                  while(true) {
                     if (!var13.hasNext()) {
                        var10000 = null;
                        break;
                     }

                     Object element$iv = var13.next();
                     String it = (String)element$iv;
                     int var16 = 0;
                     if (StringsKt.regionMatches(it, 0, (String)$this$findAnyOf, index, it.length(), ignoreCase)) {
                        var10000 = element$iv;
                        break;
                     }
                  }

                  String matchingString = (String)var10000;
                  if (matchingString != null) {
                     return TuplesKt.to(index, matchingString);
                  }
               } while(index != var7);
            }
         } else {
            int var18 = indices.getFirst();
            int var20 = indices.getLast();
            int var21 = indices.getStep();
            int index;
            if (var21 > 0 && var18 <= var20 || var21 < 0 && var20 <= var18) {
               do {
                  index = var18;
                  var18 += var21;
                  Iterable $this$firstOrNull$iv = (Iterable)strings;
                  int $i$f$firstOrNull = 0;
                  Iterator var26 = $this$firstOrNull$iv.iterator();

                  Object var30;
                  while(true) {
                     if (!var26.hasNext()) {
                        var30 = null;
                        break;
                     }

                     Object element$iv = var26.next();
                     String it = (String)element$iv;
                     int var29 = 0;
                     if (StringsKt.regionMatchesImpl((CharSequence)it, 0, $this$findAnyOf, index, it.length(), ignoreCase)) {
                        var30 = element$iv;
                        break;
                     }
                  }

                  String matchingString = (String)var30;
                  if (matchingString != null) {
                     return TuplesKt.to(index, matchingString);
                  }
               } while(index != var20);
            }
         }

         return null;
      }
   }

   @Nullable
   public static final Pair findAnyOf(@NotNull CharSequence $this$findAnyOf, @NotNull Collection strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$findAnyOf, "<this>");
      Intrinsics.checkNotNullParameter(strings, "strings");
      return findAnyOf$StringsKt__StringsKt($this$findAnyOf, strings, startIndex, ignoreCase, false);
   }

   // $FF: synthetic method
   public static Pair findAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findAnyOf(var0, var1, var2, var3);
   }

   @Nullable
   public static final Pair findLastAnyOf(@NotNull CharSequence $this$findLastAnyOf, @NotNull Collection strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$findLastAnyOf, "<this>");
      Intrinsics.checkNotNullParameter(strings, "strings");
      return findAnyOf$StringsKt__StringsKt($this$findLastAnyOf, strings, startIndex, ignoreCase, true);
   }

   // $FF: synthetic method
   public static Pair findLastAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findLastAnyOf(var0, var1, var2, var3);
   }

   public static final int indexOfAny(@NotNull CharSequence $this$indexOfAny, @NotNull Collection strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$indexOfAny, "<this>");
      Intrinsics.checkNotNullParameter(strings, "strings");
      Pair var4 = findAnyOf$StringsKt__StringsKt($this$indexOfAny, strings, startIndex, ignoreCase, false);
      return var4 == null ? -1 : ((Number)var4.getFirst()).intValue();
   }

   // $FF: synthetic method
   public static int indexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   public static final int lastIndexOfAny(@NotNull CharSequence $this$lastIndexOfAny, @NotNull Collection strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$lastIndexOfAny, "<this>");
      Intrinsics.checkNotNullParameter(strings, "strings");
      Pair var4 = findAnyOf$StringsKt__StringsKt($this$lastIndexOfAny, strings, startIndex, ignoreCase, true);
      return var4 == null ? -1 : ((Number)var4.getFirst()).intValue();
   }

   // $FF: synthetic method
   public static int lastIndexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   public static final int indexOf(@NotNull CharSequence $this$indexOf, char var1, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$indexOf, "<this>");
      int var10000;
      if (!ignoreCase && $this$indexOf instanceof String) {
         String var5 = (String)$this$indexOf;
         var10000 = var5.indexOf(var1, startIndex);
      } else {
         char[] var4 = new char[]{var1};
         var10000 = StringsKt.indexOfAny($this$indexOf, var4, startIndex, ignoreCase);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int indexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   public static final int indexOf(@NotNull CharSequence $this$indexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$indexOf, "<this>");
      Intrinsics.checkNotNullParameter(string, "string");
      int var10000;
      if (!ignoreCase && $this$indexOf instanceof String) {
         String var4 = (String)$this$indexOf;
         var10000 = var4.indexOf(string, startIndex);
      } else {
         var10000 = indexOf$StringsKt__StringsKt$default($this$indexOf, (CharSequence)string, startIndex, $this$indexOf.length(), ignoreCase, false, 16, (Object)null);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int indexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   public static final int lastIndexOf(@NotNull CharSequence $this$lastIndexOf, char var1, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$lastIndexOf, "<this>");
      int var10000;
      if (!ignoreCase && $this$lastIndexOf instanceof String) {
         String var5 = (String)$this$lastIndexOf;
         var10000 = var5.lastIndexOf(var1, startIndex);
      } else {
         char[] var4 = new char[]{var1};
         var10000 = StringsKt.lastIndexOfAny($this$lastIndexOf, var4, startIndex, ignoreCase);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   public static final int lastIndexOf(@NotNull CharSequence $this$lastIndexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$lastIndexOf, "<this>");
      Intrinsics.checkNotNullParameter(string, "string");
      int var10000;
      if (!ignoreCase && $this$lastIndexOf instanceof String) {
         String var4 = (String)$this$lastIndexOf;
         var10000 = var4.lastIndexOf(string, startIndex);
      } else {
         var10000 = indexOf$StringsKt__StringsKt($this$lastIndexOf, (CharSequence)string, startIndex, 0, ignoreCase, true);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   public static final boolean contains(@NotNull CharSequence $this$contains, @NotNull CharSequence other, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      return other instanceof String ? StringsKt.indexOf$default($this$contains, (String)other, 0, ignoreCase, 2, (Object)null) >= 0 : indexOf$StringsKt__StringsKt$default($this$contains, other, 0, $this$contains.length(), ignoreCase, false, 16, (Object)null) >= 0;
   }

   // $FF: synthetic method
   public static boolean contains$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   public static final boolean contains(@NotNull CharSequence $this$contains, char var1, boolean ignoreCase) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      return StringsKt.indexOf$default($this$contains, var1, 0, ignoreCase, 2, (Object)null) >= 0;
   }

   // $FF: synthetic method
   public static boolean contains$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean contains(CharSequence $this$contains, Regex regex) {
      Intrinsics.checkNotNullParameter($this$contains, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      return regex.containsMatchIn($this$contains);
   }

   private static final Sequence rangesDelimitedBy$StringsKt__StringsKt(CharSequence $this$rangesDelimitedBy, final char[] delimiters, int startIndex, final boolean ignoreCase, int limit) {
      StringsKt.requireNonNegativeLimit(limit);
      return new DelimitedRangesSequence($this$rangesDelimitedBy, startIndex, limit, new Function2() {
         @Nullable
         public final Pair invoke(@NotNull CharSequence $this$$receiver, int currentIndex) {
            Intrinsics.checkNotNullParameter($this$$receiver, "$this$$receiver");
            int it = StringsKt.indexOfAny($this$$receiver, delimiters, currentIndex, ignoreCase);
            int var5 = 0;
            return it < 0 ? null : TuplesKt.to(it, 1);
         }
      });
   }

   // $FF: synthetic method
   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   private static final Sequence rangesDelimitedBy$StringsKt__StringsKt(CharSequence $this$rangesDelimitedBy, String[] delimiters, int startIndex, final boolean ignoreCase, int limit) {
      StringsKt.requireNonNegativeLimit(limit);
      final List delimitersList = ArraysKt.asList(delimiters);
      return new DelimitedRangesSequence($this$rangesDelimitedBy, startIndex, limit, new Function2() {
         @Nullable
         public final Pair invoke(@NotNull CharSequence $this$$receiver, int currentIndex) {
            Intrinsics.checkNotNullParameter($this$$receiver, "$this$$receiver");
            Pair it = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt($this$$receiver, (Collection)delimitersList, currentIndex, ignoreCase, false);
            Pair var10000;
            if (it == null) {
               var10000 = null;
            } else {
               int var6 = 0;
               var10000 = TuplesKt.to(it.getFirst(), ((String)it.getSecond()).length());
            }

            return var10000;
         }
      });
   }

   // $FF: synthetic method
   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, String[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   public static final void requireNonNegativeLimit(int limit) {
      boolean var1 = limit >= 0;
      if (!var1) {
         int var2 = 0;
         String var3 = Intrinsics.stringPlus("Limit must be non-negative, but was ", limit);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @NotNull
   public static final Sequence splitToSequence(@NotNull final CharSequence $this$splitToSequence, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkNotNullParameter($this$splitToSequence, "<this>");
      Intrinsics.checkNotNullParameter(delimiters, "delimiters");
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default($this$splitToSequence, (String[])delimiters, 0, ignoreCase, limit, 2, (Object)null), new Function1() {
         @NotNull
         public final String invoke(@NotNull IntRange it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return StringsKt.substring($this$splitToSequence, it);
         }
      });
   }

   // $FF: synthetic method
   public static Sequence splitToSequence$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   @NotNull
   public static final List split(@NotNull CharSequence $this$split, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkNotNullParameter($this$split, "<this>");
      Intrinsics.checkNotNullParameter(delimiters, "delimiters");
      if (delimiters.length == 1) {
         String delimiter = delimiters[0];
         if (((CharSequence)delimiter).length() != 0) {
            return split$StringsKt__StringsKt($this$split, delimiter, ignoreCase, limit);
         }
      }

      Iterable $this$map$iv = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default($this$split, (String[])delimiters, 0, ignoreCase, limit, 2, (Object)null));
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         IntRange it = (IntRange)item$iv$iv;
         int var12 = 0;
         String var14 = StringsKt.substring($this$split, it);
         destination$iv$iv.add(var14);
      }

      return (List)destination$iv$iv;
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   @NotNull
   public static final Sequence splitToSequence(@NotNull final CharSequence $this$splitToSequence, @NotNull char[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkNotNullParameter($this$splitToSequence, "<this>");
      Intrinsics.checkNotNullParameter(delimiters, "delimiters");
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default($this$splitToSequence, (char[])delimiters, 0, ignoreCase, limit, 2, (Object)null), new Function1() {
         @NotNull
         public final String invoke(@NotNull IntRange it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return StringsKt.substring($this$splitToSequence, it);
         }
      });
   }

   // $FF: synthetic method
   public static Sequence splitToSequence$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   @NotNull
   public static final List split(@NotNull CharSequence $this$split, @NotNull char[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkNotNullParameter($this$split, "<this>");
      Intrinsics.checkNotNullParameter(delimiters, "delimiters");
      if (delimiters.length == 1) {
         return split$StringsKt__StringsKt($this$split, String.valueOf(delimiters[0]), ignoreCase, limit);
      } else {
         Iterable $this$map$iv = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default($this$split, (char[])delimiters, 0, ignoreCase, limit, 2, (Object)null));
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            IntRange it = (IntRange)item$iv$iv;
            int var12 = 0;
            String var14 = StringsKt.substring($this$split, it);
            destination$iv$iv.add(var14);
         }

         return (List)destination$iv$iv;
      }
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   private static final List split$StringsKt__StringsKt(CharSequence $this$split, String delimiter, boolean ignoreCase, int limit) {
      StringsKt.requireNonNegativeLimit(limit);
      int currentOffset = 0;
      int nextIndex = StringsKt.indexOf($this$split, delimiter, currentOffset, ignoreCase);
      if (nextIndex != -1 && limit != 1) {
         boolean isLimited = limit > 0;
         ArrayList result = new ArrayList(isLimited ? RangesKt.coerceAtMost(limit, 10) : 10);

         do {
            result.add($this$split.subSequence(currentOffset, nextIndex).toString());
            currentOffset = nextIndex + delimiter.length();
            if (isLimited && result.size() == limit - 1) {
               break;
            }

            nextIndex = StringsKt.indexOf($this$split, delimiter, currentOffset, ignoreCase);
         } while(nextIndex != -1);

         int var9 = $this$split.length();
         result.add($this$split.subSequence(currentOffset, var9).toString());
         return (List)result;
      } else {
         return CollectionsKt.listOf($this$split.toString());
      }
   }

   @InlineOnly
   private static final List split(CharSequence $this$split, Regex regex, int limit) {
      Intrinsics.checkNotNullParameter($this$split, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      return regex.split($this$split, limit);
   }

   // $FF: synthetic method
   static List split$default(CharSequence $this$split_u24default, Regex regex, int limit, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         limit = 0;
      }

      Intrinsics.checkNotNullParameter($this$split_u24default, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      return regex.split($this$split_u24default, limit);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final Sequence splitToSequence(CharSequence $this$splitToSequence, Regex regex, int limit) {
      Intrinsics.checkNotNullParameter($this$splitToSequence, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      return regex.splitToSequence($this$splitToSequence, limit);
   }

   // $FF: synthetic method
   static Sequence splitToSequence$default(CharSequence $this$splitToSequence_u24default, Regex regex, int limit, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         limit = 0;
      }

      Intrinsics.checkNotNullParameter($this$splitToSequence_u24default, "<this>");
      Intrinsics.checkNotNullParameter(regex, "regex");
      return regex.splitToSequence($this$splitToSequence_u24default, limit);
   }

   @NotNull
   public static final Sequence lineSequence(@NotNull CharSequence $this$lineSequence) {
      Intrinsics.checkNotNullParameter($this$lineSequence, "<this>");
      String[] var1 = new String[]{"\r\n", "\n", "\r"};
      return StringsKt.splitToSequence$default($this$lineSequence, var1, false, 0, 6, (Object)null);
   }

   @NotNull
   public static final List lines(@NotNull CharSequence $this$lines) {
      Intrinsics.checkNotNullParameter($this$lines, "<this>");
      return SequencesKt.toList(StringsKt.lineSequence($this$lines));
   }

   public static final boolean contentEqualsIgnoreCaseImpl(@Nullable CharSequence $this$contentEqualsIgnoreCaseImpl, @Nullable CharSequence other) {
      if ($this$contentEqualsIgnoreCaseImpl instanceof String && other instanceof String) {
         return StringsKt.equals((String)$this$contentEqualsIgnoreCaseImpl, (String)other, true);
      } else if ($this$contentEqualsIgnoreCaseImpl == other) {
         return true;
      } else if ($this$contentEqualsIgnoreCaseImpl != null && other != null && $this$contentEqualsIgnoreCaseImpl.length() == other.length()) {
         int var2 = 0;
         int var3 = $this$contentEqualsIgnoreCaseImpl.length();

         while(var2 < var3) {
            int i = var2++;
            if (!CharsKt.equals($this$contentEqualsIgnoreCaseImpl.charAt(i), other.charAt(i), true)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static final boolean contentEqualsImpl(@Nullable CharSequence $this$contentEqualsImpl, @Nullable CharSequence other) {
      if ($this$contentEqualsImpl instanceof String && other instanceof String) {
         return Intrinsics.areEqual((Object)$this$contentEqualsImpl, (Object)other);
      } else if ($this$contentEqualsImpl == other) {
         return true;
      } else if ($this$contentEqualsImpl != null && other != null && $this$contentEqualsImpl.length() == other.length()) {
         int var2 = 0;
         int var3 = $this$contentEqualsImpl.length();

         while(var2 < var3) {
            int i = var2++;
            if ($this$contentEqualsImpl.charAt(i) != other.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   public static final boolean toBooleanStrict(@NotNull String $this$toBooleanStrict) {
      Intrinsics.checkNotNullParameter($this$toBooleanStrict, "<this>");
      boolean var10000;
      if (Intrinsics.areEqual((Object)$this$toBooleanStrict, (Object)"true")) {
         var10000 = true;
      } else {
         if (!Intrinsics.areEqual((Object)$this$toBooleanStrict, (Object)"false")) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("The string doesn't represent a boolean value: ", $this$toBooleanStrict));
         }

         var10000 = false;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @Nullable
   public static final Boolean toBooleanStrictOrNull(@NotNull String $this$toBooleanStrictOrNull) {
      Intrinsics.checkNotNullParameter($this$toBooleanStrictOrNull, "<this>");
      return Intrinsics.areEqual((Object)$this$toBooleanStrictOrNull, (Object)"true") ? true : (Intrinsics.areEqual((Object)$this$toBooleanStrictOrNull, (Object)"false") ? false : null);
   }

   public StringsKt__StringsKt() {
   }
}
