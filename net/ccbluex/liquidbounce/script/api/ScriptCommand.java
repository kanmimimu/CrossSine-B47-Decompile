package net.ccbluex.liquidbounce.script.api;

import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001b\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u0016¢\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0003`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/ScriptCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "commandObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "events", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "execute", "", "args", "", "([Ljava/lang/String;)V", "on", "eventName", "handler", "CrossSine"}
)
public final class ScriptCommand extends Command {
   @NotNull
   private final JSObject commandObject;
   @NotNull
   private final HashMap events;

   public ScriptCommand(@NotNull JSObject commandObject) {
      Intrinsics.checkNotNullParameter(commandObject, "commandObject");
      Object var10001 = commandObject.getMember("name");
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         String var2 = (String)var10001;
         Object var10002 = ScriptUtils.convert(commandObject.getMember("aliases"), String[].class);
         if (var10002 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
         } else {
            super(var2, (String[])var10002);
            this.commandObject = commandObject;
            this.events = new HashMap();
         }
      }
   }

   public final void on(@NotNull String eventName, @NotNull JSObject handler) {
      Intrinsics.checkNotNullParameter(eventName, "eventName");
      Intrinsics.checkNotNullParameter(handler, "handler");
      ((Map)this.events).put(eventName, handler);
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");

      try {
         JSObject var10000 = (JSObject)this.events.get("execute");
         if (var10000 != null) {
            JSObject var10001 = this.commandObject;
            Object[] var3 = new Object[]{args};
            var10000.call(var10001, var3);
         }
      } catch (Throwable throwable) {
         ClientUtils.INSTANCE.logError("[ScriptAPI] Exception in command '" + this.getCommand() + "'!", throwable);
      }

   }
}
