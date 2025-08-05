package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/BindCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class BindCommand extends Command {
   public BindCommand() {
      int $i$f$emptyArray = 0;
      super("bind", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length > 1) {
         Module module = CrossSine.INSTANCE.getModuleManager().getModule(args[1]);
         if (module == null) {
            this.alert("Module §l" + args[1] + "§r not found.");
         } else {
            if (args.length > 2) {
               String var4 = args[2].toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toUpperCase(Locale.ROOT)");
               int key = Keyboard.getKeyIndex(var4);
               module.setKeyBind(key);
               this.alert("Bound module §l" + module.getName() + "§r to key §a§l" + Keyboard.getKeyName(key) + "§3.");
               this.playEdit();
            } else {
               CrossSine.INSTANCE.getModuleManager().setPendingBindModule(module);
               this.alert(Intrinsics.stringPlus("Press any key to bind module ", module.getName()));
            }

         }
      } else {
         String[] module = new String[]{"<module> <key>", "<module> none"};
         this.chatSyntax(module);
      }
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         String moduleName = args[0];
         List var10000;
         if (args.length == 1) {
            Iterable $this$map$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
            int $i$f$map = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
            int $i$f$mapTo = 0;

            for(Object item$iv$iv : $this$map$iv) {
               Module it = (Module)item$iv$iv;
               int var11 = 0;
               destination$iv$iv.add(it.getName());
            }

            $this$map$iv = (Iterable)((List)destination$iv$iv);
            $i$f$map = 0;
            destination$iv$iv = (Collection)(new ArrayList());
            $i$f$mapTo = 0;

            for(Object element$iv$iv : $this$map$iv) {
               String it = (String)element$iv$iv;
               int var20 = 0;
               if (StringsKt.startsWith(it, moduleName, true)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            var10000 = CollectionsKt.toList((Iterable)((List)destination$iv$iv));
         } else {
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
