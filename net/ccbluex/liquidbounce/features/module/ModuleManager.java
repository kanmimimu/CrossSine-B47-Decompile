package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.special.AutoDisable;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0005H\u0086\u0002¢\u0006\u0002\u0010\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ%\u0010\u001d\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0005¢\u0006\u0002\u0010\u0018J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00062\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\u001a2\u0006\u0010\"\u001a\u00020 J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'H\u0003J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010&\u001a\u00020)H\u0003J\u0018\u0010*\u001a\u00020\u00122\u000e\u0010\u001e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0002J\u000e\u0010*\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006J\u0006\u0010+\u001a\u00020\u0012J\u000e\u0010,\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006R2\u0010\u0003\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006-"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "moduleClassMap", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "Lkotlin/collections/HashMap;", "modules", "", "getModules", "()Ljava/util/List;", "pendingBindModule", "getPendingBindModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "setPendingBindModule", "(Lnet/ccbluex/liquidbounce/features/module/Module;)V", "generateCommand", "", "module", "generateCommand$CrossSine", "get", "T", "clazz", "(Ljava/lang/Class;)Lnet/ccbluex/liquidbounce/features/module/Module;", "getKeyBind", "", "key", "", "getModule", "moduleClass", "moduleName", "", "getModulesByName", "name", "handleEvents", "", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "registerModule", "registerModules", "unregisterModule", "CrossSine"}
)
public final class ModuleManager implements Listenable {
   @NotNull
   private final List modules = (List)(new ArrayList());
   @NotNull
   private final HashMap moduleClassMap = new HashMap();
   @Nullable
   private Module pendingBindModule;

   public ModuleManager() {
      CrossSine.INSTANCE.getEventManager().registerListener(this);
   }

   @NotNull
   public final List getModules() {
      return this.modules;
   }

   @Nullable
   public final Module getPendingBindModule() {
      return this.pendingBindModule;
   }

   public final void setPendingBindModule(@Nullable Module var1) {
      this.pendingBindModule = var1;
   }

   public final void registerModules() {
      ClientUtils.INSTANCE.logInfo("[ModuleManager] Loading modules...");
      Iterable $this$forEach$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".modules"), Module.class);
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Class p0 = (Class)element$iv;
         int var6 = 0;
         this.registerModule(p0);
      }

      $this$forEach$iv = (Iterable)this.modules;
      $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Module it = (Module)element$iv;
         int var17 = 0;
         it.onInitialize();
      }

      $this$forEach$iv = (Iterable)this.modules;
      $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Module it = (Module)element$iv;
         int var18 = 0;
         it.onLoad();
      }

      CrossSine.INSTANCE.getEventManager().registerListener(AutoDisable.INSTANCE);
      ClientUtils.INSTANCE.logInfo("[ModuleManager] Loaded " + this.modules.size() + " modules.");
   }

   public final void registerModule(@NotNull Module module) {
      Intrinsics.checkNotNullParameter(module, "module");
      ((Collection)this.modules).add(module);
      Map var2 = (Map)this.moduleClassMap;
      Class var3 = module.getClass();
      var2.put(var3, module);
      List $this$sortBy$iv = this.modules;
      int $i$f$sortBy = 0;
      if ($this$sortBy$iv.size() > 1) {
         CollectionsKt.sortWith($this$sortBy$iv, new ModuleManager$registerModule$$inlined$sortBy$1());
      }

      this.generateCommand$CrossSine(module);
      CrossSine.INSTANCE.getEventManager().registerListener(module);
   }

   private final void registerModule(Class moduleClass) {
      try {
         Object var2 = moduleClass.newInstance();
         Intrinsics.checkNotNullExpressionValue(var2, "moduleClass.newInstance()");
         this.registerModule((Module)var2);
      } catch (IllegalAccessException var3) {
         this.registerModule((Module)ClassUtils.INSTANCE.getObjectInstance(moduleClass));
      } catch (Throwable e) {
         ClientUtils.INSTANCE.logError("Failed to load module: " + moduleClass.getName() + " (" + e.getClass().getName() + ": " + e.getMessage() + ')');
      }

   }

   public final void unregisterModule(@NotNull Module module) {
      Intrinsics.checkNotNullParameter(module, "module");
      this.modules.remove(module);
      this.moduleClassMap.remove(module.getClass());
      CrossSine.INSTANCE.getEventManager().unregisterListener(module);
   }

   public final void generateCommand$CrossSine(@NotNull Module module) {
      Intrinsics.checkNotNullParameter(module, "module");
      if (module.getModuleCommand()) {
         List values = module.getValues();
         if (!values.isEmpty()) {
            CrossSine.INSTANCE.getCommandManager().registerCommand(new ModuleCommand(module, values));
         }
      }
   }

   @NotNull
   public final List getModulesByName(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      Iterable $this$filter$iv = (Iterable)this.modules;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         Module it = (Module)element$iv$iv;
         int var10 = 0;
         String var11 = it.getName().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         CharSequence var10000 = (CharSequence)var11;
         var11 = name.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         if (StringsKt.contains$default(var10000, (CharSequence)var11, false, 2, (Object)null)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      return (List)destination$iv$iv;
   }

   @Nullable
   public final Module getModule(@NotNull Class moduleClass) {
      Intrinsics.checkNotNullParameter(moduleClass, "moduleClass");
      return (Module)this.moduleClassMap.get(moduleClass);
   }

   @Nullable
   public final Module get(@NotNull Class clazz) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      return this.getModule(clazz);
   }

   @Nullable
   public final Module getModule(@Nullable String moduleName) {
      Iterator var2 = ((Iterable)this.modules).iterator();

      Object var10000;
      while(true) {
         if (var2.hasNext()) {
            Object var3 = var2.next();
            Module it = (Module)var3;
            int var5 = 0;
            if (!StringsKt.equals(it.getName(), moduleName, true)) {
               continue;
            }

            var10000 = var3;
            break;
         }

         var10000 = null;
         break;
      }

      return (Module)var10000;
   }

   @NotNull
   public final List getKeyBind(int key) {
      Iterable $this$filter$iv = (Iterable)this.modules;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         Module it = (Module)element$iv$iv;
         int var10 = 0;
         if (it.getKeyBind() == key) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      return (List)destination$iv$iv;
   }

   @EventTarget
   private final void onKey(KeyEvent event) {
      if (this.pendingBindModule == null) {
         Iterable $this$filter$iv = (Iterable)CollectionsKt.toMutableList((Collection)this.modules);
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            Module it = (Module)element$iv$iv;
            int var10 = 0;
            if (it.getTriggerType() == EnumTriggerType.TOGGLE && it.getKeyBind() == event.getKey()) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $this$filter$iv = (Iterable)((List)destination$iv$iv);
         $i$f$filter = 0;

         for(Object element$iv : $this$filter$iv) {
            Module it = (Module)element$iv;
            int var15 = 0;
            it.toggle();
         }
      } else {
         Module var10000 = this.pendingBindModule;
         Intrinsics.checkNotNull(var10000);
         var10000.setKeyBind(event.getKey());
         ClientUtils var16 = ClientUtils.INSTANCE;
         StringBuilder var10001 = (new StringBuilder()).append("Bound module §a§l");
         Module var10002 = this.pendingBindModule;
         Intrinsics.checkNotNull(var10002);
         var16.displayAlert(var10001.append(var10002.getName()).append("§3 to key §a§l").append(Keyboard.getKeyName(event.getKey())).append("§3.").toString());
         this.pendingBindModule = null;
      }

   }

   @EventTarget
   private final void onUpdate(UpdateEvent event) {
      if (this.pendingBindModule == null && Minecraft.func_71410_x().field_71462_r == null) {
         Iterable $this$filter$iv = (Iterable)this.modules;
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            Module it = (Module)element$iv$iv;
            int var10 = 0;
            if (it.getTriggerType() == EnumTriggerType.PRESS) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $this$filter$iv = (Iterable)((List)destination$iv$iv);
         $i$f$filter = 0;

         for(Object element$iv : $this$filter$iv) {
            Module it = (Module)element$iv;
            int var15 = 0;
            it.setState(Keyboard.isKeyDown(it.getKeyBind()));
         }

      }
   }

   public boolean handleEvents() {
      return true;
   }
}
