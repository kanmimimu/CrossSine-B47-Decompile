package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.modules.world.Target;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TargetCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class TargetCommand extends Command {
   public TargetCommand() {
      int $i$f$emptyArray = 0;
      super("target", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length > 1) {
         if (StringsKt.equals(args[1], "players", true)) {
            Target.INSTANCE.getPlayerValue().set(!(Boolean)Target.INSTANCE.getPlayerValue().get());
            this.alert("§7Target player toggled " + ((Boolean)Target.INSTANCE.getPlayerValue().get() ? "on" : "off") + '.');
            this.playEdit();
            return;
         }

         if (StringsKt.equals(args[1], "mobs", true)) {
            Target.INSTANCE.getMobValue().set(!(Boolean)Target.INSTANCE.getMobValue().get());
            this.alert("§7Target mobs toggled " + ((Boolean)Target.INSTANCE.getMobValue().get() ? "on" : "off") + '.');
            this.playEdit();
            return;
         }

         if (StringsKt.equals(args[1], "animals", true)) {
            Target.INSTANCE.getAnimalValue().set(!(Boolean)Target.INSTANCE.getAnimalValue().get());
            this.alert("§7Target animals toggled " + ((Boolean)Target.INSTANCE.getAnimalValue().get() ? "on" : "off") + '.');
            this.playEdit();
            return;
         }

         if (StringsKt.equals(args[1], "invisible", true)) {
            Target.INSTANCE.getInvisibleValue().set(!(Boolean)Target.INSTANCE.getInvisibleValue().get());
            this.alert("§7Target Invisible toggled " + ((Boolean)Target.INSTANCE.getInvisibleValue().get() ? "on" : "off") + '.');
            this.playEdit();
            return;
         }
      }

      this.chatSyntax("target <players/mobs/animals/invisible>");
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         if (args.length == 1) {
            String[] var2 = new String[]{"players", "mobs", "animals", "invisible"};
            Iterable var11 = (Iterable)CollectionsKt.listOf(var2);
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : var11) {
               String it = (String)element$iv$iv;
               int var10 = 0;
               if (StringsKt.startsWith(it, args[0], true)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            var10000 = (List)destination$iv$iv;
         } else {
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
