package jdk.nashorn.internal.runtime.linker;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.ECMAErrors;

final class ClassAndLoader {
   private static final AccessControlContext GET_LOADER_ACC_CTXT = createPermAccCtxt("getClassLoader");
   private final Class representativeClass;
   private ClassLoader loader;
   private boolean loaderRetrieved;

   static AccessControlContext createPermAccCtxt(String... permNames) {
      Permissions perms = new Permissions();

      for(String permName : permNames) {
         perms.add(new RuntimePermission(permName));
      }

      return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, perms)});
   }

   ClassAndLoader(Class representativeClass, boolean retrieveLoader) {
      this.representativeClass = representativeClass;
      if (retrieveLoader) {
         this.retrieveLoader();
      }

   }

   Class getRepresentativeClass() {
      return this.representativeClass;
   }

   boolean canSee(ClassAndLoader other) {
      try {
         Class<?> otherClass = other.getRepresentativeClass();
         return Class.forName(otherClass.getName(), false, this.getLoader()) == otherClass;
      } catch (ClassNotFoundException var3) {
         return false;
      }
   }

   ClassLoader getLoader() {
      if (!this.loaderRetrieved) {
         this.retrieveLoader();
      }

      return this.getRetrievedLoader();
   }

   ClassLoader getRetrievedLoader() {
      assert this.loaderRetrieved;

      return this.loader;
   }

   private void retrieveLoader() {
      this.loader = this.representativeClass.getClassLoader();
      this.loaderRetrieved = true;
   }

   public boolean equals(Object obj) {
      return obj instanceof ClassAndLoader && ((ClassAndLoader)obj).getRetrievedLoader() == this.getRetrievedLoader();
   }

   public int hashCode() {
      return System.identityHashCode(this.getRetrievedLoader());
   }

   static ClassAndLoader getDefiningClassAndLoader(final Class[] types) {
      return types.length == 1 ? new ClassAndLoader(types[0], false) : (ClassAndLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public ClassAndLoader run() {
            return ClassAndLoader.getDefiningClassAndLoaderPrivileged(types);
         }
      }, GET_LOADER_ACC_CTXT);
   }

   static ClassAndLoader getDefiningClassAndLoaderPrivileged(Class[] types) {
      Collection<ClassAndLoader> maximumVisibilityLoaders = getMaximumVisibilityLoaders(types);
      Iterator<ClassAndLoader> it = maximumVisibilityLoaders.iterator();
      if (maximumVisibilityLoaders.size() == 1) {
         return (ClassAndLoader)it.next();
      } else {
         assert maximumVisibilityLoaders.size() > 1;

         StringBuilder b = new StringBuilder();
         b.append(((ClassAndLoader)it.next()).getRepresentativeClass().getCanonicalName());

         while(it.hasNext()) {
            b.append(", ").append(((ClassAndLoader)it.next()).getRepresentativeClass().getCanonicalName());
         }

         throw ECMAErrors.typeError("extend.ambiguous.defining.class", b.toString());
      }
   }

   private static Collection getMaximumVisibilityLoaders(Class[] types) {
      List<ClassAndLoader> maximumVisibilityLoaders = new LinkedList();

      label28:
      for(ClassAndLoader maxCandidate : getClassLoadersForTypes(types)) {
         Iterator<ClassAndLoader> it = maximumVisibilityLoaders.iterator();

         while(it.hasNext()) {
            ClassAndLoader existingMax = (ClassAndLoader)it.next();
            boolean candidateSeesExisting = maxCandidate.canSee(existingMax);
            boolean exitingSeesCandidate = existingMax.canSee(maxCandidate);
            if (candidateSeesExisting) {
               if (!exitingSeesCandidate) {
                  it.remove();
               }
            } else if (exitingSeesCandidate) {
               continue label28;
            }
         }

         maximumVisibilityLoaders.add(maxCandidate);
      }

      return maximumVisibilityLoaders;
   }

   private static Collection getClassLoadersForTypes(Class[] types) {
      Map<ClassAndLoader, ClassAndLoader> classesAndLoaders = new LinkedHashMap();

      for(Class c : types) {
         ClassAndLoader cl = new ClassAndLoader(c, true);
         if (!classesAndLoaders.containsKey(cl)) {
            classesAndLoaders.put(cl, cl);
         }
      }

      return classesAndLoaders.keySet();
   }
}
