package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TpCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class TpCommand extends Command {
   public TpCommand() {
      int $i$f$emptyArray = 0;
      super("tp", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length != 2) {
         if (args.length == 4) {
            try {
               double posX = StringsKt.equals(args[1], "~", true) ? MinecraftInstance.mc.field_71439_g.field_70165_t : Double.parseDouble(args[1]);
               double posY = StringsKt.equals(args[2], "~", true) ? MinecraftInstance.mc.field_71439_g.field_70163_u : Double.parseDouble(args[2]);
               double posZ = StringsKt.equals(args[3], "~", true) ? MinecraftInstance.mc.field_71439_g.field_70161_v : Double.parseDouble(args[3]);
               MinecraftInstance.mc.field_71439_g.func_70634_a(posX, posY, posZ);
               this.chat("Attempted to teleport you to §f" + posX + "§7, §f" + posY + "§7, §f" + posZ + "§7.");
            } catch (NumberFormatException var13) {
               this.chat("§7Please check if you have typed the numbers correctly, and try again.");
            }
         } else {
            this.chatSyntax("teleport/tp <player name/x y z>");
         }
      } else {
         String theName = args[1];
         List var4 = MinecraftInstance.mc.field_71441_e.field_73010_i;
         Intrinsics.checkNotNullExpressionValue(var4, "mc.theWorld.playerEntities");
         Iterable posY = (Iterable)var4;
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : posY) {
            EntityPlayer it = (EntityPlayer)element$iv$iv;
            int var12 = 0;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (!AntiBot.isBot((EntityLivingBase)it) && StringsKt.equals(it.func_70005_c_(), theName, true)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         EntityPlayer targetPlayer = (EntityPlayer)CollectionsKt.firstOrNull((List)destination$iv$iv);
         if (targetPlayer != null) {
            MinecraftInstance.mc.field_71439_g.func_70634_a(targetPlayer.field_70165_t, targetPlayer.field_70163_u, targetPlayer.field_70161_v);
            this.chat("Attempted to teleport you to §f" + targetPlayer.func_70005_c_() + "§7.");
         } else {
            this.chat("§7We couldn't find any player in the current world with that name.");
         }
      }
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         String pref = args[0];
         List var23;
         if (args.length == 1) {
            List var3 = MinecraftInstance.mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(var3, "mc.theWorld.playerEntities");
            Iterable $this$map$iv = (Iterable)var3;
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : $this$map$iv) {
               label40: {
                  EntityPlayer it = (EntityPlayer)element$iv$iv;
                  int var11 = 0;
                  Intrinsics.checkNotNullExpressionValue(it, "it");
                  if (!AntiBot.isBot((EntityLivingBase)it)) {
                     String var12 = it.func_70005_c_();
                     Intrinsics.checkNotNullExpressionValue(var12, "it.name");
                     if (StringsKt.startsWith(var12, pref, true)) {
                        var10000 = true;
                        break label40;
                     }
                  }

                  var10000 = false;
               }

               if (var10000) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $this$map$iv = (Iterable)((List)destination$iv$iv);
            $i$f$filter = 0;
            destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
            $i$f$filterTo = 0;

            for(Object item$iv$iv : $this$map$iv) {
               EntityPlayer it = (EntityPlayer)item$iv$iv;
               int var22 = 0;
               destination$iv$iv.add(it.func_70005_c_());
            }

            var23 = CollectionsKt.toList((Iterable)((List)destination$iv$iv));
         } else {
            var23 = CollectionsKt.emptyList();
         }

         return var23;
      }
   }
}
