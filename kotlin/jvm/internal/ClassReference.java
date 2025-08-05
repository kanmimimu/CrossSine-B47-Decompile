package kotlin.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.reflect.KClass;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 O2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001OB\u0011\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010F\u001a\u00020\u00122\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010H\u001a\u00020IH\u0002J\b\u0010J\u001a\u00020KH\u0016J\u0012\u0010L\u001a\u00020\u00122\b\u0010M\u001a\u0004\u0018\u00010\u0002H\u0017J\b\u0010N\u001a\u000201H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0015R\u001a\u0010\u0018\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0018\u0010\u0015R\u001a\u0010\u001a\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u0014\u001a\u0004\b\u001a\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001c\u0010\u0015R\u001a\u0010\u001e\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u0014\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010 \u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b!\u0010\u0014\u001a\u0004\b \u0010\u0015R\u001a\u0010\"\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b#\u0010\u0014\u001a\u0004\b\"\u0010\u0015R\u001a\u0010$\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b%\u0010\u0014\u001a\u0004\b$\u0010\u0015R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001e\u0010(\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030)0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u0010R\u001e\u0010+\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0010R\u0016\u0010-\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0016\u00100\u001a\u0004\u0018\u0001018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R(\u00104\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00010\b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b5\u0010\u0014\u001a\u0004\b6\u0010\u000bR\u0016\u00107\u001a\u0004\u0018\u0001018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00103R \u00109\u001a\b\u0012\u0004\u0012\u00020:0\b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b;\u0010\u0014\u001a\u0004\b<\u0010\u000bR \u0010=\u001a\b\u0012\u0004\u0012\u00020>0\b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b?\u0010\u0014\u001a\u0004\b@\u0010\u000bR\u001c\u0010A\u001a\u0004\u0018\u00010B8VX\u0097\u0004¢\u0006\f\u0012\u0004\bC\u0010\u0014\u001a\u0004\bD\u0010E¨\u0006P"},
   d2 = {"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isFun", "isFun$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "isValue", "isValue$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "getSealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"}
)
public final class ClassReference implements KClass, ClassBasedDeclarationContainer {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final Class jClass;
   @NotNull
   private static final Map FUNCTION_CLASSES;
   @NotNull
   private static final HashMap primitiveFqNames;
   @NotNull
   private static final HashMap primitiveWrapperFqNames;
   @NotNull
   private static final HashMap classFqNames;
   @NotNull
   private static final Map simpleNames;

   public ClassReference(@NotNull Class jClass) {
      Intrinsics.checkNotNullParameter(jClass, "jClass");
      super();
      this.jClass = jClass;
   }

   @NotNull
   public Class getJClass() {
      return this.jClass;
   }

   @Nullable
   public String getSimpleName() {
      return Companion.getClassSimpleName(this.getJClass());
   }

   @Nullable
   public String getQualifiedName() {
      return Companion.getClassQualifiedName(this.getJClass());
   }

   @NotNull
   public Collection getMembers() {
      this.error();
      throw new KotlinNothingValueException();
   }

   @NotNull
   public Collection getConstructors() {
      this.error();
      throw new KotlinNothingValueException();
   }

   @NotNull
   public Collection getNestedClasses() {
      this.error();
      throw new KotlinNothingValueException();
   }

   @NotNull
   public List getAnnotations() {
      this.error();
      throw new KotlinNothingValueException();
   }

   @Nullable
   public Object getObjectInstance() {
      this.error();
      throw new KotlinNothingValueException();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isInstance(@Nullable Object value) {
      return Companion.isInstance(value, this.getJClass());
   }

   @NotNull
   public List getTypeParameters() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void getTypeParameters$annotations() {
   }

   @NotNull
   public List getSupertypes() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void getSupertypes$annotations() {
   }

   @NotNull
   public List getSealedSubclasses() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   public static void getSealedSubclasses$annotations() {
   }

   @Nullable
   public KVisibility getVisibility() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void getVisibility$annotations() {
   }

   public boolean isFinal() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void isFinal$annotations() {
   }

   public boolean isOpen() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void isOpen$annotations() {
   }

   public boolean isAbstract() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void isAbstract$annotations() {
   }

   public boolean isSealed() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void isSealed$annotations() {
   }

   public boolean isData() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void isData$annotations() {
   }

   public boolean isInner() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void isInner$annotations() {
   }

   public boolean isCompanion() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void isCompanion$annotations() {
   }

   public boolean isFun() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.4"
   )
   public static void isFun$annotations() {
   }

   public boolean isValue() {
      this.error();
      throw new KotlinNothingValueException();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   public static void isValue$annotations() {
   }

   private final Void error() {
      throw new KotlinReflectionNotSupportedError();
   }

   public boolean equals(@Nullable Object other) {
      return other instanceof ClassReference && Intrinsics.areEqual((Object)JvmClassMappingKt.getJavaObjectType(this), (Object)JvmClassMappingKt.getJavaObjectType((KClass)other));
   }

   public int hashCode() {
      return JvmClassMappingKt.getJavaObjectType(this).hashCode();
   }

   @NotNull
   public String toString() {
      return Intrinsics.stringPlus(this.getJClass().toString(), " (Kotlin reflection is not available)");
   }

   static {
      Class[] $this$primitiveFqNames_u24lambda_u2d1 = new Class[]{Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class};
      Iterable $this$mapValues$iv = (Iterable)CollectionsKt.listOf($this$primitiveFqNames_u24lambda_u2d1);
      int $i$f$mapIndexed = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapValues$iv, 10)));
      int $i$f$mapIndexedTo = 0;
      int index$iv$iv = 0;

      for(Object item$iv$iv : $this$mapValues$iv) {
         int var8 = index$iv$iv++;
         if (var8 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         Class clazz = (Class)item$iv$iv;
         int var11 = 0;
         Pair var17 = TuplesKt.to(clazz, var8);
         destination$iv$iv.add(var17);
      }

      FUNCTION_CLASSES = MapsKt.toMap((Iterable)((List)destination$iv$iv));
      HashMap $this$primitiveFqNames_u24lambda_u2d1 = new HashMap();
      int $this$mapIndexedTo$iv$iv = 0;
      $this$primitiveFqNames_u24lambda_u2d1.put("boolean", "kotlin.Boolean");
      $this$primitiveFqNames_u24lambda_u2d1.put("char", "kotlin.Char");
      $this$primitiveFqNames_u24lambda_u2d1.put("byte", "kotlin.Byte");
      $this$primitiveFqNames_u24lambda_u2d1.put("short", "kotlin.Short");
      $this$primitiveFqNames_u24lambda_u2d1.put("int", "kotlin.Int");
      $this$primitiveFqNames_u24lambda_u2d1.put("float", "kotlin.Float");
      $this$primitiveFqNames_u24lambda_u2d1.put("long", "kotlin.Long");
      $this$primitiveFqNames_u24lambda_u2d1.put("double", "kotlin.Double");
      primitiveFqNames = $this$primitiveFqNames_u24lambda_u2d1;
      $this$primitiveFqNames_u24lambda_u2d1 = new HashMap();
      $this$mapIndexedTo$iv$iv = 0;
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Boolean", "kotlin.Boolean");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Character", "kotlin.Char");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Byte", "kotlin.Byte");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Short", "kotlin.Short");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Integer", "kotlin.Int");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Float", "kotlin.Float");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Long", "kotlin.Long");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Double", "kotlin.Double");
      primitiveWrapperFqNames = $this$primitiveFqNames_u24lambda_u2d1;
      $this$primitiveFqNames_u24lambda_u2d1 = new HashMap();
      HashMap $this$classFqNames_u24lambda_u2d4 = $this$primitiveFqNames_u24lambda_u2d1;
      $this$mapIndexedTo$iv$iv = 0;
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Object", "kotlin.Any");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.String", "kotlin.String");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.CharSequence", "kotlin.CharSequence");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Throwable", "kotlin.Throwable");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Cloneable", "kotlin.Cloneable");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Number", "kotlin.Number");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Comparable", "kotlin.Comparable");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Enum", "kotlin.Enum");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.annotation.Annotation", "kotlin.Annotation");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.lang.Iterable", "kotlin.collections.Iterable");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.util.Iterator", "kotlin.collections.Iterator");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.util.Collection", "kotlin.collections.Collection");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.util.List", "kotlin.collections.List");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.util.Set", "kotlin.collections.Set");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.util.ListIterator", "kotlin.collections.ListIterator");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.util.Map", "kotlin.collections.Map");
      $this$primitiveFqNames_u24lambda_u2d1.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
      $this$primitiveFqNames_u24lambda_u2d1.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
      $this$primitiveFqNames_u24lambda_u2d1.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
      $this$primitiveFqNames_u24lambda_u2d1.putAll((Map)primitiveFqNames);
      $this$primitiveFqNames_u24lambda_u2d1.putAll((Map)primitiveWrapperFqNames);
      destination$iv$iv = primitiveFqNames.values();
      Intrinsics.checkNotNullExpressionValue(destination$iv$iv, "primitiveFqNames.values");
      Iterable $this$associateTo$iv = (Iterable)destination$iv$iv;
      $i$f$mapIndexedTo = 0;

      for(Object element$iv : $this$associateTo$iv) {
         Map var41 = (Map)$this$classFqNames_u24lambda_u2d4;
         String kotlinName = (String)element$iv;
         int var46 = 0;
         StringBuilder var10000 = (new StringBuilder()).append("kotlin.jvm.internal.");
         Intrinsics.checkNotNullExpressionValue(kotlinName, "kotlinName");
         Pair var44 = TuplesKt.to(var10000.append(StringsKt.substringAfterLast$default(kotlinName, '.', (String)null, 2, (Object)null)).append("CompanionObject").toString(), Intrinsics.stringPlus(kotlinName, ".Companion"));
         var41.put(var44.getFirst(), var44.getSecond());
      }

      Map var49 = (Map)$this$classFqNames_u24lambda_u2d4;

      for(Map.Entry $i$f$mapValuesTo : FUNCTION_CLASSES.entrySet()) {
         Class klass = (Class)$i$f$mapValuesTo.getKey();
         int arity = ((Number)$i$f$mapValuesTo.getValue()).intValue();
         $this$classFqNames_u24lambda_u2d4.put(klass.getName(), Intrinsics.stringPlus("kotlin.Function", arity));
      }

      classFqNames = $this$primitiveFqNames_u24lambda_u2d1;
      Map $this$mapValues$iv = (Map)classFqNames;
      int $i$f$mapValues = 0;
      Map destination$iv$iv = (Map)(new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size())));
      $i$f$mapIndexedTo = 0;
      Iterable $this$associateByTo$iv$iv$iv = (Iterable)$this$mapValues$iv.entrySet();
      int $i$f$associateByTo = 0;

      for(Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
         Map.Entry it$iv$iv = (Map.Entry)element$iv$iv$iv;
         int var48 = 0;
         Object var12 = it$iv$iv.getKey();
         Map.Entry $dstr$_u24__u24$fqName = (Map.Entry)element$iv$iv$iv;
         int var14 = 0;
         String fqName = (String)$dstr$_u24__u24$fqName.getValue();
         String var18 = StringsKt.substringAfterLast$default(fqName, '.', (String)null, 2, (Object)null);
         destination$iv$iv.put(var12, var18);
      }

      simpleNames = destination$iv$iv;
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005R&\u0010\u0003\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
      d2 = {"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"}
   )
   public static final class Companion {
      private Companion() {
      }

      @Nullable
      public final String getClassSimpleName(@NotNull Class jClass) {
         Intrinsics.checkNotNullParameter(jClass, "jClass");
         String var10000;
         if (jClass.isAnonymousClass()) {
            var10000 = null;
         } else if (jClass.isLocalClass()) {
            String name = jClass.getSimpleName();
            Method method = jClass.getEnclosingMethod();
            if (method == null) {
               Constructor constructor = jClass.getEnclosingConstructor();
               if (constructor == null) {
                  Intrinsics.checkNotNullExpressionValue(name, "name");
                  var10000 = StringsKt.substringAfter$default(name, '$', (String)null, 2, (Object)null);
               } else {
                  int var7 = 0;
                  Intrinsics.checkNotNullExpressionValue(name, "name");
                  var10000 = StringsKt.substringAfter$default(name, Intrinsics.stringPlus(constructor.getName(), "$"), (String)null, 2, (Object)null);
               }
            } else {
               int constructor = 0;
               Intrinsics.checkNotNullExpressionValue(name, "name");
               var10000 = StringsKt.substringAfter$default(name, Intrinsics.stringPlus(method.getName(), "$"), (String)null, 2, (Object)null);
            }
         } else if (jClass.isArray()) {
            Class componentType = jClass.getComponentType();
            if (componentType.isPrimitive()) {
               String constructor = (String)ClassReference.simpleNames.get(componentType.getName());
               var10000 = constructor == null ? null : Intrinsics.stringPlus(constructor, "Array");
            } else {
               var10000 = null;
            }

            String method = var10000;
            var10000 = method == null ? "Array" : method;
         } else {
            String var9 = (String)ClassReference.simpleNames.get(jClass.getName());
            var10000 = var9 == null ? jClass.getSimpleName() : var9;
         }

         return var10000;
      }

      @Nullable
      public final String getClassQualifiedName(@NotNull Class jClass) {
         Intrinsics.checkNotNullParameter(jClass, "jClass");
         String var10000;
         if (jClass.isAnonymousClass()) {
            var10000 = null;
         } else if (jClass.isLocalClass()) {
            var10000 = null;
         } else if (jClass.isArray()) {
            Class componentType = jClass.getComponentType();
            if (componentType.isPrimitive()) {
               String var4 = (String)ClassReference.classFqNames.get(componentType.getName());
               var10000 = var4 == null ? null : Intrinsics.stringPlus(var4, "Array");
            } else {
               var10000 = null;
            }

            String var3 = var10000;
            var10000 = var3 == null ? "kotlin.Array" : var3;
         } else {
            String var5 = (String)ClassReference.classFqNames.get(jClass.getName());
            var10000 = var5 == null ? jClass.getCanonicalName() : var5;
         }

         return var10000;
      }

      public final boolean isInstance(@Nullable Object value, @NotNull Class jClass) {
         Intrinsics.checkNotNullParameter(jClass, "jClass");
         Map var4 = ClassReference.FUNCTION_CLASSES;
         Integer var3 = (Integer)var4.get(jClass);
         if (var3 == null) {
            Class objectType = jClass.isPrimitive() ? JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(jClass)) : jClass;
            return objectType.isInstance(value);
         } else {
            int arity = ((Number)var3).intValue();
            int var6 = 0;
            return TypeIntrinsics.isFunctionOfArity(value, arity);
         }
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
