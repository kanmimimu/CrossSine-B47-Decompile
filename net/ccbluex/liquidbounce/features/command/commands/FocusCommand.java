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
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/FocusCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class FocusCommand extends Command {
   public FocusCommand() {
      int $i$f$emptyArray = 0;
      super("focus", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 3) {
         String focused = args[1];
         String target = args[2];
         List it = MinecraftInstance.mc.field_71441_e.field_73010_i;
         Intrinsics.checkNotNullExpressionValue(it, "mc.theWorld.playerEntities");
         Iterable $this$filter$iv = (Iterable)it;
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            EntityPlayer it = (EntityPlayer)element$iv$iv;
            int var13 = 0;
            if (StringsKt.equals(it.func_70005_c_(), target, true) && !it.equals(MinecraftInstance.mc.field_71439_g)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         List it = (List)destination$iv$iv;
         int $this$filterTo$iv$iv = 0;
         if (it.isEmpty()) {
            StringBuilder var23 = (new StringBuilder()).append("§6Couldn't find anyone named §a");
            String var20 = target.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var20, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            this.alert(var23.append(var20).append("§6 in the world.").toString());
            return;
         }

         EntityPlayer entity = (EntityPlayer)it.get(0);
         String var16 = focused.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         if (Intrinsics.areEqual((Object)var16, (Object)"add")) {
            List var10000 = CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList();
            Intrinsics.checkNotNullExpressionValue(entity, "entity");
            var10000.add(entity);
            StringBuilder var22 = (new StringBuilder()).append("Successfully added §a");
            var16 = target.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            this.alert(var22.append(var16).append("§3 into the focus list.").toString());
            return;
         }

         if (Intrinsics.areEqual((Object)var16, (Object)"remove")) {
            if (CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList().contains(entity)) {
               CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList().remove(entity);
               StringBuilder var21 = (new StringBuilder()).append("Successfully removed §a");
               var16 = target.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               this.alert(var21.append(var16).append("§3 from the focus list.").toString());
               return;
            }

            StringBuilder var10001 = (new StringBuilder()).append("§6Couldn't find anyone named §a");
            var16 = target.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var16, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            this.alert(var10001.append(var16).append("§6 in the focus list.").toString());
            return;
         }
      } else if (args.length == 2 && StringsKt.equals(args[1], "clear", true)) {
         CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList().clear();
         this.alert("Successfully cleared the focus list.");
         return;
      }

      this.chatSyntax("focus <clear/add/remove> <target name>");
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         String pref = args[0];
         List var10000;
         switch (args.length) {
            case 1:
               String[] var16 = new String[]{"clear", "add", "remove"};
               var10000 = CollectionsKt.listOf(var16);
               break;
            case 2:
               if (!StringsKt.equals(args[0], "add", true) && !StringsKt.equals(args[0], "remove", true)) {
                  var10000 = CollectionsKt.emptyList();
               } else {
                  List var3 = MinecraftInstance.mc.field_71441_e.field_73010_i;
                  Intrinsics.checkNotNullExpressionValue(var3, "mc.theWorld.playerEntities");
                  Iterable $this$map$iv = (Iterable)var3;
                  int $i$f$filter = 0;
                  Collection destination$iv$iv = (Collection)(new ArrayList());
                  int $i$f$filterTo = 0;

                  for(Object element$iv$iv : $this$map$iv) {
                     EntityPlayer it = (EntityPlayer)element$iv$iv;
                     int var11 = 0;
                     String var12 = it.func_70005_c_();
                     Intrinsics.checkNotNullExpressionValue(var12, "it.name");
                     if (StringsKt.startsWith(var12, pref, true)) {
                        destination$iv$iv.add(element$iv$iv);
                     }
                  }

                  $this$map$iv = (Iterable)((List)destination$iv$iv);
                  $i$f$filter = 0;
                  destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
                  $i$f$filterTo = 0;

                  for(Object item$iv$iv : $this$map$iv) {
                     EntityPlayer it = (EntityPlayer)item$iv$iv;
                     int var23 = 0;
                     destination$iv$iv.add(it.func_70005_c_());
                  }

                  var10000 = CollectionsKt.toList((Iterable)((List)destination$iv$iv));
               }
               break;
            default:
               var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
