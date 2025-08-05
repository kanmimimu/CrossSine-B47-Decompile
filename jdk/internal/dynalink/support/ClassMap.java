package jdk.internal.dynalink.support;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class ClassMap {
   private final ConcurrentMap map = new ConcurrentHashMap();
   private final Map weakMap = new WeakHashMap();
   private final ClassLoader classLoader;

   protected ClassMap(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   protected abstract Object computeValue(Class var1);

   public Object get(final Class clazz) {
      T v = (T)this.map.get(clazz);
      if (v != null) {
         return v;
      } else {
         Reference<T> ref;
         synchronized(this.weakMap) {
            ref = (Reference)this.weakMap.get(clazz);
         }

         if (ref != null) {
            T refv = (T)ref.get();
            if (refv != null) {
               return refv;
            }
         }

         T newV = (T)this.computeValue(clazz);

         assert newV != null;

         ClassLoader clazzLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
            public ClassLoader run() {
               return clazz.getClassLoader();
            }
         }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
         if (Guards.canReferenceDirectly(this.classLoader, clazzLoader)) {
            T oldV = (T)this.map.putIfAbsent(clazz, newV);
            return oldV != null ? oldV : newV;
         } else {
            synchronized(this.weakMap) {
               ref = (Reference)this.weakMap.get(clazz);
               if (ref != null) {
                  T oldV = (T)ref.get();
                  if (oldV != null) {
                     return oldV;
                  }
               }

               this.weakMap.put(clazz, new SoftReference(newV));
               return newV;
            }
         }
      }
   }
}
