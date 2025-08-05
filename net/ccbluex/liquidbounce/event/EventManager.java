package net.ccbluex.liquidbounce.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000fRF\u0010\u0003\u001a:\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004j\u001c\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/EventManager;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "registry", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/event/Event;", "", "Lnet/ccbluex/liquidbounce/event/EventHook;", "Lkotlin/collections/HashMap;", "callEvent", "", "event", "registerListener", "listener", "Lnet/ccbluex/liquidbounce/event/Listenable;", "unregisterListener", "listenable", "CrossSine"}
)
public final class EventManager extends MinecraftInstance {
   @NotNull
   private final HashMap registry = new HashMap();

   public final void registerListener(@NotNull Listenable listener) {
      Intrinsics.checkNotNullParameter(listener, "listener");
      if (!CrossSine.INSTANCE.getDestruced()) {
         Method[] var3 = listener.getClass().getDeclaredMethods();
         Intrinsics.checkNotNullExpressionValue(var3, "listener.javaClass.declaredMethods");
         Method[] var2 = var3;
         int var14 = 0;
         int var4 = var3.length;

         while(var14 < var4) {
            Method method = var2[var14];
            ++var14;
            if (method.isAnnotationPresent(EventTarget.class) && method.getParameterTypes().length == 1) {
               try {
                  if (!method.isAccessible()) {
                     method.setAccessible(true);
                  }

                  Class var10000 = method.getParameterTypes()[0];
                  if (var10000 == null) {
                     throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.event.Event>");
                  }

                  Class eventClass = var10000;
                  EventTarget eventTarget = (EventTarget)method.getAnnotation(EventTarget.class);
                  Map $this$getOrPut$iv = (Map)this.registry;
                  int $i$f$getOrPut = 0;
                  Object value$iv = $this$getOrPut$iv.get(eventClass);
                  Object var16;
                  if (value$iv == null) {
                     int var12 = 0;
                     Object answer$iv = (List)(new ArrayList());
                     $this$getOrPut$iv.put(eventClass, answer$iv);
                     var16 = answer$iv;
                  } else {
                     var16 = value$iv;
                  }

                  List invokableEventTargets = (List)var16;
                  Intrinsics.checkNotNullExpressionValue(method, "method");
                  Intrinsics.checkNotNullExpressionValue(eventTarget, "eventTarget");
                  invokableEventTargets.add(new EventHook(listener, method, eventTarget));
                  ((Map)this.registry).put(eventClass, invokableEventTargets);
               } catch (Throwable t) {
                  t.printStackTrace();
               }
            }
         }

      }
   }

   public final void unregisterListener(@NotNull Listenable listenable) {
      Intrinsics.checkNotNullParameter(listenable, "listenable");

      for(Map.Entry var3 : ((Map)this.registry).entrySet()) {
         Class key = (Class)var3.getKey();
         List targets = (List)var3.getValue();
         targets.removeIf(EventManager::unregisterListener$lambda-1);
         ((Map)this.registry).put(key, targets);
      }

   }

   public final void callEvent(@NotNull Event event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!CrossSine.INSTANCE.getDestruced()) {
         List var10000 = (List)this.registry.get(event.getClass());
         if (var10000 != null) {
            List targets = var10000;

            try {
               for(EventHook invokableEventTarget : targets) {
                  try {
                     if (invokableEventTarget.getEventClass().handleEvents() || invokableEventTarget.isIgnoreCondition()) {
                        Method var8 = invokableEventTarget.getMethod();
                        Listenable var10001 = invokableEventTarget.getEventClass();
                        Object[] var5 = new Object[]{event};
                        var8.invoke(var10001, var5);
                     }
                  } catch (Throwable throwable) {
                     throwable.printStackTrace();
                  }
               }
            } catch (Exception e) {
               e.printStackTrace();
            }

         }
      }
   }

   private static final boolean unregisterListener$lambda_1/* $FF was: unregisterListener$lambda-1*/(Listenable $listenable, EventHook it) {
      Intrinsics.checkNotNullParameter($listenable, "$listenable");
      Intrinsics.checkNotNullParameter(it, "it");
      return Intrinsics.areEqual((Object)it.getEventClass(), (Object)$listenable);
   }
}
