package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u0018\u001a\u00020\u0019\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001a¢\u0006\u0002\u0010\u001b\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\u0002H\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018Ç\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001c"},
   d2 = {"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "getJavaClass$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "getRuntimeClassOfKClassInstance$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"}
)
@JvmName(
   name = "JvmClassMappingKt"
)
public final class JvmClassMappingKt {
   @JvmName(
      name = "getJavaClass"
   )
   @NotNull
   public static final Class getJavaClass(@NotNull KClass $this$java) {
      Intrinsics.checkNotNullParameter($this$java, "<this>");
      return ((ClassBasedDeclarationContainer)$this$java).getJClass();
   }

   /** @deprecated */
   // $FF: synthetic method
   public static void getJavaClass$annotations(KClass var0) {
   }

   @Nullable
   public static final Class getJavaPrimitiveType(@NotNull KClass $this$javaPrimitiveType) {
      Intrinsics.checkNotNullParameter($this$javaPrimitiveType, "<this>");
      Class thisJClass = ((ClassBasedDeclarationContainer)$this$javaPrimitiveType).getJClass();
      if (thisJClass.isPrimitive()) {
         return thisJClass;
      } else {
         Class var10000;
         if (var2 != null) {
            switch (var2) {
               case "java.lang.Integer":
                  var10000 = Integer.TYPE;
                  return var10000;
               case "java.lang.Float":
                  var10000 = Float.TYPE;
                  return var10000;
               case "java.lang.Short":
                  var10000 = Short.TYPE;
                  return var10000;
               case "java.lang.Character":
                  var10000 = Character.TYPE;
                  return var10000;
               case "java.lang.Boolean":
                  var10000 = Boolean.TYPE;
                  return var10000;
               case "java.lang.Byte":
                  var10000 = Byte.TYPE;
                  return var10000;
               case "java.lang.Long":
                  var10000 = Long.TYPE;
                  return var10000;
               case "java.lang.Void":
                  var10000 = Void.TYPE;
                  return var10000;
               case "java.lang.Double":
                  var10000 = Double.TYPE;
                  return var10000;
            }
         }

         var10000 = null;
         return var10000;
      }
   }

   @NotNull
   public static final Class getJavaObjectType(@NotNull KClass $this$javaObjectType) {
      Intrinsics.checkNotNullParameter($this$javaObjectType, "<this>");
      Class thisJClass = ((ClassBasedDeclarationContainer)$this$javaObjectType).getJClass();
      if (!thisJClass.isPrimitive()) {
         return thisJClass;
      } else {
         Class var10000;
         if (var2 != null) {
            switch (var2) {
               case "double":
                  var10000 = Double.class;
                  return var10000;
               case "int":
                  var10000 = Integer.class;
                  return var10000;
               case "byte":
                  var10000 = Byte.class;
                  return var10000;
               case "char":
                  var10000 = Character.class;
                  return var10000;
               case "long":
                  var10000 = Long.class;
                  return var10000;
               case "void":
                  var10000 = Void.class;
                  return var10000;
               case "boolean":
                  var10000 = Boolean.class;
                  return var10000;
               case "float":
                  var10000 = Float.class;
                  return var10000;
               case "short":
                  var10000 = Short.class;
                  return var10000;
            }
         }

         var10000 = thisJClass;
         return var10000;
      }
   }

   @JvmName(
      name = "getKotlinClass"
   )
   @NotNull
   public static final KClass getKotlinClass(@NotNull Class $this$kotlin) {
      Intrinsics.checkNotNullParameter($this$kotlin, "<this>");
      KClass var1 = Reflection.getOrCreateKotlinClass($this$kotlin);
      return var1;
   }

   @NotNull
   public static final Class getJavaClass(@NotNull Object $this$javaClass) {
      Intrinsics.checkNotNullParameter($this$javaClass, "<this>");
      int $i$f$getJavaClass = 0;
      Class var2 = $this$javaClass.getClass();
      return var2;
   }

   /** @deprecated */
   @JvmName(
      name = "getRuntimeClassOfKClassInstance"
   )
   @NotNull
   public static final Class getRuntimeClassOfKClassInstance(@NotNull KClass $this$javaClass) {
      Intrinsics.checkNotNullParameter($this$javaClass, "<this>");
      int $i$f$getRuntimeClassOfKClassInstance = 0;
      Class var2 = ((Object)$this$javaClass).getClass();
      return var2;
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.",
      replaceWith = @ReplaceWith(
   expression = "(this as Any).javaClass",
   imports = {}
),
      level = DeprecationLevel.ERROR
   )
   public static void getRuntimeClassOfKClassInstance$annotations(KClass var0) {
   }

   // $FF: synthetic method
   public static final boolean isArrayOf(Object[] $this$isArrayOf) {
      Intrinsics.checkNotNullParameter($this$isArrayOf, "<this>");
      Intrinsics.reifiedOperationMarker(4, "T");
      return ((Class)Object.class).isAssignableFrom(((Class)$this$isArrayOf.getClass()).getComponentType());
   }

   @NotNull
   public static final KClass getAnnotationClass(@NotNull Annotation $this$annotationClass) {
      Intrinsics.checkNotNullParameter($this$annotationClass, "<this>");
      Class var1 = $this$annotationClass.annotationType();
      Intrinsics.checkNotNullExpressionValue(var1, "this as java.lang.annota…otation).annotationType()");
      return getKotlinClass(var1);
   }
}
