package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodType;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.support.TypeUtilities;

class ApplicableOverloadedMethods {
   private final List methods = new LinkedList();
   private final boolean varArgs;
   static final ApplicabilityTest APPLICABLE_BY_SUBTYPING = new ApplicabilityTest() {
      boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
         MethodType methodType = method.getMethodType();
         int methodArity = methodType.parameterCount();
         if (methodArity != callSiteType.parameterCount()) {
            return false;
         } else {
            for(int i = 1; i < methodArity; ++i) {
               if (!TypeUtilities.isSubtype(callSiteType.parameterType(i), methodType.parameterType(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   };
   static final ApplicabilityTest APPLICABLE_BY_METHOD_INVOCATION_CONVERSION = new ApplicabilityTest() {
      boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
         MethodType methodType = method.getMethodType();
         int methodArity = methodType.parameterCount();
         if (methodArity != callSiteType.parameterCount()) {
            return false;
         } else {
            for(int i = 1; i < methodArity; ++i) {
               if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i), methodType.parameterType(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   };
   static final ApplicabilityTest APPLICABLE_BY_VARIABLE_ARITY = new ApplicabilityTest() {
      boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
         if (!method.isVarArgs()) {
            return false;
         } else {
            MethodType methodType = method.getMethodType();
            int methodArity = methodType.parameterCount();
            int fixArity = methodArity - 1;
            int callSiteArity = callSiteType.parameterCount();
            if (fixArity > callSiteArity) {
               return false;
            } else {
               for(int i = 1; i < fixArity; ++i) {
                  if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i), methodType.parameterType(i))) {
                     return false;
                  }
               }

               Class<?> varArgType = methodType.parameterType(fixArity).getComponentType();

               for(int i = fixArity; i < callSiteArity; ++i) {
                  if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i), varArgType)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   };

   ApplicableOverloadedMethods(List methods, MethodType callSiteType, ApplicabilityTest test) {
      for(SingleDynamicMethod m : methods) {
         if (test.isApplicable(callSiteType, m)) {
            this.methods.add(m);
         }
      }

      this.varArgs = test == APPLICABLE_BY_VARIABLE_ARITY;
   }

   List getMethods() {
      return this.methods;
   }

   List findMaximallySpecificMethods() {
      return MaximallySpecific.getMaximallySpecificMethods(this.methods, this.varArgs);
   }

   abstract static class ApplicabilityTest {
      abstract boolean isApplicable(MethodType var1, SingleDynamicMethod var2);
   }
}
