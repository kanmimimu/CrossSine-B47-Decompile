package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/BindsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "CrossSine"}
)
public final class BindsCommand extends Command {
   public BindsCommand() {
      int $i$f$emptyArray = 0;
      super("binds", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length > 1 && StringsKt.equals(args[1], "clear", true)) {
         for(Module module : CrossSine.INSTANCE.getModuleManager().getModules()) {
            module.setKeyBind(0);
         }

         this.alert("Removed all binds.");
      } else {
         this.alert("§c§lBinds");
         Iterable $this$filter$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            Module it = (Module)element$iv$iv;
            int var10 = 0;
            if (it.getKeyBind() != 0) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $this$filter$iv = (Iterable)((List)destination$iv$iv);
         $i$f$filter = 0;

         for(Object element$iv : $this$filter$iv) {
            Module it = (Module)element$iv;
            int var17 = 0;
            ClientUtils.INSTANCE.displayChatMessage("§6> §c" + it.getName() + ": §a§l" + Keyboard.getKeyName(it.getKeyBind()));
         }

         this.chatSyntax("binds clear");
      }
   }
}
