package net.ccbluex.liquidbounce.script.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.api.scripting.JSObject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.SwingEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "ScriptModule",
   category = ModuleCategory.CLIENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000ð\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00062\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0016\u0010\"\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0003J\u0010\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020,H\u0007J\u0010\u0010-\u001a\u00020\u001e2\u0006\u0010.\u001a\u00020/H\u0007J\b\u00100\u001a\u00020\u001eH\u0016J\b\u00101\u001a\u00020\u001eH\u0016J\u0010\u00102\u001a\u00020\u001e2\u0006\u00103\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020\u001e2\u0006\u00106\u001a\u000207H\u0007J\b\u00108\u001a\u00020\u001eH\u0016J\u0010\u00109\u001a\u00020\u001e2\u0006\u0010:\u001a\u00020;H\u0007J\u0010\u0010<\u001a\u00020\u001e2\u0006\u0010=\u001a\u00020>H\u0007J\u0010\u0010?\u001a\u00020\u001e2\u0006\u0010@\u001a\u00020AH\u0007J\u0010\u0010B\u001a\u00020\u001e2\u0006\u0010C\u001a\u00020DH\u0007J\u0010\u0010E\u001a\u00020\u001e2\u0006\u0010F\u001a\u00020GH\u0007J\u0010\u0010H\u001a\u00020\u001e2\u0006\u0010I\u001a\u00020JH\u0007J\u0010\u0010K\u001a\u00020\u001e2\u0006\u0010L\u001a\u00020MH\u0007J\u0010\u0010N\u001a\u00020\u001e2\u0006\u0010O\u001a\u00020PH\u0007J\u0010\u0010Q\u001a\u00020\u001e2\u0006\u0010R\u001a\u00020SH\u0007J\u0010\u0010T\u001a\u00020\u001e2\u0006\u0010U\u001a\u00020VH\u0007J\u0010\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020YH\u0007J\u0010\u0010Z\u001a\u00020\u001e2\u0006\u0010[\u001a\u00020\\H\u0007J\u0010\u0010]\u001a\u00020\u001e2\u0006\u0010^\u001a\u00020_H\u0007J\u0010\u0010`\u001a\u00020\u001e2\u0006\u0010a\u001a\u00020bH\u0007J\u0010\u0010c\u001a\u00020\u001e2\u0006\u0010d\u001a\u00020eH\u0007R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R2\u0010\u0007\u001a&\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\bj\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\fj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R?\u0010\u000e\u001a&\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\bj\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t`\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R(\u0010\u0014\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006f"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/ScriptModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "moduleObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "_tag", "", "_values", "Ljava/util/LinkedHashMap;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/collections/LinkedHashMap;", "events", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "settings", "getSettings", "()Ljava/util/LinkedHashMap;", "settings$delegate", "Lkotlin/Lazy;", "value", "tag", "getTag", "()Ljava/lang/String;", "setTag", "(Ljava/lang/String;)V", "values", "", "getValues", "()Ljava/util/List;", "callEvent", "", "eventName", "payload", "", "on", "handler", "onAttack", "attackEvent", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onBlockBB", "blockBBEvent", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onClickWindow", "clickWindowEvent", "Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "onClientShutdown", "clientShutdownEvent", "Lnet/ccbluex/liquidbounce/event/ClientShutdownEvent;", "onDisable", "onEnable", "onJump", "jumpEvent", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onKey", "keyEvent", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onLoad", "onMotion", "motionEvent", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "moveEvent", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "packetEvent", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPushOut", "pushOutEvent", "Lnet/ccbluex/liquidbounce/event/PushOutEvent;", "onRender2D", "render2DEvent", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "render3DEvent", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onScreen", "screenEvent", "Lnet/ccbluex/liquidbounce/event/ScreenEvent;", "onSession", "sessionEvent", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "onSlowDown", "slowDownEvent", "Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "onStep", "stepEvent", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStrafe", "strafeEvent", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onSwing", "swingEvent", "Lnet/ccbluex/liquidbounce/event/SwingEvent;", "onTick", "tickEvent", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "updateEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "worldEvent", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class ScriptModule extends Module {
   @NotNull
   private final JSObject moduleObject;
   @NotNull
   private final HashMap events;
   @NotNull
   private final LinkedHashMap _values;
   @Nullable
   private String _tag;
   @NotNull
   private final Lazy settings$delegate;

   public ScriptModule(@NotNull JSObject moduleObject) {
      Intrinsics.checkNotNullParameter(moduleObject, "moduleObject");
      super();
      this.moduleObject = moduleObject;
      this.events = new HashMap();
      this._values = new LinkedHashMap();
      this.settings$delegate = LazyKt.lazy(new Function0() {
         @NotNull
         public final LinkedHashMap invoke() {
            return ScriptModule.this._values;
         }
      });
      Object var10001 = this.moduleObject.getMember("name");
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
      } else {
         this.setName((String)var10001);
         Object var10000 = this.moduleObject.getMember("category");
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
         } else {
            String categoryString = (String)var10000;
            ModuleCategory[] var3 = ModuleCategory.values();
            int var4 = 0;
            int var5 = var3.length;

            while(var4 < var5) {
               ModuleCategory category = var3[var4];
               ++var4;
               if (StringsKt.equals(categoryString, category.name(), true)) {
                  this.setCategory(category);
               }
            }

            if (this.moduleObject.hasMember("settings")) {
               var10000 = this.moduleObject.getMember("settings");
               if (var10000 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type jdk.nashorn.api.scripting.JSObject");
               }

               JSObject settings = (JSObject)var10000;

               for(String settingName : settings.keySet()) {
                  Map var11 = (Map)this._values;
                  Intrinsics.checkNotNullExpressionValue(settingName, "settingName");
                  Object var10002 = settings.getMember(settingName);
                  if (var10002 == null) {
                     throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.value.Value<*>");
                  }

                  var11.put(settingName, (Value)var10002);
               }
            }

            if (this.moduleObject.hasMember("tag")) {
               var10001 = this.moduleObject.getMember("tag");
               if (var10001 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
               }

               this._tag = (String)var10001;
            }

         }
      }
   }

   @NotNull
   public final LinkedHashMap getSettings() {
      Lazy var1 = this.settings$delegate;
      return (LinkedHashMap)var1.getValue();
   }

   public void onLoad() {
   }

   @NotNull
   public List getValues() {
      Collection var1 = this._values.values();
      Intrinsics.checkNotNullExpressionValue(var1, "_values.values");
      return CollectionsKt.toList((Iterable)var1);
   }

   @Nullable
   public String getTag() {
      return this._tag;
   }

   public void setTag(@Nullable String value) {
      this._tag = value;
   }

   public final void on(@NotNull String eventName, @NotNull JSObject handler) {
      Intrinsics.checkNotNullParameter(eventName, "eventName");
      Intrinsics.checkNotNullParameter(handler, "handler");
      ((Map)this.events).put(eventName, handler);
   }

   public void onEnable() {
      callEvent$default(this, "enable", (Object)null, 2, (Object)null);
   }

   public void onDisable() {
      callEvent$default(this, "disable", (Object)null, 2, (Object)null);
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent updateEvent) {
      Intrinsics.checkNotNullParameter(updateEvent, "updateEvent");
      callEvent$default(this, "update", (Object)null, 2, (Object)null);
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent motionEvent) {
      Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
      this.callEvent("motion", motionEvent);
      switch (ScriptModule.WhenMappings.$EnumSwitchMapping$0[motionEvent.getEventState().ordinal()]) {
         case 1:
            this.callEvent("premotion", motionEvent);
            break;
         case 2:
            this.callEvent("postmotion", motionEvent);
      }

   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent render2DEvent) {
      Intrinsics.checkNotNullParameter(render2DEvent, "render2DEvent");
      this.callEvent("render2D", render2DEvent);
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent render3DEvent) {
      Intrinsics.checkNotNullParameter(render3DEvent, "render3DEvent");
      this.callEvent("render3D", render3DEvent);
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent packetEvent) {
      Intrinsics.checkNotNullParameter(packetEvent, "packetEvent");
      this.callEvent("packet", packetEvent);
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent jumpEvent) {
      Intrinsics.checkNotNullParameter(jumpEvent, "jumpEvent");
      this.callEvent("jump", jumpEvent);
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent attackEvent) {
      Intrinsics.checkNotNullParameter(attackEvent, "attackEvent");
      this.callEvent("attack", attackEvent);
   }

   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent blockBBEvent) {
      Intrinsics.checkNotNullParameter(blockBBEvent, "blockBBEvent");
      this.callEvent("blockBB", blockBBEvent);
   }

   @EventTarget
   public final void onClientShutdown(@NotNull ClientShutdownEvent clientShutdownEvent) {
      Intrinsics.checkNotNullParameter(clientShutdownEvent, "clientShutdownEvent");
      this.callEvent("clientShutdown", clientShutdownEvent);
   }

   @EventTarget
   public final void onPushOut(@NotNull PushOutEvent pushOutEvent) {
      Intrinsics.checkNotNullParameter(pushOutEvent, "pushOutEvent");
      this.callEvent("pushOut", pushOutEvent);
   }

   @EventTarget
   public final void onScreen(@NotNull ScreenEvent screenEvent) {
      Intrinsics.checkNotNullParameter(screenEvent, "screenEvent");
      this.callEvent("screen", screenEvent);
   }

   @EventTarget
   public final void onTick(@NotNull TickEvent tickEvent) {
      Intrinsics.checkNotNullParameter(tickEvent, "tickEvent");
      this.callEvent("tick", tickEvent);
   }

   @EventTarget
   public final void onKey(@NotNull KeyEvent keyEvent) {
      Intrinsics.checkNotNullParameter(keyEvent, "keyEvent");
      this.callEvent("key", keyEvent);
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent moveEvent) {
      Intrinsics.checkNotNullParameter(moveEvent, "moveEvent");
      this.callEvent("move", moveEvent);
   }

   @EventTarget
   public final void onStep(@NotNull StepEvent stepEvent) {
      Intrinsics.checkNotNullParameter(stepEvent, "stepEvent");
      this.callEvent("step", stepEvent);
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent worldEvent) {
      Intrinsics.checkNotNullParameter(worldEvent, "worldEvent");
      this.callEvent("world", worldEvent);
   }

   @EventTarget
   public final void onSession(@NotNull SessionEvent sessionEvent) {
      Intrinsics.checkNotNullParameter(sessionEvent, "sessionEvent");
      callEvent$default(this, "session", (Object)null, 2, (Object)null);
   }

   @EventTarget
   public final void onClickWindow(@NotNull ClickWindowEvent clickWindowEvent) {
      Intrinsics.checkNotNullParameter(clickWindowEvent, "clickWindowEvent");
      this.callEvent("clickWindow", clickWindowEvent);
   }

   @EventTarget
   public final void onStrafe(@NotNull StrafeEvent strafeEvent) {
      Intrinsics.checkNotNullParameter(strafeEvent, "strafeEvent");
      this.callEvent("strafe", strafeEvent);
   }

   @EventTarget
   public final void onSwing(@NotNull SwingEvent swingEvent) {
      Intrinsics.checkNotNullParameter(swingEvent, "swingEvent");
      this.callEvent("swing", swingEvent);
   }

   @EventTarget
   public final void onSlowDown(@NotNull SlowDownEvent slowDownEvent) {
      Intrinsics.checkNotNullParameter(slowDownEvent, "slowDownEvent");
      this.callEvent("slowDown", slowDownEvent);
   }

   private final void callEvent(String eventName, Object payload) {
      try {
         JSObject var10000 = (JSObject)this.events.get(eventName);
         if (var10000 != null) {
            JSObject var10001 = this.moduleObject;
            Object[] var4 = new Object[]{payload};
            var10000.call(var10001, var4);
         }
      } catch (Throwable throwable) {
         ClientUtils.INSTANCE.logError("[CrossSineAPI] Exception in module '" + this.getName() + "'!", throwable);
      }

   }

   // $FF: synthetic method
   static void callEvent$default(ScriptModule var0, String var1, Object var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.callEvent(var1, var2);
   }

   // $FF: synthetic class
   @Metadata(
      mv = {1, 6, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[EventState.values().length];
         var0[EventState.PRE.ordinal()] = 1;
         var0[EventState.POST.ordinal()] = 2;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
