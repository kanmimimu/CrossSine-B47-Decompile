package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference extends CallableReference implements FunctionBase, KFunction {
   private final int arity;
   @SinceKotlin(
      version = "1.4"
   )
   private final int flags;

   public FunctionReference(int arity) {
      this(arity, NO_RECEIVER, (Class)null, (String)null, (String)null, 0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public FunctionReference(int arity, Object receiver) {
      this(arity, receiver, (Class)null, (String)null, (String)null, 0);
   }

   @SinceKotlin(
      version = "1.4"
   )
   public FunctionReference(int arity, Object receiver, Class owner, String name, String signature, int flags) {
      super(receiver, owner, name, signature, (flags & 1) == 1);
      this.arity = arity;
      this.flags = flags >> 1;
   }

   public int getArity() {
      return this.arity;
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KFunction getReflected() {
      return (KFunction)super.getReflected();
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KCallable computeReflected() {
      return Reflection.function(this);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isInline() {
      return this.getReflected().isInline();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isExternal() {
      return this.getReflected().isExternal();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isOperator() {
      return this.getReflected().isOperator();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isInfix() {
      return this.getReflected().isInfix();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof FunctionReference)) {
         return obj instanceof KFunction ? obj.equals(this.compute()) : false;
      } else {
         FunctionReference other = (FunctionReference)obj;
         return Intrinsics.areEqual((Object)this.getOwner(), (Object)other.getOwner()) && this.getName().equals(other.getName()) && this.getSignature().equals(other.getSignature()) && this.flags == other.flags && this.arity == other.arity && Intrinsics.areEqual(this.getBoundReceiver(), other.getBoundReceiver());
      }
   }

   public int hashCode() {
      return ((this.getOwner() == null ? 0 : this.getOwner().hashCode() * 31) + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
   }

   public String toString() {
      KCallable reflected = this.compute();
      if (reflected != this) {
         return reflected.toString();
      } else {
         return "<init>".equals(this.getName()) ? "constructor (Kotlin reflection is not available)" : "function " + this.getName() + " (Kotlin reflection is not available)";
      }
   }
}
