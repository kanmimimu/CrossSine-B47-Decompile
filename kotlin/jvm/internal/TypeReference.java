package kotlin.jvm.internal;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 )2\u00020\u0001:\u0001)B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0010\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\bH\u0002J\u0013\u0010$\u001a\u00020\b2\b\u0010%\u001a\u0004\u0018\u00010&H\u0096\u0002J\b\u0010'\u001a\u00020\fH\u0016J\b\u0010(\u001a\u00020\u001eH\u0016J\f\u0010\"\u001a\u00020\u001e*\u00020\u0006H\u0002R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u000b\u001a\u00020\f8\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0019R\u001e\u0010\n\u001a\u0004\u0018\u00010\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\u0016\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u001f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b \u0010!¨\u0006*"},
   d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "platformTypeUpperBound", "flags", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Lkotlin/reflect/KType;I)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "getFlags$kotlin_stdlib$annotations", "()V", "getFlags$kotlin_stdlib", "()I", "()Z", "getPlatformTypeUpperBound$kotlin_stdlib$annotations", "getPlatformTypeUpperBound$kotlin_stdlib", "()Lkotlin/reflect/KType;", "arrayClassName", "", "Ljava/lang/Class;", "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "convertPrimitiveToWrapper", "equals", "other", "", "hashCode", "toString", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.4"
)
public final class TypeReference implements KType {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final KClassifier classifier;
   @NotNull
   private final List arguments;
   @Nullable
   private final KType platformTypeUpperBound;
   private final int flags;
   public static final int IS_MARKED_NULLABLE = 1;
   public static final int IS_MUTABLE_COLLECTION_TYPE = 2;
   public static final int IS_NOTHING_TYPE = 4;

   @SinceKotlin(
      version = "1.6"
   )
   public TypeReference(@NotNull KClassifier classifier, @NotNull List arguments, @Nullable KType platformTypeUpperBound, int flags) {
      Intrinsics.checkNotNullParameter(classifier, "classifier");
      Intrinsics.checkNotNullParameter(arguments, "arguments");
      super();
      this.classifier = classifier;
      this.arguments = arguments;
      this.platformTypeUpperBound = platformTypeUpperBound;
      this.flags = flags;
   }

   @NotNull
   public KClassifier getClassifier() {
      return this.classifier;
   }

   @NotNull
   public List getArguments() {
      return this.arguments;
   }

   @Nullable
   public final KType getPlatformTypeUpperBound$kotlin_stdlib() {
      return this.platformTypeUpperBound;
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.6"
   )
   public static void getPlatformTypeUpperBound$kotlin_stdlib$annotations() {
   }

   public final int getFlags$kotlin_stdlib() {
      return this.flags;
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.6"
   )
   public static void getFlags$kotlin_stdlib$annotations() {
   }

   public TypeReference(@NotNull KClassifier classifier, @NotNull List arguments, boolean isMarkedNullable) {
      Intrinsics.checkNotNullParameter(classifier, "classifier");
      Intrinsics.checkNotNullParameter(arguments, "arguments");
      this(classifier, arguments, (KType)null, isMarkedNullable ? 1 : 0);
   }

   @NotNull
   public List getAnnotations() {
      return CollectionsKt.emptyList();
   }

   public boolean isMarkedNullable() {
      return (this.flags & 1) != 0;
   }

   public boolean equals(@Nullable Object other) {
      return other instanceof TypeReference && Intrinsics.areEqual((Object)this.getClassifier(), (Object)((TypeReference)other).getClassifier()) && Intrinsics.areEqual((Object)this.getArguments(), (Object)((TypeReference)other).getArguments()) && Intrinsics.areEqual((Object)this.platformTypeUpperBound, (Object)((TypeReference)other).platformTypeUpperBound) && this.flags == ((TypeReference)other).flags;
   }

   public int hashCode() {
      return (this.getClassifier().hashCode() * 31 + this.getArguments().hashCode()) * 31 + Integer.valueOf(this.flags).hashCode();
   }

   @NotNull
   public String toString() {
      return Intrinsics.stringPlus(this.asString(false), " (Kotlin reflection is not available)");
   }

   private final String asString(boolean convertPrimitiveToWrapper) {
      KClassifier var4 = this.getClassifier();
      KClass var3 = var4 instanceof KClass ? (KClass)var4 : null;
      Class javaClass = var3 == null ? null : JvmClassMappingKt.getJavaClass(var3);
      String klass = javaClass == null ? this.getClassifier().toString() : ((this.flags & 4) != 0 ? "kotlin.Nothing" : (javaClass.isArray() ? this.getArrayClassName(javaClass) : (convertPrimitiveToWrapper && javaClass.isPrimitive() ? JvmClassMappingKt.getJavaObjectType((KClass)this.getClassifier()).getName() : javaClass.getName())));
      String args = this.getArguments().isEmpty() ? "" : CollectionsKt.joinToString$default((Iterable)this.getArguments(), (CharSequence)", ", (CharSequence)"<", (CharSequence)">", 0, (CharSequence)null, new Function1() {
         @NotNull
         public final CharSequence invoke(@NotNull KTypeProjection it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return (CharSequence)TypeReference.this.asString(it);
         }
      }, 24, (Object)null);
      String nullable = this.isMarkedNullable() ? "?" : "";
      String result = klass + args + nullable;
      KType upper = this.platformTypeUpperBound;
      String var10000;
      if (upper instanceof TypeReference) {
         String renderedUpper = ((TypeReference)upper).asString(true);
         var10000 = Intrinsics.areEqual((Object)renderedUpper, (Object)result) ? result : (Intrinsics.areEqual((Object)renderedUpper, (Object)Intrinsics.stringPlus(result, "?")) ? Intrinsics.stringPlus(result, "!") : '(' + result + ".." + renderedUpper + ')');
      } else {
         var10000 = result;
      }

      return var10000;
   }

   private final String getArrayClassName(Class $this$arrayClassName) {
      return Intrinsics.areEqual((Object)$this$arrayClassName, (Object)boolean[].class) ? "kotlin.BooleanArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)char[].class) ? "kotlin.CharArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)byte[].class) ? "kotlin.ByteArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)short[].class) ? "kotlin.ShortArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)int[].class) ? "kotlin.IntArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)float[].class) ? "kotlin.FloatArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)long[].class) ? "kotlin.LongArray" : (Intrinsics.areEqual((Object)$this$arrayClassName, (Object)double[].class) ? "kotlin.DoubleArray" : "kotlin.Array")))))));
   }

   private final String asString(KTypeProjection $this$asString) {
      if ($this$asString.getVariance() == null) {
         return "*";
      } else {
         KType var4 = $this$asString.getType();
         TypeReference var3 = var4 instanceof TypeReference ? (TypeReference)var4 : null;
         String typeString = var3 == null ? String.valueOf($this$asString.getType()) : var3.asString(true);
         KVariance var5 = $this$asString.getVariance();
         int var6 = TypeReference.WhenMappings.$EnumSwitchMapping$0[var5.ordinal()];
         String var10000;
         switch (var6) {
            case 1:
               var10000 = typeString;
               break;
            case 2:
               var10000 = Intrinsics.stringPlus("in ", typeString);
               break;
            case 3:
               var10000 = Intrinsics.stringPlus("out ", typeString);
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         return var10000;
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0007"},
      d2 = {"Lkotlin/jvm/internal/TypeReference$Companion;", "", "()V", "IS_MARKED_NULLABLE", "", "IS_MUTABLE_COLLECTION_TYPE", "IS_NOTHING_TYPE", "kotlin-stdlib"}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
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
         int[] var0 = new int[KVariance.values().length];
         var0[KVariance.INVARIANT.ordinal()] = 1;
         var0[KVariance.IN.ordinal()] = 2;
         var0[KVariance.OUT.ordinal()] = 3;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
