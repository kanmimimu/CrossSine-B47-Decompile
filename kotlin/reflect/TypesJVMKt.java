package kotlin.reflect;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.KTypeBase;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a\"\u0010\n\u001a\u00020\u00012\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0003\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0001H\u0002\u001a\u0016\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0003\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00078BX\u0083\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\b\u001a\u0004\b\u0005\u0010\t¨\u0006\u0015"},
   d2 = {"javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType$annotations", "(Lkotlin/reflect/KType;)V", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "Lkotlin/reflect/KTypeProjection;", "(Lkotlin/reflect/KTypeProjection;)V", "(Lkotlin/reflect/KTypeProjection;)Ljava/lang/reflect/Type;", "createPossiblyInnerType", "jClass", "Ljava/lang/Class;", "arguments", "", "typeToString", "", "type", "computeJavaType", "forceWrapper", "", "kotlin-stdlib"}
)
public final class TypesJVMKt {
   @NotNull
   public static final Type getJavaType(@NotNull KType $this$javaType) {
      Intrinsics.checkNotNullParameter($this$javaType, "<this>");
      if ($this$javaType instanceof KTypeBase) {
         Type it = ((KTypeBase)$this$javaType).getJavaType();
         if (it != null) {
            int var4 = 0;
            return it;
         }
      }

      return computeJavaType$default($this$javaType, false, 1, (Object)null);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalStdlibApi
   @LowPriorityInOverloadResolution
   public static void getJavaType$annotations(KType var0) {
   }

   @ExperimentalStdlibApi
   private static final Type computeJavaType(KType $this$computeJavaType, boolean forceWrapper) {
      KClassifier classifier = $this$computeJavaType.getClassifier();
      if (classifier instanceof KTypeParameter) {
         return new TypeVariableImpl((KTypeParameter)classifier);
      } else if (classifier instanceof KClass) {
         Class jClass = forceWrapper ? JvmClassMappingKt.getJavaObjectType((KClass)classifier) : JvmClassMappingKt.getJavaClass((KClass)classifier);
         List arguments = $this$computeJavaType.getArguments();
         if (arguments.isEmpty()) {
            return (Type)jClass;
         } else if (jClass.isArray()) {
            if (jClass.getComponentType().isPrimitive()) {
               return (Type)jClass;
            } else {
               KTypeProjection var6 = (KTypeProjection)CollectionsKt.singleOrNull(arguments);
               if (var6 == null) {
                  throw new IllegalArgumentException(Intrinsics.stringPlus("kotlin.Array must have exactly one type argument: ", $this$computeJavaType));
               } else {
                  KTypeProjection var5 = var6;
                  KVariance variance = var6.component1();
                  KType elementType = var5.component2();
                  int var9 = variance == null ? -1 : TypesJVMKt.WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
                  Type var10000;
                  switch (var9) {
                     case -1:
                     case 1:
                        var10000 = (Type)jClass;
                        break;
                     case 0:
                     default:
                        throw new NoWhenBranchMatchedException();
                     case 2:
                     case 3:
                        Intrinsics.checkNotNull(elementType);
                        Type javaElementType = computeJavaType$default(elementType, false, 1, (Object)null);
                        var10000 = javaElementType instanceof Class ? (Type)jClass : (Type)(new GenericArrayTypeImpl(javaElementType));
                  }

                  return var10000;
               }
            }
         } else {
            return createPossiblyInnerType(jClass, arguments);
         }
      } else {
         throw new UnsupportedOperationException(Intrinsics.stringPlus("Unsupported type classifier: ", $this$computeJavaType));
      }
   }

   // $FF: synthetic method
   static Type computeJavaType$default(KType var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      return computeJavaType(var0, var1);
   }

   @ExperimentalStdlibApi
   private static final Type createPossiblyInnerType(Class jClass, List arguments) {
      Class var3 = jClass.getDeclaringClass();
      if (var3 == null) {
         Iterable $this$map$iv = (Iterable)arguments;
         Object var43 = null;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            KTypeProjection p0 = (KTypeProjection)item$iv$iv;
            int var41 = 0;
            Type var47 = getJavaType(p0);
            destination$iv$iv.add(var47);
         }

         List var45 = (List)destination$iv$iv;
         return new ParameterizedTypeImpl(jClass, (Type)var43, var45);
      } else {
         Class ownerClass = var3;
         if (Modifier.isStatic(jClass.getModifiers())) {
            Type var48 = (Type)var3;
            Iterable var27 = (Iterable)arguments;
            Type var42 = var48;
            int $i$f$map = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var27, 10)));
            int $i$f$mapTo = 0;

            for(Object item$iv$iv : var27) {
               KTypeProjection p0 = (KTypeProjection)item$iv$iv;
               int var39 = 0;
               Type var46 = getJavaType(p0);
               destination$iv$iv.add(var46);
            }

            List var44 = (List)destination$iv$iv;
            return new ParameterizedTypeImpl(jClass, var42, var44);
         } else {
            int n = jClass.getTypeParameters().length;
            Type var10001 = createPossiblyInnerType(ownerClass, arguments.subList(n, arguments.size()));
            Iterable $this$map$iv = (Iterable)arguments.subList(0, n);
            Type var14 = var10001;
            int $i$f$map = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
            int $i$f$mapTo = 0;

            for(Object item$iv$iv : $this$map$iv) {
               KTypeProjection p0 = (KTypeProjection)item$iv$iv;
               int var12 = 0;
               Type var16 = getJavaType(p0);
               destination$iv$iv.add(var16);
            }

            List var15 = (List)destination$iv$iv;
            return new ParameterizedTypeImpl(jClass, var14, var15);
         }
      }
   }

   private static final Type getJavaType(KTypeProjection $this$javaType) {
      KVariance var2 = $this$javaType.getVariance();
      if (var2 == null) {
         return WildcardTypeImpl.Companion.getSTAR();
      } else {
         KVariance variance = var2;
         KType var10000 = $this$javaType.getType();
         Intrinsics.checkNotNull(var10000);
         KType type = var10000;
         int var4 = TypesJVMKt.WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
         Type var6;
         switch (var4) {
            case 1:
               var6 = new WildcardTypeImpl((Type)null, computeJavaType(type, true));
               break;
            case 2:
               var6 = computeJavaType(type, true);
               break;
            case 3:
               var6 = new WildcardTypeImpl(computeJavaType(type, true), (Type)null);
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         return var6;
      }
   }

   /** @deprecated */
   // $FF: synthetic method
   @ExperimentalStdlibApi
   private static void getJavaType$annotations(KTypeProjection var0) {
   }

   private static final String typeToString(Type type) {
      String var3;
      if (type instanceof Class) {
         if (((Class)type).isArray()) {
            Sequence unwrap = SequencesKt.generateSequence(type, null.INSTANCE);
            var3 = Intrinsics.stringPlus(((Class)SequencesKt.last(unwrap)).getName(), StringsKt.repeat((CharSequence)"[]", SequencesKt.count(unwrap)));
         } else {
            var3 = ((Class)type).getName();
         }

         String var1 = var3;
         Intrinsics.checkNotNullExpressionValue(var1, "{\n        if (type.isArr…   } else type.name\n    }");
         var3 = var1;
      } else {
         var3 = type.toString();
      }

      return var3;
   }

   // $FF: synthetic method
   public static final Type access$computeJavaType(KType $receiver, boolean forceWrapper) {
      return computeJavaType($receiver, forceWrapper);
   }

   // $FF: synthetic method
   public static final String access$typeToString(Type type) {
      return typeToString(type);
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
         var0[KVariance.IN.ordinal()] = 1;
         var0[KVariance.INVARIANT.ordinal()] = 2;
         var0[KVariance.OUT.ordinal()] = 3;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
