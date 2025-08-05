package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "XRay",
   category = ModuleCategory.VISUAL,
   autoDisable = EnumAutoDisableType.RESPAWN,
   moduleCommand = false
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/XRay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "xrayBlocks", "", "Lnet/minecraft/block/Block;", "getXrayBlocks", "()Ljava/util/List;", "onToggle", "", "state", "", "CrossSine"}
)
public final class XRay extends Module {
   @NotNull
   private final List xrayBlocks;

   public XRay() {
      Block[] var1 = new Block[19];
      Block var2 = Blocks.field_150324_C;
      Intrinsics.checkNotNullExpressionValue(var2, "bed");
      var1[0] = var2;
      var2 = Blocks.field_150365_q;
      Intrinsics.checkNotNullExpressionValue(var2, "coal_ore");
      var1[1] = var2;
      var2 = Blocks.field_150366_p;
      Intrinsics.checkNotNullExpressionValue(var2, "iron_ore");
      var1[2] = var2;
      var2 = Blocks.field_150352_o;
      Intrinsics.checkNotNullExpressionValue(var2, "gold_ore");
      var1[3] = var2;
      var2 = Blocks.field_150450_ax;
      Intrinsics.checkNotNullExpressionValue(var2, "redstone_ore");
      var1[4] = var2;
      var2 = Blocks.field_150369_x;
      Intrinsics.checkNotNullExpressionValue(var2, "lapis_ore");
      var1[5] = var2;
      var2 = Blocks.field_150482_ag;
      Intrinsics.checkNotNullExpressionValue(var2, "diamond_ore");
      var1[6] = var2;
      var2 = Blocks.field_150412_bA;
      Intrinsics.checkNotNullExpressionValue(var2, "emerald_ore");
      var1[7] = var2;
      var2 = Blocks.field_150449_bY;
      Intrinsics.checkNotNullExpressionValue(var2, "quartz_ore");
      var1[8] = var2;
      var2 = Blocks.field_150402_ci;
      Intrinsics.checkNotNullExpressionValue(var2, "coal_block");
      var1[9] = var2;
      var2 = Blocks.field_150339_S;
      Intrinsics.checkNotNullExpressionValue(var2, "iron_block");
      var1[10] = var2;
      var2 = Blocks.field_150340_R;
      Intrinsics.checkNotNullExpressionValue(var2, "gold_block");
      var1[11] = var2;
      var2 = Blocks.field_150484_ah;
      Intrinsics.checkNotNullExpressionValue(var2, "diamond_block");
      var1[12] = var2;
      var2 = Blocks.field_150475_bE;
      Intrinsics.checkNotNullExpressionValue(var2, "emerald_block");
      var1[13] = var2;
      var2 = Blocks.field_150451_bX;
      Intrinsics.checkNotNullExpressionValue(var2, "redstone_block");
      var1[14] = var2;
      var2 = Blocks.field_150368_y;
      Intrinsics.checkNotNullExpressionValue(var2, "lapis_block");
      var1[15] = var2;
      var2 = Blocks.field_150474_ac;
      Intrinsics.checkNotNullExpressionValue(var2, "mob_spawner");
      var1[16] = var2;
      var2 = Blocks.field_150378_br;
      Intrinsics.checkNotNullExpressionValue(var2, "end_portal_frame");
      var1[17] = var2;
      var2 = Blocks.field_150483_bI;
      Intrinsics.checkNotNullExpressionValue(var2, "command_block");
      var1[18] = var2;
      this.xrayBlocks = CollectionsKt.mutableListOf(var1);
      CommandManager var10000 = CrossSine.INSTANCE.getCommandManager();
      int $i$f$emptyArray = 0;
      String[] var3 = new String[0];
      var10000.registerCommand(new Command(var3) {
         public void execute(@NotNull String[] args) {
            Intrinsics.checkNotNullParameter(args, "args");
            if (args.length > 1) {
               if (StringsKt.equals(args[1], "add", true)) {
                  if (args.length > 2) {
                     try {
                        Block block;
                        try {
                           block = Block.func_149729_e(Integer.parseInt(args[2]));
                        } catch (NumberFormatException var8) {
                           Block tmpBlock = Block.func_149684_b(args[2]);
                           if (Block.func_149682_b(tmpBlock) <= 0 || tmpBlock == null) {
                              this.alert("§7Block §8" + args[2] + "§7 does not exist!");
                              return;
                           }

                           block = tmpBlock;
                        }

                        if (XRay.this.getXrayBlocks().contains(block)) {
                           this.alert("This block is already on the list.");
                           return;
                        }

                        List var10000 = XRay.this.getXrayBlocks();
                        Intrinsics.checkNotNullExpressionValue(block, "block");
                        var10000.add(block);
                        CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getXrayConfig());
                        this.alert("§7Added block §8" + block.func_149732_F() + "§7.");
                        this.playEdit();
                     } catch (NumberFormatException var9) {
                        this.chatSyntaxError();
                     }

                     return;
                  }

                  this.chatSyntax("xray add <block_id>");
                  return;
               }

               if (StringsKt.equals(args[1], "remove", true)) {
                  if (args.length > 2) {
                     try {
                        Block block = null;

                        try {
                           Block block = Block.func_149729_e(Integer.parseInt(args[2]));
                           Intrinsics.checkNotNullExpressionValue(block, "getBlockById(args[2].toInt())");
                           block = block;
                        } catch (NumberFormatException var10) {
                           Block var16 = Block.func_149684_b(args[2]);
                           Intrinsics.checkNotNullExpressionValue(var16, "getBlockFromName(args[2])");
                           block = var16;
                           if (Block.func_149682_b(var16) <= 0) {
                              this.alert("§7Block §8" + args[2] + "§7 does not exist!");
                              return;
                           }
                        }

                        if (!XRay.this.getXrayBlocks().contains(block)) {
                           this.alert("This block is not on the list.");
                           return;
                        }

                        XRay.this.getXrayBlocks().remove(block);
                        CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getXrayConfig());
                        this.alert("§7Removed block §8" + block.func_149732_F() + "§7.");
                        this.playEdit();
                     } catch (NumberFormatException var11) {
                        this.chatSyntaxError();
                     }

                     return;
                  }

                  this.chatSyntax("xray remove <block_id>");
                  return;
               }

               if (StringsKt.equals(args[1], "list", true)) {
                  this.alert("§8Xray blocks:");
                  Iterable $this$forEach$iv = (Iterable)XRay.this.getXrayBlocks();
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     Block it = (Block)element$iv;
                     int var7 = 0;
                     this.alert("§8" + it.func_149732_F() + " §7-§c " + Block.func_149682_b(it));
                  }

                  return;
               }
            }

            this.chatSyntax("xray <add, remove, list>");
         }
      });
   }

   @NotNull
   public final List getXrayBlocks() {
      return this.xrayBlocks;
   }

   public void onToggle(boolean state) {
      MinecraftInstance.mc.field_71438_f.func_72712_a();
   }
}
