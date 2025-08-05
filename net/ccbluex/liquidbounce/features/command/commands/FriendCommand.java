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
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/FriendCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class FriendCommand extends Command {
   public FriendCommand() {
      String[] var1 = new String[]{"friends"};
      super("friend", var1);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length > 1) {
         FriendsConfig friendsConfig = CrossSine.INSTANCE.getFileManager().getFriendsConfig();
         if (StringsKt.equals(args[1], "add", true)) {
            if (args.length > 2) {
               String name = args[2];
               if (((CharSequence)name).length() == 0) {
                  this.alert("The name is empty.");
                  return;
               }

               boolean var54;
               if (args.length > 3) {
                  String var24 = StringUtils.toCompleteString(args, 3);
                  Intrinsics.checkNotNullExpressionValue(var24, "toCompleteString(args, 3)");
                  var54 = friendsConfig.addFriend(name, var24);
               } else {
                  var54 = FriendsConfig.addFriend$default(friendsConfig, name, (String)null, 2, (Object)null);
               }

               if (var54) {
                  CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
                  this.alert("§a§l" + name + "§3 was added to your friend list.");
                  this.playEdit();
               } else {
                  this.alert("The name is already in the list.");
               }

               return;
            }

            this.chatSyntax("friend add <name> [alias]");
            return;
         }

         if (StringsKt.equals(args[1], "addall", true)) {
            if (args.length != 3) {
               this.chatSyntax("friend addall <colored regex>");
               return;
            }

            String regex = args[2];
            String coloredRegex = ColorUtils.translateAlternateColorCodes(regex);
            int added = 0;
            List var30 = MinecraftInstance.mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(var30, "mc.theWorld.playerEntities");
            Iterable $i$f$forEach = (Iterable)var30;
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : $i$f$forEach) {
               boolean var10000;
               label111: {
                  EntityPlayer it = (EntityPlayer)element$iv$iv;
                  int var14 = 0;
                  Intrinsics.checkNotNullExpressionValue(it, "it");
                  if (!AntiBot.isBot((EntityLivingBase)it)) {
                     String var15 = it.func_145748_c_().func_150254_d();
                     Intrinsics.checkNotNullExpressionValue(var15, "it.displayName.formattedText");
                     if (StringsKt.contains((CharSequence)var15, (CharSequence)coloredRegex, false)) {
                        var10000 = true;
                        break label111;
                     }
                  }

                  var10000 = false;
               }

               if (var10000) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $i$f$forEach = (Iterable)((List)destination$iv$iv);
            $i$f$filter = 0;

            for(Object element$iv : $i$f$forEach) {
               EntityPlayer it = (EntityPlayer)element$iv;
               int var48 = 0;
               String var51 = it.func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var51, "it.name");
               if (FriendsConfig.addFriend$default(friendsConfig, var51, (String)null, 2, (Object)null)) {
                  ++added;
               }
            }

            this.alert("Added §a§l" + added + " §3players matching the same regex to your friend list.");
            this.playEdit();
            return;
         }

         if (StringsKt.equals(args[1], "removeall", true)) {
            if (args.length != 3) {
               this.chatSyntax("friend removeall <regex>");
               return;
            }

            String regex = args[2];
            int remove = 0;
            Iterable added = (Iterable)friendsConfig.getFriends();
            int $i$f$map = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(added, 10)));
            int $i$f$mapTo = 0;

            for(Object item$iv$iv : added) {
               FriendsConfig.Friend element$iv$iv = (FriendsConfig.Friend)item$iv$iv;
               int it = 0;
               destination$iv$iv.add(element$iv$iv.getPlayerName());
            }

            added = (Iterable)((List)destination$iv$iv);
            $i$f$map = 0;
            destination$iv$iv = (Collection)(new ArrayList());
            $i$f$mapTo = 0;

            for(Object element$iv$iv : added) {
               String it = (String)element$iv$iv;
               int var52 = 0;
               if (StringsKt.contains((CharSequence)it, (CharSequence)regex, false)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            added = (Iterable)((List)destination$iv$iv);
            $i$f$map = 0;

            for(Object element$iv : added) {
               String it = (String)element$iv;
               int var43 = 0;
               if (friendsConfig.removeFriend(it)) {
                  ++remove;
               }
            }

            this.alert("Removed §a§l" + remove + " §3players matching the same regex from your friend list.");
            this.playEdit();
            return;
         }

         if (StringsKt.equals(args[1], "remove", true)) {
            if (args.length > 2) {
               String name = args[2];
               if (friendsConfig.removeFriend(name)) {
                  CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
                  this.alert("§a§l" + name + "§3 was removed from your friend list.");
                  this.playEdit();
               } else {
                  this.alert("This name is not in the list.");
               }

               return;
            }

            this.chatSyntax("friend remove <name>");
            return;
         }

         if (StringsKt.equals(args[1], "clear", true)) {
            int friends = friendsConfig.getFriends().size();
            friendsConfig.clearFriends();
            CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
            this.alert("Removed " + friends + " friend(s).");
            return;
         }

         if (StringsKt.equals(args[1], "list", true)) {
            this.alert("Your Friends:");

            for(FriendsConfig.Friend friend : friendsConfig.getFriends()) {
               this.alert("§7> §a§l" + friend.getPlayerName() + " §c(§7§l" + friend.getAlias() + "§c)");
            }

            this.alert("You have §c" + friendsConfig.getFriends().size() + "§3 friends.");
            return;
         }
      }

      this.chatSyntax("friend <add/addall/removeall/list/clear>");
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
               String[] var2 = new String[]{"add", "addall", "remove", "removeall", "list", "clear"};
               Iterable $this$filter$iv = (Iterable)CollectionsKt.listOf(var2);
               int $i$f$filter = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList());
               int $i$f$filterTo = 0;

               for(Object element$iv$iv : $this$filter$iv) {
                  String it = (String)element$iv$iv;
                  int it = 0;
                  if (StringsKt.startsWith(it, args[0], true)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               var10000 = (List)destination$iv$iv;
               break;
            case 2:
               String $this$filterTo$iv$iv = args[0].toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue($this$filterTo$iv$iv, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               if (Intrinsics.areEqual((Object)$this$filterTo$iv$iv, (Object)"add")) {
                  List $this$filter$iv = MinecraftInstance.mc.field_71441_e.field_73010_i;
                  Intrinsics.checkNotNullExpressionValue($this$filter$iv, "mc.theWorld.playerEntities");
                  Iterable $this$map$iv = (Iterable)$this$filter$iv;
                  int $i$f$map = 0;
                  Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
                  int $i$f$mapTo = 0;

                  for(Object item$iv$iv : $this$map$iv) {
                     EntityPlayer it = (EntityPlayer)item$iv$iv;
                     int var44 = 0;
                     destination$iv$iv.add(it.func_70005_c_());
                  }

                  $this$map$iv = (Iterable)((List)destination$iv$iv);
                  $i$f$map = 0;
                  destination$iv$iv = (Collection)(new ArrayList());
                  $i$f$mapTo = 0;

                  for(Object element$iv$iv : $this$map$iv) {
                     String it = (String)element$iv$iv;
                     int var45 = 0;
                     Intrinsics.checkNotNullExpressionValue(it, "it");
                     if (StringsKt.startsWith(it, args[1], true)) {
                        destination$iv$iv.add(element$iv$iv);
                     }
                  }

                  return (List)destination$iv$iv;
               }

               if (!Intrinsics.areEqual((Object)$this$filterTo$iv$iv, (Object)"remove")) {
                  return CollectionsKt.emptyList();
               }

               Iterable $i$f$filter = (Iterable)CrossSine.INSTANCE.getFileManager().getFriendsConfig().getFriends();
               int $i$f$map = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($i$f$filter, 10)));
               int $i$f$mapTo = 0;

               for(Object item$iv$iv : $i$f$filter) {
                  FriendsConfig.Friend var10 = (FriendsConfig.Friend)item$iv$iv;
                  int var11 = 0;
                  destination$iv$iv.add(var10.getPlayerName());
               }

               $i$f$filter = (Iterable)((List)destination$iv$iv);
               $i$f$map = 0;
               destination$iv$iv = (Collection)(new ArrayList());
               $i$f$mapTo = 0;

               for(Object element$iv$iv : $i$f$filter) {
                  String it = (String)element$iv$iv;
                  int var43 = 0;
                  if (StringsKt.startsWith(it, args[1], true)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               return (List)destination$iv$iv;
            default:
               var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
