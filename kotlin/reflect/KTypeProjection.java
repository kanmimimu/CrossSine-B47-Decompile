package kotlin.reflect;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"},
   d2 = {"Lkotlin/reflect/KTypeProjection;", "", "variance", "Lkotlin/reflect/KVariance;", "type", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KVariance;Lkotlin/reflect/KType;)V", "getType", "()Lkotlin/reflect/KType;", "getVariance", "()Lkotlin/reflect/KVariance;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public final class KTypeProjection {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @Nullable
   private final KVariance variance;
   @Nullable
   private final KType type;
   @JvmField
   @NotNull
   public static final KTypeProjection star = new KTypeProjection((KVariance)null, (KType)null);

   public KTypeProjection(@Nullable KVariance variance, @Nullable KType type) {
      this.variance = variance;
      this.type = type;
      boolean var3 = this.variance == null == (this.type == null);
      if (!var3) {
         int var4 = 0;
         String var5 = this.getVariance() == null ? "Star projection must have no type specified." : "The projection variance " + this.getVariance() + " requires type to be specified.";
         throw new IllegalArgumentException(var5.toString());
      }
   }

   @Nullable
   public final KVariance getVariance() {
      return this.variance;
   }

   @Nullable
   public final KType getType() {
      return this.type;
   }

   @NotNull
   public String toString() {
      KVariance var1 = this.variance;
      int var2 = var1 == null ? -1 : KTypeProjection.WhenMappings.$EnumSwitchMapping$0[var1.ordinal()];
      String var10000;
      switch (var2) {
         case -1:
            var10000 = "*";
            break;
         case 0:
         default:
            throw new NoWhenBranchMatchedException();
         case 1:
            var10000 = String.valueOf(this.type);
            break;
         case 2:
            var10000 = Intrinsics.stringPlus("in ", this.type);
            break;
         case 3:
            var10000 = Intrinsics.stringPlus("out ", this.type);
      }

      return var10000;
   }

   @Nullable
   public final KVariance component1() {
      return this.variance;
   }

   @Nullable
   public final KType component2() {
      return this.type;
   }

   @NotNull
   public final KTypeProjection copy(@Nullable KVariance variance, @Nullable KType type) {
      return new KTypeProjection(variance, type);
   }

   // $FF: synthetic method
   public static KTypeProjection copy$default(KTypeProjection var0, KVariance var1, KType var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.variance;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.type;
      }

      return var0.copy(var1, var2);
   }

   public int hashCode() {
      int result = this.variance == null ? 0 : this.variance.hashCode();
      result = result * 31 + (this.type == null ? 0 : this.type.hashCode());
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof KTypeProjection)) {
         return false;
      } else {
         KTypeProjection var2 = (KTypeProjection)other;
         if (this.variance != var2.variance) {
            return false;
         } else {
            return Intrinsics.areEqual((Object)this.type, (Object)var2.type);
         }
      }
   }

   @JvmStatic
   @NotNull
   public static final KTypeProjection invariant(@NotNull KType type) {
      return Companion.invariant(type);
   }

   @JvmStatic
   @NotNull
   public static final KTypeProjection contravariant(@NotNull KType type) {
      return Companion.contravariant(type);
   }

   @JvmStatic
   @NotNull
   public static final KTypeProjection covariant(@NotNull KType type) {
      return Companion.covariant(type);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u00020\u00048\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0002¨\u0006\u000e"},
      d2 = {"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "star", "getStar$annotations", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"}
   )
   public static final class Companion {
      private Companion() {
      }

      /** @deprecated */
      // $FF: synthetic method
      @PublishedApi
      public static void getStar$annotations() {
      }

      @NotNull
      public final KTypeProjection getSTAR() {
         return KTypeProjection.star;
      }

      @JvmStatic
      @NotNull
      public final KTypeProjection invariant(@NotNull KType type) {
         Intrinsics.checkNotNullParameter(type, "type");
         return new KTypeProjection(KVariance.INVARIANT, type);
      }

      @JvmStatic
      @NotNull
      public final KTypeProjection contravariant(@NotNull KType type) {
         Intrinsics.checkNotNullParameter(type, "type");
         return new KTypeProjection(KVariance.IN, type);
      }

      @JvmStatic
      @NotNull
      public final KTypeProjection covariant(@NotNull KType type) {
         Intrinsics.checkNotNullParameter(type, "type");
         return new KTypeProjection(KVariance.OUT, type);
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
