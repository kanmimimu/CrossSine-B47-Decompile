package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0002\u001a\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\bH\u0001¢\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\bH\u0001¢\u0006\u0002\b\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"COROUTINES_DEBUG_METADATA_VERSION", "", "checkDebugMetadataVersion", "", "expected", "actual", "getDebugMetadataAnnotation", "Lkotlin/coroutines/jvm/internal/DebugMetadata;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getLabel", "getSpilledVariableFieldMapping", "", "", "(Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;)[Ljava/lang/String;", "getStackTraceElementImpl", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "kotlin-stdlib"}
)
public final class DebugMetadataKt {
   private static final int COROUTINES_DEBUG_METADATA_VERSION = 1;

   @SinceKotlin(
      version = "1.3"
   )
   @JvmName(
      name = "getStackTraceElement"
   )
   @Nullable
   public static final StackTraceElement getStackTraceElement(@NotNull BaseContinuationImpl $this$getStackTraceElementImpl) {
      Intrinsics.checkNotNullParameter($this$getStackTraceElementImpl, "<this>");
      DebugMetadata var2 = getDebugMetadataAnnotation($this$getStackTraceElementImpl);
      if (var2 == null) {
         return null;
      } else {
         DebugMetadata debugMetadata = var2;
         checkDebugMetadataVersion(1, var2.v());
         int label = getLabel($this$getStackTraceElementImpl);
         int lineNumber = label < 0 ? -1 : debugMetadata.l()[label];
         String moduleName = ModuleNameRetriever.INSTANCE.getModuleName($this$getStackTraceElementImpl);
         String moduleAndClass = moduleName == null ? debugMetadata.c() : moduleName + '/' + debugMetadata.c();
         return new StackTraceElement(moduleAndClass, debugMetadata.m(), debugMetadata.f(), lineNumber);
      }
   }

   private static final DebugMetadata getDebugMetadataAnnotation(BaseContinuationImpl $this$getDebugMetadataAnnotation) {
      return (DebugMetadata)$this$getDebugMetadataAnnotation.getClass().getAnnotation(DebugMetadata.class);
   }

   private static final int getLabel(BaseContinuationImpl $this$getLabel) {
      int field;
      try {
         Field field = $this$getLabel.getClass().getDeclaredField("label");
         field.setAccessible(true);
         Object var3 = field.get($this$getLabel);
         Integer var2 = var3 instanceof Integer ? (Integer)var3 : null;
         field = (var2 == null ? 0 : var2) - 1;
      } catch (Exception var4) {
         field = -1;
      }

      return field;
   }

   private static final void checkDebugMetadataVersion(int expected, int actual) {
      if (actual > expected) {
         throw new IllegalStateException(("Debug metadata version mismatch. Expected: " + expected + ", got " + actual + ". Please update the Kotlin standard library.").toString());
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @JvmName(
      name = "getSpilledVariableFieldMapping"
   )
   @Nullable
   public static final String[] getSpilledVariableFieldMapping(@NotNull BaseContinuationImpl $this$getSpilledVariableFieldMapping) {
      Intrinsics.checkNotNullParameter($this$getSpilledVariableFieldMapping, "<this>");
      DebugMetadata var2 = getDebugMetadataAnnotation($this$getSpilledVariableFieldMapping);
      if (var2 == null) {
         return null;
      } else {
         DebugMetadata debugMetadata = var2;
         checkDebugMetadataVersion(1, var2.v());
         ArrayList res = new ArrayList();
         int label = getLabel($this$getSpilledVariableFieldMapping);
         int[] thisCollection$iv = var2.i();
         int $i$f$toTypedArray = 0;
         int var6 = thisCollection$iv.length;

         while($i$f$toTypedArray < var6) {
            int i = $i$f$toTypedArray;
            int labelOfIndex = thisCollection$iv[$i$f$toTypedArray];
            ++$i$f$toTypedArray;
            if (labelOfIndex == label) {
               res.add(debugMetadata.s()[i]);
               res.add(debugMetadata.n()[i]);
            }
         }

         Collection $this$toTypedArray$iv = (Collection)res;
         $i$f$toTypedArray = 0;
         Object[] var12 = $this$toTypedArray$iv.toArray(new String[0]);
         if (var12 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
         } else {
            return (String[])var12;
         }
      }
   }
}
