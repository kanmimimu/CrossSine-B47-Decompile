package kotlin.jvm.internal;

import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u0018\u001a\u00020\t2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u001d2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\fJ\b\u0010\u001e\u001a\u00020\u0005H\u0016R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006 "},
   d2 = {"Lkotlin/jvm/internal/TypeParameterReference;", "Lkotlin/reflect/KTypeParameter;", "container", "", "name", "", "variance", "Lkotlin/reflect/KVariance;", "isReified", "", "(Ljava/lang/Object;Ljava/lang/String;Lkotlin/reflect/KVariance;Z)V", "bounds", "", "Lkotlin/reflect/KType;", "()Z", "getName", "()Ljava/lang/String;", "upperBounds", "getUpperBounds$annotations", "()V", "getUpperBounds", "()Ljava/util/List;", "getVariance", "()Lkotlin/reflect/KVariance;", "equals", "other", "hashCode", "", "setUpperBounds", "", "toString", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.4"
)
public final class TypeParameterReference implements KTypeParameter {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @Nullable
   private final Object container;
   @NotNull
   private final String name;
   @NotNull
   private final KVariance variance;
   private final boolean isReified;
   @Nullable
   private volatile List bounds;

   public TypeParameterReference(@Nullable Object container, @NotNull String name, @NotNull KVariance variance, boolean isReified) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(variance, "variance");
      super();
      this.container = container;
      this.name = name;
      this.variance = variance;
      this.isReified = isReified;
   }

   @NotNull
   public String getName() {
      return this.name;
   }

   @NotNull
   public KVariance getVariance() {
      return this.variance;
   }

   public boolean isReified() {
      return this.isReified;
   }

   @NotNull
   public List getUpperBounds() {
      List var1 = this.bounds;
      List var10000;
      if (var1 == null) {
         List it = CollectionsKt.listOf(Reflection.nullableTypeOf(Object.class));
         int var4 = 0;
         this.bounds = it;
         var10000 = it;
      } else {
         var10000 = var1;
      }

      return var10000;
   }

   /** @deprecated */
   // $FF: synthetic method
   public static void getUpperBounds$annotations() {
   }

   public final void setUpperBounds(@NotNull List upperBounds) {
      Intrinsics.checkNotNullParameter(upperBounds, "upperBounds");
      if (this.bounds != null) {
         throw new IllegalStateException(("Upper bounds of type parameter '" + this + "' have already been initialized.").toString());
      } else {
         this.bounds = upperBounds;
      }
   }

   public boolean equals(@Nullable Object other) {
      return other instanceof TypeParameterReference && Intrinsics.areEqual(this.container, ((TypeParameterReference)other).container) && Intrinsics.areEqual((Object)this.getName(), (Object)((TypeParameterReference)other).getName());
   }

   public int hashCode() {
      Object var1 = this.container;
      return (var1 == null ? 0 : var1.hashCode()) * 31 + this.getName().hashCode();
   }

   @NotNull
   public String toString() {
      return Companion.toString(this);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lkotlin/jvm/internal/TypeParameterReference$Companion;", "", "()V", "toString", "", "typeParameter", "Lkotlin/reflect/KTypeParameter;", "kotlin-stdlib"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final String toString(@NotNull KTypeParameter typeParameter) {
         Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
         StringBuilder var2 = new StringBuilder();
         int var4 = 0;
         KVariance var5 = typeParameter.getVariance();
         int var6 = TypeParameterReference.Companion.WhenMappings.$EnumSwitchMapping$0[var5.ordinal()];
         switch (var6) {
            case 1:
            default:
               break;
            case 2:
               var2.append("in ");
               break;
            case 3:
               var2.append("out ");
         }

         var2.append(typeParameter.getName());
         String var7 = var2.toString();
         Intrinsics.checkNotNullExpressionValue(var7, "StringBuilder().apply(builderAction).toString()");
         return var7;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
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
}
