package kotlin.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u0011\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ%\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0012¢\u0006\u0002\u0010\u0013J\u0011\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ\u0013\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\fH\u0016¢\u0006\u0002\u0010\u0017J\u0011\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ\b\u0010\u0019\u001a\u00020\u0002H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "},
   d2 = {"Lkotlin/reflect/TypeVariableImpl;", "Ljava/lang/reflect/TypeVariable;", "Ljava/lang/reflect/GenericDeclaration;", "Lkotlin/reflect/TypeImpl;", "typeParameter", "Lkotlin/reflect/KTypeParameter;", "(Lkotlin/reflect/KTypeParameter;)V", "equals", "", "other", "", "getAnnotatedBounds", "", "", "()[Ljava/lang/annotation/Annotation;", "getAnnotation", "T", "annotationClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;", "getAnnotations", "getBounds", "Ljava/lang/reflect/Type;", "()[Ljava/lang/reflect/Type;", "getDeclaredAnnotations", "getGenericDeclaration", "getName", "", "getTypeName", "hashCode", "", "toString", "kotlin-stdlib"}
)
@ExperimentalStdlibApi
final class TypeVariableImpl implements TypeVariable, TypeImpl {
   @NotNull
   private final KTypeParameter typeParameter;

   public TypeVariableImpl(@NotNull KTypeParameter typeParameter) {
      Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
      super();
      this.typeParameter = typeParameter;
   }

   @NotNull
   public String getName() {
      return this.typeParameter.getName();
   }

   @NotNull
   public GenericDeclaration getGenericDeclaration() {
      throw new NotImplementedError(Intrinsics.stringPlus("An operation is not implemented: ", Intrinsics.stringPlus("getGenericDeclaration() is not yet supported for type variables created from KType: ", this.typeParameter)));
   }

   @NotNull
   public Type[] getBounds() {
      Iterable $this$map$iv = (Iterable)this.typeParameter.getUpperBounds();
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         KType it = (KType)item$iv$iv;
         int var9 = 0;
         Type var11 = TypesJVMKt.access$computeJavaType(it, true);
         destination$iv$iv.add(var11);
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var14 = $this$toTypedArray$iv.toArray(new Type[0]);
      if (var14 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         return (Type[])var14;
      }
   }

   @NotNull
   public String getTypeName() {
      return this.getName();
   }

   public boolean equals(@Nullable Object other) {
      return other instanceof TypeVariable && Intrinsics.areEqual((Object)this.getName(), (Object)((TypeVariable)other).getName()) && Intrinsics.areEqual((Object)this.getGenericDeclaration(), (Object)((TypeVariable)other).getGenericDeclaration());
   }

   public int hashCode() {
      return this.getName().hashCode() ^ this.getGenericDeclaration().hashCode();
   }

   @NotNull
   public String toString() {
      return this.getTypeName();
   }

   @Nullable
   public final Annotation getAnnotation(@NotNull Class annotationClass) {
      Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
      return null;
   }

   @NotNull
   public final Annotation[] getAnnotations() {
      int $i$f$emptyArray = 0;
      return new Annotation[0];
   }

   @NotNull
   public final Annotation[] getDeclaredAnnotations() {
      int $i$f$emptyArray = 0;
      return new Annotation[0];
   }

   @NotNull
   public final Annotation[] getAnnotatedBounds() {
      int $i$f$emptyArray = 0;
      return new Annotation[0];
   }
}
