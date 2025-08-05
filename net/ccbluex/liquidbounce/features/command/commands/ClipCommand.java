package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ClipCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class ClipCommand extends Command {
   public ClipCommand() {
      int $i$f$emptyArray = 0;
      super("clip", new String[0]);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length > 2) {
         double dist = (double)0.0F;

         try {
            dist = Double.parseDouble(args[2]);
         } catch (NumberFormatException var11) {
            this.chatSyntaxError();
            return;
         }

         String var6 = args[1].toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         if (Intrinsics.areEqual((Object)var6, (Object)"up")) {
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + dist, MinecraftInstance.mc.field_71439_g.field_70161_v);
         } else if (Intrinsics.areEqual((Object)var6, (Object)"down")) {
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - dist, MinecraftInstance.mc.field_71439_g.field_70161_v);
         } else {
            double var10000;
            short var10001;
            label38: {
               var10000 = (double)MinecraftInstance.mc.field_71439_g.field_70177_z;
               Intrinsics.checkNotNullExpressionValue(var9, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var9) {
                  case "back":
                     var10001 = 180;
                     break label38;
                  case "left":
                     var10001 = 270;
                     break label38;
                  case "right":
                     var10001 = 90;
                     break label38;
               }

               var10001 = 0;
            }

            double yaw = Math.toRadians(var10000 + (double)var10001);
            double x = -Math.sin(yaw) * dist;
            double z = Math.cos(yaw) * dist;
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + x, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + z);
         }

      } else {
         this.chatSyntax("clip <up/down/forward/back/left/right> <dist>");
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
            String[] var2 = new String[]{"up", "down", "forward", "back", "left", "right"};
            Iterable $this$filter$iv = (Iterable)CollectionsKt.listOf(var2);
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : $this$filter$iv) {
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
