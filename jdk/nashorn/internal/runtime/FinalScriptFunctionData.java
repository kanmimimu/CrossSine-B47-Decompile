package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.List;

final class FinalScriptFunctionData extends ScriptFunctionData {
   private static final long serialVersionUID = -930632846167768864L;

   FinalScriptFunctionData(String name, int arity, List functions, int flags) {
      super(name, arity, flags);
      this.code.addAll(functions);

      assert !this.needsCallee();

   }

   FinalScriptFunctionData(String name, MethodHandle mh, Specialization[] specs, int flags) {
      super(name, methodHandleArity(mh), flags);
      this.addInvoker(mh);
      if (specs != null) {
         for(Specialization spec : specs) {
            this.addInvoker(spec.getMethodHandle(), spec);
         }
      }

   }

   protected boolean needsCallee() {
      boolean needsCallee = ((CompiledFunction)this.code.getFirst()).needsCallee();

      assert this.allNeedCallee(needsCallee);

      return needsCallee;
   }

   private boolean allNeedCallee(boolean needCallee) {
      for(CompiledFunction inv : this.code) {
         if (inv.needsCallee() != needCallee) {
            return false;
         }
      }

      return true;
   }

   CompiledFunction getBest(MethodType callSiteType, ScriptObject runtimeScope, Collection forbidden, boolean linkLogicOkay) {
      assert this.isValidCallSite(callSiteType) : callSiteType;

      CompiledFunction best = null;

      for(CompiledFunction candidate : this.code) {
         if ((linkLogicOkay || !candidate.hasLinkLogic()) && !forbidden.contains(candidate) && candidate.betterThanFinal(best, callSiteType)) {
            best = candidate;
         }
      }

      return best;
   }

   MethodType getGenericType() {
      int max = 0;

      for(CompiledFunction fn : this.code) {
         MethodType t = fn.type();
         if (ScriptFunctionData.isVarArg(t)) {
            return MethodType.genericMethodType(2, true);
         }

         int paramCount = t.parameterCount() - (ScriptFunctionData.needsCallee(t) ? 1 : 0);
         if (paramCount > max) {
            max = paramCount;
         }
      }

      return MethodType.genericMethodType(max + 1);
   }

   private CompiledFunction addInvoker(MethodHandle mh, Specialization specialization) {
      assert !needsCallee(mh);

      CompiledFunction invoker;
      if (isConstructor(mh)) {
         assert this.isConstructor();

         invoker = CompiledFunction.createBuiltInConstructor(mh);
      } else {
         invoker = new CompiledFunction(mh, (MethodHandle)null, specialization);
      }

      this.code.add(invoker);
      return invoker;
   }

   private CompiledFunction addInvoker(MethodHandle mh) {
      return this.addInvoker(mh, (Specialization)null);
   }

   private static int methodHandleArity(MethodHandle mh) {
      return isVarArg(mh) ? 250 : mh.type().parameterCount() - 1 - (needsCallee(mh) ? 1 : 0) - (isConstructor(mh) ? 1 : 0);
   }

   private static boolean isConstructor(MethodHandle mh) {
      return mh.type().parameterCount() >= 1 && mh.type().parameterType(0) == Boolean.TYPE;
   }
}
