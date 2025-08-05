package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
   d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever;", "", "()V", "cache", "Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "notOnJava9", "buildCache", "continuation", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getModuleName", "", "Cache", "kotlin-stdlib"}
)
final class ModuleNameRetriever {
   @NotNull
   public static final ModuleNameRetriever INSTANCE = new ModuleNameRetriever();
   @NotNull
   private static final Cache notOnJava9 = new Cache((Method)null, (Method)null, (Method)null);
   @Nullable
   private static Cache cache;

   private ModuleNameRetriever() {
   }

   @Nullable
   public final String getModuleName(@NotNull BaseContinuationImpl continuation) {
      Intrinsics.checkNotNullParameter(continuation, "continuation");
      Cache var3 = ModuleNameRetriever.cache;
      Cache cache = var3 == null ? this.buildCache(continuation) : var3;
      if (cache == notOnJava9) {
         return null;
      } else {
         Method descriptor = cache.getModuleMethod;
         Object module = descriptor == null ? null : descriptor.invoke(continuation.getClass());
         if (module == null) {
            return null;
         } else {
            Method var6 = cache.getDescriptorMethod;
            Object descriptor = var6 == null ? null : var6.invoke(module);
            if (descriptor == null) {
               return null;
            } else {
               var6 = cache.nameMethod;
               descriptor = var6 == null ? null : var6.invoke(descriptor);
               return descriptor instanceof String ? (String)descriptor : null;
            }
         }
      }
   }

   private final Cache buildCache(BaseContinuationImpl continuation) {
      try {
         Method getModuleMethod = Class.class.getDeclaredMethod("getModule");
         Class methodClass = continuation.getClass().getClassLoader().loadClass("java.lang.Module");
         Method getDescriptorMethod = methodClass.getDeclaredMethod("getDescriptor");
         Class moduleDescriptorClass = continuation.getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor");
         Method nameMethod = moduleDescriptorClass.getDeclaredMethod("name");
         Cache it = new Cache(getModuleMethod, getDescriptorMethod, nameMethod);
         int var9 = 0;
         ModuleNameRetriever var13 = INSTANCE;
         cache = it;
         return it;
      } catch (Exception var10) {
         Cache methodClass = notOnJava9;
         int moduleDescriptorClass = 0;
         ModuleNameRetriever var10000 = INSTANCE;
         cache = methodClass;
         return methodClass;
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"},
      d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "", "getModuleMethod", "Ljava/lang/reflect/Method;", "getDescriptorMethod", "nameMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "kotlin-stdlib"}
   )
   private static final class Cache {
      @JvmField
      @Nullable
      public final Method getModuleMethod;
      @JvmField
      @Nullable
      public final Method getDescriptorMethod;
      @JvmField
      @Nullable
      public final Method nameMethod;

      public Cache(@Nullable Method getModuleMethod, @Nullable Method getDescriptorMethod, @Nullable Method nameMethod) {
         this.getModuleMethod = getModuleMethod;
         this.getDescriptorMethod = getDescriptorMethod;
         this.nameMethod = nameMethod;
      }
   }
}
