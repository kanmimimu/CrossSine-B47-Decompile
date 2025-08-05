package net.ccbluex.liquidbounce.script.remapper.injection.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/utils/NodeUtils;", "", "()V", "toNodes", "Lorg/objectweb/asm/tree/InsnList;", "nodes", "", "Lorg/objectweb/asm/tree/AbstractInsnNode;", "([Lorg/objectweb/asm/tree/AbstractInsnNode;)Lorg/objectweb/asm/tree/InsnList;", "CrossSine"}
)
public final class NodeUtils {
   @NotNull
   public static final NodeUtils INSTANCE = new NodeUtils();

   private NodeUtils() {
   }

   @NotNull
   public final InsnList toNodes(@NotNull AbstractInsnNode... nodes) {
      Intrinsics.checkNotNullParameter(nodes, "nodes");
      InsnList insnList = new InsnList();
      int var3 = 0;
      int var4 = nodes.length;

      while(var3 < var4) {
         AbstractInsnNode node = nodes[var3];
         ++var3;
         insnList.add(node);
      }

      return insnList;
   }
}
