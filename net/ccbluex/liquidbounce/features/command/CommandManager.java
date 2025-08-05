package net.ccbluex.liquidbounce.features.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0005J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0019\u001a\u00020\u0005J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001d\u001a\u00020\u0005J\u001d\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010\u001fJ\u0018\u0010 \u001a\u00020\u001b2\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\"H\u0002J\u000e\u0010 \u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u0006J\u0006\u0010$\u001a\u00020\u001bJ\u000e\u0010%\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u0006R-\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\"\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006&"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "", "()V", "commands", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lkotlin/collections/HashMap;", "getCommands", "()Ljava/util/HashMap;", "latestAutoComplete", "", "getLatestAutoComplete", "()[Ljava/lang/String;", "setLatestAutoComplete", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "prefix", "", "getPrefix", "()C", "setPrefix", "(C)V", "autoComplete", "", "input", "executeCommands", "", "getCommand", "name", "getCompletions", "(Ljava/lang/String;)[Ljava/lang/String;", "registerCommand", "commandClass", "Ljava/lang/Class;", "command", "registerCommands", "unregisterCommand", "CrossSine"}
)
public final class CommandManager {
   @NotNull
   private final HashMap commands = new HashMap();
   @NotNull
   private String[] latestAutoComplete;
   private char prefix;

   public CommandManager() {
      int $i$f$emptyArray = 0;
      this.latestAutoComplete = new String[0];
      this.prefix = '.';
   }

   @NotNull
   public final HashMap getCommands() {
      return this.commands;
   }

   @NotNull
   public final String[] getLatestAutoComplete() {
      return this.latestAutoComplete;
   }

   public final void setLatestAutoComplete(@NotNull String[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.latestAutoComplete = var1;
   }

   public final char getPrefix() {
      return this.prefix;
   }

   public final void setPrefix(char var1) {
      this.prefix = var1;
   }

   public final void registerCommands() {
      Iterable $this$forEach$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".commands"), Command.class);
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Class p0 = (Class)element$iv;
         int var6 = 0;
         this.registerCommand(p0);
      }

   }

   public final void executeCommands(@NotNull String input) {
      Intrinsics.checkNotNullParameter(input, "input");
      CharSequence var10000 = (CharSequence)input;
      String[] thisCollection$iv = new String[]{" "};
      Collection $this$toTypedArray$iv = (Collection)StringsKt.split$default(var10000, thisCollection$iv, false, 0, 6, (Object)null);
      int $i$f$toTypedArray = 0;
      Object[] var9 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var9 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] args = (String[])var9;
         HashMap var10 = this.commands;
         String var6 = args[0].substring(1);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).substring(startIndex)");
         String thisCollection$iv = var6.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(thisCollection$iv, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         Command command = (Command)var10.get(thisCollection$iv);
         if (command != null) {
            command.execute(args);
         } else {
            ClientUtils.INSTANCE.displayChatMessage("§cCommand not found. Type " + this.prefix + "help to view all commands.");
         }

      }
   }

   public final boolean autoComplete(@NotNull String input) {
      Intrinsics.checkNotNullParameter(input, "input");
      String[] var2 = this.getCompletions(input);
      String[] var10001;
      if (var2 == null) {
         int $i$f$emptyArray = 0;
         var10001 = new String[0];
      } else {
         var10001 = var2;
      }

      this.latestAutoComplete = var10001;
      return StringsKt.startsWith$default((CharSequence)input, this.prefix, false, 2, (Object)null) && this.latestAutoComplete.length != 0;
   }

   private final String[] getCompletions(String input) {
      if (((CharSequence)input).length() > 0) {
         char[] thisCollection$iv = input.toCharArray();
         Intrinsics.checkNotNullExpressionValue(thisCollection$iv, "this as java.lang.String).toCharArray()");
         if (thisCollection$iv[0] == this.prefix) {
            CharSequence var10000 = (CharSequence)input;
            String[] thisCollection$iv = new String[]{" "};
            List args = StringsKt.split$default(var10000, thisCollection$iv, false, 0, 6, (Object)null);
            String[] var34;
            if (args.size() > 1) {
               String thisCollection$iv = ((String)args.get(0)).substring(1);
               Intrinsics.checkNotNullExpressionValue(thisCollection$iv, "this as java.lang.String).substring(startIndex)");
               Command command = this.getCommand(thisCollection$iv);
               List var33;
               if (command == null) {
                  var33 = null;
               } else {
                  Collection $this$toTypedArray$iv = (Collection)CollectionsKt.drop((Iterable)args, 1);
                  int $i$f$toTypedArray = 0;
                  Object[] var10001 = $this$toTypedArray$iv.toArray(new String[0]);
                  if (var10001 == null) {
                     throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                  }

                  var33 = command.tabComplete((String[])var10001);
               }

               List tabCompletions = var33;
               if (tabCompletions == null) {
                  var34 = null;
               } else {
                  Collection $this$toTypedArray$iv = (Collection)tabCompletions;
                  int $i$f$toTypedArray = 0;
                  Object[] var35 = $this$toTypedArray$iv.toArray(new String[0]);
                  if (var35 == null) {
                     throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                  }

                  var34 = (String[])var35;
               }
            } else {
               Map $this$map$iv = (Map)this.commands;
               int $i$f$map = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.size()));
               int $i$f$mapTo = 0;

               for(Map.Entry item$iv$iv : $this$map$iv.entrySet()) {
                  int var11 = 0;
                  destination$iv$iv.add(Intrinsics.stringPlus(".", item$iv$iv.getKey()));
               }

               Iterable thisCollection$iv = (Iterable)((List)destination$iv$iv);
               $i$f$map = 0;
               destination$iv$iv = (Collection)(new ArrayList());
               $i$f$mapTo = 0;

               for(Object element$iv$iv : thisCollection$iv) {
                  String it = (String)element$iv$iv;
                  int var31 = 0;
                  String var12 = it.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var12, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  var34 = var12;
                  var12 = ((String)args.get(0)).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var12, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  if (StringsKt.startsWith$default(var34, var12, false, 2, (Object)null)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               Collection thisCollection$iv = (Collection)((List)destination$iv$iv);
               $i$f$map = 0;
               Object[] var37 = thisCollection$iv.toArray(new String[0]);
               if (var37 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
               }

               var34 = (String[])var37;
            }

            return var34;
         }
      }

      return null;
   }

   @Nullable
   public final Command getCommand(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      HashMap var10000 = this.commands;
      String var2 = name.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      return (Command)var10000.get(var2);
   }

   public final void registerCommand(@NotNull Command command) {
      Intrinsics.checkNotNullParameter(command, "command");
      Map var2 = (Map)this.commands;
      String var4 = command.getCommand().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      var2.put(var4, command);
      Object[] $this$forEach$iv = command.getAlias();
      int $i$f$forEach = 0;
      Object var14 = $this$forEach$iv;
      int var5 = 0;
      int var6 = $this$forEach$iv.length;

      while(var5 < var6) {
         Object element$iv = ((Object[])var14)[var5];
         ++var5;
         int var9 = 0;
         Map var10 = (Map)this.getCommands();
         String var12 = ((String)element$iv).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var12, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         var10.put(var12, command);
      }

   }

   private final void registerCommand(Class commandClass) {
      try {
         Object var2 = commandClass.newInstance();
         Intrinsics.checkNotNullExpressionValue(var2, "commandClass.newInstance()");
         this.registerCommand((Command)var2);
      } catch (Throwable e) {
         ClientUtils.INSTANCE.logError("Failed to load command: " + commandClass.getName() + " (" + e.getClass().getName() + ": " + e.getMessage() + ')');
      }

   }

   public final void unregisterCommand(@NotNull Command command) {
      Intrinsics.checkNotNullParameter(command, "command");
      Iterable $this$forEach$iv = (Iterable)MapsKt.toList((Map)this.commands);
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Pair it = (Pair)element$iv;
         int var7 = 0;
         if (Intrinsics.areEqual((Object)it.getSecond(), (Object)command)) {
            this.getCommands().remove(it.getFirst());
         }
      }

   }
}
