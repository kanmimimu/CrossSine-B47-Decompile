package jdk.internal.dynalink.support;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;

public class AutoDiscovery {
   private AutoDiscovery() {
   }

   public static List loadLinkers() {
      return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class));
   }

   public static List loadLinkers(ClassLoader cl) {
      return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class, cl));
   }

   private static List getLinkers(ServiceLoader loader) {
      List<T> list = new LinkedList();

      for(Object linker : loader) {
         list.add(linker);
      }

      return list;
   }
}
