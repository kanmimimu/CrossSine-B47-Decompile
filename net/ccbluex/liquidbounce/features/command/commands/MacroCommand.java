package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0004H\u0002¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/MacroCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "save", "CrossSine"}
)
public final class MacroCommand extends Command {
   public MacroCommand() {
      String[] var1 = new String[]{"m"};
      super("macro", var1);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length <= 1) {
         this.chatSyntax("macro <add/remove/list>");
      } else {
         String arg1 = args[1];
         Intrinsics.checkNotNullExpressionValue(comm, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (comm) {
            case "remove":
               if (args.length > 2) {
                  List var38;
                  if (StringsKt.startsWith$default(args[2], ".", false, 2, (Object)null)) {
                     Iterable $this$filter$iv = (Iterable)CrossSine.INSTANCE.getMacroManager().getMacros();
                     int $i$f$filter = 0;
                     Collection destination$iv$iv = (Collection)(new ArrayList());
                     int $i$f$filterTo = 0;

                     for(Object element$iv$iv : $this$filter$iv) {
                        Macro it = (Macro)element$iv$iv;
                        int it = 0;
                        if (Intrinsics.areEqual((Object)it.getCommand(), (Object)StringUtils.toCompleteString(args, 2))) {
                           destination$iv$iv.add(element$iv$iv);
                        }
                     }

                     var38 = (List)destination$iv$iv;
                  } else {
                     String var24 = args[2].toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var24, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                     int key = Keyboard.getKeyIndex(var24);
                     Iterable $this$filter$iv = (Iterable)CrossSine.INSTANCE.getMacroManager().getMacros();
                     int $i$f$filter = 0;
                     Collection destination$iv$iv = (Collection)(new ArrayList());
                     int $i$f$filterTo = 0;

                     for(Object element$iv$iv : $this$filter$iv) {
                        Macro it = (Macro)element$iv$iv;
                        int var13 = 0;
                        if (it.getKey() == key) {
                           destination$iv$iv.add(element$iv$iv);
                        }
                     }

                     var38 = (List)destination$iv$iv;
                  }

                  Iterable $this$forEach$iv = (Iterable)var38;
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     Macro it = (Macro)element$iv;
                     int var34 = 0;
                     CrossSine.INSTANCE.getMacroManager().getMacros().remove(it);
                     this.alert("Remove macro " + it.getCommand() + '.');
                  }

                  this.save();
               } else {
                  this.chatSyntax("macro remove <macro/key>");
               }

               return;
               break;
            case "add":
               if (args.length > 3) {
                  String var23 = args[2].toUpperCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var23, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                  int key = Keyboard.getKeyIndex(var23);
                  if (key != 0) {
                     comm = StringUtils.toCompleteString(args, 3);
                     Intrinsics.checkNotNullExpressionValue(comm, "comm");
                     if (!StringsKt.startsWith$default(comm, ".", false, 2, (Object)null)) {
                        comm = Intrinsics.stringPlus(".", comm);
                     }

                     ArrayList var10000 = CrossSine.INSTANCE.getMacroManager().getMacros();
                     Intrinsics.checkNotNullExpressionValue(comm, "comm");
                     var10000.add(new Macro(key, comm));
                     this.alert("Bound macro " + comm + " to key " + Keyboard.getKeyName(key) + '.');
                  } else {
                     this.alert("Unknown key to bind macro.");
                  }

                  this.save();
               } else {
                  this.chatSyntax("macro add <key> <macro>");
               }

               return;
               break;
            case "list":
               this.alert("Macros:");
               Iterable $this$forEach$iv = (Iterable)CrossSine.INSTANCE.getMacroManager().getMacros();
               int $i$f$forEach = 0;

               for(Object element$iv : $this$forEach$iv) {
                  Macro it = (Macro)element$iv;
                  int $i$f$filterTo = 0;
                  this.alert("key=" + Keyboard.getKeyName(it.getKey()) + ", command=" + it.getCommand());
               }

               return;
         }

         this.chatSyntax("macro <add/remove/list>");
      }
   }

   private final void save() {
      CrossSine.INSTANCE.getConfigManager().smartSave();
      this.playEdit();
   }
}
