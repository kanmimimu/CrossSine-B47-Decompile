package jdk.internal.dynalink.support;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

public class CompositeGuardingDynamicLinker implements GuardingDynamicLinker, Serializable {
   private static final long serialVersionUID = 1L;
   private final GuardingDynamicLinker[] linkers;

   public CompositeGuardingDynamicLinker(Iterable linkers) {
      List<GuardingDynamicLinker> l = new LinkedList();

      for(GuardingDynamicLinker linker : linkers) {
         l.add(linker);
      }

      this.linkers = (GuardingDynamicLinker[])l.toArray(new GuardingDynamicLinker[l.size()]);
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      for(GuardingDynamicLinker linker : this.linkers) {
         GuardedInvocation invocation = linker.getGuardedInvocation(linkRequest, linkerServices);
         if (invocation != null) {
            return invocation;
         }
      }

      return null;
   }
}
