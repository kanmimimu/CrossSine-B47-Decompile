package net.ccbluex.liquidbounce.features.command.commands;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
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
import net.ccbluex.liquidbounce.file.config.ConfigManager;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ConfigCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class ConfigCommand extends Command {
   public ConfigCommand() {
      String[] var1 = new String[]{"cfg"};
      super("config", var1);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length > 1) {
         String var4 = args[1].toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var4.hashCode()) {
            case -1352294148:
               if (var4.equals("create")) {
                  if (args.length > 2) {
                     File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args[2], ".json"));
                     if (!file.exists()) {
                        CrossSine.INSTANCE.getConfigManager().load(args[2], true);
                        this.alert(Intrinsics.stringPlus("Created config ", args[2]));
                     } else {
                        this.alert("Config " + args[2] + " already exists");
                     }

                     return;
                  } else {
                     this.chatSyntax(Intrinsics.stringPlus(args[1], " <configName>"));
                     return;
                  }
               }

               return;
            case -1335458389:
               if (var4.equals("delete")) {
                  if (args.length > 2) {
                     File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args[2], ".json"));
                     if (file.exists()) {
                        file.delete();
                        this.alert(Intrinsics.stringPlus("Successfully deleted config ", args[2]));
                     } else {
                        this.alert("Config " + args[2] + " does not exist");
                     }

                     return;
                  } else {
                     this.chatSyntax(Intrinsics.stringPlus(args[1], " <configName>"));
                     return;
                  }
               }

               return;
            case -934641255:
               if (var4.equals("reload")) {
                  CrossSine.INSTANCE.getConfigManager().load(CrossSine.INSTANCE.getConfigManager().getNowConfig(), false);
                  this.alert(Intrinsics.stringPlus("Reloaded config ", CrossSine.INSTANCE.getConfigManager().getNowConfig()));
               }

               return;
            case -934594754:
               if (var4.equals("rename")) {
                  if (args.length > 3) {
                     File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args[2], ".json"));
                     File newFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args[3], ".json"));
                     if (file.exists() && !newFile.exists()) {
                        file.renameTo(newFile);
                        this.alert("Renamed config " + args[2] + " to " + args[3]);
                     } else if (!file.exists()) {
                        this.alert("Config " + args[2] + " does not exist");
                     } else if (newFile.exists()) {
                        this.alert("Config " + args[3] + " already exists");
                     }

                     if (StringsKt.equals(CrossSine.INSTANCE.getConfigManager().getNowConfig(), args[2], true)) {
                        CrossSine.INSTANCE.getConfigManager().load(args[3], false);
                        CrossSine.INSTANCE.getConfigManager().saveConfigSet();
                        return;
                     }
                  } else {
                     this.chatSyntax(Intrinsics.stringPlus(args[1], " <configName> <newName>"));
                  }

                  return;
               }

               return;
            case 3059573:
               if (var4.equals("copy")) {
                  if (args.length > 3) {
                     File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args[2], ".json"));
                     File newFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args[3], ".json"));
                     if (file.exists() && !newFile.exists()) {
                        Files.copy(file.toPath(), newFile.toPath());
                        this.alert("Copied config " + args[2] + " to " + args[3]);
                        return;
                     } else {
                        if (!file.exists()) {
                           this.alert("Config " + args[2] + " does not exist");
                        } else if (newFile.exists()) {
                           this.alert("Config " + args[3] + " already exists");
                           return;
                        }

                        return;
                     }
                  } else {
                     this.chatSyntax(Intrinsics.stringPlus(args[1], " <configName> <newName>"));
                     return;
                  }
               }

               return;
            case 3322014:
               if (var4.equals("list")) {
                  File[] var10000 = CrossSine.INSTANCE.getFileManager().getConfigsDir().listFiles();
                  if (var10000 == null) {
                     return;
                  }

                  File[] $this$map$iv = var10000;
                  int $i$f$filter = 0;
                  Collection destination$iv$iv = (Collection)(new ArrayList());
                  int $i$f$filterTo = 0;
                  File[] var9 = $this$map$iv;
                  int var10 = 0;
                  int var11 = $this$map$iv.length;

                  while(var10 < var11) {
                     Object element$iv$iv = var9[var10];
                     ++var10;
                     int var14 = 0;
                     if (((File)element$iv$iv).isFile()) {
                        destination$iv$iv.add(element$iv$iv);
                     }
                  }

                  Iterable newFile = (Iterable)((List)destination$iv$iv);
                  $i$f$filter = 0;
                  destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(newFile, 10)));
                  $i$f$filterTo = 0;

                  for(Object item$iv$iv : newFile) {
                     File var32 = (File)item$iv$iv;
                     int var33 = 0;
                     String name = var32.getName();
                     Intrinsics.checkNotNullExpressionValue(name, "name");
                     String var35;
                     if (StringsKt.endsWith$default(name, ".json", false, 2, (Object)null)) {
                        String var34 = name.substring(0, name.length() - 5);
                        Intrinsics.checkNotNullExpressionValue(var34, "this as java.lang.String…ing(startIndex, endIndex)");
                        var35 = var34;
                     } else {
                        var35 = name;
                     }

                     destination$iv$iv.add(var35);
                  }

                  List list = (List)destination$iv$iv;
                  this.alert("Configs(" + list.size() + "):");

                  for(String file : list) {
                     if (file.equals(CrossSine.INSTANCE.getConfigManager().getNowConfig())) {
                        this.alert(Intrinsics.stringPlus("-> §a§l", file));
                     } else {
                        this.alert(Intrinsics.stringPlus("> ", file));
                     }
                  }
               }

               return;
            case 3327206:
               if (!var4.equals("load")) {
                  return;
               }
               break;
            case 3522941:
               if (var4.equals("save")) {
                  ConfigManager.save$default(CrossSine.INSTANCE.getConfigManager(), true, true, false, 4, (Object)null);
                  this.alert(Intrinsics.stringPlus("Saved config ", CrossSine.INSTANCE.getConfigManager().getNowConfig()));
               }

               return;
            case 887743288:
               if (var4.equals("openfolder")) {
                  Desktop.getDesktop().open(CrossSine.INSTANCE.getFileManager().getConfigsDir());
               }

               return;
            case 1126940025:
               if (var4.equals("current")) {
                  this.alert(Intrinsics.stringPlus("Current config is ", CrossSine.INSTANCE.getConfigManager().getNowConfig()));
               }

               return;
            case 1528750673:
               if (!var4.equals("forceload")) {
                  return;
               }
               break;
            default:
               return;
         }

         if (args.length > 2) {
            File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args[2], ".json"));
            if (file.exists()) {
               CrossSine.INSTANCE.getConfigManager().load(args[2], StringsKt.equals(args[1], "load", true));
               this.alert(Intrinsics.stringPlus("Loaded config ", args[2]));
            } else {
               this.alert("Config " + args[2] + " does not exist");
            }
         } else {
            this.chatSyntax(Intrinsics.stringPlus(args[1], " <configName>"));
         }
      } else {
         String[] var2 = new String[]{"current", "copy <configName> <newName>", "create <configName>", "load <configName>", "forceload <configName>", "delete <configName>", "rename <configName> <newName>", "reload", "list", "openfolder", "save"};
         this.chatSyntax(var2);
      }

   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var41;
         switch (args.length) {
            case 1:
               String[] var2 = new String[]{"current", "copy", "create", "load", "forceload", "delete", "rename", "reload", "list", "openfolder", "save"};
               Iterable var15 = (Iterable)CollectionsKt.listOf(var2);
               int $i$f$filter = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList());
               int $i$f$filterTo = 0;

               for(Object element$iv$iv : var15) {
                  String it = (String)element$iv$iv;
                  int var36 = 0;
                  if (StringsKt.startsWith(it, args[0], true)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               var41 = (List)destination$iv$iv;
               break;
            case 2:
               label96: {
                  String $this$filterTo$iv$iv = args[0].toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue($this$filterTo$iv$iv, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  switch ($this$filterTo$iv$iv.hashCode()) {
                     case -1335458389:
                        if (!$this$filterTo$iv$iv.equals("delete")) {
                           break label96;
                        }
                        break;
                     case -934594754:
                        if (!$this$filterTo$iv$iv.equals("rename")) {
                           break label96;
                        }
                        break;
                     case 3059573:
                        if (!$this$filterTo$iv$iv.equals("copy")) {
                           break label96;
                        }
                        break;
                     case 3327206:
                        if (!$this$filterTo$iv$iv.equals("load")) {
                           break label96;
                        }
                        break;
                     case 1528750673:
                        if (!$this$filterTo$iv$iv.equals("forceload")) {
                           break label96;
                        }
                        break;
                     default:
                        break label96;
                  }

                  File[] var10000 = CrossSine.INSTANCE.getFileManager().getConfigsDir().listFiles();
                  if (var10000 == null) {
                     return CollectionsKt.emptyList();
                  }

                  File[] $i$f$filter = var10000;
                  int $i$f$filter = 0;
                  Collection destination$iv$iv = (Collection)(new ArrayList());
                  int $i$f$filterTo = 0;
                  File[] element$iv$iv = $i$f$filter;
                  int it = 0;
                  int var10 = $i$f$filter.length;

                  while(it < var10) {
                     Object element$iv$iv = element$iv$iv[it];
                     ++it;
                     int var13 = 0;
                     if (((File)element$iv$iv).isFile()) {
                        destination$iv$iv.add(element$iv$iv);
                     }
                  }

                  Iterable $this$filter$iv = (Iterable)((List)destination$iv$iv);
                  $i$f$filter = 0;
                  destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10)));
                  $i$f$filterTo = 0;

                  for(Object item$iv$iv : $this$filter$iv) {
                     File it = (File)item$iv$iv;
                     int var37 = 0;
                     String name = it.getName();
                     Intrinsics.checkNotNullExpressionValue(name, "name");
                     String var40;
                     if (StringsKt.endsWith$default(name, ".json", false, 2, (Object)null)) {
                        String var39 = name.substring(0, name.length() - 5);
                        Intrinsics.checkNotNullExpressionValue(var39, "this as java.lang.String…ing(startIndex, endIndex)");
                        var40 = var39;
                     } else {
                        var40 = name;
                     }

                     destination$iv$iv.add(var40);
                  }

                  $this$filter$iv = (Iterable)((List)destination$iv$iv);
                  $i$f$filter = 0;
                  destination$iv$iv = (Collection)(new ArrayList());
                  $i$f$filterTo = 0;

                  for(Object element$iv$iv : $this$filter$iv) {
                     String it = (String)element$iv$iv;
                     int var38 = 0;
                     Intrinsics.checkNotNullExpressionValue(it, "it");
                     if (StringsKt.startsWith(it, args[1], true)) {
                        destination$iv$iv.add(element$iv$iv);
                     }
                  }

                  var41 = (List)destination$iv$iv;
                  break;
               }

               var41 = CollectionsKt.emptyList();
               break;
            default:
               var41 = CollectionsKt.emptyList();
         }

         return var41;
      }
   }
}
