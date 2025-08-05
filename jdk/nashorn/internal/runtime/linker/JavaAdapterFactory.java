package jdk.nashorn.internal.runtime.linker;

import [Ljava.lang.Class;;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

public final class JavaAdapterFactory {
   private static final ProtectionDomain MINIMAL_PERMISSION_DOMAIN = createMinimalPermissionDomain();
   private static final AccessControlContext CREATE_ADAPTER_INFO_ACC_CTXT = ClassAndLoader.createPermAccCtxt("createClassLoader", "getClassLoader", "accessDeclaredMembers", "accessClassInPackage.jdk.nashorn.internal.runtime");
   private static final ClassValue ADAPTER_INFO_MAPS = new ClassValue() {
      protected Map computeValue(Class type) {
         return new HashMap();
      }
   };

   public static StaticClass getAdapterClassFor(Class[] types, ScriptObject classOverrides, MethodHandles.Lookup lookup) {
      return getAdapterClassFor(types, classOverrides, getProtectionDomain(lookup));
   }

   private static StaticClass getAdapterClassFor(Class[] types, ScriptObject classOverrides, ProtectionDomain protectionDomain) {
      assert types != null && types.length > 0;

      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         for(Class type : types) {
            Context.checkPackageAccess(type);
            ReflectionCheckLinker.checkReflectionAccess(type, true);
         }
      }

      return getAdapterInfo(types).getAdapterClass(classOverrides, protectionDomain);
   }

   private static ProtectionDomain getProtectionDomain(MethodHandles.Lookup lookup) {
      return (lookup.lookupModes() & 2) == 0 ? MINIMAL_PERMISSION_DOMAIN : getProtectionDomain(lookup.lookupClass());
   }

   private static ProtectionDomain getProtectionDomain(final Class clazz) {
      return (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction() {
         public ProtectionDomain run() {
            return clazz.getProtectionDomain();
         }
      });
   }

   public static MethodHandle getConstructor(Class sourceType, Class targetType, MethodHandles.Lookup lookup) throws Exception {
      StaticClass adapterClass = getAdapterClassFor(new Class[]{targetType}, (ScriptObject)null, (MethodHandles.Lookup)lookup);
      return Lookup.MH.bindTo(Bootstrap.getLinkerServices().getGuardedInvocation(new LinkRequestImpl(NashornCallSiteDescriptor.get(lookup, "dyn:new", MethodType.methodType(targetType, StaticClass.class, sourceType), 0), (Object)null, 0, false, new Object[]{adapterClass, null})).getInvocation(), adapterClass);
   }

   static boolean isAutoConvertibleFromFunction(Class clazz) {
      return getAdapterInfo(new Class[]{clazz}).autoConvertibleFromFunction;
   }

   private static AdapterInfo getAdapterInfo(Class[] types) {
      ClassAndLoader definingClassAndLoader = ClassAndLoader.getDefiningClassAndLoader(types);
      Map<List<Class<?>>, AdapterInfo> adapterInfoMap = (Map)ADAPTER_INFO_MAPS.get(definingClassAndLoader.getRepresentativeClass());
      List<Class<?>> typeList = types.length == 1 ? Collections.singletonList(types[0]) : Arrays.asList(((Class;)types).clone());
      synchronized(adapterInfoMap) {
         AdapterInfo adapterInfo = (AdapterInfo)adapterInfoMap.get(typeList);
         if (adapterInfo == null) {
            adapterInfo = createAdapterInfo(types, definingClassAndLoader);
            adapterInfoMap.put(typeList, adapterInfo);
         }

         return adapterInfo;
      }
   }

   private static AdapterInfo createAdapterInfo(final Class[] types, final ClassAndLoader definingClassAndLoader) {
      Class<?> superClass = null;
      final List<Class<?>> interfaces = new ArrayList(types.length);

      for(Class t : types) {
         int mod = t.getModifiers();
         if (!t.isInterface()) {
            if (superClass != null) {
               return new AdapterInfo(AdaptationResult.Outcome.ERROR_MULTIPLE_SUPERCLASSES, t.getCanonicalName() + " and " + superClass.getCanonicalName());
            }

            if (Modifier.isFinal(mod)) {
               return new AdapterInfo(AdaptationResult.Outcome.ERROR_FINAL_CLASS, t.getCanonicalName());
            }

            superClass = t;
         } else {
            if (interfaces.size() > 65535) {
               throw new IllegalArgumentException("interface limit exceeded");
            }

            interfaces.add(t);
         }

         if (!Modifier.isPublic(mod)) {
            return new AdapterInfo(AdaptationResult.Outcome.ERROR_NON_PUBLIC_CLASS, t.getCanonicalName());
         }
      }

      final Class<?> effectiveSuperClass = superClass == null ? Object.class : superClass;
      return (AdapterInfo)AccessController.doPrivileged(new PrivilegedAction() {
         public AdapterInfo run() {
            try {
               return new AdapterInfo(effectiveSuperClass, interfaces, definingClassAndLoader);
            } catch (AdaptationException e) {
               return new AdapterInfo(e.getAdaptationResult());
            } catch (RuntimeException e) {
               return new AdapterInfo(new AdaptationResult(AdaptationResult.Outcome.ERROR_OTHER, new String[]{Arrays.toString(types), e.toString()}));
            }
         }
      }, CREATE_ADAPTER_INFO_ACC_CTXT);
   }

   private static ProtectionDomain createMinimalPermissionDomain() {
      Permissions permissions = new Permissions();
      permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.objects"));
      permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime"));
      permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.linker"));
      return new ProtectionDomain(new CodeSource((URL)null, (CodeSigner[])null), permissions);
   }

   private static class AdapterInfo {
      private static final ClassAndLoader SCRIPT_OBJECT_LOADER = new ClassAndLoader(ScriptFunction.class, true);
      private final ClassLoader commonLoader;
      private final JavaAdapterClassLoader classAdapterGenerator;
      private final JavaAdapterClassLoader instanceAdapterGenerator;
      private final Map instanceAdapters;
      final boolean autoConvertibleFromFunction;
      final AdaptationResult adaptationResult;

      AdapterInfo(Class superClass, List interfaces, ClassAndLoader definingLoader) throws AdaptationException {
         this.instanceAdapters = new ConcurrentHashMap();
         this.commonLoader = findCommonLoader(definingLoader);
         JavaAdapterBytecodeGenerator gen = new JavaAdapterBytecodeGenerator(superClass, interfaces, this.commonLoader, false);
         this.autoConvertibleFromFunction = gen.isAutoConvertibleFromFunction();
         this.instanceAdapterGenerator = gen.createAdapterClassLoader();
         this.classAdapterGenerator = (new JavaAdapterBytecodeGenerator(superClass, interfaces, this.commonLoader, true)).createAdapterClassLoader();
         this.adaptationResult = AdaptationResult.SUCCESSFUL_RESULT;
      }

      AdapterInfo(AdaptationResult.Outcome outcome, String classList) {
         this(new AdaptationResult(outcome, new String[]{classList}));
      }

      AdapterInfo(AdaptationResult adaptationResult) {
         this.instanceAdapters = new ConcurrentHashMap();
         this.commonLoader = null;
         this.classAdapterGenerator = null;
         this.instanceAdapterGenerator = null;
         this.autoConvertibleFromFunction = false;
         this.adaptationResult = adaptationResult;
      }

      StaticClass getAdapterClass(ScriptObject classOverrides, ProtectionDomain protectionDomain) {
         if (this.adaptationResult.getOutcome() != AdaptationResult.Outcome.SUCCESS) {
            throw this.adaptationResult.typeError();
         } else {
            return classOverrides == null ? this.getInstanceAdapterClass(protectionDomain) : this.getClassAdapterClass(classOverrides, protectionDomain);
         }
      }

      private StaticClass getInstanceAdapterClass(ProtectionDomain protectionDomain) {
         CodeSource codeSource = protectionDomain.getCodeSource();
         if (codeSource == null) {
            codeSource = JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN.getCodeSource();
         }

         StaticClass instanceAdapterClass = (StaticClass)this.instanceAdapters.get(codeSource);
         if (instanceAdapterClass != null) {
            return instanceAdapterClass;
         } else {
            ProtectionDomain effectiveDomain = codeSource.equals(JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN.getCodeSource()) ? JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN : protectionDomain;
            instanceAdapterClass = this.instanceAdapterGenerator.generateClass(this.commonLoader, effectiveDomain);
            StaticClass existing = (StaticClass)this.instanceAdapters.putIfAbsent(codeSource, instanceAdapterClass);
            return existing == null ? instanceAdapterClass : existing;
         }
      }

      private StaticClass getClassAdapterClass(ScriptObject classOverrides, ProtectionDomain protectionDomain) {
         JavaAdapterServices.setClassOverrides(classOverrides);

         StaticClass var3;
         try {
            var3 = this.classAdapterGenerator.generateClass(this.commonLoader, protectionDomain);
         } finally {
            JavaAdapterServices.setClassOverrides((ScriptObject)null);
         }

         return var3;
      }

      private static ClassLoader findCommonLoader(ClassAndLoader classAndLoader) throws AdaptationException {
         if (classAndLoader.canSee(SCRIPT_OBJECT_LOADER)) {
            return classAndLoader.getLoader();
         } else if (SCRIPT_OBJECT_LOADER.canSee(classAndLoader)) {
            return SCRIPT_OBJECT_LOADER.getLoader();
         } else {
            throw new AdaptationException(AdaptationResult.Outcome.ERROR_NO_COMMON_LOADER, classAndLoader.getRepresentativeClass().getCanonicalName());
         }
      }
   }
}
