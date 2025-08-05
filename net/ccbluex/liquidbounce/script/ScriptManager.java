package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\tJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0005J\u0006\u0010\u0013\u001a\u00020\rJ\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0005J\u0006\u0010\u0015\u001a\u00020\rR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/ScriptManager;", "", "()V", "scripts", "", "Lnet/ccbluex/liquidbounce/script/Script;", "getScripts", "()Ljava/util/List;", "scriptsFolder", "Ljava/io/File;", "getScriptsFolder", "()Ljava/io/File;", "disableScripts", "", "enableScripts", "loadJsScript", "scriptFile", "loadScript", "script", "loadScripts", "unloadScript", "unloadScripts", "CrossSine"}
)
public final class ScriptManager {
   @NotNull
   private final List scripts = (List)(new ArrayList());
   @NotNull
   private final File scriptsFolder;

   public ScriptManager() {
      this.scriptsFolder = new File(CrossSine.INSTANCE.getFileManager().getDir(), "scripts");
   }

   @NotNull
   public final List getScripts() {
      return this.scripts;
   }

   @NotNull
   public final File getScriptsFolder() {
      return this.scriptsFolder;
   }

   public final void loadScripts() {
      if (!this.scriptsFolder.exists()) {
         this.scriptsFolder.mkdir();
      }

      File[] var10000 = this.scriptsFolder.listFiles();
      if (var10000 != null) {
         Object[] $this$forEach$iv = var10000;
         int $i$f$forEach = 0;
         Object var3 = $this$forEach$iv;
         int var4 = 0;
         int var5 = $this$forEach$iv.length;

         while(var4 < var5) {
            Object element$iv = ((Object[])var3)[var4];
            ++var4;
            int var8 = 0;
            String var9 = ((File)element$iv).getName();
            Intrinsics.checkNotNullExpressionValue(var9, "it.name");
            if (StringsKt.endsWith(var9, ".js", true)) {
               Remapper.INSTANCE.loadSrg();
               Intrinsics.checkNotNullExpressionValue(element$iv, "it");
               this.loadJsScript((File)element$iv);
            }
         }
      }

   }

   public final void unloadScripts() {
      this.scripts.clear();
   }

   public final void unloadScript(@NotNull Script script) {
      Intrinsics.checkNotNullParameter(script, "script");
      this.scripts.remove(script);
   }

   public final void loadScript(@NotNull Script script) {
      Intrinsics.checkNotNullParameter(script, "script");
      this.scripts.add(script);
   }

   public final void loadJsScript(@NotNull File scriptFile) {
      Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");

      try {
         this.scripts.add(new Script(scriptFile));
         ClientUtils.INSTANCE.logInfo("[ScriptAPI] Successfully loaded script '" + scriptFile.getName() + "'.");
      } catch (Throwable t) {
         ClientUtils.INSTANCE.logError("[ScriptAPI] Failed to load script '" + scriptFile.getName() + "'.", t);
      }

   }

   public final void enableScripts() {
      Iterable $this$forEach$iv = (Iterable)this.scripts;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Script it = (Script)element$iv;
         int var6 = 0;
         it.onEnable();
      }

   }

   public final void disableScripts() {
      Iterable $this$forEach$iv = (Iterable)this.scripts;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Script it = (Script)element$iv;
         int var6 = 0;
         it.onDisable();
      }

   }
}
