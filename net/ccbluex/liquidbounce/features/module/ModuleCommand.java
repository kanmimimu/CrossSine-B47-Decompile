package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.value.BlockValue;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "(Lnet/ccbluex/liquidbounce/features/module/Module;Ljava/util/List;)V", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "getValues", "()Ljava/util/List;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"}
)
public final class ModuleCommand extends Command {
   @NotNull
   private final Module module;
   @NotNull
   private final List values;

   public ModuleCommand(@NotNull Module module, @NotNull List values) {
      Intrinsics.checkNotNullParameter(module, "module");
      Intrinsics.checkNotNullParameter(values, "values");
      String var4 = module.getName().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      int $i$f$emptyArray = 0;
      super(var4, new String[0]);
      this.module = module;
      this.values = values;
      if (this.values.isEmpty()) {
         throw new IllegalArgumentException("Values are empty!");
      }
   }

   // $FF: synthetic method
   public ModuleCommand(Module var1, List var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = var1.getValues();
      }

      this(var1, var2);
   }

   @NotNull
   public final Module getModule() {
      return this.module;
   }

   @NotNull
   public final List getValues() {
      return this.values;
   }

   public void execute(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      Iterable $this$filter$iv = (Iterable)this.values;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         Value it = (Value)element$iv$iv;
         int it = 0;
         if (!(it instanceof FontValue)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      String valueNames = CollectionsKt.joinToString$default((Iterable)((List)destination$iv$iv), (CharSequence)"/", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, null.INSTANCE, 30, (Object)null);
      String $this$filterTo$iv$iv = this.module.getName().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue($this$filterTo$iv$iv, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      String moduleName = $this$filterTo$iv$iv;
      if (args.length < 2) {
         this.chatSyntax(this.values.size() == 1 ? $this$filterTo$iv$iv + ' ' + valueNames + " <value>" : $this$filterTo$iv$iv + " <" + valueNames + '>');
      } else {
         Value value = this.module.getValue(args[1]);
         if (value == null) {
            this.chatSyntax($this$filterTo$iv$iv + " <" + valueNames + '>');
         } else {
            if (value instanceof BoolValue) {
               boolean newValue = !(Boolean)((BoolValue)value).get();
               ((BoolValue)value).set(newValue);
               this.alert("§7" + this.module.getName() + " §8" + args[1] + "§7 was toggled " + (newValue ? "§8on§7" : "§8off§7."));
               this.playEdit();
            } else {
               if (args.length < 3) {
                  if (!(value instanceof IntegerValue) && !(value instanceof FloatValue) && !(value instanceof TextValue)) {
                     if (value instanceof ListValue) {
                        StringBuilder var44 = (new StringBuilder()).append($this$filterTo$iv$iv).append(' ');
                        String var23 = args[1].toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var23, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        var44 = var44.append(var23).append(" <");
                        var23 = ArraysKt.joinToString$default(((ListValue)value).getValues(), (CharSequence)"/", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var23, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        this.chatSyntax(var44.append(var23).append('>').toString());
                     }
                  } else {
                     StringBuilder var43 = (new StringBuilder()).append($this$filterTo$iv$iv).append(' ');
                     String var22 = args[1].toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var22, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     this.chatSyntax(var43.append(var22).append(" <value>").toString());
                  }

                  return;
               }

               try {
                  if (value instanceof BlockValue) {
                     try {
                        $i$f$filterTo = Integer.parseInt(args[2]);
                     } catch (NumberFormatException var15) {
                        Block var39 = Block.func_149684_b(args[2]);
                        Integer var40;
                        if (var39 == null) {
                           var40 = null;
                        } else {
                           Block it = var39;
                           int var35 = 0;
                           var40 = Block.func_149682_b(it);
                        }

                        Integer tmpId = var40;
                        if (tmpId == null || tmpId <= 0) {
                           this.alert("§7Block §8" + args[2] + "§7 does not exist!");
                           return;
                        }

                        $i$f$filterTo = tmpId;
                     }

                     ((BlockValue)value).set($i$f$filterTo);
                     StringBuilder var42 = (new StringBuilder()).append("§7").append(this.module.getName()).append(" §8");
                     String var30 = args[1].toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var30, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     this.alert(var42.append(var30).append("§7 was set to §8").append(BlockUtils.getBlockName($i$f$filterTo)).append("§7.").toString());
                     this.playEdit();
                     return;
                  }

                  if (value instanceof IntegerValue) {
                     ((IntegerValue)value).set(Integer.parseInt(args[2]));
                  } else if (value instanceof FloatValue) {
                     ((FloatValue)value).set(Float.parseFloat(args[2]));
                  } else if (!(value instanceof ListValue)) {
                     if (value instanceof TextValue) {
                        TextValue var38 = (TextValue)value;
                        String $this$any$iv = StringUtils.toCompleteString(args, 2);
                        Intrinsics.checkNotNullExpressionValue($this$any$iv, "toCompleteString(args, 2)");
                        var38.set($this$any$iv);
                     }
                  } else {
                     Object[] $this$any$iv = ((ListValue)value).getValues();
                     $i$f$filterTo = 0;
                     Object var29 = $this$any$iv;
                     int tmpId = 0;
                     int var33 = $this$any$iv.length;

                     boolean var10000;
                     while(true) {
                        if (tmpId < var33) {
                           Object element$iv = ((Object[])var29)[tmpId];
                           ++tmpId;
                           int var13 = 0;
                           String var14 = ((String)element$iv).toLowerCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var14, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                           var14 = args[2].toLowerCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var14, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                           if (!Intrinsics.areEqual((Object)var14, (Object)var14)) {
                              continue;
                           }

                           var10000 = true;
                           break;
                        }

                        var10000 = false;
                        break;
                     }

                     if (!var10000) {
                        StringBuilder var10001 = (new StringBuilder()).append(moduleName).append(' ');
                        String id = args[1].toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(id, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        var10001 = var10001.append(id).append(" <");
                        id = ArraysKt.joinToString$default(((ListValue)value).getValues(), (CharSequence)"/", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(id, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        this.chatSyntax(var10001.append(id).append('>').toString());
                        return;
                     }

                     ((ListValue)value).set(args[2]);
                  }

                  this.alert("§7" + this.module.getName() + " §8" + args[1] + "§7 was set to §8" + value.get() + "§7.");
                  this.playEdit();
               } catch (NumberFormatException var16) {
                  this.alert("§8" + args[2] + "§7 cannot be converted to number!");
               }
            }

         }
      }
   }

   @NotNull
   public List tabComplete(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         switch (args.length) {
            case 1:
               Iterable $this$map$iv = (Iterable)this.values;
               int $i$f$filter = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList());
               int $i$f$filterTo = 0;

               for(Object element$iv$iv : $this$map$iv) {
                  Value it = (Value)element$iv$iv;
                  int it = 0;
                  if (!(it instanceof FontValue) && StringsKt.startsWith(it.getName(), args[0], true)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               $this$map$iv = (Iterable)((List)destination$iv$iv);
               $i$f$filter = 0;
               destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
               $i$f$filterTo = 0;

               for(Object item$iv$iv : $this$map$iv) {
                  Value $this$filter$iv = (Value)item$iv$iv;
                  int $i$f$filter = 0;
                  String var53 = $this$filter$iv.getName().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var53, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                  destination$iv$iv.add(var53);
               }

               var10000 = (List)destination$iv$iv;
               break;
            case 2:
               Value $this$filter$iv = this.module.getValue(args[0]);
               if ($this$filter$iv instanceof BlockValue) {
                  Set $i$f$map = Block.field_149771_c.func_148742_b();
                  Intrinsics.checkNotNullExpressionValue($i$f$map, "blockRegistry.keys");
                  Iterable $this$map$iv = (Iterable)$i$f$map;
                  int $i$f$map = 0;
                  Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
                  int $i$f$mapTo = 0;

                  for(Object item$iv$iv : $this$map$iv) {
                     ResourceLocation var48 = (ResourceLocation)item$iv$iv;
                     int var11 = 0;
                     String var54 = var48.func_110623_a();
                     Intrinsics.checkNotNullExpressionValue(var54, "it.resourcePath");
                     String var55 = var54.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var55, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     destination$iv$iv.add(var55);
                  }

                  $this$map$iv = (Iterable)((List)destination$iv$iv);
                  $i$f$map = 0;
                  destination$iv$iv = (Collection)(new ArrayList());
                  $i$f$mapTo = 0;

                  for(Object element$iv$iv : $this$map$iv) {
                     String it = (String)element$iv$iv;
                     int var52 = 0;
                     if (StringsKt.startsWith(it, args[1], true)) {
                        destination$iv$iv.add(element$iv$iv);
                     }
                  }

                  return (List)destination$iv$iv;
               }

               if ($this$filter$iv instanceof ListValue) {
                  Iterable $this$forEach$iv = (Iterable)this.values;
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     Value value = (Value)element$iv;
                     int element$iv$iv = 0;
                     if (StringsKt.equals(value.getName(), args[0], true) && value instanceof ListValue) {
                        Object[] $this$filter$iv = ((ListValue)value).getValues();
                        int $i$f$filter = 0;
                        Collection destination$iv$iv = (Collection)(new ArrayList());
                        int $i$f$filterTo = 0;
                        Object var14 = $this$filter$iv;
                        int var15 = 0;
                        int var16 = $this$filter$iv.length;

                        while(var15 < var16) {
                           Object element$iv$iv = ((Object[])var14)[var15];
                           ++var15;
                           int var19 = 0;
                           if (StringsKt.startsWith((String)element$iv$iv, args[1], true)) {
                              destination$iv$iv.add(element$iv$iv);
                           }
                        }

                        return (List)destination$iv$iv;
                     }
                  }

                  return CollectionsKt.emptyList();
               }

               var10000 = CollectionsKt.emptyList();
               break;
            default:
               var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
