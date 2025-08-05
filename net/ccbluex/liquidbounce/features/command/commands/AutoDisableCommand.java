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
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016¢\u0006\u0002\u0010\nJ!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016¢\u0006\u0002\u0010\rR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/AutoDisableCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "modes", "", "", "[Ljava/lang/String;", "execute", "", "args", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class AutoDisableCommand extends Command {
   @NotNull
   private final String[] modes;

   public AutoDisableCommand() {
      String[] thisCollection$iv = new String[]{"ad"};
      super("autodisable", thisCollection$iv);
      EnumAutoDisableType[] $this$toTypedArray$iv = EnumAutoDisableType.values();
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList($this$toTypedArray$iv.length));
      int $i$f$mapTo = 0;
      EnumAutoDisableType[] var6 = $this$toTypedArray$iv;
      int var7 = 0;
      int var8 = $this$toTypedArray$iv.length;

      while(var7 < var8) {
         Object item$iv$iv = var6[var7];
         ++var7;
         int var11 = 0;
         String var12 = ((EnumAutoDisableType)item$iv$iv).name().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var12, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         destination$iv$iv.add(var12);
      }

      Collection thisCollection$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var10001 = thisCollection$iv.toArray(new String[0]);
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         this.modes = (String[])var10001;
      }
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length > 2) {
         Module module = CrossSine.INSTANCE.getModuleManager().getModule(args[1]);
         if (module == null) {
            this.alert("Module '" + args[1] + "' not found.");
         } else {
            Module var5 = module;

            EnumAutoDisableType var3;
            Module var10000;
            try {
               var10000 = var5;
               String var4 = args[2].toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toUpperCase(Locale.ROOT)");
               var3 = EnumAutoDisableType.valueOf(var4);
            } catch (IllegalArgumentException var6) {
               var10000 = module;
               var3 = EnumAutoDisableType.NONE;
            }

            var10000.setAutoDisable(var3);
            this.playEdit();
            this.alert("Set module §l" + module.getName() + "§r AutoDisable state to §l" + module.getAutoDisable() + "§r.");
         }
      } else {
         this.chatSyntax("autodisable <module> [" + StringUtils.toCompleteString(this.modes, 0, ",") + ']');
      }
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         switch (args.length) {
            case 1:
               Iterable $this$filter$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
               int $i$f$map = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10)));
               int $i$f$mapTo = 0;

               for(Object item$iv$iv : $this$filter$iv) {
                  Module it = (Module)item$iv$iv;
                  int var28 = 0;
                  destination$iv$iv.add(it.getName());
               }

               $this$filter$iv = (Iterable)((List)destination$iv$iv);
               $i$f$map = 0;
               destination$iv$iv = (Collection)(new ArrayList());
               $i$f$mapTo = 0;

               for(Object element$iv$iv : $this$filter$iv) {
                  String it = (String)element$iv$iv;
                  int element$iv$iv = 0;
                  if (StringsKt.startsWith(it, args[0], true)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               var10000 = CollectionsKt.toList((Iterable)((List)destination$iv$iv));
               break;
            case 2:
               Object[] $this$filter$iv = this.modes;
               int $i$f$filter = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList());
               int $i$f$filterTo = 0;
               Object var7 = $this$filter$iv;
               int item$iv$iv = 0;
               int it = $this$filter$iv.length;

               while(item$iv$iv < it) {
                  Object element$iv$iv = ((Object[])var7)[item$iv$iv];
                  ++item$iv$iv;
                  int var12 = 0;
                  if (StringsKt.startsWith((String)element$iv$iv, args[1], true)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               var10000 = (List)destination$iv$iv;
               break;
            default:
               var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
