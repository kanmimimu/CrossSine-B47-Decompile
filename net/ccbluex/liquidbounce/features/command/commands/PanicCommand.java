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
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/PanicCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class PanicCommand extends Command {
   public PanicCommand() {
      int $i$f$emptyArray = 0;
      super("panic", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      Iterable $this$filter$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         Module it = (Module)element$iv$iv;
         int var11 = 0;
         if (it.getState()) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      List modules = (List)destination$iv$iv;
      String msg = null;
      if (args.length > 1 && ((CharSequence)args[1]).length() > 0) {
         String $i$f$filter = args[1].toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue($i$f$filter, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         if (Intrinsics.areEqual((Object)$i$f$filter, (Object)"all")) {
            msg = "all";
         } else if (Intrinsics.areEqual((Object)$i$f$filter, (Object)"nonrender")) {
            Iterable $this$filter$iv = (Iterable)modules;
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : $this$filter$iv) {
               Module it = (Module)element$iv$iv;
               int var13 = 0;
               if (it.getCategory() != ModuleCategory.VISUAL) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            modules = (List)destination$iv$iv;
            msg = "all non-render";
         } else {
            Object[] $this$filter$iv = ModuleCategory.values();
            $i$f$filterTo = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;
            Object $i$f$filterTo = $this$filter$iv;
            int var38 = 0;
            int element$iv$iv = $this$filter$iv.length;

            while(var38 < element$iv$iv) {
               Object element$iv$iv = ((Object[])$i$f$filterTo)[var38];
               ++var38;
               int var16 = 0;
               if (StringsKt.equals(((ModuleCategory)element$iv$iv).name(), args[1], true)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            List categories = (List)destination$iv$iv;
            if (categories.isEmpty()) {
               this.chat("Category " + args[1] + " not found");
               return;
            }

            ModuleCategory category = (ModuleCategory)categories.get(0);
            Iterable $this$filter$iv = (Iterable)modules;
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : $this$filter$iv) {
               Module it = (Module)element$iv$iv;
               int it = 0;
               if (it.getCategory() == category) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            modules = (List)destination$iv$iv;
            msg = Intrinsics.stringPlus("all ", category.name());
         }

         for(Module module : modules) {
            module.setState(false);
         }

         this.chat("Disabled " + msg + " modules.");
      } else {
         this.chatSyntax("panic <all/client/combat/movement/player/visual/world>");
      }
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         if (args.length == 1) {
            String[] var2 = new String[]{"all", "client", "combat", "player", "movement", "visual", "world"};
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
