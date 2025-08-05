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
import net.ccbluex.liquidbounce.features.special.UUIDSpoofer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/UUIDCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "CrossSine"}
)
public final class UUIDCommand extends Command {
   public UUIDCommand() {
      int $i$f$emptyArray = 0;
      super("uuid", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length != 2) {
         if (args.length == 1) {
            this.chat("§6Session's UUID is §7" + MinecraftInstance.mc.field_71449_j.func_148255_b() + "§6.");
            this.chat("§6Player's UUID is §7" + MinecraftInstance.mc.field_71439_g.func_110124_au() + "§6.");
         }

         this.chatSyntax("uuid <player's name in current world/uuid/reset>");
      } else {
         String theName = args[1];
         if (StringsKt.equals(theName, "reset", true)) {
            UUIDSpoofer.INSTANCE.setSpoofId((String)null);
            this.chat("§aSuccessfully resetted your UUID.");
         } else {
            List var4 = MinecraftInstance.mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(var4, "mc.theWorld.playerEntities");
            Iterable var13 = (Iterable)var4;
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : var13) {
               EntityPlayer it = (EntityPlayer)element$iv$iv;
               int var12 = 0;
               Intrinsics.checkNotNullExpressionValue(it, "it");
               if (!AntiBot.isBot((EntityLivingBase)it) && StringsKt.equals(it.func_70005_c_(), theName, true)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            EntityPlayer targetPlayer = (EntityPlayer)CollectionsKt.firstOrNull((List)destination$iv$iv);
            if (targetPlayer == null) {
               UUIDSpoofer.INSTANCE.setSpoofId(theName);
            } else {
               UUIDSpoofer.INSTANCE.setSpoofId(targetPlayer.func_146103_bH().getId().toString());
            }

            StringBuilder var10001 = (new StringBuilder()).append("§aSuccessfully changed your UUID to §6");
            String var10002 = UUIDSpoofer.INSTANCE.getSpoofId();
            Intrinsics.checkNotNull(var10002);
            this.chat(var10001.append(var10002).append("§a. Make sure to turn on BungeeCordSpoof in server selection.").toString());
         }
      }
   }
}
