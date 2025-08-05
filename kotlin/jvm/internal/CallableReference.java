package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import kotlin.SinceKotlin;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KType;
import kotlin.reflect.KVisibility;

public abstract class CallableReference implements KCallable, Serializable {
   private transient KCallable reflected;
   @SinceKotlin(
      version = "1.1"
   )
   protected final Object receiver;
   @SinceKotlin(
      version = "1.4"
   )
   private final Class owner;
   @SinceKotlin(
      version = "1.4"
   )
   private final String name;
   @SinceKotlin(
      version = "1.4"
   )
   private final String signature;
   @SinceKotlin(
      version = "1.4"
   )
   private final boolean isTopLevel;
   @SinceKotlin(
      version = "1.1"
   )
   public static final Object NO_RECEIVER;

   public CallableReference() {
      this(NO_RECEIVER);
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected CallableReference(Object receiver) {
      this(receiver, (Class)null, (String)null, (String)null, false);
   }

   @SinceKotlin(
      version = "1.4"
   )
   protected CallableReference(Object receiver, Class owner, String name, String signature, boolean isTopLevel) {
      this.receiver = receiver;
      this.owner = owner;
      this.name = name;
      this.signature = signature;
      this.isTopLevel = isTopLevel;
   }

   protected abstract KCallable computeReflected();

   @SinceKotlin(
      version = "1.1"
   )
   public Object getBoundReceiver() {
      return this.receiver;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public KCallable compute() {
      KCallable result = this.reflected;
      if (result == null) {
         result = this.computeReflected();
         this.reflected = result;
      }

      return result;
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KCallable getReflected() {
      KCallable result = this.compute();
      if (result == this) {
         throw new KotlinReflectionNotSupportedError();
      } else {
         return result;
      }
   }

   public KDeclarationContainer getOwner() {
      return (KDeclarationContainer)(this.owner == null ? null : (this.isTopLevel ? Reflection.getOrCreateKotlinPackage(this.owner) : Reflection.getOrCreateKotlinClass(this.owner)));
   }

   public String getName() {
      return this.name;
   }

   public String getSignature() {
      return this.signature;
   }

   public List getParameters() {
      return this.getReflected().getParameters();
   }

   public KType getReturnType() {
      return this.getReflected().getReturnType();
   }

   public List getAnnotations() {
      return this.getReflected().getAnnotations();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public List getTypeParameters() {
      return this.getReflected().getTypeParameters();
   }

   public Object call(Object... args) {
      return this.getReflected().call(args);
   }

   public Object callBy(Map args) {
      return this.getReflected().callBy(args);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public KVisibility getVisibility() {
      return this.getReflected().getVisibility();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isFinal() {
      return this.getReflected().isFinal();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isOpen() {
      return this.getReflected().isOpen();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isAbstract() {
      return this.getReflected().isAbstract();
   }

   @SinceKotlin(
      version = "1.3"
   )
   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }

   static {
      NO_RECEIVER = CallableReference.NoReceiver.INSTANCE;
   }

   @SinceKotlin(
      version = "1.2"
   )
   private static class NoReceiver implements Serializable {
      private static final NoReceiver INSTANCE = new NoReceiver();

      private Object readResolve() throws ObjectStreamException {
         return INSTANCE;
      }
   }
}
