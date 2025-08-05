package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
   name = "Inventory",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0007J\b\u0010\"\u001a\u00020\u001fH\u0016J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020*H\u0007J\b\u0010+\u001a\u00020\u001fH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006,"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Inventory;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blinkPacketList", "", "Lnet/minecraft/network/play/client/C03PacketPlayer;", "bypassValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "customSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "<set-?>", "", "invOpen", "getInvOpen", "()Z", "lastInvOpen", "getLastInvOpen", "noDetectableValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "noMoveClicksValue", "noSprintValue", "getNoSprintValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "packetListYes", "Lnet/minecraft/network/play/client/C0EPacketClickWindow;", "rotateValue", "tag", "", "getTag", "()Ljava/lang/String;", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "onDisable", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onScreen", "Lnet/ccbluex/liquidbounce/event/ScreenEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "updateKeyState", "CrossSine"}
)
public final class Inventory extends Module {
   @NotNull
   private final BoolValue noDetectableValue = new BoolValue("NoDetectable", false);
   @NotNull
   private final ListValue bypassValue;
   @NotNull
   private final BoolValue rotateValue;
   @NotNull
   private final BoolValue noMoveClicksValue;
   @NotNull
   private final ListValue noSprintValue;
   @NotNull
   private final FloatValue customSpeed;
   @NotNull
   private final List blinkPacketList;
   @NotNull
   private final List packetListYes;
   private boolean lastInvOpen;
   private boolean invOpen;

   public Inventory() {
      String[] var1 = new String[]{"NoOpenPacket", "Blink", "BlocksMC", "PacketInv", "None"};
      this.bypassValue = new ListValue("Bypass", var1, "None");
      this.rotateValue = new BoolValue("Rotate", true);
      this.noMoveClicksValue = new BoolValue("NoMoveClicks", false);
      var1 = new String[]{"Real", "PacketSpoof", "None"};
      this.noSprintValue = new ListValue("NoSprint", var1, "None");
      this.customSpeed = new FloatValue("CustomSpeed", 1.0F, 0.1F, 1.0F);
      this.blinkPacketList = (List)(new ArrayList());
      this.packetListYes = (List)(new ArrayList());
   }

   @NotNull
   public final ListValue getNoSprintValue() {
      return this.noSprintValue;
   }

   public final boolean getLastInvOpen() {
      return this.lastInvOpen;
   }

   public final boolean getInvOpen() {
      return this.invOpen;
   }

   private final void updateKeyState() {
      if (MinecraftInstance.mc.field_71462_r != null && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat) && (!(Boolean)this.noDetectableValue.get() || !(MinecraftInstance.mc.field_71462_r instanceof GuiContainer))) {
         MinecraftInstance.mc.field_71474_y.field_74351_w.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74351_w);
         MinecraftInstance.mc.field_71474_y.field_74368_y.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74368_y);
         MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74366_z);
         MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74370_x);
         MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A);
         MinecraftInstance.mc.field_71474_y.field_151444_V.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_151444_V);
         if ((Boolean)this.rotateValue.get()) {
            if (Keyboard.isKeyDown(200) && MinecraftInstance.mc.field_71439_g.field_70125_A > -90.0F) {
               EntityPlayerSP var1 = MinecraftInstance.mc.field_71439_g;
               var1.field_70125_A -= (float)5;
            }

            if (Keyboard.isKeyDown(208) && MinecraftInstance.mc.field_71439_g.field_70125_A < 90.0F) {
               EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
               var2.field_70125_A += (float)5;
            }

            if (Keyboard.isKeyDown(203)) {
               EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
               var3.field_70177_z -= (float)5;
            }

            if (Keyboard.isKeyDown(205)) {
               EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
               var4.field_70177_z += (float)5;
            }
         }
      }

   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.updateKeyState();
      if (MinecraftInstance.mc.field_71462_r instanceof GuiContainer && ((Number)this.customSpeed.get()).floatValue() < 1.0F) {
         EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
         var2.field_70159_w *= (double)((Number)this.customSpeed.get()).floatValue();
         var2 = MinecraftInstance.mc.field_71439_g;
         var2.field_70179_y *= (double)((Number)this.customSpeed.get()).floatValue();
      }

   }

   @EventTarget
   public final void onScreen(@NotNull ScreenEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.updateKeyState();
   }

   @EventTarget
   public final void onClick(@NotNull ClickWindowEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)this.noMoveClicksValue.get() && MovementUtils.INSTANCE.isMoving()) {
         event.cancelEvent();
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      this.lastInvOpen = this.invOpen;
      if (packet instanceof S2DPacketOpenWindow || packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).func_149435_c() == EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
         this.invOpen = true;
         if (this.noSprintValue.equals("PacketSpoof")) {
            if (MinecraftInstance.mc.field_71439_g.func_70051_ag()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.STOP_SPRINTING)));
            }

            if (MinecraftInstance.mc.field_71439_g.func_70093_af()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.STOP_SNEAKING)));
            }
         }
      }

      if (packet instanceof S2EPacketCloseWindow || packet instanceof C0DPacketCloseWindow) {
         this.invOpen = false;
         if (this.noSprintValue.equals("PacketSpoof")) {
            if (MinecraftInstance.mc.field_71439_g.func_70051_ag()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.START_SPRINTING)));
            }

            if (MinecraftInstance.mc.field_71439_g.func_70093_af()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.START_SNEAKING)));
            }
         }
      }

      String var5 = ((String)this.bypassValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var5.hashCode()) {
         case -664563300:
            if (var5.equals("blocksmc") && packet instanceof C0FPacketConfirmTransaction && this.lastInvOpen) {
               event.cancelEvent();
            }
            break;
         case 93826908:
            if (var5.equals("blink") && packet instanceof C03PacketPlayer) {
               if (this.lastInvOpen) {
                  this.blinkPacketList.add(packet);
                  event.cancelEvent();
               } else if (!((Collection)this.blinkPacketList).isEmpty()) {
                  this.blinkPacketList.add(packet);
                  event.cancelEvent();
                  Iterable $this$forEach$iv = (Iterable)this.blinkPacketList;
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     C03PacketPlayer it = (C03PacketPlayer)element$iv;
                     int var16 = 0;
                     PacketUtils.sendPacketNoEvent((Packet)it);
                  }

                  this.blinkPacketList.clear();
               }
            }
            break;
         case 581936083:
            if (var5.equals("noopenpacket") && packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).func_149435_c() == EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
               event.cancelEvent();
            }
            break;
         case 1806147177:
            if (var5.equals("packetinv")) {
               if (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).func_149435_c() == EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                  event.cancelEvent();
               }

               if (packet instanceof C0DPacketCloseWindow) {
                  event.cancelEvent();
               }

               if (packet instanceof C0EPacketClickWindow) {
                  this.packetListYes.clear();
                  this.packetListYes.add(packet);
                  event.cancelEvent();
                  PacketUtils.sendPacketNoEvent((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
                  Iterable $this$forEach$iv = (Iterable)this.packetListYes;
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     C0EPacketClickWindow it = (C0EPacketClickWindow)element$iv;
                     int var9 = 0;
                     PacketUtils.sendPacketNoEvent((Packet)it);
                  }

                  this.packetListYes.clear();
                  PacketUtils.sendPacketNoEvent((Packet)(new C0DPacketCloseWindow(MinecraftInstance.mc.field_71439_g.field_71069_bz.field_75152_c)));
               }
            }
      }

   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.blinkPacketList.clear();
      this.invOpen = false;
      this.lastInvOpen = false;
   }

   public void onDisable() {
      if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74351_w) || MinecraftInstance.mc.field_71462_r != null) {
         MinecraftInstance.mc.field_71474_y.field_74351_w.field_74513_e = false;
      }

      if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74368_y) || MinecraftInstance.mc.field_71462_r != null) {
         MinecraftInstance.mc.field_71474_y.field_74368_y.field_74513_e = false;
      }

      if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74366_z) || MinecraftInstance.mc.field_71462_r != null) {
         MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = false;
      }

      if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74370_x) || MinecraftInstance.mc.field_71462_r != null) {
         MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = false;
      }

      if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A) || MinecraftInstance.mc.field_71462_r != null) {
         MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
      }

      if (!GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_151444_V) || MinecraftInstance.mc.field_71462_r != null) {
         MinecraftInstance.mc.field_71474_y.field_151444_V.field_74513_e = false;
      }

      this.blinkPacketList.clear();
      this.lastInvOpen = false;
      this.invOpen = false;
   }

   @Nullable
   public String getTag() {
      return (String)this.bypassValue.get();
   }
}
