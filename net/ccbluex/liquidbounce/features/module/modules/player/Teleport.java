package net.ccbluex.liquidbounce.features.module.modules.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PathUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "Teleport",
   category = ModuleCategory.PLAYER
)
public class Teleport extends Module {
   private final BoolValue ignoreNoCollision = new BoolValue("IgnoreNoCollision", true);
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Tp", "Blink", "Flag", "Rewinside", "OldRewinside", "Spoof", "Minesucht", "AAC3.5.0", "BWRel", "Karhu"}, "Tp");
   private final ListValue buttonValue = new ListValue("Button", new String[]{"Left", "Right", "Middle"}, "Middle");
   private final BoolValue needSneak = new BoolValue("NeedSneak", true);
   private final tickTimer flyTimer = new tickTimer();
   private boolean hadGround;
   private double fixedY;
   private final List packets = new ArrayList();
   private boolean disableLogger = false;
   private boolean zitter = false;
   private boolean doTeleport = false;
   private boolean freeze = false;
   private final tickTimer freezeTimer = new tickTimer();
   private final tickTimer respawnTimer = new tickTimer();
   private int delay;
   private BlockPos endPos;
   private MovingObjectPosition objectPosition;
   private double endX = (double)0.0F;
   private double endY = (double)0.0F;
   private double endZ = (double)0.0F;

   public void onEnable() {
      int matrixStage = -1;
      if (this.modeValue.equals("AAC3.5.0")) {
         this.alert("§c>>> §a§lTeleport §fAAC 3.5.0 §c<<<");
         this.alert("§cHow to teleport: §aPress " + (String)this.buttonValue.get() + " mouse button.");
         this.alert("§cHow to cancel teleport: §aDisable teleport module.");
      }

   }

   public void onDisable() {
      this.fixedY = (double)0.0F;
      this.delay = 0;
      mc.field_71428_T.field_74278_d = 1.0F;
      this.endPos = null;
      this.hadGround = false;
      this.freeze = false;
      this.disableLogger = false;
      this.flyTimer.reset();
      this.packets.clear();
   }

   @EventTarget
   public void onUpdate(UpdateEvent event) {
      int buttonIndex = Arrays.asList(this.buttonValue.getValues()).indexOf(this.buttonValue.get());
      if (this.modeValue.equals("AAC3.5.0")) {
         this.freezeTimer.update();
         if (this.freeze && this.freezeTimer.hasTimePassed(40)) {
            this.freezeTimer.reset();
            this.freeze = false;
            this.setState(false);
         }

         if (!this.flyTimer.hasTimePassed(60)) {
            this.flyTimer.update();
            if (mc.field_71439_g.field_70122_E) {
               MovementUtils.INSTANCE.jump(true, false, 0.42);
            } else {
               MovementUtils.INSTANCE.forward(this.zitter ? -0.21 : 0.21);
               this.zitter = !this.zitter;
            }

            this.hadGround = false;
         } else {
            if (mc.field_71439_g.field_70122_E) {
               this.hadGround = true;
            }

            if (this.hadGround) {
               if (mc.field_71439_g.field_70122_E) {
                  mc.field_71439_g.func_70634_a(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.2, mc.field_71439_g.field_70161_v);
               }

               float vanillaSpeed = 2.0F;
               mc.field_71439_g.field_71075_bZ.field_75100_b = false;
               mc.field_71439_g.field_70181_x = (double)0.0F;
               mc.field_71439_g.field_70159_w = (double)0.0F;
               mc.field_71439_g.field_70179_y = (double)0.0F;
               if (mc.field_71474_y.field_74314_A.func_151470_d()) {
                  EntityPlayerSP var10000 = mc.field_71439_g;
                  var10000.field_70181_x += (double)2.0F;
               }

               if (mc.field_71474_y.field_74311_E.func_151470_d()) {
                  EntityPlayerSP var6 = mc.field_71439_g;
                  var6.field_70181_x -= (double)2.0F;
               }

               MovementUtils.INSTANCE.strafe(2.0F);
               if (Mouse.isButtonDown(buttonIndex) && !this.doTeleport) {
                  mc.field_71439_g.func_70634_a(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - (double)11.0F, mc.field_71439_g.field_70161_v);
                  this.disableLogger = true;
                  this.packets.forEach((packet) -> mc.func_147114_u().func_147297_a(packet));
                  this.freezeTimer.reset();
                  this.freeze = true;
               }

               this.doTeleport = Mouse.isButtonDown(buttonIndex);
            }
         }
      } else {
         if (mc.field_71462_r == null && Mouse.isButtonDown(buttonIndex) && this.delay <= 0) {
            this.endPos = this.objectPosition.func_178782_a();
            if (((Block)Objects.requireNonNull(BlockUtils.getBlock(this.endPos))).func_149688_o() == Material.field_151579_a) {
               this.endPos = null;
               return;
            }

            this.alert("§7[§8§lTeleport§7] §3Position was set to §8" + this.endPos.func_177958_n() + "§3, §8" + ((((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_180640_a(mc.field_71441_e, this.objectPosition.func_178782_a(), ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_176223_P()) == null ? (double)this.endPos.func_177956_o() + ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.endPos))).func_149669_A() : ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_180640_a(mc.field_71441_e, this.objectPosition.func_178782_a(), ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_176223_P()).field_72337_e) + this.fixedY) + "§3, §8" + this.endPos.func_177952_p());
            this.delay = 6;
            this.endX = (double)this.endPos.func_177958_n() + (double)0.5F;
            this.endY = (double)this.endPos.func_177956_o() + (double)1.0F;
            this.endZ = (double)this.endPos.func_177952_p() + (double)0.5F;
         }

         if (this.delay > 0) {
            --this.delay;
         }

         if (this.endPos != null) {
            switch (((String)this.modeValue.get()).toLowerCase()) {
               case "blink":
                  if (mc.field_71439_g.func_70093_af() || !(Boolean)this.needSneak.get()) {
                     mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
                     PathUtils.findBlinkPath(this.endX, this.endY, this.endZ).forEach((vector3d) -> {
                        mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(vector3d.field_72450_a, vector3d.field_72448_b, vector3d.field_72449_c, true));
                        mc.field_71439_g.func_70107_b(this.endX, this.endY, this.endZ);
                     });
                     mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                     this.alert("§7[§8§lTeleport§7] §3You were teleported to §8" + this.endX + "§3, §8" + this.endY + "§3, §8" + this.endZ);
                     this.endPos = null;
                  }
                  break;
               case "flag":
                  if (mc.field_71439_g.func_70093_af() || !(Boolean)this.needSneak.get()) {
                     mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)5.0F, mc.field_71439_g.field_70161_v, true));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t + (double)0.5F, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + (double)0.5F, true));
                     MovementUtils.INSTANCE.forward(0.04);
                     mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                     this.alert("§7[§8§lTeleport§7] §3You were teleported to §8" + this.endX + "§3, §8" + this.endY + "§3, §8" + this.endZ);
                     this.endPos = null;
                  }
                  break;
               case "bwrel":
                  if (mc.field_71439_g.func_70093_af() || !(Boolean)this.needSneak.get()) {
                     mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 9.25078381072525, mc.field_71439_g.field_70161_v);
                     mc.field_71439_g.field_70181_x = 1.0420262142255328;
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                  }
                  break;
               case "rewinside":
                  mc.field_71439_g.field_70181_x = 0.1;
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.6, mc.field_71439_g.field_70161_v, true));
                  if ((double)((int)mc.field_71439_g.field_70165_t) == this.endX && (double)((int)mc.field_71439_g.field_70163_u) == this.endY && (double)((int)mc.field_71439_g.field_70161_v) == this.endZ) {
                     this.alert("§7[§8§lTeleport§7] §3You were teleported to §8" + this.endX + "§3, §8" + this.endY + "§3, §8" + this.endZ);
                     this.endPos = null;
                  } else {
                     this.alert("§7[§8§lTeleport§7] §3Teleport try...");
                  }
                  break;
               case "oldrewinside":
                  mc.field_71439_g.field_70181_x = 0.1;
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                  mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
                  if ((double)((int)mc.field_71439_g.field_70165_t) == this.endX && (double)((int)mc.field_71439_g.field_70163_u) == this.endY && (double)((int)mc.field_71439_g.field_70161_v) == this.endZ) {
                     this.alert("§7[§8§lTeleport§7] §3You were teleported to §8" + this.endX + "§3, §8" + this.endY + "§3, §8" + this.endZ);
                     this.endPos = null;
                  } else {
                     this.alert("§7[§8§lTeleport§7] §3Teleport try...");
                  }

                  MovementUtils.INSTANCE.forward(0.04);
                  break;
               case "minesucht":
                  if (mc.field_71439_g.func_70093_af() || !(Boolean)this.needSneak.get()) {
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                     this.alert("§7[§8§lTeleport§7] §3You were teleported to §8" + this.endX + "§3, §8" + this.endY + "§3, §8" + this.endZ);
                     this.endPos = null;
                  }
                  break;
               case "tp":
                  if (mc.field_71439_g.func_70093_af() || !(Boolean)this.needSneak.get()) {
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, true));
                     mc.field_71439_g.func_70107_b(this.endX, this.endY, this.endZ);
                     this.alert("§7[§8§lTeleport§7] §3You were teleported to §8" + this.endX + "§3, §8" + this.endY + "§3, §8" + this.endZ);
                     this.endPos = null;
                  }
                  break;
               case "karhu":
                  if (mc.field_71439_g.func_70093_af() || !(Boolean)this.needSneak.get()) {
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(this.endX, this.endY, this.endZ, false));
                     mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
                     mc.field_71439_g.func_70107_b(this.endX, this.endY, this.endZ);
                     this.alert("§7[§8§lTeleport§7] §3You were teleported to §8" + this.endX + "§3, §8" + this.endY + "§3, §8" + this.endZ);
                     this.endPos = null;
                  }
            }
         }

      }
   }

   @EventTarget
   public void onRender3D(Render3DEvent event) {
      if (!this.modeValue.equals("AAC3.5.0")) {
         Vec3 lookVec = new Vec3(mc.field_71439_g.func_70040_Z().field_72450_a * (double)300.0F, mc.field_71439_g.func_70040_Z().field_72448_b * (double)300.0F, mc.field_71439_g.func_70040_Z().field_72449_c * (double)300.0F);
         Vec3 posVec = new Vec3(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.62, mc.field_71439_g.field_70161_v);
         this.objectPosition = mc.field_71439_g.field_70170_p.func_147447_a(posVec, posVec.func_178787_e(lookVec), false, (Boolean)this.ignoreNoCollision.get(), false);
         if (this.objectPosition != null && this.objectPosition.func_178782_a() != null) {
            BlockPos belowBlockPos = new BlockPos(this.objectPosition.func_178782_a().func_177958_n(), this.objectPosition.func_178782_a().func_177956_o() - 1, this.objectPosition.func_178782_a().func_177952_p());
            this.fixedY = BlockUtils.getBlock(this.objectPosition.func_178782_a()) instanceof BlockFence ? (mc.field_71441_e.func_72945_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d((double)this.objectPosition.func_178782_a().func_177958_n() + (double)0.5F - mc.field_71439_g.field_70165_t, (double)this.objectPosition.func_178782_a().func_177956_o() + (double)1.5F - mc.field_71439_g.field_70163_u, (double)this.objectPosition.func_178782_a().func_177952_p() + (double)0.5F - mc.field_71439_g.field_70161_v)).isEmpty() ? (double)0.5F : (double)0.0F) : (BlockUtils.getBlock(belowBlockPos) instanceof BlockFence ? (mc.field_71441_e.func_72945_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d((double)this.objectPosition.func_178782_a().func_177958_n() + (double)0.5F - mc.field_71439_g.field_70165_t, (double)this.objectPosition.func_178782_a().func_177956_o() + (double)0.5F - mc.field_71439_g.field_70163_u, (double)this.objectPosition.func_178782_a().func_177952_p() + (double)0.5F - mc.field_71439_g.field_70161_v)).isEmpty() && ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_180640_a(mc.field_71441_e, this.objectPosition.func_178782_a(), ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_176223_P()) != null ? (double)0.5F - ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_149669_A() : (double)0.0F) : (BlockUtils.getBlock(this.objectPosition.func_178782_a()) instanceof BlockSnow ? ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_149669_A() - (double)0.125F : (double)0.0F));
            int x = this.objectPosition.func_178782_a().func_177958_n();
            double y = (((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_180640_a(mc.field_71441_e, this.objectPosition.func_178782_a(), ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_176223_P()) == null ? (double)this.objectPosition.func_178782_a().func_177956_o() + ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_149669_A() : ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_180640_a(mc.field_71441_e, this.objectPosition.func_178782_a(), ((Block)Objects.requireNonNull(BlockUtils.getBlock(this.objectPosition.func_178782_a()))).func_176223_P()).field_72337_e) - (double)1.0F + this.fixedY;
            int z = this.objectPosition.func_178782_a().func_177952_p();
            if (!(BlockUtils.getBlock(this.objectPosition.func_178782_a()) instanceof BlockAir)) {
               RenderManager renderManager = mc.func_175598_ae();
               GL11.glBlendFunc(770, 771);
               GL11.glEnable(3042);
               GL11.glLineWidth(2.0F);
               GL11.glDisable(3553);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               RenderUtils.glColor(this.modeValue.equals("minesucht") && (double)mc.field_71439_g.func_180425_c().func_177956_o() != y + (double)1.0F ? new Color(255, 0, 0, 90) : (!mc.field_71441_e.func_72945_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d((double)x + (double)0.5F - mc.field_71439_g.field_70165_t, y + (double)1.0F - mc.field_71439_g.field_70163_u, (double)z + (double)0.5F - mc.field_71439_g.field_70161_v)).isEmpty() ? new Color(255, 0, 0, 90) : new Color(0, 255, 0, 90)));
               RenderUtils.drawFilledBox(new AxisAlignedBB((double)x - renderManager.field_78725_b, y + (double)1.0F - renderManager.field_78726_c, (double)z - renderManager.field_78723_d, (double)x - renderManager.field_78725_b + (double)1.0F, y + 1.2 - renderManager.field_78726_c, (double)z - renderManager.field_78723_d + (double)1.0F));
               GL11.glEnable(3553);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               GL11.glDisable(3042);
               RenderUtils.renderNameTag(Math.round(mc.field_71439_g.func_70011_f((double)x + (double)0.5F, y + (double)1.0F, (double)z + (double)0.5F)) + "m", (double)x + (double)0.5F, y + 1.7, (double)z + (double)0.5F);
               GlStateManager.func_179117_G();
            }

         }
      }
   }

   @EventTarget
   public void onMove(MoveEvent event) {
      if (this.modeValue.equals("aac3.5.0") && this.freeze) {
         event.zeroXZ();
      }

   }

   @EventTarget
   public void onPacket(PacketEvent event) {
      Packet<?> packet = event.getPacket();
      if (!this.disableLogger) {
         if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
            switch (((String)this.modeValue.get()).toLowerCase()) {
               case "spoof":
                  if (this.endPos != null) {
                     packetPlayer.field_149479_a = (double)this.endPos.func_177958_n() + (double)0.5F;
                     packetPlayer.field_149477_b = (double)(this.endPos.func_177956_o() + 1);
                     packetPlayer.field_149478_c = (double)this.endPos.func_177952_p() + (double)0.5F;
                     mc.field_71439_g.func_70107_b((double)this.endPos.func_177958_n() + (double)0.5F, (double)(this.endPos.func_177956_o() + 1), (double)this.endPos.func_177952_p() + (double)0.5F);
                  }
                  break;
               case "aac3.5.0":
                  if (!this.flyTimer.hasTimePassed(60)) {
                     return;
                  }

                  event.cancelEvent();
                  if (!(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
                     return;
                  }

                  this.packets.add(packet);
            }
         }

      }
   }

   public String getTag() {
      return (String)this.modeValue.get();
   }
}
