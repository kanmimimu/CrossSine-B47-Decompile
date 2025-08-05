package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.script.ScriptEngine;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.script.api.ScriptCommand;
import net.ccbluex.liquidbounce.script.api.ScriptModule;
import net.ccbluex.liquidbounce.script.api.global.Chat;
import net.ccbluex.liquidbounce.script.api.global.Setting;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001:\u00018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0007J\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00100\rJ\u0006\u0010+\u001a\u00020\u000bJ\u000e\u0010,\u001a\u00020(2\u0006\u0010\u0002\u001a\u00020\u0007J\u0016\u0010-\u001a\u00020(2\u0006\u0010)\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\bJ\u0006\u0010/\u001a\u00020(J\u0006\u00100\u001a\u00020(J\u0006\u00101\u001a\u00020(J\u0016\u00102\u001a\u00020(2\u0006\u00103\u001a\u00020\b2\u0006\u00104\u001a\u00020\bJ\u0016\u00105\u001a\u00020(2\u0006\u00106\u001a\u00020\b2\u0006\u00104\u001a\u00020\bJ\u0006\u00107\u001a\u00020(R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\rX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012X\u0086.¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0018\u001a\n \u001a*\u0004\u0018\u00010\u00190\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001d\"\u0004\b\"\u0010\u001fR\u001a\u0010#\u001a\u00020\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001d\"\u0004\b%\u0010\u001fR\u000e\u0010&\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00069"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/Script;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "scriptFile", "Ljava/io/File;", "(Ljava/io/File;)V", "events", "Ljava/util/HashMap;", "", "Ljdk/nashorn/api/scripting/JSObject;", "Lkotlin/collections/HashMap;", "isEnable", "", "registeredCommands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "registeredModules", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scriptAuthors", "", "getScriptAuthors", "()[Ljava/lang/String;", "setScriptAuthors", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "scriptEngine", "Ljavax/script/ScriptEngine;", "kotlin.jvm.PlatformType", "scriptName", "getScriptName", "()Ljava/lang/String;", "setScriptName", "(Ljava/lang/String;)V", "scriptText", "getScriptText", "setScriptText", "scriptVersion", "getScriptVersion", "setScriptVersion", "state", "callEvent", "", "eventName", "getRegisteredModules", "getState", "import", "on", "handler", "onDisable", "onEnable", "regAnyThing", "registerCommand", "commandObject", "callback", "registerModule", "moduleObject", "supportLegacyScripts", "RegisterScript", "CrossSine"}
)
public final class Script extends MinecraftInstance {
   @NotNull
   private final File scriptFile;
   private final ScriptEngine scriptEngine;
   @NotNull
   private String scriptText;
   public String scriptName;
   public String scriptVersion;
   public String[] scriptAuthors;
   private boolean state;
   private boolean isEnable;
   @NotNull
   private final HashMap events;
   @NotNull
   private final List registeredModules;
   @NotNull
   private final List registeredCommands;

   public Script(@NotNull File scriptFile) {
      Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");
      super();
      this.scriptFile = scriptFile;
      NashornScriptEngineFactory var10001 = new NashornScriptEngineFactory();
      int $i$f$emptyArray = 0;
      this.scriptEngine = var10001.getScriptEngine(new String[0], this.getClass().getClassLoader(), ScriptSafetyManager.INSTANCE.getClassFilter());
      String var3 = this.scriptFile.getPath();
      Intrinsics.checkNotNullExpressionValue(var3, "scriptFile.path");
      this.scriptText = !StringsKt.contains$default((CharSequence)var3, (CharSequence)"CloudLoad", false, 2, (Object)null) ? FilesKt.readText(this.scriptFile, Charsets.UTF_8) : "//api_version=2";
      this.events = new HashMap();
      this.registeredModules = (List)(new ArrayList());
      this.registeredCommands = (List)(new ArrayList());
      this.scriptEngine.put("Chat", StaticClass.forClass(Chat.class));
      this.scriptEngine.put("Setting", StaticClass.forClass(Setting.class));
      this.scriptEngine.put("mc", MinecraftInstance.mc);
      this.scriptEngine.put("moduleManager", CrossSine.INSTANCE.getModuleManager());
      this.scriptEngine.put("commandManager", CrossSine.INSTANCE.getCommandManager());
      this.scriptEngine.put("scriptManager", CrossSine.INSTANCE.getScriptManager());
      this.scriptEngine.put("MovementUtils", MovementUtils.INSTANCE);
      this.scriptEngine.put("PacketUtils", PacketUtils.INSTANCE);
      this.scriptEngine.put("InventoryUtils", InventoryUtils.INSTANCE);
      this.scriptEngine.put("ClientUtils", ClientUtils.INSTANCE);
      this.scriptEngine.put("registerScript", new RegisterScript());
      this.supportLegacyScripts();
      this.scriptEngine.eval(this.scriptText);
      this.callEvent("load");
   }

   @NotNull
   public final String getScriptText() {
      return this.scriptText;
   }

   public final void setScriptText(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.scriptText = var1;
   }

   @NotNull
   public final String getScriptName() {
      String var1 = this.scriptName;
      if (var1 != null) {
         return var1;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("scriptName");
         return null;
      }
   }

   public final void setScriptName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.scriptName = var1;
   }

   @NotNull
   public final String getScriptVersion() {
      String var1 = this.scriptVersion;
      if (var1 != null) {
         return var1;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("scriptVersion");
         return null;
      }
   }

   public final void setScriptVersion(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.scriptVersion = var1;
   }

   @NotNull
   public final String[] getScriptAuthors() {
      String[] var1 = this.scriptAuthors;
      if (var1 != null) {
         return var1;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("scriptAuthors");
         return null;
      }
   }

   public final void setScriptAuthors(@NotNull String[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.scriptAuthors = var1;
   }

   public final boolean getState() {
      return this.isEnable;
   }

   @NotNull
   public final List getRegisteredModules() {
      return this.registeredModules;
   }

   public final void registerModule(@NotNull JSObject moduleObject, @NotNull JSObject callback) {
      Intrinsics.checkNotNullParameter(moduleObject, "moduleObject");
      Intrinsics.checkNotNullParameter(callback, "callback");
      ScriptModule module = new ScriptModule(moduleObject);
      CrossSine.INSTANCE.getModuleManager().registerModule(module);
      ((Collection)this.registeredModules).add(module);
      Object[] var4 = new Object[]{module};
      callback.call(moduleObject, var4);
   }

   public final void registerCommand(@NotNull JSObject commandObject, @NotNull JSObject callback) {
      Intrinsics.checkNotNullParameter(commandObject, "commandObject");
      Intrinsics.checkNotNullParameter(callback, "callback");
      ScriptCommand command = new ScriptCommand(commandObject);
      CrossSine.INSTANCE.getCommandManager().registerCommand(command);
      ((Collection)this.registeredCommands).add(command);
      Object[] var4 = new Object[]{command};
      callback.call(commandObject, var4);
   }

   public final void regAnyThing() {
      Iterable $this$forEach$iv = (Iterable)this.registeredModules;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Module it = (Module)element$iv;
         int var6 = 0;
         CrossSine.INSTANCE.getModuleManager().registerModule(it);
      }

      $this$forEach$iv = (Iterable)this.registeredCommands;
      $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Command it = (Command)element$iv;
         int var12 = 0;
         CrossSine.INSTANCE.getCommandManager().registerCommand(it);
      }

   }

   public final void supportLegacyScripts() {
      if (!StringsKt.contains$default((CharSequence)CollectionsKt.first(StringsKt.lines((CharSequence)this.scriptText)), (CharSequence)"api_version=2", false, 2, (Object)null)) {
         ClientUtils.INSTANCE.logWarn("[CrossSineAPI] Running script '" + this.scriptFile.getName() + "' with legacy support.");
         URL var10000 = CrossSine.class.getResource("/assets/minecraft/crosssine/scriptapi/legacy.js");
         String var5;
         if (var10000 == null) {
            var5 = null;
         } else {
            URL var2 = var10000;
            Charset var3 = Charsets.UTF_8;
            byte[] var4 = TextStreamsKt.readBytes(var2);
            var5 = new String(var4, var3);
         }

         String legacyScript = var5;
         this.scriptEngine.eval(legacyScript);
      }

   }

   public final void on(@NotNull String eventName, @NotNull JSObject handler) {
      Intrinsics.checkNotNullParameter(eventName, "eventName");
      Intrinsics.checkNotNullParameter(handler, "handler");
      ((Map)this.events).put(eventName, handler);
   }

   public final void onEnable() {
      if (!this.state) {
         this.callEvent("enable");
         this.state = true;
      }
   }

   public final void onDisable() {
      if (this.state) {
         Iterable $this$forEach$iv = (Iterable)this.registeredModules;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            Module it = (Module)element$iv;
            int var6 = 0;
            CrossSine.INSTANCE.getModuleManager().unregisterModule(it);
         }

         $this$forEach$iv = (Iterable)this.registeredCommands;
         $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            Command it = (Command)element$iv;
            int var12 = 0;
            CrossSine.INSTANCE.getCommandManager().unregisterCommand(it);
         }

         this.callEvent("disable");
         this.state = false;
      }
   }

   public final void import(@NotNull String scriptFile) {
      Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");
      this.scriptEngine.eval(FilesKt.readText$default(new File(CrossSine.INSTANCE.getScriptManager().getScriptsFolder(), scriptFile), (Charset)null, 1, (Object)null));
   }

   public final void callEvent(@NotNull String eventName) {
      Intrinsics.checkNotNullParameter(eventName, "eventName");
      if (Intrinsics.areEqual((Object)eventName, (Object)"enable")) {
         this.isEnable = true;
      } else if (Intrinsics.areEqual((Object)eventName, (Object)"disable")) {
         this.isEnable = false;
      }

      try {
         JSObject var10000 = (JSObject)this.events.get(eventName);
         if (var10000 != null) {
            var10000.call((Object)null);
         }
      } catch (Throwable throwable) {
         ClientUtils.INSTANCE.logError("[CrossSineAPI] Exception in script '" + this.getScriptName() + "'!", throwable);
      }

   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0002H\u0016¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/script/Script$RegisterScript;", "Ljava/util/function/Function;", "Ljdk/nashorn/api/scripting/JSObject;", "Lnet/ccbluex/liquidbounce/script/Script;", "(Lnet/ccbluex/liquidbounce/script/Script;)V", "apply", "scriptObject", "CrossSine"}
   )
   public final class RegisterScript implements Function {
      public RegisterScript() {
         Intrinsics.checkNotNullParameter(Script.this, "this$0");
         super();
      }

      @NotNull
      public Script apply(@NotNull JSObject scriptObject) {
         Intrinsics.checkNotNullParameter(scriptObject, "scriptObject");
         Script var10000 = Script.this;
         Object var10001 = scriptObject.getMember("name");
         if (var10001 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
         } else {
            var10000.setScriptName((String)var10001);
            var10000 = Script.this;
            var10001 = scriptObject.getMember("version");
            if (var10001 == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            } else {
               var10000.setScriptVersion((String)var10001);
               var10000 = Script.this;
               var10001 = ScriptUtils.convert(scriptObject.getMember("authors"), String[].class);
               if (var10001 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
               } else {
                  var10000.setScriptAuthors((String[])var10001);
                  return Script.this;
               }
            }
         }
      }
   }
}
