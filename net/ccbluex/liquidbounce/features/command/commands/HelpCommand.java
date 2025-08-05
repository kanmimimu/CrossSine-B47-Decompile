package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import joptsimple.internal.Strings;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HelpCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "CrossSine"}
)
public final class HelpCommand extends Command {
   public HelpCommand() {
      int $i$f$emptyArray = 0;
      super("help", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      int page = 1;
      if (args.length > 1) {
         try {
            page = Integer.parseInt(args[1]);
         } catch (NumberFormatException var17) {
            this.chatSyntaxError();
         }
      }

      if (page <= 0) {
         this.alert("The number you have entered is too low, it must be over 0");
      } else {
         double maxPageDouble = (double)CrossSine.INSTANCE.getCommandManager().getCommands().size() / (double)8.0F;
         int maxPage = maxPageDouble > (double)((int)maxPageDouble) ? (int)maxPageDouble + 1 : (int)maxPageDouble;
         if (page > maxPage) {
            this.alert("The number you have entered is too big, it must be under " + maxPage + '.');
         } else {
            this.alert("§c§lHelp");
            ClientUtils.INSTANCE.displayChatMessage("§7> Page: §8" + page + " / " + maxPage);
            Map $this$map$iv = (Map)CrossSine.INSTANCE.getCommandManager().getCommands();
            int $i$f$map = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.size()));
            int $i$f$mapTo = 0;

            for(Map.Entry item$iv$iv : $this$map$iv.entrySet()) {
               int var15 = 0;
               destination$iv$iv.add((Command)item$iv$iv.getValue());
            }

            Iterable $this$sortedBy$iv = (Iterable)CollectionsKt.distinct((Iterable)((List)destination$iv$iv));
            $i$f$map = 0;
            List commands = CollectionsKt.sortedWith($this$sortedBy$iv, new HelpCommand$execute$$inlined$sortedBy$1());

            for(int i = 8 * (page - 1); i < 8 * page && i < commands.size(); ++i) {
               Command command = (Command)commands.get(i);
               ClientUtils.INSTANCE.displayChatMessage("§6> §7" + CrossSine.INSTANCE.getCommandManager().getPrefix() + command.getCommand() + (command.getAlias().length == 0 ? "" : " §7(§8" + Strings.join(command.getAlias(), "§7, §8") + "§7)"));
            }

            ClientUtils.INSTANCE.displayChatMessage("§a------------\n§7> §c" + CrossSine.INSTANCE.getCommandManager().getPrefix() + "help §8<§7§lpage§8>");
         }
      }
   }
}
