package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/RemoteViewCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class RemoteViewCommand extends Command {
   public RemoteViewCommand() {
      String[] var1 = new String[]{"rv"};
      super("remoteview", var1);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length < 2) {
         if (!Intrinsics.areEqual((Object)MinecraftInstance.mc.func_175606_aa(), (Object)MinecraftInstance.mc.field_71439_g)) {
            MinecraftInstance.mc.func_175607_a((Entity)MinecraftInstance.mc.field_71439_g);
         } else {
            this.chatSyntax("remoteview <username>");
         }
      } else {
         String targetName = args[1];

         for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (Intrinsics.areEqual((Object)targetName, (Object)entity.func_70005_c_())) {
               MinecraftInstance.mc.func_175607_a(entity);
               this.chat("Now viewing perspective of §8" + entity.func_70005_c_() + "§3.");
               this.chat("Execute §8" + CrossSine.INSTANCE.getCommandManager().getPrefix() + "remoteview §3again to go back to yours.");
               break;
            }
         }

      }
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else if (args.length != 1) {
         return CollectionsKt.emptyList();
      } else {
         List var2 = MinecraftInstance.mc.field_71441_e.field_73010_i;
         Intrinsics.checkNotNullExpressionValue(var2, "mc.theWorld.playerEntities");
         Iterable $this$filter$iv = (Iterable)var2;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$filter$iv) {
            EntityPlayer it = (EntityPlayer)item$iv$iv;
            int var10 = 0;
            destination$iv$iv.add(it.func_70005_c_());
         }

         $this$filter$iv = (Iterable)((List)destination$iv$iv);
         $i$f$map = 0;
         destination$iv$iv = (Collection)(new ArrayList());
         $i$f$mapTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            String it = (String)element$iv$iv;
            int var20 = 0;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (StringsKt.startsWith(it, args[0], true)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         return (List)destination$iv$iv;
      }
   }
}
