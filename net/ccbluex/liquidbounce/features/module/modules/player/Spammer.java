package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Spammer",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\u0010\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Spammer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "endingCharsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "insultMessageValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "lastIndex", "", "messageValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "replace", "str", "replaceAbuse", "CrossSine"}
)
public final class Spammer extends Module {
   @NotNull
   private final IntegerRangeValue delayValue = new IntegerRangeValue("Delay", 1000, 1000, 0, 5000, (Function0)null, 32, (DefaultConstructorMarker)null);
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final IntegerValue endingCharsValue;
   @NotNull
   private final Value messageValue;
   @NotNull
   private final Value insultMessageValue;
   @NotNull
   private final MSTimer msTimer;
   private long delay;
   private int lastIndex;

   public Spammer() {
      String[] var1 = new String[]{"Single", "Insult", "OrderInsult"};
      this.modeValue = new ListValue("Mode", var1, "Single");
      this.endingCharsValue = new IntegerValue("EndingRandomChars", 5, 0, 30);
      this.messageValue = (new TextValue("Message", "Buy %r Minecraft %r Legit %r and %r stop %r using %r cracked %r servers %r%r")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !Spammer.this.modeValue.contains("insult");
         }
      });
      this.insultMessageValue = (new TextValue("InsultMessage", "[%s] %w [%s]")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Spammer.this.modeValue.contains("insult");
         }
      });
      this.msTimer = new MSTimer();
      this.delay = TimeUtils.INSTANCE.randomDelay(this.delayValue.get().getFirst(), this.delayValue.get().getLast());
      this.lastIndex = -1;
   }

   public void onEnable() {
      this.lastIndex = -1;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71462_r == null || !(MinecraftInstance.mc.field_71462_r instanceof GuiChat)) {
         if (this.modeValue.equals("Single") && StringsKt.startsWith$default((String)this.messageValue.get(), ".", false, 2, (Object)null)) {
            CrossSine.INSTANCE.getCommandManager().executeCommands((String)this.messageValue.get());
         } else {
            if (this.msTimer.hasTimePassed(this.delay)) {
               EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
               String var4 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               String var10001;
               if (Intrinsics.areEqual((Object)var4, (Object)"insult")) {
                  var10001 = this.replaceAbuse(KillSay.INSTANCE.getRandomOne());
               } else if (Intrinsics.areEqual((Object)var4, (Object)"orderinsult")) {
                  int var3 = this.lastIndex++;
                  if (this.lastIndex >= KillSay.INSTANCE.getInsultWords().size() - 1) {
                     this.lastIndex = 0;
                  }

                  var10001 = this.replaceAbuse((String)KillSay.INSTANCE.getInsultWords().get(this.lastIndex));
               } else {
                  var10001 = this.replace((String)this.messageValue.get());
               }

               var10000.func_71165_d(var10001);
               this.msTimer.reset();
               this.delay = TimeUtils.INSTANCE.randomDelay(this.delayValue.get().getFirst(), this.delayValue.get().getLast());
            }

         }
      }
   }

   private final String replaceAbuse(String str) {
      return this.replace(StringsKt.replace$default((String)this.insultMessageValue.get(), "%w", str, false, 4, (Object)null));
   }

   private final String replace(String str) {
      String var10000 = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(str, "%r", String.valueOf(RandomUtils.nextInt(0, 99)), false, 4, (Object)null), "%s", RandomUtils.INSTANCE.randomString(3), false, 4, (Object)null), "%c", RandomUtils.INSTANCE.randomString(1), false, 4, (Object)null);
      String var3;
      if (CrossSine.INSTANCE.getCombatManager().getTarget() != null) {
         EntityLivingBase var10002 = CrossSine.INSTANCE.getCombatManager().getTarget();
         Intrinsics.checkNotNull(var10002);
         var3 = var10002.func_70005_c_();
      } else {
         var3 = "You";
      }

      String var2 = var3;
      Intrinsics.checkNotNullExpressionValue(var2, "if (CrossSine.combatMana…t!!.name } else { \"You\" }");
      return Intrinsics.stringPlus(StringsKt.replace$default(var10000, "%name%", var2, false, 4, (Object)null), RandomUtils.INSTANCE.randomString(((Number)this.endingCharsValue.get()).intValue()));
   }
}
