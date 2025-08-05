package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.special.AutoDisable;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.IChatComponent;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AutoPlay",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0005H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001dH\u0007J \u0010\u001e\u001a\u00020\u00182\b\b\u0002\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00180\"H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/AutoPlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoStartValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "bwModeValue", "", "clickState", "", "clicking", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "queued", "replayWhenKickedValue", "showGuiWhenFailedValue", "tag", "getTag", "()Ljava/lang/String;", "waitForLobby", "handleEvents", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "queueAutoPlay", "delay", "", "runnable", "Lkotlin/Function0;", "CrossSine"}
)
public final class AutoPlay extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final Value bwModeValue;
   @NotNull
   private final Value autoStartValue;
   @NotNull
   private final Value replayWhenKickedValue;
   @NotNull
   private final Value showGuiWhenFailedValue;
   @NotNull
   private final IntegerValue delayValue;
   private boolean clicking;
   private boolean queued;
   private int clickState;
   private boolean waitForLobby;

   public AutoPlay() {
      String[] var1 = new String[]{"RedeSky", "BlocksMC", "Minemora", "Hypixel", "Jartex", "Pika", "Hydracraft", "HyCraft", "MineFC/HeroMC_Bedwars", "Supercraft"};
      this.modeValue = new ListValue("Server", var1, "RedeSky");
      var1 = new String[]{"SOLO", "4v4v4v4"};
      this.bwModeValue = (new ListValue("Mode", var1, "4v4v4v4")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AutoPlay.this.modeValue.equals("MineFC/HeroMC_Bedwars");
         }
      });
      this.autoStartValue = (new BoolValue("AutoStartAtLobby", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AutoPlay.this.modeValue.equals("MineFC/HeroMC_Bedwars");
         }
      });
      this.replayWhenKickedValue = (new BoolValue("ReplayWhenKicked", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AutoPlay.this.modeValue.equals("MineFC/HeroMC_Bedwars");
         }
      });
      this.showGuiWhenFailedValue = (new BoolValue("ShowGuiWhenFailed", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AutoPlay.this.modeValue.equals("MineFC/HeroMC_Bedwars");
         }
      });
      this.delayValue = new IntegerValue("JoinDelay", 3, 0, 7);
   }

   public void onEnable() {
      this.clickState = 0;
      this.clicking = false;
      this.queued = false;
      this.waitForLobby = false;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      String var5 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      if (Intrinsics.areEqual((Object)var5, (Object)"redesky")) {
         if (this.clicking && (packet instanceof C0EPacketClickWindow || packet instanceof C07PacketPlayerDigging)) {
            event.cancelEvent();
            return;
         }

         if (this.clickState == 2 && packet instanceof S2DPacketOpenWindow) {
            event.cancelEvent();
         }
      } else if (Intrinsics.areEqual((Object)var5, (Object)"hypixel") && this.clickState == 1 && packet instanceof S2DPacketOpenWindow) {
         event.cancelEvent();
      }

      if (packet instanceof S2FPacketSetSlot) {
         ItemStack var10000 = ((S2FPacketSetSlot)packet).func_149174_e();
         if (var10000 == null) {
            return;
         }

         final ItemStack item = var10000;
         int windowId = ((S2FPacketSetSlot)packet).func_149175_c();
         int slot = ((S2FPacketSetSlot)packet).func_149173_d();
         String itemName = item.func_77977_a();
         String displayName = item.func_82833_r();
         String var10 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var10.hashCode()) {
            case -664563300:
               if (!var10.equals("blocksmc")) {
                  return;
               }
               break;
            case 1083223725:
               if (var10.equals("redesky")) {
                  if (this.clickState == 0 && windowId == 0 && slot == 42) {
                     Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                     if (StringsKt.contains((CharSequence)itemName, (CharSequence)"paper", true)) {
                        Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
                        if (StringsKt.contains((CharSequence)displayName, (CharSequence)"Jogar novamente", true)) {
                           this.clickState = 1;
                           this.clicking = true;
                           queueAutoPlay$default(this, 0L, new Function0() {
                              public final void invoke() {
                                 MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(6)));
                                 MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(item)));
                                 MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                                 AutoPlay.this.clickState = 2;
                              }
                           }, 1, (Object)null);
                           return;
                        }
                     }
                  }

                  if (this.clickState == 2 && windowId != 0 && slot == 11) {
                     Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                     if (StringsKt.contains((CharSequence)itemName, (CharSequence)"enderPearl", true)) {
                        Timer var9 = new Timer();
                        long var36 = 500L;
                        TimerTask var12 = new AutoPlay$onPacket$$inlined$schedule$1(this, windowId, slot, item);
                        var9.schedule(var12, var36);
                     }

                     return;
                  }
               }

               return;
            case 1381910549:
               if (!var10.equals("hypixel")) {
                  return;
               }
               break;
            default:
               return;
         }

         if (this.clickState == 0 && windowId == 0 && slot == 43) {
            Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
            if (StringsKt.contains((CharSequence)itemName, (CharSequence)"paper", true)) {
               queueAutoPlay$default(this, 0L, new Function0() {
                  public final void invoke() {
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(7)));
                     byte var1 = 2;
                     ItemStack var2 = item;
                     int var3 = 0;

                     while(var3 < var1) {
                        ++var3;
                        int var6 = 0;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(var2)));
                     }

                  }
               }, 1, (Object)null);
               this.clickState = 1;
            }
         }

         if (this.modeValue.equals("hypixel") && this.clickState == 1 && windowId != 0 && StringsKt.equals(itemName, "item.fireworks", true)) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0EPacketClickWindow(windowId, slot, 0, 0, item, (short)1919)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0DPacketCloseWindow(windowId)));
         }
      } else if (packet instanceof S02PacketChat) {
         String text = ((S02PacketChat)packet).func_148915_c().func_150260_c();
         IChatComponent component = ((S02PacketChat)packet).func_148915_c();
         String $i$f$forEach = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue($i$f$forEach, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch ($i$f$forEach.hashCode()) {
            case -1714526331:
               if ($i$f$forEach.equals("supercraft")) {
                  Intrinsics.checkNotNullExpressionValue(text, "text");
                  if (StringsKt.contains((CharSequence)text, (CharSequence)Intrinsics.stringPlus("Ganador: ", MinecraftInstance.mc.field_71449_j.func_111285_a()), true) || StringsKt.contains((CharSequence)text, (CharSequence)Intrinsics.stringPlus(MinecraftInstance.mc.field_71449_j.func_111285_a(), " fue asesinado"), true)) {
                     queueAutoPlay$default(this, 0L, null.INSTANCE, 1, (Object)null);
                  }

                  if (StringsKt.contains((CharSequence)text, (CharSequence)"El juego ya fue iniciado.", true)) {
                     queueAutoPlay$default(this, 0L, null.INSTANCE, 1, (Object)null);
                  }
               }
               break;
            case -1362756060:
               if ($i$f$forEach.equals("minemora")) {
                  Intrinsics.checkNotNullExpressionValue(text, "text");
                  if (StringsKt.contains((CharSequence)text, (CharSequence)"Has click en alguna de las siguientes opciones", true)) {
                     queueAutoPlay$default(this, 0L, null.INSTANCE, 1, (Object)null);
                  }
               }
               break;
            case -1167184852:
               if ($i$f$forEach.equals("jartex")) {
                  Intrinsics.checkNotNullExpressionValue(text, "text");
                  if (StringsKt.contains((CharSequence)text, (CharSequence)"Play Again?", true)) {
                     List var23 = component.func_150253_a();
                     Intrinsics.checkNotNullExpressionValue(var23, "component.siblings");
                     Iterable $this$forEach$iv = (Iterable)var23;
                     int $i$f$forEach = 0;

                     for(Object element$iv : $this$forEach$iv) {
                        IChatComponent sib = (IChatComponent)element$iv;
                        int var41 = 0;
                        final ClickEvent clickEvent = sib.func_150256_b().func_150235_h();
                        if (clickEvent != null && clickEvent.func_150669_a() == Action.RUN_COMMAND) {
                           String var46 = clickEvent.func_150668_b();
                           Intrinsics.checkNotNullExpressionValue(var46, "clickEvent.value");
                           if (StringsKt.startsWith$default(var46, "/", false, 2, (Object)null)) {
                              queueAutoPlay$default(this, 0L, new Function0() {
                                 public final void invoke() {
                                    MinecraftInstance.mc.field_71439_g.func_71165_d(clickEvent.func_150668_b());
                                 }
                              }, 1, (Object)null);
                           }
                        }
                     }
                  }
               }
               break;
            case -664563300:
               if ($i$f$forEach.equals("blocksmc") && this.clickState == 1) {
                  Intrinsics.checkNotNullExpressionValue(text, "text");
                  if (StringsKt.contains((CharSequence)text, (CharSequence)"Only VIP players can join full servers!", true)) {
                     Timer var22 = new Timer();
                     long var28 = 1500L;
                     TimerTask var34 = new AutoPlay$onPacket$$inlined$schedule$2();
                     var22.schedule(var34, var28);
                  }
               }
               break;
            case -633949188:
               if ($i$f$forEach.equals("minefc/heromc_bedwars")) {
                  Intrinsics.checkNotNullExpressionValue(text, "text");
                  if (StringsKt.contains((CharSequence)text, (CharSequence)"Bạn đã bị loại!", false) || StringsKt.contains((CharSequence)text, (CharSequence)"đã thắng trò chơi", false)) {
                     MinecraftInstance.mc.field_71439_g.func_71165_d("/bw leave");
                     this.waitForLobby = true;
                  }

                  if ((this.waitForLobby || (Boolean)this.autoStartValue.get()) && StringsKt.contains((CharSequence)text, (CharSequence)"¡Hiển thị", false) || (Boolean)this.replayWhenKickedValue.get() && StringsKt.contains((CharSequence)text, (CharSequence)"[Anticheat] You have been kicked from the server!", false)) {
                     queueAutoPlay$default(this, 0L, new Function0() {
                        public final void invoke() {
                           MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/bw join ", AutoPlay.this.bwModeValue.get()));
                        }
                     }, 1, (Object)null);
                     this.waitForLobby = false;
                  }

                  if ((Boolean)this.showGuiWhenFailedValue.get() && StringsKt.contains((CharSequence)text, (CharSequence)"giây", false) && StringsKt.contains((CharSequence)text, (CharSequence)"thất bại", false)) {
                     MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/bw gui ", this.bwModeValue.get()));
                  }
               }
               break;
            case 3440911:
               if ($i$f$forEach.equals("pika")) {
                  Intrinsics.checkNotNullExpressionValue(text, "text");
                  if (StringsKt.contains((CharSequence)text, (CharSequence)"Click here to play again", true)) {
                     List var20 = component.func_150253_a();
                     Intrinsics.checkNotNullExpressionValue(var20, "component.siblings");
                     Iterable $this$forEach$iv = (Iterable)var20;
                     int $i$f$forEach = 0;

                     for(Object element$iv : $this$forEach$iv) {
                        IChatComponent sib = (IChatComponent)element$iv;
                        int var40 = 0;
                        final ClickEvent clickEvent = sib.func_150256_b().func_150235_h();
                        if (clickEvent != null && clickEvent.func_150669_a() == Action.RUN_COMMAND) {
                           String var45 = clickEvent.func_150668_b();
                           Intrinsics.checkNotNullExpressionValue(var45, "clickEvent.value");
                           if (StringsKt.startsWith$default(var45, "/", false, 2, (Object)null)) {
                              queueAutoPlay$default(this, 0L, new Function0() {
                                 public final void invoke() {
                                    MinecraftInstance.mc.field_71439_g.func_71165_d(clickEvent.func_150668_b());
                                 }
                              }, 1, (Object)null);
                           }
                        }
                     }
                  }

                  if (StringsKt.contains$default((CharSequence)text, (CharSequence)Intrinsics.stringPlus(MinecraftInstance.mc.func_110432_I().func_111285_a(), " has been"), false, 2, (Object)null) || StringsKt.contains$default((CharSequence)text, (CharSequence)Intrinsics.stringPlus(MinecraftInstance.mc.func_110432_I().func_111285_a(), " died."), false, 2, (Object)null)) {
                     queueAutoPlay$default(this, 0L, null.INSTANCE, 1, (Object)null);
                  }
               }
               break;
            case 534301822:
               if ($i$f$forEach.equals("hydracraft")) {
                  Intrinsics.checkNotNullExpressionValue(text, "text");
                  if (StringsKt.contains((CharSequence)text, (CharSequence)"Has ganado ¿Qué quieres hacer?", true)) {
                     queueAutoPlay$default(this, 0L, null.INSTANCE, 1, (Object)null);
                  }
               }
               break;
            case 1370150831:
               if ($i$f$forEach.equals("hycraft")) {
                  List $this$forEach$iv = component.func_150253_a();
                  Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "component.siblings");
                  Iterable $this$forEach$iv = (Iterable)$this$forEach$iv;
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     IChatComponent sib = (IChatComponent)element$iv;
                     int var11 = 0;
                     final ClickEvent clickEvent = sib.func_150256_b().func_150235_h();
                     if (clickEvent != null && clickEvent.func_150669_a() == Action.RUN_COMMAND) {
                        String var13 = clickEvent.func_150668_b();
                        Intrinsics.checkNotNullExpressionValue(var13, "clickEvent.value");
                        if (StringsKt.contains$default((CharSequence)var13, (CharSequence)"playagain", false, 2, (Object)null)) {
                           queueAutoPlay$default(this, 0L, new Function0() {
                              public final void invoke() {
                                 MinecraftInstance.mc.field_71439_g.func_71165_d(clickEvent.func_150668_b());
                              }
                           }, 1, (Object)null);
                        }
                     }
                  }
               }
               break;
            case 1381910549:
               if ($i$f$forEach.equals("hypixel")) {
                  IChatComponent $this$forEach$iv = ((S02PacketChat)packet).func_148915_c();
                  Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "packet.chatComponent");
                  onPacket$process(this, $this$forEach$iv);
               }
         }
      }

   }

   private final void queueAutoPlay(long delay, Function0 runnable) {
      if (!this.queued) {
         this.queued = true;
         AutoDisable.INSTANCE.handleGameEnd();
         if (this.getState()) {
            Timer var4 = new Timer();
            TimerTask var5 = new AutoPlay$queueAutoPlay$$inlined$schedule$1(this, runnable);
            var4.schedule(var5, delay);
         }

      }
   }

   // $FF: synthetic method
   static void queueAutoPlay$default(AutoPlay var0, long var1, Function0 var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = (long)((Number)var0.delayValue.get()).intValue() * (long)1000;
      }

      var0.queueAutoPlay(var1, var3);
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.clicking = false;
      this.clickState = 0;
      this.queued = false;
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }

   public boolean handleEvents() {
      return true;
   }

   private static final void onPacket$process(AutoPlay this$0, IChatComponent component) {
      ClickEvent var10000 = component.func_150256_b().func_150235_h();
      final String value = var10000 == null ? null : var10000.func_150668_b();
      if (value != null && StringsKt.startsWith(value, "/play", true)) {
         queueAutoPlay$default(this$0, 0L, new Function0() {
            public final void invoke() {
               MinecraftInstance.mc.field_71439_g.func_71165_d(value);
            }
         }, 1, (Object)null);
      }

      List var3 = component.func_150253_a();
      Intrinsics.checkNotNullExpressionValue(var3, "component.siblings");
      Iterable $this$forEach$iv = (Iterable)var3;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         IChatComponent it = (IChatComponent)element$iv;
         int var8 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         onPacket$process(this$0, it);
      }

   }

   // $FF: synthetic method
   public static final void access$setClicking$p(AutoPlay $this, boolean var1) {
      $this.clicking = var1;
   }

   // $FF: synthetic method
   public static final void access$setQueued$p(AutoPlay $this, boolean var1) {
      $this.queued = var1;
   }
}
