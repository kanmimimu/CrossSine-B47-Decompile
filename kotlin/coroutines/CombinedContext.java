package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0000H\u0002J\u0013\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00100\u0013H\u0016¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0096\u0002¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "writeReplace", "Serialized", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public final class CombinedContext implements CoroutineContext, Serializable {
   @NotNull
   private final CoroutineContext left;
   @NotNull
   private final CoroutineContext.Element element;

   public CombinedContext(@NotNull CoroutineContext left, @NotNull CoroutineContext.Element element) {
      Intrinsics.checkNotNullParameter(left, "left");
      Intrinsics.checkNotNullParameter(element, "element");
      super();
      this.left = left;
      this.element = element;
   }

   @Nullable
   public CoroutineContext.Element get(@NotNull CoroutineContext.Key key) {
      Intrinsics.checkNotNullParameter(key, "key");
      CombinedContext cur = this;

      while(true) {
         CoroutineContext.Element it = cur.element.get(key);
         if (it != null) {
            int var6 = 0;
            return it;
         }

         CoroutineContext next = cur.left;
         if (!(next instanceof CombinedContext)) {
            return next.get(key);
         }

         cur = (CombinedContext)next;
      }
   }

   public Object fold(Object initial, @NotNull Function2 operation) {
      Intrinsics.checkNotNullParameter(operation, "operation");
      return operation.invoke(this.left.fold(initial, operation), this.element);
   }

   @NotNull
   public CoroutineContext minusKey(@NotNull CoroutineContext.Key key) {
      Intrinsics.checkNotNullParameter(key, "key");
      CoroutineContext newLeft = this.element.get(key);
      if (newLeft == null) {
         newLeft = this.left.minusKey(key);
         return newLeft == this.left ? (CoroutineContext)this : (newLeft == EmptyCoroutineContext.INSTANCE ? (CoroutineContext)this.element : (CoroutineContext)(new CombinedContext(newLeft, this.element)));
      } else {
         int var5 = 0;
         return this.left;
      }
   }

   private final int size() {
      CombinedContext cur = this;
      int size = 2;

      while(true) {
         CoroutineContext var4 = cur.left;
         CombinedContext var3 = var4 instanceof CombinedContext ? (CombinedContext)var4 : null;
         if (var3 == null) {
            return size;
         }

         cur = var3;
         ++size;
      }
   }

   private final boolean contains(CoroutineContext.Element element) {
      return Intrinsics.areEqual((Object)this.get(element.getKey()), (Object)element);
   }

   private final boolean containsAll(CombinedContext context) {
      CoroutineContext next;
      for(CombinedContext cur = context; this.contains(cur.element); cur = (CombinedContext)next) {
         next = cur.left;
         if (!(next instanceof CombinedContext)) {
            return this.contains((CoroutineContext.Element)next);
         }
      }

      return false;
   }

   public boolean equals(@Nullable Object other) {
      return this == other || other instanceof CombinedContext && ((CombinedContext)other).size() == this.size() && ((CombinedContext)other).containsAll(this);
   }

   public int hashCode() {
      return this.left.hashCode() + this.element.hashCode();
   }

   @NotNull
   public String toString() {
      return '[' + (String)this.fold("", null.INSTANCE) + ']';
   }

   private final Object writeReplace() {
      int n = this.size();
      final CoroutineContext[] elements = new CoroutineContext[n];
      final Ref.IntRef index = new Ref.IntRef();
      this.fold(Unit.INSTANCE, new Function2() {
         public final void invoke(@NotNull Unit $noName_0, @NotNull CoroutineContext.Element element) {
            Intrinsics.checkNotNullParameter($noName_0, "$noName_0");
            Intrinsics.checkNotNullParameter(element, "element");
            CoroutineContext[] var10000 = elements;
            int var3 = index.element++;
            var10000[var3] = element;
         }
      });
      boolean var4 = index.element == n;
      if (!var4) {
         String var5 = "Check failed.";
         throw new IllegalStateException(var5.toString());
      } else {
         return new Serialized(elements);
      }
   }

   @NotNull
   public CoroutineContext plus(@NotNull CoroutineContext context) {
      return CoroutineContext.DefaultImpls.plus(this, context);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", "", "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", "", "Companion", "kotlin-stdlib"}
   )
   private static final class Serialized implements Serializable {
      @NotNull
      public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
      @NotNull
      private final CoroutineContext[] elements;
      private static final long serialVersionUID = 0L;

      public Serialized(@NotNull CoroutineContext[] elements) {
         Intrinsics.checkNotNullParameter(elements, "elements");
         super();
         this.elements = elements;
      }

      @NotNull
      public final CoroutineContext[] getElements() {
         return this.elements;
      }

      private final Object readResolve() {
         CoroutineContext[] $this$fold$iv = this.elements;
         Object initial$iv = EmptyCoroutineContext.INSTANCE;
         int $i$f$fold = 0;
         Object accumulator$iv = initial$iv;
         CoroutineContext[] var5 = $this$fold$iv;
         int var6 = 0;

         Object element$iv;
         CoroutineContext p0;
         for(int var7 = $this$fold$iv.length; var6 < var7; accumulator$iv = p0.plus((CoroutineContext)element$iv)) {
            element$iv = var5[var6];
            ++var6;
            p0 = (CoroutineContext)accumulator$iv;
            int var11 = 0;
         }

         return accumulator$iv;
      }

      @Metadata(
         mv = {1, 6, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
         d2 = {"Lkotlin/coroutines/CombinedContext$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}
      )
      public static final class Companion {
         private Companion() {
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
         }
      }
   }
}
